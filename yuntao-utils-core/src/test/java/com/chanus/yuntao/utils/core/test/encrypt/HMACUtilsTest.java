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

import com.chanus.yuntao.utils.core.encrypt.HMACUtils;
import org.junit.Test;

/**
 * HMACUtils 测试类
 *
 * @author Chanus
 * @since 1.1.0
 */
public class HMACUtilsTest {
    String data = "0123456789";
    String key = "9876543210";

    @Test
    public void encryptTest() {
        // bfb1338a70c06331dbe8d34f05c2cafb25c65ab5
        System.out.println("HmacSHA1 加密：" + HMACUtils.encrypt(data, key, HMACUtils.HMACEnum.HMAC_SHA1));
        // f9efc9ab6c00cb2d879d0b64c6fc000c8381763e7a646fcc9193a6f6
        System.out.println("HmacSHA224 加密：" + HMACUtils.encrypt(data, key, HMACUtils.HMACEnum.HMAC_SHA224));
        // b05dd243b37d16a5d558e0b27d4aa176384600ec0561ae51b197625983f084aa
        System.out.println("HmacSHA256 加密：" + HMACUtils.encrypt(data, key, HMACUtils.HMACEnum.HMAC_SHA256));
        // 3a5cdc201c6a4b116ccb15f8676473913930deae1e1e615940be1bfd87361ba5f9feebb99bc27e3d611f2f6757573f44
        System.out.println("HmacSHA384 加密：" + HMACUtils.encrypt(data, key, HMACUtils.HMACEnum.HMAC_SHA384));
        // 008167a1b9abb6238d28a6a8f7ae8610405043afa734859b3adacb38f7c20889cc5f3b32cb4b2e508e6a25a5d38ae27cf5cfe8b5a93190d984523783247ff234
        System.out.println("HmacSHA512 加密：" + HMACUtils.encrypt(data, key, HMACUtils.HMACEnum.HMAC_SHA512));
        // 3b861056c340463ea55d62fcc8793c12
        System.out.println("HmacMD5 加密：" + HMACUtils.encrypt(data, key, HMACUtils.HMACEnum.HMAC_MD5));
    }

    @Test
    public void hmacSHA1Test() {
        // bfb1338a70c06331dbe8d34f05c2cafb25c65ab5
        System.out.println("HmacSHA1 加密：" + HMACUtils.hmacSHA1(data, key));
    }

    @Test
    public void hmacSHA224Test() {
        // f9efc9ab6c00cb2d879d0b64c6fc000c8381763e7a646fcc9193a6f6
        System.out.println("HmacSHA224 加密：" + HMACUtils.hmacSHA224(data, key));
    }

    @Test
    public void hmacSHA256Test() {
        // b05dd243b37d16a5d558e0b27d4aa176384600ec0561ae51b197625983f084aa
        System.out.println("HmacSHA256 加密：" + HMACUtils.hmacSHA256(data, key));
    }

    @Test
    public void hmacSHA384Test() {
        // 3a5cdc201c6a4b116ccb15f8676473913930deae1e1e615940be1bfd87361ba5f9feebb99bc27e3d611f2f6757573f44
        System.out.println("HmacSHA384 加密：" + HMACUtils.hmacSHA384(data, key));
    }

    @Test
    public void hmacSHA512Test() {
        // 008167a1b9abb6238d28a6a8f7ae8610405043afa734859b3adacb38f7c20889cc5f3b32cb4b2e508e6a25a5d38ae27cf5cfe8b5a93190d984523783247ff234
        System.out.println("HmacSHA512 加密：" + HMACUtils.hmacSHA512(data, key));
    }

    @Test
    public void hmacMD5Test() {
        // 3b861056c340463ea55d62fcc8793c12
        System.out.println("HmacMD5 加密：" + HMACUtils.hmacMD5(data, key));
    }

    @Test
    public void verifyTest() {
        String sha1 = "bfb1338a70c06331dbe8d34f05c2cafb25c65ab5";
        System.out.println("HmacSHA1 加密校验：" + HMACUtils.verify(data, key, sha1, HMACUtils.HMACEnum.HMAC_SHA1));
        String sha224 = "f9efc9ab6c00cb2d879d0b64c6fc000c8381763e7a646fcc9193a6f6";
        System.out.println("HmacSHA224 加密校验：" + HMACUtils.verify(data, key, sha224, HMACUtils.HMACEnum.HMAC_SHA224));
        String sha256 = "b05dd243b37d16a5d558e0b27d4aa176384600ec0561ae51b197625983f084aa";
        System.out.println("HmacSHA256 加密校验：" + HMACUtils.verify(data, key, sha256, HMACUtils.HMACEnum.HMAC_SHA256));
        String sha384 = "3a5cdc201c6a4b116ccb15f8676473913930deae1e1e615940be1bfd87361ba5f9feebb99bc27e3d611f2f6757573f44";
        System.out.println("HmacSHA384 加密校验：" + HMACUtils.verify(data, key, sha384, HMACUtils.HMACEnum.HMAC_SHA384));
        String sha512 = "008167a1b9abb6238d28a6a8f7ae8610405043afa734859b3adacb38f7c20889cc5f3b32cb4b2e508e6a25a5d38ae27cf5cfe8b5a93190d984523783247ff234";
        System.out.println("HmacSHA512 加密校验：" + HMACUtils.verify(data, key, sha512, HMACUtils.HMACEnum.HMAC_SHA512));
        String md5 = "3b861056c340463ea55d62fcc8793c12";
        System.out.println("HmacMD5 加密校验：" + HMACUtils.verify(data, key, md5, HMACUtils.HMACEnum.HMAC_MD5));
    }

    @Test
    public void verifyHmacSHA1Test() {
        String cipher = "bfb1338a70c06331dbe8d34f05c2cafb25c65ab5";
        System.out.println("HmacSHA1 加密校验：" + HMACUtils.verifyHmacSHA1(data, key, cipher));
    }

    @Test
    public void verifyHmacSHA224Test() {
        String cipher = "f9efc9ab6c00cb2d879d0b64c6fc000c8381763e7a646fcc9193a6f6";
        System.out.println("HmacSHA224 加密校验：" + HMACUtils.verifyHmacSHA224(data, key, cipher));
    }

    @Test
    public void verifyHmacSHA256Test() {
        String cipher = "b05dd243b37d16a5d558e0b27d4aa176384600ec0561ae51b197625983f084aa";
        System.out.println("HmacSHA256 加密校验：" + HMACUtils.verifyHmacSHA256(data, key, cipher));
    }

    @Test
    public void verifyHmacSHA384Test() {
        String cipher = "3a5cdc201c6a4b116ccb15f8676473913930deae1e1e615940be1bfd87361ba5f9feebb99bc27e3d611f2f6757573f44";
        System.out.println("HmacSHA384 加密校验：" + HMACUtils.verifyHmacSHA384(data, key, cipher));
    }

    @Test
    public void verifyHmacSHA512Test() {
        String cipher = "008167a1b9abb6238d28a6a8f7ae8610405043afa734859b3adacb38f7c20889cc5f3b32cb4b2e508e6a25a5d38ae27cf5cfe8b5a93190d984523783247ff234";
        System.out.println("HmacSHA512 加密校验：" + HMACUtils.verifyHmacSHA512(data, key, cipher));
    }

    @Test
    public void verifyHmacMD5Test() {
        String cipher = "3b861056c340463ea55d62fcc8793c12";
        System.out.println("HmacMD5 加密校验：" + HMACUtils.verifyHmacMD5(data, key, cipher));
    }
}
