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
import org.junit.Test;

import java.util.Arrays;

/**
 * ArrayUtils 测试类
 *
 * @author Chanus
 * @date 2020-07-27 15:57:10
 * @since 1.1.0
 */
public class ArrayUtilsTest {
    @Test
    public void isEmptyTest() {
        String[] array1 = new String[2];
        String[] array2 = {};
        String[] array3 = {"1", "2", "3"};
        int[] ints = {1, 2, 3};
        long[] longs = {};
        String s = "";
        System.out.println(ArrayUtils.isEmpty(array1));// false
        System.out.println(ArrayUtils.isEmpty(array2));// true
        System.out.println(ArrayUtils.isEmpty(array3));// false
        System.out.println(ArrayUtils.isEmpty(ints));// false
        System.out.println(ArrayUtils.isEmpty(longs));// true
        System.out.println(ArrayUtils.isEmpty(s));// false
    }

    @Test
    public void isNotEmptyTest() {
        String[] array1 = new String[2];
        String[] array2 = {};
        String[] array3 = {"1", "2", "3"};
        int[] ints = {1, 2, 3};
        long[] longs = {};
        String s = "";
        System.out.println(ArrayUtils.isNotEmpty(array1));// true
        System.out.println(ArrayUtils.isNotEmpty(array2));// false
        System.out.println(ArrayUtils.isNotEmpty(array3));// true
        System.out.println(ArrayUtils.isNotEmpty(ints));// true
        System.out.println(ArrayUtils.isNotEmpty(longs));// false
        System.out.println(ArrayUtils.isNotEmpty(s));// true
    }

    @Test
    public void defaultIfEmptyTest() {
        String[] array1 = new String[2];
        String[] array2 = {};
        String[] array3 = {"1", "2", "3"};
        int[] ints = {1, 2};
        long[] longs = {};
        String s = "123";
        System.out.println(Arrays.toString(ArrayUtils.defaultIfEmpty(array1, new String[]{"1", "2", "3"})));// [null, null]
        System.out.println(Arrays.toString(ArrayUtils.defaultIfEmpty(array2, new String[]{"1", "2", "3"})));// [1, 2, 3]
        System.out.println(Arrays.toString(ArrayUtils.defaultIfEmpty(array3, new String[]{"1", "2", "3"})));// [1, 2, 3]
        System.out.println(ArrayUtils.toString(ArrayUtils.defaultIfEmpty(ints, new String[]{"1", "2", "3"})));// [1, 2]
        System.out.println(ArrayUtils.toString(ArrayUtils.defaultIfEmpty(longs, new String[]{"1", "2", "3"})));// [1, 2, 3]
        System.out.println(ArrayUtils.toString(ArrayUtils.defaultIfEmpty(s, new String[]{"1", "2", "3"})));// 123
    }

    @Test
    public void isArrayTest() {
        String[] array1 = new String[2];
        String[] array2 = {};
        String[] array3 = {"1", "2", "3"};
        String s = "1,2,3";
        int[] ints = {1, 2, 3};
        System.out.println(ArrayUtils.isArray(array1));// true
        System.out.println(ArrayUtils.isArray(array2));// true
        System.out.println(ArrayUtils.isArray(array3));// true
        System.out.println(ArrayUtils.isArray(s));// false
        System.out.println(ArrayUtils.isArray(ints));// true
    }

    @Test
    public void getTest() {
        String[] array1 = new String[2];
        String[] array2 = {};
        String[] array3 = {"1", "2", "3"};
        int[] ints = {1, 2, 3};
        long[] longs = {};
        System.out.println((String) ArrayUtils.get(array1, 1));// null
        System.out.println((String) ArrayUtils.get(array2, 1));// null
        System.out.println((String) ArrayUtils.get(array3, 1));// 2
        System.out.println((int) ArrayUtils.get(ints, 1));// 2
        System.out.println((int) ArrayUtils.get(ints, -1));// 3
        System.out.println((Long) ArrayUtils.get(longs, 1));// null
    }

    @Test
    public void indexOfTest() {
        String[] array = {"1", "2", "3", "4", "5", "6", "7", null};
        System.out.println(ArrayUtils.indexOf(array, 1));// -1
        System.out.println(ArrayUtils.indexOf(array, "1"));// 0
        System.out.println(ArrayUtils.indexOf(null, "1"));// -1
        System.out.println(ArrayUtils.indexOf(array, null));// 7
    }

    @Test
    public void appendTest() {
        String[] array1 = {"1", "2", "3"};
        String[] array2 = ArrayUtils.append(array1, "4", "5", "6");
        System.out.println(ArrayUtils.toString(array1));// [1, 2, 3]
        System.out.println(ArrayUtils.toString(array2));// [1, 2, 3, 4, 5, 6]
    }

    @Test
    public void insertTest() {
        String[] array1 = {"1", "2", "3"};
        String[] array2 = ArrayUtils.insert(array1, 1, "4", "5", "6");
        String[] array3 = ArrayUtils.insert(array1, 5, "4", "5", "6");
        String[] array4 = ArrayUtils.insert(array1, -1, "4", "5", "6");
        System.out.println(ArrayUtils.toString(array1));// [1, 2, 3]
        System.out.println(ArrayUtils.toString(array2));// [1, 4, 5, 6, 2, 3]
        System.out.println(ArrayUtils.toString(array3));// [1, 2, 3, null, null, 4, 5, 6]
        System.out.println(ArrayUtils.toString(array4));// [1, 2, 4, 5, 6, 3]
    }

    @Test
    public void removeTest() {
        String[] array1 = {"1", "2", "3", "4", "5", "6", "7"};
        int[] ints1 = {1, 2, 3, 4, 5, 6, 7};
        String[] array2 = ArrayUtils.remove(array1, 3);
        System.out.println(ArrayUtils.toString(array1));// [1, 2, 3, 4, 5, 6, 7]
        System.out.println(ArrayUtils.toString(array2));// [1, 2, 3, 5, 6, 7]

        int[] ints2 = (int[]) ArrayUtils.remove(ints1, 5);
        System.out.println(ArrayUtils.toString(ints1));// [1, 2, 3, 4, 5, 6, 7]
        System.out.println(ArrayUtils.toString(ints2));// [1, 2, 3, 4, 5, 7]
    }

    @Test
    public void containsTest() {
        String[] array1 = {"1", "2", "3"};
        System.out.println(ArrayUtils.contains(array1, "0"));// false
        System.out.println(ArrayUtils.contains(array1, "1"));// true
        System.out.println(ArrayUtils.contains(array1, ""));// false
        System.out.println(ArrayUtils.contains(array1, null));// false
    }

    @Test
    public void swapTest() {
        String[] array1 = {"1", "2", "3", "4", "5", "6", "7"};
        int[] ints1 = {1, 2, 3, 4, 5, 6, 7};
        String[] array2 = ArrayUtils.swap(array1, 2, 5);
        System.out.println(ArrayUtils.toString(array1));// [1, 2, 6, 4, 5, 3, 7]
        System.out.println(ArrayUtils.toString(array2));// [1, 2, 6, 4, 5, 3, 7]
        int[] ints2 = (int[]) ArrayUtils.swap(ints1, 2, 5);
        System.out.println(ArrayUtils.toString(ints1));// [1, 2, 6, 4, 5, 3, 7]
        System.out.println(ArrayUtils.toString(ints2));// [1, 2, 6, 4, 5, 3, 7]
    }

    @Test
    public void reverseTest() {
        String[] array1 = {"1", "2", "3", "4", "5", "6", "7"};
        String[] array2 = {"1", "2", "3", "4", "5", "6", "7"};
        String[] array3 = ArrayUtils.reverse(array1, 2, 5);
        System.out.println(ArrayUtils.toString(array1));// [1, 2, 5, 4, 3, 6, 7]
        System.out.println(ArrayUtils.toString(array3));// [1, 2, 5, 4, 3, 6, 7]
        String[] array4 = ArrayUtils.reverse(array2);
        System.out.println(ArrayUtils.toString(array2));// [7, 6, 5, 4, 3, 2, 1]
        System.out.println(ArrayUtils.toString(array4));// [7, 6, 5, 4, 3, 2, 1]
    }

    @Test
    public void distinctTest() {
        String[] array = {"6", "1", "2", "3", "3", "4", "4", "5", "1", "2", "3"};
        String[] array2 = ArrayUtils.distinct(array);
        System.out.println(ArrayUtils.toString(array2));// [6, 1, 2, 3, 4, 5]
    }

    @Test
    public void toStringTest() {
        String[] array1 = {"1", "2", "3", "4", "5"};
        int[] array2 = {1, 2, 3, 4, 5};
        System.out.println(ArrayUtils.toString(array1));// [1, 2, 3, 4, 5]
        System.out.println(ArrayUtils.toString(array2));// [1, 2, 3, 4, 5]
    }
}
