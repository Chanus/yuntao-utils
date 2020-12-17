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

/**
 * 字符工具类
 *
 * @author Chanus
 * @date 2020-06-20 09:29:15
 * @since 1.0.0
 */
public class CharUtils {
    /**
     * 空格
     */
    public static final char SPACE = ' ';
    /**
     * 感叹号
     */
    public static final char EXCLAMATION_MARK = '!';
    /**
     * 引号 (双引号)
     */
    public static final char DOUBLE_QUOTE = '"';
    /**
     * 数字符号
     */
    public static final char HASH = '#';
    /**
     * 美元符
     */
    public static final char DOLLAR = '$';
    /**
     * 百分号
     */
    public static final char PERCENT = '%';
    /**
     * 和号
     */
    public static final char AMPERSAND = '&';
    /**
     * 省略号 (单引号)
     */
    public static final char APOSTROPHE = '\'';
    /**
     * 左圆括号
     */
    public static final char OPEN_PARENTHESIS = '(';
    /**
     * 右圆括号
     */
    public static final char CLOSE_PARENTHESIS = ')';
    /**
     * 星号
     */
    public static final char ASTERISK = '*';
    /**
     * 加号
     */
    public static final char PLUS = '+';
    /**
     * 逗号
     */
    public static final char COMMA = ',';
    /**
     * 连字号或减号
     */
    public static final char HYPHEN = '-';
    /**
     * 句点或小数点
     */
    public static final char DOT = '.';
    /**
     * 斜杠
     */
    public static final char SLASH = '/';
    /**
     * 冒号
     */
    public static final char COLON = ':';
    /**
     * 分号
     */
    public static final char SEMICOLON = ';';
    /**
     * 小于
     */
    public static final char LESS_THAN = '<';
    /**
     * 等于
     */
    public static final char EQUAL = '=';
    /**
     * 大于
     */
    public static final char GREATER_THAN = '>';
    /**
     * 问号
     */
    public static final char QUESTION_MARK = '?';
    /**
     * 商业 at 符号
     */
    public static final char AT = '@';
    /**
     * 左中括号
     */
    public static final char OPEN_SQUARE_BRACKET = '[';
    /**
     * 右中括号
     */
    public static final char CLOSE_SQUARE_BRACKET = ']';
    /**
     * 反斜杠
     */
    public static final char BACKSLASH = '\\';
    /**
     * 音调符号
     */
    public static final char CARET = '^';
    /**
     * 下划线
     */
    public static final char UNDERSCORE = '_';
    /**
     * 重音符
     */
    public static final char BACK_QUOTE = '`';
    /**
     * 左大括号
     */
    public static final char OPEN_BRACE = '{';
    /**
     * 右大括号
     */
    public static final char CLOSE_BRACE = '}';
    /**
     * 垂直线
     */
    public static final char VERTICAL_BAR = '|';
    /**
     * 代字号
     */
    public static final char TILDE = '~';
    /**
     * 制表符
     */
    public static final char TAB = '	';
    /**
     * 回车
     */
    public static final char CR = '\r';
    /**
     * 换行
     */
    public static final char LF = '\n';

    /**
     * 给定对象对应的类是否为字符类，字符类包括：
     *
     * <pre>
     *     Character.class
     *     char.class
     * </pre>
     *
     * @param object 被检查的对象
     * @return @code true} 表示为字符类；{@code false} 表示非字符类
     */
    public static boolean isChar(Object object) {
        return object instanceof Character;
    }

    /**
     * 是否为 ASCII 字符，ASCII 字符位于 0~127 之间
     *
     * <pre>
     *     CharUtil.isAscii('a')  = true
     *     CharUtil.isAscii('A')  = true
     *     CharUtil.isAscii('1')  = true
     *     CharUtil.isAscii('-')  = true
     *     CharUtil.isAscii('\n') = true
     *     CharUtil.isAscii('©') = false
     * </pre>
     *
     * @param c 被检查的字符
     * @return {@code true} 表示为 ASCII 字符，ASCII 字符位于 0~127 之间；{@code false} 表示非 ASCII 字符
     */
    public static boolean isAscii(final char c) {
        return c < 128;
    }

    /**
     * 是否为可见 ASCII 字符，可见字符位于 32~126 之间
     *
     * <pre>
     *     CharUtil.isAsciiPrintable('a')  = true
     *     CharUtil.isAsciiPrintable('A')  = true
     *     CharUtil.isAsciiPrintable('1')  = true
     *     CharUtil.isAsciiPrintable('-')  = true
     *     CharUtil.isAsciiPrintable('\n') = false
     *     CharUtil.isAsciiPrintable('©') = false
     * </pre>
     *
     * @param c 被检查的字符
     * @return {@code true} 表示为 ASCII 可见字符，可见字符位于 32~126 之间；{@code false} 表示非 ASCII 可见字符
     */
    public static boolean isAsciiPrintable(final char c) {
        return c >= 32 && c < 127;
    }

    /**
     * 是否为 ASCII 控制符（不可见字符），控制符位于 0~31 和 127
     *
     * <pre>
     *     CharUtil.isAsciiControl('a')  = false
     *     CharUtil.isAsciiControl('A')  = false
     *     CharUtil.isAsciiControl('1')  = false
     *     CharUtil.isAsciiControl('-')  = false
     *     CharUtil.isAsciiControl('\n') = true
     *     CharUtil.isAsciiControl('©') = false
     * </pre>
     *
     * @param c 被检查的字符
     * @return {@code true} 表示为 ASCII 控制符，控制符位于 0~31 和 127；{@code false} 表示非 ASCII 控制符
     */
    public static boolean isAsciiControl(final char c) {
        return c < 32 || c == 127;
    }

    /**
     * 判断是否为字母（包括大写字母和小写字母），字母包括 A~Z 和 a~z
     *
     * <pre>
     *     CharUtil.isLetter('a')  = true
     *     CharUtil.isLetter('A')  = true
     *     CharUtil.isLetter('1')  = false
     *     CharUtil.isLetter('-')  = false
     *     CharUtil.isLetter('\n') = false
     *     CharUtil.isLetter('©') = false
     * </pre>
     *
     * @param c 被检查的字符
     * @return {@code true} 表示为字母（包括大写字母和小写字母），字母包括 A~Z 和 a~z；{@code false} 表示非字母
     */
    public static boolean isLetter(final char c) {
        return isLetterUpper(c) || isLetterLower(c);
    }

    /**
     * 判断是否为大写字母，大写字母包括 A~Z
     *
     * <pre>
     *     CharUtil.isLetterUpper('a')  = false
     *     CharUtil.isLetterUpper('A')  = true
     *     CharUtil.isLetterUpper('1')  = false
     *     CharUtil.isLetterUpper('-')  = false
     *     CharUtil.isLetterUpper('\n') = false
     *     CharUtil.isLetterUpper('©') = false
     * </pre>
     *
     * @param c 被检查的字符
     * @return {@code true} 表示为大写字母，大写字母包括 A~Z；{@code false} 表示非大写字母
     */
    public static boolean isLetterUpper(final char c) {
        return c >= 'A' && c <= 'Z';
    }

    /**
     * 检查字符是否为小写字母，小写字母指 a~z
     *
     * <pre>
     *     CharUtil.isLetterLower('a')  = true
     *     CharUtil.isLetterLower('A')  = false
     *     CharUtil.isLetterLower('1')  = false
     *     CharUtil.isLetterLower('-')  = false
     *     CharUtil.isLetterLower('\n') = false
     *     CharUtil.isLetterLower('©') = false
     * </pre>
     *
     * @param c 被检查的字符
     * @return {@code true} 表示为小写字母，小写字母指 a~z；{@code false} 表示非小写字母
     */
    public static boolean isLetterLower(final char c) {
        return c >= 'a' && c <= 'z';
    }

    /**
     * 检查是否为数字字符，数字字符指 0~9
     *
     * <pre>
     *     CharUtil.isNumber('a')  = false
     *     CharUtil.isNumber('A')  = false
     *     CharUtil.isNumber('1')  = true
     *     CharUtil.isNumber('-')  = false
     *     CharUtil.isNumber('\n') = false
     *     CharUtil.isNumber('©') = false
     * </pre>
     *
     * @param c 被检查的字符
     * @return {@code true} 表示为数字字符，数字字符指 0~9；{@code false} 表示非数字字符
     */
    public static boolean isNumber(final char c) {
        return c >= '0' && c <= '9';
    }

    /**
     * 是否为16进制规范的字符，包括 0~9、a~f、A~F
     *
     * <pre>
     *     CharUtil.isHex('1')  = true
     *     CharUtil.isHex('a')  = true
     *     CharUtil.isHex('A')  = true
     *     CharUtil.isHex('b')  = true
     *     CharUtil.isHex('B')  = true
     *     CharUtil.isHex('-')  = false
     * </pre>
     *
     * @param c 被检查的字符
     * @return {@code true} 表示为16进制规范的字符，包括 0~9、a~f、A~F；{@code false} 表示非16进制规范的字符串
     */
    public static boolean isHex(final char c) {
        return isNumber(c) || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F');
    }

    /**
     * 是否为字母或数字，包括 A~Z、a~z、0~9
     *
     * <pre>
     *     CharUtil.isLetterOrNumber('a')  = true
     *     CharUtil.isLetterOrNumber('A')  = true
     *     CharUtil.isLetterOrNumber('1')  = true
     *     CharUtil.isLetterOrNumber('-')  = false
     *     CharUtil.isLetterOrNumber('\n') = false
     *     CharUtil.isLetterOrNumber('©') = false
     * </pre>
     *
     * @param c 被检查的字符
     * @return {@code true} 表示为字母或数字，包括 A~Z、a~z、0~9；{@code false} 表示非字母或数字
     */
    public static boolean isLetterOrNumber(final char c) {
        return isLetter(c) || isNumber(c);
    }

    /**
     * 是否空白符，空白符包括空格、制表符、全角空格和不间断空格
     *
     * <pre>
     *     CharUtil.isBlank(' ')  = true
     *     CharUtil.isBlank('\t')  = true
     *     CharUtil.isBlank('\r')  = true
     *     CharUtil.isBlank('\n')  = true
     *     CharUtil.isBlank('-')  = false
     *     CharUtil.isBlank('©')  = false
     * </pre>
     *
     * @param c 被检查的字符
     * @return {@code true} 表示为空白符；{@code false} 表示非空白符
     */
    public static boolean isBlank(final char c) {
        return isBlank((int) c);
    }

    /**
     * 是否空白符，空白符包括空格、制表符、全角空格和不间断空格
     *
     * <pre>
     *     CharUtil.isBlank(0)  = false
     *     CharUtil.isBlank(9)  = true
     *     CharUtil.isBlank(10)  = true
     *     CharUtil.isBlank(13)  = true
     *     CharUtil.isBlank(32)  = true
     *     CharUtil.isBlank(33)  = false
     * </pre>
     *
     * @param c 被检查的字符
     * @return {@code true} 表示为空白符；{@code false} 表示非空白符
     */
    public static boolean isBlank(final int c) {
        return Character.isWhitespace(c) || Character.isSpaceChar(c) || c == '\ufeff' || c == '\u202a';
    }

    /**
     * 判断是否为 emoji 表情符
     *
     * @param c 被检查的字符
     * @return {@code true} 表示为 emoji 表情符；{@code false} 表示非 emoji 表情符
     */
    public static boolean isEmoji(final char c) {
        return !(c == 0x0 || c == 0x9 || c == 0xA || c == 0xD || c >= 0x20 && c <= 0xD7FF || c >= 0xE000 && c <= 0xFFFD);
    }

    /**
     * 是否为 Windows 或者 Linux（Unix）文件分隔符，Windows 平台下分隔符为 \，Linux（Unix）为 /
     *
     * @param c 被检查的字符
     * @return @code true} 表示为 Windows 或者 Linux（Unix）文件分隔符；{@code false} 表示非 Windows 或者 Linux（Unix）文件分隔符
     */
    public static boolean isFileSeparator(final char c) {
        return SLASH == c || BACKSLASH == c;
    }

    /**
     * 比较两个字符是否相同
     *
     * @param c1         字符1
     * @param c2         字符2
     * @param ignoreCase 是否忽略大小写
     * @return @code true} 表示相同；{@code false} 表示不相同
     */
    public static boolean equals(final char c1, final char c2, final boolean ignoreCase) {
        return ignoreCase ? Character.toLowerCase(c1) == Character.toLowerCase(c2) : c1 == c2;
    }

    /**
     * 获取字符类型
     *
     * @param c 字符
     * @return 字符类型
     */
    public static int getType(final int c) {
        return Character.getType(c);
    }

    /**
     * 字符转为字符串
     *
     * @param c 字符
     * @return 字符串
     */
    public static String toString(final char c) {
        return String.valueOf(c);
    }
}
