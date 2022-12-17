package com.iofairy.test;

import com.iofairy.except.UnexpectedParameterException;
import com.iofairy.falcon.time.*;
import com.iofairy.top.G;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static com.iofairy.falcon.range.IntervalType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author GG
 * @version 1.0
 * @date 2022/7/24 21:57
 */
public class DateTimeTest {

    @Test
    public void testToDate() {
        LocalDateTime ldt = LocalDateTime.of(2022, 2, 27, 8, 0, 10, 100);
        ZonedDateTime zdt = ldt.atZone(TZ.DEFAULT_ZONE);

        ZonedDateTime zdt1 = zdt.withZoneSameInstant(TZ.MOSCOW);
        Instant instant1 = zdt1.toInstant();
        OffsetDateTime odt1 = zdt1.toOffsetDateTime();
        GregorianCalendar calendar1 = GregorianCalendar.from(zdt1);

        Date date = Date.from(zdt.toInstant());

        Date date01 = DateTime.of(zdt).toDate();
        Date date02 = DateTime.of(zdt1).toDate();
        Date date03 = DateTime.of(odt1).toDate();
        Date date04 = DateTime.of(calendar1).toDate();
        Date date05 = DateTime.of(instant1).toDate();
        System.out.println(G.dtSimple(date));   // 2022-02-27 08:00:10.000
        System.out.println(G.dtSimple(date01)); // 2022-02-27 08:00:10.000
        System.out.println(G.dtSimple(date02)); // 2022-02-27 08:00:10.000
        System.out.println(G.dtSimple(date03)); // 2022-02-27 08:00:10.000
        System.out.println(G.dtSimple(date04)); // 2022-02-27 08:00:10.000
        System.out.println(G.dtSimple(date05)); // 2022-02-27 08:00:10.000

        assertEquals("2022-02-27 08:00:10.000", G.dtSimple(date));
        assertEquals("2022-02-27 08:00:10.000", G.dtSimple(date01));
        assertEquals("2022-02-27 08:00:10.000", G.dtSimple(date02));
        assertEquals("2022-02-27 08:00:10.000", G.dtSimple(date03));
        assertEquals("2022-02-27 08:00:10.000", G.dtSimple(date04));
        assertEquals("2022-02-27 08:00:10.000", G.dtSimple(date05));

        // toDate 带时区的 toDate，已经删除，没有意义
        // Date date01 = DateTime.of(zdt).toDate(TZ.UTC);
        // Date date02 = DateTime.of(zdt1).toDate(TZ.UTC);
        // Date date03 = DateTime.of(odt1).toDate(TZ.UTC);
        // Date date04 = DateTime.of(calendar1).toDate(TZ.UTC);
        // Date date05 = DateTime.of(instant1).toDate(TZ.UTC);
        // Date date06 = DateTime.of(zdt).toDate(null);
        // Date date07 = DateTime.of(zdt1).toDate(null);
        // Date date08 = DateTime.of(odt1).toDate(null);
        // Date date09 = DateTime.of(calendar1).toDate(null);
        // Date date10 = DateTime.of(instant1).toDate(null);
        //
        // System.out.println(G.dtSimple(date));   // 2022-02-27 08:00:10.000
        // System.out.println(G.dtSimple(date01)); // 2022-02-27 08:00:10.000
        // System.out.println(G.dtSimple(date02)); // 2022-02-27 08:00:10.000
        // System.out.println(G.dtSimple(date03)); // 2022-02-27 08:00:10.000
        // System.out.println(G.dtSimple(date04)); // 2022-02-27 08:00:10.000
        // System.out.println(G.dtSimple(date05)); // 2022-02-27 08:00:10.000
        // System.out.println(G.dtSimple(date06)); // 2022-02-27 08:00:10.000
        // System.out.println(G.dtSimple(date07)); // 2022-02-27 08:00:10.000
        // System.out.println(G.dtSimple(date08)); // 2022-02-27 08:00:10.000
        // System.out.println(G.dtSimple(date09)); // 2022-02-27 08:00:10.000
        // System.out.println(G.dtSimple(date10)); // 2022-02-27 08:00:10.000

    }

    @Test
    public void testToOther() {
        LocalDateTime ldt = LocalDateTime.of(2022, 2, 27, 8, 0, 10, 100);
        ZonedDateTime zdt = ldt.atZone(TZ.DEFAULT_ZONE);

        ZonedDateTime zdt1 = zdt.withZoneSameInstant(TZ.MOSCOW);
        Instant instant1 = zdt1.toInstant();
        OffsetDateTime odt1 = zdt1.toOffsetDateTime().withOffsetSameInstant(ZoneOffset.UTC);
        GregorianCalendar calendar1 = (GregorianCalendar) Calendar.getInstance(TimeZone.getTimeZone(TZ.MOSCOW));
        calendar1.setTime(Date.from(zdt.toInstant()));
        Date date1 = calendar1.getTime();

        DateTime<LocalDateTime> dt1 = DateTime.of(ldt);
        DateTime<ZonedDateTime> dt2 = DateTime.of(zdt);
        DateTime<ZonedDateTime> dt3 = DateTime.of(zdt1);
        DateTime<Instant> dt4 = DateTime.of(instant1);
        DateTime<OffsetDateTime> dt5 = DateTime.of(odt1);
        DateTime<Calendar> dt6 = DateTime.of(calendar1);
        DateTime<Date> dt7 = DateTime.of(date1);

        System.out.println(dt1.dtDetail());     // 2022-02-27 08:00:10.000000100 [周日]
        System.out.println(dt2.dtDetail());     // 2022-02-27 08:00:10.000000100 [Asia/Shanghai +08:00 GMT+8 周日]
        System.out.println(dt3.dtDetail());     // 2022-02-27 03:00:10.000000100 [Europe/Moscow +03:00 GMT+3 周日]
        System.out.println(dt4.dtDetail());     // 2022-02-27 08:00:10.000000100 [Asia/Shanghai +08:00 GMT+8 周日]
        System.out.println(dt5.dtDetail());     // 2022-02-27 00:00:10.000000100 [+00:00 GMT 周日]
        System.out.println(dt6.dtDetail());     // 2022-02-27 03:00:10.000000000 [Europe/Moscow +03:00 GMT+3 周日]
        System.out.println(dt7.dtDetail());     // 2022-02-27 08:00:10.000000000 [Asia/Shanghai +08:00 GMT+8 周日]

        Calendar calendar01 = dt1.toDefaultCalendar();      // 2022-02-27 08:00:10.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        Calendar calendar02 = dt2.toDefaultCalendar();      // 2022-02-27 08:00:10.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        Calendar calendar03 = dt3.toUTCCalendar();      // 2022-02-27 00:00:10.000000000 [UTC +00:00 GMT 周日]
        Calendar calendar04 = dt4.toUTCCalendar();      // 2022-02-27 00:00:10.000000000 [UTC +00:00 GMT 周日]
        Calendar calendar05 = dt5.toCalendar(null);     // 2022-02-27 00:00:10.000000000 [UTC +00:00 GMT 周日]
        Calendar calendar06 = dt6.toCalendar(TZ.DUBAI);     // 2022-02-27 04:00:10.000000000 [Asia/Dubai +04:00 GMT+4 周日]
        LocalDateTime ldt01 = dt6.toLocalDateTime();        // 2022-02-27 03:00:10.000000000 [周日]
        LocalDateTime ldt02 = dt7.toLocalDateTime();        // 2022-02-27 08:00:10.000000000 [周日]
        LocalDateTime ldt03 = dt1.toLocalDateTime();        // 2022-02-27 08:00:10.000000100 [周日]
        LocalDateTime ldt04 = dt2.toLocalDateTime();        // 2022-02-27 08:00:10.000000100 [周日]
        LocalDateTime ldt05 = dt3.toLocalDateTime();        // 2022-02-27 03:00:10.000000100 [周日]
        LocalDateTime ldt06 = dt4.toLocalDateTime();        // 2022-02-27 08:00:10.000000100 [周日]
        Instant instant01 = dt5.toInstant();        // 2022-02-27 08:00:10.000000100 [Asia/Shanghai +08:00 GMT+8 周日]
        Instant instant02 = dt6.toInstant();        // 2022-02-27 08:00:10.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        Instant instant03 = dt7.toInstant();        // 2022-02-27 08:00:10.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        Instant instant04 = dt1.toInstant();        // 2022-02-27 08:00:10.000000100 [Asia/Shanghai +08:00 GMT+8 周日]
        ZonedDateTime zdt01 = dt1.toDefaultZonedDT();       // 2022-02-27 08:00:10.000000100 [Asia/Shanghai +08:00 GMT+8 周日]
        ZonedDateTime zdt02 = dt2.toDefaultZonedDT();       // 2022-02-27 08:00:10.000000100 [Asia/Shanghai +08:00 GMT+8 周日]
        ZonedDateTime zdt03 = dt3.toUTCZonedDT();       // 2022-02-27 00:00:10.000000100 [UTC +00:00 GMT 周日]
        ZonedDateTime zdt04 = dt4.toUTCZonedDT();       // 2022-02-27 00:00:10.000000100 [UTC +00:00 GMT 周日]
        ZonedDateTime zdt05 = dt5.toZonedDT(null);      // 2022-02-27 00:00:10.000000100 [Z +00:00 GMT 周日]
        ZonedDateTime zdt06 = dt6.toZonedDT(TZ.UTC);        // 2022-02-27 00:00:10.000000000 [UTC +00:00 GMT 周日]
        OffsetDateTime odt01 = dt7.toDefaultOffsetDT();     // 2022-02-27 08:00:10.000000000 [+08:00 GMT+8 周日]
        OffsetDateTime odt02 = dt1.toDefaultOffsetDT();     // 2022-02-27 08:00:10.000000100 [+08:00 GMT+8 周日]
        OffsetDateTime odt03 = dt2.toUTCOffsetDT();     // 2022-02-27 00:00:10.000000100 [+00:00 GMT 周日]
        OffsetDateTime odt04 = dt3.toUTCOffsetDT();     // 2022-02-27 00:00:10.000000100 [+00:00 GMT 周日]
        OffsetDateTime odt05 = dt4.toOffsetDT(null);        // 2022-02-27 08:00:10.000000100 [+08:00 GMT+8 周日]
        OffsetDateTime odt06 = dt5.toOffsetDT(ZoneOffset.MIN);      // 2022-02-26 06:00:10.000000100 [-18:00 GMT-18 周六]
        System.out.println("========================================================");
        // System.out.println(G.dtDetail(calendar01));
        // System.out.println(G.dtDetail(calendar02));
        // System.out.println(G.dtDetail(calendar03));
        // System.out.println(G.dtDetail(calendar04));
        // System.out.println(G.dtDetail(calendar05));
        // System.out.println(G.dtDetail(calendar06));
        // System.out.println(G.dtDetail(ldt01));
        // System.out.println(G.dtDetail(ldt02));
        // System.out.println(G.dtDetail(ldt03));
        // System.out.println(G.dtDetail(ldt04));
        // System.out.println(G.dtDetail(ldt05));
        // System.out.println(G.dtDetail(ldt06));
        // System.out.println(G.dtDetail(instant01));
        // System.out.println(G.dtDetail(instant02));
        // System.out.println(G.dtDetail(instant03));
        // System.out.println(G.dtDetail(instant04));
        // System.out.println(G.dtDetail(zdt01));
        // System.out.println(G.dtDetail(zdt02));
        // System.out.println(G.dtDetail(zdt03));
        // System.out.println(G.dtDetail(zdt04));
        // System.out.println(G.dtDetail(zdt05));
        // System.out.println(G.dtDetail(zdt06));
        // System.out.println(G.dtDetail(odt01));
        // System.out.println(G.dtDetail(odt02));
        // System.out.println(G.dtDetail(odt03));
        // System.out.println(G.dtDetail(odt04));
        // System.out.println(G.dtDetail(odt05));
        // System.out.println(G.dtDetail(odt06));

        assertEquals(G.dtDetail(calendar01), "2022-02-27 08:00:10.000000000 [Asia/Shanghai +08:00 GMT+8 周日]");
        assertEquals(G.dtDetail(calendar02), "2022-02-27 08:00:10.000000000 [Asia/Shanghai +08:00 GMT+8 周日]");
        assertEquals(G.dtDetail(calendar03), "2022-02-27 00:00:10.000000000 [UTC +00:00 GMT 周日]");
        assertEquals(G.dtDetail(calendar04), "2022-02-27 00:00:10.000000000 [UTC +00:00 GMT 周日]");
        assertEquals(G.dtDetail(calendar05), "2022-02-27 00:00:10.000000000 [UTC +00:00 GMT 周日]");
        assertEquals(G.dtDetail(calendar06), "2022-02-27 04:00:10.000000000 [Asia/Dubai +04:00 GMT+4 周日]");
        assertEquals(G.dtDetail(ldt01), "2022-02-27 03:00:10.000000000 [周日]");
        assertEquals(G.dtDetail(ldt02), "2022-02-27 08:00:10.000000000 [周日]");
        assertEquals(G.dtDetail(ldt03), "2022-02-27 08:00:10.000000100 [周日]");
        assertEquals(G.dtDetail(ldt04), "2022-02-27 08:00:10.000000100 [周日]");
        assertEquals(G.dtDetail(ldt05), "2022-02-27 03:00:10.000000100 [周日]");
        assertEquals(G.dtDetail(ldt06), "2022-02-27 08:00:10.000000100 [周日]");
        assertEquals(G.dtDetail(instant01), "2022-02-27 08:00:10.000000100 [Asia/Shanghai +08:00 GMT+8 周日]");
        assertEquals(G.dtDetail(instant02), "2022-02-27 08:00:10.000000000 [Asia/Shanghai +08:00 GMT+8 周日]");
        assertEquals(G.dtDetail(instant03), "2022-02-27 08:00:10.000000000 [Asia/Shanghai +08:00 GMT+8 周日]");
        assertEquals(G.dtDetail(instant04), "2022-02-27 08:00:10.000000100 [Asia/Shanghai +08:00 GMT+8 周日]");
        assertEquals(G.dtDetail(zdt01), "2022-02-27 08:00:10.000000100 [Asia/Shanghai +08:00 GMT+8 周日]");
        assertEquals(G.dtDetail(zdt02), "2022-02-27 08:00:10.000000100 [Asia/Shanghai +08:00 GMT+8 周日]");
        assertEquals(G.dtDetail(zdt03), "2022-02-27 00:00:10.000000100 [UTC +00:00 GMT 周日]");
        assertEquals(G.dtDetail(zdt04), "2022-02-27 00:00:10.000000100 [UTC +00:00 GMT 周日]");
        assertEquals(G.dtDetail(zdt05), "2022-02-27 00:00:10.000000100 [Z +00:00 GMT 周日]");
        assertEquals(G.dtDetail(zdt06), "2022-02-27 00:00:10.000000000 [UTC +00:00 GMT 周日]");
        assertEquals(G.dtDetail(odt01), "2022-02-27 08:00:10.000000000 [+08:00 GMT+8 周日]");
        assertEquals(G.dtDetail(odt02), "2022-02-27 08:00:10.000000100 [+08:00 GMT+8 周日]");
        assertEquals(G.dtDetail(odt03), "2022-02-27 00:00:10.000000100 [+00:00 GMT 周日]");
        assertEquals(G.dtDetail(odt04), "2022-02-27 00:00:10.000000100 [+00:00 GMT 周日]");
        assertEquals(G.dtDetail(odt05), "2022-02-27 08:00:10.000000100 [+08:00 GMT+8 周日]");
        assertEquals(G.dtDetail(odt06), "2022-02-26 06:00:10.000000100 [-18:00 GMT-18 周六]");

    }

    @Test
    public void testDateTimeFormat() {
        LocalDateTime ldt = LocalDateTime.of(2022, 2, 27, 8, 0, 10, 100987456);
        ZonedDateTime zdt = ldt.atZone(TZ.DEFAULT_ZONE);

        ZonedDateTime zdt1 = zdt.withZoneSameInstant(TZ.MOSCOW);
        Instant instant1 = zdt1.toInstant();
        OffsetDateTime odt1 = zdt1.toOffsetDateTime().withOffsetSameInstant(ZoneOffset.UTC);
        GregorianCalendar calendar1 = GregorianCalendar.from(zdt1);
        Date date1 = calendar1.getTime();

        DateTime<LocalDateTime> dt1 = DateTime.of(ldt);
        DateTime<ZonedDateTime> dt2 = DateTime.of(zdt);
        DateTime<ZonedDateTime> dt3 = DateTime.of(zdt1);
        DateTime<Instant> dt4 = DateTime.of(instant1);
        DateTime<OffsetDateTime> dt5 = DateTime.of(odt1);
        DateTime<GregorianCalendar> dt6 = DateTime.of(calendar1);
        DateTime<Date> dt7 = DateTime.of(date1);

        String format01 = dt1.format("yyyy/MM/dd HH:mm:ss.SSSSSSSSS '['E']'");
        String format02 = dt2.format("yyyy/MM/dd HH:mm:ss.SSSSSSSSS '['E']'");
        String format03 = dt3.format("yyyy/MM/dd HH:mm:ss.SSSSSSSSS '['E']'");
        String format04 = dt4.format("yyyy/MM/dd HH:mm:ss.SSSSSSSSS '['E']'");
        String format05 = dt5.format("yyyy/MM/dd HH:mm:ss.SSSSSSSSS '['E']'");
        String format06 = dt6.format("yyyy/MM/dd HH:mm:ss.SSSSSSSSS '['E']'");
        String format07 = dt7.format("yyyy/MM/dd HH:mm:ss.SSSSSSSSS '['E']'");
        String format11 = dt1.format("yyyyMMddHHmmss.SSSSSSSSS '['E']'");
        String format12 = dt2.format("yyyyMMddHHmmss.SSSSSSSSS '['E']'");
        String format13 = dt3.format("yyyyMMddHHmmss.SSSSSSSSS '['E']'");
        String format14 = dt4.format("yyyyMMddHHmmss.SSSSSSSSS '['E']'");
        String format15 = dt5.format("yyyyMMddHHmmss.SSSSSSSSS '['E']'");
        String format16 = dt6.format("yyyyMMddHHmmss.SSSSSSSSS '['E']'");
        String format17 = dt7.format("yyyyMMddHHmmss.SSSSSSSSS '['E']'");

        System.out.println(format01);
        System.out.println(format02);
        System.out.println(format03);
        System.out.println(format04);
        System.out.println(format05);
        System.out.println(format06);
        System.out.println(format07);
        System.out.println(format11);
        System.out.println(format12);
        System.out.println(format13);
        System.out.println(format14);
        System.out.println(format15);
        System.out.println(format16);
        System.out.println(format17);

        assertEquals("2022/02/27 08:00:10.100987456 [周日]", format01);
        assertEquals("2022/02/27 08:00:10.100987456 [周日]", format02);
        assertEquals("2022/02/27 03:00:10.100987456 [周日]", format03);
        assertEquals("2022/02/27 08:00:10.100987456 [周日]", format04);
        assertEquals("2022/02/27 00:00:10.100987456 [周日]", format05);
        assertEquals("2022/02/27 03:00:10.100000000 [周日]", format06);
        assertEquals("2022/02/27 08:00:10.100000000 [周日]", format07);
        assertEquals("20220227080010.100987456 [周日]", format11);
        assertEquals("20220227080010.100987456 [周日]", format12);
        assertEquals("20220227030010.100987456 [周日]", format13);
        assertEquals("20220227080010.100987456 [周日]", format14);
        assertEquals("20220227000010.100987456 [周日]", format15);
        assertEquals("20220227030010.100000000 [周日]", format16);
        assertEquals("20220227080010.100000000 [周日]", format17);

    }

    @Test
    public void testDTPlusAndMinus() {
        LocalDateTime ldt = LocalDateTime.of(2022, 2, 27, 8, 0, 10, 100);
        ZonedDateTime zdt = ldt.atZone(TZ.DEFAULT_ZONE);

        ZonedDateTime zdt1 = zdt.withZoneSameInstant(TZ.MOSCOW);
        Instant instant = zdt1.toInstant();
        OffsetDateTime odt = zdt1.toOffsetDateTime().withOffsetSameInstant(ZoneOffset.UTC);
        GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance(TimeZone.getTimeZone(TZ.NEW_YORK));
        calendar.setTime(Date.from(zdt.toInstant()));
        Date date = calendar.getTime();

        DateTime<LocalDateTime> dt1 = DateTime.of(ldt);
        DateTime<ZonedDateTime> dt2 = DateTime.of(zdt);
        DateTime<ZonedDateTime> dt3 = DateTime.of(zdt1);
        DateTime<Instant> dt4 = DateTime.of(instant);
        DateTime<OffsetDateTime> dt5 = DateTime.of(odt);
        DateTime<Calendar> dt6 = DateTime.of(calendar);
        DateTime<Date> dt7 = DateTime.of(date);

        System.out.println(dt1.dtDetail());     // 2022-02-27 08:00:10.000000100 [周日]
        System.out.println(dt2.dtDetail());     // 2022-02-27 08:00:10.000000100 [Asia/Shanghai +08:00 GMT+8 周日]
        System.out.println(dt3.dtDetail());     // 2022-02-27 03:00:10.000000100 [Europe/Moscow +03:00 GMT+3 周日]
        System.out.println(dt4.dtDetail());     // 2022-02-27 08:00:10.000000100 [Asia/Shanghai +08:00 GMT+8 周日]
        System.out.println(dt5.dtDetail());     // 2022-02-27 00:00:10.000000100 [+00:00 GMT 周日]
        System.out.println(dt6.dtDetail());     // 2022-02-26 19:00:10.000000000 [America/New_York -05:00 GMT-5 周六]
        System.out.println(dt7.dtDetail());     // 2022-02-27 08:00:10.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        System.out.println("=======================================");
        DateTime<LocalDateTime> dt01 = dt1.plusDays(2);
        DateTime<ZonedDateTime> dt02 = dt2.plusMonths(5);
        DateTime<ZonedDateTime> dt03 = dt3.plusMillis(1500);
        DateTime<Instant> dt04 = dt4.plusHours(39);
        DateTime<OffsetDateTime> dt05 = dt5.plusDays(30);
        DateTime<Calendar> dt06 = dt6.plusMonths(5);
        DateTime<Date> dt07 = dt7.plusMicros(56987599);
        System.out.println(dt01.dtDetail());        // 2022-03-01 08:00:10.000000100 [周二]
        System.out.println(dt02.dtDetail());        // 2022-07-27 08:00:10.000000100 [Asia/Shanghai +08:00 GMT+8 周三]
        System.out.println(dt03.dtDetail());        // 2022-02-27 03:00:11.500000100 [Europe/Moscow +03:00 GMT+3 周日]
        System.out.println(dt04.dtDetail());        // 2022-02-28 23:00:10.000000100 [Asia/Shanghai +08:00 GMT+8 周一]
        System.out.println(dt05.dtDetail());        // 2022-03-29 00:00:10.000000100 [+00:00 GMT 周二]
        System.out.println(dt06.dtDetail());        // 2022-07-26 19:00:10.000000000 [America/New_York -04:00 GMT-4 周二]
        System.out.println(dt07.dtDetail());        // 2022-02-27 08:01:06.987000000 [Asia/Shanghai +08:00 GMT+8 周日]
        System.out.println("=======================================");
        DateTime<LocalDateTime> dt11 = dt01.minusDays(2);
        DateTime<ZonedDateTime> dt12 = dt02.minusMonths(5);
        DateTime<ZonedDateTime> dt13 = dt03.minusMillis(1500);
        DateTime<Instant> dt14 = dt04.minusHours(39);
        DateTime<OffsetDateTime> dt15 = dt05.minusDays(30);
        DateTime<Calendar> dt16 = dt06.minusMonths(5);
        DateTime<Date> dt17 = dt07.minusMicros(56987599);
        System.out.println(dt11.dtDetail());        // 2022-02-27 08:00:10.000000100 [周日]
        System.out.println(dt12.dtDetail());        // 2022-02-27 08:00:10.000000100 [Asia/Shanghai +08:00 GMT+8 周日]
        System.out.println(dt13.dtDetail());        // 2022-02-27 03:00:10.000000100 [Europe/Moscow +03:00 GMT+3 周日]
        System.out.println(dt14.dtDetail());        // 2022-02-27 08:00:10.000000100 [Asia/Shanghai +08:00 GMT+8 周日]
        System.out.println(dt15.dtDetail());        // 2022-02-27 00:00:10.000000100 [+00:00 GMT 周日]
        System.out.println(dt16.dtDetail());        // 2022-02-26 19:00:10.000000000 [America/New_York -05:00 GMT-5 周六]
        System.out.println(dt17.dtDetail());        // 2022-02-27 08:00:09.999000000 [Asia/Shanghai +08:00 GMT+8 周日]

        assertEquals("2022-03-01 08:00:10.000000100 [周二]", dt01.dtDetail());
        assertEquals("2022-07-27 08:00:10.000000100 [Asia/Shanghai +08:00 GMT+8 周三]", dt02.dtDetail());
        assertEquals("2022-02-27 03:00:11.500000100 [Europe/Moscow +03:00 GMT+3 周日]", dt03.dtDetail());
        assertEquals("2022-02-28 23:00:10.000000100 [Asia/Shanghai +08:00 GMT+8 周一]", dt04.dtDetail());
        assertEquals("2022-03-29 00:00:10.000000100 [+00:00 GMT 周二]", dt05.dtDetail());
        assertEquals("2022-07-26 19:00:10.000000000 [America/New_York -04:00 GMT-4 周二]", dt06.dtDetail());
        assertEquals("2022-02-27 08:01:06.987000000 [Asia/Shanghai +08:00 GMT+8 周日]", dt07.dtDetail());
        assertEquals("2022-02-27 08:00:10.000000100 [周日]", dt11.dtDetail());
        assertEquals("2022-02-27 08:00:10.000000100 [Asia/Shanghai +08:00 GMT+8 周日]", dt12.dtDetail());
        assertEquals("2022-02-27 03:00:10.000000100 [Europe/Moscow +03:00 GMT+3 周日]", dt13.dtDetail());
        assertEquals("2022-02-27 08:00:10.000000100 [Asia/Shanghai +08:00 GMT+8 周日]", dt14.dtDetail());
        assertEquals("2022-02-27 00:00:10.000000100 [+00:00 GMT 周日]", dt15.dtDetail());
        assertEquals("2022-02-26 19:00:10.000000000 [America/New_York -05:00 GMT-5 周六]", dt16.dtDetail());
        assertEquals("2022-02-27 08:00:09.999000000 [Asia/Shanghai +08:00 GMT+8 周日]", dt17.dtDetail());

    }

    @Test
    public void testDTWithAndDaysOfMonthAndGet() {
        LocalDateTime ldt = LocalDateTime.of(2022, 2, 27, 8, 0, 10, 100);
        ZonedDateTime zdt = ldt.atZone(TZ.DEFAULT_ZONE);

        ZonedDateTime zdt1 = zdt.withZoneSameInstant(TZ.MOSCOW);
        Instant instant = zdt1.toInstant();
        OffsetDateTime odt = zdt1.toOffsetDateTime().withOffsetSameInstant(ZoneOffset.UTC);
        GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance(TimeZone.getTimeZone(TZ.NEW_YORK));
        calendar.setTime(Date.from(zdt.toInstant()));
        Date date = calendar.getTime();

        DateTime<LocalDateTime> dt1 = DateTime.of(ldt);
        DateTime<ZonedDateTime> dt2 = DateTime.of(zdt);
        DateTime<ZonedDateTime> dt3 = DateTime.of(zdt1);
        DateTime<Instant> dt4 = DateTime.of(instant);
        DateTime<OffsetDateTime> dt5 = DateTime.of(odt);
        DateTime<Calendar> dt6 = DateTime.of(calendar);
        DateTime<Date> dt7 = DateTime.of(date);

        System.out.println(dt1.dtDetail());     // 2022-02-27 08:00:10.000000100 [周日]
        System.out.println(dt2.dtDetail());     // 2022-02-27 08:00:10.000000100 [Asia/Shanghai +08:00 GMT+8 周日]
        System.out.println(dt3.dtDetail());     // 2022-02-27 03:00:10.000000100 [Europe/Moscow +03:00 GMT+3 周日]
        System.out.println(dt4.dtDetail());     // 2022-02-27 08:00:10.000000100 [Asia/Shanghai +08:00 GMT+8 周日]
        System.out.println(dt5.dtDetail());     // 2022-02-27 00:00:10.000000100 [+00:00 GMT 周日]
        System.out.println(dt6.dtDetail());     // 2022-02-26 19:00:10.000000000 [America/New_York -05:00 GMT-5 周六]
        System.out.println(dt7.dtDetail());     // 2022-02-27 08:00:10.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        System.out.println("=======================================");

        DateTime<LocalDateTime> dt01 = dt1.withYear(2020);
        DateTime<ZonedDateTime> dt02 = dt2.withMonth(6);
        DateTime<ZonedDateTime> dt03 = dt3.withDayOfYear(365);
        DateTime<Instant> dt04 = dt4.withDayOfMonth(15);
        DateTime<OffsetDateTime> dt05 = dt5.withHour(10);
        DateTime<Calendar> dt06 = dt6.withNano(987965856);
        DateTime<Date> dt07 = dt7.withSecond(35);
        System.out.println(dt01.dtDetail());        // 2020-02-27 08:00:10.000000100 [周四]
        System.out.println(dt02.dtDetail());        // 2022-06-27 08:00:10.000000100 [Asia/Shanghai +08:00 GMT+8 周一]
        System.out.println(dt03.dtDetail());        // 2022-12-31 03:00:10.000000100 [Europe/Moscow +03:00 GMT+3 周六]
        System.out.println(dt04.dtDetail());        // 2022-02-15 08:00:10.000000100 [Asia/Shanghai +08:00 GMT+8 周二]
        System.out.println(dt05.dtDetail());        // 2022-02-27 10:00:10.000000100 [+00:00 GMT 周日]
        System.out.println(dt06.dtDetail());        // 2022-02-26 19:00:10.987000000 [America/New_York -05:00 GMT-5 周六]
        System.out.println(dt07.dtDetail());        // 2022-02-27 08:00:35.000000000 [Asia/Shanghai +08:00 GMT+8 周日]

        assertEquals("2020-02-27 08:00:10.000000100 [周四]", dt01.dtDetail());
        assertEquals("2022-06-27 08:00:10.000000100 [Asia/Shanghai +08:00 GMT+8 周一]", dt02.dtDetail());
        assertEquals("2022-12-31 03:00:10.000000100 [Europe/Moscow +03:00 GMT+3 周六]", dt03.dtDetail());
        assertEquals("2022-02-15 08:00:10.000000100 [Asia/Shanghai +08:00 GMT+8 周二]", dt04.dtDetail());
        assertEquals("2022-02-27 10:00:10.000000100 [+00:00 GMT 周日]", dt05.dtDetail());
        assertEquals("2022-02-26 19:00:10.987000000 [America/New_York -05:00 GMT-5 周六]", dt06.dtDetail());
        assertEquals("2022-02-27 08:00:35.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dt07.dtDetail());

        int daysOfMonth1 = dt01.daysOfMonth();
        int daysOfMonth2 = dt02.daysOfMonth();
        int daysOfMonth3 = dt03.daysOfMonth();
        int daysOfMonth4 = dt04.daysOfMonth();
        int daysOfMonth5 = dt05.daysOfMonth();
        int daysOfMonth6 = dt06.daysOfMonth();
        int daysOfMonth7 = dt07.daysOfMonth();

        int year = dt01.getYear();
        Month month = dt02.getMonth();
        int monthValue = dt03.getMonthValue();
        int dayOfYear = dt04.getDayOfYear();
        int dayOfMonth = dt05.getDayOfMonth();
        int hour = dt06.getHour();
        DayOfWeek dayOfWeek = dt07.getDayOfWeek();

        System.out.println(daysOfMonth1);       // 29
        System.out.println(daysOfMonth2);       // 30
        System.out.println(daysOfMonth3);       // 31
        System.out.println(daysOfMonth4);       // 28
        System.out.println(daysOfMonth5);       // 28
        System.out.println(daysOfMonth6);       // 28
        System.out.println(daysOfMonth7);       // 28
        System.out.println(year);           // 2020
        System.out.println(month);          // JUNE
        System.out.println(monthValue);         // 12
        System.out.println(dayOfYear);          // 46
        System.out.println(dayOfMonth);         // 27
        System.out.println(hour);               // 19
        System.out.println(dayOfWeek);          // SUNDAY

        assertEquals(29, daysOfMonth1);
        assertEquals(30, daysOfMonth2);
        assertEquals(31, daysOfMonth3);
        assertEquals(28, daysOfMonth4);
        assertEquals(28, daysOfMonth5);
        assertEquals(28, daysOfMonth6);
        assertEquals(28, daysOfMonth7);
        assertEquals(2020, year);
        assertEquals(Month.JUNE, month);
        assertEquals(12, monthValue);
        assertEquals(46, dayOfYear);
        assertEquals(27, dayOfMonth);
        assertEquals(19, hour);
        assertEquals(DayOfWeek.SUNDAY, dayOfWeek);

    }

    @Test
    public void testRound() {
        LocalDateTime ldt = LocalDateTime.of(2021, 12, 27, 8, 0, 10, 100);
        ZonedDateTime zdt = ldt.atZone(TZ.DEFAULT_ZONE);

        ZonedDateTime zdt1 = zdt.withZoneSameInstant(TZ.NEW_YORK);
        Instant instant = zdt1.toInstant();
        GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance(TimeZone.getTimeZone(TZ.NEW_YORK));
        calendar.setTime(Date.from(zdt.toInstant()));
        Date date = calendar.getTime();

        DateTime<ZonedDateTime> dt1 = DateTime.of(zdt1);
        DateTime<Instant> dt2 = DateTime.of(instant);
        DateTime<Date> dt3 = DateTime.of(date);

        System.out.println("ZonedDateTime: " + dt1.dtDetail()); // ZonedDateTime: 2021-12-26 19:00:10.000000100 [America/New_York -05:00 GMT-5 周日]
        System.out.println("Instant: " + dt2.dtDetail());       // Instant: 2021-12-27 08:00:10.000000100 [Asia/Shanghai +08:00 GMT+8 周一]
        System.out.println("Date: " + dt3.dtDetail());          // Date: 2021-12-27 08:00:10.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        System.out.println("========================================");
        DateTime<ZonedDateTime> roundFloor01 = dt1.round(ChronoUnit.MONTHS, RoundingDT.FLOOR);
        DateTime<ZonedDateTime> roundCeiling01 = dt1.round(ChronoUnit.MONTHS, RoundingDT.CEILING);
        DateTime<ZonedDateTime> roundHalfUp01 = dt1.round(ChronoUnit.MONTHS, RoundingDT.HALF_UP);
        DateTime<Instant> roundFloor02 = dt2.round(ChronoUnit.MONTHS, RoundingDT.FLOOR);
        DateTime<Instant> roundCeiling02 = dt2.round(ChronoUnit.MONTHS, RoundingDT.CEILING);
        DateTime<Instant> roundHalfUp02 = dt2.round(ChronoUnit.MONTHS, RoundingDT.HALF_UP);
        DateTime<Date> roundFloor03 = dt3.round(ChronoUnit.MONTHS, RoundingDT.FLOOR);
        DateTime<Date> roundCeiling03 = dt3.round(ChronoUnit.MONTHS, RoundingDT.CEILING);
        DateTime<Date> roundHalfUp03 = dt3.round(ChronoUnit.MONTHS, RoundingDT.HALF_UP);
        DateTime<ZonedDateTime> roundFloor11 = dt1.round(ChronoUnit.DAYS, RoundingDT.FLOOR);
        DateTime<ZonedDateTime> roundCeiling11 = dt1.round(ChronoUnit.DAYS, RoundingDT.CEILING);
        DateTime<ZonedDateTime> roundHalfUp11 = dt1.round(ChronoUnit.DAYS, RoundingDT.HALF_UP);
        DateTime<Instant> roundFloor12 = dt2.round(ChronoUnit.DAYS, RoundingDT.FLOOR);
        DateTime<Instant> roundCeiling12 = dt2.round(ChronoUnit.DAYS, RoundingDT.CEILING);
        DateTime<Instant> roundHalfUp12 = dt2.round(ChronoUnit.DAYS, RoundingDT.HALF_UP);
        DateTime<Date> roundFloor13 = dt3.round(ChronoUnit.DAYS, RoundingDT.FLOOR);
        DateTime<Date> roundCeiling13 = dt3.round(ChronoUnit.DAYS, RoundingDT.CEILING);
        DateTime<Date> roundHalfUp13 = dt3.round(ChronoUnit.DAYS, RoundingDT.HALF_UP);
        DateTime<ZonedDateTime> roundFloor21 = dt1.round(ChronoUnit.SECONDS, RoundingDT.FLOOR);
        DateTime<ZonedDateTime> roundCeiling21 = dt1.round(ChronoUnit.SECONDS, RoundingDT.CEILING);
        DateTime<ZonedDateTime> roundHalfUp21 = dt1.round(ChronoUnit.SECONDS, RoundingDT.HALF_UP);
        DateTime<Instant> roundFloor22 = dt2.round(ChronoUnit.SECONDS, RoundingDT.FLOOR);
        DateTime<Instant> roundCeiling22 = dt2.round(ChronoUnit.SECONDS, RoundingDT.CEILING);
        DateTime<Instant> roundHalfUp22 = dt2.round(ChronoUnit.SECONDS, RoundingDT.HALF_UP);
        DateTime<Date> roundFloor23 = dt3.round(ChronoUnit.SECONDS, RoundingDT.FLOOR);
        DateTime<Date> roundCeiling23 = dt3.round(ChronoUnit.SECONDS, RoundingDT.CEILING);
        DateTime<Date> roundHalfUp23 = dt3.round(ChronoUnit.SECONDS, RoundingDT.HALF_UP);

        // System.out.println(roundFloor01.dtDetail());        // 2021-12-01 00:00:00.000000000 [America/New_York -05:00 GMT-5 周三]
        // System.out.println(roundCeiling01.dtDetail());      // 2022-01-01 00:00:00.000000000 [America/New_York -05:00 GMT-5 周六]
        // System.out.println(roundHalfUp01.dtDetail());       // 2022-01-01 00:00:00.000000000 [America/New_York -05:00 GMT-5 周六]
        // System.out.println(roundFloor02.dtDetail());        // 2021-12-01 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周三]
        // System.out.println(roundCeiling02.dtDetail());      // 2022-01-01 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周六]
        // System.out.println(roundHalfUp02.dtDetail());       // 2022-01-01 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周六]
        // System.out.println(roundFloor03.dtDetail());        // 2021-12-01 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周三]
        // System.out.println(roundCeiling03.dtDetail());      // 2022-01-01 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周六]
        // System.out.println(roundHalfUp03.dtDetail());       // 2022-01-01 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周六]
        // System.out.println(roundFloor11.dtDetail());        // 2021-12-26 00:00:00.000000000 [America/New_York -05:00 GMT-5 周日]
        // System.out.println(roundCeiling11.dtDetail());      // 2021-12-27 00:00:00.000000000 [America/New_York -05:00 GMT-5 周一]
        // System.out.println(roundHalfUp11.dtDetail());       // 2021-12-27 00:00:00.000000000 [America/New_York -05:00 GMT-5 周一]
        // System.out.println(roundFloor12.dtDetail());        // 2021-12-27 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        // System.out.println(roundCeiling12.dtDetail());      // 2021-12-28 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周二]
        // System.out.println(roundHalfUp12.dtDetail());       // 2021-12-27 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        // System.out.println(roundFloor13.dtDetail());        // 2021-12-27 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        // System.out.println(roundCeiling13.dtDetail());      // 2021-12-28 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周二]
        // System.out.println(roundHalfUp13.dtDetail());       // 2021-12-27 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        // System.out.println(roundFloor21.dtDetail());        // 2021-12-26 19:00:10.000000000 [America/New_York -05:00 GMT-5 周日]
        // System.out.println(roundCeiling21.dtDetail());      // 2021-12-26 19:00:11.000000000 [America/New_York -05:00 GMT-5 周日]
        // System.out.println(roundHalfUp21.dtDetail());       // 2021-12-26 19:00:10.000000000 [America/New_York -05:00 GMT-5 周日]
        // System.out.println(roundFloor22.dtDetail());        // 2021-12-27 08:00:10.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        // System.out.println(roundCeiling22.dtDetail());      // 2021-12-27 08:00:11.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        // System.out.println(roundHalfUp22.dtDetail());       // 2021-12-27 08:00:10.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        // System.out.println(roundFloor23.dtDetail());        // 2021-12-27 08:00:10.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        // System.out.println(roundCeiling23.dtDetail());      // 2021-12-27 08:00:10.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        // System.out.println(roundHalfUp23.dtDetail());       // 2021-12-27 08:00:10.000000000 [Asia/Shanghai +08:00 GMT+8 周一]

        DateTime<ZonedDateTime> dt01 = dt1.withNano(500000000);
        DateTime<Instant> dt02 = dt2.withNano(500000000);
        DateTime<Date> dt03 = dt3.withNano(500000000);
        System.out.println("========================================");
        System.out.println(dt01.dtDetail());    // 2021-12-26 19:00:10.500000000 [America/New_York -05:00 GMT-5 周日]
        System.out.println(dt02.dtDetail());    // 2021-12-27 08:00:10.500000000 [Asia/Shanghai +08:00 GMT+8 周一]
        System.out.println(dt03.dtDetail());    // 2021-12-27 08:00:10.500000000 [Asia/Shanghai +08:00 GMT+8 周一]
        DateTime<ZonedDateTime> roundFloor31 = dt01.round(ChronoUnit.SECONDS, RoundingDT.FLOOR);
        DateTime<ZonedDateTime> roundCeiling31 = dt01.round(ChronoUnit.SECONDS, RoundingDT.CEILING);
        DateTime<ZonedDateTime> roundHalfUp31 = dt01.round(ChronoUnit.SECONDS, RoundingDT.HALF_UP);
        DateTime<Instant> roundFloor32 = dt02.round(ChronoUnit.SECONDS, RoundingDT.FLOOR);
        DateTime<Instant> roundCeiling32 = dt02.round(ChronoUnit.SECONDS, RoundingDT.CEILING);
        DateTime<Instant> roundHalfUp32 = dt02.round(ChronoUnit.SECONDS, RoundingDT.HALF_UP);
        DateTime<Date> roundFloor33 = dt03.round(ChronoUnit.SECONDS, RoundingDT.FLOOR);
        DateTime<Date> roundCeiling33 = dt03.round(ChronoUnit.SECONDS, RoundingDT.CEILING);
        DateTime<Date> roundHalfUp33 = dt03.round(ChronoUnit.SECONDS, RoundingDT.HALF_UP);

        // System.out.println(roundFloor31.dtDetail());    // 2021-12-26 19:00:10.000000000 [America/New_York -05:00 GMT-5 周日]
        // System.out.println(roundCeiling31.dtDetail());  // 2021-12-26 19:00:11.000000000 [America/New_York -05:00 GMT-5 周日]
        // System.out.println(roundHalfUp31.dtDetail());   // 2021-12-26 19:00:11.000000000 [America/New_York -05:00 GMT-5 周日]
        // System.out.println(roundFloor32.dtDetail());    // 2021-12-27 08:00:10.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        // System.out.println(roundCeiling32.dtDetail());  // 2021-12-27 08:00:11.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        // System.out.println(roundHalfUp32.dtDetail());   // 2021-12-27 08:00:11.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        // System.out.println(roundFloor33.dtDetail());    // 2021-12-27 08:00:10.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        // System.out.println(roundCeiling33.dtDetail());  // 2021-12-27 08:00:11.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        // System.out.println(roundHalfUp33.dtDetail());   // 2021-12-27 08:00:11.000000000 [Asia/Shanghai +08:00 GMT+8 周一]

        assertEquals("2021-12-01 00:00:00.000000000 [America/New_York -05:00 GMT-5 周三]", roundFloor01.dtDetail());
        assertEquals("2022-01-01 00:00:00.000000000 [America/New_York -05:00 GMT-5 周六]", roundCeiling01.dtDetail());
        assertEquals("2022-01-01 00:00:00.000000000 [America/New_York -05:00 GMT-5 周六]", roundHalfUp01.dtDetail());
        assertEquals("2021-12-01 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周三]", roundFloor02.dtDetail());
        assertEquals("2022-01-01 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周六]", roundCeiling02.dtDetail());
        assertEquals("2022-01-01 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周六]", roundHalfUp02.dtDetail());
        assertEquals("2021-12-01 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周三]", roundFloor03.dtDetail());
        assertEquals("2022-01-01 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周六]", roundCeiling03.dtDetail());
        assertEquals("2022-01-01 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周六]", roundHalfUp03.dtDetail());
        assertEquals("2021-12-26 00:00:00.000000000 [America/New_York -05:00 GMT-5 周日]", roundFloor11.dtDetail());
        assertEquals("2021-12-27 00:00:00.000000000 [America/New_York -05:00 GMT-5 周一]", roundCeiling11.dtDetail());
        assertEquals("2021-12-27 00:00:00.000000000 [America/New_York -05:00 GMT-5 周一]", roundHalfUp11.dtDetail());
        assertEquals("2021-12-27 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周一]", roundFloor12.dtDetail());
        assertEquals("2021-12-28 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周二]", roundCeiling12.dtDetail());
        assertEquals("2021-12-27 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周一]", roundHalfUp12.dtDetail());
        assertEquals("2021-12-27 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周一]", roundFloor13.dtDetail());
        assertEquals("2021-12-28 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周二]", roundCeiling13.dtDetail());
        assertEquals("2021-12-27 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周一]", roundHalfUp13.dtDetail());
        assertEquals("2021-12-26 19:00:10.000000000 [America/New_York -05:00 GMT-5 周日]", roundFloor21.dtDetail());
        assertEquals("2021-12-26 19:00:11.000000000 [America/New_York -05:00 GMT-5 周日]", roundCeiling21.dtDetail());
        assertEquals("2021-12-26 19:00:10.000000000 [America/New_York -05:00 GMT-5 周日]", roundHalfUp21.dtDetail());
        assertEquals("2021-12-27 08:00:10.000000000 [Asia/Shanghai +08:00 GMT+8 周一]", roundFloor22.dtDetail());
        assertEquals("2021-12-27 08:00:11.000000000 [Asia/Shanghai +08:00 GMT+8 周一]", roundCeiling22.dtDetail());
        assertEquals("2021-12-27 08:00:10.000000000 [Asia/Shanghai +08:00 GMT+8 周一]", roundHalfUp22.dtDetail());
        assertEquals("2021-12-27 08:00:10.000000000 [Asia/Shanghai +08:00 GMT+8 周一]", roundFloor23.dtDetail());
        assertEquals("2021-12-27 08:00:10.000000000 [Asia/Shanghai +08:00 GMT+8 周一]", roundCeiling23.dtDetail());
        assertEquals("2021-12-27 08:00:10.000000000 [Asia/Shanghai +08:00 GMT+8 周一]", roundHalfUp23.dtDetail());
        assertEquals("2021-12-26 19:00:10.000000000 [America/New_York -05:00 GMT-5 周日]", roundFloor31.dtDetail());
        assertEquals("2021-12-26 19:00:11.000000000 [America/New_York -05:00 GMT-5 周日]", roundCeiling31.dtDetail());
        assertEquals("2021-12-26 19:00:11.000000000 [America/New_York -05:00 GMT-5 周日]", roundHalfUp31.dtDetail());
        assertEquals("2021-12-27 08:00:10.000000000 [Asia/Shanghai +08:00 GMT+8 周一]", roundFloor32.dtDetail());
        assertEquals("2021-12-27 08:00:11.000000000 [Asia/Shanghai +08:00 GMT+8 周一]", roundCeiling32.dtDetail());
        assertEquals("2021-12-27 08:00:11.000000000 [Asia/Shanghai +08:00 GMT+8 周一]", roundHalfUp32.dtDetail());
        assertEquals("2021-12-27 08:00:10.000000000 [Asia/Shanghai +08:00 GMT+8 周一]", roundFloor33.dtDetail());
        assertEquals("2021-12-27 08:00:11.000000000 [Asia/Shanghai +08:00 GMT+8 周一]", roundCeiling33.dtDetail());
        assertEquals("2021-12-27 08:00:11.000000000 [Asia/Shanghai +08:00 GMT+8 周一]", roundHalfUp33.dtDetail());

    }

    @Test
    public void testRoundTime() {
        LocalDateTime ldt = LocalDateTime.of(2021, 2, 28, 9, 0, 16, 100);
        ZonedDateTime zdt = ldt.atZone(TZ.DEFAULT_ZONE);

        ZonedDateTime zdt1 = zdt.withZoneSameInstant(TZ.NEW_YORK);
        Instant instant = zdt1.toInstant();
        GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance(TimeZone.getTimeZone(TZ.NEW_YORK));
        calendar.setTime(Date.from(zdt.toInstant()));
        Date date = calendar.getTime();

        DateTime<ZonedDateTime> dt1 = DateTime.of(zdt1);
        DateTime<Date> dt2 = DateTime.of(date);

        System.out.println(dt1.dtDetail());     // 2021-02-27 20:00:16.000000100 [America/New_York -05:00 GMT-5 周六]
        System.out.println(dt2.dtDetail());     // 2021-02-28 09:00:16.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        // System.out.println("========================================");

        // Try.tcf(() -> dt1.roundTime(ChronoUnit.HOURS, 25, RoundingDT.FLOOR));
        // Try.tcf(() -> dt1.roundTime(ChronoUnit.SECONDS, -61, RoundingDT.FLOOR));
        System.out.println("========================================");

        DateTime<ZonedDateTime> dtZdt01 = dt1.roundTime(ChronoUnit.HOURS, 0, RoundingDT.FLOOR);    // 2021-02-27 20:00:00.000000000 [America/New_York -05:00 GMT-5 周六]
        DateTime<ZonedDateTime> dtZdt02 = dt1.roundTime(ChronoUnit.HOURS, 1, RoundingDT.FLOOR);    // 2021-02-27 20:00:00.000000000 [America/New_York -05:00 GMT-5 周六]
        DateTime<ZonedDateTime> dtZdt03 = dt1.roundTime(ChronoUnit.HOURS, -2, RoundingDT.FLOOR);   // 2021-02-27 20:00:00.000000000 [America/New_York -05:00 GMT-5 周六]
        DateTime<ZonedDateTime> dtZdt04 = dt1.roundTime(ChronoUnit.HOURS, 3, RoundingDT.FLOOR);    // 2021-02-27 18:00:00.000000000 [America/New_York -05:00 GMT-5 周六]
        DateTime<ZonedDateTime> dtZdt05 = dt1.roundTime(ChronoUnit.HOURS, -5, RoundingDT.FLOOR);   // 2021-02-27 20:00:00.000000000 [America/New_York -05:00 GMT-5 周六]
        DateTime<ZonedDateTime> dtZdt06 = dt1.roundTime(ChronoUnit.HOURS, 0, RoundingDT.CEILING);  // 2021-02-27 20:00:00.000000000 [America/New_York -05:00 GMT-5 周六]
        DateTime<ZonedDateTime> dtZdt07 = dt1.roundTime(ChronoUnit.HOURS, 1, RoundingDT.CEILING);  // 2021-02-27 21:00:00.000000000 [America/New_York -05:00 GMT-5 周六]
        DateTime<ZonedDateTime> dtZdt08 = dt1.roundTime(ChronoUnit.HOURS, -2, RoundingDT.CEILING); // 2021-02-27 22:00:00.000000000 [America/New_York -05:00 GMT-5 周六]
        DateTime<ZonedDateTime> dtZdt09 = dt1.roundTime(ChronoUnit.HOURS, 3, RoundingDT.CEILING);  // 2021-02-27 21:00:00.000000000 [America/New_York -05:00 GMT-5 周六]
        DateTime<ZonedDateTime> dtZdt10 = dt1.roundTime(ChronoUnit.HOURS, -5, RoundingDT.CEILING); // 2021-02-28 00:00:00.000000000 [America/New_York -05:00 GMT-5 周日]
        DateTime<ZonedDateTime> dtZdt11 = dt1.roundTime(ChronoUnit.HOURS, 0, RoundingDT.HALF_UP);  // 2021-02-27 20:00:00.000000000 [America/New_York -05:00 GMT-5 周六]
        DateTime<ZonedDateTime> dtZdt12 = dt1.roundTime(ChronoUnit.HOURS, 1, RoundingDT.HALF_UP);  // 2021-02-27 20:00:00.000000000 [America/New_York -05:00 GMT-5 周六]
        DateTime<ZonedDateTime> dtZdt13 = dt1.roundTime(ChronoUnit.HOURS, -2, RoundingDT.HALF_UP); // 2021-02-27 20:00:00.000000000 [America/New_York -05:00 GMT-5 周六]
        DateTime<ZonedDateTime> dtZdt14 = dt1.roundTime(ChronoUnit.HOURS, 3, RoundingDT.HALF_UP);  // 2021-02-27 21:00:00.000000000 [America/New_York -05:00 GMT-5 周六]
        DateTime<ZonedDateTime> dtZdt15 = dt1.roundTime(ChronoUnit.HOURS, -5, RoundingDT.HALF_UP); // 2021-02-27 20:00:00.000000000 [America/New_York -05:00 GMT-5 周六]
        DateTime<Date> dtDate01 = dt2.roundTime(ChronoUnit.SECONDS, 0, RoundingDT.FLOOR);          // 2021-02-28 09:00:16.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate02 = dt2.roundTime(ChronoUnit.SECONDS, 1, RoundingDT.FLOOR);          // 2021-02-28 09:00:16.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate03 = dt2.roundTime(ChronoUnit.SECONDS, -2, RoundingDT.FLOOR);         // 2021-02-28 09:00:16.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate04 = dt2.roundTime(ChronoUnit.SECONDS, 3, RoundingDT.FLOOR);          // 2021-02-28 09:00:15.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate05 = dt2.roundTime(ChronoUnit.SECONDS, -5, RoundingDT.FLOOR);         // 2021-02-28 09:00:15.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate06 = dt2.roundTime(ChronoUnit.SECONDS, 0, RoundingDT.CEILING);        // 2021-02-28 09:00:16.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate07 = dt2.roundTime(ChronoUnit.SECONDS, 1, RoundingDT.CEILING);        // 2021-02-28 09:00:16.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate08 = dt2.roundTime(ChronoUnit.SECONDS, -2, RoundingDT.CEILING);       // 2021-02-28 09:00:16.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate09 = dt2.roundTime(ChronoUnit.SECONDS, 3, RoundingDT.CEILING);        // 2021-02-28 09:00:18.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate10 = dt2.roundTime(ChronoUnit.SECONDS, -5, RoundingDT.CEILING);       // 2021-02-28 09:00:20.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate11 = dt2.roundTime(ChronoUnit.SECONDS, 0, RoundingDT.HALF_UP);        // 2021-02-28 09:00:16.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate12 = dt2.roundTime(ChronoUnit.SECONDS, 1, RoundingDT.HALF_UP);        // 2021-02-28 09:00:16.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate13 = dt2.roundTime(ChronoUnit.SECONDS, -2, RoundingDT.HALF_UP);       // 2021-02-28 09:00:16.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate14 = dt2.roundTime(ChronoUnit.SECONDS, 3, RoundingDT.HALF_UP);        // 2021-02-28 09:00:15.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate15 = dt2.roundTime(ChronoUnit.SECONDS, -6, RoundingDT.HALF_UP);       // 2021-02-28 09:00:18.000000000 [Asia/Shanghai +08:00 GMT+8 周日]

        // System.out.println(dtZdt01.dtDetail());
        // System.out.println(dtZdt02.dtDetail());
        // System.out.println(dtZdt03.dtDetail());
        // System.out.println(dtZdt04.dtDetail());
        // System.out.println(dtZdt05.dtDetail());
        // System.out.println(dtZdt06.dtDetail());
        // System.out.println(dtZdt07.dtDetail());
        // System.out.println(dtZdt08.dtDetail());
        // System.out.println(dtZdt09.dtDetail());
        // System.out.println(dtZdt10.dtDetail());
        // System.out.println(dtZdt11.dtDetail());
        // System.out.println(dtZdt12.dtDetail());
        // System.out.println(dtZdt13.dtDetail());
        // System.out.println(dtZdt14.dtDetail());
        // System.out.println(dtZdt15.dtDetail());
        // System.out.println(dtDate01.dtDetail());
        // System.out.println(dtDate02.dtDetail());
        // System.out.println(dtDate03.dtDetail());
        // System.out.println(dtDate04.dtDetail());
        // System.out.println(dtDate05.dtDetail());
        // System.out.println(dtDate06.dtDetail());
        // System.out.println(dtDate07.dtDetail());
        // System.out.println(dtDate08.dtDetail());
        // System.out.println(dtDate09.dtDetail());
        // System.out.println(dtDate10.dtDetail());
        // System.out.println(dtDate11.dtDetail());
        // System.out.println(dtDate12.dtDetail());
        // System.out.println(dtDate13.dtDetail());
        // System.out.println(dtDate14.dtDetail());
        // System.out.println(dtDate15.dtDetail());

        DateTime<Date> dt3 = dt2.withNano(500000000);
        DateTime<Date> dt4 = dt3.withSecond(59);
        System.out.println("========================================");
        System.out.println(dt3.dtDetail());     // 2021-02-28 09:00:16.500000000 [Asia/Shanghai +08:00 GMT+8 周日]
        System.out.println(dt4.dtDetail());     // 2021-02-28 09:00:59.500000000 [Asia/Shanghai +08:00 GMT+8 周日]

        DateTime<Date> dtDate001 = dt3.roundTime(ChronoUnit.SECONDS, 0, RoundingDT.FLOOR);          // 2021-02-28 09:00:16.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate002 = dt3.roundTime(ChronoUnit.SECONDS, 1, RoundingDT.FLOOR);          // 2021-02-28 09:00:16.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate003 = dt3.roundTime(ChronoUnit.SECONDS, -2, RoundingDT.FLOOR);         // 2021-02-28 09:00:16.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate004 = dt3.roundTime(ChronoUnit.SECONDS, 3, RoundingDT.FLOOR);          // 2021-02-28 09:00:15.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate005 = dt3.roundTime(ChronoUnit.SECONDS, -7, RoundingDT.FLOOR);         // 2021-02-28 09:00:14.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate006 = dt3.roundTime(ChronoUnit.SECONDS, 0, RoundingDT.CEILING);        // 2021-02-28 09:00:16.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate007 = dt3.roundTime(ChronoUnit.SECONDS, 1, RoundingDT.CEILING);        // 2021-02-28 09:00:17.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate008 = dt3.roundTime(ChronoUnit.SECONDS, -2, RoundingDT.CEILING);       // 2021-02-28 09:00:18.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate009 = dt3.roundTime(ChronoUnit.SECONDS, 3, RoundingDT.CEILING);        // 2021-02-28 09:00:18.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate010 = dt3.roundTime(ChronoUnit.SECONDS, -7, RoundingDT.CEILING);       // 2021-02-28 09:00:21.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate011 = dt3.roundTime(ChronoUnit.SECONDS, 0, RoundingDT.HALF_UP);        // 2021-02-28 09:00:16.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate012 = dt3.roundTime(ChronoUnit.SECONDS, 1, RoundingDT.HALF_UP);        // 2021-02-28 09:00:16.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate013 = dt3.roundTime(ChronoUnit.SECONDS, -2, RoundingDT.HALF_UP);       // 2021-02-28 09:00:16.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate014 = dt3.roundTime(ChronoUnit.SECONDS, 3, RoundingDT.HALF_UP);        // 2021-02-28 09:00:15.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate015 = dt3.roundTime(ChronoUnit.SECONDS, -7, RoundingDT.HALF_UP);       // 2021-02-28 09:00:14.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate101 = dt4.roundTime(ChronoUnit.SECONDS, 0, RoundingDT.FLOOR);          // 2021-02-28 09:00:59.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate102 = dt4.roundTime(ChronoUnit.SECONDS, 1, RoundingDT.FLOOR);          // 2021-02-28 09:00:59.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate103 = dt4.roundTime(ChronoUnit.SECONDS, -2, RoundingDT.FLOOR);         // 2021-02-28 09:00:58.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate104 = dt4.roundTime(ChronoUnit.SECONDS, 3, RoundingDT.FLOOR);          // 2021-02-28 09:00:57.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate105 = dt4.roundTime(ChronoUnit.SECONDS, -9, RoundingDT.FLOOR);         // 2021-02-28 09:00:54.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate106 = dt4.roundTime(ChronoUnit.SECONDS, 0, RoundingDT.CEILING);        // 2021-02-28 09:00:59.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate107 = dt4.roundTime(ChronoUnit.SECONDS, 1, RoundingDT.CEILING);        // 2021-02-28 09:01:00.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate108 = dt4.roundTime(ChronoUnit.SECONDS, -2, RoundingDT.CEILING);       // 2021-02-28 09:01:00.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate109 = dt4.roundTime(ChronoUnit.SECONDS, 3, RoundingDT.CEILING);        // 2021-02-28 09:01:00.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate110 = dt4.roundTime(ChronoUnit.SECONDS, -9, RoundingDT.CEILING);       // 2021-02-28 09:01:00.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate111 = dt4.roundTime(ChronoUnit.SECONDS, 0, RoundingDT.HALF_UP);        // 2021-02-28 09:00:59.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate112 = dt4.roundTime(ChronoUnit.SECONDS, 1, RoundingDT.HALF_UP);        // 2021-02-28 09:00:59.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate113 = dt4.roundTime(ChronoUnit.SECONDS, -2, RoundingDT.HALF_UP);       // 2021-02-28 09:00:58.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate114 = dt4.roundTime(ChronoUnit.SECONDS, 3, RoundingDT.HALF_UP);        // 2021-02-28 09:01:00.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dtDate115 = dt4.roundTime(ChronoUnit.SECONDS, -9, RoundingDT.HALF_UP);       // 2021-02-28 09:01:00.000000000 [Asia/Shanghai +08:00 GMT+8 周日]

        DateTime<Date> dt01 = dt3.roundTime(ChronoUnit.HOURS, 24, RoundingDT.FLOOR);        // 2021-02-28 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dt02 = dt3.roundTime(ChronoUnit.HOURS, 24, RoundingDT.CEILING);      // 2021-03-01 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        DateTime<Date> dt03 = dt3.roundTime(ChronoUnit.HOURS, 24, RoundingDT.HALF_UP);      // 2021-02-28 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dt04 = dt4.roundTime(ChronoUnit.SECONDS, 60, RoundingDT.FLOOR);      // 2021-02-28 09:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dt05 = dt4.roundTime(ChronoUnit.SECONDS, 60, RoundingDT.CEILING);    // 2021-02-28 09:01:00.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dt06 = dt4.roundTime(ChronoUnit.SECONDS, 60, RoundingDT.HALF_UP);    // 2021-02-28 09:01:00.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        System.out.println("========================================");
        // System.out.println(dtDate001.dtDetail());
        // System.out.println(dtDate002.dtDetail());
        // System.out.println(dtDate003.dtDetail());
        // System.out.println(dtDate004.dtDetail());
        // System.out.println(dtDate005.dtDetail());
        // System.out.println(dtDate006.dtDetail());
        // System.out.println(dtDate007.dtDetail());
        // System.out.println(dtDate008.dtDetail());
        // System.out.println(dtDate009.dtDetail());
        // System.out.println(dtDate010.dtDetail());
        // System.out.println(dtDate011.dtDetail());
        // System.out.println(dtDate012.dtDetail());
        // System.out.println(dtDate013.dtDetail());
        // System.out.println(dtDate014.dtDetail());
        // System.out.println(dtDate015.dtDetail());
        // System.out.println(dtDate101.dtDetail());
        // System.out.println(dtDate102.dtDetail());
        // System.out.println(dtDate103.dtDetail());
        // System.out.println(dtDate104.dtDetail());
        // System.out.println(dtDate105.dtDetail());
        // System.out.println(dtDate106.dtDetail());
        // System.out.println(dtDate107.dtDetail());
        // System.out.println(dtDate108.dtDetail());
        // System.out.println(dtDate109.dtDetail());
        // System.out.println(dtDate110.dtDetail());
        // System.out.println(dtDate111.dtDetail());
        // System.out.println(dtDate112.dtDetail());
        // System.out.println(dtDate113.dtDetail());
        // System.out.println(dtDate114.dtDetail());
        // System.out.println(dtDate115.dtDetail());

        System.out.println(dt01.dtDetail());
        System.out.println(dt02.dtDetail());
        System.out.println(dt03.dtDetail());
        System.out.println(dt04.dtDetail());
        System.out.println(dt05.dtDetail());
        System.out.println(dt06.dtDetail());

        assertEquals("2021-02-27 20:00:00.000000000 [America/New_York -05:00 GMT-5 周六]", dtZdt01.dtDetail());
        assertEquals("2021-02-27 20:00:00.000000000 [America/New_York -05:00 GMT-5 周六]", dtZdt02.dtDetail());
        assertEquals("2021-02-27 20:00:00.000000000 [America/New_York -05:00 GMT-5 周六]", dtZdt03.dtDetail());
        assertEquals("2021-02-27 18:00:00.000000000 [America/New_York -05:00 GMT-5 周六]", dtZdt04.dtDetail());
        assertEquals("2021-02-27 20:00:00.000000000 [America/New_York -05:00 GMT-5 周六]", dtZdt05.dtDetail());
        assertEquals("2021-02-27 20:00:00.000000000 [America/New_York -05:00 GMT-5 周六]", dtZdt06.dtDetail());
        assertEquals("2021-02-27 21:00:00.000000000 [America/New_York -05:00 GMT-5 周六]", dtZdt07.dtDetail());
        assertEquals("2021-02-27 22:00:00.000000000 [America/New_York -05:00 GMT-5 周六]", dtZdt08.dtDetail());
        assertEquals("2021-02-27 21:00:00.000000000 [America/New_York -05:00 GMT-5 周六]", dtZdt09.dtDetail());
        assertEquals("2021-02-28 00:00:00.000000000 [America/New_York -05:00 GMT-5 周日]", dtZdt10.dtDetail());
        assertEquals("2021-02-27 20:00:00.000000000 [America/New_York -05:00 GMT-5 周六]", dtZdt11.dtDetail());
        assertEquals("2021-02-27 20:00:00.000000000 [America/New_York -05:00 GMT-5 周六]", dtZdt12.dtDetail());
        assertEquals("2021-02-27 20:00:00.000000000 [America/New_York -05:00 GMT-5 周六]", dtZdt13.dtDetail());
        assertEquals("2021-02-27 21:00:00.000000000 [America/New_York -05:00 GMT-5 周六]", dtZdt14.dtDetail());
        assertEquals("2021-02-27 20:00:00.000000000 [America/New_York -05:00 GMT-5 周六]", dtZdt15.dtDetail());
        assertEquals("2021-02-28 09:00:16.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate01.dtDetail());
        assertEquals("2021-02-28 09:00:16.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate02.dtDetail());
        assertEquals("2021-02-28 09:00:16.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate03.dtDetail());
        assertEquals("2021-02-28 09:00:15.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate04.dtDetail());
        assertEquals("2021-02-28 09:00:15.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate05.dtDetail());
        assertEquals("2021-02-28 09:00:16.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate06.dtDetail());
        assertEquals("2021-02-28 09:00:16.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate07.dtDetail());
        assertEquals("2021-02-28 09:00:16.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate08.dtDetail());
        assertEquals("2021-02-28 09:00:18.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate09.dtDetail());
        assertEquals("2021-02-28 09:00:20.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate10.dtDetail());
        assertEquals("2021-02-28 09:00:16.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate11.dtDetail());
        assertEquals("2021-02-28 09:00:16.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate12.dtDetail());
        assertEquals("2021-02-28 09:00:16.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate13.dtDetail());
        assertEquals("2021-02-28 09:00:15.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate14.dtDetail());
        assertEquals("2021-02-28 09:00:18.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate15.dtDetail());

        assertEquals("2021-02-28 09:00:16.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate001.dtDetail());
        assertEquals("2021-02-28 09:00:16.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate002.dtDetail());
        assertEquals("2021-02-28 09:00:16.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate003.dtDetail());
        assertEquals("2021-02-28 09:00:15.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate004.dtDetail());
        assertEquals("2021-02-28 09:00:14.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate005.dtDetail());
        assertEquals("2021-02-28 09:00:16.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate006.dtDetail());
        assertEquals("2021-02-28 09:00:17.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate007.dtDetail());
        assertEquals("2021-02-28 09:00:18.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate008.dtDetail());
        assertEquals("2021-02-28 09:00:18.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate009.dtDetail());
        assertEquals("2021-02-28 09:00:21.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate010.dtDetail());
        assertEquals("2021-02-28 09:00:16.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate011.dtDetail());
        assertEquals("2021-02-28 09:00:16.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate012.dtDetail());
        assertEquals("2021-02-28 09:00:16.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate013.dtDetail());
        assertEquals("2021-02-28 09:00:15.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate014.dtDetail());
        assertEquals("2021-02-28 09:00:14.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate015.dtDetail());
        assertEquals("2021-02-28 09:00:59.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate101.dtDetail());
        assertEquals("2021-02-28 09:00:59.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate102.dtDetail());
        assertEquals("2021-02-28 09:00:58.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate103.dtDetail());
        assertEquals("2021-02-28 09:00:57.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate104.dtDetail());
        assertEquals("2021-02-28 09:00:54.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate105.dtDetail());
        assertEquals("2021-02-28 09:00:59.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate106.dtDetail());
        assertEquals("2021-02-28 09:01:00.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate107.dtDetail());
        assertEquals("2021-02-28 09:01:00.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate108.dtDetail());
        assertEquals("2021-02-28 09:01:00.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate109.dtDetail());
        assertEquals("2021-02-28 09:01:00.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate110.dtDetail());
        assertEquals("2021-02-28 09:00:59.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate111.dtDetail());
        assertEquals("2021-02-28 09:00:59.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate112.dtDetail());
        assertEquals("2021-02-28 09:00:58.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate113.dtDetail());
        assertEquals("2021-02-28 09:01:00.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate114.dtDetail());
        assertEquals("2021-02-28 09:01:00.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dtDate115.dtDetail());

        assertEquals("2021-02-28 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dt01.dtDetail());
        assertEquals("2021-03-01 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周一]", dt02.dtDetail());
        assertEquals("2021-02-28 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dt03.dtDetail());
        assertEquals("2021-02-28 09:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dt04.dtDetail());
        assertEquals("2021-02-28 09:01:00.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dt05.dtDetail());
        assertEquals("2021-02-28 09:01:00.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dt06.dtDetail());

    }

    @Test
    public void testDatesByShift() {
        LocalDateTime ldt = LocalDateTime.of(2022, 2, 27, 8, 0, 10, 100);
        ZonedDateTime zdt = ldt.atZone(TZ.DEFAULT_ZONE);

        ZonedDateTime zdt1 = zdt.withZoneSameInstant(TZ.MOSCOW);
        Instant instant = zdt1.toInstant();
        OffsetDateTime odt = zdt1.toOffsetDateTime().withOffsetSameInstant(ZoneOffset.UTC);
        GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance(TimeZone.getTimeZone(TZ.NEW_YORK));
        calendar.setTime(Date.from(zdt.toInstant()));
        Date date = calendar.getTime();

        DateTime<LocalDateTime> dt1 = DateTime.of(ldt);
        DateTime<ZonedDateTime> dt2 = DateTime.of(zdt);
        DateTime<ZonedDateTime> dt3 = DateTime.of(zdt1);
        DateTime<Instant> dt4 = DateTime.of(instant);
        DateTime<OffsetDateTime> dt5 = DateTime.of(odt);
        DateTime<Calendar> dt6 = DateTime.of(calendar);
        DateTime<Date> dt7 = DateTime.of(date);

        System.out.println("dt1: " + dt1.dtDetail());     // 2022-02-27 08:00:10.000000100 [周日]
        System.out.println("dt2: " + dt2.dtDetail());     // 2022-02-27 08:00:10.000000100 [Asia/Shanghai +08:00 GMT+8 周日]
        System.out.println("dt3: " + dt3.dtDetail());     // 2022-02-27 03:00:10.000000100 [Europe/Moscow +03:00 GMT+3 周日]
        System.out.println("dt4: " + dt4.dtDetail());     // 2022-02-27 08:00:10.000000100 [Asia/Shanghai +08:00 GMT+8 周日]
        System.out.println("dt5: " + dt5.dtDetail());     // 2022-02-27 00:00:10.000000100 [+00:00 GMT 周日]
        System.out.println("dt6: " + dt6.dtDetail());     // 2022-02-26 19:00:10.000000000 [America/New_York -05:00 GMT-5 周六]
        System.out.println("dt7: " + dt7.dtDetail());     // 2022-02-27 08:00:10.000000000 [Asia/Shanghai +08:00 GMT+8 周日]

        System.out.println("============================================");

        List<ZonedDateTime> zonedDateTimes1 = dt3.datesByShift(3, ChronoUnit.DAYS);
        List<ZonedDateTime> zonedDateTimes2 = dt3.datesByShift(-3, ChronoUnit.HOURS);
        List<Instant> instants1 = dt4.datesByShift(3, 3, ChronoUnit.DAYS, true);
        List<Instant> instants2 = dt4.datesByShift(-3, 3, ChronoUnit.DAYS, false);
        List<OffsetDateTime> offsetDateTimes1 = dt5.datesByShift(-3, 90, ChronoUnit.SECONDS, true);
        List<OffsetDateTime> offsetDateTimes2 = dt5.datesByShift(3, 90, ChronoUnit.SECONDS, false);
        List<Calendar> calendars1 = dt6.datesByShift(-2, -15, ChronoUnit.HOURS, true);
        List<Calendar> calendars2 = dt6.datesByShift(3, -15, ChronoUnit.HOURS, false);

        // List<String> dates1 = zonedDateTimes1.stream().map(G::dtDetail).collect(Collectors.toList());
        // List<String> dates2 = zonedDateTimes2.stream().map(G::dtDetail).collect(Collectors.toList());
        // List<String> dates3 = instants1.stream().map(G::dtDetail).collect(Collectors.toList());
        // List<String> dates4 = instants2.stream().map(G::dtDetail).collect(Collectors.toList());
        // List<String> dates5 = offsetDateTimes1.stream().map(G::dtDetail).collect(Collectors.toList());
        // List<String> dates6 = offsetDateTimes2.stream().map(G::dtDetail).collect(Collectors.toList());
        // List<String> dates7 = calendars1.stream().map(G::dtDetail).collect(Collectors.toList());
        // List<String> dates8 = calendars2.stream().map(G::dtDetail).collect(Collectors.toList());

        System.out.println(G.toString(zonedDateTimes1));
        System.out.println(G.toString(zonedDateTimes2));
        System.out.println(G.toString(instants1));
        System.out.println(G.toString(instants2));
        System.out.println(G.toString(offsetDateTimes1));
        System.out.println(G.toString(offsetDateTimes2));
        System.out.println(G.toString(calendars1));
        System.out.println(G.toString(calendars2));

        assertEquals("[2022-02-27 03:00:10.000 [Europe/Moscow +03:00], 2022-02-28 03:00:10.000 [Europe/Moscow +03:00], 2022-03-01 03:00:10.000 [Europe/Moscow +03:00], 2022-03-02 03:00:10.000 [Europe/Moscow +03:00]]", G.toString(zonedDateTimes1));
        assertEquals("[2022-02-27 00:00:10.000 [Europe/Moscow +03:00], 2022-02-27 01:00:10.000 [Europe/Moscow +03:00], 2022-02-27 02:00:10.000 [Europe/Moscow +03:00], 2022-02-27 03:00:10.000 [Europe/Moscow +03:00]]", G.toString(zonedDateTimes2));
        assertEquals("[2022-02-27 08:00:10.000, 2022-03-02 08:00:10.000, 2022-03-05 08:00:10.000, 2022-03-08 08:00:10.000]", G.toString(instants1));
        assertEquals("[2022-02-18 08:00:10.000, 2022-02-21 08:00:10.000, 2022-02-24 08:00:10.000]", G.toString(instants2));
        assertEquals("[2022-02-26 23:55:40.000 [+00:00], 2022-02-26 23:57:10.000 [+00:00], 2022-02-26 23:58:40.000 [+00:00], 2022-02-27 00:00:10.000 [+00:00]]", G.toString(offsetDateTimes1));
        assertEquals("[2022-02-27 00:01:40.000 [+00:00], 2022-02-27 00:03:10.000 [+00:00], 2022-02-27 00:04:40.000 [+00:00]]", G.toString(offsetDateTimes2));
        assertEquals("[2022-02-25 13:00:10.000 [America/New_York -05:00], 2022-02-26 04:00:10.000 [America/New_York -05:00], 2022-02-26 19:00:10.000 [America/New_York -05:00]]", G.toString(calendars1));
        assertEquals("[2022-02-27 10:00:10.000 [America/New_York -05:00], 2022-02-28 01:00:10.000 [America/New_York -05:00], 2022-02-28 16:00:10.000 [America/New_York -05:00]]", G.toString(calendars2));
    }

    @Test
    public void testDatesFromRange() {
        LocalDateTime ldt1 = LocalDateTime.of(2022, 2, 27, 8, 0, 10, 100);
        LocalDateTime ldt2 = LocalDateTime.of(2022, 3, 8, 8, 0, 11, 100);
        LocalDateTime ldt3 = LocalDateTime.of(2022, 3, 8, 8, 0, 9, 100);

        LocalDateTime ldt4 = LocalDateTime.of(2022, 2, 28, 23, 55, 40, 800000000);
        LocalDateTime ldt5 = LocalDateTime.of(2022, 3, 1, 0, 0, 10, 900000000);
        LocalDateTime ldt6 = LocalDateTime.of(2022, 3, 1, 0, 0, 10, 700000000);

        ZonedDateTime zdt1 = ldt1.atZone(TZ.DEFAULT_ZONE);
        ZonedDateTime zdt2 = ldt2.atZone(TZ.DEFAULT_ZONE);
        ZonedDateTime zdt3 = ldt3.atZone(TZ.DEFAULT_ZONE);
        ZonedDateTime zdt4 = ldt4.atZone(TZ.DEFAULT_ZONE);
        ZonedDateTime zdt5 = ldt5.atZone(TZ.DEFAULT_ZONE);
        ZonedDateTime zdt6 = ldt6.atZone(TZ.DEFAULT_ZONE);

        Instant instant1 = zdt1.toInstant();
        OffsetDateTime odt1 = zdt2.toOffsetDateTime().withOffsetSameInstant(ZoneOffset.UTC);
        Calendar calendar1 = DateTime.of(zdt3).toCalendar(TZ.MOSCOW);

        Instant instant2 = zdt4.toInstant();
        OffsetDateTime odt2 = zdt5.toOffsetDateTime().withOffsetSameInstant(ZoneOffset.UTC);
        Calendar calendar2 = DateTime.of(zdt6).toCalendar(TZ.NEW_YORK);

        DateTime<Instant> dt01 = DateTime.of(instant1);         // 2022-02-27 08:00:10.000000100 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<OffsetDateTime> dt02 = DateTime.of(odt1);      // 2022-03-08 00:00:11.000000100 [+00:00 GMT 周二] --> 2022-03-08 08:00:11.000000100 [Asia/Shanghai +08:00 GMT+8 周二]
        DateTime<Calendar> dt03 = DateTime.of(calendar1);       // 2022-03-08 03:00:09.000000000 [Europe/Moscow +03:00 GMT+3 周二] --> 2022-03-08 08:00:09.000000000 [Asia/Shanghai +08:00 GMT+8 周二]

        DateTime<Instant> dt11 = DateTime.of(instant2);         // 2022-02-28 23:55:40.800000000 [Asia/Shanghai +08:00 GMT+8 周一]
        DateTime<OffsetDateTime> dt12 = DateTime.of(odt2);      // 2022-02-28 16:00:10.900000000 [+00:00 GMT 周一] --> 2022-03-01 00:00:10.900000000 [Asia/Shanghai +08:00 GMT+8 周二]
        DateTime<Calendar> dt13 = DateTime.of(calendar2);       // 2022-02-28 11:00:10.700000000 [America/New_York -05:00 GMT-5 周一] --> 2022-03-01 00:00:10.700000000 [Asia/Shanghai +08:00 GMT+8 周二]

        List<Instant> instants01 = dt01.datesFromRange(dt02, 3, ChronoUnit.DAYS, CLOSED);
        List<Instant> instants02 = dt01.datesFromRange(dt03, 3, ChronoUnit.DAYS, CLOSED);
        List<Instant> instants03 = dt01.datesFromRange(dt02, 3, ChronoUnit.DAYS, CLOSED_OPEN);
        List<Instant> instants04 = dt01.datesFromRange(dt03, 3, ChronoUnit.DAYS, CLOSED_OPEN);
        List<Instant> instants05 = dt01.datesFromRange(dt02, 3, ChronoUnit.DAYS, OPEN);
        List<Instant> instants06 = dt01.datesFromRange(dt03, 3, ChronoUnit.DAYS, OPEN);
        List<Instant> instants07 = dt01.datesFromRange(dt02, 3, ChronoUnit.DAYS, OPEN_CLOSED);
        List<Instant> instants08 = dt01.datesFromRange(dt03, 3, ChronoUnit.DAYS, OPEN_CLOSED);
        List<OffsetDateTime> offsetDateTimes01 = dt02.datesFromRange(dt01, 3, ChronoUnit.DAYS, CLOSED);
        List<Calendar> calendars01 = dt03.datesFromRange(dt01, 3, ChronoUnit.DAYS, CLOSED);
        List<OffsetDateTime> offsetDateTimes02 = dt02.datesFromRange(dt01, 3, ChronoUnit.DAYS, CLOSED_OPEN);
        List<Calendar> calendars02 = dt03.datesFromRange(dt01, 3, ChronoUnit.DAYS, CLOSED_OPEN);
        List<OffsetDateTime> offsetDateTimes03 = dt02.datesFromRange(dt01, 3, ChronoUnit.DAYS, OPEN);
        List<Calendar> calendars03 = dt03.datesFromRange(dt01, 3, ChronoUnit.DAYS, OPEN);
        List<OffsetDateTime> offsetDateTimes04 = dt02.datesFromRange(dt01, 3, ChronoUnit.DAYS, OPEN_CLOSED);
        List<Calendar> calendars04 = dt03.datesFromRange(dt01, 3, ChronoUnit.DAYS, OPEN_CLOSED);

        System.out.println(G.toString(instants01));
        System.out.println(G.toString(instants02));
        System.out.println(G.toString(instants03));
        System.out.println(G.toString(instants04));
        System.out.println(G.toString(instants05));
        System.out.println(G.toString(instants06));
        System.out.println(G.toString(instants07));
        System.out.println(G.toString(instants08));
        System.out.println(G.toString(offsetDateTimes01));
        System.out.println(G.toString(calendars01));
        System.out.println(G.toString(offsetDateTimes02));
        System.out.println(G.toString(calendars02));
        System.out.println(G.toString(offsetDateTimes03));
        System.out.println(G.toString(calendars03));
        System.out.println(G.toString(offsetDateTimes04));
        System.out.println(G.toString(calendars04));

        List<Instant> instants11 = dt11.datesFromRange(dt12, -90, ChronoUnit.SECONDS, CLOSED);
        List<Instant> instants12 = dt11.datesFromRange(dt13, -90, ChronoUnit.SECONDS, CLOSED);
        List<Instant> instants13 = dt11.datesFromRange(dt12, -90, ChronoUnit.SECONDS, CLOSED_OPEN);
        List<Instant> instants14 = dt11.datesFromRange(dt13, -90, ChronoUnit.SECONDS, CLOSED_OPEN);
        List<Instant> instants15 = dt11.datesFromRange(dt12, -90, ChronoUnit.SECONDS, OPEN);
        List<Instant> instants16 = dt11.datesFromRange(dt13, -90, ChronoUnit.SECONDS, OPEN);
        List<Instant> instants17 = dt11.datesFromRange(dt12, -90, ChronoUnit.SECONDS, OPEN_CLOSED);
        List<Instant> instants18 = dt11.datesFromRange(dt13, -90, ChronoUnit.SECONDS, OPEN_CLOSED);
        List<OffsetDateTime> offsetDateTimes11 = dt12.datesFromRange(dt11, -90, ChronoUnit.SECONDS, CLOSED);
        List<Calendar> calendars11 = dt13.datesFromRange(dt11, -90, ChronoUnit.SECONDS, CLOSED);
        List<OffsetDateTime> offsetDateTimes12 = dt12.datesFromRange(dt11, -90, ChronoUnit.SECONDS, CLOSED_OPEN);
        List<Calendar> calendars12 = dt13.datesFromRange(dt11, -90, ChronoUnit.SECONDS, CLOSED_OPEN);
        List<OffsetDateTime> offsetDateTimes13 = dt12.datesFromRange(dt11, -90, ChronoUnit.SECONDS, OPEN);
        List<Calendar> calendars13 = dt13.datesFromRange(dt11, -90, ChronoUnit.SECONDS, OPEN);
        List<OffsetDateTime> offsetDateTimes14 = dt12.datesFromRange(dt11, -90, ChronoUnit.SECONDS, OPEN_CLOSED);
        List<Calendar> calendars14 = dt13.datesFromRange(dt11, -90, ChronoUnit.SECONDS, OPEN_CLOSED);

        System.out.println("============================================");
        System.out.println(G.toString(instants11));
        System.out.println(G.toString(instants12));
        System.out.println(G.toString(instants13));
        System.out.println(G.toString(instants14));
        System.out.println(G.toString(instants15));
        System.out.println(G.toString(instants16));
        System.out.println(G.toString(instants17));
        System.out.println(G.toString(instants18));
        System.out.println(G.toString(offsetDateTimes11));
        System.out.println(G.toString(calendars11));
        System.out.println(G.toString(offsetDateTimes12));
        System.out.println(G.toString(calendars12));
        System.out.println(G.toString(offsetDateTimes13));
        System.out.println(G.toString(calendars13));
        System.out.println(G.toString(offsetDateTimes14));
        System.out.println(G.toString(calendars14));

        assertEquals("[2022-02-27 08:00:10.000, 2022-03-02 08:00:10.000, 2022-03-05 08:00:10.000, 2022-03-08 08:00:10.000]", G.toString(instants01));
        assertEquals("[2022-02-27 08:00:10.000, 2022-03-02 08:00:10.000, 2022-03-05 08:00:10.000]", G.toString(instants02));
        assertEquals("[2022-02-27 08:00:10.000, 2022-03-02 08:00:10.000, 2022-03-05 08:00:10.000]", G.toString(instants03));
        assertEquals("[2022-02-27 08:00:10.000, 2022-03-02 08:00:10.000, 2022-03-05 08:00:10.000]", G.toString(instants04));
        assertEquals("[2022-03-02 08:00:10.000, 2022-03-05 08:00:10.000]", G.toString(instants05));
        assertEquals("[2022-03-02 08:00:10.000, 2022-03-05 08:00:10.000]", G.toString(instants06));
        assertEquals("[2022-03-02 08:00:10.000, 2022-03-05 08:00:10.000, 2022-03-08 08:00:10.000]", G.toString(instants07));
        assertEquals("[2022-03-02 08:00:10.000, 2022-03-05 08:00:10.000]", G.toString(instants08));
        assertEquals("[2022-02-27 00:00:11.000 [+00:00], 2022-03-02 00:00:11.000 [+00:00], 2022-03-05 00:00:11.000 [+00:00], 2022-03-08 00:00:11.000 [+00:00]]", G.toString(offsetDateTimes01));
        assertEquals("[2022-03-02 03:00:09.000 [Europe/Moscow +03:00], 2022-03-05 03:00:09.000 [Europe/Moscow +03:00], 2022-03-08 03:00:09.000 [Europe/Moscow +03:00]]", G.toString(calendars01));
        assertEquals("[2022-03-02 00:00:11.000 [+00:00], 2022-03-05 00:00:11.000 [+00:00], 2022-03-08 00:00:11.000 [+00:00]]", G.toString(offsetDateTimes02));
        assertEquals("[2022-03-02 03:00:09.000 [Europe/Moscow +03:00], 2022-03-05 03:00:09.000 [Europe/Moscow +03:00], 2022-03-08 03:00:09.000 [Europe/Moscow +03:00]]", G.toString(calendars02));
        assertEquals("[2022-03-02 00:00:11.000 [+00:00], 2022-03-05 00:00:11.000 [+00:00]]", G.toString(offsetDateTimes03));
        assertEquals("[2022-03-02 03:00:09.000 [Europe/Moscow +03:00], 2022-03-05 03:00:09.000 [Europe/Moscow +03:00]]", G.toString(calendars03));
        assertEquals("[2022-02-27 00:00:11.000 [+00:00], 2022-03-02 00:00:11.000 [+00:00], 2022-03-05 00:00:11.000 [+00:00]]", G.toString(offsetDateTimes04));
        assertEquals("[2022-03-02 03:00:09.000 [Europe/Moscow +03:00], 2022-03-05 03:00:09.000 [Europe/Moscow +03:00]]", G.toString(calendars04));

        assertEquals("[2022-02-28 23:55:40.800, 2022-02-28 23:57:10.800, 2022-02-28 23:58:40.800, 2022-03-01 00:00:10.800]", G.toString(instants11));
        assertEquals("[2022-02-28 23:55:40.800, 2022-02-28 23:57:10.800, 2022-02-28 23:58:40.800]", G.toString(instants12));
        assertEquals("[2022-02-28 23:55:40.800, 2022-02-28 23:57:10.800, 2022-02-28 23:58:40.800]", G.toString(instants13));
        assertEquals("[2022-02-28 23:55:40.800, 2022-02-28 23:57:10.800, 2022-02-28 23:58:40.800]", G.toString(instants14));
        assertEquals("[2022-02-28 23:57:10.800, 2022-02-28 23:58:40.800]", G.toString(instants15));
        assertEquals("[2022-02-28 23:57:10.800, 2022-02-28 23:58:40.800]", G.toString(instants16));
        assertEquals("[2022-02-28 23:57:10.800, 2022-02-28 23:58:40.800, 2022-03-01 00:00:10.800]", G.toString(instants17));
        assertEquals("[2022-02-28 23:57:10.800, 2022-02-28 23:58:40.800]", G.toString(instants18));
        assertEquals("[2022-02-28 15:55:40.900 [+00:00], 2022-02-28 15:57:10.900 [+00:00], 2022-02-28 15:58:40.900 [+00:00], 2022-02-28 16:00:10.900 [+00:00]]", G.toString(offsetDateTimes11));
        assertEquals("[2022-02-28 10:57:10.700 [America/New_York -05:00], 2022-02-28 10:58:40.700 [America/New_York -05:00], 2022-02-28 11:00:10.700 [America/New_York -05:00]]", G.toString(calendars11));
        assertEquals("[2022-02-28 15:57:10.900 [+00:00], 2022-02-28 15:58:40.900 [+00:00], 2022-02-28 16:00:10.900 [+00:00]]", G.toString(offsetDateTimes12));
        assertEquals("[2022-02-28 10:57:10.700 [America/New_York -05:00], 2022-02-28 10:58:40.700 [America/New_York -05:00], 2022-02-28 11:00:10.700 [America/New_York -05:00]]", G.toString(calendars12));
        assertEquals("[2022-02-28 15:57:10.900 [+00:00], 2022-02-28 15:58:40.900 [+00:00]]", G.toString(offsetDateTimes13));
        assertEquals("[2022-02-28 10:57:10.700 [America/New_York -05:00], 2022-02-28 10:58:40.700 [America/New_York -05:00]]", G.toString(calendars13));
        assertEquals("[2022-02-28 15:55:40.900 [+00:00], 2022-02-28 15:57:10.900 [+00:00], 2022-02-28 15:58:40.900 [+00:00]]", G.toString(offsetDateTimes14));
        assertEquals("[2022-02-28 10:57:10.700 [America/New_York -05:00], 2022-02-28 10:58:40.700 [America/New_York -05:00]]", G.toString(calendars14));

    }

    @Test
    public void testParse() {
        DateTime<LocalDateTime> dt0 = DateTime.parse("2022-8-01 10:5:15");
        DateTime<LocalDateTime> dt1 = DateTime.parse("2022-8-01T10:5:15", "y-M-d'T'H:m:s");
        DateTime<LocalDateTime> dt2 = DateTime.parse("2022-8-01T10:5:15.987", "yyyy-MM-dd'T'HH:mm:ss.SSS");
        DateTime<LocalDateTime> dt3 = DateTime.parse("2022", "y");
        DateTime<LocalDateTime> dt4 = DateTime.parse("10点5分", "HH点mm分");
        DateTime<Date> dt00 = DateTime.parseDate("2022-8-01 10:5:15", TZ.UTC);
        DateTime<Date> dt01 = DateTime.parseDate("2022-8-01T10:5:15.987", "yyyy-MM-dd'T'HH:mm:ss.SSS");
        DateTime<Date> dt02 = DateTime.parseDate("2022-8-01T10:5:15.987", "y-M-d'T'H:m:s.SSS", TZ.MOSCOW);
        DateTime<Date> dt03 = DateTime.parseDate("2022", "y");
        DateTime<Date> dt04 = DateTime.parseDate("2点5分", "H点m分", TZ.NEW_YORK);
        System.out.println(dt0.dtDetail());         // 2022-08-01 10:05:15.000000000 [周一]
        System.out.println(dt1.dtDetail());         // 2022-08-01 10:05:15.000000000 [周一]
        System.out.println(dt2.dtDetail());         // 2022-08-01 10:05:15.987000000 [周一]
        System.out.println(dt3.dtDetail());         // 2022-01-01 00:00:00.000000000 [周六]
        System.out.println(dt4.dtDetail());         // 1970-01-01 10:05:00.000000000 [周四]
        System.out.println(dt00.dtDetail());        // 2022-08-01 18:05:15.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        System.out.println(dt01.dtDetail());        // 2022-08-01 10:05:15.987000000 [Asia/Shanghai +08:00 GMT+8 周一]
        System.out.println(dt02.dtDetail());        // 2022-08-01 15:05:15.987000000 [Asia/Shanghai +08:00 GMT+8 周一]
        System.out.println(dt03.dtDetail());        // 2022-01-01 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周六]
        System.out.println(dt04.dtDetail());        // 1970-01-01 15:05:00.000000000 [Asia/Shanghai +08:00 GMT+8 周四]

        assertEquals(dt0.dtDetail(), "2022-08-01 10:05:15.000000000 [周一]");
        assertEquals(dt1.dtDetail(), "2022-08-01 10:05:15.000000000 [周一]");
        assertEquals(dt2.dtDetail(), "2022-08-01 10:05:15.987000000 [周一]");
        assertEquals(dt3.dtDetail(), "2022-01-01 00:00:00.000000000 [周六]");
        assertEquals(dt4.dtDetail(), "1970-01-01 10:05:00.000000000 [周四]");
        assertEquals(dt00.dtDetail(), "2022-08-01 18:05:15.000000000 [Asia/Shanghai +08:00 GMT+8 周一]");
        assertEquals(dt01.dtDetail(), "2022-08-01 10:05:15.987000000 [Asia/Shanghai +08:00 GMT+8 周一]");
        assertEquals(dt02.dtDetail(), "2022-08-01 15:05:15.987000000 [Asia/Shanghai +08:00 GMT+8 周一]");
        assertEquals(dt03.dtDetail(), "2022-01-01 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周六]");
        assertEquals(dt04.dtDetail(), "1970-01-01 15:05:00.000000000 [Asia/Shanghai +08:00 GMT+8 周四]");
    }

    @Test
    public void testParseAuto() {
        DateTime<LocalDateTime> dt0 = DateTime.parseAuto("2022-8-01 10:5:15");  // 2022-08-01 10:05:15.000000000 [周一]
        DateTime<LocalDateTime> dt1 = DateTime.parseAuto("2022-8-01T10:5:15");  // 2022-08-01 10:05:15.000000000 [周一]
        DateTime<LocalDateTime> dt2 = DateTime.parseAuto("2022-8-01T10:5:15.987");  // 2022-08-01 10:05:15.987000000 [周一]
        DateTime<LocalDateTime> dt3 = DateTime.parseAuto("2022");   // 2022-01-01 00:00:00.000000000 [周六]
        DateTime<LocalDateTime> dt4 = DateTime.parseAuto("999");    // 0999-01-01 00:00:00.000000000 [周二]
        DateTime<LocalDateTime> dt5 = DateTime.parseAuto("10点5分");  // 1970-01-01 10:05:00.000000000 [周四]
        DateTime<LocalDateTime> dt6 = DateTime.parseAuto("2022-8-01T10:5:15.98");   // 2022-08-01 10:05:15.980000000 [周一]
        DateTime<LocalDateTime> dt7 = DateTime.parseAuto("202208"); // 2022-08-01 00:00:00.000000000 [周一]
        DateTime<LocalDateTime> dt8 = DateTime.parseAuto("20220810");   // 2022-08-10 00:00:00.000000000 [周三]
        DateTime<LocalDateTime> dt9 = DateTime.parseAuto("2022081017"); // 2022-08-10 17:00:00.000000000 [周三]
        DateTime<LocalDateTime> dt10 = DateTime.parseAuto("202208101706");  // 2022-08-10 17:06:00.000000000 [周三]
        DateTime<LocalDateTime> dt11 = DateTime.parseAuto("20220810170650");    // 2022-08-10 17:06:50.000000000 [周三]
        DateTime<LocalDateTime> dt12 = DateTime.parseAuto("20220810170650666"); // 2022-08-10 17:06:50.666000000 [周三]
        DateTime<Date> dt00 = DateTime.parseDateAuto("2022-8-01 10:5:15", TZ.UTC);  // 2022-08-01 18:05:15.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        DateTime<Date> dt01 = DateTime.parseDateAuto("2022-8-01T10:5:15");  // 2022-08-01 10:05:15.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        DateTime<Date> dt02 = DateTime.parseDateAuto("2022-8-01T10:5:15.987");  // 2022-08-01 10:05:15.987000000 [Asia/Shanghai +08:00 GMT+8 周一]
        DateTime<Date> dt03 = DateTime.parseDateAuto("2022");   // 2022-01-01 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周六]
        DateTime<Date> dt04 = DateTime.parseDateAuto("999");    // 0999-01-01 00:00:00.000000000 [Asia/Shanghai +08:05 GMT+8:05:43 周二]
        DateTime<Date> dt05 = DateTime.parseDateAuto("2点5分", TZ.NEW_YORK);  // 1970-01-01 15:05:00.000000000 [Asia/Shanghai +08:00 GMT+8 周四]
        DateTime<Date> dt06 = DateTime.parseDateAuto("2022-8-01T10:5:15.98");   // 2022-08-01 10:05:15.980000000 [Asia/Shanghai +08:00 GMT+8 周一]

        System.out.println(dt0.dtDetail());         // 2022-08-01 10:05:15.000000000 [周一]
        System.out.println(dt1.dtDetail());         // 2022-08-01 10:05:15.000000000 [周一]
        System.out.println(dt2.dtDetail());         // 2022-08-01 10:05:15.987000000 [周一]
        System.out.println(dt3.dtDetail());         // 2022-01-01 00:00:00.000000000 [周六]
        System.out.println(dt4.dtDetail());         // 0999-01-01 00:00:00.000000000 [周二]
        System.out.println(dt5.dtDetail());         // 1970-01-01 10:05:00.000000000 [周四]
        System.out.println(dt6.dtDetail());         // 2022-08-01 10:05:15.980000000 [周一]
        System.out.println(dt7.dtDetail());         // 2022-08-01 00:00:00.000000000 [周一]
        System.out.println(dt8.dtDetail());         // 2022-08-10 00:00:00.000000000 [周三]
        System.out.println(dt9.dtDetail());         // 2022-08-10 17:00:00.000000000 [周三]
        System.out.println(dt10.dtDetail());        // 2022-08-10 17:06:00.000000000 [周三]
        System.out.println(dt11.dtDetail());        // 2022-08-10 17:06:50.000000000 [周三]
        System.out.println(dt12.dtDetail());        // 2022-08-10 17:06:50.666000000 [周三]
        System.out.println(dt00.dtDetail());        // 2022-08-01 18:05:15.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        System.out.println(dt01.dtDetail());        // 2022-08-01 10:05:15.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        System.out.println(dt02.dtDetail());        // 2022-08-01 10:05:15.987000000 [Asia/Shanghai +08:00 GMT+8 周一]
        System.out.println(dt03.dtDetail());        // 2022-01-01 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周六]
        System.out.println(dt04.dtDetail());        // 0999-01-01 00:00:00.000000000 [Asia/Shanghai +08:05 GMT+8:05:43 周二]
        System.out.println(dt05.dtDetail());        // 1970-01-01 15:05:00.000000000 [Asia/Shanghai +08:00 GMT+8 周四]
        System.out.println(dt06.dtDetail());        // 2022-08-01 10:05:15.980000000 [Asia/Shanghai +08:00 GMT+8 周一]

        assertEquals(dt0.dtDetail(), "2022-08-01 10:05:15.000000000 [周一]");
        assertEquals(dt1.dtDetail(), "2022-08-01 10:05:15.000000000 [周一]");
        assertEquals(dt2.dtDetail(), "2022-08-01 10:05:15.987000000 [周一]");
        assertEquals(dt3.dtDetail(), "2022-01-01 00:00:00.000000000 [周六]");
        assertEquals(dt4.dtDetail(), "0999-01-01 00:00:00.000000000 [周二]");
        assertEquals(dt5.dtDetail(), "1970-01-01 10:05:00.000000000 [周四]");
        assertEquals(dt6.dtDetail(), "2022-08-01 10:05:15.980000000 [周一]");
        assertEquals(dt7.dtDetail(), "2022-08-01 00:00:00.000000000 [周一]");
        assertEquals(dt8.dtDetail(), "2022-08-10 00:00:00.000000000 [周三]");
        assertEquals(dt9.dtDetail(), "2022-08-10 17:00:00.000000000 [周三]");
        assertEquals(dt10.dtDetail(), "2022-08-10 17:06:00.000000000 [周三]");
        assertEquals(dt11.dtDetail(), "2022-08-10 17:06:50.000000000 [周三]");
        assertEquals(dt12.dtDetail(), "2022-08-10 17:06:50.666000000 [周三]");
        assertEquals(dt00.dtDetail(), "2022-08-01 18:05:15.000000000 [Asia/Shanghai +08:00 GMT+8 周一]");
        assertEquals(dt01.dtDetail(), "2022-08-01 10:05:15.000000000 [Asia/Shanghai +08:00 GMT+8 周一]");
        assertEquals(dt02.dtDetail(), "2022-08-01 10:05:15.987000000 [Asia/Shanghai +08:00 GMT+8 周一]");
        assertEquals(dt03.dtDetail(), "2022-01-01 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周六]");
        assertEquals(dt04.dtDetail(), "0999-01-01 00:00:00.000000000 [Asia/Shanghai +08:05 GMT+8:05:43 周二]");
        assertEquals(dt05.dtDetail(), "1970-01-01 15:05:00.000000000 [Asia/Shanghai +08:00 GMT+8 周四]");
        assertEquals(dt06.dtDetail(), "2022-08-01 10:05:15.980000000 [Asia/Shanghai +08:00 GMT+8 周一]");

        assertThrows(DateTimeParseException.class, () -> DateTime.parseAuto("2022081017065066"));
        assertThrows(DateTimeParseException.class, () -> DateTime.parseDateAuto("20228"));
        assertThrows(DateTimeParseException.class, () -> DateTime.parseDateAuto("2022810", TZ.NEW_YORK));

    }

    @Test
    public void testFill() {
        DateTime<LocalDateTime> dt1 = DateTime.parse("2022-08-01 10:05:15.987", "yyyy-MM-dd HH:mm:ss.SSS");
        DateTime<Date> dt2 = DateTime.parseDate("2022-08-01 10:05:15.987", "yyyy-MM-dd HH:mm:ss.SSS");
        DateTime<LocalDateTime> dt3 = DateTime.parse("2022-07-10 10:05:15.987", "yyyy-MM-dd HH:mm:ss.SSS");
        DateTime<Date> dt4 = DateTime.parseDate("2022-07-10 10:05:15.987", "yyyy-MM-dd HH:mm:ss.SSS");

        DateTime<ZonedDateTime> zonedDT = DateTime.of(DateTime.parse("2022-02-10 10:05:15.987654789", "yyyy-MM-dd HH:mm:ss.SSSSSSSSS").toZonedDT(TZ.DUBAI));
        System.out.println(zonedDT.dtDetail());        // 2022-02-10 10:05:15.987654789 [Asia/Dubai +04:00 GMT+4 周四]

        DateTime<LocalDateTime> dt01 = dt1.fill0(ChronoUnit.MONTHS);        // 2022-08-01 00:00:00.000000000 [周一]
        DateTime<LocalDateTime> dt02 = dt1.fill0(ChronoUnit.DAYS);      // 2022-08-01 00:00:00.000000000 [周一]
        DateTime<LocalDateTime> dt03 = dt1.fill0(ChronoUnit.HOURS);     // 2022-08-01 10:00:00.000000000 [周一]
        DateTime<Date> dt04 = dt2.fill0(ChronoUnit.MONTHS);     // 2022-08-01 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        DateTime<Date> dt05 = dt2.fill0(ChronoUnit.DAYS);       // 2022-08-01 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        DateTime<Date> dt06 = dt2.fill0(ChronoUnit.HOURS);      // 2022-08-01 10:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        DateTime<LocalDateTime> dt07 = dt3.fill0(ChronoUnit.MONTHS);        // 2022-07-01 00:00:00.000000000 [周五]
        DateTime<LocalDateTime> dt08 = dt3.fill0(ChronoUnit.DAYS);      // 2022-07-10 00:00:00.000000000 [周日]
        DateTime<LocalDateTime> dt09 = dt3.fill0(ChronoUnit.HOURS);     // 2022-07-10 10:00:00.000000000 [周日]
        DateTime<Date> dt10 = dt4.fill0(ChronoUnit.MONTHS);     // 2022-07-01 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周五]
        DateTime<Date> dt11 = dt4.fill0(ChronoUnit.DAYS);       // 2022-07-10 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dt12 = dt4.fill0(ChronoUnit.HOURS);      // 2022-07-10 10:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<LocalDateTime> dt21 = dt1.fill9(ChronoUnit.MONTHS);        // 2022-08-31 23:59:59.999999999 [周三]
        DateTime<LocalDateTime> dt22 = dt1.fill9(ChronoUnit.DAYS);      // 2022-08-01 23:59:59.999999999 [周一]
        DateTime<LocalDateTime> dt23 = dt1.fill9(ChronoUnit.HOURS);     // 2022-08-01 10:59:59.999999999 [周一]
        DateTime<Date> dt24 = dt2.fill9(ChronoUnit.MONTHS);     // 2022-08-31 23:59:59.999000000 [Asia/Shanghai +08:00 GMT+8 周三]
        DateTime<Date> dt25 = dt2.fill9(ChronoUnit.DAYS);       // 2022-08-01 23:59:59.999000000 [Asia/Shanghai +08:00 GMT+8 周一]
        DateTime<Date> dt26 = dt2.fill9(ChronoUnit.HOURS);      // 2022-08-01 10:59:59.999000000 [Asia/Shanghai +08:00 GMT+8 周一]
        DateTime<LocalDateTime> dt27 = dt3.fill9(ChronoUnit.MONTHS);        // 2022-07-31 23:59:59.999999999 [周日]
        DateTime<LocalDateTime> dt28 = dt3.fill9(ChronoUnit.DAYS);      // 2022-07-10 23:59:59.999999999 [周日]
        DateTime<LocalDateTime> dt29 = dt3.fill9(ChronoUnit.HOURS);     // 2022-07-10 10:59:59.999999999 [周日]
        DateTime<Date> dt30 = dt4.fill9(ChronoUnit.MONTHS);     // 2022-07-31 23:59:59.999000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dt31 = dt4.fill9(ChronoUnit.DAYS);       // 2022-07-10 23:59:59.999000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dt32 = dt4.fill9(ChronoUnit.HOURS);      // 2022-07-10 10:59:59.999000000 [Asia/Shanghai +08:00 GMT+8 周日]

        DateTime<LocalDateTime> dateTime1 = dt1.fill0(ChronoUnit.YEARS);        // 2022-01-01 00:00:00.000000000 [周六]
        DateTime<Date> dateTime2 = dt2.fill0(ChronoUnit.YEARS);             // 2022-01-01 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周六]
        DateTime<LocalDateTime> dateTime3 = dt3.fill0(ChronoUnit.YEARS);        // 2022-01-01 00:00:00.000000000 [周六]
        DateTime<Date> dateTime4 = dt4.fill0(ChronoUnit.YEARS);             // 2022-01-01 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周六]
        DateTime<LocalDateTime> dateTime5 = dt1.fill9(ChronoUnit.YEARS);        // 2022-12-31 23:59:59.999999999 [周六]
        DateTime<Date> dateTime6 = dt2.fill9(ChronoUnit.YEARS);             // 2022-12-31 23:59:59.999000000 [Asia/Shanghai +08:00 GMT+8 周六]
        DateTime<LocalDateTime> dateTime7 = dt3.fill9(ChronoUnit.YEARS);        // 2022-12-31 23:59:59.999999999 [周六]
        DateTime<Date> dateTime8 = dt4.fill9(ChronoUnit.YEARS);             // 2022-12-31 23:59:59.999000000 [Asia/Shanghai +08:00 GMT+8 周六]

        DateTime<ZonedDateTime> zdt01 = zonedDT.fill0(ChronoUnit.YEARS);    // 2022-01-01 00:00:00.000000000 [Asia/Dubai +04:00 GMT+4 周六]
        DateTime<ZonedDateTime> zdt02 = zonedDT.fill0(ChronoUnit.MONTHS);   // 2022-02-01 00:00:00.000000000 [Asia/Dubai +04:00 GMT+4 周二]
        DateTime<ZonedDateTime> zdt03 = zonedDT.fill0(ChronoUnit.DAYS);     // 2022-02-10 00:00:00.000000000 [Asia/Dubai +04:00 GMT+4 周四]
        DateTime<ZonedDateTime> zdt04 = zonedDT.fill0(ChronoUnit.HOURS);    // 2022-02-10 10:00:00.000000000 [Asia/Dubai +04:00 GMT+4 周四]
        DateTime<ZonedDateTime> zdt05 = zonedDT.fill0(ChronoUnit.MINUTES);  // 2022-02-10 10:05:00.000000000 [Asia/Dubai +04:00 GMT+4 周四]
        DateTime<ZonedDateTime> zdt06 = zonedDT.fill9(ChronoUnit.YEARS);    // 2022-12-31 23:59:59.999999999 [Asia/Dubai +04:00 GMT+4 周六]
        DateTime<ZonedDateTime> zdt07 = zonedDT.fill9(ChronoUnit.MONTHS);   // 2022-02-28 23:59:59.999999999 [Asia/Dubai +04:00 GMT+4 周一]
        DateTime<ZonedDateTime> zdt08 = zonedDT.fill9(ChronoUnit.DAYS);     // 2022-02-10 23:59:59.999999999 [Asia/Dubai +04:00 GMT+4 周四]
        DateTime<ZonedDateTime> zdt09 = zonedDT.fill9(ChronoUnit.HOURS);    // 2022-02-10 10:59:59.999999999 [Asia/Dubai +04:00 GMT+4 周四]
        DateTime<ZonedDateTime> zdt10 = zonedDT.fill9(ChronoUnit.MINUTES);  // 2022-02-10 10:05:59.999999999 [Asia/Dubai +04:00 GMT+4 周四]

        System.out.println(dt01.dtDetail());
        System.out.println(dt02.dtDetail());
        System.out.println(dt03.dtDetail());
        System.out.println(dt04.dtDetail());
        System.out.println(dt05.dtDetail());
        System.out.println(dt06.dtDetail());
        System.out.println(dt07.dtDetail());
        System.out.println(dt08.dtDetail());
        System.out.println(dt09.dtDetail());
        System.out.println(dt10.dtDetail());
        System.out.println(dt11.dtDetail());
        System.out.println(dt12.dtDetail());
        System.out.println(dt21.dtDetail());
        System.out.println(dt22.dtDetail());
        System.out.println(dt23.dtDetail());
        System.out.println(dt24.dtDetail());
        System.out.println(dt25.dtDetail());
        System.out.println(dt26.dtDetail());
        System.out.println(dt27.dtDetail());
        System.out.println(dt28.dtDetail());
        System.out.println(dt29.dtDetail());
        System.out.println(dt30.dtDetail());
        System.out.println(dt31.dtDetail());
        System.out.println(dt32.dtDetail());

        System.out.println(dateTime1.dtDetail());
        System.out.println(dateTime2.dtDetail());
        System.out.println(dateTime3.dtDetail());
        System.out.println(dateTime4.dtDetail());
        System.out.println(dateTime5.dtDetail());
        System.out.println(dateTime6.dtDetail());
        System.out.println(dateTime7.dtDetail());
        System.out.println(dateTime8.dtDetail());

        System.out.println(zdt01.dtDetail());
        System.out.println(zdt02.dtDetail());
        System.out.println(zdt03.dtDetail());
        System.out.println(zdt04.dtDetail());
        System.out.println(zdt05.dtDetail());
        System.out.println(zdt06.dtDetail());
        System.out.println(zdt07.dtDetail());
        System.out.println(zdt08.dtDetail());
        System.out.println(zdt09.dtDetail());
        System.out.println(zdt10.dtDetail());

        assertEquals(dt01.dtDetail(), "2022-08-01 00:00:00.000000000 [周一]");
        assertEquals(dt02.dtDetail(), "2022-08-01 00:00:00.000000000 [周一]");
        assertEquals(dt03.dtDetail(), "2022-08-01 10:00:00.000000000 [周一]");
        assertEquals(dt04.dtDetail(), "2022-08-01 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周一]");
        assertEquals(dt05.dtDetail(), "2022-08-01 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周一]");
        assertEquals(dt06.dtDetail(), "2022-08-01 10:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周一]");
        assertEquals(dt07.dtDetail(), "2022-07-01 00:00:00.000000000 [周五]");
        assertEquals(dt08.dtDetail(), "2022-07-10 00:00:00.000000000 [周日]");
        assertEquals(dt09.dtDetail(), "2022-07-10 10:00:00.000000000 [周日]");
        assertEquals(dt10.dtDetail(), "2022-07-01 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周五]");
        assertEquals(dt11.dtDetail(), "2022-07-10 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周日]");
        assertEquals(dt12.dtDetail(), "2022-07-10 10:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周日]");
        assertEquals(dt21.dtDetail(), "2022-08-31 23:59:59.999999999 [周三]");
        assertEquals(dt22.dtDetail(), "2022-08-01 23:59:59.999999999 [周一]");
        assertEquals(dt23.dtDetail(), "2022-08-01 10:59:59.999999999 [周一]");
        assertEquals(dt24.dtDetail(), "2022-08-31 23:59:59.999000000 [Asia/Shanghai +08:00 GMT+8 周三]");
        assertEquals(dt25.dtDetail(), "2022-08-01 23:59:59.999000000 [Asia/Shanghai +08:00 GMT+8 周一]");
        assertEquals(dt26.dtDetail(), "2022-08-01 10:59:59.999000000 [Asia/Shanghai +08:00 GMT+8 周一]");
        assertEquals(dt27.dtDetail(), "2022-07-31 23:59:59.999999999 [周日]");
        assertEquals(dt28.dtDetail(), "2022-07-10 23:59:59.999999999 [周日]");
        assertEquals(dt29.dtDetail(), "2022-07-10 10:59:59.999999999 [周日]");
        assertEquals(dt30.dtDetail(), "2022-07-31 23:59:59.999000000 [Asia/Shanghai +08:00 GMT+8 周日]");
        assertEquals(dt31.dtDetail(), "2022-07-10 23:59:59.999000000 [Asia/Shanghai +08:00 GMT+8 周日]");
        assertEquals(dt32.dtDetail(), "2022-07-10 10:59:59.999000000 [Asia/Shanghai +08:00 GMT+8 周日]");

        assertEquals(dateTime1.dtDetail(), "2022-01-01 00:00:00.000000000 [周六]");
        assertEquals(dateTime2.dtDetail(), "2022-01-01 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周六]");
        assertEquals(dateTime3.dtDetail(), "2022-01-01 00:00:00.000000000 [周六]");
        assertEquals(dateTime4.dtDetail(), "2022-01-01 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 周六]");
        assertEquals(dateTime5.dtDetail(), "2022-12-31 23:59:59.999999999 [周六]");
        assertEquals(dateTime6.dtDetail(), "2022-12-31 23:59:59.999000000 [Asia/Shanghai +08:00 GMT+8 周六]");
        assertEquals(dateTime7.dtDetail(), "2022-12-31 23:59:59.999999999 [周六]");
        assertEquals(dateTime8.dtDetail(), "2022-12-31 23:59:59.999000000 [Asia/Shanghai +08:00 GMT+8 周六]");

        assertEquals(zdt01.dtDetail(), "2022-01-01 00:00:00.000000000 [Asia/Dubai +04:00 GMT+4 周六]");
        assertEquals(zdt02.dtDetail(), "2022-02-01 00:00:00.000000000 [Asia/Dubai +04:00 GMT+4 周二]");
        assertEquals(zdt03.dtDetail(), "2022-02-10 00:00:00.000000000 [Asia/Dubai +04:00 GMT+4 周四]");
        assertEquals(zdt04.dtDetail(), "2022-02-10 10:00:00.000000000 [Asia/Dubai +04:00 GMT+4 周四]");
        assertEquals(zdt05.dtDetail(), "2022-02-10 10:05:00.000000000 [Asia/Dubai +04:00 GMT+4 周四]");
        assertEquals(zdt06.dtDetail(), "2022-12-31 23:59:59.999999999 [Asia/Dubai +04:00 GMT+4 周六]");
        assertEquals(zdt07.dtDetail(), "2022-02-28 23:59:59.999999999 [Asia/Dubai +04:00 GMT+4 周一]");
        assertEquals(zdt08.dtDetail(), "2022-02-10 23:59:59.999999999 [Asia/Dubai +04:00 GMT+4 周四]");
        assertEquals(zdt09.dtDetail(), "2022-02-10 10:59:59.999999999 [Asia/Dubai +04:00 GMT+4 周四]");
        assertEquals(zdt10.dtDetail(), "2022-02-10 10:05:59.999999999 [Asia/Dubai +04:00 GMT+4 周四]");

    }

    @Test
    public void testIn() {
        DateTime<LocalDateTime> ldt1 = DateTime.parse("2022-8-06 9:10:21.689");
        DateTime<LocalDateTime> ldt2 = DateTime.parse("2022-8-06 11:10:21.689");
        DateTime<LocalDateTime> ldt3 = DateTime.parse("2022-8-06 10:10:21.689");
        DateTime<Date> date1 = DateTime.parseDate("2022-8-06 9:10:21.689");
        DateTime<Date> date2 = DateTime.parseDate("2022-8-06 11:10:21.689");
        DateTime<Date> date3 = DateTime.parseDate("2022-8-06 10:10:21.689");

        boolean in01 = ldt1.in(date1, ldt2, CLOSED);            // true
        boolean in02 = ldt1.in(date1, ldt2, CLOSED_OPEN);       // true
        boolean in03 = ldt1.in(date1, ldt2, OPEN);              // false
        boolean in04 = ldt1.in(date1, ldt2, OPEN_CLOSED);       // false
        boolean in05 = date2.in(ldt3, ldt2, CLOSED);            // true
        boolean in06 = date2.in(ldt3, ldt2, CLOSED_OPEN);       // false
        boolean in07 = date2.in(ldt3, ldt2, OPEN);              // false
        boolean in08 = date2.in(ldt3, ldt2, OPEN_CLOSED);       // true
        boolean in09 = date3.in(ldt1, ldt2, CLOSED);            // true
        boolean in10 = date3.in(ldt1, ldt2, CLOSED_OPEN);       // true
        boolean in11 = date3.in(ldt1, ldt2, OPEN);              // true
        boolean in12 = date3.in(ldt1, ldt2, OPEN_CLOSED);       // true

        assertTrue(in01);
        assertTrue(in02);
        assertFalse(in03);
        assertFalse(in04);
        assertTrue(in05);
        assertFalse(in06);
        assertFalse(in07);
        assertTrue(in08);
        assertTrue(in09);
        assertTrue(in10);
        assertTrue(in11);
        assertTrue(in12);

        assertThrows(UnexpectedParameterException.class, () -> ldt1.in(date2, ldt2, CLOSED_OPEN));
        assertThrows(UnexpectedParameterException.class, () -> ldt1.in(date2, ldt3, CLOSED_OPEN));
        assertThrows(UnexpectedParameterException.class, () -> ldt1.in(date1, ldt1, CLOSED_OPEN));
    }

    @Test
    public void testInThisWeek() {
        DateTime<Date> dt1 = DateTime.parseDate("2021-12-27 06:01:50", "yyyy-MM-dd HH:mm:ss");  // 星期一
        DateTime<Date> dt2 = DateTime.parseDate("2021-12-29 06:01:50", "yyyy-MM-dd HH:mm:ss");  // 星期三
        DateTime<Date> dt3 = DateTime.parseDate("2022-01-01 06:01:50", "yyyy-MM-dd HH:mm:ss");  // 星期六
        DateTime<Date> dt4 = DateTime.parseDate("2022-01-02 06:01:50", "yyyy-MM-dd HH:mm:ss");  // 星期日

        DateTime<Date> dt01 = dt1.dtInThisWeek(DayOfWeek.TUESDAY);              // 2021-12-28 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周二]
        DateTime<Date> dt02 = dt1.dtInThisWeek(DayOfWeek.THURSDAY);             // 2021-12-30 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周四]
        DateTime<Date> dt03 = dt1.dtInThisWeek(DayOfWeek.MONDAY);               // 2021-12-27 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        DateTime<Date> dt04 = dt1.dtInThisWeek(DayOfWeek.SUNDAY);               // 2022-01-02 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dt05 = dt1.firstInThisWeek();                // 2021-12-27 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        DateTime<Date> dt06 = dt1.lastInThisWeek();             // 2022-01-02 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dt11 = dt2.dtInThisWeek(DayOfWeek.TUESDAY);              // 2021-12-28 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周二]
        DateTime<Date> dt12 = dt2.dtInThisWeek(DayOfWeek.THURSDAY);             // 2021-12-30 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周四]
        DateTime<Date> dt13 = dt2.dtInThisWeek(DayOfWeek.MONDAY);               // 2021-12-27 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        DateTime<Date> dt14 = dt2.dtInThisWeek(DayOfWeek.SUNDAY);               // 2022-01-02 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dt15 = dt2.firstInThisWeek();                // 2021-12-27 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        DateTime<Date> dt16 = dt2.lastInThisWeek();             // 2022-01-02 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dt21 = dt3.dtInThisWeek(DayOfWeek.TUESDAY);              // 2021-12-28 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周二]
        DateTime<Date> dt22 = dt3.dtInThisWeek(DayOfWeek.THURSDAY);             // 2021-12-30 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周四]
        DateTime<Date> dt23 = dt3.dtInThisWeek(DayOfWeek.MONDAY);               // 2021-12-27 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        DateTime<Date> dt24 = dt3.dtInThisWeek(DayOfWeek.SUNDAY);               // 2022-01-02 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dt25 = dt3.firstInThisWeek();                // 2021-12-27 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        DateTime<Date> dt26 = dt3.lastInThisWeek();             // 2022-01-02 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dt31 = dt4.dtInThisWeek(DayOfWeek.TUESDAY);              // 2021-12-28 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周二]
        DateTime<Date> dt32 = dt4.dtInThisWeek(DayOfWeek.THURSDAY);             // 2021-12-30 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周四]
        DateTime<Date> dt33 = dt4.dtInThisWeek(DayOfWeek.MONDAY);               // 2021-12-27 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        DateTime<Date> dt34 = dt4.dtInThisWeek(DayOfWeek.SUNDAY);               // 2022-01-02 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dt35 = dt4.firstInThisWeek();                // 2021-12-27 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        DateTime<Date> dt36 = dt4.lastInThisWeek();             // 2022-01-02 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dt001 = dt1.dtInThisWeek(DayOfWeek.SUNDAY, DayOfWeek.TUESDAY);               // 2021-12-28 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周二]
        DateTime<Date> dt002 = dt1.dtInThisWeek(DayOfWeek.SUNDAY, DayOfWeek.THURSDAY);              // 2021-12-30 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周四]
        DateTime<Date> dt003 = dt1.dtInThisWeek(DayOfWeek.SUNDAY, DayOfWeek.MONDAY);                // 2021-12-27 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        DateTime<Date> dt004 = dt1.dtInThisWeek(DayOfWeek.SUNDAY, DayOfWeek.SUNDAY);                // 2021-12-26 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dt005 = dt1.firstInThisWeek(DayOfWeek.SUNDAY);               // 2021-12-26 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dt006 = dt1.lastInThisWeek(DayOfWeek.SUNDAY);                // 2022-01-01 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周六]
        DateTime<Date> dt011 = dt2.dtInThisWeek(DayOfWeek.SUNDAY, DayOfWeek.TUESDAY);               // 2021-12-28 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周二]
        DateTime<Date> dt012 = dt2.dtInThisWeek(DayOfWeek.SUNDAY, DayOfWeek.THURSDAY);              // 2021-12-30 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周四]
        DateTime<Date> dt013 = dt2.dtInThisWeek(DayOfWeek.SUNDAY, DayOfWeek.MONDAY);                // 2021-12-27 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        DateTime<Date> dt014 = dt2.dtInThisWeek(DayOfWeek.SUNDAY, DayOfWeek.SUNDAY);                // 2021-12-26 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dt015 = dt2.firstInThisWeek(DayOfWeek.SUNDAY);               // 2021-12-26 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dt016 = dt2.lastInThisWeek(DayOfWeek.SUNDAY);                // 2022-01-01 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周六]
        DateTime<Date> dt021 = dt3.dtInThisWeek(DayOfWeek.SUNDAY, DayOfWeek.TUESDAY);               // 2021-12-28 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周二]
        DateTime<Date> dt022 = dt3.dtInThisWeek(DayOfWeek.SUNDAY, DayOfWeek.THURSDAY);              // 2021-12-30 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周四]
        DateTime<Date> dt023 = dt3.dtInThisWeek(DayOfWeek.SUNDAY, DayOfWeek.MONDAY);                // 2021-12-27 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        DateTime<Date> dt024 = dt3.dtInThisWeek(DayOfWeek.SUNDAY, DayOfWeek.SUNDAY);                // 2021-12-26 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dt025 = dt3.firstInThisWeek(DayOfWeek.SUNDAY);               // 2021-12-26 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dt026 = dt3.lastInThisWeek(DayOfWeek.SUNDAY);                // 2022-01-01 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周六]
        DateTime<Date> dt031 = dt4.dtInThisWeek(DayOfWeek.SUNDAY, DayOfWeek.TUESDAY);               // 2022-01-04 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周二]
        DateTime<Date> dt032 = dt4.dtInThisWeek(DayOfWeek.SUNDAY, DayOfWeek.THURSDAY);              // 2022-01-06 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周四]
        DateTime<Date> dt033 = dt4.dtInThisWeek(DayOfWeek.SUNDAY, DayOfWeek.MONDAY);                // 2022-01-03 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周一]
        DateTime<Date> dt034 = dt4.dtInThisWeek(DayOfWeek.SUNDAY, DayOfWeek.SUNDAY);                // 2022-01-02 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dt035 = dt4.firstInThisWeek(DayOfWeek.SUNDAY);               // 2022-01-02 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周日]
        DateTime<Date> dt036 = dt4.lastInThisWeek(DayOfWeek.SUNDAY);                // 2022-01-08 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周六]

        assertEquals("2021-12-28 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周二]", dt01.dtDetail());
        assertEquals("2021-12-30 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周四]", dt02.dtDetail());
        assertEquals("2021-12-27 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周一]", dt03.dtDetail());
        assertEquals("2022-01-02 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dt04.dtDetail());
        assertEquals("2021-12-27 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周一]", dt05.dtDetail());
        assertEquals("2022-01-02 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dt06.dtDetail());
        assertEquals("2021-12-28 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周二]", dt11.dtDetail());
        assertEquals("2021-12-30 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周四]", dt12.dtDetail());
        assertEquals("2021-12-27 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周一]", dt13.dtDetail());
        assertEquals("2022-01-02 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dt14.dtDetail());
        assertEquals("2021-12-27 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周一]", dt15.dtDetail());
        assertEquals("2022-01-02 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dt16.dtDetail());
        assertEquals("2021-12-28 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周二]", dt21.dtDetail());
        assertEquals("2021-12-30 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周四]", dt22.dtDetail());
        assertEquals("2021-12-27 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周一]", dt23.dtDetail());
        assertEquals("2022-01-02 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dt24.dtDetail());
        assertEquals("2021-12-27 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周一]", dt25.dtDetail());
        assertEquals("2022-01-02 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dt26.dtDetail());
        assertEquals("2021-12-28 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周二]", dt31.dtDetail());
        assertEquals("2021-12-30 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周四]", dt32.dtDetail());
        assertEquals("2021-12-27 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周一]", dt33.dtDetail());
        assertEquals("2022-01-02 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dt34.dtDetail());
        assertEquals("2021-12-27 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周一]", dt35.dtDetail());
        assertEquals("2022-01-02 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dt36.dtDetail());
        assertEquals("2021-12-28 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周二]", dt001.dtDetail());
        assertEquals("2021-12-30 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周四]", dt002.dtDetail());
        assertEquals("2021-12-27 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周一]", dt003.dtDetail());
        assertEquals("2021-12-26 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dt004.dtDetail());
        assertEquals("2021-12-26 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dt005.dtDetail());
        assertEquals("2022-01-01 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周六]", dt006.dtDetail());
        assertEquals("2021-12-28 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周二]", dt011.dtDetail());
        assertEquals("2021-12-30 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周四]", dt012.dtDetail());
        assertEquals("2021-12-27 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周一]", dt013.dtDetail());
        assertEquals("2021-12-26 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dt014.dtDetail());
        assertEquals("2021-12-26 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dt015.dtDetail());
        assertEquals("2022-01-01 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周六]", dt016.dtDetail());
        assertEquals("2021-12-28 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周二]", dt021.dtDetail());
        assertEquals("2021-12-30 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周四]", dt022.dtDetail());
        assertEquals("2021-12-27 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周一]", dt023.dtDetail());
        assertEquals("2021-12-26 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dt024.dtDetail());
        assertEquals("2021-12-26 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dt025.dtDetail());
        assertEquals("2022-01-01 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周六]", dt026.dtDetail());
        assertEquals("2022-01-04 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周二]", dt031.dtDetail());
        assertEquals("2022-01-06 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周四]", dt032.dtDetail());
        assertEquals("2022-01-03 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周一]", dt033.dtDetail());
        assertEquals("2022-01-02 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dt034.dtDetail());
        assertEquals("2022-01-02 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周日]", dt035.dtDetail());
        assertEquals("2022-01-08 06:01:50.000000000 [Asia/Shanghai +08:00 GMT+8 周六]", dt036.dtDetail());

        List<DateTime<Date>> dateTimes1 = dt1.allDaysInThisWeek();
        List<DateTime<Date>> dateTimes2 = dt2.allDaysInThisWeek();
        List<DateTime<Date>> dateTimes3 = dt3.allDaysInThisWeek();
        List<DateTime<Date>> dateTimes4 = dt4.allDaysInThisWeek();
        List<DateTime<Date>> dateTimes5 = dt1.allDaysInThisWeek(DayOfWeek.SUNDAY);
        List<DateTime<Date>> dateTimes6 = dt2.allDaysInThisWeek(DayOfWeek.SUNDAY);
        List<DateTime<Date>> dateTimes7 = dt3.allDaysInThisWeek(DayOfWeek.SUNDAY);
        List<DateTime<Date>> dateTimes8 = dt4.allDaysInThisWeek(DayOfWeek.SUNDAY);

        assertEquals("[2021-12-27 06:01:50.000, 2021-12-28 06:01:50.000, 2021-12-29 06:01:50.000, 2021-12-30 06:01:50.000, " +
                "2021-12-31 06:01:50.000, 2022-01-01 06:01:50.000, 2022-01-02 06:01:50.000]", dateTimes1.toString());
        assertEquals("[2021-12-27 06:01:50.000, 2021-12-28 06:01:50.000, 2021-12-29 06:01:50.000, 2021-12-30 06:01:50.000, " +
         "2021-12-31 06:01:50.000, 2022-01-01 06:01:50.000, 2022-01-02 06:01:50.000]", dateTimes2.toString());
        assertEquals("[2021-12-27 06:01:50.000, 2021-12-28 06:01:50.000, 2021-12-29 06:01:50.000, 2021-12-30 06:01:50.000, " +
         "2021-12-31 06:01:50.000, 2022-01-01 06:01:50.000, 2022-01-02 06:01:50.000]", dateTimes3.toString());
        assertEquals("[2021-12-27 06:01:50.000, 2021-12-28 06:01:50.000, 2021-12-29 06:01:50.000, 2021-12-30 06:01:50.000, " +
         "2021-12-31 06:01:50.000, 2022-01-01 06:01:50.000, 2022-01-02 06:01:50.000]", dateTimes4.toString());
        assertEquals("[2021-12-26 06:01:50.000, 2021-12-27 06:01:50.000, 2021-12-28 06:01:50.000, 2021-12-29 06:01:50.000, " +
         "2021-12-30 06:01:50.000, 2021-12-31 06:01:50.000, 2022-01-01 06:01:50.000]", dateTimes5.toString());
        assertEquals("[2021-12-26 06:01:50.000, 2021-12-27 06:01:50.000, 2021-12-28 06:01:50.000, 2021-12-29 06:01:50.000, " +
                "2021-12-30 06:01:50.000, 2021-12-31 06:01:50.000, 2022-01-01 06:01:50.000]", dateTimes6.toString());
        assertEquals("[2021-12-26 06:01:50.000, 2021-12-27 06:01:50.000, 2021-12-28 06:01:50.000, 2021-12-29 06:01:50.000, " +
         "2021-12-30 06:01:50.000, 2021-12-31 06:01:50.000, 2022-01-01 06:01:50.000]", dateTimes7.toString());
        assertEquals("[2022-01-02 06:01:50.000, 2022-01-03 06:01:50.000, 2022-01-04 06:01:50.000, 2022-01-05 06:01:50.000, " +
         "2022-01-06 06:01:50.000, 2022-01-07 06:01:50.000, 2022-01-08 06:01:50.000]", dateTimes8.toString());


    }

    @Test
    public void testNow() {
        DateTime<LocalDateTime> nowLocalDT = DateTime.now();
        DateTime<Date> nowDate = DateTime.nowDate();
        DateTime<Instant> nowInstant = DateTime.nowInstant();
        System.out.println("DateTime.now(): " + nowLocalDT);
        System.out.println("DateTime.nowDate(): " + nowDate);
        System.out.println("DateTime.nowInstant(): " + nowInstant);
    }


}
