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
import com.chanus.yuntao.utils.core.IOUtils;
import com.chanus.yuntao.utils.core.StreamUtils;
import com.chanus.yuntao.utils.core.codec.Base64;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * Base64 测试类
 *
 * @author Chanus
 * @date 2020-07-07 17:26:49
 * @since 1.0.0
 */
public class Base64Test {
    @Test
    public void encodeTest() {
        String text = "Base64 编码测试";

        System.out.println(Arrays.toString(Base64.encode(text.getBytes(), true)));
        System.out.println(Arrays.toString(Base64.encode(text.getBytes(), false)));

        System.out.println(Base64.encode(text));
        System.out.println(Base64.encode(text, CharsetUtils.GBK));
        System.out.println(Base64.encode(text, CharsetUtils.CHARSET_GBK));
        System.out.println(Base64.encode(text.getBytes()));
        System.out.println(Base64.encode(StreamUtils.toUtf8Stream(text)));
        System.out.println(Arrays.toString(Base64.encode(text.getBytes(), true, true)));
    }

    @Test
    public void encodeUrlSafeTest() {
        String text = "Base64 编码测试";

        System.out.println(Arrays.toString(Base64.encodeUrlSafe(text.getBytes(), true)));
        System.out.println(Arrays.toString(Base64.encodeUrlSafe(text.getBytes(), false)));

        System.out.println(Base64.encodeUrlSafe(text));
        System.out.println(Base64.encodeUrlSafe(text, CharsetUtils.GBK));
        System.out.println(Base64.encodeUrlSafe(text, CharsetUtils.CHARSET_GBK));
        System.out.println(Base64.encodeUrlSafe(text.getBytes()));
        System.out.println(Base64.encodeUrlSafe(StreamUtils.toUtf8Stream(text)));
    }

    @Test
    public void decodeStrTest() {
        System.out.println(Base64.decodeStr("QmFzZTY0IOe8lueggea1i-ivlQ"));
        System.out.println(Base64.decodeStr("QmFzZTY0ILHgwuuy4srU", CharsetUtils.GBK));
        System.out.println(Base64.decodeStr("QmFzZTY0ILHgwuuy4srU", CharsetUtils.CHARSET_GBK));
    }

    @Test
    public void decodeToStreamTest() {
        OutputStream out = new ByteArrayOutputStream();
        Base64.decodeToStream("QmFzZTY0IOe8lueggea1i-ivlQ", out);
        System.out.println(out.toString());
        IOUtils.closeQuietly(out);
    }

    @Test
    public void decodeTest() {
        System.out.println(new String(Base64.decode("QmFzZTY0IOe8lueggea1i-ivlQ")));

        byte[] b = new byte[]{81, 109, 70, 122, 90, 84, 89, 48, 73, 79, 101, 56, 108, 117, 101, 103, 103, 101, 97, 49, 105, 43, 105, 118, 108, 81, 61, 61};
        System.out.println(new String(Base64.decode(b)));
    }
}
