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

import java.math.BigInteger;
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
 * @since 0.3.0
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
     * Convert local Date object to another timezone <br>
     * 将默认Date时间对象转成另一个时区的Date对象
     *
     * @param date   date
     * @param zoneId zoneId
     * @return another timezone Date
     */
    public static Date defaultDateToTZ(Date date, ZoneId zoneId) {
        return tzDateToTZ(date, TZ.DEFAULT_ZONE, zoneId);
    }

    /**
     * Convert fromZoneId Date to toZoneId <br>
     * 将Date对象从一个时区转成另一个时区的Date对象
     *
     * @param date       date
     * @param fromZoneId fromZoneId. 当 {@code fromZoneId} 为 null时，采用 {@link TZ#DEFAULT_ZONE}
     * @param toZoneId   toZoneId. 当 {@code toZoneId} 为 null时，采用 {@link TZ#DEFAULT_ZONE}
     * @return another timezone Date
     */
    public static Date tzDateToTZ(Date date, ZoneId fromZoneId, ZoneId toZoneId) {
        if (fromZoneId == null) fromZoneId = TZ.DEFAULT_ZONE;
        if (toZoneId == null) toZoneId = TZ.DEFAULT_ZONE;
        if (fromZoneId.equals(toZoneId)) return date;
        return Date.from(date.toInstant().atZone(toZoneId).withZoneSameLocal(fromZoneId).toInstant());
    }

    /**
     * Convert local LocalDateTime object to another timezone <br>
     * 将默认LocalDateTime时间对象转成另一个时区的LocalDateTime对象
     *
     * @param localDateTime LocalDateTime
     * @param zoneId        zoneId
     * @return another timezone LocalDateTime
     */
    public static LocalDateTime defaultLocalDTToTZ(LocalDateTime localDateTime, ZoneId zoneId) {
        return tzLocalDTToTZ(localDateTime, TZ.DEFAULT_ZONE, zoneId);
    }

    /**
     * Convert fromZoneId LocalDateTime to toZoneId <br>
     * 将LocalDateTime对象从一个时区转成另一个时区的LocalDateTime对象
     *
     * @param localDateTime LocalDateTime
     * @param fromZoneId    fromZoneId. 当 {@code fromZoneId} 为 null时，采用 {@link TZ#DEFAULT_ZONE}
     * @param toZoneId      toZoneId. 当 {@code toZoneId} 为 null时，采用 {@link TZ#DEFAULT_ZONE}
     * @return another timezone LocalDateTime
     */
    public static LocalDateTime tzLocalDTToTZ(LocalDateTime localDateTime, ZoneId fromZoneId, ZoneId toZoneId) {
        if (fromZoneId == null) fromZoneId = TZ.DEFAULT_ZONE;
        if (toZoneId == null) toZoneId = TZ.DEFAULT_ZONE;
        if (fromZoneId.equals(toZoneId)) return localDateTime;
        return localDateTime.atZone(fromZoneId).withZoneSameInstant(toZoneId).toLocalDateTime();
    }

    /**
     * Convert date from fromZoneId Date to toZoneId ZonedDateTime <br>
     * 将 一个时区 的 Date 转成另一个时区的 ZonedDateTime
     *
     * @param date       date
     * @param fromZoneId fromZoneId. 当 {@code fromZoneId} 为 null时，采用 {@link TZ#DEFAULT_ZONE}
     * @param toZoneId   toZoneId. 当 {@code toZoneId} 为 null时，采用 {@link TZ#DEFAULT_ZONE}
     * @return ZonedDateTime
     */
    public static ZonedDateTime tzDateToZonedDT(Date date, ZoneId fromZoneId, ZoneId toZoneId) {
        if (fromZoneId == null) fromZoneId = TZ.DEFAULT_ZONE;
        if (toZoneId == null) toZoneId = TZ.DEFAULT_ZONE;
        return date.toInstant().atZone(TZ.DEFAULT_ZONE).withZoneSameLocal(fromZoneId).withZoneSameInstant(toZoneId);
    }

    /**
     * Date 转 Calendar
     *
     * @param date   date
     * @param zoneId 时区。当前 zoneId 为 null 时，采用默认时区。
     * @return Calendar
     * @since 0.3.0
     */
    public static Calendar toCalendar(Date date, ZoneId zoneId) {
        Calendar calendar = zoneId == null ? Calendar.getInstance() : Calendar.getInstance(TimeZone.getTimeZone(zoneId));
        calendar.setTime(date);
        return calendar;
    }

    /**
     * Calendar 转成 其他时区，返回新的 Calendar 对象
     *
     * @param calendar calendar
     * @param zoneId   时区。当前 zoneId 为 null 时，采用默认时区。
     * @return Calendar
     * @since 0.3.0
     */
    public static Calendar toCalendar(Calendar calendar, ZoneId zoneId) {
        if (calendar == null) return null;
        if (zoneId == null) {
            return cloneCalendar(calendar);
        } else {
            Calendar newCalendar = cloneCalendar(calendar);
            newCalendar.setTimeZone(TimeZone.getTimeZone(zoneId));
            return newCalendar;
        }
    }

    /**
     * Instant 转成 Calendar
     *
     * @param instant instant
     * @return Calendar
     * @since 0.3.0
     */
    public static Calendar toCalendar(Instant instant) {
        if (instant == null) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(instant.toEpochMilli());
        return calendar;
    }

    /**
     * ZonedDateTime 转成 Calendar
     *
     * @param zonedDateTime zonedDateTime
     * @return Calendar
     * @since 0.3.0
     */
    public static Calendar toCalendar(ZonedDateTime zonedDateTime) {
        if (zonedDateTime == null) return null;
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(zonedDateTime.getZone()));
        calendar.setTimeInMillis(zonedDateTime.toInstant().toEpochMilli());
        return calendar;
    }

    /**
     * OffsetDateTime 转成 Calendar
     *
     * @param offsetDateTime offsetDateTime
     * @return Calendar
     * @since 0.3.0
     */
    public static Calendar toCalendar(OffsetDateTime offsetDateTime) {
        if (offsetDateTime == null) return null;
        return toCalendar(offsetDateTime.toZonedDateTime());
    }

    /**
     * 克隆一个 Calendar
     *
     * @param calendar calendar
     * @return Calendar
     * @since 0.3.0
     */
    public static Calendar cloneCalendar(Calendar calendar) {
        if (calendar == null) return null;
        return (Calendar) calendar.clone();
    }

    /**
     * 将某个时间类型转为微秒
     *
     * @param amount     时间量
     * @param chronoUnit 时间单位
     * @return 总微秒数
     * @since 0.3.0
     */
    public static BigInteger toMicros(long amount, ChronoUnit chronoUnit) {
        if (amount == 0) return BigInteger.ZERO;
        switch (chronoUnit) {
            case HOURS:
                return BigInteger.valueOf(amount).multiply(BigInteger.valueOf(3600000000L));
            case MINUTES:
                return BigInteger.valueOf(amount).multiply(BigInteger.valueOf(60000000L));
            case SECONDS:
                return BigInteger.valueOf(amount).multiply(BigInteger.valueOf(1000000L));
            case MILLIS:
                return BigInteger.valueOf(amount).multiply(BigInteger.valueOf(1000L));
            case MICROS:
                return BigInteger.valueOf(amount);
            case NANOS:
                return BigInteger.valueOf(0);
        }
        throw new UnsupportedTemporalTypeException("Unsupported unit: " + chronoUnit);
    }

    /**
     * 将某个时间类型转为纳秒
     *
     * @param amount     时间量
     * @param chronoUnit 时间单位
     * @return 总纳秒数
     * @since 0.3.0
     */
    public static BigInteger toNanos(long amount, ChronoUnit chronoUnit) {
        if (amount == 0) return BigInteger.ZERO;
        switch (chronoUnit) {
            case HOURS:
                return BigInteger.valueOf(amount).multiply(BigInteger.valueOf(3600000000000L));
            case MINUTES:
                return BigInteger.valueOf(amount).multiply(BigInteger.valueOf(60000000000L));
            case SECONDS:
                return BigInteger.valueOf(amount).multiply(BigInteger.valueOf(1000000000L));
            case MILLIS:
                return BigInteger.valueOf(amount).multiply(BigInteger.valueOf(1000000L));
            case MICROS:
                return BigInteger.valueOf(amount).multiply(BigInteger.valueOf(1000L));
            case NANOS:
                return BigInteger.valueOf(amount);
        }
        throw new UnsupportedTemporalTypeException("Unsupported unit: " + chronoUnit);
    }

    /**
     * 获取某个月的总天数
     *
     * @param year  当前年份
     * @param month 当前月份
     * @return 当前月的总天数
     * @since 0.3.0
     */
    public static int daysOfMonth(int year, int month) {
        return YearMonth.of(year, month).lengthOfMonth();
    }

    /**
     * 获取某个月的总天数
     *
     * @param temporal temporal
     * @return 当前月的总天数
     * @since 0.3.0
     */
    public static int daysOfMonth(Temporal temporal) {
        Objects.requireNonNull(temporal, "Parameter `temporal` must be non-null!");
        if (SUPPORTED_TEMPORAL_FOR_DOM.stream().noneMatch(c -> c.isAssignableFrom(temporal.getClass()))) {
            throw new UnsupportedTemporalTypeException("Only [" + SUPPORTED_TEMPORAL_FOR_DOM.stream().map(Class::getSimpleName).collect(Collectors.joining(", ")) + "] is supported for `temporal` parameter!");
        }

        if (temporal instanceof ChronoLocalDate) return ((ChronoLocalDate) temporal).lengthOfMonth();
        if (temporal instanceof YearMonth) return ((YearMonth) temporal).lengthOfMonth();

        return DateTime.of(temporal).daysOfMonth();
    }

    /**
     * 使用指定格式返回一天中的24小时
     *
     * @param withMode  0: 只返回小时；1：返回小时并在末尾拼接上00分钟；2：返回小时并在末尾拼接00分钟和00秒
     * @param separator 当 withMode 为 1，2时，需要指定分隔符。如果为 {@code null}，则默认采用 {@code ""}
     * @return 一天中的小时
     * @since 0.3.0
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
     * @since 0.3.0
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
     * @since 0.3.0
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
     * @since 0.3.0
     */
    public static List<String> secondsOfMinute(String minute, String separator) {
        return minutesOfHour(minute, false, separator);
    }

}
