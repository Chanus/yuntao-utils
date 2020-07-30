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
}
