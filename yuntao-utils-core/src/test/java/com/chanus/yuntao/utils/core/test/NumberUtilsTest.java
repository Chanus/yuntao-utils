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

import com.chanus.yuntao.utils.core.NumberUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * NumberUtils 测试类
 *
 * @author Chanus
 * @date 2020-07-04 14:09:11
 * @since 1.0.0
 */
public class NumberUtilsTest {
    @Test
    public void addTest() {
        System.out.println("两个 float 类型的数值相加");
        System.out.println(1.1f + 2.2f);// = 3.3000002
        System.out.println(NumberUtils.add(1.1f, 2.2f));// = 3.3

        System.out.println("float 类型的数值加 double 类型的数值");
        System.out.println(1.1f + 2.2d);// = 3.300000023841858
        System.out.println(NumberUtils.add(1.1f, 2.2d));// = 3.3

        System.out.println("double 类型的数值加 float 类型的数值");
        System.out.println(1.1d + 2.2f);// = 3.300000047683716
        System.out.println(NumberUtils.add(1.1d, 2.2f));// = 3.3

        System.out.println("两个 double 类型的数值相加");
        System.out.println(1.1d + 2.2d);// = 3.3000000000000003
        System.out.println(NumberUtils.add(1.1d, 2.2d));// = 3.3

        System.out.println("多个 Number 类型的数值相加");
        System.out.println(1.1d + 2.2f + 3.3d + 5.5f);// = 12.100000047683716
        System.out.println(NumberUtils.add(1.1d, 2.2f, 3.3d, 5.5f));// = 12.1

        System.out.println("多个 String 类型的数值相加");
        System.out.println(NumberUtils.add("1.1", "2.2", "3.3", "5.5"));// = 12.1

        System.out.println("多个 BigDecimal 类型的数值相加");
        System.out.println(NumberUtils.add(new BigDecimal("1.1"), new BigDecimal("2.2"), new BigDecimal("3.3"), new BigDecimal("5.5")));// = 12.1
    }

    @Test
    public void subtractTest() {
        System.out.println("两个 float 类型的数值相减");
        System.out.println(2.2f - 1.1f);// = 1.1
        System.out.println(NumberUtils.subtract(2.2f, 1.1f));// = 1.1

        System.out.println("float 类型的数值减 double 类型的数值");
        System.out.println(7.7f - 2.2d);// = 5.4999998092651365
        System.out.println(NumberUtils.subtract(7.7f, 2.2d));// = 5.5

        System.out.println("double 类型的数值减 float 类型的数值");
        System.out.println(7.7d - 2.2f);// = 5.499999952316284
        System.out.println(NumberUtils.subtract(7.7d, 2.2f));// = 5.5

        System.out.println("两个 double 类型的数值相减");
        System.out.println(7.7d - 2.2d);// = 5.5
        System.out.println(NumberUtils.subtract(7.7d, 2.2d));// = 5.5

        System.out.println("多个 Number 类型的数值相减");
        System.out.println(7.7d - 3.3f - 2.2d - 1.1f);// = 1.100000023841858
        System.out.println(NumberUtils.subtract(7.7d, 3.3f, 2.2d, 1.1f));// = 1.1

        System.out.println("多个 String 类型的数值相减");
        System.out.println(NumberUtils.subtract("7.7", "3.3", "2.2", "1.1"));// = 1.1

        System.out.println("多个 BigDecimal 类型的数值相减");
        System.out.println(NumberUtils.subtract(new BigDecimal("7.7"), new BigDecimal("3.3"), new BigDecimal("2.2"), new BigDecimal("1.1")));// = 1.1
    }

    @Test
    public void multiplyTest() {
        System.out.println("两个 float 类型的数值相乘");
        System.out.println(1.1f * 2.2f);// = 2.42
        System.out.println(NumberUtils.multiply(1.1f, 2.2f));// = 2.42

        System.out.println("float 类型的数值乘以 double 类型的数值");
        System.out.println(1.1f * 2.2d);// = 2.4200000524520875
        System.out.println(NumberUtils.multiply(1.1f, 2.2d));// = 2.42

        System.out.println("double 类型的数值乘以 float 类型的数值");
        System.out.println(1.1d * 2.2f);// = 2.4200000524520875
        System.out.println(NumberUtils.multiply(1.1d, 2.2f));// = 2.42

        System.out.println("两个 double 类型的数值相乘");
        System.out.println(1.1d * 2.2d);// = 2.4200000000000004
        System.out.println(NumberUtils.multiply(1.1d, 2.2d));// = 2.42

        System.out.println("多个 Number 类型的数值相乘");
        System.out.println(1.1d * 2.2f * 3.3d * 5.5f);// = 43.92300095200538
        System.out.println(NumberUtils.multiply(1.1d, 2.2f, 3.3d, 5.5f));// = 43.9230

        System.out.println("多个 String 类型的数值相乘");
        System.out.println(NumberUtils.multiply("1.1", "2.2", "3.3", "5.5"));// = 43.9230

        System.out.println("多个 BigDecimal 类型的数值相乘");
        System.out.println(NumberUtils.multiply(new BigDecimal("1.1"), new BigDecimal("2.2"), new BigDecimal("3.3"), new BigDecimal("5.5")));// = 43.9230
    }

    @Test
    public void divideTest() {
        System.out.println("两个 float 类型的数值相除");
        System.out.println(1.3f / 0.2f);// = 6.4999995
        System.out.println(NumberUtils.divide(1.3f, 0.2f));// = 6.5

        System.out.println("float 类型的数值除以 double 类型的数值");
        System.out.println(1.3f / 0.2d);// = 6.499999761581421
        System.out.println(NumberUtils.divide(1.3f, 0.2d));// = 6.5

        System.out.println("double 类型的数值除以 float 类型的数值");
        System.out.println(1.3d / 0.2f);// = 6.499999903142454
        System.out.println(NumberUtils.divide(1.3d, 0.2f));// = 6.5

        System.out.println("两个 double 类型的数值相除");
        System.out.println(1.3d / 0.2d);// = 6.5
        System.out.println(NumberUtils.divide(1.3d, 0.2d));// = 6.5

        System.out.println("两个 double 类型的数值相除，除不尽精确到小数点后10位");
        System.out.println(1.1d / 2.3d);// = 0.47826086956521746
        System.out.println(NumberUtils.divide(1.1d, 2.3d));// = 0.4782608696

        System.out.println("两个 Number 类型的数值相除，除不尽精确到小数点后10位");
        System.out.println((double) 1L / 3L);// = 0.3333333333333333
        System.out.println(NumberUtils.divide((Number) 1, 3));// = 0.3333333333

        System.out.println("两个 String 类型的数值相除，除不尽精确到小数点后10位");
        System.out.println(NumberUtils.divide("1", "3"));// = 0.3333333333

        System.out.println("两个 float 类型的数值相除，指定精确度");
        System.out.println(NumberUtils.divide(0.2f, 0.3f, 3));// = 0.667

        System.out.println("float 类型的数值除以 double 类型的数值，指定精确度");
        System.out.println(NumberUtils.divide(0.2f, 0.3d, 3));// = 0.667

        System.out.println("double 类型的数值除以 float 类型的数值，指定精确度");
        System.out.println(NumberUtils.divide(0.2d, 0.3f, 3));// = 0.667

        System.out.println("两个 double 类型的数值相除，指定精确度");
        System.out.println(NumberUtils.divide(0.2d, 0.3d, 3));// = 0.667

        System.out.println("两个 Number 类型的数值相除，指定精确度");
        System.out.println(NumberUtils.divide((Number) 2, 3, 3));// = 0.667

        System.out.println("两个 String 类型的数值相除，指定精确度");
        System.out.println(NumberUtils.divide("2", "3", 3));// = 0.667

        System.out.println("两个 float 类型的数值相除，指定精确度和舍入规则");
        System.out.println(NumberUtils.divide(0.2f, 0.3f, 3, RoundingMode.FLOOR));// = 0.666
        System.out.println(NumberUtils.divide(0.2f, 0.3f, 3, RoundingMode.UP));// = 0.667

        System.out.println("float 类型的数值除以 double 类型的数值，指定精确度和舍入规则");
        System.out.println(NumberUtils.divide(0.2f, 0.3d, 3, RoundingMode.FLOOR));// = 0.666
        System.out.println(NumberUtils.divide(0.2f, 0.3d, 3, RoundingMode.UP));// = 0.667

        System.out.println("double 类型的数值除以 float 类型的数值，指定精确度和舍入规则");
        System.out.println(NumberUtils.divide(0.2d, 0.3f, 3, RoundingMode.FLOOR));// = 0.666
        System.out.println(NumberUtils.divide(0.2d, 0.3f, 3, RoundingMode.UP));// = 0.667

        System.out.println("两个 double 类型的数值相除，指定精确度和舍入规则");
        System.out.println(NumberUtils.divide(0.2d, 0.3d, 3, RoundingMode.FLOOR));// = 0.666
        System.out.println(NumberUtils.divide(0.2d, 0.3d, 3, RoundingMode.UP));// = 0.667

        System.out.println("两个 Number 类型的数值相除，指定精确度和舍入规则");
        System.out.println(NumberUtils.divide((Number) 2, 3, 3, RoundingMode.FLOOR));// = 0.666
        System.out.println(NumberUtils.divide((Number) 2, 3, 3, RoundingMode.UP));// = 0.667

        System.out.println("两个 String 类型的数值相除，指定精确度和舍入规则");
        System.out.println(NumberUtils.divide("2", "3", 3, RoundingMode.FLOOR));// = 0.666
        System.out.println(NumberUtils.divide("2", "3", 3, RoundingMode.UP));// = 0.667

        System.out.println("两个 BigDecimal 类型的数值相除，指定精确度和舍入规则");
        System.out.println(NumberUtils.divide(new BigDecimal("2"), new BigDecimal("3"), 3, RoundingMode.FLOOR));// = 0.666
        System.out.println(NumberUtils.divide(new BigDecimal("2"), new BigDecimal("3"), 3, RoundingMode.UP));// = 0.667
    }

    @Test
    public void ceilDivTest() {
        System.out.println("================== ceilDiv(int v1, int v2) ==================");
        System.out.println(NumberUtils.ceilDiv(4, 5));// = 1
        System.out.println(NumberUtils.ceilDiv(5, 5));// = 1
        System.out.println(NumberUtils.ceilDiv(9, 5));// = 2
        System.out.println(NumberUtils.ceilDiv(10, 5));// = 2
        System.out.println(NumberUtils.ceilDiv(11, 5));// = 3

        System.out.println(NumberUtils.ceilDiv(-4, 5));// = 0
        System.out.println(NumberUtils.ceilDiv(-5, 5));// = -1
        System.out.println(NumberUtils.ceilDiv(-9, 5));// = -1
        System.out.println(NumberUtils.ceilDiv(-10, 5));// = -2
        System.out.println(NumberUtils.ceilDiv(-11, 5));// = -2

        System.out.println(NumberUtils.ceilDiv(-4, -5));// = 1
        System.out.println(NumberUtils.ceilDiv(-5, -5));// = 1
        System.out.println(NumberUtils.ceilDiv(-9, -5));// = 2
        System.out.println(NumberUtils.ceilDiv(-10, -5));// = 2
        System.out.println(NumberUtils.ceilDiv(-11, -5));// = 3

        System.out.println("================== ceilDiv(long v1, long v2) ==================");
        System.out.println(NumberUtils.ceilDiv(4L, 5L));// = 1
        System.out.println(NumberUtils.ceilDiv(5L, 5L));// = 1
        System.out.println(NumberUtils.ceilDiv(9L, 5L));// = 2
        System.out.println(NumberUtils.ceilDiv(10L, 5L));// = 2
        System.out.println(NumberUtils.ceilDiv(11L, 5L));// = 3

        System.out.println(NumberUtils.ceilDiv(-4L, 5L));// = 0
        System.out.println(NumberUtils.ceilDiv(-5L, 5L));// = -1
        System.out.println(NumberUtils.ceilDiv(-9L, 5L));// = -1
        System.out.println(NumberUtils.ceilDiv(-10L, 5L));// = -2
        System.out.println(NumberUtils.ceilDiv(-11L, 5L));// = -2

        System.out.println(NumberUtils.ceilDiv(-4L, -5L));// = 1
        System.out.println(NumberUtils.ceilDiv(-5L, -5L));// = 1
        System.out.println(NumberUtils.ceilDiv(-9L, -5L));// = 2
        System.out.println(NumberUtils.ceilDiv(-10L, -5L));// = 2
        System.out.println(NumberUtils.ceilDiv(-11L, -5L));// = 3
    }

    @Test
    public void roundTest() {
        System.out.println("================== round(double v, int scale) ==================");
        System.out.println(NumberUtils.round(123.456789, 2));// = 123.46
        System.out.println(NumberUtils.round(987.654321, 2));// = 987.65
        System.out.println(NumberUtils.round(-123.456789, 2));// = -123.46
        System.out.println(NumberUtils.round(-987.654321, 2));// = -987.65

        System.out.println("================== round(String numberStr, int scale) ==================");
        System.out.println(NumberUtils.round("123.456789", 2));// = 123.46
        System.out.println(NumberUtils.round("987.654321", 2));// = 987.65
        System.out.println(NumberUtils.round("-123.456789", 2));// = -123.46
        System.out.println(NumberUtils.round("-987.654321", 2));// = -987.65

        System.out.println("================== round(BigDecimal number, int scale) ==================");
        System.out.println(NumberUtils.round(new BigDecimal("123.456789"), 2));// = 123.46
        System.out.println(NumberUtils.round(new BigDecimal("987.654321"), 2));// = 987.65
        System.out.println(NumberUtils.round(new BigDecimal("-123.456789"), 2));// = -123.46
        System.out.println(NumberUtils.round(new BigDecimal("-987.654321"), 2));// = -987.65

        System.out.println("================== round(double v, int scale, RoundingMode roundingMode) ==================");
        System.out.println(NumberUtils.round(123.456789, 2, RoundingMode.DOWN));// = 123.45
        System.out.println(NumberUtils.round(987.654321, 2, RoundingMode.UP));// = 987.66
        System.out.println(NumberUtils.round(-123.456789, 2, RoundingMode.CEILING));// = -123.45
        System.out.println(NumberUtils.round(-987.654321, 2, RoundingMode.FLOOR));// = -987.66

        System.out.println("================== round(String numberStr, int scale, RoundingMode roundingMode) ==================");
        System.out.println(NumberUtils.round("123.456789", 2, RoundingMode.DOWN));// = 123.45
        System.out.println(NumberUtils.round("987.654321", 2, RoundingMode.UP));// = 987.66
        System.out.println(NumberUtils.round("-123.456789", 2, RoundingMode.CEILING));// = -123.45
        System.out.println(NumberUtils.round("-987.654321", 2, RoundingMode.FLOOR));// = -987.66

        System.out.println("================== round(BigDecimal number, int scale, RoundingMode roundingMode) ==================");
        System.out.println(NumberUtils.round(new BigDecimal("123.456789"), 2, RoundingMode.DOWN));// = 123.45
        System.out.println(NumberUtils.round(new BigDecimal("987.654321"), 2, RoundingMode.UP));// = 987.66
        System.out.println(NumberUtils.round(new BigDecimal("-123.456789"), 2, RoundingMode.CEILING));// = -123.45
        System.out.println(NumberUtils.round(new BigDecimal("-987.654321"), 2, RoundingMode.FLOOR));// = -987.66
    }

    @Test
    public void roundHalfEvenTest() {
        System.out.println("================== roundHalfEven(Number number, int scale) ==================");
        System.out.println(NumberUtils.roundHalfEven(123.456789, 2));// = 123.46
        System.out.println(NumberUtils.roundHalfEven(123.455189, 2));// = 123.46
        System.out.println(NumberUtils.roundHalfEven(123.455889, 2));// = 123.46
        System.out.println(NumberUtils.roundHalfEven(123.455, 2));// = 123.46
        System.out.println(NumberUtils.roundHalfEven(123.445, 2));// = 123.44
        System.out.println(NumberUtils.roundHalfEven(123.454789, 2));// = 123.45

        System.out.println("================== roundHalfEven(BigDecimal value, int scale) ==================");
        System.out.println(NumberUtils.roundHalfEven(new BigDecimal("123.456789"), 2));// = 123.46
        System.out.println(NumberUtils.roundHalfEven(new BigDecimal("123.455189"), 2));// = 123.46
        System.out.println(NumberUtils.roundHalfEven(new BigDecimal("123.455889"), 2));// = 123.46
        System.out.println(NumberUtils.roundHalfEven(new BigDecimal("123.455"), 2));// = 123.46
        System.out.println(NumberUtils.roundHalfEven(new BigDecimal("123.445"), 2));// = 123.44
        System.out.println(NumberUtils.roundHalfEven(new BigDecimal("123.454789"), 2));// = 123.45
    }

    @Test
    public void decimalFormatTest() {
        System.out.println("================== decimalFormat(String pattern, double value) ==================");
        System.out.println(NumberUtils.decimalFormat("0", 123456789.987654321));// = 123456790
        System.out.println(NumberUtils.decimalFormat("0.00", 123456789.987654321));// = 123456789.99
        System.out.println(NumberUtils.decimalFormat("0.00000", 123456789.987654321));// = 123456789.98765
        System.out.println(NumberUtils.decimalFormat("0.00", 123.1));// = 123.10

        System.out.println(NumberUtils.decimalFormat("#", 123456789.987654321));// = 123456790
        System.out.println(NumberUtils.decimalFormat("#.##", 123456789.987654321));// = 123456789.99
        System.out.println(NumberUtils.decimalFormat("#.##%", 123456789.987654321));// = 12345678998.77%
        System.out.println(NumberUtils.decimalFormat("#.#####E0", 123456789.987654321));// = 1.23457E8
        System.out.println(NumberUtils.decimalFormat("###.##", 1.1));// = 1.1

        System.out.println("================== decimalFormat(String pattern, long value) ==================");
        System.out.println(NumberUtils.decimalFormat("0", 123456789L));// = 123456789
        System.out.println(NumberUtils.decimalFormat("0.00", 123456789L));// = 123456789.00
        System.out.println(NumberUtils.decimalFormat("0.00000", 123456789L));// = 123456789.00000
        System.out.println(NumberUtils.decimalFormat("0.00", 123L));// = 123.00

        System.out.println(NumberUtils.decimalFormat("#", 123456789L));// = 123456789
        System.out.println(NumberUtils.decimalFormat("#.##", 123456789L));// = 123456789
        System.out.println(NumberUtils.decimalFormat("#.##%", 123456789L));// = 12345678900%
        System.out.println(NumberUtils.decimalFormat("#.#####E0", 123456789L));// = 1.23457E8
        System.out.println(NumberUtils.decimalFormat("###.##", 1L));// = 1

        System.out.println("================== decimalFormat(String pattern, Object value) ==================");
        System.out.println(NumberUtils.decimalFormat("0", new BigDecimal("123456789.987654321")));// = 123456790
        System.out.println(NumberUtils.decimalFormat("0.00", new BigDecimal("123456789.987654321")));// = 123456789.99
        System.out.println(NumberUtils.decimalFormat("0.00000", new BigDecimal("123456789.987654321")));// = 123456789.98765
        System.out.println(NumberUtils.decimalFormat("0.00", new BigDecimal("123.1")));// = 123.10

        System.out.println(NumberUtils.decimalFormat("#", new BigDecimal("123456789.987654321")));// = 123456790
        System.out.println(NumberUtils.decimalFormat("#.##", new BigDecimal("123456789.987654321")));// = 123456789.99
        System.out.println(NumberUtils.decimalFormat("#.##%", new BigDecimal("123456789.987654321")));// = 12345678998.77%
        System.out.println(NumberUtils.decimalFormat("#.#####E0", new BigDecimal("123456789.987654321")));// = 1.23457E8
        System.out.println(NumberUtils.decimalFormat("###.##", new BigDecimal("1.1")));// = 1.1
    }

    @Test
    public void decimalFormatMoneyTest() {
        System.out.println(NumberUtils.decimalFormatMoney(0));// = 0.00
        System.out.println(NumberUtils.decimalFormatMoney(1));// = 1.00
        System.out.println(NumberUtils.decimalFormatMoney(1.123456));// = 1.12
        System.out.println(NumberUtils.decimalFormatMoney(1.125456));// = 1.13
        System.out.println(NumberUtils.decimalFormatMoney(123456.123456));// = 1.12
        System.out.println(NumberUtils.decimalFormatMoney(123456789.123456));// = 1.12
    }

    @Test
    public void formatPercentTest() {
        System.out.println(NumberUtils.formatPercent(0, 2));// = 0%
        System.out.println(NumberUtils.formatPercent(1, 2));// = 100%
        System.out.println(NumberUtils.formatPercent(1.123, 2));// = 112.3%
        System.out.println(NumberUtils.formatPercent(1.123456, 2));// = 112.35%
        System.out.println(NumberUtils.formatPercent(0.123456, 2));// = 12.35%
    }

    @Test
    public void isPrimesTest() {
        System.out.println(NumberUtils.isPrimes(-1));// false
        System.out.println(NumberUtils.isPrimes(0));// false
        System.out.println(NumberUtils.isPrimes(1));// false
        System.out.println(NumberUtils.isPrimes(2));// true
        System.out.println(NumberUtils.isPrimes(3));// true
        System.out.println(NumberUtils.isPrimes(4));// false
        System.out.println(NumberUtils.isPrimes(5));// true
    }

    @Test
    public void factorialTest() {
        System.out.println(NumberUtils.factorial(5, 1));// 120
        System.out.println(NumberUtils.factorial(0, 1));// 1
        System.out.println(NumberUtils.factorial(5));// 120
        System.out.println(NumberUtils.factorial(0));// 1
    }

    @Test
    public void permutationTest() {
        System.out.println(NumberUtils.permutation(5, 1));// 5
        System.out.println(NumberUtils.permutation(5, 2));// 20
    }

    @Test
    public void combinationTest() {
        System.out.println(NumberUtils.combination(5, 1));// 5
        System.out.println(NumberUtils.combination(5, 2));// 10
    }

    @Test
    public void divisorTest() {
        System.out.println(NumberUtils.divisor(10, 5));// 5
        System.out.println(NumberUtils.divisor(5, 2));// 1
        System.out.println(NumberUtils.divisor(15, 10));// 5
    }

    @Test
    public void multipleTest() {
        System.out.println(NumberUtils.multiple(10, 5));// 10
        System.out.println(NumberUtils.multiple(5, 2));// 10
        System.out.println(NumberUtils.multiple(15, 10));// 30
    }

    @Test
    public void toBinaryStringTest() {
        System.out.println(NumberUtils.toBinaryString(10L));// 1010
        System.out.println(NumberUtils.toBinaryString(8));// 1000
        System.out.println(NumberUtils.toBinaryString(10.5));// 1010
    }

    @Test
    public void binaryToIntTest() {
        System.out.println(NumberUtils.binaryToInt("1010"));// 10
        System.out.println(NumberUtils.binaryToInt("1000"));// 8
        System.out.println(NumberUtils.binaryToInt("1010"));// 10
        System.out.println(NumberUtils.binaryToInt("101010101010"));// 2730
    }

    @Test
    public void binaryToLongTest() {
        System.out.println(NumberUtils.binaryToLong("1010"));// 10
        System.out.println(NumberUtils.binaryToLong("1000"));// 8
        System.out.println(NumberUtils.binaryToLong("1010"));// 10
        System.out.println(NumberUtils.binaryToLong("101010101010"));// 2730
    }

    @Test
    public void toBigDecimalTest() {
        System.out.println(NumberUtils.toBigDecimal(123));// 123
        System.out.println(NumberUtils.toBigDecimal(123F));// 123.0
        System.out.println(NumberUtils.toBigDecimal(123D));// 123.0
        System.out.println(NumberUtils.toBigDecimal(123L));// 123
        System.out.println(NumberUtils.toBigDecimal("123"));// 123
    }

    @Test
    public void countTest() {
        System.out.println(NumberUtils.count(120, 10));// 12
        System.out.println(NumberUtils.count(123, 10));// 13
        System.out.println(NumberUtils.count(0, 10));// 0
        System.out.println(NumberUtils.count(123, 456));// 1
    }

    @Test
    public void isBesideTest() {
        System.out.println(NumberUtils.isBeside(1L, 0L));// true
        System.out.println(NumberUtils.isBeside(-1L, 0L));// true
        System.out.println(NumberUtils.isBeside(-1L, 1L));// false
        System.out.println(NumberUtils.isBeside(1L, 1L));// false

        System.out.println(NumberUtils.isBeside(1, 0));// true
        System.out.println(NumberUtils.isBeside(-1, 0));// true
        System.out.println(NumberUtils.isBeside(-1, 1));// false
        System.out.println(NumberUtils.isBeside(1, 1));// false
    }

    @Test
    public void partValueTest() {
        System.out.println(NumberUtils.partValue(100, 10));// 10
        System.out.println(NumberUtils.partValue(100, 15));// 7
        System.out.println(NumberUtils.partValue(100, 30));// 4
    }

    @Test
    public void powTest() {
        System.out.println(NumberUtils.pow(2, 10));// 1024
        System.out.println(NumberUtils.pow(2L, 10));// 1024
        System.out.println(NumberUtils.pow(2.0f, 10));// 1024.0000000000
        System.out.println(NumberUtils.pow(2.0d, 10));// 1024.0000000000
        System.out.println(NumberUtils.pow(1.1d, 2));// 1.21
        System.out.println(NumberUtils.pow(new BigDecimal("1.1"), 2));// 1.21
    }

    @Test
    public void isZeroTest() {
        System.out.println(NumberUtils.isZero("0"));// true
        System.out.println(NumberUtils.isZero("+0"));// true
        System.out.println(NumberUtils.isZero("0.0"));// true
        System.out.println(NumberUtils.isZero("-0.0"));// true
        System.out.println(NumberUtils.isZero("0.00"));// true
        System.out.println(NumberUtils.isZero("00.00"));// true
        System.out.println(NumberUtils.isZero("0000"));// true
        System.out.println(NumberUtils.isZero("1"));// false
        System.out.println(NumberUtils.isZero("00001"));// false
        System.out.println(NumberUtils.isZero("0.1"));// false
        System.out.println(NumberUtils.isZero("1.1"));// false
        System.out.println(NumberUtils.isZero("0."));// true
        System.out.println(NumberUtils.isZero(".0"));// true
        System.out.println(NumberUtils.isZero("000100"));// false
    }
}
