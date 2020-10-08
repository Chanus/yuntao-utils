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
 * 编辑器接口
 *
 * @param <T> 编辑的对象类型
 * @param <R> 返回的对象类型
 * @author Chanus
 * @date 2020-10-01 13:08:15
 * @since 1.3.0
 */
@FunctionalInterface
public interface Editor<T, R> {
    /**
     * 编辑对象
     *
     * @param t 待编辑的对象
     * @return 编辑后的对象，如果为 {@code null} 表示被移除
     */
    R edit(T t);
}
