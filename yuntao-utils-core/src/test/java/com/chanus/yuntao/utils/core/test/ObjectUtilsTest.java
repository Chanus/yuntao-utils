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

import com.chanus.yuntao.utils.core.ObjectUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * ObjectUtils 测试类
 *
 * @author Chanus
 * @date 2020-07-07 08:54:34
 * @since 1.0.0
 */
public class ObjectUtilsTest {
    @Test
    public void equalTest() {
        System.out.println(ObjectUtils.equal(null, null));// true
        System.out.println(ObjectUtils.equal("", ""));// true
        System.out.println(ObjectUtils.equal("  ", ""));// false
        System.out.println(ObjectUtils.equal("1", ""));// false
        System.out.println(ObjectUtils.equal("123", "123"));// true
        System.out.println(ObjectUtils.equal(111, 111));// true
        System.out.println(ObjectUtils.equal(0.0, 0));// false
    }

    @Test
    public void notEqualTest() {
        System.out.println(ObjectUtils.notEqual(null, null));// false
        System.out.println(ObjectUtils.notEqual("", ""));// true
        System.out.println(ObjectUtils.notEqual("  ", ""));// true
        System.out.println(ObjectUtils.notEqual("1", ""));// true
        System.out.println(ObjectUtils.notEqual("123", "123"));// false
        System.out.println(ObjectUtils.notEqual(111, 111));// false
        System.out.println(ObjectUtils.notEqual(0.0, 0));// true
    }

    @Test
    public void lengthTest() {
        System.out.println(ObjectUtils.length(""));// = 0
        System.out.println(ObjectUtils.length("   "));// = 3
        System.out.println(ObjectUtils.length("123456789"));// = 9
        System.out.println(ObjectUtils.length(new int[]{1, 2, 3, 4}));// = 4
        System.out.println(ObjectUtils.length(new ArrayList<String>() {
            private static final long serialVersionUID = -2854147902007489618L;

            {
                add("1");
                add("2");
            }
        }));// = 2
        System.out.println(ObjectUtils.length(new HashMap<String, String>() {
            private static final long serialVersionUID = -3716534662631266083L;

            {
                put("aaa", "AAA");
                put("bbb", "BBB");
                put("ccc", "CCC");
            }
        }));// = 3
    }

    @Test
    public void containsTest() {
        System.out.println(ObjectUtils.contains("", ""));// = true
        System.out.println(ObjectUtils.contains("   ", ""));// = true
        System.out.println(ObjectUtils.contains("123456789", "123"));// = true
        System.out.println(ObjectUtils.contains(new int[]{1, 2, 3, 4}, 3));// = true
        System.out.println(ObjectUtils.contains(new ArrayList<String>() {
            private static final long serialVersionUID = -2854147902007489618L;

            {
                add("1");
                add("2");
            }
        }, "2"));// = true
        System.out.println(ObjectUtils.contains(new HashMap<String, String>() {
            private static final long serialVersionUID = -3716534662631266083L;

            {
                put("aaa", "AAA");
                put("bbb", "BBB");
                put("ccc", "CCC");
            }
        }, "CCC"));// = true
    }

    @Test
    public void isEmptyTest() {
        System.out.println(ObjectUtils.isEmpty(""));// = true
        System.out.println(ObjectUtils.isEmpty("   "));// = false
        System.out.println(ObjectUtils.isEmpty("123456789"));// = false
        System.out.println(ObjectUtils.isEmpty(new int[]{1, 2, 3, 4}));// = false
        System.out.println(ObjectUtils.isEmpty(new ArrayList<String>() {
            private static final long serialVersionUID = -2854147902007489618L;

            {
                add("1");
                add("2");
            }
        }));// = false
        System.out.println(ObjectUtils.isEmpty(new HashMap<String, String>() {
            private static final long serialVersionUID = -3716534662631266083L;

            {
                put("aaa", "AAA");
                put("bbb", "BBB");
                put("ccc", "CCC");
            }
        }));// = false
    }

    @Test
    public void isNotEmptyTest() {
        System.out.println(ObjectUtils.isNotEmpty(""));// = false
        System.out.println(ObjectUtils.isNotEmpty("   "));// = true
        System.out.println(ObjectUtils.isNotEmpty("123456789"));// = true
        System.out.println(ObjectUtils.isNotEmpty(new int[]{1, 2, 3, 4}));// = true
        System.out.println(ObjectUtils.isNotEmpty(new ArrayList<String>() {
            private static final long serialVersionUID = -2854147902007489618L;

            {
                add("1");
                add("2");
            }
        }));// = true
        System.out.println(ObjectUtils.isNotEmpty(new HashMap<String, String>() {
            private static final long serialVersionUID = -3716534662631266083L;

            {
                put("aaa", "AAA");
                put("bbb", "BBB");
                put("ccc", "CCC");
            }
        }));// = true
    }

    @Test
    public void defaultIfNullTest() {
        System.out.println(ObjectUtils.defaultIfNull("", "123"));
        System.out.println(ObjectUtils.defaultIfNull(null, "123"));
        System.out.println(ObjectUtils.defaultIfNull("123456789", "123"));
        System.out.println(Arrays.toString(ObjectUtils.defaultIfNull(null, new int[]{1, 2, 3, 4})));
        System.out.println(ObjectUtils.defaultIfNull(null, new ArrayList<String>() {
            private static final long serialVersionUID = -2854147902007489618L;

            {
                add("1");
                add("2");
            }
        }));
        System.out.println(ObjectUtils.defaultIfNull(null, new HashMap<String, String>() {
            private static final long serialVersionUID = -3716534662631266083L;

            {
                put("aaa", "AAA");
                put("bbb", "BBB");
                put("ccc", "CCC");
            }
        }));
    }

    @Test
    public void defaultIfEmptyTest() {
        System.out.println(ObjectUtils.defaultIfEmpty(null, null));
        System.out.println(ObjectUtils.defaultIfEmpty(null, ""));
        System.out.println(ObjectUtils.defaultIfEmpty("", "zz"));
        System.out.println(ObjectUtils.defaultIfEmpty(" ", "zz"));
        System.out.println(ObjectUtils.defaultIfEmpty("abc", "zz"));
    }

    @Test
    public void defaultIfBlankTest() {
        System.out.println(ObjectUtils.defaultIfBlank(null, null));
        System.out.println(ObjectUtils.defaultIfBlank(null, ""));
        System.out.println(ObjectUtils.defaultIfBlank("", "zz"));
        System.out.println(ObjectUtils.defaultIfBlank(" ", "zz"));
        System.out.println(ObjectUtils.defaultIfBlank("abc", "zz"));
    }
}
