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
package com.chanus.yuntao.utils.core.image;

import com.chanus.yuntao.utils.core.RandomUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * {@link Graphics} 相关工具类
 *
 * @author Chanus
 * @date 2020-06-24 11:42:53
 * @since 1.0.0
 */
public class GraphicsUtils {
    /**
     * 创建 {@link Graphics2D}
     *
     * @param image {@link BufferedImage}
     * @param color {@link Color} 背景颜色以及当前画笔颜色，{@code null} 表示不设置背景色
     * @return {@link Graphics2D}
     */
    public static Graphics2D createGraphics(BufferedImage image, Color color) {
        final Graphics2D g = image.createGraphics();

        if (color != null) {
            // 填充背景
            g.setColor(color);
            g.fillRect(0, 0, image.getWidth(), image.getHeight());
        }

        return g;
    }

    /**
     * 获取文字居中高度的 Y 坐标（距离上边距距离）<br>
     * 此方法依赖 FontMetrics，如果获取失败，默认为背景高度的1/3
     *
     * @param g                {@link Graphics2D} 画笔
     * @param backgroundHeight 背景高度
     * @return 最小高度，-1表示无法获取
     */
    public static int getCenterY(Graphics g, int backgroundHeight) {
        // 获取允许文字最小高度
        FontMetrics metrics = null;
        try {
            metrics = g.getFontMetrics();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int y;
        if (metrics != null) {
            y = (backgroundHeight - metrics.getHeight()) / 2 + metrics.getAscent();
        } else {
            y = backgroundHeight / 3;
        }
        return y;
    }

    /**
     * 绘制字符串，使用随机颜色，默认抗锯齿
     *
     * @param g      {@link Graphics} 画笔
     * @param str    字符串
     * @param font   字体
     * @param width  字符串总宽度
     * @param height 字符串背景高度
     * @return 画笔对象
     */
    public static Graphics drawStringColourful(Graphics g, String str, Font font, int width, int height) {
        return drawString(g, str, font, null, width, height);
    }

    /**
     * 绘制字符串，默认抗锯齿
     *
     * @param g      {@link Graphics} 画笔
     * @param str    字符串
     * @param font   字体
     * @param color  字体颜色，{@code null} 表示使用随机颜色（每个字符单独随机）
     * @param width  字符串背景的宽度
     * @param height 字符串背景的高度
     * @return 画笔对象
     */
    public static Graphics drawString(Graphics g, String str, Font font, Color color, int width, int height) {
        // 抗锯齿
        if (g instanceof Graphics2D) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        // 创建字体
        g.setFont(font);

        // 文字高度（必须在设置字体后调用）
        int midY = GraphicsUtils.getCenterY(g, height);
        if (color != null) {
            g.setColor(color);
        }

        final int len = str.length();
        int charWidth = width / len;
        for (int i = 0; i < len; i++) {
            if (null == color) {
                // 产生随机的颜色值，让输出的每个字符的颜色值都将不同。
                g.setColor(RandomUtils.getRandomColor());
            }
            g.drawString(String.valueOf(str.charAt(i)), i * charWidth, midY);
        }
        return g;
    }
}
