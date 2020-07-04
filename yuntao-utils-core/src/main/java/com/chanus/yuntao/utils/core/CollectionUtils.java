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

import java.util.*;

/**
 * 集合工具类
 *
 * @author Chanus
 * @date 2020-06-20 11:44:05
 * @since 1.0.0
 */
public class CollectionUtils {
    /**
     * 判断 Collection 集合是否为空
     *
     * @param collection Collection 集合
     * @return {@code true} 集合为空；{@code false} 集合不为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断 Map 集合是否为空
     *
     * @param map Map 集合
     * @return {@code true} Map 集合为空；{@code false} Map 集合不为空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * 判断数组是否为空
     *
     * @param objects 数组
     * @return {@code true} 数组为空；{@code false} 数组不为空
     */
    public static boolean isEmpty(Object[] objects) {
        return objects == null || objects.length == 0;
    }

    /**
     * 将 {@code map} 按 {@code key} 进行排序，按 ASCII 码从小到大排序
     *
     * @param map 源 Map 集合
     * @return 排序后的 Map 集合
     */
    public static Map<String, Object> sortMapByKey(Map<String, Object> map) {
        if (isEmpty(map))
            return null;

        return new TreeMap<String, Object>(Comparator.naturalOrder()) {
            private static final long serialVersionUID = -4667640843828612221L;

            {
                putAll(map);
            }
        };
    }

    /**
     * 判断数组是否包含某元素
     *
     * @param objects 数据数组
     * @param object  数组元素
     * @return {@code true} 数组包含指定元素；{@code false} 数组不包含指定元素
     */
    public static boolean contains(Object[] objects, Object object) {
        if (isEmpty(objects))
            return false;

        for (Object o : objects) {
            if ((o == null && object == null) || (o != null && o.equals(object))) {
                return true;
            }
        }

        return false;
    }

    /**
     * 将集合以指定连接符连接并以字符串的形式返回
     *
     * @param collection 集合
     * @param separator  连接符
     * @return 返回以 {@code separator} 连接后的字符串
     */
    public static String join(Collection<?> collection, String separator) {
        if (isEmpty(collection))
            return null;

        StringBuilder sb = new StringBuilder();
        for (Object o : collection) {
            if (o != null && !"".equals(o.toString()))
                sb.append(o.toString()).append(separator);
        }

        return sb.substring(0, sb.length() - 1);
    }

    /**
     * 将 Map 转换成指定字符连接和分割的字符串
     *
     * @param map       Map 集合数据
     * @param link      key 和 value 之间的连接符，如"="
     * @param separator 每组 key 和 value 之间的分割符，如"&"
     * @return {@code link} 与 {@code separator} 连接和分割的字符串
     */
    public static String join(Map<?, ?> map, String link, String separator) {
        if (isEmpty(map))
            return null;

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            sb.append(entry.getKey()).append(link).append(entry.getValue()).append(separator);
        }

        return sb.substring(0, sb.length() - separator.length());
    }

    /**
     * 将 Map 转换成"="连接和"&"分割的字符串
     *
     * @param map Map 集合数据
     * @return key1=value1&key2=value2...格式字符串
     */
    public static String join(Map<?, ?> map) {
        return join(map, "=", "&");
    }

    /**
     * 将 Map 的 key 以指定字符连接并以字符串的形式返回
     *
     * @param map       Map集合数据
     * @param separator 连接符
     * @return {@code separator} 连接的字符串
     */
    public static String keyJoin(Map<?, ?> map, String separator) {
        if (isEmpty(map))
            return null;

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            sb.append(entry.getKey()).append(separator);
        }

        return sb.substring(0, sb.length() - separator.length());
    }

    /**
     * 将 Map 的 value 以指定字符连接并以字符串的形式返回
     *
     * @param map       Map集合数据
     * @param separator 连接符
     * @return {@code separator} 连接的字符串
     */
    public static String valueJoin(Map<?, ?> map, String separator) {
        if (isEmpty(map))
            return null;

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            sb.append(entry.getValue()).append(separator);
        }

        return sb.substring(0, sb.length() - separator.length());
    }

    /**
     * 将数组以指定字符连接并以字符串的形式返回，排除空元素
     *
     * @param array     数据数组
     * @param separator 分割符
     * @return 以 {@code separator} 连接的字符串
     */
    public static String join(Object[] array, String separator) {
        if (isEmpty(array))
            return null;

        StringBuilder sb = new StringBuilder();
        for (Object object : array) {
            if (object != null && !"".equals(object))
                sb.append(object.toString()).append(separator);
        }

        return sb.substring(0, sb.length() - separator.length());
    }

    /**
     * 将数组以逗号连接并以字符串的形式返回
     *
     * @param array 数据数组
     * @return value1, value2...格式字符串
     */
    public static String join(Object[] array) {
        return join(array, ",");
    }

    /**
     * 数组去重
     *
     * @param array 数据数组
     * @return 去重后的数据数组
     */
    public static Object[] uniqueArray(Object[] array) {
        return isEmpty(array) ? null : new TreeSet<>(Arrays.asList(array)).toArray();
    }

    /**
     * 数组去重，并转化为以指定字符连接的字符串
     *
     * @param array     数据数组
     * @param separator 分割符
     * @return 以 {@code separator} 连接的字符串
     */
    public static String uniqueArray2String(Object[] array, String separator) {
        return isEmpty(array) ? null : join(new TreeSet<>(Arrays.asList(array)).toArray(), separator);
    }

    /**
     * 数组去重，并转化为以逗号连接的字符串
     *
     * @param array 数据数组
     * @return 以逗号连接的字符串
     */
    public static String uniqueArray2String(Object[] array) {
        return uniqueArray2String(array, ",");
    }
}
