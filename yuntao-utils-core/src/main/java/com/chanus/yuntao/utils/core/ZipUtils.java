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
import java.util.Enumeration;
import java.util.zip.*;

/**
 * ZIP 操作工具类
 *
 * @author Chanus
 * @date 2020-06-23 18:01:55
 * @since 1.0.0
 */
public class ZipUtils {
    /**
     * 递归压缩文件夹
     *
     * @param srcRootDir 压缩文件夹根目录的子路径
     * @param file       当前递归压缩的文件或目录对象
     * @param zos        压缩文件存储对象
     * @throws IOException {@link IOException}
     */
    private static void compress(String srcRootDir, File file, ZipOutputStream zos) throws IOException {
        if (file == null)
            return;

        // 如果是文件，则直接压缩该文件
        if (file.isFile()) {
            int count, bufferLen = 1024;
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
            if (!CollectionUtils.isEmpty(childFileList)) {
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
        if (StringUtils.isBlank(srcPath) || StringUtils.isBlank(zipPath) || StringUtils.isBlank(zipFileName))
            return;

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
            if (!zipDir.exists() || !zipDir.isDirectory()) zipDir.mkdirs();

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
        if (StringUtils.isBlank(zipFilePath) || StringUtils.isBlank(decompressFilePath))
            return;

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
        if (!decompressFileDir.exists() || !decompressFileDir.isDirectory()) decompressFileDir.mkdirs();

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
            zip = new ZipFile(zipFile);
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
                if (!entryDir.exists() || !entryDir.isDirectory()) entryDir.mkdirs();

                // 创建解压文件
                entryFile = new File(entryFilePath);
                if (entryFile.exists()) {
                    // 检测文件是否允许删除，如果不允许删除，将会抛出 SecurityException
                    SecurityManager securityManager = new SecurityManager();
                    securityManager.checkDelete(entryFilePath);
                    // 删除已存在的目标文件
                    boolean b = entryFile.delete();
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
                if (bos != null)
                    bos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            IOUtils.close(bos, bis, zip);
        }
    }
}
