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

import com.chanus.yuntao.utils.core.FileUtils;
import com.chanus.yuntao.utils.core.IOUtils;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

/**
 * DES（对称加密）加密解密工具类
 *
 * @author Chanus
 * @date 2020-10-29 21:23:11
 * @since 1.4.3
 */
public class DESUtils {
    /**
     * DES 加密
     */
    private static final String KEY_ALGORITHM = "DES";
    /**
     * DES CBC 加密算法
     */
    private static final String CIPHER_ALGORITHM_CBC = "DES/CBC/PKCS5PADDING";
    /**
     * DES ECB 加密算法
     */
    private static final String CIPHER_ALGORITHM_ECB = "DES/ECB/PKCS5PADDING";
    /**
     * 偏移变量，固定占8位字节
     */
    private static final String INIT_VECTOR = "12345678";

    /**
     * DES 加密字符串
     *
     * @param data 待加密字符串
     * @param key  加密密码，长度不能小于8位
     * @param iv   偏移变量，8位字节
     * @return Base64 转码后的加密数据
     */
    public static String encrypt(String data, String key, String iv) {
        try {
            Key secretKey = generateKey(key);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

            return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * DES 加密字符串，使用默认偏移向量
     *
     * @param data 待加密字符串
     * @param key  加密密码，长度不能小于8位
     * @return Base64 转码后的加密数据
     */
    public static String encrypt(String data, String key) {
        return encrypt(data, key, INIT_VECTOR);
    }

    /**
     * DES 解密字符串
     *
     * @param data 待解密字符串
     * @param key  解密密码，长度不能小于8位
     * @param iv   偏移变量，8位字节
     * @return 解密后内容
     */
    public static String decrypt(String data, String key, String iv) {
        try {
            Key secretKey = generateKey(key);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(data)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * DES 解密字符串，使用默认偏移向量
     *
     * @param data 待解密字符串
     * @param key  解密密码，长度不能小于8位
     * @return 解密后内容
     */
    public static String decrypt(String data, String key) {
        return decrypt(data, key, INIT_VECTOR);
    }

    /**
     * DES 加密文件
     *
     * @param srcFile  待加密的文件
     * @param destFile 加密后的文件
     * @param key      解密密码，长度不能小于8位
     * @param iv       偏移变量，8位字节
     * @return 加密后的文件
     */
    public static File encryptFile(File srcFile, File destFile, String key, String iv) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        CipherInputStream cipherInputStream = null;
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
            cipher.init(Cipher.ENCRYPT_MODE, generateKey(key), ivParameterSpec);
            inputStream = new FileInputStream(srcFile);
            outputStream = new FileOutputStream(destFile);
            cipherInputStream = new CipherInputStream(inputStream, cipher);
            byte[] buffer = new byte[1024];
            int r;
            while ((r = cipherInputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, r);
            }

            return destFile;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(cipherInputStream, inputStream, outputStream);
        }

        return null;
    }

    /**
     * DES 加密文件
     *
     * @param srcPath  待加密的文件路径
     * @param destPath 加密后的文件路径
     * @param key      解密密码，长度不能小于8位
     * @param iv       偏移变量，8位字节
     * @return 加密后的文件
     */
    public static File encryptFile(String srcPath, String destPath, String key, String iv) {
        return encryptFile(new File(srcPath), new File(destPath), key, iv);
    }

    /**
     * DES 加密文件，使用默认偏移向量
     *
     * @param srcFile  待加密的文件
     * @param destFile 加密后的文件
     * @param key      解密密码，长度不能小于8位
     * @return 加密后的文件
     */
    public static File encryptFile(File srcFile, File destFile, String key) {
        return encryptFile(srcFile, destFile, key, INIT_VECTOR);
    }

    /**
     * DES 加密文件，使用默认偏移向量
     *
     * @param srcPath  待加密的文件路径
     * @param destPath 加密后的文件路径
     * @param key      解密密码，长度不能小于8位
     * @return 加密后的文件
     */
    public static File encryptFile(String srcPath, String destPath, String key) {
        return encryptFile(srcPath, destPath, key, INIT_VECTOR);
    }

    /**
     * DES 解密文件
     *
     * @param srcFile  已加密的文件
     * @param destFile 解密后的文件
     * @param key      解密密码，长度不能小于8位
     * @param iv       偏移变量，8位字节
     * @return 解密后的文件
     */
    public static File decryptFile(File srcFile, File destFile, String key, String iv) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        CipherOutputStream cipherOutputStream = null;
        try {
            FileUtils.createFile(destFile);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
            cipher.init(Cipher.DECRYPT_MODE, generateKey(key), ivParameterSpec);
            inputStream = new FileInputStream(srcFile);
            outputStream = new FileOutputStream(destFile);
            cipherOutputStream = new CipherOutputStream(outputStream, cipher);
            byte[] buffer = new byte[1024];
            int r;
            while ((r = inputStream.read(buffer)) >= 0) {
                cipherOutputStream.write(buffer, 0, r);
            }

            return destFile;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(cipherOutputStream, inputStream, outputStream);
        }

        return null;
    }

    /**
     * DES 解密文件
     *
     * @param srcPath  已加密的文件路径
     * @param destPath 解密后的文件路径
     * @param key      解密密码，长度不能小于8位
     * @param iv       偏移变量，8位字节
     * @return 解密后的文件
     */
    public static File decryptFile(String srcPath, String destPath, String key, String iv) {
        return decryptFile(new File(srcPath), new File(destPath), key, iv);
    }

    /**
     * DES 解密文件，使用默认偏移向量
     *
     * @param srcFile  已加密的文件
     * @param destFile 解密后的文件
     * @param key      解密密码，长度不能小于8位
     * @return 解密后的文件
     */
    public static File decryptFile(File srcFile, File destFile, String key) {
        return decryptFile(srcFile, destFile, key, INIT_VECTOR);
    }

    /**
     * DES 解密文件，使用默认偏移向量
     *
     * @param srcPath  已加密的文件路径
     * @param destPath 解密后的文件路径
     * @param key      解密密码，长度不能小于8位
     * @return 解密后的文件
     */
    public static File decryptFile(String srcPath, String destPath, String key) {
        return decryptFile(srcPath, destPath, key, INIT_VECTOR);
    }

    /**
     * DES ECB 模式加密字符串
     *
     * @param data 待加密字符串
     * @param key  解密密码，长度不能小于8位
     * @return Base64 转码后的加密数据
     */
    public static String encryptWithEcb(String data, String key) {
        try {
            // 创建密码器
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
            // 使用密钥初始化，设置为加密模式
            cipher.init(Cipher.ENCRYPT_MODE, generateKey(key));
            // 通过Base64转码加密数据后返回
            return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * DES ECB 模式解密字符串
     *
     * @param data 待解密字符串
     * @param key  解密密码，长度不能小于8位
     * @return 解密后内容
     */
    public static String decryptWithEcb(String data, String key) {
        try {
            // 创建密码器
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
            // 使用密钥初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, generateKey(key));
            // 返回解密后数据
            return new String(cipher.doFinal(Base64.getDecoder().decode(data)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 生成 DES 加密密钥
     *
     * @param key 加密密码，长度不能小于8位
     * @return {@link javax.crypto.SecretKey}
     * @throws Exception 异常
     */
    private static Key generateKey(String key) throws Exception {
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        return secretKeyFactory.generateSecret(desKeySpec);
    }
}
