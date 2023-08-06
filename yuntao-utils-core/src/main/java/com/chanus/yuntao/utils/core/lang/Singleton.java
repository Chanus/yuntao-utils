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

import com.chanus.yuntao.utils.core.*;
import com.chanus.yuntao.utils.core.reflect.ClassUtils;
import com.chanus.yuntao.utils.core.reflect.ReflectUtils;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * 单例类<br>
 * 提供单例对象的统一管理，当调用 get 方法时，如果对象池中存在此对象，返回此对象，否则创建新对象返回
 *
 * @author Chanus
 * @since 1.2.5
 */
public final class Singleton {
    private static final SimpleCache<String, Object> POOL = new SimpleCache<>(new HashMap<>());

    private Singleton() {
    }

    /**
     * 获得指定类的单例对象<br>
     * 对象存在于池中返回，否则创建，每次调用此方法获得的对象为同一个对象<br>
     * 注意：单例针对的是类和参数，也就是说只有类、参数一致才会返回同一个对象
     *
     * @param <T>    单例对象类型
     * @param clazz  类
     * @param params 构造方法参数
     * @return 单例对象
     */
    public static <T> T get(Class<T> clazz, Object... params) {
        final String key = buildKey(clazz.getName(), params);
        return get(key, () -> ReflectUtils.newInstance(clazz, params));
    }

    /**
     * 获得指定类的单例对象<br>
     * 对象存在于池中返回，否则创建，每次调用此方法获得的对象为同一个对象<br>
     * 注意：单例针对的是类和参数，也就是说只有类、参数一致才会返回同一个对象
     *
     * @param <T>      单例对象类型
     * @param key      自定义键
     * @param supplier 单例对象的创建函数
     * @return 单例对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key, Supplier<T> supplier) {
        return (T) POOL.get(key, supplier::get);
    }

    /**
     * 获得指定类的单例对象
     *
     * @param <T>       单例对象类型
     * @param className 类名
     * @param params    构造参数
     * @return 单例对象
     */
    public static <T> T get(String className, Object... params) {
        final Class<T> clazz = ClassUtils.loadClass(className);
        return get(clazz, params);
    }

    /**
     * 将已有对象放入单例中，对象名做为键
     *
     * @param obj 对象
     */
    public static void put(Object obj) {
        put(obj.getClass().getName(), obj);
    }

    /**
     * 将已有对象放入单例中
     *
     * @param key 键
     * @param obj 对象
     */
    public static void put(String key, Object obj) {
        POOL.put(key, obj);
    }

    /**
     * 移除指定 Singleton 对象
     *
     * @param clazz 类
     */
    public static void remove(Class<?> clazz) {
        if (clazz != null) {
            remove(clazz.getName());
        }
    }

    /**
     * 移除指定 Singleton 对象
     *
     * @param key 键
     */
    public static void remove(String key) {
        POOL.remove(key);
    }

    /**
     * 清除所有 Singleton 对象
     */
    public static void destroy() {
        POOL.clear();
    }

    /**
     * 构建 key
     *
     * @param className 类名
     * @param params    参数列表
     * @return key
     */
    private static String buildKey(String className, Object... params) {
        if (ArrayUtils.isEmpty(params)) {
            return className;
        }

        return className + "#" + ArrayUtils.joinIgnoreNull(params, StringUtils.UNDERSCORE);
    }
}
