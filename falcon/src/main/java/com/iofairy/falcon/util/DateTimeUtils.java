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

import java.time.ZoneId;
import java.util.*;

/**
 * DateTime Utils
 * @since 0.0.2
 */
public class DateTimeUtils {

    private static final ZoneId DEFAULT_ZONE = ZoneId.systemDefault();

    /**
     * Convert local Date object to another timezone <br>
     * 将本地Date时间对象转成另一个时区的Date对象
     * @param date date
     * @param zoneId zoneId
     * @return another timezone Date
     */
    public static Date localDateToTZ(Date date, ZoneId zoneId) {
        return tzDateToTZ(date, DEFAULT_ZONE, zoneId);
    }

    /**
     * Convert fromZoneId Date to toZoneId <br>
     * 将Date对象从一个时区转成另一个时区的Date对象
     * @param date date
     * @param fromZoneId fromZoneId
     * @param toZoneId toZoneId
     * @return another timezone Date
     */
    public static Date tzDateToTZ(Date date, ZoneId fromZoneId, ZoneId toZoneId) {
        return Date.from(date.toInstant().atZone(toZoneId).withZoneSameLocal(fromZoneId).toInstant());
    }

    /**
     * Convert calendar to local calendar <br>
     * 将calendar对象转成本地的calendar对象
     * @param calendar calendar
     * @return local calendar
     */
    public static Calendar calendarToLocal(Calendar calendar) {
        return calendarToTZ(calendar, DEFAULT_ZONE);
    }

    /**
     * Convert calendar to another timezone calendar <br>
     * 将calendar对象转成另一个时区的calendar对象
     * @param calendar calendar
     * @param zoneId zoneId
     * @return calendar with another timezone
     */
    public static Calendar calendarToTZ(Calendar calendar, ZoneId zoneId) {
        Date newDate = tzDateToTZ(calendar.getTime(), calendar.getTimeZone().toZoneId(), zoneId);
        Calendar newCalendar = Calendar.getInstance(TimeZone.getTimeZone(zoneId));
        newCalendar.setTime(newDate);
        return newCalendar;
    }

}
