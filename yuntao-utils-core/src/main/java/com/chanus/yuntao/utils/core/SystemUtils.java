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

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.net.*;
import java.util.*;

/**
 * 系统工具类
 *
 * @author Chanus
 * @since 1.0.0
 */
public class SystemUtils {
    /**
     * JDK 的版本
     */
    public static final String JDK_VERSION = System.getProperty("java.version");
    /**
     * JVM 的编码
     */
    public static final String JVM_ENCODING = System.getProperty("file.encoding");
    /**
     * 操作系统名称
     */
    public static final String OS_NAME = System.getProperty("os.name");
    /**
     * 操作系统版本
     */
    public static final String OS_VERSION = System.getProperty("os.version");
    /**
     * 主机架构
     */
    public static final String OS_ARCH = System.getProperty("os.arch");
    /**
     * 当前用户
     */
    public static final String CURRENT_USER = System.getProperty("user.name");
    /**
     * 当前用户的家目录
     */
    public static final String CURRENT_USER_HOME = System.getProperty("user.home");

    /**
     * 系统总的物理内存
     */
    public static long totalPhysicalMemorySize;

    private static final OperatingSystemMXBean OSMXB;

    /**
     * 1KB = 1024B
     */
    public static final int KB = 1024;
    /**
     * 1MB = 1048576B
     */
    public static final int MB = 1048576;
    /**
     * 1GB = 1073741824B
     */
    public static final int GB = 1073741824;
    /**
     * 1TB = 1099511627776B
     */
    public static final long TB = 1099511627776L;

    static {
        try {
            OSMXB = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
            totalPhysicalMemorySize = OSMXB.getTotalPhysicalMemorySize();
        } catch (Exception e) {
            throw new RuntimeException("获取系统物理内存失败", e);
        }
    }

    private SystemUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 获取主机名称
     *
     * @return 主机名称
     */
    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException("获取主机名称地址异常", e);
        }
    }

    /**
     * 获取主机 Mac 地址
     *
     * @return 主机 Mac 地址
     */
    public static Set<String> getHostMac() {
        try {
            Set<String> macSet = new HashSet<>();
            StringBuilder sb = new StringBuilder();
            Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
            NetworkInterface ni;
            List<InterfaceAddress> interfaceAddresses;
            while (enumeration.hasMoreElements()) {
                ni = enumeration.nextElement();
                interfaceAddresses = ni.getInterfaceAddresses();
                for (InterfaceAddress addr : interfaceAddresses) {
                    InetAddress ip = addr.getAddress();
                    NetworkInterface network = NetworkInterface.getByInetAddress(ip);
                    if (network == null) {
                        continue;
                    }
                    byte[] mac = network.getHardwareAddress();
                    if (mac == null) {
                        continue;
                    }
                    sb.delete(0, sb.length());
                    for (int i = 0; i < mac.length; i++) {
                        sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                    }
                    macSet.add(sb.toString());
                }
            }
            return macSet;
        } catch (SocketException e) {
            throw new RuntimeException("获取主机 MAC 地址异常", e);
        }
    }

    /**
     * 获取主机 IP，排除回文地址、虚拟地址
     *
     * @return 主机所有网卡 IP
     */
    public static List<String> getHostIp() {
        try {
            List<String> ips = new ArrayList<>();
            Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
            NetworkInterface networkInterface;
            while (enumeration.hasMoreElements()) {
                networkInterface = enumeration.nextElement();
                if (networkInterface.isLoopback() || networkInterface.isVirtual()) {
                    continue;
                }
                Enumeration<InetAddress> inets = networkInterface.getInetAddresses();
                while (inets.hasMoreElements()) {
                    InetAddress addr = inets.nextElement();
                    if (addr.isLoopbackAddress() || !addr.isSiteLocalAddress() || addr.isAnyLocalAddress()) {
                        continue;
                    }
                    ips.add(addr.getHostAddress());
                }
            }
            return ips;
        } catch (SocketException e) {
            throw new RuntimeException("获取主机 IP 异常", e);
        }
    }

    /**
     * 可用的物理内存
     *
     * @return 可用的物理内存
     */
    public static long getFreePhysicalMemorySize() {
        return OSMXB == null ? 0L : OSMXB.getFreePhysicalMemorySize();
    }

    /**
     * 已使用的物理内存
     *
     * @return 已使用的物理内存
     */
    public static long getUsedPhysicalMemorySize() {
        return OSMXB == null ? 0L : (OSMXB.getTotalPhysicalMemorySize() - OSMXB.getFreePhysicalMemorySize());
    }

    /**
     * 获取 JVM 总的内存空间
     *
     * @return JVM 总的内存空间
     */
    public static long getTotalJVMMemorySize() {
        return Runtime.getRuntime().totalMemory();
    }

    /**
     * 获取 JVM 空闲的内存空间
     *
     * @return JVM 空闲的内存空间
     */
    public static long getFreeJVMMemorySize() {
        return Runtime.getRuntime().freeMemory();
    }

    /**
     * 获取 JVM 已用的内存空间
     *
     * @return JVM 已用的内存空间
     */
    public static long getUsedJVMMemorySize() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

    /**
     * 获取 JVM 最大可用内存
     *
     * @return JVM 最大可用内存
     */
    public static long getUsableJVMMemorySize() {
        return getMaxJVMMemorySize() - getTotalJVMMemorySize() + getFreeJVMMemorySize();
    }

    /**
     * 获取 JVM 最大的内存空间
     *
     * @return JVM 最大的内存空间
     */
    public static long getMaxJVMMemorySize() {
        return Runtime.getRuntime().maxMemory();
    }

    /**
     * 获取 JVM 可用的处理器数量（一般为 CPU 核心数）
     *
     * @return 可用的处理器数量
     * @since 1.1.0
     */
    public static int getProcessorCount() {
        return Runtime.getRuntime().availableProcessors();
    }
}
