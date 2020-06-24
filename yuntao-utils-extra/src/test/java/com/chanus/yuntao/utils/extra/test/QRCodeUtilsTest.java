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
package com.chanus.yuntao.utils.extra.test;

import com.chanus.yuntao.utils.core.image.ImageUtils;
import com.chanus.yuntao.utils.extra.qrcode.QRCodeConfig;
import com.chanus.yuntao.utils.extra.qrcode.QRCodeUtils;
import com.google.zxing.BarcodeFormat;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * QRCodeUtils 测试类
 *
 * @author Chanus
 * @date 2020-06-24 17:40:00
 * @since 1.0.0
 */
public class QRCodeUtilsTest {
    private String path = "F:/";// windows
    // private String path = "/Users/Chanus/Documents/";// mac

    @Test
    public void generateTest1() {
        String imageName = System.currentTimeMillis() + ".png";
        String qrCodePath = path + imageName;
        QRCodeUtils.generate("test", 300, 300, new File(qrCodePath));
    }

    @Test
    public void generateTest2() {
        String imageName = System.currentTimeMillis() + ".png";
        String qrCodePath = path + imageName;
        QRCodeConfig config = QRCodeConfig.create().setContent("1234567890")
                .setWidth(400).setHeight(400).setForeColor(Color.BLACK).setBackColor(Color.WHITE)
                .setMargin(2).setFontSize(18).setLogoImage(path + "0.jpg")
                .setLogoRatio(0.2f).setLogoRadius(1)
                .setText("这是一个带有说明文字的二维码，说明文字的颜色为红色")
                .setFontColor(Color.RED).setTextLRMargin(5).setTextTBMargin(10);
        QRCodeUtils.generate(config, new File(qrCodePath));
    }

    @Test
    public void generateTest3() {
        String imageName = System.currentTimeMillis() + ".png";
        String qrCodePath = path + imageName;
        BufferedImage image = QRCodeUtils.generate(BarcodeFormat.CODE_128, "11111111", 1000, 300);
        ImageUtils.write(image, new File(qrCodePath));
    }

    @Test
    public void generateTest4() {
        String imageName = System.currentTimeMillis() + ".png";
        String qrCodePath = path + imageName;
        QRCodeConfig config = QRCodeConfig.create().setContent("1234567890")
                .setWidth(400).setHeight(400).setForeColor(Color.BLACK).setBackColor(Color.WHITE)
                .setMargin(2).setFontSize(18).setLogoImage(path + "0.jpg")
                .setLogoRatio(0.2f).setLogoRadius(1)
                .setText("这是一个带有说明文字的二维码，说明文字的颜色为红色")
                .setFontColor(Color.RED).setTextLRMargin(5).setTextTBMargin(10);
        QRCodeUtils.generate(config, qrCodePath);
    }

    @Test
    public void decode1() {
        String string = QRCodeUtils.decode(new File(path + "1592991779648.png"));
        System.out.println(string);
    }

    @Test
    public void decode2() {
        String string = QRCodeUtils.decode(path + "1592991807916.png");
        System.out.println(string);
    }
}
