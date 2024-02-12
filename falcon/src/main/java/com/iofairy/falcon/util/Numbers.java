/*
 * Copyright (C) 2021 iofairy, <https://github.com/iofairy/falcon>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iofairy.falcon.util;

import com.iofairy.top.O;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Random;

import static com.iofairy.falcon.misc.Preconditions.*;

/**
 * Number Utils
 *
 * @since 0.3.0
 */
public class Numbers {
    /**
     * 对 float数组 设置精度并转为 double[]
     *
     * @param floats float数组
     * @return double[]
     */
    public static double[] round(float[] floats) {
        return round(floats, 2, RoundingMode.HALF_UP);
    }

    /**
     * 对 float数组 设置精度并转为 double[]
     *
     * @param floats   float数组
     * @param newScale 精度
     * @return double[]
     */
    public static double[] round(float[] floats, int newScale) {
        return round(floats, newScale, RoundingMode.HALF_UP);
    }

    /**
     * 对 float数组 设置精度并转为 double[]
     *
     * @param floats       float数组
     * @param newScale     精度
     * @param roundingMode 取整模式
     * @return double[]
     */
    public static double[] round(float[] floats, int newScale, RoundingMode roundingMode) {
        if (floats == null) return null;
        double[] ds = new double[floats.length];
        for (int i = 0; i < floats.length; i++) {
            ds[i] = round(floats[i], newScale, roundingMode);
        }
        return ds;
    }

    /**
     * 对 double数组 设置精度并转为 double[]
     *
     * @param doubles double数组
     * @return double[]
     */
    public static double[] round(double[] doubles) {
        return round(doubles, 2, RoundingMode.HALF_UP);
    }

    /**
     * 对 double数组 设置精度并转为 double[]
     *
     * @param doubles  double数组
     * @param newScale 精度
     * @return double[]
     */
    public static double[] round(double[] doubles, int newScale) {
        return round(doubles, newScale, RoundingMode.HALF_UP);
    }

    /**
     * 对 double数组 设置精度并转为 double[]
     *
     * @param doubles      double数组
     * @param newScale     精度
     * @param roundingMode 取整模式
     * @return double[]
     */
    public static double[] round(double[] doubles, int newScale, RoundingMode roundingMode) {
        if (doubles == null) return null;
        double[] ds = new double[doubles.length];
        for (int i = 0; i < doubles.length; i++) {
            ds[i] = round(doubles[i], newScale, roundingMode);
        }
        return ds;
    }

    /**
     * 对 number数组 设置精度并转为 Double[]
     *
     * @param numbers number数组
     * @return Double[]
     */
    public static Double[] round(Number[] numbers) {
        return round(numbers, 2, RoundingMode.HALF_UP);
    }

    /**
     * 对 number数组 设置精度并转为 Double[]
     *
     * @param numbers  number数组
     * @param newScale 精度
     * @return Double[]
     */
    public static Double[] round(Number[] numbers, int newScale) {
        return round(numbers, newScale, RoundingMode.HALF_UP);
    }

    /**
     * 对 number数组 设置精度并转为 Double[]
     *
     * @param numbers      number数组
     * @param newScale     精度
     * @param roundingMode 取整模式
     * @return Double[]
     */
    public static Double[] round(Number[] numbers, int newScale, RoundingMode roundingMode) {
        if (numbers == null) return null;
        Double[] ds = new Double[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            ds[i] = round(numbers[i], newScale, roundingMode);
        }
        return ds;
    }

    /**
     * 对 number集合 设置精度并转为 Double[]
     *
     * @param numbers number集合
     * @return Double[]
     */
    public static Double[] round(Collection<? extends Number> numbers) {
        return round(numbers, 2, RoundingMode.HALF_UP);
    }

    /**
     * 对 number集合 设置精度并转为 Double[]
     *
     * @param numbers  number集合
     * @param newScale 精度
     * @return Double[]
     */
    public static Double[] round(Collection<? extends Number> numbers, int newScale) {
        return round(numbers, newScale, RoundingMode.HALF_UP);
    }

    /**
     * 对 number集合 设置精度并转为 Double[]
     *
     * @param numbers      number集合
     * @param newScale     精度
     * @param roundingMode 取整模式
     * @return Double[]
     */
    public static Double[] round(Collection<? extends Number> numbers, int newScale, RoundingMode roundingMode) {
        if (numbers == null) return null;
        if (numbers.isEmpty()) return new Double[0];
        Double[] ds = new Double[numbers.size()];
        Iterator<? extends Number> iterator = numbers.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            ds[i] = round(iterator.next(), newScale, roundingMode);
            i++;
        }
        return ds;
    }

    /**
     * Number 设置精度并转 Double
     *
     * @param number number
     * @return Double
     */
    public static Double round(Number number) {
        return round(number, 2, RoundingMode.HALF_UP);
    }

    /**
     * Number 设置精度并转 Double
     *
     * @param number   number
     * @param newScale 精度
     * @return Double
     */
    public static Double round(Number number, int newScale) {
        return round(number, newScale, RoundingMode.HALF_UP);
    }

    /**
     * Number 设置精度并转 Double
     *
     * @param number       number
     * @param newScale     精度
     * @param roundingMode 取整模式
     * @return Double
     */
    public static Double round(Number number, int newScale, RoundingMode roundingMode) {
        if (number == null) return null;
        if (O.isInfinityOrNaN(number)) return number.doubleValue();
        if (O.isFloat(number) || number instanceof BigDecimal) {
            return round(O.toBigDecimal(number), newScale, roundingMode);
        } else {
            return newScale < 0 ? round(O.toBigDecimal(number), newScale, roundingMode) : number.doubleValue();
        }
    }

    /**
     * BigDecimal 设置精度并转 Double
     *
     * @param bigDecimal   bigDecimal
     * @param newScale     精度
     * @param roundingMode 取整模式
     * @return Double
     */
    public static Double round(BigDecimal bigDecimal, int newScale, RoundingMode roundingMode) {
        if (bigDecimal == null) return null;
        return bigDecimal.setScale(newScale, roundingMode).doubleValue();
    }

    /**
     * Number[] 转 Double[]
     *
     * @param numbers Number array
     * @return Double[]
     */
    public static Double[] doubles(Number... numbers) {
        if (numbers == null) return null;
        Double[] ds = new Double[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            Number number = numbers[i];
            ds[i] = number == null ? null : number.doubleValue();
        }
        return ds;
    }

    /**
     * Number集合 转 Double[]
     *
     * @param numbers Number 集合
     * @return Double[]
     */
    public static Double[] doubles(Collection<? extends Number> numbers) {
        if (numbers == null) return null;
        if (numbers.isEmpty()) return new Double[0];
        Double[] ds = new Double[numbers.size()];
        Iterator<? extends Number> iterator = numbers.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Number number = iterator.next();
            ds[i] = number == null ? null : number.doubleValue();
            i++;
        }
        return ds;
    }


    /**
     * float[] 转 double[]
     *
     * @param floats float array
     * @return double[]
     */
    public static double[] doubles(float[] floats) {
        if (floats == null) return null;
        double[] ds = new double[floats.length];
        for (int i = 0; i < floats.length; i++) {
            ds[i] = floats[i];
        }
        return ds;
    }

    /**
     * Number[] to Float[]
     *
     * @param numbers Number array
     * @return Float[]
     */
    public static Float[] floats(Number... numbers) {
        if (numbers == null) return null;
        Float[] fs = new Float[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            Number number = numbers[i];
            fs[i] = number == null ? null : number.floatValue();
        }
        return fs;
    }

    /**
     * Number集合 转 Float[]
     *
     * @param numbers Number 集合
     * @return Float[]
     */
    public static Float[] floats(Collection<? extends Number> numbers) {
        if (numbers == null) return null;
        if (numbers.isEmpty()) return new Float[0];
        Float[] fs = new Float[numbers.size()];
        Iterator<? extends Number> iterator = numbers.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Number number = iterator.next();
            fs[i] = number == null ? null : number.floatValue();
            i++;
        }
        return fs;
    }

    /**
     * double[] 转 float[]
     *
     * @param doubles double array
     * @return float[]
     */
    public static float[] floats(double[] doubles) {
        if (doubles == null) return null;
        float[] fs = new float[doubles.length];
        for (int i = 0; i < doubles.length; i++) {
            fs[i] = (float) doubles[i];
        }
        return fs;
    }


    /**
     * Division calculation function used to divide two BigDecimal numbers.
     *
     * @param dividend       The dividend, cannot be null.
     * @param divisor        The divisor, cannot be null.
     * @param scale          The scale for rounding the result.
     * @param roundingMode   The rounding mode. If null, default to {@link RoundingMode#HALF_UP}.
     * @param alwaysSetScale flag indicating whether to always set scale.
     *                       {@code true}, the scale will always be set. <b>Otherwise</b>,
     *                       the scale will only be set when <b>the division is not exact</b>.
     * @return The result of the division calculation.
     * @throws NullPointerException if the dividend or divisor is null.
     * @since 0.4.10
     */
    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor, int scale, RoundingMode roundingMode, boolean alwaysSetScale) {
        checkHasNullNPE(args(dividend, divisor), args("dividend", "divisor"));

        if (roundingMode == null) roundingMode = RoundingMode.HALF_UP;

        if (alwaysSetScale) {
            return dividend.divide(divisor, scale, roundingMode);
        } else {
            try {
                return dividend.divide(divisor);
            } catch (ArithmeticException e) {
                if (e.getMessage() != null && e.getMessage().contains("Non-terminating decimal expansion")) {
                    return dividend.divide(divisor, scale, roundingMode);
                }
                throw e;
            }
        }
    }

    /**
     * Division calculation function used to divide two BigDecimal numbers.
     *
     * @param dividend       The dividend, cannot be null.
     * @param divisor        The divisor, cannot be null.
     * @param scale          The scale for rounding the result.
     * @param alwaysSetScale flag indicating whether to always set scale.
     *                       {@code true}, the scale will always be set. <b>Otherwise</b>,
     *                       the scale will only be set when <b>the division is not exact</b>.
     * @return The result of the division calculation.
     * @throws NullPointerException if the dividend or divisor is null.
     * @since 0.4.10
     */
    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor, int scale, boolean alwaysSetScale) {
        return divide(dividend, divisor, scale, RoundingMode.HALF_UP, alwaysSetScale);
    }

    /**
     * Division calculation function used to divide two BigDecimal numbers.
     *
     * @param dividend       The dividend, cannot be null.
     * @param divisor        The divisor, cannot be null.
     * @param alwaysSetScale flag indicating whether to always set scale.
     *                       {@code true}, the scale will always be set. <b>Otherwise</b>,
     *                       the scale will only be set when <b>the division is not exact</b>.
     * @return The result of the division calculation.
     * @throws NullPointerException if the dividend or divisor is null.
     * @since 0.4.10
     */
    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor, boolean alwaysSetScale) {
        return divide(dividend, divisor, 6, RoundingMode.HALF_UP, alwaysSetScale);
    }

    /**
     * Division calculation function used to divide two BigDecimal numbers.
     *
     * @param dividend     The dividend, cannot be null.
     * @param divisor      The divisor, cannot be null.
     * @param scale        The scale for rounding the result.
     * @param roundingMode The rounding mode. If null, default to {@link RoundingMode#HALF_UP}.
     * @return The result of the division calculation.
     * @throws NullPointerException if the dividend or divisor is null.
     * @since 0.4.10
     */
    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor, int scale, RoundingMode roundingMode) {
        return divide(dividend, divisor, scale, roundingMode, true);
    }

    /**
     * Division calculation function used to divide two BigDecimal numbers.
     *
     * @param dividend The dividend, cannot be null.
     * @param divisor  The divisor, cannot be null.
     * @param scale    The scale for rounding the result.
     * @return The result of the division calculation.
     * @throws NullPointerException if the dividend or divisor is null.
     * @since 0.4.10
     */
    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor, int scale) {
        return divide(dividend, divisor, scale, RoundingMode.HALF_UP);
    }

    /**
     * Division calculation function used to divide two BigDecimal numbers.
     *
     * @param dividend The dividend, cannot be null.
     * @param divisor  The divisor, cannot be null.
     * @return The result of the division calculation.
     * @throws NullPointerException if the dividend or divisor is null.
     * @since 0.4.10
     */
    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor) {
        return divide(dividend, divisor, 6, RoundingMode.HALF_UP);
    }


    /**
     * Division calculation function used to divide two BigInteger numbers.
     *
     * @param dividend       The dividend, cannot be null.
     * @param divisor        The divisor, cannot be null.
     * @param scale          The scale for rounding the result.
     * @param roundingMode   The rounding mode. If null, default to {@link RoundingMode#HALF_UP}.
     * @param alwaysSetScale flag indicating whether to always set scale.
     *                       {@code true}, the scale will always be set. <b>Otherwise</b>,
     *                       the scale will only be set when <b>the division is not exact</b>.
     * @return The result of the division calculation.
     * @throws NullPointerException if the dividend or divisor is null.
     * @since 0.4.10
     */
    public static BigDecimal divide(BigInteger dividend, BigInteger divisor, int scale, RoundingMode roundingMode, boolean alwaysSetScale) {
        checkHasNullNPE(args(dividend, divisor), args("dividend", "divisor"));

        if (roundingMode == null) roundingMode = RoundingMode.HALF_UP;
        BigDecimal decimalDividend = new BigDecimal(dividend);
        BigDecimal decimalDivisor = new BigDecimal(divisor);

        if (alwaysSetScale) {
            return decimalDividend.divide(decimalDivisor, scale, roundingMode);
        } else {
            try {
                return decimalDividend.divide(decimalDivisor);
            } catch (ArithmeticException e) {
                if (e.getMessage() != null && e.getMessage().contains("Non-terminating decimal expansion")) {
                    return decimalDividend.divide(decimalDivisor, scale, roundingMode);
                }
                throw e;
            }
        }
    }

    /**
     * Division calculation function used to divide two BigInteger numbers.
     *
     * @param dividend       The dividend, cannot be null.
     * @param divisor        The divisor, cannot be null.
     * @param scale          The scale for rounding the result.
     * @param alwaysSetScale flag indicating whether to always set scale.
     *                       {@code true}, the scale will always be set. <b>Otherwise</b>,
     *                       the scale will only be set when <b>the division is not exact</b>.
     * @return The result of the division calculation.
     * @throws NullPointerException if the dividend or divisor is null.
     * @since 0.4.10
     */
    public static BigDecimal divide(BigInteger dividend, BigInteger divisor, int scale, boolean alwaysSetScale) {
        return divide(dividend, divisor, scale, RoundingMode.HALF_UP, alwaysSetScale);
    }

    /**
     * Division calculation function used to divide two BigInteger numbers.
     *
     * @param dividend       The dividend, cannot be null.
     * @param divisor        The divisor, cannot be null.
     * @param alwaysSetScale flag indicating whether to always set scale.
     *                       {@code true}, the scale will always be set. <b>Otherwise</b>,
     *                       the scale will only be set when <b>the division is not exact</b>.
     * @return The result of the division calculation.
     * @throws NullPointerException if the dividend or divisor is null.
     * @since 0.4.10
     */
    public static BigDecimal divide(BigInteger dividend, BigInteger divisor, boolean alwaysSetScale) {
        return divide(dividend, divisor, 6, RoundingMode.HALF_UP, alwaysSetScale);
    }

    /**
     * Division calculation function used to divide two BigInteger numbers.
     *
     * @param dividend     The dividend, cannot be null.
     * @param divisor      The divisor, cannot be null.
     * @param scale        The scale for rounding the result.
     * @param roundingMode The rounding mode. If null, default to {@link RoundingMode#HALF_UP}.
     * @return The result of the division calculation.
     * @throws NullPointerException if the dividend or divisor is null.
     * @since 0.4.10
     */
    public static BigDecimal divide(BigInteger dividend, BigInteger divisor, int scale, RoundingMode roundingMode) {
        return divide(dividend, divisor, scale, roundingMode, true);
    }

    /**
     * Division calculation function used to divide two BigInteger numbers.
     *
     * @param dividend The dividend, cannot be null.
     * @param divisor  The divisor, cannot be null.
     * @param scale    The scale for rounding the result.
     * @return The result of the division calculation.
     * @throws NullPointerException if the dividend or divisor is null.
     * @since 0.4.10
     */
    public static BigDecimal divide(BigInteger dividend, BigInteger divisor, int scale) {
        return divide(dividend, divisor, scale, RoundingMode.HALF_UP);
    }

    /**
     * Division calculation function used to divide two BigInteger numbers.
     *
     * @param dividend The dividend, cannot be null.
     * @param divisor  The divisor, cannot be null.
     * @return The result of the division calculation.
     * @throws NullPointerException if the dividend or divisor is null.
     * @since 0.4.10
     */
    public static BigDecimal divide(BigInteger dividend, BigInteger divisor) {
        return divide(dividend, divisor, 6, RoundingMode.HALF_UP);
    }

    /**
     * Converts BigDecimal to a BigInteger
     *
     * @param bigDecimal   bigDecimal
     * @param roundingMode The rounding mode. If null, default to {@link RoundingMode#DOWN}.
     * @return BigInteger
     * @see BigDecimal#toBigInteger()
     * @since 0.4.10
     */
    public static BigInteger toBigInteger(BigDecimal bigDecimal, RoundingMode roundingMode) {
        checkNullNPE(bigDecimal, args("bigDecimal"));

        if (roundingMode == null) roundingMode = RoundingMode.DOWN;
        return bigDecimal.setScale(0, roundingMode).toBigInteger();
    }

    /**
     * Converts BigDecimal to a BigInteger
     *
     * @param bigDecimal bigDecimal
     * @return BigInteger
     * @see BigDecimal#toBigInteger()
     * @since 0.4.10
     */
    public static BigInteger toBigInteger(BigDecimal bigDecimal) {
        checkNullNPE(bigDecimal, args("bigDecimal"));
        return bigDecimal.toBigInteger();
    }

    /**
     * Converts BigDecimal to a long
     *
     * @param bigDecimal   bigDecimal
     * @param roundingMode The rounding mode. If null, default to {@link RoundingMode#DOWN}.
     * @return long value
     * @see BigDecimal#longValue()
     * @since 0.4.10
     */
    public static long longValue(BigDecimal bigDecimal, RoundingMode roundingMode) {
        checkNullNPE(bigDecimal, args("bigDecimal"));

        if (roundingMode == null) roundingMode = RoundingMode.DOWN;
        return bigDecimal.setScale(0, roundingMode).longValue();
    }

    /**
     * Converts BigDecimal to a long
     *
     * @param bigDecimal bigDecimal
     * @return long value
     * @see BigDecimal#longValue()
     * @since 0.4.10
     */
    public static long longValue(BigDecimal bigDecimal) {
        checkNullNPE(bigDecimal, args("bigDecimal"));
        return bigDecimal.longValue();
    }

    /**
     * 指定随机数位数，获取int型的随机数
     *
     * @param digits 随机数的位数
     * @return int型的随机数
     * @since 0.4.15
     */
    public static int randomInt(int digits) {
        if (digits <= 0 || digits > 9) return 0;
        int pow = (int) Math.pow(10, digits - 1);
        return new Random().nextInt(9 * pow) + pow;
    }

}
