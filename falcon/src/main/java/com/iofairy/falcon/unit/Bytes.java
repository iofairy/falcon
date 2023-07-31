package com.iofairy.falcon.unit;

import com.iofairy.except.UnexpectedParameterException;
import com.iofairy.falcon.util.Numbers;
import com.iofairy.tuple.Tuple;
import com.iofairy.tuple.Tuple2;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Objects;

/**
 * 计算机存储单位间的换算与转换
 *
 * @since 0.4.10
 */
public class Bytes implements Comparable<Bytes>, Serializable {
    private static final long serialVersionUID = 98765567006076565L;

    /**
     * bit
     *
     * @see ByteBinaryUnit#bit
     * @see ByteDecimalUnit#bit
     */
    public final long bit;
    /**
     * B
     *
     * @see ByteBinaryUnit#B
     * @see ByteDecimalUnit#B
     */
    public final long b;
    /**
     * KB
     *
     * @see ByteBinaryUnit#KiB
     * @see ByteDecimalUnit#KB
     */
    public final long kb;
    /**
     * MB
     *
     * @see ByteBinaryUnit#MiB
     * @see ByteDecimalUnit#MB
     */
    public final long mb;
    /**
     * GB
     *
     * @see ByteBinaryUnit#GiB
     * @see ByteDecimalUnit#GB
     */
    public final long gb;
    /**
     * TB
     *
     * @see ByteBinaryUnit#TiB
     * @see ByteDecimalUnit#TB
     */
    public final long tb;
    /**
     * PB
     *
     * @see ByteBinaryUnit#PiB
     * @see ByteDecimalUnit#PB
     */
    public final long pb;
    /**
     * EB
     *
     * @see ByteBinaryUnit#EiB
     * @see ByteDecimalUnit#EB
     */
    public final long eb;
    /**
     * ZB
     *
     * @see ByteBinaryUnit#ZiB
     * @see ByteDecimalUnit#ZB
     */
    public final long zb;
    /**
     * YB
     *
     * @see ByteBinaryUnit#YiB
     * @see ByteDecimalUnit#YB
     */
    public final long yb;

    public final boolean isBinaryUnit;
    /*
     * 换算成每个单位的总大小
     */
    BigInteger bits;
    BigInteger bs;
    BigInteger kbs;
    BigInteger mbs;
    BigInteger gbs;
    BigInteger tbs;
    BigInteger pbs;
    BigInteger ebs;
    BigInteger zbs;
    BigInteger ybs;

    public static final Bytes ZERO_BINARY = new Bytes(0, 0, 0, 0, true);
    public static final Bytes ZERO_DECIMAL = new Bytes(0, 0, 0, 0, false);

    public Bytes(BigInteger totalBits, boolean isBinaryUnit) {
        this.isBinaryUnit = isBinaryUnit;
        BigInteger[] totalBsAndRemainder = totalBits.divideAndRemainder(ByteFactor.X8);

        Tuple2<Long[], BigInteger[]> computeBytes = standardizingBytes(isBinaryUnit, totalBsAndRemainder[0]);
        Long[] longs = computeBytes._1;
        BigInteger[] bigIntegers = computeBytes._2;

        this.bit = totalBsAndRemainder[1].longValue();
        this.b = longs[0];
        this.kb = longs[1];
        this.mb = longs[2];
        this.gb = longs[3];
        this.tb = longs[4];
        this.pb = longs[5];
        this.eb = longs[6];
        this.zb = longs[7];
        this.yb = longs[8];
        this.bits = totalBits;
        this.bs = bigIntegers[0];
        this.kbs = bigIntegers[1];
        this.mbs = bigIntegers[2];
        this.gbs = bigIntegers[3];
        this.tbs = bigIntegers[4];
        this.pbs = bigIntegers[5];
        this.ebs = bigIntegers[6];
        this.zbs = bigIntegers[7];
        this.ybs = bigIntegers[8];
    }

    public Bytes(long yb, long zb, long eb, long pb, long tb, long gb, long mb, long kb, long b, long bit, boolean isBinaryUnit) {
        this(getTotalBits(yb, zb, eb, pb, tb, gb, mb, kb, b, bit, isBinaryUnit), isBinaryUnit);
    }

    public Bytes(long zb, long eb, long pb, long tb, long gb, long mb, long kb, long b, long bit, boolean isBinaryUnit) {
        this(0, zb, eb, pb, tb, gb, mb, kb, b, bit, isBinaryUnit);
    }

    public Bytes(long eb, long pb, long tb, long gb, long mb, long kb, long b, long bit, boolean isBinaryUnit) {
        this(0, 0, eb, pb, tb, gb, mb, kb, b, bit, isBinaryUnit);
    }

    public Bytes(long pb, long tb, long gb, long mb, long kb, long b, long bit, boolean isBinaryUnit) {
        this(0, 0, 0, pb, tb, gb, mb, kb, b, bit, isBinaryUnit);
    }

    public Bytes(long tb, long gb, long mb, long kb, long b, long bit, boolean isBinaryUnit) {
        this(0, 0, 0, 0, tb, gb, mb, kb, b, bit, isBinaryUnit);
    }

    public Bytes(long tb, long gb, long mb, long kb, long b, boolean isBinaryUnit) {
        this(0, 0, 0, 0, tb, gb, mb, kb, b, 0, isBinaryUnit);
    }

    public Bytes(long gb, long mb, long kb, long b, boolean isBinaryUnit) {
        this(0, 0, 0, 0, 0, gb, mb, kb, b, 0, isBinaryUnit);
    }

    public static Bytes of(BigInteger totalBits, boolean isBinaryUnit) {
        Objects.requireNonNull(totalBits, "Parameter `totalBits` must be non-null!");
        return new Bytes(totalBits, isBinaryUnit);
    }

    public static Bytes of(long yb, long zb, long eb, long pb, long tb, long gb, long mb, long kb, long b, long bit, boolean isBinaryUnit) {
        return new Bytes(yb, zb, eb, pb, tb, gb, mb, kb, b, bit, isBinaryUnit);
    }

    public static Bytes of(long zb, long eb, long pb, long tb, long gb, long mb, long kb, long b, long bit, boolean isBinaryUnit) {
        return of(0, zb, eb, pb, tb, gb, mb, kb, b, bit, isBinaryUnit);
    }

    public static Bytes of(long eb, long pb, long tb, long gb, long mb, long kb, long b, long bit, boolean isBinaryUnit) {
        return of(0, 0, eb, pb, tb, gb, mb, kb, b, bit, isBinaryUnit);
    }

    public static Bytes of(long pb, long tb, long gb, long mb, long kb, long b, long bit, boolean isBinaryUnit) {
        return of(0, 0, 0, pb, tb, gb, mb, kb, b, bit, isBinaryUnit);
    }

    public static Bytes of(long tb, long gb, long mb, long kb, long b, long bit, boolean isBinaryUnit) {
        return of(0, 0, 0, 0, tb, gb, mb, kb, b, bit, isBinaryUnit);
    }

    public static Bytes of(long tb, long gb, long mb, long kb, long b, boolean isBinaryUnit) {
        return of(0, 0, 0, 0, tb, gb, mb, kb, b, 0, isBinaryUnit);
    }

    public static Bytes of(long gb, long mb, long kb, long b, boolean isBinaryUnit) {
        return of(0, 0, 0, 0, 0, gb, mb, kb, b, 0, isBinaryUnit);
    }

    public static Bytes ofBits(BigInteger bits, boolean isBinaryUnit) {
        return of(bits, isBinaryUnit);
    }

    public static Bytes ofBs(double bs, boolean isBinaryUnit) {
        return of(toBits(bs, isBinaryUnit, 0), isBinaryUnit);
    }

    public static Bytes ofKBs(double kbs, boolean isBinaryUnit) {
        return of(toBits(kbs, isBinaryUnit, 1), isBinaryUnit);
    }

    public static Bytes ofMBs(double mbs, boolean isBinaryUnit) {
        return of(toBits(mbs, isBinaryUnit, 2), isBinaryUnit);
    }

    public static Bytes ofGBs(double gbs, boolean isBinaryUnit) {
        return of(toBits(gbs, isBinaryUnit, 3), isBinaryUnit);
    }

    public static Bytes ofTBs(double tbs, boolean isBinaryUnit) {
        return of(toBits(tbs, isBinaryUnit, 4), isBinaryUnit);
    }

    public static Bytes ofPBs(double pbs, boolean isBinaryUnit) {
        return of(toBits(pbs, isBinaryUnit, 5), isBinaryUnit);
    }

    public static Bytes ofEBs(double ebs, boolean isBinaryUnit) {
        return of(toBits(ebs, isBinaryUnit, 6), isBinaryUnit);
    }

    public static Bytes ofZBs(double zbs, boolean isBinaryUnit) {
        return of(toBits(zbs, isBinaryUnit, 7), isBinaryUnit);
    }

    public static Bytes ofYBs(double ybs, boolean isBinaryUnit) {
        return of(toBits(ybs, isBinaryUnit, 8), isBinaryUnit);
    }

    public static Bytes ofBs(BigDecimal bs, boolean isBinaryUnit) {
        return of(toBits(bs, isBinaryUnit, 0), isBinaryUnit);
    }

    public static Bytes ofKBs(BigDecimal kbs, boolean isBinaryUnit) {
        return of(toBits(kbs, isBinaryUnit, 1), isBinaryUnit);
    }

    public static Bytes ofMBs(BigDecimal mbs, boolean isBinaryUnit) {
        return of(toBits(mbs, isBinaryUnit, 2), isBinaryUnit);
    }

    public static Bytes ofGBs(BigDecimal gbs, boolean isBinaryUnit) {
        return of(toBits(gbs, isBinaryUnit, 3), isBinaryUnit);
    }

    public static Bytes ofTBs(BigDecimal tbs, boolean isBinaryUnit) {
        return of(toBits(tbs, isBinaryUnit, 4), isBinaryUnit);
    }

    public static Bytes ofPBs(BigDecimal pbs, boolean isBinaryUnit) {
        return of(toBits(pbs, isBinaryUnit, 5), isBinaryUnit);
    }

    public static Bytes ofEBs(BigDecimal ebs, boolean isBinaryUnit) {
        return of(toBits(ebs, isBinaryUnit, 6), isBinaryUnit);
    }

    public static Bytes ofZBs(BigDecimal zbs, boolean isBinaryUnit) {
        return of(toBits(zbs, isBinaryUnit, 7), isBinaryUnit);
    }

    public static Bytes ofYBs(BigDecimal ybs, boolean isBinaryUnit) {
        return of(toBits(ybs, isBinaryUnit, 8), isBinaryUnit);
    }

    public Bytes plus(Bytes bytes) {
        Objects.requireNonNull(bytes, "Parameter `bytes` must be non-null!");
        return new Bytes(this.bits.add(bytes.bits), isBinaryUnit);
    }

    public Bytes minus(Bytes bytes) {
        Objects.requireNonNull(bytes, "Parameter `bytes` must be non-null!");
        return new Bytes(this.bits.subtract(bytes.bits), isBinaryUnit);
    }

    public Bytes plus(double amount, ByteUnit unit) {
        return plus(BigDecimal.valueOf(amount), unit);
    }

    public Bytes minus(double amount, ByteUnit unit) {
        return minus(BigDecimal.valueOf(amount), unit);
    }

    public Bytes plus(BigDecimal amount, ByteUnit unit) {
        if (unit instanceof ByteDecimalUnit || unit instanceof ByteBinaryUnit) {
            BigInteger bits = toBits(amount, unit.isBinaryUnit(), unit.getIndex());
            return new Bytes(this.bits.add(bits), isBinaryUnit);
        }
        throw new UnexpectedParameterException("Unsupported unit: " + unit);
    }

    public Bytes minus(BigDecimal amount, ByteUnit unit) {
        if (unit instanceof ByteDecimalUnit || unit instanceof ByteBinaryUnit) {
            BigInteger bits = toBits(amount, unit.isBinaryUnit(), unit.getIndex());
            return new Bytes(this.bits.subtract(bits), isBinaryUnit);
        }
        throw new UnexpectedParameterException("Unsupported unit: " + unit);
    }

    /**
     * 转换成另一种进制的 Bytes 对象，当前是二进制，则 <b>二进制 → 十进制</b>，当前十进制，则 <b>十进制 → 二进制</b>
     *
     * @return Bytes对象
     */
    public Bytes toAnotherUnit() {
        return new Bytes(this.bits, !isBinaryUnit);
    }

    @Override
    public int compareTo(Bytes bytes) {
        Objects.requireNonNull(bytes, "Parameter `bytes` must be non-null!");
        return this.bits.compareTo(bytes.bits);
    }

    @Override
    public boolean equals(Object bytes) {
        return bytes instanceof Bytes && compareTo((Bytes) bytes) == 0;
    }

    private static BigInteger getTotalBits(long yb, long zb, long eb, long pb, long tb, long gb, long mb, long kb, long b, long bit, boolean isBinaryUnit) {
        BigInteger totalBits = BigInteger.valueOf(bit);
        if (b != 0) totalBits = totalBits.add(toBits(b, isBinaryUnit, 0));
        if (kb != 0) totalBits = totalBits.add(toBits(kb, isBinaryUnit, 1));
        if (mb != 0) totalBits = totalBits.add(toBits(mb, isBinaryUnit, 2));
        if (gb != 0) totalBits = totalBits.add(toBits(gb, isBinaryUnit, 3));
        if (tb != 0) totalBits = totalBits.add(toBits(tb, isBinaryUnit, 4));
        if (pb != 0) totalBits = totalBits.add(toBits(pb, isBinaryUnit, 5));
        if (eb != 0) totalBits = totalBits.add(toBits(eb, isBinaryUnit, 6));
        if (zb != 0) totalBits = totalBits.add(toBits(zb, isBinaryUnit, 7));
        if (yb != 0) totalBits = totalBits.add(toBits(yb, isBinaryUnit, 8));

        return totalBits;
    }


    private static BigInteger toBits(double bytes, boolean isBinaryUnit, int index) {
        return toBits(BigDecimal.valueOf(bytes), isBinaryUnit, index);
    }

    private static BigInteger toBits(BigDecimal amount, boolean isBinaryUnit, int index) {
        Objects.requireNonNull(amount, "Parameter `amount` must be non-null!");

        if (index == -1) return amount.toBigInteger();
        return amount.multiply(new BigDecimal(ByteFactor.getCoef(isBinaryUnit, index))).multiply(new BigDecimal(ByteFactor.X8)).toBigInteger();
    }

    private static Tuple2<Long[], BigInteger[]> standardizingBytes(boolean isBinaryUnit, BigInteger totalBytes) {
        BigInteger factor = isBinaryUnit ? ByteFactor.X1024 : ByteFactor.X1000;
        Long[] ls = new Long[]{0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L};
        BigInteger[] ints = new BigInteger[]{BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO,
                BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO};

        ints[0] = totalBytes;
        if (ints[0] != null && !ints[0].equals(BigInteger.ZERO)) {
            BigInteger[] totalKBsAndRemainder = ints[0].divideAndRemainder(factor);

            ls[0] = totalKBsAndRemainder[1].longValue();
            ints[1] = totalKBsAndRemainder[0];
            if (!ints[1].equals(BigInteger.ZERO)) {
                BigInteger[] totalMBsAndRemainder = ints[1].divideAndRemainder(factor);

                ls[1] = totalMBsAndRemainder[1].longValue();
                ints[2] = totalMBsAndRemainder[0];
                if (!ints[2].equals(BigInteger.ZERO)) {
                    BigInteger[] totalGBsAndRemainder = ints[2].divideAndRemainder(factor);

                    ls[2] = totalGBsAndRemainder[1].longValue();
                    ints[3] = totalGBsAndRemainder[0];
                    if (!ints[3].equals(BigInteger.ZERO)) {
                        BigInteger[] totalTBsAndRemainder = ints[3].divideAndRemainder(factor);

                        ls[3] = totalTBsAndRemainder[1].longValue();
                        ints[4] = totalTBsAndRemainder[0];
                        if (!ints[4].equals(BigInteger.ZERO)) {
                            BigInteger[] totalPBsAndRemainder = ints[4].divideAndRemainder(factor);

                            ls[4] = totalPBsAndRemainder[1].longValue();
                            ints[5] = totalPBsAndRemainder[0];
                            if (!ints[5].equals(BigInteger.ZERO)) {
                                BigInteger[] totalEBsAndRemainder = ints[5].divideAndRemainder(factor);

                                ls[5] = totalEBsAndRemainder[1].longValue();
                                ints[6] = totalEBsAndRemainder[0];
                                if (!ints[6].equals(BigInteger.ZERO)) {
                                    BigInteger[] totalZBsAndRemainder = ints[6].divideAndRemainder(factor);

                                    ls[6] = totalZBsAndRemainder[1].longValue();
                                    ints[7] = totalZBsAndRemainder[0];
                                    if (!ints[7].equals(BigInteger.ZERO)) {
                                        BigInteger[] totalYBsAndRemainder = ints[7].divideAndRemainder(factor);
                                        ls[7] = totalYBsAndRemainder[1].longValue();
                                        ints[8] = totalYBsAndRemainder[0];
                                        ls[8] = ints[8].longValue();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return Tuple.of(ls, ints);
    }

    /**
     * 将当前 Bytes 转成另一个 ByteUnit
     *
     * @param toUnit 目标单位
     * @return ByteUnit 的值
     */
    public BigDecimal convertUnit(ByteUnit toUnit) {
        int index = toUnit.getIndex();
        if (index == -1) return new BigDecimal(bits);
        BigInteger coef = ByteFactor.getCoef(toUnit.isBinaryUnit(), index);
        return Numbers.divide(bits, ByteFactor.X8.multiply(coef), 99, false).stripTrailingZeros();
    }

    /**
     * 将当前 Bytes 转成另一个 ByteUnit
     *
     * @param unitIndex 单位序号。{@code isBinaryUnit} 与当前 Bytes 相同
     * @return ByteUnit 的值
     */
    public BigDecimal convertUnit(int unitIndex) {
        if (unitIndex == -1) return new BigDecimal(bits);
        BigInteger coef = ByteFactor.getCoef(isBinaryUnit, unitIndex);
        return Numbers.divide(bits, ByteFactor.X8.multiply(coef), 99, false).stripTrailingZeros();
    }

    /**
     * 将一个 ByteUnit 转成另一个 ByteUnit
     *
     * @param fromUnit 原始单位
     * @param toUnit   目标单位
     * @param amount   原始单位值
     * @return 另一个 ByteUnit 的值
     */
    public static BigDecimal convertUnit(ByteUnit fromUnit, ByteUnit toUnit, double amount) {
        return convertUnit(fromUnit, toUnit, BigDecimal.valueOf(amount));
    }

    /**
     * 将一个 ByteUnit 转成另一个 ByteUnit
     *
     * @param fromUnit 原始单位
     * @param toUnit   目标单位
     * @param amount   原始单位值
     * @return 另一个 ByteUnit 的值
     */
    public static BigDecimal convertUnit(ByteUnit fromUnit, ByteUnit toUnit, BigDecimal amount) {
        if (!(fromUnit instanceof ByteDecimalUnit || fromUnit instanceof ByteBinaryUnit))
            throw new UnexpectedParameterException("Unsupported unit: " + fromUnit);
        if (!(toUnit instanceof ByteDecimalUnit || toUnit instanceof ByteBinaryUnit))
            throw new UnexpectedParameterException("Unsupported unit: " + toUnit);

        BigInteger fromBits = toBits(amount, fromUnit.isBinaryUnit(), fromUnit.getIndex());
        int index = toUnit.getIndex();
        if (index == -1) return new BigDecimal(fromBits);
        BigInteger coef = ByteFactor.getCoef(toUnit.isBinaryUnit(), index);
        return Numbers.divide(fromBits, ByteFactor.X8.multiply(coef), 99, false).stripTrailingZeros();
    }

    /**
     * 将字节转成其他单位输出成字符串
     *
     * @param bytes        字节数
     * @param isBinaryUnit 是否二进制单位
     * @return 存储单位格式化后的字符串
     */
    public static String formatByte(long bytes, boolean isBinaryUnit) {
        return formatByte(BigInteger.valueOf(bytes), isBinaryUnit);
    }

    /**
     * 将字节转成其他更大的单位输出成字符串
     *
     * @param bytes        字节数
     * @param isBinaryUnit 是否二进制单位
     * @return 存储单位格式化后的字符串
     */
    public static String formatByte(BigInteger bytes, boolean isBinaryUnit) {
        Objects.requireNonNull(bytes, "Parameter `bytes` must be non-null!");

        if (bytes.compareTo(BigInteger.ZERO) < 0) return "-" + formatByte(bytes.abs(), isBinaryUnit);

        BigInteger coef1 = ByteFactor.getCoef(isBinaryUnit, 1);
        if (bytes.compareTo(coef1) < 0) return bytes + ByteUnit.getUnitType(isBinaryUnit, 0);
        DecimalFormat df = new DecimalFormat("0.0##");
        RoundingMode roundingMode = RoundingMode.HALF_UP;
        df.setRoundingMode(roundingMode);
        int scale = 3;

        BigInteger coef2 = ByteFactor.getCoef(isBinaryUnit, 2);
        if (bytes.compareTo(coef2) < 0) return df.format(Numbers.divide(bytes, coef1, scale, roundingMode)) + ByteUnit.getUnitType(isBinaryUnit, 1);
        BigInteger coef3 = ByteFactor.getCoef(isBinaryUnit, 3);
        if (bytes.compareTo(coef3) < 0) return df.format(Numbers.divide(bytes, coef2, scale, roundingMode)) + ByteUnit.getUnitType(isBinaryUnit, 2);
        BigInteger coef4 = ByteFactor.getCoef(isBinaryUnit, 4);
        if (bytes.compareTo(coef4) < 0) return df.format(Numbers.divide(bytes, coef3, scale, roundingMode)) + ByteUnit.getUnitType(isBinaryUnit, 3);
        BigInteger coef5 = ByteFactor.getCoef(isBinaryUnit, 5);
        if (bytes.compareTo(coef5) < 0) return df.format(Numbers.divide(bytes, coef4, scale, roundingMode)) + ByteUnit.getUnitType(isBinaryUnit, 4);
        BigInteger coef6 = ByteFactor.getCoef(isBinaryUnit, 6);
        if (bytes.compareTo(coef6) < 0) return df.format(Numbers.divide(bytes, coef5, scale, roundingMode)) + ByteUnit.getUnitType(isBinaryUnit, 5);
        BigInteger coef7 = ByteFactor.getCoef(isBinaryUnit, 7);
        if (bytes.compareTo(coef7) < 0) return df.format(Numbers.divide(bytes, coef6, scale, roundingMode)) + ByteUnit.getUnitType(isBinaryUnit, 6);
        BigInteger coef8 = ByteFactor.getCoef(isBinaryUnit, 8);
        if (bytes.compareTo(coef8) < 0) return df.format(Numbers.divide(bytes, coef7, scale, roundingMode)) + ByteUnit.getUnitType(isBinaryUnit, 7);

        return df.format(Numbers.divide(bytes, coef8, scale, roundingMode)) + ByteUnit.getUnitType(isBinaryUnit, 8);
    }

    /**
     * 将bit转成其他更大的单位输出成字符串
     *
     * @param bits         bit数
     * @param isBinaryUnit 是否二进制单位
     * @return 存储单位格式化后的字符串
     */
    public static String formatBit(long bits, boolean isBinaryUnit) {
        return formatBit(BigInteger.valueOf(bits), isBinaryUnit);
    }

    /**
     * 将bit转成其他更大的单位输出成字符串
     *
     * @param bits         bit数
     * @param isBinaryUnit 是否二进制单位
     * @return 存储单位格式化后的字符串
     */
    public static String formatBit(BigInteger bits, boolean isBinaryUnit) {
        Objects.requireNonNull(bits, "Parameter `bits` must be non-null!");

        if (bits.compareTo(BigInteger.ZERO) < 0) return "-" + formatBit(bits.abs(), isBinaryUnit);

        if (bits.compareTo(ByteFactor.X8) < 0) return bits + ByteUnit.getUnitType(isBinaryUnit, -1);
        return formatByte(bits.divide(ByteFactor.X8), isBinaryUnit);
    }

    public String format() {
        return formatBit(bits, isBinaryUnit);
    }


    @Override
    public String toString() {
        String ybStr = ByteUnit.getUnitType(isBinaryUnit, 8);
        String zbStr = ByteUnit.getUnitType(isBinaryUnit, 7);
        String ebStr = ByteUnit.getUnitType(isBinaryUnit, 6);
        String pbStr = ByteUnit.getUnitType(isBinaryUnit, 5);
        String tbStr = ByteUnit.getUnitType(isBinaryUnit, 4);
        String gbStr = ByteUnit.getUnitType(isBinaryUnit, 3);
        String mbStr = ByteUnit.getUnitType(isBinaryUnit, 2);
        String kbStr = ByteUnit.getUnitType(isBinaryUnit, 1);
        String bStr = ByteUnit.getUnitType(isBinaryUnit, 0);
        String bitStr = ByteUnit.getUnitType(isBinaryUnit, -1);

        String str = "";
        if (yb != 0) {
            str += yb + ybStr + "`" +
                    zb + zbStr + "`" +
                    eb + ebStr + "`" +
                    pb + pbStr + "`" +
                    tb + tbStr + "`" +
                    gb + gbStr + "`" +
                    mb + mbStr + "`" +
                    kb + kbStr + "`" +
                    b + bStr + "`";
        } else if (zb != 0) {
            str += zb + zbStr + "`" +
                    eb + ebStr + "`" +
                    pb + pbStr + "`" +
                    tb + tbStr + "`" +
                    gb + gbStr + "`" +
                    mb + mbStr + "`" +
                    kb + kbStr + "`" +
                    b + bStr + "`";
        } else if (eb != 0) {
            str += eb + ebStr + "`" +
                    pb + pbStr + "`" +
                    tb + tbStr + "`" +
                    gb + gbStr + "`" +
                    mb + mbStr + "`" +
                    kb + kbStr + "`" +
                    b + bStr + "`";
        } else if (pb != 0) {
            str += pb + pbStr + "`" +
                    tb + tbStr + "`" +
                    gb + gbStr + "`" +
                    mb + mbStr + "`" +
                    kb + kbStr + "`" +
                    b + bStr + "`";
        } else if (tb != 0) {
            str += tb + tbStr + "`" +
                    gb + gbStr + "`" +
                    mb + mbStr + "`" +
                    kb + kbStr + "`" +
                    b + bStr + "`";
        } else if (gb != 0) {
            str += gb + gbStr + "`" +
                    mb + mbStr + "`" +
                    kb + kbStr + "`" +
                    b + bStr + "`";
        } else if (mb != 0) {
            str += mb + mbStr + "`" +
                    kb + kbStr + "`" +
                    b + bStr + "`";
        } else if (kb != 0) {
            str += kb + kbStr + "`" +
                    b + bStr + "`";
        } else {
            str += b + bStr + "`";
        }

        if (bit != 0) str += bit + bitStr;

        if (str.endsWith("`")) str = str.substring(0, str.length() - 1);

        return "当前采用[" + (isBinaryUnit ? "二进制" : "十进制") + "]字节单位计算，字节大小为：[" + str + "]";
    }

    public String toSimpleString() {
        String ybStr = ByteUnit.getUnitType(isBinaryUnit, 8);
        String zbStr = ByteUnit.getUnitType(isBinaryUnit, 7);
        String ebStr = ByteUnit.getUnitType(isBinaryUnit, 6);
        String pbStr = ByteUnit.getUnitType(isBinaryUnit, 5);
        String tbStr = ByteUnit.getUnitType(isBinaryUnit, 4);
        String gbStr = ByteUnit.getUnitType(isBinaryUnit, 3);
        String mbStr = ByteUnit.getUnitType(isBinaryUnit, 2);
        String kbStr = ByteUnit.getUnitType(isBinaryUnit, 1);
        String bStr = ByteUnit.getUnitType(isBinaryUnit, 0);
        String bitStr = ByteUnit.getUnitType(isBinaryUnit, -1);

        if (yb == 0 && zb == 0 && eb == 0 && pb == 0 && tb == 0 && gb == 0 && mb == 0 && kb == 0 && b == 0 && bit == 0) return bit + bitStr;

        String str = "";
        if (yb != 0) str += yb + ybStr + "`";
        if (zb != 0) str += zb + zbStr + "`";
        if (eb != 0) str += eb + ebStr + "`";
        if (pb != 0) str += pb + pbStr + "`";
        if (tb != 0) str += tb + tbStr + "`";
        if (gb != 0) str += gb + gbStr + "`";
        if (mb != 0) str += mb + mbStr + "`";
        if (kb != 0) str += kb + kbStr + "`";
        if (b != 0) str += b + bStr + "`";
        if (bit != 0) str += bit + bitStr;

        if (str.endsWith("`")) str = str.substring(0, str.length() - 1);

        return "当前采用[" + (isBinaryUnit ? "二进制" : "十进制") + "]字节单位计算，字节大小为：[" + str + "]";
    }

    public String toFullString() {
        String ybStr = ByteUnit.getUnitType(isBinaryUnit, 8) + "\n";
        String zbStr = ByteUnit.getUnitType(isBinaryUnit, 7) + "\n";
        String ebStr = ByteUnit.getUnitType(isBinaryUnit, 6) + "\n";
        String pbStr = ByteUnit.getUnitType(isBinaryUnit, 5) + "\n";
        String tbStr = ByteUnit.getUnitType(isBinaryUnit, 4) + "\n";
        String gbStr = ByteUnit.getUnitType(isBinaryUnit, 3) + "\n";
        String mbStr = ByteUnit.getUnitType(isBinaryUnit, 2) + "\n";
        String kbStr = ByteUnit.getUnitType(isBinaryUnit, 1) + "\n";
        String bStr = ByteUnit.getUnitType(isBinaryUnit, 0) + "\n";
        String bitStr = ByteUnit.getUnitType(isBinaryUnit, -1) + "\n";
        String equivalentlyStr = "\n相当于：\n";

        return this + equivalentlyStr +
                "● " + ybs + ybStr +
                "● " + zbs + zbStr +
                "● " + ebs + ebStr +
                "● " + pbs + pbStr +
                "● " + tbs + tbStr +
                "● " + gbs + gbStr +
                "● " + mbs + mbStr +
                "● " + kbs + kbStr +
                "● " + bs + bStr +
                "● " + bits + bitStr;
    }


    public BigInteger getBits() {
        return bits;
    }

    public BigInteger getBs() {
        return bs;
    }

    public BigInteger getKbs() {
        return kbs;
    }

    public BigInteger getMbs() {
        return mbs;
    }

    public BigInteger getGbs() {
        return gbs;
    }

    public BigInteger getTbs() {
        return tbs;
    }

    public BigInteger getPbs() {
        return pbs;
    }

    public BigInteger getEbs() {
        return ebs;
    }

    public BigInteger getZbs() {
        return zbs;
    }

    public BigInteger getYbs() {
        return ybs;
    }

}
