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

import com.chanus.yuntao.utils.core.CollectionUtils;
import com.chanus.yuntao.utils.core.ObjectUtils;

import java.io.Serializable;
import java.util.*;

/**
 * 可重复键和值的 Map<br>
 * 通过键值单独建立 List 方式，使键值对一一对应，实现正向和反向两种查找<br>
 * 无论是正向还是反向，都是遍历列表查找过程，相比标准的 HashMap 要慢，数据越多越慢
 *
 * @param <K> 键类型
 * @param <V> 值类型
 * @author Chanus
 * @since 1.2.5
 */
public class TableMap<K, V> implements Map<K, V>, Iterable<Map.Entry<K, V>>, Serializable {
    private static final long serialVersionUID = 2573244288331995249L;

    private final List<K> keys;
    private final List<V> values;

    /**
     * 构造
     */
    public TableMap() {
        this.keys = new ArrayList<>();
        this.values = new ArrayList<>();
    }

    /**
     * 构造
     *
     * @param size 初始容量
     */
    public TableMap(int size) {
        this.keys = new ArrayList<>(size);
        this.values = new ArrayList<>(size);
    }

    /**
     * 构造
     *
     * @param keys   键列表
     * @param values 值列表
     */
    public TableMap(K[] keys, V[] values) {
        this.keys = Arrays.asList(keys);
        this.values = Arrays.asList(values);
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return new Iterator<Map.Entry<K, V>>() {
            private final Iterator<K> keysIter = keys.iterator();
            private final Iterator<V> valuesIter = values.iterator();

            @Override
            public boolean hasNext() {
                return keysIter.hasNext() && valuesIter.hasNext();
            }

            @Override
            public Map.Entry<K, V> next() {
                return new Entry<>(keysIter.next(), valuesIter.next());
            }

            @Override
            public void remove() {
                keysIter.remove();
                valuesIter.remove();
            }
        };
    }

    @Override
    public int size() {
        return keys.size();
    }

    @Override
    public boolean isEmpty() {
        return CollectionUtils.isEmpty(keys);
    }

    @Override
    public boolean containsKey(Object key) {
        //noinspection SuspiciousMethodCalls
        return keys.contains(key);
    }

    @Override
    public boolean containsValue(Object value) {
        //noinspection SuspiciousMethodCalls
        return values.contains(value);
    }

    @Override
    public V get(Object key) {
        //noinspection SuspiciousMethodCalls
        final int index = keys.indexOf(key);
        if (index > -1 && index < values.size()) {
            return values.get(index);
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        keys.add(key);
        values.add(value);
        return null;
    }

    @Override
    public V remove(Object key) {
        //noinspection SuspiciousMethodCalls
        int index = keys.indexOf(key);
        if (index > -1) {
            keys.remove(index);
            if (index < values.size()) {
                values.remove(index);
            }
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        keys.clear();
        values.clear();
    }

    @Override
    public Set<K> keySet() {
        return new HashSet<>(keys);
    }

    @Override
    public Collection<V> values() {
        return Collections.unmodifiableList(this.values);
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        final Set<Map.Entry<K, V>> hashSet = new LinkedHashSet<>();
        for (int i = 0; i < size(); i++) {
            hashSet.add(new Entry<>(keys.get(i), values.get(i)));
        }
        return hashSet;
    }

    /**
     * 根据 value 获得对应的 key，只返回找到的第一个 value 对应的 key 值
     *
     * @param value 值
     * @return 键
     */
    public K getKey(V value) {
        final int index = values.indexOf(value);
        if (index > -1 && index < keys.size()) {
            return keys.get(index);
        }
        return null;
    }

    /**
     * 获取指定 key 对应的所有值
     *
     * @param key 键
     * @return 值列表
     */
    public List<V> getValues(K key) {
        return CollectionUtils.getAny(
                this.values,
                CollectionUtils.indexOfAll(this.keys, (ele) -> ObjectUtils.equals(ele, key))
        );
    }

    /**
     * 获取指定 value 对应的所有 key
     *
     * @param value 值
     * @return 键列表
     */
    public List<K> getKeys(V value) {
        return CollectionUtils.getAny(
                this.keys,
                CollectionUtils.indexOfAll(this.values, (ele) -> ObjectUtils.equals(ele, value))
        );
    }

    @Override
    public String toString() {
        return "TableMap{" +
                "keys=" + keys +
                ", values=" + values +
                '}';
    }

    private static class Entry<K, V> implements Map.Entry<K, V> {

        private final K key;
        private final V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            throw new UnsupportedOperationException("setValue not supported.");
        }

        @Override
        public final boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o instanceof Map.Entry) {
                Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
                return Objects.equals(key, e.getKey()) &&
                        Objects.equals(value, e.getValue());
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }
    }
}
