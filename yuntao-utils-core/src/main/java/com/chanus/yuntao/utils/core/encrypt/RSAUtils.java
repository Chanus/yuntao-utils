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
    private static final String KEY_ALGORITHM = "RSA";

    /**
     * 签名算法
     */
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * RSA 最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA 最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

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
     * @return RSA 密钥对，由 Base64 编码，{@code PUBLIC_KEY} 对应公钥，{@code PRIVATE_KEY} 对应私钥
     */
    public static Map<String, String> generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGenerator.initialize(1024);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            Map<String, String> keyMap = new HashMap<>(2);
            keyMap.put(PUBLIC_KEY, Base64.getEncoder().encodeToString(publicKey.getEncoded()));
            keyMap.put(PRIVATE_KEY, Base64.getEncoder().encodeToString(privateKey.getEncoded()));
            return keyMap;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException occurred.", e);
        }
    }

    private static byte[] cipher(byte[] data, Cipher cipher, int block) throws Exception {
        int dataLength = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        while (dataLength - offSet > 0) {
            if (dataLength - offSet > block) {
                cache = cipher.doFinal(data, offSet, block);
            } else {
                cache = cipher.doFinal(data, offSet, dataLength - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * block;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    private static byte[] encrypt(byte[] data, KeyFactory keyFactory, Key key) throws Exception {
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return cipher(data, cipher, MAX_ENCRYPT_BLOCK);
    }

    private static byte[] decrypt(byte[] data, KeyFactory keyFactory, Key key) throws Exception {
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, key);

        return cipher(data, cipher, MAX_DECRYPT_BLOCK);
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
            byte[] keyBytes = Base64.getDecoder().decode(publicKey);
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key key = keyFactory.generatePublic(x509EncodedKeySpec);

            return encrypt(data, keyFactory, key);
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
            byte[] keyBytes = Base64.getDecoder().decode(privateKey);
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key key = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

            return encrypt(data, keyFactory, key);
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
            byte[] keyBytes = Base64.getDecoder().decode(publicKey);
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key key = keyFactory.generatePublic(x509EncodedKeySpec);

            return decrypt(data, keyFactory, key);
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
            byte[] keyBytes = Base64.getDecoder().decode(privateKey);
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key key = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

            return decrypt(data, keyFactory, key);
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
            byte[] keyBytes = Base64.getDecoder().decode(privateKey);
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey key = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(key);
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
            byte[] keyBytes = Base64.getDecoder().decode(publicKey);
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey key = keyFactory.generatePublic(x509EncodedKeySpec);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(key);
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
