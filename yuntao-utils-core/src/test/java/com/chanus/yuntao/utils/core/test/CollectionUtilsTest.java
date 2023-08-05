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

import com.chanus.yuntao.utils.core.CollectionUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    }

    @Test
    public void joinTest() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("b");
        System.out.println(CollectionUtils.join(list, "-"));
        List<String[]> list2 = new ArrayList<>();
        list2.add(new String[]{"1", "2", "3"});
        list2.add(new String[]{"a", "b", "c"});
        list2.add(new String[]{"A", "S", "D"});
        System.out.println(CollectionUtils.join(list2, "-"));
    }

    @Test
    public void filterTest() {
        Integer[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        List<Integer> list = (List<Integer>) CollectionUtils.filter(Arrays.asList(array), t -> t % 2 == 0);
        System.out.println(list);
    }

    @Test
    public void editTest() {
        String[] array = {"a", "b", "c", "d", "e", "f", "g"};
        List<String> list = Arrays.asList(array);
        System.out.println(list);
        list = CollectionUtils.edit(list, String::toUpperCase);
        System.out.println(list);
    }

    @Test
    public void removeTest() {
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        System.out.println(list);
        List<Integer> list2 = CollectionUtils.remove(list, t -> t % 2 == 0);
        System.out.println(list);
        System.out.println(list2);
    }

    @Test
    public void removeAnyTest() {
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        System.out.println(list);
        List<Integer> list2 = CollectionUtils.removeAny(list, 1, 2, 3, 4, 5);
        System.out.println(list);
        System.out.println(list2);
    }

    @Test
    public void getAnyTest() {
        String[] array = {"a", "s", "d", "f", "a", "s", "d", "f", "a", "s", "d", "f"};
        System.out.println(CollectionUtils.getAny(Arrays.asList(array), 2, 3, 4, -2));// [d, f, a, d]
    }

    @Test
    public void indexOfAllTest() {
        String[] array = {"a", "s", "d", "f", "a", "s", "d", "f", "a", "s", "d", "f"};
        System.out.println(Arrays.toString(CollectionUtils.indexOfAll(Arrays.asList(array), "a"::equals)));// [0, 4, 8]
    }
}
