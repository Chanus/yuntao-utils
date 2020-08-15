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

import java.awt.*;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机数工具类
 *
 * @author Chanus
 * @date 2020-06-24 08:55:29
 * @since 1.0.0
 */
public class RandomUtils {
    /**
     * 数字和大小写字母
     */
    private static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 大小写字母
     */
    private static final String LETTERCHAR = "abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 数字
     */
    private static final String NUMBERCHAR = "0123456789";

    /**
     * 随机获取 {@code int} 类型数字
     *
     * @return {@code int} 类型数字
     */
    public static int getRandomInt() {
        return ThreadLocalRandom.current().nextInt();
    }

    /**
     * 随机获取 0 到 {@code bound} 之间的 {@code int} 类型数字
     *
     * @param bound 最大值，必须为正数
     * @return 0 到 {@code bound} 之间的 {@code int} 类型数字
     */
    public static int getRandomInt(int bound) {
        return ThreadLocalRandom.current().nextInt(bound);
    }

    /**
     * 随机获取 {@code origin} 到 {@code bound} 之间的 {@code int} 类型数字
     *
     * @param origin 最小值
     * @param bound  最大值
     * @return {@code origin} 到 {@code bound} 之间的 {@code int} 类型数字
     */
    public static int getRandomInt(int origin, int bound) {
        return ThreadLocalRandom.current().nextInt(origin, bound);
    }

    /**
     * 获取固定长度的随机数字字符串
     *
     * @param length 数字字符串长度
     * @return {@code length} 位数字字符串
     */
    public static String getRandomDigits(int length) {
        StringBuilder result = new StringBuilder();
        int bound = NUMBERCHAR.length();
        for (int i = 0; i < length; i++) {
            result.append(NUMBERCHAR.charAt(ThreadLocalRandom.current().nextInt(bound)));
        }
        return result.toString();
    }

    /**
     * 随机获取 {@code long} 类型数字
     *
     * @return {@code long} 类型数字
     */
    public static long getRandomLong() {
        return ThreadLocalRandom.current().nextLong();
    }

    /**
     * 随机获取 0 到 {@code bound} 之间的 {@code long} 类型数字
     *
     * @param bound 最大值，必须为正数
     * @return 0 到 {@code bound} 之间的 {@code long} 类型数字
     */
    public static long getRandomLong(long bound) {
        return ThreadLocalRandom.current().nextLong(bound);
    }

    /**
     * 随机获取 {@code origin} 到 {@code bound} 之间的 {@code long} 类型数字
     *
     * @param origin 最小值
     * @param bound  最大值
     * @return {@code origin} 到 {@code bound} 之间的 {@code long} 类型数字
     */
    public static long getRandomLong(long origin, long bound) {
        return ThreadLocalRandom.current().nextLong(origin, bound);
    }

    /**
     * 随机获取 {@code double} 类型数字
     *
     * @return {@code double} 类型数字
     */
    public static double getRandomDouble() {
        return ThreadLocalRandom.current().nextDouble();
    }

    /**
     * 随机获取 0 到 {@code bound} 之间的 {@code double} 类型数字
     *
     * @param bound 最大值，必须为正数
     * @return 0 到 {@code bound} 之间的 {@code double} 类型数字
     */
    public static double getRandomDouble(double bound) {
        return ThreadLocalRandom.current().nextDouble(bound);
    }

    /**
     * 随机获取 {@code origin} 到 {@code bound} 之间的 {@code double} 类型数字
     *
     * @param origin 最小值
     * @param bound  最大值
     * @return {@code origin} 到 {@code bound} 之间的 {@code double} 类型数字
     */
    public static double getRandomDouble(double origin, double bound) {
        return ThreadLocalRandom.current().nextDouble(origin, bound);
    }

    /**
     * 随机获取 ASCII 码33到126之间字符
     *
     * @return ASCII 码33到126之间的字符
     */
    public static char getRandomChar() {
        return (char) ThreadLocalRandom.current().nextInt(33, 126);
    }

    /**
     * 随机获取 0-9，a-z，A-Z 之间的字符
     *
     * @return 0-9，a-z，A-Z 之间的字符
     */
    public static char getRandomNormalChar() {
        return ALLCHAR.charAt(ThreadLocalRandom.current().nextInt(ALLCHAR.length()));
    }

    /**
     * 随机获取 a-z，A-Z 之间的字符
     *
     * @return a-z，A-Z 之间的字符
     */
    public static char getRandomLetterChar() {
        return LETTERCHAR.charAt(ThreadLocalRandom.current().nextInt(LETTERCHAR.length()));
    }

    /**
     * 随机获取由 ASCII 码33到126之间的字符组成的字符串
     *
     * @param length 字符串长度
     * @return ASCII 码33到126之间的字符组成的字符串
     */
    public static String getRandomString(int length) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(getRandomChar());
        }
        return result.toString();
    }

    /**
     * 随机获取由 0-9，a-z，A-Z 之间的字符组成的字符串
     *
     * @param length 字符串长度
     * @return 0-9，a-z，A-Z 之间的字符组成的字符串
     */
    public static String getRandomNormalString(int length) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(getRandomNormalChar());
        }
        return result.toString();
    }

    /**
     * 随机获取由 a-z，A-Z 之间的字符组成的字符串
     *
     * @param length 字符串长度
     * @return a-z，A-Z 之间的字符组成的字符串
     */
    public static String getRandomLetterString(int length) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(getRandomLetterChar());
        }
        return result.toString();
    }

    /**
     * 获取由时间戳和 {@code digitCount} 位随机数字组成的纯数字字符串
     *
     * @param digitCount 随机数字个数
     * @return 时间戳和 {@code digitCount} 位随机数字组成的纯数字字符串
     * @since 1.2.2
     */
    public static String getRandomUniqueNo(int digitCount) {
        return System.currentTimeMillis() + getRandomDigits(digitCount);
    }

    /**
     * 获取由时间戳和5位随机数字组成的纯数字字符串
     *
     * @return 18位纯数字字符串
     */
    public static String getRandomUniqueNo() {
        return getRandomUniqueNo(5);
    }

    /**
     * 获取无连接符"-"的小写 UUID
     *
     * @return 无连接符"-"的小写 UUID
     */
    public static String getLowercaseUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取无连接符"-"的大写 UUID
     *
     * @return 无连接符"-"的大写 UUID
     */
    public static String getUppercaseUUID() {
        return getLowercaseUUID().toUpperCase();
    }

    /**
     * 生成随机颜色
     *
     * @return 随机颜色
     */
    public static Color getRandomColor() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }
}
