package com.iofairy;

import com.iofairy.falcon.util.DateTimeUtils;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author GG
 * @version 1.0
 */
public class DateTimeUtilsTest {
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

        Date utcDate = DateTimeUtils.localDateToTZ(date, ZoneId.of("UTC"));
        String utcDateStr = sdf.format(utcDate);
        System.out.println("local to utc date: \n" + utcDateStr);
        assertEquals("2019-12-31 21:01:50", utcDateStr);
        System.out.println("-------------------------");

        // ZoneId.of("Australia/Victoria")  +11:00
        Date utcDate1 = DateTimeUtils.tzDateToTZ(date, ZoneId.of("Australia/Victoria"), ZoneId.of("UTC"));
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
