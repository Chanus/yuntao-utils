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

import java.io.*;
import java.nio.charset.Charset;

/**
 * 流操作工具类
 *
 * @author Chanus
 * @date 2020-06-20 17:54:13
 * @since 1.0.0
 */
public class StreamUtils {
    /**
     * 从输入流中读取数据到字节数组
     *
     * @param inputStream 输入流
     * @return 数据字节数组
     */
    public static byte[] read2Byte(InputStream inputStream) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        int len;
        byte[] buffer = new byte[1024];
        try {
            while ((len = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            buffer = outStream.toByteArray();// 网页的二进制数据
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred.", e);
        } finally {
            IOUtils.close(outStream, inputStream);
        }
        return buffer;
    }

    /**
     * 从输入流中读取数据到字符串
     *
     * @param inputStream 输入流
     * @return 数据字符串
     */
    public static String read2String(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        byte[] b = new byte[4096];
        try {
            for (int n; (n = inputStream.read(b)) != -1; ) {
                stringBuilder.append(new String(b, 0, n));
            }
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred.", e);
        } finally {
            IOUtils.close(inputStream);
        }
        return stringBuilder.toString();
    }

    /**
     * 将字节数组写入输出流
     *
     * @param data 字节数组
     * @param os   输出流
     * @throws IOException I/O异常
     */
    public static void write(final byte[] data, final OutputStream os) throws IOException {
        if (data != null)
            os.write(data);
    }

    /**
     * 将字符从字符串写入使用指定的字符编码输出流
     *
     * @param data     字符串
     * @param os       输出流
     * @param encoding 字符编码
     * @throws IOException I/O异常
     */
    public static void write(final String data, final OutputStream os, final String encoding) throws IOException {
        if (data != null)
            os.write(data.getBytes(encoding));
    }

    /**
     * String 转为流
     *
     * @param content     内容
     * @param charsetName 编码
     * @return 字节流
     */
    public static ByteArrayInputStream toStream(String content, String charsetName) {
        return toStream(content, Charset.forName(charsetName));
    }

    /**
     * String 转为流
     *
     * @param content 内容
     * @param charset 编码
     * @return 字节流
     */
    public static ByteArrayInputStream toStream(String content, Charset charset) {
        if (content == null) {
            return null;
        }
        return toStream(content.getBytes(charset));
    }

    /**
     * 文件转为流
     *
     * @param file 文件
     * @return {@link FileInputStream}
     */
    public static FileInputStream toStream(File file) {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 字节数组转为流
     *
     * @param content 内容
     * @return 字节流
     */
    public static ByteArrayInputStream toStream(byte[] content) {
        if (content == null) {
            return null;
        }
        return new ByteArrayInputStream(content);
    }

    /**
     * String 转为 UTF-8 编码的字节流流
     *
     * @param content 内容
     * @return 字节流
     */
    public static ByteArrayInputStream toUtf8Stream(String content) {
        return toStream(content, CharsetUtils.UTF_8);
    }
}
