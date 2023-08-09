/*
 * Copyright (c) 2023 Chanus
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
package com.chanus.yuntao.utils.core.date;

/**
 * 日期时间格式化模式
 *
 * @author Chanus
 * @since 1.8.0
 */
public class DatePattern {
    private DatePattern() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 年格式：yyyy
     */
    public static final String NORMAL_YEAR_PATTERN = "yyyy";
    /**
     * 年格式：yyyy年
     */
    public static final String CHINESE_YEAR_PATTERN = "yyyy年";
    /**
     * 年月格式：yyyy-MM
     */
    public static final String NORMAL_MONTH_PATTERN = "yyyy-MM";
    /**
     * 年月格式：yyyyMM
     */
    public static final String PURE_MONTH_PATTERN = "yyyyMM";
    /**
     * 年月格式：yyyy年MM月
     */
    public static final String CHINESE_MONTH_PATTERN = "yyyy年MM月";
    /**
     * 日期格式：yyyy-MM-dd
     */
    public static final String NORMAL_DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 日期格式：yyyyMMdd
     */
    public static final String PURE_DATE_PATTERN = "yyyyMMdd";
    /**
     * 日期格式：yyyy年MM月dd日
     */
    public static final String CHINESE_DATE_PATTERN = "yyyy年MM月dd日";
    /**
     * 时间格式：HH:mm:ss
     */
    public static final String NORMAL_TIME_PATTERN = "HH:mm:ss";
    /**
     * 时间格式：HHmmss
     */
    public static final String PURE_TIME_PATTERN = "HHmmss";
    /**
     * 时间格式：HH时mm分ss秒
     */
    public static final String CHINESE_TIME_PATTERN = "HH时mm分ss秒";
    /**
     * 时间格式，精确到毫秒：HH:mm:ss.SSS
     */
    public static final String NORMAL_TIME_MILLIS_PATTERN = "HH:mm:ss.SSS";
    /**
     * 时间格式，精确到毫秒：HHmmssSSS
     */
    public static final String PURE_TIME_MILLIS_PATTERN = "HHmmssSSS";
    /**
     * 日期时间格式，精确到分：yyyy-MM-dd HH:mm
     */
    public static final String NORMAL_DATETIME_MINUTE_PATTERN = "yyyy-MM-dd HH:mm";
    /**
     * 日期时间格式，精确到分：yyyyMMddHHmm
     */
    public static final String PURE_DATETIME_MINUTE_PATTERN = "yyyyMMddHHmm";
    /**
     * 日期时间格式，精确到分：yyyy年MM月dd日HH时mm分
     */
    public static final String CHINESE_DATETIME_MINUTE_PATTERN = "yyyy年MM月dd日HH时mm分";
    /**
     * 日期时间格式，精确到秒：yyyy-MM-dd HH:mm:ss
     */
    public static final String NORMAL_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期时间格式，精确到秒：yyyyMMddHHmmss
     */
    public static final String PURE_DATETIME_PATTERN = "yyyyMMddHHmmss";
    /**
     * 日期时间格式，精确到秒：yyyy年MM月dd日HH时mm分ss秒
     */
    public static final String CHINESE_DATETIME_PATTERN = "yyyy年MM月dd日HH时mm分ss秒";
    /**
     * 日期时间格式，精确到毫秒：yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final String NORMAL_DATETIME_MILLIS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    /**
     * 日期时间格式，精确到毫秒：yyyyMMddHHmmssSSS
     */
    public static final String PURE_DATETIME_MILLIS_PATTERN = "yyyyMMddHHmmssSSS";

    /**
     * HTTP 头中日期时间格式：EEE, dd MMM yyyy HH:mm:ss z
     */
    public static final String HTTP_DATETIME_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";
    /**
     * JDK 中日期时间格式：EEE MMM dd HH:mm:ss zzz yyyy
     */
    public static final String JDK_DATETIME_PATTERN = "EEE MMM dd HH:mm:ss zzz yyyy";
    /**
     * ISO8601 日期时间格式：yyyy-MM-dd'T'HH:mm:ss'Z'
     */
    public static final String UTC_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    /**
     * ISO8601 日期时间格式：yyyy-MM-dd'T'HH:mm:ss
     */
    public static final String UTC_SIMPLE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    /**
     * ISO8601 日期时间格式：yyyy-MM-dd'T'HH:mm:ssZ
     */
    public static final String UTC_WITH_ZONE_OFFSET_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";
    /**
     * ISO8601 日期时间格式：yyyy-MM-dd'T'HH:mm:ssXXX
     */
    public static final String UTC_WITH_XXX_OFFSET_PATTERN = "yyyy-MM-dd'T'HH:mm:ssXXX";
    /**
     * ISO8601 日期时间格式，精确到毫秒：yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
     */
    public static final String UTC_MILLIS_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    /**
     * ISO8601 日期时间格式，精确到毫秒：yyyy-MM-dd'T'HH:mm:ss.SSS
     */
    public static final String UTC_SIMPLE_MILLIS_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    /**
     * ISO8601 日期时间格式，精确到毫秒：yyyy-MM-dd'T'HH:mm:ss.SSSZ
     */
    public static final String UTC_MILLIS_WITH_ZONE_OFFSET_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    /**
     * ISO8601 日期时间格式，精确到毫秒：yyyy-MM-dd'T'HH:mm:ss.SSSXXX
     */
    public static final String UTC_MILLIS_WITH_XXX_OFFSET_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
}
