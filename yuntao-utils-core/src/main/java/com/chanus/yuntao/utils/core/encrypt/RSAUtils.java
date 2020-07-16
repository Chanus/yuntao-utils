/*
 * Copyright (c) 2020 Chanus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.chanus.yuntao.utils.core.encrypt;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA（非对称加密）加密解密工具类
 *
 * @author Chanus
 * @date 2020-06-24 11:27:21
 * @since 1.0.0
 */
public class RSAUtils {
    /**
     * 加密算法 RSA
     */
    private static final String RSA_ALGORITHM = "RSA";

    /**
     * 签名算法
     */
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * 获取公钥的 key
     */
    public static final String PUBLIC_KEY = "RSAPublicKey";

    /**
     * 获取私钥的 key
     */
    public static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * 生成密钥对(公钥和私钥)
     *
     * @param keySize 密钥位数，512/1024/2048/4096 bit
     * @return RSA 密钥对，由 Base64 编码，{@code PUBLIC_KEY} 对应公钥，{@code PRIVATE_KEY} 对应私钥
     */
    public static Map<String, String> generateKeyPair(int keySize) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
            keyPairGenerator.initialize(keySize);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            Key publicKey = keyPair.getPublic();
            Key privateKey = keyPair.getPrivate();
            Map<String, String> keyMap = new HashMap<>(2);
            keyMap.put(PUBLIC_KEY, Base64.getEncoder().encodeToString(publicKey.getEncoded()));
            keyMap.put(PRIVATE_KEY, Base64.getEncoder().encodeToString(privateKey.getEncoded()));
            return keyMap;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException occurred.", e);
        }
    }

    /**
     * 生成1024位密钥对(公钥和私钥)
     *
     * @return RSA 密钥对，由 Base64 编码，{@code PUBLIC_KEY} 对应公钥，{@code PRIVATE_KEY} 对应私钥
     */
    public static Map<String, String> generateKeyPair() {
        return generateKeyPair(1024);
    }

    /**
     * 得到公钥
     *
     * @param publicKey 密钥字符串（经过 Base64 编码）
     * @return 公钥
     */
    public static RSAPublicKey getPublicKey(String publicKey) {
        try {
            // 通过 X509 编码的 Key 指令获得公钥对象
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
            return (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("NoSuchAlgorithmException or InvalidKeySpecException occurred.", e);
        }
    }

    /**
     * 得到私钥
     *
     * @param privateKey 密钥字符串（经过 Base64 编码）
     * @return 私钥
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) {
        try {
            // 通过 PKCS#8 编码的 Key 指令获得私钥对象
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
            return (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("NoSuchAlgorithmException or InvalidKeySpecException occurred.", e);
        }
    }

    private static byte[] cipher(byte[] data, Cipher cipher, int maxBlock) throws Exception {
        int dataLength = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        while (dataLength - offSet > 0) {
            if (dataLength - offSet > maxBlock) {
                cache = cipher.doFinal(data, offSet, maxBlock);
            } else {
                cache = cipher.doFinal(data, offSet, dataLength - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * maxBlock;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    private static int getMaxBlock(int mode, int keySize) {
        return mode == Cipher.DECRYPT_MODE ? (keySize / 8) : (keySize / 8 - 11);
    }

    private static byte[] encrypt(byte[] data, Key key, int maxBlock) throws Exception {
        // 对数据加密
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return cipher(data, cipher, maxBlock);
    }

    private static byte[] decrypt(byte[] data, Key key, int maxBlock) throws Exception {
        // 对数据解密
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);

        return cipher(data, cipher, maxBlock);
    }

    /**
     * 公钥加密
     *
     * @param data      源数据
     * @param publicKey 公钥（BASE64 编码）
     * @return 公钥加密后的数据
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey) {
        try {
            RSAPublicKey rsaPublicKey = getPublicKey(publicKey);
            int maxBlock = getMaxBlock(Cipher.ENCRYPT_MODE, rsaPublicKey.getModulus().bitLength());
            return encrypt(data, rsaPublicKey, maxBlock);
        } catch (Exception e) {
            throw new RuntimeException("encrypt by public key error.", e);
        }
    }

    /**
     * 公钥加密
     *
     * @param data      源数据
     * @param publicKey 公钥（BASE64 编码）
     * @return 公钥加密后的数据
     */
    public static String encryptByPublicKey(String data, String publicKey) {
        return Base64.getEncoder().encodeToString(encryptByPublicKey(data.getBytes(), publicKey));
    }

    /**
     * 私钥加密
     *
     * @param data       源数据
     * @param privateKey 私钥（BASE64 编码）
     * @return 私钥加密后的数据
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey) {
        try {
            RSAPrivateKey rsaPrivateKey = getPrivateKey(privateKey);
            int maxBlock = getMaxBlock(Cipher.ENCRYPT_MODE, rsaPrivateKey.getModulus().bitLength());
            return encrypt(data, rsaPrivateKey, maxBlock);
        } catch (Exception e) {
            throw new RuntimeException("encrypt by private key error.", e);
        }
    }

    /**
     * 私钥加密
     *
     * @param data       源数据
     * @param privateKey 私钥（BASE64 编码）
     * @return 公钥加密后的数据
     */
    public static String encryptByPrivateKey(String data, String privateKey) {
        return Base64.getEncoder().encodeToString(encryptByPrivateKey(data.getBytes(), privateKey));
    }

    /**
     * 公钥解密
     *
     * @param data      已加密数据
     * @param publicKey 公钥（BASE64 编码）
     * @return 公钥解密后的数据
     */
    public static byte[] decryptByPublicKey(byte[] data, String publicKey) {
        try {
            RSAPublicKey rsaPublicKey = getPublicKey(publicKey);
            int maxBlock = getMaxBlock(Cipher.DECRYPT_MODE, rsaPublicKey.getModulus().bitLength());
            return decrypt(data, rsaPublicKey, maxBlock);
        } catch (Exception e) {
            throw new RuntimeException("decrypt by public key error.", e);
        }
    }

    /**
     * 公钥解密
     *
     * @param data      已加密数据
     * @param publicKey 公钥（BASE64 编码）
     * @return 公钥解密后的数据
     */
    public static String decryptByPublicKey(String data, String publicKey) {
        return new String(decryptByPublicKey(Base64.getDecoder().decode(data), publicKey));
    }

    /**
     * 私钥解密
     *
     * @param data       已加密数据
     * @param privateKey 私钥（BASE64 编码）
     * @return 私钥解密后的数据
     */
    public static byte[] decryptByPrivateKey(byte[] data, String privateKey) {
        try {
            RSAPrivateKey rsaPrivateKey = getPrivateKey(privateKey);
            int maxBlock = getMaxBlock(Cipher.DECRYPT_MODE, rsaPrivateKey.getModulus().bitLength());
            return decrypt(data, rsaPrivateKey, maxBlock);
        } catch (Exception e) {
            throw new RuntimeException("decrypt by private key error.", e);
        }
    }

    /**
     * 私钥解密
     *
     * @param data       已加密数据
     * @param privateKey 私钥（BASE64 编码）
     * @return 私钥解密后的数据
     */
    public static String decryptByPrivateKey(String data, String privateKey) {
        return new String(decryptByPrivateKey(Base64.getDecoder().decode(data), privateKey));
    }

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data       源数据
     * @param privateKey 私钥（BASE64 编码）
     * @return 数字签名
     */
    public static String sign(byte[] data, String privateKey) {
        try {
            PrivateKey rsaPrivateKey = getPrivateKey(privateKey);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(rsaPrivateKey);
            signature.update(data);
            return new String(Base64.getEncoder().encode(signature.sign()));
        } catch (Exception e) {
            throw new RuntimeException("generate sign error.", e);
        }
    }

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data       源数据
     * @param privateKey 私钥（BASE64 编码）
     * @return 数字签名
     */
    public static String sign(String data, String privateKey) {
        return sign(data.getBytes(), privateKey);
    }

    /**
     * 校验数字签名
     *
     * @param data      源数据
     * @param sign      数字签名
     * @param publicKey 公钥（BASE64 编码）
     * @return {@code true} 校验数字签名成功；{@code false} 校验数字签名失败
     */
    public static boolean verify(byte[] data, String sign, String publicKey) {
        try {
            PublicKey rsaPublicKey = getPublicKey(publicKey);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(rsaPublicKey);
            signature.update(data);
            return signature.verify(Base64.getDecoder().decode(sign));
        } catch (Exception e) {
            throw new RuntimeException("verify sign error.", e);
        }
    }

    /**
     * 校验数字签名
     *
     * @param data      源数据
     * @param sign      数字签名
     * @param publicKey 公钥（BASE64 编码）
     * @return {@code true} 校验数字签名成功；{@code false} 校验数字签名失败
     */
    public static boolean verify(String data, String sign, String publicKey) {
        return verify(data.getBytes(), sign, publicKey);
    }
}
