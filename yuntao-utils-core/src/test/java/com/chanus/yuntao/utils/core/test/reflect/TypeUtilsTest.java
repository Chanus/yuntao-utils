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
package com.chanus.yuntao.utils.core.test.reflect;

import com.chanus.yuntao.utils.core.reflect.TypeUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * TypeUtils 测试类
 *
 * @author Chanus
 * @date 2020-09-22 11:32:19
 * @since 1.2.5
 */
public class TypeUtilsTest {
    @Test
    public void getClassTest() {
        System.out.println(TypeUtils.getClass(Integer.TYPE));
        System.out.println(TypeUtils.getClass(Long.TYPE));
    }

    @Test
    public void getTypeTest() throws NoSuchFieldException {
        Field field = User.class.getDeclaredField("userId");
        System.out.println(TypeUtils.getType(field).getTypeName());

        Field field2 = User.class.getDeclaredField("userNo");
        System.out.println(TypeUtils.getType(field2).getTypeName());
    }

    @Test
    public void getClassTest2() throws NoSuchFieldException {
        Field field = User.class.getDeclaredField("userId");
        System.out.println(TypeUtils.getClass(field).getName());

        Field field2 = User.class.getDeclaredField("userNo");
        System.out.println(TypeUtils.getClass(field2).getName());
    }

    @Test
    public void getFirstParamTypeTest() throws NoSuchMethodException {
        Method method = User.class.getMethod("speak", String.class);
        System.out.println(TypeUtils.getFirstParamType(method).getTypeName());
    }

    @Test
    public void getFirstParamClassTest() throws NoSuchMethodException {
        Method method = User.class.getMethod("speak", String.class);
        System.out.println(TypeUtils.getFirstParamClass(method).getName());
    }

    @Test
    public void getParamTypeTest() throws NoSuchMethodException {
        Method method = User.class.getMethod("speak", String.class, int.class);
        System.out.println(TypeUtils.getParamType(method, 1));
    }

    @Test
    public void getParamClassTest() throws NoSuchMethodException {
        Method method = User.class.getMethod("speak", String.class, int.class);
        System.out.println(TypeUtils.getParamClass(method, 1));
    }

    @Test
    public void getParamTypesTest() throws NoSuchMethodException {
        Method method = User.class.getMethod("speak", String.class, int.class);
        System.out.println(Arrays.toString(TypeUtils.getParamTypes(method)));
    }

    @Test
    public void getParamClassesTest() throws NoSuchMethodException {
        Method method = User.class.getMethod("speak", String.class, int.class);
        System.out.println(Arrays.toString(TypeUtils.getParamClasses(method)));
    }

    @Test
    public void getReturnTypeTest() throws NoSuchMethodException {
        Method method = User.class.getMethod("speak", String.class, int.class);
        System.out.println(TypeUtils.getReturnType(method));
    }

    @Test
    public void getReturnClassTest() throws NoSuchMethodException {
        Method method = User.class.getMethod("speak", String.class, int.class);
        System.out.println(TypeUtils.getReturnClass(method));
    }
}
