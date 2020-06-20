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

import org.junit.Test;

import java.util.*;

/**
 * CollectionUtils 测试类
 *
 * @author Chanus
 * @date 2020-06-20 14:16:48
 * @since 1.0.0
 */
public class CollectionUtilsTest {
    @Test
    public void isEmptyTest() {
        System.out.println("================= isEmpty(Collection<?> collection) =================");
        List<String> list = new ArrayList<>();
        System.out.println(CollectionUtils.isEmpty(list));
        list.add("a");
        list.add("b");
        list.add("b");
        System.out.println(CollectionUtils.isEmpty(list));

        System.out.println("================= isEmpty(Map<?, ?> map) =================");
        Map<String, Object> map = new HashMap<>();
        System.out.println(CollectionUtils.isEmpty(map));
        map.put("a", "A");
        map.put("b", "B");
        map.put("c", "C");
        System.out.println(CollectionUtils.isEmpty(map));

        System.out.println("================= isEmpty(Object[] objects) =================");
        String[] arrary = {};
        System.out.println(CollectionUtils.isEmpty(arrary));
        arrary = new String[3];
        System.out.println(CollectionUtils.isEmpty(arrary));
        arrary[0] = "a";
        arrary[1] = "b";
        arrary[2] = "c";
        System.out.println(CollectionUtils.isEmpty(arrary));
    }

    @Test
    public void sortMapByKeyTest() {
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("s", "S");
            put("a", "A");
            put("z", "Z");
            put("d", "D");
            put("g", "G");
            put("4", "4");
            put("1", "1");
        }};
        System.out.println("排序前：");
        Objects.requireNonNull(map).forEach((k, v) -> System.out.println(k + "=" + v));
        System.out.println("排序后：");
        Objects.requireNonNull(CollectionUtils.sortMapByKey(map)).forEach((k, v) -> System.out.println(k + "=" + v));
    }

    @Test
    public void containsTest() {
        String[] array = {"a", "s", "d", "f"};
        System.out.println(CollectionUtils.contains(array, "d"));
        System.out.println(CollectionUtils.contains(array, "b"));
    }

    @Test
    public void collectionJoinTest() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("b");
        System.out.println(CollectionUtils.join(list, "-"));
    }

    @Test
    public void mapJoinTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("a", "A");
        map.put("b", "B");
        map.put("c", "C");
        System.out.println(CollectionUtils.join(map, "=", "&"));
        System.out.println(CollectionUtils.join(map));
        System.out.println(CollectionUtils.keyJoin(map, "-"));
        System.out.println(CollectionUtils.valueJoin(map, "-"));
    }

    @Test
    public void arrayJoinTest() {
        String[] array = {"a", "s", "d", "f"};
        System.out.println(CollectionUtils.join(array, "-"));
        System.out.println(CollectionUtils.join(array));
    }

    @Test
    public void uniqueArrayTest() {
        String[] array = {"a", "s", "d", "f", "a", "s", "d", "f", "a", "s", "d", "f"};
        System.out.println(Arrays.toString(CollectionUtils.uniqueArray(array)));
    }

    @Test
    public void uniqueArray2StringTest() {
        String[] array = {"a", "s", "d", "f", "a", "s", "d", "f", "a", "s", "d", "f"};
        System.out.println(CollectionUtils.uniqueArray2String(array, "-"));
        System.out.println(CollectionUtils.uniqueArray2String(array));
    }
}
