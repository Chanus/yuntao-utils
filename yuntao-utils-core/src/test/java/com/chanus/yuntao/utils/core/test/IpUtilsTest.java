/*
 * Copyright (c) 2023 Chanus
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

import com.chanus.yuntao.utils.core.IpUtils;
import org.junit.Test;

/**
 * IpUtils 测试类
 *
 * @author Chanus
 * @version 1.7.0
 * @since 2023-08-02 10:10:21
 */
public class IpUtilsTest {
    @Test
    public void internalIpTest() {
        String ip = "127.0.0.1";
        System.out.println(IpUtils.internalIp(ip));
        ip = "192.168.1.1";
        System.out.println(IpUtils.internalIp(ip));
        ip = "111.111.111.111";
        System.out.println(IpUtils.internalIp(ip));
    }
}
