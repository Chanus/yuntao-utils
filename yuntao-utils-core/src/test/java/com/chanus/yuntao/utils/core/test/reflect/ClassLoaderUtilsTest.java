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
package com.chanus.yuntao.utils.core.test.reflect;

import com.chanus.yuntao.utils.core.lang.JarClassLoader;
import com.chanus.yuntao.utils.core.reflect.ClassLoaderUtils;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

/**
 * ClassLoaderUtils 测试类
 *
 * @author Chanus
 * @since 1.2.5
 */
public class ClassLoaderUtilsTest {
    @Test
    public void getContextClassLoaderTest() {
        ClassLoader classLoader = ClassLoaderUtils.getContextClassLoader();
        System.out.println(classLoader.toString());
    }

    @Test
    public void getClassLoaderTest() {
        ClassLoader classLoader = ClassLoaderUtils.getClassLoader();
        System.out.println(classLoader.toString());
    }

    @Test
    public void loadClassTest() {
        Class<?> clazz = ClassLoaderUtils.loadClass("int[]");
        System.out.println(clazz.getName());
        System.out.println(clazz.isArray());

        Class<?> clazz2 = ClassLoaderUtils.loadClass("int[]", false);
        System.out.println(clazz2.getName());
        System.out.println(clazz2.isArray());
    }

    @Test
    public void loadPrimitiveClassTest() {
        Class<?> clazz = ClassLoaderUtils.loadPrimitiveClass("[I");
        System.out.println(clazz.getName());
        System.out.println(clazz.isArray());
    }

    @Test
    public void getJarClassLoaderTest() {
        File jarOrDir = new File("D:\\work\\maven\\repository\\com\\chanus\\yuntao-utils-core\\1.1.0\\yuntao-utils-core-1.1.0.jar");
        JarClassLoader jarClassLoader = ClassLoaderUtils.getJarClassLoader(jarOrDir);
        System.out.println(jarClassLoader.toString());
    }

    @Test
    public void loadClassTest2() {
        File jarOrDir = new File("D:\\work\\maven\\repository\\com\\chanus\\yuntao-utils-core\\1.1.0\\yuntao-utils-core-1.1.0.jar");
        Class<?> clazz = ClassLoaderUtils.loadClass(jarOrDir, "com.chanus.yuntao.utils.core.StringUtils");
        System.out.println(Arrays.toString(clazz.getMethods()));
    }

    @Test
    public void isPresentTest() {
        System.out.println(ClassLoaderUtils.isPresent("com.chanus.yuntao.utils.core.StringUtils"));
        System.out.println(ClassLoaderUtils.isPresent("com.chanus.yuntao.utils.core.StringUtils1"));

        File jarOrDir = new File("D:\\work\\maven\\repository\\com\\chanus\\yuntao-utils-core\\1.1.0\\yuntao-utils-core-1.1.0.jar");
        JarClassLoader jarClassLoader = ClassLoaderUtils.getJarClassLoader(jarOrDir);
        System.out.println(ClassLoaderUtils.isPresent("com.chanus.yuntao.utils.core.StringUtils", jarClassLoader));
        System.out.println(ClassLoaderUtils.isPresent("com.chanus.yuntao.utils.core.StringUtils1", jarClassLoader));
    }
}
