package com.iofairy.test;

import com.iofairy.except.UnexpectedParameterException;
import com.iofairy.falcon.time.*;
import com.iofairy.tcf.Try;
import com.iofairy.top.G;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author GG
 * @version 1.0
 * @date 2021/10/1 8:44
 */
public class IntervalTest {
    @Test
    public void testSignedInterval() {
        ZoneOffset zoneOffset = ZoneOffset.ofHours(8);
        LocalDateTime fromLocalDateTime = LocalDateTime.of(1903, 6, 15, 10, 56, 43, 987656789);
        Instant fromInstant = fromLocalDateTime.toInstant(zoneOffset);
        OffsetDateTime fromOffsetDateTime = OffsetDateTime.ofInstant(fromInstant, TZ.NEW_YORK);
        ZonedDateTime fromZonedDateTime = ZonedDateTime.ofInstant(fromInstant, TZ.NEW_YORK);
        Date fromDate = Date.from(fromInstant);
        Calendar fromCalendar = DateTime.of(fromInstant).toDefaultCalendar();
        // System.out.println(G.dtSimple(fromDate));
        // System.out.println(G.dtSimple(fromCalendar));
        // -----------------------------------------------------------------------------
        LocalDateTime toLocalDateTime = LocalDateTime.of(2020, 8, 10, 3, 33, 10, 987656700);
        Instant toInstant = toLocalDateTime.toInstant(zoneOffset);
        OffsetDateTime toOffsetDateTime = OffsetDateTime.ofInstant(toInstant, TZ.NEW_YORK);
        ZonedDateTime toZonedDateTime = ZonedDateTime.ofInstant(toInstant, TZ.NEW_YORK);
        Date toDate = Date.from(toInstant);
        Calendar toCalendar = DateTime.of(toInstant).toDefaultCalendar();
        // System.out.println(G.dtSimple(toDate));
        // System.out.println(G.dtSimple(toCalendar));
        // -----------------------------------------------------------------------------
        ZonedDateTime fromZDT = LocalDateTime.of(2022, 2, 10, 3, 0).atZone(TZ.NEW_YORK);
        ZonedDateTime toZDT = LocalDateTime.of(2022, 6, 10, 3, 0).atZone(TZ.NEW_YORK);
        ZonedDateTime fromZDT1 = LocalDateTime.of(1900, 1, 1, 0, 0).atZone(TZ.DEFAULT_ZONE);
        ZonedDateTime toZDT1 = LocalDateTime.of(2009, 7, 6, 0, 0).atZone(TZ.DEFAULT_ZONE);
        System.out.println("fromZDT: " + G.dtDetail(fromZDT));  // fromZDT: 2022-02-10 03:00:00.000000000 [America/New_York -05:00 GMT-5 周四]
        System.out.println("toZDT: " + G.dtDetail(toZDT));  // toZDT: 2022-06-10 03:00:00.000000000 [America/New_York -04:00 GMT-4 周五]
        System.out.println("fromZDT1: " + G.dtDetail(fromZDT1));    // fromZDT1: 1900-01-01 00:00:00.000000000 [Asia/Shanghai +08:05 GMT+8:05:43 周一]
        System.out.println("toZDT1: " + G.dtDetail(toZDT1));    // toZDT1: 2009-07-06 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        // -----------------------------------------------------------------------------
        SignedInterval signedInterval1 = SignedInterval.between(fromLocalDateTime, toLocalDateTime);
        SignedInterval signedInterval2 = SignedInterval.between(fromLocalDateTime, toInstant);
        SignedInterval signedInterval3 = SignedInterval.between(fromLocalDateTime, toOffsetDateTime);
        SignedInterval signedInterval4 = SignedInterval.between(fromLocalDateTime, toZonedDateTime);
        SignedInterval signedInterval5 = SignedInterval.between(toLocalDateTime, fromLocalDateTime);
        SignedInterval signedInterval6 = SignedInterval.between(toLocalDateTime, fromInstant);
        SignedInterval signedInterval7 = SignedInterval.between(toLocalDateTime, fromOffsetDateTime);
        SignedInterval signedInterval8 = SignedInterval.between(toLocalDateTime, fromZonedDateTime);
        SignedInterval signedInterval9 = SignedInterval.between(fromDate, toDate);
        SignedInterval signedInterval10 = SignedInterval.between(fromCalendar, toCalendar);
        SignedInterval signedInterval11 = SignedInterval.between(toDate, fromDate);
        SignedInterval signedInterval12 = SignedInterval.between(toCalendar, fromCalendar);
        SignedInterval signedInterval13 = SignedInterval.between(fromZDT, toZDT);
        SignedInterval signedInterval14 = SignedInterval.between(toZDT, fromZDT);
        SignedInterval signedInterval15 = SignedInterval.between(fromZDT1, toZDT1);
        SignedInterval signedInterval16 = SignedInterval.between(toZDT1, fromZDT1);

        System.out.println(signedInterval1);
        System.out.println(signedInterval2);
        System.out.println(signedInterval3);
        System.out.println(signedInterval4);
        System.out.println(signedInterval5);
        System.out.println(signedInterval6);
        System.out.println(signedInterval7);
        System.out.println(signedInterval8);
        System.out.println(signedInterval9);
        System.out.println(signedInterval10);
        System.out.println(signedInterval11);
        System.out.println(signedInterval12);
        System.out.println("fromZDT - toZDT: " + signedInterval13); // fromZDT - toZDT: 4月0天0时0分0秒0毫秒
        System.out.println("toZDT - fromZDT: " + signedInterval14); // toZDT - fromZDT: -4月0天0时0分0秒0毫秒
        System.out.println("fromZDT1 - toZDT1: " + signedInterval15);   // fromZDT1 - toZDT1: 1世纪9年6月5天0时0分0秒0毫秒
        System.out.println("toZDT1 - fromZDT1: " + signedInterval16);   // toZDT1 - fromZDT1: -1世纪-9年-6月-5天0时0分0秒0毫秒

        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", signedInterval1.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", signedInterval2.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", signedInterval3.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", signedInterval4.toString());
        assertEquals("-1世纪-17年-1月-24天-16时-36分-26秒-999毫秒-999微秒-911纳秒", signedInterval5.toString());
        assertEquals("-1世纪-17年-1月-24天-16时-36分-26秒-999毫秒-999微秒-911纳秒", signedInterval6.toString());
        assertEquals("-1世纪-17年-1月-24天-16时-36分-26秒-999毫秒-999微秒-911纳秒", signedInterval7.toString());
        assertEquals("-1世纪-17年-1月-24天-16时-36分-26秒-999毫秒-999微秒-911纳秒", signedInterval8.toString());
        assertEquals("1世纪17年1月25天16时36分27秒0毫秒", signedInterval9.toString());
        assertEquals("1世纪17年1月25天16时36分27秒0毫秒", signedInterval10.toString());
        assertEquals("-1世纪-17年-1月-24天-16时-36分-27秒0毫秒", signedInterval11.toString());
        assertEquals("-1世纪-17年-1月-24天-16时-36分-27秒0毫秒", signedInterval12.toString());
        assertEquals("4月0天0时0分0秒0毫秒", signedInterval13.toString());
        assertEquals("-4月0天0时0分0秒0毫秒", signedInterval14.toString());
        assertEquals("1世纪9年6月5天0时0分0秒0毫秒", signedInterval15.toString());
        assertEquals("-1世纪-9年-6月-5天0时0分0秒0毫秒", signedInterval16.toString());
        System.out.println("======================================");
        System.out.println(signedInterval1.equals(signedInterval2));
        System.out.println(signedInterval3.equals(signedInterval4));
        System.out.println(signedInterval1.equals(signedInterval5));
        System.out.println(signedInterval7.equals(signedInterval8));
        System.out.println("======================================");
        Temporal temporal1 = signedInterval1.subtractFrom(toInstant);
        Temporal temporal2 = signedInterval1.addTo(fromLocalDateTime);
        Temporal temporal3 = signedInterval6.subtractFrom(fromInstant);
        Temporal temporal4 = signedInterval6.addTo(toLocalDateTime);
        System.out.println(G.dtSimple(temporal1));
        System.out.println(G.dtSimple(temporal2));
        System.out.println(G.dtSimple(temporal3));
        System.out.println(G.dtSimple(temporal4));
        Date date1 = signedInterval9.addTo(fromDate);
        Date date2 = signedInterval9.subtractFrom(toDate);
        Date date3 = signedInterval11.addTo(toDate);
        Date date4 = signedInterval11.subtractFrom(fromDate);
        Calendar calendar1 = signedInterval10.addTo(fromCalendar);
        Calendar calendar2 = signedInterval10.subtractFrom(toCalendar);
        Calendar calendar3 = signedInterval12.addTo(toCalendar);
        Calendar calendar4 = signedInterval12.subtractFrom(fromCalendar);
        System.out.println(G.dtSimple(date1));
        System.out.println(G.dtSimple(date2));
        System.out.println(G.dtSimple(date3));
        System.out.println(G.dtSimple(date4));
        System.out.println(G.dtSimple(calendar1));
        System.out.println(G.dtSimple(calendar2));
        System.out.println(G.dtSimple(calendar3));
        System.out.println(G.dtSimple(calendar4));
        System.out.println("======================================");
        System.out.println(signedInterval1.toFullString());
        System.out.println(signedInterval5.toFullString());
        System.out.println(signedInterval9.toFullString());
        System.out.println(signedInterval11.toFullString());

        Temporal t1 = signedInterval13.subtractFrom(toZDT);
        Temporal t2 = signedInterval14.addTo(toZDT);
        Temporal t3 = signedInterval15.addTo(fromZDT1);
        Temporal t4 = signedInterval16.addTo(toZDT1);
        System.out.println(G.dtDetail(t1));
        System.out.println(G.dtDetail(t2));
        System.out.println(G.dtDetail(t3));
        System.out.println(G.dtDetail(t4));
        assertEquals("2022-02-10 03:00:00.000000000 [America/New_York -05:00 GMT-5 周四]", G.dtDetail(t1));
        assertEquals("2022-02-10 03:00:00.000000000 [America/New_York -05:00 GMT-5 周四]", G.dtDetail(t2));
        assertEquals("2009-07-06 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周一]", G.dtDetail(t3));
        assertEquals("1900-01-01 00:00:00.000000000 [Asia/Shanghai +08:05 GMT+8:05:43 周一]", G.dtDetail(t4));
    }

    @Test
    public void testSignedInterval1() {
        ZoneOffset zoneOffset = ZoneOffset.ofHours(8);
        LocalDateTime fromLocalDateTime = LocalDateTime.of(1903, 2, 15, 10, 56, 43, 987656789);
        Instant fromInstant = fromLocalDateTime.toInstant(zoneOffset);
        OffsetDateTime fromOffsetDateTime = OffsetDateTime.ofInstant(fromInstant, TZ.NEW_YORK);
        ZonedDateTime fromZonedDateTime = ZonedDateTime.ofInstant(fromInstant, TZ.NEW_YORK);
        Date fromDate = Date.from(fromInstant);
        Calendar fromCalendar = DateTime.of(fromInstant).toDefaultCalendar();
        // System.out.println(G.dtSimple(fromDate));
        // System.out.println(G.dtSimple(fromCalendar));
        // -----------------------------------------------------------------------------
        LocalDateTime toLocalDateTime = LocalDateTime.of(2020, 4, 10, 3, 33, 10, 987656700);
        Instant toInstant = toLocalDateTime.toInstant(zoneOffset);
        OffsetDateTime toOffsetDateTime = OffsetDateTime.ofInstant(toInstant, TZ.NEW_YORK);
        ZonedDateTime toZonedDateTime = ZonedDateTime.ofInstant(toInstant, TZ.NEW_YORK);
        Date toDate = Date.from(toInstant);
        Calendar toCalendar = DateTime.of(toInstant).toDefaultCalendar();
        // System.out.println(G.dtSimple(toDate));
        // System.out.println(G.dtSimple(toCalendar));
        // -----------------------------------------------------------------------------
        SignedInterval signedInterval1 = SignedInterval.between(fromLocalDateTime, toLocalDateTime);
        SignedInterval signedInterval2 = SignedInterval.between(fromLocalDateTime, toInstant);
        SignedInterval signedInterval3 = SignedInterval.between(fromLocalDateTime, toOffsetDateTime);
        SignedInterval signedInterval4 = SignedInterval.between(fromLocalDateTime, toZonedDateTime);
        SignedInterval signedInterval5 = SignedInterval.between(toLocalDateTime, fromLocalDateTime);
        SignedInterval signedInterval6 = SignedInterval.between(toLocalDateTime, fromInstant);
        SignedInterval signedInterval7 = SignedInterval.between(toLocalDateTime, fromOffsetDateTime);
        SignedInterval signedInterval8 = SignedInterval.between(toLocalDateTime, fromZonedDateTime);
        SignedInterval signedInterval9 = SignedInterval.between(fromDate, toDate);
        SignedInterval signedInterval10 = SignedInterval.between(fromCalendar, toCalendar);
        SignedInterval signedInterval11 = SignedInterval.between(toDate, fromDate);
        SignedInterval signedInterval12 = SignedInterval.between(toCalendar, fromCalendar);
        System.out.println(signedInterval1);
        System.out.println(signedInterval2);
        System.out.println(signedInterval3);
        System.out.println(signedInterval4);
        System.out.println(signedInterval5);
        System.out.println(signedInterval6);
        System.out.println(signedInterval7);
        System.out.println(signedInterval8);
        System.out.println(signedInterval9);
        System.out.println(signedInterval10);
        System.out.println(signedInterval11);
        System.out.println(signedInterval12);
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", signedInterval1.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", signedInterval2.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", signedInterval3.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", signedInterval4.toString());
        assertEquals("-1世纪-17年-1月-22天-16时-36分-26秒-999毫秒-999微秒-911纳秒", signedInterval5.toString());
        assertEquals("-1世纪-17年-1月-22天-16时-36分-26秒-999毫秒-999微秒-911纳秒", signedInterval6.toString());
        assertEquals("-1世纪-17年-1月-22天-16时-36分-26秒-999毫秒-999微秒-911纳秒", signedInterval7.toString());
        assertEquals("-1世纪-17年-1月-22天-16时-36分-26秒-999毫秒-999微秒-911纳秒", signedInterval8.toString());
        assertEquals("1世纪17年1月25天16时36分27秒0毫秒", signedInterval9.toString());
        assertEquals("1世纪17年1月25天16时36分27秒0毫秒", signedInterval10.toString());
        assertEquals("-1世纪-17年-1月-22天-16时-36分-27秒0毫秒", signedInterval11.toString());
        assertEquals("-1世纪-17年-1月-22天-16时-36分-27秒0毫秒", signedInterval12.toString());
        System.out.println("======================================");
        System.out.println(signedInterval1.equals(signedInterval2));
        System.out.println(signedInterval3.equals(signedInterval4));
        System.out.println(signedInterval1.equals(signedInterval5));
        System.out.println(signedInterval7.equals(signedInterval8));
        System.out.println("======================================");
        Temporal temporal1 = signedInterval1.subtractFrom(toInstant);
        Temporal temporal2 = signedInterval1.addTo(fromLocalDateTime);
        Temporal temporal3 = signedInterval6.subtractFrom(fromInstant);
        Temporal temporal4 = signedInterval6.addTo(toLocalDateTime);
        System.out.println(G.dtSimple(temporal1));
        System.out.println(G.dtSimple(temporal2));
        System.out.println(G.dtSimple(temporal3));
        System.out.println(G.dtSimple(temporal4));
        Date date1 = signedInterval9.addTo(fromDate);
        Date date2 = signedInterval9.subtractFrom(toDate);
        Date date3 = signedInterval11.addTo(toDate);
        Date date4 = signedInterval11.subtractFrom(fromDate);
        Calendar calendar1 = signedInterval10.addTo(fromCalendar);
        Calendar calendar2 = signedInterval10.subtractFrom(toCalendar);
        Calendar calendar3 = signedInterval12.addTo(toCalendar);
        Calendar calendar4 = signedInterval12.subtractFrom(fromCalendar);
        System.out.println(G.dtSimple(date1));
        System.out.println(G.dtSimple(date2));
        System.out.println(G.dtSimple(date3));
        System.out.println(G.dtSimple(date4));
        System.out.println(G.dtSimple(calendar1));
        System.out.println(G.dtSimple(calendar2));
        System.out.println(G.dtSimple(calendar3));
        System.out.println(G.dtSimple(calendar4));
        System.out.println("======================================");
        System.out.println(signedInterval1.toFullString());
        System.out.println(signedInterval5.toFullString());
        System.out.println(signedInterval9.toFullString());
        System.out.println(signedInterval11.toFullString());
    }

    @Test
    public void testInterval() {
        ZoneOffset zoneOffset = ZoneOffset.ofHours(8);
        LocalDateTime fromLocalDateTime = LocalDateTime.of(1903, 6, 15, 10, 56, 43, 987656789);
        Instant fromInstant = fromLocalDateTime.toInstant(zoneOffset);
        OffsetDateTime fromOffsetDateTime = OffsetDateTime.ofInstant(fromInstant, TZ.NEW_YORK);
        ZonedDateTime fromZonedDateTime = ZonedDateTime.ofInstant(fromInstant, TZ.NEW_YORK);
        Date fromDate = Date.from(fromInstant);
        Calendar fromCalendar = DateTime.of(fromInstant).toDefaultCalendar();
        // System.out.println(G.dtSimple(fromDate));
        // System.out.println(G.dtSimple(fromCalendar));
        // -----------------------------------------------------------------------------
        LocalDateTime toLocalDateTime = LocalDateTime.of(2020, 8, 10, 3, 33, 10, 987656700);
        Instant toInstant = toLocalDateTime.toInstant(zoneOffset);
        OffsetDateTime toOffsetDateTime = OffsetDateTime.ofInstant(toInstant, TZ.NEW_YORK);
        ZonedDateTime toZonedDateTime = ZonedDateTime.ofInstant(toInstant, TZ.NEW_YORK);
        Date toDate = Date.from(toInstant);
        Calendar toCalendar = DateTime.of(toInstant).toDefaultCalendar();
        // System.out.println(G.dtSimple(toDate));
        // System.out.println(G.dtSimple(toCalendar));
        // -----------------------------------------------------------------------------
        Interval interval1 = Interval.between(fromLocalDateTime, toLocalDateTime);
        Interval interval2 = Interval.between(fromLocalDateTime, toInstant);
        Interval interval3 = Interval.between(fromLocalDateTime, toOffsetDateTime);
        Interval interval4 = Interval.between(fromLocalDateTime, toZonedDateTime);
        Interval interval5 = Interval.between(toLocalDateTime, fromLocalDateTime);
        Interval interval6 = Interval.between(toLocalDateTime, fromInstant);
        Interval interval7 = Interval.between(toLocalDateTime, fromOffsetDateTime);
        Interval interval8 = Interval.between(toLocalDateTime, fromZonedDateTime);
        Interval interval9 = Interval.between(fromDate, toDate);
        Interval interval10 = Interval.between(fromCalendar, toCalendar);
        Interval interval11 = Interval.between(toDate, fromDate);
        Interval interval12 = Interval.between(toCalendar, fromCalendar);
        System.out.println(interval1);
        System.out.println(interval2);
        System.out.println(interval3);
        System.out.println(interval4);
        System.out.println(interval5);
        System.out.println(interval6);
        System.out.println(interval7);
        System.out.println(interval8);
        System.out.println(interval9);
        System.out.println(interval10);
        System.out.println(interval11);
        System.out.println(interval12);
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", interval1.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", interval2.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", interval3.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", interval4.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", interval5.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", interval6.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", interval7.toString());
        assertEquals("1世纪17年1月25天17时36分26秒999毫秒999微秒911纳秒", interval8.toString());
        assertEquals("1世纪17年1月25天16时36分27秒0毫秒", interval9.toString());
        assertEquals("1世纪17年1月25天16时36分27秒0毫秒", interval10.toString());
        assertEquals("1世纪17年1月25天16时36分27秒0毫秒", interval11.toString());
        assertEquals("1世纪17年1月25天16时36分27秒0毫秒", interval12.toString());
        System.out.println("======================================");
        System.out.println(interval1.equals(interval2));
        System.out.println(interval3.equals(interval4));
        System.out.println(interval1.equals(interval5));
        System.out.println(interval7.equals(interval8));
        System.out.println("======================================");
        Temporal temporal1 = interval1.subtractFrom(toInstant);
        Temporal temporal2 = interval1.addTo(fromLocalDateTime);
        Temporal temporal3 = interval6.subtractFrom(fromInstant);
        Temporal temporal4 = interval6.addTo(toLocalDateTime);
        System.out.println(G.dtSimple(temporal1));
        System.out.println(G.dtSimple(temporal2));
        System.out.println(G.dtSimple(temporal3));
        System.out.println(G.dtSimple(temporal4));
        Date date1 = interval9.addTo(fromDate);
        Date date2 = interval9.subtractFrom(toDate);
        Calendar calendar1 = interval10.addTo(fromCalendar);
        Calendar calendar2 = interval10.subtractFrom(toCalendar);
        System.out.println(G.dtSimple(date1));
        System.out.println(G.dtSimple(date2));
        System.out.println(G.dtSimple(calendar1));
        System.out.println(G.dtSimple(calendar2));
        System.out.println("======================================");
        System.out.println(interval1.toFullString());
        System.out.println(interval5.toFullString());
        System.out.println(interval9.toFullString());
        System.out.println(interval11.toFullString());
    }

    @Test
    public void testInterval1() {
        ZoneOffset zoneOffset = ZoneOffset.ofHours(8);
        LocalDateTime fromLocalDateTime = LocalDateTime.of(1903, 2, 15, 10, 56, 43, 987656789);
        Instant fromInstant = fromLocalDateTime.toInstant(zoneOffset);
        OffsetDateTime fromOffsetDateTime = OffsetDateTime.ofInstant(fromInstant, TZ.NEW_YORK);
        ZonedDateTime fromZonedDateTime = ZonedDateTime.ofInstant(fromInstant, TZ.NEW_YORK);
        Date fromDate = Date.from(fromInstant);
        Calendar fromCalendar = DateTime.of(fromInstant).toDefaultCalendar();
        // System.out.println(G.dtSimple(fromDate));
        // System.out.println(G.dtSimple(fromCalendar));
        // -----------------------------------------------------------------------------
        LocalDateTime toLocalDateTime = LocalDateTime.of(2020, 4, 10, 3, 33, 10, 987656700);
        Instant toInstant = toLocalDateTime.toInstant(zoneOffset);
        OffsetDateTime toOffsetDateTime = OffsetDateTime.ofInstant(toInstant, TZ.NEW_YORK);
        ZonedDateTime toZonedDateTime = ZonedDateTime.ofInstant(toInstant, TZ.NEW_YORK);
        Date toDate = Date.from(toInstant);
        Calendar toCalendar = DateTime.of(toInstant).toDefaultCalendar();
        // System.out.println(G.dtSimple(toDate));
        // System.out.println(G.dtSimple(toCalendar));
        // -----------------------------------------------------------------------------
        Interval interval1 = Interval.between(fromLocalDateTime, toLocalDateTime);
        Interval interval2 = Interval.between(fromLocalDateTime, toInstant);
        Interval interval3 = Interval.between(fromLocalDateTime, toOffsetDateTime);
        Interval interval4 = Interval.between(fromLocalDateTime, toZonedDateTime);
        Interval interval5 = Interval.between(toLocalDateTime, fromLocalDateTime);
        Interval interval6 = Interval.between(toLocalDateTime, fromInstant);
        Interval interval7 = Interval.between(toLocalDateTime, fromOffsetDateTime);
        Interval interval8 = Interval.between(toLocalDateTime, fromZonedDateTime);
        Interval interval9 = Interval.between(fromDate, toDate);
        Interval interval10 = Interval.between(fromCalendar, toCalendar);
        Interval interval11 = Interval.between(toDate, fromDate);
        Interval interval12 = Interval.between(toCalendar, fromCalendar);
        System.out.println(interval1);
        System.out.println(interval2);
        System.out.println(interval3);
        System.out.println(interval4);
        System.out.println(interval5);
        System.out.println(interval6);
        System.out.println(interval7);
        System.out.println(interval8);
        System.out.println(interval9);
        System.out.println(interval10);
        System.out.println(interval11);
        System.out.println(interval12);
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", interval1.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", interval2.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", interval3.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", interval4.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", interval5.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", interval6.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", interval7.toString());
        assertEquals("1世纪17年1月25天17时36分26秒999毫秒999微秒911纳秒", interval8.toString());
        assertEquals("1世纪17年1月25天16时36分27秒0毫秒", interval9.toString());
        assertEquals("1世纪17年1月25天16时36分27秒0毫秒", interval10.toString());
        assertEquals("1世纪17年1月25天16时36分27秒0毫秒", interval11.toString());
        assertEquals("1世纪17年1月25天16时36分27秒0毫秒", interval12.toString());
        System.out.println("======================================");
        System.out.println(interval1.equals(interval2));
        System.out.println(interval3.equals(interval4));
        System.out.println(interval1.equals(interval5));
        System.out.println(interval7.equals(interval8));
        System.out.println("======================================");
        Temporal temporal1 = interval1.subtractFrom(toInstant);
        Temporal temporal2 = interval1.addTo(fromLocalDateTime);
        Temporal temporal3 = interval6.subtractFrom(fromInstant);
        Temporal temporal4 = interval6.addTo(toLocalDateTime);
        System.out.println(G.dtSimple(temporal1));
        System.out.println(G.dtSimple(temporal2));
        System.out.println(G.dtSimple(temporal3));
        System.out.println(G.dtSimple(temporal4));
        Date date1 = interval9.addTo(fromDate);
        Date date2 = interval9.subtractFrom(toDate);
        Calendar calendar1 = interval10.addTo(fromCalendar);
        Calendar calendar2 = interval10.subtractFrom(toCalendar);
        System.out.println(G.dtSimple(date1));
        System.out.println(G.dtSimple(date2));
        System.out.println(G.dtSimple(calendar1));
        System.out.println(G.dtSimple(calendar2));
        System.out.println("======================================");
        System.out.println(interval1.toFullString());
        System.out.println(interval5.toFullString());
        System.out.println(interval9.toFullString());
        System.out.println(interval11.toFullString());
    }

    @Test
    public void testSignedInterval2() {
        ZoneOffset zoneOffset = ZoneOffset.ofHours(8);
        LocalDateTime fromLocalDateTime = LocalDateTime.of(1903, 6, 15, 10, 56, 43, 987656789);
        Instant fromInstant = fromLocalDateTime.toInstant(zoneOffset);
        OffsetDateTime fromOffsetDateTime = OffsetDateTime.ofInstant(fromInstant, TZ.NEW_YORK);
        ZonedDateTime fromZonedDateTime = ZonedDateTime.ofInstant(fromInstant, TZ.NEW_YORK);
        // -----------------------------------------------------------------------------
        LocalDateTime toLocalDateTime = LocalDateTime.of(2020, 8, 10, 3, 33, 10, 987656700);
        Instant toInstant = toLocalDateTime.toInstant(zoneOffset);
        OffsetDateTime toOffsetDateTime = OffsetDateTime.ofInstant(toInstant, TZ.NEW_YORK);
        ZonedDateTime toZonedDateTime = ZonedDateTime.ofInstant(toInstant, TZ.NEW_YORK);
        // -----------------------------------------------------------------------------
        SignedInterval signedInterval1 = SignedInterval.between(fromLocalDateTime, toLocalDateTime);
        SignedInterval signedInterval2 = SignedInterval.between(fromLocalDateTime, toInstant);
        SignedInterval signedInterval3 = SignedInterval.between(fromLocalDateTime, toOffsetDateTime);
        SignedInterval signedInterval4 = SignedInterval.between(fromLocalDateTime, toZonedDateTime);
        SignedInterval signedInterval5 = SignedInterval.between(toLocalDateTime, fromLocalDateTime);
        SignedInterval signedInterval6 = SignedInterval.between(toLocalDateTime, fromInstant);
        SignedInterval signedInterval7 = SignedInterval.between(toLocalDateTime, fromOffsetDateTime);
        SignedInterval signedInterval8 = SignedInterval.between(toLocalDateTime, fromZonedDateTime);
        System.out.println(signedInterval1);
        System.out.println(signedInterval2);
        System.out.println(signedInterval3);
        System.out.println(signedInterval4);
        System.out.println(signedInterval5);
        System.out.println(signedInterval6);
        System.out.println(signedInterval7);
        System.out.println(signedInterval8);
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", signedInterval1.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", signedInterval2.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", signedInterval3.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", signedInterval4.toString());
        assertEquals("-1世纪-17年-1月-24天-16时-36分-26秒-999毫秒-999微秒-911纳秒", signedInterval5.toString());
        assertEquals("-1世纪-17年-1月-24天-16时-36分-26秒-999毫秒-999微秒-911纳秒", signedInterval6.toString());
        assertEquals("-1世纪-17年-1月-24天-16时-36分-26秒-999毫秒-999微秒-911纳秒", signedInterval7.toString());
        assertEquals("-1世纪-17年-1月-24天-16时-36分-26秒-999毫秒-999微秒-911纳秒", signedInterval8.toString());
        System.out.println("======================================");
        Instant temporal1 = signedInterval1.minusFrom(toInstant);
        LocalDateTime temporal2 = signedInterval1.plusTo(fromLocalDateTime);
        Instant temporal3 = signedInterval6.minusFrom(fromInstant);
        LocalDateTime temporal4 = signedInterval6.plusTo(toLocalDateTime);
        System.out.println(G.dtSimple(temporal1));
        System.out.println(G.dtSimple(temporal2));
        System.out.println(G.dtSimple(temporal3));
        System.out.println(G.dtSimple(temporal4));
        assertEquals("1903-06-14 10:56:43.987", G.dtSimple(temporal1));
        assertEquals("2020-08-10 03:33:10.987", G.dtSimple(temporal2));
        assertEquals("2020-08-09 03:33:10.987", G.dtSimple(temporal3));
        assertEquals("1903-06-15 10:56:43.987", G.dtSimple(temporal4));
    }

    @Test
    public void testSignedInterval3() {
        ZoneOffset zoneOffset = ZoneOffset.ofHours(8);
        LocalDateTime fromLocalDateTime = LocalDateTime.of(1903, 2, 15, 10, 56, 43, 987656789);
        Instant fromInstant = fromLocalDateTime.toInstant(zoneOffset);
        OffsetDateTime fromOffsetDateTime = OffsetDateTime.ofInstant(fromInstant, TZ.NEW_YORK);
        ZonedDateTime fromZonedDateTime = ZonedDateTime.ofInstant(fromInstant, TZ.NEW_YORK);
        // -----------------------------------------------------------------------------
        LocalDateTime toLocalDateTime = LocalDateTime.of(2020, 4, 10, 3, 33, 10, 987656700);
        Instant toInstant = toLocalDateTime.toInstant(zoneOffset);
        OffsetDateTime toOffsetDateTime = OffsetDateTime.ofInstant(toInstant, TZ.NEW_YORK);
        ZonedDateTime toZonedDateTime = ZonedDateTime.ofInstant(toInstant, TZ.NEW_YORK);
        // -----------------------------------------------------------------------------
        SignedInterval signedInterval1 = SignedInterval.between(fromLocalDateTime, toLocalDateTime);
        SignedInterval signedInterval2 = SignedInterval.between(fromLocalDateTime, toInstant);
        SignedInterval signedInterval3 = SignedInterval.between(fromLocalDateTime, toOffsetDateTime);
        SignedInterval signedInterval4 = SignedInterval.between(fromLocalDateTime, toZonedDateTime);
        SignedInterval signedInterval5 = SignedInterval.between(toLocalDateTime, fromLocalDateTime);
        SignedInterval signedInterval6 = SignedInterval.between(toLocalDateTime, fromInstant);
        SignedInterval signedInterval7 = SignedInterval.between(toLocalDateTime, fromOffsetDateTime);
        SignedInterval signedInterval8 = SignedInterval.between(toLocalDateTime, fromZonedDateTime);
        System.out.println(signedInterval1);
        System.out.println(signedInterval2);
        System.out.println(signedInterval3);
        System.out.println(signedInterval4);
        System.out.println(signedInterval5);
        System.out.println(signedInterval6);
        System.out.println(signedInterval7);
        System.out.println(signedInterval8);
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", signedInterval1.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", signedInterval2.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", signedInterval3.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", signedInterval4.toString());
        assertEquals("-1世纪-17年-1月-22天-16时-36分-26秒-999毫秒-999微秒-911纳秒", signedInterval5.toString());
        assertEquals("-1世纪-17年-1月-22天-16时-36分-26秒-999毫秒-999微秒-911纳秒", signedInterval6.toString());
        assertEquals("-1世纪-17年-1月-22天-16时-36分-26秒-999毫秒-999微秒-911纳秒", signedInterval7.toString());
        assertEquals("-1世纪-17年-1月-22天-16时-36分-26秒-999毫秒-999微秒-911纳秒", signedInterval8.toString());
        System.out.println("======================================");
        Instant temporal1 = signedInterval1.minusFrom(toInstant);
        LocalDateTime temporal2 = signedInterval1.plusTo(fromLocalDateTime);
        Instant temporal3 = signedInterval6.minusFrom(fromInstant);
        LocalDateTime temporal4 = signedInterval6.plusTo(toLocalDateTime);
        System.out.println(G.dtSimple(temporal1));
        System.out.println(G.dtSimple(temporal2));
        System.out.println(G.dtSimple(temporal3));
        System.out.println(G.dtSimple(temporal4));
        assertEquals("1903-02-12 10:56:43.987", G.dtSimple(temporal1));
        assertEquals("2020-04-10 03:33:10.987", G.dtSimple(temporal2));
        assertEquals("2020-04-07 03:33:10.987", G.dtSimple(temporal3));
        assertEquals("1903-02-15 10:56:43.987", G.dtSimple(temporal4));
    }

    @Test
    public void testInterval2() {
        ZoneOffset zoneOffset = ZoneOffset.ofHours(8);
        LocalDateTime fromLocalDateTime = LocalDateTime.of(1903, 6, 15, 10, 56, 43, 987656789);
        Instant fromInstant = fromLocalDateTime.toInstant(zoneOffset);
        OffsetDateTime fromOffsetDateTime = OffsetDateTime.ofInstant(fromInstant, TZ.NEW_YORK);
        ZonedDateTime fromZonedDateTime = ZonedDateTime.ofInstant(fromInstant, TZ.NEW_YORK);
        // -----------------------------------------------------------------------------
        LocalDateTime toLocalDateTime = LocalDateTime.of(2020, 8, 10, 3, 33, 10, 987656700);
        Instant toInstant = toLocalDateTime.toInstant(zoneOffset);
        OffsetDateTime toOffsetDateTime = OffsetDateTime.ofInstant(toInstant, TZ.NEW_YORK);
        ZonedDateTime toZonedDateTime = ZonedDateTime.ofInstant(toInstant, TZ.NEW_YORK);
        // -----------------------------------------------------------------------------
        Interval interval1 = Interval.between(fromLocalDateTime, toLocalDateTime);
        Interval interval2 = Interval.between(fromLocalDateTime, toInstant);
        Interval interval3 = Interval.between(fromLocalDateTime, toOffsetDateTime);
        Interval interval4 = Interval.between(fromLocalDateTime, toZonedDateTime);
        Interval interval5 = Interval.between(toLocalDateTime, fromLocalDateTime);
        Interval interval6 = Interval.between(toLocalDateTime, fromInstant);
        Interval interval7 = Interval.between(toLocalDateTime, fromOffsetDateTime);
        Interval interval8 = Interval.between(toLocalDateTime, fromZonedDateTime);
        System.out.println(interval1);
        System.out.println(interval2);
        System.out.println(interval3);
        System.out.println(interval4);
        System.out.println(interval5);
        System.out.println(interval6);
        System.out.println(interval7);
        System.out.println(interval8);
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", interval1.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", interval2.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", interval3.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", interval4.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", interval5.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", interval6.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", interval7.toString());
        assertEquals("1世纪17年1月25天17时36分26秒999毫秒999微秒911纳秒", interval8.toString());
        System.out.println("======================================");
        Instant temporal1 = interval1.minusFrom(toInstant);
        LocalDateTime temporal2 = interval1.plusTo(fromLocalDateTime);
        Instant temporal3 = interval6.minusFrom(fromInstant);
        LocalDateTime temporal4 = interval6.plusTo(toLocalDateTime);
        System.out.println(G.dtSimple(temporal1));
        System.out.println(G.dtSimple(temporal2));
        System.out.println(G.dtSimple(temporal3));
        System.out.println(G.dtSimple(temporal4));
        assertEquals("1903-06-15 10:56:43.987", G.dtSimple(temporal1));
        assertEquals("2020-08-10 03:33:10.987", G.dtSimple(temporal2));
        assertEquals("1786-04-20 18:20:16.987", G.dtSimple(temporal3));
        assertEquals("2137-10-05 20:09:37.987", G.dtSimple(temporal4));
    }

    @Test
    public void testInterval3() {
        ZoneOffset zoneOffset = ZoneOffset.ofHours(8);
        LocalDateTime fromLocalDateTime = LocalDateTime.of(1903, 2, 15, 10, 56, 43, 987656789);
        Instant fromInstant = fromLocalDateTime.toInstant(zoneOffset);
        OffsetDateTime fromOffsetDateTime = OffsetDateTime.ofInstant(fromInstant, TZ.NEW_YORK);
        ZonedDateTime fromZonedDateTime = ZonedDateTime.ofInstant(fromInstant, TZ.NEW_YORK);
        // -----------------------------------------------------------------------------
        LocalDateTime toLocalDateTime = LocalDateTime.of(2020, 4, 10, 3, 33, 10, 987656700);
        Instant toInstant = toLocalDateTime.toInstant(zoneOffset);
        OffsetDateTime toOffsetDateTime = OffsetDateTime.ofInstant(toInstant, TZ.NEW_YORK);
        ZonedDateTime toZonedDateTime = ZonedDateTime.ofInstant(toInstant, TZ.NEW_YORK);
        // -----------------------------------------------------------------------------
        Interval interval1 = Interval.between(fromLocalDateTime, toLocalDateTime);
        Interval interval2 = Interval.between(fromLocalDateTime, toInstant);
        Interval interval3 = Interval.between(fromLocalDateTime, toOffsetDateTime);
        Interval interval4 = Interval.between(fromLocalDateTime, toZonedDateTime);
        Interval interval5 = Interval.between(toLocalDateTime, fromLocalDateTime);
        Interval interval6 = Interval.between(toLocalDateTime, fromInstant);
        Interval interval7 = Interval.between(toLocalDateTime, fromOffsetDateTime);
        Interval interval8 = Interval.between(toLocalDateTime, fromZonedDateTime);
        System.out.println(interval1);
        System.out.println(interval2);
        System.out.println(interval3);
        System.out.println(interval4);
        System.out.println(interval5);
        System.out.println(interval6);
        System.out.println(interval7);
        System.out.println(interval8);
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", interval1.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", interval2.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", interval3.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", interval4.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", interval5.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", interval6.toString());
        assertEquals("1世纪17年1月25天16时36分26秒999毫秒999微秒911纳秒", interval7.toString());
        assertEquals("1世纪17年1月25天17时36分26秒999毫秒999微秒911纳秒", interval8.toString());
        System.out.println("======================================");
        Instant temporal1 = interval1.minusFrom(toInstant);
        LocalDateTime temporal2 = interval1.plusTo(fromLocalDateTime);
        Instant temporal3 = interval6.minusFrom(fromInstant);
        LocalDateTime temporal4 = interval6.plusTo(toLocalDateTime);
        System.out.println(G.dtSimple(temporal1));
        System.out.println(G.dtSimple(temporal2));
        System.out.println(G.dtSimple(temporal3));
        System.out.println(G.dtSimple(temporal4));
        assertEquals("1903-02-15 10:56:43.987", G.dtSimple(temporal1));
        assertEquals("2020-04-10 03:33:10.987", G.dtSimple(temporal2));
        assertEquals("1785-12-20 18:20:16.987", G.dtSimple(temporal3));
        assertEquals("2137-06-04 20:09:37.987", G.dtSimple(temporal4));
    }

    @Test
    public void testDateTimeInterval() {
        LocalDateTime ldt1 = LocalDateTime.of(2020, 8, 10, 3, 33, 10, 987656700);
        Instant instant1 = ldt1.toInstant(ZoneOffset.ofHours(8));
        Date date1 = Date.from(instant1);
        Calendar calendar1 = DateTime.of(instant1).toCalendar(TZ.MOSCOW);

        LocalDateTime ldt2 = LocalDateTime.of(2022, 6, 10, 3, 33, 10, 896500);
        Instant instant2 = ldt2.toInstant(ZoneOffset.ofHours(8));
        OffsetDateTime odt2 = OffsetDateTime.ofInstant(instant2, ZoneOffset.ofHours(4));
        ZonedDateTime zdt2 = ZonedDateTime.ofInstant(instant2, TZ.NEW_YORK);
        Calendar calendar2 = DateTime.of(zdt2).toCalendar(null);

        System.out.println("ldt1: " + G.dtDetail(ldt1));           // 2020-08-10 03:33:10.987656700 [周一]
        System.out.println("instant1: " + G.dtDetail(instant1));       // 2020-08-10 03:33:10.987656700 [Asia/Shanghai +08:00 GMT+8 周一]
        System.out.println("date1: " + G.dtDetail(date1));          // 2020-08-10 03:33:10.987000000 [Asia/Shanghai +08:00 GMT+8 周一]
        System.out.println("calendar1: " + G.dtDetail(calendar1));      // 2020-08-09 22:33:10.987000000 [Europe/Moscow +03:00 GMT+3 周日]
        System.out.println("ldt2: " + G.dtDetail(ldt2));           // 2022-06-10 03:33:10.000896500 [周五]
        System.out.println("instant2: " + G.dtDetail(instant2));       // 2022-06-10 03:33:10.000896500 [Asia/Shanghai +08:00 GMT+8 周五]
        System.out.println("odt2: " + G.dtDetail(odt2));           // 2022-06-09 23:33:10.000896500 [+04:00 GMT+4 周四]
        System.out.println("zdt2: " + G.dtDetail(zdt2));           // 2022-06-09 15:33:10.000896500 [America/New_York -04:00 GMT-4 周四]
        System.out.println("calendar2: " + G.dtDetail(calendar2));      // 2022-06-09 15:33:10.000000000 [America/New_York -04:00 GMT-4 周四]

        SignedInterval between1 = SignedInterval.between(DateTime.of(ldt1), DateTime.of(odt2));
        SignedInterval between2 = SignedInterval.between(DateTime.of(zdt2), DateTime.of(calendar1));
        Interval between3 = Interval.between(DateTime.of(date1), DateTime.of(calendar2));
        Interval between4 = Interval.between(DateTime.of(instant2), DateTime.of(calendar1));

        System.out.println(between1.toString());    // 1年9月30天23时59分59秒13毫秒239微秒800纳秒
        System.out.println(between2.toString());    // -1年-9月-30天-23时-59分-59秒-13毫秒-896微秒-500纳秒
        System.out.println(between3.toString());    // 1年9月30天23时59分59秒13毫秒
        System.out.println(between4.toString());    // 1年9月30天23时59分59秒13毫秒896微秒500纳秒

        DateTime<LocalDateTime> dt1 = between1.addTo(DateTime.of(ldt1));
        DateTime<Calendar> dt2 = between2.subtractFrom(DateTime.of(calendar1));
        DateTime<Date> dt3 = between3.addTo(DateTime.of(date1));
        DateTime<Instant> dt4 = between4.subtractFrom(DateTime.of(instant2));
        System.out.println(dt1.dtDetail());     // 2022-06-10 03:33:10.000896500 [周五]
        System.out.println(dt2.dtDetail());     // 2022-06-09 22:33:10.000000000 [Europe/Moscow +03:00 GMT+3 周四]
        System.out.println(dt3.dtDetail());     // 2022-06-10 03:33:10.000000000 [Asia/Shanghai +08:00 GMT+8 周五]
        System.out.println(dt4.dtDetail());     // 2020-08-10 03:33:10.987000000 [Asia/Shanghai +08:00 GMT+8 周一]

        assertEquals(between1.toString(), "1年9月30天23时59分59秒13毫秒239微秒800纳秒");
        assertEquals(between2.toString(), "-1年-9月-30天-23时-59分-59秒-13毫秒-896微秒-500纳秒");
        assertEquals(between3.toString(), "1年9月30天23时59分59秒13毫秒");
        assertEquals(between4.toString(), "1年9月30天23时59分59秒13毫秒896微秒500纳秒");
        assertEquals(dt1.dtDetail(), "2022-06-10 03:33:10.000896500 [周五]");
        assertEquals(dt2.dtDetail(), "2022-06-09 22:33:10.000000000 [Europe/Moscow +03:00 GMT+3 周四]");
        assertEquals(dt3.dtDetail(), "2022-06-10 03:33:10.000000000 [Asia/Shanghai +08:00 GMT+8 周五]");
        assertEquals(dt4.dtDetail(), "2020-08-10 03:33:10.987000000 [Asia/Shanghai +08:00 GMT+8 周一]");
    }

    @Test
    public void testInterval4() {
        ZonedDateTime fromZDT = LocalDateTime.of(2022, 2, 10, 3, 0).atZone(TZ.NEW_YORK);
        ZonedDateTime toZDT = LocalDateTime.of(2022, 6, 10, 3, 0).atZone(TZ.NEW_YORK);
        /*
         * dateDT
         * datetime: 1900-01-01 00:00:00.000+0800 （这里的+0800是当前默认时区的偏移量，不是 当时默认时区的偏移量，当时偏移量应为：+08:05:43），这应该是 date 的bug。
         * localDateTime: 1900-01-01 00:05:43.000
         */
        DateTime<Date> dateDT = DateTime.of(Try.tcf(() -> new SimpleDateFormat("yyyy-MM-dd").parse("1900-1-1"), false));
        /*
         * fromDT1
         * datetime: 1899-12-31 23:54:17.000+0800 （这里的+0800是当前默认时区的偏移量，不是 当时默认时区的偏移量，当时偏移量应为：+08:05:43），这应该是 date 的bug。
         * localDateTime: 1900-01-01 00:00:00.000
         */
        DateTime<Date> fromDT1 = DateTime.parseDate("1900-1-1");
        DateTime<ZonedDateTime> toDT1 = DateTime.of(LocalDateTime.of(2009, 7, 6, 0, 0).atZone(TZ.DEFAULT_ZONE));
        OffsetDateTime fromODT2 = LocalDateTime.of(1900, 1, 1, 0, 0).atOffset(ZoneOffset.of("+08:05"));
        ZonedDateTime toZDT2 = LocalDateTime.of(2009, 7, 6, 0, 0).atZone(TZ.DEFAULT_ZONE);

        LocalDateTime fromLocalDateTime = LocalDateTime.of(1903, 2, 15, 10, 56, 43, 987656789);
        Instant fromInstant = fromLocalDateTime.toInstant(ZoneOffset.ofHours(8));
        ZonedDateTime fromZonedDateTime = ZonedDateTime.ofInstant(fromInstant, TZ.NEW_YORK);
        LocalDateTime toLocalDateTime = LocalDateTime.of(2020, 4, 10, 3, 33, 10, 987656700);

        System.out.println("fromZDT: " + fromZDT);              // fromZDT: 2022-02-10T03:00-05:00[America/New_York]
        System.out.println("toZDT: " + toZDT);                  // toZDT: 2022-06-10T03:00-04:00[America/New_York]
        System.out.println("fromDT1: " + fromDT1.dtDetail());   // fromDT1: 1900-01-01T00:00+08:05:43[Asia/Shanghai]
        System.out.println("toDT1: " + toDT1);                  // toDT1: 2009-07-06T00:00+08:00[Asia/Shanghai]
        System.out.println("fromODT2: " + fromODT2);            // fromODT2: 1900-01-01T00:00+08:05
        System.out.println("toZDT2: " + toZDT2);                // toZDT2: 2009-07-06T00:00+08:00[Asia/Shanghai]
        System.out.println("fromZonedDateTime: " + fromZonedDateTime);      // fromZonedDateTime: 1903-02-14T21:56:43.987656789-05:00[America/New_York]
        System.out.println("toLocalDateTime: " + toLocalDateTime);          // toLocalDateTime: 2020-04-10T03:33:10.987656700
        System.out.println("===================================================");
        Interval interval1 = Interval.between(fromZDT, toZDT);
        Interval interval2 = Interval.between(fromDT1, toDT1);
        Interval interval3 = Interval.between(toZDT2, fromODT2);
        Interval interval4 = Interval.between(toLocalDateTime, fromZonedDateTime);

        System.out.println("fromZDT - toZDT: " + interval1);        // fromZDT - toZDT: 4月0天0时0分0秒0毫秒
        System.out.println("fromDT1 - toDT1: " + interval2);        // fromDT1 - toDT1: 1世纪9年6月5天0时0分0秒0毫秒
        System.out.println("toZDT2 - fromODT2: " + interval3);      // toZDT2 - fromODT2: 1世纪9年6月5天0时5分0秒0毫秒
        System.out.println("toLocalDateTime - fromZonedDateTime: " + interval4);    // toLocalDateTime - fromZonedDateTime: 1世纪17年1月25天17时36分26秒999毫秒999微秒911纳秒

        assertEquals("4月0天0时0分0秒0毫秒", interval1.toString());
        assertEquals("1世纪9年6月5天0时0分0秒0毫秒", interval2.toString());
        assertEquals("1世纪9年6月5天0时5分0秒0毫秒", interval3.toString());
        assertEquals("1世纪17年1月25天17时36分26秒999毫秒999微秒911纳秒", interval4.toString());
        System.out.println("===================================================");

        Temporal toDate1 = interval1.addTo(fromZDT);
        DateTime<Date> toDate2 = interval2.addTo(fromDT1);
        Temporal toDate3 = interval3.addTo(fromODT2);
        Temporal toDate4 = interval4.addTo(fromZonedDateTime);
        Temporal fromDate1 = interval1.subtractFrom(toZDT);
        DateTime<ZonedDateTime> fromDate2 = interval2.subtractFrom(toDT1);
        Temporal fromDate3 = interval3.subtractFrom(toZDT2);
        Temporal fromDate4 = interval4.subtractFrom(toLocalDateTime);

        System.out.println("toDate1: " + toDate1);  // toDate1: 2022-06-10T03:00-04:00[America/New_York]
        System.out.println("toDate2: " + toDate2);  // toDate2: 2009-07-06 00:00:00.000
        System.out.println("toDate3: " + toDate3);  // toDate3: 2009-07-06T00:05+08:05
        System.out.println("toDate4: " + toDate4);  // toDate4: 2020-04-09T15:33:10.987656700-04:00[America/New_York]
        System.out.println("fromDate1: " + fromDate1);  // fromDate1: 2022-02-10T03:00-05:00[America/New_York]
        System.out.println("fromDate2: " + fromDate2);  // fromDate2: 1900-01-01 00:00:00.000
        System.out.println("fromDate3: " + fromDate3);  // fromDate3: 1899-12-30T23:55+08:05:43[Asia/Shanghai]
        System.out.println("fromDate4: " + fromDate4);  // fromDate4: 1903-02-15T09:56:43.987656789

        assertEquals("2022-06-10T03:00-04:00[America/New_York]", toDate1.toString());
        assertEquals("2009-07-06 00:00:00.000", toDate2.toString());
        assertEquals("2009-07-06T00:05+08:05", toDate3.toString());
        assertEquals("2020-04-09T15:33:10.987656700-04:00[America/New_York]", toDate4.toString());
        assertEquals("2022-02-10T03:00-05:00[America/New_York]", fromDate1.toString());
        assertEquals("1900-01-01 00:00:00.000", fromDate2.toString());
        assertEquals("1899-12-30T23:55+08:05:43[Asia/Shanghai]", fromDate3.toString());
        assertEquals("1903-02-15T09:56:43.987656789", fromDate4.toString());


    }

    @Test
    public void testSignedIntervalPlusMinus() {
        SignedInterval signedInterval01 = SignedInterval.of(3, ChronoUnit.DAYS);
        SignedInterval signedInterval02 = SignedInterval.of(-2, ChronoUnit.DAYS);
        SignedInterval signedInterval03 = SignedInterval.ofMonths(2);
        SignedInterval signedInterval04 = SignedInterval.ofMonths(-5);
        SignedInterval signedInterval05 = signedInterval01.plus(signedInterval03);
        SignedInterval signedInterval06 = signedInterval01.plus(signedInterval04);
        SignedInterval signedInterval07 = signedInterval02.plus(5, ChronoUnit.DAYS);
        SignedInterval signedInterval08 = signedInterval03.plus(-5, ChronoUnit.MONTHS);
        SignedInterval signedInterval09 = signedInterval04.plus(2, ChronoUnit.YEARS);
        SignedInterval signedInterval10 = signedInterval04.plus(6, ChronoUnit.MONTHS);
        SignedInterval signedInterval11 = signedInterval04.plus(2, ChronoUnit.MONTHS);
        SignedInterval signedInterval12 = signedInterval03.minus(5, ChronoUnit.MONTHS);
        SignedInterval signedInterval13 = signedInterval04.minus(-2, ChronoUnit.YEARS);
        SignedInterval signedInterval14 = signedInterval04.minus(-6, ChronoUnit.MONTHS);
        SignedInterval signedInterval15 = signedInterval04.minus(-2, ChronoUnit.MONTHS);

        assertEquals("3天0时0分0秒0毫秒", signedInterval01.toString());
        assertEquals("-2天0时0分0秒0毫秒", signedInterval02.toString());
        assertEquals("2月0天0时0分0秒0毫秒", signedInterval03.toString());
        assertEquals("-5月0天0时0分0秒0毫秒", signedInterval04.toString());
        assertEquals("2月3天0时0分0秒0毫秒", signedInterval05.toString());
        assertEquals("-5月3天0时0分0秒0毫秒", signedInterval06.toString());
        assertEquals("3天0时0分0秒0毫秒", signedInterval07.toString());
        assertEquals("-3月0天0时0分0秒0毫秒", signedInterval08.toString());
        assertEquals("2年-5月0天0时0分0秒0毫秒", signedInterval09.toString());
        assertEquals("1月0天0时0分0秒0毫秒", signedInterval10.toString());
        assertEquals("-3月0天0时0分0秒0毫秒", signedInterval11.toString());
        assertEquals("-3月0天0时0分0秒0毫秒", signedInterval12.toString());
        assertEquals("2年-5月0天0时0分0秒0毫秒", signedInterval13.toString());
        assertEquals("1月0天0时0分0秒0毫秒", signedInterval14.toString());
        assertEquals("-3月0天0时0分0秒0毫秒", signedInterval15.toString());

        assertEquals("3天", signedInterval01.toSimpleString());
        assertEquals("-2天", signedInterval02.toSimpleString());
        assertEquals("2月", signedInterval03.toSimpleString());
        assertEquals("-5月", signedInterval04.toSimpleString());
        assertEquals("2月3天", signedInterval05.toSimpleString());
        assertEquals("-5月3天", signedInterval06.toSimpleString());
        assertEquals("3天", signedInterval07.toSimpleString());
        assertEquals("-3月", signedInterval08.toSimpleString());
        assertEquals("2年-5月", signedInterval09.toSimpleString());
        assertEquals("1月", signedInterval10.toSimpleString());
        assertEquals("-3月", signedInterval11.toSimpleString());
        assertEquals("-3月", signedInterval12.toSimpleString());
        assertEquals("2年-5月", signedInterval13.toSimpleString());
        assertEquals("1月", signedInterval14.toSimpleString());
        assertEquals("-3月", signedInterval15.toSimpleString());

        System.out.println("signedInterval01: " + signedInterval01);
        System.out.println("signedInterval02: " + signedInterval02);
        System.out.println("signedInterval03: " + signedInterval03);
        System.out.println("signedInterval04: " + signedInterval04);
        System.out.println("signedInterval05: " + signedInterval05);
        System.out.println("signedInterval06: " + signedInterval06);
        System.out.println("signedInterval07: " + signedInterval07);
        System.out.println("signedInterval08: " + signedInterval08);
        System.out.println("signedInterval09: " + signedInterval09);
        System.out.println("signedInterval10: " + signedInterval10);
        System.out.println("signedInterval11: " + signedInterval11);
        System.out.println("signedInterval12: " + signedInterval12);
        System.out.println("signedInterval13: " + signedInterval13);
        System.out.println("signedInterval14: " + signedInterval14);
        System.out.println("signedInterval15: " + signedInterval15);
    }

    @Test
    public void testIntervalPlusMinus() {
        Interval interval01 = Interval.of(3, ChronoUnit.DAYS);
        // Interval interval02 = Interval.of(-2, ChronoUnit.DAYS);
        Interval interval03 = Interval.ofMonths(2);
        Interval interval04 = Interval.ofMonths(5);
        Interval interval05 = interval01.plus(interval03);
        Interval interval06 = interval01.plus(interval04);
        // Interval interval07 = interval02.plus(5, ChronoUnit.DAYS);
        // Interval interval08 = interval03.plus(-5, ChronoUnit.MONTHS);
        Interval interval09 = interval04.plus(2, ChronoUnit.YEARS);
        Interval interval10 = interval04.plus(6, ChronoUnit.MONTHS);
        Interval interval11 = interval04.plus(-2, ChronoUnit.MONTHS);
        Interval interval12 = interval03.minus(-5, ChronoUnit.MONTHS);
        // Interval interval13 = interval04.minus(2, ChronoUnit.YEARS);
        // Interval interval14 = interval04.minus(6, ChronoUnit.MONTHS);
        Interval interval15 = interval04.minus(2, ChronoUnit.MONTHS);

        assertEquals("3天0时0分0秒0毫秒", interval01.toString());
        assertEquals("2月0天0时0分0秒0毫秒", interval03.toString());
        assertEquals("5月0天0时0分0秒0毫秒", interval04.toString());
        assertEquals("2月3天0时0分0秒0毫秒", interval05.toString());
        assertEquals("5月3天0时0分0秒0毫秒", interval06.toString());
        assertEquals("2年5月0天0时0分0秒0毫秒", interval09.toString());
        assertEquals("11月0天0时0分0秒0毫秒", interval10.toString());
        assertEquals("3月0天0时0分0秒0毫秒", interval11.toString());
        assertEquals("7月0天0时0分0秒0毫秒", interval12.toString());
        assertEquals("3月0天0时0分0秒0毫秒", interval15.toString());

        assertEquals("3天", interval01.toSimpleString());
        assertEquals("2月", interval03.toSimpleString());
        assertEquals("5月", interval04.toSimpleString());
        assertEquals("2月3天", interval05.toSimpleString());
        assertEquals("5月3天", interval06.toSimpleString());
        assertEquals("2年5月", interval09.toSimpleString());
        assertEquals("11月", interval10.toSimpleString());
        assertEquals("3月", interval11.toSimpleString());
        assertEquals("7月", interval12.toSimpleString());
        assertEquals("3月", interval15.toSimpleString());

        System.out.println("interval01: " + interval01);
        // System.out.println("interval02: " + interval02);
        System.out.println("interval03: " + interval03);
        System.out.println("interval04: " + interval04);
        System.out.println("interval05: " + interval05);
        System.out.println("interval06: " + interval06);
        // System.out.println("interval07: " + interval07);
        // System.out.println("interval08: " + interval08);
        System.out.println("interval09: " + interval09);
        System.out.println("interval10: " + interval10);
        System.out.println("interval11: " + interval11);
        System.out.println("interval12: " + interval12);
        // System.out.println("interval13: " + interval13);
        // System.out.println("interval14: " + interval14);
        System.out.println("interval15: " + interval15);
    }

    @Test
    public void testDatePlusMinus() {
        SignedInterval signedInterval1 = SignedInterval.ofDays(5);
        SignedInterval signedInterval2 = SignedInterval.ofDays(-5);
        Interval interval = Interval.ofDays(5);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = Try.tcf(() -> sdf.parse("2022-03-02 10:00:00"), false);
        Calendar calendar = DateTime.of(date).toDefaultCalendar();

        Date date1 = signedInterval1.addTo(date);                       // +5
        Date date2 = signedInterval2.addTo(date);                       // +(-5)
        Date date3 = interval.addTo(date);                              // +5
        Date date4 = signedInterval1.subtractFrom(date);                // -5
        Date date5 = signedInterval2.subtractFrom(date);                // -(-5)
        Date date6 = interval.subtractFrom(date);                       // -5
        Calendar calendar1 = signedInterval1.addTo(calendar);           // +5
        Calendar calendar2 = signedInterval2.addTo(calendar);           // +(-5)
        Calendar calendar3 = interval.addTo(calendar);                  // +5
        Calendar calendar4 = signedInterval1.subtractFrom(calendar);    // -5
        Calendar calendar5 = signedInterval2.subtractFrom(calendar);    // -(-5)
        Calendar calendar6 = interval.subtractFrom(calendar);           // -5

        assertEquals("2022-03-07 10:00:00.000", G.dtSimple(date1));
        assertEquals("2022-02-25 10:00:00.000", G.dtSimple(date2));
        assertEquals("2022-03-07 10:00:00.000", G.dtSimple(date3));
        assertEquals("2022-02-25 10:00:00.000", G.dtSimple(date4));
        assertEquals("2022-03-07 10:00:00.000", G.dtSimple(date5));
        assertEquals("2022-02-25 10:00:00.000", G.dtSimple(date6));
        assertEquals("2022-03-07 10:00:00.000", G.dtSimple(calendar1));
        assertEquals("2022-02-25 10:00:00.000", G.dtSimple(calendar2));
        assertEquals("2022-03-07 10:00:00.000", G.dtSimple(calendar3));
        assertEquals("2022-02-25 10:00:00.000", G.dtSimple(calendar4));
        assertEquals("2022-03-07 10:00:00.000", G.dtSimple(calendar5));
        assertEquals("2022-02-25 10:00:00.000", G.dtSimple(calendar6));
        // System.out.println("2022-03-02 10:00:00 *** +5: " + G.dtSimple(date1));
        // System.out.println("2022-03-02 10:00:00 *** +(-5): " + G.dtSimple(date2));
        // System.out.println("2022-03-02 10:00:00 *** +5: " + G.dtSimple(date3));
        // System.out.println("2022-03-02 10:00:00 *** -5: " + G.dtSimple(date4));
        // System.out.println("2022-03-02 10:00:00 *** -(-5): " + G.dtSimple(date5));
        // System.out.println("2022-03-02 10:00:00 *** -5: " + G.dtSimple(date6));
        // System.out.println("2022-03-02 10:00:00 *** +5: " + G.dtSimple(calendar1));
        // System.out.println("2022-03-02 10:00:00 *** +(-5): " + G.dtSimple(calendar2));
        // System.out.println("2022-03-02 10:00:00 *** +5: " + G.dtSimple(calendar3));
        // System.out.println("2022-03-02 10:00:00 *** -5: " + G.dtSimple(calendar4));
        // System.out.println("2022-03-02 10:00:00 *** -(-5): " + G.dtSimple(calendar5));
        // System.out.println("2022-03-02 10:00:00 *** -5: " + G.dtSimple(calendar6));
    }

    @Test
    public void testIntervalPlus() {
        SignedInterval signedInterval = SignedInterval.of(0, 0, 1, -2, 530, -61);
        SignedInterval minusSI = signedInterval.minus(new SignedInterval(0, 0, 0, 2, -13, 2));
        Interval interval = Interval.of(0, 0, 1, -2, 530, -61);
        Interval minusInterval = interval.minus(new Interval(0, 0, 0, 2, -13, 2));
        System.out.println(signedInterval);
        System.out.println(minusSI);
        System.out.println(interval);
        System.out.println(minusInterval);
        assertEquals("1天6时48分59秒0毫秒", signedInterval.toString());
        assertEquals("1天5时1分57秒0毫秒", minusSI.toString());
        assertEquals("1天6时48分59秒0毫秒", interval.toString());
        assertEquals("1天5时1分57秒0毫秒", minusInterval.toString());
        assertThrows(UnexpectedParameterException.class, () -> Interval.ofHours(-8));
        assertThrows(UnexpectedParameterException.class, () -> Interval.of(-1, 10, 1));

    }


    @Test
    public void testToSimpleString() {
        SignedInterval signedInterval = SignedInterval.of(1, 0, 0, 1, -2, 530, 0);
        Interval interval = Interval.of(1, 0, 3, 1, -2, 0, 900);

        System.out.println(signedInterval.toSimpleString());
        System.out.println(interval.toSimpleString());

        assertEquals("1年1时6分50秒", signedInterval.toSimpleString());
        assertEquals("1年3天58分900毫秒", interval.toSimpleString());

    }
}
