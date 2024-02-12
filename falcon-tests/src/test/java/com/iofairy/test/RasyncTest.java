package com.iofairy.test;

import com.iofairy.falcon.base.Rasync;
import com.iofairy.tcf.Try;
import org.junit.jupiter.api.Test;

/**
 * @author GG
 * @version 1.0
 * @date 2023/12/28 16:44
 */
public class RasyncTest {
    @Test
    public void testRasync() {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                Rasync<String> rasync = Rasync.of("abc", new NullPointerException("发生了空指针异常！"));
                System.out.println(rasync);
            }).start();
        }
        Rasync<String> rasync = Rasync.of("abc", new NullPointerException("发生了空指针异常！"));
        System.out.println(rasync);
        System.out.println(rasync.getResult());
        System.out.println(rasync.result);
        Try.sleep(1000);

    }

}
