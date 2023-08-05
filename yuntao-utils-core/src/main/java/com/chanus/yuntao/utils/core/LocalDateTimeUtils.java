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

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * 日期时间工具类，适应于jdk 1.8及以上版本
 *
 * @author Chanus
 * @date 2020-06-24 10:34:31
 * @since 1.0.0
 */
public class LocalDateTimeUtils {
    /**
     * 日期时间格式
     */
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * 日期时间格式，带毫秒数
     */
    private static final DateTimeFormatter DATE_TIME_MILLIS_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    /**
     * 日期格式
     */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    /**
     * 时间格式
     */
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    /**
     * 时间格式，带毫秒数
     */
    private static final DateTimeFormatter TIME_MILLIS_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    /**
     * 日期时间格式，java.util.Date原始格式
     */
    private static final DateTimeFormatter ORIGINAL_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

    private LocalDateTimeUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 获取 DateTimeFormatter 对象
     *
     * @param pattern 日期时间格式字符串
     * @return {@code DateTimeFormatter} 对象
     */
    public static DateTimeFormatter getDateTimeFormatter(String pattern) {
        return DateTimeFormatter.ofPattern(pattern);
    }

    /**
     * {@code LocalDateTime} 日期时间对象转自定义格式 {@code pattern} 字符串，{@code pattern} 可以包含年月日时分秒毫秒数，例如：
     * <pre>
     *     yyyy-MM-dd HH:mm:ss.SSS
     *     yyyy-MM-dd HH:mm:ss
     *     yyyy-MM-dd
     *     HH:mm:ss.SSS
     *     HH:mm:ss
     * </pre>
     *
     * @param localDateTime 日期时间，为空时返回 {@code null}
     * @param pattern       日期时间格式字符串，不能为空
     * @return {@code pattern} 格式字符串
     */
    public static String format(LocalDateTime localDateTime, String pattern) {
        return localDateTime == null ? null : localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * {@code LocalDate} 日期对象转自定义格式 {@code pattern} 字符串，{@code pattern} 可以包含年月日，例如：
     * <pre>
     *     yyyy-MM-dd
     *     yyyy-MM
     * </pre>
     *
     * @param localDate 日期，为空时返回 {@code null}
     * @param pattern   日期格式字符串，不能为空
     * @return {@code pattern} 格式字符串
     */
    public static String format(LocalDate localDate, String pattern) {
        return localDate == null ? null : localDate.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * {@code LocalTime} 时间对象转自定义格式 {@code pattern} 字符串，{@code pattern} 可以包含时分秒毫秒数，例如：
     * <pre>
     *     HH:mm:ss.SSS
     *     HH:mm:ss
     * </pre>
     *
     * @param localTime 时间，为空时返回{@code null}
     * @param pattern   时间格式字符串，不能为空
     * @return {@code pattern}格式字符串
     */
    public static String format(LocalTime localTime, String pattern) {
        return localTime == null ? null : localTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * {@code LocalDateTime} 日期时间对象转 yyyy-MM-dd HH:mm:ss 格式字符串
     *
     * @param localDateTime 日期时间，为空时返回 {@code null}
     * @return yyyy-MM-dd HH:mm:ss 格式字符串
     */
    public static String formatDateTime(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.format(DATE_TIME_FORMATTER);
    }

    /**
     * {@code LocalDateTime} 日期时间对象转 yyyy-MM-dd HH:mm:ss.SSS 格式字符串
     *
     * @param localDateTime 日期时间，为空时返回 {@code null}
     * @return yyyy-MM-dd HH:mm:ss.SSS 格式字符串
     */
    public static String formatDateTimeMillis(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.format(DATE_TIME_MILLIS_FORMATTER);
    }

    /**
     * {@code LocalDateTime} 日期时间对象转 yyyy-MM-dd 格式字符串
     *
     * @param localDateTime 日期时间，为空时返回 {@code null}
     * @return yyyy-MM-dd 格式字符串
     */
    public static String formatDate(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.format(DATE_FORMATTER);
    }

    /**
     * {@code localDate} 日期对象转 yyyy-MM-dd 格式字符串
     *
     * @param localDate 日期，为空时返回 {@code null}
     * @return yyyy-MM-dd 格式字符串
     */
    public static String formatDate(LocalDate localDate) {
        return localDate == null ? null : localDate.format(DATE_FORMATTER);
    }

    /**
     * {@code LocalDateTime} 日期时间对象转 HH:mm:ss 格式字符串
     *
     * @param localDateTime 日期时间，为空时返回 {@code null}
     * @return HH:mm:ss 格式字符串
     */
    public static String formatTime(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.format(TIME_FORMATTER);
    }

    /**
     * {@code localTime} 时间对象转 HH:mm:ss 格式字符串
     *
     * @param localTime 时间，为空时返回 {@code null}
     * @return HH:mm:ss 格式字符串
     */
    public static String formatTime(LocalTime localTime) {
        return localTime == null ? null : localTime.format(TIME_FORMATTER);
    }

    /**
     * {@code LocalDateTime} 日期时间对象转 HH:mm:ss.SSS 格式字符串
     *
     * @param localDateTime 日期时间，为空时返回 {@code null}
     * @return HH:mm:ss.SSS 格式字符串
     */
    public static String formatTimeMillis(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.format(TIME_MILLIS_FORMATTER);
    }

    /**
     * {@code localTime} 时间对象转 HH:mm:ss.SSS 格式字符串
     *
     * @param localTime 时间，为空时返回 {@code null}
     * @return HH:mm:ss.SSS 格式字符串
     */
    public static String formatTimeMillis(LocalTime localTime) {
        return localTime == null ? null : localTime.format(TIME_MILLIS_FORMATTER);
    }

    /**
     * 日期时间字符串转自定义格式 {@code pattern} 的 {@code LocalDateTime} 日期时间对象<br>
     * {@code localDateTime} 与 {@code pattern} 需对应，且必须包含时间<br>
     * {@code pattern} 可以包含年月日时分秒毫秒数，例如：
     * <pre>
     *     yyyy-MM-dd HH:mm:ss.SSS
     *     yyyy-MM-dd HH:mm:ss
     *     yyyy-MM-dd HH:mm
     *     yyyy年MM月dd日 HH时mm分ss秒
     * </pre>
     *
     * @param localDateTime 日期时间字符串，为空时返回 {@code null}
     * @param pattern       日期时间格式字符串，不能为空
     * @return {@code LocalDateTime} 日期时间对象
     */
    public static LocalDateTime parseDateTime(String localDateTime, String pattern) {
        return StringUtils.isBlank(localDateTime) ? null : LocalDateTime.parse(localDateTime, getDateTimeFormatter(pattern));
    }

    /**
     * yyyy-MM-dd HH:mm:ss 格式字符串转 {@code LocalDateTime} 日期时间对象
     *
     * @param localDateTime 日期时间字符串，为空时返回 {@code null}
     * @return {@code LocalDateTime} 日期时间对象
     */
    public static LocalDateTime parseDateTime(String localDateTime) {
        return StringUtils.isBlank(localDateTime) ? null : LocalDateTime.parse(localDateTime, DATE_TIME_FORMATTER);
    }

    /**
     * yyyy-MM-dd HH:mm:ss.SSS 格式字符串转 {@code LocalDateTime} 日期时间对象
     *
     * @param localDateTimeMillis 日期时间字符串，为空时返回 {@code null}
     * @return {@code LocalDateTime} 日期时间对象
     */
    public static LocalDateTime parseDateTimeMillis(String localDateTimeMillis) {
        return StringUtils.isBlank(localDateTimeMillis) ? null : LocalDateTime.parse(localDateTimeMillis, DATE_TIME_MILLIS_FORMATTER);
    }

    /**
     * 日期字符串转自定义格式 {@code pattern} 的 {@code LocalDate} 日期对象<br>
     * {@code localDate} 与 {@code pattern} 需对应，且不能包含时间<br>
     * {@code pattern} 可以包含年月日，例如：
     * <pre>
     *     yyyy-MM-dd
     *     yyyy年MM月dd日
     * </pre>
     *
     * @param localDate 日期字符串，为空时返回 {@code null}
     * @param pattern   日期格式字符串，不能为空
     * @return {@code LocalDate} 日期对象
     */
    public static LocalDate parseDate(String localDate, String pattern) {
        return StringUtils.isBlank(localDate) ? null : LocalDate.parse(localDate, getDateTimeFormatter(pattern));
    }

    /**
     * yyyy-MM-dd 格式字符串转 {@code LocalDate} 日期对象
     *
     * @param localDate 日期字符串，为空时返回 {@code null}
     * @return {@code LocalDate} 日期对象
     */
    public static LocalDate parseDate(String localDate) {
        return StringUtils.isBlank(localDate) ? null : LocalDate.parse(localDate, DATE_FORMATTER);
    }

    /**
     * 时间字符串转自定义格式 {@code pattern} 的 {@code LocalTime} 日期时间对象<br>
     * {@code localTime} 与 {@code pattern} 需对应，且不能包含年月日<br>
     * {@code pattern} 可以包含时分秒毫秒数，例如：
     * <pre>
     *     HH:mm:ss.SSS
     *     HH:mm:ss
     *     HH时mm分ss秒
     * </pre>
     *
     * @param localTime 时间字符串，为空时返回 {@code null}
     * @param pattern   时间格式字符串，不能为空
     * @return {@code LocalTime} 时间对象
     */
    public static LocalTime parseTime(String localTime, String pattern) {
        return StringUtils.isBlank(localTime) ? null : LocalTime.parse(localTime, getDateTimeFormatter(pattern));
    }

    /**
     * HH:mm:ss 格式字符串转 {@code LocalTime} 时间对象
     *
     * @param localTime 时间字符串，为空时返回 {@code null}
     * @return {@code LocalTime} 时间对象
     */
    public static LocalTime parseTime(String localTime) {
        return StringUtils.isBlank(localTime) ? null : LocalTime.parse(localTime, TIME_FORMATTER);
    }

    /**
     * HH:mm:ss.SSS 格式字符串转 {@code LocalTime} 时间对象
     *
     * @param localTimeMillis 时间字符串，为空时返回 {@code null}
     * @return {@code LocalTime} 时间对象
     */
    public static LocalTime parseTimeMillis(String localTimeMillis) {
        return StringUtils.isBlank(localTimeMillis) ? null : LocalTime.parse(localTimeMillis, TIME_MILLIS_FORMATTER);
    }

    /**
     * EEE MMM dd HH:mm:ss zzz yyyy 格式字符串转 {@code LocalDateTime} 日期时间对象
     *
     * @param originalDateTime 日期时间字符串，为空时返回 {@code null}
     * @return {@code LocalDateTime} 日期时间对象
     */
    public static LocalDateTime parseOriginalDateTime(String originalDateTime) {
        return StringUtils.isBlank(originalDateTime) ? null : ZonedDateTime.parse(originalDateTime, ORIGINAL_DATETIME_FORMATTER).toLocalDateTime();
    }

    /**
     * 获取当前日期时间的自定义格式 {@code pattern} 字符串
     *
     * @param pattern 时间格式字符串，不能为空
     * @return {@code pattern} 格式字符串
     */
    public static String now(String pattern) {
        return LocalDateTime.now().format(getDateTimeFormatter(pattern));
    }

    /**
     * 获取当前日期时间的 yyyy-MM-dd HH:mm:ss 格式字符串
     *
     * @return yyyy-MM-dd HH:mm:ss 格式字符串
     */
    public static String nowDateTime() {
        return LocalDateTime.now().format(DATE_TIME_FORMATTER);
    }

    /**
     * 获取当前日期时间的 yyyy-MM-dd HH:mm:ss.SSS 格式字符串
     *
     * @return yyyy-MM-dd HH:mm:ss.SSS 格式字符串
     */
    public static String nowDateTimeMillis() {
        return LocalDateTime.now().format(DATE_TIME_MILLIS_FORMATTER);
    }

    /**
     * 获取当前日期的 yyyy-MM-dd 格式字符串
     *
     * @return yyyy-MM-dd 格式字符串
     */
    public static String nowDate() {
        return LocalDate.now().format(DATE_FORMATTER);
    }

    /**
     * 获取当前时间的 HH:mm:ss 格式字符串
     *
     * @return HH:mm:ss 格式字符串
     */
    public static String nowTime() {
        return LocalTime.now().format(TIME_FORMATTER);
    }

    /**
     * 获取当前时间的 HH:mm:ss.SSS 格式字符串
     *
     * @return HH:mm:ss.SSS 格式字符串
     */
    public static String nowTimeMillis() {
        return LocalTime.now().format(TIME_MILLIS_FORMATTER);
    }

    /**
     * 获取昨日的 yyyy-MM-dd 格式字符串
     *
     * @return yyyy-MM-dd 格式字符串
     */
    public static String yesterday() {
        return minus(LocalDate.now(), 1, ChronoUnit.DAYS).format(DATE_FORMATTER);
    }

    /**
     * 获取明日的 yyyy-MM-dd 格式字符串
     *
     * @return yyyy-MM-dd 格式字符串
     */
    public static String tomorrow() {
        return plus(LocalDate.now(), 1, ChronoUnit.DAYS).format(DATE_FORMATTER);
    }

    /**
     * {@code LocalDateTime} 转 {@code Date}
     *
     * @param localDateTime {@code LocalDateTime} 对象
     * @return {@code Date} 对象
     */
    public static Date convertToDate(LocalDateTime localDateTime) {
        return localDateTime == null ? null : Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * {@code LocalDate} 转 {@code Date}
     *
     * @param localDate {@code LocalDate} 对象
     * @return {@code Date} 对象
     */
    public static Date convertToDate(LocalDate localDate) {
        return localDate == null ? null : Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * {@code ZonedDateTime} 转 {@code Date}
     *
     * @param zonedDateTime {@code ZonedDateTime} 对象
     * @return {@code Date} 对象
     * @since 1.6.0
     */
    public static Date convertToDate(ZonedDateTime zonedDateTime) {
        return zonedDateTime == null ? null : Date.from(zonedDateTime.toInstant());
    }

    /**
     * {@code Date} 转 {@code LocalDateTime}
     *
     * @param date {@code Date} 对象
     * @return {@code LocalDateTime} 对象
     */
    public static LocalDateTime convertToLocalDateTime(Date date) {
        return date == null ? null : LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 时间戳转 {@code LocalDateTime}
     *
     * @param timestamp 时间戳
     * @return {@code LocalDateTime} 对象
     */
    public static LocalDateTime convertToLocalDateTime(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }

    /**
     * {@code Date} 转 {@code LocalDate}
     *
     * @param date {@code Date} 对象
     * @return {@code LocalDate} 对象
     */
    public static LocalDate convertToLocalDate(Date date) {
        return date == null ? null : LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * 时间戳转 {@code LocalDate}
     *
     * @param timestamp 时间戳
     * @return {@code LocalDate} 对象
     */
    public static LocalDate convertToLocalDate(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * {@code Date} 转 {@code ZonedDateTime}
     *
     * @param date {@code Date} 对象
     * @return {@code ZonedDateTime} 对象
     * @since 1.6.0
     */
    public static ZonedDateTime convertToZonedDateTime(Date date) {
        return date == null ? null : Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault())
                                            .toLocalDateTime().atZone(ZoneId.systemDefault());
    }

    /**
     * {@code Date} 转 {@code ZonedDateTime}
     *
     * @param date   {@code Date} 对象
     * @param zoneId 时区
     * @return {@code ZonedDateTime} 对象
     * @since 1.6.0
     */
    public static ZonedDateTime convertToZonedDateTime(Date date, ZoneId zoneId) {
        return date == null ? null : Instant.ofEpochMilli(date.getTime()).atZone(zoneId).toLocalDateTime().atZone(zoneId);
    }

    /**
     * {@code Date} 转 {@code ZonedDateTime}
     *
     * @param date   {@code Date} 对象
     * @param zoneId 时区
     * @return {@code ZonedDateTime} 对象
     * @since 1.6.0
     */
    public static ZonedDateTime convertToZonedDateTime(Date date, String zoneId) {
        return convertToZonedDateTime(date, ZoneId.of(zoneId));
    }

    /**
     * {@code LocalDateTime} 转 {@code ZonedDateTime}
     *
     * @param localDateTime {@code LocalDateTime} 对象
     * @return {@code ZonedDateTime} 对象
     * @since 1.6.0
     */
    public static ZonedDateTime convertToZonedDateTime(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.atZone(ZoneId.systemDefault());
    }

    /**
     * {@code LocalDateTime} 转 {@code ZonedDateTime}
     *
     * @param localDateTime {@code LocalDateTime} 对象
     * @param zoneId        时区
     * @return {@code ZonedDateTime} 对象
     * @since 1.6.0
     */
    public static ZonedDateTime convertToZonedDateTime(LocalDateTime localDateTime, ZoneId zoneId) {
        return localDateTime == null ? null : localDateTime.atZone(zoneId);
    }

    /**
     * {@code LocalDateTime} 转 {@code ZonedDateTime}
     *
     * @param localDateTime {@code LocalDateTime} 对象
     * @param zoneId        时区
     * @return {@code ZonedDateTime} 对象
     * @since 1.6.0
     */
    public static ZonedDateTime convertToZonedDateTime(LocalDateTime localDateTime, String zoneId) {
        return convertToZonedDateTime(localDateTime, ZoneId.of(zoneId));
    }

    /**
     * {@code LocalDate} 转 {@code ZonedDateTime}
     *
     * @param localDate {@code LocalDate} 对象
     * @return {@code ZonedDateTime} 对象
     * @since 1.6.0
     */
    public static ZonedDateTime convertToZonedDateTime(LocalDate localDate) {
        return localDate == null ? null : localDate.atStartOfDay().atZone(ZoneId.systemDefault());
    }

    /**
     * {@code LocalDate} 转 {@code ZonedDateTime}
     *
     * @param localDate {@code LocalDate} 对象
     * @param zoneId    时区
     * @return {@code ZonedDateTime} 对象
     * @since 1.6.0
     */
    public static ZonedDateTime convertToZonedDateTime(LocalDate localDate, ZoneId zoneId) {
        return localDate == null ? null : localDate.atStartOfDay().atZone(zoneId);
    }

    /**
     * {@code LocalDate} 转 {@code ZonedDateTime}
     *
     * @param localDate {@code LocalDate} 对象
     * @param zoneId    时区
     * @return {@code ZonedDateTime} 对象
     * @since 1.6.0
     */
    public static ZonedDateTime convertToZonedDateTime(LocalDate localDate, String zoneId) {
        return convertToZonedDateTime(localDate, ZoneId.of(zoneId));
    }

    /**
     * 转换时区
     *
     * @param zonedDateTime {@code ZonedDateTime} 对象
     * @param zoneId        时区
     * @return 转换时区后的时间
     * @since 1.6.0
     */
    public static ZonedDateTime convertTimeZone(ZonedDateTime zonedDateTime, ZoneId zoneId) {
        return zonedDateTime == null ? null : zonedDateTime.withZoneSameInstant(zoneId);
    }

    /**
     * 转换时区
     *
     * @param zonedDateTime {@code ZonedDateTime} 对象
     * @param zoneId        时区
     * @return 转换时区后的时间
     * @since 1.6.0
     */
    public static ZonedDateTime convertTimeZone(ZonedDateTime zonedDateTime, String zoneId) {
        return convertTimeZone(zonedDateTime, ZoneId.of(zoneId));
    }

    /**
     * 将系统时区日期时间转换到指定时区日期时间
     *
     * @param localDateTime {@code LocalDateTime} 对象
     * @param zoneId        时区
     * @return 转换时区后的日期时间
     * @since 1.6.0
     */
    public static LocalDateTime convertTimeZone(LocalDateTime localDateTime, ZoneId zoneId) {
        return localDateTime == null ? null : localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(zoneId)
                                                           .toLocalDateTime();
    }

    /**
     * 将系统时区日期时间转换到指定时区日期时间
     *
     * @param localDateTime {@code LocalDateTime} 对象
     * @param zoneId        时区
     * @return 转换时区后的日期时间
     * @since 1.6.0
     */
    public static LocalDateTime convertTimeZone(LocalDateTime localDateTime, String zoneId) {
        return convertTimeZone(localDateTime, ZoneId.of(zoneId));
    }

    /**
     * 将某时区日期时间转换到指定时区日期时间
     *
     * @param localDateTime {@code LocalDateTime} 对象
     * @param sourceZoneId  原时区
     * @param targetZoneId  目标时区
     * @return 转换时区后的日期时间
     * @since 1.6.0
     */
    public static LocalDateTime convertTimeZone(LocalDateTime localDateTime, ZoneId sourceZoneId, ZoneId targetZoneId) {
        return localDateTime == null ? null : localDateTime.atZone(sourceZoneId).withZoneSameInstant(targetZoneId)
                                                           .toLocalDateTime();
    }

    /**
     * 将某时区日期时间转换到指定时区日期时间
     *
     * @param localDateTime {@code LocalDateTime} 对象
     * @param sourceZoneId  原时区
     * @param targetZoneId  目标时区
     * @return 转换时区后的日期时间
     * @since 1.6.0
     */
    public static LocalDateTime convertTimeZone(LocalDateTime localDateTime, String sourceZoneId, String targetZoneId) {
        return convertTimeZone(localDateTime, ZoneId.of(sourceZoneId), ZoneId.of(targetZoneId));
    }

    /**
     * 获取指定时间 {@code localDateTime} 在系统时区的秒数
     *
     * @param localDateTime 日期时间
     * @return 指定时间 {@code localDateTime} 在系统时区的秒数
     */
    public static long getSeconds(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    /**
     * 获取指定时间 {@code localDateTime} 在指定时区的秒数
     *
     * @param localDateTime 日期时间
     * @param zoneId        时区
     * @return 指定时间 {@code localDateTime} 在指定时区 {@code zoneId} 的秒数
     * @since 1.6.0
     */
    public static long getSeconds(LocalDateTime localDateTime, ZoneId zoneId) {
        return localDateTime.atZone(zoneId).toInstant().getEpochSecond();
    }

    /**
     * 获取指定时间 {@code localDateTime} 在指定时区的秒数
     *
     * @param localDateTime 日期时间
     * @param zoneId        时区
     * @return 指定时间 {@code localDateTime} 在指定时区 {@code zoneId} 的秒数
     * @since 1.6.0
     */
    public static long getSeconds(LocalDateTime localDateTime, String zoneId) {
        return getSeconds(localDateTime, ZoneId.of(zoneId));
    }

    /**
     * 获取指定时间 {@code localDateTime} 在系统时区的毫秒数
     *
     * @param localDateTime 日期时间
     * @return 指定时间{@code localDateTime} 在系统时区的毫秒数
     */
    public static long getMillis(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取指定时间 {@code localDateTime} 在指定时区的毫秒数
     *
     * @param localDateTime 日期时间
     * @param zoneId        时区
     * @return 指定时间 {@code localDateTime} 在指定时区 {@code zoneId} 的毫秒数
     * @since 1.6.0
     */
    public static long getMillis(LocalDateTime localDateTime, ZoneId zoneId) {
        return localDateTime.atZone(zoneId).toInstant().toEpochMilli();
    }

    /**
     * 获取指定时间 {@code localDateTime} 在指定时区的毫秒数
     *
     * @param localDateTime 日期时间
     * @param zoneId        时区
     * @return 指定时间 {@code localDateTime} 在指定时区 {@code zoneId} 的毫秒数
     * @since 1.6.0
     */
    public static long getMillis(LocalDateTime localDateTime, String zoneId) {
        return getMillis(localDateTime, ZoneId.of(zoneId));
    }

    /**
     * 获取某一天的开始时间字符串
     *
     * @param localDateTime 日期时间
     * @return 某一天的 00:00:00 时刻
     */
    public static String getDayStartTime(LocalDateTime localDateTime) {
        return localDateTime == null ? null : formatDateTime(localDateTime.with(LocalTime.MIN));
    }

    /**
     * 获取某一天的结束时间字符串
     *
     * @param localDateTime 日期时间
     * @return 某一天的 23:59:59 时刻
     */
    public static String getDayEndTime(LocalDateTime localDateTime) {
        return localDateTime == null ? null : formatDateTime(localDateTime.with(LocalTime.MAX));
    }

    /**
     * 获取当天的开始时间字符串
     *
     * @return 当天的 00:00:00 时刻
     */
    public static String getTodayStartTime() {
        return formatDateTime(LocalDateTime.now().with(LocalTime.MIN));
    }

    /**
     * 获取当天的结束时间字符串
     *
     * @return 当天的 23:59:59 时刻
     */
    public static String getTodayEndTime() {
        return formatDateTime(LocalDateTime.now().with(LocalTime.MAX));
    }

    /**
     * 获取某月的开始时间字符串
     *
     * @param localDateTime 日期时间
     * @return 某月第一天的 00:00:00 时刻
     */
    public static String getMonthStartTime(LocalDateTime localDateTime) {
        return localDateTime == null ? null : formatDateTime(localDateTime.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN));
    }

    /**
     * 获取某月的结束时间字符串
     *
     * @param localDateTime 日期时间
     * @return 某月最后一天的 23:59:59 时刻
     */
    public static String getMonthEndTime(LocalDateTime localDateTime) {
        return localDateTime == null ? null : formatDateTime(localDateTime.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX));
    }

    /**
     * 获取当月的开始时间字符串
     *
     * @return 当月第一天的 00:00:00 时刻
     */
    public static String getThisMonthStartTime() {
        return formatDateTime(LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN));
    }

    /**
     * 获取当月的结束时间字符串
     *
     * @return 当月最后一天的 23:59:59 时刻
     */
    public static String getThisMonthEndTime() {
        return formatDateTime(LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX));
    }

    /**
     * 获取某年的开始时间字符串
     *
     * @param localDateTime 日期时间
     * @return 某年第一天的 00:00:00 时刻
     */
    public static String getYearStartTime(LocalDateTime localDateTime) {
        return localDateTime == null ? null : formatDateTime(localDateTime.with(TemporalAdjusters.firstDayOfYear()).with(LocalTime.MIN));
    }

    /**
     * 获取某年的结束时间字符串
     *
     * @param localDateTime 日期时间
     * @return 某年最后一天的 23:59:59 时刻
     */
    public static String getYearEndTime(LocalDateTime localDateTime) {
        return localDateTime == null ? null : formatDateTime(localDateTime.with(TemporalAdjusters.lastDayOfYear()).with(LocalTime.MAX));
    }

    /**
     * 获取当年的开始时间字符串
     *
     * @return 当年第一天的 00:00:00 时刻
     */
    public static String getThisYearStartTime() {
        return formatDateTime(LocalDateTime.now().with(TemporalAdjusters.firstDayOfYear()).with(LocalTime.MIN));
    }

    /**
     * 获取当年的结束时间字符串
     *
     * @return 当年最后一天的 23:59:59 时刻
     */
    public static String getThisYearEndTime() {
        return formatDateTime(LocalDateTime.now().with(TemporalAdjusters.lastDayOfYear()).with(LocalTime.MAX));
    }

    /**
     * 日期时间根据 {@code field} 加上一个值 {@code number}
     *
     * @param localDateTime 日期时间
     * @param number        增加的数值
     * @param field         操作区域，年、月、日、时、分、秒、毫秒、星期等
     * @return {@code localDateTime} 根据 {@code field} 加上 {@code number} 后的日期时间
     */
    public static LocalDateTime plus(LocalDateTime localDateTime, long number, ChronoUnit field) {
        return localDateTime == null ? null : localDateTime.plus(number, field);
    }

    /**
     * 当前日期时间根据 {@code field} 加上一个值 {@code number}
     *
     * @param number 增加的数值
     * @param field  操作区域，年、月、日、时、分、秒、毫秒、星期等
     * @return 当前日期时间根据 {@code field} 加上 {@code number} 后的日期时间
     * @since 1.2.2
     */
    public static LocalDateTime plusNowDateTime(long number, ChronoUnit field) {
        return LocalDateTime.now().plus(number, field);
    }

    /**
     * 日期根据 {@code field} 加上一个值 {@code number}
     *
     * @param localDate 日期
     * @param number    增加的数值
     * @param field     操作区域，年、月、日、星期等
     * @return {@code localDate} 根据 {@code field} 加上 {@code number} 后的日期
     */
    public static LocalDate plus(LocalDate localDate, long number, ChronoUnit field) {
        return localDate == null ? null : localDate.plus(number, field);
    }

    /**
     * 当前日期根据 {@code field} 加上一个值 {@code number}
     *
     * @param number 增加的数值
     * @param field  操作区域，年、月、日、星期等
     * @return 当前日期根据 {@code field} 加上 {@code number} 后的日期
     * @since 1.2.2
     */
    public static LocalDate plusNowDate(long number, ChronoUnit field) {
        return LocalDate.now().plus(number, field);
    }

    /**
     * 时间根据 {@code field} 加上一个值 {@code number}
     *
     * @param localTime 时间
     * @param number    增加的数值
     * @param field     操作区域，时、分、秒、毫秒等
     * @return {@code localTime} 根据 {@code field} 加上 {@code number} 后的时间
     */
    public static LocalTime plus(LocalTime localTime, long number, ChronoUnit field) {
        return localTime == null ? null : localTime.plus(number, field);
    }

    /**
     * 当前时间根据 {@code field} 加上一个值 {@code number}
     *
     * @param number 增加的数值
     * @param field  操作区域，时、分、秒、毫秒等
     * @return 当前时间根据 {@code field} 加上 {@code number} 后的时间
     * @since 1.2.2
     */
    public static LocalTime plusNowTime(long number, ChronoUnit field) {
        return LocalTime.now().plus(number, field);
    }

    /**
     * 日期时间根据 {@code field} 减去一个值 {@code number}
     *
     * @param localDateTime 日期时间
     * @param number        减去的数值
     * @param field         操作区域，年、月、日、时、分、秒、毫秒、星期等
     * @return {@code localDateTime} 根据 {@code field} 减去 {@code number} 后的日期时间
     */
    public static LocalDateTime minus(LocalDateTime localDateTime, long number, ChronoUnit field) {
        return localDateTime == null ? null : localDateTime.minus(number, field);
    }

    /**
     * 当前日期时间根据 {@code field} 减去一个值 {@code number}
     *
     * @param number 减去的数值
     * @param field  操作区域，年、月、日、时、分、秒、毫秒、星期等
     * @return 当前日期时间根据 {@code field} 减去 {@code number} 后的日期时间
     * @since 1.2.2
     */
    public static LocalDateTime minusNowDateTime(long number, ChronoUnit field) {
        return LocalDateTime.now().minus(number, field);
    }

    /**
     * 日期根据 {@code field} 减去一个值 {@code number}
     *
     * @param localDate 日期
     * @param number    减去的数值
     * @param field     操作区域，年、月、日、星期等
     * @return {@code localDate} 根据 {@code field} 减去 {@code number} 后的日期
     */
    public static LocalDate minus(LocalDate localDate, long number, ChronoUnit field) {
        return localDate == null ? null : localDate.minus(number, field);
    }

    /**
     * 当前日期根据 {@code field} 减去一个值 {@code number}
     *
     * @param number 减去的数值
     * @param field  操作区域，年、月、日、星期等
     * @return 当前日期根据 {@code field} 减去 {@code number} 后的日期
     * @since 1.2.2
     */
    public static LocalDate minusNowDate(long number, ChronoUnit field) {
        return LocalDate.now().minus(number, field);
    }

    /**
     * 时间根据 {@code field} 减去一个值 {@code number}
     *
     * @param localTime 时间
     * @param number    减去的数值
     * @param field     操作区域，时、分、秒、毫秒等
     * @return {@code localTime} 根据 {@code field} 减去 {@code number} 后的时间
     */
    public static LocalTime minus(LocalTime localTime, long number, ChronoUnit field) {
        return localTime == null ? null : localTime.minus(number, field);
    }

    /**
     * 当前时间根据 {@code field} 减去一个值 {@code number}
     *
     * @param number 减去的数值
     * @param field  操作区域，时、分、秒、毫秒等
     * @return 当前时间根据 {@code field} 减去 {@code number} 后的时间
     * @since 1.2.2
     */
    public static LocalTime minusNowTime(long number, ChronoUnit field) {
        return LocalTime.now().minus(number, field);
    }

    /**
     * 根据 {@code field} 获取两个日期时间相间隔的数值，如相间隔的年数，月数，天数，小时数，分钟数，秒数，毫秒数，星期数等<br>
     * 其中，计算相间隔的年数和月数时忽略时间部分
     *
     * @param start 开始日期时间
     * @param end   结束日期时间
     * @param field 操作区域，年、月、日、时、分、秒、毫秒、星期等
     * @return {@code start} 与 {@code end} 根据 {@code field} 相间的数值
     */
    public static long interval(LocalDateTime start, LocalDateTime end, ChronoUnit field) {
        if (field == ChronoUnit.YEARS || field == ChronoUnit.MONTHS) {
            Period period = Period.between(LocalDate.from(start), LocalDate.from(end));
            return field == ChronoUnit.YEARS ? period.getYears() : (period.getYears() * 12L + period.getMonths());
        }

        return field.between(start, end);
    }

    /**
     * 根据 {@code field} 获取两个日期相间隔的数值，如相间隔的年数，月数，天数，星期数
     *
     * @param start 开始日期
     * @param end   结束日期
     * @param field 操作区域，年、月、日、星期
     * @return {@code start} 与 {@code end} 根据 {@code field} 相间的数值
     */
    public static long interval(LocalDate start, LocalDate end, ChronoUnit field) {
        if (field == ChronoUnit.YEARS || field == ChronoUnit.MONTHS) {
            Period period = Period.between(start, end);
            return field == ChronoUnit.YEARS ? period.getYears() : (period.getYears() * 12L + period.getMonths());
        }

        return field.between(start, end);
    }

    /**
     * 根据 {@code field} 获取两个时间相间隔的数值，如相间隔的小时数，分钟数，秒数，毫秒数等
     *
     * @param start 开始时间
     * @param end   结束时间
     * @param field 操作区域，时、分、秒、毫秒等
     * @return {@code start} 与 {@code end} 根据 {@code field} 相间的数值
     */
    public static long interval(LocalTime start, LocalTime end, ChronoUnit field) {
        return field.between(start, end);
    }

    /**
     * 比较两个日期时间的大小
     *
     * @param source 源日期时间
     * @param target 目标日期时间
     * @return {@code source} 大于 {@code target} 返回1，{@code source} 小于 {@code target} 返回-1，{@code source} 等于 {@code target} 返回0
     */
    public static int compare(LocalDateTime source, LocalDateTime target) {
        if (source.isBefore(target)) {
            return -1;
        } else if (source.isAfter(target)) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 比较两个日期的大小
     *
     * @param source 源日期
     * @param target 目标日期
     * @return {@code source} 大于 {@code target} 返回1，{@code source} 小于 {@code target} 返回-1，{@code source} 等于 {@code target} 返回0
     */
    public static int compare(LocalDate source, LocalDate target) {
        if (source.isBefore(target)) {
            return -1;
        } else if (source.isAfter(target)) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 比较两个时间的大小
     *
     * @param source 源时间
     * @param target 目标时间
     * @return {@code source} 大于 {@code target} 返回1，{@code source} 小于 {@code target} 返回-1，{@code source} 等于 {@code target} 返回0
     */
    public static int compare(LocalTime source, LocalTime target) {
        if (source.isBefore(target)) {
            return -1;
        } else if (source.isAfter(target)) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 比较指定日期时间与当前日期时间的大小
     *
     * @param source 需要比较的日期时间
     * @return {@code source} 大于当前日期时间返回1，{@code source} 小于当前日期时间返回-1，{@code source} 等于当前日期时间返回0
     * @since 1.4.5
     */
    public static int compareNow(LocalDateTime source) {
        return compare(source, LocalDateTime.now());
    }

    /**
     * 比较指定日期与当前日期的大小
     *
     * @param source 需要比较的日期
     * @return {@code source} 大于当前日期返回1，{@code source} 小于当前日期返回-1，{@code source} 等于当前日期返回0
     * @since 1.4.5
     */
    public static int compareNow(LocalDate source) {
        return compare(source, LocalDate.now());
    }

    /**
     * 比较指定时间与当前时间的大小
     *
     * @param source 需要比较的时间
     * @return {@code source} 大于当前时间返回1，{@code source} 小于当前时间返回-1，{@code source} 等于当前时间返回0
     * @since 1.4.5
     */
    public static int compareNow(LocalTime source) {
        return compare(source, LocalTime.now());
    }

    /**
     * 比较日期时间是否在指定日期时间范围之内
     *
     * @param localDateTime 待比较日期时间
     * @param start         开始日期时间
     * @param end           结束日期时间
     * @return {@code localDateTime} 在 {@code start} 与 {@code end} 之间返回 {@code true}，否则返回 {@code false}
     */
    public static boolean between(LocalDateTime localDateTime, LocalDateTime start, LocalDateTime end) {
        return !localDateTime.isBefore(start) && !localDateTime.isAfter(end);
    }

    /**
     * 比较日期是否在指定日期范围之内
     *
     * @param localDate 待比较日期
     * @param start     开始日期
     * @param end       结束日期
     * @return {@code localDate} 在 {@code start} 与 {@code end} 之间返回 {@code true}，否则返回 {@code false}
     */
    public static boolean between(LocalDate localDate, LocalDate start, LocalDate end) {
        return !localDate.isBefore(start) && !localDate.isAfter(end);
    }

    /**
     * 比较时间是否在指定时间范围之内
     *
     * @param localTime 待比较时间
     * @param start     开始时间
     * @param end       结束时间
     * @return {@code localTime} 在 {@code start} 与 {@code end} 之间返回 {@code true}，否则返回 {@code false}
     */
    public static boolean between(LocalTime localTime, LocalTime start, LocalTime end) {
        return !localTime.isBefore(start) && !localTime.isAfter(end);
    }

    /**
     * 获取指定日期是星期几，1表示周一，2表示周二，3表示周三，4表示周四，5表示周五，6表示周六，7表示周日
     *
     * @param date 日期
     * @return {@code date} 是星期几
     */
    public static int dayOfWeek(LocalDate date) {
        int dayOfWeek = date.getDayOfWeek().getValue();

        return dayOfWeek == 0 ? 7 : dayOfWeek;
    }

    /**
     * 获取指定日期是星期几，1表示周一，2表示周二，3表示周三，4表示周四，5表示周五，6表示周六，7表示周日
     *
     * @param dateStr 日期字符串
     * @return {@code dateStr} 是星期几
     */
    public static int dayOfWeek(String dateStr) {
        return dayOfWeek(Objects.requireNonNull(parseDate(dateStr)));
    }

    /**
     * 获取今天是星期几，1表示周一，2表示周二，3表示周三，4表示周四，5表示周五，6表示周六，7表示周日
     *
     * @return 今天是星期几
     */
    public static int dayOfWeek() {
        return dayOfWeek(LocalDate.now());
    }

    /**
     * 获取指定日期是指定时间周期内的第几天，1表示第一天，0表示指定时间小于周期开始日期
     *
     * @param date      日期
     * @param cycle     时间周期的天数
     * @param beginDate 时间周期的开始日期
     * @return 指定日期是指定时间周期内的第几天，如果指定日期小于周期开始日期，则返回0
     */
    public static int dayOfCycle(LocalDate date, int cycle, LocalDate beginDate) {
        if (compare(date, beginDate) == -1) {
            return 0;
        }
        return (int) (interval(beginDate, date, ChronoUnit.DAYS) % cycle + 1);
    }

    /**
     * 获取指定日期是指定时间周期内的第几天，1表示第一天，0表示指定时间小于周期开始日期
     *
     * @param dateStr      日期字符串
     * @param cycle        时间周期的天数
     * @param beginDateStr 时间周期的开始日期字符串
     * @return 指定日期是指定时间周期内的第几天，如果指定日期小于周期开始日期，则返回0
     */
    public static int dayOfCycle(String dateStr, int cycle, String beginDateStr) {
        return dayOfCycle(parseDate(dateStr), cycle, parseDate(beginDateStr));
    }

    /**
     * 获取今天是指定时间周期内的第几天，1表示第一天，0表示今天小于周期开始日期
     *
     * @param cycle     时间周期的天数
     * @param beginDate 时间周期的开始日期
     * @return 今天是指定时间周期内的第几天，如果今天小于周期开始日期，则返回0
     */
    public static int dayOfCycle(int cycle, LocalDate beginDate) {
        return dayOfCycle(LocalDate.now(), cycle, beginDate);
    }

    /**
     * 获取今天是指定时间周期内的第几天，1表示第一天，0表示今天小于周期开始日期
     *
     * @param cycle        时间周期的天数
     * @param beginDateStr 时间周期的开始日期字符串
     * @return 今天是指定时间周期内的第几天，如果今天小于周期开始日期，则返回0
     */
    public static int dayOfCycle(int cycle, String beginDateStr) {
        return dayOfCycle(LocalDate.now(), cycle, parseDate(beginDateStr));
    }
}
