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
package com.chanus.yuntao.utils.core;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;

/**
 * 字符集工具类
 *
 * @author Chanus
 * @since 1.0.0
 */
public class CharsetUtils {
    /**
     * ISO-8859-1
     */
    public static final String ISO_8859_1 = "ISO-8859-1";
    /**
     * UTF-8
     */
    public static final String UTF_8 = "UTF-8";
    /**
     * GBK
     */
    public static final String GBK = "GBK";

    /**
     * ISO-8859-1
     */
    public static final Charset CHARSET_ISO_8859_1 = StandardCharsets.ISO_8859_1;
    /**
     * UTF-8
     */
    public static final Charset CHARSET_UTF_8 = StandardCharsets.UTF_8;
    /**
     * GBK
     */
    public static final Charset CHARSET_GBK;

    static {
        // 避免不支持 GBK 的系统中运行报错
        Charset gbk = null;
        try {
            gbk = Charset.forName(GBK);
        } catch (UnsupportedCharsetException e) {
            //ignore
        }
        CHARSET_GBK = gbk;
    }

    private CharsetUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 解析字符串编码为 Charset 对象
     *
     * @param charsetName 字符集，为空则返回默认字符集
     * @return Charset
     * @throws UnsupportedCharsetException 编码不支持
     */
    public static Charset charset(String charsetName) throws UnsupportedCharsetException {
        return StringUtils.isBlank(charsetName) ? Charset.defaultCharset() : Charset.forName(charsetName);
    }

    /**
     * 解析字符串编码为 Charset 对象，解析失败返回系统默认编码
     *
     * @param charsetName 字符集，为空则返回默认字符集
     * @return Charset
     */
    public static Charset parse(String charsetName) {
        return parse(charsetName, Charset.defaultCharset());
    }

    /**
     * 解析字符串编码为 Charset 对象，解析失败返回默认编码
     *
     * @param charsetName    字符集，为空则返回默认编码
     * @param defaultCharset 默认编码
     * @return Charset
     */
    public static Charset parse(String charsetName, Charset defaultCharset) {
        if (StringUtils.isBlank(charsetName)) {
            return defaultCharset;
        }

        Charset result;
        try {
            result = Charset.forName(charsetName);
        } catch (UnsupportedCharsetException e) {
            result = defaultCharset;
        }

        return result;
    }

    /**
     * 转换字符串的字符集编码
     *
     * @param source      字符串
     * @param srcCharset  源字符集，默认 ISO-8859-1
     * @param destCharset 目标字符集，默认 UTF-8
     * @return 转换后的字符集
     */
    public static String convert(String source, String srcCharset, String destCharset) {
        return convert(source, Charset.forName(srcCharset), Charset.forName(destCharset));
    }

    /**
     * 转换字符串的字符集编码<br>
     * 当以错误的编码读取为字符串时，打印字符串将出现乱码。<br>
     * 此方法用于纠正因读取使用编码错误导致的乱码问题。<br>
     * 例如，在 Servlet 请求中客户端用 GBK 编码了请求参数，我们使用 UTF-8 读取到的是乱码，此时，使用此方法即可还原原编码的内容
     * <pre>
     *     客户端 -》 GBK编码 -》 Servlet容器 -》 UTF-8解码 -》 乱码
     *     乱码 -》 UTF-8编码 -》 GBK解码 -》 正确内容
     * </pre>
     *
     * @param source      字符串
     * @param srcCharset  源字符集，默认 ISO-8859-1
     * @param destCharset 目标字符集，默认 UTF-8
     * @return 转换后的字符集
     */
    public static String convert(String source, Charset srcCharset, Charset destCharset) {
        if (null == srcCharset) {
            srcCharset = StandardCharsets.ISO_8859_1;
        }

        if (null == destCharset) {
            destCharset = StandardCharsets.UTF_8;
        }

        if (StringUtils.isBlank(source) || srcCharset.equals(destCharset)) {
            return source;
        }
        return new String(source.getBytes(srcCharset), destCharset);
    }

    /**
     * 系统默认字符集编码
     *
     * @return 系统字符集编码
     */
    public static String defaultCharsetName() {
        return Charset.defaultCharset().name();
    }

    /**
     * 系统默认字符集编码
     *
     * @return 系统字符集编码
     */
    public static Charset defaultCharset() {
        return Charset.defaultCharset();
    }
}
