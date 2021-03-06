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
package com.chanus.yuntao.utils.core.reflect;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基本变量类型的枚举，包括原始类型和包装类型
 *
 * @author Chanus
 * @date 2020-09-17 15:20:14
 * @since 1.2.5
 */
public enum BasicType {
    BYTE, SHORT, INT, INTEGER, LONG, DOUBLE, FLOAT, BOOLEAN, CHAR, CHARACTER, STRING;

    /**
     * 包装类型为 Key，原始类型为 Value，例如： Integer.class =》 int.class.
     */
    public static final Map<Class<?>, Class<?>> wrapperPrimitiveMap = new ConcurrentHashMap<>(8);
    /**
     * 原始类型为 Key，包装类型为 Value，例如： int.class =》 Integer.class.
     */
    public static final Map<Class<?>, Class<?>> primitiveWrapperMap = new ConcurrentHashMap<>(8);

    static {
        wrapperPrimitiveMap.put(Boolean.class, boolean.class);
        wrapperPrimitiveMap.put(Byte.class, byte.class);
        wrapperPrimitiveMap.put(Character.class, char.class);
        wrapperPrimitiveMap.put(Double.class, double.class);
        wrapperPrimitiveMap.put(Float.class, float.class);
        wrapperPrimitiveMap.put(Integer.class, int.class);
        wrapperPrimitiveMap.put(Long.class, long.class);
        wrapperPrimitiveMap.put(Short.class, short.class);

        for (Map.Entry<Class<?>, Class<?>> entry : wrapperPrimitiveMap.entrySet()) {
            primitiveWrapperMap.put(entry.getValue(), entry.getKey());
        }
    }

    /**
     * 原始类转为包装类，非原始类返回原类
     *
     * @param clazz 原始类
     * @return 包装类
     */
    public static Class<?> wrap(Class<?> clazz) {
        if (null == clazz || !clazz.isPrimitive())
            return clazz;

        Class<?> result = primitiveWrapperMap.get(clazz);
        return (null == result) ? clazz : result;
    }

    /**
     * 包装类转为原始类，非包装类返回原类
     *
     * @param clazz 包装类
     * @return 原始类
     */
    public static Class<?> unWrap(Class<?> clazz) {
        if (null == clazz || clazz.isPrimitive())
            return clazz;

        Class<?> result = wrapperPrimitiveMap.get(clazz);
        return (null == result) ? clazz : result;
    }
}
