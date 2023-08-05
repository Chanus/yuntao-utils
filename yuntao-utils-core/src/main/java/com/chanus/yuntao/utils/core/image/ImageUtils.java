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

import com.chanus.yuntao.utils.core.*;

import javax.imageio.*;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URL;
import java.util.Base64;
import java.util.Iterator;

/**
 * 图片处理工具类
 *
 * @author Chanus
 * @date 2020-06-24 11:44:42
 * @since 1.0.0
 */
public class ImageUtils {
    /**
     * 图形交换格式
     */
    public static final String IMAGE_TYPE_GIF = "gif";
    /**
     * 联合照片专家组
     */
    public static final String IMAGE_TYPE_JPG = "jpg";
    /**
     * 联合照片专家组
     */
    public static final String IMAGE_TYPE_JPEG = "jpeg";
    /**
     * 英文 Bitmap（位图）的简写，它是 Windows 操作系统中的标准图像文件格式
     */
    public static final String IMAGE_TYPE_BMP = "bmp";
    /**
     * 可移植网络图形
     */
    public static final String IMAGE_TYPE_PNG = "png";
    /**
     * Photoshop 的专用格式 Photoshop
     */
    public static final String IMAGE_TYPE_PSD = "psd";

    private ImageUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 缩放图像（按比例缩放），目标文件的扩展名决定目标文件类型
     *
     * @param srcImageFile  源图像文件
     * @param destImageFile 缩放后的图像文件，扩展名决定目标类型
     * @param scale         缩放比例。比例大于1时为放大，小于1大于0为缩小
     */
    public static void scale(File srcImageFile, File destImageFile, float scale) {
        scale(read(srcImageFile), destImageFile, scale);
    }

    /**
     * 缩放图像（按比例缩放）<br>
     * 缩放后默认为 jpeg 格式，此方法并不关闭流
     *
     * @param srcStream  源图像来源流
     * @param destStream 缩放后的图像写出到的流
     * @param scale      缩放比例。比例大于1时为放大，小于1大于0为缩小
     */
    public static void scale(InputStream srcStream, OutputStream destStream, float scale) {
        scale(read(srcStream), destStream, scale);
    }

    /**
     * 缩放图像（按比例缩放）<br>
     * 缩放后默认为 jpeg 格式，此方法并不关闭流
     *
     * @param srcStream  源图像来源流
     * @param destStream 缩放后的图像写出到的流
     * @param scale      缩放比例。比例大于1时为放大，小于1大于0为缩小
     */
    public static void scale(ImageInputStream srcStream, ImageOutputStream destStream, float scale) {
        scale(read(srcStream), destStream, scale);
    }

    /**
     * 缩放图像（按比例缩放）<br>
     * 缩放后默认为 jpeg 格式，此方法并不关闭流
     *
     * @param srcImg   源图像来源流
     * @param destFile 缩放后的图像写出到的流
     * @param scale    缩放比例。比例大于1时为放大，小于1大于0为缩小
     */
    public static void scale(Image srcImg, File destFile, float scale) {
        Img.from(srcImg).setTargetImageType(FileUtils.getFileExtension(destFile)).scale(scale).write(destFile);
    }

    /**
     * 缩放图像（按比例缩放）<br>
     * 缩放后默认为 jpeg 格式，此方法并不关闭流
     *
     * @param srcImg 源图像来源流
     * @param out    缩放后的图像写出到的流
     * @param scale  缩放比例。比例大于1时为放大，小于1大于0为缩小
     */
    public static void scale(Image srcImg, OutputStream out, float scale) {
        scale(srcImg, getImageOutputStream(out), scale);
    }

    /**
     * 缩放图像（按比例缩放）<br>
     * 缩放后默认为 jpeg 格式，此方法并不关闭流
     *
     * @param srcImg          源图像来源流
     * @param destImageStream 缩放后的图像写出到的流
     * @param scale           缩放比例。比例大于1时为放大，小于1大于0为缩小
     */
    public static void scale(Image srcImg, ImageOutputStream destImageStream, float scale) {
        writeJpg(scale(srcImg, scale), destImageStream);
    }

    /**
     * 缩放图像（按比例缩放）
     *
     * @param srcImg 源图像来源流
     * @param scale  缩放比例。比例大于1时为放大，小于1大于0为缩小
     * @return {@link Image}
     */
    public static Image scale(Image srcImg, float scale) {
        return Img.from(srcImg).scale(scale).getImg();
    }

    /**
     * 缩放图像（按长宽缩放）<br>
     * 注意：目标长宽与原图不成比例会变形
     *
     * @param srcImg 源图像来源流
     * @param width  目标宽度
     * @param height 目标高度
     * @return {@link Image}
     */
    public static Image scale(Image srcImg, int width, int height) {
        return Img.from(srcImg).scale(width, height).getImg();
    }

    /**
     * 缩放图像（按高度和宽度缩放）<br>
     * 缩放后默认格式与源图片相同，无法识别原图片默认 JPG
     *
     * @param srcImageFile  源图像文件地址
     * @param destImageFile 缩放后的图像地址
     * @param width         缩放后的宽度
     * @param height        缩放后的高度
     * @param fixedColor    补充的颜色，不补充为 <code>null</code>
     */
    public static void scale(File srcImageFile, File destImageFile, int width, int height, Color fixedColor) {
        Img.from(srcImageFile)
                .setTargetImageType(FileUtils.getFileExtension(destImageFile))
                .scale(width, height, fixedColor)
                .write(destImageFile);
    }

    /**
     * 缩放图像（按高度和宽度缩放）<br>
     * 缩放后默认为 jpeg 格式，此方法并不关闭流
     *
     * @param srcStream  源图像流
     * @param destStream 缩放后的图像目标流
     * @param width      缩放后的宽度
     * @param height     缩放后的高度
     * @param fixedColor 比例不对时补充的颜色，不补充为 <code>null</code>
     */
    public static void scale(InputStream srcStream, OutputStream destStream, int width, int height, Color fixedColor) {
        scale(read(srcStream), getImageOutputStream(destStream), width, height, fixedColor);
    }

    /**
     * 缩放图像（按高度和宽度缩放）<br>
     * 缩放后默认为 jpeg 格式，此方法并不关闭流
     *
     * @param srcStream  源图像流
     * @param destStream 缩放后的图像目标流
     * @param width      缩放后的宽度
     * @param height     缩放后的高度
     * @param fixedColor 比例不对时补充的颜色，不补充为 <code>null</code>
     */
    public static void scale(ImageInputStream srcStream, ImageOutputStream destStream, int width, int height, Color fixedColor) {
        scale(read(srcStream), destStream, width, height, fixedColor);
    }

    /**
     * 缩放图像（按高度和宽度缩放）<br>
     * 缩放后默认为 jpeg 格式，此方法并不关闭流
     *
     * @param srcImage        源图像
     * @param destImageStream 缩放后的图像目标流
     * @param width           缩放后的宽度
     * @param height          缩放后的高度
     * @param fixedColor      比例不对时补充的颜色，不补充为 <code>null</code>
     */
    public static void scale(Image srcImage, ImageOutputStream destImageStream, int width, int height, Color fixedColor) {
        writeJpg(scale(srcImage, width, height, fixedColor), destImageStream);
    }

    /**
     * 缩放图像（按高度和宽度缩放）<br>
     * 缩放后默认为 jpeg 格式
     *
     * @param srcImage   源图像
     * @param width      缩放后的宽度
     * @param height     缩放后的高度
     * @param fixedColor 比例不对时补充的颜色，不补充为 <code>null</code>
     * @return {@link Image}
     */
    public static Image scale(Image srcImage, int width, int height, Color fixedColor) {
        return Img.from(srcImage).scale(width, height, fixedColor).getImg();
    }

    /**
     * 图像切割（按指定起点坐标和宽高切割）
     *
     * @param srcImgFile  源图像文件
     * @param destImgFile 切片后的图像文件
     * @param rectangle   矩形对象，表示矩形区域的 x，y，width，height
     */
    public static void cut(File srcImgFile, File destImgFile, Rectangle rectangle) {
        cut(read(srcImgFile), destImgFile, rectangle);
    }

    /**
     * 图像切割（按指定起点坐标和宽高切割），此方法并不关闭流
     *
     * @param srcStream  源图像流
     * @param destStream 切片后的图像输出流
     * @param rectangle  矩形对象，表示矩形区域的 x，y，width，height
     */
    public static void cut(InputStream srcStream, OutputStream destStream, Rectangle rectangle) {
        cut(read(srcStream), destStream, rectangle);
    }

    /**
     * 图像切割（按指定起点坐标和宽高切割），此方法并不关闭流
     *
     * @param srcStream  源图像流
     * @param destStream 切片后的图像输出流
     * @param rectangle  矩形对象，表示矩形区域的 x，y，width，height
     */
    public static void cut(ImageInputStream srcStream, ImageOutputStream destStream, Rectangle rectangle) {
        cut(read(srcStream), destStream, rectangle);
    }

    /**
     * 图像切割（按指定起点坐标和宽高切割），此方法并不关闭流
     *
     * @param srcImage  源图像
     * @param destFile  输出的文件
     * @param rectangle 矩形对象，表示矩形区域的 x，y，width，height
     */
    public static void cut(Image srcImage, File destFile, Rectangle rectangle) {
        write(cut(srcImage, rectangle), destFile);
    }

    /**
     * 图像切割（按指定起点坐标和宽高切割），此方法并不关闭流
     *
     * @param srcImage  源图像
     * @param out       切片后的图像输出流
     * @param rectangle 矩形对象，表示矩形区域的 x，y，width，height
     */
    public static void cut(Image srcImage, OutputStream out, Rectangle rectangle) {
        cut(srcImage, getImageOutputStream(out), rectangle);
    }

    /**
     * 图像切割（按指定起点坐标和宽高切割），此方法并不关闭流
     *
     * @param srcImage        源图像
     * @param destImageStream 切片后的图像输出流
     * @param rectangle       矩形对象，表示矩形区域的 x，y，width，height
     */
    public static void cut(Image srcImage, ImageOutputStream destImageStream, Rectangle rectangle) {
        writeJpg(cut(srcImage, rectangle), destImageStream);
    }

    /**
     * 图像切割（按指定起点坐标和宽高切割）
     *
     * @param srcImage  源图像
     * @param rectangle 矩形对象，表示矩形区域的 x，y，width，height
     * @return {@link BufferedImage}
     */
    public static Image cut(Image srcImage, Rectangle rectangle) {
        return Img.from(srcImage).setPositionBaseCentre(false).cut(rectangle).getImg();
    }

    /**
     * 图像切割（按指定起点坐标和宽高切割），填充满整个图片（直径取长宽最小值）
     *
     * @param srcImage 源图像
     * @param x        原图的 x 坐标起始位置
     * @param y        原图的 y 坐标起始位置
     * @return {@link Image}
     */
    public static Image cut(Image srcImage, int x, int y) {
        return cut(srcImage, x, y, -1);
    }

    /**
     * 图像切割（按指定起点坐标和宽高切割）
     *
     * @param srcImage 源图像
     * @param x        原图的 x 坐标起始位置
     * @param y        原图的 y 坐标起始位置
     * @param radius   半径，小于0表示填充满整个图片（直径取长宽最小值）
     * @return {@link Image}
     */
    public static Image cut(Image srcImage, int x, int y, int radius) {
        return Img.from(srcImage).cut(x, y, radius).getImg();
    }

    /**
     * 图像切片（指定切片的宽度和高度）
     *
     * @param srcImageFile 源图像
     * @param descDir      切片目标文件夹
     * @param destWidth    目标切片宽度。默认200
     * @param destHeight   目标切片高度。默认150
     */
    public static void slice(File srcImageFile, File descDir, int destWidth, int destHeight) {
        slice(read(srcImageFile), descDir, destWidth, destHeight);
    }

    /**
     * 图像切片（指定切片的宽度和高度）
     *
     * @param srcImage   源图像
     * @param descDir    切片目标文件夹
     * @param destWidth  目标切片宽度。默认200
     * @param destHeight 目标切片高度。默认150
     */
    public static void slice(Image srcImage, File descDir, int destWidth, int destHeight) {
        if (srcImage == null) {
            return;
        }
        if (destWidth <= 0) {
            // 切片宽度
            destWidth = 200;
        }
        if (destHeight <= 0) {
            // 切片高度
            destHeight = 150;
        }
        // 源图宽度
        int srcWidth = srcImage.getWidth(null);
        // 源图高度
        int srcHeight = srcImage.getHeight(null);

        try {
            FileUtils.mkdirs(descDir);

            if (srcWidth > destWidth && srcHeight > destHeight) {
                // 切片横向数量
                int cols;
                // 切片纵向数量
                int rows;
                // 计算切片的横向和纵向数量
                if (srcWidth % destWidth == 0) {
                    cols = srcWidth / destWidth;
                } else {
                    cols = (int) Math.floor((double) srcWidth / destWidth) + 1;
                }
                if (srcHeight % destHeight == 0) {
                    rows = srcHeight / destHeight;
                } else {
                    rows = (int) Math.floor((double) srcHeight / destHeight) + 1;
                }
                // 循环建立切片
                Image tag;
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        // 四个参数分别为图像起点坐标和宽高
                        // 即: CropImageFilter(int x,int y,int width,int height)
                        tag = cut(srcImage, new Rectangle(j * destWidth, i * destHeight, destWidth, destHeight));
                        // 输出为文件
                        ImageIO.write(toRenderedImage(tag), IMAGE_TYPE_JPEG, new File(descDir, "_r" + i + "_c" + j + ".jpg"));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 图像切割（指定切片的行数和列数）
     *
     * @param srcImageFile 源图像文件
     * @param destDir      切片目标文件夹
     * @param rows         目标切片行数。默认2，必须是范围 [1, 20] 之内
     * @param cols         目标切片列数。默认2，必须是范围 [1, 20] 之内
     */
    public static void sliceByRowsAndCols(File srcImageFile, File destDir, int rows, int cols) {
        try {
            sliceByRowsAndCols(ImageIO.read(srcImageFile), destDir, rows, cols);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 图像切割（指定切片的行数和列数）
     *
     * @param srcImage 源图像
     * @param destDir  切片目标文件夹
     * @param rows     目标切片行数。默认2，必须是范围 [1, 20] 之内
     * @param cols     目标切片列数。默认2，必须是范围 [1, 20] 之内
     */
    public static void sliceByRowsAndCols(Image srcImage, File destDir, int rows, int cols) {
        FileUtils.mkdirs(destDir);
        try {
            if (rows <= 0 || rows > 20) {
                // 切片行数
                rows = 2;
            }
            if (cols <= 0 || cols > 20) {
                // 切片列数
                cols = 2;
            }
            // 读取源图像
            final Image bi = toBufferedImage(srcImage);
            // 源图宽度
            int srcWidth = bi.getWidth(null);
            // 源图高度
            int srcHeight = bi.getHeight(null);

            // 每张切片的宽度
            int destWidth = NumberUtils.partValue(srcWidth, cols);
            // 每张切片的高度
            int destHeight = NumberUtils.partValue(srcHeight, rows);

            // 循环建立切片
            Image tag;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    tag = cut(bi, new Rectangle(j * destWidth, i * destHeight, destWidth, destHeight));
                    // 输出为文件
                    ImageIO.write(toRenderedImage(tag), IMAGE_TYPE_JPEG, new File(destDir, "_r" + i + "_c" + j + ".jpg"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 图像类型转换：GIF=》JPG、GIF=》PNG、PNG=》JPG、PNG=》GIF(X)、BMP=》PNG
     *
     * @param srcImageFile  源图像文件
     * @param destImageFile 目标图像文件
     */
    public static void convert(File srcImageFile, File destImageFile) {
        final String srcExtName = FileUtils.getFileExtension(srcImageFile);
        final String destExtName = FileUtils.getFileExtension(destImageFile);
        if (StringUtils.equalsIgnoreCase(srcExtName, destExtName)) {
            // 扩展名相同直接复制文件
            FileUtils.copyFile(srcImageFile, destImageFile);
        }

        ImageOutputStream imageOutputStream = null;
        try {
            imageOutputStream = getImageOutputStream(destImageFile);
            convert(read(srcImageFile), destExtName, imageOutputStream, StringUtils.equalsIgnoreCase(IMAGE_TYPE_PNG, srcExtName));
        } finally {
            IOUtils.close(imageOutputStream);
        }
    }

    /**
     * 图像类型转换：GIF=》JPG、GIF=》PNG、PNG=》JPG、PNG=》GIF(X)、BMP=》PNG<br>
     * 此方法并不关闭流
     *
     * @param srcStream  源图像流
     * @param formatName 包含格式非正式名称的 String：如 JPG、JPEG、GIF 等
     * @param destStream 目标图像输出流
     */
    public static void convert(InputStream srcStream, String formatName, OutputStream destStream) {
        write(read(srcStream), formatName, getImageOutputStream(destStream));
    }

    /**
     * 图像类型转换：GIF=》JPG、GIF=》PNG、PNG=》JPG、PNG=》GIF(X)、BMP=》PNG<br>
     * 此方法并不关闭流
     *
     * @param srcImage        源图像流
     * @param formatName      包含格式非正式名称的 String：如 JPG、JPEG、GIF 等
     * @param destImageStream 目标图像输出流
     * @param isSrcPng        源图片是否为 PNG 格式
     */
    public static void convert(Image srcImage, String formatName, ImageOutputStream destImageStream, boolean isSrcPng) {
        try {
            ImageIO.write(isSrcPng ? copyImage(srcImage, BufferedImage.TYPE_INT_RGB) : toBufferedImage(srcImage), formatName, destImageStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 彩色转为黑白
     *
     * @param srcImageFile  源图像地址
     * @param destImageFile 目标图像地址
     */
    public static void gray(File srcImageFile, File destImageFile) {
        gray(read(srcImageFile), destImageFile);
    }

    /**
     * 彩色转为黑白<br>
     * 此方法并不关闭流
     *
     * @param srcStream  源图像流
     * @param destStream 目标图像流
     */
    public static void gray(InputStream srcStream, OutputStream destStream) {
        gray(read(srcStream), getImageOutputStream(destStream));
    }

    /**
     * 彩色转为黑白<br>
     * 此方法并不关闭流
     *
     * @param srcStream  源图像流
     * @param destStream 目标图像流
     */
    public static void gray(ImageInputStream srcStream, ImageOutputStream destStream) {
        gray(read(srcStream), destStream);
    }

    /**
     * 彩色转为黑白
     *
     * @param srcImage 源图像流
     * @param outFile  目标文件
     */
    public static void gray(Image srcImage, File outFile) {
        write(gray(srcImage), outFile);
    }

    /**
     * 彩色转为黑白<br>
     * 此方法并不关闭流
     *
     * @param srcImage 源图像流
     * @param out      目标图像流
     */
    public static void gray(Image srcImage, OutputStream out) {
        gray(srcImage, getImageOutputStream(out));
    }

    /**
     * 彩色转为黑白<br>
     * 此方法并不关闭流
     *
     * @param srcImage        源图像流
     * @param destImageStream 目标图像流
     */
    public static void gray(Image srcImage, ImageOutputStream destImageStream) {
        writeJpg(gray(srcImage), destImageStream);
    }

    /**
     * 彩色转为黑白
     *
     * @param srcImage 源图像流
     * @return {@link Image} 灰度后的图片
     */
    public static Image gray(Image srcImage) {
        return Img.from(srcImage).gray().getImg();
    }

    /**
     * 彩色转为黑白二值化图片，根据目标文件扩展名确定转换后的格式
     *
     * @param srcImageFile  源图像地址
     * @param destImageFile 目标图像地址
     */
    public static void binary(File srcImageFile, File destImageFile) {
        binary(read(srcImageFile), destImageFile);
    }

    /**
     * 彩色转为黑白二值化图片<br>
     * 此方法并不关闭流
     *
     * @param srcStream  源图像流
     * @param destStream 目标图像流
     * @param imageType  图片格式（扩展名）
     */
    public static void binary(InputStream srcStream, OutputStream destStream, String imageType) {
        binary(read(srcStream), getImageOutputStream(destStream), imageType);
    }

    /**
     * 彩色转为黑白黑白二值化图片<br>
     * 此方法并不关闭流
     *
     * @param srcStream  源图像流
     * @param destStream 目标图像流
     * @param imageType  图片格式（扩展名）
     */
    public static void binary(ImageInputStream srcStream, ImageOutputStream destStream, String imageType) {
        binary(read(srcStream), destStream, imageType);
    }

    /**
     * 彩色转为黑白二值化图片，根据目标文件扩展名确定转换后的格式
     *
     * @param srcImage 源图像流
     * @param outFile  目标文件
     */
    public static void binary(Image srcImage, File outFile) {
        write(binary(srcImage), outFile);
    }

    /**
     * 彩色转为黑白二值化图片<br>
     * 此方法并不关闭流，输出 JPG 格式
     *
     * @param srcImage  源图像流
     * @param out       目标图像流
     * @param imageType 图片格式（扩展名）
     */
    public static void binary(Image srcImage, OutputStream out, String imageType) {
        binary(srcImage, getImageOutputStream(out), imageType);
    }

    /**
     * 彩色转为黑白二值化图片<br>
     * 此方法并不关闭流，输出 JPG 格式
     *
     * @param srcImage        源图像流
     * @param destImageStream 目标图像流
     * @param imageType       图片格式（扩展名）
     */
    public static void binary(Image srcImage, ImageOutputStream destImageStream, String imageType) {
        write(binary(srcImage), imageType, destImageStream);
    }

    /**
     * 彩色转为黑白二值化图片
     *
     * @param srcImage 源图像流
     * @return {@link Image} 二值化后的图片
     */
    public static Image binary(Image srcImage) {
        return Img.from(srcImage).binary().getImg();
    }

    /**
     * 给图片添加文字水印
     *
     * @param imageFile 源图像文件
     * @param destFile  目标图像文件
     * @param pressText 水印文字
     * @param color     水印的字体颜色
     * @param font      {@link Font} 字体相关信息，如果默认则为 {@code null}
     * @param x         修正值。 默认在中间，偏移量相对于中间偏移
     * @param y         修正值。 默认在中间，偏移量相对于中间偏移
     * @param alpha     透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public static void pressText(File imageFile, File destFile, String pressText, Color color, Font font, int x, int y, float alpha) {
        pressText(read(imageFile), destFile, pressText, color, font, x, y, alpha);
    }

    /**
     * 给图片添加文字水印<br>
     * 此方法并不关闭流
     *
     * @param srcStream  源图像流
     * @param destStream 目标图像流
     * @param pressText  水印文字
     * @param color      水印的字体颜色
     * @param font       {@link Font} 字体相关信息，如果默认则为 {@code null}
     * @param x          修正值。 默认在中间，偏移量相对于中间偏移
     * @param y          修正值。 默认在中间，偏移量相对于中间偏移
     * @param alpha      透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public static void pressText(InputStream srcStream, OutputStream destStream, String pressText, Color color, Font font, int x, int y, float alpha) {
        pressText(read(srcStream), getImageOutputStream(destStream), pressText, color, font, x, y, alpha);
    }

    /**
     * 给图片添加文字水印<br>
     * 此方法并不关闭流
     *
     * @param srcStream  源图像流
     * @param destStream 目标图像流
     * @param pressText  水印文字
     * @param color      水印的字体颜色
     * @param font       {@link Font} 字体相关信息，如果默认则为 {@code null}
     * @param x          修正值。 默认在中间，偏移量相对于中间偏移
     * @param y          修正值。 默认在中间，偏移量相对于中间偏移
     * @param alpha      透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public static void pressText(ImageInputStream srcStream, ImageOutputStream destStream, String pressText, Color color, Font font, int x, int y, float alpha) {
        pressText(read(srcStream), destStream, pressText, color, font, x, y, alpha);
    }

    /**
     * 给图片添加文字水印<br>
     * 此方法并不关闭流
     *
     * @param srcImage  源图像
     * @param destFile  目标流
     * @param pressText 水印文字
     * @param color     水印的字体颜色
     * @param font      {@link Font} 字体相关信息，如果默认则为 {@code null}
     * @param x         修正值。 默认在中间，偏移量相对于中间偏移
     * @param y         修正值。 默认在中间，偏移量相对于中间偏移
     * @param alpha     透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public static void pressText(Image srcImage, File destFile, String pressText, Color color, Font font, int x, int y, float alpha) {
        write(pressText(srcImage, pressText, color, font, x, y, alpha), destFile);
    }

    /**
     * 给图片添加文字水印<br>
     * 此方法并不关闭流
     *
     * @param srcImage  源图像
     * @param to        目标流
     * @param pressText 水印文字
     * @param color     水印的字体颜色
     * @param font      {@link Font} 字体相关信息，如果默认则为 {@code null}
     * @param x         修正值。 默认在中间，偏移量相对于中间偏移
     * @param y         修正值。 默认在中间，偏移量相对于中间偏移
     * @param alpha     透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public static void pressText(Image srcImage, OutputStream to, String pressText, Color color, Font font, int x, int y, float alpha) {
        pressText(srcImage, getImageOutputStream(to), pressText, color, font, x, y, alpha);
    }

    /**
     * 给图片添加文字水印<br>
     * 此方法并不关闭流
     *
     * @param srcImage        源图像
     * @param destImageStream 目标图像流
     * @param pressText       水印文字
     * @param color           水印的字体颜色
     * @param font            {@link Font} 字体相关信息，如果默认则为 {@code null}
     * @param x               修正值。 默认在中间，偏移量相对于中间偏移
     * @param y               修正值。 默认在中间，偏移量相对于中间偏移
     * @param alpha           透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public static void pressText(Image srcImage, ImageOutputStream destImageStream, String pressText, Color color, Font font, int x, int y, float alpha) {
        writeJpg(pressText(srcImage, pressText, color, font, x, y, alpha), destImageStream);
    }

    /**
     * 给图片添加文字水印<br>
     * 此方法并不关闭流
     *
     * @param srcImage  源图像
     * @param pressText 水印文字
     * @param color     水印的字体颜色
     * @param font      {@link Font} 字体相关信息，如果默认则为 {@code null}
     * @param x         修正值。 默认在中间，偏移量相对于中间偏移
     * @param y         修正值。 默认在中间，偏移量相对于中间偏移
     * @param alpha     透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @return 处理后的图像
     */
    public static Image pressText(Image srcImage, String pressText, Color color, Font font, int x, int y, float alpha) {
        return Img.from(srcImage).pressText(pressText, color, font, x, y, alpha).getImg();
    }

    /**
     * 给图片添加图片水印
     *
     * @param srcImageFile  源图像文件
     * @param destImageFile 目标图像文件
     * @param pressImg      水印图片
     * @param x             修正值。 默认在中间，偏移量相对于中间偏移
     * @param y             修正值。 默认在中间，偏移量相对于中间偏移
     * @param alpha         透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public static void pressImage(File srcImageFile, File destImageFile, Image pressImg, int x, int y, float alpha) {
        pressImage(read(srcImageFile), destImageFile, pressImg, x, y, alpha);
    }

    /**
     * 给图片添加图片水印<br>
     * 此方法并不关闭流
     *
     * @param srcStream  源图像流
     * @param destStream 目标图像流
     * @param pressImg   水印图片，可以使用 {@link ImageIO#read(File)} 方法读取文件
     * @param x          修正值。 默认在中间，偏移量相对于中间偏移
     * @param y          修正值。 默认在中间，偏移量相对于中间偏移
     * @param alpha      透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public static void pressImage(InputStream srcStream, OutputStream destStream, Image pressImg, int x, int y, float alpha) {
        pressImage(read(srcStream), getImageOutputStream(destStream), pressImg, x, y, alpha);
    }

    /**
     * 给图片添加图片水印<br>
     * 此方法并不关闭流
     *
     * @param srcStream  源图像流
     * @param destStream 目标图像流
     * @param pressImg   水印图片，可以使用 {@link ImageIO#read(File)} 方法读取文件
     * @param x          修正值。 默认在中间，偏移量相对于中间偏移
     * @param y          修正值。 默认在中间，偏移量相对于中间偏移
     * @param alpha      透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public static void pressImage(ImageInputStream srcStream, ImageOutputStream destStream, Image pressImg, int x, int y, float alpha) {
        pressImage(read(srcStream), destStream, pressImg, x, y, alpha);
    }

    /**
     * 给图片添加图片水印<br>
     * 此方法并不关闭流
     *
     * @param srcImage 源图像流
     * @param outFile  写出文件
     * @param pressImg 水印图片，可以使用 {@link ImageIO#read(File)} 方法读取文件
     * @param x        修正值。 默认在中间，偏移量相对于中间偏移
     * @param y        修正值。 默认在中间，偏移量相对于中间偏移
     * @param alpha    透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public static void pressImage(Image srcImage, File outFile, Image pressImg, int x, int y, float alpha) {
        write(pressImage(srcImage, pressImg, x, y, alpha), outFile);
    }

    /**
     * 给图片添加图片水印<br>
     * 此方法并不关闭流
     *
     * @param srcImage 源图像流
     * @param out      目标图像流
     * @param pressImg 水印图片，可以使用 {@link ImageIO#read(File)} 方法读取文件
     * @param x        修正值。 默认在中间，偏移量相对于中间偏移
     * @param y        修正值。 默认在中间，偏移量相对于中间偏移
     * @param alpha    透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public static void pressImage(Image srcImage, OutputStream out, Image pressImg, int x, int y, float alpha) {
        pressImage(srcImage, getImageOutputStream(out), pressImg, x, y, alpha);
    }

    /**
     * 给图片添加图片水印<br>
     * 此方法并不关闭流
     *
     * @param srcImage        源图像流
     * @param destImageStream 目标图像流
     * @param pressImg        水印图片，可以使用 {@link ImageIO#read(File)} 方法读取文件
     * @param x               修正值。 默认在中间，偏移量相对于中间偏移
     * @param y               修正值。 默认在中间，偏移量相对于中间偏移
     * @param alpha           透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public static void pressImage(Image srcImage, ImageOutputStream destImageStream, Image pressImg, int x, int y, float alpha) {
        writeJpg(pressImage(srcImage, pressImg, x, y, alpha), destImageStream);
    }

    /**
     * 给图片添加图片水印<br>
     * 此方法并不关闭流
     *
     * @param srcImage 源图像流
     * @param pressImg 水印图片，可以使用 {@link ImageIO#read(File)} 方法读取文件
     * @param x        修正值。 默认在中间，偏移量相对于中间偏移
     * @param y        修正值。 默认在中间，偏移量相对于中间偏移
     * @param alpha    透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @return 结果图片
     */
    public static Image pressImage(Image srcImage, Image pressImg, int x, int y, float alpha) {
        return Img.from(srcImage).pressImage(pressImg, x, y, alpha).getImg();
    }

    /**
     * 给图片添加图片水印<br>
     * 此方法并不关闭流
     *
     * @param srcImage  源图像流
     * @param pressImg  水印图片，可以使用 {@link ImageIO#read(File)} 方法读取文件
     * @param rectangle 矩形对象，表示矩形区域的 x，y，width，height，x,y 从背景图片中心计算
     * @param alpha     透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @return 结果图片
     */
    public static Image pressImage(Image srcImage, Image pressImg, Rectangle rectangle, float alpha) {
        return Img.from(srcImage).pressImage(pressImg, rectangle, alpha).getImg();
    }

    /**
     * 旋转图片为指定角度<br>
     * 此方法不会关闭输出流
     *
     * @param imageFile 被旋转图像文件
     * @param degree    旋转角度
     * @param outFile   输出文件
     */
    public static void rotate(File imageFile, int degree, File outFile) {
        rotate(read(imageFile), degree, outFile);
    }

    /**
     * 旋转图片为指定角度<br>
     * 此方法不会关闭输出流
     *
     * @param image   目标图像
     * @param degree  旋转角度
     * @param outFile 输出文件
     */
    public static void rotate(Image image, int degree, File outFile) {
        write(rotate(image, degree), outFile);
    }

    /**
     * 旋转图片为指定角度<br>
     * 此方法不会关闭输出流
     *
     * @param image  目标图像
     * @param degree 旋转角度
     * @param out    输出流
     */
    public static void rotate(Image image, int degree, OutputStream out) {
        writeJpg(rotate(image, degree), getImageOutputStream(out));
    }

    /**
     * 旋转图片为指定角度<br>
     * 此方法不会关闭输出流，输出格式为JPG
     *
     * @param image  目标图像
     * @param degree 旋转角度
     * @param out    输出图像流
     */
    public static void rotate(Image image, int degree, ImageOutputStream out) {
        writeJpg(rotate(image, degree), out);
    }

    /**
     * 旋转图片为指定角度<br>
     *
     * @param image  目标图像
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    public static Image rotate(Image image, int degree) {
        return Img.from(image).rotate(degree).getImg();
    }

    /**
     * 水平翻转图像
     *
     * @param imageFile 图像文件
     * @param outFile   输出文件
     */
    public static void flip(File imageFile, File outFile) {
        flip(read(imageFile), outFile);
    }

    /**
     * 水平翻转图像
     *
     * @param image   图像
     * @param outFile 输出文件
     */
    public static void flip(Image image, File outFile) {
        write(flip(image), outFile);
    }

    /**
     * 水平翻转图像
     *
     * @param image 图像
     * @param out   输出
     */
    public static void flip(Image image, OutputStream out) {
        flip(image, getImageOutputStream(out));
    }

    /**
     * 水平翻转图像，写出格式为JPG
     *
     * @param image 图像
     * @param out   输出
     */
    public static void flip(Image image, ImageOutputStream out) {
        writeJpg(flip(image), out);
    }

    /**
     * 水平翻转图像
     *
     * @param image 图像
     * @return 翻转后的图片
     */
    public static Image flip(Image image) {
        return Img.from(image).flip().getImg();
    }

    /**
     * 压缩图像，输出图像只支持 jpg 文件
     *
     * @param imageFile 图像文件
     * @param outFile   输出文件，只支持 jpg 文件
     * @param quality   压缩比例，必须为0~1
     */
    public static void compress(File imageFile, File outFile, float quality) {
        Img.from(imageFile).setQuality(quality).write(outFile);
    }

    /**
     * {@link Image} 转 {@link RenderedImage}<br>
     * 首先尝试强转，否则新建一个 {@link BufferedImage} 后重新绘制
     *
     * @param img {@link Image}
     * @return {@link BufferedImage}
     */
    public static RenderedImage toRenderedImage(Image img) {
        if (img instanceof RenderedImage) {
            return (RenderedImage) img;
        }

        return copyImage(img, BufferedImage.TYPE_INT_RGB);
    }

    /**
     * {@link Image} 转 {@link BufferedImage}<br>
     * 首先尝试强转，否则新建一个 {@link BufferedImage} 后重新绘制
     *
     * @param img {@link Image}
     * @return {@link BufferedImage}
     */
    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        return copyImage(img, BufferedImage.TYPE_INT_RGB);
    }

    /**
     * {@link Image} 转 {@link BufferedImage}<br>
     * 如果源图片的RGB模式与目标模式一致，则直接转换，否则重新绘制
     *
     * @param image     {@link Image}
     * @param imageType 目标图片类型
     * @return {@link BufferedImage}
     */
    public static BufferedImage toBufferedImage(Image image, String imageType) {
        BufferedImage bufferedImage;
        if (!imageType.equalsIgnoreCase(IMAGE_TYPE_PNG)) {
            // 当目标为非PNG类图片时，源图片统一转换为RGB格式
            if (image instanceof BufferedImage) {
                bufferedImage = (BufferedImage) image;
                if (BufferedImage.TYPE_INT_RGB != bufferedImage.getType()) {
                    bufferedImage = copyImage(image, BufferedImage.TYPE_INT_RGB);
                }
            } else {
                bufferedImage = copyImage(image, BufferedImage.TYPE_INT_RGB);
            }
        } else {
            bufferedImage = toBufferedImage(image);
        }
        return bufferedImage;
    }

    /**
     * 将已有 Image 复制新的一份出来
     *
     * @param img       {@link Image}
     * @param imageType 目标图片类型，{@link BufferedImage} 中的常量，例如黑白等
     * @return {@link BufferedImage}
     * @see BufferedImage#TYPE_INT_RGB
     * @see BufferedImage#TYPE_INT_ARGB
     * @see BufferedImage#TYPE_INT_ARGB_PRE
     * @see BufferedImage#TYPE_INT_BGR
     * @see BufferedImage#TYPE_3BYTE_BGR
     * @see BufferedImage#TYPE_4BYTE_ABGR
     * @see BufferedImage#TYPE_4BYTE_ABGR_PRE
     * @see BufferedImage#TYPE_BYTE_GRAY
     * @see BufferedImage#TYPE_USHORT_GRAY
     * @see BufferedImage#TYPE_BYTE_BINARY
     * @see BufferedImage#TYPE_BYTE_INDEXED
     * @see BufferedImage#TYPE_USHORT_565_RGB
     * @see BufferedImage#TYPE_USHORT_555_RGB
     */
    public static BufferedImage copyImage(Image img, int imageType) {
        return copyImage(img, imageType, null);
    }

    /**
     * 将已有 Image 复制新的一份出来
     *
     * @param img             {@link Image}
     * @param imageType       目标图片类型，{@link BufferedImage} 中的常量，例如黑白等
     * @param backgroundColor 背景色，{@code null} 表示默认背景色（黑色或者透明）
     * @return {@link BufferedImage}
     * @see BufferedImage#TYPE_INT_RGB
     * @see BufferedImage#TYPE_INT_ARGB
     * @see BufferedImage#TYPE_INT_ARGB_PRE
     * @see BufferedImage#TYPE_INT_BGR
     * @see BufferedImage#TYPE_3BYTE_BGR
     * @see BufferedImage#TYPE_4BYTE_ABGR
     * @see BufferedImage#TYPE_4BYTE_ABGR_PRE
     * @see BufferedImage#TYPE_BYTE_GRAY
     * @see BufferedImage#TYPE_USHORT_GRAY
     * @see BufferedImage#TYPE_BYTE_BINARY
     * @see BufferedImage#TYPE_BYTE_INDEXED
     * @see BufferedImage#TYPE_USHORT_565_RGB
     * @see BufferedImage#TYPE_USHORT_555_RGB
     */
    public static BufferedImage copyImage(Image img, int imageType, Color backgroundColor) {
        final BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), imageType);
        final Graphics2D bGr = GraphicsUtils.createGraphics(bimage, backgroundColor);
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        return bimage;
    }

    /**
     * 将 Base64 编码的图像信息转为 {@link BufferedImage}
     *
     * @param base64 图像的 Base64 表示
     * @return {@link BufferedImage}
     */
    public static BufferedImage toImage(String base64) {
        return toImage(Base64.getDecoder().decode(base64));
    }

    /**
     * 将的图像 bytes 转为 {@link BufferedImage}
     *
     * @param imageBytes 图像 bytes
     * @return {@link BufferedImage}
     */
    public static BufferedImage toImage(byte[] imageBytes) {
        return read(new ByteArrayInputStream(imageBytes));
    }

    /**
     * 将图片对象转换为 InputStream 形式
     *
     * @param image     图片对象
     * @param imageType 图片类型
     * @return Base64 的字符串表现形式
     */
    public static ByteArrayInputStream toStream(Image image, String imageType) {
        return StreamUtils.toStream(toBytes(image, imageType));
    }

    /**
     * 将图片对象转换为 Base64 形式
     *
     * @param image     图片对象
     * @param imageType 图片类型
     * @return Base64 的字符串表现形式
     */
    public static String toBase64(Image image, String imageType) {
        return new String(Base64.getEncoder().encode(toBytes(image, imageType)));
    }

    /**
     * 将图片对象转换为 bytes 形式
     *
     * @param image     图片对象
     * @param imageType 图片类型
     * @return Base64 的字符串表现形式
     */
    public static byte[] toBytes(Image image, String imageType) {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        write(image, imageType, out);
        return out.toByteArray();
    }

    /**
     * 根据文字创建 PNG 图片
     *
     * @param str             文字
     * @param font            字体 {@link Font}
     * @param backgroundColor 背景颜色，默认透明
     * @param fontColor       字体颜色，默认黑色
     * @param out             图片输出地
     */
    public static void createImage(String str, Font font, Color backgroundColor, Color fontColor, ImageOutputStream out) {
        writePng(createImage(str, font, backgroundColor, fontColor, BufferedImage.TYPE_INT_ARGB), out);
    }

    /**
     * 根据文字创建图片
     *
     * @param str             文字
     * @param font            字体 {@link Font}
     * @param backgroundColor 背景颜色，默认透明
     * @param fontColor       字体颜色，默认黑色
     * @param imageType       图片类型，见：{@link BufferedImage}
     * @return 图片
     */
    public static BufferedImage createImage(String str, Font font, Color backgroundColor, Color fontColor, int imageType) {
        // 获取font的样式应用在str上的整个矩形
        final Rectangle2D r = getRectangle(str, font);
        // 获取单个字符的高度
        int unitHeight = (int) Math.floor(r.getHeight());
        // 获取整个str用了font样式的宽度这里用四舍五入后+1保证宽度绝对能容纳这个字符串作为图片的宽度
        int width = (int) Math.round(r.getWidth()) + 1;
        // 把单个字符的高度+3保证高度绝对能容纳字符串作为图片的高度
        int height = unitHeight + 3;

        // 创建图片
        final BufferedImage image = new BufferedImage(width, height, imageType);
        final Graphics g = image.getGraphics();
        if (null != backgroundColor) {
            // 先用背景色填充整张图片,也就是背景
            g.setColor(backgroundColor);
            g.fillRect(0, 0, width, height);
        }
        // 画出字符串
        g.setColor(fontColor != null ? fontColor : Color.BLACK);
        g.setFont(font);
        g.drawString(str, 0, font.getSize());
        g.dispose();

        return image;
    }

    /**
     * 获取 font 的样式应用在 str 上的整个矩形
     *
     * @param str  字符串，必须非空
     * @param font 字体，必须非空
     * @return {@link Rectangle2D}
     */
    public static Rectangle2D getRectangle(String str, Font font) {
        return font.getStringBounds(str,
                new FontRenderContext(AffineTransform.getScaleInstance(1, 1),
                        false,
                        false));
    }

    /**
     * 根据文件创建字体<br>
     * 首先尝试创建 {@link Font#TRUETYPE_FONT} 字体，此类字体无效则创建 {@link Font#TYPE1_FONT}
     *
     * @param fontFile 字体文件
     * @return {@link Font}
     */
    public static Font createFont(File fontFile) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, fontFile);
        } catch (FontFormatException e) {
            // True Type字体无效时使用Type1字体
            try {
                return Font.createFont(Font.TYPE1_FONT, fontFile);
            } catch (Exception e1) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据文件创建字体<br>
     * 首先尝试创建 {@link Font#TRUETYPE_FONT} 字体，此类字体无效则创建 {@link Font#TYPE1_FONT}
     *
     * @param fontStream 字体流
     * @return {@link Font}
     */
    public static Font createFont(InputStream fontStream) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, fontStream);
        } catch (FontFormatException e) {
            // True Type字体无效时使用Type1字体
            try {
                return Font.createFont(Font.TYPE1_FONT, fontStream);
            } catch (Exception e1) {
                throw new RuntimeException(e1);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 创建 {@link Graphics2D}
     *
     * @param image {@link BufferedImage}
     * @param color {@link Color} 背景颜色以及当前画笔颜色
     * @return {@link Graphics2D}
     * @see GraphicsUtils#createGraphics(BufferedImage, Color)
     */
    public static Graphics2D createGraphics(BufferedImage image, Color color) {
        return GraphicsUtils.createGraphics(image, color);
    }

    /**
     * 写出图像为 JPG 格式
     *
     * @param image           {@link Image}
     * @param destImageStream 写出到的目标流
     */
    public static void writeJpg(Image image, ImageOutputStream destImageStream) {
        write(image, IMAGE_TYPE_JPG, destImageStream);
    }

    /**
     * 写出图像为 PNG 格式
     *
     * @param image           {@link Image}
     * @param destImageStream 写出到的目标流
     */
    public static void writePng(Image image, ImageOutputStream destImageStream) {
        write(image, IMAGE_TYPE_PNG, destImageStream);
    }

    /**
     * 写出图像为 JPG 格式
     *
     * @param image {@link Image}
     * @param out   写出到的目标流
     */
    public static void writeJpg(Image image, OutputStream out) {
        write(image, IMAGE_TYPE_JPG, out);
    }

    /**
     * 写出图像为 PNG 格式
     *
     * @param image {@link Image}
     * @param out   写出到的目标流
     */
    public static void writePng(Image image, OutputStream out) {
        write(image, IMAGE_TYPE_PNG, out);
    }

    /**
     * 按照目标格式写出图像：GIF=》JPG、GIF=》PNG、PNG=》JPG、PNG=》GIF(X)、BMP=》PNG<br>
     * 此方法并不关闭流
     *
     * @param srcStream  源图像流
     * @param formatName 包含格式非正式名称的 String：如 JPG、JPEG、GIF 等
     * @param destStream 目标图像输出流
     */
    public static void write(ImageInputStream srcStream, String formatName, ImageOutputStream destStream) {
        write(read(srcStream), formatName, destStream);
    }

    /**
     * 写出图像：GIF=》JPG、GIF=》PNG、PNG=》JPG、PNG=》GIF(X)、BMP=》PNG<br>
     * 此方法并不关闭流
     *
     * @param image     {@link Image}
     * @param imageType 图片类型（图片扩展名）
     * @param out       写出到的目标流
     */
    public static void write(Image image, String imageType, OutputStream out) {
        write(image, imageType, getImageOutputStream(out));
    }

    /**
     * 写出图像为指定格式：GIF=》JPG、GIF=》PNG、PNG=》JPG、PNG=》GIF(X)、BMP=》PNG<br>
     * 此方法并不关闭流
     *
     * @param image           {@link Image}
     * @param imageType       图片类型（图片扩展名）
     * @param destImageStream 写出到的目标流
     * @return 是否成功写出，如果返回 false 表示未找到合适的 Writer
     */
    public static boolean write(Image image, String imageType, ImageOutputStream destImageStream) {
        return write(image, imageType, destImageStream, 1);
    }

    /**
     * 写出图像为指定格式
     *
     * @param image           {@link Image}
     * @param imageType       图片类型（图片扩展名）
     * @param destImageStream 写出到的目标流
     * @param quality         质量，数字为0~1（不包括0和1）表示质量压缩比，除此数字外设置表示不压缩
     * @return 是否成功写出，如果返回 false 表示未找到合适的 Writer
     */
    public static boolean write(Image image, String imageType, ImageOutputStream destImageStream, float quality) {
        if (StringUtils.isBlank(imageType)) {
            imageType = IMAGE_TYPE_JPG;
        }

        final ImageWriter writer = getWriter(image, imageType);
        return write(toBufferedImage(image, imageType), writer, destImageStream, quality);
    }

    /**
     * 写出图像为目标文件扩展名对应的格式
     *
     * @param image      {@link Image}
     * @param targetFile 目标文件
     */
    public static void write(Image image, File targetFile) {
        ImageOutputStream out = null;
        try {
            out = getImageOutputStream(targetFile);
            write(image, FileUtils.getFileExtension(targetFile), out);
        } finally {
            IOUtils.close(out);
        }
    }

    /**
     * 通过 {@link ImageWriter} 写出图片到输出流
     *
     * @param image   图片
     * @param writer  {@link ImageWriter}
     * @param output  输出的 Image 流 {@link ImageOutputStream}
     * @param quality 质量，数字为0~1（不包括0和1）表示质量压缩比，除此数字外设置表示不压缩
     * @return 是否成功写出
     */
    public static boolean write(Image image, ImageWriter writer, ImageOutputStream output, float quality) {
        if (writer == null) {
            return false;
        }

        writer.setOutput(output);
        final RenderedImage renderedImage = toRenderedImage(image);
        // 设置质量
        ImageWriteParam imgWriteParams = null;
        if (quality > 0 && quality < 1) {
            imgWriteParams = writer.getDefaultWriteParam();
            if (imgWriteParams.canWriteCompressed()) {
                imgWriteParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                imgWriteParams.setCompressionQuality(quality);
                final ColorModel colorModel = renderedImage.getColorModel();
                imgWriteParams.setDestinationType(new ImageTypeSpecifier(colorModel, colorModel.createCompatibleSampleModel(16, 16)));
            }
        }

        try {
            if (null != imgWriteParams) {
                writer.write(null, new IIOImage(renderedImage, null, null), imgWriteParams);
            } else {
                writer.write(renderedImage);
            }
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.dispose();
        }
        return true;
    }

    /**
     * 获得 {@link ImageReader}
     *
     * @param type 图片文件类型，例如 "jpeg" 或 "tiff"
     * @return {@link ImageReader}
     */
    public static ImageReader getReader(String type) {
        final Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName(type);
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

    /**
     * 从文件中读取图片，请使用绝对路径
     *
     * @param imageFilePath 图片文件路径
     * @return 图片
     */
    public static BufferedImage read(String imageFilePath) {
        return read(new File(imageFilePath));
    }

    /**
     * 从文件中读取图片
     *
     * @param imageFile 图片文件
     * @return 图片
     */
    public static BufferedImage read(File imageFile) {
        try {
            return ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 从流中读取图片
     *
     * @param imageStream 图片文件
     * @return 图片
     */
    public static BufferedImage read(InputStream imageStream) {
        try {
            return ImageIO.read(imageStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从图片流中读取图片
     *
     * @param imageStream 图片文件
     * @return 图片
     */
    public static BufferedImage read(ImageInputStream imageStream) {
        try {
            return ImageIO.read(imageStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从URL中读取图片
     *
     * @param imageUrl 图片文件
     * @return 图片
     */
    public static BufferedImage read(URL imageUrl) {
        try {
            return ImageIO.read(imageUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 {@link ImageOutputStream}
     *
     * @param out {@link OutputStream}
     * @return {@link ImageOutputStream}
     */
    public static ImageOutputStream getImageOutputStream(OutputStream out) {
        try {
            return ImageIO.createImageOutputStream(out);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 {@link ImageOutputStream}
     *
     * @param outFile {@link File}
     * @return {@link ImageOutputStream}
     */
    public static ImageOutputStream getImageOutputStream(File outFile) {
        try {
            return ImageIO.createImageOutputStream(outFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 {@link ImageInputStream}
     *
     * @param in {@link InputStream}
     * @return {@link ImageInputStream}
     */
    public static ImageInputStream getImageInputStream(InputStream in) {
        try {
            return ImageIO.createImageInputStream(in);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据给定的 Image 对象和格式获取对应的 {@link ImageWriter}，如果未找到合适的 Writer，返回 null
     *
     * @param img        {@link Image}
     * @param formatName 图片格式，例如 "jpg"、"png"
     * @return {@link ImageWriter}
     */
    public static ImageWriter getWriter(Image img, String formatName) {
        final ImageTypeSpecifier type = ImageTypeSpecifier.createFromRenderedImage(toRenderedImage(img));
        final Iterator<ImageWriter> iter = ImageIO.getImageWriters(type, formatName);
        return iter.hasNext() ? iter.next() : null;
    }

    /**
     * 根据给定的图片格式或者扩展名获取 {@link ImageWriter}，如果未找到合适的 Writer，返回 null
     *
     * @param formatName 图片格式或扩展名，例如 "jpg"、"png"
     * @return {@link ImageWriter}
     */
    public static ImageWriter getWriter(String formatName) {
        ImageWriter writer = null;
        Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName(formatName);
        if (iter.hasNext()) {
            writer = iter.next();
        }
        if (null == writer) {
            // 尝试扩展名获取
            iter = ImageIO.getImageWritersBySuffix(formatName);
            if (iter.hasNext()) {
                writer = iter.next();
            }
        }
        return writer;
    }

    /**
     * Color 对象转16进制表示，例如 #fcf6d6
     *
     * @param color {@link Color}
     * @return 16进制的颜色值，例如 #fcf6d6
     */
    public static String toHex(Color color) {
        String r = Integer.toHexString(color.getRed());
        r = r.length() < 2 ? ('0' + r) : r;
        String g = Integer.toHexString(color.getGreen());
        g = g.length() < 2 ? ('0' + g) : g;
        String b = Integer.toHexString(color.getBlue());
        b = b.length() < 2 ? ('0' + b) : b;
        return '#' + r + g + b;
    }
}
