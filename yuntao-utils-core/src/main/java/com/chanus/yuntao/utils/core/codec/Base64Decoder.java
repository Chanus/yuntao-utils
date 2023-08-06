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
 * Base64 解码实现
 *
 * @author Chanus
 * @since 1.0.0
 */
public class Base64Decoder {

    private static final byte PADDING = -2;

    /**
     * Base64 解码表，共128位，-1表示非 Base64 字符，-2表示 padding
     */
    private static final byte[] DECODE_TABLE = {
            // 0 1 2 3 4 5 6 7 8 9 A B C D E F
            // 00-0f
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            // 10-1f
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            // 20-2f + - /
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 62, -1, 63,
            // 30-3f 0-9
            52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1,
            // 40-4f A-O
            -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
            // 50-5f P-Z _
            15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63,
            // 60-6f a-o
            -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
            // 70-7a p-z
            41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51
    };

    private Base64Decoder() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Base64 解码
     *
     * @param source Base64 字符串
     * @return 解码后的字符串
     */
    public static String decodeStr(CharSequence source) {
        return decodeStr(source, StandardCharsets.UTF_8);
    }

    /**
     * Base64 解码
     *
     * @param source  Base64 字符串
     * @param charset 字符集
     * @return 解码后的字符串
     */
    public static String decodeStr(CharSequence source, Charset charset) {
        return new String(decode(source), charset);
    }

    /**
     * Base64 解码
     *
     * @param source Base64 字符串
     * @return 解码后的字节数组
     */
    public static byte[] decode(CharSequence source) {
        return decode(StringUtils.bytes(source, StandardCharsets.UTF_8));
    }

    /**
     * Base64 解码
     *
     * @param bytes 字节数组
     * @return 解码后的字节数组
     */
    public static byte[] decode(byte[] bytes) {
        return bytes == null ? null : decode(bytes, 0, bytes.length);
    }

    /**
     * Base64 解码
     *
     * @param bytes  字节数组
     * @param pos    开始位置
     * @param length 长度
     * @return 解码后的字节数组
     */
    public static byte[] decode(byte[] bytes, int pos, int length) {
        if (bytes == null) {
            return null;
        }

        final IntWrapper offset = new IntWrapper(pos);

        byte sestet0;
        byte sestet1;
        byte sestet2;
        byte sestet3;
        int maxPos = pos + length - 1;
        int octetId = 0;
        // over-estimated if non-base64 characters present
        byte[] octet = new byte[length * 3 / 4];
        while (offset.value <= maxPos) {
            sestet0 = getNextValidDecodeByte(bytes, offset, maxPos);
            sestet1 = getNextValidDecodeByte(bytes, offset, maxPos);
            sestet2 = getNextValidDecodeByte(bytes, offset, maxPos);
            sestet3 = getNextValidDecodeByte(bytes, offset, maxPos);

            if (PADDING != sestet1) {
                octet[octetId++] = (byte) ((sestet0 << 2) | (sestet1 >>> 4));
            }
            if (PADDING != sestet2) {
                octet[octetId++] = (byte) (((sestet1 & 0xf) << 4) | (sestet2 >>> 2));
            }
            if (PADDING != sestet3) {
                octet[octetId++] = (byte) (((sestet2 & 3) << 6) | sestet3);
            }
        }

        if (octetId == octet.length) {
            return octet;
        } else {
            // 如果有非Base64字符混入，则实际结果比解析的要短，截取之
            byte[] octetBytes = new byte[octetId];
            System.arraycopy(octet, 0, octetBytes, 0, octetId);
            return octetBytes;
        }
    }

    /**
     * 获取下一个有效的 byte 字符
     *
     * @param in     输入
     * @param pos    当前位置，调用此方法后此位置保持在有效字符的下一个位置
     * @param maxPos 最大位置
     * @return 有效字符，如果达到末尾返回
     */
    private static byte getNextValidDecodeByte(byte[] in, IntWrapper pos, int maxPos) {
        byte base64Byte;
        byte decodeByte;
        while (pos.value <= maxPos) {
            base64Byte = in[pos.value++];
            if (base64Byte > -1) {
                decodeByte = DECODE_TABLE[base64Byte];
                if (decodeByte > -1) {
                    return decodeByte;
                }
            }
        }
        // padding if reached max position
        return PADDING;
    }

    /**
     * int 包装，使之可变
     */
    private static class IntWrapper {
        int value;

        IntWrapper(int value) {
            this.value = value;
        }
    }
}
