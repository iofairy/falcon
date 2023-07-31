package com.iofairy.falcon.unit;

import java.util.HashMap;
import java.util.Map;

/**
 * 二进制字节单位<br>
 * <a href='https://en.wikipedia.org/wiki/Byte'>字节单位wiki</a>
 *
 * @since 0.4.10
 */
public enum ByteBinaryUnit implements ByteUnit {
    bit("bit", ByteValue.of(1), -1),
    B("byte", ByteValue.of(1024, 0), 0),
    KiB("kibibyte", ByteValue.of(1024, 1), 1),
    MiB("mebibyte", ByteValue.of(1024, 2), 2),
    GiB("gibibyte", ByteValue.of(1024, 3), 3),
    TiB("tebibyte", ByteValue.of(1024, 4), 4),
    PiB("pebibyte", ByteValue.of(1024, 5), 5),
    EiB("exbibyte", ByteValue.of(1024, 6), 6),
    ZiB("zebibyte", ByteValue.of(1024, 7), 7),
    YiB("yobibyte", ByteValue.of(1024, 8), 8);

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

    static final Map<String, ByteBinaryUnit> UNITS_MAP = new HashMap<>();
    static final Map<Integer, ByteBinaryUnit> UNITS_INDEX_MAP = new HashMap<>();
    static final Map<Integer, String> INDEX_TYPE_MAP = new HashMap<>();

    static {
        for (ByteBinaryUnit value : values()) {
            String valueStr = value.toString();
            UNITS_MAP.put(valueStr.toUpperCase(), value);
            UNITS_INDEX_MAP.put(value.index, value);
            INDEX_TYPE_MAP.put(value.index, valueStr);
        }
    }

    ByteBinaryUnit(String name, ByteValue byteValue, int index) {
        this.name = name;
        this.byteValue = byteValue;
        this.index = index;
    }

    public static ByteBinaryUnit of(String type) {
        if (type == null) return null;
        return UNITS_MAP.get(type.toUpperCase());
    }

    public static ByteBinaryUnit of(int index) {
        return UNITS_INDEX_MAP.get(index);
    }

    public static String getUnitType(int index) {
        return INDEX_TYPE_MAP.get(index);
    }

    @Override
    public boolean isBinaryUnit() {
        return true;
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
