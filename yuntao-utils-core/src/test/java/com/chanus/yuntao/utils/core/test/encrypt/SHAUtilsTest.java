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

import com.chanus.yuntao.utils.core.encrypt.SHAUtils;
import org.junit.Test;

/**
 * SHAUtils 测试类
 *
 * @author Chanus
 * @since 1.0.0
 */
public class SHAUtilsTest {
    String text = "我是密码，请对我进行SHA加密。";

    @Test
    public void sha1Test() {
        String sha1 = SHAUtils.sha1(text);
        System.out.println("SHA-1加密：" + sha1);
    }

    @Test
    public void sha224Test() {
        String sha224 = SHAUtils.sha224(text);
        System.out.println("SHA-224加密：" + sha224);
    }

    @Test
    public void sha256Test() {
        String sha256 = SHAUtils.sha256(text);
        System.out.println("SHA-256加密：" + sha256);
    }

    @Test
    public void sha384Test() {
        String sha384 = SHAUtils.sha384(text);
        System.out.println("SHA-384加密：" + sha384);
    }

    @Test
    public void sha512Test() {
        String sha512 = SHAUtils.sha512(text);
        System.out.println("SHA-512加密：" + sha512);
    }

    @Test
    public void verifySHA1Test() {
        String sha1 = "2143699140198a515b1adef1efb271344fc71f13";
        System.out.println("SHA-1加密校验：" + SHAUtils.verifySHA1(text, sha1));
    }

    @Test
    public void verifySHA224Test() {
        String sha224 = "eba078cc0dacd97361f6655e97526abb927074f0be0ba4c5aef7d2b8";
        System.out.println("SHA-224加密校验：" + SHAUtils.verifySHA224(text, sha224));
    }

    @Test
    public void verifySHA256Test() {
        String sha256 = "c22d96285d9c27d46064100c9f6e9f5605d66051d7f254aad7a716ea62272241";
        System.out.println("SHA-256加密校验：" + SHAUtils.verifySHA256(text, sha256));
    }

    @Test
    public void verifySHA384Test() {
        String sha384 = "dddaaf0f015345905aa371bdedf05733384ce23fcba8f9bd33e789d513171e9164b622e808751256f5e2485cb2859c49";
        System.out.println("SHA-384加密校验：" + SHAUtils.verifySHA384(text, sha384));
    }

    @Test
    public void verifySHA512Test() {
        String sha512 = "9ace62a81db6bba14159fac427442b12681539c49896fea6c12ef0c04b5fa2035a543574e6109dd9771e6aecd5e66a6cdb53ff659e746526bae38aae48121194";
        System.out.println("SHA-512加密校验：" + SHAUtils.verifySHA512(text, sha512));
    }
}
