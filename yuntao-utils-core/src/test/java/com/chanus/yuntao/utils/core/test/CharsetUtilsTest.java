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

import com.chanus.yuntao.utils.core.CharsetUtils;
import org.junit.Test;

/**
 * CharsetUtils 测试类
 *
 * @author Chanus
 * @date 2020-07-02 17:32:29
 * @since 1.0.0
 */
public class CharsetUtilsTest {
    @Test
    public void charsetTest() {
        System.out.println(CharsetUtils.charset(null));
        System.out.println(CharsetUtils.charset(CharsetUtils.ISO_8859_1));
    }

    @Test
    public void parseTest() {
        System.out.println(CharsetUtils.parse(null));
        System.out.println(CharsetUtils.parse(CharsetUtils.ISO_8859_1));
        System.out.println(CharsetUtils.parse("123"));

        System.out.println(CharsetUtils.parse(null, CharsetUtils.CHARSET_ISO_8859_1));
        System.out.println(CharsetUtils.parse(CharsetUtils.UTF_8, CharsetUtils.CHARSET_ISO_8859_1));
        System.out.println(CharsetUtils.parse("123", CharsetUtils.CHARSET_ISO_8859_1));
    }

    @Test
    public void convertTest() {
        String source = "字符串转码";
        System.out.println(CharsetUtils.convert(source, CharsetUtils.UTF_8, CharsetUtils.GBK));
        System.out.println(CharsetUtils.convert(source, CharsetUtils.CHARSET_UTF_8, CharsetUtils.CHARSET_GBK));
    }

    @Test
    public void defaultCharsetNameTest() {
        System.out.println(CharsetUtils.defaultCharsetName());
    }

    @Test
    public void defaultCharsetTest() {
        System.out.println(CharsetUtils.defaultCharset());
    }
}
