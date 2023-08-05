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

import com.chanus.yuntao.utils.core.CharsetUtils;
import com.chanus.yuntao.utils.core.StringUtils;

import java.nio.charset.Charset;

/**
 * Base32 编码工具类<br>
 * Base32 就是用32（2的5次方）个特定 ASCII 字符来表示256个 ASCII 字符。<br>
 * 所以，5个 ASCII 字符经过 Base32 编码后会变为8个字符（公约数为40），长度增加3/5.不足8n用“=”补足。
 *
 * @author Chanus
 * @date 2020-06-24 14:25:06
 * @since 1.0.0
 */
public class Base32 {
    private static final String BASE32_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";
    private static final int[] BASE32_LOOKUP = {
            // '0', '1', '2', '3', '4', '5', '6', '7'
            0xFF, 0xFF, 0x1A, 0x1B, 0x1C, 0x1D, 0x1E, 0x1F,
            // '8', '9', ':', ';', '<', '=', '>', '?'
            0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
            // '@', 'A', 'B', 'C', 'D', 'E', 'F', 'G'
            0xFF, 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06,
            // 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O'
            0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E,
            // 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W'
            0x0F, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16,
            // 'X', 'Y', 'Z', '[', '\', ']', '^', '_'
            0x17, 0x18, 0x19, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
            // '`', 'a', 'b', 'c', 'd', 'e', 'f', 'g'
            0xFF, 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06,
            // 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o'
            0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E,
            // 'p', 'q', 'r', 's', 't', 'u', 'v', 'w'
            0x0F, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16,
            // 'x', 'y', 'z', '{', '|', '}', '~', 'DEL'
            0x17, 0x18, 0x19, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF
    };

    private Base32() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Base32 编码
     *
     * @param bytes 数据
     * @return 被加密后的字符串
     */
    public static String encode(final byte[] bytes) {
        int i = 0;
        int index = 0;
        int digit;
        int currByte;
        int nextByte;
        StringBuilder base32 = new StringBuilder((bytes.length + 7) * 8 / 5);

        while (i < bytes.length) {
            // unsigned
            currByte = (bytes[i] >= 0) ? bytes[i] : (bytes[i] + 256);

            /* Is the current digit going to span a byte boundary? */
            if (index > 3) {
                if ((i + 1) < bytes.length) {
                    nextByte = (bytes[i + 1] >= 0) ? bytes[i + 1] : (bytes[i + 1] + 256);
                } else {
                    nextByte = 0;
                }

                digit = currByte & (0xFF >> index);
                index = (index + 5) % 8;
                digit <<= index;
                digit |= nextByte >> (8 - index);
                i++;
            } else {
                digit = (currByte >> (8 - (index + 5))) & 0x1F;
                index = (index + 5) % 8;
                if (index == 0) {
                    i++;
                }
            }
            base32.append(BASE32_CHARS.charAt(digit));
        }

        return base32.toString();
    }

    /**
     * Base32 编码
     *
     * @param source 被编码的 Base32 字符串
     * @return 被加密后的字符串
     */
    public static String encode(String source) {
        return encode(source, CharsetUtils.UTF_8);
    }

    /**
     * Base32 编码
     *
     * @param source  被编码的 Base32 字符串
     * @param charset 字符集
     * @return 被加密后的字符串
     */
    public static String encode(String source, String charset) {
        return encode(StringUtils.bytes(source, charset));
    }

    /**
     * Base32 编码
     *
     * @param source  被编码的 Base32 字符串
     * @param charset 字符集
     * @return 被加密后的字符串
     */
    public static String encode(String source, Charset charset) {
        return encode(StringUtils.bytes(source, charset));
    }

    /**
     * Base32 解码
     *
     * @param base32 被编码的 Base32 字符串
     * @return 解码后的数据
     */
    public static byte[] decode2Bytes(final String base32) {
        int i;
        int index;
        int lookup;
        int offset;
        int digit;
        byte[] bytes = new byte[base32.length() * 5 / 8];

        for (i = 0, index = 0, offset = 0; i < base32.length(); i++) {
            lookup = base32.charAt(i) - '0';

            /* Skip chars outside the lookup table */
            if (lookup < 0 || lookup >= BASE32_LOOKUP.length) {
                continue;
            }

            digit = BASE32_LOOKUP[lookup];

            /* If this digit is not in the table, ignore it */
            if (digit == 0xFF) {
                continue;
            }

            if (index <= 3) {
                index = (index + 5) % 8;
                if (index == 0) {
                    bytes[offset] |= digit;
                    offset++;
                    if (offset >= bytes.length) {
                        break;
                    }
                } else {
                    bytes[offset] |= digit << (8 - index);
                }
            } else {
                index = (index + 5) % 8;
                bytes[offset] |= (digit >>> index);
                offset++;

                if (offset >= bytes.length) {
                    break;
                }
                bytes[offset] |= digit << (8 - index);
            }
        }
        return bytes;
    }

    /**
     * Base32 解码
     *
     * @param source 被编码的 Base32 字符串
     * @return 解码后的的字符串
     */
    public static String decode(String source) {
        return decode(source, CharsetUtils.UTF_8);
    }

    /**
     * Base32 解码
     *
     * @param source  被编码的 Base32 字符串
     * @param charset 字符集
     * @return 解码后的的字符串
     */
    public static String decode(String source, String charset) {
        return new String(decode2Bytes(source), StringUtils.isBlank(charset) ? Charset.defaultCharset() : Charset.forName(charset));
    }

    /**
     * Base32 解码
     *
     * @param source  被编码的 Base32 字符串
     * @param charset 字符集
     * @return 解码后的的字符串
     */
    public static String decode(String source, Charset charset) {
        return new String(decode2Bytes(source), charset == null ? Charset.defaultCharset() : charset);
    }
}
