package com.iofairy.test;

import com.iofairy.except.IDGenerateException;
import com.iofairy.falcon.id.OrderedID;
import com.iofairy.falcon.time.Stopwatch;
import com.iofairy.tcf.Try;
import lombok.SneakyThrows;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author GG
 * @version 1.0
 * @date 2025/1/11 19:54
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderedIDTest {
    @Order(1)
    @SneakyThrows
    @Test
    public void testOrderedID() {
        Set<Long> ids = new ConcurrentSkipListSet<>();
        System.out.println(OrderedID.nextId("2025-01-01"));
        System.out.println(OrderedID.nextId("ABC_2025-01-01"));
        System.out.println("============================================================");
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                for (int i1 = 0; i1 < 100; i1++) {
                    if (i1 == 10) {
                        System.out.println(Thread.currentThread().getName() + " *** All max ids: " + OrderedID.allMaxIds());
                    }
                    long id = OrderedID.nextId();
                    ids.add(id);
                    // System.out.println(Thread.currentThread().getName() + " *** " + id);
                }
            }).start();
        }

        Try.sleep(1000);
        System.out.println(OrderedID.allMaxIds());
        System.out.println(ids.size());
        assertEquals("{=2999, ABC_2025-01-01=0, 2025-01-01=0}", OrderedID.allMaxIds().toString());
        assertEquals(3000, ids.size());

    }

    @Order(2)
    @SneakyThrows
    @Test
    public void testOrderedIDPerformance() {
        Stopwatch stopwatch = Stopwatch.run();
        Thread thread = new Thread(() -> {
            for (int j = 0; j < 100000000; j++) {
                OrderedID.nextId("分类1");
            }
        });
        thread.start();
        thread.join(1000);

        System.out.println(stopwatch);
        System.out.println(OrderedID.nextId("分类1"));
    }

    @Order(3)
    @SneakyThrows
    @Test
    public void testOrderedIDException() {
        try {
            long id = OrderedID.nextId("AAA-20250105", key -> {
                throw new IOException("IO异常");
            });

            throwException();
        } catch (Exception e) {
            assertSame(e.getClass(), IDGenerateException.class);
            assertEquals(e.getMessage(), "OrderedID生成分类【AAA-20250105】自增ID异常。");
            // assertEquals(e.getMessage(), "OrderedID failed to generate the Self-increment ID of classify [AAA-20250105]. ");
        }
    }


    private void throwException() {
        throw new RuntimeException();
    }

}
