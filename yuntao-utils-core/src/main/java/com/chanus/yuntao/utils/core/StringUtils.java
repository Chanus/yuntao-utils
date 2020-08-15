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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 字符串工具类
 *
 * @author Chanus
 * @date 2020-06-20 15:24:34
 * @since 1.0.0
 */
public class StringUtils {
    public static final String SPACE = " ";
    public static final String TAB = "	";
    public static final String DOT = ".";
    public static final String DOUBLE_DOT = "..";
    public static final String SLASH = "/";
    public static final String BACKSLASH = "\\";
    public static final String EMPTY = "";
    public static final String NULL = "null";
    public static final String CR = "\r";
    public static final String LF = "\n";
    public static final String CRLF = "\r\n";
    public static final String UNDERLINE = "_";
    public static final String DASHED = "-";
    public static final String COMMA = ",";
    public static final String DELIM_START = "{";
    public static final String DELIM_END = "}";
    public static final String BRACKET_START = "[";
    public static final String BRACKET_END = "]";
    public static final String COLON = ":";

    public static final String HTML_NBSP = "&nbsp;";
    public static final String HTML_AMP = "&amp;";
    public static final String HTML_QUOTE = "&quot;";
    public static final String HTML_APOS = "&apos;";
    public static final String HTML_LT = "&lt;";
    public static final String HTML_GT = "&gt;";

    public static final String EMPTY_JSON = "{}";

    public static final int INDEX_NOT_FOUND = -1;

    /**
     * 判断字符串是否为空或空白，空白的定义如下：
     * <pre>
     *     1、为null
     *     2、为不可见字符（如空格）
     *     3、""
     * </pre>
     *
     * @param s 字符串
     * @return {@code true} 字符串为空；{@code false} 字符串不为空
     */
    public static boolean isBlank(final String s) {
        return s == null || s.trim().length() == 0;
    }

    /**
     * 判断字符串是否为空或空白，空白的定义如下：
     * <pre>
     *     1、为null
     *     2、为不可见字符（如空格）
     *     3、""
     * </pre>
     *
     * @param s 字符串
     * @return {@code true} 字符串为空；{@code false} 字符串不为空
     */
    public static boolean isBlank(CharSequence s) {
        int length;

        if ((s == null) || ((length = s.length()) == 0)) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            // 只要有一个非空字符即为非空字符串
            if (!CharUtils.isBlank(s.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断字符串是否全为空或空白
     *
     * @param strs 字符串
     * @return {@code true} 字符串全为空或空白；{@code false} 字符串不全为空或空白
     * @since 1.1.0
     */
    public static boolean isAllBlank(String... strs) {
        if (strs == null)
            return true;

        for (String str : strs) {
            if (isNotBlank(str))
                return false;
        }
        return true;
    }

    /**
     * 判断字符串是否全为空或空白
     *
     * @param strs 字符串
     * @return {@code true} 字符串全为空或空白；{@code false} 字符串不全为空或空白
     * @since 1.1.0
     */
    public static boolean isAllBlank(CharSequence... strs) {
        if (strs == null)
            return true;

        for (CharSequence str : strs) {
            if (isNotBlank(str))
                return false;
        }
        return true;
    }

    /**
     * 判断字符串是否为非空或非空白，空白的定义如下：
     * <pre>
     *     1、为null
     *     2、为不可见字符（如空格）
     *     3、""
     * </pre>
     *
     * @param s 字符串
     * @return {@code true} 字符串不为空；{@code false} 字符串为空
     */
    public static boolean isNotBlank(final String s) {
        return !isBlank(s);
    }

    /**
     * 判断字符串是否为非空或非空白，空白的定义如下：
     * <pre>
     *     1、为null
     *     2、为不可见字符（如空格）
     *     3、""
     * </pre>
     *
     * @param s 字符串
     * @return {@code true} 字符串不为空；{@code false} 字符串为空
     */
    public static boolean isNotBlank(CharSequence s) {
        return !isBlank(s);
    }

    /**
     * 判断字符串是否全不为空或空白
     *
     * @param strs 字符串
     * @return {@code true} 字符串全不为空或空白；{@code false} 字符串存在空或空白
     * @since 1.1.0
     */
    public static boolean isAllNotBlank(String... strs) {
        if (strs == null)
            return false;

        for (String str : strs) {
            if (isBlank(str))
                return false;
        }
        return true;
    }

    /**
     * 判断字符串是否全不为空或空白
     *
     * @param strs 字符串
     * @return {@code true} 字符串全不为空或空白；{@code false} 字符串存在空或空白
     * @since 1.1.0
     */
    public static boolean isAllNotBlank(CharSequence... strs) {
        if (strs == null)
            return false;

        for (CharSequence str : strs) {
            if (isBlank(str))
                return false;
        }
        return true;
    }

    /**
     * 字符串是否为空，空的定义如下：
     * <pre>
     *     1、为null
     *     2、为""
     * </pre>
     *
     * @param s 字符串
     * @return {@code true} 字符串为空；{@code false} 字符串不为空
     */
    public static boolean isEmpty(final String s) {
        return s == null || s.length() == 0;
    }

    /**
     * 字符串是否为空，空的定义如下：
     * <pre>
     *     1、为null
     *     2、为""
     * </pre>
     *
     * @param s 字符串
     * @return {@code true} 字符串为空；{@code false} 字符串不为空
     */
    public static boolean isEmpty(CharSequence s) {
        return s == null || s.length() == 0;
    }

    /**
     * 判断字符串是否全为空
     *
     * @param strs 字符串
     * @return {@code true} 字符串全为空；{@code false} 字符串不全为空
     * @since 1.1.0
     */
    public static boolean isAllEmpty(String... strs) {
        if (strs == null)
            return true;

        for (String str : strs) {
            if (isNotEmpty(str))
                return false;
        }
        return true;
    }

    /**
     * 判断字符串是否全为空
     *
     * @param strs 字符串
     * @return {@code true} 字符串全为空；{@code false} 字符串不全为空
     * @since 1.1.0
     */
    public static boolean isAllEmpty(CharSequence... strs) {
        if (strs == null)
            return true;

        for (CharSequence str : strs) {
            if (isNotEmpty(str))
                return false;
        }
        return true;
    }

    /**
     * 字符串是否不为空，空的定义如下：
     * <pre>
     *     1、为null
     *     2、为""
     * </pre>
     *
     * @param s 字符串
     * @return {@code true} 字符串不为空；{@code false} 字符串为空
     */
    public static boolean isNotEmpty(final String s) {
        return !isEmpty(s);
    }

    /**
     * 字符串是否不为空，空的定义如下：
     * <pre>
     *     1、为null
     *     2、为""
     * </pre>
     *
     * @param s 字符串
     * @return {@code true} 字符串不为空；{@code false} 字符串为空
     */
    public static boolean isNotEmpty(CharSequence s) {
        return !isEmpty(s);
    }

    /**
     * 判断字符串是否全不为空
     *
     * @param strs 字符串
     * @return {@code true} 字符串全不为空；{@code false} 字符串存在空
     * @since 1.1.0
     */
    public static boolean isAllNotEmpty(String... strs) {
        if (strs == null)
            return false;

        for (String str : strs) {
            if (isEmpty(str))
                return false;
        }
        return true;
    }

    /**
     * 判断字符串是否全不为空
     *
     * @param strs 字符串
     * @return {@code true} 字符串全不为空；{@code false} 字符串存在空
     * @since 1.1.0
     */
    public static boolean isAllNotEmpty(CharSequence... strs) {
        if (strs == null)
            return false;

        for (CharSequence str : strs) {
            if (isEmpty(str))
                return false;
        }
        return true;
    }

    /**
     * 去除字符串首尾的空格，如果为空白字符串则返回 null
     *
     * @param s 字符串
     * @return 去除首尾空格后的字符串
     */
    public static String trim(final String s) {
        return isBlank(s) ? null : s.trim();
    }

    /**
     * 如果字符串是 null 或空白则返回默认字符串，否则返回本身
     *
     * @param str        字符串
     * @param defaultStr 默认字符串
     * @return 字符串
     * @since 1.1.0
     */
    public static String defaultIfBlank(CharSequence str, String defaultStr) {
        return isBlank(str) ? defaultStr : str.toString();
    }

    /**
     * 如果字符串是 null 或 "" 则返回默认字符串，否则返回本身
     *
     * @param str        字符串
     * @param defaultStr 默认字符串
     * @return 字符串
     * @since 1.1.0
     */
    public static String defaultIfEmpty(CharSequence str, String defaultStr) {
        return isEmpty(str) ? defaultStr : str.toString();
    }

    /**
     * 如果字符串是 null 则返回默认字符串，否则返回本身
     *
     * @param str        字符串
     * @param defaultStr 默认字符串
     * @return 字符串
     * @since 1.1.0
     */
    public static String defaultIfNull(CharSequence str, String defaultStr) {
        return str == null ? defaultStr : str.toString();
    }

    /**
     * 判断字符串是否包含某字符串
     *
     * @param s 源字符串
     * @param t 目标字符串
     * @return {@code true} s 包含字符串 t；{@code false} s 不包含字符串 t
     */
    public static boolean contains(final String s, final String t) {
        return s != null && t != null && s.contains(t);
    }

    /**
     * 安全的比较两个字符串是否相等
     *
     * @param s 字符串1
     * @param t 字符串2
     * @return {@code true} 两个字符串相等；{@code false} 两个字符串不相等
     */
    public static boolean equals(final String s, final String t) {
        return Objects.equals(s, t);
    }

    /**
     * 判断字符串列表中是否包含指定字符串
     *
     * @param s    待比较字符串
     * @param strs 字符串列表
     * @return {@code true} 包含；{@code false} 不包含
     * @since 1.1.0
     */
    public static boolean equalsAny(final String s, final String... strs) {
        if (strs == null)
            return false;

        for (String str : strs) {
            if (Objects.equals(s, str)) return true;
        }
        return false;
    }

    /**
     * 安全的比较两个字符串是否相等，忽略大小写
     *
     * @param s 字符串1
     * @param t 字符串2
     * @return {@code true} 两个字符串相等；{@code false} 两个字符串不相等
     */
    public static boolean equalsIgnoreCase(final String s, final String t) {
        return s == null ? t == null : s.equalsIgnoreCase(t);
    }

    /**
     * 判断字符串列表中是否包含指定字符串，忽略大小写
     *
     * @param s    待比较字符串
     * @param strs 字符串列表
     * @return {@code true} 包含；{@code false} 不包含
     * @since 1.1.0
     */
    public static boolean equalsAnyIgnoreCase(final String s, final String... strs) {
        if (strs == null)
            return false;

        for (String str : strs) {
            if (equalsIgnoreCase(s, str)) return true;
        }
        return false;
    }

    /**
     * 判断字符串是否为纯数字
     *
     * @param s 字符串
     * @return {@code true} 字符串是纯数字；{@code false} 字符串不是纯数字
     */
    public static boolean isNumeric(final String s) {
        return isNotBlank(s) && Pattern.compile("[0-9]+").matcher(s).matches();
    }

    /**
     * 判断字符串是否为数值
     *
     * @param s 字符串
     * @return {@code true} 字符串是数值；{@code false} 字符串不是数值
     */
    public static boolean isNumber(final String s) {
        return isNotBlank(s) && Pattern.compile("^([+|-]?0|([+|-]?0\\.\\d+)|^([+|-]?[1-9]\\d*(\\.\\d+)?))$").matcher(s).matches();
    }

    /**
     * 将布尔型数据转换为字符串
     *
     * @param b 布尔型数据
     * @return b 为 {@code true} 时返回"1"，b 为空或者 {@code false} 时返回"0"
     */
    public static String boolean2String(Boolean b) {
        return (b != null && b) ? "1" : "0";
    }

    /**
     * 压缩字符串
     *
     * @param s 待压缩的字符串内容
     * @return 压缩后的字节数组
     */
    public static byte[] compress(final String s) {
        if (isBlank(s))
            return null;

        ByteArrayOutputStream baos = null;
        GZIPOutputStream gzos = null;
        try {
            baos = new ByteArrayOutputStream();
            gzos = new GZIPOutputStream(baos);
            gzos.write(s.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            IOUtils.closeQuietly(gzos, baos);
        }
        return baos.toByteArray();
    }

    /**
     * 解压流
     *
     * @param b 待解压的内容
     * @return 解压后的字节数组
     */
    public static byte[] decompress(byte[] b) {
        if (b == null || b.length == 0)
            return null;

        ByteArrayInputStream bais = null;
        ByteArrayOutputStream baos = null;
        GZIPInputStream gzis = null;
        try {
            baos = new ByteArrayOutputStream();
            bais = new ByteArrayInputStream(b);
            gzis = new GZIPInputStream(bais);
            byte[] buffer = new byte[1024];
            int i;
            while ((i = gzis.read(buffer, 0, buffer.length)) != -1) {
                baos.write(buffer, 0, i);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            IOUtils.closeQuietly(gzis, bais, baos);
        }
        return baos.toByteArray();
    }

    /**
     * 将源字符串重复生成 {@code num} 次组成新的字符串
     *
     * @param s   源字符串
     * @param num 重复生成次数
     * @return 返回已生成的重复字符串
     */
    public static String repeat(final String s, final int num) {
        if (s == null) return null;
        if (num <= 0) return EMPTY;
        if (num == 1) return s;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++)
            sb.append(s);
        return sb.toString();
    }

    /**
     * 将源字符串重复生成 {@code num} 次，并用分隔符 {@code separator} 连接，组成新的字符串
     *
     * @param s         源字符串
     * @param num       重复生成次数
     * @param separator 分隔符
     * @return 返回已生成的重复字符串
     */
    public static String repeatWithSeparator(String s, int num, String separator) {
        if (s == null) return null;
        if (num <= 0) return EMPTY;
        if (num == 1) return s;

        boolean join = true, isFirst = true;
        if (isEmpty(separator))
            join = false;

        StringBuilder sb = new StringBuilder();
        while (num-- > 0) {
            if (isFirst)
                isFirst = false;
            else if (join)
                sb.append(separator);
            sb.append(s);
        }
        for (int i = 0; i < num; i++)
            sb.append(s);
        return sb.toString();
    }

    /**
     * 将源字符串指定位置的字符替换成指定个数的目标字符串
     *
     * @param source 源字符串
     * @param target 目标字符串
     * @param begin  开始替换的位置
     * @param end    结束替换的位置
     * @param num    目标字符串重复次数
     * @return 返回替换后的字符串
     */
    public static String replace(final String source, final String target, final int begin, final int end, final int num) {
        return source.substring(0, begin) + repeat(target, num) + source.substring(end);
    }

    /**
     * 将源字符串指定位置的字符替换成"*"
     *
     * @param source 源字符串
     * @param begin  开始替换的位置
     * @param end    结束替换的位置
     * @return 返回替换后的字符串
     */
    public static String replace(final String source, final int begin, final int end) {
        return source.substring(0, begin) + repeat("*", end - begin) + source.substring(end);
    }

    /**
     * 将字符串的首字母转为大写
     *
     * @param s 字符串
     * @return 首字母转为大写后的字符串
     */
    public static String capitalize(final String s) {
        return isEmpty(s) ? s : Character.toTitleCase(s.charAt(0)) + s.substring(1);
    }

    /**
     * 将字符串的首字母转为小写
     *
     * @param s 字符串
     * @return 首字母转为小写后的字符串
     */
    public static String uncapitalize(final String s) {
        return isEmpty(s) ? s : Character.toLowerCase(s.charAt(0)) + s.substring(1);
    }

    /**
     * 字符串转 Unicode 码
     *
     * @param s 待转换的字符串
     * @return 转换后的 Unicode 码
     */
    public static String string2Unicode(final String s) {
        StringBuilder unicode = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            unicode.append("\\u").append(Integer.toHexString(s.charAt(i)));
        }

        return unicode.toString();
    }

    /**
     * Unicode 码转字符串
     *
     * @param unicode 待转换的 Unicode 码
     * @return 转换后的字符串
     */
    public static String unicode2String(final String unicode) {
        StringBuilder s = new StringBuilder();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            s.append((char) Integer.parseInt(hex[i], 16));
        }

        return s.toString();
    }

    /**
     * 获取字符串 UTF-8 格式的字节码
     *
     * @param str 字符串
     * @return 编码后的字节码
     */
    public static byte[] utf8Bytes(CharSequence str) {
        return bytes(str, CharsetUtils.UTF_8);
    }

    /**
     * 获取字符串系统默认编码格式的字节码
     *
     * @param str 字符串
     * @return 编码后的字节码
     */
    public static byte[] bytes(CharSequence str) {
        return bytes(str, Charset.defaultCharset());
    }

    /**
     * 获取字符串指定编码格式的字节码
     *
     * @param str     字符串
     * @param charset 字符集，如果为空，则使用系统默认编码格式
     * @return 编码后的字节码
     */
    public static byte[] bytes(CharSequence str, String charset) {
        return bytes(str, isBlank(charset) ? Charset.defaultCharset() : Charset.forName(charset));
    }

    /**
     * 获取字符串指定编码格式的字节码
     *
     * @param str     字符串
     * @param charset 字符集，如果为空，则使用系统默认编码格式
     * @return 编码后的字节码
     */
    public static byte[] bytes(CharSequence str, Charset charset) {
        if (str == null)
            return null;

        return charset == null ? str.toString().getBytes() : str.toString().getBytes(charset);
    }

    /**
     * 将 byte 数组转为 UTF-8 格式的字符串
     *
     * @param bytes byte 数组
     * @return 字符串
     * @since 1.1.0
     */
    public static String toUtf8String(byte[] bytes) {
        return toString(bytes, StandardCharsets.UTF_8);
    }

    /**
     * 将 byte 数组转为字符串
     *
     * @param bytes   byte 数组
     * @param charset 字符集，如果为空，则使用平台默认字符集
     * @return 字符串
     * @since 1.1.0
     */
    public static String toString(byte[] bytes, String charset) {
        return toString(bytes, isBlank(charset) ? Charset.defaultCharset() : Charset.forName(charset));
    }

    /**
     * 将 byte 数组转为字符串
     *
     * @param data    byte 数组
     * @param charset 字符集，如果为空，则使用平台默认字符集
     * @return 字符串
     * @since 1.1.0
     */
    public static String toString(byte[] data, Charset charset) {
        if (data == null)
            return null;

        return charset == null ? new String(data) : new String(data, charset);
    }

    /**
     * 获取字符串的长度，如果为 null 返回0
     *
     * @param str 字符串
     * @return 字符串的长度
     * @since 1.1.0
     */
    public static int length(CharSequence str) {
        return str == null ? 0 : str.length();
    }

    /**
     * 连接多个字符串
     *
     * @param isNullToEmpty 是否将 null 转为 ""
     * @param strs          字符串数组
     * @return 连接后的字符串
     * @since 1.1.0
     */
    public static String concat(boolean isNullToEmpty, CharSequence... strs) {
        final StringBuilder sb = new StringBuilder();
        for (CharSequence str : strs) {
            sb.append(isNullToEmpty ? defaultIfNull(str, EMPTY) : str);
        }
        return sb.toString();
    }

    /**
     * 字符串去除指定前缀
     *
     * @param s      字符串
     * @param prefix 前缀
     * @return 去除指定前缀后的字符串，若前缀不是 {@code prefix}，返回原字符串
     * @since 1.1.0
     */
    public static String removePrefix(String s, String prefix) {
        if (isEmpty(s) || isEmpty(prefix))
            return s;

        if (s.startsWith(prefix)) {
            return s.substring(prefix.length());
        }
        return s;
    }

    /**
     * 字符串去除指定前缀，忽略大小写
     *
     * @param s      字符串
     * @param prefix 前缀
     * @return 去除指定前缀后的字符串，若前缀不是 {@code prefix}，返回原字符串
     * @since 1.1.0
     */
    public static String removePrefixIgnoreCase(String s, String prefix) {
        if (isEmpty(s) || isEmpty(prefix))
            return s;

        if (s.toLowerCase().startsWith(prefix.toLowerCase())) {
            return s.substring(prefix.length());
        }
        return s;
    }

    /**
     * 字符串去除指定后缀
     *
     * @param s      字符串
     * @param suffix 后缀
     * @return 去除指定后缀后的字符串，若后缀不是 {@code suffix}，返回原字符串
     * @since 1.1.0
     */
    public static String removeSuffix(String s, String suffix) {
        if (isEmpty(s) || isEmpty(suffix))
            return s;

        if (s.endsWith(suffix)) {
            return s.substring(0, s.length() - suffix.length());
        }
        return s;
    }

    /**
     * 字符串去除指定后缀，忽略大小写
     *
     * @param s      字符串
     * @param suffix 后缀
     * @return 去除指定后缀后的字符串，若后缀不是 {@code suffix}，返回原字符串
     * @since 1.1.0
     */
    public static String removeSuffixIgnoreCase(String s, String suffix) {
        if (isEmpty(s) || isEmpty(suffix))
            return s;

        if (s.toLowerCase().endsWith(suffix.toLowerCase())) {
            return s.substring(0, s.length() - suffix.length());
        }
        return s;
    }

    /**
     * 将驼峰式命名的字符串转换为下划线方式
     *
     * @param s 驼峰式命名的字符串
     * @return 下划线连接方式命名的字符串
     * @since 1.1.0
     */
    public static String toUnderlineCase(String s) {
        if (s == null)
            return null;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            final char c = s.charAt(i);
            final Character preChar = i > 0 ? s.charAt(i - 1) : null;
            if (Character.isUpperCase(c)) {
                if (preChar != null && preChar != CharUtils.UNDERLINE)
                    sb.append(UNDERLINE);

                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 将下划线方式命名的字符串转换为驼峰式命名的字符串
     *
     * @param s 下划线方式命名的字符串
     * @return 驼峰式命名的字符串
     * @since 1.1.0
     */
    public static String toCamelCase(String s) {
        if (s == null)
            return null;

        if (s.contains(UNDERLINE)) {
            final StringBuilder sb = new StringBuilder(s.length());
            boolean upperCase = false;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);

                if (c == CharUtils.UNDERLINE) {
                    upperCase = true;
                } else if (upperCase) {
                    sb.append(Character.toUpperCase(c));
                    upperCase = false;
                } else {
                    sb.append(Character.toLowerCase(c));
                }
            }
            return sb.toString();
        } else {
            return s;
        }
    }

    /**
     * 在字符串前面将字符串填充到指定长度，如果字符串超过指定长度则返回原字符串
     *
     * @param s         被填充的字符串
     * @param filledStr 填充的字符
     * @param minLength 填充长度
     * @return 填充后的字符串
     * @since 1.1.0
     */
    public static String fillBefore(String s, String filledStr, int minLength) {
        return fill(s, filledStr, minLength, true);
    }

    /**
     * 在字符串后面将字符串填充到指定长度，如果字符串超过指定长度则返回原字符串
     *
     * @param s         被填充的字符串
     * @param filledStr 填充的字符
     * @param minLength 填充长度
     * @return 填充后的字符串
     * @since 1.1.0
     */
    public static String fillAfter(String s, String filledStr, int minLength) {
        return fill(s, filledStr, minLength, false);
    }

    /**
     * 将字符串填充到指定长度，如果字符串超过指定长度则返回原字符串
     *
     * @param s         被填充的字符串
     * @param filledStr 填充的字符
     * @param minLength 填充长度
     * @param isPre     是否填充在前
     * @return 填充后的字符串
     * @since 1.1.0
     */
    public static String fill(String s, String filledStr, int minLength, boolean isPre) {
        final int length = s.length();
        if (length > minLength)
            return s;

        String filled = repeat(filledStr, minLength - length);
        return isPre ? filled.concat(s) : s.concat(filled);
    }

    /**
     * 反转字符串
     *
     * @param s 被反转的字符串
     * @return 反转后的字符串
     * @since 1.1.0
     */
    public static String reverse(String s) {
        if (s == null)
            return null;

        char[] chars = s.toCharArray();
        int length = chars.length;
        char[] chars1 = new char[length];
        for (int i = length - 1; i >= 0; i--) {
            chars1[length - 1 - i] = chars[i];
        }

        return new String(chars1);
    }

    /**
     * 指定范围内查找字符串
     *
     * @param str        字符串
     * @param searchStr  需要查找的字符串
     * @param fromIndex  起始位置
     * @param ignoreCase 是否忽略大小写
     * @return 需要查找的字符串的位置，未找到返回 -1
     * @since 1.2.2
     */
    public static int indexOf(final CharSequence str, CharSequence searchStr, int fromIndex, boolean ignoreCase) {
        if (str == null || searchStr == null)
            return INDEX_NOT_FOUND;

        if (fromIndex < 0)
            fromIndex = 0;

        final int endLimit = str.length() - searchStr.length() + 1;
        if (fromIndex > endLimit)
            return INDEX_NOT_FOUND;

        if (searchStr.length() == 0)
            return fromIndex;

        if (!ignoreCase) {
            return str.toString().indexOf(searchStr.toString(), fromIndex);
        }

        for (int i = fromIndex; i < endLimit; i++) {
            if (str.toString().regionMatches(true, i, searchStr.toString(), 0, searchStr.length())) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 查找字符串，不忽略大小写
     *
     * @param str       字符串
     * @param searchStr 需要查找的字符串
     * @return 需要查找的字符串的位置，未找到返回 -1
     * @since 1.2.2
     */
    public static int indexOf(final CharSequence str, CharSequence searchStr) {
        return indexOf(str, searchStr, 0, false);
    }

    /**
     * 查找字符串，忽略大小写
     *
     * @param str       字符串
     * @param searchStr 需要查找的字符串
     * @return 需要查找的字符串的位置，未找到返回 -1
     * @since 1.2.2
     */
    public static int indexOfIgnoreCase(final CharSequence str, CharSequence searchStr) {
        return indexOf(str, searchStr, 0, true);
    }

    /**
     * 截取字符串<br>
     * 截取规则：<br>
     * <pre>
     *     1. beginIndex < 0，beginIndex = s.length() + beginIndex，即 beginIndex 从后向前开始，
     *     若 s.length() + beginIndex < 0，则 beginIndex 默认为 0
     *     2. beginIndex > s.length()，则 beginIndex 默认为 s.length()
     *     3. endIndex > s.length()，则 endIndex 默认为 s.length()
     *     4. endIndex < 0，则 endIndex = s.length() + endIndex，即 endIndex 从后向前开始，
     *     若 s.length() + endIndex <= 0，则返回 ""
     *     5. endIndex < beginIndex，则交换 beginIndex 和 endIndex
     * </pre>
     *
     * @param s          被截取的字符串
     * @param beginIndex 开始位置
     * @param endIndex   结束位置
     * @return 截取后的字符串
     * @since 1.2.2
     */
    public static String substring(String s, int beginIndex, int endIndex) {
        if (s == null)
            return null;

        int len = s.length();

        if (beginIndex < 0) {
            beginIndex = len + beginIndex;
            if (beginIndex < 0)
                beginIndex = 0;
        } else if (beginIndex > len) {
            beginIndex = len;
        }

        if (endIndex < 0) {
            endIndex = len + endIndex;
            if (endIndex < 0)
                return EMPTY;
        } else if (endIndex > len) {
            endIndex = len;
        }

        if (endIndex < beginIndex) {
            int tmp = beginIndex;
            beginIndex = endIndex;
            endIndex = tmp;
        }

        if (beginIndex == endIndex)
            return EMPTY;

        return s.substring(beginIndex, endIndex);
    }

    /**
     * 截取字符串左侧部分<br>
     * 截取规则：<br>
     * <pre>
     *     1. endIndex > s.length()，则 endIndex 默认为 s.length()
     *     2. endIndex < 0，则 endIndex = s.length() + endIndex，即 endIndex 从后向前开始，
     *     若 s.length() + endIndex <= 0，则返回 ""
     * </pre>
     *
     * @param s        被截取的字符串
     * @param endIndex 结束位置
     * @return 截取后的字符串
     * @since 1.2.2
     */
    public static String left(String s, int endIndex) {
        return substring(s, 0, endIndex);
    }

    /**
     * 截取字符串右侧部分<br>
     * 截取规则：<br>
     * <pre>
     *     1. beginIndex < 0，beginIndex = s.length() + beginIndex，即 beginIndex 从后向前开始，
     *     若 s.length() + beginIndex < 0，则 beginIndex 默认为 0
     *     2. beginIndex > s.length()，则返回 ""
     * </pre>
     *
     * @param s          被截取的字符串
     * @param beginIndex 开始位置
     * @return 截取后的字符串
     * @since 1.2.2
     */
    public static String right(String s, int beginIndex) {
        if (s == null)
            return null;
        return substring(s, beginIndex, s.length());
    }

    /**
     * 切分字符串
     *
     * @param s           被切分的字符串
     * @param separator   分隔符字符
     * @param limit       限制分片数，-1不限制
     * @param isTrim      是否去除切分字符串后每个元素两边的空格
     * @param ignoreEmpty 是否忽略空串
     * @param ignoreCase  是否忽略大小写
     * @return 切分后的集合
     * @since 1.2.2
     */
    public static List<String> split(String s, char separator, int limit, boolean isTrim, boolean ignoreEmpty, boolean ignoreCase) {
        if (isEmpty(s))
            return new ArrayList<>(0);

        if (limit == 1)
            return addToList(new ArrayList<>(1), s, isTrim, ignoreEmpty);

        final ArrayList<String> list = new ArrayList<>(limit > 0 ? limit : 16);
        int len = s.length();
        int start = 0;// 切分后每个部分的起始
        for (int i = 0; i < len; i++) {
            if (CharUtils.equals(separator, s.charAt(i), ignoreCase)) {
                addToList(list, s.substring(start, i), isTrim, ignoreEmpty);
                start = i + 1;// i+1同时将 start 与 i 保持一致

                // 检查是否超出范围（最大允许 limit-1 个，剩下一个留给末尾字符串）
                if (limit > 0 && list.size() > limit - 2) {
                    break;
                }
            }
        }
        return addToList(list, s.substring(start, len), isTrim, ignoreEmpty);
    }

    /**
     * 切分字符串，去除切分字符串后每个元素两边的空格，忽略空串
     *
     * @param s         被切分的字符串
     * @param separator 分隔符字符
     * @param limit     限制分片数，-1不限制
     * @return 切分后的集合
     * @since 1.2.2
     */
    public static List<String> split(String s, char separator, int limit) {
        return split(s, separator, limit, true, true, false);
    }

    /**
     * 切分字符串，去除切分字符串后每个元素两边的空格，忽略空串
     *
     * @param s         被切分的字符串
     * @param separator 分隔符字符
     * @return 切分后的集合
     * @since 1.2.2
     */
    public static List<String> split(String s, char separator) {
        return split(s, separator, -1, true, true, false);
    }

    /**
     * 使用空白符切分字符串<br>
     * 切分后的字符串两边不包含空白符，空串或空白符串并不做为元素之一
     *
     * @param s     被切分的字符串
     * @param limit 限制分片数，-1不限制
     * @return 切分后的集合
     * @since 1.2.2
     */
    public static List<String> split(String s, int limit) {
        if (isEmpty(s))
            return new ArrayList<>(0);

        if (limit == 1)
            return addToList(new ArrayList<>(1), s, true, true);

        final ArrayList<String> list = new ArrayList<>();
        int len = s.length();
        int start = 0;// 切分后每个部分的起始
        for (int i = 0; i < len; i++) {
            if (CharUtils.isBlank(s.charAt(i))) {
                addToList(list, s.substring(start, i), true, true);
                start = i + 1;// i+1 同时将 start 与 i 保持一致

                // 检查是否超出范围（最大允许 limit-1 个，剩下一个留给末尾字符串）
                if (limit > 0 && list.size() > limit - 2)
                    break;
            }
        }
        return addToList(list, s.substring(start, len), true, true);
    }

    /**
     * 使用空白符切分字符串<br>
     * 切分后的字符串两边不包含空白符，空串或空白符串并不做为元素之一
     *
     * @param s 被切分的字符串
     * @return 切分后的集合
     * @since 1.2.2
     */
    public static List<String> split(String s) {
        return split(s, -1);
    }

    /**
     * 切分字符串
     *
     * @param s           被切分的字符串
     * @param separator   分隔符字符串
     * @param limit       限制分片数，-1不限制
     * @param isTrim      是否去除切分字符串后每个元素两边的空格
     * @param ignoreEmpty 是否忽略空串
     * @param ignoreCase  是否忽略大小写
     * @return 切分后的集合
     * @since 1.2.2
     */
    public static List<String> split(String s, String separator, int limit, boolean isTrim, boolean ignoreEmpty, boolean ignoreCase) {
        if (isEmpty(s))
            return new ArrayList<>(0);

        if (limit == 1)
            return addToList(new ArrayList<>(1), s, isTrim, ignoreEmpty);

        if (isEmpty(separator)) {// 分隔符为空时按照空白符切分
            return split(s, limit);
        } else if (separator.length() == 1) {// 分隔符只有一个字符长度时按照单分隔符切分
            return split(s, separator.charAt(0), limit, isTrim, ignoreEmpty, ignoreCase);
        }

        final ArrayList<String> list = new ArrayList<>();
        int len = s.length();
        int separatorLen = separator.length();
        int start = 0;
        int i = 0;
        while (i < len) {
            i = indexOf(s, separator, start, ignoreCase);
            if (i > -1) {
                addToList(list, s.substring(start, i), isTrim, ignoreEmpty);
                start = i + separatorLen;

                // 检查是否超出范围（最大允许 limit-1 个，剩下一个留给末尾字符串）
                if (limit > 0 && list.size() > limit - 2) {
                    break;
                }
            } else {
                break;
            }
        }
        return addToList(list, s.substring(start, len), isTrim, ignoreEmpty);
    }

    /**
     * 切分字符串，去除切分字符串后每个元素两边的空格，忽略空串
     *
     * @param s         被切分的字符串
     * @param separator 分隔符字符串
     * @param limit     限制分片数，-1不限制
     * @return 切分后的集合
     * @since 1.2.2
     */
    public static List<String> split(String s, String separator, int limit) {
        return split(s, separator, limit, true, true, false);
    }

    /**
     * 切分字符串，去除切分字符串后每个元素两边的空格，忽略空串
     *
     * @param s         被切分的字符串
     * @param separator 分隔符字符串
     * @return 切分后的集合
     * @since 1.2.2
     */
    public static List<String> split(String s, String separator) {
        return split(s, separator, -1, true, true, false);
    }

    /**
     * 切分字符串为字符串数组
     *
     * @param s           被切分的字符串
     * @param separator   分隔符字符
     * @param limit       限制分片数，-1不限制
     * @param isTrim      是否去除切分字符串后每个元素两边的空格
     * @param ignoreEmpty 是否忽略空串
     * @param ignoreCase  是否忽略大小写
     * @return 切分后的字符串数组
     * @since 1.2.2
     */
    public static String[] splitToArray(String s, char separator, int limit, boolean isTrim, boolean ignoreEmpty, boolean ignoreCase) {
        return split(s, separator, limit, isTrim, ignoreEmpty, ignoreCase).toArray(new String[0]);
    }

    /**
     * 切分字符串为字符串数组，去除切分字符串后每个元素两边的空格，忽略空串
     *
     * @param s         被切分的字符串
     * @param separator 分隔符字符
     * @param limit     限制分片数，-1不限制
     * @return 切分后的字符串数组
     * @since 1.2.2
     */
    public static String[] splitToArray(String s, char separator, int limit) {
        return splitToArray(s, separator, limit, true, true, false);
    }

    /**
     * 切分字符串为字符串数组，去除切分字符串后每个元素两边的空格，忽略空串
     *
     * @param s         被切分的字符串
     * @param separator 分隔符字符
     * @return 切分后的字符串数组
     * @since 1.2.2
     */
    public static String[] splitToArray(String s, char separator) {
        return splitToArray(s, separator, -1, true, true, false);
    }

    /**
     * 使用空白符切分字符串为字符串数组<br>
     * 切分后的字符串两边不包含空白符，空串或空白符串并不做为元素之一
     *
     * @param s     被切分的字符串
     * @param limit 限制分片数，-1不限制
     * @return 切分后的字符串数组
     * @since 1.2.2
     */
    public static String[] splitToArray(String s, int limit) {
        return split(s, limit).toArray(new String[0]);
    }

    /**
     * 使用空白符切分字符串为字符串数组<br>
     * 切分后的字符串两边不包含空白符，空串或空白符串并不做为元素之一
     *
     * @param s 被切分的字符串
     * @return 切分后的字符串数组
     * @since 1.2.2
     */
    public static String[] splitToArray(String s) {
        return splitToArray(s, -1);
    }

    /**
     * 切分字符串为字符串数组
     *
     * @param s           被切分的字符串
     * @param separator   分隔符字符串
     * @param limit       限制分片数，-1不限制
     * @param isTrim      是否去除切分字符串后每个元素两边的空格
     * @param ignoreEmpty 是否忽略空串
     * @param ignoreCase  是否忽略大小写
     * @return 切分后的字符串数组
     * @since 1.2.2
     */
    public static String[] splitToArray(String s, String separator, int limit, boolean isTrim, boolean ignoreEmpty, boolean ignoreCase) {
        return split(s, separator, limit, isTrim, ignoreEmpty, ignoreCase).toArray(new String[0]);
    }

    /**
     * 切分字符串为字符串数组，去除切分字符串后每个元素两边的空格，忽略空串
     *
     * @param s         被切分的字符串
     * @param separator 分隔符字符串
     * @param limit     限制分片数，-1不限制
     * @return 切分后的字符串数组
     * @since 1.2.2
     */
    public static String[] splitToArray(String s, String separator, int limit) {
        return splitToArray(s, separator, limit, true, true, false);
    }

    /**
     * 切分字符串为字符串数组，去除切分字符串后每个元素两边的空格，忽略空串
     *
     * @param s         被切分的字符串
     * @param separator 分隔符字符串
     * @return 切分后的字符串数组
     * @since 1.2.2
     */
    public static String[] splitToArray(String s, String separator) {
        return splitToArray(s, separator, -1, true, true, false);
    }

    /**
     * 将字符串加入 List 列表中
     *
     * @param list        列表
     * @param part        被加入的部分
     * @param isTrim      是否去除两端空白符
     * @param ignoreEmpty 是否略过空字符串（空字符串不做为一个元素）
     * @return 列表
     * @since 1.2.2
     */
    private static List<String> addToList(List<String> list, String part, boolean isTrim, boolean ignoreEmpty) {
        if (isTrim) {
            part = trim(part);
        }
        if (!ignoreEmpty || isNotEmpty(part)) {
            list.add(part);
        }
        return list;
    }
}
