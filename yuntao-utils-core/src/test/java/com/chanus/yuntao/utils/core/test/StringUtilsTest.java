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
package com.chanus.yuntao.utils.core.test;

import com.chanus.yuntao.utils.core.ArrayUtils;
import com.chanus.yuntao.utils.core.CharsetUtils;
import com.chanus.yuntao.utils.core.StringUtils;
import org.junit.Test;

import java.util.*;

/**
 * StringUtils 测试类
 *
 * @author Chanus
 * @date 2020-06-20 22:09:35
 * @since 1.0.0
 */
public class StringUtilsTest {
    @Test
    public void isBlankTest() {
        System.out.println("================= isBlank(final String s) =================");
        System.out.println(StringUtils.isBlank(""));// true
        System.out.println(StringUtils.isBlank(" "));// true
        System.out.println(StringUtils.isBlank("\n"));// true
        System.out.println(StringUtils.isBlank("\t"));// true
        System.out.println(StringUtils.isBlank("\r"));// true
        System.out.println(StringUtils.isBlank("    "));// true
        System.out.println(StringUtils.isBlank("   aaa   "));// false
        System.out.println(StringUtils.isBlank("a   b"));// false
        System.out.println(StringUtils.isBlank(null));// true

        System.out.println("================= isBlank(CharSequence s) =================");
        CharSequence s1 = "";
        CharSequence s2 = " ";
        CharSequence s3 = "\n";
        CharSequence s4 = "a";
        System.out.println(StringUtils.isBlank(s1));// true
        System.out.println(StringUtils.isBlank(s2));// true
        System.out.println(StringUtils.isBlank(s3));// true
        System.out.println(StringUtils.isBlank(s4));// false
        StringBuffer stringBuffer = new StringBuffer();
        System.out.println(StringUtils.isBlank(stringBuffer));// true
        stringBuffer.append("\n").append("\r");
        System.out.println(StringUtils.isBlank(stringBuffer));// true
        stringBuffer.append("a").append(1);
        System.out.println(StringUtils.isBlank(stringBuffer));// false
    }

    @Test
    public void isAllBlankTest() {
        System.out.println("================= isAllBlank(String... strs) =================");
        System.out.println(StringUtils.isAllBlank("", null, "  "));// true
        System.out.println(StringUtils.isAllBlank(" ", null, "123"));// false
        System.out.println(StringUtils.isAllBlank());// true

        System.out.println("================= isAllBlank(CharSequence... strs) =================");
        CharSequence s1 = "";
        CharSequence s2 = " ";
        CharSequence s3 = "\n";
        CharSequence s4 = "a";
        System.out.println(StringUtils.isAllBlank(s1, s2, s3));// true
        System.out.println(StringUtils.isAllBlank(s1, s2, s4));// false
    }

    @Test
    public void isNotBlankTest() {
        System.out.println("================= isNotBlank(final String s) =================");
        System.out.println(StringUtils.isNotBlank(""));// false
        System.out.println(StringUtils.isNotBlank(" "));// false
        System.out.println(StringUtils.isNotBlank("\n"));// false
        System.out.println(StringUtils.isNotBlank("\t"));// false
        System.out.println(StringUtils.isNotBlank("\r"));// false
        System.out.println(StringUtils.isNotBlank("    "));// false
        System.out.println(StringUtils.isNotBlank("   aaa   "));// true
        System.out.println(StringUtils.isNotBlank("a   b"));// true
        System.out.println(StringUtils.isNotBlank(null));// false

        System.out.println("================= isNotBlank(CharSequence s) =================");
        CharSequence s1 = "";
        CharSequence s2 = " ";
        CharSequence s3 = "\n";
        CharSequence s4 = "a";
        System.out.println(StringUtils.isNotBlank(s1));// false
        System.out.println(StringUtils.isNotBlank(s2));// false
        System.out.println(StringUtils.isNotBlank(s3));// false
        System.out.println(StringUtils.isNotBlank(s4));// true
        StringBuffer stringBuffer = new StringBuffer();
        System.out.println(StringUtils.isNotBlank(stringBuffer));// false
        stringBuffer.append("\n").append("\r");
        System.out.println(StringUtils.isNotBlank(stringBuffer));// false
        stringBuffer.append("a").append(1);
        System.out.println(StringUtils.isNotBlank(stringBuffer));// true
    }

    @Test
    public void isAllNotBlankTest() {
        System.out.println("================= isAllNotBlank(String... strs) =================");
        System.out.println(StringUtils.isAllNotBlank("", null, "  "));// false
        System.out.println(StringUtils.isAllNotBlank(" ", null, "123"));// false
        System.out.println(StringUtils.isAllNotBlank("a", "b", "123"));// true

        System.out.println("================= isAllNotBlank(CharSequence... strs) =================");
        CharSequence s1 = "";
        CharSequence s2 = " ";
        CharSequence s3 = "\n";
        CharSequence s4 = "a";
        CharSequence s5 = "b";
        System.out.println(StringUtils.isAllNotBlank(s1, s2, s3));// false
        System.out.println(StringUtils.isAllNotBlank(s1, s2, s4));// false
        System.out.println(StringUtils.isAllNotBlank(s4, s5));// true
    }

    @Test
    public void hasBlankTest() {
        System.out.println("================= hasBlank(String... strs) =================");
        System.out.println(StringUtils.hasBlank("", null, "  "));// true
        System.out.println(StringUtils.hasBlank(" ", null, "123"));// true
        System.out.println(StringUtils.hasBlank("a", "b", "123"));// false

        System.out.println("================= hasBlank(CharSequence... strs) =================");
        CharSequence s1 = "";
        CharSequence s2 = " ";
        CharSequence s3 = "\n";
        CharSequence s4 = "a";
        CharSequence s5 = "b";
        System.out.println(StringUtils.hasBlank(s1, s2, s3));// true
        System.out.println(StringUtils.hasBlank(s1, s2, s4));// true
        System.out.println(StringUtils.hasBlank(s4, s5));// false
    }

    @Test
    public void isEmptyTest() {
        System.out.println("================= isBlank(final String s) =================");
        System.out.println(StringUtils.isEmpty(""));// true
        System.out.println(StringUtils.isEmpty(" "));// false
        System.out.println(StringUtils.isEmpty("\n"));// false
        System.out.println(StringUtils.isEmpty("\t"));// false
        System.out.println(StringUtils.isEmpty("\r"));// false
        System.out.println(StringUtils.isEmpty("    "));// false
        System.out.println(StringUtils.isEmpty("   aaa   "));// false
        System.out.println(StringUtils.isEmpty("a   b"));// false
        System.out.println(StringUtils.isEmpty(null));// true

        System.out.println("================= isBlank(CharSequence s) =================");
        CharSequence s1 = "";
        CharSequence s2 = " ";
        CharSequence s3 = "\n";
        CharSequence s4 = "a";
        System.out.println(StringUtils.isEmpty(s1));// true
        System.out.println(StringUtils.isEmpty(s2));// false
        System.out.println(StringUtils.isEmpty(s3));// false
        System.out.println(StringUtils.isEmpty(s4));// false
        StringBuffer stringBuffer = new StringBuffer();
        System.out.println(StringUtils.isEmpty(stringBuffer));// true
        stringBuffer.append("\n").append("\r");
        System.out.println(StringUtils.isEmpty(stringBuffer));// false
        stringBuffer.append("a").append(1);
        System.out.println(StringUtils.isEmpty(stringBuffer));// false
    }

    @Test
    public void isAllEmptyTest() {
        System.out.println("================= isAllEmpty(String... strs) =================");
        System.out.println(StringUtils.isAllEmpty("", null, ""));// true
        System.out.println(StringUtils.isAllEmpty("", null, "  "));// false
        System.out.println(StringUtils.isAllEmpty(" ", null, "123"));// false

        System.out.println("================= isAllEmpty(CharSequence... strs) =================");
        CharSequence s1 = "";
        CharSequence s2 = " ";
        CharSequence s3 = "\n";
        CharSequence s4 = "a";
        System.out.println(StringUtils.isAllEmpty(s1, null));// true
        System.out.println(StringUtils.isAllEmpty(s1, s2, s3));// false
        System.out.println(StringUtils.isAllEmpty(s1, s2, s4));// false
    }

    @Test
    public void isNotEmptyTest() {
        System.out.println("================= isNotBlank(final String s) =================");
        System.out.println(StringUtils.isNotEmpty(""));// false
        System.out.println(StringUtils.isNotEmpty(" "));// true
        System.out.println(StringUtils.isNotEmpty("\n"));// true
        System.out.println(StringUtils.isNotEmpty("\t"));// true
        System.out.println(StringUtils.isNotEmpty("\r"));// true
        System.out.println(StringUtils.isNotEmpty("    "));// true
        System.out.println(StringUtils.isNotEmpty("   aaa   "));// true
        System.out.println(StringUtils.isNotEmpty("a   b"));// true
        System.out.println(StringUtils.isNotEmpty(null));// false

        System.out.println("================= isNotBlank(CharSequence s) =================");
        CharSequence s1 = "";
        CharSequence s2 = " ";
        CharSequence s3 = "\n";
        CharSequence s4 = "a";
        System.out.println(StringUtils.isNotEmpty(s1));// false
        System.out.println(StringUtils.isNotEmpty(s2));// true
        System.out.println(StringUtils.isNotEmpty(s3));// true
        System.out.println(StringUtils.isNotEmpty(s4));// true
        StringBuffer stringBuffer = new StringBuffer();
        System.out.println(StringUtils.isNotEmpty(stringBuffer));// false
        stringBuffer.append("\n").append("\r");
        System.out.println(StringUtils.isNotEmpty(stringBuffer));// true
        stringBuffer.append("a").append(1);
        System.out.println(StringUtils.isNotEmpty(stringBuffer));// true
    }

    @Test
    public void isAllNotEmptyTest() {
        System.out.println("================= isAllNotEmpty(String... strs) =================");
        System.out.println(StringUtils.isAllNotEmpty("", null, ""));// false
        System.out.println(StringUtils.isAllNotEmpty(" ", null, "123"));// false
        System.out.println(StringUtils.isAllNotEmpty("a", "b", "123"));// true

        System.out.println("================= isAllNotEmpty(CharSequence... strs) =================");
        CharSequence s1 = "";
        CharSequence s2 = " ";
        CharSequence s3 = "\n";
        CharSequence s4 = "a";
        CharSequence s5 = "b";
        System.out.println(StringUtils.isAllNotEmpty(s1, s2, s3));// false
        System.out.println(StringUtils.isAllNotEmpty(s1, s2, s4));// false
        System.out.println(StringUtils.isAllNotEmpty(s4, s5));// true
    }

    @Test
    public void hasEmptyTest() {
        System.out.println("================= hasEmpty(String... strs) =================");
        System.out.println(StringUtils.hasEmpty("", null, ""));// true
        System.out.println(StringUtils.hasEmpty(" ", null, "123"));// true
        System.out.println(StringUtils.hasEmpty("a", "b", "123"));// false

        System.out.println("================= hasEmpty(CharSequence... strs) =================");
        CharSequence s1 = "";
        CharSequence s2 = " ";
        CharSequence s3 = "\n";
        CharSequence s4 = "a";
        CharSequence s5 = "b";
        System.out.println(StringUtils.hasEmpty(s1, s2, s3));// true
        System.out.println(StringUtils.hasEmpty(s1, s2, s4));// true
        System.out.println(StringUtils.hasEmpty(s4, s5));// false
    }

    @Test
    public void defaultIfBlankTest() {
        System.out.println(StringUtils.defaultIfBlank("", "123"));// 123
        System.out.println(StringUtils.defaultIfBlank(" ", "123"));// 123
        System.out.println(StringUtils.defaultIfBlank("\n", "123"));// 123
        System.out.println(StringUtils.defaultIfBlank("\t", "123"));// 123
        System.out.println(StringUtils.defaultIfBlank("\r", "123"));// 123
        System.out.println(StringUtils.defaultIfBlank("    ", "123"));// 123
        System.out.println(StringUtils.defaultIfBlank("   aaa   ", "123"));//    aaa
        System.out.println(StringUtils.defaultIfBlank("a   b", "123"));// a   b
        System.out.println(StringUtils.defaultIfBlank(null, "123"));// 123
    }

    @Test
    public void defaultIfEmptyTest() {
        System.out.println(StringUtils.defaultIfEmpty("", "123"));// 123
        System.out.println(StringUtils.defaultIfEmpty(" ", "123"));//
        System.out.println(StringUtils.defaultIfEmpty("\n", "123"));// 换行
        System.out.println(StringUtils.defaultIfEmpty("\t", "123"));// 制表符
        System.out.println(StringUtils.defaultIfEmpty("\r", "123"));// 回车
        System.out.println(StringUtils.defaultIfEmpty("    ", "123"));//
        System.out.println(StringUtils.defaultIfEmpty("   aaa   ", "123"));//    aaa
        System.out.println(StringUtils.defaultIfEmpty("a   b", "123"));// a   b
        System.out.println(StringUtils.defaultIfEmpty(null, "123"));// 123
    }

    @Test
    public void defaultIfNullTest() {
        System.out.println(StringUtils.defaultIfNull("", "123"));// ""
        System.out.println(StringUtils.defaultIfNull(" ", "123"));// 空格
        System.out.println(StringUtils.defaultIfNull("\n", "123"));// 换行
        System.out.println(StringUtils.defaultIfNull("\t", "123"));// 制表符
        System.out.println(StringUtils.defaultIfNull("\r", "123"));// 回车
        System.out.println(StringUtils.defaultIfNull("a   b", "123"));// a   b
        System.out.println(StringUtils.defaultIfNull(null, "123"));// 123
    }

    @Test
    public void trimTest() {
        System.out.println(StringUtils.trim(""));// null
        System.out.println(StringUtils.trim(" "));// null
        System.out.println(StringUtils.trim("\n"));// null
        System.out.println(StringUtils.trim("\t"));// null
        System.out.println(StringUtils.trim("\r"));// null
        System.out.println(StringUtils.trim("    "));// null
        System.out.println(StringUtils.trim("   aaa   "));// aaa
        System.out.println(StringUtils.trim("a   b"));// a   b
        System.out.println(StringUtils.trim(null));// null
    }

    @Test
    public void containsTest() {
        System.out.println(StringUtils.contains("", ""));// true
        System.out.println(StringUtils.contains("  ", " "));// true
        System.out.println(StringUtils.contains("  ", "   "));// false
        System.out.println(StringUtils.contains(null, ""));// false
        System.out.println(StringUtils.contains("", null));// false
        System.out.println(StringUtils.contains(null, null));// false
        System.out.println(StringUtils.contains("asdfgh", "asd"));// true
        System.out.println(StringUtils.contains("    a   d  ", "a"));// true
        System.out.println(StringUtils.contains("asdf", ""));// true
        System.out.println(StringUtils.contains("", "aa"));// false
    }

    @Test
    public void equalsTest() {
        System.out.println(StringUtils.equals("", ""));// true
        System.out.println(StringUtils.equals("", null));// false
        System.out.println(StringUtils.equals(null, ""));// false
        System.out.println(StringUtils.equals(null, null));// true
        System.out.println(StringUtils.equals(" ", " "));// true
        System.out.println(StringUtils.equals("", " "));// false
        System.out.println(StringUtils.equals("aa", "AA"));// false
        System.out.println(StringUtils.equals("bb", "cc"));// false
        System.out.println(StringUtils.equals("dd", "dd"));// true
    }

    @Test
    public void equalsAnyTest() {
        System.out.println(StringUtils.equalsAny("abc"));// false
        System.out.println(StringUtils.equalsAny("abc", "123", "a", "bc", "abcd"));// false
        System.out.println(StringUtils.equalsAny("abc", "123", "a", "Abc", "abcd"));// false
        System.out.println(StringUtils.equalsAny("abc", "123", "a", "abc", "abcd"));// true
        System.out.println(StringUtils.equalsAny("", "123", null, "abc", "abcd"));// false
        System.out.println(StringUtils.equalsAny(null, "123", null, "abc", "abcd"));// true
    }

    @Test
    public void equalsIgnoreCaseTest() {
        System.out.println(StringUtils.equalsIgnoreCase("", ""));// true
        System.out.println(StringUtils.equalsIgnoreCase("", null));// false
        System.out.println(StringUtils.equalsIgnoreCase(null, ""));// false
        System.out.println(StringUtils.equalsIgnoreCase(null, null));// true
        System.out.println(StringUtils.equalsIgnoreCase(" ", " "));// true
        System.out.println(StringUtils.equalsIgnoreCase("", " "));// false
        System.out.println(StringUtils.equalsIgnoreCase("aa", "AA"));// true
        System.out.println(StringUtils.equalsIgnoreCase("bb", "cc"));// false
        System.out.println(StringUtils.equalsIgnoreCase("dd", "dd"));// true
    }

    @Test
    public void equalsAnyIgnoreCaseTest() {
        System.out.println(StringUtils.equalsAnyIgnoreCase("abc"));// false
        System.out.println(StringUtils.equalsAnyIgnoreCase("abc", "123", "a", "bc", "abcd"));// false
        System.out.println(StringUtils.equalsAnyIgnoreCase("abc", "123", "a", "Abc", "abcd"));// true
        System.out.println(StringUtils.equalsAnyIgnoreCase("abc", "123", "a", "abc", "abcd"));// true
        System.out.println(StringUtils.equalsAnyIgnoreCase("", "123", null, "abc", "abcd"));// false
        System.out.println(StringUtils.equalsAnyIgnoreCase(null, "123", null, "abc", "abcd"));// true
    }

    @Test
    public void isNumericTest() {
        System.out.println(StringUtils.isNumeric(""));// false
        System.out.println(StringUtils.isNumeric(null));// false
        System.out.println(StringUtils.isNumeric("  "));// false
        System.out.println(StringUtils.isNumeric("0"));// true
        System.out.println(StringUtils.isNumeric(" 12 "));// false
        System.out.println(StringUtils.isNumeric(" -12 "));// false
        System.out.println(StringUtils.isNumeric("+1"));// false
        System.out.println(StringUtils.isNumeric("-1"));// false
        System.out.println(StringUtils.isNumeric("12we34"));// false
        System.out.println(StringUtils.isNumeric("123"));// true
        System.out.println(StringUtils.isNumeric("001"));// true
        System.out.println(StringUtils.isNumeric("1.1"));// false
    }

    @Test
    public void isNumberTest() {
        System.out.println(StringUtils.isNumber(""));// false
        System.out.println(StringUtils.isNumber(null));// false
        System.out.println(StringUtils.isNumber("  "));// false
        System.out.println(StringUtils.isNumber("+0"));// true
        System.out.println(StringUtils.isNumber("-0"));// true
        System.out.println(StringUtils.isNumber("-0.00"));// true
        System.out.println(StringUtils.isNumber("-000.000"));// false
        System.out.println(StringUtils.isNumber("-010.001"));// false
        System.out.println(StringUtils.isNumber(" 12 "));// false
        System.out.println(StringUtils.isNumber(" -12 "));// false
        System.out.println(StringUtils.isNumber("+1"));// true
        System.out.println(StringUtils.isNumber("-1"));// true
        System.out.println(StringUtils.isNumber("12we34"));// false
        System.out.println(StringUtils.isNumber("123"));// true
        System.out.println(StringUtils.isNumber("001"));// false
        System.out.println(StringUtils.isNumber("+11111.1111111"));// true
    }

    @Test
    public void boolean2StringTest() {
        System.out.println(StringUtils.boolean2String(true));
        System.out.println(StringUtils.boolean2String(false));
        System.out.println(StringUtils.boolean2String(null));
    }

    @Test
    public void compressTest() {
        String s = "test";
        byte[] b = StringUtils.compress(s);
        String s2 = new String(Objects.requireNonNull(StringUtils.decompress(b)));
        System.out.println(s2);
    }

    @Test
    public void repeatTest() {
        System.out.println(StringUtils.repeat("abc", 5));
    }

    @Test
    public void repeatWithSeparatorTest() {
        System.out.println(StringUtils.repeatWithSeparator("a", 10, "-"));
        System.out.println(StringUtils.repeatWithSeparator("a", 10, null));
        System.out.println(StringUtils.repeatWithSeparator("", 10, "-"));
    }

    @Test
    public void replaceTest() {
        System.out.println(StringUtils.replace("13378653645", "*", 3, 7, 4));
        System.out.println(StringUtils.replace("13378653645", 3, 7));
    }

    @Test
    public void replaceAnyTest() {
        System.out.println(StringUtils.replaceAny("13378653645", "*", "3", "7", "4"));
    }

    @Test
    public void capitalizeTest() {
        System.out.println(StringUtils.capitalize("test"));
    }

    @Test
    public void uncapitalizeTest() {
        System.out.println(StringUtils.uncapitalize("Test"));
    }

    @Test
    public void string2UnicodeTest() {
        System.out.println(StringUtils.string2Unicode("测试"));
        System.out.println(StringUtils.string2Unicode("test"));
        System.out.println(StringUtils.string2Unicode("测试1234567890"));
        System.out.println(StringUtils.string2Unicode("测试!@#$%^&*()"));
    }

    @Test
    public void unicode2StringTest() {
        System.out.println(StringUtils.unicode2String("\\u6d4b\\u8bd5"));
        System.out.println(StringUtils.unicode2String("\\u74\\u65\\u73\\u74"));
        System.out.println(StringUtils.unicode2String("\\u6d4b\\u8bd5\\u31\\u32\\u33\\u34\\u35\\u36\\u37\\u38\\u39\\u30"));
        System.out.println(StringUtils.unicode2String("\\u6d4b\\u8bd5\\u21\\u40\\u23\\u24\\u25\\u5e\\u26\\u2a\\u28\\u29"));
    }

    @Test
    public void utf8BytesTest() {
        String s = "测试";
        byte[] b = StringUtils.utf8Bytes(s);
        System.out.println(Arrays.toString(b));// [-26, -75, -117, -24, -81, -107]
    }

    @Test
    public void bytesTest() {
        String s = "测试";
        byte[] b = StringUtils.bytes(s);
        System.out.println(Arrays.toString(b));// [-26, -75, -117, -24, -81, -107]
        b = StringUtils.bytes(s, CharsetUtils.CHARSET_GBK);
        System.out.println(Arrays.toString(b));// [-78, -30, -54, -44]
    }

    @Test
    public void toUtf8StringTest() {
        byte[] b = {-26, -75, -117, -24, -81, -107};
        System.out.println(StringUtils.toUtf8String(b));// 测试
    }

    @Test
    public void toStringTest() {
        byte[] b = {-26, -75, -117, -24, -81, -107};
        System.out.println(StringUtils.toString(b, CharsetUtils.UTF_8));// 测试
        byte[] b2 = {-78, -30, -54, -44};
        System.out.println(StringUtils.toString(b2, CharsetUtils.CHARSET_GBK));// 测试
    }

    @Test
    public void lengthTest() {
        System.out.println(StringUtils.length(""));// 0
        System.out.println(StringUtils.length(null));// 0
        System.out.println(StringUtils.length("123"));// 3
        System.out.println(StringUtils.length(new StringBuilder("abc")));// 3
    }

    @Test
    public void concatTest() {
        System.out.println(StringUtils.concat(true,"", "a", "b", null, "c"));// abc
        System.out.println(StringUtils.concat(false,"", "a", "b", null, "c"));// abnullc
    }

    @Test
    public void removePrefixTest() {
        System.out.println(StringUtils.removePrefix("abcdefghijklmn", "abc"));// defghijklmn
        System.out.println(StringUtils.removePrefix("Abcdefghijklmn", "abc"));// Abcdefghijklmn
    }

    @Test
    public void removePrefixIgnoreCaseTest() {
        System.out.println(StringUtils.removePrefixIgnoreCase("abcdefghijklmn", "abc"));// defghijklmn
        System.out.println(StringUtils.removePrefixIgnoreCase("Abcdefghijklmn", "abc"));// defghijklmn
    }

    @Test
    public void removeSuffixTest() {
        System.out.println(StringUtils.removeSuffix("abcdefghijklmn", "lmn"));// abcdefghijk
        System.out.println(StringUtils.removeSuffix("AbcdefghijkLmN", "lmn"));// AbcdefghijkLmN
    }

    @Test
    public void removeSuffixIgnoreCaseTest() {
        System.out.println(StringUtils.removeSuffixIgnoreCase("abcdefghijklmn", "lmn"));// abcdefghijk
        System.out.println(StringUtils.removeSuffixIgnoreCase("AbcdefghijkLmN", "lmn"));// Abcdefghijk
    }

    @Test
    public void toUnderlineCaseTest() {
        System.out.println(StringUtils.toUnderlineCase("helloWorld"));// hello_world
        System.out.println(StringUtils.toUnderlineCase("AbCdEfG"));// ab_cd_ef_g
        System.out.println(StringUtils.toUnderlineCase("abc_0_DefG"));// abc_0_def_g
    }

    @Test
    public void toCamelCaseTest() {
        System.out.println(StringUtils.toCamelCase("hello_world"));// helloWorld
        System.out.println(StringUtils.toCamelCase("helloWorld"));// helloWorld
        System.out.println(StringUtils.toCamelCase("Hello_World"));// helloWorld
    }

    @Test
    public void fillBeforeTest() {
        System.out.println(StringUtils.fillBefore("1", "0", 5));// 00001
        System.out.println(StringUtils.fillBefore("0000001", "0", 5));// 0000001
    }

    @Test
    public void fillAfterTest() {
        System.out.println(StringUtils.fillAfter("1", "0", 5));// 10000
        System.out.println(StringUtils.fillAfter("1000000", "0", 5));// 1000000
    }

    @Test
    public void fillTest() {
        System.out.println(StringUtils.fill("1", "0", 5, true));// 00001
        System.out.println(StringUtils.fill("0000001", "0", 5, true));// 0000001
        System.out.println(StringUtils.fill("1", "0", 5, false));// 10000
        System.out.println(StringUtils.fill("1000000", "0", 5, false));// 1000000
    }

    @Test
    public void reverseTest() {
        System.out.println(StringUtils.reverse("abcdefg"));// gfedcba
        System.out.println(StringUtils.reverse("Hello World"));// dlroW olleH
    }

    @Test
    public void indexOfTest() {
        String s = "Hello World! Java.";
        System.out.println(StringUtils.indexOf(s, "hello", 0, false));// -1
        System.out.println(StringUtils.indexOf(s, "Hello", 1, false));// -1
        System.out.println(StringUtils.indexOf(s, "Hello"));// 0
        System.out.println(StringUtils.indexOfIgnoreCase(s, "world"));// 6
        System.out.println(StringUtils.indexOf(s, " ", 0, true));// 5
        System.out.println(StringUtils.indexOf(s, " ", 6, true));// 12
    }

    @Test
    public void substringTest() {
        String s = "abcdefg";
        System.out.println(StringUtils.substring(s, 0, 3));// abc
        System.out.println(StringUtils.substring(s, 0, 7));// abcdefg
        System.out.println(StringUtils.substring(s, 0, 10));// abcdefg
        System.out.println(StringUtils.substring(s, 1, 1));// ""
        System.out.println(StringUtils.substring(s, -1, 10));// g
        System.out.println(StringUtils.substring(s, 1, -2));// bcde
        System.out.println(StringUtils.substring(s, 1, -10));// ""
        System.out.println(StringUtils.substring(s, 5, 4));// e
        System.out.println(StringUtils.substring(s, 5, -4));// de
        System.out.println(StringUtils.substring(s, 10, 1));// bcdefg
    }

    @Test
    public void leftTest() {
        String s = "abcdefg";
        System.out.println(StringUtils.left(s, 3));// abc
        System.out.println(StringUtils.left(s, 7));// abcdefg
        System.out.println(StringUtils.left(s, 10));// abcdefg
        System.out.println(StringUtils.left(s, 0));// ""
        System.out.println(StringUtils.left(s, -2));// abcde
        System.out.println(StringUtils.left(s, -10));// ""
    }

    @Test
    public void rightTest() {
        String s = "abcdefg";
        System.out.println(StringUtils.right(s, 0));// abcdefg
        System.out.println(StringUtils.right(s, 1));// bcdefg
        System.out.println(StringUtils.right(s, -1));// g
        System.out.println(StringUtils.right(s, 10));// ""
    }

    @Test
    public void splitTest() {
        String s = " a1 , b 2 , c3 , d  4 , e5 , f6 ,  ,   g7  , null ";
        List<String> list1 = StringUtils.split(s, ',', -1, false, false, false);
        list1.forEach(System.out::println);
        System.out.println("----------------------------------------------------------------");
        List<String> list2 = StringUtils.split(s, ',', -1, true, true, false);
        list2.forEach(System.out::println);
        System.out.println("----------------------------------------------------------------");
        List<String> list3 = StringUtils.split(s, ',', 3);
        list3.forEach(System.out::println);
        System.out.println("----------------------------------------------------------------");
        List<String> list4 = StringUtils.split(s, ',');
        list4.forEach(System.out::println);
        System.out.println("----------------------------------------------------------------");
        List<String> list5 = StringUtils.split(s, 3);
        list5.forEach(System.out::println);
        System.out.println("----------------------------------------------------------------");
        List<String> list6 = StringUtils.split(s);
        list6.forEach(System.out::println);
    }

    @Test
    public void splitTest2() {
        String s = " a1 , b 2 , c3 , d  4 , e5 , f6 ,  ,   g7  , null ";
        List<String> list1 = StringUtils.split(s, ",", -1, false, false, false);
        list1.forEach(System.out::println);
        System.out.println("----------------------------------------------------------------");
        List<String> list2 = StringUtils.split(s, ",", -1, true, true, false);
        list2.forEach(System.out::println);
        System.out.println("----------------------------------------------------------------");
        List<String> list3 = StringUtils.split(s, ",", 3);
        list3.forEach(System.out::println);
        System.out.println("----------------------------------------------------------------");
        List<String> list4 = StringUtils.split(s, ",");
        list4.forEach(System.out::println);
        System.out.println("----------------------------------------------------------------");
        List<String> list5 = StringUtils.split(s, "");
        list5.forEach(System.out::println);
    }

    @Test
    public void splitToArrayTest() {
        String s = " a1 @ b 2 @ c3 @ d  4 @ e5 @ f6 @  @   g7  @ null ";
        String[] array1 = StringUtils.splitToArray(s, '@', -1, false, false, false);
        System.out.println(ArrayUtils.toString(array1));
        System.out.println("----------------------------------------------------------------");
        String[] array2 = StringUtils.splitToArray(s, '@', -1, true, true, false);
        System.out.println(ArrayUtils.toString(array2));
        System.out.println("----------------------------------------------------------------");
        String[] array3 = StringUtils.splitToArray(s, '@', 3);
        System.out.println(ArrayUtils.toString(array3));
        System.out.println("----------------------------------------------------------------");
        String[] array4 = StringUtils.splitToArray(s, '@');
        System.out.println(ArrayUtils.toString(array4));
        System.out.println("----------------------------------------------------------------");
        String[] array5 = StringUtils.splitToArray(s, 3);
        System.out.println(ArrayUtils.toString(array5));
        System.out.println("----------------------------------------------------------------");
        String[] array6 = StringUtils.splitToArray(s);
        System.out.println(ArrayUtils.toString(array6));
    }

    @Test
    public void splitToArrayTest2() {
        String s = " a1 @ b 2 @ c3 @ d  4 @ e5 @ f6 @  @   g7  @ null ";
        String[] array1 = StringUtils.splitToArray(s, "@", -1, false, false, false);
        System.out.println(ArrayUtils.toString(array1));
        System.out.println("----------------------------------------------------------------");
        String[] array2 = StringUtils.splitToArray(s, "@", -1, true, true, false);
        System.out.println(ArrayUtils.toString(array2));
        System.out.println("----------------------------------------------------------------");
        String[] array3 = StringUtils.splitToArray(s, "@", 3);
        System.out.println(ArrayUtils.toString(array3));
        System.out.println("----------------------------------------------------------------");
        String[] array4 = StringUtils.splitToArray(s, "@");
        System.out.println(ArrayUtils.toString(array4));
        System.out.println("----------------------------------------------------------------");
        String[] array5 = StringUtils.splitToArray(s, "");
        System.out.println(ArrayUtils.toString(array5));
    }

    @Test
    public void formatTest() {
        System.out.println(StringUtils.format("this is {} for {}", "a", "b"));// this is a for b
        System.out.println(StringUtils.format("this is \\{} for {}", "a", "b"));// this is {} for a
        System.out.println(StringUtils.format("this is \\\\{} for {}", "a", "b"));// this is \a for b
    }
}
