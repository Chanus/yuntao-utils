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
package com.chanus.yuntao.utils.extra.qrcode;

import com.chanus.yuntao.utils.core.image.ImageUtils;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.*;
import java.io.File;
import java.util.EnumMap;
import java.util.Map;

/**
 * 二维码配置
 *
 * @author Chanus
 * @date 2020-06-24 17:33:17
 * @since 1.0.0
 */
public class QRCodeConfig {
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;
    /**
     * 编码格式
     */
    public static final String CHARSET = "UTF-8";

    /**
     * 二维码正文
     */
    private String content;
    /**
     * 二维码宽度
     */
    private int width;
    /**
     * 二维码高度
     */
    private int height;
    /**
     * 二维码前景色（二维码颜色），默认黑色
     */
    private int foreColor = BLACK;
    /**
     * 二维码背景色，默认白色，null 表示透明
     */
    private Integer backColor = WHITE;
    /**
     * 二维码边距
     */
    private Integer margin = 2;
    /**
     * 纠错级别，L（QR_ECLEVEL_L，7%），M（QR_ECLEVEL_M，15%），Q（QR_ECLEVEL_Q，25%），H（QR_ECLEVEL_H，30%）
     */
    private ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.H;
    /**
     * 编码方式
     */
    private String charset = CHARSET;
    /**
     * 图片类型（图片扩展名），见{@link ImageUtils}
     */
    private String imageType = ImageUtils.IMAGE_TYPE_PNG;
    /**
     * 二维码 logo
     */
    private Image logoImage;
    /**
     * 二维码 logo 所占二维码比例
     */
    private float logoRatio = 0.20f;
    /**
     * 二维码 logo 圆角程度（0-1,0表示不圆角，1表示圆）
     */
    private double logoRadius = 0.0D;
    /**
     * 二维码下文字
     */
    private String text;
    /**
     * 字体大小
     */
    private int fontSize = 12;
    /**
     * 字体颜色，默认黑色
     */
    private Color fontColor = Color.BLACK;
    /**
     * 二维码下文字的左右间距，当文字存在换行时生效，否则文字居中显示
     */
    private int textLRMargin;
    /**
     * 二维码下文字的上下间距
     */
    private int textTBMargin = 10;

    /**
     * 创建QrConfig
     *
     * @return QRCodeConfig
     */
    public static QRCodeConfig create() {
        return new QRCodeConfig();
    }

    /**
     * 构造
     *
     * @param width  宽
     * @param height 高
     */
    public QRCodeConfig(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * 构造
     *
     * @param content 二维码正文
     * @param width   宽
     * @param height  高
     */
    public QRCodeConfig(String content, int width, int height) {
        this.content = content;
        this.width = width;
        this.height = height;
    }

    /**
     * 构造，默认宽高为400
     */
    public QRCodeConfig() {
        this(400, 400);
    }

    public String getContent() {
        return content;
    }

    public QRCodeConfig setContent(String content) {
        this.content = content;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public QRCodeConfig setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public QRCodeConfig setHeight(int height) {
        this.height = height;
        return this;
    }

    public int getForeColor() {
        return foreColor;
    }

    public QRCodeConfig setForeColor(Color foreColor) {
        if (foreColor != null) {
            this.foreColor = foreColor.getRGB();
        }

        return this;
    }

    public Integer getBackColor() {
        return backColor;
    }

    public QRCodeConfig setBackColor(Color backColor) {
        if (backColor == null) {
            this.backColor = null;
        } else {
            this.backColor = backColor.getRGB();
        }

        return this;
    }

    public Integer getMargin() {
        return margin;
    }

    public QRCodeConfig setMargin(Integer margin) {
        this.margin = margin;
        return this;
    }

    public ErrorCorrectionLevel getErrorCorrectionLevel() {
        return errorCorrectionLevel;
    }

    public QRCodeConfig setErrorCorrectionLevel(ErrorCorrectionLevel errorCorrectionLevel) {
        this.errorCorrectionLevel = errorCorrectionLevel;
        return this;
    }

    public String getCharset() {
        return charset;
    }

    public QRCodeConfig setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    public String getImageType() {
        return imageType;
    }

    public QRCodeConfig setImageType(String imageType) {
        this.imageType = imageType;
        return this;
    }

    public Image getLogoImage() {
        return logoImage;
    }

    public QRCodeConfig setLogoImage(Image logoImage) {
        this.logoImage = logoImage;
        return this;
    }

    public QRCodeConfig setLogoImage(File logoFile) {
        return setLogoImage(ImageUtils.read(logoFile));
    }

    public QRCodeConfig setLogoImage(String logoPath) {
        return setLogoImage(new File(logoPath));
    }

    public float getLogoRatio() {
        return logoRatio;
    }

    public QRCodeConfig setLogoRatio(float logoRatio) {
        this.logoRatio = logoRatio;
        return this;
    }

    public double getLogoRadius() {
        return logoRadius;
    }

    public QRCodeConfig setLogoRadius(double logoRadius) {
        this.logoRadius = logoRadius;
        return this;
    }

    public String getText() {
        return text;
    }

    public QRCodeConfig setText(String text) {
        this.text = text;
        return this;
    }

    public int getFontSize() {
        return fontSize;
    }

    public QRCodeConfig setFontSize(int fontSize) {
        this.fontSize = fontSize;
        return this;
    }

    public Color getFontColor() {
        return fontColor;
    }

    public QRCodeConfig setFontColor(Color fontColor) {
        this.fontColor = fontColor;
        return this;
    }

    public int getTextLRMargin() {
        return textLRMargin;
    }

    public QRCodeConfig setTextLRMargin(int textLRMargin) {
        this.textLRMargin = textLRMargin;
        return this;
    }

    public int getTextTBMargin() {
        return textTBMargin;
    }

    public QRCodeConfig setTextTBMargin(int textTBMargin) {
        this.textTBMargin = textTBMargin;
        return this;
    }

    /**
     * 转换为 Zxing 的二维码配置
     *
     * @return 二维码配置
     */
    public Map<EncodeHintType, Object> toHints() {
        // 配置
        final Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        if (this.charset != null) {
            hints.put(EncodeHintType.CHARACTER_SET, charset);
        }
        if (this.errorCorrectionLevel != null) {
            hints.put(EncodeHintType.ERROR_CORRECTION, this.errorCorrectionLevel);
        }
        if (this.margin != null) {
            hints.put(EncodeHintType.MARGIN, this.margin);
        }
        return hints;
    }

    @Override
    public String toString() {
        return "QRCodeConfig [" +
                "content=" + content +
                ", width=" + width +
                ", height=" + height +
                ", foreColor=" + foreColor +
                ", backColor=" + backColor +
                ", margin=" + margin +
                ", charset=" + charset +
                ", logoRatio=" + logoRatio +
                ", text=" + text +
                ", fontSize=" + fontSize +
                ", fontColor=" + fontColor +
                ", textLRMargin=" + textLRMargin +
                ", textTBMargin=" + textTBMargin +
                "]";
    }
}
