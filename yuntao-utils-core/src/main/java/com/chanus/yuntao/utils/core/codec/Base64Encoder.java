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
package com.chanus.yuntao.utils.core.codec;

import com.chanus.yuntao.utils.core.StringUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Base64 编码实现
 *
 * @author Chanus
 * @since 1.0.0
 */
public class Base64Encoder {
    /**
     * 标准编码表
     */
    private static final byte[] STANDARD_ENCODE_TABLE = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '+', '/'
    };
    /**
     * URL 安全的编码表，将 + 和 / 替换为 - 和 _
     */
    private static final byte[] URL_SAFE_ENCODE_TABLE = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '-', '_'
    };

    private Base64Encoder() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 编码为 Base64，非 URL 安全
     *
     * @param bytes   字节数组
     * @param lineSep 在76个 char 之后是 CRLF 还是 EOF
     * @return 编码后的字节数组
     */
    public static byte[] encode(byte[] bytes, boolean lineSep) {
        return encode(bytes, lineSep, false);
    }

    /**
     * 编码为 Base64，URL 安全
     *
     * @param bytes   字节数组
     * @param lineSep 在76个 char 之后是 CRLF 还是 EOF
     * @return 编码后的字节数组
     */
    public static byte[] encodeUrlSafe(byte[] bytes, boolean lineSep) {
        return encode(bytes, lineSep, true);
    }

    /**
     * Base64 编码，非 URL 安全
     *
     * @param source 字符串
     * @return 编码后的字符串
     */
    public static String encode(CharSequence source) {
        return encode(source, StandardCharsets.UTF_8);
    }

    /**
     * Base64 编码，URL 安全
     *
     * @param source 字符串
     * @return 编码后的字符串
     */
    public static String encodeUrlSafe(CharSequence source) {
        return encodeUrlSafe(source, StandardCharsets.UTF_8);
    }

    /**
     * Base64 编码，非 URL 安全
     *
     * @param source  字符串
     * @param charset 字符集
     * @return 编码后的字符串
     */
    public static String encode(CharSequence source, Charset charset) {
        return encode(StringUtils.bytes(source, charset));
    }

    /**
     * Base64 编码，URL 安全
     *
     * @param source  字符串
     * @param charset 字符集
     * @return 编码后的字符串
     */
    public static String encodeUrlSafe(CharSequence source, Charset charset) {
        return encodeUrlSafe(StringUtils.bytes(source, charset));
    }

    /**
     * Base64 编码，非 URL 安全
     *
     * @param source 字符串
     * @return 编码后的字符串
     */
    public static String encode(byte[] source) {
        return new String(encode(source, false), StandardCharsets.UTF_8);
    }

    /**
     * Base64 编码，URL 安全
     *
     * @param source 字符串
     * @return 编码后的字符串
     */
    public static String encodeUrlSafe(byte[] source) {
        return new String(encodeUrlSafe(source, false), StandardCharsets.UTF_8);
    }

    /**
     * Base64 编码<br>
     * 如果 {@code isMultiLine} 为 {@code true}，则每76个字符一个换行符，否则在一行显示
     *
     * @param bytes       字节数组
     * @param isMultiLine 在76个 char 之后是 CRLF 还是 EOF
     * @param isUrlSafe   是否使用 URL 安全字符，一般为 <code>false</code>
     * @return 编码后的字节数组
     */
    public static byte[] encode(byte[] bytes, boolean isMultiLine, boolean isUrlSafe) {
        if (bytes == null) {
            return null;
        }

        int len = bytes.length;
        if (len == 0) {
            return new byte[0];
        }

        int evenLen = (len / 3) * 3;
        int cnt = ((len - 1) / 3 + 1) << 2;
        int destLen = cnt + (isMultiLine ? (cnt - 1) / 76 << 1 : 0);
        byte[] dest = new byte[destLen];

        byte[] encodeTable = isUrlSafe ? URL_SAFE_ENCODE_TABLE : STANDARD_ENCODE_TABLE;

        for (int s = 0, d = 0, cc = 0; s < evenLen;) {
            int i = (bytes[s++] & 0xff) << 16 | (bytes[s++] & 0xff) << 8 | (bytes[s++] & 0xff);

            dest[d++] = encodeTable[(i >>> 18) & 0x3f];
            dest[d++] = encodeTable[(i >>> 12) & 0x3f];
            dest[d++] = encodeTable[(i >>> 6) & 0x3f];
            dest[d++] = encodeTable[i & 0x3f];

            if (isMultiLine && ++cc == 19 && d < destLen - 2) {
                dest[d++] = '\r';
                dest[d++] = '\n';
                cc = 0;
            }
        }

        // 剩余位数
        int left = len - evenLen;
        if (left > 0) {
            int i = ((bytes[evenLen] & 0xff) << 10) | (left == 2 ? ((bytes[len - 1] & 0xff) << 2) : 0);

            dest[destLen - 4] = encodeTable[i >> 12];
            dest[destLen - 3] = encodeTable[(i >>> 6) & 0x3f];

            if (isUrlSafe) {
                // 在URL Safe模式下，=为URL中的关键字符，不需要补充。空余的byte位要去掉。
                int urlSafeLen = destLen - 2;
                if (2 == left) {
                    dest[destLen - 2] = encodeTable[i & 0x3f];
                    urlSafeLen += 1;
                }
                byte[] urlSafeDest = new byte[urlSafeLen];
                System.arraycopy(dest, 0, urlSafeDest, 0, urlSafeLen);
                return urlSafeDest;
            } else {
                dest[destLen - 2] = (left == 2) ? encodeTable[i & 0x3f] : (byte) '=';
                dest[destLen - 1] = '=';
            }
        }
        return dest;
    }
}
