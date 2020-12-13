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

import com.chanus.yuntao.utils.core.UrlUtils;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * UrlUtils 测试类
 *
 * @author Chanus
 * @date 2020-07-07 15:00:17
 * @since 1.0.0
 */
public class UrlUtilsTest {
    @Test
    public void buildUrlTest() throws UnsupportedEncodingException {
        String host = "http://localhost/";
        String path = "query";
        Map<String, Object> queries = new HashMap<>();
        queries.put("a", "aaa");
        queries.put("b", 123456);
        queries.put("c", 3.1415926);
        queries.put("d", true);
        queries.put(null, "abcdefg");
        queries.put("e", "你好");
        queries.put("f", null);
        String url = UrlUtils.buildUrl(host, path, queries);
        System.out.println(url);
    }

    @Test
    public void getParamsMapTest() {
        String query = "a=aaa&b=bbb&c=ccc&d=ddd&e=e1&e=e2&e=e3";
        String duplicate = "-";
        Map<String, String> map = UrlUtils.getParamsMap(query, duplicate);
        map.forEach((x, y) -> System.out.println(x + " = " + y));
        System.out.println("---------------------------------------------");
        map = UrlUtils.getParamsMap(query, null);
        map.forEach((x, y) -> System.out.println(x + " = " + y));
    }

    @Test
    public void getParamsUriTest() {
        Map<String, Object> map = new HashMap<String, Object>() {
            private static final long serialVersionUID = -7346761257298985734L;

            {
                put("abc", "aaa");
                put("cba", "bbb");
                put("ccc", "ccc");
                put("drt", "ddd");
                put("zkj", "zzz");
                put("ydf", "yyy");
                put("xee", "xxx");
            }
        };
        System.out.println(UrlUtils.getParamsUri(map));
        System.out.println(UrlUtils.getParamsUri(map, "abc"));
    }

    @Test
    public void getParamValueTest() {
        String url = "http://www.test.com?a=aaa&b=bbb&c=ccc&d=ddd&e=e1,e2,e3";
        System.out.println(UrlUtils.getParamValue(url, "a"));// aaa
        System.out.println(UrlUtils.getParamValue(url, "e"));// e1,e2,e3
    }

    @Test
    public void setParamTest() {
        String url = "http://www.test.com";
        url = UrlUtils.setParam(url, "a", "aaa");
        System.out.println(url);// http://www.test.com?a=aaa
        url = UrlUtils.setParam(url, "b", "bbb");
        System.out.println(url);// http://www.test.com?a=aaa&b=bbb
        url = "http://www.test.com";
        Map<String, Object> map = new HashMap<String, Object>() {
            private static final long serialVersionUID = -7346761257298985734L;

            {
                put("a", "aaa");
                put("b", "bbb");
                put("c", "ccc");
                put("d", "ddd");
            }
        };
        System.out.println(UrlUtils.setParam(url, map));// http://www.test.com?a=aaa&b=bbb&c=ccc&d=ddd
    }

    @Test
    public void removeParamTest() {
        String url = "http://www.test.com?a=aaa&b=bbb&c=ccc&d=ddd&e=e1,e2,e3";
        url = UrlUtils.removeParam(url, "c");
        System.out.println(url);// http://www.test.com?a=aaa&b=bbb&d=ddd&e=e1,e2,e3

        url = "http://www.test.com?a=aaa&b=bbb&c=ccc&d=ddd&e=e1,e2,e3";
        url = UrlUtils.removeParam(url, "c", "d", "e");
        System.out.println(url);// http://www.test.com?a=aaa&b=bbb
    }
}
