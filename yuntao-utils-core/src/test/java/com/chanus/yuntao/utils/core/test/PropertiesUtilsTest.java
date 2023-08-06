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
package com.chanus.yuntao.utils.core.test;

import com.chanus.yuntao.utils.core.PropertiesUtils;
import org.junit.Test;

/**
 * PropertiesUtils 测试类
 *
 * @author Chanus
 * @since 1.0.0
 */
public class PropertiesUtilsTest {
    private final String path = "F:\\config.properties";

    @Test
    public void readTest() {
        System.out.println("====================== read(String filePath, String key) ======================");
        System.out.println(PropertiesUtils.read(path, "system.name"));
        System.out.println(PropertiesUtils.read(path, "system.version"));
        System.out.println(PropertiesUtils.read(path, "system.copyright"));

        System.out.println("====================== read(String filePath) ======================");
        System.out.println(PropertiesUtils.read(path));
    }

    @Test
    public void readDefaultTest() {
        System.out.println("====================== readDefault(String key) ======================");
        System.out.println(PropertiesUtils.readDefault("system.name"));
        System.out.println(PropertiesUtils.readDefault("system.version"));
        System.out.println(PropertiesUtils.readDefault("system.copyright"));

        System.out.println("====================== readDefault() ======================");
        System.out.println(PropertiesUtils.readDefault());
    }

    @Test
    public void writeTest() {
        PropertiesUtils.write(path, "system.test", "write test");
        System.out.println(PropertiesUtils.read(path, "system.test"));
    }

    @Test
    public void writeDefaultTest() {
        PropertiesUtils.writeDefault("system.test", "write test");
        System.out.println(PropertiesUtils.readDefault("system.test"));
    }
}
