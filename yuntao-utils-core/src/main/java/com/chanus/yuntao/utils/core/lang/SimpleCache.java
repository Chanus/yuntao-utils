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
package com.chanus.yuntao.utils.core.lang;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

/**
 * 简单缓存，无超时实现，默认使用 {@link WeakHashMap} 实现缓存自动清理
 *
 * @param <K> 键类型
 * @param <V> 值类型
 * @author Chanus
 * @since 1.2.5
 */
public class SimpleCache<K, V> implements Iterable<Map.Entry<K, V>>, Serializable {
    private static final long serialVersionUID = 8286320920628923543L;

    /**
     * 缓存池
     */
    private final Map<K, V> cache;
    /**
     * 乐观读写锁
     */
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * 构造，默认使用 {@link WeakHashMap} 实现缓存自动清理
     */
    public SimpleCache() {
        this(new WeakHashMap<>());
    }

    /**
     * 构造
     * <p>
     * 通过自定义 Map 初始化，可以自定义缓存实现。<br>
     * 比如使用 {@link WeakHashMap} 则会自动清理 key，使用 HashMap 则不会清理<br>
     * 同时，传入的 Map 对象也可以自带初始化的键值对，防止在 get 时创建
     * </p>
     *
     * @param initMap 初始 Map，用于定义 Map 类型
     */
    public SimpleCache(Map<K, V> initMap) {
        this.cache = initMap;
    }

    /**
     * 从缓存池中查找值
     *
     * @param key 键
     * @return 值
     */
    public V get(K key) {
        lock.readLock().lock();
        try {
            return cache.get(key);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * 从缓存中获得对象，当对象不在缓存中或已经过期返回 {@link Supplier} 回调产生的对象
     *
     * @param key      键
     * @param supplier 如果不存在回调方法，用于生产值对象
     * @return 值对象
     */
    public V get(K key, Supplier<V> supplier) {
        V v = get(key);

        if (v == null && supplier != null) {
            lock.writeLock().lock();
            try {
                v = cache.get(key);
                // 双重检查，防止在竞争锁的过程中已经有其它线程写入
                if (v == null) {
                    try {
                        v = supplier.get();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    cache.put(key, v);
                }
            } finally {
                lock.writeLock().unlock();
            }
        }

        return v;
    }

    /**
     * 放入缓存
     *
     * @param key   键
     * @param value 值
     * @return 值
     */
    public V put(K key, V value) {
        // 独占写锁
        lock.writeLock().lock();
        try {
            cache.put(key, value);
        } finally {
            lock.writeLock().unlock();
        }
        return value;
    }

    /**
     * 移除缓存
     *
     * @param key 键
     * @return 移除的值
     */
    public V remove(K key) {
        // 独占写锁
        lock.writeLock().lock();
        try {
            return cache.remove(key);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 清空缓存池
     */
    public void clear() {
        // 独占写锁
        lock.writeLock().lock();
        try {
            this.cache.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return this.cache.entrySet().iterator();
    }
}
