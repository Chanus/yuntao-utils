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

import com.chanus.yuntao.utils.core.RandomUtils;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * RandomUtils 测试类
 *
 * @author Chanus
 * @date 2020-06-24 14:03:49
 * @since 1.0.0
 */
public class RandomUtilsTest {
    @Test
    public void getRandomIntTest() {
        System.out.println("随机 int 数值：" + RandomUtils.getRandomInt());
        System.out.println("0-100之间的随机 int 数值：" + RandomUtils.getRandomInt(100));
        System.out.println("100-1000之间的随机 int 数值：" + RandomUtils.getRandomInt(100, 1000));
    }

    @Test
    public void getRandomDigitsTest() throws InterruptedException {
        int length = 10;

        ExecutorService executorService = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                for (int j = 0; j < 10; j++) {
                    System.out.println("随机数字字符串：" + RandomUtils.getRandomDigits(length));
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);
    }

    @Test
    public void getRandomLongTest() {
        System.out.println("随机 long 数值：" + RandomUtils.getRandomLong());
        System.out.println("0-100之间的随机 long 数值：" + RandomUtils.getRandomLong(100L));
        System.out.println("100-1000之间的随机 long 数值：" + RandomUtils.getRandomLong(100L, 1000L));
    }

    @Test
    public void getRandomDoubleTest() {
        System.out.println("随机 double 数值：" + RandomUtils.getRandomDouble());
        System.out.println("0-100之间的随机 double 数值：" + RandomUtils.getRandomDouble(100.0D));
        System.out.println("100-1000之间的随机 double 数值：" + RandomUtils.getRandomDouble(100.0D, 1000.0D));
    }

    @Test
    public void getRandomCharTest() {
        System.out.println(RandomUtils.getRandomChar());
    }

    @Test
    public void getRandomNormalCharTest() {
        System.out.println(RandomUtils.getRandomNormalChar());
    }

    @Test
    public void getRandomLetterCharTest() {
        System.out.println(RandomUtils.getRandomLetterChar());
    }

    @Test
    public void getRandomStringTest() {
        System.out.println(RandomUtils.getRandomString(10));
    }

    @Test
    public void getRandomNormalStringTest() {
        System.out.println(RandomUtils.getRandomNormalString(10));
    }

    @Test
    public void getRandomLetterStringTest() {
        System.out.println(RandomUtils.getRandomLetterString(10));
    }

    @Test
    public void getRandomUniqueNoTest() {
        System.out.println(RandomUtils.getRandomUniqueNo());
    }

    @Test
    public void getLowercaseUUIDTest() {
        System.out.println(RandomUtils.getLowercaseUUID());
    }

    @Test
    public void getUppercaseUUIDTest() {
        System.out.println(RandomUtils.getUppercaseUUID());
    }

    @Test
    public void getRandomColorTest() {
        System.out.println(RandomUtils.getRandomColor());
    }
}
