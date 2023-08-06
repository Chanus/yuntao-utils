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
 * 匹配接口
 *
 * @param <T> 匹配的对象类型
 * @author Chanus
 * @since 1.2.5
 */
@FunctionalInterface
public interface Matcher<T> {
    /**
     * 给定对象是否匹配
     *
     * @param t 对象
     * @return {@code true} 匹配；{@code false} 不匹配
     */
    boolean match(T t);
}
