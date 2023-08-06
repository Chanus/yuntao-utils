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

import com.chanus.yuntao.utils.core.encrypt.DESedeUtils;
import org.junit.Test;

import java.io.File;

/**
 * DESedeUtils 测试类
 *
 * @author Chanus
 * @since 1.4.3
 */
public class DESedeUtilsTest {
    private final String key = "abcdefghijklmnopqrstuvwx";
    private final String iv = "11111111";

    @Test
    public void encryptTest() {
        String data = "DES 加密测试";
        System.out.println(DESedeUtils.encrypt(data, key, iv));// caouHkEwv5ub4ZoMBGgDeLjFPL4WTmQJ
        System.out.println(DESedeUtils.encrypt(data, key));// 883A/Su7FGmQD6UCQW0nmuWTx27CGukq
    }

    @Test
    public void decryptTest() {
        System.out.println(DESedeUtils.decrypt("caouHkEwv5ub4ZoMBGgDeLjFPL4WTmQJ", key, iv));// DES 加密测试
        System.out.println(DESedeUtils.decrypt("883A/Su7FGmQD6UCQW0nmuWTx27CGukq", key));// DES 加密测试
    }

    @Test
    public void encryptFileTest() {
        DESedeUtils.encryptFile(new File("/Users/Chanus/Documents/desede.txt"), new File("/Users/Chanus/Documents/desede1.txt"), key);
        DESedeUtils.encryptFile("/Users/Chanus/Documents/desede.txt", "/Users/Chanus/Documents/desede2.txt", key);
    }

    @Test
    public void decryptFileTest() {
        DESedeUtils.decryptFile(new File("/Users/Chanus/Documents/desede1.txt"), new File("/Users/Chanus/Documents/desede3.txt"), key);
        DESedeUtils.decryptFile("/Users/Chanus/Documents/desede2.txt", "/Users/Chanus/Documents/desede4.txt", key);
    }

    @Test
    public void encryptWithEcbTest() {
        String data = "DESede ECB 模式加密测试";
        System.out.println(DESedeUtils.encryptWithEcb(data, key));// IpMIvJyt6vF7NpJK7vRipzWto8KfqQnz5EOphIDnITQ=
    }

    @Test
    public void decryptWithEcbTest() {
        System.out.println(DESedeUtils.decryptWithEcb("IpMIvJyt6vF7NpJK7vRipzWto8KfqQnz5EOphIDnITQ=", key));// DESede ECB 模式加密测试
    }
}
