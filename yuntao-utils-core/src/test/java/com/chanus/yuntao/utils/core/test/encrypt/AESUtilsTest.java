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

import com.chanus.yuntao.utils.core.encrypt.AESUtils;
import org.junit.Test;

/**
 * AESUtils 测试类
 *
 * @author Chanus
 * @since 1.0.0
 */
public class AESUtilsTest {
    String key = "2wH7a7TxmIsWb3+1JM0OVQ==";
    String customKey = "0123456789ABCDEF";

    String text = "测试AES加密！";

    @Test
    public void generateKeyTest() {
        String seed = "yuntao";
        String key1 = AESUtils.generateKey(seed);
        String key2 = AESUtils.generateKey(seed);
        String key3 = AESUtils.generateKey(seed);

        System.out.println("key1 = " + key1);
        System.out.println("key2 = " + key2);
        System.out.println("key3 = " + key3);
    }

    @Test
    public void aesCBCTest() {
        String vector = "ABCDEFGHIJKLMNOP";

        String cipher1 = AESUtils.encrypt(text, key, vector);
        System.out.println("指定秘钥和向量加密：" + cipher1);

        String cipher2 = AESUtils.encrypt(text, key);
        System.out.println("指定秘钥，使用默认向量加密：" + cipher2);

        String text1 = AESUtils.decrypt(cipher1, key, vector);
        System.out.println("指定秘钥和向量解密：" + text1);

        String text2 = AESUtils.decrypt(cipher2, key);
        System.out.println("指定秘钥，使用默认向量解密：" + text2);

        String cipher3 = AESUtils.encrypt(text, customKey, vector);
        System.out.println("指定自定义秘钥和向量加密：" + cipher3);

        String cipher4 = AESUtils.encrypt(text, customKey);
        System.out.println("指定自定义秘钥，使用默认向量加密：" + cipher4);

        String text4 = AESUtils.decrypt(cipher3, customKey, vector);
        System.out.println("指定自定义秘钥和向量解密：" + text4);

        String text5 = AESUtils.decrypt(cipher4, customKey);
        System.out.println("指定自定义秘钥，使用默认向量解密：" + text5);
    }

    @Test
    public void aesECBTest() {
        String cipher1 = AESUtils.encryptWithEcb(text, key);
        System.out.println("指定秘钥加密：" + cipher1);

        String text1 = AESUtils.decryptWithEcb(cipher1, key);
        System.out.println("指定秘钥解密：" + text1);

        String cipher2 = AESUtils.encryptWithEcb(text, customKey);
        System.out.println("指定自定义秘钥加密：" + cipher2);

        String text2 = AESUtils.decryptWithEcb(cipher2, customKey);
        System.out.println("指定自定义秘钥解密：" + text2);
    }
}
