package com.iofairy.test;

import com.iofairy.falcon.time.DateTime;
import com.iofairy.falcon.time.DateTimes;
import com.iofairy.falcon.time.TZ;
import com.iofairy.falcon.util.DateTimeUtils;
import com.iofairy.top.G;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author GG
 * @version 1.0
 */
public class DateTimesTest {

    @Test
    public void testTzToTZ() {
        LocalDateTime ldt = LocalDateTime.of(2022, 1, 27, 6, 0, 10, 65891000);
        Date date = DateTime.of(ldt).toDate();

        Date date1 = DateTimes.defaultDateToTZ(date, TZ.MOSCOW);
        Date date2 = DateTimes.defaultDateToTZ(date, TZ.HONG_KONG);
        Date date3 = DateTimes.tzDateToTZ(date1, TZ.MOSCOW, TZ.DUBAI);
        Date date4 = DateTimes.tzDateToTZ(date1, TZ.SHANGHAI, TZ.NEW_YORK);
        Date date5 = DateTimes.tzDateToTZ(date1, null, null);

        LocalDateTime ldt1 = DateTimes.defaultLocalDTToTZ(ldt, TZ.MOSCOW);
        LocalDateTime ldt2 = DateTimes.defaultLocalDTToTZ(ldt, TZ.HONG_KONG);
        LocalDateTime ldt3 = DateTimes.tzLocalDTToTZ(ldt1, TZ.MOSCOW, TZ.DUBAI);
        LocalDateTime ldt4 = DateTimes.tzLocalDTToTZ(ldt1, TZ.SHANGHAI, TZ.NEW_YORK);
        LocalDateTime ldt5 = DateTimes.tzLocalDTToTZ(ldt1, null, null);

        ZonedDateTime zdt1 = DateTimes.tzDateToZonedDT(date, null, TZ.NEW_YORK);
        ZonedDateTime zdt2 = DateTimes.tzDateToZonedDT(date, TZ.MOSCOW, TZ.NEW_YORK);
        ZonedDateTime zdt3 = DateTimes.tzDateToZonedDT(date, TZ.NEW_YORK, TZ.DUBAI);
        ZonedDateTime zdt4 = DateTimes.tzDateToZonedDT(date, null, null);
        ZonedDateTime zdt5 = DateTimes.tzDateToZonedDT(date, TZ.NEW_YORK, TZ.NEW_YORK);

        System.out.println("before(+8): " + G.dtSimple(date) + "---after(+3): " + G.dtSimple(date1));   // before(+8): 2022-01-27 06:00:10.065---after(+3): 2022-01-27 01:00:10.065
        System.out.println("before(+8): " + G.dtSimple(date) + "---after(+8): " + G.dtSimple(date2));   // before(+8): 2022-01-27 06:00:10.065---after(+8): 2022-01-27 06:00:10.065
        System.out.println("before(+3): " + G.dtSimple(date1) + "---after(+4): " + G.dtSimple(date3));  // before(+3): 2022-01-27 01:00:10.065---after(+4): 2022-01-27 02:00:10.065
        System.out.println("before(+8): " + G.dtSimple(date1) + "---after(-5): " + G.dtSimple(date4));  // before(+8): 2022-01-27 01:00:10.065---after(-5): 2022-01-26 12:00:10.065
        System.out.println("before(+8): " + G.dtSimple(date1) + "---after(+8): " + G.dtSimple(date5));  // before(+8): 2022-01-27 01:00:10.065---after(+8): 2022-01-27 01:00:10.065
        System.out.println("before(+8): " + G.dtSimple(ldt) + "---after(+3): " + G.dtSimple(ldt1));     // before(+8): 2022-01-27 06:00:10.065---after(+3): 2022-01-27 01:00:10.065
        System.out.println("before(+8): " + G.dtSimple(ldt) + "---after(+8): " + G.dtSimple(ldt2));     // before(+8): 2022-01-27 06:00:10.065---after(+8): 2022-01-27 06:00:10.065
        System.out.println("before(+3): " + G.dtSimple(ldt1) + "---after(+4): " + G.dtSimple(ldt3));    // before(+3): 2022-01-27 01:00:10.065---after(+4): 2022-01-27 02:00:10.065
        System.out.println("before(+8): " + G.dtSimple(ldt1) + "---after(-5): " + G.dtSimple(ldt4));    // before(+8): 2022-01-27 01:00:10.065---after(-5): 2022-01-26 12:00:10.065
        System.out.println("before(+8): " + G.dtSimple(ldt1) + "---after(+8): " + G.dtSimple(ldt5));    // before(+8): 2022-01-27 01:00:10.065---after(+8): 2022-01-27 01:00:10.065

        System.out.println("before(+8): " + G.dtSimple(date) + "---after(-5): " + G.dtSimple(zdt1));
        System.out.println("before(+3): " + G.dtSimple(date) + "---after(-5): " + G.dtSimple(zdt2));
        System.out.println("before(-5): " + G.dtSimple(date) + "---after(+4): " + G.dtSimple(zdt3));
        System.out.println("before(+8): " + G.dtSimple(date) + "---after(+8): " + G.dtSimple(zdt4));
        System.out.println("before(-5): " + G.dtSimple(date) + "---after(-5): " + G.dtSimple(zdt5));

        assertEquals("2022-01-27 01:00:10.065", G.dtSimple(date1));
        assertEquals("2022-01-27 06:00:10.065", G.dtSimple(date2));
        assertEquals("2022-01-27 02:00:10.065", G.dtSimple(date3));
        assertEquals("2022-01-26 12:00:10.065", G.dtSimple(date4));
        assertEquals("2022-01-27 01:00:10.065", G.dtSimple(date5));
        assertEquals("2022-01-27 01:00:10.065", G.dtSimple(ldt1));
        assertEquals("2022-01-27 06:00:10.065", G.dtSimple(ldt2));
        assertEquals("2022-01-27 02:00:10.065", G.dtSimple(ldt3));
        assertEquals("2022-01-26 17:00:10.065 [America/New_York -05:00]", G.dtSimple(zdt1));
        assertEquals("2022-01-26 22:00:10.065 [America/New_York -05:00]", G.dtSimple(zdt2));
        assertEquals("2022-01-27 15:00:10.065 [Asia/Dubai +04:00]", G.dtSimple(zdt3));
        assertEquals("2022-01-27 06:00:10.065", G.dtSimple(zdt4));
        assertEquals("2022-01-27 06:00:10.065 [America/New_York -05:00]", G.dtSimple(zdt5));

    }


    @Test
    public void testHoursAndMinutes() {
        List<String> hhs1 = DateTimes.hoursOfDay(0, ":");
        List<String> hhs2 = DateTimes.hoursOfDay(0, "");
        List<String> hhs3 = DateTimes.hoursOfDay(1, ":");
        List<String> hhs4 = DateTimes.hoursOfDay(1, "");
        List<String> hhs5 = DateTimes.hoursOfDay(2, ":");
        List<String> hhs6 = DateTimes.hoursOfDay(2, "");
        List<String> minutes1 = DateTimes.minutesOfHour("", false, ":");
        List<String> minutes2 = DateTimes.minutesOfHour("", false, "");
        List<String> minutes3 = DateTimes.minutesOfHour("15", false, ":");
        List<String> minutes4 = DateTimes.minutesOfHour("15", false, "");
        List<String> minutes5 = DateTimes.minutesOfHour("", true, ":");
        List<String> minutes6 = DateTimes.minutesOfHour("", true, "");
        List<String> minutes7 = DateTimes.minutesOfHour("15", true, ":");
        List<String> minutes8 = DateTimes.minutesOfHour("15", true, "");

        List<String> seconds1 = DateTimes.secondsOfMinute("", ":");
        List<String> seconds2 = DateTimes.secondsOfMinute("", "");
        List<String> seconds3 = DateTimes.secondsOfMinute("56", ":");
        List<String> seconds4 = DateTimes.secondsOfMinute("56", "");

        // System.out.println(hhs1);
        // System.out.println(hhs2);
        // System.out.println(hhs3);
        // System.out.println(hhs4);
        // System.out.println(hhs5);
        // System.out.println(hhs6);
        // System.out.println(minutes1);
        // System.out.println(minutes2);
        // System.out.println(minutes3);
        // System.out.println(minutes4);
        // System.out.println(minutes5);
        // System.out.println(minutes6);
        // System.out.println(minutes7);
        // System.out.println(minutes8);
        // System.out.println(seconds1);
        // System.out.println(seconds2);
        // System.out.println(seconds3);
        // System.out.println(seconds4);

        assertEquals(hhs1.toString(), "[00, 01, 02, 03, 04, 05, 06, 07, 08, 09, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23]");
        assertEquals(hhs2.toString(), "[00, 01, 02, 03, 04, 05, 06, 07, 08, 09, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23]");
        assertEquals(hhs3.toString(), "[00:00, 01:00, 02:00, 03:00, 04:00, 05:00, 06:00, 07:00, 08:00, 09:00, 10:00, 11:00, 12:00, 13:00, 14:00, " +
                "15:00, 16:00, 17:00, 18:00, 19:00, 20:00, 21:00, 22:00, 23:00]");
        assertEquals(hhs4.toString(), "[0000, 0100, 0200, 0300, 0400, 0500, 0600, 0700, 0800, 0900, 1000, 1100, 1200, 1300, 1400, 1500, 1600, 1700, " +
                "1800, 1900, 2000, 2100, 2200, 2300]");
        assertEquals(hhs5.toString(), "[00:00:00, 01:00:00, 02:00:00, 03:00:00, 04:00:00, 05:00:00, 06:00:00, 07:00:00, 08:00:00, 09:00:00, 10:00:00, " +
                "11:00:00, 12:00:00, 13:00:00, 14:00:00, 15:00:00, 16:00:00, 17:00:00, 18:00:00, 19:00:00, 20:00:00, 21:00:00, 22:00:00, 23:00:00]");
        assertEquals(hhs6.toString(), "[000000, 010000, 020000, 030000, 040000, 050000, 060000, 070000, 080000, 090000, 100000, 110000, 120000, 130000, " +
                "140000, 150000, 160000, 170000, 180000, 190000, 200000, 210000, 220000, 230000]");


        assertEquals(minutes1.size(), 60);
        assertEquals(minutes2.size(), 60);
        assertEquals(minutes3.size(), 60);
        assertEquals(minutes4.size(), 60);
        assertEquals(minutes5.size(), 60);
        assertEquals(minutes6.size(), 60);
        assertEquals(minutes7.size(), 60);
        assertEquals(minutes8.size(), 60);
        assertEquals(minutes1.get(29), "29");
        assertEquals(minutes2.get(29), "29");
        assertEquals(minutes3.get(29), "15:29");
        assertEquals(minutes4.get(29), "1529");
        assertEquals(minutes5.get(29), "29:00");
        assertEquals(minutes6.get(29), "2900");
        assertEquals(minutes7.get(29), "15:29:00");
        assertEquals(minutes8.get(29), "152900");

        assertEquals(seconds1.get(3), "03");
        assertEquals(seconds2.get(3), "03");
        assertEquals(seconds3.get(3), "56:03");
        assertEquals(seconds4.get(3), "5603");
        assertEquals(seconds1.get(39), "39");
        assertEquals(seconds2.get(39), "39");
        assertEquals(seconds3.get(39), "56:39");
        assertEquals(seconds4.get(39), "5639");

    }

    @Test
    public void testHourMinutesOfDay() {
        List<String> hourMinutes1 = DateTimes.hourMinutesOfDay(false, ":");
        List<String> hourMinutes2 = DateTimes.hourMinutesOfDay(true, ":");
        List<String> hourMinutes3 = DateTimes.hourMinutesOfDay(false, null);
        List<String> hourMinutes4 = DateTimes.hourMinutesOfDay(true, null);
        // System.out.println(hourMinutes1);
        // System.out.println(hourMinutes2);
        // System.out.println(hourMinutes3);
        // System.out.println(hourMinutes4);

        assertEquals(hourMinutes1.size(), 1440);
        assertEquals(hourMinutes2.size(), 1440);
        assertEquals(hourMinutes3.size(), 1440);
        assertEquals(hourMinutes4.size(), 1440);
        assertEquals(hourMinutes1.get(999), "16:39");
        assertEquals(hourMinutes2.get(999), "16:39:00");
        assertEquals(hourMinutes3.get(999), "1639");
        assertEquals(hourMinutes4.get(999), "163900");
        assertEquals(hourMinutes1.get(1439), "23:59");
        assertEquals(hourMinutes2.get(1439), "23:59:00");
        assertEquals(hourMinutes3.get(1439), "2359");
        assertEquals(hourMinutes4.get(1439), "235900");
    }

    @Test
    public void testToUTCZonedDT() {
        LocalDateTime localDateTime = LocalDateTime.of(2022, 1, 1, 10, 10, 5, 987656789);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, TZ.NEW_YORK);
        OffsetDateTime offsetDateTime = OffsetDateTime.of(localDateTime, ZoneOffset.UTC);
        Instant instant = localDateTime.toInstant(ZoneOffset.ofHours(8));
        // 转换成 ZonedDateTime
        ZonedDateTime zonedDateTime1 = DateTime.of(localDateTime).toZonedDT(null);
        ZonedDateTime zonedDateTime2 = DateTime.of(zonedDateTime).toZonedDT(null);
        ZonedDateTime zonedDateTime3 = DateTime.of(offsetDateTime).toZonedDT(null);
        ZonedDateTime zonedDateTime4 = DateTime.of(instant).toZonedDT(null);
        // System.out.println(localDateTime);
        // System.out.println(zonedDateTime);
        // System.out.println(offsetDateTime);
        // System.out.println(instant);
        // System.out.println(G.dtDetail(zonedDateTime1));
        // System.out.println(G.dtDetail(zonedDateTime2));
        // System.out.println(G.dtDetail(zonedDateTime3));
        // System.out.println(G.dtDetail(zonedDateTime4));
        assertEquals("2022-01-01 10:10:05.987656789 [Asia/Shanghai +08:00 GMT+8 周六]", G.dtDetail(zonedDateTime1));
        assertEquals("2022-01-01 10:10:05.987656789 [America/New_York -05:00 GMT-5 周六]", G.dtDetail(zonedDateTime2));
        assertEquals("2022-01-01 10:10:05.987656789 [Z +00:00 GMT 周六]", G.dtDetail(zonedDateTime3));
        assertEquals("2022-01-01 10:10:05.987656789 [Asia/Shanghai +08:00 GMT+8 周六]", G.dtDetail(zonedDateTime4));
    }

    @Test
    public void testDateToTZ() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse("2020-01-01 5:01:50");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("origin date: \n" + sdf.format(date));
        System.out.println("-------------------------");

        Date utcDate = DateTimes.defaultDateToTZ(date, ZoneId.of("UTC"));
        String utcDateStr = sdf.format(utcDate);
        System.out.println("local to utc date: \n" + utcDateStr);
        assertEquals("2019-12-31 21:01:50", utcDateStr);
        System.out.println("-------------------------");

        // ZoneId.of("Australia/Victoria")  +11:00
        Date utcDate1 = DateTimes.tzDateToTZ(date, ZoneId.of("Australia/Victoria"), ZoneId.of("UTC"));
        System.out.println("Australia/Victoria to utc date: \n" + sdf.format(utcDate1));
        assertEquals("2019-12-31 18:01:50", sdf.format(utcDate1));
        System.out.println("-------------------------");

    }

    @Test
    public void testCalendarToTZ() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse("2019-12-31 22:01:50");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("origin date: \n" + sdf.format(date));
        System.out.println("-------------------------");

        Calendar utcCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        utcCalendar.setTime(date);
        System.out.println("utcCalendar: \n" + utcCalendar);
        System.out.println("utcCalendar date: \n" + sdf.format(utcCalendar.getTime()));
        assertEquals("2019-12-31 22:01:50", sdf.format(utcCalendar.getTime()));
        System.out.println("-------------------------");

        Calendar calendar = DateTimeUtils.calendarToLocal(utcCalendar);
        System.out.println("calendar: \n" + calendar);
        System.out.println("calendar date: \n" + sdf.format(calendar.getTime()));
        assertEquals("2020-01-01 06:01:50", sdf.format(calendar.getTime()));
    }

    @Test
    public void testCalendarToTZ1() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse("2019-12-31 22:01:50");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("origin date: \n" + sdf.format(date));
        System.out.println("-------------------------");

        Calendar utcCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        utcCalendar.setTime(date);
        System.out.println("utcCalendar: \n" + utcCalendar);
        System.out.println("utcCalendar date: \n" + sdf.format(utcCalendar.getTime()));
        assertEquals("2019-12-31 22:01:50", sdf.format(utcCalendar.getTime()));
        System.out.println("-------------------------");

        // ZoneId.of("Australia/Victoria")  +11:00
        Calendar calendar = DateTimeUtils.calendarToTZ(utcCalendar, ZoneId.of("Australia/Victoria"));
        System.out.println("calendar: \n" + calendar);
        System.out.println("calendar date: \n" + sdf.format(calendar.getTime()));
        assertEquals("2020-01-01 09:01:50", sdf.format(calendar.getTime()));
    }

    @Test
    public void testCalendarToTZ2() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse("2019-12-31 22:01:50");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("origin date: \n" + sdf.format(date));
        System.out.println("-------------------------");

        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTime(date);
        System.out.println("Calendar: \n" + localCalendar);
        System.out.println("localCalendar date: \n" + sdf.format(localCalendar.getTime()));
        assertEquals("2019-12-31 22:01:50", sdf.format(localCalendar.getTime()));
        System.out.println("-------------------------");

        // ZoneId.of("Australia/Victoria")  +11:00
        Calendar calendar = DateTimeUtils.calendarToTZ(localCalendar, ZoneId.of("Australia/Victoria"));
        System.out.println("calendar: \n" + calendar);
        System.out.println("calendar date: \n" + sdf.format(calendar.getTime()));
        assertEquals("2020-01-01 01:01:50", sdf.format(calendar.getTime()));
    }

}
