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

import com.chanus.yuntao.utils.core.ZipUtils;
import org.junit.Test;

/**
 * ZipUtils 测试类
 *
 * @author Chanus
 * @date 2020-06-30 23:58:03
 * @since 1.0.0
 */
public class ZipUtilsTest {
    @Test
    public void compressTest() {
        String srcPath = "/Users/Chanus/Downloads/test";
        String zipPath = "/Users/Chanus/Documents/test";
        String zipFileName =  "test.zip";
        ZipUtils.compress(srcPath, zipPath, zipFileName);
    }

    @Test
    public void decompressTest() {
        String zipFilePath = "F:\\test2.zip";
        String decompressFilePath = "F:\\test1";
        ZipUtils.decompress(zipFilePath, decompressFilePath, true);
        decompressFilePath = "F:\\test2";
        ZipUtils.decompress(zipFilePath, decompressFilePath, false);
    }

    @Test
    public void decompressTest2() {
        String zipFilePath = "F:\\test2.zip";
        String decompressFilePath = "F:\\test1";
        ZipUtils.decompress(zipFilePath, decompressFilePath, false, true);
        decompressFilePath = "F:\\test2";
        ZipUtils.decompress(zipFilePath, decompressFilePath, false, false);
    }
}
