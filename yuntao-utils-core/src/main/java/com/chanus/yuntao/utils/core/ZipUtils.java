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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.*;

/**
 * ZIP 操作工具类
 *
 * @author Chanus
 * @date 2020-06-23 18:01:55
 * @since 1.0.0
 */
public class ZipUtils {
    private ZipUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 递归压缩文件夹
     *
     * @param srcRootDir 压缩文件夹根目录的子路径
     * @param file       当前递归压缩的文件或目录对象
     * @param zos        压缩文件存储对象
     * @throws IOException {@link IOException} IO 异常
     */
    private static void compress(String srcRootDir, File file, ZipOutputStream zos) throws IOException {
        if (file == null) {
            return;
        }

        // 如果是文件，则直接压缩该文件
        if (file.isFile()) {
            int count;
            int bufferLen = 1024;
            byte[] data = new byte[bufferLen];

            // 获取文件相对于压缩文件夹根目录的子路径
            String subPath = file.getAbsolutePath();
            int index = subPath.indexOf(srcRootDir);
            if (index != -1) {
                subPath = subPath.substring(srcRootDir.length() + File.separator.length());
            }
            ZipEntry entry = new ZipEntry(subPath);
            zos.putNextEntry(entry);
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            while ((count = bis.read(data, 0, bufferLen)) != -1) {
                zos.write(data, 0, count);
            }
            bis.close();
            zos.closeEntry();
        } else {// 如果是目录，则压缩整个目录
            // 压缩目录中的文件或子目录
            File[] childFileList = file.listFiles();
            if (ArrayUtils.isNotEmpty(childFileList)) {
                for (File value : childFileList) {
                    value.getAbsolutePath().indexOf(file.getAbsolutePath());
                    compress(srcRootDir, value, zos);
                }
            }
        }
    }

    /**
     * 压缩文件或文件目录
     *
     * @param srcPath     要压缩的源文件路径。如果压缩一个文件，则为该文件的全路径；如果压缩一个目录，则为该目录的顶层目录路径
     * @param zipPath     压缩文件保存的路径。注意：{@code zipPath} 不能是 {@code srcPath} 路径下的子文件夹
     * @param zipFileName 压缩文件名
     */
    public static void compress(String srcPath, String zipPath, String zipFileName) {
        if (StringUtils.isBlank(srcPath) || StringUtils.isBlank(zipPath) || StringUtils.isBlank(zipFileName)) {
            return;
        }

        CheckedOutputStream cos = null;
        ZipOutputStream zos = null;
        try {
            File srcFile = new File(srcPath);

            // 判断压缩文件保存的路径是否为源文件路径的子文件夹，如果是，则抛出异常（防止无限递归压缩的发生）
            if (srcFile.isDirectory() && zipPath.contains(srcPath)) {
                throw new RuntimeException("zipPath must not be the child directory of srcPath.");
            }

            // 判断压缩文件保存的路径是否存在，如果不存在，则创建目录
            File zipDir = new File(zipPath);
            if (!zipDir.exists() || !zipDir.isDirectory()) {
                zipDir.mkdirs();
            }

            // 创建压缩文件保存的文件对象
            String zipFilePath = zipPath + File.separator + zipFileName;
            File zipFile = new File(zipFilePath);
            if (zipFile.exists()) {
                // 检测文件是否允许删除，如果不允许删除，将会抛出 SecurityException
                SecurityManager securityManager = new SecurityManager();
                securityManager.checkDelete(zipFilePath);
                // 删除已存在的目标文件
                zipFile.delete();
            }

            cos = new CheckedOutputStream(new FileOutputStream(zipFile), new CRC32());
            zos = new ZipOutputStream(cos);

            // 如果只是压缩一个文件，则需要截取该文件的父目录
            String srcRootDir = srcPath;
            if (srcFile.isFile()) {
                int index = srcPath.lastIndexOf(File.separator);
                if (index != -1) {
                    srcRootDir = srcPath.substring(0, index);
                }
            }
            // 调用递归压缩方法进行目录或文件压缩
            compress(srcRootDir, srcFile, zos);
            zos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(zos, cos);
        }
    }

    /**
     * 解压缩 zip 包
     *
     * @param zipFilePath        zip 文件的全路径
     * @param decompressFilePath 解压后文件保存的路径
     * @param includeZipFileName 解压后文件保存的路径是否包含压缩文件的文件名：{@code true} 包含；{@code false} 不包含
     */
    @SuppressWarnings("unchecked")
    public static void decompress(String zipFilePath, String decompressFilePath, boolean includeZipFileName) {
        if (StringUtils.isBlank(zipFilePath) || StringUtils.isBlank(decompressFilePath)) {
            return;
        }

        File zipFile = new File(zipFilePath);
        // 如果解压后的文件保存路径包含压缩文件的文件名，则追加该文件名到解压路径
        if (includeZipFileName) {
            String fileName = zipFile.getName();
            if (StringUtils.isNotBlank(fileName)) {
                fileName = fileName.substring(0, fileName.lastIndexOf("."));
            }
            decompressFilePath = decompressFilePath + File.separator + fileName;
        }

        // 创建解压缩文件保存的路径
        File decompressFileDir = new File(decompressFilePath);
        if (!decompressFileDir.exists() || !decompressFileDir.isDirectory()) {
            decompressFileDir.mkdirs();
        }

        // 开始解压
        ZipEntry entry;
        String entryFilePath, entryDirPath;
        File entryFile, entryDir;
        int index, count, bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        ZipFile zip = null;
        try {
            zip = getZipFile(zipFile);
            Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zip.entries();

            // 循环对压缩包里的每一个文件进行解压
            while (entries.hasMoreElements()) {
                entry = entries.nextElement();
                // 构建压缩包中一个文件解压后保存的文件全路径
                entryFilePath = decompressFilePath + File.separator + entry.getName();
                // 构建解压后保存的文件夹路径
                index = entryFilePath.lastIndexOf(File.separator);
                if (index != -1) {
                    entryDirPath = entryFilePath.substring(0, index);
                } else {
                    entryDirPath = StringUtils.EMPTY;
                }
                entryDir = new File(entryDirPath);
                // 如果文件夹路径不存在，则创建文件夹
                if (!entryDir.exists() || !entryDir.isDirectory()) {
                    entryDir.mkdirs();
                }

                // 创建解压文件
                entryFile = new File(entryFilePath);
                if (entryFile.exists()) {
                    // 检测文件是否允许删除，如果不允许删除，将会抛出 SecurityException
                    SecurityManager securityManager = new SecurityManager();
                    securityManager.checkDelete(entryFilePath);
                    // 删除已存在的目标文件
                    entryFile.delete();
                }

                // 写入文件
                bos = new BufferedOutputStream(new FileOutputStream(entryFile));
                bis = new BufferedInputStream(zip.getInputStream(entry));
                while ((count = bis.read(buffer, 0, bufferSize)) != -1) {
                    bos.write(buffer, 0, count);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            IOUtils.close(bos, bis, zip);
        }
    }

    /**
     * 解压缩 zip 包
     *
     * @param zipFilePath        zip 文件的全路径
     * @param decompressFilePath 解压后文件保存的路径
     * @param includeZipFileName 解压后文件保存的路径是否包含压缩文件的文件名：{@code true} 包含；{@code false} 不包含
     * @param isCover            解压后文件是否覆盖已存在的文件：{@code true} 覆盖；{@code false} 不覆盖
     * @since 1.2.1
     */
    @SuppressWarnings("unchecked")
    public static void decompress(String zipFilePath, String decompressFilePath, boolean includeZipFileName, boolean isCover) {
        if (StringUtils.isBlank(zipFilePath) || StringUtils.isBlank(decompressFilePath)) {
            return;
        }

        File zipFile = new File(zipFilePath);
        // 如果解压后的文件保存路径包含压缩文件的文件名，则追加该文件名到解压路径
        if (includeZipFileName) {
            String fileName = zipFile.getName();
            if (StringUtils.isNotBlank(fileName)) {
                fileName = fileName.substring(0, fileName.lastIndexOf("."));
            }
            decompressFilePath = decompressFilePath + File.separator + fileName;
        }

        // 创建解压缩文件保存的路径
        File decompressFileDir = new File(decompressFilePath);
        if (!decompressFileDir.exists() || !decompressFileDir.isDirectory()) {
            decompressFileDir.mkdirs();
        }

        // 开始解压
        ZipEntry entry;
        String entryFilePath, entryDirPath;
        File entryFile, entryDir;
        int index, count, bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        ZipFile zip = null;
        try {
            zip = getZipFile(zipFile);
            Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zip.entries();

            // 循环对压缩包里的每一个文件进行解压
            while (entries.hasMoreElements()) {
                entry = entries.nextElement();
                // 构建压缩包中一个文件解压后保存的文件全路径
                entryFilePath = decompressFilePath + File.separator + entry.getName();
                // 构建解压后保存的文件夹路径
                index = entryFilePath.lastIndexOf(File.separator);
                if (index != -1) {
                    entryDirPath = entryFilePath.substring(0, index);
                } else {
                    entryDirPath = StringUtils.EMPTY;
                }
                entryDir = new File(entryDirPath);
                // 如果文件夹路径不存在，则创建文件夹
                if (!entryDir.exists() || !entryDir.isDirectory()) {
                    entryDir.mkdirs();
                }

                // 创建解压文件
                entryFile = new File(entryFilePath);
                if (entryFile.exists()) {
                    if (isCover) {
                        // 删除已存在的目标文件
                        entryFile.delete();
                        // 写入文件
                        bos = new BufferedOutputStream(new FileOutputStream(entryFile));
                        bis = new BufferedInputStream(zip.getInputStream(entry));
                        while ((count = bis.read(buffer, 0, bufferSize)) != -1) {
                            bos.write(buffer, 0, count);
                        }
                    }
                } else {
                    // 写入文件
                    bos = new BufferedOutputStream(new FileOutputStream(entryFile));
                    bis = new BufferedInputStream(zip.getInputStream(entry));
                    while ((count = bis.read(buffer, 0, bufferSize)) != -1) {
                        bos.write(buffer, 0, count);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            IOUtils.close(bos, bis, zip);
        }
    }

    /**
     * 压缩到当前目录，使用 UTF-8 编码
     *
     * @param srcPath 源文件路径
     * @return 压缩文件
     * @since 1.3.0
     */
    public static File zip(String srcPath) {
        return zip(srcPath, Charset.defaultCharset());
    }

    /**
     * 压缩到当前目录
     *
     * @param srcPath 源文件路径
     * @param charset 编码
     * @return 压缩文件
     * @since 1.3.0
     */
    public static File zip(String srcPath, Charset charset) {
        return zip(FileUtils.newFile(srcPath), charset);
    }

    /**
     * 压缩到当前目录，使用 UTF-8 编码
     *
     * @param srcFile 源文件或目录
     * @return 压缩文件
     * @since 1.3.0
     */
    public static File zip(File srcFile) {
        return zip(srcFile, Charset.defaultCharset());
    }

    /**
     * 压缩到当前目录
     *
     * @param srcFile 源文件或目录
     * @param charset 编码
     * @return 压缩文件
     * @since 1.3.0
     */
    public static File zip(File srcFile, Charset charset) {
        String zipFileName = srcFile.isDirectory() ? srcFile.getName() : FileUtils.getFileNameWithoutExtension(srcFile);
        final File zipFile = FileUtils.newFile(srcFile.getParentFile(), zipFileName + ".zip");
        zip(zipFile, charset, false, srcFile);
        return zipFile;
    }

    /**
     * 压缩文件或目录，不包含被压缩目录
     *
     * @param srcPath 待压缩的源文件路径。如果压缩一个文件，则为该文件的全路径；如果压缩一个目录，则为该目录的顶层目录路径
     * @param zipPath 压缩文件保存的路径，包括文件名。注意：zipPath 不能是 srcPath 路径下的子文件夹
     * @return 压缩文件
     * @since 1.3.0
     */
    public static File zip(String srcPath, String zipPath) {
        return zip(srcPath, zipPath, false);
    }

    /**
     * 压缩文件或目录
     *
     * @param srcPath    待压缩的源文件路径。如果压缩一个文件，则为该文件的全路径；如果压缩一个目录，则为该目录的顶层目录路径
     * @param zipPath    压缩文件保存的路径，包括文件名。注意：zipPath 不能是 srcPath 路径下的子文件夹
     * @param withSrcDir 是否包含被压缩目录，只针对压缩目录有效。若为 {@code false}，则只压缩目录下的文件或目录，若为 {@code true}，则将本目录也压缩
     * @return 压缩文件
     * @since 1.3.0
     */
    public static File zip(String srcPath, String zipPath, boolean withSrcDir) {
        return zip(srcPath, zipPath, Charset.defaultCharset(), withSrcDir);
    }

    /**
     * 压缩文件或目录
     *
     * @param srcPath    待压缩的源文件路径。如果压缩一个文件，则为该文件的全路径；如果压缩一个目录，则为该目录的顶层目录路径
     * @param zipPath    压缩文件保存的路径，包括文件名。注意：zipPath 不能是 srcPath 路径下的子文件夹
     * @param charset    编码
     * @param withSrcDir 是否包含被压缩目录，只针对压缩目录有效。若为 {@code false}，则只压缩目录下的文件或目录，若为 {@code true}，则将本目录也压缩
     * @return 压缩文件
     * @since 1.3.0
     */
    public static File zip(String srcPath, String zipPath, Charset charset, boolean withSrcDir) {
        final File srcFile = FileUtils.newFile(srcPath);
        final File zipFile = FileUtils.newFile(zipPath);
        zip(zipFile, charset, withSrcDir, srcFile);
        return zipFile;
    }

    /**
     * 压缩文件或目录，使用 UTF-8 编码
     *
     * @param zipFile    生成的 Zip 文件，包括文件名
     * @param withSrcDir 是否包含被压缩目录，只针对压缩目录有效。若为 {@code false}，则只压缩目录下的文件或目录，若为 {@code true}，则将本目录也压缩
     * @param srcFiles   待压缩的源文件或目录
     * @return 压缩文件
     * @since 1.3.0
     */
    public static File zip(File zipFile, boolean withSrcDir, File... srcFiles) {
        return zip(zipFile, Charset.defaultCharset(), withSrcDir, srcFiles);
    }

    /**
     * 压缩文件或目录
     *
     * @param zipFile    生成的 Zip 文件，包括文件名
     * @param charset    编码
     * @param withSrcDir 是否包含被压缩目录，只针对压缩目录有效。若为 {@code false}，则只压缩目录下的文件或目录，若为 {@code true}，则将本目录也压缩
     * @param srcFiles   待压缩的源文件或目录
     * @return 压缩文件
     * @since 1.3.0
     */
    public static File zip(File zipFile, Charset charset, boolean withSrcDir, File... srcFiles) {
        return zip(zipFile, charset, withSrcDir, null, srcFiles);
    }

    /**
     * 压缩文件或目录
     *
     * @param zipFile    生成的 Zip 文件，包括文件名
     * @param charset    编码
     * @param withSrcDir 是否包含被压缩目录，只针对压缩目录有效。若为 {@code false}，则只压缩目录下的文件或目录，若为 {@code true}，则将本目录也压缩
     * @param filter     文件过滤器，通过实现此接口，自定义要过滤的文件（过滤掉哪些文件或文件夹不加入压缩）
     * @param srcFiles   待压缩的源文件或目录
     * @return 压缩文件
     * @since 1.3.0
     */
    public static File zip(File zipFile, Charset charset, boolean withSrcDir, FileFilter filter, File... srcFiles) {
        validateFiles(zipFile, srcFiles);

        try (ZipOutputStream out = getZipOutputStream(zipFile, charset)) {
            zip(out, charset, withSrcDir, filter, srcFiles);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return zipFile;
    }

    /**
     * 压缩文件或目录
     *
     * @param out        生成的 Zip 到的目标流，包括文件名
     * @param charset    编码
     * @param withSrcDir 是否包含被压缩目录，只针对压缩目录有效。若为 {@code false}，则只压缩目录下的文件或目录，若为 {@code true}，则将本目录也压缩
     * @param filter     文件过滤器，通过实现此接口，自定义要过滤的文件（过滤掉哪些文件或文件夹不加入压缩）
     * @param srcFiles   待压缩的源文件或目录
     * @since 1.3.0
     */
    public static void zip(OutputStream out, Charset charset, boolean withSrcDir, FileFilter filter, File... srcFiles) {
        zip(getZipOutputStream(out, charset), withSrcDir, filter, srcFiles);
    }

    /**
     * 压缩文件或目录
     *
     * @param zipOutputStream 生成的 Zip 到的目标流，不关闭此流
     * @param withSrcDir      是否包含被压缩目录，只针对压缩目录有效。若为 {@code false}，则只压缩目录下的文件或目录，若为 {@code true}，则将本目录也压缩
     * @param filter          文件过滤器，通过实现此接口，自定义要过滤的文件（过滤掉哪些文件或文件夹不加入压缩）
     * @param srcFiles        待压缩的源文件或目录
     * @since 1.3.0
     */
    public static void zip(ZipOutputStream zipOutputStream, boolean withSrcDir, FileFilter filter, File... srcFiles) {
        String srcRootDir;
        try {
            for (File srcFile : srcFiles) {
                if (null == srcFile) {
                    continue;
                }
                // 如果只是压缩一个文件，则需要截取该文件的父目录
                srcRootDir = srcFile.getCanonicalPath();
                if (srcFile.isFile() || withSrcDir) {
                    // 若是文件，则将父目录完整路径都截取掉；若设置包含目录，则将上级目录全部截取掉，保留本目录名
                    srcRootDir = srcFile.getCanonicalFile().getParentFile().getCanonicalPath();
                }
                // 调用递归压缩方法进行目录或文件压缩
                zip(srcFile, srcRootDir, zipOutputStream, filter);
                zipOutputStream.flush();
            }
            zipOutputStream.finish();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 压缩字符串数据，使用系统默认编码
     *
     * @param zipFile 生成的 Zip 文件，包括文件名
     * @param path    字符串数据在压缩文件中的路径或文件名
     * @param data    待压缩的字符串数据
     * @return 压缩文件
     * @since 1.3.0
     */
    public static File zip(File zipFile, String path, String data) {
        return zip(zipFile, path, data, Charset.defaultCharset());
    }

    /**
     * 压缩字符串数据
     *
     * @param zipFile 生成的 Zip 文件，包括文件名
     * @param path    字符串数据在压缩文件中的路径或文件名
     * @param data    待压缩的字符串数据
     * @param charset 编码
     * @return 压缩文件
     * @since 1.3.0
     */
    public static File zip(File zipFile, String path, String data, Charset charset) {
        return zip(zipFile, path, StreamUtils.toStream(data, charset), charset);
    }

    /**
     * 压缩数据流中的数据，使用系统默认编码
     *
     * @param zipFile 生成的 Zip 文件，包括文件名
     * @param path    流数据在压缩文件中的路径或文件名
     * @param in      待压缩的源，添加完成后自动关闭流
     * @return 压缩文件
     * @since 1.3.0
     */
    public static File zip(File zipFile, String path, InputStream in) {
        return zip(zipFile, path, in, Charset.defaultCharset());
    }

    /**
     * 压缩数据流中的数据
     *
     * @param zipFile 生成的 Zip 文件，包括文件名
     * @param path    流数据在压缩文件中的路径或文件名
     * @param in      待压缩的源，添加完成后自动关闭流
     * @param charset 编码
     * @return 压缩文件
     * @since 1.3.0
     */
    public static File zip(File zipFile, String path, InputStream in, Charset charset) {
        return zip(zipFile, new String[]{path}, new InputStream[]{in}, charset);
    }

    /**
     * 压缩数据流中的数据，路径列表和流列表长度必须一致，使用系统默认编码
     *
     * @param zipFile 生成的 Zip 文件，包括文件名
     * @param paths   流数据在压缩文件中的路径或文件名
     * @param ins     待压缩的源，添加完成后自动关闭流
     * @return 压缩文件
     * @since 1.3.0
     */
    public static File zip(File zipFile, String[] paths, InputStream[] ins) {
        return zip(zipFile, paths, ins, Charset.defaultCharset());
    }

    /**
     * 压缩数据流中的数据，路径列表和流列表长度必须一致
     *
     * @param zipFile 生成的 Zip 文件，包括文件名
     * @param paths   流数据在压缩文件中的路径或文件名
     * @param ins     待压缩的源，添加完成后自动关闭流
     * @param charset 编码
     * @return 压缩文件
     * @since 1.3.0
     */
    public static File zip(File zipFile, String[] paths, InputStream[] ins, Charset charset) {
        if (ArrayUtils.isEmpty(paths) || ArrayUtils.isEmpty(ins)) {
            throw new IllegalArgumentException("Paths or ins is empty !");
        }
        if (paths.length != ins.length) {
            throw new IllegalArgumentException("Paths length is not equals to ins length !");
        }

        ZipOutputStream out = null;
        try {
            out = getZipOutputStream(zipFile, charset);
            for (int i = 0; i < paths.length; i++) {
                addFile(ins[i], paths[i], out);
            }
        } finally {
            IOUtils.close(out);
        }
        return zipFile;
    }

    /**
     * 解压 Zip 到文件名相同的目录中，使用系统默认编码
     *
     * @param zipFilePath Zip 文件的路径
     * @return 解压的 Zip 文件目录
     * @since 1.3.0
     */
    public static File unzip(String zipFilePath) {
        return unzip(zipFilePath, Charset.defaultCharset());
    }

    /**
     * 解压 Zip 到文件名相同的目录中
     *
     * @param zipFilePath Zip 文件的路径
     * @param charset     编码
     * @return 解压的 Zip 文件目录
     * @since 1.3.0
     */
    public static File unzip(String zipFilePath, Charset charset) {
        return unzip(FileUtils.newFile(zipFilePath), charset);
    }

    /**
     * 解压 Zip 到文件名相同的目录中，使用系统默认编码
     *
     * @param zipFile Zip 文件
     * @return 解压的 Zip 文件目录
     * @since 1.3.0
     */
    public static File unzip(File zipFile) {
        return unzip(zipFile, Charset.defaultCharset());
    }

    /**
     * 解压 Zip 到文件名相同的目录中
     *
     * @param zipFile Zip 文件
     * @param charset 编码
     * @return 解压的 Zip 文件目录
     * @since 1.3.0
     */
    public static File unzip(File zipFile, Charset charset) {
        final File destDir = FileUtils.newFile(zipFile.getParentFile(), zipFile.isDirectory() ? zipFile.getName() : FileUtils.getFileNameWithoutExtension(zipFile));
        return unzip(zipFile, destDir, charset);
    }

    /**
     * 解压 Zip，使用系统默认编码
     *
     * @param zipFilePath Zip 文件的路径
     * @param outFileDir  解压到的目录
     * @return 解压的 Zip 文件目录
     * @since 1.3.0
     */
    public static File unzip(String zipFilePath, String outFileDir) {
        return unzip(zipFilePath, outFileDir, Charset.defaultCharset());
    }

    /**
     * 解压 Zip
     *
     * @param zipFilePath Zip 文件的路径
     * @param outFileDir  解压到的目录
     * @param charset     编码
     * @return 解压的 Zip 文件目录
     * @since 1.3.0
     */
    public static File unzip(String zipFilePath, String outFileDir, Charset charset) {
        return unzip(FileUtils.newFile(zipFilePath), FileUtils.mkdirs(outFileDir), charset);
    }

    /**
     * 解压 Zip，使用系统默认编码
     *
     * @param zipFile Zip 文件
     * @param outFile 解压到的目录
     * @return 解压的 Zip 文件目录
     * @since 1.3.0
     */
    public static File unzip(File zipFile, File outFile) {
        return unzip(zipFile, outFile, Charset.defaultCharset());
    }

    /**
     * 解压 Zip
     *
     * @param zipFile Zip 文件
     * @param outFile 解压到的目录
     * @param charset 编码
     * @return 解压的 Zip 文件目录
     * @since 1.3.0
     */
    public static File unzip(File zipFile, File outFile, Charset charset) {
        ZipFile zip;
        try {
            zip = new ZipFile(zipFile, charset);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return unzip(zip, outFile);
    }

    /**
     * 解压 Zip
     *
     * @param zipFile Zip 文件，附带编码信息，使用完毕自动关闭
     * @param outFile 解压到的目录
     * @return 解压的 Zip 文件目录
     * @since 1.3.0
     */
    @SuppressWarnings("unchecked")
    public static File unzip(ZipFile zipFile, File outFile) {
        if (outFile.exists() && outFile.isFile()) {
            throw new RuntimeException(StringUtils.format("Target path [{}] exist!", outFile.getAbsolutePath()));
        }

        try {
            final Enumeration<ZipEntry> em = (Enumeration<ZipEntry>) zipFile.entries();
            ZipEntry zipEntry;
            File outItemFile;
            while (em.hasMoreElements()) {
                zipEntry = em.nextElement();
                outItemFile = buildFile(outFile, zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    // 创建对应目录
                    outItemFile.mkdirs();
                } else {
                    // 写出文件
                    write(zipFile, zipEntry, outItemFile);
                }
            }
        } finally {
            IOUtils.close(zipFile);
        }
        return outFile;
    }

    /**
     * 解压 Zip，Zip 条目不使用高速缓冲
     *
     * @param in      Zip 文件流，使用完毕自动关闭
     * @param outFile 解压到的目录
     * @param charset 编码，若为空则使用系统默认编码
     * @return 解压的 Zip 文件目录
     * @since 1.3.0
     */
    public static File unzip(InputStream in, File outFile, Charset charset) {
        if (charset == null) {
            charset = Charset.defaultCharset();
        }

        return unzip(new ZipInputStream(in, charset), outFile);
    }

    /**
     * 解压 Zip，Zip 条目不使用高速缓冲
     *
     * @param zipStream Zip 文件流，包含编码信息
     * @param outFile   解压到的目录
     * @return 解压的 Zip 文件目录
     * @since 1.3.0
     */
    public static File unzip(ZipInputStream zipStream, File outFile) {
        try {
            ZipEntry zipEntry;
            File outItemFile;
            while (null != (zipEntry = zipStream.getNextEntry())) {
                outItemFile = FileUtils.newFile(outFile, zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    // 目录
                    outItemFile.mkdirs();
                } else {
                    // 文件
                    FileUtils.writeFromStream(outItemFile, zipStream, false);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.close(zipStream);
        }
        return outFile;
    }

    /**
     * 从 Zip 文件中提取指定的文件为字节数组，使用系统默认编码
     *
     * @param zipFilePath Zip 文件路径
     * @param name        文件名，如果存在于子文件夹中，此文件名必须包含目录名
     * @return 文件内容字节数组
     * @since 1.3.0
     */
    public static byte[] unzipFileBytes(String zipFilePath, String name) {
        return unzipFileBytes(zipFilePath, Charset.defaultCharset(), name);
    }

    /**
     * 从 Zip 文件中提取指定的文件为字节数组
     *
     * @param zipFilePath Zip 文件路径
     * @param charset     编码
     * @param name        文件名，如果存在于子文件夹中，此文件名必须包含目录名
     * @return 文件内容字节数组
     * @since 1.3.0
     */
    public static byte[] unzipFileBytes(String zipFilePath, Charset charset, String name) {
        return unzipFileBytes(FileUtils.newFile(zipFilePath), charset, name);
    }

    /**
     * 从 Zip 文件中提取指定的文件为字节数组，使用系统默认编码
     *
     * @param zipFile Zip 文件
     * @param name    文件名，如果存在于子文件夹中，此文件名必须包含目录名
     * @return 文件内容字节数组
     * @since 1.3.0
     */
    public static byte[] unzipFileBytes(File zipFile, String name) {
        return unzipFileBytes(zipFile, Charset.defaultCharset(), name);
    }

    /**
     * 从 Zip 文件中提取指定的文件为字节数组
     *
     * @param zipFile Zip 文件
     * @param charset 编码
     * @param name    文件名，如果存在于子文件夹中，此文件名必须包含目录名
     * @return 文件内容字节数组
     * @since 1.3.0
     */
    @SuppressWarnings("unchecked")
    public static byte[] unzipFileBytes(File zipFile, Charset charset, String name) {
        ZipFile zipFileObj = null;
        try {
            zipFileObj = new ZipFile(zipFile, charset);
            final Enumeration<ZipEntry> em = (Enumeration<ZipEntry>) zipFileObj.entries();
            ZipEntry zipEntry;
            while (em.hasMoreElements()) {
                zipEntry = em.nextElement();
                if ((!zipEntry.isDirectory()) && name.equals(zipEntry.getName())) {
                    return StreamUtils.read2Byte(zipFileObj.getInputStream(zipEntry));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.close(zipFileObj);
        }
        return null;
    }

    /**
     * Gzip 压缩字符串
     *
     * @param content 字符串
     * @param charset 编码
     * @return 压缩后的字节数组
     * @since 1.3.0
     */
    public static byte[] gzip(String content, String charset) {
        return gzip(StringUtils.bytes(content, charset));
    }

    /**
     * Gzip 压缩字节数据
     *
     * @param buf 字节数据
     * @return 压缩后的字节数组
     * @since 1.3.0
     */
    public static byte[] gzip(byte[] buf) {
        return gzip(new ByteArrayInputStream(buf), buf.length);
    }

    /**
     * Gzip 压缩文件
     *
     * @param file 文件
     * @return 压缩后的字节数组
     * @since 1.3.0
     */
    public static byte[] gzip(File file) {
        BufferedInputStream in = null;
        try {
            in = StreamUtils.toBuffered(StreamUtils.toStream(file));
            return gzip(in, (int) file.length());
        } finally {
            IOUtils.close(in);
        }
    }

    /**
     * Gzip 压缩数据流
     *
     * @param in 数据流
     * @return 压缩后的字节数组
     * @since 1.3.0
     */
    public static byte[] gzip(InputStream in) {
        return gzip(in, 32);
    }

    /**
     * Gzip 压缩数据流
     *
     * @param in     数据流
     * @param length 预估长度
     * @return 压缩后的字节数组
     * @since 1.3.0
     */
    public static byte[] gzip(InputStream in, int length) {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream(length);
        GZIPOutputStream gos = null;
        try {
            gos = new GZIPOutputStream(bos);
            StreamUtils.copy(in, gos);
            // 返回必须在关闭gos后进行，因为关闭时会自动执行finish()方法，保证数据全部写出
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.close(gos);
        }
    }

    /**
     * Gzip 解压缩
     *
     * @param buf     压缩过的字节数组
     * @param charset 编码
     * @return 解压后的字符串
     * @since 1.3.0
     */
    public static String unGzip(byte[] buf, String charset) {
        return StringUtils.toString(unGzip(buf), charset);
    }

    /**
     * Gzip 解压缩
     *
     * @param buf 压缩过的字节数组
     * @return 解压后的字节数组
     * @since 1.3.0
     */
    public static byte[] unGzip(byte[] buf) {
        return unGzip(new ByteArrayInputStream(buf), buf.length);
    }

    /**
     * Gzip 解压缩
     *
     * @param in 数据流
     * @return 解压后的字节数组
     * @since 1.3.0
     */
    public static byte[] unGzip(InputStream in) {
        return unGzip(in, 32);
    }

    /**
     * Gzip 解压缩
     *
     * @param in     数据流
     * @param length 估算长度
     * @return 解压后的字节数组
     * @since 1.3.0
     */
    public static byte[] unGzip(InputStream in, int length) {
        GZIPInputStream gzi = null;
        ByteArrayOutputStream bos = null;
        try {
            gzi = (in instanceof GZIPInputStream) ? (GZIPInputStream) in : new GZIPInputStream(in);
            bos = new ByteArrayOutputStream(length);
            StreamUtils.copy(gzi, bos);
            // 返回必须在关闭gos后进行，因为关闭时会自动执行finish()方法，保证数据全部写出
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.close(gzi, bos);
        }
    }

    /**
     * Zlib 压缩字符串
     *
     * @param content 被压缩的字符串
     * @param charset 编码
     * @param level   压缩级别，1~9
     * @return 压缩后的字节数组
     * @since 1.3.0
     */
    public static byte[] zlib(String content, String charset, int level) {
        return zlib(StringUtils.bytes(content, charset), level);
    }

    /**
     * Zlib 压缩文件
     *
     * @param file  被压缩的文件
     * @param level 压缩级别，1~9
     * @return 压缩后的字节数组
     * @since 1.3.0
     */
    public static byte[] zlib(File file, int level) {
        BufferedInputStream in = null;
        try {
            in = StreamUtils.toBuffered(StreamUtils.toStream(file));
            return zlib(in, level, (int) file.length());
        } finally {
            IOUtils.close(in);
        }
    }

    /**
     * Zlib 压缩字节数据
     *
     * @param buf   字节数据
     * @param level 压缩级别，0~9
     * @return 压缩后的字节数组
     * @since 1.3.0
     */
    public static byte[] zlib(byte[] buf, int level) {
        return zlib(new ByteArrayInputStream(buf), level, buf.length);
    }

    /**
     * Zlib 压缩数据流
     *
     * @param in    数据流
     * @param level 压缩级别，0~9
     * @return 压缩后的字节数组
     * @since 1.3.0
     */
    public static byte[] zlib(InputStream in, int level) {
        return zlib(in, level, 32);
    }

    /**
     * Zlib 压缩数据流
     *
     * @param in     数据流
     * @param level  压缩级别，0~9
     * @param length 预估大小
     * @return 压缩后的字节数组
     * @since 1.3.0
     */
    public static byte[] zlib(InputStream in, int level, int length) {
        final ByteArrayOutputStream out = new ByteArrayOutputStream(length);
        deflater(in, out, level, false);
        return out.toByteArray();
    }

    /**
     * Zlib 解压缩
     *
     * @param buf     压缩过的字节数组
     * @param charset 编码
     * @return 解压后的字符串
     * @since 1.3.0
     */
    public static String unZlib(byte[] buf, String charset) {
        return StringUtils.toString(unZlib(buf), charset);
    }

    /**
     * Zlib 解压缩
     *
     * @param buf 压缩过的字节数组
     * @return 解压后的字节数组
     * @since 1.3.0
     */
    public static byte[] unZlib(byte[] buf) {
        return unZlib(new ByteArrayInputStream(buf), buf.length);
    }

    /**
     * Zlib 解压缩
     *
     * @param in 数据流
     * @return 解压后的字节数组
     * @since 1.3.0
     */
    public static byte[] unZlib(InputStream in) {
        return unZlib(in, 32);
    }

    /**
     * Zlib 解压缩
     *
     * @param in     数据流
     * @param length 预估长度
     * @return 解压后的字节数组
     * @since 1.3.0
     */
    public static byte[] unZlib(InputStream in, int length) {
        final ByteArrayOutputStream out = new ByteArrayOutputStream(length);
        inflater(in, out, false);
        return out.toByteArray();
    }

    /**
     * 获取 Zip 文件中指定目录下的所有文件，只显示文件，不显示目录
     *
     * @param zipFile Zip 文件
     * @param dir     目录前缀（目录前缀不包含开头的/）
     * @return 文件列表
     * @since 1.3.0
     */
    public static List<String> listFileNames(ZipFile zipFile, String dir) {
        if (StringUtils.isNotBlank(dir) && !dir.endsWith(StringUtils.SLASH)) {
            // 目录尾部添加"/"
            dir += StringUtils.SLASH;
        }

        final List<String> fileNames = new ArrayList<>();
        String name;
        for (ZipEntry entry : Collections.list(zipFile.entries())) {
            name = entry.getName();
            if (StringUtils.isEmpty(dir) || name.startsWith(dir)) {
                final String nameSuffix = StringUtils.removePrefix(name, dir);
                if (StringUtils.isNotEmpty(nameSuffix) && !StringUtils.contains(nameSuffix, StringUtils.SLASH)) {
                    fileNames.add(nameSuffix);
                }
            }
        }

        return fileNames;
    }

    /**
     * 获取压缩文件
     *
     * @param zipFile 待压缩文件
     * @return {@link ZipFile}
     * @throws IOException {@link IOException}
     * @since 1.2.1
     */
    private static ZipFile getZipFile(File zipFile) throws IOException {
        ZipFile zip = new ZipFile(zipFile, StandardCharsets.UTF_8);
        Enumeration<?> entries = zip.entries();
        while (entries.hasMoreElements()) {
            try {
                entries.nextElement();
                zip.close();
                zip = new ZipFile(zipFile, StandardCharsets.UTF_8);
                return zip;
            } catch (Exception e) {
                zip = new ZipFile(zipFile, Charset.forName("GBK"));
                return zip;
            }
        }
        return zip;
    }

    /**
     * 获得 {@link ZipOutputStream}
     *
     * @param zipFile 压缩文件
     * @param charset 编码
     * @return {@link ZipOutputStream}
     * @since 1.3.0
     */
    private static ZipOutputStream getZipOutputStream(File zipFile, Charset charset) {
        try {
            return getZipOutputStream(StreamUtils.toBuffered(new FileOutputStream(zipFile)), charset);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获得 {@link ZipOutputStream}
     *
     * @param out     压缩文件流
     * @param charset 编码，若为空则使用系统默认编码
     * @return {@link ZipOutputStream}
     * @since 1.3.0
     */
    private static ZipOutputStream getZipOutputStream(OutputStream out, Charset charset) {
        if (out instanceof ZipOutputStream) {
            return (ZipOutputStream) out;
        }

        return new ZipOutputStream(out, ObjectUtils.defaultIfNull(charset, Charset.defaultCharset()));
    }

    /**
     * 递归压缩文件夹<br>
     * srcRootDir 决定了路径截取的位置，例如：<br>
     * file 的路径为 d:/a/b/c/d.txt，srcRootDir 为 d:/a/b，则压缩后的文件与目录为结构为 c/d.txt
     *
     * @param file       当前递归压缩的文件或目录对象
     * @param srcRootDir 被压缩的文件夹根目录
     * @param out        压缩文件存储对象
     * @param filter     文件过滤器，通过实现此接口，自定义要过滤的文件（过滤掉哪些文件或文件夹不加入压缩）
     * @since 1.3.0
     */
    private static void zip(File file, String srcRootDir, ZipOutputStream out, FileFilter filter) {
        if (file == null || (filter != null && !filter.accept(file))) {
            return;
        }

        // 获取文件相对于压缩文件夹根目录的子路径
        final String subPath = FileUtils.subPath(srcRootDir, file);
        if (file.isDirectory()) {
            // 如果是目录，则压缩压缩目录中的文件或子目录
            final File[] files = file.listFiles();
            if (ArrayUtils.isEmpty(files) && StringUtils.isNotEmpty(subPath)) {
                // 加入目录，只有空目录时才加入目录，非空时会在创建文件时自动添加父级目录
                addDir(subPath, out);
            }
            // 压缩目录下的子文件或目录
            for (File childFile : files) {
                zip(childFile, srcRootDir, out, filter);
            }
        } else {
            // 如果是文件或其它符号，则直接压缩该文件
            addFile(file, subPath, out);
        }
    }

    /**
     * 添加文件到压缩包
     *
     * @param file 需要压缩的文件
     * @param path 文件在压缩文件中的路径
     * @param out  压缩文件存储对象
     * @since 1.3.0
     */
    private static void addFile(File file, String path, ZipOutputStream out) {
        addFile(StreamUtils.toBuffered(StreamUtils.toStream(file)), path, out);
    }

    /**
     * 添加文件流到压缩包，添加后关闭流
     *
     * @param in   需要压缩的输入流
     * @param path 压缩的路径
     * @param out  压缩文件存储对象
     * @since 1.3.0
     */
    private static void addFile(InputStream in, String path, ZipOutputStream out) {
        if (in == null) {
            return;
        }

        try {
            out.putNextEntry(new ZipEntry(path));
            StreamUtils.copy(in, out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.close(in);
            closeEntry(out);
        }
    }

    /**
     * 在压缩包中新建目录
     *
     * @param path 压缩的路径
     * @param out  压缩文件存储对象
     * @since 1.3.0
     */
    private static void addDir(String path, ZipOutputStream out) {
        if (!path.endsWith(StringUtils.SLASH)) {
            path = path.concat(StringUtils.SLASH);
        }

        try {
            out.putNextEntry(new ZipEntry(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            closeEntry(out);
        }
    }

    /**
     * 判断压缩文件保存的路径是否为源文件路径的子文件夹，如果是，则抛出异常（防止无限递归压缩的发生）
     *
     * @param zipFile  压缩文件
     * @param srcFiles 被压缩的文件或目录
     * @since 1.3.0
     */
    private static void validateFiles(File zipFile, File... srcFiles) {
        if (zipFile.isDirectory()) {
            throw new RuntimeException(StringUtils.format("Zip file [{}] must not be a directory !", zipFile.getAbsoluteFile()));
        }

        for (File srcFile : srcFiles) {
            if (srcFile == null) {
                continue;
            }

            if (!srcFile.exists()) {
                throw new RuntimeException(StringUtils.format("File [{}] not exist!", srcFile.getAbsolutePath()));
            }

            try {
                final File parentFile = zipFile.getCanonicalFile().getParentFile();
                // 压缩文件不能位于被压缩的目录内
                if (srcFile.isDirectory() && parentFile.getCanonicalPath().contains(srcFile.getCanonicalPath())) {
                    throw new RuntimeException(StringUtils.format("Zip file path [{}] must not be the child directory of [{}] !", zipFile.getCanonicalPath(), srcFile.getCanonicalPath()));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 关闭当前 Entry
     *
     * @param out {@link ZipOutputStream}
     * @since 1.3.0
     */
    private static void closeEntry(ZipOutputStream out) {
        try {
            out.closeEntry();
        } catch (IOException ignored) {
        }
    }

    /**
     * 从 Zip 中读取文件流并写出到文件
     *
     * @param zipFile     Zip 文件
     * @param zipEntry    Zip 文件中的子文件
     * @param outItemFile 输出到的文件
     * @since 1.3.0
     */
    private static void write(ZipFile zipFile, ZipEntry zipEntry, File outItemFile) {
        InputStream in = null;
        try {
            in = zipFile.getInputStream(zipEntry);
            FileUtils.writeFromStream(outItemFile, in, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.close(in);
        }
    }

    /**
     * 将 Zlib 流解压到输出流中
     *
     * @param in     Zlib 数据流
     * @param out    输出流
     * @param nowrap {@code true} 表示兼容 Gzip 压缩
     * @since 1.3.0
     */
    @SuppressWarnings("SameParameterValue")
    private static void inflater(InputStream in, OutputStream out, boolean nowrap) {
        final InflaterOutputStream ios = (out instanceof InflaterOutputStream) ? (InflaterOutputStream) out : new InflaterOutputStream(out, new Inflater(nowrap));
        StreamUtils.copy(in, ios);
        try {
            ios.finish();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将普通数据流压缩成 Zlib 到输出流中
     *
     * @param in     Zlib 数据流
     * @param out    输出流
     * @param level  压缩级别，0~9
     * @param nowrap {@code true} 表示兼容 Gzip 压缩
     * @since 1.3.0
     */
    @SuppressWarnings("SameParameterValue")
    private static void deflater(InputStream in, OutputStream out, int level, boolean nowrap) {
        final DeflaterOutputStream ios = (out instanceof DeflaterOutputStream) ? (DeflaterOutputStream) out : new DeflaterOutputStream(out, new Deflater(level, nowrap));
        StreamUtils.copy(in, ios);
        try {
            ios.finish();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据压缩包中的路径构建目录结构，在 Windows 下直接构建，在 Linux 下拆分路径单独构建
     *
     * @param outFile  最外部路径
     * @param fileName 文件名，可以包含路径
     * @return 文件或目录
     * @since 1.3.0
     */
    private static File buildFile(File outFile, String fileName) {
        // 替换 Windows 路径分隔符为 Linux 路径分隔符，便于统一处理
        fileName = fileName.replace(CharUtils.BACKSLASH, CharUtils.SLASH);
        // 检查文件名中是否包含"/"，不考虑以"/"结尾的情况
        if (!FileUtils.isWindows() && fileName.lastIndexOf(CharUtils.SLASH, fileName.length() - 2) > 0) {
            // 在 Linux 下多层目录创建存在问题，/会被当成文件名的一部分，此处做处理
            // 使用/拆分路径（zip中无\），级联创建父目录
            final List<String> pathParts = StringUtils.split(fileName, CharUtils.SLASH, 0, false, true, false);
            //目录个数
            final int lastPartIndex = pathParts.size() - 1;
            for (int i = 0; i < lastPartIndex; i++) {
                // 由于路径拆分，slip 不检查，在最后一步检查
                outFile = FileUtils.checkSlip(outFile, new File(outFile, pathParts.get(i)));
            }
            outFile.mkdirs();
            // 最后一个部分如果非空，作为文件名
            fileName = pathParts.get(lastPartIndex);
        }
        return new File(outFile, fileName);
    }

}
