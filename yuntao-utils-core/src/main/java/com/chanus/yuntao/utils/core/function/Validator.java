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
package com.chanus.yuntao.utils.core.function;

/**
 * 验证器接口
 *
 * @param <T> 验证的对象类型
 * @author Chanus
 * @date 2021-04-10 11:49:49
 * @since 1.6.0
 */
@FunctionalInterface
public interface Validator<T> {
    /**
     * 验证对象
     *
     * @param t 待验证的对象
     * @return {@code true} 验证通过；{@code false} 验证不通过
     */
    boolean verify(T t);
}
