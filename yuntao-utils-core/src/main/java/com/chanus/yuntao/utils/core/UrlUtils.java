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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Http 请求 URL 处理工具类
 *
 * @author Chanus
 * @since 1.0.0
 */
public class UrlUtils {
    private UrlUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 构建 http 请求 URL
     *
     * @param host    请求域名
     * @param path    请求路径
     * @param queries 请求参数
     * @return http 请求 URL
     * @throws UnsupportedEncodingException {@link URLEncoder} 转码失败时抛出异常
     * @since 1.4.5
     */
    public static String buildUrl(String host, String path, Map<String, Object> queries) throws UnsupportedEncodingException {
        StringBuilder sbUrl = new StringBuilder();
        sbUrl.append(host);
        if (StringUtils.isNotBlank(path)) {
            sbUrl.append(path);
        }

        if (MapUtils.isNotEmpty(queries)) {
            StringBuilder sbQuery = new StringBuilder();
            for (Map.Entry<String, Object> query : queries.entrySet()) {
                if (sbQuery.length() > 0) {
                    sbQuery.append(StringUtils.AMPERSAND);
                }

                if (StringUtils.isBlank(query.getKey()) && ObjectUtils.isNotEmpty(query.getValue())) {
                    sbQuery.append(URLEncoder.encode(query.getValue().toString(), CharsetUtils.UTF_8));
                }

                if (StringUtils.isNotBlank(query.getKey())) {
                    sbQuery.append(query.getKey());
                    if (ObjectUtils.isNotEmpty(query.getValue())) {
                        sbQuery.append(StringUtils.EQUAL);
                        sbQuery.append(URLEncoder.encode(query.getValue().toString(), CharsetUtils.UTF_8));
                    }
                }
            }
            if (sbQuery.length() > 0) {
                sbUrl.append(StringUtils.QUESTION_MARK).append(sbQuery);
            }
        }

        return sbUrl.toString();
    }

    /**
     * 解析 http 请求 URI 获取请求参数，转换为 Map 键值对
     *
     * @param query     http 请求 URI
     * @param duplicate 重复参数名的参数值之间的连接符，连接后的字符串作为该参数的参数值，若为 null，则不允许重复参数名出现，靠后的参数值会覆盖掉靠前的参数值
     * @return 请求参数 {@code Map} 集合
     */
    public static Map<String, String> getParamsMap(String query, String duplicate) {
        if (StringUtils.isBlank(query) || !query.contains(StringUtils.EQUAL)) {
            return null;
        }

        Map<String, String> params = new HashMap<>(8);
        if (query.contains(StringUtils.AMPERSAND)) {
            // 多个参数
            String[] kvs = query.split(StringUtils.AMPERSAND);
            String[] kv;
            for (String p : kvs) {
                if (StringUtils.isBlank(p)) {
                    continue;
                }
                kv = p.split(StringUtils.EQUAL);
                if (kv.length != 2 || StringUtils.isBlank(kv[0]) || StringUtils.isBlank(kv[1])) {
                    continue;
                }
                if (params.containsKey(kv[0])) {
                    params.put(kv[0], StringUtils.isBlank(duplicate) ? kv[1] : (params.get(kv[0]) + duplicate + kv[1]));
                } else {
                    params.put(kv[0], kv[1]);
                }
            }
        } else {// 一个参数
            String[] kv = query.split(StringUtils.EQUAL);
            if (kv.length == 2 && StringUtils.isNotBlank(kv[0]) && StringUtils.isNotBlank(kv[1])) {
                params.put(kv[0], kv[1]);
            }
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
        if (MapUtils.isEmpty(params)) {
            return null;
        }

        StringBuilder uri = new StringBuilder();
        for (String key : params.keySet()) {
            if (params.get(key) == null) {
                continue;
            }

            try {
                uri.append(key).append(StringUtils.EQUAL).append(URLEncoder.encode(params.get(key).toString(), CharsetUtils.UTF_8)).append(StringUtils.AMPERSAND);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return uri.substring(0, uri.length() - 1);
    }

    /**
     * 将 Map 集合转换成 http 请求 URI，生成的 URI 中的参数名会按字典序排序
     *
     * @param params     Map 集合
     * @param ignoreKeys 转换成 http 请求 URI 时需要忽略的参数名
     * @return http 请求 URI
     * @since 1.4.4
     */
    public static String getParamsUri(Map<String, Object> params, final String... ignoreKeys) {
        if (MapUtils.isEmpty(params)) {
            return null;
        }

        return getParamsUri(MapUtils.removeAny(MapUtils.sort(params), ignoreKeys));
    }

    /**
     * 获取 http 请求 URL 中指定参数的值
     *
     * @param url       http 请求 URL
     * @param paramName 参数名
     * @return 返回参数名 {@code paramName} 对应的值
     */
    public static String getParamValue(String url, String paramName) {
        String paramValue = null;
        int tempIndex = url.indexOf(StringUtils.QUESTION_MARK);
        int paramIndex;
        if (tempIndex == -1) {
            // 没有 ?
            paramIndex = url.indexOf(paramName + StringUtils.EQUAL);
        } else {
            // 有 ?
            paramIndex = url.indexOf(paramName + StringUtils.EQUAL, tempIndex + 1);
        }

        if (paramIndex != -1) {
            // 有 = 有参数
            tempIndex = url.indexOf(StringUtils.AMPERSAND, paramIndex + paramName.length() + 1);
            if (tempIndex != -1) {
                paramValue = url.substring(paramIndex + paramName.length() + 1, tempIndex);
            } else {
                paramValue = url.substring(paramIndex + paramName.length() + 1);
            }
        }

        return paramValue;
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
        try {
            paramValue = URLEncoder.encode(paramValue, CharsetUtils.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int tempIndex = url.indexOf(StringUtils.QUESTION_MARK);
        if (tempIndex != -1) {
            // url 中已带有参数
            int paramIndex = url.indexOf(paramName + StringUtils.EQUAL, tempIndex + 1);
            if (paramIndex != -1) {
                // url 中已存在要追加的参数
                tempIndex = url.indexOf(StringUtils.AMPERSAND, paramIndex + paramName.length() + 1);
                if (tempIndex != -1) {
                    return url.substring(0, paramIndex) + paramName + StringUtils.EQUAL + paramValue + url.substring(tempIndex);
                }
                return url.substring(0, paramIndex) + paramName + StringUtils.EQUAL + paramValue;
            } else {
                // url 中不存在要追加的参数
                if (url.lastIndexOf(StringUtils.AMPERSAND) == url.length() - 1) {
                    // url 以 & 结尾
                    return url + paramName + StringUtils.EQUAL + paramValue;
                }
                // url 不以 & 结尾
                return url + StringUtils.AMPERSAND + paramName + StringUtils.EQUAL + paramValue;
            }
        } else {
            // url 中不带参数
            return url + StringUtils.QUESTION_MARK + paramName + StringUtils.EQUAL + paramValue;
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
        if (null == uri) {
            return url;
        }

        int tempIndex = url.indexOf(StringUtils.QUESTION_MARK);
        if (tempIndex != -1) {
            // url 中已带有参数
            if (url.lastIndexOf(StringUtils.AMPERSAND) == url.length() - 1) {
                // url 以 & 结尾
                return url + uri;
            }
            // url 不以 & 结尾
            return url + StringUtils.AMPERSAND + uri;
        } else {
            // url 中不带参数
            return url + StringUtils.QUESTION_MARK + uri;
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
        int tempIndex = url.indexOf(StringUtils.QUESTION_MARK);
        if (tempIndex != -1) {
            int paramIndex = url.indexOf(paramName + StringUtils.EQUAL, tempIndex + 1);
            if (paramIndex != -1) {
                tempIndex = url.indexOf(StringUtils.AMPERSAND, paramIndex + paramName.length() + 1);
                if (tempIndex != -1) {
                    return url.substring(0, paramIndex) + url.substring(tempIndex + 1);
                }
                return url.substring(0, paramIndex - 1);

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
