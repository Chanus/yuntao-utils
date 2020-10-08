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

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

/**
 * I/O 工具类
 *
 * @author Chanus
 * @date 2020-06-20 17:20:10
 * @since 1.0.0
 */
public class IOUtils {
    /**
     * 关闭资源
     *
     * @param closeable 待关闭的资源
     */
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred.", e);
            }
        }
    }

    /**
     * 关闭资源
     *
     * @param closeables 待关闭的资源
     */
    public static void close(Closeable... closeables) {
        for (Closeable closeable : closeables) {
            close(closeable);
        }
    }

    /**
     * 安静的关闭资源
     *
     * @param closeable 待关闭的资源
     */
    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * 安静的关闭资源
     *
     * @param closeables 待关闭的资源
     */
    public static void closeQuietly(Closeable... closeables) {
        for (Closeable closeable : closeables) {
            closeQuietly(closeable);
        }
    }

    /**
     * 从缓存中刷出数据
     *
     * @param flushable {@link Flushable}
     * @since 1.3.0
     */
    public static void flush(Flushable flushable) {
        if (flushable != null) {
            try {
                flushable.flush();
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred.", e);
            }
        }
    }

    /**
     * 安静的从缓存中刷出数据
     *
     * @param flushable {@link Flushable}
     * @since 1.3.0
     */
    public static void flushQuietly(Flushable flushable) {
        if (flushable != null) {
            try {
                flushable.flush();
            } catch (IOException ignored) {
            }
        }
    }
}
