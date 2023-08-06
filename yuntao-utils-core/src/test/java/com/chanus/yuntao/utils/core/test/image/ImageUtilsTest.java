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
package com.chanus.yuntao.utils.core.test.image;

import com.chanus.yuntao.utils.core.image.ImageUtils;
import org.junit.Test;

import java.awt.*;
import java.io.File;

/**
 * ImageUtils 测试类
 *
 * @author Chanus
 * @since 1.0.0
 */
public class ImageUtilsTest {
    @Test
    public void scaleTest1() {
        ImageUtils.scale(new File("F:\\1.jpg"), new File("F:\\2.jpg"), 0.2f);
    }

    @Test
    public void cutTest1() {
        ImageUtils.cut(new File("F:\\3.jpg"), new File("F:\\3_1.jpg"), new Rectangle(1452, 2384-1336, 1265, 1336));
    }

    @Test
    public void sliceTest1() {
        ImageUtils.slice(new File("F:\\0.jpg"), new File("F:\\slice"), 200, 150);
    }

    @Test
    public void sliceByRowsAndColsTest1() {
        ImageUtils.sliceByRowsAndCols(new File("F:\\0.jpg"), new File("F:\\slice"), 3, 3);
    }

    @Test
    public void convertTest1() {
        ImageUtils.convert(new File("F:\\0.jpg"), new File("F:\\1.png"));
    }

    @Test
    public void grayTest1() {
        ImageUtils.gray(new File("F:\\0.jpg"), new File("F:\\1.png"));
    }

    @Test
    public void pressTextTest1() {
        ImageUtils.pressText(new File("F:\\0.jpg"), new File("F:\\1.png"), "yuntao", Color.GRAY, null, 0, 0, 0.5f);
    }

    @Test
    public void rotateTest() {
        ImageUtils.rotate(new File("F:\\3_1.jpg"), 89, new File("F:\\3_2.jpg"));
    }
}
