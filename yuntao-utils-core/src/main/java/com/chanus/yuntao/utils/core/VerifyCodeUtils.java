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
package com.chanus.yuntao.utils.core;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证码图片生成工具类
 *
 * @author Chanus
 * @date 2020-06-24 08:52:56
 * @since 1.0.0
 */
public class VerifyCodeUtils {
    /**
     * 验证码图片的宽度
     */
    private int width;
    /**
     * 验证码图片的高度
     */
    private int height;
    /**
     * x 坐标，即字符左端距图片左边界距离
     */
    private int x;
    /**
     * y 坐标，即字符顶端距图片下边界距离
     */
    private int y;
    /**
     * 验证码字符个数
     */
    private int codeCount;
    /**
     * 验证码字符宽度
     */
    private int codeWidth;
    /**
     * 验证码字符高度
     */
    private int codeHeight;
    /**
     * 验证码字符集
     */
    private char[] codeSequence;

    /**
     * 初始化默认参数
     */
    public VerifyCodeUtils() {
        super();
        this.width = 120;
        this.height = 38;
        this.x = 16;
        this.y = 32;
        this.codeCount = 4;
        this.codeWidth = (this.width - 2 * x) / this.codeCount + 1;
        this.codeHeight = 2 * y - this.height + 6;
        this.codeSequence = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    }

    /**
     * 初始化参数
     *
     * @param width        验证码图片的宽度
     * @param height       验证码图片的高度
     * @param x            x 坐标，即字符左端距图片左边界距离
     * @param y            y 坐标，即字符顶端距图片下边界距离
     * @param codeWidthX   验证码字符宽度微调值
     * @param codeHeightY  验证码字符高度微调值
     * @param codeCount    验证码字符个数
     * @param codeSequence 验证码字符集
     */
    public void init(int width, int height, int x, int y, int codeWidthX, int codeHeightY, int codeCount, char[] codeSequence) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.codeCount = codeCount;
        this.codeWidth = (width - 2 * x) / codeCount + codeWidthX;
        this.codeHeight = 2 * y - height + codeHeightY;
        this.codeSequence = codeSequence;
    }

    /**
     * 生成验证码图片
     *
     * @return Map    randomCode-验证码，buffImg-验证码图片
     */
    public Map<String, Object> generate() {
        // 定义图像buffer
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();
        // 将图像填充为白色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, codeHeight);
        // 设置字体。
        g.setFont(font);
        // 画边框。
        g.setColor(Color.WHITE);
        g.drawRect(0, 0, width - 1, height - 1);
        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuilder randomCode = new StringBuilder();
        // 随机产生codeCount数字的验证码。
        char randomChar;
        for (int i = 0; i < codeCount; i++) {
            // 得到随机产生的验证码数字。
            randomChar = codeSequence[RandomUtils.getRandomInt(codeSequence.length)];
            // 用随机产生的颜色将验证码绘制到图像中。
            g.setColor(RandomUtils.getRandomColor());
            g.drawString(String.valueOf(randomChar), i * codeWidth + x, y);
            // 将产生的四个随机数组合在一起。
            randomCode.append(randomChar);
        }

        Map<String, Object> map = new HashMap<>(8);
        map.put("randomCode", randomCode);
        map.put("buffImg", buffImg);
        return map;
    }
}
