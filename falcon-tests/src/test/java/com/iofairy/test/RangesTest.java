package com.iofairy.test;

import com.iofairy.except.ConditionsNotMetException;
import com.iofairy.except.OutOfBoundsException;
import com.iofairy.falcon.util.Ranges;
import com.iofairy.range.Range;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author GG
 * @version 1.0
 * @date 2022/2/14 22:00
 */
public class RangesTest {
    @Test
    public void testDivideRange() {
        List<Range<Long>> ranges1 = Ranges.split(0, 500, 5, 0);
        List<Range<Long>> ranges2 = Ranges.split(-600, -100, 5, -0.1f);
        List<Range<Long>> ranges3 = Ranges.split(-100, 400, 5, 0.1f);
        List<Range<Long>> ranges4 = Ranges.split(2, 48900568, 6, -0.1f);
        List<Range<Long>> ranges5 = Ranges.split(0, 5, 5, 0);
        List<Range<Long>> ranges6 = Ranges.split(0, 5, 5, -0.2f);
        System.out.println(ranges1);
        System.out.println(ranges2);
        System.out.println(ranges3);
        System.out.println(ranges4);
        System.out.println(ranges5);
        System.out.println(ranges6);

        assertEquals("[[0, 100), [100, 200), [200, 300), [300, 400), [400, 500)]", ranges1.toString());
        assertEquals("[[-600, -478), [-478, -368), [-368, -269), [-269, -180), [-180, -100)]", ranges2.toString());
        assertEquals("[[-100, -18), [-18, 72), [72, 171), [171, 280), [280, 400)]", ranges3.toString());
        assertEquals("[[2, 10436576), [10436576, 19829492), [19829492, 28283116), [28283116, 35891378), [35891378, 42738813), [42738813, 48900568)]", ranges4.toString());
        assertEquals("[[0, 1), [1, 2), [2, 3), [3, 4), [4, 5)]", ranges5.toString());
        assertEquals("[[0, 1), [1, 2), [2, 3), [3, 4), [4, 5)]", ranges6.toString());
        System.out.println("============================================================");
        List<Range<Long>> ranges7 = Ranges.split(Range.openClosed(-100L, 400L), 5, 0.1f);
        List<Range<Long>> ranges8 = Ranges.split(Range.openClosed(2L, 48900568L), 6, -0.1f);
        System.out.println(ranges7);
        System.out.println(ranges8);
        assertEquals("[(-100, -18], (-18, 72], (72, 171], (171, 280], (280, 400]]", ranges7.toString());
        assertEquals("[(2, 10436576], (10436576, 19829492], (19829492, 28283116], (28283116, 35891378], (35891378, 42738813], (42738813, 48900568]]", ranges8.toString());
        System.out.println("============================================================");

    }

    @Test
    public void testCheckArgument() {
        try {
            Ranges.split(Range.open(0L, 500L), 1, 0);
            throwException();
        } catch (Exception e) {
            assertSame(e.getClass(), ConditionsNotMetException.class);
            assertEquals("The `range` must be half open interval! ", e.getMessage());
        }
        try {
            Ranges.split(Range.closedOpen(0L, 500L), 1, 0);
            throwException();
        } catch (Exception e) {
            assertSame(e.getClass(), ConditionsNotMetException.class);
            assertEquals("Parameter `splitCount` must ≥ 2! ", e.getMessage());
        }
        try {
            Ranges.split(0, 4, 5, 0);
            throwException();
        } catch (Exception e) {
            assertSame(e.getClass(), ConditionsNotMetException.class);
            assertEquals("(`endIndex` - `beginIndex`) must be ≥ `splitCount`! ", e.getMessage());
        }
        try {
            Ranges.split(Range.closedOpen(0L, 500L), 5, -1);
            throwException();
        } catch (Exception e) {
            assertSame(e.getClass(), OutOfBoundsException.class);
            assertEquals("数值超出所允许的范围，当前值为：[-1.0]。参数`skewRatio`的取值范围为：(-1, 1)。", e.getMessage());
        }

        try {
            Ranges.split(Range.closedOpen(null, null), 5, -1);
            throwException();
        } catch (Exception e) {
            assertSame(e.getClass(), ConditionsNotMetException.class);
            assertEquals("The `range` must be half open interval! ", e.getMessage());
        }

        try {
            Ranges.split(Range.closedOpen(1L, null), 5, -1);
            throwException();
        } catch (Exception e) {
            assertSame(e.getClass(), ConditionsNotMetException.class);
            assertEquals("The `range` can't be an infinite interval! ", e.getMessage());
        }
    }

    private void throwException() {
        throw new RuntimeException();
    }
}
