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

import com.chanus.yuntao.utils.core.GoogleAuthenticatorUtils;
import org.junit.Test;

/**
 * GoogleAuthenticatorUtils 测试类
 *
 * @author Chanus
 * @date 2020-06-24 16:36:38
 * @since 1.0.0
 */
public class GoogleAuthenticatorUtilsTest {
    @Test
    public void generateSecretKeyTest() {
        String key = GoogleAuthenticatorUtils.generateSecretKey();
        System.out.println(key);
    }

    @Test
    public void getQRBarcodeURLTest() {
        String qrBarcodeURL = GoogleAuthenticatorUtils.getQRBarcodeURL("test", "test@test.com", "N6VDTUFTVZS6ED6P");
        System.out.println(qrBarcodeURL);
    }

    @Test
    public void getQRBarcodeTest() {
        String qrBarcode = GoogleAuthenticatorUtils.getQRBarcode("test", "N6VDTUFTVZS6ED6P");
        System.out.println(qrBarcode);
    }

    @Test
    public void checkCodeTest() {
        String secret = "N6VDTUFTVZS6ED6P";
        long code = Long.parseLong("059352");
        System.out.println(GoogleAuthenticatorUtils.checkCode(secret, code, System.currentTimeMillis()));
        System.out.println(GoogleAuthenticatorUtils.checkCode(secret, code));
    }
}
