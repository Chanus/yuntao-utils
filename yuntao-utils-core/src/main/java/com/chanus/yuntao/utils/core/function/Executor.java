/*
 * Copyright (c) 2022 Chanus
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
 * 执行器接口
 *
 * @author Chanus
 * @version 1.7.0
 * @since 2022-12-05 18:15:44
 */
@FunctionalInterface
public interface Executor {
    /**
     * 执行
     */
    void execute();
}
