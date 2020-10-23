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

import java.util.HashMap;
import java.util.Map;

/**
 * Http 请求 URL 处理工具类
 *
 * @author Chanus
 * @date 2020-06-24 09:01:33
 * @since 1.0.0
 */
public class UrlUtils {
    /**
     * 解析 http 请求 URI 获取请求参数，转换为 Map 键值对
     *
     * @param query     http 请求 URI
     * @param duplicate 重复参数名的参数值之间的连接符，连接后的字符串作为该参数的参数值，若为 null，则不允许重复参数名出现，靠后的参数值会覆盖掉靠前的参数值
     * @return 请求参数 {@code Map} 集合
     */
    public static Map<String, String> getParamsMap(String query, String duplicate) {
        if (StringUtils.isBlank(query) || !query.contains("="))
            return null;

        Map<String, String> params = new HashMap<>();
        if (query.contains("&")) {// 多个参数
            String[] kvs = query.split("&");
            String[] kv;
            for (String p : kvs) {
                if (StringUtils.isBlank(p))
                    continue;
                kv = p.split("=");
                if (kv.length != 2 || StringUtils.isBlank(kv[0]) || StringUtils.isBlank(kv[1]))
                    continue;
                if (params.containsKey(kv[0])) {
                    params.put(kv[0], StringUtils.isBlank(duplicate) ? kv[1] : (params.get(kv[0]) + duplicate + kv[1]));
                } else {
                    params.put(kv[0], kv[1]);
                }
            }
        } else {// 一个参数
            String[] kv = query.split("=");
            if (kv.length == 2 && StringUtils.isNotBlank(kv[0]) && StringUtils.isNotBlank(kv[1]))
                params.put(kv[0], kv[1]);
        }

        return params;
    }

    /**
     * 将 Map 集合转换成 http 请求 URI
     *
     * @param params Map 集合
     * @return http 请求 URI
     */
    public static String getParamsUri(Map<String, Object> params) {
        if (MapUtils.isEmpty(params))
            return null;

        StringBuilder uri = new StringBuilder();
        for (String key : params.keySet()) {
            if (params.get(key) == null)
                continue;

            uri.append(key).append("=").append(params.get(key).toString()).append("&");
        }

        return uri.substring(0, uri.length() - 1);
    }

    /**
     * 获取 http 请求 URL 中指定参数的值
     *
     * @param url       http 请求 URL
     * @param paramName 参数名
     * @return 返回参数名 {@code paramName} 对应的值
     */
    public static String getParamValue(String url, String paramName) {
        int temp_index = url.indexOf("?");
        if (temp_index != -1) {
            int param_index = url.indexOf(paramName + "=", temp_index + 1);
            if (param_index != -1) {
                temp_index = url.indexOf("&", param_index + paramName.length() + 1);
                if (temp_index != -1) {
                    return url.substring(param_index + paramName.length() + 1, temp_index);
                }
                return url.substring(param_index + paramName.length() + 1);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 向 http 请求 URL 中追加参数
     *
     * @param url        http 请求 URL
     * @param paramName  参数名
     * @param paramValue 参数值
     * @return 返回追加参数后的 URL
     */
    public static String setParam(String url, String paramName, String paramValue) {
        int temp_index = url.indexOf("?");
        if (temp_index != -1) {// url 中已带有参数
            int param_index = url.indexOf(paramName + "=", temp_index + 1);
            if (param_index != -1) {// url 中已存在要追加的参数
                temp_index = url.indexOf("&", param_index + paramName.length() + 1);
                if (temp_index != -1) {
                    return url.substring(0, param_index) + paramName + "=" + paramValue + url.substring(temp_index);
                }
                return url.substring(0, param_index) + paramName + "=" + paramValue;
            } else {// url 中不存在要追加的参数
                // url 以 & 结尾
                if (url.lastIndexOf("&") == url.length() - 1) {
                    return url + paramName + "=" + paramValue;
                }
                // url 不以 & 结尾
                return url + "&" + paramName + "=" + paramValue;
            }
        } else {// url 中不带参数
            return url + "?" + paramName + "=" + paramValue;
        }
    }

    /**
     * 向 http 请求 URL 中追加参数
     *
     * @param url    http 请求 URL
     * @param params 参数名和参数值 Map
     * @return 返回追加参数后的 URL
     */
    public static String setParam(String url, Map<String, Object> params) {
        String uri = getParamsUri(params);
        if (null == uri)
            return url;

        int temp_index = url.indexOf("?");
        if (temp_index != -1) {// url 中已带有参数
            // url 以 & 结尾
            if (url.lastIndexOf("&") == url.length() - 1) {
                return url + uri;
            }
            // url 不以 & 结尾
            return url + "&" + uri;
        } else {// url 中不带参数
            return url + "?" + uri;
        }
    }

    /**
     * 移除 http 请求 URL 中的参数
     *
     * @param url       http 请求 URL
     * @param paramName 参数名
     * @return 返回移除参数 {@code paramName} 后的 URL
     */
    public static String removeParam(String url, String paramName) {
        int temp_index = url.indexOf("?");
        if (temp_index != -1) {
            int param_index = url.indexOf(paramName + "=", temp_index + 1);
            if (param_index != -1) {
                temp_index = url.indexOf("&", param_index + paramName.length() + 1);
                if (temp_index != -1) {
                    return url.substring(0, param_index) + url.substring(temp_index + 1);
                }
                return url.substring(0, param_index - 1);

            } else {
                return url;
            }
        } else {
            return url;
        }
    }

    /**
     * 批量移除 http 请求 URL 中的参数
     *
     * @param url        http 请求 URL
     * @param paramNames 参数名数组
     * @return 返回移除参数 {@code paramNames} 后的URL
     */
    public static String removeParam(String url, String... paramNames) {
        for (String paramName : paramNames) {
            url = removeParam(url, paramName);
        }
        return url;
    }
}
