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
package com.chanus.yuntao.utils.core.map;

import com.chanus.yuntao.utils.core.StringUtils;

import java.util.HashMap;

/**
 * 自定义 Map，继承 HashMap
 *
 * @author Chanus
 * @date 2020-09-30 15:00:00
 * @since 1.2.6
 */
public class CustomMap extends HashMap<String, Object> {
    private static final long serialVersionUID = -8758604201563163293L;

    /**
     * 构造
     */
    public CustomMap() {
        super();
    }

    /**
     * 构造
     *
     * @param initialCapacity 初始容量
     */
    public CustomMap(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * 构造
     *
     * @param key   键
     * @param value 值
     */
    public CustomMap(String key, Object value) {
        this.put(key, value);
    }

    /**
     * 创建 {@code CustomMap} 对象
     *
     * @return {@link CustomMap}
     */
    public static CustomMap create() {
        return new CustomMap();
    }

    /**
     * 创建 {@code CustomMap} 对象
     *
     * @param key   键
     * @param value 值
     * @return {@link CustomMap}
     */
    public static CustomMap create(String key, Object value) {
        return new CustomMap(key, value);
    }

    /**
     * 存储数据并返回当前对象
     *
     * @param key   键
     * @param value 值
     * @return 当前 {@code CustomMap} 对象
     */
    public CustomMap putNext(String key, Object value) {
        this.put(key, value);
        return this;
    }

    /**
     * 存储数据并返回当前对象，忽略 {@code null} 值
     *
     * @param key   键
     * @param value 值，若为 {@code null} 则忽略
     * @return 当前 {@code CustomMap} 对象
     * @since 1.4.6
     */
    public CustomMap putNextIgnoreNull(String key, Object value) {
        if (value != null)
            this.put(key, value);

        return this;
    }

    /**
     * 存储数据并返回当前对象，忽略 {@code null} 和 "" 值
     *
     * @param key   键
     * @param value 值，若为 {@code null} 或 "" 则忽略
     * @return 当前 {@code CustomMap} 对象
     * @since 1.4.6
     */
    public CustomMap putNextIgnoreEmpty(String key, CharSequence value) {
        if (StringUtils.isNotEmpty(value))
            this.put(key, value);

        return this;
    }

    /**
     * 存储数据并返回当前对象，忽略空或空白值
     *
     * @param key   键
     * @param value 值，若为 {@code null} 或 "" 或不可见字符则忽略
     * @return 当前 {@code CustomMap} 对象
     * @since 1.4.6
     */
    public CustomMap putNextIgnoreBlank(String key, CharSequence value) {
        if (StringUtils.isNotBlank(value))
            this.put(key, value);

        return this;
    }
}
