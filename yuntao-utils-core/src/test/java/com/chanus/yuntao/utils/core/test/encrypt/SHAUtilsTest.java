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
 * @date 2020-06-24 17:52:33
 * @since 1.0.0
 */
public class SHAUtilsTest {
    @Test
    public void shaTest() {
        String text = "我是密码，请对我进行SHA加密。";

        String sha1 = SHAUtils.sha1(text);
        System.out.println("SHA-1加密：" + sha1);

        String sha224 = SHAUtils.sha224(text);
        System.out.println("SHA-224加密：" + sha224);

        String sha256 = SHAUtils.sha256(text);
        System.out.println("SHA-256加密：" + sha256);

        String sha384 = SHAUtils.sha384(text);
        System.out.println("SHA-384加密：" + sha384);

        String sha512 = SHAUtils.sha512(text);
        System.out.println("SHA-512加密：" + sha512);

        System.out.println("SHA-1加密校验：" + SHAUtils.verifySHA1(text, sha1));
        System.out.println("SHA-224加密校验：" + SHAUtils.verifySHA224(text, sha224));
        System.out.println("SHA-256加密校验：" + SHAUtils.verifySHA256(text, sha256));
        System.out.println("SHA-384加密校验：" + SHAUtils.verifySHA384(text, sha384));
        System.out.println("SHA-512加密校验：" + SHAUtils.verifySHA512(text, sha512));
    }
}
