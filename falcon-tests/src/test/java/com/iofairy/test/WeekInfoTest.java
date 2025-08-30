package com.iofairy.test;

import com.iofairy.except.OutOfBoundsException;
import com.iofairy.falcon.time.DateTime;
import com.iofairy.falcon.time.WeekInfo;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.WeekFields;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static com.iofairy.falcon.time.DTC.*;

/**
 * @author GG
 * @version 1.0
 */
@Deprecated
public class WeekInfoTest {

    @Test
    public void testDateTimeGetWeekInfo() {
        DateTime<Date> dt01 = DateTime.parseDate("2023-7-1");           // 2023-07-01 [星期六]
        DateTime<Date> dt02 = DateTime.parseDate("2023-7-31");          // 2023-07-31 [星期一]
        DateTime<Date> dt03 = DateTime.parseDate("2023-8-1");           // 2023-08-01 [星期二]
        DateTime<Date> dt04 = DateTime.parseDate("2023-1-1");           // 2023-01-01 [星期日]
        DateTime<Date> dt05 = DateTime.parseDate("2022-12-31");         // 2022-12-31 [星期六]
        DateTime<LocalDateTime> dt06 = DateTime.parse("2020-1-1");      // 2020-01-01 [星期三]
        DateTime<LocalDateTime> dt07 = DateTime.parse("2019-12-31");    // 2019-12-31 [星期二]

        WeekInfo weekInfo01 = dt01.getWeekInfo();
        WeekInfo weekInfo02 = dt02.getWeekInfo();
        WeekInfo weekInfo03 = dt03.getWeekInfo();
        WeekInfo weekInfo04 = dt04.getWeekInfo();
        WeekInfo weekInfo05 = dt05.getWeekInfo();
        WeekInfo weekInfo06 = dt06.getWeekInfo();
        WeekInfo weekInfo07 = dt07.getWeekInfo();
        WeekInfo weekInfo11 = dt01.getWeekInfo(WEEK_ISO);
        WeekInfo weekInfo12 = dt02.getWeekInfo(WEEK_ISO);
        WeekInfo weekInfo13 = dt03.getWeekInfo(WEEK_ISO);
        WeekInfo weekInfo14 = dt04.getWeekInfo(WEEK_ISO);
        WeekInfo weekInfo15 = dt05.getWeekInfo(WEEK_ISO);
        WeekInfo weekInfo16 = dt06.getWeekInfo(WEEK_ISO);
        WeekInfo weekInfo17 = dt07.getWeekInfo(WEEK_ISO);

        System.out.println(weekInfo01);
        System.out.println(weekInfo02);
        System.out.println(weekInfo03);
        System.out.println(weekInfo04);
        System.out.println(weekInfo05);
        System.out.println(weekInfo06);
        System.out.println(weekInfo07);
        System.out.println(weekInfo11);
        System.out.println(weekInfo12);
        System.out.println(weekInfo13);
        System.out.println(weekInfo14);
        System.out.println(weekInfo15);
        System.out.println(weekInfo16);
        System.out.println(weekInfo17);

        assertEquals(weekInfo01.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期六, MONDAY,   7), [localDate<dayIndexOfWeek>]=(2023-07-01<6>, 2023-06-26 ~ 2023-07-02), [minYear<days>, maxYear<days>, isCrossYear]=(2023<7>, 2023<7>, false), [minMonth<days>, maxMonth<days>, isCrossMonth]=(06<5>, 07<2>, true ), [weekYear, weekBasedYear]=(2023-W26, 2023-W26), [weekMonth, weekBasedMonth]=(07-W00, 06-W04)}");
        assertEquals(weekInfo02.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期一, MONDAY,   7), [localDate<dayIndexOfWeek>]=(2023-07-31<1>, 2023-07-31 ~ 2023-08-06), [minYear<days>, maxYear<days>, isCrossYear]=(2023<7>, 2023<7>, false), [minMonth<days>, maxMonth<days>, isCrossMonth]=(07<1>, 08<6>, true ), [weekYear, weekBasedYear]=(2023-W31, 2023-W31), [weekMonth, weekBasedMonth]=(07-W05, 07-W05)}");
        assertEquals(weekInfo03.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期二, MONDAY,   7), [localDate<dayIndexOfWeek>]=(2023-08-01<2>, 2023-07-31 ~ 2023-08-06), [minYear<days>, maxYear<days>, isCrossYear]=(2023<7>, 2023<7>, false), [minMonth<days>, maxMonth<days>, isCrossMonth]=(07<1>, 08<6>, true ), [weekYear, weekBasedYear]=(2023-W31, 2023-W31), [weekMonth, weekBasedMonth]=(08-W00, 07-W05)}");
        assertEquals(weekInfo04.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期日, MONDAY,   7), [localDate<dayIndexOfWeek>]=(2023-01-01<7>, 2022-12-26 ~ 2023-01-01), [minYear<days>, maxYear<days>, isCrossYear]=(2022<6>, 2023<1>, true ), [minMonth<days>, maxMonth<days>, isCrossMonth]=(12<6>, 01<1>, true ), [weekYear, weekBasedYear]=(2023-W00, 2022-W52), [weekMonth, weekBasedMonth]=(01-W00, 12-W04)}");
        assertEquals(weekInfo05.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期六, MONDAY,   7), [localDate<dayIndexOfWeek>]=(2022-12-31<6>, 2022-12-26 ~ 2023-01-01), [minYear<days>, maxYear<days>, isCrossYear]=(2022<6>, 2023<1>, true ), [minMonth<days>, maxMonth<days>, isCrossMonth]=(12<6>, 01<1>, true ), [weekYear, weekBasedYear]=(2022-W52, 2022-W52), [weekMonth, weekBasedMonth]=(12-W04, 12-W04)}");
        assertEquals(weekInfo06.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期三, MONDAY,   7), [localDate<dayIndexOfWeek>]=(2020-01-01<3>, 2019-12-30 ~ 2020-01-05), [minYear<days>, maxYear<days>, isCrossYear]=(2019<2>, 2020<5>, true ), [minMonth<days>, maxMonth<days>, isCrossMonth]=(12<2>, 01<5>, true ), [weekYear, weekBasedYear]=(2020-W00, 2019-W52), [weekMonth, weekBasedMonth]=(01-W00, 12-W05)}");
        assertEquals(weekInfo07.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期二, MONDAY,   7), [localDate<dayIndexOfWeek>]=(2019-12-31<2>, 2019-12-30 ~ 2020-01-05), [minYear<days>, maxYear<days>, isCrossYear]=(2019<2>, 2020<5>, true ), [minMonth<days>, maxMonth<days>, isCrossMonth]=(12<2>, 01<5>, true ), [weekYear, weekBasedYear]=(2019-W52, 2019-W52), [weekMonth, weekBasedMonth]=(12-W05, 12-W05)}");
        assertEquals(weekInfo11.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期六, MONDAY,   4), [localDate<dayIndexOfWeek>]=(2023-07-01<6>, 2023-06-26 ~ 2023-07-02), [minYear<days>, maxYear<days>, isCrossYear]=(2023<7>, 2023<7>, false), [minMonth<days>, maxMonth<days>, isCrossMonth]=(06<5>, 07<2>, true ), [weekYear, weekBasedYear]=(2023-W26, 2023-W26), [weekMonth, weekBasedMonth]=(07-W00, 06-W05)}");
        assertEquals(weekInfo12.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期一, MONDAY,   4), [localDate<dayIndexOfWeek>]=(2023-07-31<1>, 2023-07-31 ~ 2023-08-06), [minYear<days>, maxYear<days>, isCrossYear]=(2023<7>, 2023<7>, false), [minMonth<days>, maxMonth<days>, isCrossMonth]=(07<1>, 08<6>, true ), [weekYear, weekBasedYear]=(2023-W31, 2023-W31), [weekMonth, weekBasedMonth]=(07-W05, 08-W01)}");
        assertEquals(weekInfo13.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期二, MONDAY,   4), [localDate<dayIndexOfWeek>]=(2023-08-01<2>, 2023-07-31 ~ 2023-08-06), [minYear<days>, maxYear<days>, isCrossYear]=(2023<7>, 2023<7>, false), [minMonth<days>, maxMonth<days>, isCrossMonth]=(07<1>, 08<6>, true ), [weekYear, weekBasedYear]=(2023-W31, 2023-W31), [weekMonth, weekBasedMonth]=(08-W01, 08-W01)}");
        assertEquals(weekInfo14.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期日, MONDAY,   4), [localDate<dayIndexOfWeek>]=(2023-01-01<7>, 2022-12-26 ~ 2023-01-01), [minYear<days>, maxYear<days>, isCrossYear]=(2022<6>, 2023<1>, true ), [minMonth<days>, maxMonth<days>, isCrossMonth]=(12<6>, 01<1>, true ), [weekYear, weekBasedYear]=(2023-W00, 2022-W52), [weekMonth, weekBasedMonth]=(01-W00, 12-W05)}");
        assertEquals(weekInfo15.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期六, MONDAY,   4), [localDate<dayIndexOfWeek>]=(2022-12-31<6>, 2022-12-26 ~ 2023-01-01), [minYear<days>, maxYear<days>, isCrossYear]=(2022<6>, 2023<1>, true ), [minMonth<days>, maxMonth<days>, isCrossMonth]=(12<6>, 01<1>, true ), [weekYear, weekBasedYear]=(2022-W52, 2022-W52), [weekMonth, weekBasedMonth]=(12-W05, 12-W05)}");
        assertEquals(weekInfo16.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期三, MONDAY,   4), [localDate<dayIndexOfWeek>]=(2020-01-01<3>, 2019-12-30 ~ 2020-01-05), [minYear<days>, maxYear<days>, isCrossYear]=(2019<2>, 2020<5>, true ), [minMonth<days>, maxMonth<days>, isCrossMonth]=(12<2>, 01<5>, true ), [weekYear, weekBasedYear]=(2020-W01, 2020-W01), [weekMonth, weekBasedMonth]=(01-W01, 01-W01)}");
        assertEquals(weekInfo17.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期二, MONDAY,   4), [localDate<dayIndexOfWeek>]=(2019-12-31<2>, 2019-12-30 ~ 2020-01-05), [minYear<days>, maxYear<days>, isCrossYear]=(2019<2>, 2020<5>, true ), [minMonth<days>, maxMonth<days>, isCrossMonth]=(12<2>, 01<5>, true ), [weekYear, weekBasedYear]=(2019-W53, 2020-W01), [weekMonth, weekBasedMonth]=(12-W05, 01-W01)}");

    }

    @Test
    public void testWeekInfo() {
        DateTime<Date> dt01 = DateTime.parseDate("2023-7-1");           // 2023-07-01 [星期六]
        DateTime<Date> dt02 = DateTime.parseDate("2023-7-31");          // 2023-07-31 [星期一]
        DateTime<Date> dt03 = DateTime.parseDate("2023-8-1");           // 2023-08-01 [星期二]
        DateTime<Date> dt04 = DateTime.parseDate("2023-1-1");           // 2023-01-01 [星期日]
        Date dt05 = DateTime.parseDate("2022-12-31").get();         // 2022-12-31 [星期六]
        DateTime<LocalDateTime> dt06 = DateTime.parse("2020-1-1");      // 2020-01-01 [星期三]
        DateTime<LocalDateTime> dt07 = DateTime.parse("2019-12-31");    // 2019-12-31 [星期二]


        WeekFields weekFields = MONDAY_MIN7;

        WeekInfo weekInfo01 = WeekInfo.of(weekFields, dt01).baseYearMonth();
        WeekInfo weekInfo02 = WeekInfo.of(weekFields, dt02).baseYearMonth();
        WeekInfo weekInfo03 = WeekInfo.of(weekFields, dt03).baseYearMonth();
        WeekInfo weekInfo04 = WeekInfo.of(weekFields, dt04).baseYearMonth();
        WeekInfo weekInfo05 = WeekInfo.of(weekFields, dt05).baseYearMonth();
        WeekInfo weekInfo06 = WeekInfo.of(weekFields, dt06).baseYearMonth();
        WeekInfo weekInfo07 = WeekInfo.of(weekFields, dt07).baseYearMonth();

        weekFields = WEEK_ISO;
        WeekInfo weekInfo11 = WeekInfo.of(weekFields, dt01).baseYearMonth();
        WeekInfo weekInfo12 = WeekInfo.of(weekFields, dt02).baseYearMonth();
        WeekInfo weekInfo13 = WeekInfo.of(weekFields, dt03).baseYearMonth();
        WeekInfo weekInfo14 = WeekInfo.of(weekFields, dt04).baseYearMonth();
        WeekInfo weekInfo15 = WeekInfo.of(weekFields, dt05).baseYearMonth();
        WeekInfo weekInfo16 = WeekInfo.of(weekFields, dt06).baseYearMonth();
        WeekInfo weekInfo17 = WeekInfo.of(weekFields, dt07).baseYearMonth();

        weekFields = SUNDAY_MIN1;
        WeekInfo weekInfo21 = WeekInfo.of(weekFields, dt01).baseYearMonth();
        WeekInfo weekInfo22 = WeekInfo.of(weekFields, dt02).baseYearMonth();
        WeekInfo weekInfo23 = WeekInfo.of(weekFields, dt03).baseYearMonth();
        WeekInfo weekInfo24 = WeekInfo.of(weekFields, dt04).baseYearMonth();
        WeekInfo weekInfo25 = WeekInfo.of(weekFields, dt05).baseYearMonth();
        WeekInfo weekInfo26 = WeekInfo.of(weekFields, dt06).baseYearMonth();
        WeekInfo weekInfo27 = WeekInfo.of(weekFields, dt07).baseYearMonth();

        weekFields = null;
        WeekInfo weekInfo31 = WeekInfo.of(weekFields, dt01).baseYearMonth();
        WeekInfo weekInfo32 = WeekInfo.of(weekFields, dt02).baseYearMonth();
        WeekInfo weekInfo33 = WeekInfo.of(weekFields, dt03).baseYearMonth();
        WeekInfo weekInfo34 = WeekInfo.of(weekFields, dt04).baseYearMonth();
        WeekInfo weekInfo35 = WeekInfo.of(weekFields, dt05).baseYearMonth();
        WeekInfo weekInfo36 = WeekInfo.of(weekFields, dt06).baseYearMonth();
        WeekInfo weekInfo37 = WeekInfo.of(weekFields, dt07).baseYearMonth();

        System.out.println(weekInfo01);
        System.out.println(weekInfo02);
        System.out.println(weekInfo03);
        System.out.println(weekInfo04);
        System.out.println(weekInfo05);
        System.out.println(weekInfo06);
        System.out.println(weekInfo07);
        System.out.println(weekInfo11);
        System.out.println(weekInfo12);
        System.out.println(weekInfo13);
        System.out.println(weekInfo14);
        System.out.println(weekInfo15);
        System.out.println(weekInfo16);
        System.out.println(weekInfo17);
        System.out.println(weekInfo21);
        System.out.println(weekInfo22);
        System.out.println(weekInfo23);
        System.out.println(weekInfo24);
        System.out.println(weekInfo25);
        System.out.println(weekInfo26);
        System.out.println(weekInfo27);
        System.out.println(weekInfo31);
        System.out.println(weekInfo32);
        System.out.println(weekInfo33);
        System.out.println(weekInfo34);
        System.out.println(weekInfo35);
        System.out.println(weekInfo36);
        System.out.println(weekInfo37);
        System.out.println("============================================================");
        String format01 = "weekInfo01: " + weekInfo01.getLocalDate() + " ***dayIndexOfWeek: " + weekInfo01.getDayIndexOfWeek() + " *** " + weekInfo01.getWeekFields() + " *** " + weekInfo01.format() + " === " + weekInfo01.format("Y-'W'ww") + " === " + weekInfo01.formatWeek() + " === " + weekInfo01.formatWeek("Y-'W'w") + " === " + weekInfo01.format("Y年M月第W周") + " === " + weekInfo01.formatWeek("Y年M月'w'WW");
        String format02 = "weekInfo02: " + weekInfo02.getLocalDate() + " ***dayIndexOfWeek: " + weekInfo02.getDayIndexOfWeek() + " *** " + weekInfo02.getWeekFields() + " *** " + weekInfo02.format() + " === " + weekInfo02.format("Y-'W'ww") + " === " + weekInfo02.formatWeek() + " === " + weekInfo02.formatWeek("Y-'W'w") + " === " + weekInfo02.format("Y年M月第W周") + " === " + weekInfo02.formatWeek("Y年M月'w'WW");
        String format03 = "weekInfo03: " + weekInfo03.getLocalDate() + " ***dayIndexOfWeek: " + weekInfo03.getDayIndexOfWeek() + " *** " + weekInfo03.getWeekFields() + " *** " + weekInfo03.format() + " === " + weekInfo03.format("Y-'W'ww") + " === " + weekInfo03.formatWeek() + " === " + weekInfo03.formatWeek("Y-'W'w") + " === " + weekInfo03.format("Y年M月第W周") + " === " + weekInfo03.formatWeek("Y年M月'w'WW");
        String format04 = "weekInfo04: " + weekInfo04.getLocalDate() + " ***dayIndexOfWeek: " + weekInfo04.getDayIndexOfWeek() + " *** " + weekInfo04.getWeekFields() + " *** " + weekInfo04.format() + " === " + weekInfo04.format("Y-'W'ww") + " === " + weekInfo04.formatWeek() + " === " + weekInfo04.formatWeek("Y-'W'w") + " === " + weekInfo04.format("Y年M月第W周") + " === " + weekInfo04.formatWeek("Y年M月'w'WW");
        String format05 = "weekInfo05: " + weekInfo05.getLocalDate() + " ***dayIndexOfWeek: " + weekInfo05.getDayIndexOfWeek() + " *** " + weekInfo05.getWeekFields() + " *** " + weekInfo05.format() + " === " + weekInfo05.format("Y-'W'ww") + " === " + weekInfo05.formatWeek() + " === " + weekInfo05.formatWeek("Y-'W'w") + " === " + weekInfo05.format("Y年M月第W周") + " === " + weekInfo05.formatWeek("Y年M月'w'WW");
        String format06 = "weekInfo06: " + weekInfo06.getLocalDate() + " ***dayIndexOfWeek: " + weekInfo06.getDayIndexOfWeek() + " *** " + weekInfo06.getWeekFields() + " *** " + weekInfo06.format() + " === " + weekInfo06.format("Y-'W'ww") + " === " + weekInfo06.formatWeek() + " === " + weekInfo06.formatWeek("Y-'W'w") + " === " + weekInfo06.format("Y年M月第W周") + " === " + weekInfo06.formatWeek("Y年M月'w'WW");
        String format07 = "weekInfo07: " + weekInfo07.getLocalDate() + " ***dayIndexOfWeek: " + weekInfo07.getDayIndexOfWeek() + " *** " + weekInfo07.getWeekFields() + " *** " + weekInfo07.format() + " === " + weekInfo07.format("Y-'W'ww") + " === " + weekInfo07.formatWeek() + " === " + weekInfo07.formatWeek("Y-'W'w") + " === " + weekInfo07.format("Y年M月第W周") + " === " + weekInfo07.formatWeek("Y年M月'w'WW");
        String format11 = "weekInfo11: " + weekInfo11.getLocalDate() + " ***dayIndexOfWeek: " + weekInfo11.getDayIndexOfWeek() + " *** " + weekInfo11.getWeekFields() + " *** " + weekInfo11.format() + " === " + weekInfo11.format("Y-'W'ww") + " === " + weekInfo11.formatWeek() + " === " + weekInfo11.formatWeek("Y-'W'w") + " === " + weekInfo11.format("Y年M月第W周") + " === " + weekInfo11.formatWeek("Y年M月'w'WW");
        String format12 = "weekInfo12: " + weekInfo12.getLocalDate() + " ***dayIndexOfWeek: " + weekInfo12.getDayIndexOfWeek() + " *** " + weekInfo12.getWeekFields() + " *** " + weekInfo12.format() + " === " + weekInfo12.format("Y-'W'ww") + " === " + weekInfo12.formatWeek() + " === " + weekInfo12.formatWeek("Y-'W'w") + " === " + weekInfo12.format("Y年M月第W周") + " === " + weekInfo12.formatWeek("Y年M月'w'WW");
        String format13 = "weekInfo13: " + weekInfo13.getLocalDate() + " ***dayIndexOfWeek: " + weekInfo13.getDayIndexOfWeek() + " *** " + weekInfo13.getWeekFields() + " *** " + weekInfo13.format() + " === " + weekInfo13.format("Y-'W'ww") + " === " + weekInfo13.formatWeek() + " === " + weekInfo13.formatWeek("Y-'W'w") + " === " + weekInfo13.format("Y年M月第W周") + " === " + weekInfo13.formatWeek("Y年M月'w'WW");
        String format14 = "weekInfo14: " + weekInfo14.getLocalDate() + " ***dayIndexOfWeek: " + weekInfo14.getDayIndexOfWeek() + " *** " + weekInfo14.getWeekFields() + " *** " + weekInfo14.format() + " === " + weekInfo14.format("Y-'W'ww") + " === " + weekInfo14.formatWeek() + " === " + weekInfo14.formatWeek("Y-'W'w") + " === " + weekInfo14.format("Y年M月第W周") + " === " + weekInfo14.formatWeek("Y年M月'w'WW");
        String format15 = "weekInfo15: " + weekInfo15.getLocalDate() + " ***dayIndexOfWeek: " + weekInfo15.getDayIndexOfWeek() + " *** " + weekInfo15.getWeekFields() + " *** " + weekInfo15.format() + " === " + weekInfo15.format("Y-'W'ww") + " === " + weekInfo15.formatWeek() + " === " + weekInfo15.formatWeek("Y-'W'w") + " === " + weekInfo15.format("Y年M月第W周") + " === " + weekInfo15.formatWeek("Y年M月'w'WW");
        String format16 = "weekInfo16: " + weekInfo16.getLocalDate() + " ***dayIndexOfWeek: " + weekInfo16.getDayIndexOfWeek() + " *** " + weekInfo16.getWeekFields() + " *** " + weekInfo16.format() + " === " + weekInfo16.format("Y-'W'ww") + " === " + weekInfo16.formatWeek() + " === " + weekInfo16.formatWeek("Y-'W'w") + " === " + weekInfo16.format("Y年M月第W周") + " === " + weekInfo16.formatWeek("Y年M月'w'WW");
        String format17 = "weekInfo17: " + weekInfo17.getLocalDate() + " ***dayIndexOfWeek: " + weekInfo17.getDayIndexOfWeek() + " *** " + weekInfo17.getWeekFields() + " *** " + weekInfo17.format() + " === " + weekInfo17.format("Y-'W'ww") + " === " + weekInfo17.formatWeek() + " === " + weekInfo17.formatWeek("Y-'W'w") + " === " + weekInfo17.format("Y年M月第W周") + " === " + weekInfo17.formatWeek("Y年M月'w'WW");
        String format21 = "weekInfo21: " + weekInfo21.getLocalDate() + " ***dayIndexOfWeek: " + weekInfo21.getDayIndexOfWeek() + " *** " + weekInfo21.getWeekFields() + " *** " + weekInfo21.format() + " === " + weekInfo21.format("Y-'W'ww") + " === " + weekInfo21.formatWeek() + " === " + weekInfo21.formatWeek("Y-'W'w") + " === " + weekInfo21.format("Y年M月第W周") + " === " + weekInfo21.formatWeek("Y年M月'w'WW");
        String format22 = "weekInfo22: " + weekInfo22.getLocalDate() + " ***dayIndexOfWeek: " + weekInfo22.getDayIndexOfWeek() + " *** " + weekInfo22.getWeekFields() + " *** " + weekInfo22.format() + " === " + weekInfo22.format("Y-'W'ww") + " === " + weekInfo22.formatWeek() + " === " + weekInfo22.formatWeek("Y-'W'w") + " === " + weekInfo22.format("Y年M月第W周") + " === " + weekInfo22.formatWeek("Y年M月'w'WW");
        String format23 = "weekInfo23: " + weekInfo23.getLocalDate() + " ***dayIndexOfWeek: " + weekInfo23.getDayIndexOfWeek() + " *** " + weekInfo23.getWeekFields() + " *** " + weekInfo23.format() + " === " + weekInfo23.format("Y-'W'ww") + " === " + weekInfo23.formatWeek() + " === " + weekInfo23.formatWeek("Y-'W'w") + " === " + weekInfo23.format("Y年M月第W周") + " === " + weekInfo23.formatWeek("Y年M月'w'WW");
        String format24 = "weekInfo24: " + weekInfo24.getLocalDate() + " ***dayIndexOfWeek: " + weekInfo24.getDayIndexOfWeek() + " *** " + weekInfo24.getWeekFields() + " *** " + weekInfo24.format() + " === " + weekInfo24.format("Y-'W'ww") + " === " + weekInfo24.formatWeek() + " === " + weekInfo24.formatWeek("Y-'W'w") + " === " + weekInfo24.format("Y年M月第W周") + " === " + weekInfo24.formatWeek("Y年M月'w'WW");
        String format25 = "weekInfo25: " + weekInfo25.getLocalDate() + " ***dayIndexOfWeek: " + weekInfo25.getDayIndexOfWeek() + " *** " + weekInfo25.getWeekFields() + " *** " + weekInfo25.format() + " === " + weekInfo25.format("Y-'W'ww") + " === " + weekInfo25.formatWeek() + " === " + weekInfo25.formatWeek("Y-'W'w") + " === " + weekInfo25.format("Y年M月第W周") + " === " + weekInfo25.formatWeek("Y年M月'w'WW");
        String format26 = "weekInfo26: " + weekInfo26.getLocalDate() + " ***dayIndexOfWeek: " + weekInfo26.getDayIndexOfWeek() + " *** " + weekInfo26.getWeekFields() + " *** " + weekInfo26.format() + " === " + weekInfo26.format("Y-'W'ww") + " === " + weekInfo26.formatWeek() + " === " + weekInfo26.formatWeek("Y-'W'w") + " === " + weekInfo26.format("Y年M月第W周") + " === " + weekInfo26.formatWeek("Y年M月'w'WW");
        String format27 = "weekInfo27: " + weekInfo27.getLocalDate() + " ***dayIndexOfWeek: " + weekInfo27.getDayIndexOfWeek() + " *** " + weekInfo27.getWeekFields() + " *** " + weekInfo27.format() + " === " + weekInfo27.format("Y-'W'ww") + " === " + weekInfo27.formatWeek() + " === " + weekInfo27.formatWeek("Y-'W'w") + " === " + weekInfo27.format("Y年M月第W周") + " === " + weekInfo27.formatWeek("Y年M月'w'WW");
        String format31 = "weekInfo31: " + weekInfo31.getLocalDate() + " ***dayIndexOfWeek: " + weekInfo31.getDayIndexOfWeek() + " *** " + weekInfo31.getWeekFields() + " *** " + weekInfo31.format() + " === " + weekInfo31.format("Y-'W'ww") + " === " + weekInfo31.formatWeek() + " === " + weekInfo31.formatWeek("Y-'W'w") + " === " + weekInfo31.format("Y年M月第W周") + " === " + weekInfo31.formatWeek("Y年M月'w'WW");
        String format32 = "weekInfo32: " + weekInfo32.getLocalDate() + " ***dayIndexOfWeek: " + weekInfo32.getDayIndexOfWeek() + " *** " + weekInfo32.getWeekFields() + " *** " + weekInfo32.format() + " === " + weekInfo32.format("Y-'W'ww") + " === " + weekInfo32.formatWeek() + " === " + weekInfo32.formatWeek("Y-'W'w") + " === " + weekInfo32.format("Y年M月第W周") + " === " + weekInfo32.formatWeek("Y年M月'w'WW");
        String format33 = "weekInfo33: " + weekInfo33.getLocalDate() + " ***dayIndexOfWeek: " + weekInfo33.getDayIndexOfWeek() + " *** " + weekInfo33.getWeekFields() + " *** " + weekInfo33.format() + " === " + weekInfo33.format("Y-'W'ww") + " === " + weekInfo33.formatWeek() + " === " + weekInfo33.formatWeek("Y-'W'w") + " === " + weekInfo33.format("Y年M月第W周") + " === " + weekInfo33.formatWeek("Y年M月'w'WW");
        String format34 = "weekInfo34: " + weekInfo34.getLocalDate() + " ***dayIndexOfWeek: " + weekInfo34.getDayIndexOfWeek() + " *** " + weekInfo34.getWeekFields() + " *** " + weekInfo34.format() + " === " + weekInfo34.format("Y-'W'ww") + " === " + weekInfo34.formatWeek() + " === " + weekInfo34.formatWeek("Y-'W'w") + " === " + weekInfo34.format("Y年M月第W周") + " === " + weekInfo34.formatWeek("Y年M月'w'WW");
        String format35 = "weekInfo35: " + weekInfo35.getLocalDate() + " ***dayIndexOfWeek: " + weekInfo35.getDayIndexOfWeek() + " *** " + weekInfo35.getWeekFields() + " *** " + weekInfo35.format() + " === " + weekInfo35.format("Y-'W'ww") + " === " + weekInfo35.formatWeek() + " === " + weekInfo35.formatWeek("Y-'W'w") + " === " + weekInfo35.format("Y年M月第W周") + " === " + weekInfo35.formatWeek("Y年M月'w'WW");
        String format36 = "weekInfo36: " + weekInfo36.getLocalDate() + " ***dayIndexOfWeek: " + weekInfo36.getDayIndexOfWeek() + " *** " + weekInfo36.getWeekFields() + " *** " + weekInfo36.format() + " === " + weekInfo36.format("Y-'W'ww") + " === " + weekInfo36.formatWeek() + " === " + weekInfo36.formatWeek("Y-'W'w") + " === " + weekInfo36.format("Y年M月第W周") + " === " + weekInfo36.formatWeek("Y年M月'w'WW");
        String format37 = "weekInfo37: " + weekInfo37.getLocalDate() + " ***dayIndexOfWeek: " + weekInfo37.getDayIndexOfWeek() + " *** " + weekInfo37.getWeekFields() + " *** " + weekInfo37.format() + " === " + weekInfo37.format("Y-'W'ww") + " === " + weekInfo37.formatWeek() + " === " + weekInfo37.formatWeek("Y-'W'w") + " === " + weekInfo37.format("Y年M月第W周") + " === " + weekInfo37.formatWeek("Y年M月'w'WW");


        assertEquals(weekInfo01.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期六, MONDAY,   7), [localDate<dayIndexOfWeek>]=(2023-07-01<6>, 2023-06-26 ~ 2023-07-02), [minYear<days>, maxYear<days>, isCrossYear]=(2023<7>, 2023<7>, false), [minMonth<days>, maxMonth<days>, isCrossMonth]=(06<5>, 07<2>, true ), [weekYear, weekBasedYear]=(2023-W26, 2023-W26), [weekMonth, weekBasedMonth]=(07-W00, 06-W04)}");
        assertEquals(weekInfo02.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期一, MONDAY,   7), [localDate<dayIndexOfWeek>]=(2023-07-31<1>, 2023-07-31 ~ 2023-08-06), [minYear<days>, maxYear<days>, isCrossYear]=(2023<7>, 2023<7>, false), [minMonth<days>, maxMonth<days>, isCrossMonth]=(07<1>, 08<6>, true ), [weekYear, weekBasedYear]=(2023-W31, 2023-W31), [weekMonth, weekBasedMonth]=(07-W05, 07-W05)}");
        assertEquals(weekInfo03.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期二, MONDAY,   7), [localDate<dayIndexOfWeek>]=(2023-08-01<2>, 2023-07-31 ~ 2023-08-06), [minYear<days>, maxYear<days>, isCrossYear]=(2023<7>, 2023<7>, false), [minMonth<days>, maxMonth<days>, isCrossMonth]=(07<1>, 08<6>, true ), [weekYear, weekBasedYear]=(2023-W31, 2023-W31), [weekMonth, weekBasedMonth]=(08-W00, 07-W05)}");
        assertEquals(weekInfo04.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期日, MONDAY,   7), [localDate<dayIndexOfWeek>]=(2023-01-01<7>, 2022-12-26 ~ 2023-01-01), [minYear<days>, maxYear<days>, isCrossYear]=(2022<6>, 2023<1>, true ), [minMonth<days>, maxMonth<days>, isCrossMonth]=(12<6>, 01<1>, true ), [weekYear, weekBasedYear]=(2023-W00, 2022-W52), [weekMonth, weekBasedMonth]=(01-W00, 12-W04)}");
        assertEquals(weekInfo05.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期六, MONDAY,   7), [localDate<dayIndexOfWeek>]=(2022-12-31<6>, 2022-12-26 ~ 2023-01-01), [minYear<days>, maxYear<days>, isCrossYear]=(2022<6>, 2023<1>, true ), [minMonth<days>, maxMonth<days>, isCrossMonth]=(12<6>, 01<1>, true ), [weekYear, weekBasedYear]=(2022-W52, 2022-W52), [weekMonth, weekBasedMonth]=(12-W04, 12-W04)}");
        assertEquals(weekInfo06.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期三, MONDAY,   7), [localDate<dayIndexOfWeek>]=(2020-01-01<3>, 2019-12-30 ~ 2020-01-05), [minYear<days>, maxYear<days>, isCrossYear]=(2019<2>, 2020<5>, true ), [minMonth<days>, maxMonth<days>, isCrossMonth]=(12<2>, 01<5>, true ), [weekYear, weekBasedYear]=(2020-W00, 2019-W52), [weekMonth, weekBasedMonth]=(01-W00, 12-W05)}");
        assertEquals(weekInfo07.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期二, MONDAY,   7), [localDate<dayIndexOfWeek>]=(2019-12-31<2>, 2019-12-30 ~ 2020-01-05), [minYear<days>, maxYear<days>, isCrossYear]=(2019<2>, 2020<5>, true ), [minMonth<days>, maxMonth<days>, isCrossMonth]=(12<2>, 01<5>, true ), [weekYear, weekBasedYear]=(2019-W52, 2019-W52), [weekMonth, weekBasedMonth]=(12-W05, 12-W05)}");
        assertEquals(weekInfo11.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期六, MONDAY,   4), [localDate<dayIndexOfWeek>]=(2023-07-01<6>, 2023-06-26 ~ 2023-07-02), [minYear<days>, maxYear<days>, isCrossYear]=(2023<7>, 2023<7>, false), [minMonth<days>, maxMonth<days>, isCrossMonth]=(06<5>, 07<2>, true ), [weekYear, weekBasedYear]=(2023-W26, 2023-W26), [weekMonth, weekBasedMonth]=(07-W00, 06-W05)}");
        assertEquals(weekInfo12.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期一, MONDAY,   4), [localDate<dayIndexOfWeek>]=(2023-07-31<1>, 2023-07-31 ~ 2023-08-06), [minYear<days>, maxYear<days>, isCrossYear]=(2023<7>, 2023<7>, false), [minMonth<days>, maxMonth<days>, isCrossMonth]=(07<1>, 08<6>, true ), [weekYear, weekBasedYear]=(2023-W31, 2023-W31), [weekMonth, weekBasedMonth]=(07-W05, 08-W01)}");
        assertEquals(weekInfo13.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期二, MONDAY,   4), [localDate<dayIndexOfWeek>]=(2023-08-01<2>, 2023-07-31 ~ 2023-08-06), [minYear<days>, maxYear<days>, isCrossYear]=(2023<7>, 2023<7>, false), [minMonth<days>, maxMonth<days>, isCrossMonth]=(07<1>, 08<6>, true ), [weekYear, weekBasedYear]=(2023-W31, 2023-W31), [weekMonth, weekBasedMonth]=(08-W01, 08-W01)}");
        assertEquals(weekInfo14.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期日, MONDAY,   4), [localDate<dayIndexOfWeek>]=(2023-01-01<7>, 2022-12-26 ~ 2023-01-01), [minYear<days>, maxYear<days>, isCrossYear]=(2022<6>, 2023<1>, true ), [minMonth<days>, maxMonth<days>, isCrossMonth]=(12<6>, 01<1>, true ), [weekYear, weekBasedYear]=(2023-W00, 2022-W52), [weekMonth, weekBasedMonth]=(01-W00, 12-W05)}");
        assertEquals(weekInfo15.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期六, MONDAY,   4), [localDate<dayIndexOfWeek>]=(2022-12-31<6>, 2022-12-26 ~ 2023-01-01), [minYear<days>, maxYear<days>, isCrossYear]=(2022<6>, 2023<1>, true ), [minMonth<days>, maxMonth<days>, isCrossMonth]=(12<6>, 01<1>, true ), [weekYear, weekBasedYear]=(2022-W52, 2022-W52), [weekMonth, weekBasedMonth]=(12-W05, 12-W05)}");
        assertEquals(weekInfo16.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期三, MONDAY,   4), [localDate<dayIndexOfWeek>]=(2020-01-01<3>, 2019-12-30 ~ 2020-01-05), [minYear<days>, maxYear<days>, isCrossYear]=(2019<2>, 2020<5>, true ), [minMonth<days>, maxMonth<days>, isCrossMonth]=(12<2>, 01<5>, true ), [weekYear, weekBasedYear]=(2020-W01, 2020-W01), [weekMonth, weekBasedMonth]=(01-W01, 01-W01)}");
        assertEquals(weekInfo17.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期二, MONDAY,   4), [localDate<dayIndexOfWeek>]=(2019-12-31<2>, 2019-12-30 ~ 2020-01-05), [minYear<days>, maxYear<days>, isCrossYear]=(2019<2>, 2020<5>, true ), [minMonth<days>, maxMonth<days>, isCrossMonth]=(12<2>, 01<5>, true ), [weekYear, weekBasedYear]=(2019-W53, 2020-W01), [weekMonth, weekBasedMonth]=(12-W05, 01-W01)}");
        assertEquals(weekInfo21.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期六, SUNDAY,   1), [localDate<dayIndexOfWeek>]=(2023-07-01<7>, 2023-06-25 ~ 2023-07-01), [minYear<days>, maxYear<days>, isCrossYear]=(2023<7>, 2023<7>, false), [minMonth<days>, maxMonth<days>, isCrossMonth]=(06<6>, 07<1>, true ), [weekYear, weekBasedYear]=(2023-W26, 2023-W26), [weekMonth, weekBasedMonth]=(07-W01, 07-W01)}");
        assertEquals(weekInfo22.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期一, SUNDAY,   1), [localDate<dayIndexOfWeek>]=(2023-07-31<2>, 2023-07-30 ~ 2023-08-05), [minYear<days>, maxYear<days>, isCrossYear]=(2023<7>, 2023<7>, false), [minMonth<days>, maxMonth<days>, isCrossMonth]=(07<2>, 08<5>, true ), [weekYear, weekBasedYear]=(2023-W31, 2023-W31), [weekMonth, weekBasedMonth]=(07-W06, 08-W01)}");
        assertEquals(weekInfo23.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期二, SUNDAY,   1), [localDate<dayIndexOfWeek>]=(2023-08-01<3>, 2023-07-30 ~ 2023-08-05), [minYear<days>, maxYear<days>, isCrossYear]=(2023<7>, 2023<7>, false), [minMonth<days>, maxMonth<days>, isCrossMonth]=(07<2>, 08<5>, true ), [weekYear, weekBasedYear]=(2023-W31, 2023-W31), [weekMonth, weekBasedMonth]=(08-W01, 08-W01)}");
        assertEquals(weekInfo24.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期日, SUNDAY,   1), [localDate<dayIndexOfWeek>]=(2023-01-01<1>, 2023-01-01 ~ 2023-01-07), [minYear<days>, maxYear<days>, isCrossYear]=(2023<7>, 2023<7>, false), [minMonth<days>, maxMonth<days>, isCrossMonth]=(01<7>, 01<7>, false), [weekYear, weekBasedYear]=(2023-W01, 2023-W01), [weekMonth, weekBasedMonth]=(01-W01, 01-W01)}");
        assertEquals(weekInfo25.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期六, SUNDAY,   1), [localDate<dayIndexOfWeek>]=(2022-12-31<7>, 2022-12-25 ~ 2022-12-31), [minYear<days>, maxYear<days>, isCrossYear]=(2022<7>, 2022<7>, false), [minMonth<days>, maxMonth<days>, isCrossMonth]=(12<7>, 12<7>, false), [weekYear, weekBasedYear]=(2022-W53, 2022-W53), [weekMonth, weekBasedMonth]=(12-W05, 12-W05)}");
        assertEquals(weekInfo26.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期三, SUNDAY,   1), [localDate<dayIndexOfWeek>]=(2020-01-01<4>, 2019-12-29 ~ 2020-01-04), [minYear<days>, maxYear<days>, isCrossYear]=(2019<3>, 2020<4>, true ), [minMonth<days>, maxMonth<days>, isCrossMonth]=(12<3>, 01<4>, true ), [weekYear, weekBasedYear]=(2020-W01, 2020-W01), [weekMonth, weekBasedMonth]=(01-W01, 01-W01)}");
        assertEquals(weekInfo27.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期二, SUNDAY,   1), [localDate<dayIndexOfWeek>]=(2019-12-31<3>, 2019-12-29 ~ 2020-01-04), [minYear<days>, maxYear<days>, isCrossYear]=(2019<3>, 2020<4>, true ), [minMonth<days>, maxMonth<days>, isCrossMonth]=(12<3>, 01<4>, true ), [weekYear, weekBasedYear]=(2019-W53, 2020-W01), [weekMonth, weekBasedMonth]=(12-W05, 01-W01)}");
        assertEquals(weekInfo31.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期六, null,  null), [localDate<dayIndexOfWeek>]=(2023-07-01<1>, 2023-07-01 ~ 2023-07-07), [minYear<days>, maxYear<days>, isCrossYear]=(2023<7>, 2023<7>, false), [minMonth<days>, maxMonth<days>, isCrossMonth]=(07<7>, 07<7>, false), [weekYear, weekBasedYear]=(2023-W00, 2023-W00), [weekMonth, weekBasedMonth]=(07-W01, 07-W01)}");
        assertEquals(weekInfo32.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期一, null,  null), [localDate<dayIndexOfWeek>]=(2023-07-31<3>, 2023-07-29 ~ 2023-07-31), [minYear<days>, maxYear<days>, isCrossYear]=(2023<3>, 2023<3>, false), [minMonth<days>, maxMonth<days>, isCrossMonth]=(07<3>, 07<3>, false), [weekYear, weekBasedYear]=(2023-W00, 2023-W00), [weekMonth, weekBasedMonth]=(07-W05, 07-W05)}");
        assertEquals(weekInfo33.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期二, null,  null), [localDate<dayIndexOfWeek>]=(2023-08-01<1>, 2023-08-01 ~ 2023-08-07), [minYear<days>, maxYear<days>, isCrossYear]=(2023<7>, 2023<7>, false), [minMonth<days>, maxMonth<days>, isCrossMonth]=(08<7>, 08<7>, false), [weekYear, weekBasedYear]=(2023-W00, 2023-W00), [weekMonth, weekBasedMonth]=(08-W01, 08-W01)}");
        assertEquals(weekInfo34.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期日, null,  null), [localDate<dayIndexOfWeek>]=(2023-01-01<1>, 2023-01-01 ~ 2023-01-07), [minYear<days>, maxYear<days>, isCrossYear]=(2023<7>, 2023<7>, false), [minMonth<days>, maxMonth<days>, isCrossMonth]=(01<7>, 01<7>, false), [weekYear, weekBasedYear]=(2023-W00, 2023-W00), [weekMonth, weekBasedMonth]=(01-W01, 01-W01)}");
        assertEquals(weekInfo35.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期六, null,  null), [localDate<dayIndexOfWeek>]=(2022-12-31<3>, 2022-12-29 ~ 2022-12-31), [minYear<days>, maxYear<days>, isCrossYear]=(2022<3>, 2022<3>, false), [minMonth<days>, maxMonth<days>, isCrossMonth]=(12<3>, 12<3>, false), [weekYear, weekBasedYear]=(2022-W00, 2022-W00), [weekMonth, weekBasedMonth]=(12-W05, 12-W05)}");
        assertEquals(weekInfo36.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期三, null,  null), [localDate<dayIndexOfWeek>]=(2020-01-01<1>, 2020-01-01 ~ 2020-01-07), [minYear<days>, maxYear<days>, isCrossYear]=(2020<7>, 2020<7>, false), [minMonth<days>, maxMonth<days>, isCrossMonth]=(01<7>, 01<7>, false), [weekYear, weekBasedYear]=(2020-W00, 2020-W00), [weekMonth, weekBasedMonth]=(01-W01, 01-W01)}");
        assertEquals(weekInfo37.toString(), "WeekInfo{[dayOfWeek, firstDayOfWeek, minDaysInFirstWeek]=(星期二, null,  null), [localDate<dayIndexOfWeek>]=(2019-12-31<3>, 2019-12-29 ~ 2019-12-31), [minYear<days>, maxYear<days>, isCrossYear]=(2019<3>, 2019<3>, false), [minMonth<days>, maxMonth<days>, isCrossMonth]=(12<3>, 12<3>, false), [weekYear, weekBasedYear]=(2019-W00, 2019-W00), [weekMonth, weekBasedMonth]=(12-W05, 12-W05)}");

        assertEquals(format01, "weekInfo01: 2023-07-01 ***dayIndexOfWeek: 6 *** WeekFields[MONDAY,7] *** 2023-06-W04 === 2023-W26 === 2023-07-W00 === 2023-W26 === 2023年06月第4周 === 2023年07月w00");
        assertEquals(format02, "weekInfo02: 2023-07-31 ***dayIndexOfWeek: 1 *** WeekFields[MONDAY,7] *** 2023-07-W05 === 2023-W31 === 2023-07-W05 === 2023-W31 === 2023年07月第5周 === 2023年07月w05");
        assertEquals(format03, "weekInfo03: 2023-08-01 ***dayIndexOfWeek: 2 *** WeekFields[MONDAY,7] *** 2023-07-W05 === 2023-W31 === 2023-08-W00 === 2023-W31 === 2023年07月第5周 === 2023年08月w00");
        assertEquals(format04, "weekInfo04: 2023-01-01 ***dayIndexOfWeek: 7 *** WeekFields[MONDAY,7] *** 2022-12-W04 === 2022-W52 === 2023-01-W00 === 2023-W0 === 2022年12月第4周 === 2023年01月w00");
        assertEquals(format05, "weekInfo05: 2022-12-31 ***dayIndexOfWeek: 6 *** WeekFields[MONDAY,7] *** 2022-12-W04 === 2022-W52 === 2022-12-W04 === 2022-W52 === 2022年12月第4周 === 2022年12月w04");
        assertEquals(format06, "weekInfo06: 2020-01-01 ***dayIndexOfWeek: 3 *** WeekFields[MONDAY,7] *** 2019-12-W05 === 2019-W52 === 2020-01-W00 === 2020-W0 === 2019年12月第5周 === 2020年01月w00");
        assertEquals(format07, "weekInfo07: 2019-12-31 ***dayIndexOfWeek: 2 *** WeekFields[MONDAY,7] *** 2019-12-W05 === 2019-W52 === 2019-12-W05 === 2019-W52 === 2019年12月第5周 === 2019年12月w05");
        assertEquals(format11, "weekInfo11: 2023-07-01 ***dayIndexOfWeek: 6 *** WeekFields[MONDAY,4] *** 2023-06-W05 === 2023-W26 === 2023-07-W00 === 2023-W26 === 2023年06月第5周 === 2023年07月w00");
        assertEquals(format12, "weekInfo12: 2023-07-31 ***dayIndexOfWeek: 1 *** WeekFields[MONDAY,4] *** 2023-08-W01 === 2023-W31 === 2023-07-W05 === 2023-W31 === 2023年08月第1周 === 2023年07月w05");
        assertEquals(format13, "weekInfo13: 2023-08-01 ***dayIndexOfWeek: 2 *** WeekFields[MONDAY,4] *** 2023-08-W01 === 2023-W31 === 2023-08-W01 === 2023-W31 === 2023年08月第1周 === 2023年08月w01");
        assertEquals(format14, "weekInfo14: 2023-01-01 ***dayIndexOfWeek: 7 *** WeekFields[MONDAY,4] *** 2022-12-W05 === 2022-W52 === 2023-01-W00 === 2023-W0 === 2022年12月第5周 === 2023年01月w00");
        assertEquals(format15, "weekInfo15: 2022-12-31 ***dayIndexOfWeek: 6 *** WeekFields[MONDAY,4] *** 2022-12-W05 === 2022-W52 === 2022-12-W05 === 2022-W52 === 2022年12月第5周 === 2022年12月w05");
        assertEquals(format16, "weekInfo16: 2020-01-01 ***dayIndexOfWeek: 3 *** WeekFields[MONDAY,4] *** 2020-01-W01 === 2020-W01 === 2020-01-W01 === 2020-W1 === 2020年01月第1周 === 2020年01月w01");
        assertEquals(format17, "weekInfo17: 2019-12-31 ***dayIndexOfWeek: 2 *** WeekFields[MONDAY,4] *** 2020-01-W01 === 2020-W01 === 2019-12-W05 === 2019-W53 === 2020年01月第1周 === 2019年12月w05");
        assertEquals(format21, "weekInfo21: 2023-07-01 ***dayIndexOfWeek: 7 *** WeekFields[SUNDAY,1] *** 2023-07-W01 === 2023-W26 === 2023-07-W01 === 2023-W26 === 2023年07月第1周 === 2023年07月w01");
        assertEquals(format22, "weekInfo22: 2023-07-31 ***dayIndexOfWeek: 2 *** WeekFields[SUNDAY,1] *** 2023-08-W01 === 2023-W31 === 2023-07-W06 === 2023-W31 === 2023年08月第1周 === 2023年07月w06");
        assertEquals(format23, "weekInfo23: 2023-08-01 ***dayIndexOfWeek: 3 *** WeekFields[SUNDAY,1] *** 2023-08-W01 === 2023-W31 === 2023-08-W01 === 2023-W31 === 2023年08月第1周 === 2023年08月w01");
        assertEquals(format24, "weekInfo24: 2023-01-01 ***dayIndexOfWeek: 1 *** WeekFields[SUNDAY,1] *** 2023-01-W01 === 2023-W01 === 2023-01-W01 === 2023-W1 === 2023年01月第1周 === 2023年01月w01");
        assertEquals(format25, "weekInfo25: 2022-12-31 ***dayIndexOfWeek: 7 *** WeekFields[SUNDAY,1] *** 2022-12-W05 === 2022-W53 === 2022-12-W05 === 2022-W53 === 2022年12月第5周 === 2022年12月w05");
        assertEquals(format26, "weekInfo26: 2020-01-01 ***dayIndexOfWeek: 4 *** WeekFields[SUNDAY,1] *** 2020-01-W01 === 2020-W01 === 2020-01-W01 === 2020-W1 === 2020年01月第1周 === 2020年01月w01");
        assertEquals(format27, "weekInfo27: 2019-12-31 ***dayIndexOfWeek: 3 *** WeekFields[SUNDAY,1] *** 2020-01-W01 === 2020-W01 === 2019-12-W05 === 2019-W53 === 2020年01月第1周 === 2019年12月w05");
        assertEquals(format31, "weekInfo31: 2023-07-01 ***dayIndexOfWeek: 1 *** null *** 2023-07-W01 === 2023-W00 === 2023-07-W01 === 2023-W0 === 2023年07月第1周 === 2023年07月w01");
        assertEquals(format32, "weekInfo32: 2023-07-31 ***dayIndexOfWeek: 3 *** null *** 2023-07-W05 === 2023-W00 === 2023-07-W05 === 2023-W0 === 2023年07月第5周 === 2023年07月w05");
        assertEquals(format33, "weekInfo33: 2023-08-01 ***dayIndexOfWeek: 1 *** null *** 2023-08-W01 === 2023-W00 === 2023-08-W01 === 2023-W0 === 2023年08月第1周 === 2023年08月w01");
        assertEquals(format34, "weekInfo34: 2023-01-01 ***dayIndexOfWeek: 1 *** null *** 2023-01-W01 === 2023-W00 === 2023-01-W01 === 2023-W0 === 2023年01月第1周 === 2023年01月w01");
        assertEquals(format35, "weekInfo35: 2022-12-31 ***dayIndexOfWeek: 3 *** null *** 2022-12-W05 === 2022-W00 === 2022-12-W05 === 2022-W0 === 2022年12月第5周 === 2022年12月w05");
        assertEquals(format36, "weekInfo36: 2020-01-01 ***dayIndexOfWeek: 1 *** null *** 2020-01-W01 === 2020-W00 === 2020-01-W01 === 2020-W0 === 2020年01月第1周 === 2020年01月w01");
        assertEquals(format37, "weekInfo37: 2019-12-31 ***dayIndexOfWeek: 3 *** null *** 2019-12-W05 === 2019-W00 === 2019-12-W05 === 2019-W0 === 2019年12月第5周 === 2019年12月w05");

    }

    @Test
    public void testLocalDateBaseMonth() {
        LocalDate localDate01 = WeekInfo.baseMonth(2023, 7, 0);
        LocalDate localDate02 = WeekInfo.baseMonth(2023, 7, 5);
        LocalDate localDate03 = WeekInfo.baseMonth(2023, 8, 0);
        LocalDate localDate04 = WeekInfo.baseMonth(2023, 1, 0);
        LocalDate localDate05 = WeekInfo.baseMonth(2022, 12, 4);
        LocalDate localDate06 = WeekInfo.baseMonth(2020, 1, 0);
        LocalDate localDate07 = WeekInfo.baseMonth(2019, 12, 5);
        LocalDate localDate11 = WeekInfo.baseMonth(WEEK_ISO, 2023, 7, 0);
        LocalDate localDate12 = WeekInfo.baseMonth(WEEK_ISO, 2023, 7, 5);
        LocalDate localDate13 = WeekInfo.baseMonth(WEEK_ISO, 2023, 8, 1);
        LocalDate localDate14 = WeekInfo.baseMonth(WEEK_ISO, 2023, 1, 0);
        LocalDate localDate15 = WeekInfo.baseMonth(WEEK_ISO, 2022, 12, 5);
        LocalDate localDate16 = WeekInfo.baseMonth(WEEK_ISO, 2020, 1, 1);
        LocalDate localDate17 = WeekInfo.baseMonth(WEEK_ISO, 2019, 12, 5);
        LocalDate localDate21 = WeekInfo.baseMonth(SUNDAY_MIN1, 2023, 7, 1);
        LocalDate localDate22 = WeekInfo.baseMonth(SUNDAY_MIN1, 2023, 7, 6);
        LocalDate localDate23 = WeekInfo.baseMonth(SUNDAY_MIN1, 2023, 8, 1);
        LocalDate localDate24 = WeekInfo.baseMonth(SUNDAY_MIN1, 2023, 1, 1);
        LocalDate localDate25 = WeekInfo.baseMonth(SUNDAY_MIN1, 2022, 12, 5);
        LocalDate localDate26 = WeekInfo.baseMonth(SUNDAY_MIN1, 2020, 1, 1);
        LocalDate localDate27 = WeekInfo.baseMonth(SUNDAY_MIN1, 2019, 12, 5);
        LocalDate localDate31 = WeekInfo.baseMonth(null, 2023, 7, 1);
        LocalDate localDate32 = WeekInfo.baseMonth(null, 2023, 7, 5);
        LocalDate localDate33 = WeekInfo.baseMonth(null, 2023, 8, 1);
        LocalDate localDate34 = WeekInfo.baseMonth(null, 2023, 1, 1);
        LocalDate localDate35 = WeekInfo.baseMonth(null, 2022, 12, 5);
        LocalDate localDate36 = WeekInfo.baseMonth(null, 2020, 1, 1);
        LocalDate localDate37 = WeekInfo.baseMonth(null, 2019, 12, 5);

        assertEquals(localDate01.toString(), "2023-06-26");
        assertEquals(localDate02.toString(), "2023-07-31");
        assertEquals(localDate03.toString(), "2023-07-31");
        assertEquals(localDate04.toString(), "2022-12-26");
        assertEquals(localDate05.toString(), "2022-12-26");
        assertEquals(localDate06.toString(), "2019-12-30");
        assertEquals(localDate07.toString(), "2019-12-30");
        assertEquals(localDate11.toString(), "2023-06-26");
        assertEquals(localDate12.toString(), "2023-07-31");
        assertEquals(localDate13.toString(), "2023-07-31");
        assertEquals(localDate14.toString(), "2022-12-26");
        assertEquals(localDate15.toString(), "2022-12-26");
        assertEquals(localDate16.toString(), "2019-12-30");
        assertEquals(localDate17.toString(), "2019-12-30");
        assertEquals(localDate21.toString(), "2023-06-25");
        assertEquals(localDate22.toString(), "2023-07-30");
        assertEquals(localDate23.toString(), "2023-07-30");
        assertEquals(localDate24.toString(), "2023-01-01");
        assertEquals(localDate25.toString(), "2022-12-25");
        assertEquals(localDate26.toString(), "2019-12-29");
        assertEquals(localDate27.toString(), "2019-12-29");
        assertEquals(localDate31.toString(), "2023-07-01");
        assertEquals(localDate32.toString(), "2023-07-29");
        assertEquals(localDate33.toString(), "2023-08-01");
        assertEquals(localDate34.toString(), "2023-01-01");
        assertEquals(localDate35.toString(), "2022-12-29");
        assertEquals(localDate36.toString(), "2020-01-01");
        assertEquals(localDate37.toString(), "2019-12-29");

    }

    @Test
    public void testLocalDateBaseYear() {
        LocalDate localDate01 = WeekInfo.baseYear(2023, 26);
        LocalDate localDate02 = WeekInfo.baseYear(2023, 31);
        LocalDate localDate03 = WeekInfo.baseYear(2023, 31);
        LocalDate localDate04 = WeekInfo.baseYear(2023, 0);
        LocalDate localDate05 = WeekInfo.baseYear(2022, 52);
        LocalDate localDate06 = WeekInfo.baseYear(2020, 0);
        LocalDate localDate07 = WeekInfo.baseYear(2019, 52);
        LocalDate localDate11 = WeekInfo.baseYear(WEEK_ISO, 2023, 26);
        LocalDate localDate12 = WeekInfo.baseYear(WEEK_ISO, 2023, 31);
        LocalDate localDate13 = WeekInfo.baseYear(WEEK_ISO, 2023, 31);
        LocalDate localDate14 = WeekInfo.baseYear(WEEK_ISO, 2023, 0);
        LocalDate localDate15 = WeekInfo.baseYear(WEEK_ISO, 2022, 52);
        LocalDate localDate16 = WeekInfo.baseYear(WEEK_ISO, 2020, 1);
        LocalDate localDate17 = WeekInfo.baseYear(WEEK_ISO, 2019, 53);
        LocalDate localDate21 = WeekInfo.baseYear(SUNDAY_MIN1, 2023, 26);
        LocalDate localDate22 = WeekInfo.baseYear(SUNDAY_MIN1, 2023, 31);
        LocalDate localDate23 = WeekInfo.baseYear(SUNDAY_MIN1, 2023, 31);
        LocalDate localDate24 = WeekInfo.baseYear(SUNDAY_MIN1, 2023, 1);
        LocalDate localDate25 = WeekInfo.baseYear(SUNDAY_MIN1, 2022, 53);
        LocalDate localDate26 = WeekInfo.baseYear(SUNDAY_MIN1, 2020, 1);
        LocalDate localDate27 = WeekInfo.baseYear(SUNDAY_MIN1, 2019, 53);

        assertEquals(localDate01.toString(), "2023-06-26");
        assertEquals(localDate02.toString(), "2023-07-31");
        assertEquals(localDate03.toString(), "2023-07-31");
        assertEquals(localDate04.toString(), "2022-12-26");
        assertEquals(localDate05.toString(), "2022-12-26");
        assertEquals(localDate06.toString(), "2019-12-30");
        assertEquals(localDate07.toString(), "2019-12-30");
        assertEquals(localDate11.toString(), "2023-06-26");
        assertEquals(localDate12.toString(), "2023-07-31");
        assertEquals(localDate13.toString(), "2023-07-31");
        assertEquals(localDate14.toString(), "2022-12-26");
        assertEquals(localDate15.toString(), "2022-12-26");
        assertEquals(localDate16.toString(), "2019-12-30");
        assertEquals(localDate17.toString(), "2019-12-30");
        assertEquals(localDate21.toString(), "2023-06-25");
        assertEquals(localDate22.toString(), "2023-07-30");
        assertEquals(localDate23.toString(), "2023-07-30");
        assertEquals(localDate24.toString(), "2023-01-01");
        assertEquals(localDate25.toString(), "2022-12-25");
        assertEquals(localDate26.toString(), "2019-12-29");
        assertEquals(localDate27.toString(), "2019-12-29");

    }

    @Test
    public void testCheckArgument() {
        try {
            WeekInfo.baseMonth(YearMonth.of(2023, 1), 7);
            throwException();
        } catch (Exception e) {
            assertSame(e.getClass(), OutOfBoundsException.class);
            assertEquals(e.getMessage(), "数值超出所允许的范围，当前值为：[7]。指定月中的周序号时，参数`weekNo`的取值范围为：[0, 6]。");
        }
    }

    private void throwException() {
        throw new RuntimeException();
    }
}
