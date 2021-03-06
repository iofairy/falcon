package com.iofairy.test;

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
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", signedInterval1.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", signedInterval2.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", signedInterval3.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", signedInterval4.toString());
        assertEquals("-1??????-17???-1???-24???-16???-36???-26???-999??????-999??????-911??????", signedInterval5.toString());
        assertEquals("-1??????-17???-1???-24???-16???-36???-26???-999??????-999??????-911??????", signedInterval6.toString());
        assertEquals("-1??????-17???-1???-24???-16???-36???-26???-999??????-999??????-911??????", signedInterval7.toString());
        assertEquals("-1??????-17???-1???-24???-16???-36???-26???-999??????-999??????-911??????", signedInterval8.toString());
        assertEquals("1??????17???1???25???16???36???27???0??????", signedInterval9.toString());
        assertEquals("1??????17???1???25???16???36???27???0??????", signedInterval10.toString());
        assertEquals("-1??????-17???-1???-24???-16???-36???-27???0??????", signedInterval11.toString());
        assertEquals("-1??????-17???-1???-24???-16???-36???-27???0??????", signedInterval12.toString());
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
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", signedInterval1.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", signedInterval2.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", signedInterval3.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", signedInterval4.toString());
        assertEquals("-1??????-17???-1???-22???-16???-36???-26???-999??????-999??????-911??????", signedInterval5.toString());
        assertEquals("-1??????-17???-1???-22???-16???-36???-26???-999??????-999??????-911??????", signedInterval6.toString());
        assertEquals("-1??????-17???-1???-22???-16???-36???-26???-999??????-999??????-911??????", signedInterval7.toString());
        assertEquals("-1??????-17???-1???-22???-16???-36???-26???-999??????-999??????-911??????", signedInterval8.toString());
        assertEquals("1??????17???1???25???16???36???27???0??????", signedInterval9.toString());
        assertEquals("1??????17???1???25???16???36???27???0??????", signedInterval10.toString());
        assertEquals("-1??????-17???-1???-22???-16???-36???-27???0??????", signedInterval11.toString());
        assertEquals("-1??????-17???-1???-22???-16???-36???-27???0??????", signedInterval12.toString());
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
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", interval1.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", interval2.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", interval3.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", interval4.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", interval5.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", interval6.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", interval7.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", interval8.toString());
        assertEquals("1??????17???1???25???16???36???27???0??????", interval9.toString());
        assertEquals("1??????17???1???25???16???36???27???0??????", interval10.toString());
        assertEquals("1??????17???1???25???16???36???27???0??????", interval11.toString());
        assertEquals("1??????17???1???25???16???36???27???0??????", interval12.toString());
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
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", interval1.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", interval2.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", interval3.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", interval4.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", interval5.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", interval6.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", interval7.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", interval8.toString());
        assertEquals("1??????17???1???25???16???36???27???0??????", interval9.toString());
        assertEquals("1??????17???1???25???16???36???27???0??????", interval10.toString());
        assertEquals("1??????17???1???25???16???36???27???0??????", interval11.toString());
        assertEquals("1??????17???1???25???16???36???27???0??????", interval12.toString());
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
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", signedInterval1.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", signedInterval2.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", signedInterval3.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", signedInterval4.toString());
        assertEquals("-1??????-17???-1???-24???-16???-36???-26???-999??????-999??????-911??????", signedInterval5.toString());
        assertEquals("-1??????-17???-1???-24???-16???-36???-26???-999??????-999??????-911??????", signedInterval6.toString());
        assertEquals("-1??????-17???-1???-24???-16???-36???-26???-999??????-999??????-911??????", signedInterval7.toString());
        assertEquals("-1??????-17???-1???-24???-16???-36???-26???-999??????-999??????-911??????", signedInterval8.toString());
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
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", signedInterval1.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", signedInterval2.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", signedInterval3.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", signedInterval4.toString());
        assertEquals("-1??????-17???-1???-22???-16???-36???-26???-999??????-999??????-911??????", signedInterval5.toString());
        assertEquals("-1??????-17???-1???-22???-16???-36???-26???-999??????-999??????-911??????", signedInterval6.toString());
        assertEquals("-1??????-17???-1???-22???-16???-36???-26???-999??????-999??????-911??????", signedInterval7.toString());
        assertEquals("-1??????-17???-1???-22???-16???-36???-26???-999??????-999??????-911??????", signedInterval8.toString());
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
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", interval1.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", interval2.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", interval3.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", interval4.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", interval5.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", interval6.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", interval7.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", interval8.toString());
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
        assertEquals("1786-04-20 18:25:59.987", G.dtSimple(temporal3));
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
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", interval1.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", interval2.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", interval3.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", interval4.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", interval5.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", interval6.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", interval7.toString());
        assertEquals("1??????17???1???25???16???36???26???999??????999??????911??????", interval8.toString());
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
        assertEquals("1785-12-20 18:25:59.987", G.dtSimple(temporal3));
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

        System.out.println("ldt1: " + G.dtDetail(ldt1));           // 2020-08-10 03:33:10.987656700 [??????]
        System.out.println("instant1: " + G.dtDetail(instant1));       // 2020-08-10 03:33:10.987656700 [Asia/Shanghai +08:00 GMT+8 ??????]
        System.out.println("date1: " + G.dtDetail(date1));          // 2020-08-10 03:33:10.987000000 [Asia/Shanghai +08:00 GMT+8 ??????]
        System.out.println("calendar1: " + G.dtDetail(calendar1));      // 2020-08-09 22:33:10.987000000 [Europe/Moscow +03:00 GMT+3 ??????]
        System.out.println("ldt2: " + G.dtDetail(ldt2));           // 2022-06-10 03:33:10.000896500 [??????]
        System.out.println("instant2: " + G.dtDetail(instant2));       // 2022-06-10 03:33:10.000896500 [Asia/Shanghai +08:00 GMT+8 ??????]
        System.out.println("odt2: " + G.dtDetail(odt2));           // 2022-06-09 23:33:10.000896500 [+04:00 GMT+4 ??????]
        System.out.println("zdt2: " + G.dtDetail(zdt2));           // 2022-06-09 15:33:10.000896500 [America/New_York -04:00 GMT-4 ??????]
        System.out.println("calendar2: " + G.dtDetail(calendar2));      // 2022-06-09 15:33:10.000000000 [America/New_York -04:00 GMT-4 ??????]

        SignedInterval between1 = SignedInterval.between(DateTime.of(ldt1), DateTime.of(odt2));
        SignedInterval between2 = SignedInterval.between(DateTime.of(zdt2), DateTime.of(calendar1));
        Interval between3 = Interval.between(DateTime.of(date1), DateTime.of(calendar2));
        Interval between4 = Interval.between(DateTime.of(instant2), DateTime.of(calendar1));

        System.out.println(between1.toString());    // 1???9???30???23???59???59???13??????239??????800??????
        System.out.println(between2.toString());    // -1???-9???-30???-23???-59???-59???-13??????-896??????-500??????
        System.out.println(between3.toString());    // 1???9???30???23???59???59???13??????
        System.out.println(between4.toString());    // 1???9???30???23???59???59???13??????896??????500??????

        DateTime<LocalDateTime> dt1 = between1.addTo(DateTime.of(ldt1));
        DateTime<Calendar> dt2 = between2.subtractFrom(DateTime.of(calendar1));
        DateTime<Date> dt3 = between3.addTo(DateTime.of(date1));
        DateTime<Instant> dt4 = between4.subtractFrom(DateTime.of(instant2));
        System.out.println(dt1.dtDetail());     // 2022-06-10 03:33:10.000896500 [??????]
        System.out.println(dt2.dtDetail());     // 2022-06-09 22:33:10.000000000 [Europe/Moscow +03:00 GMT+3 ??????]
        System.out.println(dt3.dtDetail());     // 2022-06-10 03:33:10.000000000 [Asia/Shanghai +08:00 GMT+8 ??????]
        System.out.println(dt4.dtDetail());     // 2020-08-10 03:33:10.987000000 [Asia/Shanghai +08:00 GMT+8 ??????]

        assertEquals(between1.toString(), "1???9???30???23???59???59???13??????239??????800??????");
        assertEquals(between2.toString(), "-1???-9???-30???-23???-59???-59???-13??????-896??????-500??????");
        assertEquals(between3.toString(), "1???9???30???23???59???59???13??????");
        assertEquals(between4.toString(), "1???9???30???23???59???59???13??????896??????500??????");
        assertEquals(dt1.dtDetail(), "2022-06-10 03:33:10.000896500 [??????]");
        assertEquals(dt2.dtDetail(), "2022-06-09 22:33:10.000000000 [Europe/Moscow +03:00 GMT+3 ??????]");
        assertEquals(dt3.dtDetail(), "2022-06-10 03:33:10.000000000 [Asia/Shanghai +08:00 GMT+8 ??????]");
        assertEquals(dt4.dtDetail(), "2020-08-10 03:33:10.987000000 [Asia/Shanghai +08:00 GMT+8 ??????]");
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

        assertEquals("3???0???0???0???0??????", signedInterval01.toString());
        assertEquals("-2???0???0???0???0??????", signedInterval02.toString());
        assertEquals("2???0???0???0???0???0??????", signedInterval03.toString());
        assertEquals("-5???0???0???0???0???0??????", signedInterval04.toString());
        assertEquals("2???3???0???0???0???0??????", signedInterval05.toString());
        assertEquals("-5???3???0???0???0???0??????", signedInterval06.toString());
        assertEquals("3???0???0???0???0??????", signedInterval07.toString());
        assertEquals("-3???0???0???0???0???0??????", signedInterval08.toString());
        assertEquals("2???-5???0???0???0???0???0??????", signedInterval09.toString());
        assertEquals("1???0???0???0???0???0??????", signedInterval10.toString());
        assertEquals("-3???0???0???0???0???0??????", signedInterval11.toString());
        assertEquals("-3???0???0???0???0???0??????", signedInterval12.toString());
        assertEquals("2???-5???0???0???0???0???0??????", signedInterval13.toString());
        assertEquals("1???0???0???0???0???0??????", signedInterval14.toString());
        assertEquals("-3???0???0???0???0???0??????", signedInterval15.toString());
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

        assertEquals("3???0???0???0???0??????", interval01.toString());
        assertEquals("2???0???0???0???0???0??????", interval03.toString());
        assertEquals("5???0???0???0???0???0??????", interval04.toString());
        assertEquals("2???3???0???0???0???0??????", interval05.toString());
        assertEquals("5???3???0???0???0???0??????", interval06.toString());
        assertEquals("2???5???0???0???0???0???0??????", interval09.toString());
        assertEquals("11???0???0???0???0???0??????", interval10.toString());
        assertEquals("3???0???0???0???0???0??????", interval11.toString());
        assertEquals("7???0???0???0???0???0??????", interval12.toString());
        assertEquals("3???0???0???0???0???0??????", interval15.toString());

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
        assertEquals("1???6???48???59???0??????", signedInterval.toString());
        assertEquals("1???5???1???57???0??????", minusSI.toString());
        assertEquals("1???6???48???59???0??????", interval.toString());
        assertEquals("1???5???1???57???0??????", minusInterval.toString());
    }
}
