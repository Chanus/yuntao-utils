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

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * HMAC 加密工具类
 *
 * @author Chanus
 * @date 2020-07-21 11:48:12
 * @since 1.1.0
 */
public class HMACUtils {
    /**
     * HMAC 加密方式
     */
    public enum HMACEnum {
        HMAC_SHA1("HmacSHA1"),
        HMAC_SHA224("HmacSHA224"),
        HMAC_SHA256("HmacSHA256"),
        HMAC_SHA384("HmacSHA384"),
        HMAC_SHA512("HmacSHA512"),
        HMAC_MD5("HmacMD5");

        private final String hmacType;

        public String getHmacType() {
            return hmacType;
        }

        HMACEnum(String hmacType) {
            this.hmacType = hmacType;
        }
    }

    /**
     * HMAC 加密
     *
     * @param data     待加密数据
     * @param key      密钥
     * @param hmacType 加密方式
     * @return 加密结果
     */
    public static String encrypt(String data, String key, HMACEnum hmacType) {
        try {
            Mac hmac = Mac.getInstance(hmacType.getHmacType());
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), hmacType.getHmacType());
            hmac.init(secretKeySpec);
            byte[] array = hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100), 1, 3);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("HMAC encrypt error.", e);
        }
    }

    /**
     * HMACSHA1 加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 加密结果
     */
    public static String hmacSHA1(String data, String key) {
        return encrypt(data, key, HMACEnum.HMAC_SHA1);
    }

    /**
     * HMACSHA224 加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 加密结果
     */
    public static String hmacSHA224(String data, String key) {
        return encrypt(data, key, HMACEnum.HMAC_SHA224);
    }

    /**
     * HMACSHA256 加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 加密结果
     */
    public static String hmacSHA256(String data, String key) {
        return encrypt(data, key, HMACEnum.HMAC_SHA256);
    }

    /**
     * HMACSHA384 加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 加密结果
     */
    public static String hmacSHA384(String data, String key) {
        return encrypt(data, key, HMACEnum.HMAC_SHA384);
    }

    /**
     * HMACSHA512 加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 加密结果
     */
    public static String hmacSHA512(String data, String key) {
        return encrypt(data, key, HMACEnum.HMAC_SHA512);
    }

    /**
     * HMACMD5 加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 加密结果
     */
    public static String hmacMD5(String data, String key) {
        return encrypt(data, key, HMACEnum.HMAC_MD5);
    }

    /**
     * HMAC 加密校验
     *
     * @param data     明文数据
     * @param key      密钥
     * @param cipher   密文字符串
     * @param hmacType 加密方式
     * @return {@code true} 验证通过；{@code false} 验证不通过
     */
    public static boolean verify(String data, String key, String cipher, HMACEnum hmacType) {
        return Objects.equals(encrypt(data, key, hmacType), cipher);
    }

    /**
     * HMACSHA1 加密校验
     *
     * @param data   待加密数据
     * @param key    密钥
     * @param cipher 密文字符串
     * @return {@code true} 验证通过；{@code false} 验证不通过
     */
    public static boolean verifyHmacSHA1(String data, String key, String cipher) {
        return verify(data, key, cipher, HMACEnum.HMAC_SHA1);
    }

    /**
     * HMACSHA224 加密校验
     *
     * @param data   待加密数据
     * @param key    密钥
     * @param cipher 密文字符串
     * @return {@code true} 验证通过；{@code false} 验证不通过
     */
    public static boolean verifyHmacSHA224(String data, String key, String cipher) {
        return verify(data, key, cipher, HMACEnum.HMAC_SHA224);
    }

    /**
     * HMACSHA256 加密校验
     *
     * @param data   待加密数据
     * @param key    密钥
     * @param cipher 密文字符串
     * @return {@code true} 验证通过；{@code false} 验证不通过
     */
    public static boolean verifyHmacSHA256(String data, String key, String cipher) {
        return verify(data, key, cipher, HMACEnum.HMAC_SHA256);
    }

    /**
     * HMACSHA384 加密校验
     *
     * @param data   待加密数据
     * @param key    密钥
     * @param cipher 密文字符串
     * @return {@code true} 验证通过；{@code false} 验证不通过
     */
    public static boolean verifyHmacSHA384(String data, String key, String cipher) {
        return verify(data, key, cipher, HMACEnum.HMAC_SHA384);
    }

    /**
     * HMACSHA512 加密校验
     *
     * @param data   待加密数据
     * @param key    密钥
     * @param cipher 密文字符串
     * @return {@code true} 验证通过；{@code false} 验证不通过
     */
    public static boolean verifyHmacSHA512(String data, String key, String cipher) {
        return verify(data, key, cipher, HMACEnum.HMAC_SHA512);
    }

    /**
     * HMACMD5 加密校验
     *
     * @param data   待加密数据
     * @param key    密钥
     * @param cipher 密文字符串
     * @return {@code true} 验证通过；{@code false} 验证不通过
     */
    public static boolean verifyHmacMD5(String data, String key, String cipher) {
        return verify(data, key, cipher, HMACEnum.HMAC_MD5);
    }
}
