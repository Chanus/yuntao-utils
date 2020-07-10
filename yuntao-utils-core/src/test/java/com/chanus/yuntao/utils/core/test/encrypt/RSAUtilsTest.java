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

import com.chanus.yuntao.utils.core.StringUtils;
import com.chanus.yuntao.utils.core.encrypt.RSAUtils;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * RSAUtils 测试类
 *
 * @author Chanus
 * @date 2020-06-24 17:51:52
 * @since 1.0.0
 */
public class RSAUtilsTest {
    private String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAItMl6IQBYcYZJnppS/U0VKzmJVMKvN1T8w3pNAG9hShPLFVCKpuW29r7EEtARt1yxa0D3OHJP355C9KTQD7ks02RJUJfRt5oIgSyPD3m3A3asCor9z6HgxBXnMThBT4V9/1NJkTydkuXyLDcWKHpMUgULoz0hhU7JeTP1GWaXEhAgMBAAECgYBwFzHtNvc7vmU1dM13r8jAw8QEX0YwojWUHzKAMxnRf+1WxRY0DpmFD1MNHynE7wR9QGcuj5E0zeDQLPnJ0KZPP88BrCpm6u74+ghKEaYfBmdUwICMkSvUJ1JGM+j8NspsbrG1BxCbNPR9uAloxiFih7bthe27Z+Rq99SEfsOgAQJBAPjS5FCcrtA0PopwAFFUL8EOhfKuPLenM3pvgZgUbriMv1XGBSXobRs54DOUmVFHgo/ovPMrawUj4PKix5FMw5UCQQCPUQ/QRjwXm6Wa+2fTF8rPLwyJecurMz+E4fwD5/WhAQPDUxCL/TeK/d5OlcqwuQYXvSrwDYFmnq4aFvb5k9RdAkEAt2JOzS69FEv87/Dd+xLN4z498H8D1uMO6KS34Yrlk3SAK8o2zxp/FzrPh5R0btgCXOfOInYUeQRZbNoVtXvbBQJAKSYsNS/FVz3wabRfliORrrUik2JuEQTQ6xV7p591TjodW3rBGICm7vh/WO73WJB3aF9/MZefHNjYwlLvrK8+XQJBAK2TpSFbBjacIPhLp55SQSkJ3iGRpBz3CSr5necd0oQr02gK/u3U5gFKBB3iRW1pIeBHkLsdouNXHKS/FYWJowM=";
    private String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCLTJeiEAWHGGSZ6aUv1NFSs5iVTCrzdU/MN6TQBvYUoTyxVQiqbltva+xBLQEbdcsWtA9zhyT9+eQvSk0A+5LNNkSVCX0beaCIEsjw95twN2rAqK/c+h4MQV5zE4QU+Fff9TSZE8nZLl8iw3Fih6TFIFC6M9IYVOyXkz9RlmlxIQIDAQAB";

    @Test
    public void generateKeyPairTest() {
        Map<String, String> keyPair = RSAUtils.generateKeyPair();
        keyPair.forEach((k, v) -> System.out.println(k + " : " + v));
    }

    @Test
    public void encryptByPublicKeyTest() {
        // 公钥加密的密文每次都不一样
        String text = "我是明文，请加密我！";
        byte[] b = RSAUtils.encryptByPublicKey(StringUtils.utf8Bytes(text), publicKey);
        String cipher1 = com.chanus.yuntao.utils.core.codec.Base64.encode(b);
        System.out.println(cipher1);
        String cipher2 = RSAUtils.encryptByPublicKey(text, publicKey);
        System.out.println(cipher2);

        String text1 = new String(RSAUtils.decryptByPrivateKey(b, privateKey), StandardCharsets.UTF_8);
        String text2 = RSAUtils.decryptByPrivateKey(cipher2, privateKey);
        System.out.println("公钥解密私钥加密密文：" + text1);
        System.out.println("公钥解密私钥加密密文：" + text2);
    }

    @Test
    public void encryptByPrivateKeyTest() {
        // 私钥加密的密文每次都一样
        String text = "我是明文，请加密我！";
        byte[] b = RSAUtils.encryptByPrivateKey(StringUtils.utf8Bytes(text), privateKey);
        String cipher1 = com.chanus.yuntao.utils.core.codec.Base64.encode(b);
        System.out.println(cipher1);
        String cipher2 = RSAUtils.encryptByPrivateKey(text, privateKey);
        System.out.println(cipher2);

        String text1 = new String(RSAUtils.decryptByPublicKey(b, publicKey), StandardCharsets.UTF_8);
        String text2 = RSAUtils.decryptByPublicKey(cipher2, publicKey);
        System.out.println("私钥解密公钥加密密文：" + text1);
        System.out.println("私钥解密公钥加密密文：" + text2);
    }

    @Test
    public void signTest() {
        String text = "我是一个字符串，请对我进行数字签名。";

        String signature1 = RSAUtils.sign(text.getBytes(), privateKey);
        String signature2 = RSAUtils.sign(text, privateKey);
        System.out.println("数字签名：" + signature1);
        System.out.println("数字签名：" + signature2);
    }

    @Test
    public void verifyTest() {
        String text = "我是一个字符串，请对我进行数字签名。";
        String signature = "LK2unNh1ctIyZLaGhITnv1r5NgzDqEsHP/dKKvW3iDXIC8XQZNtX7VNEY7CjdWhY/dJ93IGjmmt8FGbU9540L8EV6uFCN8VyuhJpUDmQf17twB/88uyxAKIZcSx7IFJCv8yFfgfS3yCVA7kdho8aUKZegzKDVHMLalLTC+wSZzo=";

        boolean b1 = RSAUtils.verify(text.getBytes(), signature, publicKey);
        boolean b2 = RSAUtils.verify(text, signature, publicKey);
        System.out.println("验签结果：" + b1);
        System.out.println("验签结果：" + b2);
    }
}
