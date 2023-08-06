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
package com.chanus.yuntao.utils.core.test.codec;

import com.chanus.yuntao.utils.core.CharsetUtils;
import com.chanus.yuntao.utils.core.codec.Base32;
import org.junit.Test;

/**
 * Base32 测试类
 *
 * @author Chanus
 * @since 1.0.0
 */
public class Base32Test {
    @Test
    public void encodeTest() {
        String text = "Base32 编码测试";
        System.out.println(Base32.encode(text.getBytes()));
        System.out.println(Base32.encode(text));
        System.out.println(Base32.encode(text, CharsetUtils.GBK));
        System.out.println(Base32.encode(text, CharsetUtils.CHARSET_GBK));
    }

    @Test
    public void decodeTest() {
        System.out.println(new String(Base32.decode2Bytes("IJQXGZJTGIQOPPEW46QIDZVVRPUK7FI")));
        System.out.println(Base32.decode("IJQXGZJTGIQOPPEW46QIDZVVRPUK7FI"));
        System.out.println(Base32.decode("IJQXGZJTGIQLDYGC5OZOFSWU", CharsetUtils.GBK));
        System.out.println(Base32.decode("IJQXGZJTGIQLDYGC5OZOFSWU", CharsetUtils.CHARSET_GBK));
    }
}
