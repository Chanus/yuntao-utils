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

import jakarta.servlet.http.HttpServletRequest;

/**
 * IP 地址工具类
 *
 * @author Chanus
 * @since 1.0.0
 */
public class IpUtils {
    private IpUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 未知 IP
     */
    private static final String UNKNOWN = "unknown";
    /**
     * 本地 IP
     */
    private static final String LOCALHOST = "127.0.0.1";
    /**
     * 本地 IP（ipv6）
     */
    private static final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";

    /**
     * 获取当前网络 IP 地址
     *
     * @param request {@link HttpServletRequest}
     * @return IP 地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        if (request == null) {
            return UNKNOWN;
        }

        String ipAddress = request.getHeader("X-Real-IP");
        if (StringUtils.isBlank(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("X-Forwarded-For");
        }

        if (StringUtils.isBlank(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }

        if (StringUtils.isBlank(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }

        if (StringUtils.isBlank(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }

        if (StringUtils.isBlank(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (StringUtils.isBlank(ipAddress)) {
            return UNKNOWN;
        } else if (LOCALHOST_IPV6.equals(ipAddress)) {
            return LOCALHOST;
        } else {
            // 从多级反向代理中获得第一个非 unknown IP 地址
            String[] ips = ipAddress.split(StringUtils.COMMA);
            for (String ip : ips) {
                if (!(UNKNOWN.equalsIgnoreCase(ip))) {
                    ipAddress = ip;
                    break;
                }
            }
        }

        return ipAddress;
    }

    /**
     * 检查是否为内部 IP 地址
     *
     * @param ip IP 地址
     * @return 结果
     * @since 1.7.0
     */
    public static boolean internalIp(String ip) {
        if (LOCALHOST.equals(ip)) {
            return true;
        }

        return internalIp(ipToByte(ip));
    }

    /**
     * 检查是否为内部 IP 地址
     *
     * @param addr byte 地址
     * @return 结果
     * @since 1.7.0
     */
    private static boolean internalIp(byte[] addr) {
        if (addr == null || addr.length < 2) {
            return true;
        }
        final byte b0 = addr[0];
        final byte b1 = addr[1];
        // 10.x.x.x/8
        final byte section1 = 0x0A;
        // 172.16.x.x/12
        final byte section2 = (byte) 0xAC;
        final byte section3 = (byte) 0x10;
        final byte section4 = (byte) 0x1F;
        // 192.168.x.x/16
        final byte section5 = (byte) 0xC0;
        final byte section6 = (byte) 0xA8;
        switch (b0) {
            case section1:
                return true;
            case section2:
                return b1 >= section3 && b1 <= section4;
            case section5:
                return b1 == section6;
            default:
                return false;
        }
    }

    /**
     * 将 IPv4 地址转换成字节
     *
     * @param ip IPv4 地址
     * @return byte 字节
     * @since 1.7.0
     */
    private static byte[] ipToByte(String ip) {
        if (ip.length() == 0) {
            return new byte[0];
        }

        byte[] bytes = new byte[4];
        String[] elements = ip.split("\\.", -1);
        try {
            long l;
            int i;
            switch (elements.length) {
                case 1:
                    l = Long.parseLong(elements[0]);
                    if ((l < 0L) || (l > 4294967295L)) {
                        return new byte[0];
                    }
                    bytes[0] = (byte) (int) (l >> 24 & 0xFF);
                    bytes[1] = (byte) (int) ((l & 0xFFFFFF) >> 16 & 0xFF);
                    bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 2:
                    l = Integer.parseInt(elements[0]);
                    if ((l < 0L) || (l > 255L)) {
                        return new byte[0];
                    }
                    bytes[0] = (byte) (int) (l & 0xFF);
                    l = Integer.parseInt(elements[1]);
                    if ((l < 0L) || (l > 16777215L)) {
                        return new byte[0];
                    }
                    bytes[1] = (byte) (int) (l >> 16 & 0xFF);
                    bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 3:
                    for (i = 0; i < 2; ++i) {
                        l = Integer.parseInt(elements[i]);
                        if ((l < 0L) || (l > 255L)) {
                            return new byte[0];
                        }
                        bytes[i] = (byte) (int) (l & 0xFF);
                    }
                    l = Integer.parseInt(elements[2]);
                    if ((l < 0L) || (l > 65535L)) {
                        return new byte[0];
                    }
                    bytes[2] = (byte) (int) (l >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 4:
                    for (i = 0; i < 4; ++i) {
                        l = Integer.parseInt(elements[i]);
                        if ((l < 0L) || (l > 255L)) {
                            return new byte[0];
                        }
                        bytes[i] = (byte) (int) (l & 0xFF);
                    }
                    break;
                default:
                    return new byte[0];
            }
        } catch (NumberFormatException e) {
            return new byte[0];
        }
        return bytes;
    }
}
