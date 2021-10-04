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

import com.iofairy.falcon.util.DateTimeUtils;

import java.io.Serializable;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static java.lang.Math.*;

/**
 * Date time interval<br>
 * 时间间隔
 *
 * @since 0.0.2
 */
public class Interval implements Comparable<Interval>, Serializable {
    private static final long serialVersionUID = 652695997090L;

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

    private long totalYears;        // 总年数
    private long totalMonths;       // 总月数
    private long totalWeeks;        // 总周数
    private long totalDays;         // 总天数
    private long totalHours;        // 总小时数
    private long totalMinutes;      // 总分钟数
    private long totalSeconds;      // 总秒数
    private long totalMillis;       // 总毫秒数

    private static final ZoneId DEFAULT_ZONE = ZoneId.systemDefault();

    public Interval(long centuries, long years, long months, long days, long hours, long minutes, long seconds, long millis) {
        this.centuries = centuries;
        this.years = years;
        this.months = months;
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.millis = millis;
    }

    public Interval(long years, long months, long days, long hours, long minutes, long seconds, long millis) {
        this(0, years, months, days, hours, minutes, seconds, millis);
    }

    public Interval(long years, long months, long days, long hours, long minutes, long seconds) {
        this(0, years, months, days, hours, minutes, seconds, 0);
    }

    public Interval(long years, long months, long days, long hours, long minutes) {
        this(0, years, months, days, hours, minutes, 0, 0);
    }

    /*
     * 小日期到大日期 与 大日期到小日期可能会产生不同的结果，主要影响在天。天的计算主要根据当前月天数决定。
     * 小日期到大日期 与 大日期到小日期最后计算天时的月份可能不一样，就可能会造成不同的结果。
     * 所以，必须强制规定计算 Interval 必须从 小日期到大日期
     */

    /**
     * Obtain two {@link ZonedDateTime} date and time interval, it will be always positive number. <br>
     * 获取两个{@link ZonedDateTime}的时间间隔，值永远是正数。
     *
     * @param firstZonedDT  first ZonedDateTime
     * @param secondZonedDT second ZonedDateTime
     * @return interval
     */
    public static Interval between(ZonedDateTime firstZonedDT, ZonedDateTime secondZonedDT) {
        Objects.requireNonNull(firstZonedDT);
        Objects.requireNonNull(secondZonedDT);

        int compare = firstZonedDT.compareTo(secondZonedDT);
        ZonedDateTime tmpFirstZonedDT = ZonedDateTime.from(compare < 0 ? firstZonedDT : secondZonedDT);
        ZonedDateTime tmpSecondZonedDT = ZonedDateTime.from(compare < 0 ? secondZonedDT : firstZonedDT);

        ZonedDateTime tmpZonedDT = ZonedDateTime.from(tmpFirstZonedDT);

        long centuries = tmpZonedDT.until(tmpSecondZonedDT, ChronoUnit.CENTURIES);
        tmpZonedDT = tmpZonedDT.plusYears(centuries * 100);

        long years = tmpZonedDT.until(tmpSecondZonedDT, ChronoUnit.YEARS);
        tmpZonedDT = tmpZonedDT.plusYears(years);

        long months = tmpZonedDT.until(tmpSecondZonedDT, ChronoUnit.MONTHS);
        tmpZonedDT = tmpZonedDT.plusMonths(months);

        long days = tmpZonedDT.until(tmpSecondZonedDT, ChronoUnit.DAYS);
        tmpZonedDT = tmpZonedDT.plusDays(days);

        long hours = tmpZonedDT.until(tmpSecondZonedDT, ChronoUnit.HOURS);
        tmpZonedDT = tmpZonedDT.plusHours(hours);

        long minutes = tmpZonedDT.until(tmpSecondZonedDT, ChronoUnit.MINUTES);
        tmpZonedDT = tmpZonedDT.plusMinutes(minutes);

        long seconds = tmpZonedDT.until(tmpSecondZonedDT, ChronoUnit.SECONDS);
        tmpZonedDT = tmpZonedDT.plusSeconds(seconds);

        long millis = tmpZonedDT.until(tmpSecondZonedDT, ChronoUnit.MILLIS);

        Interval interval = new Interval(abs(centuries), abs(years), abs(months), abs(days), abs(hours), abs(minutes), abs(seconds), abs(millis));
        interval.totalYears = abs(centuries * 100) + abs(years);
        interval.totalMonths = abs(interval.totalYears * 12) + abs(months);
        interval.totalWeeks = abs(ChronoUnit.WEEKS.between(tmpFirstZonedDT, tmpSecondZonedDT));
        interval.totalDays = abs(ChronoUnit.DAYS.between(tmpFirstZonedDT, tmpSecondZonedDT));
        interval.totalHours = abs(interval.totalDays * 24) + abs(hours);
        interval.totalMinutes = abs(interval.totalHours * 60) + abs(minutes);
        interval.totalSeconds = abs(interval.totalMinutes * 60) + abs(seconds);
        interval.totalMillis = abs(interval.totalSeconds * 1000) + abs(millis);
        return interval;
    }

    public static Interval between(OffsetDateTime firstOffsetDT, OffsetDateTime secondOffsetDT) {
        return between(firstOffsetDT.toZonedDateTime(), secondOffsetDT.toZonedDateTime());
    }

    public static Interval between(LocalDateTime firstLocalDT, LocalDateTime secondLocaldDT) {
        return between(firstLocalDT.atZone(DEFAULT_ZONE), secondLocaldDT.atZone(DEFAULT_ZONE));
    }

    public static Interval between(Instant firstInstant, Instant secondInstant) {
        return between(ZonedDateTime.ofInstant(firstInstant, DEFAULT_ZONE), ZonedDateTime.ofInstant(secondInstant, DEFAULT_ZONE));
    }

    public static Interval between(Date firstDate, Date secondDate) {
        return between(firstDate.toInstant(), secondDate.toInstant());
    }

    public static Interval between(Calendar firstCalendar, Calendar secondCalendar) {
        return betweenIgnoreTZ(DateTimeUtils.calendarToLocal(firstCalendar), DateTimeUtils.calendarToLocal(secondCalendar));
    }

    /**
     * Obtain two {@link Calendar} (ignore timezone) date and time interval, it will be always positive number. <br>
     * 获取两个{@link Calendar}（忽略时区）的时间间隔，值永远是正数。
     *
     * @param firstCalendar  first Calendar
     * @param secondCalendar second Calendar
     * @return interval
     */
    public static Interval betweenIgnoreTZ(Calendar firstCalendar, Calendar secondCalendar) {
        return between(firstCalendar.toInstant(), secondCalendar.toInstant());
    }

    public static ZonedDateTime plusInterval(ZonedDateTime fromDateTime, Interval interval) {
        Objects.requireNonNull(fromDateTime);
        Objects.requireNonNull(interval);
        ZonedDateTime tmpZonedDT = ZonedDateTime.from(fromDateTime);
        tmpZonedDT = tmpZonedDT.plusYears(interval.centuries * 100 + interval.years);
        tmpZonedDT = tmpZonedDT.plusMonths(interval.months);
        tmpZonedDT = tmpZonedDT.plusDays(interval.days);
        tmpZonedDT = tmpZonedDT.plusHours(interval.hours);
        tmpZonedDT = tmpZonedDT.plusMinutes(interval.minutes);
        tmpZonedDT = tmpZonedDT.plusSeconds(interval.seconds);
        return tmpZonedDT.plusNanos(interval.millis * 1000000);
    }

    public static OffsetDateTime plusInterval(OffsetDateTime fromDateTime, Interval interval) {
        return plusInterval(fromDateTime.toZonedDateTime(), interval).toOffsetDateTime();
    }

    public static LocalDateTime plusInterval(LocalDateTime fromDateTime, Interval interval) {
        return plusInterval(fromDateTime.atZone(DEFAULT_ZONE), interval).toLocalDateTime();
    }

    public static Instant plusInterval(Instant fromInstant, Interval interval) {
        return plusInterval(LocalDateTime.ofInstant(fromInstant, ZoneId.of("UTC")), interval).toInstant(ZoneOffset.UTC);
    }

    public static Date plusInterval(Date fromDate, Interval interval) {
        return Date.from(plusInterval(fromDate.toInstant(), interval));
    }

    public static Calendar plusInterval(Calendar fromCalendar, Interval interval) {
        Date date = plusInterval(fromCalendar.getTime(), interval);
        Calendar calendar = Calendar.getInstance(fromCalendar.getTimeZone());
        calendar.setTime(date);
        return calendar;
    }

    public static ZonedDateTime minusInterval(ZonedDateTime fromDateTime, Interval interval) {
        Objects.requireNonNull(fromDateTime);
        Objects.requireNonNull(interval);
        ZonedDateTime tmpZonedDT = ZonedDateTime.from(fromDateTime);
        tmpZonedDT = tmpZonedDT.minusNanos(interval.millis * 1000000);
        tmpZonedDT = tmpZonedDT.minusSeconds(interval.seconds);
        tmpZonedDT = tmpZonedDT.minusMinutes(interval.minutes);
        tmpZonedDT = tmpZonedDT.minusHours(interval.hours);
        tmpZonedDT = tmpZonedDT.minusDays(interval.days);
        tmpZonedDT = tmpZonedDT.minusMonths(interval.months);
        tmpZonedDT = tmpZonedDT.minusYears(interval.centuries * 100 + interval.years);
        return tmpZonedDT;
    }

    public static OffsetDateTime minusInterval(OffsetDateTime fromDateTime, Interval interval) {
        return minusInterval(fromDateTime.toZonedDateTime(), interval).toOffsetDateTime();
    }

    public static LocalDateTime minusInterval(LocalDateTime fromDateTime, Interval interval) {
        return minusInterval(fromDateTime.atZone(DEFAULT_ZONE), interval).toLocalDateTime();
    }

    public static Instant minusInterval(Instant fromInstant, Interval interval) {
        return minusInterval(LocalDateTime.ofInstant(fromInstant, ZoneId.of("UTC")), interval).toInstant(ZoneOffset.UTC);
    }

    public static Date minusInterval(Date fromDateTime, Interval interval) {
        return Date.from(minusInterval(fromDateTime.toInstant(), interval));
    }

    public static Calendar minusInterval(Calendar fromCalendar, Interval interval) {
        Date date = minusInterval(fromCalendar.getTime(), interval);
        Calendar calendar = Calendar.getInstance(fromCalendar.getTimeZone());
        calendar.setTime(date);
        return calendar;
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

    @Override
    public int compareTo(Interval interval) {
        return Long.compare(totalMillis, interval.totalMillis);
    }

    public String toFullString() {
        String language = Locale.getDefault().getLanguage();
        boolean isZh = language.equals("zh");
        String equivalentlyStr = isZh ? "\n相当于：\n" : "\nAlternative time units: \n";
        String yearsStr = isZh ? " 年\n" : " years\n";
        String monthsStr = isZh ? " 月\n" : " months\n";
        String weeksStr = isZh ? " 周\n" : " weeks\n";
        String daysStr = isZh ? " 天\n" : " days\n";
        String hoursStr = isZh ? " 时\n" : " hours\n";
        String minutesStr = isZh ? " 分\n" : " minutes\n";
        String secondsStr = isZh ? " 秒\n" : " seconds\n";
        String millisStr = isZh ? " 毫秒" : " millis";
        return this + equivalentlyStr +
                "● " + totalYears + yearsStr +
                "● " + totalMonths + monthsStr +
                "● " + totalWeeks + weeksStr +
                "● " + totalDays + daysStr +
                "● " + totalHours + hoursStr +
                "● " + totalMinutes + minutesStr +
                "● " + totalSeconds + secondsStr +
                "● " + totalMillis + millisStr;

    }

    @Override
    public String toString() {
        String language = Locale.getDefault().getLanguage();
        boolean isZh = language.equals("zh");
        String centuriesStr = isZh ? "世纪" : " centuries ";
        String yearsStr = isZh ? "年" : " years ";
        String monthsStr = isZh ? "月" : " months ";
        String daysStr = isZh ? "天" : " days ";
        String hoursStr = isZh ? "时" : " hours ";
        String minutesStr = isZh ? "分" : " minutes ";
        String secondsStr = isZh ? "秒" : " seconds ";
        String millisStr = isZh ? "毫秒" : " millis";

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

        return intervalStr;
    }
}
