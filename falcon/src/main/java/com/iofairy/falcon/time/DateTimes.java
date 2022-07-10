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

import java.time.*;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.*;

/**
 * DateTime Utils
 *
 * @since 0.2.5
 */
public final class DateTimes {
    /**
     * Supported units for {@code round} methods.
     */
    private static final List<ChronoUnit> SUPPORTED_UNITS_FOR_ROUND = Arrays.asList(YEARS, MONTHS, DAYS, HOURS, MINUTES, SECONDS);
    /**
     * Supported temporal for {@link #plus(Temporal, long, ChronoUnit)} and {@link #minus(Temporal, long, ChronoUnit)} methods.
     */
    private static final List<Class<? extends Temporal>> SUPPORTED_TEMPORAL_FOR_PM =
            Arrays.asList(LocalDateTime.class, ZonedDateTime.class, OffsetDateTime.class, Instant.class);
    /**
     * Supported temporal for {@code daysOfMonth} methods.
     */
    private static final List<Class<? extends Temporal>> SUPPORTED_TEMPORAL_FOR_DOM =
            Arrays.asList(LocalDateTime.class, ZonedDateTime.class, OffsetDateTime.class, Instant.class, YearMonth.class, ChronoLocalDate.class);
    /**
     * Supported temporal for {@code round} methods.
     */
    private static final List<Class<? extends Temporal>> SUPPORTED_TEMPORAL_FOR_ROUND =
            Arrays.asList(LocalDateTime.class, ZonedDateTime.class, OffsetDateTime.class, Instant.class);

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
     * 对时间进行增减操作，支持的时间类型：{@link Instant}，{@link LocalDateTime}，{@link ZonedDateTime}，{@link OffsetDateTime}。<br>
     * 注：
     * {@link Instant} 类型的时间会先转成 UTC 时区的 ZonedDateTime 进行增减。
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
        if (!SUPPORTED_TEMPORAL_FOR_PM.contains(temporal.getClass())) {
            throw new UnsupportedTemporalTypeException("Only [" + SUPPORTED_TEMPORAL_FOR_PM.stream().map(Class::getSimpleName).collect(Collectors.joining(", ")) + "] is supported for `temporal` parameter!");
        }
        if (temporal instanceof Instant) {
            ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant((Instant) temporal, TZ.UTC).plus(amount, unit);
            return (T) zonedDateTime.toInstant();
        }
        return (T) temporal.plus(amount, unit);
    }

    /**
     * 对时间进行增减操作，支持的时间类型：{@link Instant}，{@link LocalDateTime}，{@link ZonedDateTime}，{@link OffsetDateTime}。<br>
     * 注：
     * {@link Instant} 类型的时间会先转成 UTC 时区的 ZonedDateTime 进行增减。
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
        if (!SUPPORTED_TEMPORAL_FOR_PM.contains(temporal.getClass())) {
            throw new UnsupportedTemporalTypeException("Only [" + SUPPORTED_TEMPORAL_FOR_PM.stream().map(Class::getSimpleName).collect(Collectors.joining(", ")) + "] is supported for `temporal` parameter!");
        }
        if (temporal instanceof Instant) {
            ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant((Instant) temporal, TZ.UTC).minus(amount, unit);
            return (T) zonedDateTime.toInstant();
        }
        return (T) temporal.minus(amount, unit);
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
     * 对时间进行取整
     *
     * @param localDateTime 时间
     * @param chronoUnit    按此时间单位作为取整后的精度
     * @param roundingDT    取整类型，值为{code null}默认为：{@link RoundingDT#FLOOR}
     * @return 取整后的时间
     * @since 0.2.5
     */
    public static LocalDateTime round(LocalDateTime localDateTime, ChronoUnit chronoUnit, RoundingDT roundingDT) {
        Objects.requireNonNull(localDateTime, "Parameter `localDateTime` must be non-null!");
        if (!SUPPORTED_UNITS_FOR_ROUND.contains(chronoUnit)) {
            throw new UnsupportedTemporalTypeException("Only [" + SUPPORTED_UNITS_FOR_ROUND.stream().map(ChronoUnit::toString).collect(Collectors.joining(", ")) + "] is supported for `chronoUnit` parameter!");
        }
        roundingDT = roundingDT == null ? RoundingDT.FLOOR : roundingDT;

        LocalDateTime ldt = localDateTime;
        switch (chronoUnit) {
            case YEARS:
                switch (roundingDT) {
                    case CEILING:
                        if (!(ldt.getMonth() == Month.JANUARY && ldt.getDayOfMonth() == 1 && ldt.getHour() == 0 && ldt.getMinute() == 0 && ldt.getSecond() == 0 && ldt.getNano() == 0)) {
                            ldt = ldt.plusYears(1);
                        }

                        break;
                    case HALF_UP:
                        if (ldt.getMonthValue() >= 7) {     // 7月
                            ldt = ldt.plusYears(1);
                        }
                        break;
                }
                return LocalDateTime.of(ldt.getYear(), Month.JANUARY, 1, 0, 0, 0, 0);
            case MONTHS:
                switch (roundingDT) {
                    case CEILING:
                        if (!(ldt.getDayOfMonth() == 1 && ldt.getHour() == 0 && ldt.getMinute() == 0 && ldt.getSecond() == 0 && ldt.getNano() == 0)) {
                            ldt = ldt.plusMonths(1);
                        }
                        break;
                    case HALF_UP:
                        int dayOfMonth = ldt.getDayOfMonth();    // 当前是几号
                        int halfUpDay = halfUpDay(daysOfMonth(ldt));
                        if (dayOfMonth >= halfUpDay) {
                            ldt = ldt.plusMonths(1);
                        }
                        break;
                }
                return LocalDateTime.of(ldt.getYear(), ldt.getMonth(), 1, 0, 0, 0, 0);
            case DAYS:
                switch (roundingDT) {
                    case CEILING:
                        if (!(ldt.getHour() == 0 && ldt.getMinute() == 0 && ldt.getSecond() == 0 && ldt.getNano() == 0)) {
                            ldt = ldt.plusDays(1);
                        }

                        break;
                    case HALF_UP:
                        if (ldt.getHour() >= 12) {
                            ldt = ldt.plusDays(1);
                        }
                        break;
                }
                return LocalDateTime.of(ldt.getYear(), ldt.getMonth(), ldt.getDayOfMonth(), 0, 0, 0, 0);
            case HOURS:
                switch (roundingDT) {
                    case CEILING:
                        if (!(ldt.getMinute() == 0 && ldt.getSecond() == 0 && ldt.getNano() == 0)) {
                            ldt = ldt.plusHours(1);
                        }

                        break;
                    case HALF_UP:
                        if (ldt.getMinute() >= 30) {
                            ldt = ldt.plusHours(1);
                        }
                        break;
                }
                return LocalDateTime.of(ldt.getYear(), ldt.getMonth(), ldt.getDayOfMonth(), ldt.getHour(), 0, 0, 0);
            case MINUTES:
                switch (roundingDT) {
                    case CEILING:
                        if (!(ldt.getSecond() == 0 && ldt.getNano() == 0)) {
                            ldt = ldt.plusMinutes(1);
                        }
                        break;
                    case HALF_UP:
                        if (ldt.getSecond() >= 30) {
                            ldt = ldt.plusMinutes(1);
                        }
                        break;
                }
                return LocalDateTime.of(ldt.getYear(), ldt.getMonth(), ldt.getDayOfMonth(), ldt.getHour(), ldt.getMinute(), 0, 0);
            default:
                switch (roundingDT) {
                    case CEILING:
                        if (!(ldt.getNano() == 0)) {
                            ldt = ldt.plusSeconds(1);
                        }
                        break;
                    case HALF_UP:
                        if (ldt.getNano() >= 500000000) {
                            ldt = ldt.plusSeconds(1);
                        }
                        break;
                }
                return LocalDateTime.of(ldt.getYear(), ldt.getMonth(), ldt.getDayOfMonth(), ldt.getHour(), ldt.getMinute(), ldt.getSecond(), 0);
        }
    }

    /**
     * 对时间进行取整
     *
     * @param temporal   时间，若时间类型为 {@code Instant}，则以 {@code UTC} 时区为准，进行取整运算。
     * @param chronoUnit 按此时间单位作为取整后的精度
     * @param roundingDT 取整类型，值为{code null}默认为：{@link RoundingDT#FLOOR}
     * @param <T>        时间类型，若类型为 {@code Instant}，则以 {@code UTC} 时区为准，进行取整运算。
     * @return 取整后的时间
     * @since 0.2.5
     */
    @SuppressWarnings("unchecked")
    public static <T extends Temporal> T round(T temporal, ChronoUnit chronoUnit, RoundingDT roundingDT) {
        if (!SUPPORTED_TEMPORAL_FOR_ROUND.contains(temporal.getClass())) {
            throw new UnsupportedTemporalTypeException("Only [" + SUPPORTED_TEMPORAL_FOR_ROUND.stream().map(Class::getSimpleName).collect(Collectors.joining(", ")) + "] is supported for `temporal` parameter!");
        }
        if (temporal instanceof ZonedDateTime) {
            ZonedDateTime zdt = (ZonedDateTime) temporal;
            LocalDateTime ldt = round(zdt.toLocalDateTime(), chronoUnit, roundingDT);
            return (T) ldt.atZone(zdt.getZone());
        }
        if (temporal instanceof OffsetDateTime) {
            OffsetDateTime odt = (OffsetDateTime) temporal;
            LocalDateTime ldt = round(odt.toLocalDateTime(), chronoUnit, roundingDT);
            return (T) ldt.atZone(odt.getOffset());
        }
        if (temporal instanceof Instant) {
            Instant instant = (Instant) temporal;
            LocalDateTime ldt = LocalDateTime.ofInstant(instant, TZ.UTC);
            ldt = round(ldt, chronoUnit, roundingDT);
            return (T) ldt.atZone(TZ.UTC).toInstant();
        }

        return (T) round((LocalDateTime) temporal, chronoUnit, roundingDT);
    }

    /**
     * 对时间进行取整
     *
     * @param date       时间
     * @param chronoUnit 按此时间单位作为取整后的精度
     * @param roundingDT 取整类型，值为{code null}默认为：{@link RoundingDT#FLOOR}
     * @return 取整后的时间
     * @since 0.2.5
     */
    public static Date round(Date date, ChronoUnit chronoUnit, RoundingDT roundingDT) {
        Objects.requireNonNull(date, "Parameter `date` must be non-null!");
        return round(calendar(date), chronoUnit, roundingDT).getTime();
    }

    /**
     * 对时间进行取整
     *
     * @param calendar   时间
     * @param chronoUnit 按此时间单位作为取整后的精度
     * @param roundingDT 取整类型，值为{code null}默认为：{@link RoundingDT#FLOOR}
     * @return 取整后的时间
     * @since 0.2.5
     */
    public static Calendar round(Calendar calendar, ChronoUnit chronoUnit, RoundingDT roundingDT) {
        Objects.requireNonNull(calendar, "Parameter `calendar` must be non-null!");
        if (!SUPPORTED_UNITS_FOR_ROUND.contains(chronoUnit)) {
            throw new UnsupportedTemporalTypeException("Only [" + SUPPORTED_UNITS_FOR_ROUND.stream().map(ChronoUnit::toString).collect(Collectors.joining(", ")) + "] is supported for `chronoUnit` parameter!");
        }
        roundingDT = roundingDT == null ? RoundingDT.FLOOR : roundingDT;

        Calendar newCalendar = (Calendar) calendar.clone();
        switch (chronoUnit) {
            case YEARS:
                switch (roundingDT) {
                    case CEILING:
                        if (!(newCalendar.get(Calendar.MONTH) == Calendar.JANUARY && newCalendar.get(Calendar.DAY_OF_MONTH) == 1
                                && newCalendar.get(Calendar.HOUR_OF_DAY) == 0 && newCalendar.get(Calendar.MINUTE) == 0 && newCalendar.get(Calendar.SECOND) == 0)) {
                            newCalendar.add(Calendar.YEAR, 1);
                        }

                        break;
                    case HALF_UP:
                        if (newCalendar.get(Calendar.MONTH) >= 6) {     // 6 代表 7月
                            newCalendar.add(Calendar.YEAR, 1);
                        }
                        break;
                }
                newCalendar.set(newCalendar.get(Calendar.YEAR), Calendar.JANUARY, 1, 0, 0, 0);

                break;
            case MONTHS:
                switch (roundingDT) {
                    case CEILING:
                        if (!(newCalendar.get(Calendar.DAY_OF_MONTH) == 1 && newCalendar.get(Calendar.HOUR_OF_DAY) == 0
                                && newCalendar.get(Calendar.MINUTE) == 0 && newCalendar.get(Calendar.SECOND) == 0)) {
                            newCalendar.add(Calendar.MONTH, 1);
                        }
                        break;
                    case HALF_UP:
                        int dayOfMonth = newCalendar.get(Calendar.DAY_OF_MONTH);    // 当前是几号
                        int halfUpDay = halfUpDay(daysOfMonth(newCalendar));
                        if (dayOfMonth >= halfUpDay) {
                            newCalendar.add(Calendar.MONTH, 1);
                        }
                        break;
                }
                newCalendar.set(newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), 1, 0, 0, 0);

                break;
            case DAYS:
                switch (roundingDT) {
                    case CEILING:
                        if (!(newCalendar.get(Calendar.HOUR_OF_DAY) == 0 && newCalendar.get(Calendar.MINUTE) == 0 && newCalendar.get(Calendar.SECOND) == 0)) {
                            newCalendar.add(Calendar.DAY_OF_MONTH, 1);
                        }
                        break;
                    case HALF_UP:
                        if (newCalendar.get(Calendar.HOUR_OF_DAY) >= 12) {
                            newCalendar.add(Calendar.DAY_OF_MONTH, 1);
                        }
                        break;
                }
                newCalendar.set(newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

                break;
            case HOURS:
                switch (roundingDT) {
                    case CEILING:
                        if (!(newCalendar.get(Calendar.MINUTE) == 0 && newCalendar.get(Calendar.SECOND) == 0)) {
                            newCalendar.add(Calendar.HOUR_OF_DAY, 1);
                        }
                        break;
                    case HALF_UP:
                        if (newCalendar.get(Calendar.MINUTE) >= 30) {
                            newCalendar.add(Calendar.HOUR_OF_DAY, 1);
                        }
                        break;
                }
                newCalendar.set(newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH),
                        newCalendar.get(Calendar.HOUR_OF_DAY), 0, 0);

                break;
            case MINUTES:
                switch (roundingDT) {
                    case CEILING:
                        if (!(newCalendar.get(Calendar.SECOND) == 0)) {
                            newCalendar.add(Calendar.MINUTE, 1);
                        }
                        break;
                    case HALF_UP:
                        if (newCalendar.get(Calendar.SECOND) >= 30) {
                            newCalendar.add(Calendar.MINUTE, 1);
                        }
                        break;
                }
                newCalendar.set(newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH),
                        newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), 0);
                break;
        }
        return newCalendar;
    }

    /**
     * 获取需要向上取整的最小天数
     *
     * @param daysOfMonth 当前月份的总天数
     * @return 需要向上取整的最小天数
     * @since 0.2.5
     */
    private static int halfUpDay(int daysOfMonth) {
        return daysOfMonth == 28 || daysOfMonth == 29 ? 15 : 16;
    }
}
