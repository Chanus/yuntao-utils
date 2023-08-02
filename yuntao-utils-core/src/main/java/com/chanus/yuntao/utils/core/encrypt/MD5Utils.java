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

import java.security.MessageDigest;
import java.util.Objects;

/**
 * MD5 加密工具类
 *
 * @author Chanus
 * @date 2020-06-24 11:26:00
 * @since 1.0.0
 */
public class MD5Utils {
    /**
     * MD5 加密
     */
    private static final String KEY_ALGORITHM = "MD5";
    /**
     * 用于加密的字符
     */
    private static final String[] HEX_DIGITS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    private MD5Utils() {
        throw new IllegalStateException("Utility class");
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuilder hex = new StringBuilder();
        for (byte value : b) {
            hex.append(byteToHexString(value));
        }

        return hex.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }

    /**
     * MD5 加密字符串
     *
     * @param data 明文字符串
     * @return MD5 加密后的密文字符串
     */
    public static String md5(String data) {
        try {
            return byteArrayToHexString(MessageDigest.getInstance(KEY_ALGORITHM).digest(data.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * MD5 根据密钥加密字符串
     *
     * @param data 明文字符串
     * @param key  密钥
     * @return MD5 加密后的密文字符串
     */
    public static String md5(String data, String key) {
        return md5(data + key);
    }

    /**
     * MD5 验证字符串
     *
     * @param data   明文字符串
     * @param cipher 密文字符串
     * @return {@code true} 验证通过；{@code false} 验证不通过
     */
    public static boolean verify(String data, String cipher) {
        return Objects.equals(md5(data), cipher);
    }

    /**
     * MD5 根据密钥验证字符串
     *
     * @param data   明文字符串
     * @param cipher 密文字符串
     * @param key    密钥
     * @return {@code true} 验证通过；{@code false} 验证不通过
     */
    public static boolean verify(String data, String cipher, String key) {
        return verify(data + key, cipher);
    }
}
