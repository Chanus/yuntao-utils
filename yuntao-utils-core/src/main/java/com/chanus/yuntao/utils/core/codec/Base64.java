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
import com.chanus.yuntao.utils.core.StreamUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * Base64 编码工具类<br>
 * Base64 编码是用64（2的6次方）个 ASCII 字符来表示256（2的8次方）个 ASCII 字符。<br>
 * 也就是三位二进制数组经过编码后变为四位的 ASCII 字符显示，长度比原来增加1/3。
 *
 * @author Chanus
 * @date 2020-06-24 15:12:24
 * @since 1.0.0
 */
public class Base64 {
    /**
     * 编码为 Base64，非 URL 安全
     *
     * @param bytes   字节数组
     * @param lineSep 在76个 char 之后是 CRLF 还是 EOF
     * @return 编码后的字节数组
     */
    public static byte[] encode(byte[] bytes, boolean lineSep) {
        return Base64Encoder.encode(bytes, lineSep);
    }

    /**
     * 编码为 Base64，URL 安全
     *
     * @param bytes   字节数组
     * @param lineSep 在76个 char 之后是 CRLF 还是 EOF
     * @return 编码后的字节数组
     */
    public static byte[] encodeUrlSafe(byte[] bytes, boolean lineSep) {
        return Base64Encoder.encodeUrlSafe(bytes, lineSep);
    }

    /**
     * 编码为 Base64，非 URL 安全
     *
     * @param source 字符串
     * @return 编码后的字符串
     */
    public static String encode(CharSequence source) {
        return Base64Encoder.encode(source);
    }

    /**
     * 编码为 Base64，URL 安全
     *
     * @param source 字符串
     * @return 编码后的字符串
     */
    public static String encodeUrlSafe(CharSequence source) {
        return Base64Encoder.encodeUrlSafe(source);
    }

    /**
     * 编码为 Base64，非 URL 安全
     *
     * @param source  字符串
     * @param charset 字符集
     * @return 编码后的字符串
     */
    public static String encode(CharSequence source, String charset) {
        return encode(source, CharsetUtils.charset(charset));
    }

    /**
     * 编码为 Base64，URL 安全
     *
     * @param source  字符串
     * @param charset 字符集
     * @return 编码后的字符串
     */
    public static String encodeUrlSafe(CharSequence source, String charset) {
        return encodeUrlSafe(source, CharsetUtils.charset(charset));
    }

    /**
     * 编码为 Base64，非 URL 安全
     *
     * @param source  字符串
     * @param charset 字符集
     * @return 编码后的字符串
     */
    public static String encode(CharSequence source, Charset charset) {
        return Base64Encoder.encode(source, charset);
    }

    /**
     * 编码为 Base64，URL 安全
     *
     * @param source  字符串
     * @param charset 字符集
     * @return 编码后的字符串
     */
    public static String encodeUrlSafe(CharSequence source, Charset charset) {
        return Base64Encoder.encodeUrlSafe(source, charset);
    }

    /**
     * 编码为 Base64，非 URL 安全
     *
     * @param source 字节数组
     * @return 编码后的字符串
     */
    public static String encode(byte[] source) {
        return Base64Encoder.encode(source);
    }

    /**
     * 编码为 Base64，URL 安全
     *
     * @param source 字节数组
     * @return 编码后的字符串
     */
    public static String encodeUrlSafe(byte[] source) {
        return Base64Encoder.encodeUrlSafe(source);
    }

    /**
     * 编码为 Base64，非 URL 安全
     *
     * @param in 输入流（一般为图片流或者文件流）
     * @return 编码后的字符串
     */
    public static String encode(InputStream in) {
        return Base64Encoder.encode(StreamUtils.read2Byte(in));
    }

    /**
     * 编码为 Base64，URL 安全
     *
     * @param in 输入流（一般为图片流或者文件流）
     * @return 编码后的字符串
     */
    public static String encodeUrlSafe(InputStream in) {
        return Base64Encoder.encodeUrlSafe(StreamUtils.read2Byte(in));
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
        return Base64Encoder.encode(bytes, isMultiLine, isUrlSafe);
    }

    /**
     * Base64 解码
     *
     * @param source Base64 字符串
     * @return 解码后的字符串
     */
    public static String decodeStr(CharSequence source) {
        return Base64Decoder.decodeStr(source);
    }

    /**
     * Base64 解码
     *
     * @param source  Base64 字符串
     * @param charset 字符集
     * @return 解码后的字符串
     */
    public static String decodeStr(CharSequence source, String charset) {
        return decodeStr(source, CharsetUtils.charset(charset));
    }

    /**
     * Base64 解码
     *
     * @param source  Base64 字符串
     * @param charset 字符集
     * @return 解码后的字符串
     */
    public static String decodeStr(CharSequence source, Charset charset) {
        return Base64Decoder.decodeStr(source, charset);
    }

    /**
     * Base64 解码
     *
     * @param base64 Base64 字符串
     * @param out    写出到的流
     */
    public static void decodeToStream(CharSequence base64, OutputStream out) {
        StreamUtils.write(Base64Decoder.decode(base64), out);
    }

    /**
     * Base64 解码
     *
     * @param source Base64 字符串
     * @return 解码后的字节数组
     */
    public static byte[] decode(CharSequence source) {
        return Base64Decoder.decode(source);
    }

    /**
     * Base64 解码
     *
     * @param bytes 字节数组
     * @return 解码后的字节数组
     */
    public static byte[] decode(byte[] bytes) {
        return Base64Decoder.decode(bytes);
    }
}
