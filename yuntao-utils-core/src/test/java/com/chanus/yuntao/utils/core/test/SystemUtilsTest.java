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

import com.chanus.yuntao.utils.core.SystemUtils;
import org.junit.Test;

import java.util.List;
import java.util.Set;

/**
 * SystemUtils 测试类
 *
 * @author Chanus
 * @since 1.0.0
 */
public class SystemUtilsTest {
    @Test
    public void test() {
        System.out.println("JDK的版本: " + SystemUtils.JDK_VERSION);
        System.out.println("JVM的编码: " + SystemUtils.JVM_ENCODING);
        System.out.println("操作系统名称: " + SystemUtils.OS_NAME);
        System.out.println("操作系统版本: " + SystemUtils.OS_VERSION);
        System.out.println("主机架构: " + SystemUtils.OS_ARCH);
        System.out.println("当前用户: " + SystemUtils.CURRENT_USER);
        System.out.println("当前用户的家目录: " + SystemUtils.CURRENT_USER_HOME);
        System.out.println("系统总的物理内存: " + SystemUtils.totalPhysicalMemorySize);
        System.out.println("1KB = " + SystemUtils.KB + "B");
        System.out.println("1MB = " + SystemUtils.MB + "B");
        System.out.println("1GB = " + SystemUtils.GB + "B");
        System.out.println("1TB = " + SystemUtils.TB + "B");
        System.out.println("可用的物理内存: " + SystemUtils.getFreePhysicalMemorySize());
        System.out.println("已使用的物理内存: " + SystemUtils.getUsedPhysicalMemorySize());
        System.out.println("JVM总的内存空间: " + SystemUtils.getTotalJVMMemorySize());
        System.out.println("JVM空闲的内存空间: " + SystemUtils.getFreeJVMMemorySize());
        System.out.println("JVM已用的内存空间: " + SystemUtils.getUsedJVMMemorySize());
        System.out.println("JVM最大的内存空间: " + SystemUtils.getMaxJVMMemorySize());
        System.out.println("JVM最大可用内存: " + SystemUtils.getUsableJVMMemorySize());
    }

    @Test
    public void getHostNameTest() {
        System.out.println(SystemUtils.getHostName());
    }

    @Test
    public void getHostMacTest() {
        Set<String> macSet = SystemUtils.getHostMac();
        macSet.forEach(System.out::println);

        System.out.println(macSet.contains("AC-DE-48-00-11-22"));
    }

    @Test
    public void getHostIPTest() {
        List<String> ips = SystemUtils.getHostIp();
        ips.forEach(System.out::println);
    }

    @Test
    public void getProcessorCountTest() {
        System.out.println("JVM 可用的处理器数量：" + SystemUtils.getProcessorCount());
    }
}
