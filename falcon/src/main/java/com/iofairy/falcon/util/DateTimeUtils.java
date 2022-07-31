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
package com.iofairy.falcon.util;

import com.iofairy.falcon.time.TZ;
import com.iofairy.tcf.Try;

import java.time.*;
import java.time.temporal.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * DateTime Utils
 *
 * @see com.iofairy.falcon.time.DateTimes
 * @since 0.0.2
 * @deprecated Since falcon version 0.3.0, replaced by {@link com.iofairy.falcon.time.DateTimes}
 */
@Deprecated
public class DateTimeUtils {

    /**
     * 获取当前时间的 ZoneOffset 以及对应的 ZoneId 列表。
     * 由于有些地区有<b>夏令时</b>，不同时间结果会不一样。所以需要实时获取。
     *
     * @return ZoneOffset 以及对应的 ZoneId 列表
     */
    public static Map<ZoneOffset, List<ZoneId>> getOffsetZoneIds() {
        return TZ.ZONE_IDS.stream().collect(Collectors.groupingBy(DateTimeUtils::getZoneOffset));
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
     * 获取当前系统当前时间的ZoneOffset。
     * 由于有些地区有<b>夏令时</b>，不同时间结果会不一样。所以需要实时获取。
     *
     * @return ZoneOffset
     */
    public static ZoneOffset defaultOffset() {
        return Try.tcf(() -> OffsetDateTime.now().getOffset(), false);
    }

    /**
     * 通过ZoneId名称获取ZoneOffset
     *
     * @param zoneIdName ZoneId名称
     * @return ZoneOffset
     */
    public static ZoneOffset getZoneOffset(final String zoneIdName) {
        return getZoneOffset(Try.tcf(() -> ZoneId.of(zoneIdName), false));
    }

    /**
     * 通过ZoneId获取ZoneOffset
     *
     * @param zoneId ZoneId
     * @return ZoneOffset
     */
    public static ZoneOffset getZoneOffset(final ZoneId zoneId) {
        return Try.tcf(() -> zoneId.getRules().getOffset(Instant.now()), false);
    }

    /**
     * Convert temporal to ZonedDateTime
     *
     * @param temporal temporal
     * @return ZonedDateTime
     */
    public static ZonedDateTime toUTCZonedDT(Temporal temporal) {
        if (temporal == null) return null;

        if (temporal instanceof ZonedDateTime) return ((ZonedDateTime) temporal).withZoneSameInstant(TZ.UTC);
        if (temporal instanceof OffsetDateTime) return ((OffsetDateTime) temporal).atZoneSameInstant(TZ.UTC);
        if (temporal instanceof LocalDateTime)
            return ((LocalDateTime) temporal).atZone(TZ.DEFAULT_ZONE).withZoneSameInstant(TZ.UTC);
        if (temporal instanceof Instant) return ZonedDateTime.ofInstant((Instant) temporal, TZ.UTC);

        throw new UnsupportedTemporalTypeException("Only [ZonedDateTime, OffsetDateTime, LocalDateTime, Instant] is supported!");
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
     * @param date     date
     * @param timeZone timeZone
     * @return Calendar
     */
    public static Calendar calendar(Date date, TimeZone timeZone) {
        Calendar calendar = Calendar.getInstance(timeZone);
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
     * @param instant  instant
     * @param timeZone timeZone
     * @return Calendar
     */
    public static Calendar calendar(Instant instant, TimeZone timeZone) {
        Calendar calendar = Calendar.getInstance(timeZone);
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

}
