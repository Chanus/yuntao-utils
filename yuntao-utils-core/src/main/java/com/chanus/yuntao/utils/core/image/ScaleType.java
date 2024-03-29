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
package com.chanus.yuntao.utils.core.image;

import java.awt.*;

/**
 * 图片缩略算法类型
 *
 * @author Chanus
 * @since 1.0.0
 */
public enum ScaleType {
    /**
     * 默认
     */
    DEFAULT(Image.SCALE_DEFAULT),
    /**
     * 快速
     */
    FAST(Image.SCALE_FAST),
    /**
     * 平滑
     */
    SMOOTH(Image.SCALE_SMOOTH),
    /**
     * 使用 ReplicateScaleFilter 类中包含的图像缩放算法
     */
    REPLICATE(Image.SCALE_REPLICATE),
    /**
     * Area Averaging 算法
     */
    AREA_AVERAGING(Image.SCALE_AREA_AVERAGING);

    /**
     * 缩放方式
     */
    private final int value;

    /**
     * 构造
     *
     * @param value 缩放方式
     * @see Image#SCALE_DEFAULT
     * @see Image#SCALE_FAST
     * @see Image#SCALE_SMOOTH
     * @see Image#SCALE_REPLICATE
     * @see Image#SCALE_AREA_AVERAGING
     */
    ScaleType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
