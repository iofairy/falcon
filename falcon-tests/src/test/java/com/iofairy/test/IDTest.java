package com.iofairy.test;

import com.iofairy.falcon.iterable.CollectionKit;
import com.iofairy.falcon.uuid.TimedUUID;
import com.iofairy.tcf.Try;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author GG
 * @version 1.0
 * @date 2024/1/8 11:31
 */
public class IDTest {
    @Test
    public void testTimedUUID() {
        System.out.println("===========testTimedUUID===========");
        String id01 = TimedUUID.getId();
        TimedUUID timedUUID = TimedUUID.of();
        String id02 = timedUUID.randomId();
        timedUUID = TimedUUID.of(56, 8, 100, true, true, 4);
        String id03 = timedUUID.randomId();
        timedUUID = TimedUUID.of(10000, 8);
        String id04 = timedUUID.randomId();
        timedUUID = TimedUUID.of(10000, 8, 50);
        String id05 = timedUUID.randomId();
        timedUUID = TimedUUID.of(100, true, true);
        String id06 = timedUUID.randomId();
        timedUUID = TimedUUID.of(20, -1, 50, false, true, 3);
        String id07 = timedUUID.randomId();
        try {
            timedUUID = TimedUUID.of(10000, -1, 50, false, true, 2);
        } catch (Exception e) {
            assertEquals("参数`number`超出范围，当前位数[1]下，最大允许值[25]，无法映射到指定位数的字母！", e.getMessage());
        }


        System.out.println(id01 + "---" + id01.length());
        System.out.println(id02 + "---" + id02.length());
        System.out.println(id03 + "---" + id03.length());
        System.out.println(id04 + "---" + id04.length());
        System.out.println(id05 + "---" + id05.length());
        System.out.println(id06 + "---" + id06.length());
        System.out.println(id07 + "---" + id07.length());

        assertEquals(25, id01.length());
        assertEquals(25, id02.length());
        assertEquals(49, id03.length());
        assertEquals(25, id04.length());
        assertEquals(46, id05.length());
        assertEquals(43, id06.length());
        assertEquals(43, id07.length());

    }

    @Test
    public void testTimedUUID1() {
        String id01 = TimedUUID.newId();
        String id02 = TimedUUID.shortId();
        String id03 = TimedUUID.mediumId();
        String id04 = TimedUUID.longId();
        String id05 = TimedUUID.linedId();

        System.out.println(id01);
        System.out.println(id02);
        System.out.println(id03);
        System.out.println(id04);
        System.out.println(id05);

        assertEquals(25, id01.length());
        assertEquals(26, id02.length());
        assertEquals(30, id03.length());
        assertEquals(32, id04.length());
        assertEquals(28, id05.length());
        assertTrue(id05.contains("_"));

    }

    @Test
    public void testDefaultId() {
        TimedUUID defaultId = TimedUUID.getDefaultId();
        if (defaultId != null) return;
        System.out.println(defaultId);

        new Thread(() -> {
            String id = TimedUUID.getId();
            System.out.println(TimedUUID.getDefaultId());
            System.out.println(id);
        }).start();

        Try.sleep(500);

        String id = TimedUUID.getId();
        TimedUUID.setDefaultId(TimedUUID.TIMED_ID32);
        System.out.println(TimedUUID.getDefaultId());
        System.out.println(id);
        assertEquals("TimedUUID{workerNum=0, workerIdLength=2, workerId='AA', idLength=25, upperCase=true, withUnderline=false, yearLength=2}", TimedUUID.getDefaultId().toString());
        assertEquals(25, id.length());
    }

    @Test
    public void testDefaultId1() {
        TimedUUID defaultId = TimedUUID.getDefaultId();
        if (defaultId != null) return;
        System.out.println(defaultId);

        new Thread(() -> {
            TimedUUID.setDefaultId(TimedUUID.TIMED_ID32);
            String id = TimedUUID.getId();
            System.out.println(TimedUUID.getDefaultId());
            System.out.println(id);
        }).start();

        Try.sleep(500);

        TimedUUID.setDefaultId(TimedUUID.TIMED_ID30);
        String id = TimedUUID.getId();
        System.out.println(TimedUUID.getDefaultId());
        System.out.println(id);
        assertEquals("TimedUUID{workerNum=0, workerIdLength=2, workerId='AA', idLength=32, upperCase=true, withUnderline=false, yearLength=4}", TimedUUID.getDefaultId().toString());
        assertEquals(32, id.length());
    }

    @Test
    public void testNumberToLetters() {
        System.out.println("===========testNumberToLetters===========");
        String l01 = TimedUUID.numberToLetters(0, 1);
        String l02 = TimedUUID.numberToLetters(25, 1);
        String l03 = TimedUUID.numberToLetters(0, 2);
        String l04 = TimedUUID.numberToLetters(675, 2);
        String l05 = TimedUUID.numberToLetters(0, 3);
        String l06 = TimedUUID.numberToLetters(17575, 3);
        String l07 = TimedUUID.numberToLetters(0, 4);
        String l08 = TimedUUID.numberToLetters(456975, 4);
        String l09 = TimedUUID.numberToLetters(0, 5);
        String l10 = TimedUUID.numberToLetters(11881375, 5);
        String l11 = TimedUUID.numberToLetters(0, 6);
        String l12 = TimedUUID.numberToLetters(308915775, 6);
        String l13 = TimedUUID.numberToLetters(8789565, 5);
        String l14 = TimedUUID.numberToLetters(999999, 6);
        String l15 = TimedUUID.numberToLetters(308915774, 6);

        assertEquals(l01, "A");
        assertEquals(l02, "Z");
        assertEquals(l03, "AA");
        assertEquals(l04, "ZZ");
        assertEquals(l05, "AAA");
        assertEquals(l06, "ZZZ");
        assertEquals(l07, "AAAA");
        assertEquals(l08, "ZZZZ");
        assertEquals(l09, "AAAAA");
        assertEquals(l10, "ZZZZZ");
        assertEquals(l11, "AAAAAA");
        assertEquals(l12, "ZZZZZZ");
        assertEquals(l13, "TGCIF");
        assertEquals(l14, "ACEXHN");
        assertEquals(l15, "ZZZZZY");

        try {
            TimedUUID.numberToLetters(0, 0);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "参数`number`应为非负数，`letterLength`取值范围为[1, 6]！");
        }
        try {
            TimedUUID.numberToLetters(0, 7);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "参数`number`应为非负数，`letterLength`取值范围为[1, 6]！");
        }
        try {
            TimedUUID.numberToLetters(1000, 1);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "参数`number`超出范围，当前位数[1]下，最大允许值[25]，无法映射到指定位数的字母！");
        }

    }

    @Test
    public void testGetTimedUUID() {
        System.out.println("===========testGetTimedUUID===========");
        for (int i = 0; i < 50; i++) {
            System.out.println(TimedUUID.getId());
        }
        System.out.println("============================================================");
        TimedUUID timedUUID = TimedUUID.of(true, true);
        for (int i = 0; i < 50; i++) {
            System.out.println(timedUUID.randomId());
        }

    }

    /**
     * 测试ID生成性能
     */
    @Test
    public void testPerformance() {
        System.out.println("===========testPerformance===========");
        performance();
    }

    private static void performance() {
        AtomicInteger atomicInteger = new AtomicInteger();
        Thread thread = new Thread(() -> {
            for (int j = 0; j < 100000; j++) {
                TimedUUID.getId();
                atomicInteger.incrementAndGet();
            }
        });
        thread.start();
        Try.tcf(() -> thread.join(1000), false);
        /*
         * 不转36进制，单线程每秒可以获取4万多ID
         * 转36进制，单线程每秒可以获取3万多ID
         */
        System.out.println("1秒获取ID数量：" + atomicInteger.get());
    }

    /**
     * 测试生成的ID是否会重复
     */
    @Test
    public void testCheckRepeat() {
        if (true) {
            return;
        }

        // TimedUUID.DEFAULT_UUID = TimedUUID.TIMED_ID30;

        long startTime = System.currentTimeMillis();

        int numberOfThread = 50;
        List<Set<String>> hashSets = new ArrayList<>();
        for (int i = 0; i < numberOfThread; i++) {
            hashSets.add(new HashSet<>());
        }

        Thread[] threads = new Thread[numberOfThread];
        for (int i = 0; i < numberOfThread; i++) {
            final Set<String> set = hashSets.get(i);
            threads[i] = new Thread(() -> {
                // 单线程每秒就3万个左右
                for (int j = 0; j < 40000; j++) {
                    set.add(TimedUUID.getId());
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            Try.tcf(() -> thread.join(), false);
        }

        /*
         * 获取每个线程的ID数量
         */
        for (int i = 0; i < hashSets.size(); i++) {
            Set<String> ids = hashSets.get(i);
            System.out.println(i + "---" + ids.size());
        }

        System.out.println("============================================================");
        Set<String> allIds = new HashSet<>();
        for (Set<String> hashSet : hashSets) {
            allIds.addAll(hashSet);
        }

        /*
        1、
        300线程（未产生重复ID）
        所有ID数量：12000000
        耗时：166555
        每秒产生ID数量：72289（未产生重复ID）

        2、
        100线程（未产生重复ID）
        所有ID数量：4000000
        耗时：57200
        每秒产生ID数量：70175（未产生重复ID）

        3、
        50线程（未产生重复ID）
        所有ID数量：2000000
        耗时：27693
        每秒产生ID数量：74074（未产生重复ID）
         */
        System.out.println("随机取一个ID：" + CollectionKit.findRandom(allIds, null));
        System.out.println("所有ID数量：" + allIds.size());
        long spendTime = System.currentTimeMillis() - startTime;
        System.out.println("耗时：" + spendTime);
        System.out.println("每秒产生ID数量：" + (allIds.size() / (spendTime / 1000)));
    }

}
