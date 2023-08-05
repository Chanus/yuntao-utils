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

import com.chanus.yuntao.utils.core.function.Filter;

import java.util.*;

/**
 * Map 工具类
 *
 * @author Chanus
 * @date 2020-09-30 15:22:51
 * @since 1.2.6
 */
public class MapUtils {
    private MapUtils() {
        throw new IllegalStateException("Utility class");
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
     * 判断 Map 集合是否不为空
     *
     * @param map Map 集合
     * @return {@code true} Map 集合不为空；{@code false} Map 集合为空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return map != null && !map.isEmpty();
    }

    /**
     * 如果给定的 Map 集合为空，则返回默认 Map
     *
     * @param <T>        集合类型
     * @param <K>        键类型
     * @param <V>        值类型
     * @param map        Map 集合
     * @param defaultMap 默认 Map 集合
     * @return 非空的原 Map 集合或默认 Map 集合
     */
    public static <T extends Map<K, V>, K, V> T defaultIfEmpty(T map, T defaultMap) {
        return isEmpty(map) ? defaultMap : map;
    }

    /**
     * 将 Map 集合转成字符串
     *
     * @param <K>               键类型
     * @param <V>               值类型
     * @param map               Map 集合
     * @param separator         Map 集合元素之间的连接符
     * @param keyValueSeparator 键值之间的连接符
     * @param isIgnoreNull      是否忽略 {@code null} 的键和值
     * @return 连接后的字符串
     */
    public static <K, V> String join(Map<K, V> map, String separator, String keyValueSeparator, boolean isIgnoreNull) {
        if (isEmpty(map)) {
            return null;
        }

        final StringBuilder stringBuilder = new StringBuilder();
        boolean isFirst = true;
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (!isIgnoreNull || (entry.getKey() != null && entry.getValue() != null)) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    stringBuilder.append(separator);
                }
                stringBuilder.append(entry.getKey()).append(keyValueSeparator).append(entry.getValue());
            }
        }

        return stringBuilder.toString();
    }

    /**
     * 将 Map 集合转成字符串，忽略 {@code null} 的键和值
     *
     * @param <K>               键类型
     * @param <V>               值类型
     * @param map               Map 集合
     * @param separator         Map 集合元素之间的连接符
     * @param keyValueSeparator 键值之间的连接符
     * @return 连接后的字符串
     */
    public static <K, V> String joinIgnoreNull(Map<K, V> map, String separator, String keyValueSeparator) {
        return join(map, separator, keyValueSeparator, true);
    }

    /**
     * 将 Map 集合转成字符串，以"="连接和"&"分割，忽略 {@code null} 的键和值
     *
     * @param <K> 键类型
     * @param <V> 值类型
     * @param map Map 集合
     * @return 连接后的字符串
     * @since 1.4.0
     */
    public static <K, V> String joinDefaultIgnoreNull(Map<K, V> map) {
        return joinIgnoreNull(map, StringUtils.AMPERSAND, StringUtils.EQUAL);
    }

    /**
     * 将 Map 集合的 key 转成字符串
     *
     * @param <K>          键类型
     * @param <V>          值类型
     * @param map          Map 集合
     * @param separator    连接符
     * @param isIgnoreNull 是否忽略 {@code null} 的键和值
     * @return {@code separator} 连接的字符串
     * @since 1.4.0
     */
    public static <K, V> String keyJoin(Map<K, V> map, String separator, boolean isIgnoreNull) {
        if (isEmpty(map)) {
            return null;
        }

        final StringBuilder stringBuilder = new StringBuilder();
        boolean isFirst = true;
        for (K k : map.keySet()) {
            if (!isIgnoreNull || ObjectUtils.isNotEmpty(k)) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    stringBuilder.append(separator);
                }
                stringBuilder.append(k);
            }
        }

        return stringBuilder.toString();
    }

    /**
     * 将 Map 集合的 key 转成字符串，忽略 {@code null} 的 key
     *
     * @param <K>       键类型
     * @param <V>       值类型
     * @param map       Map 集合
     * @param separator 连接符
     * @return {@code separator} 连接的字符串
     * @since 1.4.0
     */
    public static <K, V> String keyJoinIgnoreNull(Map<K, V> map, String separator) {
        return keyJoin(map, separator, true);
    }

    /**
     * 将 Map 集合的 value 转成字符串
     *
     * @param <K>          键类型
     * @param <V>          值类型
     * @param map          Map 集合
     * @param separator    连接符
     * @param isIgnoreNull 是否忽略 {@code null} 的键和值
     * @return {@code separator} 连接的字符串
     * @since 1.4.0
     */
    public static <K, V> String valueJoin(Map<K, V> map, String separator, boolean isIgnoreNull) {
        if (isEmpty(map)) {
            return null;
        }

        final StringBuilder stringBuilder = new StringBuilder();
        boolean isFirst = true;
        for (V v : map.values()) {
            if (!isIgnoreNull || ObjectUtils.isNotEmpty(v)) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    stringBuilder.append(separator);
                }
                stringBuilder.append(v);
            }
        }

        return stringBuilder.toString();
    }

    /**
     * 将 Map 集合的 value 转成字符串，忽略 {@code null} 的 value
     *
     * @param <K>       键类型
     * @param <V>       值类型
     * @param map       Map 集合
     * @param separator 连接符
     * @return {@code separator} 连接的字符串
     * @since 1.4.0
     */
    public static <K, V> String valueJoinIgnoreNull(Map<K, V> map, String separator) {
        return valueJoin(map, separator, true);
    }

    /**
     * 过滤 Map 集合，通过传入的 {@link Filter} 实现来返回需要的元素内容
     *
     * @param <K>    键类型
     * @param <V>    值类型
     * @param map    Map 集合
     * @param filter 过滤器接口
     * @return 过滤的新 Map 集合，类型与原 Map 集合保持一致
     */
    public static <K, V> Map<K, V> filter(Map<K, V> map, Filter<Map.Entry<K, V>> filter) {
        if (map == null || filter == null) {
            return map;
        }

        final Map<K, V> map2 = ObjectUtils.clone(map);
        if (isEmpty(map2)) {
            return map2;
        }

        map2.clear();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (filter.accept(entry)) {
                map2.put(entry.getKey(), entry.getValue());
            }
        }
        return map2;
    }

    /**
     * 过滤 Map 集合，保留指定键值对，如果键不存在跳过
     *
     * @param <K>  键类型
     * @param <V>  值类型
     * @param map  Map 集合
     * @param keys 键列表
     * @return 过滤的新 Map 集合，类型与原 Map 集合保持一致
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> filter(Map<K, V> map, K... keys) {
        final Map<K, V> map2 = ObjectUtils.clone(map);
        if (isEmpty(map2)) {
            return map2;
        }

        map2.clear();
        for (K key : keys) {
            if (map.containsKey(key)) {
                map2.put(key, map.get(key));
            }
        }
        return map2;
    }

    /**
     * 排序 Map 集合，使用默认排序方式（字母顺序）
     *
     * @param <K> 键类型
     * @param <V> 值类型
     * @param map Map 集合
     * @return {@link TreeMap}
     */
    public static <K, V> TreeMap<K, V> sort(Map<K, V> map) {
        return sort(map, null);
    }

    /**
     * 排序 Map 集合
     *
     * @param <K>        键类型
     * @param <V>        值类型
     * @param map        Map 集合
     * @param comparator 比较器
     * @return {@link TreeMap}
     */
    public static <K, V> TreeMap<K, V> sort(Map<K, V> map, Comparator<? super K> comparator) {
        if (map == null) {
            return null;
        }

        TreeMap<K, V> result;
        if (map instanceof TreeMap) {
            // 已经是可排序 Map，此时只有比较器一致才返回原 map
            result = (TreeMap<K, V>) map;
            if (comparator == null || comparator.equals(result.comparator())) {
                return result;
            }
        } else {
            result = comparator == null ? new TreeMap<>() : new TreeMap<>(comparator);
            if (isNotEmpty(map)) {
                result.putAll(map);
            }
        }

        return result;
    }

    /**
     * 将 Map 集合转换为不可修改的 Map 集合
     *
     * @param map Map 集合
     * @param <K> 键类型
     * @param <V> 值类型
     * @return 不可修改的 Map 集合
     */
    public static <K, V> Map<K, V> unmodifiable(Map<K, V> map) {
        return Collections.unmodifiableMap(map);
    }

    /**
     * 获取 Map 集合的部分 key 生成新的 Map 集合
     *
     * @param <K>  键类型
     * @param <V>  值类型
     * @param map  Map 集合
     * @param keys 键列表
     * @return 包含指定的 key 的 Map 集合
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> getAny(Map<K, V> map, final K... keys) {
        return filter(map, entry -> ArrayUtils.contains(keys, entry.getKey()));
    }

    /**
     * 移除 Map 集合中指定 key 的键值对
     *
     * @param <K>  键类型
     * @param <V>  值类型
     * @param map  Map 集合
     * @param keys 键列表
     * @return 移除指定 key 的键值对后的 Map 集合
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> removeAny(Map<K, V> map, final K... keys) {
        for (K key : keys) {
            map.remove(key);
        }
        return map;
    }

    /**
     * 去除 Map 中值为 {@code null} 的键值对<br>
     * 注意：此方法在传入的 Map 上直接修改
     *
     * @param <K> key 的类型
     * @param <V> value 的类型
     * @param map Map
     * @return 去除值为 {@code null} 的键值对后的 Map
     */
    public static <K, V> Map<K, V> removeNullValue(Map<K, V> map) {
        if (isEmpty(map)) {
            return map;
        }

        final Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
        Map.Entry<K, V> entry;
        while (iterator.hasNext()) {
            entry = iterator.next();
            if (entry.getValue() == null) {
                iterator.remove();
            }
        }

        return map;
    }

    /**
     * 清除一个或多个 Map 集合内的元素，每个 Map 调用 clear() 方法
     *
     * @param maps 一个或多个 Map
     */
    public static void clear(Map<?, ?>... maps) {
        for (Map<?, ?> map : maps) {
            if (isNotEmpty(map)) {
                map.clear();
            }
        }
    }
}
