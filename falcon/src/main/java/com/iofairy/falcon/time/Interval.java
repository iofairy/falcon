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
import com.iofairy.falcon.util.DateTimeUtils;

import java.time.*;
import java.time.temporal.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.*;

/**
 * Interval
 *
 * @since 0.0.2
 */
public class Interval extends SignedInterval {
    public Interval(long centuries, long years, long months, long days, long hours, long minutes, long seconds, long millis, long micros, long nanos) {
        super(centuries, years, months, days, hours, minutes, seconds, millis, micros, nanos);
        if (centuries < 0 || years < 0 || months < 0 || days < 0 || hours < 0 || minutes < 0 || seconds < 0 || millis < 0 || micros < 0 || nanos < 0)
            throw new UnexpectedParameterException("Interval Constructor all parameters must not be negative number! ");
    }

    public Interval(long centuries, long years, long months, long days, long hours, long minutes, long seconds, long millis) {
        this(centuries, years, months, days, hours, minutes, seconds, millis, 0, 0);
    }

    public Interval(long years, long months, long days, long hours, long minutes, long seconds, long millis) {
        this(0, years, months, days, hours, minutes, seconds, millis, 0, 0);
    }

    public Interval(long years, long months, long days, long hours, long minutes, long seconds) {
        this(0, years, months, days, hours, minutes, seconds, 0, 0, 0);
    }

    public Interval(long years, long months, long days, long hours, long minutes) {
        this(0, years, months, days, hours, minutes, 0, 0, 0, 0);
    }

    /**
     * Obtain two {@link Temporal} datetime interval. It is recommended to call {@link #isSupported(Temporal)}
     * to check if the specified temporal is supported first.<br>
     * 获取两个{@link Temporal}的时间间隔。建议在调用此方法之前先确认是否支持该时间类型 {@link #isSupported(Temporal)}。
     *
     * @param startTemporal start Temporal
     * @param endTemporal   end Temporal
     * @return Interval
     */
    public static Interval between(Temporal startTemporal, Temporal endTemporal) {
        Objects.requireNonNull(startTemporal);
        Objects.requireNonNull(endTemporal);

        if (!isSupported(startTemporal) || !isSupported(endTemporal))
            throw new UnsupportedTemporalTypeException("Only [" + SUPPORTED_TEMPORAL.stream().map(Class::getSimpleName).collect(Collectors.joining(", ")) + "] is supported!");

        boolean isBefore = DateTimeUtils.toUTCZonedDT(startTemporal).isBefore(DateTimeUtils.toUTCZonedDT(endTemporal));
        Temporal tmpStartTemporal = isBefore ? startTemporal : endTemporal;
        Temporal tmpEndTemporal = isBefore ? endTemporal : startTemporal;

        SignedInterval signedInterval = SignedInterval.between(tmpStartTemporal, tmpEndTemporal);
        Interval interval = new Interval(signedInterval.centuries, signedInterval.years, signedInterval.months, signedInterval.days, signedInterval.hours,
                signedInterval.minutes, signedInterval.seconds, signedInterval.millis, signedInterval.micros, signedInterval.nanos);
        interval.startTime = tmpStartTemporal;
        interval.endTime = tmpEndTemporal;
        interval.totalYears = signedInterval.totalYears;
        interval.totalMonths = signedInterval.totalMonths;
        interval.totalWeeks = signedInterval.totalWeeks;
        interval.totalDays = signedInterval.totalDays;
        interval.totalHours = signedInterval.totalHours;
        interval.totalMinutes = signedInterval.totalMinutes;
        interval.totalSeconds = signedInterval.totalSeconds;
        interval.totalMillis = signedInterval.totalMillis;
        interval.totalMicros = signedInterval.totalMicros;
        interval.totalNanos = signedInterval.totalNanos;
        return interval;
    }

    /**
     * Obtain two {@link Date} datetime interval. <br>
     * 获取两个{@link Date}的时间间隔。
     *
     * @param startDate start Date
     * @param endDate   end Date
     * @return Interval
     */
    public static Interval between(Date startDate, Date endDate) {
        return between(startDate.toInstant(), endDate.toInstant());
    }

    /**
     * Obtain two {@link Calendar} datetime interval. <br>
     * 获取两个{@link Calendar}的时间间隔。
     *
     * @param startCalendar start Calendar
     * @param endCalendar   end Calendar
     * @return Interval
     */
    public static Interval between(Calendar startCalendar, Calendar endCalendar) {
        return between(startCalendar.toInstant(), endCalendar.toInstant());
    }

    @Override
    public Temporal subtractFrom(Temporal temporal) {
        Objects.requireNonNull(temporal);
        if (!isSupported(temporal))
            throw new UnsupportedTemporalTypeException("Only [" + SUPPORTED_TEMPORAL.stream().map(Class::getSimpleName).collect(Collectors.joining(", ")) + "] is supported!");

        boolean isInstant = temporal instanceof Instant;
        temporal = isInstant ? DateTimeUtils.toUTCZonedDT(temporal) : temporal;

        temporal = temporal.minus(nanos, NANOS);
        temporal = temporal.minus(micros, MICROS);
        temporal = temporal.minus(millis, MILLIS);
        temporal = temporal.minus(seconds, SECONDS);
        temporal = temporal.minus(minutes, MINUTES);
        temporal = temporal.minus(hours, HOURS);
        temporal = temporal.minus(days, DAYS);
        temporal = temporal.minus(months, MONTHS);
        temporal = temporal.minus(centuries * 100 + years, YEARS);
        return isInstant ? ((ZonedDateTime) temporal).toInstant() : temporal;
    }

}
