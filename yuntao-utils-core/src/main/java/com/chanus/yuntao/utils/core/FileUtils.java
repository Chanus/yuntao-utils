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

import com.chanus.yuntao.utils.core.reflect.ClassUtils;

import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * 文件操作工具类
 *
 * @author Chanus
 * @date 2020-06-22 08:39:51
 * @since 1.0.0
 */
public class FileUtils {
    /**
     * 文件名和扩展名的分隔符
     */
    private static final String FILE_EXTENSION_SEPARATOR = ".";

    private FileUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 判断当前环境是否为 Windows 环境
     *
     * @return {@code true} 当前环境是 Windows 环境；{@code false} 当前环境不是 Windows 环境
     * @since 1.2.1
     */
    public static boolean isWindows() {
        return CharUtils.BACKSLASH == File.separatorChar;
    }

    /**
     * 列出目录文件<br>
     * 给定的绝对路径不能是压缩包中的路径
     *
     * @param path 目录绝对路径或者相对路径
     * @return 文件列表（包含目录）
     * @since 1.2.1
     */
    public static File[] ls(String path) {
        if (path == null) {
            return null;
        }

        File file = new File(path);
        if (file.isDirectory()) {
            return file.listFiles();
        }

        throw new RuntimeException(String.format("Path [%s] is not directory!", path));
    }

    /**
     * 判断文件或目录是否为空<br>
     * 目录：里面没有文件时为空；文件：文件大小为0时为空
     *
     * @param file 文件或目录
     * @return {@code true} 文件或目录为空；{@code false} 文件或目录不为空
     * @since 1.2.1
     */
    public static boolean isEmpty(File file) {
        if (file == null) {
            return true;
        }

        if (file.isDirectory()) {
            String[] subFiles = file.list();
            return ArrayUtils.isEmpty(subFiles);
        } else if (file.isFile()) {
            return file.length() <= 0;
        }

        return false;
    }

    /**
     * 判断文件或目录是否不为空<br>
     * 目录：里面有文件时不为空；文件：文件大小不为0时不为空
     *
     * @param file 文件或目录
     * @return {@code true} 文件或目录不为空；{@code false} 文件或目录为空
     * @since 1.2.1
     */
    public static boolean isNotEmpty(File file) {
        return !isEmpty(file);
    }

    /**
     * 递归遍历目录以及子目录中的所有文件，如果提供路径为文件，直接返回过滤结果
     *
     * @param path 当前遍历文件或目录的路径
     * @return 文件列表
     * @since 1.2.1
     */
    public static List<File> loopFiles(String path) {
        return loopFiles(newFile(path));
    }

    /**
     * 递归遍历目录以及子目录中的所有文件，如果提供 file 为文件，直接返回过滤结果
     *
     * @param file 当前遍历文件或目录
     * @return 文件列表
     * @since 1.2.1
     */
    public static List<File> loopFiles(File file) {
        return loopFiles(file, null);
    }

    /**
     * 递归遍历目录以及子目录中的所有文件<br>
     * 如果提供 file 为文件，直接返回过滤结果
     *
     * @param path       当前遍历文件或目录的路径
     * @param fileFilter 文件过滤规则对象，选择要保留的文件，只对文件有效，不过滤目录
     * @return 文件列表
     * @since 1.2.5
     */
    public static List<File> loopFiles(String path, FileFilter fileFilter) {
        return loopFiles(newFile(path), fileFilter);
    }

    /**
     * 递归遍历目录以及子目录中的所有文件<br>
     * 如果提供 file 为文件，直接返回过滤结果
     *
     * @param file       当前遍历文件或目录
     * @param fileFilter 文件过滤规则对象，选择要保留的文件，只对文件有效，不过滤目录
     * @return 文件列表
     * @since 1.2.5
     */
    public static List<File> loopFiles(File file, FileFilter fileFilter) {
        final List<File> fileList = new ArrayList<>();
        if (file == null || !file.exists()) {
            return fileList;
        }

        if (file.isDirectory()) {
            final File[] subFiles = file.listFiles();
            if (ArrayUtils.isNotEmpty(subFiles)) {
                for (File tmp : subFiles) {
                    fileList.addAll(loopFiles(tmp, fileFilter));
                }
            }
        } else {
            if (fileFilter == null || fileFilter.accept(file)) {
                fileList.add(file);
            }
        }

        return fileList;
    }

    /**
     * 递归遍历目录以及子目录中的所有文件<br>
     * 如果提供 file 为文件，直接返回过滤结果
     *
     * @param file       当前遍历文件或目录
     * @param maxDepth   遍历最大深度，-1表示遍历到没有目录为止
     * @param fileFilter 文件过滤规则对象，选择要保留的文件，只对文件有效，不过滤目录，null 表示接收全部文件
     * @return 文件列表
     * @since 1.2.5
     */
    public static List<File> loopFiles(File file, int maxDepth, final FileFilter fileFilter) {
        final List<File> fileList = new ArrayList<>();
        if (file == null || !file.exists()) {
            return fileList;
        } else if (!file.isDirectory()) {
            if (fileFilter == null || fileFilter.accept(file)) {
                fileList.add(file);
            }
            return fileList;
        }

        walkFiles(file.toPath(), maxDepth, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                final File file = path.toFile();
                if (fileFilter == null || fileFilter.accept(file)) {
                    fileList.add(file);
                }
                return FileVisitResult.CONTINUE;
            }
        });

        return fileList;
    }

    /**
     * 遍历指定目录下的文件并做处理
     *
     * @param start    起始路径，必须为目录
     * @param maxDepth 最大遍历深度，-1表示不限制深度
     * @param visitor  {@link FileVisitor} 接口，用于自定义在访问文件时，访问目录前后等节点做的操作
     * @see Files#walkFileTree(Path, java.util.Set, int, FileVisitor)
     * @since 1.2.5
     */
    public static void walkFiles(Path start, int maxDepth, FileVisitor<? super Path> visitor) {
        if (maxDepth < 0) {
            // < 0 表示遍历到最底层
            maxDepth = Integer.MAX_VALUE;
        }

        try {
            Files.walkFileTree(start, EnumSet.noneOf(FileVisitOption.class), maxDepth, visitor);
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred.", e);
        }
    }

    /**
     * 获得输入流
     *
     * @param file 文件
     * @return {@link BufferedInputStream}
     * @since 1.4.0
     */
    public static BufferedInputStream getInputStream(File file) {
        return StreamUtils.toBuffered(StreamUtils.toStream(file));
    }

    /**
     * 获得输入流
     *
     * @param path 文件路径
     * @return {@link BufferedInputStream}
     * @since 1.4.0
     */
    public static BufferedInputStream getInputStream(String path) {
        return getInputStream(newFile(path));
    }

    /**
     * 获得一个文件读取器
     *
     * @param file    文件
     * @param charset 字符集
     * @return {@link BufferedReader}
     * @since 1.4.0
     */
    public static BufferedReader getReader(File file, Charset charset) {
        return StreamUtils.getReader(getInputStream(file), charset);
    }

    /**
     * 获得一个文件读取器
     *
     * @param path    文件路径
     * @param charset 字符集
     * @return {@link BufferedReader}
     * @since 1.4.0
     */
    public static BufferedReader getReader(String path, Charset charset) {
        return getReader(newFile(path), charset);
    }

    /**
     * 获得一个文件读取器
     *
     * @param file 文件
     * @return {@link BufferedReader}
     * @since 1.4.0
     */
    public static BufferedReader getUtf8Reader(File file) {
        return getReader(file, StandardCharsets.UTF_8);
    }

    /**
     * 获得一个文件读取器
     *
     * @param path 文件路径
     * @return {@link BufferedReader}
     * @since 1.4.0
     */
    public static BufferedReader getUtf8Reader(String path) {
        return getReader(path, StandardCharsets.UTF_8);
    }

    /**
     * 读文件，文件的长度不能超过 Integer.MAX_VALUE
     *
     * @param file 文件
     * @return 字节码
     * @throws IOException IO 异常
     * @since 1.4.0
     */
    public static byte[] readBytes(File file) throws IOException {
        if (file == null) {
            return null;
        }

        long len = file.length();
        if (len >= Integer.MAX_VALUE) {
            throw new IOException("File is larger then max array size");
        }

        byte[] bytes = new byte[(int) len];
        FileInputStream in = null;
        int readLength;
        try {
            in = new FileInputStream(file);
            readLength = in.read(bytes);
            if (readLength < len) {
                throw new IOException(StringUtils.format("File length is [{}] but read [{}]!", len, readLength));
            }
        } catch (Exception e) {
            throw new IOException(e);
        } finally {
            IOUtils.close(in);
        }

        return bytes;
    }

    /**
     * 读文件，文件的长度不能超过 Integer.MAX_VALUE
     *
     * @param path 文件路径
     * @return 字节码
     * @throws IOException IO 异常
     * @since 1.4.0
     */
    public static byte[] readBytes(String path) throws IOException {
        return readBytes(newFile(path));
    }

    /**
     * 读文件
     *
     * @param file    文件
     * @param charset 字符集
     * @return 文件内容
     * @throws IOException IO 异常
     * @since 1.4.0
     */
    public static String readString(File file, Charset charset) throws IOException {
        return new String(readBytes(file), charset);
    }

    /**
     * 读文件
     *
     * @param path    文件路径
     * @param charset 字符集
     * @return 文件内容
     * @throws IOException IO 异常
     * @since 1.4.0
     */
    public static String readString(String path, Charset charset) throws IOException {
        return readString(newFile(path), charset);
    }

    /**
     * 读文件
     *
     * @param url     文件 URL
     * @param charset 字符集
     * @return 文件内容
     * @throws IOException IO 异常
     * @since 1.4.0
     */
    public static String readString(URL url, Charset charset) throws IOException {
        if (url == null) {
            throw new NullPointerException("Empty url provided!");
        }

        InputStream inputStream = null;
        try {
            inputStream = url.openStream();
            return StreamUtils.read2String(inputStream, charset);
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            IOUtils.close(inputStream);
        }
    }

    /**
     * 读文件，使用 UTF-8 编码
     *
     * @param file 文件
     * @return 文件内容
     * @throws IOException IO 异常
     * @since 1.4.0
     */
    public static String readUtf8String(File file) throws IOException {
        return readString(file, StandardCharsets.UTF_8);
    }

    /**
     * 读文件，使用 UTF-8 编码
     *
     * @param path 文件路径
     * @return 文件内容
     * @throws IOException IO 异常
     * @since 1.4.0
     */
    public static String readUtf8String(String path) throws IOException {
        return readString(path, StandardCharsets.UTF_8);
    }

    /**
     * 读文件，使用 UTF-8 编码
     *
     * @param url 文件 URL
     * @return 文件内容
     * @throws IOException IO 异常
     * @since 1.4.0
     */
    public static String readUtf8String(URL url) throws IOException {
        return readString(url, StandardCharsets.UTF_8);
    }

    /**
     * 从文件中读取每一行数据
     *
     * @param file    文件
     * @param charset 字符集
     * @return 文件中的每行内容的集合
     * @throws IOException IO 异常
     * @since 1.4.0
     */
    public static List<String> readLines(File file, Charset charset) throws IOException {
        BufferedReader reader = null;
        try {
            List<String> lines = new ArrayList<>();
            reader = getReader(file, charset);
            String line;
            while (true) {
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                lines.add(line);
            }
            return lines;
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            IOUtils.close(reader);
        }
    }

    /**
     * 从文件中读取每一行数据
     *
     * @param path    文件路径
     * @param charset 字符集
     * @return 文件中的每行内容的集合
     * @throws IOException IO 异常
     * @since 1.4.0
     */
    public static List<String> readLines(String path, Charset charset) throws IOException {
        return readLines(newFile(path), charset);
    }

    /**
     * 从文件中读取每一行数据
     *
     * @param url     文件的 URL
     * @param charset 字符集
     * @return 文件中的每行内容的集合
     * @throws IOException IO 异常
     * @since 1.4.0
     */
    public static List<String> readLines(URL url, Charset charset) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = url.openStream();
            return StreamUtils.readLines(inputStream, charset, new ArrayList<>());
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            IOUtils.close(inputStream);
        }
    }

    /**
     * 从文件中读取每一行数据，使用 UTF-8 编码
     *
     * @param file 文件
     * @return 文件中的每行内容的集合
     * @throws IOException IO 异常
     * @since 1.4.0
     */
    public static List<String> readUtf8Lines(File file) throws IOException {
        return readLines(file, StandardCharsets.UTF_8);
    }

    /**
     * 从文件中读取每一行数据，使用 UTF-8 编码
     *
     * @param path 文件路径
     * @return 文件中的每行内容的集合
     * @throws IOException IO 异常
     * @since 1.4.0
     */
    public static List<String> readUtf8Lines(String path) throws IOException {
        return readLines(path, StandardCharsets.UTF_8);
    }

    /**
     * 从文件中读取每一行数据，使用 UTF-8 编码
     *
     * @param url 文件的 URL
     * @return 文件中的每行内容的集合
     * @throws IOException IO 异常
     * @since 1.4.0
     */
    public static List<String> readUtf8Lines(URL url) throws IOException {
        return readLines(url, StandardCharsets.UTF_8);
    }

    /**
     * 获得一个输出流对象
     *
     * @param file 输出文件
     * @return 输出流对象
     * @since 1.4.0
     */
    public static BufferedOutputStream getOutputStream(File file) {
        final OutputStream outputStream;
        try {
            outputStream = new FileOutputStream(createFile(file));
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred.", e);
        }
        return StreamUtils.toBuffered(outputStream);
    }

    /**
     * 获得一个输出流对象
     *
     * @param path 输出到的文件路径，绝对路径
     * @return 输出流对象
     * @since 1.4.0
     */
    public static BufferedOutputStream getOutputStream(String path) {
        return getOutputStream(createFile(path));
    }

    /**
     * 获得一个带缓存的写入对象
     *
     * @param file     输出文件
     * @param charset  字符集
     * @param isAppend 是否向文件中追加内容，{@code true} 在文件结尾追加内容，{@code false} 覆盖文件内容
     * @return {@link BufferedWriter} 对象
     * @since 1.4.0
     */
    public static BufferedWriter getWriter(File file, Charset charset, boolean isAppend) {
        try {
            return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(createFile(file), isAppend), charset));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException occurred.", e);
        }
    }

    /**
     * 获得一个带缓存的写入对象
     *
     * @param path     输出到的文件路径，绝对路径
     * @param charset  字符集
     * @param isAppend 是否向文件中追加内容，{@code true} 在文件结尾追加内容，{@code false} 覆盖文件内容
     * @return {@link BufferedWriter} 对象
     * @since 1.4.0
     */
    public static BufferedWriter getWriter(String path, Charset charset, boolean isAppend) {
        return getWriter(createFile(path), charset, isAppend);
    }

    /**
     * 获得一个打印写入对象
     *
     * @param file     输出文件
     * @param charset  字符集
     * @param isAppend 是否向文件中追加内容，{@code true} 在文件结尾追加内容，{@code false} 覆盖文件内容
     * @return {@link PrintWriter} 对象
     * @since 1.4.0
     */
    public static PrintWriter getPrintWriter(File file, Charset charset, boolean isAppend) {
        return new PrintWriter(getWriter(file, charset, isAppend));
    }

    /**
     * 获得一个打印写入对象
     *
     * @param path     输出到的文件路径，绝对路径
     * @param charset  字符集
     * @param isAppend 是否向文件中追加内容，{@code true} 在文件结尾追加内容，{@code false} 覆盖文件内容
     * @return {@link PrintWriter} 对象
     * @since 1.4.0
     */
    public static PrintWriter getPrintWriter(String path, Charset charset, boolean isAppend) {
        return new PrintWriter(getWriter(path, charset, isAppend));
    }

    /**
     * 写文件
     *
     * @param file     文件
     * @param content  文件内容
     * @param charset  字符集
     * @param isAppend 是否向文件中追加内容，{@code true} 在文件结尾追加内容，{@code false} 覆盖文件内容
     * @return 被写入的文件
     * @since 1.4.0
     */
    public static File write(File file, String content, Charset charset, boolean isAppend) {
        if (file == null || StringUtils.isBlank(content)) {
            return file;
        }

        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = getWriter(file, charset, isAppend);
            bufferedWriter.write(content);
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred.", e);
        } finally {
            IOUtils.close(bufferedWriter);
        }

        return file;
    }

    /**
     * 写文件，覆盖模式
     *
     * @param file    文件
     * @param content 文件内容
     * @param charset 字符集
     * @return 被写入的文件
     * @since 1.4.0
     */
    public static File write(File file, String content, Charset charset) {
        return write(file, content, charset, false);
    }

    /**
     * 写文件
     *
     * @param path     文件路径
     * @param content  文件内容
     * @param charset  字符集
     * @param isAppend 是否向文件中追加内容，{@code true} 在文件结尾追加内容，{@code false} 覆盖文件内容
     * @return 被写入的文件
     * @since 1.4.0
     */
    public static File write(String path, String content, Charset charset, boolean isAppend) {
        return write(createFile(path), content, charset, isAppend);
    }

    /**
     * 写文件，覆盖模式
     *
     * @param path    文件路径
     * @param content 文件内容
     * @param charset 字符集
     * @return 被写入的文件
     * @since 1.4.0
     */
    public static File write(String path, String content, Charset charset) {
        return write(path, content, charset, false);
    }

    /**
     * 写文件，使用 UTF-8 编码
     *
     * @param file     文件
     * @param content  文件内容
     * @param isAppend 是否向文件中追加内容，{@code true} 在文件结尾追加内容，{@code false} 覆盖文件内容
     * @return 被写入的文件
     * @since 1.4.0
     */
    public static File write(File file, String content, boolean isAppend) {
        return write(file, content, StandardCharsets.UTF_8, isAppend);
    }

    /**
     * 写文件，使用 UTF-8 编码，覆盖模式
     *
     * @param file    文件
     * @param content 文件内容
     * @return 被写入的文件
     * @since 1.4.0
     */
    public static File write(File file, String content) {
        return write(file, content, false);
    }

    /**
     * 写文件，使用 UTF-8 编码
     *
     * @param path     文件路径
     * @param content  文件内容
     * @param isAppend 是否向文件中追加内容，{@code true} 在文件结尾追加内容，{@code false} 覆盖文件内容
     * @return 被写入的文件
     */
    public static File write(String path, String content, boolean isAppend) {
        return write(path, content, StandardCharsets.UTF_8, isAppend);
    }

    /**
     * 写文件，使用 UTF-8 编码，覆盖模式
     *
     * @param path    文件路径
     * @param content 文件内容
     * @return 被写入的文件
     * @since 1.4.0
     */
    public static File write(String path, String content) {
        return write(path, content, false);
    }

    /**
     * 写文件
     *
     * @param file     文件
     * @param contents 文件内容列表
     * @param charset  字符集
     * @param isAppend 是否向文件中追加内容，{@code true} 在文件结尾追加内容，{@code false} 覆盖文件内容
     * @return 被写入的文件
     * @since 1.4.0
     */
    public static File write(File file, List<String> contents, Charset charset, boolean isAppend) {
        if (file == null || CollectionUtils.isEmpty(contents)) {
            return file;
        }

        PrintWriter printWriter = null;
        try {
            printWriter = getPrintWriter(file, charset, isAppend);
            for (String line : contents) {
                printWriter.println(line);
                printWriter.flush();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.close(printWriter);
        }
        return file;
    }

    /**
     * 写文件，覆盖模式
     *
     * @param file     文件
     * @param contents 文件内容列表
     * @param charset  字符集
     * @return 被写入的文件
     * @since 1.4.0
     */
    public static File write(File file, List<String> contents, Charset charset) {
        return write(file, contents, charset, false);
    }

    /**
     * 写文件
     *
     * @param path     文件路径
     * @param contents 文件内容列表
     * @param charset  字符集
     * @param isAppend 是否向文件中追加内容，{@code true} 在文件结尾追加内容，{@code false} 覆盖文件内容
     * @return 被写入的文件
     * @since 1.4.0
     */
    public static File write(String path, List<String> contents, Charset charset, boolean isAppend) {
        return write(createFile(path), contents, charset, isAppend);
    }

    /**
     * 写文件，覆盖模式
     *
     * @param path     文件路径
     * @param contents 文件内容列表
     * @param charset  字符集
     * @return 被写入的文件
     * @since 1.4.0
     */
    public static File write(String path, List<String> contents, Charset charset) {
        return write(path, contents, charset, false);
    }

    /**
     * 写文件，使用 UTF-8 编码
     *
     * @param file     文件
     * @param contents 文件内容列表
     * @param isAppend 是否向文件中追加内容，{@code true} 在文件结尾追加内容，{@code false} 覆盖文件内容
     * @return 被写入的文件
     * @since 1.4.0
     */
    public static File write(File file, List<String> contents, boolean isAppend) {
        return write(file, contents, StandardCharsets.UTF_8, isAppend);
    }

    /**
     * 写文件，使用 UTF-8 编码，覆盖模式
     *
     * @param file     文件
     * @param contents 文件内容列表
     * @return 被写入的文件
     * @since 1.4.0
     */
    public static File write(File file, List<String> contents) {
        return write(file, contents, false);
    }

    /**
     * 写文件，使用 UTF-8 编码
     *
     * @param path     文件路径
     * @param contents 文件内容列表
     * @param isAppend 是否向文件中追加内容，{@code true} 在文件结尾追加内容，{@code false} 覆盖文件内容
     * @return 被写入的文件
     */
    public static File write(String path, List<String> contents, boolean isAppend) {
        return write(path, contents, StandardCharsets.UTF_8, isAppend);
    }

    /**
     * 写文件，使用 UTF-8 编码，覆盖模式
     *
     * @param path     文件路径
     * @param contents 文件内容列表
     * @return 被写入的文件
     * @since 1.4.0
     */
    public static File write(String path, List<String> contents) {
        return write(path, contents, false);
    }

    /**
     * 写入数据到文件
     *
     * @param file     文件
     * @param data     数据
     * @param isAppend 是否向文件中追加内容，{@code true} 在文件结尾追加内容，{@code false} 覆盖文件内容
     * @return 被写入的文件
     * @since 1.2.1
     */
    public static File write(File file, byte[] data, boolean isAppend) {
        if (file == null || data == null) {
            return file;
        }

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(createFile(file), isAppend);
            fileOutputStream.write(data, 0, data.length);
            fileOutputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred.", e);
        } finally {
            IOUtils.close(fileOutputStream);
        }
        return file;
    }

    /**
     * 写入数据到文件，覆盖模式
     *
     * @param file 文件
     * @param data 数据
     * @return 被写入的文件
     * @since 1.4.0
     */
    public static File write(File file, byte[] data) {
        return write(file, data, false);
    }

    /**
     * 写入数据到文件
     *
     * @param path     文件路径
     * @param data     数据
     * @param isAppend 是否向文件中追加内容，{@code true} 在文件结尾追加内容，{@code false} 覆盖文件内容
     * @return 被写入的文件
     * @since 1.2.1
     */
    public static File write(String path, byte[] data, boolean isAppend) {
        return write(createFile(path), data, isAppend);
    }

    /**
     * 写入数据到文件，覆盖模式
     *
     * @param path 文件路径
     * @param data 数据
     * @return 被写入的文件
     * @since 1.4.0
     */
    public static File write(String path, byte[] data) {
        return write(path, data, false);
    }

    /**
     * 将流的内容写入文件
     *
     * @param file        文件
     * @param inputStream 输入流
     * @param isAppend    是否向文件中追加内容，{@code true} 在文件结尾追加内容，{@code false} 覆盖文件内容
     * @return 被写入的文件
     * @since 1.2.1
     */
    public static File writeFromStream(File file, InputStream inputStream, boolean isAppend) {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(createFile(file), isAppend);
            byte[] data = new byte[1024];
            int length;
            while ((length = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, length);
            }
            outputStream.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException occurred.", e);
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred.", e);
        } finally {
            IOUtils.close(outputStream, inputStream);
        }
        return file;
    }

    /**
     * 将流的内容写入文件
     *
     * @param path        文件路径
     * @param inputStream 输入流
     * @param isAppend    是否向文件中追加内容，{@code true} 在文件结尾追加内容，{@code false} 覆盖文件内容
     * @return 被写入的文件
     * @since 1.2.1
     */
    public static File writeFromStream(String path, InputStream inputStream, boolean isAppend) {
        return writeFromStream(new File(path), inputStream, isAppend);
    }

    /**
     * 将文件写入流中
     *
     * @param file         文件
     * @param outputStream 输出流
     * @since 1.2.1
     */
    public static void writeToStream(File file, OutputStream outputStream) {
        try (FileInputStream in = new FileInputStream(file)) {
            StreamUtils.copy(in, outputStream);
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred.", e);
        }
    }

    /**
     * 将文件写入流中
     *
     * @param path         文件路径
     * @param outputStream 输出流
     * @since 1.2.1
     */
    public static void writeToStream(String path, OutputStream outputStream) {
        if (StringUtils.isBlank(path)) {
            return;
        }

        writeToStream(new File(path), outputStream);
    }

    /**
     * 写文件，追加模式
     *
     * @param file    文件
     * @param content 文件内容
     * @param charset 字符集
     * @return 被写入的文件
     * @since 1.4.0
     */
    public static File append(File file, String content, Charset charset) {
        return write(file, content, charset, true);
    }

    /**
     * 写文件，追加模式
     *
     * @param path    文件路径
     * @param content 文件内容
     * @param charset 字符集
     * @return 被写入的文件
     * @since 1.4.0
     */
    public static File append(String path, String content, Charset charset) {
        return write(path, content, charset, true);
    }

    /**
     * 写文件，使用 UTF-8 编码，追加模式
     *
     * @param file    文件
     * @param content 文件内容
     * @return 被写入的文件
     * @since 1.4.0
     */
    public static File append(File file, String content) {
        return write(file, content, true);
    }

    /**
     * 写文件，使用 UTF-8 编码，追加模式
     *
     * @param path    文件路径
     * @param content 文件内容
     * @return 被写入的文件
     * @since 1.4.0
     */
    public static File append(String path, String content) {
        return write(path, content, true);
    }

    /**
     * 写文件，追加模式
     *
     * @param file     文件
     * @param contents 文件内容列表
     * @param charset  字符集
     * @return 被写入的文件
     * @since 1.4.0
     */
    public static File append(File file, List<String> contents, Charset charset) {
        return write(file, contents, charset, true);
    }

    /**
     * 写文件，追加模式
     *
     * @param path     文件路径
     * @param contents 文件内容列表
     * @param charset  字符集
     * @return 被写入的文件
     * @since 1.4.0
     */
    public static File append(String path, List<String> contents, Charset charset) {
        return write(path, contents, charset, true);
    }

    /**
     * 写文件，使用 UTF-8 编码，追加模式
     *
     * @param file     文件
     * @param contents 文件内容列表
     * @return 被写入的文件
     * @since 1.4.0
     */
    public static File append(File file, List<String> contents) {
        return write(file, contents, true);
    }

    /**
     * 写文件，使用 UTF-8 编码，追加模式
     *
     * @param path     文件路径
     * @param contents 文件内容列表
     * @return 被写入的文件
     * @since 1.4.0
     */
    public static File append(String path, List<String> contents) {
        return write(path, contents, true);
    }

    /**
     * 写入数据到文件，追加模式
     *
     * @param file 文件
     * @param data 数据
     * @return 被写入的文件
     * @since 1.4.0
     */
    public static File append(File file, byte[] data) {
        return write(file, data, true);
    }

    /**
     * 写入数据到文件，追加模式
     *
     * @param path 文件路径
     * @param data 数据
     * @return 被写入的文件
     * @since 1.4.0
     */
    public static File append(String path, byte[] data) {
        return write(path, data, true);
    }

    /**
     * 获取文件名，包含扩展名
     *
     * @param file 文件
     * @return 文件名，包含扩展名
     */
    public static String getFileName(File file) {
        return file == null ? null : file.getName();
    }

    /**
     * 获取文件名，包含扩展名
     *
     * @param path 文件路径
     * @return 文件名，包含扩展名
     */
    public static String getFileName(String path) {
        if (path == null) {
            return null;
        }

        int len = path.length();
        if (len == 0) {
            return path;
        }

        if (CharUtils.isFileSeparator(path.charAt(len - 1))) {
            // 以分隔符结尾的去掉结尾分隔符
            len--;
        }

        int begin = 0;
        char c;
        for (int i = len - 1; i > -1; i--) {
            c = path.charAt(i);
            if (CharUtils.isFileSeparator(c)) {
                // 查找最后一个路径分隔符（/或者\）
                begin = i + 1;
                break;
            }
        }

        return path.substring(begin, len);
    }

    /**
     * 去除文件名中不支持的特殊字符（\ / : * " < > | ?）
     *
     * @param fileName 文件名
     * @return 去除特殊字符后的文件名
     * @since 1.4.5
     */
    public static String getValidFileName(String fileName) {
        return StringUtils.remove(fileName, StringUtils.BACKSLASH, StringUtils.SLASH, StringUtils.COLON,
                StringUtils.ASTERISK, StringUtils.DOUBLE_QUOTE, StringUtils.LESS_THAN, StringUtils.GREATER_THAN,
                StringUtils.VERTICAL_BAR, StringUtils.QUESTION_MARK);
    }

    /**
     * 获取文件名，不包含扩展名
     *
     * @param file 文件
     * @return 文件名，不包含扩展名
     */
    public static String getFileNameWithoutExtension(File file) {
        String fileName = getFileName(file);
        if (fileName == null) {
            return null;
        }

        if (fileName.contains(FILE_EXTENSION_SEPARATOR)) {
            return fileName.substring(0, fileName.lastIndexOf(FILE_EXTENSION_SEPARATOR));
        } else {
            return fileName;
        }
    }

    /**
     * 获取文件名，不包含扩展名
     *
     * @param path 文件路径
     * @return 文件名，不包含扩展名
     */
    public static String getFileNameWithoutExtension(String path) {
        return StringUtils.isBlank(path) ? null : getFileNameWithoutExtension(new File(path));
    }

    /**
     * 获取文件扩展名，扩展名不带“.”
     *
     * @param file 文件
     * @return 文件扩展名
     */
    public static String getFileExtension(File file) {
        String fileName = getFileName(file);
        if (fileName == null || !fileName.contains(FILE_EXTENSION_SEPARATOR)) {
            return null;
        }

        return fileName.substring(fileName.lastIndexOf(FILE_EXTENSION_SEPARATOR) + 1);
    }

    /**
     * 获取文件扩展名，扩展名不带“.”
     *
     * @param path 文件路径
     * @return 文件扩展名
     */
    public static String getFileExtension(String path) {
        return StringUtils.isBlank(path) ? null : getFileExtension(new File(path));
    }

    /**
     * 获取文件扩展名，扩展名不带“.”
     *
     * @param bufferedInputStream 文件流
     * @return 文件扩展名
     * @since 1.2.6
     */
    public static String getFileExtension(BufferedInputStream bufferedInputStream) {
        if (bufferedInputStream == null) {
            return null;
        }

        try {
            String mimeType = URLConnection.guessContentTypeFromStream(bufferedInputStream);
            return StringUtils.isBlank(mimeType) ? null : mimeType.substring(mimeType.indexOf("/") + 1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取文件所在目录
     *
     * @param file 文件
     * @return 文件目录
     */
    public static String getFolderName(File file) {
        if (file == null) {
            return null;
        }

        String filePath = file.getAbsolutePath();
        int filePosi = filePath.lastIndexOf(File.separator);

        return filePosi == -1 ? null : filePath.substring(0, filePosi);
    }

    /**
     * 获取文件所在目录
     *
     * @param path 文件路径
     * @return 文件目录
     */
    public static String getFolderName(String path) {
        if (StringUtils.isBlank(path)) {
            return null;
        }

        return getFolderName(new File(path));
    }

    /**
     * 获取文件大小
     *
     * @param file 文件或目录
     * @return 返回文件字节数，若文件不存在则返回0
     */
    public static long getFileSize(File file) {
        if (file == null || !file.exists()) {
            return 0L;
        }

        if (file.isDirectory()) {
            long size = 0L;
            File[] subFiles = file.listFiles();
            if (ArrayUtils.isEmpty(subFiles)) {
                return 0L;
            }
            for (File subFile : subFiles) {
                size += getFileSize(subFile);
            }
            return size;
        } else {
            return file.length();
        }
    }

    /**
     * 获取文件大小
     *
     * @param path 文件路径或目录路径
     * @return 返回文件字节数，若文件不存在则返回0
     */
    public static long getFileSize(String path) {
        if (StringUtils.isBlank(path)) {
            return 0L;
        }

        return getFileSize(new File(path));
    }

    /**
     * 获取文件的 MD5 值
     *
     * @param file 文件
     * @return 文件 MD5 值
     */
    public static String getFileMD5(File file) {
        if (file == null || !file.exists() || !file.isFile()) {
            return null;
        }

        FileInputStream fis = null;
        try {
            byte[] buffer = new byte[8192];
            int len;
            MessageDigest md = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(file);
            while ((len = fis.read(buffer)) != -1) {
                md.update(buffer, 0, len);
            }
            fis.close();
            return new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fis);
        }

        return null;
    }

    /**
     * 获取文件的 MD5 值
     *
     * @param path 文件路径
     * @return 文件 MD5 值
     */
    public static String getFileMD5(String path) {
        return StringUtils.isBlank(path) ? null : getFileMD5(new File(path));
    }

    /**
     * 获取文件的 Mime 类型
     *
     * @param path 文件全路径
     * @return 文件的 Mime 类型
     */
    public static String getFileMimeType(String path) {
        if (StringUtils.isBlank(path)) {
            return null;
        }

        return URLConnection.getFileNameMap().getContentTypeFor(path);
    }

    /**
     * 获取文件的 Mime 类型
     *
     * @param file 文件
     * @return 文件的 Mime 类型
     */
    public static String getFileMimeType(File file) {
        if (file == null) {
            return null;
        }

        return getFileMimeType(file.getAbsolutePath());
    }

    /**
     * 获取文件最后的修改时间
     *
     * @param file 文件
     * @return 文件最后的修改时间
     */
    public static Date getFileLastModifyTime(File file) {
        if (!isFileExist(file)) {
            return null;
        }

        return new Date(file.lastModified());
    }

    /**
     * 获取文件最后的修改时间
     *
     * @param path 文件全路径
     * @return 文件最后的修改时间
     */
    public static Date getFileLastModifyTime(String path) {
        if (!isFileExist(path)) {
            return null;
        }

        return getFileLastModifyTime(new File(path));
    }

    /**
     * 创建文件目录
     *
     * @param dir 文件目录
     * @return 创建的目录
     */
    public static File mkdirs(File dir) {
        if (dir == null) {
            return null;
        }

        if (!dir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            dir.mkdirs();
        }

        return dir;
    }

    /**
     * 创建文件目录
     *
     * @param dir 文件目录
     * @return 创建的目录
     */
    public static File mkdirs(String dir) {
        if (StringUtils.isBlank(dir)) {
            return null;
        }

        return mkdirs(new File(dir));
    }

    /**
     * 创建所给文件或目录的父目录
     *
     * @param file 文件或目录
     * @return 父目录
     * @since 1.5.0
     */
    public static File mkParentDirs(File file) {
        final File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            //noinspection ResultOfMethodCallIgnored
            parentFile.mkdirs();
        }
        return parentFile;
    }

    /**
     * 创建所给路径的父文件夹，如果存在直接返回此文件夹
     *
     * @param path 文件夹路径，使用 POSIX 格式，无论哪个平台
     * @return 创建的目录
     * @since 1.5.0
     */
    public static File mkParentDirs(String path) {
        if (path == null) {
            return null;
        }

        return mkParentDirs(new File(path));
    }

    /**
     * 创建文件
     *
     * @param file 文件
     */
    public static File createFile(File file) {
        if (file == null) {
            return null;
        }

        if (!file.exists()) {
            mkParentDirs(file);
            try {
                if (!file.isFile()) {
                    // noinspection ResultOfMethodCallIgnored
                    file.createNewFile();
                }
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred.", e);
            }
        }

        return file;
    }

    /**
     * 创建文件
     *
     * @param path 文件路径
     */
    public static File createFile(String path) {
        if (StringUtils.isBlank(path)) {
            return null;
        }

        return createFile(new File(path));
    }

    /**
     * 创建文件，此方法会检查 slip 漏洞
     *
     * @param parent 父目录
     * @param path   文件路径
     * @since 1.2.1
     */
    public static File createFile(String parent, String path) {
        if (StringUtils.isBlank(parent) || StringUtils.isBlank(path)) {
            return null;
        }

        return createFile(newFile(parent, path));
    }

    /**
     * 创建 File 对象，文件路径为空则返回 {@code null}
     *
     * @param path 文件路径
     * @return {@link File}
     * @since 1.3.0
     */
    public static File newFile(String path) {
        return StringUtils.isBlank(path) ? null : new File(path);
    }

    /**
     * 创建 File 对象，此方法会检查 slip 漏洞
     *
     * @param parent 父目录
     * @param path   文件路径
     * @return {@link File}
     * @since 1.3.0
     */
    public static File newFile(String parent, String path) {
        return newFile(new File(parent), path);
    }

    /**
     * 创建 File 对象，此方法会检查 slip 漏洞
     *
     * @param parent 父目录对象
     * @param path   文件路径
     * @return {@link File}
     * @since 1.3.0
     */
    public static File newFile(File parent, String path) {
        if (StringUtils.isBlank(path)) {
            throw new NullPointerException("File path is blank!");
        }

        return checkSlip(parent, new File(parent, path));
    }

    /**
     * 判断文件是否存在
     *
     * @param file 文件
     * @return {@code true} 文件存在；{@code false} 文件不存在
     */
    public static boolean isFileExist(File file) {
        if (file == null) {
            return false;
        }

        return file.exists() && file.isFile();
    }

    /**
     * 判断文件是否存在
     *
     * @param path 文件路径
     * @return {@code true} 文件存在；{@code false} 文件不存在
     */
    public static boolean isFileExist(String path) {
        if (StringUtils.isBlank(path)) {
            return false;
        }

        return isFileExist(new File(path));
    }

    /**
     * 判断文件目录是否存在
     *
     * @param dir 文件目录
     * @return {@code true} 文件目录存在；{@code false} 文件目录不存在
     */
    public static boolean isFolderExist(File dir) {
        if (dir == null) {
            return false;
        }

        return dir.exists() && dir.isDirectory();
    }

    /**
     * 判断文件目录是否存在
     *
     * @param dir 文件目录
     * @return {@code true} 文件目录存在；{@code false} 文件目录不存在
     */
    public static boolean isFolderExist(String dir) {
        if (StringUtils.isBlank(dir)) {
            return false;
        }

        return isFolderExist(new File(dir));
    }

    /**
     * 判断两个文件是否是同一个文件
     *
     * @param file1 文件1
     * @param file2 文件2
     * @return {@code true} 两个文件是同一个文件；{@code false} 两个文件不是同一个文件
     * @since 1.2.1
     */
    public static boolean equals(File file1, File file2) {
        if (!file1.exists() || !file2.exists()) {
            // 两个文件都不存在判断其路径是否相同，对于一个存在一个不存在的情况，一定不相同
            return !file1.exists() && !file2.exists() && pathEquals(file1, file2);
        }
        try {
            return Files.isSameFile(file1.toPath(), file2.toPath());
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 判断两个文件的路径是否相同<br>
     * 取两个文件的绝对路径比较，在 Windows 下忽略大小写，在 Linux 下不忽略
     *
     * @param file1 文件1
     * @param file2 文件2
     * @return {@code true} 两个文件的路径相同；{@code false} 两个文件的路径不相同
     * @since 1.2.1
     */
    public static boolean pathEquals(File file1, File file2) {
        if (isWindows()) {
            // Windows环境
            try {
                if (StringUtils.equalsIgnoreCase(file1.getCanonicalPath(), file2.getCanonicalPath())) {
                    return true;
                }
            } catch (Exception e) {
                if (StringUtils.equalsIgnoreCase(file1.getAbsolutePath(), file2.getAbsolutePath())) {
                    return true;
                }
            }
        } else {
            // 类Unix环境
            try {
                if (StringUtils.equals(file1.getCanonicalPath(), file2.getCanonicalPath())) {
                    return true;
                }
            } catch (Exception e) {
                if (StringUtils.equals(file1.getAbsolutePath(), file2.getAbsolutePath())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 删除文件
     * <ul>
     *    <li>{@code files}是文件，则删除该文件</li>
     *    <li>{@code files}是文件目录，则删除该目录及其子目录下所有文件</li>
     * </ul>
     *
     * @param files 文件
     */
    public static void delete(File... files) {
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file == null || !file.exists()) {
                break;
            }
            if (file.isFile()) {
                // 是文件
                file.delete();
            } else if (file.isDirectory()) {
                // 是目录
                for (File f : file.listFiles()) {
                    if (f.isFile()) {
                        f.delete();
                    } else if (f.isDirectory()) {
                        delete(f);
                    }
                }
            }
            file.delete();
        }
    }

    /**
     * 删除文件
     * <ul>
     *     <li>{@code paths} 是文件路径，则删除该文件</li>
     *     <li>{@code paths} 是文件目录，则删除该目录及其子目录下所有文件</li>
     * </ul>
     *
     * @param paths 文件路径
     */
    public static void delete(String... paths) {
        if (paths == null) {
            return;
        }

        File file;
        for (String path : paths) {
            if (StringUtils.isBlank(path)) {
                break;
            }
            file = new File(path);
            if (!file.exists()) {
                break;
            }
            if (file.isFile()) {
                // 是文件
                file.delete();
            } else if (file.isDirectory()) {
                // 是目录
                for (File f : file.listFiles()) {
                    if (f.isFile()) {
                        f.delete();
                    } else if (f.isDirectory()) {
                        delete(f.getAbsolutePath());
                    }
                }
            }
            file.delete();
        }
    }

    /**
     * 快速删除大文件
     *
     * @param file 待处理文件
     */
    public static void deleteBigFile(File file) {
        if (file != null) {
            clean(file);
            file.delete();
        }
    }

    /**
     * 快速删除大文件
     *
     * @param path 待处理文件全路径
     */
    public static void deleteBigFile(String path) {
        if (StringUtils.isNotBlank(path)) {
            deleteBigFile(new File(path));
        }
    }

    /**
     * 文件拷贝<br>
     * 拷贝规则为：
     * <pre>
     * 1、源为文件，目标为已存在目录，则拷贝到目录下，文件名不变
     * 2、源为文件，目标为不存在路径，则目标以文件对待（自动创建父级目录）比如：/dest/aaa，如果 aaa 不存在，则 aaa 被当作文件名
     * 3、源为文件，目标是一个已存在的文件，则当 {@code isCover} 为 true 时会被覆盖相同文件，否则不覆盖
     * </pre>
     *
     * @param sourceFile 源文件
     * @param targetFile 目标文件或目录
     * @param isCover    是否覆盖目标文件
     * @return 拷贝后目标的文件或目录
     * @since 1.2.1
     */
    public static File copyFile(File sourceFile, File targetFile, boolean isCover) {
        try {
            if (sourceFile == null || !sourceFile.exists()) {
                throw new IOException("The source file is not exist!");
            }
            if (!sourceFile.isFile()) {
                throw new IOException("The source file is not a file!");
            }
            if (targetFile == null) {
                throw new IOException("The directory file or directory is null!");
            }
            if (equals(sourceFile, targetFile)) {
                throw new IOException("Files source file and target file are equal!");
            }

            internalCopyFile(sourceFile, targetFile, isCover);
            return targetFile;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred.", e);
        }
    }

    /**
     * 文件拷贝<br>
     * 拷贝规则为：
     * <pre>
     * 1、源为文件，目标为已存在目录，则拷贝到目录下，文件名不变
     * 2、源为文件，目标为不存在路径，则目标以文件对待（自动创建父级目录）比如：/dest/aaa，如果 aaa 不存在，则 aaa 被当作文件名
     * 3、源为文件，目标是一个已存在的文件，文件相同时覆盖
     * </pre>
     *
     * @param sourceFile 源文件
     * @param targetFile 目标文件或目录
     * @return 拷贝后目标的文件或目录
     */
    public static File copyFile(File sourceFile, File targetFile) {
        return copyFile(sourceFile, targetFile, true);
    }

    /**
     * 文件拷贝<br>
     * 拷贝规则为：
     * <pre>
     * 1、源为文件，目标为已存在目录，则拷贝到目录下，文件名不变
     * 2、源为文件，目标为不存在路径，则目标以文件对待（自动创建父级目录）比如：/dest/aaa，如果 aaa 不存在，则 aaa 被当作文件名
     * 3、源为文件，目标是一个已存在的文件，则当 {@code isCover} 为 true 时会被覆盖相同文件，否则不覆盖
     * </pre>
     *
     * @param sourcePath 源文件路径
     * @param targetPath 目标文件或目录路径
     * @param isCover    是否覆盖目标文件
     * @return 拷贝后目标的文件或目录
     * @since 1.2.1
     */
    public static File copyFile(String sourcePath, String targetPath, boolean isCover) {
        return copyFile(new File(sourcePath), new File(targetPath), isCover);
    }

    /**
     * 文件拷贝<br>
     * 拷贝规则为：
     * <pre>
     * 1、源为文件，目标为已存在目录，则拷贝到目录下，文件名不变
     * 2、源为文件，目标为不存在路径，则目标以文件对待（自动创建父级目录）比如：/dest/aaa，如果 aaa 不存在，则 aaa 被当作文件名
     * 3、源为文件，目标是一个已存在的文件，文件相同时覆盖
     * </pre>
     *
     * @param sourcePath 源文件路径
     * @param targetPath 目标文件或目录路径
     * @return 拷贝后目标的文件或目录
     */
    public static File copyFile(String sourcePath, String targetPath) {
        return copyFile(sourcePath, targetPath, true);
    }

    /**
     * 文件夹拷贝<br>
     * 拷贝规则为：
     * <pre>
     * 1、源为目录，目标为已存在目录，当 {@code isCopyContentIfDir} 为 true 时，只拷贝目录中的内容到目标目录中，否则整个源目录连同其子目录拷贝到目标目录中
     * 2、源为目录，目标为不存在路径，则自动创建目标为新目录，然后按照规则1复制
     * 3、源为目录，目标为文件，抛出IO异常
     * </pre>
     *
     * @param sourceDir          源目录
     * @param targetDir          目标目录
     * @param isCover            是否覆盖目标文件
     * @param isOnlyCopyFile     是否只拷贝文件而忽略子目录
     * @param isCopyContentIfDir 是否只拷贝目录下的内容
     * @return 拷贝后目标的目录
     * @since 1.2.1
     */
    public static File copyDir(File sourceDir, File targetDir, boolean isCover, boolean isOnlyCopyFile, boolean isCopyContentIfDir) {
        try {
            if (sourceDir == null || !sourceDir.exists()) {
                throw new IOException("The source directory is not exist!");
            }
            if (!sourceDir.isDirectory()) {
                throw new IOException("The source directory is not a directory!");
            }
            if (targetDir == null) {
                throw new IOException("The destination directory is null!");
            }
            if (targetDir.exists() && !targetDir.isDirectory()) {
                throw new IOException("The destination directory is not a directory!");
            }
            if (equals(sourceDir, targetDir)) {
                throw new IOException("Directories source directory and target directory are equal!");
            }
            if (isSub(sourceDir, targetDir)) {
                throw new IOException("The destination directory is a sub directory of the source directory!");
            }

            final File subDir = isCopyContentIfDir ? targetDir : mkdirs(new File(targetDir, sourceDir.getName()));
            internalCopyDirContent(sourceDir, subDir, isCover, isOnlyCopyFile);
            return targetDir;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred.", e);
        }
    }

    /**
     * 文件夹拷贝<br>
     * 拷贝规则为：
     * <pre>
     * 1、源为目录，目标为已存在目录，只拷贝目录中的内容到目标目录中，会覆盖相同文件
     * 2、源为目录，目标为不存在路径，则自动创建目标为新目录，然后按照规则1复制
     * 3、源为目录，目标为文件，抛出IO异常
     * </pre>
     *
     * @param sourceDir 源目录
     * @param targetDir 目标目录
     * @return 拷贝后目标的目录
     */
    public static File copyDir(File sourceDir, File targetDir) {
        return copyDir(sourceDir, targetDir, true, false, true);
    }

    /**
     * 文件夹拷贝<br>
     * 拷贝规则为：
     * <pre>
     * 1、源为目录，目标为已存在目录，当 {@code isCopyContentIfDir} 为 true 时，只拷贝目录中的内容到目标目录中，否则整个源目录连同其子目录拷贝到目标目录中
     * 2、源为目录，目标为不存在路径，则自动创建目标为新目录，然后按照规则1复制
     * 3、源为目录，目标为文件，抛出IO异常
     * </pre>
     *
     * @param sourceDirPath      源目录路径
     * @param targetDirPath      目标目录路径
     * @param isCover            是否覆盖目标文件
     * @param isOnlyCopyFile     是否只拷贝文件而忽略子目录
     * @param isCopyContentIfDir 是否只拷贝目录下的内容
     * @return 拷贝后目标的目录
     * @since 1.2.1
     */
    public static File copyDir(String sourceDirPath, String targetDirPath, boolean isCover, boolean isOnlyCopyFile, boolean isCopyContentIfDir) {
        return copyDir(new File(sourceDirPath), new File(targetDirPath), isCover, isOnlyCopyFile, isCopyContentIfDir);
    }

    /**
     * 文件夹拷贝<br>
     * 拷贝规则为：
     * <pre>
     * 1、源为目录，目标为已存在目录，只拷贝目录中的内容到目标目录中，会覆盖相同文件
     * 2、源为目录，目标为不存在路径，则自动创建目标为新目录，然后按照规则1复制
     * 3、源为目录，目标为文件，抛出IO异常
     * </pre>
     *
     * @param sourceDirPath 源目录路径
     * @param targetDirPath 目标目录路径
     * @return 拷贝后目标的目录
     */
    public static File copyDir(String sourceDirPath, String targetDirPath) {
        return copyDir(new File(sourceDirPath), new File(targetDirPath));
    }

    /**
     * 文件拷贝<br>
     * 拷贝规则为：
     * <pre>
     * 1、源为文件，目标为已存在目录，则拷贝到目录下，文件名不变
     * 2、源为文件，目标为不存在路径，则目标以文件对待（自动创建父级目录）比如：/dest/aaa，如果 aaa 不存在，则 aaa 被当作文件名
     * 3、源为文件，目标是一个已存在的文件，则当 {@code isCover} 为 true 时会被覆盖相同文件，否则不覆盖
     * 4、源为目录，目标为已存在目录，当 {@code isCopyContentIfDir} 为 true 时，只拷贝目录中的内容到目标目录中，否则整个源目录连同其子目录拷贝到目标目录中
     * 5、源为目录，目标为不存在路径，则自动创建目标为新目录，然后按照规则4复制
     * 6、源为目录，目标为文件，抛出IO异常
     * 7、源路径和目标路径相同时，抛出IO异常
     * </pre>
     *
     * @param source             源文件或目录
     * @param target             目标文件或目录
     * @param isCover            是否覆盖目标文件
     * @param isOnlyCopyFile     当拷贝来源是目录时是否只拷贝文件而忽略子目录
     * @param isCopyContentIfDir 当拷贝来源是目录时是否只拷贝目录下的内容
     * @return 拷贝后目标的文件或目录
     * @since 1.2.1
     */
    public static File copy(File source, File target, boolean isCover, boolean isOnlyCopyFile, boolean isCopyContentIfDir) throws IOException {
        if (source == null || !source.exists()) {
            throw new IOException("The source file or directory is not exist!");
        }
        if (target == null) {
            throw new IOException("The destination file or directory is null!");
        }
        if (equals(source, target)) {
            throw new IOException("Files source and target are equal!");
        }

        if (source.isDirectory()) {
            // 复制目录
            if (target.exists() && !target.isDirectory()) {
                // 源为目录，目标为文件，抛出IO异常
                throw new IOException("The source is a directory but the destination is a file!");
            }
            if (isSub(source, target)) {
                throw new IOException("The destination directory is a sub directory of the source directory!");
            }

            final File subDest = isCopyContentIfDir ? target : mkdirs(new File(target, source.getName()));
            internalCopyDirContent(source, subDest, isCover, isOnlyCopyFile);
        } else {// 复制文件
            internalCopyFile(source, target, isCover);
        }
        return target;
    }

    /**
     * 文件拷贝<br>
     * 拷贝规则为：
     * <pre>
     * 1、源为文件，目标为已存在目录，则拷贝到目录下，文件名不变
     * 2、源为文件，目标为不存在路径，则目标以文件对待（自动创建父级目录）比如：/dest/aaa，如果 aaa 不存在，则 aaa 被当作文件名
     * 3、源为文件，目标是一个已存在的文件，则当 {@code isCover} 为 true 时会被覆盖相同文件，否则不覆盖
     * 4、源为目录，目标为已存在目录，只拷贝目录中的内容到目标目录中
     * 5、源为目录，目标为不存在路径，则自动创建目标为新目录，然后按照规则4复制
     * 6、源为目录，目标为文件，抛出IO异常
     * 7、源路径和目标路径相同时，抛出IO异常
     * </pre>
     *
     * @param source  源文件或目录
     * @param target  目标文件或目录
     * @param isCover 是否覆盖目标文件
     * @return 拷贝后目标的文件或目录
     * @since 1.2.1
     */
    public static File copy(File source, File target, boolean isCover) throws IOException {
        return copy(source, target, isCover, false, true);
    }

    /**
     * 移动文件或者目录
     *
     * @param source  源文件或目录
     * @param target  目标文件或目录
     * @param isCover 是否覆盖目标，只有目标为文件才覆盖
     * @throws IOException IO异常
     * @since 1.2.1
     */
    public static void move(File source, File target, boolean isCover) throws IOException {
        if (source == null || !source.exists()) {
            throw new IOException("The source file or directory is not exist!");
        }

        // 来源为文件夹，目标为文件
        if (source.isDirectory() && target.isFile()) {
            throw new IOException(String.format("Can not move directory [%s] to file [%s]", source.getPath(), target.getPath()));
        }

        // 只有目标为文件的情况下覆盖之
        if (isCover && target.isFile()) {
            // noinspection ResultOfMethodCallIgnored
            target.delete();
        }

        // 来源为文件，目标为文件夹
        if (source.isFile() && target.isDirectory()) {
            target = new File(target, source.getName());
        }

        if (!source.renameTo(target)) {
            // 在文件系统不同的情况下，renameTo 会失败，此时使用 copy，然后删除原文件
            try {
                copy(source, target, isCover);
            } catch (Exception e) {
                throw new IOException(String.format("Move [%s] to [%s] failed!", source.getPath(), target.getPath()), e);
            }
            // 复制后删除源
            delete(source);
        }
    }

    /**
     * 移动文件或者目录
     *
     * @param source 源文件或目录
     * @param target 目标文件或目录
     * @throws IOException IO异常
     */
    public static void move(File source, File target) throws IOException {
        move(source, target, true);
    }

    /**
     * 移动文件或者目录
     *
     * @param sourcePath 源文件或目录路径
     * @param targetPath 目标文件或目录目录
     * @throws IOException IO异常
     */
    public static void move(String sourcePath, String targetPath) throws IOException {
        if (StringUtils.isBlank(sourcePath) || StringUtils.isBlank(targetPath)) {
            return;
        }
        move(new File(sourcePath), new File(targetPath));
    }

    /**
     * 修改文件或目录的文件名，不变更路径，只是简单修改文件名
     *
     * @param file        被修改的文件
     * @param newName     新的文件名，包括扩展名
     * @param isRetainExt 是否保留原文件的扩展名，如果保留，则 {@code newName} 不需要加扩展名
     * @return 目标文件
     * @since 1.2.1
     */
    public static File rename(File file, String newName, boolean isRetainExt) {
        if (isRetainExt) {
            final String extensionName = getFileExtension(file);
            if (StringUtils.isNotBlank(extensionName)) {
                newName = newName.concat(".").concat(extensionName);
            }
        }
        final Path path = file.toPath();
        try {
            return Files.move(path, path.resolveSibling(newName)).toFile();
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred.", e);
        }
    }

    /**
     * 修改文件或目录的文件名，不变更路径，只是简单修改文件名
     *
     * @param file    被修改的文件
     * @param newName 新的文件名，包括扩展名
     * @return 目标文件
     * @since 1.2.1
     */
    public static File rename(File file, String newName) {
        return rename(file, newName, false);
    }

    /**
     * 清空文件
     *
     * @param file 待处理文件
     */
    public static void clean(File file) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(StringUtils.EMPTY);
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred.", e);
        } finally {
            IOUtils.close(fileWriter);
        }
    }

    /**
     * 清空文件
     *
     * @param path 待处理文件全路径
     */
    public static void clean(String path) {
        if (StringUtils.isNotBlank(path)) {
            clean(new File(path));
        }
    }

    /**
     * 判断给定的目录是否为给定文件或文件夹的子目录
     *
     * @param parent 父目录
     * @param sub    子目录
     * @return {@code true} 子目录是为父目录的子目录；{@code false} 子目录不是否为父目录的子目录
     * @since 1.2.1
     */
    public static boolean isSub(File parent, File sub) {
        if (parent == null || sub == null) {
            return false;
        }

        return sub.toPath().startsWith(parent.toPath());
    }

    /**
     * 获取相对子路径
     *
     * @param rootDir 绝对父路径
     * @param file    文件
     * @return 相对子路径
     * @since 1.3.0
     */
    public static String subPath(String rootDir, File file) {
        try {
            return subPath(rootDir, file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取相对子路径，忽略大小写
     *
     * @param dirPath  父路径
     * @param filePath 文件路径
     * @return 相对子路径
     * @since 1.3.0
     */
    public static String subPath(String dirPath, String filePath) {
        if (StringUtils.isNotEmpty(dirPath) && StringUtils.isNotEmpty(filePath)) {
            dirPath = StringUtils.removeSuffix(dirPath, "/");

            final String result = StringUtils.removePrefixIgnoreCase(filePath, dirPath);
            return StringUtils.removePrefix(result, "/");
        }
        return filePath;
    }

    /**
     * 文件转换成 Base64 字符串
     *
     * @param inputStream 文件流
     * @return Base64 编码的字符串
     * @since 1.2.4
     */
    public static String toBase64(InputStream inputStream) {
        // 读取图片字节数组
        try {
            byte[] data = new byte[inputStream.available()];
            inputStream.read(data);

            // 对字节数组进行 Base64 编码，得到 Base64 编码的字符串
            return Base64.getEncoder().encodeToString(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.close(inputStream);
        }
    }

    /**
     * 文件转换成 Base64 字符串
     *
     * @param file 文件
     * @return Base64 编码的字符串
     * @since 1.2.4
     */
    public static String toBase64(File file) {
        try {
            return toBase64(new FileInputStream(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 在线文件转换成 Base64 字符串
     *
     * @param fileUrl 在线文件路径
     * @return Base64 编码的字符串
     * @since 1.2.4
     */
    public static String toBase64Online(String fileUrl) {
        BufferedInputStream bis = HttpUtils.downloadGet(fileUrl);
        byte[] data = StreamUtils.read2Byte(bis);
        // 对字节数组进行 Base64 编码，得到 Base64 编码的字符串
        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * 将 Base64 编码的字符串生成文件
     *
     * @param base64String Base64 编码的字符串
     * @param filePath     文件路径
     * @param fileName     文件名
     * @return Base64 编码的字符串生成的文件
     * @since 1.2.4
     */
    public static File base64ToFile(String base64String, String filePath, String fileName) {
        byte[] data = Base64.getDecoder().decode(base64String);

        File file = createFile(filePath + File.separator + fileName);
        write(file, data, false);

        return file;
    }

    /**
     * 将 Base64 编码的字符串生成文件
     *
     * @param base64String Base64 编码的字符串
     * @param filePath     文件全路径
     * @return Base64 编码的字符串生成的文件
     * @since 1.2.4
     */
    public static File base64ToFile(String base64String, String filePath) {
        byte[] data = Base64.getDecoder().decode(base64String);

        File file = createFile(filePath);
        write(file, data, false);

        return file;
    }

    /**
     * 拷贝目录内容，拷贝内容的意思为源目录下的所有文件和目录拷贝到另一个目录下，而不拷贝源目录本身
     *
     * @param source         源目录
     * @param target         目标目录
     * @param isCover        是否覆盖目标文件
     * @param isOnlyCopyFile 当拷贝来源是目录时是否只拷贝文件而忽略子目录
     * @throws IOException IO异常
     * @since 1.2.1
     */
    private static void internalCopyDirContent(File source, File target, boolean isCover, boolean isOnlyCopyFile) throws IOException {
        if (!target.exists()) {
            // 目标为不存在路径，创建为目录
            target.mkdirs();
        } else if (!target.isDirectory()) {
            throw new IOException(String.format("The source [%s] is a directory but the destination [%s] is a file!", source.getPath(), target.getPath()));
        }

        final String[] files = source.list();
        if (ArrayUtils.isNotEmpty(files)) {
            File srcFile;
            File destFile;
            for (String file : files) {
                srcFile = new File(source, file);
                destFile = isOnlyCopyFile ? target : new File(target, file);
                // 递归复制
                if (srcFile.isDirectory()) {
                    internalCopyDirContent(srcFile, destFile, isCover, isOnlyCopyFile);
                } else {
                    internalCopyFile(srcFile, destFile, isCover);
                }
            }
        }
    }

    /**
     * 拷贝文件<br>
     * 情况如下：
     * <pre>
     * 1、如果目标是一个不存在的路径，则目标以文件对待（自动创建父级目录）比如：/dest/aaa，如果 aaa 不存在，则 aaa 被当作文件名
     * 2、如果目标是一个已存在的目录，则文件拷贝到此目录下，文件名与原文件名一致
     * </pre>
     *
     * @param source  源文件，必须为文件
     * @param target  目标文件，如果非覆盖模式必须为目录
     * @param isCover 是否覆盖目标文件
     * @throws IOException IO异常
     * @since 1.2.1
     */
    private static void internalCopyFile(File source, File target, boolean isCover) throws IOException {
        // 如果已经存在目标文件，切为不覆盖模式，跳过该文件
        if (target.exists()) {
            if (target.isDirectory()) {
                // 目标为目录，目录下创建同名文件
                target = new File(target, source.getName());
            }

            if (target.exists() && !isCover) {
                // 非覆盖模式跳过
                return;
            }
        } else {
            // 路径不存在则创建父目录
            target.getParentFile().mkdirs();
        }

        final ArrayList<CopyOption> copyOptions = new ArrayList<>(2);
        if (isCover) {
            copyOptions.add(StandardCopyOption.REPLACE_EXISTING);
        }

        Files.copy(source.toPath(), target.toPath(), copyOptions.toArray(new CopyOption[0]));
    }

    /**
     * 检查父完整路径是否为子路径的前半部分，如果不是说明不是子路径，可能存在 slip 注入
     *
     * @param parentFile 父文件或目录
     * @param file       子文件或目录
     * @return 子文件或目录
     * @since 1.3.0
     */
    public static File checkSlip(File parentFile, File file) {
        if (parentFile != null && file != null) {
            String parentCanonicalPath;
            String canonicalPath;
            try {
                parentCanonicalPath = parentFile.getCanonicalPath();
                canonicalPath = file.getCanonicalPath();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (!canonicalPath.startsWith(parentCanonicalPath)) {
                throw new IllegalArgumentException(StringUtils.format("New file is outside of the parent dir: {}", file.getName()));
            }
        }
        return file;
    }

    /**
     * 获取指定层级的父路径
     *
     * @param filePath 目录或文件路径
     * @param level    层级
     * @return 父路径，如果不存在返回 {@code null}
     * @since 1.3.0
     */
    public static String getParent(String filePath, int level) {
        final File parent = getParent(newFile(filePath), level);
        try {
            return parent == null ? null : parent.getCanonicalPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取指定层级的父路径
     *
     * @param file  目录或文件
     * @param level 层级
     * @return 父路径 File，如果不存在返回 {@code null}
     * @since 1.3.0
     */
    public static File getParent(File file, int level) {
        if (level < 1 || file == null) {
            return file;
        }

        try {
            File parentFile = file.getCanonicalFile().getParentFile();
            return level == 1 ? parentFile : getParent(parentFile, level - 1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取 Web 项目下的 web root 路径<br>
     * 原理是首先获取 ClassPath 路径，由于在 Web 项目中 ClassPath 位于 WEB-INF/classes/ 下，故向上获取两级目录即可
     *
     * @return web root 路径
     * @since 1.3.0
     */
    public static File getWebRoot() {
        final String classPath = ClassUtils.getClassPath();

        return StringUtils.isBlank(classPath) ? null : getParent(newFile(classPath), 2);
    }

    /**
     * 获取当前系统的换行分隔符
     * <pre>
     *     Windows: \r\n
     *     Mac: \r
     *     Linux: \n
     * </pre>
     *
     * @return 当前系统的换行分隔符
     * @since 1.4.0
     */
    public static String getLineSeparator() {
        return System.lineSeparator();
    }
}
