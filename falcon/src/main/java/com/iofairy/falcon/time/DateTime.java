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

import com.iofairy.except.UnexpectedParameterException;
import com.iofairy.falcon.range.IntervalType;
import com.iofairy.top.G;
import com.iofairy.top.S;
import com.iofairy.tuple.Tuple;
import com.iofairy.tuple.Tuple2;

import java.io.Serializable;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.*;
import java.util.*;
import java.util.stream.Collectors;


/**
 * The {@code DateTime} class wraps a value of the {@link Date} or {@link Calendar} or {@link Instant}
 * or {@link LocalDateTime} or {@link OffsetDateTime} or {@link ZonedDateTime} type. <br>
 *
 * <p>
 * This class is <b>immutable</b> and <b>thread-safe.</b>
 * </p>
 *
 * @param <T> The type of DateTime's value
 * @since 0.3.0
 */
public class DateTime<T> implements Temporal, Comparable<DateTime<?>>, Serializable {
    private static final long serialVersionUID = 6206615563566L;

    private final T dateTime;
    /**
     * The local date-time.
     */
    private final LocalDateTime localDateTime;
    /**
     * 时区偏移量。<br>
     * 当 dateTime 是 {@link Instant} 类型时，时区偏移量采用当前默认时区的偏移量；<br>
     * 当 dateTime 是 {@link LocalDateTime} 类型时，时区偏移量采用当前默认时区的偏移量。
     */
    private final ZoneOffset offset;
    /**
     * 时区。<br>
     * 当 dateTime 是 {@link Instant} 类型时，时区采用当前默认时区 {@link TZ#DEFAULT_ZONE}；<br>
     * 当 dateTime 是 {@link LocalDateTime} 类型时，时区采用当前默认时区 {@link TZ#DEFAULT_ZONE}。
     */
    private final ZoneId zone;
    /**
     * An instantaneous point on the time-line.
     */
    private final Instant instant;
    /**
     * The date-time with a time-zone
     */
    private final ZonedDateTime zonedDateTime;

    /**
     * Supported date time
     */
    private static final List<Class<?>> SUPPORTED_DATETIME =
            Arrays.asList(Date.class, Calendar.class, LocalDateTime.class, ZonedDateTime.class, OffsetDateTime.class, Instant.class);
    /**
     * Supported date time string
     */
    private static final String SUPPORTED_DATETIME_STRING = SUPPORTED_DATETIME.stream().map(Class::getSimpleName).collect(Collectors.joining(", "));

    public DateTime(T dateTime) {
        this(dateTime, true);
    }

    private DateTime(T dateTime, boolean checkValue) {
        if (checkValue) {
            Objects.requireNonNull(dateTime, "Parameter `dateTime` must be non-null!");
            if (SUPPORTED_DATETIME.stream().noneMatch(c -> c.isAssignableFrom(dateTime.getClass()))) {
                throw new UnsupportedTemporalTypeException("Only [" + SUPPORTED_DATETIME_STRING + "] is supported for `dateTime` parameter!");
            }
        }

        this.dateTime = getDateTime(dateTime);

        Tuple2<Instant, ZonedDateTime> zdtAndInstant = toZDTAndInstant(dateTime);
        this.instant = zdtAndInstant._1;
        this.zonedDateTime = zdtAndInstant._2;
        this.localDateTime = zonedDateTime.toLocalDateTime();
        this.offset = zonedDateTime.getOffset();
        this.zone = zonedDateTime.getZone();

    }

    public static <T> DateTime<T> of(T dateTime) {
        return new DateTime<>(dateTime, true);
    }

    static <T> DateTime<T> from(T dateTime) {
        return new DateTime<>(dateTime, false);
    }

    @SuppressWarnings("unchecked")
    private T getDateTime(T dt) {
        if (dt instanceof Date) {
            return (T) DateTimes.clone((Date) dt);
        } else if (dt instanceof Calendar) {
            return (T) DateTimes.clone((Calendar) dt);
        } else {
            return dt;
        }
    }

    /**
     * Get the <b>copied</b> {@link DateTime#dateTime} value.
     *
     * @return dateTime
     */
    public T get() {
        return getDateTime(dateTime);
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public ZoneOffset getOffset() {
        return offset;
    }

    public ZoneId getZone() {
        return zone;
    }

    public Instant getInstant() {
        return instant;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    private Tuple2<Instant, ZonedDateTime> toZDTAndInstant(Object dateTimeObj) {
        ZonedDateTime zdt = null;
        Instant ins = null;
        if (dateTimeObj instanceof Calendar) {
            Calendar calendar = (Calendar) dateTime;
            ins = calendar.toInstant();
            zdt = ins.atZone(calendar.getTimeZone().toZoneId());
            return Tuple.of(ins, zdt);
        }
        if (dateTimeObj instanceof Date) {
            ins = ((Date) dateTime).toInstant();
            zdt = ins.atZone(TZ.DEFAULT_ZONE);
            return Tuple.of(ins, zdt);
        }
        if (dateTimeObj instanceof Instant) {
            ins = (Instant) dateTime;
            zdt = ZonedDateTime.ofInstant(ins, TZ.DEFAULT_ZONE);
            return Tuple.of(ins, zdt);
        }
        if (dateTimeObj instanceof LocalDateTime) {
            zdt = ((LocalDateTime) dateTimeObj).atZone(TZ.DEFAULT_ZONE);
            ins = zdt.toInstant();
            return Tuple.of(ins, zdt);
        }
        if (dateTimeObj instanceof OffsetDateTime) {
            ins = ((OffsetDateTime) dateTimeObj).toInstant();
            zdt = ((OffsetDateTime) dateTimeObj).toZonedDateTime();
            return Tuple.of(ins, zdt);
        }
        zdt = (ZonedDateTime) dateTimeObj;
        ins = zdt.toInstant();
        return Tuple.of(ins, zdt);
    }

    /**
     * 将 DateTime 转为 {@link TZ#UTC} 时区的 {@code ZonedDateTime}
     *
     * @return ZonedDateTime
     */
    public ZonedDateTime toUTCZonedDT() {
        return toZonedDT(TZ.UTC);
    }

    /**
     * 将 DateTime 转为 {@link TZ#DEFAULT_ZONE} 时区的 {@code ZonedDateTime}
     *
     * @return ZonedDateTime
     */
    public ZonedDateTime toDefaultZonedDT() {
        return toZonedDT(TZ.DEFAULT_ZONE);
    }

    /**
     * 将 DateTime 转为指定时区的 {@code ZonedDateTime}
     *
     * @param zoneId zoneId. 当 zoneId为{@code null}时，且 dateTime 是 {@code ZonedDateTime} 或 {@code OffsetDateTime} 或 {@code Calendar} 类型时，
     *               默认采用dateTime自己的时区；否则，默认采用{@link TZ#DEFAULT_ZONE}
     * @return ZonedDateTime
     */
    public ZonedDateTime toZonedDT(ZoneId zoneId) {
        if (zoneId == null) return zonedDateTime;

        if (dateTime instanceof LocalDateTime) return localDateTime.atZone(zoneId);
        return zonedDateTime.withZoneSameInstant(zoneId);
    }


    /**
     * 将 DateTime 转为 {@link ZoneOffset#UTC} 时区的 {@code OffsetDateTime}
     *
     * @return OffsetDateTime
     */
    public OffsetDateTime toUTCOffsetDT() {
        return toOffsetDT(ZoneOffset.UTC);
    }

    /**
     * 将 DateTime 转为 {@link DateTimes#defaultOffset()} 时区的 {@code OffsetDateTime}
     *
     * @return OffsetDateTime
     */
    public OffsetDateTime toDefaultOffsetDT() {
        return toOffsetDT(DateTimes.defaultOffset());
    }

    /**
     * 将 DateTime 转为指定时区的 {@code OffsetDateTime}
     *
     * @param zoneOffset zoneOffset. 当 zoneOffset为{@code null}时，且 datetime 是 {@code OffsetDateTime} 或 {@code ZonedDateTime} 或 {@code Calendar} 类型时，
     *                   默认采用datetime自己的时区偏移；否则，默认采用{@link DateTimes#defaultOffset()}时区偏移
     * @return OffsetDateTime
     */
    public OffsetDateTime toOffsetDT(ZoneOffset zoneOffset) {
        if (zoneOffset == null) return zonedDateTime.toOffsetDateTime();

        if (dateTime instanceof OffsetDateTime) {
            OffsetDateTime odt = (OffsetDateTime) dateTime;
            return odt.getOffset().equals(zoneOffset) ? odt : odt.withOffsetSameInstant(zoneOffset);
        }
        if (dateTime instanceof ZonedDateTime) return zonedDateTime.toOffsetDateTime().withOffsetSameInstant(zoneOffset);
        if (dateTime instanceof Calendar) return instant.atOffset(zoneOffset);
        if (dateTime instanceof LocalDateTime) return ((LocalDateTime) dateTime).atOffset(zoneOffset);
        if (dateTime instanceof Instant) return OffsetDateTime.ofInstant(instant, zoneOffset);

        return instant.atOffset(zoneOffset);
    }

    /**
     * 将 DateTime 转为 {@link TZ#UTC} 时区的 {@code Calendar}
     *
     * @return Calendar
     */
    public Calendar toUTCCalendar() {
        return toCalendar(TZ.UTC);
    }

    /**
     * 将 DateTime 转为 {@link TZ#DEFAULT_ZONE} 时区的 {@code Calendar}
     *
     * @return Calendar
     */
    public Calendar toDefaultCalendar() {
        return toCalendar(TZ.DEFAULT_ZONE);
    }

    /**
     * 将 DateTime 转为指定时区的 Calendar。
     *
     * @param zoneId 时区。当 zoneId为{@code null}时，且 dateTime 是 {@code ZonedDateTime} 或 {@code OffsetDateTime} 或 {@code Calendar} 类型时，
     *               默认采用dateTime自己的时区；否则，默认采用{@link TZ#DEFAULT_ZONE}
     * @return Calendar
     */
    public Calendar toCalendar(ZoneId zoneId) {
        if (zoneId == null) {
            if (dateTime instanceof Calendar) return DateTimes.clone((Calendar) dateTime);
            return DateTimes.toCalendar(zonedDateTime);
        }

        if (dateTime instanceof Calendar) {
            Calendar calendar = DateTimes.clone((Calendar) dateTime);
            calendar.setTimeZone(TimeZone.getTimeZone(zoneId));
            return calendar;
        }

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(zoneId));
        if (dateTime instanceof Date) {
            Date date = (Date) dateTime;
            calendar.setTime(date);
            return calendar;
        }

        if (dateTime instanceof Instant) {
            calendar.setTimeInMillis(instant.toEpochMilli());
            return calendar;
        }

        return DateTimes.toCalendar(toZonedDT(zoneId));
    }

    /**
     * ZonedDateTime 转成 Calendar，且返回的 Calendar 与参数 calendar 相同类型
     *
     * @param calendar      calendar，用于指示返回的 Calendar 的类型
     * @param zonedDateTime zonedDateTime
     * @return 返回与参数 calendar 相同类型的 Calendar
     */
    Calendar toCalendar(Calendar calendar, ZonedDateTime zonedDateTime) {
        Calendar newCalendar = (Calendar) calendar.clone();
        newCalendar.setTimeZone(TimeZone.getTimeZone(zonedDateTime.getZone()));
        newCalendar.setTimeInMillis(zonedDateTime.toInstant().toEpochMilli());
        return newCalendar;
    }

    /**
     * 将 DateTime 转为 {@code LocalDateTime}
     *
     * @return LocalDateTime
     */
    public LocalDateTime toLocalDateTime() {
        return localDateTime;
    }

    /**
     * 将 DateTime 转为 特定时区的 Date <br>
     * 注：<br>
     * {@link Date} 是指当前默认时区的时间，所以指定时区没有意义，总是会转换成默认时区。
     *
     * @return Date
     */
    public Date toDate() {
        if (dateTime instanceof Date) return (Date) dateTime;
        if (dateTime instanceof Calendar) return ((Calendar) dateTime).getTime();
        return Date.from(instant);
    }

    /**
     * 将 DateTime 转为 {@code Instant}。<br>
     * {@code LocalDateTime} 转为 {@code Instant} 时，以 <b>默认时区</b> 的 {@code LocalDateTime} 转为 {@code Instant}。
     *
     * @return Instant
     */
    public Instant toInstant() {
        return instant;
    }

    /**
     * 对 DateTime 进行增减操作。<br>
     * 注：
     * {@link Instant} 类型的时间会先转成 {@link TZ#DEFAULT_ZONE} 时区的 ZonedDateTime 进行增减。
     *
     * @param amount 增减的量
     * @param unit   时间单位
     * @return 增减后的 DateTime
     */
    @Override
    @SuppressWarnings("unchecked")
    public DateTime<T> plus(long amount, TemporalUnit unit) {
        if (amount == 0) return this;
        if (dateTime instanceof Instant) {
            ZonedDateTime zdt = zonedDateTime.plus(amount, unit);
            return DateTime.from((T) zdt.toInstant());
        }
        if (dateTime instanceof Calendar) {
            ZonedDateTime zdt = zonedDateTime.plus(amount, unit);
            return DateTime.from((T) toCalendar((Calendar) dateTime, zdt));
        }
        if (dateTime instanceof Date) {
            ZonedDateTime zdt = zonedDateTime.plus(amount, unit);
            return DateTime.from((T) Date.from(zdt.toInstant()));
        }
        return DateTime.from((T) ((Temporal) dateTime).plus(amount, unit));
    }

    /**
     * 对 DateTime 进行增减操作。
     *
     * @param amountToAdd 增减的量
     * @return 增减后的 DateTime
     */
    @Override
    @SuppressWarnings("unchecked")
    public DateTime<T> plus(TemporalAmount amountToAdd) {
        if (amountToAdd instanceof ChronoInterval) {
            return ((ChronoInterval) amountToAdd).addTo(this);
        } else {
            if (dateTime instanceof Instant) {
                ZonedDateTime zdt = (ZonedDateTime) amountToAdd.addTo(zonedDateTime);
                return DateTime.from((T) zdt.toInstant());
            }

            if (dateTime instanceof Date) {
                ZonedDateTime zdt = (ZonedDateTime) amountToAdd.addTo(zonedDateTime);
                return DateTime.from((T) Date.from(zdt.toInstant()));
            }

            if (dateTime instanceof Calendar) {
                ZonedDateTime zdt = (ZonedDateTime) amountToAdd.addTo(zonedDateTime);
                return DateTime.from((T) toCalendar((Calendar) dateTime, zdt));
            }

            return DateTime.from((T) amountToAdd.addTo((Temporal) dateTime));
        }
    }

    public DateTime<T> plusYears(long years) {
        return plus(years, ChronoUnit.YEARS);
    }

    public DateTime<T> plusMonths(long months) {
        return plus(months, ChronoUnit.MONTHS);
    }

    public DateTime<T> plusDays(long days) {
        return plus(days, ChronoUnit.DAYS);
    }

    public DateTime<T> plusHours(long hours) {
        return plus(hours, ChronoUnit.HOURS);
    }

    public DateTime<T> plusMinutes(long minutes) {
        return plus(minutes, ChronoUnit.MINUTES);
    }

    public DateTime<T> plusSeconds(long seconds) {
        return plus(seconds, ChronoUnit.SECONDS);
    }

    public DateTime<T> plusMillis(long millis) {
        return plus(millis, ChronoUnit.MILLIS);
    }

    public DateTime<T> plusMicros(long micros) {
        return plus(micros, ChronoUnit.MICROS);
    }

    public DateTime<T> plusNanos(long nanos) {
        return plus(nanos, ChronoUnit.NANOS);
    }

    /**
     * 对 DateTime 进行增减操作。<br>
     * 注：
     * {@link Instant} 类型的时间会先转成 {@link TZ#DEFAULT_ZONE} 时区的 ZonedDateTime 进行增减。
     *
     * @param amount 增减的量
     * @param unit   时间单位
     * @return 增减后的 DateTime
     */
    @Override
    @SuppressWarnings("unchecked")
    public DateTime<T> minus(long amount, TemporalUnit unit) {
        if (amount == 0) return this;
        if (dateTime instanceof Instant) {
            ZonedDateTime zdt = zonedDateTime.minus(amount, unit);
            return DateTime.from((T) zdt.toInstant());
        }
        if (dateTime instanceof Calendar) {
            ZonedDateTime zdt = zonedDateTime.minus(amount, unit);
            return DateTime.from((T) toCalendar((Calendar) dateTime, zdt));
        }
        if (dateTime instanceof Date) {
            ZonedDateTime zdt = zonedDateTime.minus(amount, unit);
            return DateTime.from((T) Date.from(zdt.toInstant()));
        }
        return DateTime.from((T) ((Temporal) dateTime).minus(amount, unit));
    }

    /**
     * 对 DateTime 进行增减操作。
     *
     * @param amountToSubtract 增减的量
     * @return 增减后的 DateTime
     */
    @SuppressWarnings("unchecked")
    public DateTime<T> minus(TemporalAmount amountToSubtract) {
        if (amountToSubtract instanceof ChronoInterval) {
            return ((ChronoInterval) amountToSubtract).subtractFrom(this);
        } else {
            if (dateTime instanceof Instant) {
                ZonedDateTime zdt = (ZonedDateTime) amountToSubtract.subtractFrom(zonedDateTime);
                return DateTime.from((T) zdt.toInstant());
            }

            if (dateTime instanceof Date) {
                ZonedDateTime zdt = (ZonedDateTime) amountToSubtract.subtractFrom(zonedDateTime);
                return DateTime.from((T) Date.from(zdt.toInstant()));
            }

            if (dateTime instanceof Calendar) {
                ZonedDateTime zdt = (ZonedDateTime) amountToSubtract.subtractFrom(zonedDateTime);
                return DateTime.from((T) toCalendar((Calendar) dateTime, zdt));
            }

            return DateTime.from((T) amountToSubtract.subtractFrom((Temporal) dateTime));
        }
    }

    public DateTime<T> minusYears(long years) {
        return minus(years, ChronoUnit.YEARS);
    }

    public DateTime<T> minusMonths(long months) {
        return minus(months, ChronoUnit.MONTHS);
    }

    public DateTime<T> minusDays(long days) {
        return minus(days, ChronoUnit.DAYS);
    }

    public DateTime<T> minusHours(long hours) {
        return minus(hours, ChronoUnit.HOURS);
    }

    public DateTime<T> minusMinutes(long minutes) {
        return minus(minutes, ChronoUnit.MINUTES);
    }

    public DateTime<T> minusSeconds(long seconds) {
        return minus(seconds, ChronoUnit.SECONDS);
    }

    public DateTime<T> minusMillis(long millis) {
        return minus(millis, ChronoUnit.MILLIS);
    }

    public DateTime<T> minusMicros(long micros) {
        return minus(micros, ChronoUnit.MICROS);
    }

    public DateTime<T> minusNanos(long nanos) {
        return minus(nanos, ChronoUnit.NANOS);
    }

    @Override
    @SuppressWarnings("unchecked")
    public DateTime<T> with(TemporalField field, long newValue) {
        if (dateTime instanceof LocalDateTime || dateTime instanceof OffsetDateTime || dateTime instanceof ZonedDateTime) {
            return DateTime.from((T) ((Temporal) dateTime).with(field, newValue));
        }

        ZonedDateTime zdt = zonedDateTime.with(field, newValue);
        if (dateTime instanceof Date) {
            return DateTime.from((T) Date.from(zdt.toInstant()));
        }
        if (dateTime instanceof Calendar) {
            return DateTime.from((T) toCalendar((Calendar) dateTime, zdt));
        }
        return DateTime.from((T) zdt.toInstant());
    }

    public DateTime<T> withYear(int year) {
        return with(ChronoField.YEAR, year);
    }

    public DateTime<T> withMonth(int month) {
        return with(ChronoField.MONTH_OF_YEAR, month);
    }

    public DateTime<T> withDayOfYear(int dayOfYear) {
        return with(ChronoField.DAY_OF_YEAR, dayOfYear);
    }

    public DateTime<T> withDayOfMonth(int dayOfMonth) {
        return with(ChronoField.DAY_OF_MONTH, dayOfMonth);
    }

    public DateTime<T> withHour(int hour) {
        return with(ChronoField.HOUR_OF_DAY, hour);
    }

    public DateTime<T> withMinute(int minute) {
        return with(ChronoField.MINUTE_OF_HOUR, minute);
    }

    public DateTime<T> withSecond(int second) {
        return with(ChronoField.SECOND_OF_MINUTE, second);
    }

    public DateTime<T> withNano(int nanoOfSecond) {
        return with(ChronoField.NANO_OF_SECOND, nanoOfSecond);
    }

    public int get(TemporalField field) {
        return dateTime instanceof LocalDateTime ? localDateTime.get(field) : zonedDateTime.get(field);
    }

    public int getYear() {
        return localDateTime.getYear();
    }

    public int getMonthValue() {
        return localDateTime.getMonthValue();
    }

    public Month getMonth() {
        return localDateTime.getMonth();
    }

    public int getDayOfYear() {
        return localDateTime.getDayOfYear();
    }

    public int getDayOfMonth() {
        return localDateTime.getDayOfMonth();
    }

    public DayOfWeek getDayOfWeek() {
        return localDateTime.getDayOfWeek();
    }

    public int getHour() {
        return localDateTime.getHour();
    }

    public int getMinute() {
        return localDateTime.getMinute();
    }

    public int getSecond() {
        return localDateTime.getSecond();
    }

    public int getNano() {
        return localDateTime.getNano();
    }

    /**
     * 对 DateTime 进行取整
     *
     * @param chronoUnit 按此时间单位作为取整后的精度
     * @param roundingDT 取整类型，值为{@code null}默认为：{@link RoundingDT#FLOOR}
     * @return 取整后的时间
     */
    @SuppressWarnings("unchecked")
    public DateTime<T> round(ChronoUnit chronoUnit, RoundingDT roundingDT) {
        if (dateTime instanceof Calendar) return DateTime.from((T) DateTimeRound.round((Calendar) dateTime, chronoUnit, roundingDT));
        if (dateTime instanceof Date) return DateTime.from((T) DateTimeRound.round((Date) dateTime, chronoUnit, roundingDT));
        if (dateTime instanceof LocalDateTime) return DateTime.from((T) DateTimeRound.round(localDateTime, chronoUnit, roundingDT));
        return DateTime.from((T) DateTimeRound.round((Temporal) dateTime, chronoUnit, roundingDT));
    }

    /**
     * 对 DateTime 的 时，分，秒 进行取整。
     *
     * @param chronoUnit 按此时间单位作为取整后的精度
     * @param amountUnit 时间步长（不分正负）
     * @param roundingDT 取整类型，值为{@code null}默认为：{@link RoundingDT#FLOOR}
     * @return 取整后的时间
     */
    @SuppressWarnings("unchecked")
    public DateTime<T> roundTime(ChronoUnit chronoUnit, int amountUnit, RoundingDT roundingDT) {
        if (dateTime instanceof Calendar) return DateTime.from((T) DateTimeRound.roundTime((Calendar) dateTime, chronoUnit, amountUnit, roundingDT));
        if (dateTime instanceof Date) return DateTime.from((T) DateTimeRound.roundTime((Date) dateTime, chronoUnit, amountUnit, roundingDT));
        if (dateTime instanceof LocalDateTime) return DateTime.from((T) DateTimeRound.roundTime(localDateTime, chronoUnit, amountUnit, roundingDT));
        return DateTime.from((T) DateTimeRound.roundTime((Temporal) dateTime, chronoUnit, amountUnit, roundingDT));
    }

    /**
     * 从给定的时间，按指定的时间单位，指定的步长偏移时间，返回每次偏移后的所有时间列表
     *
     * @param shiftTimes         偏移的次数，负数：则时间往前偏移；正数：则时间往后偏移
     * @param amountUnit         偏移的步长（不分正负）
     * @param chronoUnit         时间单位
     * @param includeCurrentTime 最后列表是否包含 fromDate
     * @return 每次偏移后的所有时间列表
     */
    @SuppressWarnings("unchecked")
    public List<T> datesByShift(int shiftTimes, int amountUnit, ChronoUnit chronoUnit, boolean includeCurrentTime) {
        if (dateTime instanceof Date) return (List<T>) DateTimeShift.datesByShift((Date) dateTime, shiftTimes, amountUnit, chronoUnit, includeCurrentTime);
        if (dateTime instanceof Calendar) return (List<T>) DateTimeShift.datesByShift((Calendar) dateTime, shiftTimes, amountUnit, chronoUnit, includeCurrentTime);
        return (List<T>) DateTimeShift.datesByShift((Temporal) dateTime, shiftTimes, amountUnit, chronoUnit, includeCurrentTime);
    }

    /**
     * 从给定的时间，按指定的时间单位，指定的步长偏移时间，返回每次偏移后的所有时间列表
     *
     * @param shiftTimes         偏移的次数，负数：则时间往前偏移；正数：则时间往后偏移
     * @param chronoUnit         时间单位
     * @param includeCurrentTime 最后列表是否包含 fromDate
     * @return 每次偏移后的所有时间列表
     */
    public List<T> datesByShift(int shiftTimes, ChronoUnit chronoUnit, boolean includeCurrentTime) {
        return datesByShift(shiftTimes, 1, chronoUnit, includeCurrentTime);
    }

    /**
     * 从给定的时间，按指定的时间单位，指定的步长偏移时间，返回每次偏移后的所有时间列表
     *
     * @param shiftTimes 偏移的次数，负数：则时间往前偏移；正数：则时间往后偏移
     * @param chronoUnit 时间单位
     * @return 每次偏移后的所有时间列表
     */
    public List<T> datesByShift(int shiftTimes, ChronoUnit chronoUnit) {
        return datesByShift(shiftTimes, 1, chronoUnit, true);
    }

    /**
     * 从给定的时间范围（开始时间，结束时间），按指定的时间单位，指定的步长偏移时间，返回每次偏移后的所有时间列表
     *
     * @param toDateTime   结束的时间
     * @param amountUnit   偏移的步长（不分正负）
     * @param chronoUnit   时间单位
     * @param intervalType 区间类型
     * @return 每次偏移后的所有时间列表
     */
    @SuppressWarnings("unchecked")
    public List<T> datesFromRange(DateTime<?> toDateTime, int amountUnit, ChronoUnit chronoUnit, IntervalType intervalType) {
        Objects.requireNonNull(toDateTime, "Parameters `toDate`, `toDateTime` must be non-null!");
        if (dateTime instanceof Date) return (List<T>) DateTimeShift.datesFromRange((Date) dateTime, toDateTime.toDate(), amountUnit, chronoUnit, intervalType);
        if (dateTime instanceof Calendar) return (List<T>) DateTimeShift.datesFromRange((Calendar) dateTime, toDateTime.toCalendar(null), amountUnit, chronoUnit, intervalType);
        return (List<T>) DateTimeShift.datesFromRange((Temporal) dateTime, toDateTime.toDefaultOffsetDT(), amountUnit, chronoUnit, intervalType);
    }

    /**
     * 从给定的时间范围（开始时间，结束时间），按指定的时间单位，指定的步长偏移时间，返回每次偏移后的所有时间列表
     *
     * @param toDateTime   结束的时间
     * @param chronoUnit   时间单位
     * @param intervalType 区间类型
     * @return 每次偏移后的所有时间列表
     */
    public List<T> datesFromRange(DateTime<?> toDateTime, ChronoUnit chronoUnit, IntervalType intervalType) {
        return datesFromRange(toDateTime, 1, chronoUnit, intervalType);
    }

    /**
     * 从给定的时间范围（开始时间，结束时间），按指定的时间单位，指定的步长偏移时间，返回每次偏移后的所有时间列表
     *
     * @param toDateTime 结束的时间
     * @param chronoUnit 时间单位
     * @return 每次偏移后的所有时间列表
     */
    public List<T> datesFromRange(DateTime<?> toDateTime, ChronoUnit chronoUnit) {
        return datesFromRange(toDateTime, 1, chronoUnit, IntervalType.CLOSED);
    }

    /**
     * 保留所提供的chronoUnit以及比chronoUnit大的单位的值，填充比 chronoUnit 小的单位的值为 0。
     *
     * @param chronoUnit 时间单位
     * @return DateTime
     * @since 0.3.1
     */
    public DateTime<T> fill0(ChronoUnit chronoUnit) {
        return round(chronoUnit, RoundingDT.FLOOR);
    }

    /**
     * 保留所提供的chronoUnit以及比chronoUnit大的单位的值，填充比 chronoUnit 小的单位的值为 所允许的最大值。
     *
     * @param chronoUnit 时间单位
     * @return DateTime
     * @since 0.3.1
     */
    public DateTime<T> fill9(ChronoUnit chronoUnit) {
        return round(chronoUnit, RoundingDT.FLOOR).plus(1, chronoUnit).minusNanos(1);
    }

    /**
     * 获取某个月的总天数
     *
     * @return 当前月的总天数
     */
    public int daysOfMonth() {
        return DateTimes.daysOfMonth(localDateTime.getYear(), localDateTime.getMonthValue());
    }

    /**
     * Formats this date-time using the specified DateTime Pattern
     *
     * @param dtPattern DateTime Pattern
     * @return the formatted date-time string, not null
     */
    public String format(String dtPattern) {
        if (S.isBlank(dtPattern)) dtPattern = "yyyy-MM-dd HH:mm:ss.SSS";
        return format(DateTimeFormatter.ofPattern(dtPattern));
    }

    /**
     * Formats this date-time using the specified formatter
     *
     * @param formatter DateTimeFormatter
     * @return the formatted date-time string, not null
     */
    public String format(DateTimeFormatter formatter) {
        return dateTime instanceof LocalDateTime ? localDateTime.format(formatter) : zonedDateTime.format(formatter);
    }

    public static DateTime<LocalDateTime> parse(CharSequence text) {
        return parse(text, "y-M-d H:m:s[.SSS][.SS][.S]");
    }

    public static DateTime<LocalDateTime> parse(CharSequence text, String dtPattern) {
        return parse(text, DateTimePattern.getDTF(dtPattern));
    }

    /**
     * Obtains an instance of {@code DateTime<LocalDateTime>} from a text string using a specific formatter.
     * <p>
     * The text is parsed using the formatter, returning a date-time.
     *
     * @param text      the text to parse, not null
     * @param formatter the formatter to use, not null
     * @return DateTime of LocalDateTime
     * @throws DateTimeParseException if the text cannot be parsed
     * @since 0.3.1
     */
    public static DateTime<LocalDateTime> parse(CharSequence text, DateTimeFormatter formatter) {
        return DateTime.from(LocalDateTime.parse(text, formatter));
    }

    public static DateTime<Date> parseDate(CharSequence text) {
        return parseDate(text, DateTimePattern.getDTF("y-M-d H:m:s[.SSS][.SS][.S]"), TZ.DEFAULT_ZONE);
    }

    public static DateTime<Date> parseDate(CharSequence text, ZoneId zoneId) {
        return parseDate(text, DateTimePattern.getDTF("y-M-d H:m:s[.SSS][.SS][.S]"), zoneId);
    }

    public static DateTime<Date> parseDate(CharSequence text, String dtPattern) {
        return parseDate(text, DateTimePattern.getDTF(dtPattern), TZ.DEFAULT_ZONE);
    }

    public static DateTime<Date> parseDate(CharSequence text, String dtPattern, ZoneId zoneId) {
        return parseDate(text, DateTimePattern.getDTF(dtPattern), zoneId);
    }

    public static DateTime<Date> parseDate(CharSequence text, DateTimeFormatter formatter) {
        return parseDate(text, formatter, TZ.DEFAULT_ZONE);
    }

    /**
     * 从 时间格式串 中获取 {@code DateTime<Date>}
     *
     * @param text      时间格式串
     * @param formatter formatter
     * @param zoneId    时间格式串所属的时区。null 则为 默认时区。
     * @return DateTime&lt;Date&gt;
     * @since 0.3.1
     */
    public static DateTime<Date> parseDate(CharSequence text, DateTimeFormatter formatter, ZoneId zoneId) {
        LocalDateTime ldt = LocalDateTime.parse(text, formatter);
        ZonedDateTime zdt = ldt.atZone(zoneId == null ? TZ.DEFAULT_ZONE : zoneId);
        return DateTime.from(Date.from(zdt.toInstant()));
    }

    @Override
    public long until(Temporal endExclusive, TemporalUnit unit) {
        OffsetDateTime offsetDateTime = toOffsetDT(null);
        OffsetDateTime endOffsetDateTime = DateTime.of(endExclusive).toOffsetDT(offsetDateTime.getOffset());
        return offsetDateTime.until(endOffsetDateTime, unit);
    }

    @Override
    public boolean isSupported(TemporalUnit unit) {
        if (dateTime instanceof Date || dateTime instanceof Calendar || dateTime instanceof Instant) {
            return zonedDateTime.isSupported(unit);
        }
        return ((Temporal) dateTime).isSupported(unit);
    }

    @Override
    public boolean isSupported(TemporalField field) {
        if (dateTime instanceof Date || dateTime instanceof Calendar || dateTime instanceof Instant) {
            return zonedDateTime.isSupported(field);
        }
        return ((Temporal) dateTime).isSupported(field);
    }

    @Override
    public long getLong(TemporalField field) {
        if (dateTime instanceof Date || dateTime instanceof Calendar || dateTime instanceof Instant) {
            return zonedDateTime.getLong(field);
        }
        return ((Temporal) dateTime).getLong(field);
    }

    public boolean isBefore(DateTime<?> otherDT) {
        return this.compareTo(otherDT) < 0;
    }

    public boolean isAfter(DateTime<?> otherDT) {
        return this.compareTo(otherDT) > 0;
    }

    public boolean isBeforeOrEquals(DateTime<?> otherDT) {
        return this.compareTo(otherDT) <= 0;
    }

    public boolean isAfterOrEquals(DateTime<?> otherDT) {
        return this.compareTo(otherDT) >= 0;
    }

    /**
     * 判断当前 DateTime 是否在提供的两个 DateTime 之间
     *
     * @param startDT      开始 DateTime
     * @param endDT        结束 DateTime
     * @param intervalType 区间类型。
     * @return 当前 DateTime 在提供的两个 DateTime 之间，则返回 {@code true}，否则返回 {@code false}
     * @since 0.3.3
     */
    public boolean in(DateTime<?> startDT, DateTime<?> endDT, IntervalType intervalType) {
        if (G.hasNull(startDT, endDT, intervalType)) throw new NullPointerException("Parameters `startDT`, `endDT`, `intervalType` must be non-null!");
        if (startDT.isAfterOrEquals(endDT)) throw new UnexpectedParameterException("Parameter `startDT` must be before `endDT`! ");

        switch (intervalType) {
            case OPEN:
                return this.isAfter(startDT) && this.isBefore(endDT);
            case CLOSED:
                return this.isAfterOrEquals(startDT) && this.isBeforeOrEquals(endDT);
            case OPEN_CLOSED:
                return this.isAfter(startDT) && this.isBeforeOrEquals(endDT);
            default:  // CLOSED_OPEN
                return this.isAfterOrEquals(startDT) && this.isBefore(endDT);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;

        if (obj instanceof DateTime) {
            DateTime<?> otherDT = (DateTime<?>) obj;
            int i = this.compareTo(otherDT);
            return i == 0;
        }
        return false;
    }

    @Override
    public int compareTo(DateTime<?> otherDT) {
        Objects.requireNonNull(otherDT, "Parameter `otherDT` must be non-null!");
        Instant otherInstant = otherDT.toInstant();
        if (instant.equals(otherInstant)) return 0;
        return instant.isBefore(otherInstant) ? -1 : 1;
    }

    /**
     * 详细的 toString
     *
     * @return 时间格式化输出
     */
    public String dtDetail() {
        if (dateTime instanceof Date || dateTime instanceof Calendar) return G.dtDetail(zonedDateTime);
        return G.dtDetail((Temporal) dateTime);
    }

    @Override
    public String toString() {
        if (dateTime instanceof Date || dateTime instanceof Calendar) return G.dtSimple(zonedDateTime);
        return G.dtSimple((Temporal) dateTime);
    }

}
