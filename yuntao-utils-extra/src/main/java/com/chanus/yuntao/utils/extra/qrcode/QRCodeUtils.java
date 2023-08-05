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

import com.chanus.yuntao.utils.core.StringUtils;
import com.chanus.yuntao.utils.core.image.ImageUtils;
import com.chanus.yuntao.utils.core.image.Img;
import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.EnumMap;
import java.util.Map;

/**
 * 二维码工具类，基于 zxing 库
 *
 * @author Chanus
 * @date 2020-06-24 17:35:27
 * @since 1.0.0
 */
public class QRCodeUtils {
    private QRCodeUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 生成 PNG 格式的二维码图片，以 byte[] 形式表示
     *
     * @param content 内容
     * @param width   宽度
     * @param height  高度
     * @return 图片的 byte[]
     */
    public static byte[] generatePng(String content, int width, int height) {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        generate(content, width, height, ImageUtils.IMAGE_TYPE_PNG, out);
        return out.toByteArray();
    }

    /**
     * 生成 PNG 格式的二维码图片，以 byte[] 形式表示
     *
     * @param config 二维码配置
     * @return 图片的 byte[]
     */
    public static byte[] generatePng(QRCodeConfig config) {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        generate(config.setImageType(ImageUtils.IMAGE_TYPE_PNG), out);
        return out.toByteArray();
    }

    /**
     * 生成二维码到文件，二维码图片格式取决于文件的扩展名
     *
     * @param content    文本内容
     * @param width      宽度
     * @param height     高度
     * @param targetFile 目标文件，扩展名决定输出格式
     * @return 目标文件
     */
    public static File generate(String content, int width, int height, File targetFile) {
        final BufferedImage image = generate(content, width, height);
        ImageUtils.write(image, targetFile);
        return targetFile;
    }

    /**
     * 生成二维码到文件，二维码图片格式取决于文件的扩展名
     *
     * @param config     二维码配置
     * @param targetFile 目标文件，扩展名决定输出格式
     * @return 目标文件
     */
    public static File generate(QRCodeConfig config, File targetFile) {
        final BufferedImage image = generate(config);
        ImageUtils.write(image, targetFile);
        return targetFile;
    }

    /**
     * 生成二维码到文件，二维码图片格式取决于文件的扩展名
     *
     * @param content        文本内容
     * @param width          宽度
     * @param height         高度
     * @param targetFilePath 目标文件路径，扩展名决定输出格式
     * @return 目标文件
     */
    public static File generate(String content, int width, int height, String targetFilePath) {
        final BufferedImage image = generate(content, width, height);
        File targetFile = new File(targetFilePath);
        ImageUtils.write(image, targetFile);
        return targetFile;
    }

    /**
     * 生成二维码到文件，二维码图片格式取决于文件的扩展名
     *
     * @param config         二维码配置
     * @param targetFilePath 目标文件路径，扩展名决定输出格式
     * @return 目标文件
     */
    public static File generate(QRCodeConfig config, String targetFilePath) {
        final BufferedImage image = generate(config);
        File targetFile = new File(targetFilePath);
        ImageUtils.write(image, targetFile);
        return targetFile;
    }

    /**
     * 生成二维码到输出流
     *
     * @param content   文本内容
     * @param width     宽度
     * @param height    高度
     * @param imageType 图片类型（图片扩展名），见 {@link ImageUtils}
     * @param out       目标流
     */
    public static void generate(String content, int width, int height, String imageType, OutputStream out) {
        final BufferedImage image = generate(content, width, height);
        ImageUtils.write(image, imageType, out);
    }

    /**
     * 生成二维码到输出流
     *
     * @param config 二维码配置
     * @param out    目标流
     */
    public static void generate(QRCodeConfig config, OutputStream out) {
        final BufferedImage image = generate(config);
        ImageUtils.write(image, config.getImageType(), out);
    }

    /**
     * 生成二维码图片
     *
     * @param content 文本内容
     * @param width   宽度
     * @param height  高度
     * @return 二维码图片（黑白）
     */
    public static BufferedImage generate(String content, int width, int height) {
        return generate(new QRCodeConfig(content, width, height));
    }

    /**
     * 生成二维码图片
     *
     * @param config 二维码配置
     * @return 二维码图片
     */
    public static BufferedImage generate(QRCodeConfig config) {
        return generate(BarcodeFormat.QR_CODE, config);
    }

    /**
     * 生成二维码或条形码图片
     *
     * @param format  格式，可选二维码或者条形码
     * @param content 文本内容
     * @param width   宽度
     * @param height  高度
     * @return 二维码图片（黑白）
     */
    public static BufferedImage generate(BarcodeFormat format, String content, int width, int height) {
        return generate(format, new QRCodeConfig(content, width, height));
    }

    /**
     * 生成二维码或条形码图片<br>
     * 只有生成二维码时 QRCodeConfig 中的图片才有效
     *
     * @param format 格式，可选二维码、条形码等
     * @param config 二维码配置
     * @return 二维码图片（黑白）
     */
    public static BufferedImage generate(BarcodeFormat format, QRCodeConfig config) {
        final BitMatrix bitMatrix = encode(format, config);
        BufferedImage image = toImage(bitMatrix, config.getForeColor(), config.getBackColor());

        int qrWidth = image.getWidth();
        int qrHeight = image.getHeight();

        // 处理logo图片
        final Image logoImage = config.getLogoImage();
        if (logoImage != null && BarcodeFormat.QR_CODE == format) {
            // 只有二维码可以贴图
            final float logoRatio = config.getLogoRatio();
            int width;// logo宽度
            int height;// logo高度
            // 按照最短的边做比例缩放
            if (qrWidth < qrHeight) {
                width = (int) (qrWidth * logoRatio);
                height = logoImage.getHeight(null) * width / logoImage.getWidth(null);
            } else {
                height = (int) (qrHeight * logoRatio);
                width = logoImage.getWidth(null) * height / logoImage.getHeight(null);
            }

            Img.from(image).pressImage(Img.from(logoImage).round(config.getLogoRadius()).getImg(), new Rectangle(width, height), 1);
        }

        // 处理下方文字
        String text = config.getText();
        if (StringUtils.isNotBlank(text)) {
            Graphics g = image.getGraphics();
            // 文字起始位置，即文字左边距
            int textLRMargin = config.getTextLRMargin();
            // 文字与二维码之间的间距
            int textTBMargin = config.getTextTBMargin();
            // 设置文字字体
            Font font = new Font("宋体", Font.PLAIN, config.getFontSize());
            int fontHeight = g.getFontMetrics(font).getHeight();
            // 计算需要多少行
            int lineNum = 1;
            int currentLineLen = 0;
            int textLineMaxLen = config.getWidth() - (textLRMargin * 2);
            for (int i = 0; i < text.length(); i++) {
                String c = text.substring(i, i + 1);
                int charWidth = g.getFontMetrics(font).stringWidth(c);
                if (currentLineLen + charWidth > textLineMaxLen) {
                    lineNum++;
                    currentLineLen = charWidth;
                    continue;
                }
                currentLineLen += charWidth;
                if (lineNum == 1 && i == text.length() - 1) {
                    // 不存在换行时
                    textLRMargin = (config.getWidth() - currentLineLen) / 2;
                }
            }
            int totalFontHeight = fontHeight * lineNum;
            BufferedImage bufferedImage = new BufferedImage(qrWidth, qrHeight + totalFontHeight + (textTBMargin * 2),
                    BufferedImage.TYPE_INT_RGB);
            Graphics g1 = bufferedImage.getGraphics();
            if (totalFontHeight + (textTBMargin * 2) > 0) {
                g1.setColor(Color.WHITE);
                g1.fillRect(0, qrHeight, qrWidth, totalFontHeight + (textTBMargin * 2));
            }
            g1.setColor(config.getFontColor());
            g1.setFont(font);
            g1.drawImage(image, 0, 0, null);
            qrHeight = config.getHeight() + textTBMargin;
            currentLineLen = 0;
            int currentLineIndex = 0;
            int baseLo = g1.getFontMetrics().getAscent();
            for (int i = 0; i < text.length(); i++) {
                String c = text.substring(i, i + 1);
                int charWidth = g.getFontMetrics(font).stringWidth(c);
                if (currentLineLen + charWidth > textLineMaxLen) {
                    currentLineIndex++;
                    currentLineLen = 0;
                    g1.drawString(c, currentLineLen + textLRMargin, qrHeight + baseLo + (fontHeight * currentLineIndex));
                    currentLineLen = charWidth;
                    continue;
                }
                g1.drawString(c, currentLineLen + textLRMargin, qrHeight + baseLo + (fontHeight * currentLineIndex));
                currentLineLen += charWidth;
            }
            g.dispose();
            g1.dispose();

            image = bufferedImage;
        }

        return image;
    }

    /**
     * 将文本内容编码为条形码或二维码<br>
     * 只有生成二维码时 QRCodeConfig 中的图片才有效
     *
     * @param format 格式枚举
     * @param config 二维码配置信息
     * @return {@link BitMatrix}
     */
    public static BitMatrix encode(BarcodeFormat format, QRCodeConfig config) {
        final MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        if (config == null) {
            // 默认配置
            config = new QRCodeConfig();
        }
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = multiFormatWriter.encode(config.getContent(), format, config.getWidth(), config.getHeight(), config.toHints());
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return bitMatrix;
    }

    /**
     * 将文本内容编码为条形码或二维码<br>
     * 只有生成二维码时 QRCodeConfig 中的图片才有效
     *
     * @param format  格式枚举
     * @param content 文本内容
     * @param width   宽度
     * @param height  高度
     * @return {@link BitMatrix}
     */
    public static BitMatrix encode(BarcodeFormat format, String content, int width, int height) {
        return encode(format, new QRCodeConfig(content, width, height));
    }

    /**
     * 将文本内容编码为二维码
     *
     * @param config 二维码配置信息
     * @return {@link BitMatrix}
     */
    public static BitMatrix encode(QRCodeConfig config) {
        return encode(BarcodeFormat.QR_CODE, config);
    }

    /**
     * 将文本内容编码为二维码
     *
     * @param content 文本内容
     * @param width   宽度
     * @param height  高度
     * @return {@link BitMatrix}
     */
    public static BitMatrix encode(String content, int width, int height) {
        return encode(BarcodeFormat.QR_CODE, content, width, height);
    }

    /**
     * 解码二维码图片为文本
     *
     * @param qrCodeInputstream 二维码输入流
     * @return 解码文本
     */
    public static String decode(InputStream qrCodeInputstream) {
        return decode(ImageUtils.read(qrCodeInputstream));
    }

    /**
     * 解码二维码图片为文本
     *
     * @param qrCodeFile 二维码文件
     * @return 解码文本
     */
    public static String decode(File qrCodeFile) {
        return decode(ImageUtils.read(qrCodeFile));
    }

    /**
     * 解码二维码图片为文本
     *
     * @param qrCodeFilePath 二维码文件路径
     * @return 解码文本
     */
    public static String decode(String qrCodeFilePath) {
        return decode(ImageUtils.read(qrCodeFilePath));
    }

    /**
     * 将二维码图片解码为文本
     *
     * @param image {@link Image} 二维码图片
     * @return 解码后的文本
     */
    public static String decode(Image image) {
        return decode(image, true, false);
    }

    /**
     * 将二维码图片解码为文本
     *
     * @param image         {@link Image} 二维码图片
     * @param isTryHarder   是否优化精度
     * @param isPureBarcode 是否使用复杂模式，扫描带 logo 的二维码设为 true
     * @return 解码后的文本
     */
    public static String decode(Image image, boolean isTryHarder, boolean isPureBarcode) {
        return decode(image, isTryHarder, isPureBarcode, QRCodeConfig.CHARSET);
    }

    /**
     * 将二维码图片解码为文本
     *
     * @param image         {@link Image} 二维码图片
     * @param isTryHarder   是否优化精度
     * @param isPureBarcode 是否使用复杂模式，扫描带 logo 的二维码设为 true
     * @param charset       编码格式
     * @return 解码后的文本
     */
    public static String decode(Image image, boolean isTryHarder, boolean isPureBarcode, String charset) {
        final MultiFormatReader formatReader = new MultiFormatReader();

        final LuminanceSource source = new BufferedImageLuminanceSource(ImageUtils.toBufferedImage(image));
        final BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));

        final Map<DecodeHintType, Object> hints = new EnumMap<>(DecodeHintType.class);
        hints.put(DecodeHintType.CHARACTER_SET, charset);
        // 优化精度
        hints.put(DecodeHintType.TRY_HARDER, isTryHarder);
        // 复杂模式，开启PURE_BARCODE模式
        hints.put(DecodeHintType.PURE_BARCODE, isPureBarcode);
        Result result;
        try {
            result = formatReader.decode(binaryBitmap, hints);
        } catch (NotFoundException e) {
            // 报错尝试关闭复杂模式
            hints.remove(DecodeHintType.PURE_BARCODE);
            try {
                result = formatReader.decode(binaryBitmap, hints);
            } catch (NotFoundException notFoundException) {
                throw new RuntimeException("qr_code decode fail.", notFoundException);
            }
        }
        return result.getText();
    }

    /**
     * BitMatrix 转 BufferedImage
     *
     * @param matrix    BitMatrix
     * @param foreColor 前景色
     * @param backColor 背景色（null 表示透明背景）
     * @return BufferedImage
     */
    public static BufferedImage toImage(BitMatrix matrix, int foreColor, Integer backColor) {
        final int width = matrix.getWidth();
        final int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, backColor == null ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (matrix.get(x, y)) {
                    image.setRGB(x, y, foreColor);
                } else if (null != backColor) {
                    image.setRGB(x, y, backColor);
                }
            }
        }
        return image;
    }
}
