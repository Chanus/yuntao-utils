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
package com.chanus.yuntao.utils.core.reflect;

import com.chanus.yuntao.utils.core.ArrayUtils;
import com.chanus.yuntao.utils.core.map.TableMap;

import java.lang.reflect.*;
import java.util.Map;

/**
 * 针对 {@link Type} 的工具类封装<br>
 * 最主要功能包括：
 * <pre>
 *     1. 获取方法的参数和返回值类型（包括 Type 和 Class）
 *     2. 获取泛型参数类型（包括对象的泛型参数或集合元素的泛型类型）
 * </pre>
 *
 * @author Chanus
 * @date 2020-09-18 09:10:05
 * @since 1.2.5
 */
public class TypeUtils {
    /**
     * 获得 {@link Type} 对应的原始类
     *
     * @param type {@link Type}
     * @return {@link Type} 对应的原始类，如果无法获取原始类，返回 {@code null}
     */
    public static Class<?> getClass(Type type) {
        if (type != null) {
            if (type instanceof Class) {
                return (Class<?>) type;
            } else if (type instanceof ParameterizedType) {
                return (Class<?>) ((ParameterizedType) type).getRawType();
            } else if (type instanceof TypeVariable) {
                return (Class<?>) ((TypeVariable<?>) type).getBounds()[0];
            } else if (type instanceof WildcardType) {
                final Type[] upperBounds = ((WildcardType) type).getUpperBounds();
                if (upperBounds.length == 1) {
                    return getClass(upperBounds[0]);
                }
            }
        }
        return null;
    }

    /**
     * 获取字段对应的 {@link Type} 类型，方法优先获取 GenericType，获取不到则获取 Type
     *
     * @param field 字段
     * @return 字段对应的 {@link Type} 类型，可能为 {@code null}
     */
    public static Type getType(Field field) {
        return field == null ? null : field.getGenericType();
    }

    /**
     * 获取字段的泛型类型
     *
     * @param clazz     Bean 类
     * @param fieldName 字段名
     * @return 字段的泛型类型
     * @since 1.3.0
     */
    public static Type getFieldType(Class<?> clazz, String fieldName) {
        return getType(ReflectUtils.getField(clazz, fieldName));
    }

    /**
     * 获取 {@link Field} 对应的原始类
     *
     * @param field 字段
     * @return {@link Field} 对应的原始类，如果无法获取原始类，返回 {@code null}
     */
    public static Class<?> getClass(Field field) {
        return field == null ? null : field.getType();
    }

    /**
     * 获取方法的第一个参数类型<br>
     * 优先获取方法的 GenericParameterTypes，如果获取不到，则获取 ParameterTypes
     *
     * @param method 方法
     * @return {@link Type}，可能为 {@code null}
     */
    public static Type getFirstParamType(Method method) {
        return getParamType(method, 0);
    }

    /**
     * 获取方法的第一个参数类
     *
     * @param method 方法
     * @return 方法的第一个参数类，可能为 {@code null}
     */
    public static Class<?> getFirstParamClass(Method method) {
        return getParamClass(method, 0);
    }

    /**
     * 获取方法的参数类型<br>
     * 优先获取方法的 GenericParameterTypes，如果获取不到，则获取 ParameterTypes
     *
     * @param method 方法
     * @param index  第几个参数的索引，从0开始计数
     * @return 方法的参数类型，可能为 {@code null}
     */
    public static Type getParamType(Method method, int index) {
        Type[] types = getParamTypes(method);
        if (types != null && types.length > index) {
            return types[index];
        }
        return null;
    }

    /**
     * 获取方法的参数类
     *
     * @param method 方法
     * @param index  第几个参数的索引，从0开始计数
     * @return 方法的参数类，可能为 {@code null}
     */
    public static Class<?> getParamClass(Method method, int index) {
        Class<?>[] classes = getParamClasses(method);
        if (classes != null && classes.length > index) {
            return classes[index];
        }
        return null;
    }

    /**
     * 获取方法的参数类型列表<br>
     * 优先获取方法的 GenericParameterTypes，如果获取不到，则获取 ParameterTypes
     *
     * @param method 方法
     * @return 方法的参数类型列表，可能为 {@code null}
     * @see Method#getGenericParameterTypes()
     * @see Method#getParameterTypes()
     */
    public static Type[] getParamTypes(Method method) {
        return method == null ? null : method.getGenericParameterTypes();
    }

    /**
     * 获取方法的参数类型列表
     *
     * @param method 方法
     * @return 方法的参数类型列表
     * @see Method#getGenericParameterTypes
     * @see Method#getParameterTypes
     */
    public static Class<?>[] getParamClasses(Method method) {
        return method == null ? null : method.getParameterTypes();
    }

    /**
     * 获取方法的返回值类型
     *
     * @param method 方法
     * @return 方法的返回值类型，可能为 {@code null}
     * @see Method#getGenericReturnType()
     * @see Method#getReturnType()
     */
    public static Type getReturnType(Method method) {
        return method == null ? null : method.getGenericReturnType();
    }

    /**
     * 获取方法的返回类型类
     *
     * @param method 方法
     * @return 方法的返回类型类
     * @see Method#getGenericReturnType
     * @see Method#getReturnType
     */
    public static Class<?> getReturnClass(Method method) {
        return method == null ? null : method.getReturnType();
    }

    /**
     * 获得给定类的第一个泛型参数
     *
     * @param type 被检查的类型，必须是已经确定泛型类型的类型
     * @return {@link Type}，可能为 {@code null}
     */
    public static Type getTypeArgument(Type type) {
        return getTypeArgument(type, 0);
    }

    /**
     * 获得给定类的泛型参数
     *
     * @param type  被检查的类型，必须是已经确定泛型类型的类
     * @param index 泛型类型的索引号，即第几个泛型类型
     * @return {@link Type}
     */
    public static Type getTypeArgument(Type type, int index) {
        final Type[] typeArguments = getTypeArguments(type);
        if (typeArguments != null && typeArguments.length > index) {
            return typeArguments[index];
        }
        return null;
    }

    /**
     * 获得指定类型中所有泛型参数类型
     *
     * @param type 类型
     * @return 指定类型中所有泛型参数类型
     */
    public static Type[] getTypeArguments(Type type) {
        if (type == null)
            return null;

        final ParameterizedType parameterizedType = toParameterizedType(type);
        return parameterizedType == null ? null : parameterizedType.getActualTypeArguments();
    }

    /**
     * 将 {@link Type} 转换为 {@link ParameterizedType}<br>
     * {@link ParameterizedType} 用于获取当前类或父类中泛型参数化后的类型<br>
     * 一般用于获取泛型参数具体的参数类型
     *
     * @param type {@link Type}
     * @return {@link ParameterizedType}
     */
    public static ParameterizedType toParameterizedType(Type type) {
        ParameterizedType result = null;
        if (type instanceof ParameterizedType) {
            result = (ParameterizedType) type;
        } else if (type instanceof Class) {
            final Class<?> clazz = (Class<?>) type;
            Type genericSuper = clazz.getGenericSuperclass();
            if (genericSuper == null || Object.class.equals(genericSuper)) {
                // 如果类没有父类，而是实现一些定义好的泛型接口，则取接口的Type
                final Type[] genericInterfaces = clazz.getGenericInterfaces();
                if (ArrayUtils.isNotEmpty(genericInterfaces)) {
                    // 默认取第一个实现接口的泛型Type
                    genericSuper = genericInterfaces[0];
                }
            }
            result = toParameterizedType(genericSuper);
        }
        return result;
    }

    /**
     * 获取指定泛型变量对应的真实类型<br>
     * 由于子类中泛型参数实现和父类（接口）中泛型定义位置是一一对应的，因此可以通过对应关系找到泛型实现类型<br>
     * 使用此方法注意：
     * <pre>
     *     1. superClass 必须是 clazz 的父类或者 clazz 实现的接口
     *     2. typeVariable 必须在 superClass 中声明
     * </pre>
     *
     * @param actualType      真实类型所在类，此类中记录了泛型参数对应的实际类型
     * @param typeDefineClass 泛型变量声明所在类或接口，此类中定义了泛型类型
     * @param typeVariables   泛型变量，需要的实际类型对应的泛型参数
     * @return 给定泛型参数对应的实际类型，如果无对应类型，返回 {@code null}
     */
    public static Type[] getActualTypes(Type actualType, Class<?> typeDefineClass, Type... typeVariables) {
        if (!typeDefineClass.isAssignableFrom(getClass(actualType))) {
            throw new IllegalArgumentException("Parameter [superClass] must be assignable from [clazz]");
        }

        // 泛型参数标识符列表
        final TypeVariable<?>[] typeVars = typeDefineClass.getTypeParameters();
        if (ArrayUtils.isEmpty(typeVars))
            return null;

        // 实际类型列表
        final Type[] actualTypeArguments = getTypeArguments(actualType);
        if (ArrayUtils.isEmpty(actualTypeArguments))
            return null;

        int size = Math.min(actualTypeArguments.length, typeVars.length);
        final Map<TypeVariable<?>, Type> tableMap = new TableMap<>(typeVars, actualTypeArguments);

        // 查找方法定义所在类或接口中此泛型参数的位置
        final Type[] result = new Type[size];
        for (int i = 0; i < typeVariables.length; i++) {
            //noinspection SuspiciousMethodCalls
            result[i] = (typeVariables[i] instanceof TypeVariable) ? tableMap.get(typeVariables[i]) : typeVariables[i];
        }
        return result;
    }

    /**
     * 获取指定泛型变量对应的真实类型<br>
     * 由于子类中泛型参数实现和父类（接口）中泛型定义位置是一一对应的，因此可以通过对应关系找到泛型实现类型<br>
     * 使用此方法注意：
     * <pre>
     *     1. superClass 必须是 clazz 的父类或者 clazz 实现的接口
     *     2. typeVariable 必须在 superClass 中声明
     * </pre>
     *
     * @param actualType      真实类型所在类，此类中记录了泛型参数对应的实际类型
     * @param typeDefineClass 泛型变量声明所在类或接口，此类中定义了泛型类型
     * @param typeVariable    泛型变量，需要的实际类型对应的泛型参数
     * @return 给定泛型参数对应的实际类型
     */
    public static Type getActualType(Type actualType, Class<?> typeDefineClass, Type typeVariable) {
        Type[] types = getActualTypes(actualType, typeDefineClass, typeVariable);
        if (ArrayUtils.isNotEmpty(types)) {
            return types[0];
        }
        return null;
    }

    /**
     * 是否是未知类型<br>
     * {@link Type} 为 {@code null} 或者 {@link TypeVariable} 都视为未知类型
     *
     * @param type 类型
     * @return {@code true} 是未知类型；{@code false} 不是未知类型
     */
    public static boolean isUnknow(Type type) {
        return type == null || type instanceof TypeVariable;
    }

    /**
     * 指定泛型数组中是否含有泛型变量
     *
     * @param types 泛型数组
     * @return {@code true} 含有泛型变量；{@code false} 不含有泛型变量
     */
    public static boolean hasTypeVeriable(Type... types) {
        for (Type type : types) {
            if (type instanceof TypeVariable) {
                return true;
            }
        }
        return false;
    }
}
