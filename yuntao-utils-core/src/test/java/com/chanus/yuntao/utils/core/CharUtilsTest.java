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

import org.junit.Test;

import java.io.File;

/**
 * CharUtils 测试类
 *
 * @author Chanus
 * @date 2020-06-20 09:39:55
 * @since 1.0.0
 */
public class CharUtilsTest {
    @Test
    public void isCharTest() {
        System.out.println(CharUtils.isChar("a"));// false
        System.out.println(CharUtils.isChar('a'));// true
        System.out.println(CharUtils.isChar("\n"));// false
        System.out.println(CharUtils.isChar('\n'));// true
    }

    @Test
    public void isAsciiTest() {
        System.out.println(CharUtils.isAscii('a'));// true
        System.out.println(CharUtils.isAscii('A'));// true
        System.out.println(CharUtils.isAscii('1'));// true
        System.out.println(CharUtils.isAscii('-'));// true
        System.out.println(CharUtils.isAscii('\n'));// true
        System.out.println(CharUtils.isAscii('©'));// false
    }

    @Test
    public void isAsciiPrintableTest() {
        System.out.println(CharUtils.isAsciiPrintable('a'));// true
        System.out.println(CharUtils.isAsciiPrintable('A'));// true
        System.out.println(CharUtils.isAsciiPrintable('1'));// true
        System.out.println(CharUtils.isAsciiPrintable('-'));// true
        System.out.println(CharUtils.isAsciiPrintable('\n'));// false
        System.out.println(CharUtils.isAsciiPrintable('©'));// false
    }

    @Test
    public void isAsciiControlTest() {
        System.out.println(CharUtils.isAsciiControl('a'));// false
        System.out.println(CharUtils.isAsciiControl('A'));// false
        System.out.println(CharUtils.isAsciiControl('1'));// false
        System.out.println(CharUtils.isAsciiControl('-'));// false
        System.out.println(CharUtils.isAsciiControl('\n'));// true
        System.out.println(CharUtils.isAsciiControl('©'));// false
    }

    @Test
    public void isLetterTest() {
        System.out.println(CharUtils.isLetter('a'));// true
        System.out.println(CharUtils.isLetter('A'));// true
        System.out.println(CharUtils.isLetter('1'));// false
        System.out.println(CharUtils.isLetter('-'));// false
        System.out.println(CharUtils.isLetter('\n'));// false
        System.out.println(CharUtils.isLetter('©'));// false
    }

    @Test
    public void isLetterUpperTest() {
        System.out.println(CharUtils.isLetterUpper('a'));// false
        System.out.println(CharUtils.isLetterUpper('A'));// true
        System.out.println(CharUtils.isLetterUpper('1'));// false
        System.out.println(CharUtils.isLetterUpper('-'));// false
        System.out.println(CharUtils.isLetterUpper('\n'));// false
        System.out.println(CharUtils.isLetterUpper('©'));// false
    }

    @Test
    public void isLetterLowerTest() {
        System.out.println(CharUtils.isLetterLower('a'));// true
        System.out.println(CharUtils.isLetterLower('A'));// false
        System.out.println(CharUtils.isLetterLower('1'));// false
        System.out.println(CharUtils.isLetterLower('-'));// false
        System.out.println(CharUtils.isLetterLower('\n'));// false
        System.out.println(CharUtils.isLetterLower('©'));// false
    }

    @Test
    public void isNumberTest() {
        System.out.println(CharUtils.isNumber('a'));// false
        System.out.println(CharUtils.isNumber('A'));// false
        System.out.println(CharUtils.isNumber('1'));// true
        System.out.println(CharUtils.isNumber('-'));// false
        System.out.println(CharUtils.isNumber('\n'));// false
        System.out.println(CharUtils.isNumber('©'));// false
    }

    @Test
    public void isHexTest() {
        System.out.println(CharUtils.isHex('1'));// true
        System.out.println(CharUtils.isHex('a'));// true
        System.out.println(CharUtils.isHex('A'));// true
        System.out.println(CharUtils.isHex('b'));// true
        System.out.println(CharUtils.isHex('B'));// true
        System.out.println(CharUtils.isHex('-'));// false
    }

    @Test
    public void isLetterOrNumberTest() {
        System.out.println(CharUtils.isLetterOrNumber('a'));// true
        System.out.println(CharUtils.isLetterOrNumber('A'));// true
        System.out.println(CharUtils.isLetterOrNumber('1'));// true
        System.out.println(CharUtils.isLetterOrNumber('-'));// false
        System.out.println(CharUtils.isLetterOrNumber('\n'));// false
        System.out.println(CharUtils.isLetterOrNumber('©'));// false
    }

    @Test
    public void isBlankTest() {
        System.out.println(CharUtils.isBlank(' '));// true
        System.out.println(CharUtils.isBlank('\t'));// true
        System.out.println(CharUtils.isBlank('\r'));// true
        System.out.println(CharUtils.isBlank('\n'));// true
        System.out.println(CharUtils.isBlank('-'));// false
        System.out.println(CharUtils.isBlank('©'));// false
        System.out.println("==============================");
        System.out.println("空字符：" + CharUtils.isBlank(0));// false
        System.out.println("水平制表符：" + CharUtils.isBlank(9));// true
        System.out.println("换行键：" + CharUtils.isBlank(10));// true
        System.out.println("回车键：" + CharUtils.isBlank(13));// true
        System.out.println("空格：" + CharUtils.isBlank(32));// true
        System.out.println("感叹号：" + CharUtils.isBlank(33));// false
    }

    @Test
    public void isEmojiTest() {
        System.out.println(CharUtils.isEmoji("🌹".charAt(0)));// true
        System.out.println(CharUtils.isEmoji("😄".charAt(0)));// true
        System.out.println(CharUtils.isEmoji('1'));// false
        System.out.println(CharUtils.isEmoji('-'));// false
        System.out.println(CharUtils.isEmoji('\n'));// false
        System.out.println(CharUtils.isEmoji('©'));// false
    }

    @Test
    public void isFileSeparatorTest() {
        char separator = File.separatorChar;
        System.out.println(separator + "：" + CharUtils.isFileSeparator(separator));
    }

    @Test
    public void equalsTest() {
        System.out.println(CharUtils.equals('a', 'A', true));// true
        System.out.println(CharUtils.equals('a', 'A', false));// false
        System.out.println(CharUtils.equals('a', 'a', true));// true
        System.out.println(CharUtils.equals('a', 'b', true));// false
        System.out.println(CharUtils.equals('a', 'B', false));// false
    }

    @Test
    public void getTypeTest() {
        System.out.println(CharUtils.getType('©'));// 28
        System.out.println(CharUtils.getType(28));// 15
        System.out.println(CharUtils.getType('a'));// 2
        System.out.println(CharUtils.getType('A'));// 1
        System.out.println(CharUtils.getType('1'));// 9
        System.out.println(CharUtils.getType('-'));// 20
        System.out.println(CharUtils.getType('\n'));// 15
        System.out.println(CharUtils.getType('\t'));// 15
    }

    @Test
    public void toStringTest() {
        System.out.println(CharUtils.toString('a'));// a
        System.out.println(CharUtils.toString('A'));// A
        System.out.println(CharUtils.toString('1'));// 1
        System.out.println(CharUtils.toString('-'));// -
        System.out.println(CharUtils.toString('\n'));// 换行
        System.out.println(CharUtils.toString('©'));// ©
    }
}
