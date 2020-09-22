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
import com.chanus.yuntao.utils.core.CharUtils;
import com.chanus.yuntao.utils.core.CharsetUtils;
import com.chanus.yuntao.utils.core.StringUtils;
import com.chanus.yuntao.utils.core.lang.Filter;
import com.chanus.yuntao.utils.core.lang.Singleton;
import com.sun.xml.internal.ws.util.UtilException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

/**
 * 类工具类
 *
 * @author Chanus
 * @date 2020-09-17 10:18:16
 * @since 1.2.5
 */
public class ClassUtils {
    /**
     * 获取对象类型
     *
     * @param <T> 对象类型
     * @param obj 对象，如果为 {@code null} 则返回 {@code null}
     * @return 对象类型
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClass(T obj) {
        return obj == null ? null : (Class<T>) obj.getClass();
    }

    /**
     * 获取外围类
     *
     * @param clazz 类
     * @return 定义此类或匿名类所在的类，如果类本身是在包中定义的，返回 {@code null}
     */
    public static Class<?> getEnclosingClass(Class<?> clazz) {
        return clazz == null ? null : clazz.getEnclosingClass();
    }

    /**
     * 是否为顶层类，即定义在包中的类，而非定义在类中的内部类
     *
     * @param clazz 类
     * @return {@code true} 是顶层类；{@code false} 不是顶层类
     */
    public static boolean isTopLevelClass(Class<?> clazz) {
        return clazz != null && getEnclosingClass(clazz) == null;
    }

    /**
     * 获取类名
     *
     * @param obj      获取类名对象
     * @param isSimple 是否简单类名，如果为 {@code true}，返回不带包名的类名
     * @return 类名
     */
    public static String getClassName(Object obj, boolean isSimple) {
        return obj == null ? null : getClassName(obj.getClass(), isSimple);
    }

    /**
     * 获取类名，类名并不包含扩展名“.class”
     *
     * @param clazz    类
     * @param isSimple 是否简单类名，如果为 {@code true}，返回不带包名的类名
     * @return 类名
     */
    public static String getClassName(Class<?> clazz, boolean isSimple) {
        if (clazz == null)
            return null;

        return isSimple ? clazz.getSimpleName() : clazz.getName();
    }

    /**
     * 获取完整类名的短格式
     *
     * @param className 类名
     * @return 短格式类名
     */
    public static String getShortClassName(String className) {
        final List<String> packages = StringUtils.split(className, CharUtils.DOT);
        if (packages == null || packages.size() < 2)
            return className;

        final int size = packages.size();
        final StringBuilder result = new StringBuilder();
        result.append(packages.get(0).charAt(0));
        for (int i = 1; i < size - 1; i++) {
            result.append(CharUtils.DOT).append(packages.get(i).charAt(0));
        }
        result.append(CharUtils.DOT).append(packages.get(size - 1));

        return result.toString();
    }

    /**
     * 获取对象数组的类数组
     *
     * @param objects 对象数组，如果数组中存在 {@code null} 元素，则此元素被认为是 Object 类型
     * @return 类数组
     */
    public static Class<?>[] getClasses(Object... objects) {
        Class<?>[] classes = new Class<?>[objects.length];
        Object obj;
        for (int i = 0; i < objects.length; i++) {
            obj = objects[i];
            classes[i] = obj == null ? Object.class : obj.getClass();
        }
        return classes;
    }

    /**
     * 指定类是否与给定的类名相同
     *
     * @param clazz      类
     * @param className  类名，可以是全类名（包含包名），也可以是简单类名（不包含包名）
     * @param ignoreCase 是否忽略大小写
     * @return 指定类是否与给定的类名相同
     */
    public static boolean equals(Class<?> clazz, String className, boolean ignoreCase) {
        if (clazz == null || StringUtils.isBlank(className))
            return false;

        if (ignoreCase) {
            return className.equalsIgnoreCase(clazz.getName()) || className.equalsIgnoreCase(clazz.getSimpleName());
        } else {
            return className.equals(clazz.getName()) || className.equals(clazz.getSimpleName());
        }
    }

    /**
     * 获取指定类中的 public 方法名，去重重载的方法
     *
     * @param clazz 类
     * @return 方法名 Set
     */
    public static Set<String> getPublicMethodNames(Class<?> clazz) {
        return ReflectUtils.getPublicMethodNames(clazz);
    }

    /**
     * 获取本类及其父类所有的 public 方法
     *
     * @param clazz 类
     * @return 过滤后的方法数组
     */
    public static Method[] getPublicMethods(Class<?> clazz) {
        return ReflectUtils.getPublicMethods(clazz);
    }

    /**
     * 获取指定类过滤后的 public 方法列表
     *
     * @param clazz  类
     * @param filter 过滤器
     * @return 过滤后的方法列表
     */
    public static List<Method> getPublicMethods(Class<?> clazz, Filter<Method> filter) {
        return ReflectUtils.getPublicMethods(clazz, filter);
    }

    /**
     * 获取指定类过滤后的 public 方法列表
     *
     * @param clazz          类
     * @param excludeMethods 不包括的方法
     * @return 过滤后的方法列表
     */
    public static List<Method> getPublicMethods(Class<?> clazz, Method... excludeMethods) {
        return ReflectUtils.getPublicMethods(clazz, excludeMethods);
    }

    /**
     * 获取指定类过滤后的 public 方法列表
     *
     * @param clazz              类
     * @param excludeMethodNames 不包括的方法名列表
     * @return 过滤后的方法列表
     */
    public static List<Method> getPublicMethods(Class<?> clazz, String... excludeMethodNames) {
        return ReflectUtils.getPublicMethods(clazz, excludeMethodNames);
    }

    /**
     * 获取指定的 public 方法，如果找不到对应的方法或方法不为 public，则返回 {@code null}
     *
     * @param clazz      类
     * @param methodName 方法名
     * @param paramTypes 参数类型
     * @return 方法
     * @throws SecurityException 无权访问抛出异常
     */
    public static Method getPublicMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) throws SecurityException {
        return ReflectUtils.getPublicMethod(clazz, methodName, paramTypes);
    }

    /**
     * 获取指定类中的 public 方法名，去重重载的方法
     *
     * @param clazz 类
     * @return 方法名 Set
     */
    public static Set<String> getDeclaredMethodNames(Class<?> clazz) {
        return ReflectUtils.getMethodNames(clazz);
    }

    /**
     * 获取声明的所有方法，包括本类及其父类和接口的所有方法和 Object 类的方法
     *
     * @param clazz 类
     * @return 方法数组
     */
    public static Method[] getDeclaredMethods(Class<?> clazz) {
        return ReflectUtils.getMethods(clazz);
    }

    /**
     * 获取指定对象中的指定方法
     *
     * @param obj        对象
     * @param methodName 方法名
     * @param args       参数
     * @return 方法
     * @throws SecurityException 无访问权限抛出异常
     */
    public static Method getDeclaredMethodOfObj(Object obj, String methodName, Object... args) throws SecurityException {
        return getDeclaredMethod(obj.getClass(), methodName, getClasses(args));
    }

    /**
     * 获取指定类中的指定方法
     *
     * @param clazz          类
     * @param methodName     方法名
     * @param parameterTypes 参数类型
     * @return 方法
     * @throws SecurityException 无访问权限抛出异常
     */
    public static Method getDeclaredMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) throws SecurityException {
        return ReflectUtils.getMethod(clazz, methodName, parameterTypes);
    }

    /**
     * 获取指定类中的指定字段
     *
     * @param clazz     类
     * @param fieldName 字段名
     * @return 字段
     * @throws NoSuchFieldException 找不到字段异常
     * @throws SecurityException    无访问权限抛出异常
     */
    public static Field getDeclaredField(Class<?> clazz, String fieldName) throws NoSuchFieldException, SecurityException {
        if (clazz == null || StringUtils.isBlank(fieldName))
            return null;

        return clazz.getDeclaredField(fieldName);
    }

    /**
     * 获取指定类中的所有字段（包括非 public 字段)
     *
     * @param clazz 类
     * @return 字段数组
     * @throws SecurityException 无访问权限抛出异常
     */
    public static Field[] getDeclaredFields(Class<?> clazz) throws SecurityException {
        return clazz == null ? null : clazz.getDeclaredFields();
    }

    /**
     * 获取 ClassPath，不解码路径中的特殊字符（例如空格和中文）
     *
     * @return ClassPath 集合
     */
    public static Set<String> getClassPathResources() {
        return getClassPathResources(false);
    }

    /**
     * 获取 ClassPath
     *
     * @param isDecode 是否解码路径中的特殊字符（例如空格和中文）
     * @return ClassPath 集合
     */
    public static Set<String> getClassPathResources(boolean isDecode) {
        return getClassPaths(StringUtils.EMPTY, isDecode);
    }

    /**
     * 获取 ClassPath，不解码路径中的特殊字符（例如空格和中文）
     *
     * @param packageName 包名称
     * @return ClassPath 路径字符串集合
     */
    public static Set<String> getClassPaths(String packageName) {
        return getClassPaths(packageName, false);
    }

    /**
     * 获取 ClassPath
     *
     * @param packageName 包名称
     * @param isDecode    是否解码路径中的特殊字符（例如空格和中文）
     * @return ClassPath 路径字符串集合
     */
    public static Set<String> getClassPaths(String packageName, boolean isDecode) {
        String packagePath = packageName.replace(StringUtils.DOT, StringUtils.SLASH);
        Enumeration<URL> resources;
        try {
            resources = getClassLoader().getResources(packagePath);
        } catch (IOException e) {
            throw new RuntimeException("Loading classPath error!", e);
        }
        final Set<String> paths = new HashSet<>();
        String path;
        while (resources.hasMoreElements()) {
            path = resources.nextElement().getPath();
            try {
                paths.add(isDecode ? URLDecoder.decode(path, CharsetUtils.defaultCharsetName()) : path);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("Decode classPath error!", e);
            }
        }
        return paths;
    }

    /**
     * 获取 ClassPath
     *
     * @return ClassPath
     */
    public static String getClassPath() {
        return getClassPathURL().getPath();
    }

    /**
     * 获取 ClassPath URL
     *
     * @return ClassPath URL
     */
    public static URL getClassPathURL() {
        return getResourceURL(StringUtils.EMPTY);
    }

    /**
     * 获取资源的 URL<br>
     * 路径用/分隔，例如：
     * <pre>
     *     config/a/db.config
     *     spring/xml/test.xml
     * </pre>
     *
     * @param resource 资源相对 Classpath 的路径
     * @return 资源 URL
     */
    public static URL getResourceURL(String resource) {
        return ClassLoaderUtils.getClassLoader().getResource(resource);
    }

    /**
     * 获取指定路径下的资源列表<br>
     * 路径格式必须为目录格式，用/分隔，例如:
     * <pre>
     *     config/a
     *     spring/xml
     * </pre>
     *
     * @param resource 资源路径
     * @return 资源列表
     */
    public static List<URL> getResources(String resource) {
        try {
            Enumeration<URL> resources = ClassLoaderUtils.getClassLoader().getResources(resource);
            if (resources == null)
                return null;

            ArrayList<URL> list = new ArrayList<>();
            while (resources.hasMoreElements()) {
                list.add(resources.nextElement());
            }
            return list;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取资源相对路径对应的 URL
     *
     * @param resource  资源相对路径
     * @param baseClass 基准 Class，获取的相对路径相对于此 Class 所在路径，如果为 {@code null} 则相对 ClassPath
     * @return {@link URL}
     */
    public static URL getResourceURL(String resource, Class<?> baseClass) {
        return baseClass != null ? baseClass.getResource(resource) : ClassLoaderUtils.getClassLoader().getResource(resource);
    }

    /**
     * 获取 Java ClassPath 路径，不包括 jre
     *
     * @return Java ClassPath 路径，不包括 jre
     */
    public static String[] getJavaClassPaths() {
        return System.getProperty("java.class.path").split(System.getProperty("path.separator"));
    }

    /**
     * 获取当前线程的 {@link ClassLoader}
     *
     * @return 当前线程的 class loader
     * @see ClassLoaderUtils#getClassLoader()
     */
    public static ClassLoader getContextClassLoader() {
        return ClassLoaderUtils.getContextClassLoader();
    }

    /**
     * 获取 {@link ClassLoader}<br>
     * 获取顺序如下：
     * <pre>
     *    1、获取当前线程的 ContextClassLoader
     *    2、获取 {@link ClassUtils} 类对应的 ClassLoader
     *    3、获取系统 ClassLoader（{@link ClassLoader#getSystemClassLoader()}）
     * </pre>
     *
     * @return 类加载器
     */
    public static ClassLoader getClassLoader() {
        return ClassLoaderUtils.getClassLoader();
    }

    /**
     * 比较 types1 和 types2 两组类，判断 types1 中所有的类是否都与 types2 对应位置的类相同，或者是其父类或接口
     *
     * @param types1 类组1
     * @param types2 类组2
     * @return 是否相同、父类或接口
     */
    public static boolean isAllAssignableFrom(Class<?>[] types1, Class<?>[] types2) {
        if (ArrayUtils.isEmpty(types1) && ArrayUtils.isEmpty(types2))
            return true;

        if (types1 == null || types2 == null) {
            // 任何一个为 null 则不相等（之前已判断两个都为 null 的情况）
            return false;
        }
        if (types1.length != types2.length) {
            return false;
        }

        Class<?> type1;
        Class<?> type2;
        for (int i = 0; i < types1.length; i++) {
            type1 = types1[i];
            type2 = types2[i];
            if (isBasicType(type1) && isBasicType(type2)) {
                // 原始类型和包装类型存在不一致情况
                if (BasicType.unWrap(type1) != BasicType.unWrap(type2)) {
                    return false;
                }
            } else if (!type1.isAssignableFrom(type2)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 加载类
     *
     * @param <T>           对象类型
     * @param className     类名
     * @param isInitialized 是否初始化
     * @return 类
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> loadClass(String className, boolean isInitialized) {
        return (Class<T>) ClassLoaderUtils.loadClass(className, isInitialized);
    }

    /**
     * 加载类并初始化
     *
     * @param <T>       对象类型
     * @param className 类名
     * @return 类
     */
    public static <T> Class<T> loadClass(String className) {
        return loadClass(className, true);
    }

    /**
     * 执行方法<br>
     * 可执行 private 方法，也可执行 static 方法<br>
     * 执行非 static 方法时，必须满足对象有默认构造方法<br>
     * 非单例模式，如果是非静态方法，每次创建一个新对象
     *
     * @param <T>        对象类型
     * @param className  类名，完整类路径
     * @param methodName 方法名
     * @param args       参数，必须严格对应指定方法的参数类型和数量
     * @return 返回结果
     */
    public static <T> T invoke(String className, String methodName, Object[] args) {
        return invoke(className, methodName, false, args);
    }

    /**
     * 执行方法<br>
     * 可执行 private 方法，也可执行 static 方法<br>
     * 执行非 static 方法时，必须满足对象有默认构造方法<br>
     *
     * @param <T>         对象类型
     * @param className   类名，完整类路径
     * @param methodName  方法名
     * @param isSingleton 是否为单例对象，如果此参数为 {@code false}，每次执行方法时创建一个新对象
     * @param args        参数，必须严格对应指定方法的参数类型和数量
     * @return 返回结果
     */
    public static <T> T invoke(String className, String methodName, boolean isSingleton, Object... args) {
        Class<Object> clazz = loadClass(className);
        try {
            final Method method = getDeclaredMethod(clazz, methodName, getClasses(args));
            if (method == null) {
                throw new NoSuchMethodException("No such method: [" + methodName + "]");
            }
            if (isStatic(method)) {
                return ReflectUtils.invoke(null, method, args);
            } else {
                return ReflectUtils.invoke(isSingleton ? Singleton.get(clazz) : clazz.newInstance(), method, args);
            }
        } catch (Exception e) {
            throw new UtilException(e);
        }
    }

    /**
     * 是否为包装类型
     *
     * @param clazz 类
     * @return {@code true} 是包装类型；{@code false} 不是包装类型
     */
    public static boolean isPrimitiveWrapper(Class<?> clazz) {
        return clazz != null && BasicType.wrapperPrimitiveMap.containsKey(clazz);
    }

    /**
     * 是否为基本类型（包括包装类和原始类）
     *
     * @param clazz 类
     * @return {@code true} 是基本类型；{@code false} 不是基本类型
     */
    public static boolean isBasicType(Class<?> clazz) {
        return clazz != null && (clazz.isPrimitive() || isPrimitiveWrapper(clazz));
    }

    /**
     * 检查目标类是否可以从原类转化<br>
     * 转化包括：
     * <pre>
     *     1、原类是对象，目标类型是原类型实现的接口
     *     2、目标类型是原类型的父类
     *     3、两者是原始类型或者包装类型（相互转换）
     * </pre>
     *
     * @param targetType 目标类型
     * @param sourceType 原类型
     * @return {@code true} 可转化；{@code false} 不可转化
     */
    public static boolean isAssignable(Class<?> targetType, Class<?> sourceType) {
        if (targetType == null || sourceType == null)
            return false;

        // 对象类型
        if (targetType.isAssignableFrom(sourceType))
            return true;

        // 基本类型
        if (targetType.isPrimitive()) {
            // 原始类型
            Class<?> resolvedPrimitive = BasicType.wrapperPrimitiveMap.get(sourceType);
            return targetType.equals(resolvedPrimitive);
        } else {
            // 包装类型
            Class<?> resolvedWrapper = BasicType.primitiveWrapperMap.get(sourceType);
            return resolvedWrapper != null && targetType.isAssignableFrom(resolvedWrapper);
        }
    }

    /**
     * 是否为 public 类
     *
     * @param clazz 类
     * @return {@code true} 是 public 类；{@code false} 不是 public 类
     */
    public static boolean isPublic(Class<?> clazz) {
        return Modifier.isPublic(clazz.getModifiers());
    }

    /**
     * 是否为 public 方法
     *
     * @param method 方法
     * @return {@code true} 是 public 方法；{@code false} 不是 public 方法
     */
    public static boolean isPublic(Method method) {
        return Modifier.isPublic(method.getModifiers());
    }

    /**
     * 是否为静态方法
     *
     * @param method 方法
     * @return {@code true} 是静态方法；{@code false} 不是静态方法
     */
    public static boolean isStatic(Method method) {
        return Modifier.isStatic(method.getModifiers());
    }

    /**
     * 设置方法为可访问
     *
     * @param method 方法
     * @return 方法
     */
    public static Method setAccessible(Method method) {
        if (method != null && !method.isAccessible())
            method.setAccessible(true);

        return method;
    }

    /**
     * 是否为抽象类
     *
     * @param clazz 类
     * @return {@code true} 是抽象类；{@code false} 不是抽象类
     */
    public static boolean isAbstract(Class<?> clazz) {
        return Modifier.isAbstract(clazz.getModifiers());
    }

    /**
     * 判断类是否为枚举类型
     *
     * @param clazz 类
     * @return {@code true} 是枚举类型；{@code false} 不是枚举类型
     */
    public static boolean isEnum(Class<?> clazz) {
        return clazz != null && clazz.isEnum();
    }

    /**
     * 获取给定类的第一个泛型参数
     *
     * @param clazz 被检查的类，必须是已经确定泛型类型的类
     * @return {@link Class}
     */
    public static Class<?> getTypeArgument(Class<?> clazz) {
        return getTypeArgument(clazz, 0);
    }

    /**
     * 获取给定类的泛型参数
     *
     * @param clazz 被检查的类，必须是已经确定泛型类型的类
     * @param index 泛型类型的索引号，即第几个泛型类型
     * @return {@link Class}
     */
    public static Class<?> getTypeArgument(Class<?> clazz, int index) {
        final Type argumentType = TypeUtils.getTypeArgument(clazz, index);
        if (argumentType instanceof Class) {
            return (Class<?>) argumentType;
        }
        return null;
    }

    /**
     * 获取给定类所在包的名称
     *
     * @param clazz 类
     * @return 类所在包的名称
     */
    public static String getPackage(Class<?> clazz) {
        if (clazz == null)
            return StringUtils.EMPTY;

        final String className = clazz.getName();
        int packageEndIndex = className.lastIndexOf(StringUtils.DOT);

        return packageEndIndex == -1 ? StringUtils.EMPTY : className.substring(0, packageEndIndex);
    }

    /**
     * 获取给定类所在包的路径
     *
     * @param clazz 类
     * @return 类所在包的路径
     */
    public static String getPackagePath(Class<?> clazz) {
        return getPackage(clazz).replace(CharUtils.DOT, CharUtils.SLASH);
    }

    /**
     * 获取指定类型的默认值，默认值规则为：
     * <pre>
     *     1、如果为原始类型，返回0
     *     2、非原始类型返回 {@code null}
     * </pre>
     *
     * @param clazz 类
     * @return 默认值
     */
    public static Object getDefaultValue(Class<?> clazz) {
        if (clazz.isPrimitive()) {
            if (long.class == clazz) {
                return 0L;
            } else if (int.class == clazz) {
                return 0;
            } else if (short.class == clazz) {
                return (short) 0;
            } else if (char.class == clazz) {
                return (char) 0;
            } else if (byte.class == clazz) {
                return (byte) 0;
            } else if (double.class == clazz) {
                return 0D;
            } else if (float.class == clazz) {
                return 0f;
            } else if (boolean.class == clazz) {
                return false;
            }
        }

        return null;
    }

    /**
     * 获取默认值列表
     *
     * @param classes 值类型
     * @return 默认值列表
     */
    public static Object[] getDefaultValues(Class<?>... classes) {
        final Object[] values = new Object[classes.length];
        for (int i = 0; i < classes.length; i++) {
            values[i] = getDefaultValue(classes[i]);
        }
        return values;
    }

    /**
     * 是否为 JDK 中定义的类或接口，判断依据：
     * <pre>
     *     1、以 java.、javax. 开头的包名
     *     2、ClassLoader 为 null
     * </pre>
     *
     * @param clazz 被检查的类
     * @return {@code true} 是 JDK 中定义的类或接口；{@code false} 不是 JDK 中定义的类或接口
     */
    public static boolean isJdkClass(Class<?> clazz) {
        final Package objectPackage = clazz.getPackage();
        if (objectPackage == null)
            return false;

        final String objectPackageName = objectPackage.getName();
        return objectPackageName.startsWith("java.")
                || objectPackageName.startsWith("javax.")
                || clazz.getClassLoader() == null;
    }

    /**
     * 获取 class 类路径 URL，不管是否在 jar 包中都会返回文件夹的路径<br>
     * class 在 jar 包中返回 jar 所在文件夹，class 不在 jar 中返回文件夹目录<br>
     * jdk 中的类不能使用此方法
     *
     * @param clazz 类
     * @return class 类路径 URL
     */
    public static URL getLocation(Class<?> clazz) {
        return clazz == null ? null : clazz.getProtectionDomain().getCodeSource().getLocation();
    }

    /**
     * 获取 class 类路径，不管是否在 jar 包中都会返回文件夹的路径<br>
     * class 在 jar 包中返回 jar 所在文件夹，class 不在 jar 中返回文件夹目录<br>
     * jdk 中的类不能使用此方法
     *
     * @param clazz 类
     * @return class 路径
     */
    public static String getLocationPath(Class<?> clazz) {
        final URL location = getLocation(clazz);

        return location == null ? null : location.getPath();
    }
}
