/*
 * Copyright (C) 2021 iofairy, <https://github.com/iofairy/falcon>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iofairy.falcon.time;

import com.iofairy.tcf.Try;
import com.iofairy.top.G;
import com.iofairy.top.S;

import java.time.*;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * DateTime Utils
 *
 * @since 0.2.5
 */
public final class DateTimes {
    /**
     * Supported temporal for {@code daysOfMonth} methods.
     */
    private static final List<Class<? extends Temporal>> SUPPORTED_TEMPORAL_FOR_DOM =
            Arrays.asList(LocalDateTime.class, ZonedDateTime.class, OffsetDateTime.class, Instant.class, YearMonth.class, ChronoLocalDate.class);

    /**
     * 获取当前时间的 ZoneOffset 以及对应的 ZoneId 列表。
     * 由于有些地区有<b>夏令时</b>，不同时间结果会不一样。所以需要实时获取。
     *
     * @return ZoneOffset 以及对应的 ZoneId 列表
     */
    public static Map<ZoneOffset, List<ZoneId>> getOffsetZoneIds() {
        return TZ.ZONE_IDS.stream().collect(Collectors.groupingBy(DateTimes::zoneOffset));
    }

    /**
     * 通过zoneOffset获取对应的ZoneId集合
     *
     * @param zoneOffset zoneOffset
     * @return ZoneId列表
     */
    public static List<ZoneId> getZoneIds(ZoneOffset zoneOffset) {
        return getOffsetZoneIds().get(zoneOffset);
    }

    /**
     * 获取当前系统当前时间的时区偏移{@code ZoneOffset}。
     * 由于有些地区有<b>夏令时</b>，不同时间结果会不一样。所以需要实时获取。
     *
     * @return ZoneOffset
     */
    public static ZoneOffset defaultOffset() {
        return Try.tcf(() -> OffsetDateTime.now().getOffset(), false);
    }

    /**
     * 通过ZoneId名称获取当前时间的时区偏移{@code ZoneOffset}。
     *
     * @param zoneIdName ZoneId名称
     * @return ZoneOffset
     */
    public static ZoneOffset zoneOffset(final String zoneIdName) {
        return zoneOffset(Try.tcf(() -> ZoneId.of(zoneIdName), false));
    }

    /**
     * 通过ZoneId获取当前时间的时区偏移{@code ZoneOffset}。
     *
     * @param zoneId ZoneId
     * @return ZoneOffset
     */
    public static ZoneOffset zoneOffset(final ZoneId zoneId) {
        return zoneOffset(zoneId, Instant.now());
    }

    /**
     * 根据指定 zoneId 与 instant 获取当时的时区偏移{@code ZoneOffset}。<br>
     * 由于有些地区有<b>夏令时</b>，不同时间结果{@code ZoneOffset}会不一样。所以需要根据指定的 instant 获取。
     *
     * @param zoneId  zoneId
     * @param instant instant
     * @return ZoneOffset
     */
    public static ZoneOffset zoneOffset(final ZoneId zoneId, final Instant instant) {
        if (G.hasNull(zoneId, instant)) return null;
        return Try.tcf(() -> zoneId.getRules().getOffset(instant), false);
    }

    /**
     * 获取 calendar 的时区偏移{@code ZoneOffset}
     *
     * @param calendar calendar
     * @return ZoneOffset
     */
    public static ZoneOffset zoneOffset(final Calendar calendar) {
        if (calendar == null) return null;
        return ZoneOffset.ofTotalSeconds(calendar.getTimeZone().getOffset(calendar.getTimeInMillis()) / 1000);
    }

    /**
     * 将temporal转为 {@link TZ#UTC} 时区的ZonedDateTime
     *
     * @param temporal temporal
     * @return ZonedDateTime
     */
    public static ZonedDateTime toUTCZonedDT(Temporal temporal) {
        return toZonedDT(temporal, TZ.UTC);
    }

    /**
     * 将temporal转为 {@link TZ#DEFAULT_ZONE} 时区的ZonedDateTime
     *
     * @param temporal temporal
     * @return ZonedDateTime
     */
    public static ZonedDateTime toDefaultZonedDT(Temporal temporal) {
        return toZonedDT(temporal, TZ.DEFAULT_ZONE);
    }

    /**
     * 将temporal转为指定时区的ZonedDateTime
     *
     * @param temporal temporal
     * @param zoneId   zoneId. 当 zoneId为{@code null}时，且temporal是ZonedDateTime类型时，默认采用temporal自己的时区；
     *                 temporal不是ZonedDateTime类型时，默认采用{@link TZ#DEFAULT_ZONE}
     * @return ZonedDateTime
     */
    public static ZonedDateTime toZonedDT(Temporal temporal, ZoneId zoneId) {
        if (temporal == null) return null;

        if (temporal instanceof ZonedDateTime) {
            ZonedDateTime zdt = (ZonedDateTime) temporal;
            return (zoneId == null || zdt.getZone().equals(zoneId)) ? zdt : zdt.withZoneSameInstant(zoneId);
        }

        if (zoneId == null) zoneId = TZ.DEFAULT_ZONE;
        if (temporal instanceof OffsetDateTime) return ((OffsetDateTime) temporal).atZoneSameInstant(zoneId);
        if (temporal instanceof LocalDateTime) return ((LocalDateTime) temporal).atZone(TZ.DEFAULT_ZONE).withZoneSameInstant(zoneId);
        if (temporal instanceof Instant) return ZonedDateTime.ofInstant((Instant) temporal, zoneId);

        throw new UnsupportedTemporalTypeException("Only [ZonedDateTime, OffsetDateTime, LocalDateTime, Instant] is supported!");
    }

    /**
     * 将calendar转为 {@link TZ#UTC} 时区的ZonedDateTime
     *
     * @param calendar calendar
     * @return ZonedDateTime
     */
    public static ZonedDateTime toUTCZonedDT(Calendar calendar) {
        return toZonedDT(calendar, TZ.UTC);
    }

    /**
     * 将calendar转为 {@link TZ#DEFAULT_ZONE} 时区的ZonedDateTime
     *
     * @param calendar calendar
     * @return ZonedDateTime
     */
    public static ZonedDateTime toDefaultZonedDT(Calendar calendar) {
        return toZonedDT(calendar, TZ.DEFAULT_ZONE);
    }

    /**
     * 将calendar转为指定时区的ZonedDateTime
     *
     * @param calendar calendar
     * @param zoneId   zoneId. 当 zoneId为{@code null}时，默认采用 calendar 的时区
     * @return ZonedDateTime
     */
    public static ZonedDateTime toZonedDT(Calendar calendar, ZoneId zoneId) {
        if (calendar == null) return null;
        if (zoneId == null) zoneId = calendar.getTimeZone().toZoneId();
        return calendar.toInstant().atZone(zoneId);
    }

    /**
     * 将date转为 {@link TZ#UTC} 时区的ZonedDateTime
     *
     * @param date date
     * @return ZonedDateTime
     */
    public static ZonedDateTime toUTCZonedDT(Date date) {
        return toZonedDT(date, TZ.UTC);
    }

    /**
     * 将date转为 {@link TZ#DEFAULT_ZONE} 时区的ZonedDateTime
     *
     * @param date date
     * @return ZonedDateTime
     */
    public static ZonedDateTime toDefaultZonedDT(Date date) {
        return toZonedDT(date, TZ.DEFAULT_ZONE);
    }

    /**
     * 将date转为指定时区的ZonedDateTime
     *
     * @param date   date
     * @param zoneId zoneId. 当 zoneId为{@code null}时，默认采用 {@link TZ#DEFAULT_ZONE} 时区
     * @return ZonedDateTime
     */
    public static ZonedDateTime toZonedDT(Date date, ZoneId zoneId) {
        if (date == null) return null;
        if (zoneId == null) zoneId = TZ.DEFAULT_ZONE;
        return date.toInstant().atZone(zoneId);
    }

    /**
     * 将temporal转为 {@link ZoneOffset#UTC} 时区的OffsetDateTime
     *
     * @param temporal temporal
     * @return OffsetDateTime
     */
    public static OffsetDateTime toUTCOffsetDT(Temporal temporal) {
        return toOffsetDT(temporal, ZoneOffset.UTC);
    }

    /**
     * 将temporal转为 {@link #defaultOffset()} 时区的OffsetDateTime
     *
     * @param temporal temporal
     * @return OffsetDateTime
     */
    public static OffsetDateTime toDefaultOffsetDT(Temporal temporal) {
        return toOffsetDT(temporal, defaultOffset());
    }

    /**
     * 将temporal转为指定时区的OffsetDateTime
     *
     * @param temporal   temporal
     * @param zoneOffset zoneOffset. 当 zoneOffset为{@code null}时，且temporal是OffsetDateTime类型时，默认采用temporal自己的时区偏移；
     *                   temporal不是OffsetDateTime类型时，默认采用{@link #defaultOffset()}时区偏移
     * @return OffsetDateTime
     */
    public static OffsetDateTime toOffsetDT(Temporal temporal, ZoneOffset zoneOffset) {
        if (temporal == null) return null;

        if (temporal instanceof OffsetDateTime) {
            OffsetDateTime odt = (OffsetDateTime) temporal;
            return (zoneOffset == null || odt.getOffset().equals(zoneOffset)) ? odt : odt.withOffsetSameInstant(zoneOffset);
        }

        if (zoneOffset == null) zoneOffset = defaultOffset();
        if (temporal instanceof ZonedDateTime) return ((ZonedDateTime) temporal).toOffsetDateTime().withOffsetSameInstant(zoneOffset);
        if (temporal instanceof LocalDateTime) return ((LocalDateTime) temporal).atOffset(defaultOffset()).withOffsetSameInstant(zoneOffset);
        if (temporal instanceof Instant) return OffsetDateTime.ofInstant((Instant) temporal, zoneOffset);

        throw new UnsupportedTemporalTypeException("Only [ZonedDateTime, OffsetDateTime, LocalDateTime, Instant] is supported!");
    }

    /**
     * 将calendar转为 {@link ZoneOffset#UTC} 时区的OffsetDateTime
     *
     * @param calendar calendar
     * @return OffsetDateTime
     */
    public static OffsetDateTime toUTCOffsetDT(Calendar calendar) {
        return toOffsetDT(calendar, ZoneOffset.UTC);
    }

    /**
     * 将calendar转为 {@link #defaultOffset()} 时区的OffsetDateTime
     *
     * @param calendar calendar
     * @return OffsetDateTime
     */
    public static OffsetDateTime toDefaultOffsetDT(Calendar calendar) {
        return toOffsetDT(calendar, defaultOffset());
    }

    /**
     * 将calendar转为指定时区的OffsetDateTime
     *
     * @param calendar   calendar
     * @param zoneOffset zoneOffset. 当 zoneOffset为{@code null}时，默认采用 calendar 的时区
     * @return OffsetDateTime
     */
    public static OffsetDateTime toOffsetDT(Calendar calendar, ZoneOffset zoneOffset) {
        if (calendar == null) return null;
        if (zoneOffset == null) zoneOffset = zoneOffset(calendar);
        return calendar.toInstant().atOffset(zoneOffset);
    }

    /**
     * 将date转为 {@link ZoneOffset#UTC} 时区的OffsetDateTime
     *
     * @param date date
     * @return OffsetDateTime
     */
    public static OffsetDateTime toUTCOffsetDT(Date date) {
        return toOffsetDT(date, ZoneOffset.UTC);
    }

    /**
     * 将date转为 {@link #defaultOffset()} 时区的OffsetDateTime
     *
     * @param date date
     * @return OffsetDateTime
     */
    public static OffsetDateTime toDefaultOffsetDT(Date date) {
        return toOffsetDT(date, defaultOffset());
    }

    /**
     * 将date转为指定时区的OffsetDateTime
     *
     * @param date       date
     * @param zoneOffset zoneOffset. 当 zoneId为{@code null}时，默认采用 {@link #defaultOffset()} 时区
     * @return OffsetDateTime
     */
    public static OffsetDateTime toOffsetDT(Date date, ZoneOffset zoneOffset) {
        if (date == null) return null;
        if (zoneOffset == null) zoneOffset = defaultOffset();
        return date.toInstant().atOffset(zoneOffset);
    }

    /**
     * Date 转 Calendar
     *
     * @param date date
     * @return Calendar
     */
    public static Calendar calendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * Date 转 Calendar
     *
     * @param date   date
     * @param zoneId timeZone
     * @return Calendar
     */
    public static Calendar calendar(Date date, ZoneId zoneId) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(zoneId));
        calendar.setTime(date);
        return calendar;
    }

    /**
     * Instant 转 Calendar
     *
     * @param instant instant
     * @return Calendar
     */
    public static Calendar calendar(Instant instant) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(instant.toEpochMilli());
        return calendar;
    }

    /**
     * ZonedDateTime 转 Calendar
     *
     * @param zonedDateTime zonedDateTime
     * @return Calendar
     */
    public static Calendar calendar(ZonedDateTime zonedDateTime) {
        if (zonedDateTime == null) return null;
        return GregorianCalendar.from(zonedDateTime);
    }

    /**
     * OffsetDateTime 转 Calendar
     *
     * @param offsetDateTime offsetDateTime
     * @return Calendar
     */
    public static Calendar calendar(OffsetDateTime offsetDateTime) {
        if (offsetDateTime == null) return null;
        return GregorianCalendar.from(offsetDateTime.toZonedDateTime());
    }

    /**
     * Instant 转 Calendar
     *
     * @param instant instant
     * @param zoneId  timeZone
     * @return Calendar
     */
    public static Calendar calendar(Instant instant, ZoneId zoneId) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(zoneId));
        calendar.setTimeInMillis(instant.toEpochMilli());
        return calendar;
    }

    /**
     * Convert local Date object to another timezone <br>
     * 将本地Date时间对象转成另一个时区的Date对象
     *
     * @param date   date
     * @param zoneId zoneId
     * @return another timezone Date
     */
    public static Date localDateToTZ(Date date, ZoneId zoneId) {
        return tzDateToTZ(date, TZ.DEFAULT_ZONE, zoneId);
    }

    /**
     * Convert fromZoneId Date to toZoneId <br>
     * 将Date对象从一个时区转成另一个时区的Date对象
     *
     * @param date       date
     * @param fromZoneId fromZoneId
     * @param toZoneId   toZoneId
     * @return another timezone Date
     */
    public static Date tzDateToTZ(Date date, ZoneId fromZoneId, ZoneId toZoneId) {
        return Date.from(date.toInstant().atZone(toZoneId).withZoneSameLocal(fromZoneId).toInstant());
    }

    /**
     * Convert calendar to local calendar <br>
     * 将calendar对象转成本地的calendar对象
     *
     * @param calendar calendar
     * @return local calendar
     */
    public static Calendar calendarToLocal(Calendar calendar) {
        return calendarToTZ(calendar, TZ.DEFAULT_ZONE);
    }

    /**
     * Convert calendar to another timezone calendar <br>
     * 将calendar对象转成另一个时区的calendar对象
     *
     * @param calendar calendar
     * @param zoneId   zoneId
     * @return calendar with another timezone
     */
    public static Calendar calendarToTZ(Calendar calendar, ZoneId zoneId) {
        Date newDate = tzDateToTZ(calendar.getTime(), calendar.getTimeZone().toZoneId(), zoneId);
        Calendar newCalendar = Calendar.getInstance(TimeZone.getTimeZone(zoneId));
        newCalendar.setTime(newDate);
        return newCalendar;
    }

    /**
     * 对时间进行增减操作，支持的时间类型：{@link DTConst#SUPPORTED_TEMPORAL_COMMON}。<br>
     * 注：
     * {@link Instant} 类型的时间会先转成 {@link #defaultOffset()} 时区的 OffsetDateTime 进行增减。
     *
     * @param temporal 时间
     * @param amount   增减的量
     * @param unit     时间单位
     * @param <T>      时间类型
     * @return 增减后的时间
     */
    @SuppressWarnings("unchecked")
    public static <T extends Temporal> T plus(T temporal, long amount, ChronoUnit unit) {
        if (temporal == null || amount == 0) return temporal;
        if (!DTConst.SUPPORTED_TEMPORAL_COMMON.contains(temporal.getClass())) {
            throw new UnsupportedTemporalTypeException("Only [" + DTConst.SUPPORTED_TEMPORAL_COMMON_STRING + "] is supported for `temporal` parameter!");
        }
        if (temporal instanceof Instant) {
            OffsetDateTime odt = toDefaultOffsetDT(temporal).plus(amount, unit);
            return (T) odt.toInstant();
        }
        return (T) temporal.plus(amount, unit);
    }

    /**
     * 对时间进行增减操作
     *
     * @param calendar 时间
     * @param amount   增减的量
     * @param unit     时间单位
     * @return 增减后的时间
     */
    public static Calendar plus(Calendar calendar, long amount, ChronoUnit unit) {
        if (calendar == null) return calendar;
        /*
         * Calendar自带的add方法，无法执行 long 型的加减
         */
        ZonedDateTime zonedDateTime = toZonedDT(calendar, null);
        zonedDateTime = plus(zonedDateTime, amount, unit);
        return calendar(zonedDateTime);
    }

    /**
     * 对时间进行增减操作
     *
     * @param date   时间
     * @param amount 增减的量
     * @param unit   时间单位
     * @return 增减后的时间
     */
    public static Date plus(Date date, long amount, ChronoUnit unit) {
        if (date == null) return null;
        return Date.from(plus(date.toInstant(), amount, unit));
    }

    /**
     * 对时间进行增减操作，支持的时间类型：{@link DTConst#SUPPORTED_TEMPORAL_COMMON}。<br>
     * 注：
     * {@link Instant} 类型的时间会先转成 {@link #defaultOffset()} 时区的 OffsetDateTime 进行增减。
     *
     * @param temporal 时间
     * @param amount   增减的量
     * @param unit     时间单位
     * @param <T>      时间类型
     * @return 增减后的时间
     */
    @SuppressWarnings("unchecked")
    public static <T extends Temporal> T minus(T temporal, long amount, ChronoUnit unit) {
        if (temporal == null || amount == 0) return temporal;
        if (!DTConst.SUPPORTED_TEMPORAL_COMMON.contains(temporal.getClass())) {
            throw new UnsupportedTemporalTypeException("Only [" + DTConst.SUPPORTED_TEMPORAL_COMMON_STRING + "] is supported for `temporal` parameter!");
        }
        if (temporal instanceof Instant) {
            OffsetDateTime odt = toDefaultOffsetDT(temporal).minus(amount, unit);
            return (T) odt.toInstant();
        }
        return (T) temporal.minus(amount, unit);
    }

    /**
     * 对时间进行增减操作
     *
     * @param calendar 时间
     * @param amount   增减的量
     * @param unit     时间单位
     * @return 增减后的时间
     */
    public static Calendar minus(Calendar calendar, long amount, ChronoUnit unit) {
        if (calendar == null) return calendar;
        /*
         * Calendar自带的add方法，无法执行 long 型的加减
         */
        ZonedDateTime zonedDateTime = toZonedDT(calendar, null);
        zonedDateTime = minus(zonedDateTime, amount, unit);
        return calendar(zonedDateTime);
    }

    /**
     * 对时间进行增减操作
     *
     * @param date   时间
     * @param amount 增减的量
     * @param unit   时间单位
     * @return 增减后的时间
     */
    public static Date minus(Date date, long amount, ChronoUnit unit) {
        if (date == null) return null;
        return Date.from(minus(date.toInstant(), amount, unit));
    }

    /**
     * 获取某个月的总天数
     *
     * @param year  当前年份
     * @param month 当前月份
     * @return 当前月的总天数
     * @since 0.2.5
     */
    public static int daysOfMonth(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        return yearMonth.lengthOfMonth();
    }

    /**
     * 获取某个月的总天数
     *
     * @param calendar calendar
     * @return 当前月的总天数
     * @since 0.2.5
     */
    public static int daysOfMonth(Calendar calendar) {
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取某个月的总天数
     *
     * @param date date
     * @return 当前月的总天数
     * @since 0.2.5
     */
    public static int daysOfMonth(Date date) {
        return daysOfMonth(calendar(date));
    }

    /**
     * 获取某个月的总天数
     *
     * @param temporal temporal
     * @return 当前月的总天数
     * @since 0.2.5
     */
    public static int daysOfMonth(Temporal temporal) {
        Objects.requireNonNull(temporal, "Parameter `temporal` must be non-null!");
        if (!SUPPORTED_TEMPORAL_FOR_DOM.contains(temporal.getClass())) {
            throw new UnsupportedTemporalTypeException("Only [" + SUPPORTED_TEMPORAL_FOR_DOM.stream().map(Class::getSimpleName).collect(Collectors.joining(", ")) + "] is supported for `temporal` parameter!");
        }
        if (temporal instanceof ChronoLocalDate) return ((ChronoLocalDate) temporal).lengthOfMonth();
        if (temporal instanceof YearMonth) return ((YearMonth) temporal).lengthOfMonth();

        int year;
        int month;
        if (temporal instanceof LocalDateTime) {
            LocalDateTime localDateTime = (LocalDateTime) temporal;
            year = localDateTime.getYear();
            month = localDateTime.getMonthValue();
        } else if (temporal instanceof OffsetDateTime) {
            OffsetDateTime offsetDateTime = (OffsetDateTime) temporal;
            year = offsetDateTime.getYear();
            month = offsetDateTime.getMonthValue();
        } else if (temporal instanceof ZonedDateTime) {
            ZonedDateTime zonedDateTime = (ZonedDateTime) temporal;
            year = zonedDateTime.getYear();
            month = zonedDateTime.getMonthValue();
        } else {
            LocalDateTime localDateTime = LocalDateTime.ofInstant((Instant) temporal, TZ.DEFAULT_ZONE);
            year = localDateTime.getYear();
            month = localDateTime.getMonthValue();
        }

        return daysOfMonth(year, month);
    }

    /**
     * 使用指定格式返回一天中的24小时
     *
     * @param withMode  0: 只返回小时；1：返回小时并在末尾拼接上00分钟；2：返回小时并在末尾拼接00分钟和00秒
     * @param separator 当 withMode 为 1，2时，需要指定分隔符。如果为 {@code null}，则默认采用 {@code ""}
     * @return 一天中的小时
     * @since 0.2.5
     */
    public static List<String> hoursOfDay(int withMode, String separator) {
        if (withMode == 0) return DTConst.HHs;
        if (separator == null) separator = "";
        List<String> hhs = new ArrayList<>();
        if (withMode == 1) {
            for (String hh : DTConst.HHs) {
                hhs.add(hh + separator + "00");
            }
        } else {
            for (String hh : DTConst.HHs) {
                hhs.add(hh + separator + "00" + separator + "00");
            }
        }
        return hhs;
    }

    /**
     * 一天中的所有分钟
     *
     * @param withZeroSecond 是否在末尾拼接00秒
     * @param separator      拼接时采用的分隔符。如果为 {@code null}，则默认采用 {@code ""}
     * @return 一天中的所有分钟
     * @since 0.2.5
     */
    public static List<String> hourMinutesOfDay(boolean withZeroSecond, String separator) {
        if (separator == null) separator = "";
        List<String> hhmms = new ArrayList<>();
        if (withZeroSecond) {
            for (String hh : DTConst.HHs) {
                for (String ms : DTConst.MSs) {
                    hhmms.add(hh + separator + ms + separator + "00");
                }
            }
        } else {
            for (String hh : DTConst.HHs) {
                for (String ms : DTConst.MSs) {
                    hhmms.add(hh + separator + ms);
                }
            }
        }
        return hhmms;
    }

    /**
     * 某个指定小时的所有分钟
     *
     * @param hour           时。如果为 {@code null}，则默认采用 {@code ""}
     * @param withZeroSecond 是否在末尾拼接00秒
     * @param separator      拼接时采用的分隔符。如果为 {@code null}，则默认采用 {@code ""}
     * @return 某个指定小时的所有分钟
     * @since 0.2.5
     */
    public static List<String> minutesOfHour(String hour, boolean withZeroSecond, String separator) {
        if (separator == null) separator = "";
        boolean isEmpty = S.isEmpty(hour);
        hour = isEmpty ? "" : hour;
        String hmSeparator = isEmpty ? "" : separator;  // 小时与分钟之间的分隔符
        List<String> hhmms = new ArrayList<>();
        if (withZeroSecond) {
            for (String ms : DTConst.MSs) {
                hhmms.add(hour + hmSeparator + ms + separator + "00");
            }
        } else {
            for (String ms : DTConst.MSs) {
                hhmms.add(hour + hmSeparator + ms);
            }
        }
        return hhmms;
    }

    /**
     * 某个指定分钟的所有秒
     *
     * @param minute    分钟。如果为 {@code null}，则默认采用 {@code ""}
     * @param separator 拼接时采用的分隔符。如果为 {@code null}，则默认采用 {@code ""}
     * @return 某个指定分钟的所有秒
     * @since 0.2.5
     */
    public static List<String> secondsOfMinute(String minute, String separator) {
        return minutesOfHour(minute, false, separator);
    }

}
