package com.iofairy.falcon.unit;

import java.util.HashMap;
import java.util.Map;

/**
 * 十进制字节单位<br>
 * <a href='https://en.wikipedia.org/wiki/Byte'>字节单位wiki</a>
 *
 * @since 0.4.10
 */
public enum ByteDecimalUnit implements ByteUnit {
    bit("bit", ByteValue.of(1), -1),
    B("byte", ByteValue.of(1000, 0), 0),
    KB("kilobyte", ByteValue.of(1000, 1), 1),
    MB("megabyte", ByteValue.of(1000, 2), 2),
    GB("gigabyte", ByteValue.of(1000, 3), 3),
    TB("terabyte", ByteValue.of(1000, 4), 4),
    PB("petabyte", ByteValue.of(1000, 5), 5),
    EB("exabyte", ByteValue.of(1000, 6), 6),
    ZB("zettabyte", ByteValue.of(1000, 7), 7),
    YB("yottabyte", ByteValue.of(1000, 8), 8);

    /**
     * 单位名称
     */
    final String name;
    /**
     * 单位 bit 或 byte值
     */
    final ByteValue byteValue;
    /**
     * 单位序号
     */
    final int index;

    static final Map<String, ByteDecimalUnit> UNITS_MAP = new HashMap<>();
    static final Map<Integer, ByteDecimalUnit> UNITS_INDEX_MAP = new HashMap<>();
    static final Map<Integer, String> INDEX_TYPE_MAP = new HashMap<>();

    static {
        for (ByteDecimalUnit value : values()) {
            String valueStr = value.toString();
            UNITS_MAP.put(valueStr.toUpperCase(), value);
            UNITS_INDEX_MAP.put(value.index, value);
            INDEX_TYPE_MAP.put(value.index, valueStr);
        }
    }

    ByteDecimalUnit(String name, ByteValue byteValue, int index) {
        this.name = name;
        this.byteValue = byteValue;
        this.index = index;
    }

    public static ByteDecimalUnit of(String type) {
        if (type == null) return null;
        return UNITS_MAP.get(type.toUpperCase());
    }

    public static ByteDecimalUnit of(int index) {
        return UNITS_INDEX_MAP.get(index);
    }

    public static String getUnitType(int index) {
        return INDEX_TYPE_MAP.get(index);
    }

    @Override
    public boolean isBinaryUnit() {
        return false;
    }

    public String getName() {
        return name;
    }

    public ByteValue getByteValue() {
        return byteValue;
    }

    public int getIndex() {
        return index;
    }

}
