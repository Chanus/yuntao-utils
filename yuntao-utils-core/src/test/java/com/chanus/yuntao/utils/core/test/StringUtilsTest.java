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

import com.chanus.yuntao.utils.core.CharsetUtils;
import com.chanus.yuntao.utils.core.StringUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.Objects;

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
    public void replaceTest() {
        System.out.println(StringUtils.replace("13378653645", "*", 3, 7, 4));
        System.out.println(StringUtils.replace("13378653645", 3, 7));
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
    }

    @Test
    public void unicode2StringTest() {
        System.out.println(StringUtils.unicode2String("\\u6d4b\\u8bd5"));
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
}
