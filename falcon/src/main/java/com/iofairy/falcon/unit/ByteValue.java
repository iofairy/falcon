package com.iofairy.falcon.unit;

import java.math.BigInteger;

/**
 * 各单位的 bit数或字节数
 *
 * @since 0.4.10
 */
public class ByteValue {
    /**
     * bit数
     */
    int bits;
    /**
     * 字节数
     */
    int bytes;
    /**
     * 字节数的指数
     */
    int exponentForBytes;

    public ByteValue() {
    }

    public ByteValue(int bits) {
        this.bits = bits;
    }

    public ByteValue(int bytes, int exponentForBytes) {
        this.bytes = bytes;
        this.exponentForBytes = exponentForBytes;
    }

    public static ByteValue of() {
        return new ByteValue();
    }

    public static ByteValue of(int bits) {
        return new ByteValue(bits);
    }

    public static ByteValue of(int bytes, int exponentForBytes) {
        return new ByteValue(bytes, exponentForBytes);
    }

    public BigInteger getAllBytes() {
        return BigInteger.valueOf(bytes).pow(exponentForBytes);
    }

    public BigInteger getAllBits() {
        return getAllBytes().multiply(ByteFactor.X8).add(BigInteger.valueOf(bits));
    }

    public int getBits() {
        return bits;
    }

    public void setBits(int bits) {
        this.bits = bits;
    }

    public int getBytes() {
        return bytes;
    }

    public void setBytes(int bytes) {
        this.bytes = bytes;
    }

    public int getExponentForBytes() {
        return exponentForBytes;
    }

    public void setExponentForBytes(int exponentForBytes) {
        this.exponentForBytes = exponentForBytes;
    }

    @Override
    public String toString() {
        return "ByteValue{" +
                "bits=" + bits +
                ", bytes=" + bytes +
                ", exponentForBytes=" + exponentForBytes +
                '}';
    }

}
