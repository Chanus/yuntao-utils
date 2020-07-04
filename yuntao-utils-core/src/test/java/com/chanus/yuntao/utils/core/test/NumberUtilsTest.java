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
        System.out.println(1.1f / 2.2f);// = 0.5
        System.out.println(NumberUtils.divide(1.1f, 2.2f));// = 0.5

        System.out.println("float 类型的数值除以 double 类型的数值");
        System.out.println(1.1f / 2.2d);// = 0.5000000108372081
        System.out.println(NumberUtils.divide(1.1f, 2.2d));// = 0.5

        System.out.println("double 类型的数值除以 float 类型的数值");
        System.out.println(1.1d / 2.2f);// = 0.49999998916279215
        System.out.println(NumberUtils.divide(1.1d, 2.2f));// = 0.5

        System.out.println("两个 double 类型的数值相除");
        System.out.println(1.1d / 2.2d);// = 0.5
        System.out.println(NumberUtils.divide(1.1d, 2.2d));// = 0.5

        System.out.println("两个 double 类型的数值相除，除不尽精确到小数点后10位");
        System.out.println(1.1d / 2.3d);// = 0.47826086956521746
        System.out.println(NumberUtils.divide(1.1d, 2.3d));// = 0.4782608696

        System.out.println("两个 Number 类型的数值相除，除不尽精确到小数点后10位");
        System.out.println(1L / 3L);// = 0.47826086956521746
        System.out.println(NumberUtils.divide((Number) 1, 3));// = 0.4782608696
    }
}
