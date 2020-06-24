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

import com.chanus.yuntao.utils.core.HttpUtils;
import com.chanus.yuntao.utils.core.IpUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * HttpUtils 测试类
 *
 * @author Chanus
 * @date 2020-06-24 14:00:39
 * @since 1.0.0
 */
public class HttpUtilsTest {
    @Test
    public void getTest() {
        String url = "http://api.pi.do/api/v1/queryip";
        String ip = "36.4.195.250";
        String result1 = HttpUtils.get(url + "?ip=" +  ip);
        System.out.println("result1 == " + result1);

        Map<String, Object> params = new HashMap<>();
        params.put("ip", ip);
        String result2 = HttpUtils.get(url, params);
        System.out.println("result2 == " + result2);

        HttpUtils.getAsyn(url + "?ip=" +  ip, result -> System.out.println("result3 == " + result));

        IpUtils.IpAddress ipAddress = new IpUtils.IpAddress();
    }
}
