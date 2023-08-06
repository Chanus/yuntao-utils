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

import java.io.Serializable;
import java.nio.charset.Charset;

/**
 * FTP 配置项，提供 FTP 各种参数信息
 *
 * @author Chanus
 * @since 1.5.0
 */
public class FtpConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主机
     */
    private String host;
    /**
     * 端口
     */
    private int port;
    /**
     * 用户名
     */
    private String user;
    /**
     * 密码
     */
    private String password;
    /**
     * 编码
     */
    private Charset charset;
    /**
     * 用于设置终端 Socket 与 FTP 服务器建立连接这个过程的超时时间，单位毫秒
     */
    private int connectionTimeout = 60000;
    /**
     * 用于设置终端的传输控制命令的 Socket 的 SoTimeout，即针对传输控制命令的 Socket 的输入流做读取操作时每次陷入阻塞的超时时间，单位毫秒
     */
    private int defaultTimeout = 0;
    /**
     * 作用与 defaultTimeout 一样，区别仅在于该方法设置的超时会覆盖掉 defaultTimeout 设置的值，单位毫秒
     */
    private int soTimeout = 60000;
    /**
     * 用于设置终端的传输数据的 Socket 的 SoTimeout，即针对传输文件数据的 Socket 的输入流做读取操作时每次陷入阻塞的超时时间，单位毫秒
     */
    private int dataTimeout = 60000;

    /**
     * 构造
     */
    public FtpConfig() {
    }

    /**
     * 构造
     *
     * @param host     主机
     * @param port     端口
     * @param user     用户名
     * @param password 密码
     * @param charset  编码
     */
    public FtpConfig(String host, int port, String user, String password, Charset charset) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.charset = charset;
    }

    /**
     * 构造
     *
     * @param host              主机
     * @param port              端口
     * @param user              用户名
     * @param password          密码
     * @param charset           编码
     * @param connectionTimeout connectionTimeout
     * @param defaultTimeout    defaultTimeout
     * @param soTimeout         soTimeout
     * @param dataTimeout       dataTimeout
     */
    public FtpConfig(String host, int port, String user, String password, Charset charset,
                     int connectionTimeout, int defaultTimeout, int soTimeout, int dataTimeout) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.charset = charset;
        this.connectionTimeout = connectionTimeout;
        this.defaultTimeout = defaultTimeout;
        this.soTimeout = soTimeout;
        this.dataTimeout = dataTimeout;
    }

    /**
     * 创建 FtpConfig 对象
     *
     * @return FtpConfig
     */
    public static FtpConfig create() {
        return new FtpConfig();
    }

    public String getHost() {
        return host;
    }

    public FtpConfig setHost(String host) {
        this.host = host;
        return this;
    }

    public int getPort() {
        return port;
    }

    public FtpConfig setPort(int port) {
        this.port = port;
        return this;
    }

    public String getUser() {
        return user;
    }

    public FtpConfig setUser(String user) {
        this.user = user;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public FtpConfig setPassword(String password) {
        this.password = password;
        return this;
    }

    public Charset getCharset() {
        return charset;
    }

    public FtpConfig setCharset(Charset charset) {
        this.charset = charset;
        return this;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public FtpConfig setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
        return this;
    }

    public int getDefaultTimeout() {
        return defaultTimeout;
    }

    public FtpConfig setDefaultTimeout(int defaultTimeout) {
        this.defaultTimeout = defaultTimeout;
        return this;
    }

    public int getSoTimeout() {
        return soTimeout;
    }

    public FtpConfig setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
        return this;
    }

    public int getDataTimeout() {
        return dataTimeout;
    }

    public FtpConfig setDataTimeout(int dataTimeout) {
        this.dataTimeout = dataTimeout;
        return this;
    }
}
