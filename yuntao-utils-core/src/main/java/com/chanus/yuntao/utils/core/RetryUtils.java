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
package com.chanus.yuntao.utils.core;

import java.util.concurrent.Callable;

/**
 * 异常重试工具类
 *
 * @author Chanus
 * @since 1.0.0
 */
public class RetryUtils {
    private RetryUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 回调结果检查
     */
    public interface ResultCheck {
        /**
         * 校验是否匹配
         *
         * @return {@code true} 匹配，{@code false} 不匹配
         */
        boolean matching();

        /**
         * 获取 JSON 字符串
         * @return JSON 字符串
         */
        String getJson();
    }

    /**
     * 在遇到异常时尝试重试
     *
     * @param retryLimit    重试次数
     * @param retryCallable 重试回调
     * @param <V>           泛型
     * @return V 结果
     */
    public static <V extends ResultCheck> V retryOnException(int retryLimit, Callable<V> retryCallable) {
        V v = null;
        for (int i = 0; i < retryLimit; i++) {
            try {
                v = retryCallable.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (null != v && v.matching()) {
                break;
            }
        }
        return v;
    }

    /**
     * 在遇到异常时尝试重试
     *
     * @param retryLimit    重试次数
     * @param sleepMillis   每次重试之后休眠的时间
     * @param retryCallable 重试回调
     * @param <V>           泛型
     * @return V 结果
     * @throws InterruptedException 线程异常
     */
    public static <V extends ResultCheck> V retryOnException(int retryLimit, long sleepMillis, Callable<V> retryCallable) throws InterruptedException {
        V v = null;
        for (int i = 0; i < retryLimit; i++) {
            try {
                v = retryCallable.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (null != v && v.matching()) {
                break;
            }
            Thread.sleep(sleepMillis);
        }
        return v;
    }
}
