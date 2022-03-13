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

import com.iofairy.falcon.os.OS;
import com.iofairy.falcon.util.DateTimeUtils;

import java.math.BigInteger;
import java.time.*;
import java.time.temporal.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.*;

/**
 * SignedInterval
 *
 * @since 0.1.0
 */
public class SignedInterval implements ChronoInterval, Comparable<SignedInterval> {
    private static final long serialVersionUID = 7006057265L;

    /**
     * 世纪（100年）
     *
     * @see ChronoUnit#CENTURIES
     */
    public final long centuries;
    /**
     * 年
     *
     * @see ChronoUnit#YEARS
     */
    public final long years;
    /**
     * 月
     *
     * @see ChronoUnit#MONTHS
     */
    public final long months;
    /**
     * 日
     *
     * @see ChronoUnit#DAYS
     */
    public final long days;
    /**
     * 时
     *
     * @see ChronoUnit#HOURS
     */
    public final long hours;
    /**
     * 分
     *
     * @see ChronoUnit#MINUTES
     */
    public final long minutes;
    /**
     * 秒
     *
     * @see ChronoUnit#SECONDS
     */
    public final long seconds;
    /**
     * 毫秒
     *
     * @see ChronoUnit#MILLIS
     */
    public final long millis;
    /**
     * 微秒
     *
     * @see ChronoUnit#MICROS
     */
    public final long micros;
    /**
     * 纳秒
     *
     * @see ChronoUnit#NANOS
     */
    public final long nanos;

    /*
     * 换算成每个时间单位的总时间
     */
    protected long totalYears;          // 总年数
    protected long totalMonths;         // 总月数
    protected long totalWeeks;          // 总周数
    protected long totalDays;           // 总天数
    protected long totalHours;          // 总小时数
    protected long totalMinutes;        // 总分钟数
    protected long totalSeconds;        // 总秒数
    protected long totalMillis;         // 总毫秒数
    protected BigInteger totalMicros;   // 总微秒数
    protected BigInteger totalNanos;    // 总纳秒数

    /*
     * 开始时间、结束时间
     */
    protected Temporal startTime, endTime;

    public static final SignedInterval ZERO = new SignedInterval(0, 0, 0);

    private static final List<TemporalUnit> SUPPORTED_UNITS =
            Collections.unmodifiableList(Arrays.<TemporalUnit>asList(CENTURIES, YEARS, MONTHS, DAYS, HOURS, MINUTES, SECONDS, MILLIS, MICROS, NANOS));
    /**
     * {@link #between(Temporal, Temporal)}支持的temporal类型
     */
    public static final List<Class<? extends Temporal>> SUPPORTED_TEMPORAL =
            Collections.unmodifiableList(Arrays.asList(ZonedDateTime.class, OffsetDateTime.class, LocalDateTime.class, Instant.class));

    public SignedInterval(long centuries, long years, long months, long days, long hours, long minutes, long seconds, long millis, long micros, long nanos) {
        this.centuries = centuries;
        this.years = years;
        this.months = months;
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.millis = millis;
        this.micros = micros;
        this.nanos = nanos;
    }

    public SignedInterval(long centuries, long years, long months, long days, long hours, long minutes, long seconds, long millis) {
        this(centuries, years, months, days, hours, minutes, seconds, millis, 0, 0);
    }

    public SignedInterval(long years, long months, long days, long hours, long minutes, long seconds, long millis) {
        this(0, years, months, days, hours, minutes, seconds, millis, 0, 0);
    }

    public SignedInterval(long years, long months, long days, long hours, long minutes, long seconds) {
        this(0, years, months, days, hours, minutes, seconds, 0, 0, 0);
    }

    public SignedInterval(long years, long months, long days, long hours, long minutes) {
        this(0, years, months, days, hours, minutes, 0, 0, 0, 0);
    }

    public SignedInterval(long months, long days, long hours, long minutes) {
        this(0, 0, months, days, hours, minutes, 0, 0, 0, 0);
    }

    public SignedInterval(long days, long hours, long minutes) {
        this(0, 0, 0, days, hours, minutes, 0, 0, 0, 0);
    }

    public static SignedInterval of(long centuries, long years, long months, long days, long hours, long minutes, long seconds, long millis, long micros, long nanos) {
        return new SignedInterval(centuries, years, months, days, hours, minutes, seconds, millis, micros, nanos);
    }

    public static SignedInterval of(long centuries, long years, long months, long days, long hours, long minutes, long seconds, long millis) {
        return new SignedInterval(centuries, years, months, days, hours, minutes, seconds, millis, 0, 0);
    }

    public static SignedInterval of(long years, long months, long days, long hours, long minutes, long seconds, long millis) {
        return new SignedInterval(0, years, months, days, hours, minutes, seconds, millis, 0, 0);
    }

    public static SignedInterval of(long years, long months, long days, long hours, long minutes, long seconds) {
        return new SignedInterval(0, years, months, days, hours, minutes, seconds, 0, 0, 0);
    }

    public static SignedInterval of(long years, long months, long days, long hours, long minutes) {
        return new SignedInterval(0, years, months, days, hours, minutes, 0, 0, 0, 0);
    }

    public static SignedInterval of(long months, long days, long hours, long minutes) {
        return new SignedInterval(0, 0, months, days, hours, minutes, 0, 0, 0, 0);
    }

    public static SignedInterval of(long days, long hours, long minutes) {
        return new SignedInterval(0, 0, 0, days, hours, minutes, 0, 0, 0, 0);
    }

    public static SignedInterval of(long amount, TemporalUnit unit) {
        return ZERO.plus(amount, unit);
    }

    public static SignedInterval ofCenturies(long centuries) {
        return ZERO.plus(centuries, CENTURIES);
    }

    public static SignedInterval ofYears(long years) {
        return ZERO.plus(years, YEARS);
    }

    public static SignedInterval ofMonths(long months) {
        return ZERO.plus(months, MONTHS);
    }

    public static SignedInterval ofDays(long days) {
        return ZERO.plus(days, DAYS);
    }

    public static SignedInterval ofHours(long hours) {
        return ZERO.plus(hours, HOURS);
    }

    public static SignedInterval ofMinutes(long minutes) {
        return ZERO.plus(minutes, MINUTES);
    }

    public static SignedInterval ofSeconds(long seconds) {
        return ZERO.plus(seconds, SECONDS);
    }

    public static SignedInterval ofMillis(long millis) {
        return ZERO.plus(millis, MILLIS);
    }

    public static SignedInterval ofMicros(long micros) {
        return ZERO.plus(micros, MICROS);
    }

    public static SignedInterval ofNanos(long nanos) {
        return ZERO.plus(nanos, NANOS);
    }

    public SignedInterval plus(SignedInterval signedInterval) {
        long centuries = this.centuries + signedInterval.centuries;
        long years = this.years + signedInterval.years;
        long months = this.months + signedInterval.months;
        long days = this.days + signedInterval.days;
        long hours = this.hours + signedInterval.hours;
        long minutes = this.minutes + signedInterval.minutes;
        long seconds = this.seconds + signedInterval.seconds;
        long millis = this.millis + signedInterval.millis;
        long micros = this.micros + signedInterval.micros;
        long nanos = this.nanos + signedInterval.nanos;
        return new SignedInterval(centuries, years, months, days, hours, minutes, seconds, millis, micros, nanos);
    }

    public SignedInterval plus(long amount, TemporalUnit unit) {
        if (unit instanceof ChronoUnit) {
            long centuries = this.centuries;
            long years = this.years;
            long months = this.months;
            long days = this.days;
            long hours = this.hours;
            long minutes = this.minutes;
            long seconds = this.seconds;
            long millis = this.millis;
            long micros = this.micros;
            long nanos = this.nanos;
            ChronoUnit chronoUnit = (ChronoUnit) unit;
            switch (chronoUnit) {
                case CENTURIES:
                    centuries = centuries + amount;
                    break;
                case YEARS:
                    years = years + amount;
                    break;
                case MONTHS:
                    months = months + amount;
                    break;
                case DAYS:
                    days = days + amount;
                    break;
                case HOURS:
                    hours = hours + amount;
                    break;
                case MINUTES:
                    minutes = minutes + amount;
                    break;
                case SECONDS:
                    seconds = seconds + amount;
                    break;
                case MILLIS:
                    millis = millis + amount;
                    break;
                case MICROS:
                    micros = micros + amount;
                    break;
                case NANOS:
                    nanos = nanos + amount;
                    break;
            }
            return new SignedInterval(centuries, years, months, days, hours, minutes, seconds, millis, micros, nanos);
        }
        throw new UnsupportedTemporalTypeException("Unsupported unit: " + unit);
    }

    public SignedInterval minus(SignedInterval signedInterval) {
        long centuries = this.centuries - signedInterval.centuries;
        long years = this.years - signedInterval.years;
        long months = this.months - signedInterval.months;
        long days = this.days - signedInterval.days;
        long hours = this.hours - signedInterval.hours;
        long minutes = this.minutes - signedInterval.minutes;
        long seconds = this.seconds - signedInterval.seconds;
        long millis = this.millis - signedInterval.millis;
        long micros = this.micros - signedInterval.micros;
        long nanos = this.nanos - signedInterval.nanos;
        return new SignedInterval(centuries, years, months, days, hours, minutes, seconds, millis, micros, nanos);
    }

    public SignedInterval minus(long amount, TemporalUnit unit) {
        if (unit instanceof ChronoUnit) {
            long centuries = this.centuries;
            long years = this.years;
            long months = this.months;
            long days = this.days;
            long hours = this.hours;
            long minutes = this.minutes;
            long seconds = this.seconds;
            long millis = this.millis;
            long micros = this.micros;
            long nanos = this.nanos;
            ChronoUnit chronoUnit = (ChronoUnit) unit;
            switch (chronoUnit) {
                case CENTURIES:
                    centuries = centuries - amount;
                    break;
                case YEARS:
                    years = years - amount;
                    break;
                case MONTHS:
                    months = months - amount;
                    break;
                case DAYS:
                    days = days - amount;
                    break;
                case HOURS:
                    hours = hours - amount;
                    break;
                case MINUTES:
                    minutes = minutes - amount;
                    break;
                case SECONDS:
                    seconds = seconds - amount;
                    break;
                case MILLIS:
                    millis = millis - amount;
                    break;
                case MICROS:
                    micros = micros - amount;
                    break;
                case NANOS:
                    nanos = nanos - amount;
                    break;
            }
            return new SignedInterval(centuries, years, months, days, hours, minutes, seconds, millis, micros, nanos);
        }
        throw new UnsupportedTemporalTypeException("Unsupported unit: " + unit);
    }

    /**
     * Obtain two {@link Temporal} datetime interval. It is recommended to call {@link #isSupported(Temporal)}
     * to check if the specified temporal is supported first.<br>
     * 获取两个{@link Temporal}的时间间隔。建议在调用此方法之前先确认是否支持该时间类型 {@link #isSupported(Temporal)}。
     *
     * @param startTemporal start Temporal
     * @param endTemporal   end Temporal
     * @return SignedInterval
     */
    public static SignedInterval between(Temporal startTemporal, Temporal endTemporal) {
        Objects.requireNonNull(startTemporal);
        Objects.requireNonNull(endTemporal);
        if (!isSupported(startTemporal) || !isSupported(endTemporal))
            throw new UnsupportedTemporalTypeException("Only [" + SUPPORTED_TEMPORAL.stream().map(Class::getSimpleName).collect(Collectors.joining(", ")) + "] is supported!");

        Temporal originalStartTemporal = startTemporal;
        Temporal originalEndTemporal = endTemporal;

        startTemporal = DateTimeUtils.toUTCZonedDT(startTemporal);
        endTemporal = DateTimeUtils.toUTCZonedDT(endTemporal);

        long totalYears = startTemporal.until(endTemporal, YEARS);
        long totalMonths = startTemporal.until(endTemporal, MONTHS);
        long totalWeeks = startTemporal.until(endTemporal, WEEKS);
        long totalDays = startTemporal.until(endTemporal, DAYS);
        long totalHours = startTemporal.until(endTemporal, HOURS);
        long totalMinutes = startTemporal.until(endTemporal, MINUTES);
        long totalSeconds = startTemporal.until(endTemporal, SECONDS);
        long totalMillis = startTemporal.until(endTemporal, MILLIS);

        long centuries = startTemporal.until(endTemporal, CENTURIES);
        startTemporal = startTemporal.plus(centuries, CENTURIES);

        long years = startTemporal.until(endTemporal, YEARS);
        startTemporal = startTemporal.plus(years, YEARS);

        long months = startTemporal.until(endTemporal, MONTHS);
        startTemporal = startTemporal.plus(months, MONTHS);

        long days = startTemporal.until(endTemporal, DAYS);
        startTemporal = startTemporal.plus(days, DAYS);

        long hours = startTemporal.until(endTemporal, HOURS);
        startTemporal = startTemporal.plus(hours, HOURS);

        long minutes = startTemporal.until(endTemporal, MINUTES);
        startTemporal = startTemporal.plus(minutes, MINUTES);

        long seconds = startTemporal.until(endTemporal, SECONDS);
        startTemporal = startTemporal.plus(seconds, SECONDS);

        long millis = startTemporal.until(endTemporal, MILLIS);
        startTemporal = startTemporal.plus(millis, MILLIS);

        long micros = startTemporal.until(endTemporal, MICROS);
        startTemporal = startTemporal.plus(micros, MICROS);

        long nanos = startTemporal.until(endTemporal, NANOS);

        BigInteger totalMicros = BigInteger.valueOf(totalMillis).multiply(BigInteger.valueOf(1000L)).add(BigInteger.valueOf(micros));
        BigInteger totalNanos = totalMicros.multiply(BigInteger.valueOf(1000L)).add(BigInteger.valueOf(nanos));
        SignedInterval signedInterval = new SignedInterval(centuries, years, months, days, hours, minutes, seconds, millis, micros, nanos);
        signedInterval.startTime = originalStartTemporal;
        signedInterval.endTime = originalEndTemporal;
        signedInterval.totalYears = totalYears;
        signedInterval.totalMonths = totalMonths;
        signedInterval.totalWeeks = totalWeeks;
        signedInterval.totalDays = totalDays;
        signedInterval.totalHours = totalHours;
        signedInterval.totalMinutes = totalMinutes;
        signedInterval.totalSeconds = totalSeconds;
        signedInterval.totalMillis = totalMillis;
        signedInterval.totalMicros = totalMicros;
        signedInterval.totalNanos = totalNanos;
        return signedInterval;
    }

    /**
     * Obtain two {@link Date} datetime interval. <br>
     * 获取两个{@link Date}的时间间隔。
     *
     * @param startDate start Date
     * @param endDate   end Date
     * @return SignedInterval
     */
    public static SignedInterval between(Date startDate, Date endDate) {
        return between(startDate.toInstant(), endDate.toInstant());
    }

    /**
     * Obtain two {@link Calendar} datetime interval. <br>
     * 获取两个{@link Calendar}的时间间隔。
     *
     * @param startCalendar start Calendar
     * @param endCalendar   end Calendar
     * @return SignedInterval
     */
    public static SignedInterval between(Calendar startCalendar, Calendar endCalendar) {
        return between(startCalendar.toInstant(), endCalendar.toInstant());
    }

    /**
     * Checks if the specified temporal is supported.
     *
     * @param temporal temporal
     * @return true if the temporal can be added/subtracted, false if not
     */
    public static boolean isSupported(final Temporal temporal) {
        return temporal != null && SUPPORTED_TEMPORAL.stream().anyMatch(e -> e == temporal.getClass());
    }

    @Override
    public long get(TemporalUnit unit) {
        if (unit instanceof ChronoUnit) {
            ChronoUnit chronoUnit = (ChronoUnit) unit;
            switch (chronoUnit) {
                case CENTURIES:
                    return centuries;
                case YEARS:
                    return years;
                case MONTHS:
                    return months;
                case DAYS:
                    return days;
                case HOURS:
                    return hours;
                case MINUTES:
                    return minutes;
                case SECONDS:
                    return seconds;
                case MILLIS:
                    return millis;
                case MICROS:
                    return micros;
                case NANOS:
                    return nanos;
            }
        }
        throw new UnsupportedTemporalTypeException("Unsupported unit: " + unit);
    }

    @Override
    public List<TemporalUnit> getUnits() {
        return SUPPORTED_UNITS;
    }

    @Override
    public Temporal addTo(Temporal temporal) {
        Objects.requireNonNull(temporal);
        if (!isSupported(temporal))
            throw new UnsupportedTemporalTypeException("Only [" + SUPPORTED_TEMPORAL.stream().map(Class::getSimpleName).collect(Collectors.joining(", ")) + "] is supported!");

        boolean isInstant = temporal instanceof Instant;
        temporal = isInstant ? DateTimeUtils.toUTCZonedDT(temporal) : temporal;

        temporal = plus(temporal, centuries * 100 + years, YEARS);
        temporal = plus(temporal, months, MONTHS);
        temporal = plus(temporal, days, DAYS);
        temporal = plus(temporal, hours, HOURS);
        temporal = plus(temporal, minutes, MINUTES);
        temporal = plus(temporal, seconds, SECONDS);
        temporal = plus(temporal, millis, MILLIS);
        temporal = plus(temporal, micros, MICROS);
        temporal = plus(temporal, nanos, NANOS);
        return isInstant ? ((ZonedDateTime) temporal).toInstant() : temporal;
    }

    protected Temporal plus(Temporal temporal, long amountToAdd, TemporalUnit unit) {
        return amountToAdd == 0 ? temporal : temporal.plus(amountToAdd, unit);
    }

    @Override
    public Date addTo(Date date) {
        return Date.from((Instant) addTo(date.toInstant()));
    }

    @Override
    public Calendar addTo(Calendar calendar) {
        return DateTimeUtils.calendar((Instant) addTo(calendar.toInstant()), calendar.getTimeZone());
    }

    @Override
    public Temporal subtractFrom(Temporal temporal) {
        Objects.requireNonNull(temporal);
        if (!isSupported(temporal))
            throw new UnsupportedTemporalTypeException("Only [" + SUPPORTED_TEMPORAL.stream().map(Class::getSimpleName).collect(Collectors.joining(", ")) + "] is supported!");

        boolean isInstant = temporal instanceof Instant;
        temporal = isInstant ? DateTimeUtils.toUTCZonedDT(temporal) : temporal;

        temporal = minus(temporal, centuries * 100 + years, YEARS);
        temporal = minus(temporal, months, MONTHS);
        temporal = minus(temporal, days, DAYS);
        temporal = minus(temporal, hours, HOURS);
        temporal = minus(temporal, minutes, MINUTES);
        temporal = minus(temporal, seconds, SECONDS);
        temporal = minus(temporal, millis, MILLIS);
        temporal = minus(temporal, micros, MICROS);
        temporal = minus(temporal, nanos, NANOS);
        return isInstant ? ((ZonedDateTime) temporal).toInstant() : temporal;
    }

    protected Temporal minus(Temporal temporal, long amountToSubtract, TemporalUnit unit) {
        return amountToSubtract == 0 ? temporal : temporal.minus(amountToSubtract, unit);
    }

    @Override
    public Date subtractFrom(Date date) {
        return Date.from((Instant) subtractFrom(date.toInstant()));
    }

    @Override
    public Calendar subtractFrom(Calendar calendar) {
        return DateTimeUtils.calendar((Instant) subtractFrom(calendar.toInstant()), calendar.getTimeZone());
    }

    public long toYears() {
        return totalYears;
    }

    public long toMonths() {
        return totalMonths;
    }

    public long toWeeks() {
        return totalWeeks;
    }

    public long toDays() {
        return totalDays;
    }

    public long toHours() {
        return totalHours;
    }

    public long toMinutes() {
        return totalMinutes;
    }

    public long toSeconds() {
        return totalSeconds;
    }

    public long toMillis() {
        return totalMillis;
    }

    public BigInteger toMicros() {
        return totalMicros;
    }

    public BigInteger toNanos() {
        return totalNanos;
    }

    public Temporal getStartTime() {
        return startTime;
    }

    public Temporal getEndTime() {
        return endTime;
    }

    @Override
    public boolean equals(Object baseInterval) {
        return baseInterval instanceof SignedInterval && compareTo((SignedInterval) baseInterval) == 0;
    }

    @Override
    public int compareTo(SignedInterval other) {
        Objects.requireNonNull(other);
        if (this.totalNanos != null && other.totalNanos != null) {
            return this.totalNanos.compareTo(other.totalNanos);
        }
        long thisMonths = (centuries * 100 + years) * 12 + months;
        long otherMonths = (other.centuries * 100 + other.years) * 12 + other.months;
        if (thisMonths == otherMonths) {
            if (days == other.days) {
                long thisNanos = (((((hours * 60) + minutes) * 60 + seconds) * 1000 + millis) * 1000 + micros) * 1000 + nanos;
                long otherNanos = (((((other.hours * 60) + other.minutes) * 60 + other.seconds) * 1000 + other.millis) * 1000 + other.micros) * 1000 + other.nanos;
                return Long.compare(thisNanos, otherNanos);
            } else return Long.compare(days, other.days);
        } else return Long.compare(thisMonths, otherMonths);
    }

    public String toFullString() {
        String equivalentlyStr = OS.IS_ZH_LANG ? "\n相当于：\n" : "\nAlternative time units: \n";
        String yearsStr = OS.IS_ZH_LANG ? " 年\n" : " years\n";
        String monthsStr = OS.IS_ZH_LANG ? " 月\n" : " months\n";
        String weeksStr = OS.IS_ZH_LANG ? " 周\n" : " weeks\n";
        String daysStr = OS.IS_ZH_LANG ? " 天\n" : " days\n";
        String hoursStr = OS.IS_ZH_LANG ? " 时\n" : " hours\n";
        String minutesStr = OS.IS_ZH_LANG ? " 分\n" : " minutes\n";
        String secondsStr = OS.IS_ZH_LANG ? " 秒\n" : " seconds\n";
        String millisStr = OS.IS_ZH_LANG ? " 毫秒\n" : " millis\n";
        String microsStr = OS.IS_ZH_LANG ? " 微秒\n" : " micros\n";
        String nanosStr = OS.IS_ZH_LANG ? " 纳秒" : " nanos";

        return this + equivalentlyStr +
                "● " + totalYears + yearsStr +
                "● " + totalMonths + monthsStr +
                "● " + totalWeeks + weeksStr +
                "● " + totalDays + daysStr +
                "● " + totalHours + hoursStr +
                "● " + totalMinutes + minutesStr +
                "● " + totalSeconds + secondsStr +
                "● " + totalMillis + millisStr +
                "● " + (totalMicros == null ? "0" : totalMicros.toString()) + microsStr +
                "● " + (totalNanos == null ? "0" : totalNanos.toString()) + nanosStr;
    }

    @Override
    public String toString() {
        String centuriesStr = OS.IS_ZH_LANG ? "世纪" : " centuries ";
        String yearsStr = OS.IS_ZH_LANG ? "年" : " years ";
        String monthsStr = OS.IS_ZH_LANG ? "月" : " months ";
        String daysStr = OS.IS_ZH_LANG ? "天" : " days ";
        String hoursStr = OS.IS_ZH_LANG ? "时" : " hours ";
        String minutesStr = OS.IS_ZH_LANG ? "分" : " minutes ";
        String secondsStr = OS.IS_ZH_LANG ? "秒" : " seconds ";
        String millisStr = OS.IS_ZH_LANG ? "毫秒" : " millis";
        String microsStr = OS.IS_ZH_LANG ? "微秒" : " micros";
        String nanosStr = OS.IS_ZH_LANG ? "纳秒" : " nanos";

        String intervalStr = "";
        if (centuries != 0) {
            intervalStr += centuries + centuriesStr +
                    years + yearsStr +
                    months + monthsStr +
                    days + daysStr +
                    hours + hoursStr +
                    minutes + minutesStr +
                    seconds + secondsStr +
                    millis + millisStr;
        } else if (years != 0) {
            intervalStr += years + yearsStr +
                    months + monthsStr +
                    days + daysStr +
                    hours + hoursStr +
                    minutes + minutesStr +
                    seconds + secondsStr +
                    millis + millisStr;
        } else if (months != 0) {
            intervalStr += months + monthsStr +
                    days + daysStr +
                    hours + hoursStr +
                    minutes + minutesStr +
                    seconds + secondsStr +
                    millis + millisStr;
        } else if (days != 0) {
            intervalStr += days + daysStr +
                    hours + hoursStr +
                    minutes + minutesStr +
                    seconds + secondsStr +
                    millis + millisStr;
        } else if (hours != 0) {
            intervalStr += hours + hoursStr +
                    minutes + minutesStr +
                    seconds + secondsStr +
                    millis + millisStr;
        } else if (minutes != 0) {
            intervalStr += minutes + minutesStr +
                    seconds + secondsStr +
                    millis + millisStr;
        } else if (seconds != 0) {
            intervalStr += seconds + secondsStr +
                    millis + millisStr;
        } else {
            intervalStr += millis + millisStr;
        }
        if (micros != 0) intervalStr += micros + microsStr;
        if (nanos != 0) intervalStr += nanos + nanosStr;

        return intervalStr;
    }
}
