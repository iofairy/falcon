package com.iofairy.test.reflect;

/**
 * @author GG
 * @version 1.0
 * @date 2022/6/19 12:15
 */
public class Outer {
    public static Class<?> anonymousClass = new Outer() {
    }.getClass();

    public static class StaticInner1 {
        public enum Enum1 {

        }

        public static class StaticInner2 {

        }

        public @interface Annotation {

        }
    }

    public class Inner1 {
        public class Inner2 {

        }

    }
}
