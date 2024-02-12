package com.iofairy.test;

import com.iofairy.falcon.string.Strings;
import com.iofairy.tcf.Try;
import com.iofairy.top.G;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author GG
 * @version 1.0
 */
public class StringsTest {
    @Test
    public void testRepeat() {
        String repeat01 = Strings.repeat(null, 0);
        String repeat02 = Strings.repeat("", 0);
        String repeat03 = Strings.repeat("", 1);
        String repeat04 = Strings.repeat("a", 0);
        String repeat05 = Strings.repeat("a", 1);
        String repeat06 = Strings.repeat("a", 10);
        String repeat07 = Strings.repeat("abc", 10);

        System.out.println(repeat01);
        System.out.println(repeat02);
        System.out.println(repeat03);
        System.out.println(repeat04);
        System.out.println(repeat05);
        System.out.println(repeat06);
        System.out.println(repeat07);

        assertNull(repeat01);
        assertEquals(repeat02, "");
        assertEquals(repeat03, "");
        assertEquals(repeat04, "");
        assertEquals(repeat05, "a");
        assertEquals(repeat06, "aaaaaaaaaa");
        assertEquals(repeat07, "abcabcabcabcabcabcabcabcabcabc");

        Try.sleep(500);
        try {
            Strings.repeat("a", Integer.MAX_VALUE);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertEquals(e.getMessage(), "Parameter `repeatTimes` must be <= (Integer.MAX_VALUE - 8), otherwise, the memory will overflow! ");
        }

    }


    @Test
    public void testConvertTime() {
        String time01 = Strings.convertTime(-1000000);
        String time02 = Strings.convertTime(-1);
        String time03 = Strings.convertTime(0);
        String time04 = Strings.convertTime(1);
        String time05 = Strings.convertTime(500);
        String time06 = Strings.convertTime(1000);
        String time07 = Strings.convertTime(3000);
        String time08 = Strings.convertTime(9000);
        String time09 = Strings.convertTime(7000000);
        String time10 = Strings.convertTime(86400000);
        String time11 = Strings.convertTime(306400000);

        System.out.println(time01);
        System.out.println(time02);
        System.out.println(time03);
        System.out.println(time04);
        System.out.println(time05);
        System.out.println(time06);
        System.out.println(time07);
        System.out.println(time08);
        System.out.println(time09);
        System.out.println(time10);
        System.out.println(time11);

        assertEquals(time01, "-16.7分");
        assertEquals(time02, "-1毫秒");
        assertEquals(time03, "0毫秒");
        assertEquals(time04, "1毫秒");
        assertEquals(time05, "500毫秒");
        assertEquals(time06, "1.0秒");
        assertEquals(time07, "3.0秒");
        assertEquals(time08, "9.0秒");
        assertEquals(time09, "1.9时");
        assertEquals(time10, "1.0天");
        assertEquals(time11, "3.5天");

    }

}
