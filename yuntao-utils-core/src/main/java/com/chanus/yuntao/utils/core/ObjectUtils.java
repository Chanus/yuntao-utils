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

import java.lang.reflect.Array;
import java.util.*;

/**
 * 对象工具类
 *
 * @author Chanus
 * @date 2020-06-24 09:14:43
 * @since 1.0.0
 */
public class ObjectUtils {
    /**
     * 比较两个对象是否相等，相同的条件有两个，满足其一即可：
     * <ol>
     *     <li>obj1 == null &amp;&amp; obj2 == null</li>
     *     <li>obj1.equals(obj2)</li>
     * </ol>
     *
     * @param obj1 对象1
     * @param obj2 对象2
     * @return {@code true} 相等，{@code false} 不相等
     * @see Objects#equals(Object, Object)
     */
    public static boolean equal(Object obj1, Object obj2) {
        return Objects.equals(obj1, obj2);
    }

    /**
     * 比较两个对象是否不相等
     *
     * @param obj1 对象1
     * @param obj2 对象2
     * @return {@code true} 不相等，{@code false} 相等
     */
    public static boolean notEqual(Object obj1, Object obj2) {
        return !equal(obj1, obj2);
    }

    /**
     * 计算对象长度，如果是字符串调用其 length 函数，集合类调用其 size 函数，数组调用其 length 属性，其他可遍历对象遍历计算长度<br>
     * 支持的类型包括：
     * <ul>
     *     <li>CharSequence</li>
     *     <li>Map</li>
     *     <li>Iterator</li>
     *     <li>Enumeration</li>
     *     <li>Array</li>
     * </ul>
     *
     * @param obj 被计算长度的对象
     * @return 对象长度
     */
    public static int length(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length();
        }
        if (obj instanceof Collection) {
            return ((Collection<?>) obj).size();
        }
        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).size();
        }

        int count;
        if (obj instanceof Iterator) {
            Iterator<?> iter = (Iterator<?>) obj;
            count = 0;
            while (iter.hasNext()) {
                count++;
                iter.next();
            }
            return count;
        }
        if (obj instanceof Enumeration) {
            Enumeration<?> enumeration = (Enumeration<?>) obj;
            count = 0;
            while (enumeration.hasMoreElements()) {
                count++;
                enumeration.nextElement();
            }
            return count;
        }
        if (obj.getClass().isArray()) {
            return Array.getLength(obj);
        }
        return -1;
    }

    /**
     * 对象中是否包含元素，支持的对象类型包括：
     * <ul>
     *     <li>String</li>
     *     <li>Collection</li>
     *     <li>Map</li>
     *     <li>Iterator</li>
     *     <li>Enumeration</li>
     *     <li>Array</li>
     * </ul>
     *
     * @param obj     对象
     * @param element 元素
     * @return {@code true} 对象中包含元素，{@code false} 对象中不包含元素
     */
    public static boolean contains(Object obj, Object element) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof String) {
            if (element == null) {
                return false;
            }
            return ((String) obj).contains(element.toString());
        }
        if (obj instanceof Collection) {
            return ((Collection<?>) obj).contains(element);
        }
        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).containsValue(element);
        }

        if (obj instanceof Iterator) {
            Iterator<?> iter = (Iterator<?>) obj;
            while (iter.hasNext()) {
                Object o = iter.next();
                if (equal(o, element)) {
                    return true;
                }
            }
            return false;
        }
        if (obj instanceof Enumeration) {
            Enumeration<?> enumeration = (Enumeration<?>) obj;
            while (enumeration.hasMoreElements()) {
                Object o = enumeration.nextElement();
                if (equal(o, element)) {
                    return true;
                }
            }
            return false;
        }
        if (obj.getClass().isArray()) {
            int len = Array.getLength(obj);
            for (int i = 0; i < len; i++) {
                Object o = Array.get(obj, i);
                if (equal(o, element)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断指定对象是否为空，支持：
     *
     * <ul>
     *     <li>Optional</li>
     *     <li>CharSequence</li>
     *     <li>Array</li>
     *     <li>Collection</li>
     *     <li>Map</li>
     * </ul>
     *
     * @param obj 被判断的对象
     * @return {@code true} 对象为空，{@code false} 对象不为空，或者类型不支持
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object obj) {
        if (obj == null)
            return true;

        if (obj instanceof Optional) {
            return !((Optional) obj).isPresent();
        } else if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        } else if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        } else if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        } else if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }

        return false;
    }

    /**
     * 判断指定对象是否为非空，支持：
     *
     * <ul>
     *     <li>Optional</li>
     *     <li>CharSequence</li>
     *     <li>Array</li>
     *     <li>Collection</li>
     *     <li>Map</li>
     * </ul>
     *
     * @param obj 被判断的对象
     * @return {@code true} 对象不为空，或者类型不支持，{@code false} 对象为空
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * 如果给定对象为 {@code null} 返回默认值
     *
     * <pre>
     *     ObjectUtil.defaultIfNull(null, null) = null
     *     ObjectUtil.defaultIfNull(null, "") = ""
     *     ObjectUtil.defaultIfNull(null, "zz") = "zz"
     *     ObjectUtil.defaultIfNull("abc", *) = "abc"
     *     ObjectUtil.defaultIfNull(Boolean.TRUE, *) = Boolean.TRUE
     * </pre>
     *
     * @param <T>          对象类型
     * @param object       被检查对象，可能为 {@code null}
     * @param defaultValue 被检查对象为 {@code null} 返回的默认值，可以为 {@code null}
     * @return 被检查对象为 {@code null} 返回默认值，否则返回原值
     */
    public static <T> T defaultIfNull(final T object, final T defaultValue) {
        return object != null ? object : defaultValue;
    }

    /**
     * 如果给定对象为 {@code null} 或者 "" 返回默认值
     *
     * <pre>
     *     ObjectUtil.defaultIfEmpty(null, null)      = null
     *     ObjectUtil.defaultIfEmpty(null, "")        = ""
     *     ObjectUtil.defaultIfEmpty("", "zz")      = "zz"
     *     ObjectUtil.defaultIfEmpty(" ", "zz")      = " "
     *     ObjectUtil.defaultIfEmpty("abc", *)        = "abc"
     * </pre>
     *
     * @param <T>          对象类型（必须实现CharSequence接口）
     * @param str          被检查对象，可能为{@code null}
     * @param defaultValue 被检查对象为{@code null}或者 ""返回的默认值，可以为{@code null}或者 ""
     * @return 被检查对象为{@code null}或者 ""返回默认值，否则返回原值
     */
    public static <T extends CharSequence> T defaultIfEmpty(final T str, final T defaultValue) {
        return StringUtils.isEmpty(str) ? defaultValue : str;
    }

    /**
     * 如果给定对象为 {@code null} 或者 "" 或者空白符返回默认值
     *
     * <pre>
     *     ObjectUtil.defaultIfEmpty(null, null) = null
     *     ObjectUtil.defaultIfEmpty(null, "") = ""
     *     ObjectUtil.defaultIfEmpty("", "zz") = "zz"
     *     ObjectUtil.defaultIfEmpty(" ", "zz") = "zz"
     *     ObjectUtil.defaultIfEmpty("abc", *) = "abc"
     * </pre>
     *
     * @param <T>          对象类型（必须实现 CharSequence 接口）
     * @param str          被检查对象，可能为 {@code null}
     * @param defaultValue 被检查对象为 {@code null} 或者 "" 或者空白符返回的默认值，可以为 {@code null} 或者 "" 或者空白符
     * @return 被检查对象为 {@code null} 或者 "" 或者空白符返回默认值，否则返回原值
     */
    public static <T extends CharSequence> T defaultIfBlank(final T str, final T defaultValue) {
        return StringUtils.isBlank(str) ? defaultValue : str;
    }
}
