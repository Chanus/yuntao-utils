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
package com.chanus.yuntao.utils.ftp;

import com.chanus.yuntao.utils.core.*;
import org.apache.commons.net.ftp.*;

import java.io.*;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * FTP 客户端封装<br>
 * 基于 Apache Commons Net
 *
 * @author Chanus
 * @since 1.5.0
 */
public class Ftp implements Closeable {
    /**
     * 路径格式化字符串
     */
    private static final String PATH_FORMATTER = "{}/{}";
    /**
     * 默认端口
     */
    private static final int DEFAULT_PORT = 21;

    /**
     * FTP 配置项
     */
    private final FtpConfig ftpConfig;
    /**
     * FTP 客户端
     */
    private FTPClient client;
    /**
     * FTP 连接模式
     */
    private FtpMode mode;

    /**
     * 构造，匿名登录
     *
     * @param host 域名或 IP
     */
    public Ftp(String host) {
        this(host, DEFAULT_PORT);
    }

    /**
     * 构造，匿名登录
     *
     * @param host 域名或 IP
     * @param port 端口
     */
    public Ftp(String host, int port) {
        this(host, port, "anonymous", "");
    }

    /**
     * 构造
     *
     * @param host     域名或 IP
     * @param port     端口
     * @param user     用户名
     * @param password 密码
     */
    public Ftp(String host, int port, String user, String password) {
        this(host, port, user, password, CharsetUtils.CHARSET_UTF_8);
    }

    /**
     * 构造
     *
     * @param host     域名或 IP
     * @param port     端口
     * @param user     用户名
     * @param password 密码
     * @param charset  编码
     */
    public Ftp(String host, int port, String user, String password, Charset charset) {
        this(host, port, user, password, charset, null);
    }

    /**
     * 构造
     *
     * @param host     域名或 IP
     * @param port     端口
     * @param user     用户名
     * @param password 密码
     * @param charset  编码
     * @param mode     连接模式
     */
    public Ftp(String host, int port, String user, String password, Charset charset, FtpMode mode) {
        this(new FtpConfig(host, port, user, password, charset), mode);
    }

    /**
     * 构造
     *
     * @param config FTP 配置
     */
    public Ftp(FtpConfig config) {
        this.ftpConfig = config;
        this.init();
    }

    /**
     * 构造
     *
     * @param config FTP 配置
     * @param mode   连接模式
     */
    public Ftp(FtpConfig config, FtpMode mode) {
        this.ftpConfig = config;
        this.mode = mode;
        this.init();
    }

    /**
     * 初始化连接
     *
     * @return this
     */
    public Ftp init() {
        return this.init(this.ftpConfig, this.mode);
    }

    /**
     * 初始化连接
     *
     * @param config FTP 配置
     * @param mode   FTP 连接模式
     * @return this
     */
    public Ftp init(FtpConfig config, FtpMode mode) {
        final FTPClient ftpClient = new FTPClient();
        // 设置文件传输的编码
        ftpClient.setControlEncoding(config.getCharset().toString());
        // 设置连接超时时长
        ftpClient.setConnectTimeout(config.getConnectionTimeout());
        try {
            // 连接 FTP 服务器
            ftpClient.connect(config.getHost(), config.getPort());
            // 设置 defaultTimeout
            ftpClient.setDefaultTimeout(config.getDefaultTimeout());
            // 设置 Socket 连接超时时长
            ftpClient.setSoTimeout(config.getSoTimeout());
            // 设置 dataTimeout
            ftpClient.setDataTimeout(Duration.ofMillis(config.getDataTimeout()));
            // 登录 FTP 服务器
            ftpClient.login(config.getUser(), config.getPassword());
        } catch (IOException e) {
            throw new FtpException(e);
        }
        // 确认应答状态码是否正确完成响应，判断是否成功登录服务器，凡是2开头的 isPositiveCompletion 都会返回 true
        final int replyCode = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(replyCode)) {
            try {
                // 中断文件正在进行的文件传输，成功时返回 true，否则返回 false
                ftpClient.abort();
                // 断开与服务器的连接，并恢复默认参数值
                ftpClient.disconnect();
            } catch (IOException e) {
                // ignore
            }
            throw new FtpException("Login failed for user [{}], reply code is: [{}]", config.getUser(), replyCode);
        }
        this.client = ftpClient;
        if (mode != null) {
            setMode(mode);
        }
        return this;
    }

    /**
     * 设置 FTP 连接模式，可选主动模式和被动模式
     *
     * @param mode FTP 连接模式枚举
     * @return this
     */
    public Ftp setMode(FtpMode mode) {
        this.mode = mode;
        if (mode == FtpMode.ACTIVE) {
            this.client.enterLocalActiveMode();
        } else if (mode == FtpMode.PASSIVE) {
            this.client.enterLocalPassiveMode();
        }

        return this;
    }

    /**
     * 设置 FTP 文件传输模式
     *
     * @param fileType FTP 文件传输模式
     * @return this
     */
    public Ftp setFileType(int fileType) {
        try {
            this.client.setFileType(fileType);
        } catch (IOException e) {
            throw new FtpException(e);
        }
        return this;
    }

    /**
     * 如果连接超时的话，重新进行连接
     *
     * @return this
     */
    public Ftp reconnectIfTimeout() {
        String pwd = null;
        try {
            pwd = pwd();
        } catch (FtpException e) {
            // ignore
        }

        return pwd == null ? this.init() : this;
    }

    /**
     * 变更工作目录到新目录
     * <pre>
     *     1. directory 不以"/"开头表示相对路径，新目录以当前工作目录为基准，即当前工作目录下不存在此新目录时，变更失败
     *     2. 参数必须是目录，当是文件时改变路径无效
     * </pre>
     *
     * @param directory directory
     * @return {@code true} 打开指定目录成功；{@code false} 打开指定目录失败
     */
    public boolean cd(String directory) {
        if (StringUtils.isBlank(directory)) {
            return false;
        }

        try {
            return client.changeWorkingDirectory(directory);
        } catch (IOException e) {
            throw new FtpException(e);
        }
    }

    /**
     * 打开上级目录
     *
     * @return {@code true} 打开上级目录成功；{@code false} 打开上级目录失败
     */
    public boolean cdToParent() {
        try {
            return client.changeToParentDirectory();
        } catch (IOException e) {
            throw new FtpException(e);
        }
    }

    /**
     * 远程当前目录（工作目录）
     *
     * @return 远程当前目录
     */
    public String pwd() {
        try {
            return client.printWorkingDirectory();
        } catch (IOException e) {
            throw new FtpException(e);
        }
    }

    /**
     * 在当前远程目录（工作目录）下创建新的目录，如果目录本身已经存在，则不会再创建，多层目录需要一层层创建
     * <pre>
     *     1. dir 可以是相对路径，即不以"/"开头，相对的是 FTPClient 当前的工作路径，会在当前工作目录进行新建目录
     *     2. dir 可以是绝对路径，即以"/"开头，与 FTPCLient 当前工作目录无关
     *     3. 注意多级目录时，必须确保父目录存在，否则创建失败
     * </pre>
     *
     * @param dir 文件目录
     * @return {@code true} 创建成功；{@code false} 创建失败
     */
    public boolean mkdir(String dir) {
        try {
            return this.client.makeDirectory(dir);
        } catch (IOException e) {
            throw new FtpException(e);
        }
    }

    /**
     * 创建指定文件夹及其父目录，从根目录开始创建，创建完成后回到默认的工作目录
     *
     * @param dir 文件夹路径，绝对路径
     */
    public void mkdirs(String dir) {
        if (StringUtils.isBlank(dir)) {
            return;
        }

        final String[] dirs = dir.split("[\\\\/]+");

        final String now = pwd();
        if (dirs.length > 0 && StringUtils.isEmpty(dirs[0])) {
            // 首位为空，表示以/开头
            this.cd(StringUtils.SLASH);
        }
        for (String s : dirs) {
            if (StringUtils.isNotEmpty(s) && (!cd(s))) {
                    // 目录不存在时创建
                    mkdir(s);
                    cd(s);
            }
        }
        // 切换回工作目录
        cd(now);
    }

    /**
     * 判断文件或目录是否存在
     *
     * @param path 文件或目录
     * @return {@code true} 存在；{@code false} 不存在
     */
    public boolean exist(String path) {
        final String fileName = FileUtils.getFileName(path);
        if (StringUtils.isEmpty(fileName)) {
            return false;
        }

        final String dir = StringUtils.removeSuffix(path, fileName);
        final List<String> names = ls(dir);
        return CollectionUtils.contains(names, fileName::equalsIgnoreCase);
    }

    /**
     * 遍历当前目录下所有文件和目录，不会递归遍历
     *
     * @return 文件和目录列表
     */
    public List<String> ls() {
        return ls(null, null);
    }

    /**
     * 遍历当前目录下所有文件和目录，不会递归遍历
     *
     * @param filter 过滤器
     * @return 文件和目录列表
     * @since 1.6.0
     */
    public List<String> ls(FTPFileFilter filter) {
        return ls(null, filter);
    }

    /**
     * 遍历某个目录下所有文件和目录，不会递归遍历
     *
     * @param path 需要遍历的目录
     * @return 文件和目录列表
     */
    public List<String> ls(String path) {
        return ls(path, null);
    }

    /**
     * 遍历某个目录下所有文件和目录，不会递归遍历
     *
     * @param path   需要遍历的目录
     * @param filter 过滤器
     * @return 文件和目录列表
     * @since 1.6.0
     */
    public List<String> ls(String path, FTPFileFilter filter) {
        final FTPFile[] ftpFiles = lsFiles(path, filter);

        final List<String> fileNames = new ArrayList<>();
        for (FTPFile ftpFile : ftpFiles) {
            fileNames.add(ftpFile.getName());
        }
        return fileNames;
    }

    /**
     * 遍历当前目录下所有文件，不会递归遍历
     *
     * @return 文件列表
     * @since 1.6.0
     */
    public List<String> lsFile() {
        return lsFile(null, null);
    }

    /**
     * 遍历当前目录下所有文件，不会递归遍历
     *
     * @param filter 过滤器
     * @return 文件列表
     * @since 1.6.0
     */
    public List<String> lsFile(FTPFileFilter filter) {
        return lsFile(null, filter);
    }

    /**
     * 遍历某个目录下所有文件，不会递归遍历
     *
     * @param path 需要遍历的目录
     * @return 文件列表
     * @since 1.6.0
     */
    public List<String> lsFile(String path) {
        return lsFile(path, null);
    }

    /**
     * 遍历某个目录下所有文件，不会递归遍历
     *
     * @param path   需要遍历的目录
     * @param filter 过滤器
     * @return 文件列表
     * @since 1.6.0
     */
    public List<String> lsFile(String path, FTPFileFilter filter) {
        final FTPFile[] ftpFiles = lsFiles(path, filter);

        final List<String> fileNames = new ArrayList<>();
        for (FTPFile ftpFile : ftpFiles) {
            if (ftpFile.isFile()) {
                fileNames.add(ftpFile.getName());
            }
        }
        return fileNames;
    }

    /**
     * 递归遍历当前目录下所有文件和目录
     *
     * @return 文件和目录列表
     */
    public List<String> lsRecursive() {
        return lsRecursive(null, null);
    }

    /**
     * 递归遍历当前目录下所有文件和目录
     *
     * @param filter 过滤器
     * @return 文件和目录列表
     * @since 1.6.0
     */
    public List<String> lsRecursive(FTPFileFilter filter) {
        return lsRecursive(null, filter);
    }

    /**
     * 递归遍历某个目录下所有文件和目录
     *
     * @param path 需要遍历的目录
     * @return 文件和目录列表
     */
    public List<String> lsRecursive(String path) {
        return lsRecursive(path, null);
    }

    /**
     * 递归遍历某个目录下所有文件和目录
     *
     * @param path   需要遍历的目录
     * @param filter 过滤器
     * @return 文件和目录列表
     * @since 1.6.0
     */
    public List<String> lsRecursive(String path, FTPFileFilter filter) {
        final FTPFile[] ftpFiles = lsFiles(path, filter);

        final List<String> fileNames = new ArrayList<>();
        path = StringUtils.isBlank(path) ? StringUtils.EMPTY : (path + File.separator);
        for (FTPFile ftpFile : ftpFiles) {
            fileNames.add(path + ftpFile.getName());
            if (ftpFile.isDirectory()) {
                fileNames.addAll(lsRecursive(path + ftpFile.getName(), filter));
            }
        }
        return fileNames;
    }

    /**
     * 递归遍历当前目录下所有文件
     *
     * @return 文件列表
     * @since 1.6.0
     */
    public List<String> lsFileRecursive() {
        return lsFileRecursive(null, null);
    }

    /**
     * 递归遍历当前目录下所有文件
     *
     * @param filter 过滤器
     * @return 文件列表
     * @since 1.6.0
     */
    public List<String> lsFileRecursive(FTPFileFilter filter) {
        return lsFileRecursive(null, filter);
    }

    /**
     * 递归遍历某个目录下所有文件
     *
     * @param path 需要遍历的目录
     * @return 文件列表
     * @since 1.6.0
     */
    public List<String> lsFileRecursive(String path) {
        return lsFileRecursive(path, null);
    }

    /**
     * 递归遍历某个目录下所有文件
     *
     * @param path   需要遍历的目录
     * @param filter 过滤器
     * @return 文件列表
     * @since 1.6.0
     */
    public List<String> lsFileRecursive(String path, FTPFileFilter filter) {
        final FTPFile[] ftpFiles = lsFiles(path);

        final List<String> fileNames = new ArrayList<>();
        path = StringUtils.isBlank(path) ? StringUtils.EMPTY : (path + File.separator);
        for (FTPFile ftpFile : ftpFiles) {
            if (ftpFile.isDirectory()) {
                fileNames.addAll(lsFileRecursive(path + ftpFile.getName(), filter));
            } else {
                if (filter == null || filter.accept(ftpFile)) {
                    fileNames.add(path + ftpFile.getName());
                }
            }
        }
        return fileNames;
    }

    /**
     * 遍历当前工作目录下所有文件和目录，不会递归遍历
     *
     * @return 文件和目录列表
     */
    public FTPFile[] lsFiles() {
        return lsFiles(null, null);
    }

    /**
     * 遍历当前工作目录下所有文件和目录，不会递归遍历
     *
     * @param filter 过滤器
     * @return 文件和目录列表
     * @since 1.6.0
     */
    public FTPFile[] lsFiles(FTPFileFilter filter) {
        return lsFiles(null, filter);
    }

    /**
     * 遍历某个目录下所有文件和目录，不会递归遍历
     *
     * @param path 需要遍历的目录
     * @return 文件和目录列表
     */
    public FTPFile[] lsFiles(String path) {
        try {
            return StringUtils.isBlank(path) ? this.client.listFiles() : this.client.listFiles(path);
        } catch (IOException e) {
            throw new FtpException(e);
        }
    }

    /**
     * 遍历某个目录下所有文件和目录，不会递归遍历
     *
     * @param path   需要遍历的目录
     * @param filter 过滤器
     * @return 文件和目录列表
     */
    public FTPFile[] lsFiles(String path, FTPFileFilter filter) {
        try {
            if (StringUtils.isBlank(path)) {
                path = client.printWorkingDirectory();
            }
            return filter == null ? this.client.listFiles(path) : this.client.listFiles(path, filter);
        } catch (IOException e) {
            throw new FtpException(e);
        }
    }

    /**
     * 遍历当前工作目录下所有文件和目录，过滤器忽略文件目录，不会递归遍历
     *
     * @param filter 过滤器
     * @return 文件和目录列表
     * @since 1.6.0
     */
    public List<FTPFile> lsFilesIgnoreDirectory(FTPFileFilter filter) {
        return lsFilesIgnoreDirectory(null, filter);
    }

    /**
     * 遍历某个目录下所有文件和目录，过滤器忽略文件目录，不会递归遍历
     *
     * @param path   需要遍历的目录
     * @param filter 过滤器
     * @return 文件和目录列表
     * @since 1.6.0
     */
    public List<FTPFile> lsFilesIgnoreDirectory(String path, FTPFileFilter filter) {
        try {
            if (StringUtils.isBlank(path)) {
                path = client.printWorkingDirectory();
            }

            List<FTPFile> ftpFileList = new ArrayList<>();
            FTPFile[] ftpFiles = this.client.listFiles(path);
            for (FTPFile ftpFile : ftpFiles) {
                if (ftpFile.isDirectory() || (filter != null && filter.accept(ftpFile))) {
                    ftpFileList.add(ftpFile);
                }
            }

            return ftpFileList;
        } catch (IOException e) {
            throw new FtpException(e);
        }
    }

    /**
     * 获取服务端目录状态
     *
     * @param path 服务端目录
     * @return 服务端目录状态，服务端不同，返回不同
     */
    public int stat(String path) {
        try {
            return this.client.stat(path);
        } catch (IOException e) {
            throw new FtpException(e);
        }
    }

    /**
     * 判断 FTP 服务器目录是否为空，如果是文件则判断文件是否存在
     *
     * @param path 文件或目录路径
     * @return {@code true} 文件存在或目录不为空；{@code false} 文件不存在或目录为空
     */
    public boolean isEmpty(String path) {
        try {
            return ArrayUtils.isNotEmpty(client.listFiles(path));
        } catch (IOException e) {
            throw new FtpException(e);
        }
    }

    /**
     * 删除指定目录下的指定文件
     *
     * @param path 文件路径
     * @return {@code true} 删除成功；{@code false} 删除失败
     */
    public boolean delFile(String path) {
        final String pwd = pwd();
        final String fileName = FileUtils.getFileName(path);
        final String dir = StringUtils.removeSuffix(path, fileName);
        cd(dir);
        boolean isSuccess;
        try {
            isSuccess = client.deleteFile(fileName);
        } catch (IOException e) {
            throw new FtpException(e);
        } finally {
            // 回到原目录
            cd(pwd);
        }
        return isSuccess;
    }

    /**
     * 删除文件夹及其文件夹下的所有文件
     *
     * @param dirPath 文件夹路径
     * @return {@code true} 删除成功；{@code false} 删除失败
     */
    public boolean delDir(String dirPath) {
        FTPFile[] dirs;
        try {
            dirs = client.listFiles(dirPath);
        } catch (IOException e) {
            throw new FtpException(e);
        }
        String name;
        String childPath;
        for (FTPFile ftpFile : dirs) {
            name = ftpFile.getName();
            childPath = StringUtils.format(PATH_FORMATTER, dirPath, name);
            if (ftpFile.isDirectory()) {
                // 上级和本级目录除外
                if (!StringUtils.DOT.equals(name) && !StringUtils.DOUBLE_DOT.equals(name)) {
                    delDir(childPath);
                }
            } else {
                delFile(childPath);
            }
        }

        // 删除空目录
        try {
            return this.client.removeDirectory(dirPath);
        } catch (IOException e) {
            throw new FtpException(e);
        }
    }

    /**
     * 将本地文件上传到目标服务器当前目录下，目标文件名将与 file 文件名相同，覆盖模式
     *
     * @param file 待上传的文件
     * @return {@code true} 上传成功；{@code false} 上传失败
     */
    public boolean uploadToPwd(File file) {
        if (file == null || !file.exists()) {
            return false;
        }

        return uploadToPwd(file, file.getName());
    }

    /**
     * 将本地文件上传到目标服务器当前目录下，覆盖模式
     *
     * @param file     待上传的文件
     * @param fileName 自定义在服务端保存的文件名
     * @return {@code true} 上传成功；{@code false} 上传失败
     */
    public boolean uploadToPwd(File file, String fileName) {
        return upload(file, null, fileName);
    }

    /**
     * 将本地文件上传到目标服务器，目标文件名将与 file 文件名相同，覆盖模式
     * <pre>
     *     1. destPath 为 null 或""上传到当前路径
     *     2. destPath 为相对路径则相对于当前路径的子路径
     *     3. destPath 为绝对路径则上传到此路径
     * </pre>
     *
     * @param file     待上传的文件
     * @param destPath 服务端路径，可以为 {@code null} 或者相对路径或绝对路径
     * @return {@code true} 上传成功；{@code false} 上传失败
     */
    public boolean upload(File file, String destPath) {
        if (file == null || !file.exists()) {
            return false;
        }

        return upload(file, destPath, file.getName());
    }

    /**
     * 将本地文件上传到目标服务器，覆盖模式
     * <pre>
     *     1. destPath 为 null 或""上传到当前路径
     *     2. destPath 为相对路径则相对于当前路径的子路径
     *     3. destPath 为绝对路径则上传到此路径
     * </pre>
     *
     * @param file     待上传的文件
     * @param destPath 服务端路径，可以为 {@code null} 或者相对路径或绝对路径
     * @param fileName 自定义在服务端保存的文件名
     * @return {@code true} 上传成功；{@code false} 上传失败
     */
    public boolean upload(File file, String destPath, String fileName) {
        if (file == null || !file.exists()) {
            return false;
        }

        try (InputStream in = FileUtils.getInputStream(file)) {
            return upload(in, destPath, fileName);
        } catch (IOException e) {
            throw new FtpException(e);
        }
    }

    /**
     * 将本地文件上传到目标服务器，覆盖模式
     * <pre>
     *     1. destPath 为 null 或""上传到当前路径
     *     2. destPath 为相对路径则相对于当前路径的子路径
     *     3. destPath 为绝对路径则上传到此路径
     * </pre>
     *
     * @param inputStream 文件流
     * @param destPath    服务端路径，可以为 {@code null} 或者相对路径或绝对路径
     * @param fileName    自定义在服务端保存的文件名
     * @return {@code true} 上传成功；{@code false} 上传失败
     */
    public boolean upload(InputStream inputStream, String destPath, String fileName) {
        if (inputStream == null) {
            return false;
        }

        // 设置文件传输类型
        setFileType(FTP.BINARY_FILE_TYPE);
        // 当前工作目录
        final String pwd = pwd();
        // 如果目标目录不为空，创建目录并变更工作目录到新目录
        if (StringUtils.isNotBlank(destPath)) {
            mkdirs(destPath);
            boolean isOk = cd(destPath);
            if (!isOk) {
                return false;
            }
        }

        // 上传文件
        try {
            return client.storeFile(fileName, inputStream);
        } catch (IOException e) {
            throw new FtpException(e);
        } finally {
            cd(pwd);
        }
    }

    /**
     * 将本地文件或目录上传到目标服务器当前工作目录下，覆盖模式
     *
     * @param sourcePath 待上传的文件或目录
     */
    public void uploadRecursiveToPwd(String sourcePath) {
        uploadRecursiveToPwd(FileUtils.newFile(sourcePath));
    }

    /**
     * 将本地文件或目录上传到目标服务器当前工作目录下，覆盖模式
     *
     * @param file 待上传的文件或目录
     */
    public void uploadRecursiveToPwd(File file) {
        uploadRecursive(file, null);
    }

    /**
     * 将本地文件或目录上传到目标服务器，覆盖模式
     * <pre>
     *     1. destPath 为 null 或""上传到当前路径
     *     2. destPath 为相对路径则相对于当前路径的子路径
     *     3. destPath 为绝对路径则上传到此路径
     * </pre>
     *
     * @param sourcePath 待上传的文件或目录
     * @param destPath   服务端路径，可以为 {@code null} 或者相对路径或绝对路径
     */
    public void uploadRecursive(String sourcePath, String destPath) {
        uploadRecursive(FileUtils.newFile(sourcePath), destPath);
    }

    /**
     * 将本地文件或目录上传到目标服务器，覆盖模式
     * <pre>
     *     1. destPath 为 null 或""上传到当前路径
     *     2. destPath 为相对路径则相对于当前路径的子路径
     *     3. destPath 为绝对路径则上传到此路径
     * </pre>
     *
     * @param file     待上传的文件或目录
     * @param destPath 服务端路径，可以为 {@code null} 或者相对路径或绝对路径
     */
    public void uploadRecursive(File file, String destPath) {
        // 设置文件传输类型
        setFileType(FTP.BINARY_FILE_TYPE);
        // 当前工作目录
        final String pwd = pwd();
        // 如果目标目录不为空，创建目录并变更工作目录到新目录
        if (StringUtils.isNotBlank(destPath)) {
            mkdirs(destPath);
            boolean isOk = cd(destPath);
            if (!isOk) {
                return;
            }
        }

        // 上传文件
        try {
            uploadRecursive(file);
        } catch (IOException e) {
            throw new FtpException(e);
        } finally {
            cd(pwd);
        }
    }

    /**
     * 将本地文件或目录上传到目标服务器，覆盖模式
     *
     * @param file 待上传的文件或目录
     * @throws IOException {@link IOException}
     */
    private void uploadRecursive(File file) throws IOException {
        if (file == null || !file.exists()) {
            return;
        }

        // 上传目录
        if (file.isDirectory()) {
            // 创建目录
            client.makeDirectory(file.getName());
            // 变更工作目录
            client.changeWorkingDirectory(file.getName());

            File[] files = file.listFiles();
            if (ArrayUtils.isEmpty(files)) {
                return;
            }

            for (File loopFile : files) {
                if (loopFile.isDirectory()) {
                    // 如果有子目录，则迭代调用方法进行上传
                    uploadRecursive(loopFile);
                    // 将工作目录移到上一层，子目录上传完成后，必须将工作目录返回上一层，否则容易导致文件上传后，目录不一致
                    client.changeToParentDirectory();
                } else {
                    // 如果目录中全是文件，则直接上传
                    FileInputStream input = new FileInputStream(loopFile);
                    client.storeFile(loopFile.getName(), input);
                    input.close();
                }
            }
        } else {// 上传文件
            FileInputStream input = new FileInputStream(file);
            client.storeFile(file.getName(), input);
            input.close();
        }
    }

    /**
     * 下载文件
     *
     * @param remoteFilePath FTP 服务器文件路径
     * @param outFilePath    输出文件路径，如果是目录，则必须是已存在的目录，文件名将与 FTP 服务器文件名相同
     * @return {@code true} 下载文件成功；{@code false} 下载文件失败
     */
    public boolean download(String remoteFilePath, String outFilePath) {
        if (StringUtils.hasBlank(remoteFilePath, outFilePath)) {
            return false;
        }

        return download(remoteFilePath, new File(outFilePath));
    }

    /**
     * 下载文件
     *
     * @param remoteFilePath FTP 服务器文件路径
     * @param outFile        输出文件或目录，如果是目录，则必须是已存在的目录，文件名将与 FTP 服务器文件名相同
     * @return {@code true} 下载文件成功；{@code false} 下载文件失败
     */
    public boolean download(String remoteFilePath, File outFile) {
        if (StringUtils.isBlank(remoteFilePath)) {
            return false;
        }

        final String fileName = FileUtils.getFileName(remoteFilePath);
        final String dir = StringUtils.removeSuffix(remoteFilePath, fileName);
        return download(dir, fileName, outFile);
    }

    /**
     * 下载文件
     *
     * @param remotePath  FTP 服务器文件目录，为 null 或""则表示当前工作目录
     * @param fileName    文件名
     * @param outPath     输出文件目录
     * @param outFileName 输出文件名
     * @return {@code true} 下载文件成功；{@code false} 下载文件失败
     */
    public boolean download(String remotePath, String fileName, String outPath, String outFileName) {
        return download(remotePath, fileName, new File(outPath, outFileName));
    }

    /**
     * 下载文件
     *
     * @param remotePath  FTP 服务器文件目录，为 null 或""则表示当前工作目录
     * @param fileName    文件名
     * @param outFilePath 输出文件路径，如果是目录，则必须是已存在的目录，文件名将与 fileName 相同
     * @return {@code true} 下载文件成功；{@code false} 下载文件失败
     */
    public boolean download(String remotePath, String fileName, String outFilePath) {
        return download(remotePath, fileName, new File(outFilePath));
    }

    /**
     * 下载文件
     *
     * @param remotePath FTP 服务器文件目录，为 null 或""则表示当前工作目录
     * @param fileName   文件名
     * @param outFile    输出文件或目录
     * @return {@code true} 下载文件成功；{@code false} 下载文件失败
     */
    public boolean download(String remotePath, String fileName, File outFile) {
        if (outFile.isDirectory()) {
            outFile = new File(outFile, fileName);
        }
        if (!outFile.exists()) {
            FileUtils.createFile(outFile);
        }

        try (OutputStream outputStream = FileUtils.getOutputStream(outFile)) {
            return download(remotePath, fileName, outputStream);
        } catch (IOException e) {
            throw new FtpException(e);
        }
    }

    /**
     * 下载文件到输出流
     *
     * @param remotePath   FTP 服务器文件目录，为 null 或""则表示当前工作目录
     * @param fileName     文件名
     * @param outputStream 输出流
     * @return {@code true} 下载文件成功；{@code false} 下载文件失败
     */
    public boolean download(String remotePath, String fileName, OutputStream outputStream) {
        // 设置文件传输类型
        setFileType(FTP.BINARY_FILE_TYPE);
        // 当前工作目录
        final String pwd = pwd();
        // 如果文件目录不为空，变更工作目录
        if (StringUtils.isNotBlank(remotePath)) {
            boolean isOk = cd(remotePath);
            if (!isOk) {
                return false;
            }
        }

        // 下载文件
        try {
            return client.retrieveFile(fileName, outputStream);
        } catch (IOException e) {
            throw new FtpException(e);
        } finally {
            cd(pwd);
        }
    }

    /**
     * 下载文件到输出流
     *
     * @param remoteFilePath FTP 服务器文件路径
     * @param outputStream   输出流
     * @return {@code true} 下载文件成功；{@code false} 下载文件失败
     * @since 1.6.0
     */
    public boolean download(String remoteFilePath, OutputStream outputStream) {
        // 设置文件传输类型
        setFileType(FTP.BINARY_FILE_TYPE);

        // 下载文件
        try {
            return client.retrieveFile(remoteFilePath, outputStream);
        } catch (IOException e) {
            throw new FtpException(e);
        }
    }

    /**
     * 递归下载 FTP 服务器上文件到本地（文件目录和服务器同步），服务器上有新文件会覆盖本地文件
     *
     * @param remotePath FTP 服务器目录
     * @param destPath   本地目录
     */
    public void downloadRecursive(String remotePath, String destPath) {
        downloadRecursive(remotePath, FileUtils.mkdirs(destPath));
    }

    /**
     * 递归下载 FTP 服务器上文件到本地（文件目录和服务器同步），服务器上有新文件会覆盖本地文件
     *
     * @param remotePath FTP 服务器目录
     * @param destPath   本地目录
     * @param filter     过滤器
     * @since 1.6.0
     */
    public void downloadRecursive(String remotePath, String destPath, FTPFileFilter filter) {
        downloadRecursive(remotePath, FileUtils.mkdirs(destPath), filter);
    }

    /**
     * 递归下载 FTP 服务器上文件到本地（文件目录和服务器同步），服务器上有新文件会覆盖本地文件
     *
     * @param remotePath FTP 服务器目录
     * @param destDir    本地目录
     */
    public void downloadRecursive(String remotePath, File destDir) {
        downloadRecursive(remotePath, destDir, null);
    }

    /**
     * 递归下载 FTP 服务器上文件到本地（文件目录和服务器同步），服务器上有新文件会覆盖本地文件
     *
     * @param remotePath FTP 服务器目录
     * @param destDir    本地目录
     * @param filter     过滤器
     * @since 1.6.0
     */
    public void downloadRecursive(String remotePath, File destDir, FTPFileFilter filter) {
        if (destDir == null) {
            return;
        }

        String fileName;
        String srcFile;
        File destFile;
        FTPFile[] ftpFiles = lsFiles(remotePath, filter);
        if (ArrayUtils.isEmpty(ftpFiles)) {
            return;
        }
        for (FTPFile ftpFile : ftpFiles) {
            fileName = ftpFile.getName();
            srcFile = StringUtils.format(PATH_FORMATTER, remotePath, fileName);
            destFile = FileUtils.newFile(destDir, fileName);

            if (ftpFile.isDirectory()) {
                // 服务端依旧是目录，继续递归
                downloadRecursive(srcFile, destFile, filter);
            } else {
                // 本地不存在文件或者 FTP 上文件有修改则下载
                if (!FileUtils.isFileExist(destFile) || destFile.length() != ftpFile.getSize()
                        || (ftpFile.getTimestamp().getTimeInMillis() > destFile.lastModified())) {
                    download(srcFile, destFile);
                }
            }
        }
    }

    /**
     * 递归下载 FTP 服务器上文件到本地（文件目录和服务器同步），服务器上有新文件会覆盖本地文件，过滤器忽略文件目录
     *
     * @param remotePath FTP 服务器目录
     * @param destPath   本地目录
     * @param filter     过滤器
     * @since 1.6.0
     */
    public void downloadRecursiveIgnoreDirectory(String remotePath, String destPath, FTPFileFilter filter) {
        downloadRecursiveIgnoreDirectory(remotePath, FileUtils.mkdirs(destPath), filter);
    }

    /**
     * 递归下载 FTP 服务器上文件到本地（文件目录和服务器同步），服务器上有新文件会覆盖本地文件，过滤器忽略文件目录
     *
     * @param remotePath FTP 服务器目录
     * @param destDir    本地目录
     * @param filter     过滤器
     * @since 1.6.0
     */
    public void downloadRecursiveIgnoreDirectory(String remotePath, File destDir, FTPFileFilter filter) {
        if (destDir == null) {
            return;
        }

        String fileName;
        String srcFile;
        File destFile;
        List<FTPFile> ftpFiles = lsFilesIgnoreDirectory(remotePath, filter);
        for (FTPFile ftpFile : ftpFiles) {
            fileName = ftpFile.getName();
            srcFile = StringUtils.format(PATH_FORMATTER, remotePath, fileName);
            destFile = FileUtils.newFile(destDir, fileName);

            if (ftpFile.isDirectory()) {
                // 服务端依旧是目录，继续递归
                downloadRecursiveIgnoreDirectory(srcFile, destFile, filter);
            } else {
                // 本地不存在文件或者 FTP 上文件有修改则下载
                if (!FileUtils.isFileExist(destFile) || destFile.length() != ftpFile.getSize()
                        || (ftpFile.getTimestamp().getTimeInMillis() > destFile.lastModified())) {
                    download(srcFile, destFile);
                }
            }
        }
    }

    /**
     * 读取 FTP 服务器上文件内容
     *
     * @param remotePath FTP 服务器文件目录，为 null 或""则表示当前工作目录
     * @param fileName   文件名
     * @return 文件流
     * @since 1.6.0
     */
    public InputStream read(String remotePath, String fileName) {
        // 当前工作目录
        final String pwd = pwd();
        // 如果文件目录不为空，变更工作目录
        if (StringUtils.isNotBlank(remotePath)) {
            boolean isOk = cd(remotePath);
            if (!isOk) {
                throw new FtpException("file path is null");
            }
        }

        try {
            setFileType(FTP.BINARY_FILE_TYPE);
            return client.retrieveFileStream(fileName);
        } catch (IOException e) {
            throw new FtpException(e);
        } finally {
            cd(pwd);
            try {
                client.completePendingCommand();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取 FTP 服务器上文件内容
     *
     * @param remoteFileName FTP 服务器文件路径
     * @return 文件流
     * @since 1.6.0
     */
    public InputStream read(String remoteFileName) {
        try {
            setFileType(FTP.BINARY_FILE_TYPE);
            return client.retrieveFileStream(remoteFileName);
        } catch (IOException e) {
            throw new FtpException(e);
        } finally {
            try {
                client.completePendingCommand();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 移动文件
     *
     * @param fromFilePath 待移动文件路径，如果为相对路径则是相对于当前工作目录
     * @param toFilePath   移动后文件路径，如果为相对路径则是相对于当前工作目录
     * @return {@code true} 移动文件成功；{@code false} 移动文件失败
     */
    public boolean move(String fromFilePath, String toFilePath) {
        if (StringUtils.hasBlank(fromFilePath, toFilePath)) {
            return false;
        }

        // 当前工作目录
        final String pwd = pwd();
        // 如果移动后目录不为空，创建目录
        final String toName = FileUtils.getFileName(toFilePath);
        final String toPath = StringUtils.removeSuffix(toFilePath, toName);
        if (StringUtils.isNotBlank(toPath)) {
            mkdirs(toPath);
        }

        // 重命名文件
        try {
            return client.rename(fromFilePath, toFilePath);
        } catch (IOException e) {
            throw new FtpException(e);
        } finally {
            cd(pwd);
        }
    }

    /**
     * 移动文件
     *
     * @param fromPath 待移动目录，为 null 或""则表示当前工作目录
     * @param fromName 待移动文件名
     * @param toPath   移动后目录，为 null 或""则表示当前工作目录
     * @param toName   移动后文件名
     * @return {@code true} 移动文件成功；{@code false} 移动文件失败
     */
    public boolean move(String fromPath, String fromName, String toPath, String toName) {
        final String fromFilePath = StringUtils.isBlank(fromPath) ? fromName : (fromPath + File.separator + fromName);
        final String toFilePath = StringUtils.isBlank(toPath) ? toName : (toPath + File.separator + toName);

        return move(fromFilePath, toFilePath);
    }

    /**
     * 移动文件到目录，移动后文件名与原文件名相同
     *
     * @param fromPath 待移动目录，为 null 或""则表示当前工作目录
     * @param fromName 待移动文件名
     * @param toPath   移动后目录，为 null 或""则表示当前工作目录
     * @return {@code true} 移动文件成功；{@code false} 移动文件失败
     */
    public boolean moveToDir(String fromPath, String fromName, String toPath) {
        return move(fromPath, fromName, toPath, fromName);
    }

    /**
     * 移动文件到目录，移动后文件名与原文件名相同
     *
     * @param fromFilePath 待移动文件路径
     * @param toPath       移动后目录，为 null 或""则表示当前工作目录
     * @return {@code true} 移动文件成功；{@code false} 移动文件失败
     */
    public boolean moveToDir(String fromFilePath, String toPath) {
        final String fromName = FileUtils.getFileName(fromFilePath);
        final String toFilePath = StringUtils.isBlank(toPath) ? fromName : (toPath + File.separator + fromName);

        return move(fromFilePath, toFilePath);
    }

    /**
     * 获取 FTPClient 客户端对象
     *
     * @return {@link FTPClient}
     */
    public FTPClient getClient() {
        return this.client;
    }

    @Override
    public void close() throws IOException {
        if (this.client != null) {
            this.client.logout();
            if (this.client.isConnected()) {
                this.client.disconnect();
            }
            this.client = null;
        }
    }
}
