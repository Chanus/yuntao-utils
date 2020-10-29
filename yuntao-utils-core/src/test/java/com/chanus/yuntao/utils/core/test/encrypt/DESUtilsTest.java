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

import com.chanus.yuntao.utils.core.encrypt.DESUtils;
import org.junit.Test;

import java.io.File;

/**
 * DESUtils 测试类
 *
 * @author Chanus
 * @date 2020-10-29 21:46:27
 * @since 1.4.3
 */
public class DESUtilsTest {
    private final String key = "abcdefgh";
    private final String iv = "11111111";

    @Test
    public void encryptTest() {
        String data = "DES 加密测试";
        System.out.println(DESUtils.encrypt(data, key, iv));// t85XVx5rns98KLTrqFsMk2TjeDB2q+BV
        System.out.println(DESUtils.encrypt(data, key));// WNXzTpz90t/HuTDKefJotHO7WlCIxs6g
    }

    @Test
    public void decryptTest() {
        System.out.println(DESUtils.decrypt("t85XVx5rns98KLTrqFsMk2TjeDB2q+BV", key, iv));// DES 加密测试
        System.out.println(DESUtils.decrypt("WNXzTpz90t/HuTDKefJotHO7WlCIxs6g", key));// DES 加密测试
    }

    @Test
    public void encryptFileTest() {
        DESUtils.encryptFile(new File("/Users/Chanus/Documents/des.txt"), new File("/Users/Chanus/Documents/des1.txt"), key);
        DESUtils.encryptFile("/Users/Chanus/Documents/des.txt", "/Users/Chanus/Documents/des2.txt", key);
    }

    @Test
    public void decryptFileTest() {
        DESUtils.decryptFile(new File("/Users/Chanus/Documents/des1.txt"), new File("/Users/Chanus/Documents/des3.txt"), key);
        DESUtils.decryptFile("/Users/Chanus/Documents/des2.txt", "/Users/Chanus/Documents/des4.txt", key);
    }

    @Test
    public void encryptWithEcbTest() {
        String data = "DES ECB 模式加密测试";
        System.out.println(DESUtils.encryptWithEcb(data, key));// ZxGGURTamuv1h0TOt3qLXjt6C/n9KY2KeH21HhgtVVc=
    }

    @Test
    public void decryptWithEcbTest() {
        System.out.println(DESUtils.decryptWithEcb("ZxGGURTamuv1h0TOt3qLXjt6C/n9KY2KeH21HhgtVVc=", key));// DES ECB 模式加密测试
    }
}
