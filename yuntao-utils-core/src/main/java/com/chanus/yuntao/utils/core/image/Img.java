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

import com.chanus.yuntao.utils.core.FileUtils;
import com.chanus.yuntao.utils.core.IOUtils;
import com.chanus.yuntao.utils.core.NumberUtils;
import com.chanus.yuntao.utils.core.StringUtils;

import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.*;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.nio.file.Path;

/**
 * 图像编辑工具类
 *
 * @author Chanus
 * @date 2020-06-24 11:33:45
 * @since 1.0.0
 */
public class Img implements Serializable {
    private static final long serialVersionUID = -5433451863790029050L;

    private final BufferedImage srcImage;
    private Image targetImage;
    /**
     * 目标图片文件格式，用于写出
     */
    private String targetImageType;
    /**
     * 计算 x,y 坐标的时候是否从中心做为原始坐标开始计算
     */
    private boolean positionBaseCentre = true;
    /**
     * 图片输出质量，用于压缩
     */
    private float quality = -1;

    /**
     * 从 Path 读取图片并开始处理
     *
     * @param imagePath 图片文件路径
     * @return {@link Img}
     */
    public static Img from(Path imagePath) {
        return from(imagePath.toFile());
    }

    /**
     * 从文件读取图片并开始处理
     *
     * @param imageFile 图片文件
     * @return {@link Img}
     */
    public static Img from(File imageFile) {
        return new Img(ImageUtils.read(imageFile));
    }

    /**
     * 从流读取图片并开始处理
     *
     * @param in 图片流
     * @return {@link Img}
     */
    public static Img from(InputStream in) {
        return new Img(ImageUtils.read(in));
    }

    /**
     * 从 ImageInputStream 取图片并开始处理
     *
     * @param imageStream 图片流
     * @return {@link Img}
     */
    public static Img from(ImageInputStream imageStream) {
        return new Img(ImageUtils.read(imageStream));
    }

    /**
     * 从 URL 取图片并开始处理
     *
     * @param imageUrl 图片 URL
     * @return {@link Img}
     */
    public static Img from(URL imageUrl) {
        return new Img(ImageUtils.read(imageUrl));
    }

    /**
     * 从 Image 取图片并开始处理
     *
     * @param image 图片
     * @return {@link Img}
     */
    public static Img from(Image image) {
        return new Img(ImageUtils.toBufferedImage(image));
    }

    /**
     * 构造
     *
     * @param srcImage 来源图片
     */
    public Img(BufferedImage srcImage) {
        this(srcImage, null);
    }

    /**
     * 构造
     *
     * @param srcImage        来源图片
     * @param targetImageType 目标图片类型
     */
    public Img(BufferedImage srcImage, String targetImageType) {
        this.srcImage = srcImage;
        if (null == targetImageType) {
            targetImageType = ImageUtils.IMAGE_TYPE_JPG;
        }
        this.targetImageType = targetImageType;
    }

    /**
     * 设置目标图片文件格式，用于写出
     *
     * @param imgType 图片格式
     * @return this
     * @see ImageUtils#IMAGE_TYPE_JPG
     * @see ImageUtils#IMAGE_TYPE_PNG
     */
    public Img setTargetImageType(String imgType) {
        this.targetImageType = imgType;
        return this;
    }

    /**
     * 计算 x,y 坐标的时候是否从中心做为原始坐标开始计算
     *
     * @param positionBaseCentre 是否从中心做为原始坐标开始计算
     * @return this
     */
    public Img setPositionBaseCentre(boolean positionBaseCentre) {
        this.positionBaseCentre = positionBaseCentre;
        return this;
    }

    /**
     * 设置图片输出质量，数字为0~1（不包括0和1）表示质量压缩比，除此数字外设置表示不压缩
     *
     * @param quality 质量，数字为0~1（不包括0和1）表示质量压缩比，除此数字外设置表示不压缩
     * @return this
     */
    public Img setQuality(double quality) {
        return setQuality((float) quality);
    }

    /**
     * 设置图片输出质量，数字为0~1（不包括0和1）表示质量压缩比，除此数字外设置表示不压缩
     *
     * @param quality 质量，数字为0~1（不包括0和1）表示质量压缩比，除此数字外设置表示不压缩
     * @return this
     */
    public Img setQuality(float quality) {
        if (quality > 0 && quality < 1) {
            this.quality = quality;
        } else {
            this.quality = 1;
        }
        return this;
    }

    /**
     * 缩放图像（按比例缩放）
     *
     * @param scale 缩放比例。比例大于1时为放大，小于1大于0为缩小
     * @return this
     */
    public Img scale(float scale) {
        if (scale < 0) {
            // 自动修正负数
            scale = -scale;
        }

        final Image srcImg = getValidSrcImg();

        // PNG图片特殊处理
        if (ImageUtils.IMAGE_TYPE_PNG.equals(this.targetImageType)) {
            final AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(scale, scale), null);
            this.targetImage = op.filter(ImageUtils.toBufferedImage(srcImg), null);
        } else {
            final String scaleStr = Float.toString(scale);
            // 缩放后的图片宽
            int width = NumberUtils.multiply(Integer.toString(srcImg.getWidth(null)), scaleStr).intValue();
            // 缩放后的图片高
            int height = NumberUtils.multiply(Integer.toString(srcImg.getHeight(null)), scaleStr).intValue();
            scale(width, height);
        }
        return this;
    }

    /**
     * 缩放图像（按长宽缩放）<br>
     * 注意：目标长宽与原图不成比例会变形
     *
     * @param width  目标宽度
     * @param height 目标高度
     * @return this
     */
    public Img scale(int width, int height) {
        final Image srcImg = getValidSrcImg();

        int srcHeight = srcImg.getHeight(null);
        int srcWidth = srcImg.getWidth(null);
        int scaleType;
        if (srcHeight == height && srcWidth == width) {
            // 源与目标长宽一致返回原图
            this.targetImage = srcImg;
            return this;
        } else if (srcHeight < height || srcWidth < width) {
            // 放大图片使用平滑模式
            scaleType = Image.SCALE_SMOOTH;
        } else {
            scaleType = Image.SCALE_DEFAULT;
        }

        double sx = NumberUtils.divide(width, srcWidth);
        double sy = NumberUtils.divide(height, srcHeight);

        if (ImageUtils.IMAGE_TYPE_PNG.equals(this.targetImageType)) {
            final AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(sx, sy), null);
            this.targetImage = op.filter(ImageUtils.toBufferedImage(srcImg), null);
        } else {
            this.targetImage = srcImg.getScaledInstance(width, height, scaleType);
        }

        return this;
    }

    /**
     * 等比缩放图像，此方法按照按照给定的长宽等比缩放图片，按照长宽缩放比最多的一边等比缩放，空白部分填充背景色，缩放后默认为 jpeg 格式
     *
     * @param width      缩放后的宽度
     * @param height     缩放后的高度
     * @param fixedColor 比例不对时补充的颜色，不补充为 <code>null</code>
     * @return this
     */
    public Img scale(int width, int height, Color fixedColor) {
        Image srcImage = getValidSrcImg();
        int srcHeight = srcImage.getHeight(null);
        int srcWidth = srcImage.getWidth(null);
        double heightRatio = NumberUtils.divide(height, srcHeight);
        double widthRatio = NumberUtils.divide(width, srcWidth);

        if (widthRatio == heightRatio) {
            // 长宽都按照相同比例缩放时，返回缩放后的图片
            scale(width, height);
        } else if (widthRatio < heightRatio) {
            // 宽缩放比例多就按照宽缩放
            scale(width, (int) (srcHeight * widthRatio));
        } else {
            // 否则按照高缩放
            scale((int) (srcWidth * heightRatio), height);
        }

        // 获取缩放后的新的宽和高
        srcImage = getValidSrcImg();
        srcHeight = srcImage.getHeight(null);
        srcWidth = srcImage.getWidth(null);

        final BufferedImage image = new BufferedImage(width, height, getTypeInt());
        Graphics2D g = image.createGraphics();

        // 设置背景
        if (null != fixedColor) {
            g.setBackground(fixedColor);
            g.clearRect(0, 0, width, height);
        }

        // 在中间贴图
        g.drawImage(srcImage, (width - srcWidth) / 2, (height - srcHeight) / 2, srcWidth, srcHeight, fixedColor, null);

        g.dispose();
        this.targetImage = image;
        return this;
    }

    /**
     * 图像切割（按指定起点坐标和宽高切割）
     *
     * @param rectangle 矩形对象，表示矩形区域的 x，y，width，height
     * @return this
     */
    public Img cut(Rectangle rectangle) {
        final Image srcImage = getValidSrcImg();
        fixRectangle(rectangle, srcImage.getWidth(null), srcImage.getHeight(null));

        final ImageFilter cropFilter = new CropImageFilter(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        final Image image = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(srcImage.getSource(), cropFilter));
        this.targetImage = ImageUtils.toBufferedImage(image);
        return this;
    }

    /**
     * 图像切割为圆形（按指定起点坐标和半径切割），填充满整个图片（直径取长宽最小值）
     *
     * @param x 原图的 x 坐标起始位置
     * @param y 原图的 y 坐标起始位置
     * @return this
     */
    public Img cut(int x, int y) {
        return cut(x, y, -1);
    }

    /**
     * 图像切割为圆形（按指定起点坐标和半径切割）
     *
     * @param x      原图的 x 坐标起始位置
     * @param y      原图的 y 坐标起始位置
     * @param radius 半径，小于0表示填充满整个图片（直径取长宽最小值）
     * @return this
     */
    public Img cut(int x, int y, int radius) {
        final Image srcImage = getValidSrcImg();
        final int width = srcImage.getWidth(null);
        final int height = srcImage.getHeight(null);

        // 计算直径
        final int diameter = radius > 0 ? radius * 2 : Math.min(width, height);
        final BufferedImage targetImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = targetImage.createGraphics();
        g.setClip(new Ellipse2D.Double(0, 0, diameter, diameter));

        if (this.positionBaseCentre) {
            x = x - width / 2 + diameter / 2;
            y = y - height / 2 + diameter / 2;
        }
        g.drawImage(srcImage, x, y, null);
        g.dispose();
        this.targetImage = targetImage;
        return this;
    }

    /**
     * 图片圆角处理
     *
     * @param arc 圆角弧度，0~1，为长宽占比
     * @return this
     */
    public Img round(double arc) {
        final Image srcImage = getValidSrcImg();
        final int width = srcImage.getWidth(null);
        final int height = srcImage.getHeight(null);

        // 通过弧度占比计算弧度
        arc = NumberUtils.multiply(arc, Math.min(width, height));

        final BufferedImage targetImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g2 = targetImage.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        // 抗锯齿
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fill(new RoundRectangle2D.Double(0, 0, width, height, arc, arc));
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(srcImage, 0, 0, null);
        g2.dispose();
        this.targetImage = targetImage;
        return this;
    }

    /**
     * 彩色转为黑白
     *
     * @return this
     */
    public Img gray() {
        final ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        this.targetImage = op.filter(ImageUtils.toBufferedImage(getValidSrcImg()), null);
        return this;
    }

    /**
     * 彩色转为黑白二值化图片
     *
     * @return this
     */
    public Img binary() {
        this.targetImage = ImageUtils.copyImage(getValidSrcImg(), BufferedImage.TYPE_BYTE_BINARY);
        return this;
    }

    /**
     * 给图片添加文字水印<br>
     * 此方法并不关闭流
     *
     * @param pressText 水印文字
     * @param color     水印的字体颜色
     * @param font      {@link Font} 字体相关信息
     * @param x         修正值。 默认在中间，偏移量相对于中间偏移
     * @param y         修正值。 默认在中间，偏移量相对于中间偏移
     * @param alpha     透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @return 处理后的图像
     */
    public Img pressText(String pressText, Color color, Font font, int x, int y, float alpha) {
        final BufferedImage targetImage = ImageUtils.toBufferedImage(getValidSrcImg());
        final Graphics2D g = targetImage.createGraphics();

        if (null == font) {
            // 默认字体
            font = new Font("Courier", Font.PLAIN, (int) (targetImage.getHeight() * 0.75));
        }

        // 抗锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(color);
        g.setFont(font);
        // 透明度
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        // 在指定坐标绘制水印文字
        final FontMetrics metrics = g.getFontMetrics(font);
        final int textLength = metrics.stringWidth(pressText);
        final int textHeight = metrics.getAscent() - metrics.getLeading() - metrics.getDescent();
        g.drawString(pressText, Math.abs(targetImage.getWidth() - textLength) / 2 + x, Math.abs(targetImage.getHeight() + textHeight) / 2 + y);
        g.dispose();
        this.targetImage = targetImage;

        return this;
    }

    /**
     * 给图片添加图片水印
     *
     * @param pressImg 水印图片，可以使用 {@link javax.imageio.ImageIO#read(File)} 方法读取文件
     * @param x        修正值。 默认在中间，偏移量相对于中间偏移
     * @param y        修正值。 默认在中间，偏移量相对于中间偏移
     * @param alpha    透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @return this
     */
    public Img pressImage(Image pressImg, int x, int y, float alpha) {
        final int pressImgWidth = pressImg.getWidth(null);
        final int pressImgHeight = pressImg.getHeight(null);

        return pressImage(pressImg, new Rectangle(x, y, pressImgWidth, pressImgHeight), alpha);
    }

    /**
     * 给图片添加图片水印
     *
     * @param pressImg  水印图片，可以使用 {@link javax.imageio.ImageIO#read(File)} 方法读取文件
     * @param rectangle 矩形对象，表示矩形区域的 x，y，width，height，x,y 从背景图片中心计算
     * @param alpha     透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @return this
     */
    public Img pressImage(Image pressImg, Rectangle rectangle, float alpha) {
        final Image targetImg = getValidSrcImg();

        fixRectangle(rectangle, targetImg.getWidth(null), targetImg.getHeight(null));
        this.targetImage = draw(ImageUtils.toBufferedImage(targetImg), pressImg, rectangle, alpha);
        return this;
    }

    /**
     * 旋转图片为指定角度
     *
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    public Img rotate(int degree) {
        final Image image = getValidSrcImg();
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        final Rectangle rectangle = calcRotatedSize(width, height, degree);
        final BufferedImage targetImg = new BufferedImage(rectangle.width, rectangle.height, getTypeInt());
        Graphics2D graphics2d = targetImg.createGraphics();
        // 抗锯齿
        graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        // 从中心旋转
        graphics2d.translate((rectangle.width - width) / 2D, (rectangle.height - height) / 2D);
        graphics2d.rotate(Math.toRadians(degree), width / 2D, height / 2D);
        graphics2d.drawImage(image, 0, 0, null);
        graphics2d.dispose();
        this.targetImage = targetImg;
        return this;
    }

    /**
     * 水平翻转图像
     *
     * @return this
     */
    public Img flip() {
        final Image image = getValidSrcImg();
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        final BufferedImage targetImg = new BufferedImage(width, height, getTypeInt());
        Graphics2D graphics2d = targetImg.createGraphics();
        graphics2d.drawImage(image, 0, 0, width, height, width, 0, 0, height, null);
        graphics2d.dispose();
        this.targetImage = targetImg;
        return this;
    }

    /**
     * 获取处理过的图片
     *
     * @return 处理过的图片
     */
    public Image getImg() {
        return this.targetImage;
    }

    /**
     * 写出图像
     *
     * @param out 写出到的目标流
     * @return 是否成功写出，如果返回 false 表示未找到合适的 Writer
     */
    public boolean write(OutputStream out) {
        return write(ImageUtils.getImageOutputStream(out));
    }

    /**
     * 写出图像为 PNG 格式
     *
     * @param targetImageStream 写出到的目标流
     * @return 是否成功写出，如果返回 false 表示未找到合适的 Writer
     */
    public boolean write(ImageOutputStream targetImageStream) {
        final Image targetImage = (this.targetImage == null) ? this.srcImage : this.targetImage;

        return ImageUtils.write(targetImage, this.targetImageType, targetImageStream, this.quality);
    }

    /**
     * 写出图像为目标文件扩展名对应的格式
     *
     * @param targetFile 目标文件
     * @return 是否成功写出，如果返回 false 表示未找到合适的 Writer
     */
    public boolean write(File targetFile) {
        final String formatName = FileUtils.getFileExtension(targetFile);
        if (StringUtils.isNotBlank(formatName))
            this.targetImageType = formatName;

        if (targetFile.exists())
            targetFile.delete();

        ImageOutputStream out = null;
        try {
            out = ImageUtils.getImageOutputStream(targetFile);
            return write(out);
        } finally {
            IOUtils.close(out);
        }
    }

    /**
     * 将图片绘制在背景上
     *
     * @param backgroundImg 背景图片
     * @param img           要绘制的图片
     * @param rectangle     矩形对象，表示矩形区域的 x，y，width，height，x,y 从背景图片中心计算
     * @return 绘制后的背景
     */
    private static BufferedImage draw(BufferedImage backgroundImg, Image img, Rectangle rectangle, float alpha) {
        final Graphics2D g = backgroundImg.createGraphics();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        g.drawImage(img, rectangle.x, rectangle.y, rectangle.width, rectangle.height, null); // 绘制切割后的图
        g.dispose();
        return backgroundImg;
    }

    /**
     * 获取 int 类型的图片类型
     *
     * @return 图片类型
     * @see BufferedImage#TYPE_INT_ARGB
     * @see BufferedImage#TYPE_INT_RGB
     */
    private int getTypeInt() {
        return ImageUtils.IMAGE_TYPE_PNG.equals(this.targetImageType) ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
    }

    /**
     * 获取有效的源图片，首先检查上一次处理的结果图片，如无则使用用户传入的源图片
     *
     * @return 有效的源图片
     */
    private Image getValidSrcImg() {
        return this.targetImage != null ? this.targetImage : this.srcImage;
    }

    /**
     * 修正矩形框位置，如果 {@link Img#setPositionBaseCentre(boolean)} 设为 {@code true}，则坐标修正为基于图形中心，否则基于左上角
     *
     * @param rectangle  矩形
     * @param baseWidth  参考宽
     * @param baseHeight 参考高
     * @return 修正后的 {@link Rectangle}
     */
    private Rectangle fixRectangle(Rectangle rectangle, int baseWidth, int baseHeight) {
        if (this.positionBaseCentre) {
            // 修正图片位置从背景的中心计算
            rectangle.setLocation(//
                    rectangle.x + (Math.abs(baseWidth - rectangle.width) / 2), //
                    rectangle.y + (Math.abs(baseHeight - rectangle.height) / 2)//
            );
        }
        return rectangle;
    }

    /**
     * 计算旋转后的图片尺寸
     *
     * @param width  宽度
     * @param height 高度
     * @param degree 旋转角度
     * @return 计算后目标尺寸
     */
    private static Rectangle calcRotatedSize(int width, int height, int degree) {
        if (degree < 0) {
            // 负数角度转换为正数角度
            degree += 360;
        }
        if (degree >= 90) {
            if (degree / 90 % 2 == 1) {
                int temp = height;
                //noinspection SuspiciousNameCombination
                height = width;
                width = temp;
            }
            degree = degree % 90;
        }
        double r = Math.sqrt(height * height + width * width) / 2;
        double len = 2 * Math.sin(Math.toRadians(degree) / 2) * r;
        double angel_alpha = (Math.PI - Math.toRadians(degree)) / 2;
        double angel_dalta_width = Math.atan((double) height / width);
        double angel_dalta_height = Math.atan((double) width / height);
        int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha - angel_dalta_width));
        int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha - angel_dalta_height));
        int des_width = width + len_dalta_width * 2;
        int des_height = height + len_dalta_height * 2;

        return new Rectangle(des_width, des_height);
    }
}
