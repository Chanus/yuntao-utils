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

import com.chanus.yuntao.utils.core.codec.Base64;
import com.chanus.yuntao.utils.core.function.Validator;
import com.chanus.yuntao.utils.core.reflect.ReflectUtils;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static boolean equals(Object obj1, Object obj2) {
        return Objects.equals(obj1, obj2);
    }

    /**
     * 比较两个对象是否不相等
     *
     * @param obj1 对象1
     * @param obj2 对象2
     * @return {@code true} 不相等，{@code false} 相等
     */
    public static boolean notEquals(Object obj1, Object obj2) {
        return !equals(obj1, obj2);
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
                if (equals(o, element)) {
                    return true;
                }
            }
            return false;
        }
        if (obj instanceof Enumeration) {
            Enumeration<?> enumeration = (Enumeration<?>) obj;
            while (enumeration.hasMoreElements()) {
                Object o = enumeration.nextElement();
                if (equals(o, element)) {
                    return true;
                }
            }
            return false;
        }
        if (obj.getClass().isArray()) {
            int len = Array.getLength(obj);
            for (int i = 0; i < len; i++) {
                Object o = Array.get(obj, i);
                if (equals(o, element)) {
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
     * 判断一个或多个对象是否全都为 {@code null} 或空对象
     *
     * @param objects 一个或多个对象
     * @return {@code true} 对象都为 {@code null} 或空对象，{@code false} 对象不都为 {@code null} 或空对象
     * @since 1.2.6
     */
    public static boolean isAllEmpty(Object... objects) {
        if (ArrayUtils.isNotEmpty(objects)) {
            for (Object object : objects) {
                if (isNotEmpty(object))
                    return false;
            }
        }
        return true;
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
     * 判断一个或多个对象是否存在 {@code null} 或空对象
     *
     * @param objects 一个或多个对象
     * @return {@code true} 存在 {@code null} 或空对象，{@code false} 不存在 {@code null} 或空对象
     * @since 1.2.6
     */
    public static boolean hasEmpty(Object... objects) {
        if (ArrayUtils.isEmpty(objects))
            return true;

        for (Object object : objects) {
            if (isEmpty(object))
                return true;
        }
        return false;
    }

    /**
     * 判断一个或多个对象是否全都不为 {@code null} 或空对象
     *
     * @param objects 一个或多个对象
     * @return {@code true} 对象都不为 {@code null} 或空对象，{@code false} 存在 {@code null} 或空对象
     * @since 1.2.6
     */
    public static boolean isAllNotEmpty(Object... objects) {
        return !hasEmpty(objects);
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
     * 校验对象是否满足条件
     *
     * @param <T>       对象类型
     * @param object    被检查对象
     * @param validator 验证器
     * @return {@code true} 校验通过；{@code false} 校验不通过
     * @since 1.6.0
     */
    public static <T> boolean verify(final T object, Validator<T> validator) {
        return validator.verify(object);
    }

    /**
     * 校验对象是否满足条件，满足则返回原值，否则返回默认值
     *
     * @param <T>          对象类型
     * @param object       被检查对象
     * @param defaultValue 默认值
     * @param validator    验证器
     * @return 被检查对象校验不通过返回默认值，否则返回原值
     * @since 1.6.0
     */
    public static <T> T verify(final T object, final T defaultValue, Validator<T> validator) {
        return validator.verify(object) ? object : defaultValue;
    }

    /**
     * 对象转字符串
     *
     * @param value 对象
     * @return {@link String}
     * @since 1.6.0
     */
    public static String toString(Object value) {
        return value == null ? null : value.toString();
    }

    /**
     * 对象转 {@link Character}
     *
     * @param value 对象
     * @return {@link Character}
     * @since 1.6.0
     */
    public static Character toChar(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Character) {
            return (Character) value;
        } else if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0) {
                return null;
            } else if (strVal.length() == 1) {
                return strVal.charAt(0);
            } else {
                throw new RuntimeException("can not cast to char, value : " + value);
            }
        } else {
            throw new RuntimeException("can not cast to char, value : " + value);
        }
    }

    /**
     * 对象转 {@link Boolean}
     *
     * @param value 对象
     * @return {@link Boolean}
     * @since 1.6.0
     */
    public static Boolean toBoolean(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof BigDecimal) {
            BigDecimal decimal = (BigDecimal) value;
            int scale = decimal.scale();
            return (scale >= -100 && scale <= 100 ? decimal.intValue() : decimal.intValueExact()) == 1;
        } else if (value instanceof Number) {
            return ((Number) value).intValue() == 1;
        } else if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0 || "null".equalsIgnoreCase(strVal))
                return null;

            if (!"true".equalsIgnoreCase(strVal) && !"1".equals(strVal)) {
                if (!"false".equalsIgnoreCase(strVal) && !"0".equals(strVal)) {
                    if (!"Y".equalsIgnoreCase(strVal) && !"T".equals(strVal)) {
                        if (!"F".equalsIgnoreCase(strVal) && !"N".equals(strVal)) {
                            throw new RuntimeException("can not cast to boolean, value : " + value);
                        } else {
                            return Boolean.FALSE;
                        }
                    } else {
                        return Boolean.TRUE;
                    }
                } else {
                    return Boolean.FALSE;
                }
            } else {
                return Boolean.TRUE;
            }
        } else {
            throw new RuntimeException("can not cast to boolean, value : " + value);
        }
    }

    /**
     * 对象转 {@link Byte}
     *
     * @param value 对象
     * @return {@link Byte}
     * @since 1.6.0
     */
    public static Byte toByte(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof BigDecimal) {
            BigDecimal decimal = (BigDecimal) value;
            int scale = decimal.scale();
            return (scale >= -100 && scale <= 100 ? decimal.byteValue() : decimal.byteValueExact());
        } else if (value instanceof Number) {
            return ((Number) value).byteValue();
        } else if (value instanceof String) {
            String strVal = (String) value;
            return strVal.length() == 0 || "null".equalsIgnoreCase(strVal) ? null : Byte.parseByte(strVal);
        } else if (value instanceof Boolean) {
            return (byte) ((Boolean) value ? 1 : 0);
        } else {
            throw new RuntimeException("can not cast to byte, value : " + value);
        }
    }

    /**
     * 对象转 {@link Short}
     *
     * @param value 对象
     * @return {@link Short}
     * @since 1.6.0
     */
    public static Short toShort(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof BigDecimal) {
            BigDecimal decimal = (BigDecimal) value;
            int scale = decimal.scale();
            return scale >= -100 && scale <= 100 ? decimal.shortValue() : decimal.shortValueExact();
        } else if (value instanceof Number) {
            return ((Number) value).shortValue();
        } else if (value instanceof String) {
            String strVal = (String) value;
            return strVal.length() == 0 || "null".equalsIgnoreCase(strVal) ? null : Short.parseShort(strVal);
        } else if (value instanceof Boolean) {
            return (short) ((Boolean) value ? 1 : 0);
        } else {
            throw new RuntimeException("can not cast to short, value : " + value);
        }
    }

    /**
     * 对象转 {@link BigDecimal}
     *
     * @param value 对象
     * @return {@link BigDecimal}
     * @since 1.6.0
     */
    public static BigDecimal toBigDecimal(Object value) {
        if (value == null) {
            return null;
        } else {
            if (value instanceof Float) {
                if (Float.isNaN((Float) value) || Float.isInfinite((Float) value))
                    return null;
            } else if (value instanceof Double) {
                if (Double.isNaN((Double) value) || Double.isInfinite((Double) value))
                    return null;
            } else {
                if (value instanceof BigDecimal)
                    return (BigDecimal) value;

                if (value instanceof BigInteger)
                    return new BigDecimal((BigInteger) value);
            }

            String strVal = value.toString();
            if (strVal.length() == 0 || "null".equalsIgnoreCase(strVal))
                return null;

            if (strVal.indexOf(44) != -1)
                strVal = strVal.replaceAll(StringUtils.COMMA, StringUtils.EMPTY);

            if (strVal.length() > 65535) {
                throw new RuntimeException("decimal overflow");
            } else {
                return new BigDecimal(strVal);
            }
        }
    }

    /**
     * 对象转 {@link BigInteger}
     *
     * @param value 对象
     * @return {@link BigInteger}
     * @since 1.6.0
     */
    public static BigInteger toBigInteger(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Float) {
            float floatValue = (Float) value;
            return !Float.isNaN(floatValue) && !Float.isInfinite(floatValue) ? BigInteger.valueOf((long) floatValue) : null;
        } else if (value instanceof Double) {
            double doubleValue = (Double) value;
            return !Double.isNaN(doubleValue) && !Double.isInfinite(doubleValue) ? BigInteger.valueOf((long) doubleValue) : null;
        } else if (value instanceof BigInteger) {
            return (BigInteger) value;
        } else {
            if (value instanceof BigDecimal) {
                BigDecimal decimal = (BigDecimal) value;
                int scale = decimal.scale();
                if (scale > -1000 && scale < 1000) {
                    return ((BigDecimal) value).toBigInteger();
                }
            }

            String strVal = value.toString();
            if (strVal.length() == 0 || "null".equalsIgnoreCase(strVal))
                return null;

            if (strVal.indexOf(44) != -1)
                strVal = strVal.replaceAll(StringUtils.COMMA, StringUtils.EMPTY);

            if (strVal.length() > 65535) {
                throw new RuntimeException("decimal overflow");
            } else {
                return new BigInteger(strVal);
            }
        }
    }

    /**
     * 对象转 {@link Integer}
     *
     * @param value 对象
     * @return {@link Integer}
     * @since 1.6.0
     */
    public static Integer toInteger(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof BigDecimal) {
            BigDecimal decimal = (BigDecimal) value;
            int scale = decimal.scale();
            return scale >= -100 && scale <= 100 ? decimal.intValue() : decimal.intValueExact();
        } else if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0 || "null".equalsIgnoreCase(strVal))
                return null;

            if (strVal.indexOf(44) != -1)
                strVal = strVal.replaceAll(StringUtils.COMMA, StringUtils.EMPTY);

            Matcher matcher = Pattern.compile("\\.0*$").matcher(strVal);
            if (matcher.find()) {
                strVal = matcher.replaceAll("");
            }

            return Integer.parseInt(strVal);
        } else if (value instanceof Boolean) {
            return (Boolean) value ? 1 : 0;
        } else {
            throw new RuntimeException("can not cast to int, value : " + value);
        }
    }

    /**
     * 对象转 {@link Long}
     *
     * @param value 对象
     * @return {@link Long}
     * @since 1.6.0
     */
    public static Long toLong(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof BigDecimal) {
            BigDecimal decimal = (BigDecimal) value;
            int scale = decimal.scale();
            return scale >= -100 && scale <= 100 ? decimal.longValue() : decimal.longValueExact();
        } else if (value instanceof Number) {
            return ((Number) value).longValue();
        } else if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0 || "null".equalsIgnoreCase(strVal)) {
                return null;
            }

            if (strVal.indexOf(44) != -1)
                strVal = strVal.replaceAll(StringUtils.COMMA, StringUtils.EMPTY);

            return Long.parseLong(strVal);
        } else if (value instanceof Boolean) {
            return (Boolean) value ? 1L : 0L;
        } else {
            throw new RuntimeException("can not cast to long, value : " + value);
        }
    }

    /**
     * 对象转 {@link Float}
     *
     * @param value 对象
     * @return {@link Float}
     * @since 1.6.0
     */
    public static Float toFloat(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Number) {
            return ((Number) value).floatValue();
        } else if (value instanceof String) {
            String strVal = value.toString();
            if (strVal.length() == 0 || "null".equalsIgnoreCase(strVal))
                return null;

            if (strVal.indexOf(44) != -1)
                strVal = strVal.replaceAll(StringUtils.COMMA, StringUtils.EMPTY);

            return Float.parseFloat(strVal);
        } else if (value instanceof Boolean) {
            return (Boolean) value ? 1.0F : 0.0F;
        } else {
            throw new RuntimeException("can not cast to float, value : " + value);
        }
    }

    /**
     * 对象转 {@link Double}
     *
     * @param value 对象
     * @return {@link Double}
     * @since 1.6.0
     */
    public static Double toDouble(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Number) {
            return ((Number) value).doubleValue();
        } else if (value instanceof String) {
            String strVal = value.toString();
            if (strVal.length() == 0 || "null".equalsIgnoreCase(strVal))
                return null;

            if (strVal.indexOf(44) != -1)
                strVal = strVal.replaceAll(StringUtils.COMMA, StringUtils.EMPTY);

            return Double.parseDouble(strVal);
        } else if (value instanceof Boolean) {
            return (Boolean) value ? 1.0D : 0.0D;
        } else {
            throw new RuntimeException("can not cast to double, value : " + value);
        }
    }

    /**
     * 对象转字节数组
     *
     * @param value 对象
     * @return 字节数组
     * @since 1.6.0
     */
    public static byte[] toBytes(Object value) {
        if (value instanceof byte[]) {
            return (byte[]) value;
        } else if (value instanceof String) {
            return Base64.decode((String) value);
        } else {
            throw new RuntimeException("can not cast to byte[], value : " + value);
        }
    }

    /**
     * 对象转 Map，不包含空属性
     *
     * @param object 待转换的对象
     * @param <T>    对象类型
     * @return 对象转换后的 Map
     * @since 1.1.0
     */
    public static <T> Map<String, Object> toMap(T object) {
        Map<String, Object> map = new HashMap<>();
        Object o;
        for (Field field : object.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                o = field.get(object);
                if (o != null) {
                    map.put(field.getName(), o);
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 对象转 Map，包含空属性
     *
     * @param object 待转换的对象
     * @param <T>    对象类型
     * @return 对象转换后的 Map
     * @since 1.1.0
     */
    public static <T> Map<String, Object> toMapWithNull(T object) {
        Map<String, Object> map = new HashMap<>();
        for (Field field : object.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                map.put(field.getName(), field.get(object));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * Map 转对象
     *
     * @param map   待转换的 Map
     * @param clazz 对象 class
     * @param <T>   对象类型
     * @return Map 转换后的对象
     * @since 1.1.0
     */
    public static <T> T mapToObject(Map<String, Object> map, Class<T> clazz) {
        if (MapUtils.isEmpty(map))
            return null;

        T object = null;
        try {
            object = clazz.newInstance();
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                field.set(object, map.get(field.getName()));
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * 克隆对象<br>
     * 如果对象实现 {@link Cloneable} 接口，调用其 clone 方法<br>
     * 如果实现 {@link java.io.Serializable} 接口，执行深度克隆<br>
     * 否则返回 {@code null}
     *
     * @param <T> 对象类型
     * @param obj 被克隆的对象
     * @return 克隆后的对象
     * @since 1.2.6
     */
    public static <T> T clone(T obj) {
        T result = ArrayUtils.clone(obj);
        if (result == null) {
            if (obj instanceof Cloneable) {
                result = ReflectUtils.invoke(obj, "clone");
            } else {
                result = cloneByStream(obj);
            }
        }
        return result;
    }

    /**
     * 克隆对象，如果克隆失败，则返回原对象
     *
     * @param <T> 对象类型
     * @param obj 被克隆的对象
     * @return 克隆后的对象或原对象
     * @since 1.2.6
     */
    public static <T> T cloneIfPossible(final T obj) {
        T clone = null;
        try {
            clone = clone(obj);
        } catch (Exception ignored) {

        }
        return clone == null ? obj : clone;
    }

    /**
     * 克隆对象，通过字节流序列化实现深度克隆，需要克隆的对象必须实现 {@link java.io.Serializable} 接口
     *
     * @param <T> 对象类型
     * @param obj 被克隆的对象
     * @return 克隆后的对象
     * @since 1.2.6
     */
    @SuppressWarnings("unchecked")
    public static <T> T cloneByStream(T obj) {
        if (!(obj instanceof Serializable))
            return null;

        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(obj);
            out.flush();

            return (T) new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray())).readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.close(out, bos);
        }
    }
}
