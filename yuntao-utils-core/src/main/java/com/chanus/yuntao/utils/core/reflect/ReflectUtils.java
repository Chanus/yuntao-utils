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

import com.chanus.yuntao.utils.core.ArrayUtils;
import com.chanus.yuntao.utils.core.StringUtils;
import com.chanus.yuntao.utils.core.function.Filter;
import com.chanus.yuntao.utils.core.lang.SimpleCache;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 反射工具类
 *
 * @author Chanus
 * @date 2020-09-17 10:04:43
 * @since 1.2.5
 */
public class ReflectUtils {
    /**
     * 对象缓存
     */
    private static final SimpleCache<Class<?>, Constructor<?>[]> CONSTRUCTORS_CACHE = new SimpleCache<>();
    /**
     * 字段缓存
     */
    private static final SimpleCache<Class<?>, Field[]> FIELDS_CACHE = new SimpleCache<>();
    /**
     * 方法缓存
     */
    private static final SimpleCache<Class<?>, Method[]> METHODS_CACHE = new SimpleCache<>();

    /**
     * 查找类中的指定参数的构造方法，如果找到构造方法，会自动设置可访问为 true
     *
     * @param <T>            对象类型
     * @param clazz          类
     * @param parameterTypes 参数类型，只要任何一个参数是指定参数的父类或接口或相等即可，此参数可以不传
     * @return 构造方法，如果未找到返回 {@code null}
     */
    @SuppressWarnings("unchecked")
    public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... parameterTypes) {
        if (clazz == null)
            return null;

        final Constructor<?>[] constructors = getConstructors(clazz);
        Class<?>[] pts;
        for (Constructor<?> constructor : constructors) {
            pts = constructor.getParameterTypes();
            if (ClassUtils.isAllAssignableFrom(pts, parameterTypes)) {
                // 构造可访问
                setAccessible(constructor);
                return (Constructor<T>) constructor;
            }
        }
        return null;
    }

    /**
     * 获得一个类中所有构造列表
     *
     * @param <T>       构造的对象类型
     * @param beanClass 类
     * @return 字段列表
     * @throws SecurityException 安全检查异常
     */
    @SuppressWarnings("unchecked")
    public static <T> Constructor<T>[] getConstructors(Class<T> beanClass) throws SecurityException {
        Constructor<?>[] constructors = CONSTRUCTORS_CACHE.get(beanClass);
        if (constructors != null) {
            return (Constructor<T>[]) constructors;
        }

        constructors = getConstructorsDirectly(beanClass);
        return (Constructor<T>[]) CONSTRUCTORS_CACHE.put(beanClass, constructors);
    }

    /**
     * 获得一个类中所有字段列表，直接反射获取，无缓存
     *
     * @param beanClass 类
     * @return 字段列表
     * @throws SecurityException 安全检查异常
     */
    public static Constructor<?>[] getConstructorsDirectly(Class<?> beanClass) throws SecurityException {
        return beanClass.getDeclaredConstructors();
    }

    /**
     * 查找指定类中是否包含指定名称对应的字段，包括所有字段（包括非 public 字段），也包括父类和 Object 类的字段
     *
     * @param beanClass 被查找字段的类，不能为 null
     * @param name      字段名
     * @return {@code true} 包含字段；{@code false} 不包含字段
     * @throws SecurityException 安全异常
     */
    public static boolean hasField(Class<?> beanClass, String name) throws SecurityException {
        return getField(beanClass, name) != null;
    }

    /**
     * 获取字段名
     *
     * @param field 字段
     * @return 字段名
     */
    public static String getFieldName(Field field) {
        return field == null ? null : field.getName();
    }

    /**
     * 查找指定类中的所有字段（包括非 public 字段），也包括父类和 Object 类的字段，字段不存在则返回 {@code null}
     *
     * @param beanClass 被查找字段的类，不能为 null
     * @param fieldName 字段名
     * @return 字段
     * @throws SecurityException 安全异常
     */
    public static Field getField(Class<?> beanClass, String fieldName) throws SecurityException {
        final Field[] fields = getFields(beanClass);
        if (ArrayUtils.isNotEmpty(fields)) {
            for (Field field : fields) {
                if ((fieldName.equals(getFieldName(field)))) {
                    return field;
                }
            }
        }
        return null;
    }

    /**
     * 获取指定类中字段名和字段对应的 Map，包括其父类中的字段
     *
     * @param beanClass 类
     * @return 字段名和字段对应的 Map
     */
    public static Map<String, Field> getFieldMap(Class<?> beanClass) {
        final Field[] fields = getFields(beanClass);
        final HashMap<String, Field> map = new HashMap<>(fields.length);
        for (Field field : fields) {
            map.put(field.getName(), field);
        }
        return map;
    }

    /**
     * 获得一个类中所有字段列表，包括其父类中的字段
     *
     * @param beanClass 类
     * @return 字段列表
     * @throws SecurityException 安全检查异常
     */
    public static Field[] getFields(Class<?> beanClass) throws SecurityException {
        Field[] allFields = FIELDS_CACHE.get(beanClass);
        if (allFields != null) {
            return allFields;
        }

        allFields = getFieldsDirectly(beanClass, true);
        return FIELDS_CACHE.put(beanClass, allFields);
    }

    /**
     * 获得一个类中所有字段列表，直接反射获取，无缓存
     *
     * @param beanClass           类
     * @param withSuperClassFieds 是否包括父类的字段列表
     * @return 字段列表
     * @throws SecurityException 安全检查异常
     */
    public static Field[] getFieldsDirectly(Class<?> beanClass, boolean withSuperClassFieds) throws SecurityException {
        Field[] allFields = null;
        Class<?> searchType = beanClass;
        Field[] declaredFields;
        while (searchType != null) {
            declaredFields = searchType.getDeclaredFields();
            if (allFields == null) {
                allFields = declaredFields;
            } else {
                allFields = ArrayUtils.append(allFields, declaredFields);
            }
            searchType = withSuperClassFieds ? searchType.getSuperclass() : null;
        }

        return allFields;
    }

    /**
     * 获取字段值
     *
     * @param obj       对象，如果 static 字段，此处为类
     * @param fieldName 字段名
     * @return 字段值
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        if (obj == null || StringUtils.isBlank(fieldName))
            return null;

        return getFieldValue(obj, getField(obj instanceof Class ? (Class<?>) obj : obj.getClass(), fieldName));
    }

    /**
     * 获取静态字段值
     *
     * @param field 字段
     * @return 静态字段值
     */
    public static Object getStaticFieldValue(Field field) {
        return getFieldValue(null, field);
    }

    /**
     * 获取字段值
     *
     * @param obj   对象，static 字段则此字段为 null
     * @param field 字段
     * @return 字段值
     */
    public static Object getFieldValue(Object obj, Field field) {
        if (field == null)
            return null;

        if (obj instanceof Class) {
            // 静态字段获取时对象为 null
            obj = null;
        }

        setAccessible(field);
        Object result;
        try {
            result = field.get(obj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("IllegalAccess for " + field.getDeclaringClass() + "." + field.getName(), e);
        }
        return result;
    }

    /**
     * 获取所有字段的值
     *
     * @param obj bean 对象，如果是 static 字段，此处为类 class
     * @return 字段值数组
     */
    public static Object[] getFieldsValue(Object obj) {
        if (obj != null) {
            final Field[] fields = getFields(obj instanceof Class ? (Class<?>) obj : obj.getClass());
            if (fields != null) {
                final Object[] values = new Object[fields.length];
                for (int i = 0; i < fields.length; i++) {
                    values[i] = getFieldValue(obj, fields[i]);
                }
                return values;
            }
        }
        return null;
    }

    /**
     * 设置字段值
     *
     * @param obj       对象，static 字段则此处传 Class
     * @param fieldName 字段名
     * @param value     值，值类型必须与字段类型匹配，不会自动转换对象类型
     */
    public static void setFieldValue(Object obj, String fieldName, Object value) {
        final Field field = getField((obj instanceof Class) ? (Class<?>) obj : obj.getClass(), fieldName);
        if (field != null)
            setFieldValue(obj, field, value);
    }

    /**
     * 设置字段值
     *
     * @param obj   对象，如果是 static 字段，此参数为 null
     * @param field 字段
     * @param value 值，值类型必须与字段类型匹配，不会自动转换对象类型
     */
    public static void setFieldValue(Object obj, Field field, Object value) {
        final Class<?> fieldType = field.getType();
        if (value == null) {
            // 获取 null 对应默认值，防止原始类型造成空指针问题
            value = ClassUtils.getDefaultValue(fieldType);
        }

        setAccessible(field);
        try {
            field.set(obj instanceof Class ? null : obj, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("IllegalAccess for " + obj + "." + field.getName(), e);
        }
    }

    /**
     * 获得指定类本类及其父类中的 public 方法名，去重重载的方法
     *
     * @param clazz 类
     * @return 方法名 Set 集合
     */
    public static Set<String> getPublicMethodNames(Class<?> clazz) {
        final HashSet<String> methodSet = new HashSet<>();
        final Method[] methodArray = getPublicMethods(clazz);
        if (ArrayUtils.isNotEmpty(methodArray)) {
            for (Method method : methodArray) {
                methodSet.add(method.getName());
            }
        }
        return methodSet;
    }

    /**
     * 获得本类及其父类所有 public 方法
     *
     * @param clazz 类
     * @return 方法数组
     */
    public static Method[] getPublicMethods(Class<?> clazz) {
        return clazz == null ? null : clazz.getMethods();
    }

    /**
     * 获得指定类过滤后的 public 方法列表
     *
     * @param clazz  类
     * @param filter 过滤器
     * @return 过滤后的方法列表
     */
    public static List<Method> getPublicMethods(Class<?> clazz, Filter<Method> filter) {
        if (clazz == null)
            return null;

        final Method[] methods = getPublicMethods(clazz);
        List<Method> methodList;
        if (filter != null) {
            methodList = new ArrayList<>();
            for (Method method : methods) {
                if (filter.accept(method)) {
                    methodList.add(method);
                }
            }
        } else {
            methodList = Arrays.asList(methods);
        }
        return methodList;
    }

    /**
     * 获得指定类过滤后的 public 方法列表
     *
     * @param clazz          类
     * @param excludeMethods 不包括的方法
     * @return 过滤后的方法列表
     */
    public static List<Method> getPublicMethods(Class<?> clazz, Method... excludeMethods) {
        final HashSet<Method> excludeMethodSet = new HashSet<>(Arrays.asList(excludeMethods));
        return getPublicMethods(clazz, method -> !excludeMethodSet.contains(method));
    }

    /**
     * 获得指定类过滤后的 public 方法列表
     *
     * @param clazz              类
     * @param excludeMethodNames 不包括的方法名列表
     * @return 过滤后的方法列表
     */
    public static List<Method> getPublicMethods(Class<?> clazz, String... excludeMethodNames) {
        final HashSet<String> excludeMethodNameSet = new HashSet<>(Arrays.asList(excludeMethodNames));
        return getPublicMethods(clazz, method -> !excludeMethodNameSet.contains(method.getName()));
    }

    /**
     * 查找指定 public 方法，如果找不到对应的方法或方法不为 public 的则返回 {@code null}
     *
     * @param clazz      类
     * @param methodName 方法名
     * @param paramTypes 参数类型
     * @return 方法
     * @throws SecurityException 无权访问抛出异常
     */
    public static Method getPublicMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) throws SecurityException {
        try {
            return clazz.getMethod(methodName, paramTypes);
        } catch (NoSuchMethodException ex) {
            return null;
        }
    }

    /**
     * 查找指定对象中的所有方法（包括非 public 方法），也包括父对象和 Object 类的方法<br>
     * 此方法为精准获取方法名，即方法名和参数数量和类型必须一致，否则返回 {@code null}
     *
     * @param obj        被查找的对象，如果为 {@code null} 返回 {@code null}
     * @param methodName 方法名，如果为空字符串则返回 {@code null}
     * @param args       参数
     * @return 方法
     * @throws SecurityException 无访问权限抛出异常
     */
    public static Method getMethodOfObj(Object obj, String methodName, Object... args) throws SecurityException {
        if (obj == null || StringUtils.isBlank(methodName))
            return null;

        return getMethod(obj.getClass(), methodName, ClassUtils.getClasses(args));
    }

    /**
     * 查找指定方法，如果找不到对应的方法则返回 {@code null}<br>
     * 此方法为精准获取方法名，即方法名和参数数量和类型必须一致，否则返回 {@code null}
     *
     * @param clazz      类，如果为 {@code null} 则返回 {@code null}
     * @param methodName 方法名，如果为空字符串则返回 {@code null}
     * @param paramTypes 参数类型，指定参数类型如果是方法的子类也算
     * @return 方法
     * @throws SecurityException 无权访问抛出异常
     */
    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) throws SecurityException {
        if (clazz == null || StringUtils.isBlank(methodName))
            return null;

        final Method[] methods = getMethods(clazz);
        if (ArrayUtils.isNotEmpty(methods)) {
            for (Method method : methods) {
                if (methodName.equals(method.getName())) {
                    if (ClassUtils.isAllAssignableFrom(method.getParameterTypes(), paramTypes)) {
                        return method;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 按照方法名查找指定方法名的方法，只返回匹配到的第一个方法，如果找不到对应的方法则返回 {@code null}<br>
     * 此方法只检查方法名是否一致，并不检查参数的一致性
     *
     * @param clazz      类，如果为 {@code null} 则返回 {@code null}
     * @param methodName 方法名，如果为空字符串则返回 {@code null}
     * @return 方法
     * @throws SecurityException 无权访问抛出异常
     */
    public static Method getMethodByName(Class<?> clazz, String methodName) throws SecurityException {
        if (clazz == null || StringUtils.isBlank(methodName))
            return null;

        final Method[] methods = getMethods(clazz);
        if (ArrayUtils.isNotEmpty(methods)) {
            for (Method method : methods) {
                if (methodName.equals(method.getName()))
                    return method;
            }
        }
        return null;
    }

    /**
     * 获得指定类中的方法名，去重重载的方法
     *
     * @param clazz 类
     * @return 方法名 Set 集合
     * @throws SecurityException 无权访问抛出异常
     */
    public static Set<String> getMethodNames(Class<?> clazz) throws SecurityException {
        final HashSet<String> methodSet = new HashSet<>();
        final Method[] methods = getMethods(clazz);
        for (Method method : methods) {
            methodSet.add(method.getName());
        }
        return methodSet;
    }

    /**
     * 获得指定类过滤后的方法列表
     *
     * @param clazz  类
     * @param filter 过滤器
     * @return 过滤后的方法数组
     * @throws SecurityException 无权访问抛出异常
     */
    public static Method[] getMethods(Class<?> clazz, Filter<Method> filter) throws SecurityException {
        if (clazz == null)
            return null;

        return ArrayUtils.filter(getMethods(clazz), filter);
    }

    /**
     * 获得一个类中所有方法列表，包括其父类中的方法
     *
     * @param beanClass 类
     * @return 方法列表
     * @throws SecurityException 无权访问抛出异常
     */
    public static Method[] getMethods(Class<?> beanClass) throws SecurityException {
        Method[] allMethods = METHODS_CACHE.get(beanClass);
        if (allMethods != null)
            return allMethods;

        allMethods = getMethodsDirectly(beanClass, true);
        return METHODS_CACHE.put(beanClass, allMethods);
    }

    /**
     * 获得一个类中所有方法列表，直接反射获取，无缓存
     *
     * @param beanClass             类
     * @param withSuperClassMethods 是否包括父类的方法列表
     * @return 方法列表
     * @throws SecurityException 无权访问抛出异常
     */
    public static Method[] getMethodsDirectly(Class<?> beanClass, boolean withSuperClassMethods) throws SecurityException {
        Method[] allMethods = null;
        Class<?> searchType = beanClass;
        Method[] declaredMethods;
        while (searchType != null) {
            declaredMethods = searchType.getDeclaredMethods();
            if (allMethods == null) {
                allMethods = declaredMethods;
            } else {
                allMethods = ArrayUtils.append(allMethods, declaredMethods);
            }
            searchType = withSuperClassMethods ? searchType.getSuperclass() : null;
        }

        return allMethods;
    }

    /**
     * 实例化对象
     *
     * @param <T>   对象类型
     * @param clazz 类名
     * @return 对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(String clazz) {
        try {
            return (T) Class.forName(clazz).newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Instance class [" + clazz + "] error!", e);
        }
    }

    /**
     * 实例化对象
     *
     * @param <T>    对象类型
     * @param clazz  类
     * @param params 构造函数参数
     * @return 对象
     */
    public static <T> T newInstance(Class<T> clazz, Object... params) {
        if (ArrayUtils.isEmpty(params)) {
            final Constructor<T> constructor = getConstructor(clazz);
            try {
                return constructor.newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Instance class [" + clazz + "] error!", e);
            }
        }

        final Class<?>[] paramTypes = ClassUtils.getClasses(params);
        final Constructor<T> constructor = getConstructor(clazz, paramTypes);
        if (constructor == null) {
            throw new RuntimeException("No Constructor matched for parameter types: [" + paramTypes + "]");
        }
        try {
            return constructor.newInstance(params);
        } catch (Exception e) {
            throw new RuntimeException("Instance class [" + clazz + "] error!", e);
        }
    }

    /**
     * 尝试遍历并调用此类的所有构造方法，直到构造成功并返回<br>
     * 对于某些特殊的接口，按照其默认实现实例化，例如：
     * <pre>
     *     Map       -》 HashMap
     *     Collction -》 ArrayList
     *     List      -》 ArrayList
     *     Set       -》 HashSet
     * </pre>
     *
     * @param <T>       对象类型
     * @param beanClass 被构造的类
     * @return 构造后的对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstanceIfPossible(Class<T> beanClass) {
        // 某些特殊接口的实例化按照默认实现进行
        if (beanClass.isAssignableFrom(AbstractMap.class)) {
            beanClass = (Class<T>) HashMap.class;
        } else if (beanClass.isAssignableFrom(List.class)) {
            beanClass = (Class<T>) ArrayList.class;
        } else if (beanClass.isAssignableFrom(Set.class)) {
            beanClass = (Class<T>) HashSet.class;
        }

        try {
            return newInstance(beanClass);
        } catch (Exception e) {
            // ignore
            // 默认构造不存在的情况下查找其它构造
        }

        final Constructor<T>[] constructors = getConstructors(beanClass);
        Class<?>[] parameterTypes;
        for (Constructor<T> constructor : constructors) {
            parameterTypes = constructor.getParameterTypes();
            if (0 == parameterTypes.length) {
                continue;
            }
            setAccessible(constructor);
            try {
                return constructor.newInstance(ClassUtils.getDefaultValues(parameterTypes));
            } catch (Exception ignore) {
                // 构造出错时继续尝试下一种构造方式
            }
        }
        return null;
    }

    /**
     * 执行静态方法
     *
     * @param <T>    对象类型
     * @param method 方法（对象方法或 static 方法都可以）
     * @param args   参数对象
     * @return 结果
     */
    public static <T> T invokeStatic(Method method, Object... args) {
        return invoke(null, method, args);
    }

    /**
     * 执行方法<br>
     * 执行前要检查给定参数：
     * <pre>
     *     1. 参数个数是否与方法参数个数一致
     *     2. 如果某个参数为 null 但是方法这个位置的参数为原始类型，则赋予原始类型默认值
     * </pre>
     *
     * @param <T>    返回对象类型
     * @param obj    对象，如果执行静态方法，此值为 {@code null}
     * @param method 方法（对象方法或 static 方法都可以）
     * @param args   参数对象
     * @return 结果
     */
    public static <T> T invokeWithCheck(Object obj, Method method, Object... args) {
        final Class<?>[] types = method.getParameterTypes();
        if (args != null) {
            Class<?> type;
            for (int i = 0; i < args.length; i++) {
                type = types[i];
                if (type.isPrimitive() && args[i] == null) {
                    // 参数是原始类型，而传入参数为null时赋予默认值
                    args[i] = ClassUtils.getDefaultValue(type);
                }
            }
        }

        return invoke(obj, method, args);
    }

    /**
     * 执行方法<br>
     * 对于用户传入参数会做必要检查，包括：
     * <pre>
     *     1、忽略多余的参数
     *     2、参数不够补齐默认值
     *     3、传入参数为 null，但是目标参数类型为原始类型，做转换
     * </pre>
     *
     * @param <T>    返回对象类型
     * @param obj    对象，如果执行静态方法，此值为 {@code null}
     * @param method 方法（对象方法或 static 方法都可以）
     * @param args   参数对象
     * @return 结果
     */
    @SuppressWarnings("unchecked")
    public static <T> T invoke(Object obj, Method method, Object... args) {
        setAccessible(method);

        // 检查用户传入参数：
        // 1、忽略多余的参数
        // 2、参数不够补齐默认值
        // 3、传入参数为 null，但是目标参数类型为原始类型，做转换
        final Class<?>[] parameterTypes = method.getParameterTypes();
        final Object[] actualArgs = new Object[parameterTypes.length];
        if (null != args) {
            for (int i = 0; i < actualArgs.length; i++) {
                if (i >= args.length || args[i] == null) {
                    // 越界或者空值
                    actualArgs[i] = ClassUtils.getDefaultValue(parameterTypes[i]);
                } else {
                    actualArgs[i] = args[i];
                }
            }
        }

        try {
            return (T) method.invoke(ClassUtils.isStatic(method) ? null : obj, actualArgs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 执行对象中指定方法
     *
     * @param <T>        返回对象类型
     * @param obj        方法所在对象
     * @param methodName 方法名
     * @param args       参数列表
     * @return 执行结果
     */
    public static <T> T invoke(Object obj, String methodName, Object... args) {
        final Method method = getMethodOfObj(obj, methodName, args);
        if (method == null) {
            throw new RuntimeException("No such method: [" + methodName + "]");
        }
        return invoke(obj, method, args);
    }

    /**
     * 设置方法为可访问（私有方法可以被外部调用）
     *
     * @param <T>              AccessibleObject 的子类，比如 Class、Method、Field 等
     * @param accessibleObject 可设置访问权限的对象，比如 Class、Method、Field 等
     * @return 被设置可访问的对象
     */
    public static <T extends AccessibleObject> T setAccessible(T accessibleObject) {
        if (accessibleObject != null && !accessibleObject.isAccessible())
            accessibleObject.setAccessible(true);

        return accessibleObject;
    }
}
