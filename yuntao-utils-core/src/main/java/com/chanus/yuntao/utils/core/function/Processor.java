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
 * 处理器接口
 *
 * @param <T> 待处理的对象类型
 * @param <R> 处理后的对象类型
 * @author Chanus
 * @date 2021-04-05 15:13:09
 * @since 1.6.0
 */
@FunctionalInterface
public interface Processor<T, R> {
    /**
     * 处理对象
     *
     * @param t 待处理的对象
     * @return 处理后的对象
     */
    R process(T t);
}
