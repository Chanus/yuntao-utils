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
package com.chanus.yuntao.utils.core.test.map;

import com.chanus.yuntao.utils.core.StringUtils;
import com.chanus.yuntao.utils.core.map.CustomMap;
import org.junit.Test;

/**
 * CustomMap 测试类
 *
 * @author Chanus
 * @date 2020-10-21 15:23:33
 * @since 1.4.0
 */
public class CustomMapTest {
    @Test
    public void constructorTest() {
        CustomMap map1 = new CustomMap();
        map1.put("aaa", 111);
        map1.put("bbb", 222);
        map1.forEach((k, v) -> System.out.println(StringUtils.format("key = {}, value = {}", k, v)));

        CustomMap map2 = new CustomMap("ccc", 333).putNext("ddd", 444);
        map2.forEach((k, v) -> System.out.println(StringUtils.format("key = {}, value = {}", k, v)));
    }

    @Test
    public void createTest() {
        CustomMap map1 = CustomMap.create();
        map1.put("aaa", 111);
        map1.put("bbb", 222);
        map1.forEach((k, v) -> System.out.println(StringUtils.format("key = {}, value = {}", k, v)));

        CustomMap map2 = CustomMap.create("ccc", 333).putNext("ddd", 444);
        map2.forEach((k, v) -> System.out.println(StringUtils.format("key = {}, value = {}", k, v)));
    }

    @Test
    public void putNextTest() {
        CustomMap map = CustomMap.create().putNext("aaa", 111).putNext("bbb", 222);
        map.forEach((k, v) -> System.out.println(StringUtils.format("key = {}, value = {}", k, v)));
    }

    @Test
    public void getStringValueTest() {
        CustomMap map = CustomMap.create().putNext("aaa", "111").putNext("bbb", "222");
        System.out.println(map.getStringValue("aaa"));
        System.out.println(map.getStringValue("ccc"));
    }
}
