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

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IP 地址工具类
 *
 * @author Chanus
 * @date 2020-06-24 10:59:43
 * @since 1.0.0
 */
public class IpUtils {
    /**
     * 获取当前网络 IP 地址
     *
     * @param request {@link HttpServletRequest}
     * @return IP 地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Real-IP");
        if (StringUtils.isBlank(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("X-Forwarded-For");
        }

        if (StringUtils.isBlank(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }

        if (StringUtils.isBlank(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }

        if (StringUtils.isBlank(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }

        if (StringUtils.isBlank(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (StringUtils.isBlank(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if ("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet != null ? inet.getHostAddress() : ipAddress;
            }
        } else {
            String[] ips = ipAddress.split(",");
            for (String ip : ips) {
                if (!("unknown".equalsIgnoreCase(ip))) {
                    ipAddress = ip;
                    break;
                }
            }
        }

        return ipAddress;
    }

    /**
     * IP 物理地址
     * 由于没有找到稳定的免费的获取 IP 物理地址的接口，所以废弃掉该类
     */
    @Deprecated
    public static class IpAddress implements Serializable {
        private static final long serialVersionUID = -1L;

        /**
         * 状态码，0标识成功 ，非0表示失败
         */
        private int code;

        /**
         * IP地址
         */
        private String ip;

        /**
         * 国家
         */
        private String country;

        /**
         * 省
         */
        private String province;

        /**
         * 城市
         */
        private String city;

        /**
         * 运营商
         */
        private String isp;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getIsp() {
            return isp;
        }

        public void setIsp(String isp) {
            this.isp = isp;
        }

        @Override
        public String toString() {
            return "IpAddress [" +
                    "code=" + code +
                    ", ip=" + ip +
                    ", country=" + country +
                    ", province=" + province +
                    ", city=" + city +
                    ", isp=" + isp +
                    "]";
        }
    }
}
