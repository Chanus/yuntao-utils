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

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Cookie 操作工具类
 *
 * @author Chanus
 * @date 2020-06-24 11:16:05
 * @since 1.0.0
 */
public class CookieUtils {
    private CookieUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 添加 cookie
     *
     * @param response {@link HttpServletResponse}
     * @param name     cookie 名称
     * @param value    cookie 值
     * @param expiry   cookie 有效时间，不设置的话，则 cookie 不写入硬盘，而是写在内存，只在当前页面有用，以秒为单位
     * @param path     cookie 路径
     * @param domain   cookie 域
     */
    public static void addCookie(HttpServletResponse response, String name, String value, Integer expiry, String path, String domain) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(expiry);
        cookie.setPath(StringUtils.isBlank(path) ? "/" : path);
        if (StringUtils.isNotBlank(domain)) {
            cookie.setDomain(domain);
        }
        response.addCookie(cookie);
    }

    /**
     * 修改 cookie
     *
     * @param request  {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @param name     cookie 名称
     * @param value    cookie 值
     * @param expiry   cookie 有效时间，不设置的话，则 cookie 不写入硬盘，而是写在内存，只在当前页面有用，以秒为单位
     * @param path     cookie 路径
     * @param domain   cookie 域
     */
    public static void editCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, Integer expiry, String path, String domain) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && StringUtils.isNotBlank(name)) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    cookie.setValue(value);
                    cookie.setMaxAge(expiry);
                    if (StringUtils.isNotBlank(path)) {
                        cookie.setPath(path);
                    }
                    if (StringUtils.isNotBlank(domain)) {
                        cookie.setDomain(domain);
                    }
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }

    /**
     * 根据名称获取 cookie 值
     *
     * @param request {@link HttpServletRequest}
     * @param name    cookie 名称
     * @return cookie 值
     */
    public static String getCookieByName(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || StringUtils.isBlank(name)) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }

    /**
     * 根据名称清除 cookie
     *
     * @param response {@link HttpServletResponse}
     * @param name     cookie 名称
     * @param path     cookie 路径
     * @param domain   cookie 域
     */
    public static void removeCookieByName(HttpServletResponse response, String name, String path, String domain) {
        Cookie cookie = new Cookie(name, null);
        if (StringUtils.isNotBlank(path)) {
            cookie.setPath(path);
        }
        if (StringUtils.isNotBlank(domain)) {
            cookie.setDomain(domain);
        }

        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    /**
     * 清除所有 cookie
     *
     * @param request  {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @param path     cookie 路径
     * @param domain   cookie 域
     */
    public static void removeCookies(HttpServletRequest request, HttpServletResponse response, String path, String domain) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setValue(null);
                if (StringUtils.isNotBlank(path)) {
                    cookie.setPath(path);
                }
                if (StringUtils.isNotBlank(domain)) {
                    cookie.setDomain(domain);
                }
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }
}
