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
package com.chanus.yuntao.utils.core.test.encrypt;

import com.chanus.yuntao.utils.core.encrypt.MD5Utils;
import org.junit.Test;

/**
 * MD5Utils 测试类
 *
 * @author Chanus
 * @since 1.0.0
 */
public class MD5UtilsTest {
    @Test
    public void md5Test() {
        String text1 = "hello";
        String text2 = "Chanus";
        String text3 = "Hello, Chanus!";
        String text4 = "你好，Chanus！";
        String key = "yuntao";

        String cipher1 = MD5Utils.md5(text1);
        String cipher2 = MD5Utils.md5(text2);
        String cipher3 = MD5Utils.md5(text3);
        String cipher4 = MD5Utils.md5(text4);

        System.out.println("MD5加密text1：" + cipher1);
        System.out.println("MD5加密text2：" + cipher2);
        System.out.println("MD5加密text3：" + cipher3);
        System.out.println("MD5加密text4：" + cipher4);

        String cipher5 = MD5Utils.md5(text1, key);
        String cipher6 = MD5Utils.md5(text2, key);
        String cipher7 = MD5Utils.md5(text3, key);
        String cipher8 = MD5Utils.md5(text4, key);

        System.out.println("MD5根据key加密text1：" + cipher5);
        System.out.println("MD5根据key加密text2：" + cipher6);
        System.out.println("MD5根据key加密text3：" + cipher7);
        System.out.println("MD5根据key加密text4：" + cipher8);

        System.out.println("MD5加密text1校验：" + MD5Utils.verify(text1, cipher1));
        System.out.println("MD5加密text2校验：" + MD5Utils.verify(text2, cipher2));
        System.out.println("MD5加密text3校验：" + MD5Utils.verify(text3, cipher3));
        System.out.println("MD5加密text4校验：" + MD5Utils.verify(text4, cipher4));

        System.out.println("MD5根据key加密text1校验：" + MD5Utils.verify(text1, cipher5, key));
        System.out.println("MD5根据key加密text2校验：" + MD5Utils.verify(text2, cipher6, key));
        System.out.println("MD5根据key加密text3校验：" + MD5Utils.verify(text3, cipher7, key));
        System.out.println("MD5根据key加密text4校验：" + MD5Utils.verify(text4, cipher8, key));
    }
}
