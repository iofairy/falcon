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
    public void testBigDecimalDivide() {
        BigDecimal divide01 = Numbers.divide(BigDecimal.valueOf(1), BigDecimal.valueOf(3));
        BigDecimal divide02 = Numbers.divide(BigDecimal.valueOf(1), BigDecimal.valueOf(1024L * 1024 * 1024 * 1024L), true);
        BigDecimal divide03 = Numbers.divide(BigDecimal.valueOf(1), BigDecimal.valueOf(1024L * 1024 * 1024 * 1024L), false);
        BigDecimal divide04 = Numbers.divide(BigInteger.valueOf(1), BigInteger.valueOf(33333333333333333L));
        BigDecimal divide05 = Numbers.divide(BigInteger.valueOf(1), BigInteger.valueOf(33333333333333333L), 30);
        BigDecimal divide06 = Numbers.divide(BigInteger.valueOf(1), BigInteger.valueOf(1024L * 1024 * 1024 * 1024L), 15, true);
        BigDecimal divide07 = Numbers.divide(BigInteger.valueOf(1), BigInteger.valueOf(1024L * 1024 * 1024 * 1024L), false);
        System.out.println(divide01.toPlainString());     // 0.333333
        System.out.println(divide02.toPlainString());     // 0.000000
        System.out.println(divide03.toPlainString());     // 0.0000000000009094947017729282379150390625
        System.out.println(divide04.toPlainString());     // 0.000000
        System.out.println(divide05.toPlainString());     // 0.000000000000000030000000000000
        System.out.println(divide06.toPlainString());     // 0.000000000000909
        System.out.println(divide07.toPlainString());     // 0.0000000000009094947017729282379150390625

        assertEquals(divide01.toPlainString(), "0.333333");
        assertEquals(divide02.toPlainString(), "0.000000");
        assertEquals(divide03.toPlainString(), "0.0000000000009094947017729282379150390625");
        assertEquals(divide04.toPlainString(), "0.000000");
        assertEquals(divide05.toPlainString(), "0.000000000000000030000000000000");
        assertEquals(divide06.toPlainString(), "0.000000000000909");
        assertEquals(divide07.toPlainString(), "0.0000000000009094947017729282379150390625");

    }


}
