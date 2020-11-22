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
import java.nio.MappedByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

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
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }

        return size;
    }

    /**
     * 从 {@link Reader} 中按行读取数据
     *
     * @param <T>        集合类型
     * @param reader     {@link Reader}
     * @param collection 返回集合
     * @return 内容集合
     * @since 1.3.0
     */
    public static <T extends Collection<String>> T readLines(Reader reader, final T collection) {
        // 从返回的内容中读取所需内容
        final BufferedReader bufferedReader = getReader(reader);
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                collection.add(line);
            }
            return collection;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 从输入流中按行读取数据
     *
     * @param <T>         集合类型
     * @param inputStream 输入流
     * @param charset     字符集
     * @param collection  返回集合
     * @return 内容集合
     * @since 1.3.0
     */
    public static <T extends Collection<String>> T readLines(InputStream inputStream, Charset charset, T collection) {
        return readLines(getReader(inputStream, charset), collection);
    }

    /**
     * 从输入流中按行读取数据，使用 UTF-8 编码
     *
     * @param <T>         集合类型
     * @param inputStream 输入流
     * @param collection  返回集合
     * @return 内容集合
     * @since 1.3.0
     */
    public static <T extends Collection<String>> T readUtf8Lines(InputStream inputStream, T collection) {
        return readLines(inputStream, CharsetUtils.CHARSET_UTF_8, collection);
    }

    /**
     * 从输入流中读取数据到字节数组
     *
     * @param inputStream   输入流
     * @param isCloseStream 是否关闭输入流
     * @return 数据字节数组
     * @since 1.3.0
     */
    public static byte[] read2Byte(InputStream inputStream, boolean isCloseStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            copy(inputStream, outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("IOException occurred.", e);
        } finally {
            IOUtils.close(outputStream);
            if (isCloseStream)
                IOUtils.close(inputStream);
        }
    }

    /**
     * 从输入流中读取数据到字节数组
     *
     * @param inputStream 输入流
     * @return 数据字节数组
     */
    public static byte[] read2Byte(InputStream inputStream) {
        return read2Byte(inputStream, true);
    }

    /**
     * 从输入流中读取指定长度的字节数组，不关闭流
     *
     * @param inputStream 输入流
     * @param length      长度，小于等于0返回空byte数组
     * @return 数据字节数组
     * @since 1.3.0
     */
    public static byte[] read2Byte(InputStream inputStream, int length) {
        if (inputStream == null)
            return null;

        if (length <= 0)
            return new byte[0];

        byte[] b = new byte[length];
        int readLength;
        try {
            readLength = inputStream.read(b);
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred.", e);
        }
        if (readLength > 0 && readLength < length) {
            byte[] b2 = new byte[readLength];
            System.arraycopy(b, 0, b2, 0, readLength);
            return b2;
        } else {
            return b;
        }
    }

    /**
     * 从输入流中读取数据到字符串
     *
     * @param inputStream 输入流
     * @param charset     字符集
     * @return 数据字符串
     * @since 1.3.0
     */
    public static String read2String(InputStream inputStream, Charset charset) {
        StringBuilder stringBuilder = new StringBuilder();
        byte[] b = new byte[1024];
        try {
            for (int n; (n = inputStream.read(b)) != -1; ) {
                stringBuilder.append(new String(b, 0, n, charset));
            }
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred.", e);
        } finally {
            IOUtils.close(inputStream);
        }
        return stringBuilder.toString();
    }

    /**
     * 从 FileChannel 中读取数据
     *
     * @param fileChannel 文件管道
     * @param charset     字符集
     * @return 数据字符串
     * @throws IOException IO 异常
     */
    public static String read2String(FileChannel fileChannel, Charset charset) throws IOException {
        MappedByteBuffer buffer;
        try {
            buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size()).load();
        } catch (IOException e) {
            throw new IOException(e);
        }
        return StringUtils.toString(buffer, charset);
    }

    /**
     * 从输入流中读取数据到字符串
     *
     * @param inputStream 输入流
     * @return 数据字符串
     */
    public static String read2String(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        byte[] b = new byte[1024];
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
     * 从 {@link Reader} 中读取数据到字符串
     *
     * @param reader {@link Reader}
     * @return 数据字符串
     * @since 1.4.4
     */
    public static String read2String(Reader reader) {
        // 从返回的内容中读取所需内容
        final BufferedReader bufferedReader = getReader(reader);
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 从输入流中读取数据到字符串，使用 UTF-8 编码
     *
     * @param inputStream 输入流
     * @return 数据字符串
     * @since 1.3.0
     */
    public static String read2Utf8String(InputStream inputStream) {
        return read2String(inputStream, StandardCharsets.UTF_8);
    }

    /**
     * 从 FileChannel 中读取数据，使用 UTF-8 编码
     *
     * @param fileChannel 文件管道
     * @return 数据字符串
     * @throws IOException IO 异常
     * @since 1.4.0
     */
    public static String read2Utf8String(FileChannel fileChannel) throws IOException {
        return read2String(fileChannel, StandardCharsets.UTF_8);
    }

    /**
     * 将字节数组写入输出流
     *
     * @param data         字节数组
     * @param outputStream 输出流
     * @param isCloseOut   写入完毕是否关闭输出流
     * @since 1.2.1
     */
    public static void write(final byte[] data, final OutputStream outputStream, boolean isCloseOut) {
        try {
            if (data != null)
                outputStream.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
     */
    public static void write(final byte[] data, final OutputStream outputStream) {
        write(data, outputStream, false);
    }

    /**
     * 将多部分内容写到流中
     *
     * @param outputStream 输出流
     * @param charset      字符集
     * @param isCloseOut   写入完毕是否关闭输出流
     * @param datas        写入的内容
     * @since 1.2.1
     */
    public static void write(OutputStream outputStream, Charset charset, boolean isCloseOut, String... datas) {
        OutputStreamWriter outputStreamWriter = null;
        try {
            outputStreamWriter = getWriter(outputStream, charset);
            for (String data : datas) {
                if (data != null) {
                    outputStreamWriter.write(data);
                }
            }
            outputStreamWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
     * @since 1.2.1
     */
    public static void write(OutputStream outputStream, Charset charset, String... datas) {
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
        if (content == null)
            return null;

        return toStream(content.getBytes(charset));
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
        if (content == null)
            return null;

        return new ByteArrayInputStream(content);
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
     * 将 {@link InputStream} 转换为支持 mark 标记的流<br>
     * 若原流支持 mark 标记，则返回原流，否则使用 {@link BufferedInputStream} 包装
     *
     * @param inputStream {@link InputStream}
     * @return {@link InputStream}
     * @since 1.3.0
     */
    public static InputStream toMarkSupportStream(InputStream inputStream) {
        if (inputStream == null)
            return null;

        return inputStream.markSupported() ? inputStream : new BufferedInputStream(inputStream);
    }

    /**
     * 将 {@link InputStream} 转换为 {@link PushbackInputStream}<br>
     * 若原流已经是 {@link PushbackInputStream}，强转返回，否则新建一个
     *
     * @param inputStream  {@link InputStream}
     * @param pushBackSize 推后的字节数
     * @return {@link PushbackInputStream}
     * @since 1.3.0
     */
    public static PushbackInputStream toPushbackStream(InputStream inputStream, int pushBackSize) {
        return (inputStream instanceof PushbackInputStream) ? (PushbackInputStream) inputStream : new PushbackInputStream(inputStream, pushBackSize);
    }

    /**
     * 获取 {@link BufferedReader}
     *
     * @param in      输入流
     * @param charset 字符集
     * @return {@link BufferedReader} 对象
     * @since 1.3.0
     */
    public static BufferedReader getReader(InputStream in, Charset charset) {
        if (in == null)
            return null;

        InputStreamReader reader;
        if (charset == null) {
            reader = new InputStreamReader(in);
        } else {
            reader = new InputStreamReader(in, charset);
        }

        return new BufferedReader(reader);
    }

    /**
     * 获取 {@link BufferedReader}
     *
     * @param in          输入流
     * @param charsetName 字符集名称
     * @return {@link BufferedReader} 对象
     * @since 1.3.0
     */
    public static BufferedReader getReader(InputStream in, String charsetName) {
        return getReader(in, Charset.forName(charsetName));
    }

    /**
     * 获取 {@link BufferedReader}，默认使用 UTF-8 编码
     *
     * @param in 输入流
     * @return {@link BufferedReader} 对象
     * @since 1.3.0
     */
    public static BufferedReader getUtf8Reader(InputStream in) {
        return getReader(in, CharsetUtils.CHARSET_UTF_8);
    }

    /**
     * 获取 {@link BufferedReader}
     *
     * @param reader {@link Reader}，如果为 {@code nulll} 则返回 {@code nulll}
     * @return {@link BufferedReader} 对象
     * @since 1.3.0
     */
    public static BufferedReader getReader(Reader reader) {
        if (reader == null)
            return null;

        return (reader instanceof BufferedReader) ? (BufferedReader) reader : new BufferedReader(reader);
    }

    /**
     * 获取 {@link PushbackReader}
     *
     * @param reader       {@link Reader}
     * @param pushBackSize 推后的字节数
     * @return {@link PushbackReader} 对象
     * @since 3.1.0
     */
    public static PushbackReader getPushBackReader(Reader reader, int pushBackSize) {
        return (reader instanceof PushbackReader) ? (PushbackReader) reader : new PushbackReader(reader, pushBackSize);
    }

    /**
     * 获取 {@link OutputStreamWriter}
     *
     * @param outputStream 输入流
     * @param charset      字符集
     * @return {@link OutputStreamWriter} 对象
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
     * 获取 {@link OutputStreamWriter}
     *
     * @param outputStream 输入流
     * @param charsetName  字符集名称
     * @return {@link OutputStreamWriter} 对象
     * @since 1.2.1
     */
    public static OutputStreamWriter getWriter(OutputStream outputStream, String charsetName) {
        return getWriter(outputStream, Charset.forName(charsetName));
    }

    /**
     * 获取 {@link OutputStreamWriter}，默认编码 UTF-8
     *
     * @param outputStream 输入流
     * @return {@link OutputStreamWriter} 对象
     * @since 1.2.1
     */
    public static OutputStreamWriter getUtf8Writer(OutputStream outputStream) {
        return getWriter(outputStream, CharsetUtils.CHARSET_UTF_8);
    }
}
