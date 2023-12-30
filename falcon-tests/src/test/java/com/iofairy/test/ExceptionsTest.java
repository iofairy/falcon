package com.iofairy.test;

import com.iofairy.falcon.util.Exceptions;
import org.junit.jupiter.api.Test;

/**
 * @author GG
 * @version 1.0
 * @date 2023/12/29 16:27
 */
public class ExceptionsTest {
    @Test
    public void testExceptions() {
        RuntimeException runtimeException = new RuntimeException("哎呀！运行时异常！");
        NullPointerException nullPointerException = new NullPointerException("注意！有对象为null值！");

        String stackTrace01 = Exceptions.stackTrace(runtimeException.getMessage());
        String stackTrace02 = Exceptions.stackTrace(nullPointerException.getMessage(), null);
        String stackTrace03 = Exceptions.stackTrace(runtimeException.getMessage(), "运行时异常");
        String stackTrace04 = Exceptions.stackTrace(nullPointerException.getMessage(), "空指针异常");
        String stackTrace11 = Exceptions.stackTrace(runtimeException);
        String stackTrace12 = Exceptions.stackTrace(nullPointerException, null);
        String stackTrace13 = Exceptions.stackTrace(runtimeException, "运行时异常");
        String stackTrace14 = Exceptions.stackTrace(nullPointerException, "空指针异常");
        String stackTrace21 = Exceptions.stackTrace(runtimeException, 100);
        String stackTrace22 = Exceptions.stackTrace(nullPointerException, null, 100);
        String stackTrace23 = Exceptions.stackTrace(runtimeException, "运行时异常", 100);
        String stackTrace24 = Exceptions.stackTrace(nullPointerException, "空指针异常", 100);

        System.out.println(stackTrace01);
        System.out.println(stackTrace02);
        System.out.println(stackTrace03);
        System.out.println(stackTrace04);
        System.out.println("============================================================");
        System.out.println(stackTrace11);
        System.out.println(stackTrace12);
        System.out.println(stackTrace13);
        System.out.println(stackTrace14);
        System.out.println("============================================================");
        System.out.println(stackTrace21);
        System.out.println(stackTrace22);
        System.out.println(stackTrace23);
        System.out.println(stackTrace24);

    }
}
