package com.iofairy.test;

import com.iofairy.falcon.time.Interval;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author GG
 * @version 1.0
 * @date 2021/10/1 8:44
 */
public class IntervalTest {
    @Test
    public void testInterval() {
        LocalDateTime fromDateTime = LocalDateTime.of(1803, 11, 23, 10, 56, 43);
        LocalDateTime toDateTime = LocalDateTime.of(2020, 9, 1, 3, 33, 10);
        Interval interval = Interval.between(fromDateTime, toDateTime);
        System.out.println(interval.toFullString());
        assertEquals("2世纪16年9月8天16时36分27秒0毫秒", interval.toString());

        LocalDateTime fromDateTime1 = LocalDateTime.of(2020, 9, 1, 3, 33, 10);
        LocalDateTime toDateTime1 = LocalDateTime.of(1803, 11, 23, 10, 56, 43);
        Interval interval1 = Interval.between(fromDateTime1, toDateTime1);
        System.out.println(interval1.toFullString());
        assertEquals("2世纪16年9月8天16时36分27秒0毫秒", interval1.toString());
    }

    @Test
    public void testInterval2() {
        LocalDateTime fromDateTime = LocalDateTime.of(1803, 11, 23, 10, 56, 43, 65 * 1000000);
        LocalDateTime toDateTime = LocalDateTime.of(2020, 9, 1, 3, 33, 10, 74 * 1000000);
        Interval interval = Interval.between(fromDateTime, toDateTime);
        System.out.println(interval.toFullString());
        assertEquals("2世纪16年9月8天16时36分27秒9毫秒", interval.toString());

        LocalDateTime fromDateTime1 = LocalDateTime.of(1803, 11, 23, 10, 56, 43, 65 * 1000000);
        LocalDateTime toDateTime1 = LocalDateTime.of(2020, 9, 1, 3, 33, 10, 29 * 1000000);
        Interval interval1 = Interval.between(fromDateTime1, toDateTime1);
        System.out.println(interval1.toFullString());
        assertEquals("2世纪16年9月8天16时36分26秒964毫秒", interval1.toString());
    }

    @Test
    public void testPlusAndMinus() {
        LocalDateTime fromDateTime = LocalDateTime.of(1803, 11, 23, 10, 56, 43, 65 * 1000000);
        LocalDateTime toDateTime = LocalDateTime.of(2020, 9, 1, 3, 33, 10, 29 * 1000000);
        // 2世纪16年9月8天16时36分26秒964毫秒
        Interval interval = new Interval(2, 16, 9, 8, 16, 36, 26, 964);
        LocalDateTime localDateTime = Interval.plusInterval(fromDateTime, interval);
        System.out.println(localDateTime);
        assertEquals("2020-09-01T03:33:10.029", localDateTime.toString());

        LocalDateTime localDateTime1 = Interval.minusInterval(toDateTime, interval);
        System.out.println(localDateTime1);
        assertEquals("1803-11-23T10:56:43.065", localDateTime1.toString());
    }

    @Test
    public void testOffsetInterval() {
        LocalDateTime fromDateTime = LocalDateTime.of(2020, 9, 1, 3, 33, 10);
        LocalDateTime toDateTime = LocalDateTime.of(2020, 9, 1, 3, 33, 10);

        OffsetDateTime fromDT = OffsetDateTime.of(fromDateTime, ZoneOffset.UTC);
        OffsetDateTime toDT = OffsetDateTime.of(toDateTime, ZoneOffset.of("+08:00"));

        Interval interval = Interval.between(fromDT, toDT);     // 8时0分0秒0毫秒
        System.out.println(interval.toFullString());
        assertEquals("8时0分0秒0毫秒", interval.toString());

    }

    @Test
    public void testCalendarInterval() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse("2020-01-01 5:01:50");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTime(date);
        Calendar utcCalendar = Calendar.getInstance(TimeZone.getTimeZone(ZoneId.of("UTC")));
        utcCalendar.setTime(date);

        Interval interval = Interval.between(localCalendar, utcCalendar);
        System.out.println(interval);
        assertEquals("8时0分0秒0毫秒", interval.toString());

        Interval interval1 = Interval.betweenIgnoreTZ(localCalendar, utcCalendar);
        System.out.println(interval1);
        assertEquals("0毫秒", interval1.toString());

    }

    @Test
    public void testPlusCalendar() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = null;
        try {
            date = sdf.parse("1803-11-23 10:56:43.065");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(date);
        Interval interval = new Interval(2, 16, 9, 8, 16, 36, 26, 964);

        Calendar toCalendar = Interval.plusInterval(calendar, interval);
        System.out.println(toCalendar);
        System.out.println(sdf.format(toCalendar.getTime()));
        assertEquals("2020-09-01 03:33:10.029", sdf.format(toCalendar.getTime()));
    }
}
