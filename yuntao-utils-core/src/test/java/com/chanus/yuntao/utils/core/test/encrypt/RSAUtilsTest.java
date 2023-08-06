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

import com.chanus.yuntao.utils.core.CharsetUtils;
import com.chanus.yuntao.utils.core.StringUtils;
import com.chanus.yuntao.utils.core.encrypt.RSAUtils;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * RSAUtils 测试类
 *
 * @author Chanus
 * @since 1.0.0
 */
public class RSAUtilsTest {
    private String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAItMl6IQBYcYZJnppS/U0VKzmJVMKvN1T8w3pNAG9hShPLFVCKpuW29r7EEtARt1yxa0D3OHJP355C9KTQD7ks02RJUJfRt5oIgSyPD3m3A3asCor9z6HgxBXnMThBT4V9/1NJkTydkuXyLDcWKHpMUgULoz0hhU7JeTP1GWaXEhAgMBAAECgYBwFzHtNvc7vmU1dM13r8jAw8QEX0YwojWUHzKAMxnRf+1WxRY0DpmFD1MNHynE7wR9QGcuj5E0zeDQLPnJ0KZPP88BrCpm6u74+ghKEaYfBmdUwICMkSvUJ1JGM+j8NspsbrG1BxCbNPR9uAloxiFih7bthe27Z+Rq99SEfsOgAQJBAPjS5FCcrtA0PopwAFFUL8EOhfKuPLenM3pvgZgUbriMv1XGBSXobRs54DOUmVFHgo/ovPMrawUj4PKix5FMw5UCQQCPUQ/QRjwXm6Wa+2fTF8rPLwyJecurMz+E4fwD5/WhAQPDUxCL/TeK/d5OlcqwuQYXvSrwDYFmnq4aFvb5k9RdAkEAt2JOzS69FEv87/Dd+xLN4z498H8D1uMO6KS34Yrlk3SAK8o2zxp/FzrPh5R0btgCXOfOInYUeQRZbNoVtXvbBQJAKSYsNS/FVz3wabRfliORrrUik2JuEQTQ6xV7p591TjodW3rBGICm7vh/WO73WJB3aF9/MZefHNjYwlLvrK8+XQJBAK2TpSFbBjacIPhLp55SQSkJ3iGRpBz3CSr5necd0oQr02gK/u3U5gFKBB3iRW1pIeBHkLsdouNXHKS/FYWJowM=";
    private String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCLTJeiEAWHGGSZ6aUv1NFSs5iVTCrzdU/MN6TQBvYUoTyxVQiqbltva+xBLQEbdcsWtA9zhyT9+eQvSk0A+5LNNkSVCX0beaCIEsjw95twN2rAqK/c+h4MQV5zE4QU+Fff9TSZE8nZLl8iw3Fih6TFIFC6M9IYVOyXkz9RlmlxIQIDAQAB";
    // private String privateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC9PglBwk3pDhG4i9drG4cXNz5EAg2obUFiCX9TdtvhM7MpoljBPFUuUayFBzrGaU2YG+7PmKp/HqbO1KhH8ft0AOf/EazzL3sHdlyWNsvb308Q4/VrJpEOHH4cEUvdfg4s6p0U4J/JJZMPVYX7p1bjsWG3I4c4VaBbwaZB753pPjPGwzQhc5BQRGWJV8ajoWpHVdZdRb0W5ER0e+vRjpXqrsge06I2nzRFCNRIHAUJWX56pIFoiVc37XCtOpcOuRjfEe8PNAm3S3vru8NtBH6ikS7Qh0bgYtVYX+KDxf9yOC4LeyNJm79cEzjp43czTItgGCDkFSEHQF2BQ0DrlqelAgMBAAECggEBAJ2CC8xav+PxHNjAM9zm/YpaJY4PM3Ty8o95nitG633GPSGzCTJUWAWEXxYdzVKlBLtMzHim9Yl2ZpoRr7qp8oM72dyXxp5l731ni2pPU1++vp0tTvAPGn5nQqNz1fQVXhEOeQ2OZA/UIVGK+khZoF1FHzKB9KLzydN1Gz8YBBPDi0p8jnwehEZ+zDnH4qDa5fe6JSEbHbPCYKA+cpAHD42A0ak7cOl/qUuCX6r9VRPXsWJAllv+z7ulua2vxitEt+CGJYrISlHZ4PO3OD1oUvtmzQQqfCXvtorOXxhH01ZxshlqNYVdsXq0jUnKubn6AAT6SKbBRp44pSY8Y2v9tUUCgYEA+2rXw1rae5AOFRCcdrZ0gn9iciTY5phgvavCKFS1OmI89HMDcBhVnhKdqIMiriuR7cv5bkBR4FCgVqNtTSXo6Eu1cu7EWkJo+I7aJBkNiRb+1fxUg+jLJFLMqnPiK4sZaFvv0JJzNXAR7rTitmuUWTZugeltajVDKwyR8XUpV1MCgYEAwLES5q4MPuLmNkyzFnpsl8vuF/IB2Q6pDTCSXXgoWcxtuDJouV83FkrX9pWZzxTy/rbye9OpxMVczIc08E+z6s9gt8N2+mbRgE8399RUmJfMaMf/kRJfYoomJRDNAao5zDymBxiOKoHb2Pj7Rg3qwEl7JpMrn8nUvqIFj0UC/icCgYEAt5qK/ws2Ss4kupVq7fbIlxRX3Gu0SpI0886e970ErCJZyVrEw/PBcrK9l/ZMOU1wQGyZNz6ZjGrU7PBkPdbVg+7mD4z2r8OXzQzlmT8Cd37nk3W2cq5qy5f8Bu6h2JK4d1HMjFCm+fth3SUT+4wMxinOAelqEgnbh3Cik+avzikCgYBsQ5FRFr0+oBGktJdyq7pT13qg7ZJoODp3dLDn31kciyWidoAslvjGh6qrmwZeOXEF+BhpxlT+f71SFS2DT+TQq0+2MBarPhW7t8bEcgJN8gwhWy2WfvIk1WX1MWld5ByxSeom1n8aFvhFAi97/tnEzARregjqsGXd7f6drrKtxQKBgQDIyW4FuVKfjswjLF2VI0m9p6Y+nEB2z70waQFL3qJrrU7eOjXsseKoWpf2alCKWUC7e53/oVBZkzXqwWThPXT24zASehM5vAihArlB6hZQm5BknPstMNyNDdsETd7cBJ1f6TkTAAftmITAK8HF7Wq37J2hEz4KzC5BeflOfCW/6Q==";
    // private String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvT4JQcJN6Q4RuIvXaxuHFzc+RAINqG1BYgl/U3bb4TOzKaJYwTxVLlGshQc6xmlNmBvuz5iqfx6mztSoR/H7dADn/xGs8y97B3ZcljbL299PEOP1ayaRDhx+HBFL3X4OLOqdFOCfySWTD1WF+6dW47FhtyOHOFWgW8GmQe+d6T4zxsM0IXOQUERliVfGo6FqR1XWXUW9FuREdHvr0Y6V6q7IHtOiNp80RQjUSBwFCVl+eqSBaIlXN+1wrTqXDrkY3xHvDzQJt0t767vDbQR+opEu0IdG4GLVWF/ig8X/cjguC3sjSZu/XBM46eN3M0yLYBgg5BUhB0BdgUNA65anpQIDAQAB";

    @Test
    public void generateKeyPairTest() {
        Map<String, String> keyPair = RSAUtils.generateKeyPair(2048);
        keyPair.forEach((k, v) -> System.out.println(k + " : " + v));

        keyPair = RSAUtils.generateKeyPair();
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
        String cipher3 = RSAUtils.encryptByPublicKey(text, publicKey, CharsetUtils.CHARSET_GBK);
        System.out.println(cipher3);

        String text1 = new String(RSAUtils.decryptByPrivateKey(b, privateKey), StandardCharsets.UTF_8);
        String text2 = RSAUtils.decryptByPrivateKey(cipher2, privateKey);
        String text3 = RSAUtils.decryptByPrivateKey(cipher3, privateKey, CharsetUtils.CHARSET_GBK);
        System.out.println("私钥解密公钥加密密文：" + text1);
        System.out.println("私钥解密公钥加密密文：" + text2);
        System.out.println("私钥解密公钥加密密文：" + text3);
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
        String cipher3 = RSAUtils.encryptByPrivateKey(text, privateKey, CharsetUtils.CHARSET_GBK);
        System.out.println(cipher3);

        String text1 = new String(RSAUtils.decryptByPublicKey(b, publicKey), StandardCharsets.UTF_8);
        String text2 = RSAUtils.decryptByPublicKey(cipher2, publicKey);
        String text3 = RSAUtils.decryptByPublicKey(cipher3, publicKey, CharsetUtils.CHARSET_GBK);
        System.out.println("公钥解密私钥加密密文：" + text1);
        System.out.println("公钥解密私钥加密密文：" + text2);
        System.out.println("公钥解密私钥加密密文：" + text3);
    }

    @Test
    public void signTest() {
        String text = "我是一个字符串，请对我进行数字签名。";

        String signature1 = RSAUtils.sign(text.getBytes(), privateKey);
        String signature2 = RSAUtils.sign(text, privateKey);
        String signature3 = RSAUtils.sign(text, privateKey, CharsetUtils.CHARSET_GBK);
        System.out.println("数字签名：" + signature1);
        System.out.println("数字签名：" + signature2);
        System.out.println("数字签名：" + signature3);
    }

    @Test
    public void verifyTest() {
        String text = "我是一个字符串，请对我进行数字签名。";
        String signature1 = "LK2unNh1ctIyZLaGhITnv1r5NgzDqEsHP/dKKvW3iDXIC8XQZNtX7VNEY7CjdWhY/dJ93IGjmmt8FGbU9540L8EV6uFCN8VyuhJpUDmQf17twB/88uyxAKIZcSx7IFJCv8yFfgfS3yCVA7kdho8aUKZegzKDVHMLalLTC+wSZzo=";
        String signature2 = "LK2unNh1ctIyZLaGhITnv1r5NgzDqEsHP/dKKvW3iDXIC8XQZNtX7VNEY7CjdWhY/dJ93IGjmmt8FGbU9540L8EV6uFCN8VyuhJpUDmQf17twB/88uyxAKIZcSx7IFJCv8yFfgfS3yCVA7kdho8aUKZegzKDVHMLalLTC+wSZzo=";
        String signature3 = "NvssnLziPPYoyleXp/NH3B87uHH4sbQaUbr5mEXyKo3dezzCil96O47iXj7tyqf2m3pzquac7IyvhRZ6Lav4W5aD6q7moliW5cPNBHAcFQgkSj6YZshrcl4PxhR4hL9y83uhtgUT3dfkmOmdCNCorU1YSNKu3oR9dxSR5vgHJ7o=";

        boolean b1 = RSAUtils.verify(text.getBytes(), signature1, publicKey);
        boolean b2 = RSAUtils.verify(text, signature2, publicKey);
        boolean b3 = RSAUtils.verify(text, signature3, publicKey, CharsetUtils.CHARSET_GBK);
        System.out.println("验签结果：" + b1);
        System.out.println("验签结果：" + b2);
        System.out.println("验签结果：" + b3);
    }
}
