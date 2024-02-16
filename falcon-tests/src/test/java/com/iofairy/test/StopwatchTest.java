package com.iofairy.test;

import com.iofairy.falcon.time.Stopwatch;
import com.iofairy.tcf.Try;
import org.junit.jupiter.api.Test;


import static java.util.concurrent.TimeUnit.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author GG
 * @version 1.0
 * @date 2024/2/16 19:49
 */
public class StopwatchTest {

    @Test
    public void testStopwatch() {
        Stopwatch stopwatch = Stopwatch.create();
        stopwatchPrint(stopwatch);
        assertEquals(stopwatch.getMarks().toString(), "{}");
        stopwatch.clearMarks();
        stopwatchPrint(stopwatch);
        assertEquals(stopwatch.getMarks().toString(), "{}");
        System.out.println("==================start=================");
        stopwatch.start();
        stopwatchPrint(stopwatch);
        assertEquals(stopwatch.getMarks().toString(), "{0=START}");

        Try.sleep(100);
        System.out.println("===============================sleep(100)=============================");
        stopwatchPrint(stopwatch);
        stopwatch.mark();       // 1=MARK1
        assertEquals(stopwatch.getMarks().toString(), "{0=START, 1=MARK1}");

        Try.sleep(150);
        System.out.println("===============================sleep(150)=============================");
        stopwatch.mark("a");    // 2=a
        assertEquals(stopwatch.getMarks().toString(), "{0=START, 1=MARK1, 2=a}");

        Stopwatch.Elapsed elapsed = stopwatch.elapsed("MARK1", 0);
        elapsedPrint(elapsed);

        Try.sleep(1500);
        System.out.println("===============================sleep(1500)=============================");
        stopwatch.mark("b");
        stopwatch.mark();
        assertEquals(stopwatch.getMarks().toString(), "{0=START, 1=MARK1, 2=a, 3=b, 4=MARK4}");

        elapsed = stopwatch.elapsed("a", 2);
        elapsedPrint(elapsed);
        elapsed = stopwatch.elapsed(1, 4);
        elapsedPrint(elapsed);
        stopwatchPrint(stopwatch);
        System.out.println("==================reset=================");
        stopwatch.reset();
        assertEquals(stopwatch.getMarks().toString(), "{0=START}");
        stopwatchPrint(stopwatch);

        Try.sleep(100);
        System.out.println("===============================sleep(100)=============================");
        stopwatch.clearMarks();
        assertEquals(stopwatch.getMarks().toString(), "{0=START}");
        stopwatchPrint(stopwatch);

        Try.sleep(1800);
        System.out.println("===============================sleep(1800)=============================");
        stopwatch.mark("1");
        Try.sleep(160);
        System.out.println("===============================sleep(160)=============================");
        stopwatchPrint(stopwatch);
        System.out.println("==================stop=================");
        stopwatch.stop();
        assertEquals(stopwatch.getMarks().toString(), "{0=START, 1=1, 2=STOP}");
        stopwatchPrint(stopwatch);
        Try.sleep(180);
        System.out.println("===============================sleep(180)=============================");
        stopwatchPrint(stopwatch);
        stopwatch.clearMarks();
        assertEquals(stopwatch.getMarks().toString(), "{0=START, 1=STOP}");
        stopwatchPrint(stopwatch);
    }

    private static void elapsedPrint(Stopwatch.Elapsed elapsed) {
        System.out.println("===============elapsedPrint===============");
        System.out.println("elapsed.elapsed(MILLISECONDS): " + elapsed.elapsed(MILLISECONDS));
        System.out.println("elapsed.elapsedExact(MILLISECONDS): " + elapsed.elapsedExact(MILLISECONDS));
        System.out.println("elapsed.elapsed().toMillis(): " + elapsed.elapsed().toMillis());
        System.out.println("elapsed: " + elapsed);
        System.out.println("elapsed.toFullString(): " + elapsed.toFullString());
        System.out.println("=============================================");
    }

    private static void stopwatchPrint(Stopwatch stopwatch) {
        System.out.println("===============stopwatchPrint===============");
        System.out.println("stopwatch.elapsed(MILLISECONDS): " + stopwatch.elapsed(MILLISECONDS));
        System.out.println("stopwatch.elapsedExact(SECONDS): " + stopwatch.elapsedExact(SECONDS));
        System.out.println("stopwatch.elapsed().toMillis(): " + stopwatch.elapsed().toMillis());
        System.out.println("stopwatch: " + stopwatch);
        System.out.println("stopwatch.elapsedLast(MILLISECONDS): " + stopwatch.elapsedLast(MILLISECONDS));
        System.out.println("stopwatch.elapsedLastExact(MILLISECONDS): " + stopwatch.elapsedLastExact(MILLISECONDS));
        System.out.println("stopwatch.elapsedLast().toMillis(): " + stopwatch.elapsedLast().toMillis());
        System.out.println("stopwatch.elapsedlastString(): " + stopwatch.elapsedlastString());
        System.out.println("stopwatch.getMarks(): " + stopwatch.getMarks());
        System.out.println("stopwatch.isRunning(): " + stopwatch.isRunning()
                + "---stopwatch.getElapsedNanos(): " + stopwatch.getElapsedNanos()
                + "---stopwatch.getStartTick(): " + stopwatch.getStartTick()
                + "---stopwatch.getStopTick(): " + stopwatch.getStopTick()
                + "---stopwatch.getLastMarkTick(): " + stopwatch.getLastMarkTick()
                + "---stopwatch.getMarkIndex(): " + stopwatch.getMarkIndex()
        );
        System.out.println("=============================================");
    }

    @Test
    public void testCheckArgument() {
        Stopwatch stopwatch = Stopwatch.create().start();

        try {
            stopwatch.start();
            throwException();
        } catch (Exception e) {
            assertSame(e.getClass(), IllegalStateException.class);
            assertEquals(e.getMessage(), "This stopwatch is already running! ");
        }
        try {
            stopwatch.stop();
            stopwatch.stop();
            throwException();
        } catch (Exception e) {
            assertSame(e.getClass(), IllegalStateException.class);
            assertEquals(e.getMessage(), "This stopwatch is already stopped! ");
        }
        try {
            stopwatch.mark();
            throwException();
        } catch (Exception e) {
            assertSame(e.getClass(), IllegalStateException.class);
            assertEquals(e.getMessage(), "This stopwatch is already stopped! ");
        }

        stopwatch.start();

        try {
            stopwatch.mark("start");
            throwException();
        } catch (Exception e) {
            assertSame(e.getClass(), IllegalArgumentException.class);
            assertEquals(e.getMessage(), "The words 'START' and 'STOP' and ('MARK1', 'MARK2', 'MARK3', 'MARKn' ...) are reserved and can't be used. ");
        }
        try {
            stopwatch.mark("stop");
            throwException();
        } catch (Exception e) {
            assertSame(e.getClass(), IllegalArgumentException.class);
            assertEquals(e.getMessage(), "The words 'START' and 'STOP' and ('MARK1', 'MARK2', 'MARK3', 'MARKn' ...) are reserved and can't be used. ");
        }
        try {
            stopwatch.mark("mark0");
            throwException();
        } catch (Exception e) {
            assertSame(e.getClass(), IllegalArgumentException.class);
            assertEquals(e.getMessage(), "The words 'START' and 'STOP' and ('MARK1', 'MARK2', 'MARK3', 'MARKn' ...) are reserved and can't be used. ");
        }
        try {
            stopwatch.mark(null);
            throwException();
        } catch (Exception e) {
            assertSame(e.getClass(), NullPointerException.class);
            assertEquals(e.getMessage(), "The parameter `name` must be non-null! ");
        }
        try {
            stopwatch.elapsed("mark0", new Object());
            throwException();
        } catch (Exception e) {
            assertSame(e.getClass(), IllegalArgumentException.class);
            assertEquals(e.getMessage(), "The types of parameters `from` and `to` must be either [String] or [Integer]! ");
        }
        try {
            stopwatch.elapsed(null, new Object());
            throwException();
        } catch (Exception e) {
            assertSame(e.getClass(), IllegalArgumentException.class);
            assertEquals(e.getMessage(), "The types of parameters `from` and `to` must be either [String] or [Integer]! ");
        }
        try {
            stopwatch.elapsed(3, "a");
            throwException();
        } catch (Exception e) {
            assertSame(e.getClass(), IllegalArgumentException.class);
            assertEquals(e.getMessage(), "Not found for this mark point [3]. ");
        }
    }

    private void throwException() {
        throw new RuntimeException();
    }
}
