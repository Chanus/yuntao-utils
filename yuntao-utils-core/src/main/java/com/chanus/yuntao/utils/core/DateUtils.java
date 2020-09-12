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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期时间工具类
 *
 * @author Chanus
 * @date 2020-06-20 15:22:07
 * @since 1.0.0
 */
public class DateUtils {
    /**
     * 日期时间格式
     */
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期格式
     */
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 时间格式
     */
    private static final String TIME_FORMAT = "HH:mm:ss";
    /**
     * java.util.Date 原始格式
     */
    private static final String ORIGINAL_DATETIME_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";
    /**
     * 每日开始时刻 00:00:00
     */
    private static final String START_TIME = " 00:00:00";
    /**
     * 每日结束时刻 23:59:59
     */
    private static final String END_TIME = " 23:59:59";

    /**
     * 获取 SimpleDateFormat 对象
     *
     * @param format 时间格式
     * @return {@code SimpleDateFormat} 对象
     */
    private static SimpleDateFormat getDateFormat(final String format) {
        return new SimpleDateFormat(format);
    }

    /**
     * 格式化时间对象
     *
     * @param date   时间对象
     * @param format 时间格式
     * @return 格式化后的时间字符串，若 {@code date} 或 {@code format} 为空，则返回 null
     */
    public static String format(Date date, String format) {
        return (date == null || StringUtils.isBlank(format)) ? null : getDateFormat(format).format(date);
    }

    /**
     * 将时间对象转换成 yyyy-MM-dd HH:mm:ss 格式的字符串
     *
     * @param date 时间对象
     * @return yyyy-MM-dd HH:mm:ss 格式的字符串，若 {@code date} 为空，则返回 null
     * @see DateUtils#format(Date, String)
     */
    public static String formatDateTime(Date date) {
        return format(date, DATETIME_FORMAT);
    }

    /**
     * 将时间对象转换成 yyyy-MM-dd 格式的字符串
     *
     * @param date 时间对象
     * @return yyyy-MM-dd 格式的字符串，若 {@code date} 为空，则返回 null
     * @see DateUtils#format(Date, String)
     */
    public static String formatDate(Date date) {
        return format(date, DATE_FORMAT);
    }

    /**
     * 将时间对象转换成 HH:mm:ss 格式的字符串
     *
     * @param date 时间对象
     * @return HH:mm:ss 格式的字符串，若 {@code date} 为空，则返回 null
     * @see DateUtils#format(Date, String)
     */
    public static String formatTime(Date date) {
        return format(date, TIME_FORMAT);
    }

    /**
     * 时间字符串转换成时间对象
     *
     * @param dateStr 时间字符串
     * @param format  时间格式
     * @return 转换后的时间对象，若 {@code dateStr} 或 {@code format} 为空，则返回 null
     */
    public static Date parse(String dateStr, String format) {
        if (StringUtils.isBlank(dateStr) || StringUtils.isBlank(format))
            return null;

        try {
            return getDateFormat(format).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将 yyyy-MM-dd HH:mm:ss 格式的时间字符串转换成时间对象
     *
     * @param dateStr 时间字符串
     * @return 时间对象，若 {@code dateStr} 为空，则返回 null
     * @see DateUtils#parse(String, String)
     */
    public static Date parseDateTime(String dateStr) {
        return parse(dateStr, DATETIME_FORMAT);
    }

    /**
     * 将 yyyy-MM-dd 格式的字符串转换成时间对象
     *
     * @param dateStr 时间字符串
     * @return 时间对象，若 {@code dateStr} 为空，则返回 null
     * @see DateUtils#parse(String, String)
     */
    public static Date parseDate(String dateStr) {
        return parse(dateStr, DATE_FORMAT);
    }

    /**
     * 将 HH:mm:ss 格式的时间字符串转换成时间对象
     *
     * @param dateStr 时间字符串
     * @return 时间对象，若 {@code dateStr} 为空，则返回null
     * @see DateUtils#parse(String, String)
     */
    public static Date parseTime(String dateStr) {
        return parse(dateStr, TIME_FORMAT);
    }

    /**
     * 将 EEE MMM dd HH:mm:ss zzz yyyy 格式的时间字符串转换成时间对象
     *
     * @param dateStr EEE MMM dd HH:mm:ss zzz yyyy 格式时间字符串
     * @return 时间对象，若 {@code dateStr} 为空，则返回null
     */
    public static Date parseOriginalDateTime(String dateStr) {
        if (StringUtils.isBlank(dateStr))
            return null;

        try {
            return new SimpleDateFormat(ORIGINAL_DATETIME_FORMAT, Locale.ENGLISH).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前时间的 yyyy-MM-dd HH:mm:ss 格式字符串
     *
     * @return 当前时间的 yyyy-MM-dd HH:mm:ss 格式字符串
     * @see DateUtils#formatDateTime(Date)
     */
    public static String nowDateTime() {
        return formatDateTime(new Date());
    }

    /**
     * 获取当前时间的 yyyy-MM-dd 格式字符串
     *
     * @return 当前时间的 yyyy-MM-dd 格式字符串
     * @see DateUtils#formatDate(Date)
     */
    public static String nowDate() {
        return formatDate(new Date());
    }

    /**
     * 获取当前时间的 HH:mm:ss 格式字符串
     *
     * @return 当前时间的 HH:mm:ss 格式字符串
     * @see DateUtils#formatTime(Date)
     */
    public static String nowTime() {
        return formatTime(new Date());
    }

    /**
     * 获取昨天的 yyyy-MM-dd 格式字符串
     *
     * @return 昨天的 yyyy-MM-dd 格式字符串
     * @see DateUtils#formatDate(Date)
     */
    public static String yesterday() {
        return formatDate(dayOffset(new Date(), -1));
    }

    /**
     * 获取明天的 yyyy-MM-dd 格式字符串
     *
     * @return 明天的 yyyy-MM-dd 格式字符串
     * @see DateUtils#formatDate(Date)
     */
    public static String tomorrow() {
        return formatDate(dayOffset(new Date(), 1));
    }

    /**
     * 获取某天的开始时间
     *
     * @param date 时间对象
     * @return 某天的 00:00:00 时刻时间，若 {@code date} 为空，则默认为当天
     * @see DateUtils#parseDateTime(String)
     */
    public static Date dayBegin(Date date) {
        String dateStr = date == null ? nowDate() : formatDate(date);
        return parseDateTime(dateStr + START_TIME);
    }

    /**
     * 获取某天的结束时间
     *
     * @param date 时间对象
     * @return 某天的 23:59:59 时刻时间，若 {@code date} 为空，则默认为当天
     * @see DateUtils#parseDateTime(String)
     */
    public static Date dayEnd(Date date) {
        String dateStr = date == null ? nowDate() : formatDate(date);
        return parseDateTime(dateStr + END_TIME);
    }

    /**
     * 获取当天的开始时间
     *
     * @return 当天 00:00:00 时刻时间
     * @see DateUtils#parseDateTime(String)
     */
    public static Date todayBegin() {
        return parseDateTime(nowDate() + START_TIME);
    }

    /**
     * 获取当天的结束时间
     *
     * @return 当天 23:59:59 时刻时间
     * @see DateUtils#parseDateTime(String)
     */
    public static Date todayEnd() {
        return parseDateTime(nowDate() + END_TIME);
    }

    /**
     * 获取指定时间之前或之后的时间
     *
     * @param date   时间
     * @param offset 时间偏移量，正数是 {@code date} 之后，负数是 {@code date} 之前
     * @param unit   时间偏移单位，Calendar.YEAR、Calendar.MONTH、Calendar.DATE 等
     * @return 指定时间按照 {@code unit} 偏移 {@code offset} 的时间
     */
    public static Date offset(Date date, int offset, int unit) {
        if (date == null)
            return null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(unit, offset);

        return calendar.getTime();
    }

    /**
     * 获取当前时间之前或之后的时间
     *
     * @param offset 时间偏移量，正数是当前时间之后，负数是当前时间之前
     * @param unit   时间偏移单位，Calendar.YEAR、Calendar.MONTH、Calendar.DATE 等
     * @return 当前时间按照 {@code unit} 偏移 {@code offset} 的时间
     * @since 1.2.2
     */
    public static Date offset(int offset, int unit) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(unit, offset);

        return calendar.getTime();
    }

    /**
     * 获取指定时间之前或之后 {@code year} 年的时间
     *
     * @param date 时间
     * @param year 年数，正数是 {@code date} 之后，负数是 {@code date} 之前
     * @return 指定时间之前或之后 {@code year} 年的时间
     * @see DateUtils#offset(Date, int, int)
     */
    public static Date yearOffset(Date date, int year) {
        return offset(date, year, Calendar.YEAR);
    }

    /**
     * 获取当前时间之前或之后 {@code year} 年的时间
     *
     * @param year 年数，正数是当前时间之后，负数是当前时间之前
     * @return 当前时间之前或之后 {@code year} 年的时间
     * @see DateUtils#offset(int, int)
     * @since 1.2.2
     */
    public static Date yearOffset(int year) {
        return offset(year, Calendar.YEAR);
    }

    /**
     * 获取指定时间之前或之后 {@code month} 月的时间
     *
     * @param date  时间
     * @param month 月数，正数是 {@code date} 之后，负数是 {@code date} 之前
     * @return 指定时间之前或之后 {@code month} 月的时间
     * @see DateUtils#offset(Date, int, int)
     */
    public static Date monthOffset(Date date, int month) {
        return offset(date, month, Calendar.MONTH);
    }

    /**
     * 获取当前时间之前或之后 {@code month} 月的时间
     *
     * @param month 月数，正数是当前时间之后，负数是当前时间之前
     * @return 当前时间之前或之后 {@code month} 月的时间
     * @see DateUtils#offset(int, int)
     * @since 1.2.2
     */
    public static Date monthOffset(int month) {
        return offset(month, Calendar.MONTH);
    }

    /**
     * 获取指定时间之前或之后 {@code day} 天的时间
     *
     * @param date 时间
     * @param day  天数，正数是 {@code date} 之后，负数是 {@code date} 之前
     * @return 指定时间之前或之后 {@code day} 天的时间
     * @see DateUtils#offset(Date, int, int)
     */
    public static Date dayOffset(Date date, int day) {
        return offset(date, day, Calendar.DATE);
    }

    /**
     * 获取当前时间之前或之后 {@code day} 天的时间
     *
     * @param day 天数，正数是当前时间之后，负数是当前时间之前
     * @return 当前时间之前或之后 {@code day} 天的时间
     * @see DateUtils#offset(int, int)
     * @since 1.2.2
     */
    public static Date dayOffset(int day) {
        return offset(day, Calendar.DATE);
    }

    /**
     * 获取指定时间之前或之后 {@code hour} 个小时的时间
     *
     * @param date 时间
     * @param hour 小时数，正数是 {@code date} 之后，负数是 {@code date} 之前
     * @return 指定时间之前或之后 {@code hour} 个小时的时间
     * @see DateUtils#offset(Date, int, int)
     */
    public static Date hourOffset(Date date, int hour) {
        return offset(date, hour, Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取当前时间之前或之后 {@code hour} 个小时的时间
     *
     * @param hour 小时数，正数是当前时间之后，负数是当前时间之前
     * @return 当前时间之前或之后 {@code hour} 个小时的时间
     * @see DateUtils#offset(int, int)
     * @since 1.2.2
     */
    public static Date hourOffset(int hour) {
        return offset(hour, Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取指定时间之前或之后 {@code minute} 分钟的时间
     *
     * @param date   时间
     * @param minute 分钟数，正数是 {@code date} 之后，负数是 {@code date} 之前
     * @return 指定时间之前或之后 {@code minute} 分钟的时间
     * @see DateUtils#offset(Date, int, int)
     */
    public static Date minuteOffset(Date date, int minute) {
        return offset(date, minute, Calendar.MINUTE);
    }

    /**
     * 获取当前时间之前或之后 {@code minute} 分钟的时间
     *
     * @param minute 分钟数，正数是当前时间之后，负数是当前时间之前
     * @return 当前时间之前或之后 {@code minute} 分钟的时间
     * @see DateUtils#offset(int, int)
     * @since 1.2.2
     */
    public static Date minuteOffset(int minute) {
        return offset(minute, Calendar.MINUTE);
    }

    /**
     * 获取指定时间之前或之后 {@code second} 秒的时间
     *
     * @param date   时间
     * @param second 秒数，正数是 {@code date} 之后，负数是 {@code date} 之前
     * @return 指定时间之前或之后 {@code second} 秒的时间
     * @see DateUtils#offset(Date, int, int)
     */
    public static Date secondOffset(Date date, int second) {
        return offset(date, second, Calendar.SECOND);
    }

    /**
     * 获取当前时间之前或之后 {@code second} 秒的时间
     *
     * @param second 秒数，正数是当前时间之后，负数是当前时间之前
     * @return 当前时间之前或之后 {@code second} 秒的时间
     * @see DateUtils#offset(int, int)
     * @since 1.2.2
     */
    public static Date secondOffset(int second) {
        return offset(second, Calendar.SECOND);
    }

    /**
     * 计算两个时间相差的毫秒数
     *
     * @param sourceDate 源时间
     * @param targetDate 目标时间
     * @return {@code sourceDate} 和 {@code targetDate} 相差的毫秒数
     */
    public static long diffMillis(Date sourceDate, Date targetDate) {
        return Math.abs(sourceDate.getTime() - targetDate.getTime());
    }

    /**
     * 计算两个时间相差的年数，忽略时分秒
     *
     * @param sourceDate 源时间
     * @param targetDate 目标时间
     * @return {@code sourceDate} 和 {@code targetDate} 相差的年数
     */
    public static int diffYears(Date sourceDate, Date targetDate) {
        Calendar[] calendars = getCalendars(sourceDate, targetDate);

        int n = calendars[0].get(Calendar.YEAR) - calendars[1].get(Calendar.YEAR);
        if (n == 0)
            return n;

        int sourceMonth = calendars[0].get(Calendar.MONTH);
        int sourceDay = calendars[0].get(Calendar.DAY_OF_MONTH);
        int targetMonth = calendars[1].get(Calendar.MONTH);
        int targetDay = calendars[1].get(Calendar.DAY_OF_MONTH);

        return (sourceMonth > targetMonth || (sourceMonth == targetMonth && sourceDay >= targetDay)) ? n : (n - 1);
    }

    /**
     * 计算两个时间相差的月数，忽略时分秒
     *
     * @param sourceDate 源时间
     * @param targetDate 目标时间
     * @return {@code sourceDate} 和 {@code targetDate} 相差的月数
     */
    public static int diffMonths(Date sourceDate, Date targetDate) {
        Calendar[] calendars = getCalendars(sourceDate, targetDate);

        int n = (calendars[0].get(Calendar.YEAR) - calendars[1].get(Calendar.YEAR)) * 12 + calendars[0].get(Calendar.MONTH) - calendars[1].get(Calendar.MONTH);

        return calendars[0].get(Calendar.DAY_OF_MONTH) >= calendars[1].get(Calendar.DAY_OF_MONTH) ? n : (n - 1);
    }

    /**
     * 计算两个时间相差的天数，忽略时分秒
     *
     * @param sourceDate 源时间
     * @param targetDate 目标时间
     * @return {@code sourceDate} 和 {@code targetDate} 相差的天数
     */
    public static int diffDays(Date sourceDate, Date targetDate) {
        Calendar[] calendars = getCalendars(sourceDate, targetDate);

        int n = 0;
        while (calendars[0].get(Calendar.YEAR) != calendars[1].get(Calendar.YEAR)
                || calendars[0].get(Calendar.MONTH) != calendars[1].get(Calendar.MONTH)
                || calendars[0].get(Calendar.DAY_OF_MONTH) != calendars[1].get(Calendar.DAY_OF_MONTH)) {
            calendars[1].add(Calendar.DATE, 1);
            n++;
        }

        return n;
    }

    /**
     * 比较两个时间的大小
     * <pre>
     *     sourceDate > targetDate 返回 1
     *     sourceDate = targetDate 返回 0
     *     sourceDate < targetDate 返回 -1
     * </pre>
     *
     * @param sourceDate 需要比较的时间
     * @param targetDate 被比较的时间
     * @return 比较结果
     */
    public static int compare(Date sourceDate, Date targetDate) {
        return Long.compare(sourceDate.getTime(), targetDate.getTime());
    }

    /**
     * 比较两个指定格式的时间字符串的大小
     * <pre>
     *     sourceDateStr > targetDateStr 返回 1
     *     sourceDateStr = targetDateStr 返回 0
     *     sourceDateStr < targetDateStr 返回 -1
     * </pre>
     *
     * @param sourceDateStr 需要比较的时间字符串
     * @param targetDateStr 被比较的时间字符串
     * @param format        待转换的时间格式
     * @return 比较结果
     */
    public static int compare(String sourceDateStr, String targetDateStr, String format) {
        SimpleDateFormat sdf = getDateFormat(format);
        try {
            return compare(sdf.parse(sourceDateStr), sdf.parse(targetDateStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 比较指定时间与当前时间的大小
     * <pre>
     *     date > 当前时间 返回 1
     *     date = 当前时间 返回 0
     *     date < 当前时间 返回 -1
     * </pre>
     *
     * @param date 需要比较的时间
     * @return 比较结果
     */
    public static int compareNow(Date date) {
        return compare(date, new Date());
    }

    /**
     * 判断某个时间是否在某一时间段内
     *
     * @param date  时间
     * @param start 时间段开始时间
     * @param end   时间段结束时间
     * @return {@code true} {@code date} 在 {@code start} 和 {@code end} 之间；{@code false} {@code date} 不在 {@code start} 和 {@code end} 之间
     */
    public static boolean between(Date date, Date start, Date end) {
        return !date.before(start) && !date.after(end);
    }

    /**
     * 将当前时区的时间转换成指定时区的时间
     *
     * @param date           时间
     * @param sourceTimeZone 时间时区
     * @param targetTimeZone 待转换时区
     * @return 转换后的时间
     */
    public static Date convertByTimeZone(Date date, TimeZone sourceTimeZone, TimeZone targetTimeZone) {
        if (sourceTimeZone == null || targetTimeZone == null)
            return date;

        return new Date(date.getTime() - sourceTimeZone.getRawOffset() + targetTimeZone.getRawOffset());
    }

    /**
     * 获取指定日期是星期几，1表示周一，2表示周二，3表示周三，4表示周四，5表示周五，6表示周六，7表示周日
     *
     * @param date 日期时间
     * @return {@code date}是星期几
     */
    public static int dayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        return dayOfWeek == 0 ? 7 : dayOfWeek;
    }

    /**
     * 获取指定日期是星期几，1表示周一，2表示周二，3表示周三，4表示周四，5表示周五，6表示周六，7表示周日
     *
     * @param dateStr 日期时间字符串
     * @return {@code dateStr}是星期几
     */
    public static int dayOfWeek(String dateStr) {
        return dayOfWeek(parseDate(dateStr));
    }

    /**
     * 获取今天是星期几，1表示周一，2表示周二，3表示周三，4表示周四，5表示周五，6表示周六，7表示周日
     *
     * @return 今天是星期几
     */
    public static int dayOfWeek() {
        return dayOfWeek(new Date());
    }

    /**
     * 获取指定日期是指定时间周期内的第几天，1表示第一天，0表示指定时间小于周期开始时间
     *
     * @param date      日期时间
     * @param cycle     时间周期的天数
     * @param beginDate 时间周期的开始时间
     * @return 指定日期是指定时间周期内的第几天，如果指定时间小于周期开始时间，则返回0
     */
    public static int dayOfCycle(Date date, int cycle, Date beginDate) {
        if (DateUtils.compare(date, beginDate) == -1)
            return 0;
        return diffDays(beginDate, date) % cycle + 1;
    }

    /**
     * 获取指定日期是指定时间周期内的第几天，1表示第一天，0表示指定时间小于周期开始时间
     *
     * @param dateStr      日期时间字符串
     * @param cycle        时间周期的天数
     * @param beginDateStr 时间周期的开始时间字符串
     * @return 指定日期是指定时间周期内的第几天，如果指定时间小于周期开始时间，则返回0
     */
    public static int dayOfCycle(String dateStr, int cycle, String beginDateStr) {
        return dayOfCycle(DateUtils.parseDate(dateStr), cycle, DateUtils.parseDate(beginDateStr));
    }

    /**
     * 获取今天是指定时间周期内的第几天，1表示第一天，0表示今天小于周期开始时间
     *
     * @param cycle     时间周期的天数
     * @param beginDate 时间周期的开始时间
     * @return 今天是指定时间周期内的第几天，如果今天小于周期开始时间，则返回0
     */
    public static int dayOfCycle(int cycle, Date beginDate) {
        return dayOfCycle(new Date(), cycle, beginDate);
    }

    /**
     * 获取今天是指定时间周期内的第几天，1表示第一天，0表示今天小于周期开始时间
     *
     * @param cycle        时间周期的天数
     * @param beginDateStr 时间周期的开始时间字符串
     * @return 今天是指定时间周期内的第几天，如果今天小于周期开始时间，则返回0
     */
    public static int dayOfCycle(int cycle, String beginDateStr) {
        return dayOfCycle(new Date(), cycle, DateUtils.parseDate(beginDateStr));
    }

    private static Calendar[] getCalendars(Date sourceDate, Date targetDate) {
        Calendar sourceCalendar = Calendar.getInstance();
        Calendar targetCalendar = Calendar.getInstance();
        if (compare(sourceDate, targetDate) >= 0) {
            sourceCalendar.setTime(sourceDate);
            targetCalendar.setTime(targetDate);
        } else {
            sourceCalendar.setTime(targetDate);
            targetCalendar.setTime(sourceDate);
        }

        return new Calendar[]{sourceCalendar, targetCalendar};
    }
}
