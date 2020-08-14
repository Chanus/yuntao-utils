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
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
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
     * 默认缓存大小 8192
     */
    public static final int DEFAULT_BUFFER_SIZE = 2 << 12;
    /**
     * 默认中等缓存大小 16384
     */
    public static final int DEFAULT_MIDDLE_BUFFER_SIZE = 2 << 13;
    /**
     * 默认大缓存大小 32768
     */
    public static final int DEFAULT_LARGE_BUFFER_SIZE = 2 << 14;
    /**
     * 数据流末尾
     */
    public static final int EOF = -1;

    /**
     * 将 Reader 中的内容复制到 Writer 中 使用默认缓存大小，拷贝后不关闭 Reader
     *
     * @param reader Reader
     * @param writer Writer
     * @return 拷贝的字节数
     * @since 1.2.1
     */
    public static long copy(Reader reader, Writer writer) {
        return copy(reader, writer, DEFAULT_BUFFER_SIZE);
    }

    /**
     * 将 Reader 中的内容复制到 Writer 中，拷贝后不关闭 Reader
     *
     * @param reader     Reader
     * @param writer     Writer
     * @param bufferSize 缓存大小
     * @return 拷贝的字节数
     * @since 1.2.1
     */
    public static long copy(Reader reader, Writer writer, int bufferSize) {
        char[] buffer = new char[bufferSize];
        long size = 0;
        int readSize;
        try {
            while ((readSize = reader.read(buffer, 0, bufferSize)) != EOF) {
                writer.write(buffer, 0, readSize);
                size += readSize;
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 拷贝流，使用默认 Buffer 大小，拷贝后不关闭流
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     * @return 拷贝的字节数
     * @since 1.2.1
     */
    public static long copy(InputStream inputStream, OutputStream outputStream) {
        return copy(inputStream, outputStream, DEFAULT_BUFFER_SIZE);
    }

    /**
     * 拷贝流，拷贝后不关闭流
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     * @param bufferSize   缓存大小
     * @return 拷贝的字节数
     * @since 1.2.1
     */
    public static long copy(InputStream inputStream, OutputStream outputStream, int bufferSize) {
        if (bufferSize <= 0)
            bufferSize = DEFAULT_BUFFER_SIZE;

        byte[] buffer = new byte[bufferSize];
        long size = 0;
        try {
            for (int readSize; (readSize = inputStream.read(buffer)) != EOF; ) {
                outputStream.write(buffer, 0, readSize);
                size += readSize;
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 拷贝流，使用 NIO，会关闭流
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     * @param bufferSize   缓存大小
     * @return 拷贝的字节数
     * @since 1.2.1
     */
    public static long copyByNIO(InputStream inputStream, OutputStream outputStream, int bufferSize) {
        return copy(Channels.newChannel(inputStream), Channels.newChannel(outputStream), bufferSize);
    }

    /**
     * 拷贝文件流，使用 NIO
     *
     * @param fileInputStream  文件输入流
     * @param fileOutputStream 文件输出流
     * @return 拷贝的字节数
     * @since 1.2.1
     */
    public static long copy(FileInputStream fileInputStream, FileOutputStream fileOutputStream) {
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            inChannel = fileInputStream.getChannel();
            outChannel = fileOutputStream.getChannel();
            return inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred.", e);
        } finally {
            IOUtils.close(outChannel, inChannel);
        }
    }

    /**
     * 拷贝流，使用 NIO，不会关闭流
     *
     * @param in  {@link ReadableByteChannel}
     * @param out {@link WritableByteChannel}
     * @return 拷贝的字节数
     * @since 1.2.1
     */
    public static long copy(ReadableByteChannel in, WritableByteChannel out) {
        return copy(in, out, DEFAULT_BUFFER_SIZE);
    }

    /**
     * 拷贝流，使用 NIO，不会关闭流
     *
     * @param in         {@link ReadableByteChannel}
     * @param out        {@link WritableByteChannel}
     * @param bufferSize 缓冲大小，如果小于等于0，使用默认
     * @return 拷贝的字节数
     * @since 1.2.1
     */
    public static long copy(ReadableByteChannel in, WritableByteChannel out, int bufferSize) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize <= 0 ? DEFAULT_BUFFER_SIZE : bufferSize);
        long size = 0;
        try {
            while (in.read(byteBuffer) != EOF) {
                byteBuffer.flip();// 写转读
                size += out.write(byteBuffer);
                byteBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return size;
    }

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
     * @param data         字节数组
     * @param outputStream 输出流
     * @param isCloseOut   写入完毕是否关闭输出流
     * @throws IOException I/O异常
     * @since 1.2.1
     */
    public static void write(final byte[] data, final OutputStream outputStream, boolean isCloseOut) throws IOException {
        try {
            if (data != null)
                outputStream.write(data);
        } finally {
            if (isCloseOut)
                IOUtils.close(outputStream);
        }
    }

    /**
     * 将字节数组写入输出流，写入完毕不关闭输出流
     *
     * @param data         字节数组
     * @param outputStream 输出流
     * @throws IOException I/O异常
     */
    public static void write(final byte[] data, final OutputStream outputStream) throws IOException {
        if (data != null)
            outputStream.write(data);
    }

    /**
     * 将多部分内容写到流中
     *
     * @param outputStream 输出流
     * @param charset      字符集
     * @param isCloseOut   写入完毕是否关闭输出流
     * @param datas        写入的内容
     * @throws IOException I/O异常
     * @since 1.2.1
     */
    public static void write(OutputStream outputStream, Charset charset, boolean isCloseOut, String... datas) throws IOException {
        OutputStreamWriter outputStreamWriter = null;
        try {
            outputStreamWriter = getWriter(outputStream, charset);
            for (String data : datas) {
                if (data != null) {
                    outputStreamWriter.write(data);
                }
            }
            outputStreamWriter.flush();
        } finally {
            if (isCloseOut)
                IOUtils.close(outputStreamWriter);
        }
    }

    /**
     * 将多部分内容写到流中，写入完毕不关闭输出流
     *
     * @param outputStream 输出流
     * @param charset      字符集
     * @param datas        写入的内容
     * @throws IOException I/O异常
     * @since 1.2.1
     */
    public static void write(OutputStream outputStream, Charset charset, String... datas) throws IOException {
        write(outputStream, charset, false, datas);
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
            throw new RuntimeException("FileNotFoundException occurred.", e);
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

    /**
     * {@link InputStream} 转换为 {@link BufferedInputStream}
     *
     * @param inputStream {@link InputStream}
     * @return {@link BufferedInputStream}
     * @since 1.2.1
     */
    public static BufferedInputStream toBuffered(InputStream inputStream) {
        return (inputStream instanceof BufferedInputStream) ? (BufferedInputStream) inputStream : new BufferedInputStream(inputStream);
    }

    /**
     * {@link OutputStream} 转换为 {@link BufferedOutputStream}
     *
     * @param outputStream {@link OutputStream}
     * @return {@link BufferedOutputStream}
     * @since 1.2.1
     */
    public static BufferedOutputStream toBuffered(OutputStream outputStream) {
        return (outputStream instanceof BufferedOutputStream) ? (BufferedOutputStream) outputStream : new BufferedOutputStream(outputStream);
    }

    /**
     * 获得一个输出流对象
     *
     * @param file 文件
     * @return 输出流对象
     * @throws FileNotFoundException FileNotFoundException 异常
     * @since 1.2.1
     */
    public static BufferedOutputStream toBuffered(File file) throws FileNotFoundException {
        return new BufferedOutputStream(new FileOutputStream(FileUtils.createFile(file)));
    }

    /**
     * 获得一个输出流对象
     *
     * @param path 输出到的文件路径，绝对路径
     * @return 输出流对象
     * @throws FileNotFoundException FileNotFoundException 异常
     * @since 1.2.1
     */
    public static BufferedOutputStream getOutputStream(String path) throws FileNotFoundException {
        return toBuffered(new File(path));
    }

    /**
     * 获得一个 Writer
     *
     * @param outputStream 输入流
     * @param charset      字符集
     * @return OutputStreamWriter 对象
     * @since 1.2.1
     */
    public static OutputStreamWriter getWriter(OutputStream outputStream, Charset charset) {
        if (outputStream == null)
            return null;

        if (charset == null) {
            return new OutputStreamWriter(outputStream);
        } else {
            return new OutputStreamWriter(outputStream, charset);
        }
    }

    /**
     * 获得一个 Writer
     *
     * @param outputStream 输入流
     * @param charset      字符集
     * @return OutputStreamWriter 对象
     * @since 1.2.1
     */
    public static OutputStreamWriter getWriter(OutputStream outputStream, String charset) {
        return getWriter(outputStream, Charset.forName(charset));
    }

    /**
     * 获得一个 Writer，默认编码 UTF-8
     *
     * @param outputStream 输入流
     * @return OutputStreamWriter 对象
     * @since 1.2.1
     */
    public static OutputStreamWriter getUtf8Writer(OutputStream outputStream) {
        return getWriter(outputStream, CharsetUtils.CHARSET_UTF_8);
    }
}
