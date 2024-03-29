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
package com.chanus.yuntao.utils.core.test.date;

import com.chanus.yuntao.utils.core.date.LocalDateTimeUtils;
import com.chanus.yuntao.utils.core.date.DateFormatter;
import com.chanus.yuntao.utils.core.date.DatePattern;
import org.junit.Test;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * LocalDateTimeUtils 测试类
 *
 * @author Chanus
 * @since 1.0.0
 */
public class LocalDateTimeUtilsTest {
    @Test
    public void formatTest() {
        System.out.println("-------------------format(ZonedDateTime zonedDateTime, DateTimeFormatter formatter)-------------------");
        ZonedDateTime zonedDateTime = LocalDateTime.now().atZone(ZoneId.systemDefault());
        System.out.println(zonedDateTime);
        System.out.println("EEE, dd MMM yyyy HH:mm:ss z: " + LocalDateTimeUtils.format(zonedDateTime, DateFormatter.HTTP_DATETIME_FORMATTER));
        System.out.println("EEE MMM dd HH:mm:ss zzz yyyy: " + LocalDateTimeUtils.format(zonedDateTime, DateFormatter.JDK_DATETIME_FORMATTER));
        System.out.println("yyyy-MM-dd'T'HH:mm:ss'Z': " + LocalDateTimeUtils.format(zonedDateTime, DateFormatter.UTC_FORMATTER));
        System.out.println("yyyy-MM-dd'T'HH:mm:ss: " + LocalDateTimeUtils.format(zonedDateTime, DateFormatter.UTC_SIMPLE_FORMATTER));
        System.out.println("yyyy-MM-dd'T'HH:mm:ssZ: " + LocalDateTimeUtils.format(zonedDateTime, DateFormatter.UTC_WITH_ZONE_OFFSET_FORMATTER));
        System.out.println("yyyy-MM-dd'T'HH:mm:ssXXX: " + LocalDateTimeUtils.format(zonedDateTime, DateFormatter.UTC_WITH_XXX_OFFSET_FORMATTER));
        System.out.println("yyyy-MM-dd'T'HH:mm:ss.SSS'Z': " + LocalDateTimeUtils.format(zonedDateTime, DateFormatter.UTC_MILLIS_FORMATTER));
        System.out.println("yyyy-MM-dd'T'HH:mm:ss.SSS: " + LocalDateTimeUtils.format(zonedDateTime, DateFormatter.UTC_SIMPLE_MILLIS_FORMATTER));
        System.out.println("yyyy-MM-dd'T'HH:mm:ss.SSSZ: " + LocalDateTimeUtils.format(zonedDateTime, DateFormatter.UTC_MILLIS_WITH_ZONE_OFFSET_FORMATTER));
        System.out.println("yyyy-MM-dd'T'HH:mm:ss.SSSXXX: " + LocalDateTimeUtils.format(zonedDateTime, DateFormatter.UTC_MILLIS_WITH_XXX_OFFSET_FORMATTER));

        System.out.println("-------------------format(LocalDateTime localDateTime, String pattern)-------------------");
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
        System.out.println("yyyy: " + LocalDateTimeUtils.format(localDateTime, DateFormatter.NORMAL_YEAR_FORMATTER));
        System.out.println("yyyy年: " + LocalDateTimeUtils.format(localDateTime, DateFormatter.CHINESE_YEAR_FORMATTER));
        System.out.println("yyyy-MM: " + LocalDateTimeUtils.format(localDateTime, DateFormatter.NORMAL_MONTH_FORMATTER));
        System.out.println("yyyyMM: " + LocalDateTimeUtils.format(localDateTime, DateFormatter.PURE_MONTH_FORMATTER));
        System.out.println("yyyy年MM月: " + LocalDateTimeUtils.format(localDateTime, DateFormatter.CHINESE_MONTH_FORMATTER));
        System.out.println("yyyy-MM-dd: " + LocalDateTimeUtils.format(localDateTime, DateFormatter.NORMAL_DATE_FORMATTER));
        System.out.println("yyyyMMdd: " + LocalDateTimeUtils.format(localDateTime, DateFormatter.PURE_DATE_FORMATTER));
        System.out.println("yyyy年MM月dd日: " + LocalDateTimeUtils.format(localDateTime, DateFormatter.CHINESE_DATE_FORMATTER));
        System.out.println("HH:mm:ss: " + LocalDateTimeUtils.format(localDateTime, DateFormatter.NORMAL_TIME_FORMATTER));
        System.out.println("HHmmss: " + LocalDateTimeUtils.format(localDateTime, DateFormatter.PURE_TIME_FORMATTER));
        System.out.println("HH时mm分ss秒: " + LocalDateTimeUtils.format(localDateTime, DateFormatter.CHINESE_TIME_FORMATTER));
        System.out.println("HH:mm:ss.SSS: " + LocalDateTimeUtils.format(localDateTime, DateFormatter.NORMAL_TIME_MILLIS_FORMATTER));
        System.out.println("HHmmssSSS: " + LocalDateTimeUtils.format(localDateTime, DateFormatter.PURE_TIME_MILLIS_FORMATTER));
        System.out.println("yyyy-MM-dd HH:mm: " + LocalDateTimeUtils.format(localDateTime, DateFormatter.NORMAL_DATETIME_MINUTE_FORMATTER));
        System.out.println("yyyyMMddHHmm: " + LocalDateTimeUtils.format(localDateTime, DateFormatter.PURE_DATETIME_MINUTE_FORMATTER));
        System.out.println("yyyy年MM月dd日HH时mm分: " + LocalDateTimeUtils.format(localDateTime, DateFormatter.CHINESE_DATETIME_MINUTE_FORMATTER));
        System.out.println("yyyy-MM-dd HH:mm:ss: " + LocalDateTimeUtils.format(localDateTime, DateFormatter.NORMAL_DATETIME_FORMATTER));
        System.out.println("yyyyMMddHHmmss: " + LocalDateTimeUtils.format(localDateTime, DateFormatter.PURE_DATETIME_FORMATTER));
        System.out.println("yyyy年MM月dd日HH时mm分ss秒: " + LocalDateTimeUtils.format(localDateTime, DateFormatter.CHINESE_DATETIME_FORMATTER));
        System.out.println("yyyy-MM-dd HH:mm:ss.SSS: " + LocalDateTimeUtils.format(localDateTime, DateFormatter.NORMAL_DATETIME_MILLIS_FORMATTER));
        System.out.println("yyyyMMddHHmmssSSS: " + LocalDateTimeUtils.format(localDateTime, DateFormatter.PURE_DATETIME_MILLIS_FORMATTER));
        System.out.println("EEE, dd MMM yyyy HH:mm:ss z: " + LocalDateTimeUtils.format(localDateTime, DateFormatter.HTTP_DATETIME_FORMATTER));
        System.out.println("EEE MMM dd HH:mm:ss zzz yyyy: " + LocalDateTimeUtils.format(localDateTime, DateFormatter.JDK_DATETIME_FORMATTER));

        System.out.println("-------------------format(LocalDate localDate, String pattern)-------------------");
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
        System.out.println(LocalDateTimeUtils.format(localDate, "yyyy-MM-dd"));
        System.out.println(LocalDateTimeUtils.format(localDate, "yyyy-MM"));

        System.out.println("-------------------format(LocalTime localTime, String pattern)-------------------");
        LocalTime localTime = LocalTime.now();
        System.out.println(localTime);
        System.out.println(LocalDateTimeUtils.format(localTime, "HH:mm:ss.SSS"));
        System.out.println(LocalDateTimeUtils.format(localTime, "HH:mm:ss"));
        System.out.println(LocalDateTimeUtils.format(localTime, "hh:mm:ss"));
        System.out.println(LocalDateTimeUtils.format(localTime, "HH:mm"));
    }

    @Test
    public void formatDateTimeTest() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(LocalDateTimeUtils.formatDateTime(localDateTime));
    }

    @Test
    public void formatDateTimeMillisTest() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(LocalDateTimeUtils.formatDateTimeMillis(localDateTime));
    }

    @Test
    public void formatDateTest() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(LocalDateTimeUtils.formatDate(localDateTime));
        LocalDate localDate = LocalDate.now();
        System.out.println(LocalDateTimeUtils.formatDate(localDate));
    }

    @Test
    public void formatTimeTest() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(LocalDateTimeUtils.formatTime(localDateTime));
        LocalTime localTime = LocalTime.now();
        System.out.println(LocalDateTimeUtils.formatTime(localTime));
    }

    @Test
    public void formatTimeMillisTest() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(LocalDateTimeUtils.formatTimeMillis(localDateTime));
        LocalTime localTime = LocalTime.now();
        System.out.println(LocalDateTimeUtils.formatTimeMillis(localTime));
    }

    @Test
    public void parseZonedDateTimeTest() {
        System.out.println("EEE, dd MMM yyyy HH:mm:ss z: " + LocalDateTimeUtils.parseZonedDateTime("Wed, 09 Aug 2023 15:32:42 CST", DateFormatter.HTTP_DATETIME_FORMATTER));
        System.out.println("EEE MMM dd HH:mm:ss zzz yyyy: " + LocalDateTimeUtils.parseZonedDateTime("Wed Aug 09 15:32:42 CST 2023", DateFormatter.JDK_DATETIME_FORMATTER));
        System.out.println("yyyy-MM-dd'T'HH:mm:ss'Z': " + LocalDateTimeUtils.parseZonedDateTime("2023-08-09T15:32:42Z", DateFormatter.UTC_FORMATTER));
        System.out.println("yyyy-MM-dd'T'HH:mm:ss: " + LocalDateTimeUtils.parseZonedDateTime("2023-08-09T15:35:18", DateFormatter.UTC_SIMPLE_FORMATTER));
        System.out.println("yyyy-MM-dd'T'HH:mm:ssZ: " + LocalDateTimeUtils.parseZonedDateTime("2023-08-09T15:32:42+0800", DateFormatter.UTC_WITH_ZONE_OFFSET_FORMATTER));
        System.out.println("yyyy-MM-dd'T'HH:mm:ssXXX: " + LocalDateTimeUtils.parseZonedDateTime("2023-08-09T15:32:42+08:00", DateFormatter.UTC_WITH_XXX_OFFSET_FORMATTER));
        System.out.println("yyyy-MM-dd'T'HH:mm:ss.SSS'Z': " + LocalDateTimeUtils.parseZonedDateTime("2023-08-09T15:32:42.028Z", DateFormatter.UTC_MILLIS_FORMATTER));
        System.out.println("yyyy-MM-dd'T'HH:mm:ss.SSS: " + LocalDateTimeUtils.parseZonedDateTime("2023-08-09T15:32:42.028", DateFormatter.UTC_SIMPLE_MILLIS_FORMATTER));
        System.out.println("yyyy-MM-dd'T'HH:mm:ss.SSSZ: " + LocalDateTimeUtils.parseZonedDateTime("2023-08-09T15:32:42.028+0800", DateFormatter.UTC_MILLIS_WITH_ZONE_OFFSET_FORMATTER));
        System.out.println("yyyy-MM-dd'T'HH:mm:ss.SSSXXX: " + LocalDateTimeUtils.parseZonedDateTime("2023-08-09T15:32:42.028+08:00", DateFormatter.UTC_MILLIS_WITH_XXX_OFFSET_FORMATTER));
        System.out.println("yyyy-MM-dd HH:mm:ss.SSS: " + LocalDateTimeUtils.parseZonedDateTime("2023-08-09 15:32:42.028", DateFormatter.NORMAL_DATETIME_MILLIS_FORMATTER));
        System.out.println("yyyy-MM-dd HH:mm: " + LocalDateTimeUtils.parseZonedDateTime("2023-08-09 15:32", DateFormatter.NORMAL_DATETIME_MINUTE_FORMATTER));
    }

    @Test
    public void parseDateTimeTest() {
        System.out.println("-------------------parseDateTime(String localDateTime, DateTimeFormatter formatter)-------------------");
        System.out.println(LocalDateTimeUtils.parseDateTime("2019-06-12 20:11:33.123", DateFormatter.NORMAL_DATETIME_MILLIS_FORMATTER));

        System.out.println("-------------------parseDateTime(String localDateTime, String pattern)-------------------");
        System.out.println(LocalDateTimeUtils.parseDateTime("2019-06-12 20:11:33.123", "yyyy-MM-dd HH:mm:ss.SSS"));
        System.out.println(LocalDateTimeUtils.parseDateTime("2019-06-12 20:11:33", "yyyy-MM-dd HH:mm:ss"));
        System.out.println(LocalDateTimeUtils.parseDateTime("2019年06月12日 20时11分33秒", "yyyy年MM月dd日 HH时mm分ss秒"));
        System.out.println(LocalDateTimeUtils.parseDateTime("2019-06-12 20:11", "yyyy-MM-dd HH:mm"));

        System.out.println("-------------------parseDateTime(String localDateTime)-------------------");
        System.out.println(LocalDateTimeUtils.parseDateTime("2019-06-12 20:11:33"));
    }

    @Test
    public void parseDateTimeMillisTest() {
        System.out.println(LocalDateTimeUtils.parseDateTimeMillis("2019-06-12 20:11:33.123"));
    }

    @Test
    public void parseDateTest() {
        System.out.println("-------------------parseDate(String localDate, DateTimeFormatter formatter)-------------------");
        System.out.println(LocalDateTimeUtils.parseDate("2019-06-12", DateFormatter.NORMAL_DATE_FORMATTER));

        System.out.println("-------------------parseDate(String localDate, String pattern)-------------------");
        System.out.println(LocalDateTimeUtils.parseDate("2019-06-12", "yyyy-MM-dd"));
        System.out.println(LocalDateTimeUtils.parseDate("2019年06月12日", "yyyy年MM月dd日"));

        System.out.println("-------------------parseDate(String localDate)-------------------");
        System.out.println(LocalDateTimeUtils.parseDate("2019-06-12"));
    }

    @Test
    public void parseTimeTest() {
        System.out.println("-------------------parseTime(String localTime, DateTimeFormatter formatter)-------------------");
        System.out.println(LocalDateTimeUtils.parseTime("20:11:33.123", DateFormatter.NORMAL_TIME_MILLIS_FORMATTER));

        System.out.println("-------------------parseTime(String localTime, String pattern)-------------------");
        System.out.println(LocalDateTimeUtils.parseTime("20:11:33.123", "HH:mm:ss.SSS"));
        System.out.println(LocalDateTimeUtils.parseTime("20:11:33", "HH:mm:ss"));
        System.out.println(LocalDateTimeUtils.parseTime("20时11分33秒", "HH时mm分ss秒"));
        System.out.println(LocalDateTimeUtils.parseTime("20:11", "HH:mm"));

        System.out.println("-------------------parseTime(String localTime)-------------------");
        System.out.println(LocalDateTimeUtils.parseTime("20:11:33"));
    }

    @Test
    public void parseTimeMillisTest() {
        System.out.println(LocalDateTimeUtils.parseTimeMillis("20:11:33.123"));
    }

    @Test
    public void nowTest() {
        System.out.println("-------------------now(DateTimeFormatter formatter)-------------------");
        System.out.println("nowDateTimeMillis: " + LocalDateTimeUtils.now(DatePattern.NORMAL_DATETIME_MILLIS_PATTERN));
        System.out.println("nowDateTime: " + LocalDateTimeUtils.now(DatePattern.NORMAL_DATETIME_PATTERN));
        System.out.println("nowDate: " + LocalDateTimeUtils.now(DatePattern.NORMAL_DATE_PATTERN));
        System.out.println("nowTime: " + LocalDateTimeUtils.now(DatePattern.NORMAL_TIME_PATTERN));

        System.out.println("-------------------now(String pattern)-------------------");
        System.out.println("nowDateTimeMillis: " + LocalDateTimeUtils.now("yyyy-MM-dd HH:mm:ss.SSS"));
        System.out.println("nowDateTime: " + LocalDateTimeUtils.now("yyyy-MM-dd HH:mm:ss"));
        System.out.println("nowDate: " + LocalDateTimeUtils.now("yyyy-MM-dd"));
        System.out.println("nowTime: " + LocalDateTimeUtils.now("HH:mm:ss"));
    }

    @Test
    public void nowDateTimeTest() {
        System.out.println("nowDateTime: " + LocalDateTimeUtils.nowDateTime());
    }

    @Test
    public void nowDateTimeMillisTest() {
        System.out.println("nowDateTimeMillis: " + LocalDateTimeUtils.nowDateTimeMillis());
    }

    @Test
    public void nowDateTest() {
        System.out.println("nowDate: " + LocalDateTimeUtils.nowDate());
    }

    @Test
    public void nowTimeTest() {
        System.out.println("nowTime: " + LocalDateTimeUtils.nowTime());
    }

    @Test
    public void nowTimeMillisTest() {
        System.out.println("nowTimeMillis: " + LocalDateTimeUtils.nowTimeMillis());
    }

    @Test
    public void yesterdayTest() {
        System.out.println("yesterday: " + LocalDateTimeUtils.yesterday());
    }

    @Test
    public void tomorrowTest() {
        System.out.println("tomorrow: " + LocalDateTimeUtils.tomorrow());
    }

    @Test
    public void monthTest() {
        System.out.println("this month: " + LocalDateTimeUtils.month());
    }

    @Test
    public void lastMonthTest() {
        System.out.println("last month: " + LocalDateTimeUtils.lastMonth());
    }

    @Test
    public void nextMonthTest() {
        System.out.println("next month: " + LocalDateTimeUtils.nextMonth());
    }

    @Test
    public void convertToDateTest() {
        System.out.println("LocalDateTime 转 Date：" + LocalDateTimeUtils.convertToDate(LocalDateTime.now()));
        System.out.println("LocalDate 转 Date：" + LocalDateTimeUtils.convertToDate(LocalDate.now()));
        System.out.println("ZonedDateTime 转 Date：" + LocalDateTimeUtils.convertToDate(ZonedDateTime.now()));
    }

    @Test
    public void convertToLocalDateTimeTest() {
        Date date = new Date();
        System.out.println("date：" + date);
        System.out.println("Date 转 LocalDateTime：" + LocalDateTimeUtils.convertToLocalDateTime(date));
        long timestamp = System.currentTimeMillis();
        System.out.println("timestamp：" + timestamp);
        System.out.println("timestamp 转 LocalDateTime：" + LocalDateTimeUtils.convertToLocalDateTime(timestamp));
    }

    @Test
    public void convertToLocalDateTest() {
        Date date = new Date();
        System.out.println("date：" + date);
        System.out.println("Date 转 LocalDate：" + LocalDateTimeUtils.convertToLocalDate(date));
        long timestamp = System.currentTimeMillis();
        System.out.println("timestamp：" + timestamp);
        System.out.println("timestamp 转 LocalDate：" + LocalDateTimeUtils.convertToLocalDate(timestamp));
    }

    @Test
    public void convertToZonedDateTimeTest() {
        Date date = new Date();
        System.out.println("date：" + date);
        System.out.println("Date 转 ZonedDateTime：" + LocalDateTimeUtils.convertToZonedDateTime(date));
        System.out.println("Date 转 ZonedDateTime：" + LocalDateTimeUtils.convertToZonedDateTime(date, ZoneId.of("Asia/Kolkata")));
        System.out.println("Date 转 ZonedDateTime：" + LocalDateTimeUtils.convertToZonedDateTime(date, "Asia/Kolkata"));
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("localDateTime：" + localDateTime);
        System.out.println("LocalDateTime 转 ZonedDateTime：" + LocalDateTimeUtils.convertToZonedDateTime(localDateTime));
        System.out.println("LocalDateTime 转 ZonedDateTime：" + LocalDateTimeUtils.convertToZonedDateTime(localDateTime, ZoneId.of("Asia/Kolkata")));
        System.out.println("LocalDateTime 转 ZonedDateTime：" + LocalDateTimeUtils.convertToZonedDateTime(localDateTime, "Asia/Kolkata"));
        LocalDate localDate = LocalDate.now();
        System.out.println("localDate：" + localDate);
        System.out.println("LocalDate 转 ZonedDateTime：" + LocalDateTimeUtils.convertToZonedDateTime(localDate));
        System.out.println("LocalDate 转 ZonedDateTime：" + LocalDateTimeUtils.convertToZonedDateTime(localDate, ZoneId.of("Asia/Kolkata")));
        System.out.println("LocalDate 转 ZonedDateTime：" + LocalDateTimeUtils.convertToZonedDateTime(localDate, "Asia/Kolkata"));
    }

    @Test
    public void convertTimeZoneTest() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println("zonedDateTime：" + zonedDateTime);
        System.out.println("转换时区：" + LocalDateTimeUtils.convertTimeZone(zonedDateTime, ZoneId.of("Asia/Kolkata")));
        System.out.println("转换时区：" + LocalDateTimeUtils.convertTimeZone(zonedDateTime, "Asia/Kolkata"));
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("localDateTime：" + localDateTime);
        System.out.println("转换时区：" + LocalDateTimeUtils.convertTimeZone(localDateTime, ZoneId.of("Asia/Kolkata")));
        System.out.println("转换时区：" + LocalDateTimeUtils.convertTimeZone(localDateTime, "Asia/Kolkata"));
        System.out.println("转换时区：" + LocalDateTimeUtils.convertTimeZone(localDateTime, ZoneId.of("Asia/Tokyo"), ZoneId.of("Asia/Kolkata")));
        System.out.println("转换时区：" + LocalDateTimeUtils.convertTimeZone(localDateTime, "Asia/Tokyo", "Asia/Kolkata"));
    }

    @Test
    public void getSecondsTest() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("localDateTime：" + localDateTime);
        System.out.println("seconds: " + LocalDateTimeUtils.getSeconds(localDateTime));
        System.out.println("seconds: " + LocalDateTimeUtils.getSeconds(localDateTime, ZoneId.of("Asia/Kolkata")));
        System.out.println("seconds: " + LocalDateTimeUtils.getSeconds(localDateTime, "Asia/Kolkata"));
    }

    @Test
    public void getMillisTest() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("localDateTime：" + localDateTime);
        System.out.println("millis: " + LocalDateTimeUtils.getMillis(localDateTime));
        System.out.println("millis: " + LocalDateTimeUtils.getMillis(LocalDateTimeUtils.parseDateTime("2021-03-21 17:28:01")));
        System.out.println("millis: " + LocalDateTimeUtils.getMillis(localDateTime, ZoneId.of("Asia/Kolkata")));
        System.out.println("millis: " + LocalDateTimeUtils.getMillis(localDateTime, "Asia/Kolkata"));
    }

    @Test
    public void getDayStartTimeTest() {
        LocalDateTime localDateTime = LocalDateTimeUtils.parseDateTime("2019-05-12 20:11:33");
        System.out.println("指定某一天：" + localDateTime);
        System.out.println("指定某一天开始时间：" + LocalDateTimeUtils.getDayStartTime(localDateTime));
    }

    @Test
    public void getDayEndTimeTest() {
        LocalDateTime localDateTime = LocalDateTimeUtils.parseDateTime("2019-05-12 20:11:33");
        System.out.println("指定某一天：" + localDateTime);
        System.out.println("指定某一天结束时间：" + LocalDateTimeUtils.getDayEndTime(localDateTime));
    }

    @Test
    public void getTodayStartTimeTest() {
        System.out.println("当天开始时间：" + LocalDateTimeUtils.getTodayStartTime());
    }

    @Test
    public void getTodayEndTimeTest() {
        System.out.println("当天结束时间：" + LocalDateTimeUtils.getTodayEndTime());
    }

    @Test
    public void getMonthStartTimeTest() {
        LocalDateTime localDateTime = LocalDateTimeUtils.parseDateTime("2019-05-12 20:11:33");
        System.out.println("指定某月开始时间：" + LocalDateTimeUtils.getMonthStartTime(localDateTime));
    }

    @Test
    public void getMonthEndTimeTest() {
        LocalDateTime localDateTime = LocalDateTimeUtils.parseDateTime("2019-05-12 20:11:33");
        System.out.println("指定某月结束时间：" + LocalDateTimeUtils.getMonthEndTime(localDateTime));
    }

    @Test
    public void getThisMonthStartTimeTest() {
        System.out.println("当月开始时间：" + LocalDateTimeUtils.getThisMonthStartTime());
    }

    @Test
    public void getThisMonthEndTimeTest() {
        System.out.println("当月结束时间：" + LocalDateTimeUtils.getThisMonthEndTime());
    }

    @Test
    public void getYearStartTimeTest() {
        LocalDateTime localDateTime = LocalDateTimeUtils.parseDateTime("2019-05-12 20:11:33");
        System.out.println("指定某年开始时间：" + LocalDateTimeUtils.getYearStartTime(localDateTime));
    }

    @Test
    public void getYearEndTimeTest() {
        LocalDateTime localDateTime = LocalDateTimeUtils.parseDateTime("2019-05-12 20:11:33");
        System.out.println("指定某年结束时间：" + LocalDateTimeUtils.getYearEndTime(localDateTime));
    }

    @Test
    public void getThisYearStartTimeTest() {
        System.out.println("当年开始时间：" + LocalDateTimeUtils.getThisYearStartTime());
    }

    @Test
    public void getThisYearEndTimeTest() {
        System.out.println("当年结束时间：" + LocalDateTimeUtils.getThisYearEndTime());
    }

    @Test
    public void plusTest() {
        LocalDateTime nowDateTime = LocalDateTime.now();
        System.out.println("当前日期时间：" + nowDateTime);
        System.out.println("当前日期时间加一年：" + LocalDateTimeUtils.plus(nowDateTime, 1, ChronoUnit.YEARS));
        System.out.println("当前日期时间加一月：" + LocalDateTimeUtils.plus(nowDateTime, 1, ChronoUnit.MONTHS));
        System.out.println("当前日期时间加一天：" + LocalDateTimeUtils.plus(nowDateTime, 1, ChronoUnit.DAYS));
        System.out.println("当前日期时间加一小时：" + LocalDateTimeUtils.plus(nowDateTime, 1, ChronoUnit.HOURS));
        System.out.println("当前日期时间加一分钟：" + LocalDateTimeUtils.plus(nowDateTime, 1, ChronoUnit.MINUTES));
        System.out.println("当前日期时间加一秒钟：" + LocalDateTimeUtils.plus(nowDateTime, 1, ChronoUnit.SECONDS));
        System.out.println("当前日期时间加一毫秒：" + LocalDateTimeUtils.plus(nowDateTime, 1, ChronoUnit.MILLIS));
        System.out.println("当前日期时间加一星期：" + LocalDateTimeUtils.plus(nowDateTime, 1, ChronoUnit.WEEKS));

        LocalDate nowDate = LocalDate.now();
        System.out.println("当前日期：" + nowDate);
        System.out.println("当前日期加一年：" + LocalDateTimeUtils.plus(nowDate, 1, ChronoUnit.YEARS));
        System.out.println("当前日期加一月：" + LocalDateTimeUtils.plus(nowDate, 1, ChronoUnit.MONTHS));
        System.out.println("当前日期加一天：" + LocalDateTimeUtils.plus(nowDate, 1, ChronoUnit.DAYS));
        System.out.println("当前日期加一星期：" + LocalDateTimeUtils.plus(nowDate, 1, ChronoUnit.WEEKS));

        LocalTime nowTime = LocalTime.now();
        System.out.println("当前时间：" + nowTime);
        System.out.println("当前时间加一小时：" + LocalDateTimeUtils.plus(nowTime, 1, ChronoUnit.HOURS));
        System.out.println("当前时间加一分钟：" + LocalDateTimeUtils.plus(nowTime, 1, ChronoUnit.MINUTES));
        System.out.println("当前时间加一秒钟：" + LocalDateTimeUtils.plus(nowTime, 1, ChronoUnit.SECONDS));
        System.out.println("当前时间加一毫秒：" + LocalDateTimeUtils.plus(nowTime, 1, ChronoUnit.MILLIS));
    }

    @Test
    public void plusNowTest() {
        System.out.println("当前日期时间：" + LocalDateTime.now());
        System.out.println("当前日期时间加一年：" + LocalDateTimeUtils.plusNowDateTime(1, ChronoUnit.YEARS));
        System.out.println("当前日期时间加一月：" + LocalDateTimeUtils.plusNowDateTime(1, ChronoUnit.MONTHS));
        System.out.println("当前日期时间加一天：" + LocalDateTimeUtils.plusNowDateTime(1, ChronoUnit.DAYS));
        System.out.println("当前日期时间加一小时：" + LocalDateTimeUtils.plusNowDateTime(1, ChronoUnit.HOURS));
        System.out.println("当前日期时间加一分钟：" + LocalDateTimeUtils.plusNowDateTime(1, ChronoUnit.MINUTES));
        System.out.println("当前日期时间加一秒钟：" + LocalDateTimeUtils.plusNowDateTime(1, ChronoUnit.SECONDS));
        System.out.println("当前日期时间加一毫秒：" + LocalDateTimeUtils.plusNowDateTime(1, ChronoUnit.MILLIS));
        System.out.println("当前日期时间加一星期：" + LocalDateTimeUtils.plusNowDateTime(1, ChronoUnit.WEEKS));

        System.out.println("当前日期：" + LocalDate.now());
        System.out.println("当前日期加一年：" + LocalDateTimeUtils.plusNowDate(1, ChronoUnit.YEARS));
        System.out.println("当前日期加一月：" + LocalDateTimeUtils.plusNowDate(1, ChronoUnit.MONTHS));
        System.out.println("当前日期加一天：" + LocalDateTimeUtils.plusNowDate(1, ChronoUnit.DAYS));
        System.out.println("当前日期加一星期：" + LocalDateTimeUtils.plusNowDate(1, ChronoUnit.WEEKS));

        System.out.println("当前时间：" + LocalTime.now());
        System.out.println("当前时间加一小时：" + LocalDateTimeUtils.plusNowTime(1, ChronoUnit.HOURS));
        System.out.println("当前时间加一分钟：" + LocalDateTimeUtils.plusNowTime(1, ChronoUnit.MINUTES));
        System.out.println("当前时间加一秒钟：" + LocalDateTimeUtils.plusNowTime(1, ChronoUnit.SECONDS));
        System.out.println("当前时间加一毫秒：" + LocalDateTimeUtils.plusNowTime(1, ChronoUnit.MILLIS));
    }

    @Test
    public void minusTest() {
        LocalDateTime nowDateTime = LocalDateTime.now();
        System.out.println("当前日期时间：" + nowDateTime);
        System.out.println("当前日期时间减一年：" + LocalDateTimeUtils.minus(nowDateTime, 1, ChronoUnit.YEARS));
        System.out.println("当前日期时间减一月：" + LocalDateTimeUtils.minus(nowDateTime, 1, ChronoUnit.MONTHS));
        System.out.println("当前日期时间减一天：" + LocalDateTimeUtils.minus(nowDateTime, 1, ChronoUnit.DAYS));
        System.out.println("当前日期时间减一小时：" + LocalDateTimeUtils.minus(nowDateTime, 1, ChronoUnit.HOURS));
        System.out.println("当前日期时间减一分钟：" + LocalDateTimeUtils.minus(nowDateTime, 1, ChronoUnit.MINUTES));
        System.out.println("当前日期时间减一秒钟：" + LocalDateTimeUtils.minus(nowDateTime, 1, ChronoUnit.SECONDS));
        System.out.println("当前日期时间减一毫秒：" + LocalDateTimeUtils.minus(nowDateTime, 1, ChronoUnit.MILLIS));
        System.out.println("当前日期时间减一星期：" + LocalDateTimeUtils.minus(nowDateTime, 1, ChronoUnit.WEEKS));

        LocalDate nowDate = LocalDate.now();
        System.out.println("当前日期：" + nowDate);
        System.out.println("当前日期减一年：" + LocalDateTimeUtils.minus(nowDate, 1, ChronoUnit.YEARS));
        System.out.println("当前日期减一月：" + LocalDateTimeUtils.minus(nowDate, 1, ChronoUnit.MONTHS));
        System.out.println("当前日期减一天：" + LocalDateTimeUtils.minus(nowDate, 1, ChronoUnit.DAYS));
        System.out.println("当前日期减一星期：" + LocalDateTimeUtils.minus(nowDate, 1, ChronoUnit.WEEKS));

        LocalTime nowTime = LocalTime.now();
        System.out.println("当前时间：" + nowTime);
        System.out.println("当前时间减一小时：" + LocalDateTimeUtils.minus(nowTime, 1, ChronoUnit.HOURS));
        System.out.println("当前时间减一分钟：" + LocalDateTimeUtils.minus(nowTime, 1, ChronoUnit.MINUTES));
        System.out.println("当前时间减一秒钟：" + LocalDateTimeUtils.minus(nowTime, 1, ChronoUnit.SECONDS));
        System.out.println("当前时间减一毫秒：" + LocalDateTimeUtils.minus(nowTime, 1, ChronoUnit.MILLIS));
    }

    @Test
    public void minusNowTest() {
        System.out.println("当前日期时间：" + LocalDateTime.now());
        System.out.println("当前日期时间减一年：" + LocalDateTimeUtils.minusNowDateTime(1, ChronoUnit.YEARS));
        System.out.println("当前日期时间减一月：" + LocalDateTimeUtils.minusNowDateTime(1, ChronoUnit.MONTHS));
        System.out.println("当前日期时间减一天：" + LocalDateTimeUtils.minusNowDateTime(1, ChronoUnit.DAYS));
        System.out.println("当前日期时间减一小时：" + LocalDateTimeUtils.minusNowDateTime(1, ChronoUnit.HOURS));
        System.out.println("当前日期时间减一分钟：" + LocalDateTimeUtils.minusNowDateTime(1, ChronoUnit.MINUTES));
        System.out.println("当前日期时间减一秒钟：" + LocalDateTimeUtils.minusNowDateTime(1, ChronoUnit.SECONDS));
        System.out.println("当前日期时间减一毫秒：" + LocalDateTimeUtils.minusNowDateTime(1, ChronoUnit.MILLIS));
        System.out.println("当前日期时间减一星期：" + LocalDateTimeUtils.minusNowDateTime(1, ChronoUnit.WEEKS));

        System.out.println("当前日期：" + LocalDate.now());
        System.out.println("当前日期减一年：" + LocalDateTimeUtils.minusNowDate(1, ChronoUnit.YEARS));
        System.out.println("当前日期减一月：" + LocalDateTimeUtils.minusNowDate(1, ChronoUnit.MONTHS));
        System.out.println("当前日期减一天：" + LocalDateTimeUtils.minusNowDate(1, ChronoUnit.DAYS));
        System.out.println("当前日期减一星期：" + LocalDateTimeUtils.minusNowDate(1, ChronoUnit.WEEKS));

        System.out.println("当前时间：" + LocalTime.now());
        System.out.println("当前时间减一小时：" + LocalDateTimeUtils.minusNowTime(1, ChronoUnit.HOURS));
        System.out.println("当前时间减一分钟：" + LocalDateTimeUtils.minusNowTime(1, ChronoUnit.MINUTES));
        System.out.println("当前时间减一秒钟：" + LocalDateTimeUtils.minusNowTime(1, ChronoUnit.SECONDS));
        System.out.println("当前时间减一毫秒：" + LocalDateTimeUtils.minusNowTime(1, ChronoUnit.MILLIS));
    }

    @Test
    public void intervalTest() {
        LocalDateTime start = LocalDateTimeUtils.parseDateTimeMillis("2018-05-12 20:11:33.123");
        LocalDateTime end = LocalDateTimeUtils.parseDateTimeMillis("2019-05-12 20:11:33.123");
        System.out.println("2018-05-12 20:11:33.123 和 2019-05-12 20:11:33.123");
        System.out.println("相差年数：" + LocalDateTimeUtils.interval(start, end, ChronoUnit.YEARS));// 忽略时间
        System.out.println("相差月数：" + LocalDateTimeUtils.interval(start, end, ChronoUnit.MONTHS));// 忽略时间
        System.out.println("相差天数：" + LocalDateTimeUtils.interval(start, end, ChronoUnit.DAYS));
        System.out.println("相差小时数：" + LocalDateTimeUtils.interval(start, end, ChronoUnit.HOURS));
        System.out.println("相差分钟数：" + LocalDateTimeUtils.interval(start, end, ChronoUnit.MINUTES));
        System.out.println("相差秒钟数：" + LocalDateTimeUtils.interval(start, end, ChronoUnit.SECONDS));
        System.out.println("相差毫秒数：" + LocalDateTimeUtils.interval(start, end, ChronoUnit.MILLIS));
        System.out.println("相差星期数：" + LocalDateTimeUtils.interval(start, end, ChronoUnit.WEEKS));

        LocalDate start2 = LocalDateTimeUtils.parseDate("2019-05-12");
        LocalDate end2 = LocalDateTimeUtils.parseDate("2020-05-12");
        System.out.println("2019-05-12 和 2020-05-12");
        System.out.println("相差年数：" + LocalDateTimeUtils.interval(start2, end2, ChronoUnit.YEARS));
        System.out.println("相差月数：" + LocalDateTimeUtils.interval(start2, end2, ChronoUnit.MONTHS));
        System.out.println("相差天数：" + LocalDateTimeUtils.interval(start2, end2, ChronoUnit.DAYS));
        System.out.println("相差星期数：" + LocalDateTimeUtils.interval(start2, end2, ChronoUnit.WEEKS));

        LocalTime start3 = LocalDateTimeUtils.parseTimeMillis("20:11:33.123");
        LocalTime end3 = LocalDateTimeUtils.parseTimeMillis("22:12:33.123");
        System.out.println("20:11:33.123 和 22:12:33.123");
        System.out.println("相差小时数：" + LocalDateTimeUtils.interval(start3, end3, ChronoUnit.HOURS));
        System.out.println("相差分钟数：" + LocalDateTimeUtils.interval(start3, end3, ChronoUnit.MINUTES));
        System.out.println("相差秒钟数：" + LocalDateTimeUtils.interval(start3, end3, ChronoUnit.SECONDS));
        System.out.println("相差毫秒数：" + LocalDateTimeUtils.interval(start3, end3, ChronoUnit.MILLIS));
    }

    @Test
    public void compareTest() {
        LocalDateTime source = LocalDateTimeUtils.parseDateTimeMillis("2018-05-12 20:11:33.123");
        LocalDateTime target = LocalDateTimeUtils.parseDateTimeMillis("2019-05-12 20:11:33.123");
        System.out.println("比较日期时间：" + LocalDateTimeUtils.compare(source, target));

        LocalDate source2 = LocalDateTimeUtils.parseDate("2021-05-12");
        LocalDate target2 = LocalDateTimeUtils.parseDate("2020-05-12");
        System.out.println("比较日期：" + LocalDateTimeUtils.compare(source2, target2));

        LocalTime source3 = LocalDateTimeUtils.parseTimeMillis("20:11:33.123");
        LocalTime target3 = LocalDateTimeUtils.parseTimeMillis("20:11:33.123");
        System.out.println("比较时间：" + LocalDateTimeUtils.compare(source3, target3));
    }

    @Test
    public void compareNowTest() {
        LocalDateTime source = LocalDateTimeUtils.parseDateTimeMillis("2018-05-12 20:11:33.123");
        System.out.println("比较日期时间：" + LocalDateTimeUtils.compareNow(source));

        LocalDate source2 = LocalDateTimeUtils.parseDate("2020-12-12");
        System.out.println("比较日期：" + LocalDateTimeUtils.compareNow(source2));

        LocalTime source3 = LocalDateTimeUtils.parseTimeMillis("20:11:33.123");
        System.out.println("比较时间：" + LocalDateTimeUtils.compareNow(source3));
    }

    @Test
    public void betweenTest() {
        LocalDateTime start = LocalDateTimeUtils.parseDateTimeMillis("2019-05-12 20:11:33.123");
        LocalDateTime end = LocalDateTimeUtils.parseDateTimeMillis("2021-05-12 20:11:33.123");
        System.out.println("判断日期时间范围：" + LocalDateTimeUtils.between(LocalDateTime.now(), start, end));

        LocalDate start2 = LocalDateTimeUtils.parseDate("2019-06-12");
        LocalDate end2 = LocalDateTimeUtils.parseDate("2021-06-13");
        System.out.println("判断日期范围：" + LocalDateTimeUtils.between(LocalDate.now(), start2, end2));

        LocalTime start3 = LocalDateTimeUtils.parseTimeMillis("20:11:33.123");
        LocalTime end3 = LocalDateTimeUtils.parseTimeMillis("22:11:33.123");
        System.out.println("判断时间范围：" + LocalDateTimeUtils.between(LocalTime.now(), start3, end3));
    }

    @Test
    public void dayOfWeekTest() {
        System.out.println(LocalDateTimeUtils.dayOfWeek("2020-06-08"));// 周一
        System.out.println(LocalDateTimeUtils.dayOfWeek("2020-06-09"));// 周二
        System.out.println(LocalDateTimeUtils.dayOfWeek("2020-06-10"));// 周三
        System.out.println(LocalDateTimeUtils.dayOfWeek("2020-06-11"));// 周四
        System.out.println(LocalDateTimeUtils.dayOfWeek("2020-06-12"));// 周五
        System.out.println(LocalDateTimeUtils.dayOfWeek("2020-06-13"));// 周六
        System.out.println(LocalDateTimeUtils.dayOfWeek("2020-06-14"));// 周日

        System.out.println(LocalDateTimeUtils.dayOfWeek());
    }

    @Test
    public void dayOfCycleTest() {
        System.out.println(LocalDateTimeUtils.dayOfCycle("2020-06-16", 7, "2020-05-25"));
        System.out.println(LocalDateTimeUtils.dayOfCycle("2020-06-25", 7, "2020-05-25"));
        System.out.println(LocalDateTimeUtils.dayOfCycle("2020-06-26", 7, "2020-05-25"));
        System.out.println(LocalDateTimeUtils.dayOfCycle("2020-06-27", 7, "2020-05-25"));
        System.out.println(LocalDateTimeUtils.dayOfCycle("2020-06-28", 7, "2020-05-25"));
        System.out.println(LocalDateTimeUtils.dayOfCycle("2020-06-29", 7, "2020-05-25"));
        System.out.println(LocalDateTimeUtils.dayOfCycle("2020-06-30", 7, "2020-05-25"));
        System.out.println(LocalDateTimeUtils.dayOfCycle("2020-06-31", 7, "2020-05-25"));
        System.out.println(LocalDateTimeUtils.dayOfCycle("2020-06-01", 7, "2020-05-25"));
        System.out.println(LocalDateTimeUtils.dayOfCycle("2020-06-30", 7, "2020-05-25"));
        System.out.println(LocalDateTimeUtils.dayOfCycle("2020-06-31", 7, "2020-05-25"));
        System.out.println(LocalDateTimeUtils.dayOfCycle("2021-06-28", 7, "2020-05-25"));
        System.out.println(LocalDateTimeUtils.dayOfCycle("2021-06-31", 7, "2020-05-25"));

        System.out.println(LocalDateTimeUtils.dayOfCycle(7, LocalDateTimeUtils.parseDate("2020-05-25")));
        System.out.println(LocalDateTimeUtils.dayOfCycle(10, "2020-06-01"));
    }
}
