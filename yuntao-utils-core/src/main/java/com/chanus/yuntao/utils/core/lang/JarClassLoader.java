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

import com.chanus.yuntao.utils.core.reflect.ClassUtils;
import com.chanus.yuntao.utils.core.FileUtils;
import com.chanus.yuntao.utils.core.reflect.ReflectUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

/**
 * 外部 Jar 的类加载器
 *
 * @author Chanus
 * @since 1.2.5
 */
public class JarClassLoader extends URLClassLoader {

    /**
     * 构造方法
     */
    public JarClassLoader() {
        this(new URL[]{});
    }

    /**
     * 构造方法
     *
     * @param urls 被加载的 URL
     */
    public JarClassLoader(URL[] urls) {
        super(urls, ClassUtils.getClassLoader());
    }

    /**
     * 加载 Jar 到 ClassPath
     *
     * @param dir jar 文件或所在目录
     * @return JarClassLoader
     */
    public static JarClassLoader load(File dir) {
        final JarClassLoader loader = new JarClassLoader();
        // 查找加载所有 jar
        loader.addJar(dir);
        // 查找加载所有 class
        loader.addUrl(dir);
        return loader;
    }

    /**
     * 加载 Jar 到 ClassPath
     *
     * @param jarFile jar 文件或所在目录
     * @return JarClassLoader
     */
    public static JarClassLoader loadJar(File jarFile) {
        final JarClassLoader loader = new JarClassLoader();
        loader.addJar(jarFile);
        return loader;
    }

    /**
     * 加载 Jar 文件到指定 loader 中
     *
     * @param loader  {@link URLClassLoader}
     * @param jarFile 被加载的 jar
     * @throws IOException IO异常
     */
    public static void loadJar(URLClassLoader loader, File jarFile) throws IOException {
        final Method method = ClassUtils.getDeclaredMethod(URLClassLoader.class, "addURL", URL.class);
        if (null != method) {
            method.setAccessible(true);
            final List<File> jars = loopJar(jarFile);
            for (File jar : jars) {
                ReflectUtils.invoke(loader, method, jar.toURI().toURL());
            }
        }
    }

    /**
     * 加载 Jar 文件到 System ClassLoader 中
     *
     * @param jarFile 被加载的 jar
     * @return System ClassLoader
     * @throws IOException IO异常
     */
    public static URLClassLoader loadJarToSystemClassLoader(File jarFile) throws IOException {
        URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        loadJar(urlClassLoader, jarFile);
        return urlClassLoader;
    }

    /**
     * 加载 Jar 文件，或者加载目录
     *
     * @param jarFileOrDir jar 文件或者 jar 文件所在目录
     * @return this
     */
    public JarClassLoader addJar(File jarFileOrDir) {
        if (isJarFile(jarFileOrDir)) {
            return addUrl(jarFileOrDir);
        }

        final List<File> jars = loopJar(jarFileOrDir);
        for (File jar : jars) {
            addUrl(jar);
        }
        return this;
    }

    /**
     * 增加 class 所在目录或文件<br>
     * 如果为目录，此目录用于搜索 class 文件，如果为文件，需为 jar 文件
     *
     * @param dir 目录
     * @return this
     */
    public JarClassLoader addUrl(File dir) {
        try {
            super.addURL(dir.toURI().toURL());
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred.", e);
        }
        return this;
    }

    /**
     * 递归获得 Jar 文件
     *
     * @param file jar 文件或者包含 jar 文件的目录
     * @return jar 文件列表
     */
    private static List<File> loopJar(File file) {
        return FileUtils.loopFiles(file, JarClassLoader::isJarFile);
    }

    /**
     * 是否为 jar 文件
     *
     * @param file 文件
     * @return {@code true} 是 jar 文件；{@code false} 不是 jar 文件
     */
    private static boolean isJarFile(File file) {
        if (file == null || !file.isFile()) {
            return false;
        }

        return file.getPath().toLowerCase().endsWith(".jar");
    }

    @Override
    public void addURL(URL url) {
        super.addURL(url);
    }
}
