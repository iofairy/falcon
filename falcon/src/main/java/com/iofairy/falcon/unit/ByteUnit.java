package com.iofairy.falcon.unit;

/**
 * 字节单位
 *
 * @since 0.4.10
 */
public interface ByteUnit {
    /**
     * 是否是二进制的单位
     *
     * @return 二进制单位则为 true，否则 false。
     */
    boolean isBinaryUnit();

    String getName();

    ByteValue getByteValue();

    int getIndex();

    static ByteUnit getByteUnit(boolean isBinaryUnit, int index) {
        return isBinaryUnit ? ByteBinaryUnit.of(index) : ByteDecimalUnit.of(index);
    }

    static String getUnitType(boolean isBinaryUnit, int index) {
        return isBinaryUnit ? ByteBinaryUnit.getUnitType(index) : ByteDecimalUnit.getUnitType(index);
    }

}
