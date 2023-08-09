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

import com.chanus.yuntao.utils.core.date.DateUtils;
import com.chanus.yuntao.utils.core.date.DatePattern;
import org.junit.Test;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * DateUtils 测试类
 *
 * @author Chanus
 * @since 1.0.0
 */
public class DateUtilsTest {
    @Test
    public void formatTest() {
        Date date = new Date();
        System.out.println("-------------------format(Date date, SimpleDateFormat format)-------------------");
        System.out.println("yyyy:" + DateUtils.format(date, DateUtils.createDateFormat(DatePattern.NORMAL_YEAR_PATTERN)));
        System.out.println("yyyy年:" + DateUtils.format(date, DateUtils.createDateFormat(DatePattern.CHINESE_YEAR_PATTERN)));
        System.out.println("yyyy-MM:" + DateUtils.format(date, DateUtils.createDateFormat(DatePattern.NORMAL_MONTH_PATTERN)));
        System.out.println("yyyyMM:" + DateUtils.format(date, DateUtils.createDateFormat(DatePattern.PURE_MONTH_PATTERN)));
        System.out.println("yyyy年MM月:" + DateUtils.format(date, DateUtils.createDateFormat(DatePattern.CHINESE_MONTH_PATTERN)));
        System.out.println("yyyy-MM-dd:" + DateUtils.format(date, DateUtils.createDateFormat(DatePattern.NORMAL_DATE_PATTERN)));
        System.out.println("yyyyMMdd:" + DateUtils.format(date, DateUtils.createDateFormat(DatePattern.PURE_DATE_PATTERN)));
        System.out.println("yyyy年MM月dd日:" + DateUtils.format(date, DateUtils.createDateFormat(DatePattern.CHINESE_DATE_PATTERN)));
        System.out.println("yyyy年MM月dd日:" + DateUtils.format(date, DateUtils.createDateFormat(DatePattern.NORMAL_TIME_PATTERN)));
        System.out.println("yyyy年MM月dd日:" + DateUtils.format(date, DateUtils.createDateFormat(DatePattern.NORMAL_TIME_PATTERN)));
        System.out.println("HH:mm:ss:" + DateUtils.format(date, DateUtils.createDateFormat(DatePattern.NORMAL_TIME_PATTERN)));
        System.out.println("HHmmss:" + DateUtils.format(date, DateUtils.createDateFormat(DatePattern.PURE_TIME_PATTERN)));
        System.out.println("HH时mm分ss秒:" + DateUtils.format(date, DateUtils.createDateFormat(DatePattern.CHINESE_TIME_PATTERN)));
        System.out.println("yyyy-MM-dd HH:mm:ss:" + DateUtils.format(date, DateUtils.createDateFormat(DatePattern.NORMAL_DATETIME_PATTERN)));
        System.out.println("yyyyMMddHHmmss:" + DateUtils.format(date, DateUtils.createDateFormat(DatePattern.PURE_DATETIME_PATTERN)));
        System.out.println("yyyy年MM月dd日HH时mm分ss秒:" + DateUtils.format(date, DateUtils.createDateFormat(DatePattern.CHINESE_DATETIME_PATTERN)));
        System.out.println("EEE, dd MMM yyyy HH:mm:ss z:" + DateUtils.format(date, DateUtils.createDateFormat(DatePattern.HTTP_DATETIME_PATTERN)));
        System.out.println("EEE, dd MMM yyyy HH:mm:ss z:" + DateUtils.format(date, DateUtils.createDateFormat(DatePattern.HTTP_DATETIME_PATTERN, Locale.US)));
        System.out.println("EEE MMM dd HH:mm:ss zzz yyyy:" + DateUtils.format(date, DateUtils.createDateFormat(DatePattern.JDK_DATETIME_PATTERN)));
        System.out.println("EEE MMM dd HH:mm:ss zzz yyyy:" + DateUtils.format(date, DateUtils.createDateFormat(DatePattern.JDK_DATETIME_PATTERN, Locale.US)));
        System.out.println("yyyy-MM-dd'T'HH:mm:ss'Z':" + DateUtils.format(date, DateUtils.createDateFormat(DatePattern.UTC_PATTERN)));
        System.out.println("yyyy-MM-dd'T'HH:mm:ss:" + DateUtils.format(date, DateUtils.createDateFormat(DatePattern.UTC_SIMPLE_PATTERN)));
        System.out.println("yyyy-MM-dd'T'HH:mm:ssZ:" + DateUtils.format(date, DateUtils.createDateFormat(DatePattern.UTC_WITH_ZONE_OFFSET_PATTERN)));
        System.out.println("yyyy-MM-dd'T'HH:mm:ssXXX:" + DateUtils.format(date, DateUtils.createDateFormat(DatePattern.UTC_WITH_XXX_OFFSET_PATTERN)));

        System.out.println("-------------------format(Date date, String pattern)-------------------");
        System.out.println("yyyy:" + DateUtils.format(date, DatePattern.NORMAL_YEAR_PATTERN));
        System.out.println("yyyy年:" + DateUtils.format(date, DatePattern.CHINESE_YEAR_PATTERN));
        System.out.println("yyyy-MM:" + DateUtils.format(date, DatePattern.NORMAL_MONTH_PATTERN));
        System.out.println("yyyyMM:" + DateUtils.format(date, DatePattern.PURE_MONTH_PATTERN));
        System.out.println("yyyy年MM月:" + DateUtils.format(date, DatePattern.CHINESE_MONTH_PATTERN));
        System.out.println("yyyy-MM-dd:" + DateUtils.format(date, DatePattern.NORMAL_DATE_PATTERN));
        System.out.println("yyyyMMdd:" + DateUtils.format(date, DatePattern.PURE_DATE_PATTERN));
        System.out.println("yyyy年MM月dd日:" + DateUtils.format(date, DatePattern.CHINESE_DATE_PATTERN));
        System.out.println("HH:mm:ss:" + DateUtils.format(date, DatePattern.NORMAL_TIME_PATTERN));
        System.out.println("HHmmss:" + DateUtils.format(date, DatePattern.PURE_TIME_PATTERN));
        System.out.println("HH时mm分ss秒:" + DateUtils.format(date, DatePattern.CHINESE_TIME_PATTERN));
        System.out.println("EEE, dd MMM yyyy HH:mm:ss z:" + DateUtils.format(date, DatePattern.HTTP_DATETIME_PATTERN));
        System.out.println("EEE MMM dd HH:mm:ss zzz yyyy:" + DateUtils.format(date, DatePattern.JDK_DATETIME_PATTERN));
        System.out.println("yyyy-MM-dd'T'HH:mm:ss'Z':" + DateUtils.format(date, DatePattern.UTC_PATTERN));
        System.out.println("yyyy-MM-dd'T'HH:mm:ss:" + DateUtils.format(date, DatePattern.UTC_SIMPLE_PATTERN));
        System.out.println("yyyy-MM-dd'T'HH:mm:ssZ:" + DateUtils.format(date, DatePattern.UTC_WITH_ZONE_OFFSET_PATTERN));
        System.out.println("yyyy-MM-dd'T'HH:mm:ssXXX:" + DateUtils.format(date, DatePattern.UTC_WITH_XXX_OFFSET_PATTERN));

        System.out.println("-------------------format(Date date, String pattern, Locale locale)-------------------");
        System.out.println("EEE, dd MMM yyyy HH:mm:ss z:" + DateUtils.format(date, DatePattern.HTTP_DATETIME_PATTERN, Locale.US));
        System.out.println("EEE MMM dd HH:mm:ss zzz yyyy:" + DateUtils.format(date, DatePattern.JDK_DATETIME_PATTERN, Locale.US));
    }

    @Test
    public void formatDateTimeTest() {
        System.out.println(DateUtils.formatDateTime(new Date()));
    }

    @Test
    public void formatDateTest() {
        System.out.println(DateUtils.formatDate(new Date()));
    }

    @Test
    public void formatTimeTest() {
        System.out.println(DateUtils.formatTime(new Date()));
    }

    @Test
    public void parseTest() {
        System.out.println("-------------------parse(String dateStr, SimpleDateFormat format)-------------------");
        System.out.println("yyyy-MM-dd:" + DateUtils.parse("2023-08-09", DateUtils.createDateFormat(DatePattern.NORMAL_DATE_PATTERN)));
        System.out.println("yyyyMMdd:" + DateUtils.parse("20230809", DateUtils.createDateFormat(DatePattern.PURE_DATE_PATTERN)));
        System.out.println("yyyy年MM月dd日:" + DateUtils.parse("2023年08月09日", DateUtils.createDateFormat(DatePattern.CHINESE_DATE_PATTERN)));
        System.out.println("yyyy-MM-dd HH:mm:ss:" + DateUtils.parse("2023-08-09 12:33:25", DateUtils.createDateFormat(DatePattern.NORMAL_DATETIME_PATTERN)));
        System.out.println("yyyyMMddHHmmss:" + DateUtils.parse("20230809123325", DateUtils.createDateFormat(DatePattern.PURE_DATETIME_PATTERN)));
        System.out.println("yyyy年MM月dd日HH时mm分ss秒:" + DateUtils.parse("2023年08月09日12时33分25秒", DateUtils.createDateFormat(DatePattern.CHINESE_DATETIME_PATTERN)));
        System.out.println("HH:mm:ss:" + DateUtils.parse("12:38:33", DateUtils.createDateFormat(DatePattern.NORMAL_TIME_PATTERN)));
        System.out.println("HHmmss:" + DateUtils.parse("123833", DateUtils.createDateFormat(DatePattern.PURE_TIME_PATTERN)));
        System.out.println("HH时mm分ss秒:" + DateUtils.parse("12时38分33秒", DateUtils.createDateFormat(DatePattern.CHINESE_TIME_PATTERN)));
        System.out.println("EEE, dd MMM yyyy HH:mm:ss z:" + DateUtils.parse("Wed, 09 Aug 2023 12:33:25 CST", DateUtils.createDateFormat(DatePattern.HTTP_DATETIME_PATTERN, Locale.US)));
        System.out.println("EEE MMM dd HH:mm:ss zzz yyyy:" + DateUtils.parse("Wed Aug 09 12:33:25 CST 2023", DateUtils.createDateFormat(DatePattern.JDK_DATETIME_PATTERN, Locale.US)));
        System.out.println("yyyy-MM-dd'T'HH:mm:ss'Z':" + DateUtils.parse("2023-08-09T12:33:25Z", DatePattern.UTC_PATTERN));
        System.out.println("yyyy-MM-dd'T'HH:mm:ss:" + DateUtils.parse("2023-08-09T12:33:25", DatePattern.UTC_SIMPLE_PATTERN));
        System.out.println("yyyy-MM-dd'T'HH:mm:ssZ:" + DateUtils.parse("2023-08-09T12:33:25+0800", DatePattern.UTC_WITH_ZONE_OFFSET_PATTERN));
        System.out.println("yyyy-MM-dd'T'HH:mm:ssXXX:" + DateUtils.parse("2023-08-09T12:33:25+08:00", DatePattern.UTC_WITH_XXX_OFFSET_PATTERN));

        System.out.println("-------------------parse(String dateStr, SimpleDateFormat format)-------------------");
        System.out.println(DateUtils.parse("2019年06月13日 22时47分33秒547毫秒", "yyyy年MM月dd日 HH时mm分ss秒SSS毫秒"));
        System.out.println(DateUtils.parse("2019-06-13 22:47:33.547", DatePattern.NORMAL_DATETIME_MILLIS_PATTERN));
        System.out.println(DateUtils.parse("2019-06-13 22:47:33", DatePattern.NORMAL_DATETIME_PATTERN));
        System.out.println(DateUtils.parse("2019-06-13", DatePattern.NORMAL_DATE_PATTERN));
        System.out.println(DateUtils.parse("22:47:33", DatePattern.NORMAL_TIME_PATTERN));

        System.out.println("-------------------parse(String dateStr, String pattern, Locale locale)-------------------");
        System.out.println("EEE, dd MMM yyyy HH:mm:ss z:" + DateUtils.parse("Wed, 09 Aug 2023 12:33:25 CST", DatePattern.HTTP_DATETIME_PATTERN, Locale.US));
        System.out.println("EEE MMM dd HH:mm:ss zzz yyyy:" + DateUtils.parse("Wed Aug 09 12:33:25 CST 2023", DatePattern.JDK_DATETIME_PATTERN, Locale.US));
    }

    @Test
    public void parseDateTimeTest() {
        System.out.println(DateUtils.parseDateTime("2019-06-13 22:47:33"));
    }

    @Test
    public void parseDateTest() {
        System.out.println(DateUtils.parseDate("2019-06-13"));
    }

    @Test
    public void parseTimeTest() {
        System.out.println(DateUtils.parseTime("23:07:35"));
    }

    @Test
    public void nowDateTimeTest() {
        System.out.println("当前日期时间：" + DateUtils.nowDateTime());
    }

    @Test
    public void nowDateTest() {
        System.out.println("当前日期：" + DateUtils.nowDate());
    }

    @Test
    public void nowTimeTest() {
        System.out.println("当前时间：" + DateUtils.nowTime());
    }

    @Test
    public void yesterdayTest() {
        System.out.println("昨天：" + DateUtils.yesterday());
    }

    @Test
    public void tomorrowTest() {
        System.out.println("明天：" + DateUtils.tomorrow());
    }

    @Test
    public void dayBeginTest() {
        System.out.println("开始时间：" + DateUtils.dayBegin(new Date()));
    }

    @Test
    public void dayEndTest() {
        System.out.println("结束时间：" + DateUtils.dayEnd(new Date()));
    }

    @Test
    public void todayBeginTest() {
        System.out.println("今天开始时间：" + DateUtils.todayBegin());
    }

    @Test
    public void todayEndTest() {
        System.out.println("今天结束时间：" + DateUtils.todayEnd());
    }

    @Test
    public void offsetTest() {
        Date date = new Date();
        System.out.println("当前时间：" + date);
        System.out.println("两周前：" + DateUtils.offset(date, -2, Calendar.WEEK_OF_YEAR));
        System.out.println("两周前：" + DateUtils.offset(-2, Calendar.WEEK_OF_YEAR));
        System.out.println("两周后：" + DateUtils.offset(date, 2, Calendar.WEEK_OF_YEAR));
        System.out.println("两周后：" + DateUtils.offset(2, Calendar.WEEK_OF_YEAR));
    }

    @Test
    public void yearOffsetTest() {
        Date date = new Date();
        System.out.println("当前时间：" + date);
        System.out.println("两年前：" + DateUtils.yearOffset(date, -2));
        System.out.println("两年前：" + DateUtils.yearOffset(-2));
        System.out.println("两年后：" + DateUtils.yearOffset(date, 2));
        System.out.println("两年后：" + DateUtils.yearOffset(2));
    }

    @Test
    public void monthOffsetTest() {
        Date date = new Date();
        System.out.println("当前时间：" + date);
        System.out.println("两月前：" + DateUtils.monthOffset(date, -2));
        System.out.println("两月前：" + DateUtils.monthOffset(-2));
        System.out.println("两月后：" + DateUtils.monthOffset(date, 2));
        System.out.println("两月后：" + DateUtils.monthOffset(2));
    }

    @Test
    public void dayOffsetTest() {
        Date date = new Date();
        System.out.println("当前时间：" + date);
        System.out.println("两天前：" + DateUtils.dayOffset(date, -2));
        System.out.println("两天前：" + DateUtils.dayOffset(-2));
        System.out.println("两天后：" + DateUtils.dayOffset(date, 2));
        System.out.println("两天后：" + DateUtils.dayOffset(2));
    }

    @Test
    public void hourOffsetTest() {
        Date date = new Date();
        System.out.println("当前时间：" + date);
        System.out.println("两小时前：" + DateUtils.hourOffset(date, -2));
        System.out.println("两小时前：" + DateUtils.hourOffset(-2));
        System.out.println("两小时后：" + DateUtils.hourOffset(date, 2));
        System.out.println("两小时后：" + DateUtils.hourOffset(2));
    }

    @Test
    public void minuteOffsetTest() {
        Date date = new Date();
        System.out.println("当前时间：" + date);
        System.out.println("两分钟前：" + DateUtils.minuteOffset(date, -2));
        System.out.println("两分钟前：" + DateUtils.minuteOffset(-2));
        System.out.println("两分钟后：" + DateUtils.minuteOffset(date, 2));
        System.out.println("两分钟后：" + DateUtils.minuteOffset(2));
    }

    @Test
    public void secondOffsetTest() {
        Date date = new Date();
        System.out.println("当前时间：" + date);
        System.out.println("两秒钟前：" + DateUtils.secondOffset(date, -2));
        System.out.println("两秒钟前：" + DateUtils.secondOffset(-2));
        System.out.println("两秒钟后：" + DateUtils.secondOffset(date, 2));
        System.out.println("两秒钟后：" + DateUtils.secondOffset(2));
    }

    @Test
    public void diffMillisTest() {
        System.out.println(DateUtils.diffMillis(DateUtils.parseDateTime("2019-06-13 22:48:33"), DateUtils.parseDateTime("2019-06-13 22:47:33")));
        System.out.println(DateUtils.diffMillis(DateUtils.parseDateTime("2019-06-13 22:47:33"), DateUtils.parseDateTime("2019-06-13 22:48:33")));
    }

    @Test
    public void diffSecondsTest() {
        System.out.println(DateUtils.diffSeconds(DateUtils.parseDateTime("2019-06-13 22:48:33"), DateUtils.parseDateTime("2019-06-13 22:47:33")));
        System.out.println(DateUtils.diffSeconds(DateUtils.parseDateTime("2019-06-13 22:47:33"), DateUtils.parseDateTime("2019-06-13 22:48:33")));
    }

    @Test
    public void diffMinutesTest() {
        System.out.println(DateUtils.diffMinutes(DateUtils.parseDateTime("2019-06-13 22:48:33"), DateUtils.parseDateTime("2019-06-13 22:47:33")));
        System.out.println(DateUtils.diffMinutes(DateUtils.parseDateTime("2019-06-13 22:47:33"), DateUtils.parseDateTime("2019-06-13 22:48:33")));
    }

    @Test
    public void diffHoursTest() {
        System.out.println(DateUtils.diffHours(DateUtils.parseDateTime("2019-06-13 21:48:33"), DateUtils.parseDateTime("2019-06-13 22:48:33")));
        System.out.println(DateUtils.diffHours(DateUtils.parseDateTime("2019-06-13 22:48:33"), DateUtils.parseDateTime("2019-06-13 21:48:33")));
    }

    @Test
    public void diffDaysTest() {
        System.out.println(DateUtils.diffDays(DateUtils.parseDateTime("2018-06-13 22:47:33"), DateUtils.parseDateTime("2019-06-13 22:47:33")));
        System.out.println(DateUtils.diffDays(DateUtils.parseDateTime("2019-06-13 22:47:33"), DateUtils.parseDateTime("2018-06-13 22:47:33")));
    }

    @Test
    public void diffMonthsTest() {
        System.out.println(DateUtils.diffMonths(DateUtils.parseDateTime("2020-06-13 22:47:33"), DateUtils.parseDateTime("2019-06-13 22:47:33")));
        System.out.println(DateUtils.diffMonths(DateUtils.parseDateTime("2019-06-13 22:47:33"), DateUtils.parseDateTime("2020-06-13 22:47:33")));
    }

    @Test
    public void diffYearsTest() {
        System.out.println(DateUtils.diffYears(DateUtils.parseDateTime("2020-06-13 22:47:33"), DateUtils.parseDateTime("2019-06-13 22:47:33")));
        System.out.println(DateUtils.diffYears(DateUtils.parseDateTime("2019-06-13 22:47:33"), DateUtils.parseDateTime("2020-06-13 22:47:33")));
    }

    @Test
    public void compareTest() {
        System.out.println(DateUtils.compare(DateUtils.parseDateTime("2019-06-13 12:47:33"), DateUtils.parseDateTime("2019-06-13 22:47:33")));
        System.out.println(DateUtils.compare(DateUtils.parseDateTime("2019-06-13 23:47:33"), DateUtils.parseDateTime("2019-06-13 22:47:33")));
        System.out.println(DateUtils.compare(DateUtils.parseDateTime("2019-06-13 22:47:33"), DateUtils.parseDateTime("2019-06-13 22:47:33")));

        System.out.println(DateUtils.compare("2019-06-13 12:47:33", "2019-06-13 22:47:33", "yyyy-MM-dd HH:mm:ss"));
        System.out.println(DateUtils.compare("2019-06-13 23:47:33", "2019-06-13 22:47:33", "yyyy-MM-dd HH:mm:ss"));
        System.out.println(DateUtils.compare("2019-06-13 22:47:33", "2019-06-13 22:47:33", "yyyy-MM-dd HH:mm:ss"));

        System.out.println(DateUtils.compare("2019-06-13 12:47:33", "2019-06-13 22:47:33", "yyyy-MM-dd"));
        System.out.println(DateUtils.compare("2019-06-14 23:47:33", "2019-06-13 22:47:33", "yyyy-MM-dd"));
        System.out.println(DateUtils.compare("2019-06-12 22:47:33", "2019-06-13 22:47:33", "yyyy-MM-dd"));
    }

    @Test
    public void compareNowTest() {
        System.out.println(DateUtils.compareNow(DateUtils.parseDateTime("2020-05-27 12:47:33")));
        System.out.println(DateUtils.compareNow(new Date()));
        System.out.println(DateUtils.compareNow(DateUtils.parseDateTime("2020-07-27 22:47:33")));
    }

    @Test
    public void betweenTest() {
        Date date = DateUtils.parseDateTime("2019-06-13 22:47:33");
        System.out.println(DateUtils.between(date, DateUtils.parseDateTime("2019-06-13 12:47:33"), DateUtils.parseDateTime("2019-06-13 22:47:33")));
        System.out.println(DateUtils.between(date, DateUtils.parseDateTime("2019-06-13 22:47:33"), DateUtils.parseDateTime("2019-06-14 22:47:33")));
        System.out.println(DateUtils.between(date, DateUtils.parseDateTime("2019-06-12 22:47:33"), DateUtils.parseDateTime("2019-06-13 22:47:32")));
    }

    @Test
    public void convertByTimeZoneTest() {
        Date date = DateUtils.parseDateTime("2019-06-13 22:47:33");
        System.out.println(DateUtils.convertByTimeZone(date, TimeZone.getDefault(), TimeZone.getTimeZone("Asia/Shanghai")));
        System.out.println(DateUtils.convertByTimeZone(date, TimeZone.getTimeZone("Asia/Shanghai"), TimeZone.getDefault()));
        System.out.println(DateUtils.convertByTimeZone(date, TimeZone.getTimeZone(ZoneId.of("GMT+10:00")), TimeZone.getTimeZone(ZoneId.of("GMT+12:00"))));
    }

    @Test
    public void dayOfWeekTest() {
        System.out.println(DateUtils.dayOfWeek("2020-05-25"));// 周一
        System.out.println(DateUtils.dayOfWeek("2020-05-26"));// 周二
        System.out.println(DateUtils.dayOfWeek("2020-05-27"));// 周三
        System.out.println(DateUtils.dayOfWeek("2020-05-28"));// 周四
        System.out.println(DateUtils.dayOfWeek("2020-05-29"));// 周五
        System.out.println(DateUtils.dayOfWeek("2020-05-30"));// 周六
        System.out.println(DateUtils.dayOfWeek("2020-05-31 15:47:30"));// 周日

        System.out.println(DateUtils.dayOfWeek());
    }

    @Test
    public void dayOfCycleTest() {
        System.out.println(DateUtils.dayOfCycle("2020-05-17", 7, "2020-05-25"));
        System.out.println(DateUtils.dayOfCycle("2020-05-25", 7, "2020-05-25"));
        System.out.println(DateUtils.dayOfCycle("2020-05-26", 7, "2020-05-25"));
        System.out.println(DateUtils.dayOfCycle("2020-05-27", 7, "2020-05-25"));
        System.out.println(DateUtils.dayOfCycle("2020-05-28", 7, "2020-05-25"));
        System.out.println(DateUtils.dayOfCycle("2020-05-29", 7, "2020-05-25"));
        System.out.println(DateUtils.dayOfCycle("2020-05-30", 7, "2020-05-25"));
        System.out.println(DateUtils.dayOfCycle("2020-05-31", 7, "2020-05-25"));
        System.out.println(DateUtils.dayOfCycle("2020-06-01", 7, "2020-05-25"));
        System.out.println(DateUtils.dayOfCycle("2020-06-30", 7, "2020-05-25"));
        System.out.println(DateUtils.dayOfCycle("2020-07-31", 7, "2020-05-25"));
        System.out.println(DateUtils.dayOfCycle("2021-02-28", 7, "2020-05-25"));
        System.out.println(DateUtils.dayOfCycle("2021-05-31", 7, "2020-05-25"));

        System.out.println(DateUtils.dayOfCycle(7, DateUtils.parseDate("2020-05-25")));
        System.out.println(DateUtils.dayOfCycle(7, "2020-05-25"));
    }
}
