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

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * 属性文件操作工具类
 *
 * @author Chanus
 * @since 1.0.0
 */
public class PropertiesUtils {
    /**
     * 默认的属性文件为 classes 目录下的 config.properties
     */
    private static final String PROPERTIES_NAME = "config.properties";

    private PropertiesUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 从属性文件中读取属性值
     *
     * @param filePath 属性文件路径
     * @param key      要读取的属性名称
     * @return 属性值
     */
    public static String read(String filePath, String key) {
        Properties properties = new Properties();
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            properties.load(is);
            return properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    /**
     * 从属性文件中读取全部属性值，并封装到 Map 中
     *
     * @param filePath 属性文件路径
     * @return 存储属性值的 Map
     */
    public static Map<String, String> read(String filePath) {
        Properties properties = new Properties();
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            properties.load(is);
            Enumeration<?> en = properties.propertyNames();
            Map<String, String> map = new HashMap<>(8);
            String key;
            while (en.hasMoreElements()) {
                key = (String) en.nextElement();
                map.put(key, properties.getProperty(key));
            }
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
        }
        return null;
    }

    /**
     * 从默认的属性文件中读取属性值
     *
     * @param key 要读取的属性名称
     * @return 属性值
     */
    public static String readDefault(String key) {
        Properties properties = new Properties();
        InputStream is = null;
        try {
            is = PropertiesUtils.class.getClassLoader().getResourceAsStream(PROPERTIES_NAME);
            properties.load(is);
            return properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    /**
     * 从默认的属性文件中读取全部属性值，并封装到 Map 中
     *
     * @return 存储属性值的 Map
     */
    public static Map<String, String> readDefault() {
        Properties properties = new Properties();
        InputStream is = null;
        try {
            is = PropertiesUtils.class.getClassLoader().getResourceAsStream(PROPERTIES_NAME);
            properties.load(is);
            Enumeration<?> en = properties.propertyNames();
            Map<String, String> map = new HashMap<>(8);
            String key;
            while (en.hasMoreElements()) {
                key = (String) en.nextElement();
                map.put(key, properties.getProperty(key));
            }
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    /**
     * 向属性文件中写入信息
     *
     * @param filePath 属性文件路径
     * @param key      属性名称
     * @param value    属性值
     */
    public static void write(String filePath, String key, String value) {
        Properties properties = new Properties();
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(filePath);
            properties.load(is);
            os = new FileOutputStream(filePath);
            properties.setProperty(key, value);
            properties.store(os, "Update '" + key + "' value");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is, os);
        }
    }

    /**
     * 向默认的属性文件中写入信息
     *
     * @param key   属性名称
     * @param value 属性值
     */
    public static void writeDefault(String key, String value) {
        Properties properties = new Properties();
        InputStream is = null;
        OutputStream os = null;
        try {
            URL url = PropertiesUtils.class.getClassLoader().getResource(PROPERTIES_NAME);
            if (url == null) {
                throw new RuntimeException("default properties file is not exist");
            }
            String propertiesFile = PropertiesUtils.class.getClassLoader().getResource(PROPERTIES_NAME).getFile();
            is = new FileInputStream(propertiesFile);
            properties.load(is);
            os = new FileOutputStream(propertiesFile);
            properties.setProperty(key, value);
            properties.store(os, "Update '" + key + "' value");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is, os);
        }
    }
}
