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

import com.chanus.yuntao.utils.core.CharUtils;
import com.chanus.yuntao.utils.core.StringUtils;
import com.chanus.yuntao.utils.core.lang.JarClassLoader;
import com.chanus.yuntao.utils.core.lang.SimpleCache;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 类加载工具类
 *
 * @author Chanus
 * @date 2020-09-21 08:59:10
 * @since 1.2.5
 */
public class ClassLoaderUtils {
    /**
     * 数组类的结尾符: "[]"
     */
    private static final String ARRAY_SUFFIX = "[]";
    /**
     * 内部数组类名前缀: "["
     */
    private static final String INTERNAL_ARRAY_PREFIX = "[";
    /**
     * 内部非原始类型类名前缀: "[L"
     */
    private static final String NON_PRIMITIVE_ARRAY_PREFIX = "[L";
    /**
     * 包名分界符: '.'
     */
    private static final char PACKAGE_SEPARATOR = CharUtils.DOT;
    /**
     * 内部类分界符: '$'
     */
    private static final char INNER_CLASS_SEPARATOR = '$';

    /**
     * 原始类型名和其 class 对应表，例如：int =》 int.class
     */
    private static final Map<String, Class<?>> primitiveTypeNameMap = new ConcurrentHashMap<>(32);
    private static final SimpleCache<String, Class<?>> classCache = new SimpleCache<>();

    static {
        List<Class<?>> primitiveTypes = new ArrayList<>(32);
        // 加入原始类型
        primitiveTypes.addAll(BasicType.primitiveWrapperMap.keySet());
        // 加入原始类型数组类型
        primitiveTypes.add(boolean[].class);
        primitiveTypes.add(byte[].class);
        primitiveTypes.add(char[].class);
        primitiveTypes.add(double[].class);
        primitiveTypes.add(float[].class);
        primitiveTypes.add(int[].class);
        primitiveTypes.add(long[].class);
        primitiveTypes.add(short[].class);
        primitiveTypes.add(void.class);
        for (Class<?> primitiveType : primitiveTypes) {
            primitiveTypeNameMap.put(primitiveType.getName(), primitiveType);
        }
    }

    /**
     * 获取当前线程的 {@link ClassLoader}
     *
     * @return 当前线程的 class loader
     * @see Thread#getContextClassLoader()
     */
    public static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 获取 {@link ClassLoader} <br>
     * 获取顺序如下：
     * <pre>
     *     1、获取当前线程的 ContextClassLoader
     *     2、获取 {@link ClassLoaderUtils} 类对应的 ClassLoader
     *     3、获取系统 ClassLoader（{@link ClassLoader#getSystemClassLoader()}）
     * </pre>
     *
     * @return 类加载器
     */
    public static ClassLoader getClassLoader() {
        ClassLoader classLoader = getContextClassLoader();
        if (classLoader == null) {
            classLoader = ClassLoaderUtils.class.getClassLoader();
            if (classLoader == null) {
                classLoader = ClassLoader.getSystemClassLoader();
            }
        }
        return classLoader;
    }

    /**
     * 加载类，通过传入类的字符串，返回其对应的类名，使用默认 ClassLoader 并初始化类（调用 static 模块内容和初始化 static 属性）<br>
     * 扩展 {@link Class#forName(String, boolean, ClassLoader)} 方法，支持以下几类类名的加载：
     * <pre>
     *     1、原始类型，例如：int
     *     2、数组类型，例如：int[]、Long[]、String[]
     *     3、内部类，例如：java.lang.Thread.State 会被转为 java.lang.Thread$State 加载
     * </pre>
     *
     * @param name 类名
     * @return 类名对应的类
     */
    public static Class<?> loadClass(String name) {
        return loadClass(name, true);
    }

    /**
     * 加载类，通过传入类的字符串，返回其对应的类名，使用默认 ClassLoader<br>
     * 扩展 {@link Class#forName(String, boolean, ClassLoader)} 方法，支持以下几类类名的加载：
     * <pre>
     *     1、原始类型，例如：int
     *     2、数组类型，例如：int[]、Long[]、String[]
     *     3、内部类，例如：java.lang.Thread.State 会被转为 java.lang.Thread$State 加载
     * </pre>
     *
     * @param name          类名
     * @param isInitialized 是否初始化类（调用 static 模块内容和初始化 static 属性）
     * @return 类名对应的类
     */
    public static Class<?> loadClass(String name, boolean isInitialized) {
        return loadClass(name, null, isInitialized);
    }

    /**
     * 加载类，通过传入类的字符串，返回其对应的类名<br>
     * 此方法支持缓存，第一次被加载的类之后会读取缓存中的类<br>
     * 加载失败的原因可能是此类不存在或其关联引用类不存在<br>
     * 扩展 {@link Class#forName(String, boolean, ClassLoader)} 方法，支持以下几类类名的加载：
     * <pre>
     *     1、原始类型，例如：int
     *     2、数组类型，例如：int[]、Long[]、String[]
     *     3、内部类，例如：java.lang.Thread.State 会被转为 java.lang.Thread$State 加载
     * </pre>
     *
     * @param name          类名
     * @param classLoader   {@link ClassLoader}，{@code null} 则使用系统默认 ClassLoader
     * @param isInitialized 是否初始化类（调用 static 模块内容和初始化 static 属性）
     * @return 类名对应的类
     */
    public static Class<?> loadClass(String name, ClassLoader classLoader, boolean isInitialized) {
        // 加载原始类型和缓存中的类
        Class<?> clazz = loadPrimitiveClass(name);
        if (clazz == null)
            clazz = classCache.get(name);

        if (clazz != null)
            return clazz;

        if (name.endsWith(ARRAY_SUFFIX)) {
            // 对象数组"java.lang.String[]"风格
            final String elementClassName = name.substring(0, name.length() - ARRAY_SUFFIX.length());
            final Class<?> elementClass = loadClass(elementClassName, classLoader, isInitialized);
            clazz = Array.newInstance(elementClass, 0).getClass();
        } else if (name.startsWith(NON_PRIMITIVE_ARRAY_PREFIX) && name.endsWith(";")) {
            // "[Ljava.lang.String;" 风格
            final String elementName = name.substring(NON_PRIMITIVE_ARRAY_PREFIX.length(), name.length() - 1);
            final Class<?> elementClass = loadClass(elementName, classLoader, isInitialized);
            clazz = Array.newInstance(elementClass, 0).getClass();
        } else if (name.startsWith(INTERNAL_ARRAY_PREFIX)) {
            // "[[I" 或 "[[Ljava.lang.String;" 风格
            final String elementName = name.substring(INTERNAL_ARRAY_PREFIX.length());
            final Class<?> elementClass = loadClass(elementName, classLoader, isInitialized);
            clazz = Array.newInstance(elementClass, 0).getClass();
        } else {
            // 加载普通类
            if (classLoader == null) {
                classLoader = getClassLoader();
            }
            try {
                clazz = Class.forName(name, isInitialized, classLoader);
            } catch (ClassNotFoundException ex) {
                // 尝试获取内部类，例如java.lang.Thread.State =》java.lang.Thread$State
                clazz = tryLoadInnerClass(name, classLoader, isInitialized);
                if (clazz == null) {
                    throw new RuntimeException(ex);
                }
            }
        }

        // 加入缓存并返回
        return classCache.put(name, clazz);
    }

    /**
     * 加载原始类型的类。包括原始类型、原始类型数组和 void
     *
     * @param name 原始类型名，比如 int
     * @return 原始类型类
     */
    public static Class<?> loadPrimitiveClass(String name) {
        Class<?> result = null;
        if (StringUtils.isNotBlank(name)) {
            name = name.trim();
            if (name.length() <= 8) {
                result = primitiveTypeNameMap.get(name);
            }
        }
        return result;
    }

    /**
     * 创建新的 {@link JarClassLoader}，并使用此 Classloader 加载目录下的 class 文件和 jar 文件
     *
     * @param jarOrDir jar 文件或者包含 jar 和 class 文件的目录
     * @return {@link JarClassLoader}
     */
    public static JarClassLoader getJarClassLoader(File jarOrDir) {
        return JarClassLoader.load(jarOrDir);
    }

    /**
     * 加载外部类
     *
     * @param jarOrDir jar 文件或者包含 jar 和 class 文件的目录
     * @param name     类名
     * @return 类
     */
    public static Class<?> loadClass(File jarOrDir, String name) {
        try {
            return getJarClassLoader(jarOrDir).loadClass(name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 指定类是否被提供，使用默认 ClassLoader<br>
     * 通过调用 {@link #loadClass(String, ClassLoader, boolean)} 方法尝试加载指定类名的类，如果加载失败返回 {@code false}<br>
     * 加载失败的原因可能是此类不存在或其关联引用类不存在
     *
     * @param className 类名
     * @return {@code true} 被提供；{@code false} 没有被提供
     */
    public static boolean isPresent(String className) {
        return isPresent(className, null);
    }

    /**
     * 指定类是否被提供<br>
     * 通过调用 {@link #loadClass(String, ClassLoader, boolean)} 方法尝试加载指定类名的类，如果加载失败返回 {@code false}<br>
     * 加载失败的原因可能是此类不存在或其关联引用类不存在
     *
     * @param className   类名
     * @param classLoader {@link ClassLoader}
     * @return {@code true} 被提供；{@code false} 没有被提供
     */
    public static boolean isPresent(String className, ClassLoader classLoader) {
        try {
            loadClass(className, classLoader, false);
            return true;
        } catch (Throwable ex) {
            return false;
        }
    }

    /**
     * 尝试转换并加载内部类，例如 java.lang.Thread.State =》java.lang.Thread$State
     *
     * @param name          类名
     * @param classLoader   {@link ClassLoader}，{@code null} 则使用系统默认 ClassLoader
     * @param isInitialized 是否初始化类（调用 static 模块内容和初始化 static 属性）
     * @return 类名对应的类
     */
    private static Class<?> tryLoadInnerClass(String name, ClassLoader classLoader, boolean isInitialized) {
        // 尝试获取内部类，例如java.lang.Thread.State =》java.lang.Thread$State
        final int lastDotIndex = name.lastIndexOf(PACKAGE_SEPARATOR);
        if (lastDotIndex > 0) {// 类与内部类的分隔符不能在第一位，因此>0
            final String innerClassName = name.substring(0, lastDotIndex) + INNER_CLASS_SEPARATOR + name.substring(lastDotIndex + 1);
            try {
                return Class.forName(innerClassName, isInitialized, classLoader);
            } catch (ClassNotFoundException ignored) {

            }
        }
        return null;
    }
}
