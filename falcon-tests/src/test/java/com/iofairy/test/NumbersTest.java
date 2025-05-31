package com.iofairy.test;

import com.iofairy.falcon.iterable.PrimitiveArrays;
import com.iofairy.falcon.util.Numbers;
import com.iofairy.top.G;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author GG
 * @version 1.0
 */
public class NumbersTest {
    @Test
    public void testNumberRound() {
        double[] ds = {0.0d / 0.0, Float.POSITIVE_INFINITY, 1.0f, Double.NEGATIVE_INFINITY, 10.000198f, 897.66897975569d, 9999999999.120000000089569999d};
        Double[] ds1 = {-0.0d / 0.0, -0.01 / 0.0, null, Double.NEGATIVE_INFINITY, 10.000198d, 897.66897975569d, 9999999999.120000000089569999d};
        float[] fs = {0.0f / -0.0f, 0.01f / 0.0f, Float.NEGATIVE_INFINITY, -10.000198f, 897.66897975569f};
        Number[] ns = {0.0d / 0.0, Float.POSITIVE_INFINITY, 1.0f, null, Double.NEGATIVE_INFINITY, 10.000198f, 897.66897975569d,
                new BigDecimal("100.1000000"), 9999999999l, 9999999999.120000000089569999d};
        Number[] ns1 = {};
        List<Float> fs1 = PrimitiveArrays.asList(fs);
        List<Number> ns2 = Arrays.asList(ns);
        float[] fs3 = null;

        double[] doubles0 = Numbers.round(ds, 4);
        Double[] doubles1 = Numbers.round(ds1, 4);
        double[] doubles2 = Numbers.round(fs, 4);
        Double[] doubles3 = Numbers.round(ns, 4);
        Double[] doubles4 = Numbers.round(ns1, 4);
        Double[] doubles5 = Numbers.round(fs1, 4);
        Double[] doubles6 = Numbers.round(ns2, 4);
        double[] doubles7 = Numbers.round(fs3, 4);

        double[] doubles10 = Numbers.round(ds);
        Double[] doubles11 = Numbers.round(ds1);
        double[] doubles12 = Numbers.round(fs);
        Double[] doubles13 = Numbers.round(ns);
        Double[] doubles14 = Numbers.round(ns1);
        Double[] doubles15 = Numbers.round(fs1);
        Double[] doubles16 = Numbers.round(ns2);
        double[] doubles17 = Numbers.round(fs3);

        System.out.println(G.toString(doubles0));   // [NaN, ∞, 1.0, -∞, 10.0002, 897.669, 9999999999.12]
        System.out.println(G.toString(doubles1));   // [NaN, -∞, null, -∞, 10.0002, 897.669, 9999999999.12]
        System.out.println(G.toString(doubles2));   // [NaN, ∞, -∞, -10.0002, 897.669]
        System.out.println(G.toString(doubles3));   // [NaN, ∞, 1.0, null, -∞, 10.0002, 897.669, 100.1, 9999999999.0, 9999999999.12]
        System.out.println(G.toString(doubles4));   // []
        System.out.println(G.toString(doubles5));   // [NaN, ∞, -∞, -10.0002, 897.669]
        System.out.println(G.toString(doubles6));   // [NaN, ∞, 1.0, null, -∞, 10.0002, 897.669, 100.1, 9999999999.0, 9999999999.12]
        System.out.println(G.toString(doubles7));   // null
        System.out.println(G.toString(doubles10));  // [NaN, ∞, 1.0, -∞, 10.0, 897.67, 9999999999.12]
        System.out.println(G.toString(doubles11));  // [NaN, -∞, null, -∞, 10.0, 897.67, 9999999999.12]
        System.out.println(G.toString(doubles12));  // [NaN, ∞, -∞, -10.0, 897.67]
        System.out.println(G.toString(doubles13));  // [NaN, ∞, 1.0, null, -∞, 10.0, 897.67, 100.1, 9999999999.0, 9999999999.12]
        System.out.println(G.toString(doubles14));  // []
        System.out.println(G.toString(doubles15));  // [NaN, ∞, -∞, -10.0, 897.67]
        System.out.println(G.toString(doubles16));  // [NaN, ∞, 1.0, null, -∞, 10.0, 897.67, 100.1, 9999999999.0, 9999999999.12]
        System.out.println(G.toString(doubles17));  // null

        assertEquals(G.toString(doubles0), "[NaN, ∞, 1.0, -∞, 10.0002, 897.669, 9999999999.12]");
        assertEquals(G.toString(doubles1), "[NaN, -∞, null, -∞, 10.0002, 897.669, 9999999999.12]");
        assertEquals(G.toString(doubles2), "[NaN, ∞, -∞, -10.0002, 897.669]");
        assertEquals(G.toString(doubles3), "[NaN, ∞, 1.0, null, -∞, 10.0002, 897.669, 100.1, 9999999999.0, 9999999999.12]");
        assertEquals(G.toString(doubles4), "[]");
        assertEquals(G.toString(doubles5), "[NaN, ∞, -∞, -10.0002, 897.669]");
        assertEquals(G.toString(doubles6), "[NaN, ∞, 1.0, null, -∞, 10.0002, 897.669, 100.1, 9999999999.0, 9999999999.12]");
        assertEquals(G.toString(doubles7), "null");
        assertEquals(G.toString(doubles10), "[NaN, ∞, 1.0, -∞, 10.0, 897.67, 9999999999.12]");
        assertEquals(G.toString(doubles11), "[NaN, -∞, null, -∞, 10.0, 897.67, 9999999999.12]");
        assertEquals(G.toString(doubles12), "[NaN, ∞, -∞, -10.0, 897.67]");
        assertEquals(G.toString(doubles13), "[NaN, ∞, 1.0, null, -∞, 10.0, 897.67, 100.1, 9999999999.0, 9999999999.12]");
        assertEquals(G.toString(doubles14), "[]");
        assertEquals(G.toString(doubles15), "[NaN, ∞, -∞, -10.0, 897.67]");
        assertEquals(G.toString(doubles16), "[NaN, ∞, 1.0, null, -∞, 10.0, 897.67, 100.1, 9999999999.0, 9999999999.12]");
        assertEquals(G.toString(doubles17), "null");
    }

    @Test
    public void testDoublesToFloats() {
        float[] fs = {0.0f / -0.0f, 0.01f / 0.0f, Float.NEGATIVE_INFINITY, -10.000198f, 897.66897975569f};
        Number[] ns = {0.0d / 0.0, Float.POSITIVE_INFINITY, 1.0f, null, Double.NEGATIVE_INFINITY, 10.000198f, 897.66897975569d,
                new BigDecimal("9865464949466494946516594949464.6165164949494949197456499494"), 9999999999L, 9999999999.120000000089569999d,
                9865464949466494946516594949464.6165164949494949197456499494f, 9464.6165164949494949197456499494f,
                9865464949466494946516594949464.6165164949494949197456499494d, 64.6165164949494949197456499494d};

        Number[] ns1 = {};
        List<Float> fs1 = PrimitiveArrays.asList(fs);
        List<Number> ns2 = Arrays.asList(ns);
        float[] fs3 = null;
        double[] ds = null;

        float[] floats0 = Numbers.floats(new double[]{1.1d, 1.11d});
        float[] floats1 = Numbers.floats(ds);
        Float[] floats2 = Numbers.floats(fs1);
        Float[] floats3 = Numbers.floats(ns);
        Float[] floats4 = Numbers.floats(ns1);
        Float[] floats5 = Numbers.floats(ns2);

        double[] doubles0 = Numbers.doubles(new float[]{1.1f, 1.11f});
        double[] doubles1 = Numbers.doubles(fs);
        Double[] doubles2 = Numbers.doubles(fs1);
        Double[] doubles3 = Numbers.doubles(ns);
        Double[] doubles4 = Numbers.doubles(ns1);
        Double[] doubles5 = Numbers.doubles(ns2);

        System.out.println(G.toString(floats0, 15));
        System.out.println(G.toString(floats1, 15));
        System.out.println(G.toString(floats2, 15));
        System.out.println(G.toString(floats3, 15));
        System.out.println(G.toString(floats4, 15));
        System.out.println(G.toString(floats5, 15));
        System.out.println(G.toString(doubles0, 15));
        System.out.println(G.toString(doubles1, 15));
        System.out.println(G.toString(doubles2, 15));
        System.out.println(G.toString(doubles3, 15));
        System.out.println(G.toString(doubles4, 15));
        System.out.println(G.toString(doubles5, 15));

        assertEquals(G.toString(floats0, 15), "[1.1, 1.11]");
        assertEquals(G.toString(floats1, 15), "null");
        assertEquals(G.toString(floats2, 15), "[NaN, ∞, -∞, -10.000198, 897.669]");
        assertEquals(G.toString(floats3, 15), "[NaN, ∞, 1.0, null, -∞, 10.000198, 897.669, 9865465000000000000000000000000.0, " +
                "10000000000.0, 10000000000.0, 9865465000000000000000000000000.0, 9464.616, 9865465000000000000000000000000.0, 64.616516]");
        assertEquals(G.toString(floats4, 15), "[]");
        assertEquals(G.toString(floats5, 15), "[NaN, ∞, 1.0, null, -∞, 10.000198, 897.669, 9865465000000000000000000000000.0, " +
                "10000000000.0, 10000000000.0, 9865465000000000000000000000000.0, 9464.616, 9865465000000000000000000000000.0, 64.616516]");
        assertEquals(G.toString(doubles0, 15), "[1.100000023841858, 1.110000014305115]");
        assertEquals(G.toString(doubles1, 15), "[NaN, ∞, -∞, -10.000198364257812, 897.6690063476562]");
        assertEquals(G.toString(doubles2, 15), "[NaN, ∞, -∞, -10.000198364257812, 897.6690063476562]");
        assertEquals(G.toString(doubles3, 15), "[NaN, ∞, 1.0, null, -∞, 10.000198364257812, 897.66897975569, 9865464949466495000000000000000.0, " +
                "9999999999.0, 9999999999.12, 9865465142870303000000000000000.0, 9464.6162109375, 9865464949466495000000000000000.0, 64.6165164949495]");
        assertEquals(G.toString(doubles4, 15), "[]");
        assertEquals(G.toString(doubles5, 15), "[NaN, ∞, 1.0, null, -∞, 10.000198364257812, 897.66897975569, 9865464949466495000000000000000.0, " +
                "9999999999.0, 9999999999.12, 9865465142870303000000000000000.0, 9464.6162109375, 9865464949466495000000000000000.0, 64.6165164949495]");
    }

    @Test
    public void testDivide() {
        BigDecimal divide01 = Numbers.divide(BigDecimal.valueOf(1), BigDecimal.valueOf(3));
        BigDecimal divide02 = Numbers.divide(BigDecimal.valueOf(1), BigDecimal.valueOf(1024L * 1024 * 1024 * 1024L), true);
        BigDecimal divide03 = Numbers.divide(BigDecimal.valueOf(1), BigDecimal.valueOf(1024L * 1024 * 1024 * 1024L), false);
        BigDecimal divide04 = Numbers.divide(BigInteger.valueOf(1), BigInteger.valueOf(33333333333333333L));
        BigDecimal divide05 = Numbers.divide(BigInteger.valueOf(1), BigInteger.valueOf(33333333333333333L), 30);
        BigDecimal divide06 = Numbers.divide(BigInteger.valueOf(1), BigInteger.valueOf(1024L * 1024 * 1024 * 1024L), 15, true);
        BigDecimal divide07 = Numbers.divide(BigInteger.valueOf(1), BigInteger.valueOf(1024L * 1024 * 1024 * 1024L), false);
        BigDecimal divide08 = Numbers.divide(1, BigDecimal.valueOf(3));
        assertThrows(ArithmeticException.class, () -> Numbers.divide(BigDecimal.valueOf(1), 0), "/ by zero");
        assertThrows(NumberFormatException.class, () -> Numbers.divide(BigDecimal.valueOf(1), Double.NaN), "The `number` is NaN or Infinity, can't convert to BigDecimal");
        System.out.println(divide01.toPlainString());     // 0.333333
        System.out.println(divide02.toPlainString());     // 0.000000
        System.out.println(divide03.toPlainString());     // 0.0000000000009094947017729282379150390625
        System.out.println(divide04.toPlainString());     // 0.000000
        System.out.println(divide05.toPlainString());     // 0.000000000000000030000000000000
        System.out.println(divide06.toPlainString());     // 0.000000000000909
        System.out.println(divide07.toPlainString());     // 0.0000000000009094947017729282379150390625
        System.out.println(divide08.toPlainString());     // 0.333333

        assertEquals(divide01.toPlainString(), "0.333333");
        assertEquals(divide02.toPlainString(), "0.000000");
        assertEquals(divide03.toPlainString(), "0.0000000000009094947017729282379150390625");
        assertEquals(divide04.toPlainString(), "0.000000");
        assertEquals(divide05.toPlainString(), "0.000000000000000030000000000000");
        assertEquals(divide06.toPlainString(), "0.000000000000909");
        assertEquals(divide07.toPlainString(), "0.0000000000009094947017729282379150390625");
        assertEquals(divide08.toPlainString(), "0.333333");
    }

    @Test
    public void testDividePower() {
        Number divide01 = Numbers.dividePower(BigDecimal.valueOf(1), BigDecimal.valueOf(3));
        Number divide02 = Numbers.dividePower(BigDecimal.valueOf(1), BigDecimal.valueOf(1024L * 1024 * 1024 * 1024L), true);
        Number divide03 = Numbers.dividePower(BigDecimal.valueOf(1), BigDecimal.valueOf(1024L * 1024 * 1024 * 1024L), false);
        Number divide04 = Numbers.dividePower(BigInteger.valueOf(1), BigInteger.valueOf(33333333333333333L));
        Number divide05 = Numbers.dividePower(BigInteger.valueOf(1), BigInteger.valueOf(33333333333333333L), 30);
        Number divide06 = Numbers.dividePower(BigInteger.valueOf(1), BigInteger.valueOf(1024L * 1024 * 1024 * 1024L), 15, true);
        Number divide07 = Numbers.dividePower(BigInteger.valueOf(1), BigInteger.valueOf(1024L * 1024 * 1024 * 1024L), false);
        Number divide08 = Numbers.dividePower(1, 3);
        Number divide09 = Numbers.dividePower(1, 3.0);
        Number divide10 = Numbers.dividePower(-1, 0.0);
        Number divide11 = Numbers.dividePower(1, Double.NaN);
        assertThrows(ArithmeticException.class, () -> Numbers.dividePower(1, 0), "/ by zero");
        assertThrows(ArithmeticException.class, () -> Numbers.dividePower(BigDecimal.valueOf(1), 0), "/ by zero");
        assertThrows(NumberFormatException.class, () -> Numbers.dividePower(BigDecimal.valueOf(1), Double.NaN), "The `number` is NaN or Infinity, can't convert to BigDecimal");
        System.out.println(G.toString(divide01));     // 0.333333
        System.out.println(G.toString(divide02));     // 0.000000
        System.out.println(G.toString(divide03, 50));     // 0.0000000000009094947017729282379150390625
        System.out.println(G.toString(divide04));     // 0.000000
        System.out.println(G.toString(divide05, 50));     // 0.000000000000000030000000000000
        System.out.println(G.toString(divide06));     // 0.000000000000909
        System.out.println(G.toString(divide07, 50));     // 0
        System.out.println(G.toString(divide08));     // 0
        System.out.println(G.toString(divide09));
        System.out.println(G.toString(divide10));
        System.out.println(G.toString(divide11));

        assertEquals(G.toString(divide01), "0.333333");
        assertEquals(G.toString(divide02), "0.0");
        assertEquals(G.toString(divide03, 50), "0.0000000000009094947017729282379150390625");
        assertEquals(G.toString(divide04), "0");
        assertEquals(G.toString(divide05, 50), "0");
        assertEquals(G.toString(divide06), "0");
        assertEquals(G.toString(divide07, 50), "0");
        assertEquals(G.toString(divide08), "0");
        assertEquals(G.toString(divide09), "0.333333");
        assertEquals(G.toString(divide10), "-∞");
        assertEquals(G.toString(divide11), "NaN");
    }

    @Test
    public void testLog() {
        double log1 = Numbers.log(100, 10);
        double log2 = Numbers.log(2, 100);
        double log3 = Numbers.log(6, 36);
        double log4 = Numbers.log(36, 6);
        double log5 = Numbers.log(6, 30);
        double log6 = Numbers.log(2, 16);
        double log7 = Numbers.log(3, 9);
        double log8 = Numbers.log(16, 8);
        System.out.println(log1);       // 0.5
        System.out.println(log2);       // 6.643856189774725
        System.out.println(log3);       // 2.0
        System.out.println(log4);       // 0.5
        System.out.println(log5);       // 1.8982444017039273
        System.out.println(log6);       // 4.0
        System.out.println(log7);       // 2.0
        System.out.println(log8);       // 0.75

        assertEquals(log1, 0.5);
        assertEquals(log2, 6.643856189774725d);
        assertEquals(log3, 2.0);
        assertEquals(log4, 0.5);
        assertEquals(log5, 1.8982444017039273);
        assertEquals(log6, 4.0);
        assertEquals(log7, 2.0);
        assertEquals(log8, 0.75);

    }

    @Test
    public void testRadixConversion() {
        String number01 = Numbers.radixConversion("10010", 2, 36, true);
        String number02 = Numbers.radixConversion("-2Za", 36, 10, true);
        String number03 = Numbers.radixConversion("-3862", 10, 36, true);
        String number04 = Numbers.radixConversion("-2Za", 36, 2, false);
        String number05 = Numbers.radixConversion("-2Za", 36, 6, false);
        String number06 = Numbers.radixConversion("-2ZA", 36, 6, true);
        String number07 = Numbers.radixConversion("-550014", 6, 36, true);
        String number08 = Numbers.radixConversion("2zA", 36, 2, true);
        String number09 = Numbers.radixConversion("-10010", 2, 16, true);
        String number10 = Numbers.radixConversion("-1c", 16, 2, true);
        String number11 = Numbers.radixConversion("+1C", 16, 2, true);
        System.out.println("number01: " + number01);   // i
        System.out.println("number02: " + number02);   // -3862
        System.out.println("number03: " + number03);   // -2za
        System.out.println("number04: " + number04);   // -111100010110
        System.out.println("number05: " + number05);   // -25514
        System.out.println("number06: " + number06);   // -025514
        System.out.println("number07: " + number07);   // -z0a
        System.out.println("number08: " + number08);   // 111100010110
        System.out.println("number09: " + number09);   // -12
        System.out.println("number10: " + number10);   // -00011100
        System.out.println("number11: " + number11);   // 00011100

        assertEquals(number01, "i");
        assertEquals(number02, "-3862");
        assertEquals(number03, "-2za");
        assertEquals(number04, "-111100010110");
        assertEquals(number05, "-25514");
        assertEquals(number06, "-025514");
        assertEquals(number07, "-z0a");
        assertEquals(number08, "111100010110");
        assertEquals(number09, "-12");
        assertEquals(number10, "-00011100");
        assertEquals(number11, "00011100");


        try {
            Numbers.radixConversion("aaaaa", 2, 4, true);
            throwException();
        } catch (Exception e) {
            assertEquals(e.getClass(), NumberFormatException.class);
            assertEquals(e.getMessage(), "在2进制下，字符串【aaaaa】无法转成数字！");
        }
        try {
            Numbers.radixConversion("010101", 1, 4, true);
            throwException();
        } catch (Exception e) {
            assertEquals(e.getClass(), IllegalArgumentException.class);
            assertEquals(e.getMessage(), "The parameter `fromRadix` must be in [2, 36], currently is [1]. ");
        }
        try {
            Numbers.radixConversion("010101", 2, 37, true);
            throwException();
        } catch (Exception e) {
            assertEquals(e.getClass(), IllegalArgumentException.class);
            assertEquals(e.getMessage(), "The parameter `toRadix` must be in [2, 36], currently is [37]. ");
        }

    }

    private void throwException() {
        throw new RuntimeException();
    }

}
