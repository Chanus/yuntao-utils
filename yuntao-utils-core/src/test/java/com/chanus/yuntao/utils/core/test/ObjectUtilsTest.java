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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * ObjectUtils 测试类
 *
 * @author Chanus
 * @since 1.0.0
 */
public class ObjectUtilsTest {
    @Test
    public void equalTest() {
        System.out.println(ObjectUtils.equals(null, null));// true
        System.out.println(ObjectUtils.equals("", ""));// true
        System.out.println(ObjectUtils.equals("  ", ""));// false
        System.out.println(ObjectUtils.equals("1", ""));// false
        System.out.println(ObjectUtils.equals("123", "123"));// true
        System.out.println(ObjectUtils.equals(111, 111));// true
        System.out.println(ObjectUtils.equals(0.0, 0));// false
    }

    @Test
    public void notEqualTest() {
        System.out.println(ObjectUtils.notEquals(null, null));// false
        System.out.println(ObjectUtils.notEquals("", ""));// true
        System.out.println(ObjectUtils.notEquals("  ", ""));// true
        System.out.println(ObjectUtils.notEquals("1", ""));// true
        System.out.println(ObjectUtils.notEquals("123", "123"));// false
        System.out.println(ObjectUtils.notEquals(111, 111));// false
        System.out.println(ObjectUtils.notEquals(0.0, 0));// true
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
    public void verifyTest() {
        System.out.println(ObjectUtils.verify("abc123", s -> s.startsWith("abc")));// true
        System.out.println(ObjectUtils.verify("abc123", s -> s.startsWith("abcd")));// false
        System.out.println(ObjectUtils.verify("abc123", "abcd123", s -> s.startsWith("abc")));// abc123
        System.out.println(ObjectUtils.verify("abc123", "abcd123", s -> s.startsWith("abcd")));// abcd123
    }

    @Test
    public void toMapTest() {
        Student student = new Student();
        student.setId(1);
        student.setStudentNo("1001");
        student.setStudentName("Tom");
        student.setSex("M");
        student.setBirthday(LocalDate.now());

        Map<String, Object> map = ObjectUtils.toMap(student);
        map.forEach((k, v) -> System.out.println(k + "===" + v));
    }

    @Test
    public void toMapWithNullTest() {
        Student student = new Student();
        student.setId(1);
        student.setStudentNo("1001");
        student.setStudentName("Tom");
        student.setSex("M");
        student.setBirthday(LocalDate.now());

        Map<String, Object> map = ObjectUtils.toMapWithNull(student);
        map.forEach((k, v) -> System.out.println(k + "===" + v));
    }

    @Test
    public void mapToObjectTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("studentNo", "1001");
        map.put("studentName", "Tom");
        map.put("sex", "M");
        map.put("birthday", LocalDate.now());

        Student student = ObjectUtils.mapToObject(map, Student.class);
        assert student != null;
        System.out.println(student.toString());
    }

    public static class Student {
        private Integer id;
        private String studentNo;
        private String studentName;
        private Integer age;
        private String sex;
        private LocalDate birthday;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getStudentNo() {
            return studentNo;
        }

        public void setStudentNo(String studentNo) {
            this.studentNo = studentNo;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public LocalDate getBirthday() {
            return birthday;
        }

        public void setBirthday(LocalDate birthday) {
            this.birthday = birthday;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "id=" + id +
                    ", studentNo=" + studentNo +
                    ", studentName=" + studentName +
                    ", age=" + age +
                    ", sex=" + sex +
                    ", birthday=" + birthday +
                    '}';
        }
    }
}
