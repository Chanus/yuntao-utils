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

import com.chanus.yuntao.utils.core.ObjectUtils;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * 日期时间格式化对象，适用于 {@link java.time.LocalDate}、{@link java.time.LocalTime}、{@link java.time.LocalDateTime}
 *
 * @author Chanus
 * @since 1.8.0
 */
public class DateFormatter {
    private DateFormatter() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 年格式：yyyy
     */
    public static final DateTimeFormatter NORMAL_YEAR_FORMATTER = createDateTimeFormatter(DatePattern.NORMAL_YEAR_PATTERN);
    /**
     * 年格式：yyyy年
     */
    public static final DateTimeFormatter CHINESE_YEAR_FORMATTER = createDateTimeFormatter(DatePattern.CHINESE_YEAR_PATTERN);
    /**
     * 年月格式：yyyy-MM
     */
    public static final DateTimeFormatter NORMAL_MONTH_FORMATTER = createDateTimeFormatter(DatePattern.NORMAL_MONTH_PATTERN);
    /**
     * 年月格式：yyyyMM
     */
    public static final DateTimeFormatter PURE_MONTH_FORMATTER = createDateTimeFormatter(DatePattern.PURE_MONTH_PATTERN);
    /**
     * 年月格式：yyyy年MM月
     */
    public static final DateTimeFormatter CHINESE_MONTH_FORMATTER = createDateTimeFormatter(DatePattern.CHINESE_MONTH_PATTERN);
    /**
     * 日期格式：yyyy-MM-dd
     */
    public static final DateTimeFormatter NORMAL_DATE_FORMATTER = createDateTimeFormatter(DatePattern.NORMAL_DATE_PATTERN);
    /**
     * 日期格式：yyyyMMdd
     */
    public static final DateTimeFormatter PURE_DATE_FORMATTER = createDateTimeFormatter(DatePattern.PURE_DATE_PATTERN);
    /**
     * 日期格式：yyyy年MM月dd日
     */
    public static final DateTimeFormatter CHINESE_DATE_FORMATTER = createDateTimeFormatter(DatePattern.CHINESE_DATE_PATTERN);
    /**
     * 时间格式：HH:mm:ss
     */
    public static final DateTimeFormatter NORMAL_TIME_FORMATTER = createDateTimeFormatter(DatePattern.NORMAL_TIME_PATTERN);
    /**
     * 时间格式：HHmmss
     */
    public static final DateTimeFormatter PURE_TIME_FORMATTER = createDateTimeFormatter(DatePattern.PURE_TIME_PATTERN);
    /**
     * 时间格式：HH时mm分ss秒
     */
    public static final DateTimeFormatter CHINESE_TIME_FORMATTER = createDateTimeFormatter(DatePattern.CHINESE_TIME_PATTERN);
    /**
     * 时间格式，精确到毫秒：HH:mm:ss.SSS
     */
    public static final DateTimeFormatter NORMAL_TIME_MILLIS_FORMATTER = createDateTimeFormatter(DatePattern.NORMAL_TIME_MILLIS_PATTERN);
    /**
     * 时间格式，精确到毫秒：HHmmssSSS
     */
    public static final DateTimeFormatter PURE_TIME_MILLIS_FORMATTER = createDateTimeFormatter(DatePattern.PURE_TIME_MILLIS_PATTERN);
    /**
     * 日期时间格式，精确到分：yyyy-MM-dd HH:mm
     */
    public static final DateTimeFormatter NORMAL_DATETIME_MINUTE_FORMATTER = createDateTimeFormatter(DatePattern.NORMAL_DATETIME_MINUTE_PATTERN);
    /**
     * 日期时间格式，精确到分：yyyyMMddHHmm
     */
    public static final DateTimeFormatter PURE_DATETIME_MINUTE_FORMATTER = createDateTimeFormatter(DatePattern.PURE_DATETIME_MINUTE_PATTERN);
    /**
     * 日期时间格式，精确到分：yyyy年MM月dd日HH时mm分
     */
    public static final DateTimeFormatter CHINESE_DATETIME_MINUTE_FORMATTER = createDateTimeFormatter(DatePattern.CHINESE_DATETIME_MINUTE_PATTERN);
    /**
     * 日期时间格式，精确到秒：yyyy-MM-dd HH:mm:ss
     */
    public static final DateTimeFormatter NORMAL_DATETIME_FORMATTER = createDateTimeFormatter(DatePattern.NORMAL_DATETIME_PATTERN);
    /**
     * 日期时间格式，精确到秒：yyyyMMddHHmmss
     */
    public static final DateTimeFormatter PURE_DATETIME_FORMATTER = createDateTimeFormatter(DatePattern.PURE_DATETIME_PATTERN);
    /**
     * 日期时间格式，精确到秒：yyyy年MM月dd日HH时mm分ss秒
     */
    public static final DateTimeFormatter CHINESE_DATETIME_FORMATTER = createDateTimeFormatter(DatePattern.CHINESE_DATETIME_PATTERN);
    /**
     * 日期时间格式，精确到毫秒：yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final DateTimeFormatter NORMAL_DATETIME_MILLIS_FORMATTER = createDateTimeFormatter(DatePattern.NORMAL_DATETIME_MILLIS_PATTERN);
    /**
     * 日期时间格式，精确到毫秒：yyyyMMddHHmmssSSS
     */
    public static final DateTimeFormatter PURE_DATETIME_MILLIS_FORMATTER = createDateTimeFormatter(DatePattern.PURE_DATETIME_MILLIS_PATTERN);

    /**
     * HTTP 头中日期时间格式：EEE, dd MMM yyyy HH:mm:ss z
     */
    public static final DateTimeFormatter HTTP_DATETIME_FORMATTER = createDateTimeFormatter(DatePattern.HTTP_DATETIME_PATTERN, Locale.US);
    /**
     * JDK 中日期时间格式：EEE MMM dd HH:mm:ss zzz yyyy
     */
    public static final DateTimeFormatter JDK_DATETIME_FORMATTER = createDateTimeFormatter(DatePattern.JDK_DATETIME_PATTERN, Locale.US);
    /**
     * ISO8601 日期时间格式：yyyy-MM-dd'T'HH:mm:ss'Z'
     */
    public static final DateTimeFormatter UTC_FORMATTER = createDateTimeFormatter(DatePattern.UTC_PATTERN);
    /**
     * ISO8601 日期时间格式：yyyy-MM-dd'T'HH:mm:ss
     */
    public static final DateTimeFormatter UTC_SIMPLE_FORMATTER = createDateTimeFormatter(DatePattern.UTC_SIMPLE_PATTERN);
    /**
     * ISO8601 日期时间格式：yyyy-MM-dd'T'HH:mm:ssZ
     */
    public static final DateTimeFormatter UTC_WITH_ZONE_OFFSET_FORMATTER = createDateTimeFormatter(DatePattern.UTC_WITH_ZONE_OFFSET_PATTERN);
    /**
     * ISO8601 日期时间格式：yyyy-MM-dd'T'HH:mm:ssXXX
     */
    public static final DateTimeFormatter UTC_WITH_XXX_OFFSET_FORMATTER = createDateTimeFormatter(DatePattern.UTC_WITH_XXX_OFFSET_PATTERN);
    /**
     * ISO8601 日期时间格式，精确到毫秒：yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
     */
    public static final DateTimeFormatter UTC_MILLIS_FORMATTER = createDateTimeFormatter(DatePattern.UTC_MILLIS_PATTERN);
    /**
     * ISO8601 日期时间格式，精确到毫秒：yyyy-MM-dd'T'HH:mm:ss.SSS
     */
    public static final DateTimeFormatter UTC_SIMPLE_MILLIS_FORMATTER = createDateTimeFormatter(DatePattern.UTC_SIMPLE_MILLIS_PATTERN);
    /**
     * ISO8601 日期时间格式，精确到毫秒：yyyy-MM-dd'T'HH:mm:ss.SSSZ
     */
    public static final DateTimeFormatter UTC_MILLIS_WITH_ZONE_OFFSET_FORMATTER = createDateTimeFormatter(DatePattern.UTC_MILLIS_WITH_ZONE_OFFSET_PATTERN);
    /**
     * ISO8601 日期时间格式，精确到毫秒：yyyy-MM-dd'T'HH:mm:ss.SSSXXX
     */
    public static final DateTimeFormatter UTC_MILLIS_WITH_XXX_OFFSET_FORMATTER = createDateTimeFormatter(DatePattern.UTC_MILLIS_WITH_XXX_OFFSET_PATTERN);

    /**
     * 创建 {@link DateTimeFormatter} 对象，并赋予默认时区和位置信息，默认值为系统默认值。
     *
     * @param pattern 日期格式
     * @return {@link DateTimeFormatter}
     */
    public static DateTimeFormatter createDateTimeFormatter(String pattern) {
        return DateTimeFormatter.ofPattern(pattern, Locale.getDefault()).withZone(ZoneId.systemDefault());
    }

    /**
     * 创建 {@link DateTimeFormatter} 对象，指定时区并赋予默认位置信息。
     *
     * @param pattern 日期格式
     * @param locale  地区，若为 {@code null} 则使用默认地区
     * @return {@link DateTimeFormatter}
     */
    public static DateTimeFormatter createDateTimeFormatter(String pattern, Locale locale) {
        return DateTimeFormatter.ofPattern(pattern, ObjectUtils.defaultIfNull(locale, Locale.getDefault()))
                .withZone(ZoneId.systemDefault());
    }

    /**
     * 创建 {@link DateTimeFormatter} 对象，指定时区和位置信息。
     *
     * @param pattern 日期格式
     * @param locale  地区，若为 {@code null} 则使用默认地区
     * @param zoneId  时区，若为 {@code null} 则使用默认时区
     * @return {@link DateTimeFormatter}
     */
    public static DateTimeFormatter createDateTimeFormatter(String pattern, Locale locale, ZoneId zoneId) {
        return DateTimeFormatter.ofPattern(pattern, ObjectUtils.defaultIfNull(locale, Locale.getDefault()))
                .withZone(ObjectUtils.defaultIfNull(zoneId, ZoneId.systemDefault()));
    }
}
