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

import com.chanus.yuntao.utils.core.FileUtils;
import com.chanus.yuntao.utils.core.HttpUtils;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.File;
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
        String url = "http://pv.sohu.com/cityjson?ie=utf-8";
        String ip = "125.78.96.179";
        String result1 = HttpUtils.get(url + "&ip=" + ip);
        System.out.println("result1 == " + result1);

        Map<String, Object> params = new HashMap<>();
        params.put("ip", ip);
        String result2 = HttpUtils.get(url, params);
        System.out.println("result2 == " + result2);

        // http://timor.tech/api/holiday
        String holiday = HttpUtils.get("http://timor.tech/api/holiday/year/2010-10");
        System.out.println(holiday);
    }

    @Test
    public void getAsynTest() {
        String url = "http://pv.sohu.com/cityjson?ie=utf-8";
        String ip = "125.78.96.179";
        HttpUtils.getAsyn(url + "&ip=" + ip, result -> System.out.println("result == " + result));

        Map<String, Object> params = new HashMap<>();
        params.put("ip", ip);
        HttpUtils.getAsyn(url, params, result -> System.out.println("result == " + result));
    }

    @Test
    public void postTest() {
        String url = "http://pv.sohu.com/cityjson?ie=utf-8";
        String ip = "125.78.96.179";
        String result1 = HttpUtils.post(url, "{\"ip\":\"" + ip + "\"}");
        System.out.println("result1 == " + result1);

        String result2 = HttpUtils.post(url + "&ip=" + ip);
        System.out.println("result2 == " + result2);

        Map<String, Object> params = new HashMap<>();
        params.put("ip", ip);
        String result3 = HttpUtils.post(url, params);
        System.out.println("result3 == " + result3);
    }

    @Test
    public void postAsynTest() {
        String url = "http://pv.sohu.com/cityjson?ie=utf-8";
        String ip = "125.78.96.179";
        HttpUtils.postAsyn(url, "{\"ip\":\"" + ip + "\"}", result -> System.out.println("result == " + result));
        HttpUtils.postAsyn(url + "&ip=" + ip, result -> System.out.println("result == " + result));
        Map<String, Object> params = new HashMap<>();
        params.put("ip", ip);
        HttpUtils.postAsyn(url, params, result -> System.out.println("result == " + result));
    }

    @Test
    public void uploadTest() {
        String url = "http://localhost:10080/yuntao_manager/index/user/upload?aaa=789";
        File file = new File("F:\\download\\111.txt");
        String result1 = HttpUtils.upload(url, file, "media");
        System.out.println(result1);
        String result2 = HttpUtils.upload(url, file);
        System.out.println(result2);
    }

    @Test
    public void downloadPostTest() {
        String url = "http://localhost:10080/yuntao_manager/verify-code";

        HttpUtils.downloadPost(url, "{\"test\":\"123456\"}", "F:\\download", "321.jpg");

        HttpUtils.downloadPost(url, "F:\\download", "213.jpg");

        BufferedInputStream bis = HttpUtils.downloadPost(url, "{\"test\":\"123456\"}");
        FileUtils.writeFromStream("F:\\download\\123.jpg", bis, false);

        BufferedInputStream bis2 = HttpUtils.downloadPost(url);
        FileUtils.writeFromStream("F:\\download\\132.jpg", bis2, false);
    }

    @Test
    public void downloadGetTest() {
        String url = "http://localhost:10080/yuntao_manager/verify-code?abc=123";
        HttpUtils.downloadGet(url, "F:\\download", "321.jpg");

        String url2 = "http://thirdwx.qlogo.cn/mmopen/0pQWlgoY1K0t3XKIaqdjljLVFdlIkCpib8Qrb9srYcf39LQIibQ3yiavbbBd5TxaO1zCZ7AEfCXDib4DOeiaBAKcfcg46M1VgJD26/132";
        HttpUtils.downloadGet(url2, "F:\\download");

        BufferedInputStream bis = HttpUtils.downloadGet(url);
        FileUtils.writeFromStream("F:\\download\\123.jpg", bis, false);
    }
}
