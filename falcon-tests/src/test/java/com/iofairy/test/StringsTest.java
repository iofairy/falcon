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
}
