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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Pattern;

/**
 * 数字工具类
 *
 * @author Chanus
 * @since 1.0.0
 */
public class NumberUtils {
    /**
     * 默认除法运算精度
     */
    private static final int DEFAULT_DIVIDE_SCALE = 10;
    /**
     * 金额数字格式化
     */
    private static final String MONEY_FORMATTER = ",##0.00";

    private NumberUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 提供精确的加法运算
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 和
     */
    public static double add(float v1, float v2) {
        return add(Float.toString(v1), Float.toString(v2)).doubleValue();
    }

    /**
     * 提供精确的加法运算
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 和
     */
    public static double add(float v1, double v2) {
        return add(Float.toString(v1), Double.toString(v2)).doubleValue();
    }

    /**
     * 提供精确的加法运算
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 和
     */
    public static double add(double v1, float v2) {
        return add(Double.toString(v1), Float.toString(v2)).doubleValue();
    }

    /**
     * 提供精确的加法运算
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 和
     */
    public static double add(double v1, double v2) {
        return add(Double.toString(v1), Double.toString(v2)).doubleValue();
    }

    /**
     * 提供精确的加法运算<br>
     * 如果传入多个值为 null 或者空，则返回0
     *
     * @param values 多个被加值
     * @return 和
     */
    public static BigDecimal add(Number... values) {
        BigDecimal result = BigDecimal.ZERO;

        if (ArrayUtils.isEmpty(values)) {
            return result;
        }

        for (Number value : values) {
            if (value != null) {
                result = result.add(new BigDecimal(value.toString()));
            }
        }

        return result;
    }

    /**
     * 提供精确的加法运算<br>
     * 如果传入多个值为 null 或者空，则返回0
     *
     * @param values 多个被加值
     * @return 和
     */
    public static BigDecimal add(String... values) {
        BigDecimal result = BigDecimal.ZERO;

        if (ArrayUtils.isEmpty(values)) {
            return result;
        }

        for (String value : values) {
            if (StringUtils.isNotBlank(value)) {
                result = result.add(new BigDecimal(value));
            }
        }

        return result;
    }

    /**
     * 提供精确的加法运算<br>
     * 如果传入多个值为 null 或者空，则返回0
     *
     * @param values 多个被加值
     * @return 和
     */
    public static BigDecimal add(BigDecimal... values) {
        BigDecimal result = BigDecimal.ZERO;

        if (ArrayUtils.isEmpty(values)) {
            return result;
        }

        for (BigDecimal value : values) {
            if (value != null) {
                result = result.add(value);
            }
        }

        return result;
    }

    /**
     * 提供精确的减法运算
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 差
     */
    public static double subtract(float v1, float v2) {
        return subtract(Float.toString(v1), Float.toString(v2)).doubleValue();
    }

    /**
     * 提供精确的减法运算
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 差
     */
    public static double subtract(float v1, double v2) {
        return subtract(Float.toString(v1), Double.toString(v2)).doubleValue();
    }

    /**
     * 提供精确的减法运算
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 差
     */
    public static double subtract(double v1, float v2) {
        return subtract(Double.toString(v1), Float.toString(v2)).doubleValue();
    }

    /**
     * 提供精确的减法运算
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 差
     */
    public static double subtract(double v1, double v2) {
        return subtract(Double.toString(v1), Double.toString(v2)).doubleValue();
    }

    /**
     * 提供精确的减法运算<br>
     * 如果传入多个值为 null 或者空，则返回0
     *
     * @param values 多个被减值
     * @return 差
     */
    public static BigDecimal subtract(Number... values) {
        BigDecimal result = BigDecimal.ZERO;

        if (ArrayUtils.isEmpty(values)) {
            return result;
        }

        if (values[0] != null) {
            result = new BigDecimal(values[0].toString());
        }

        int length = values.length;
        if (length == 1) {
            return result;
        }

        for (int i = 1; i < length; i++) {
            if (values[i] != null) {
                result = result.subtract(new BigDecimal(values[i].toString()));
            }
        }

        return result;
    }

    /**
     * 提供精确的减法运算<br>
     * 如果传入多个值为 null 或者空，则返回0
     *
     * @param values 多个被减值
     * @return 差
     */
    public static BigDecimal subtract(String... values) {
        BigDecimal result = BigDecimal.ZERO;

        if (ArrayUtils.isEmpty(values)) {
            return result;
        }

        if (StringUtils.isNotBlank(values[0])) {
            result = new BigDecimal(values[0]);
        }

        int length = values.length;
        if (length == 1) {
            return result;
        }

        for (int i = 1; i < length; i++) {
            if (StringUtils.isNotBlank(values[i])) {
                result = result.subtract(new BigDecimal(values[i]));
            }
        }

        return result;
    }

    /**
     * 提供精确的减法运算<br>
     * 如果传入多个值为 null 或者空，则返回0
     *
     * @param values 多个被减值
     * @return 差
     */
    public static BigDecimal subtract(BigDecimal... values) {
        BigDecimal result = BigDecimal.ZERO;

        if (ArrayUtils.isEmpty(values)) {
            return result;
        }

        if (values[0] != null) {
            result = values[0];
        }

        int length = values.length;
        if (length == 1) {
            return result;
        }

        for (int i = 1; i < length; i++) {
            if (values[i] != null) {
                result = result.subtract(values[i]);
            }
        }

        return result;
    }

    /**
     * 提供精确的乘法运算
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 积
     */
    public static double multiply(float v1, float v2) {
        return multiply(Float.toString(v1), Float.toString(v2)).doubleValue();
    }

    /**
     * 提供精确的乘法运算
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 积
     */
    public static double multiply(float v1, double v2) {
        return multiply(Float.toString(v1), Double.toString(v2)).doubleValue();
    }

    /**
     * 提供精确的乘法运算
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 积
     */
    public static double multiply(double v1, float v2) {
        return multiply(Double.toString(v1), Float.toString(v2)).doubleValue();
    }

    /**
     * 提供精确的乘法运算
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 积
     */
    public static double multiply(double v1, double v2) {
        return multiply(Double.toString(v1), Double.toString(v2)).doubleValue();
    }

    /**
     * 提供精确的乘法运算<br>
     * 如果传入多个值为 null 或者空，则返回0
     *
     * @param values 多个被乘值
     * @return 积
     */
    public static BigDecimal multiply(Number... values) {
        if (ArrayUtils.isEmpty(values)) {
            return BigDecimal.ZERO;
        }

        BigDecimal result = BigDecimal.ONE;
        for (Number value : values) {
            if (value == null) {
                continue;
            }
            if (isZero(value.toString())) {
                return BigDecimal.ZERO;
            }

            result = result.multiply(new BigDecimal(value.toString()));
        }

        return result;
    }

    /**
     * 提供精确的乘法运算<br>
     * 如果传入多个值为 null 或者空，则返回0
     *
     * @param values 多个被乘值
     * @return 积
     */
    public static BigDecimal multiply(String... values) {
        if (ArrayUtils.isEmpty(values)) {
            return BigDecimal.ZERO;
        }

        BigDecimal result = BigDecimal.ONE;
        for (String value : values) {
            if (StringUtils.isBlank(value)) {
                continue;
            }
            if (isZero(value)) {
                return BigDecimal.ZERO;
            }

            result = result.multiply(new BigDecimal(value));
        }

        return result;
    }

    /**
     * 提供精确的乘法运算<br>
     * 如果传入多个值为 null 或者空，则返回0
     *
     * @param values 多个被乘值
     * @return 积
     */
    public static BigDecimal multiply(BigDecimal... values) {
        if (ArrayUtils.isEmpty(values)) {
            return BigDecimal.ZERO;
        }

        BigDecimal result = BigDecimal.ONE;
        for (BigDecimal value : values) {
            if (value == null) {
                continue;
            }
            if (isZero(value.toString())) {
                return BigDecimal.ZERO;
            }

            result = result.multiply(value);
        }

        return result;
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况的时候，精确到小数点后10位，后面的四舍五入
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double divide(float v1, float v2) {
        return divide(v1, v2, DEFAULT_DIVIDE_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况的时候，精确到小数点后10位，后面的四舍五入
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double divide(float v1, double v2) {
        return divide(v1, v2, DEFAULT_DIVIDE_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况的时候，精确到小数点后10位，后面的四舍五入
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double divide(double v1, float v2) {
        return divide(v1, v2, DEFAULT_DIVIDE_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况的时候，精确到小数点后10位，后面的四舍五入
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double divide(double v1, double v2) {
        return divide(v1, v2, DEFAULT_DIVIDE_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况的时候，精确到小数点后10位，后面的四舍五入
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static BigDecimal divide(Number v1, Number v2) {
        return divide(v1, v2, DEFAULT_DIVIDE_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况的时候，精确到小数点后10位，后面的四舍五入
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static BigDecimal divide(String v1, String v2) {
        return divide(v1, v2, DEFAULT_DIVIDE_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，由 scale 指定精确度，后面的四舍五入
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 精确度，如果为负值，取绝对值
     * @return 两个参数的商
     */
    public static double divide(float v1, float v2, int scale) {
        return divide(v1, v2, scale, RoundingMode.HALF_UP);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，由 scale 指定精确度，后面的四舍五入
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 精确度，如果为负值，取绝对值
     * @return 两个参数的商
     */
    public static double divide(float v1, double v2, int scale) {
        return divide(v1, v2, scale, RoundingMode.HALF_UP);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，由 scale 指定精确度，后面的四舍五入
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 精确度，如果为负值，取绝对值
     * @return 两个参数的商
     */
    public static double divide(double v1, float v2, int scale) {
        return divide(v1, v2, scale, RoundingMode.HALF_UP);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，由 scale 指定精确度，后面的四舍五入
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 精确度，如果为负值，取绝对值
     * @return 两个参数的商
     */
    public static double divide(double v1, double v2, int scale) {
        return divide(v1, v2, scale, RoundingMode.HALF_UP);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，由 scale 指定精确度，后面的四舍五入
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 精确度，如果为负值，取绝对值
     * @return 两个参数的商
     */
    public static BigDecimal divide(Number v1, Number v2, int scale) {
        return divide(v1, v2, scale, RoundingMode.HALF_UP);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，由 scale 指定精确度，后面的四舍五入
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 精确度，如果为负值，取绝对值
     * @return 两个参数的商
     */
    public static BigDecimal divide(String v1, String v2, int scale) {
        return divide(v1, v2, scale, RoundingMode.HALF_UP);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，由 scale 指定精确度
     *
     * @param v1           被除数
     * @param v2           除数
     * @param scale        精确度，如果为负值，取绝对值
     * @param roundingMode 保留小数的模式 {@link RoundingMode}
     * @return 两个参数的商
     */
    public static double divide(float v1, float v2, int scale, RoundingMode roundingMode) {
        return divide(Float.toString(v1), Float.toString(v2), scale, roundingMode).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，由 scale 指定精确度
     *
     * @param v1           被除数
     * @param v2           除数
     * @param scale        精确度，如果为负值，取绝对值
     * @param roundingMode 保留小数的模式 {@link RoundingMode}
     * @return 两个参数的商
     */
    public static double divide(float v1, double v2, int scale, RoundingMode roundingMode) {
        return divide(Float.toString(v1), Double.toString(v2), scale, roundingMode).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，由 scale 指定精确度
     *
     * @param v1           被除数
     * @param v2           除数
     * @param scale        精确度，如果为负值，取绝对值
     * @param roundingMode 保留小数的模式 {@link RoundingMode}
     * @return 两个参数的商
     */
    public static double divide(double v1, float v2, int scale, RoundingMode roundingMode) {
        return divide(Double.toString(v1), Float.toString(v2), scale, roundingMode).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，由 scale 指定精确度
     *
     * @param v1           被除数
     * @param v2           除数
     * @param scale        精确度，如果为负值，取绝对值
     * @param roundingMode 保留小数的模式 {@link RoundingMode}
     * @return 两个参数的商
     */
    public static double divide(double v1, double v2, int scale, RoundingMode roundingMode) {
        return divide(Double.toString(v1), Double.toString(v2), scale, roundingMode).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，由 scale 指定精确度
     *
     * @param v1           被除数
     * @param v2           除数
     * @param scale        精确度，如果为负值，取绝对值
     * @param roundingMode 保留小数的模式 {@link RoundingMode}
     * @return 两个参数的商
     */
    public static BigDecimal divide(Number v1, Number v2, int scale, RoundingMode roundingMode) {
        return divide(v1.toString(), v2.toString(), scale, roundingMode);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，由 scale 指定精确度
     *
     * @param v1           被除数
     * @param v2           除数
     * @param scale        精确度，如果为负值，取绝对值
     * @param roundingMode 保留小数的模式 {@link RoundingMode}
     * @return 两个参数的商
     */
    public static BigDecimal divide(String v1, String v2, int scale, RoundingMode roundingMode) {
        return divide(new BigDecimal(v1), new BigDecimal(v2), scale, roundingMode);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，由 scale 指定精确度
     *
     * @param v1           被除数
     * @param v2           除数
     * @param scale        精确度，如果为负值，取绝对值
     * @param roundingMode 保留小数的模式 {@link RoundingMode}
     * @return 两个参数的商
     */
    public static BigDecimal divide(BigDecimal v1, BigDecimal v2, int scale, RoundingMode roundingMode) {
        if (null == v1) {
            return BigDecimal.ZERO;
        }
        if (scale < 0) {
            scale = -scale;
        }
        return v1.divide(v2, scale, roundingMode);
    }

    /**
     * 获取大于或等于商的整数
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static int ceilDiv(int v1, int v2) {
        int r = v1 / v2;
        if ((v1 ^ v2) > 0 && (r * v2 != v1)) {
            r++;
        }
        return r;
    }

    /**
     * 获取大于或等于商的整数
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static long ceilDiv(long v1, long v2) {
        long r = v1 / v2;
        if ((v1 ^ v2) > 0 && (r * v2 != v1)) {
            r++;
        }
        return r;
    }

    /**
     * 保留固定位数小数<br>
     * 采用四舍五入策略 {@link RoundingMode#HALF_UP}<br>
     * 例如保留2位小数：123.456789 =》 123.46
     *
     * @param v     值
     * @param scale 保留小数位数
     * @return 新值
     */
    public static BigDecimal round(double v, int scale) {
        return round(v, scale, RoundingMode.HALF_UP);
    }

    /**
     * 保留固定位数小数<br>
     * 采用四舍五入策略 {@link RoundingMode#HALF_UP}<br>
     * 例如保留2位小数：123.456789 =》 123.46
     *
     * @param numberStr 数字值的字符串表现形式
     * @param scale     保留小数位数
     * @return 新值
     */
    public static BigDecimal round(String numberStr, int scale) {
        return round(numberStr, scale, RoundingMode.HALF_UP);
    }

    /**
     * 保留固定位数小数<br>
     * 采用四舍五入策略 {@link RoundingMode#HALF_UP}<br>
     * 例如保留2位小数：123.456789 =》 123.46
     *
     * @param number 数字值
     * @param scale  保留小数位数
     * @return 新值
     */
    public static BigDecimal round(BigDecimal number, int scale) {
        return round(number, scale, RoundingMode.HALF_UP);
    }

    /**
     * 保留固定位数小数<br>
     * 例如保留四位小数：123.456789 =》 123.4567
     *
     * @param v            值
     * @param scale        保留小数位数，如果传入小于0，则默认0
     * @param roundingMode 保留小数的模式 {@link RoundingMode}
     * @return 新值
     */
    public static BigDecimal round(double v, int scale, RoundingMode roundingMode) {
        return round(Double.toString(v), scale, roundingMode);
    }

    /**
     * 保留固定位数小数<br>
     * 例如保留四位小数：123.456789 =》 123.4567
     *
     * @param numberStr    数字值的字符串表现形式
     * @param scale        保留小数位数，如果传入小于0，则默认0
     * @param roundingMode 保留小数的模式 {@link RoundingMode}，如果传入 null 则默认四舍五入
     * @return 新值
     */
    public static BigDecimal round(String numberStr, int scale, RoundingMode roundingMode) {
        if (scale < 0) {
            scale = 0;
        }
        return round(toBigDecimal(numberStr), scale, roundingMode);
    }

    /**
     * 保留固定位数小数<br>
     * 例如保留四位小数：123.456789 =》 123.4567
     *
     * @param number       数字值
     * @param scale        保留小数位数，如果传入小于0，则默认0
     * @param roundingMode 保留小数的模式 {@link RoundingMode}，如果传入null则默认四舍五入
     * @return 新值
     */
    public static BigDecimal round(BigDecimal number, int scale, RoundingMode roundingMode) {
        if (null == number) {
            number = BigDecimal.ZERO;
        }
        if (scale < 0) {
            scale = 0;
        }
        if (null == roundingMode) {
            roundingMode = RoundingMode.HALF_UP;
        }

        return number.setScale(scale, roundingMode);
    }

    /**
     * 四舍六入五成双计算法
     * <p>
     * 四舍六入五成双是一种比较精确比较科学的计数保留法，是一种数字修约规则。
     * </p>
     *
     * <pre>
     * 算法规则:
     * 四舍六入五考虑，
     * 五后非零就进一，
     * 五后皆零看奇偶，
     * 五前为偶应舍去，
     * 五前为奇要进一。
     * </pre>
     *
     * @param number 需要科学计算的数据
     * @param scale  保留的小数位
     * @return 结果
     */
    public static BigDecimal roundHalfEven(Number number, int scale) {
        return roundHalfEven(toBigDecimal(number), scale);
    }

    /**
     * 四舍六入五成双计算法
     * <p>
     * 四舍六入五成双是一种比较精确比较科学的计数保留法，是一种数字修约规则。
     * </p>
     *
     * <pre>
     * 算法规则:
     * 四舍六入五考虑，
     * 五后非零就进一，
     * 五后皆零看奇偶，
     * 五前为偶应舍去，
     * 五前为奇要进一。
     * </pre>
     *
     * @param value 需要科学计算的数据
     * @param scale 保留的小数位
     * @return 结果
     */
    public static BigDecimal roundHalfEven(BigDecimal value, int scale) {
        return round(value, scale, RoundingMode.HALF_EVEN);
    }

    /**
     * 格式化 double<br>
     * 对 {@link DecimalFormat} 做封装<br>
     *
     * @param pattern 格式 格式中主要以 # 和 0 两种占位符号来指定数字长度。0 表示如果位数不足则以 0 填充，# 表示只要有可能就把数字拉上这个位置。<br>
     *                <ul>
     *                <li>0 =》 取一位整数</li>
     *                <li>0.00 =》 取一位整数和两位小数</li>
     *                <li>00.000 =》 取两位整数和三位小数</li>
     *                <li># =》 取所有整数部分</li>
     *                <li>#.##% =》 以百分比方式计数，并取两位小数</li>
     *                <li>#.#####E0 =》 显示为科学计数法，并取五位小数</li>
     *                <li>,### =》 每三位以逗号进行分隔，例如：299,792,458</li>
     *                <li>光速大小为每秒,###米 =》 将格式嵌入文本</li>
     *                </ul>
     * @param value   值
     * @return 格式化后的值
     */
    public static String decimalFormat(String pattern, double value) {
        return new DecimalFormat(pattern).format(value);
    }

    /**
     * 格式化 double<br>
     * 对 {@link DecimalFormat} 做封装<br>
     *
     * @param pattern 格式 格式中主要以 # 和 0 两种占位符号来指定数字长度。0 表示如果位数不足则以 0 填充，# 表示只要有可能就把数字拉上这个位置。<br>
     *                <ul>
     *                <li>0 =》 取一位整数</li>
     *                <li>0.00 =》 取一位整数和两位小数</li>
     *                <li>00.000 =》 取两位整数和三位小数</li>
     *                <li># =》 取所有整数部分</li>
     *                <li>#.##% =》 以百分比方式计数，并取两位小数</li>
     *                <li>#.#####E0 =》 显示为科学计数法，并取五位小数</li>
     *                <li>,### =》 每三位以逗号进行分隔，例如：299,792,458</li>
     *                <li>光速大小为每秒,###米 =》 将格式嵌入文本</li>
     *                </ul>
     * @param value   值
     * @return 格式化后的值
     */
    public static String decimalFormat(String pattern, long value) {
        return new DecimalFormat(pattern).format(value);
    }

    /**
     * 格式化 double<br>
     * 对 {@link DecimalFormat} 做封装<br>
     *
     * @param pattern 格式 格式中主要以 # 和 0 两种占位符号来指定数字长度。0 表示如果位数不足则以 0 填充，# 表示只要有可能就把数字拉上这个位置。<br>
     *                <ul>
     *                <li>0 =》 取一位整数</li>
     *                <li>0.00 =》 取一位整数和两位小数</li>
     *                <li>00.000 =》 取两位整数和三位小数</li>
     *                <li># =》 取所有整数部分</li>
     *                <li>#.##% =》 以百分比方式计数，并取两位小数</li>
     *                <li>#.#####E0 =》 显示为科学计数法，并取五位小数</li>
     *                <li>,### =》 每三位以逗号进行分隔，例如：299,792,458</li>
     *                <li>光速大小为每秒,###米 =》 将格式嵌入文本</li>
     *                </ul>
     * @param value   值，支持BigDecimal、BigInteger、Number等类型
     * @return 格式化后的值
     */
    public static String decimalFormat(String pattern, Object value) {
        return new DecimalFormat(pattern).format(value);
    }

    /**
     * 格式化字符串类型数值<br>
     * 对 {@link DecimalFormat} 做封装<br>
     *
     * @param pattern 格式 格式中主要以 # 和 0 两种占位符号来指定数字长度。0 表示如果位数不足则以 0 填充，# 表示只要有可能就把数字拉上这个位置。<br>
     *                <ul>
     *                <li>0 =》 取一位整数</li>
     *                <li>0.00 =》 取一位整数和两位小数</li>
     *                <li>00.000 =》 取两位整数和三位小数</li>
     *                <li># =》 取所有整数部分</li>
     *                <li>#.##% =》 以百分比方式计数，并取两位小数</li>
     *                <li>#.#####E0 =》 显示为科学计数法，并取五位小数</li>
     *                <li>,### =》 每三位以逗号进行分隔，例如：299,792,458</li>
     *                <li>光速大小为每秒,###米 =》 将格式嵌入文本</li>
     *                </ul>
     * @param value   值，字符串类型数值
     * @return 格式化后的值
     * @since 1.2.3
     */
    public static String decimalFormat(String pattern, String value) {
        return new DecimalFormat(pattern).format(toBigDecimal(value));
    }

    /**
     * 格式化金额输出，每三位用逗号分隔
     *
     * @param value 金额
     * @return 格式化后的值
     */
    public static String decimalFormatMoney(double value) {
        return decimalFormat(MONEY_FORMATTER, value);
    }

    /**
     * 格式化金额输出，每三位用逗号分隔
     *
     * @param value 金额
     * @return 格式化后的值
     * @since 1.2.3
     */
    public static String decimalFormatMoney(Object value) {
        return decimalFormat(MONEY_FORMATTER, value);
    }

    /**
     * 格式化金额输出，每三位用逗号分隔
     *
     * @param value 金额
     * @return 格式化后的值
     * @since 1.2.3
     */
    public static String decimalFormatMoney(String value) {
        return decimalFormat(MONEY_FORMATTER, value);
    }

    /**
     * 格式化百分比，小数采用四舍五入方式
     *
     * @param number 值
     * @param scale  保留小数位数
     * @return 百分比
     */
    public static String formatPercent(double number, int scale) {
        final NumberFormat format = NumberFormat.getPercentInstance();
        format.setMaximumFractionDigits(scale);
        return format.format(number);
    }

    /**
     * 是否是质数（素数）<br>
     * 质数表的质数又称素数。指整数在一个大于1的自然数中，除了1和此整数自身外，没法被其他自然数整除的数。
     *
     * @param n 数字
     * @return 是否是质数
     */
    public static boolean isPrimes(int n) {
        if (n <= 1) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 计算阶乘
     * <ul>
     *     {@literal n! = n * (n-1) * ... * end}
     * </ul>
     *
     * @param start 阶乘起始
     * @param end   阶乘结束，必须小于起始
     * @return 结果
     */
    public static long factorial(long start, long end) {
        if (0L == start || start == end) {
            return 1L;
        }
        if (start < end) {
            return 0L;
        }
        return start * factorial(start - 1, end);
    }

    /**
     * 计算阶乘
     * <ul>
     *     {@literal n! = n * (n-1) * ... * 2 * 1}
     * </ul>
     *
     * @param n 阶乘起始
     * @return 结果
     */
    public static long factorial(long n) {
        return factorial(n, 1);
    }

    /**
     * 排列数计算<br>
     * 从 n 个不同元素中，任取 m（m≤n，m 与 n 均为自然数，下同）个不同的元素按照一定的顺序排成一列，叫做从 n 个不同元素中取出 m 个元素的一个排列；<br>
     * 从 n 个不同元素中取出 m（m≤n）个元素的所有排列的个数，叫做从 n 个不同元素中取出 m 个元素的排列数，用符号 A(n,m) 表示
     *
     * @param n 元素总数
     * @param m 参与选择的元素个数
     * @return 排列数
     */
    public static long permutation(long n, long m) {
        return factorial(n) / factorial(n - m);
    }

    /**
     * 组合数计算<br>
     * 从 n 个不同元素中，任取 m（m≤n）个元素并成一组，叫做从 n 个不同元素中取出 m 个元素的一个组合；<br>
     * 从 n 个不同元素中取出 m（m≤n）个元素的所有组合的个数，叫做从 n 个不同元素中取出 m 个元素的组合数。用符号 C(n,m) 表示
     *
     * @param n 元素总数
     * @param m 参与选择的元素个数
     * @return 组合数
     */
    public static long combination(long n, long m) {
        return factorial(n) / (factorial(m) * factorial(n - m));
    }

    /**
     * 最大公约数
     *
     * @param m 第一个值
     * @param n 第二个值
     * @return 最大公约数
     */
    public static int divisor(int m, int n) {
        while (m % n != 0) {
            int temp = m % n;
            m = n;
            n = temp;
        }
        return n;
    }

    /**
     * 最小公倍数
     *
     * @param m 第一个值
     * @param n 第二个值
     * @return 最小公倍数
     */
    public static int multiple(int m, int n) {
        return m * n / divisor(m, n);
    }

    /**
     * 获得数字对应的二进制字符串
     *
     * @param number 数字
     * @return 二进制字符串
     */
    public static String toBinaryString(Number number) {
        if (number instanceof Long) {
            return Long.toBinaryString((Long) number);
        } else if (number instanceof Integer) {
            return Integer.toBinaryString((Integer) number);
        } else {
            return Long.toBinaryString(number.longValue());
        }
    }

    /**
     * 二进制转 int
     *
     * @param binaryString 二进制字符串
     * @return int
     */
    public static int binaryToInt(String binaryString) {
        return Integer.parseInt(binaryString, 2);
    }

    /**
     * 二进制转 long
     *
     * @param binaryString 二进制字符串
     * @return long
     */
    public static long binaryToLong(String binaryString) {
        return Long.parseLong(binaryString, 2);
    }

    /**
     * 数字转 {@link BigDecimal}
     *
     * @param number 数字
     * @return {@link BigDecimal}
     */
    public static BigDecimal toBigDecimal(Number number) {
        if (null == number) {
            return BigDecimal.ZERO;
        }
        return toBigDecimal(number.toString());
    }

    /**
     * 数字转 {@link BigDecimal}
     *
     * @param number 数字
     * @return {@link BigDecimal}
     */
    public static BigDecimal toBigDecimal(String number) {
        return StringUtils.isBlank(number) ? BigDecimal.ZERO : new BigDecimal(number);
    }

    /**
     * 计算等份个数
     *
     * @param total 总数
     * @param part  每份的个数
     * @return 分成了几份
     */
    public static int count(int total, int part) {
        return (total % part == 0) ? (total / part) : (total / part + 1);
    }

    /**
     * 判断两个数字是否相邻，例如1和2相邻，1和3不相邻，判断方法为做差取绝对值判断是否为1
     *
     * @param number1 数字1
     * @param number2 数字2
     * @return 是否相邻，{@code true} 相邻，{@code false} 不相邻
     */
    public static boolean isBeside(long number1, long number2) {
        return Math.abs(number1 - number2) == 1;
    }

    /**
     * 判断两个数字是否相邻，例如1和2相邻，1和3不相邻，判断方法为做差取绝对值判断是否为1
     *
     * @param number1 数字1
     * @param number2 数字2
     * @return 是否相邻，{@code true} 相邻，{@code false} 不相邻
     */
    public static boolean isBeside(int number1, int number2) {
        return Math.abs(number1 - number2) == 1;
    }

    /**
     * 把给定的总数平均分成 N 份，返回每份的个数，当除以份数有余数时每份+1
     *
     * @param total     总数
     * @param partCount 份数
     * @return 每份的个数
     */
    public static int partValue(int total, int partCount) {
        return partValue(total, partCount, true);
    }

    /**
     * 把给定的总数平均分成 N 份，返回每份的个数<br>
     * 如果 {@code isPlusOneWhenHasRem} 为 {@code true}，则当除以份数有余数时每份+1，否则丢弃余数部分
     *
     * @param total               总数
     * @param partCount           份数
     * @param isPlusOneWhenHasRem 总数除以份数有余数时是否每份+1
     * @return 每份的个数
     */
    public static int partValue(int total, int partCount, boolean isPlusOneWhenHasRem) {
        int partValue = total / partCount;
        if (isPlusOneWhenHasRem && total % partCount != 0) {
            partValue++;
        }
        return partValue;
    }

    /**
     * 提供精确的幂运算
     *
     * @param number 底数
     * @param n      指数
     * @return 幂的积
     */
    public static BigDecimal pow(Number number, int n) {
        return pow(toBigDecimal(number), n);
    }

    /**
     * 提供精确的幂运算
     *
     * @param number 底数
     * @param n      指数
     * @return 幂的积
     */
    public static BigDecimal pow(BigDecimal number, int n) {
        return number.pow(n);
    }

    /**
     * 判断字符串是否为数值0
     *
     * @param number 字符串
     * @return 字符串是否为数值0，{@code true} 是，{@code false} 不是
     */
    public static boolean isZero(String number) {
        number = new BigDecimal(number).toString();
        String pattern = "[+\\-]?0+\\.?0*";
        return Pattern.matches(pattern, number);
    }
}
