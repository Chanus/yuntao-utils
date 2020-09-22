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

import com.chanus.yuntao.utils.core.lang.Filter;

import java.lang.reflect.Array;
import java.util.*;

/**
 * 数组工具类
 *
 * @author Chanus
 * @date 2020-07-27 15:40:21
 * @since 1.1.0
 */
public class ArrayUtils {
    /**
     * 判断数组是否为空
     *
     * @param array 数组
     * @param <T>   数组元素类型
     * @return {@code true} 数组为空；{@code false} 数组不为空
     */
    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断数组对象是否为空，如果此对象为非数组，理解为此对象为数组的第一个元素，则返回 {@code false}
     *
     * @param array 数组对象
     * @return {@code true} 数组对象为空；{@code false} 数组对象不为空
     */
    public static boolean isEmpty(Object array) {
        if (array == null)
            return true;

        return isArray(array) && Array.getLength(array) == 0;
    }

    /**
     * 判断数组是否不为空
     *
     * @param array 数组
     * @param <T>   数组元素类型
     * @return {@code true} 数组不为空；{@code false} 数组为空
     */
    public static <T> boolean isNotEmpty(T[] array) {
        return array != null && array.length > 0;
    }

    /**
     * 判断数组对象是否不为空，如果此对象为非数组，理解为此对象为数组的第一个元素，则返回 {@code true}
     *
     * @param array 数组对象
     * @return {@code true} 数组对象不为空；{@code false} 数组对象为空
     */
    public static boolean isNotEmpty(Object array) {
        return !isEmpty(array);
    }

    /**
     * 如果给定数组为空，返回默认数组
     *
     * @param array        数组
     * @param defaultArray 默认数组
     * @param <T>          数组元素类型
     * @return 非空的原数组或默认数组
     */
    public static <T> T[] defaultIfEmpty(T[] array, T[] defaultArray) {
        return isEmpty(array) ? defaultArray : array;
    }

    /**
     * 如果给定数组对象为空，返回默认数组对象
     *
     * @param array        数组对象
     * @param defaultArray 默认数组对象
     * @return 非空的原数组对象或默认数组对象
     */
    public static Object defaultIfEmpty(Object array, Object defaultArray) {
        return isEmpty(array) ? defaultArray : array;
    }

    /**
     * 判断对象是否为数组对象
     *
     * @param obj 对象
     * @return {@code true} 是数组对象；{@code false} 不是数组对象，如果对象为 {@code null} 返回 {@code false}
     */
    public static boolean isArray(Object obj) {
        return obj != null && obj.getClass().isArray();
    }

    /**
     * 获取数组对象中指定位置的值，支持负数，例如-1表示倒数第一个值<br>
     * 如果数组下标越界，返回 null
     *
     * @param array 数组对象
     * @param index 下标，支持负数
     * @param <T>   数组元素类型
     * @return 数组对象中指定位置的值
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(Object array, int index) {
        if (array == null)
            return null;

        if (index < 0)
            index += Array.getLength(array);

        try {
            return (T) Array.get(array, index);
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * 获取元素在数组中的位置
     *
     * @param array   数组
     * @param element 元素
     * @param <T>     数组元素类型
     * @return 元素在数组中的位置，数组为空或没找到元素则返回-1
     */
    public static <T> int indexOf(T[] array, T element) {
        if (array == null)
            return -1;

        for (int i = 0; i < array.length; i++) {
            if (Objects.equals(array[i], element))
                return i;
        }
        return -1;
    }

    /**
     * 将新元素添加到已有数组中，添加新元素会生成一个新的数组，不影响原数组
     *
     * @param array       已有数组
     * @param newElements 新元素
     * @param <T>         数组元素类型
     * @return 新数组
     */
    @SafeVarargs
    public static <T> T[] append(T[] array, T... newElements) {
        if (isEmpty(array))
            return newElements;

        return insert(array, array.length, newElements);
    }

    /**
     * 将新元素添加到已有数组中，添加新元素会生成一个新的数组，不影响原数组
     *
     * @param array       已有数组
     * @param newElements 新元素
     * @param <T>         数组元素类型
     * @return 新数组
     */
    @SafeVarargs
    public static <T> Object append(Object array, T... newElements) {
        if (isEmpty(array))
            return newElements;

        return insert(array, Array.getLength(array), newElements);
    }

    /**
     * 将新元素插入到到已有数组中的某个位置<br>
     * 添加新元素会生成一个新的数组，不影响原数组<br>
     * 如果插入位置为为负数，从原数组从后向前计数，若大于原数组长度，则空白处用 null 填充
     *
     * @param array       已有数组
     * @param index       插入位置，此位置为对应此位置元素之前的空档
     * @param newElements 新元素
     * @param <T>         数组元素类型
     * @return 新数组
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] insert(T[] array, int index, T... newElements) {
        return (T[]) insert((Object) array, index, newElements);
    }

    /**
     * 将新元素插入到到已有数组中的某个位置<br>
     * 添加新元素会生成一个新的数组，不影响原数组<br>
     * 如果插入位置为为负数，从原数组从后向前计数，若大于原数组长度，则空白处用 null 填充
     *
     * @param array       已有数组
     * @param index       插入位置，此位置为对应此位置元素之前的空档
     * @param newElements 新元素
     * @param <T>         数组元素类型
     * @return 新数组
     */
    @SuppressWarnings({"unchecked", "SuspiciousSystemArraycopy"})
    public static <T> Object insert(Object array, int index, T... newElements) {
        if (isEmpty(newElements))
            return array;

        if (isEmpty(array))
            return newElements;

        final int len = Array.getLength(array);
        if (index < 0)
            index = (index % len) + len;

        final T[] result = (T[]) Array.newInstance(array.getClass().getComponentType(), Math.max(len, index) + newElements.length);
        System.arraycopy(array, 0, result, 0, Math.min(len, index));
        System.arraycopy(newElements, 0, result, index, newElements.length);
        if (index < len)
            System.arraycopy(array, index, result, index + newElements.length, len - index);

        return result;
    }

    /**
     * 移除数组中对应位置的元素
     *
     * @param array 数组
     * @param index 位置，如果位置小于0或者大于数组长度，返回原数组
     * @param <T>   数组元素类型
     * @return 去掉指定元素后的新数组或原数组
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] remove(T[] array, int index) {
        return (T[]) remove((Object) array, index);
    }

    /**
     * 移除数组对象中对应位置的元素
     *
     * @param array 数组对象
     * @param index 位置，如果位置小于0或者大于数组长度，返回原数组对象
     * @return 去掉指定元素后的新数组对象或原数组对象
     */
    @SuppressWarnings("SuspiciousSystemArraycopy")
    public static Object remove(Object array, int index) {
        if (array == null)
            return null;

        if (!isArray(array))
            throw new IllegalArgumentException("Object must be array!");

        int length = Array.getLength(array);
        if (index < 0 || index >= length)
            return array;

        final Object result = Array.newInstance(array.getClass().getComponentType(), length - 1);
        System.arraycopy(array, 0, result, 0, index);
        if (index < length - 1) {
            System.arraycopy(array, index + 1, result, index, length - index - 1);
        }

        return result;
    }

    /**
     * 数组中是否包含元素
     *
     * @param array 数组
     * @param value 被检查的元素
     * @param <T>   数组元素类型
     * @return {@code true} 包含；{@code false} 不包含
     */
    public static <T> boolean contains(T[] array, T value) {
        if (array == null)
            return false;

        for (T t : array) {
            if (Objects.equals(t, value))
                return true;
        }
        return false;
    }

    /**
     * 交换数组中两个位置的元素
     *
     * @param array  数组
     * @param index1 位置1
     * @param index2 位置2
     * @param <T>    元素类型
     * @return 交换位置后的数组，与传入数组为同一对象
     */
    public static <T> T[] swap(T[] array, int index1, int index2) {
        if (isEmpty(array)) {
            throw new IllegalArgumentException("Array must not empty!");
        }
        T tmp = array[index1];
        array[index1] = array[index2];
        array[index2] = tmp;
        return array;
    }

    /**
     * 交换数组中两个位置的元素
     *
     * @param array  数组对象
     * @param index1 位置1
     * @param index2 位置2
     * @return 交换位置后的数组，与传入数组为同一对象
     */
    public static Object swap(Object array, int index1, int index2) {
        if (isEmpty(array)) {
            throw new IllegalArgumentException("Array must not empty!");
        }
        Object tmp = get(array, index1);
        Array.set(array, index1, Array.get(array, index2));
        Array.set(array, index2, tmp);
        return array;
    }

    /**
     * 反转数组，会变更原数组
     *
     * @param <T>                 数组元素类型
     * @param array               数组，会变更
     * @param startIndexInclusive 开始位置（包含）
     * @param endIndexExclusive   结束位置（不包含）
     * @return 变更后的原数组
     * @since 3.0.9
     */
    public static <T> T[] reverse(final T[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (isEmpty(array)) {
            return array;
        }
        int i = Math.max(startIndexInclusive, 0);
        int j = Math.min(array.length, endIndexExclusive) - 1;
        T tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
        return array;
    }

    /**
     * 反转数组，会变更原数组
     *
     * @param <T>   数组元素类型
     * @param array 数组，会变更
     * @return 变更后的原数组
     * @since 3.0.9
     */
    public static <T> T[] reverse(final T[] array) {
        return reverse(array, 0, array.length);
    }

    /**
     * 去重数组中的元素，去重后生成新的数组，原数组不变
     *
     * @param array 数组
     * @param <T>   数组元素类型
     * @return 去重后的数组
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] distinct(T[] array) {
        if (isEmpty(array))
            return array;

        final Set<T> set = new LinkedHashSet<>(array.length, 1);
        Collections.addAll(set, array);
        return set.toArray((T[]) Array.newInstance(array.getClass().getComponentType(), 0));
    }

    /**
     * 过滤数据
     *
     * @param <T>    数组元素类型
     * @param array  数组
     * @param filter 过滤器接口，用于定义过滤规则，null 表示不过滤，返回原数组
     * @return 过滤后的数组
     * @since 1.2.5
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] filter(T[] array, Filter<T> filter) {
        if (filter == null)
            return array;

        final ArrayList<T> list = new ArrayList<>(array.length);
        for (T t : array) {
            if (filter.accept(t)) {
                list.add(t);
            }
        }

        return list.toArray((T[]) Array.newInstance(array.getClass().getComponentType(), list.size()));
    }

    /**
     * 数组或集合转字符串
     *
     * @param obj 数组或集合对象
     * @return 字符串
     */
    public static String toString(Object obj) {
        if (obj == null)
            return null;

        if (obj instanceof long[]) {
            return Arrays.toString((long[]) obj);
        } else if (obj instanceof int[]) {
            return Arrays.toString((int[]) obj);
        } else if (obj instanceof short[]) {
            return Arrays.toString((short[]) obj);
        } else if (obj instanceof char[]) {
            return Arrays.toString((char[]) obj);
        } else if (obj instanceof byte[]) {
            return Arrays.toString((byte[]) obj);
        } else if (obj instanceof boolean[]) {
            return Arrays.toString((boolean[]) obj);
        } else if (obj instanceof float[]) {
            return Arrays.toString((float[]) obj);
        } else if (obj instanceof double[]) {
            return Arrays.toString((double[]) obj);
        } else if (isArray(obj)) {
            // 对象数组
            try {
                return Arrays.deepToString((Object[]) obj);
            } catch (Exception ignore) {
                //ignore
            }
        }

        return obj.toString();
    }
}
