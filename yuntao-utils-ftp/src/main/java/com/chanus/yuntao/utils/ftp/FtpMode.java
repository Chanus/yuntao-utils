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

/**
 * FTP 连接模式
 * <pre>
 *     FTP两种连接模式：主动模式（Active FTP）和被动模式（Passive FTP）。
 *     在主动模式下，FTP客户端随机开启一个大于1024的端口N向服务器的21号端口发起连接，然后开放N+1号端口进行监听，并向服务器发出PORT N+1命令。服务器接收到命令后，会用其本地的FTP数据端口（通常是20）来连接客户端指定的端口N+1，进行数据传输。
 *     在被动模式下，FTP客户端随机开启一个大于1024的端口N向服务器的21号端口发起连接，同时会开启N+1号端口。然后向服务器发送PASV命令，通知服务器自己处于被动模式。服务器收到命令后，会开放一个大于1024的端口P进行监听，然后用PORT P命令通知客户端，自己的数据端口是P。客户端收到命令后，会通过N+1号端口连接服务器的端口P，然后在两个端口之间进行数据传输。
 *     总的来说，主动模式的FTP是指服务器主动连接客户端的数据端口，被动模式的FTP是指服务器被动地等待客户端连接自己的数据端口。
 * </pre>
 *
 * @author Chanus
 * @since 1.5.0
 */
public enum FtpMode {
    /**
     * 主动模式
     */
    ACTIVE,
    /**
     * 被动模式
     */
    PASSIVE
}
