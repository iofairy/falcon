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
package com.iofairy.falcon.iterable;

import com.iofairy.string.Ascii;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 * Utils for Primitive Array ({@code char[], boolean[], byte[], short[], int[], float[], long[], double[]}).<br>
 * 原始类型数组 ({@code char[], boolean[], byte[], short[], int[], float[], long[], double[]}) 的工具类
 *
 * @since 0.2.0
 */
public class PrimitiveArrays {

    /**
     * Character Collection to char[] with {@link ToArrayMode}
     *
     * @param toArrayMode toArrayMode. 该值如果为null，则按 {@link ToArrayMode#IGNORE_NULL} 处理。
     * @param chars       Character Collection
     * @return char[], return {@code null} when {@code chars} is {@code null}
     * @since 0.2.0
     */
    public static char[] toChars(ToArrayMode toArrayMode, Collection<Character> chars) {
        if (chars == null) return null;
        if (chars.size() == 0) return new char[0];
        if (toArrayMode == null) toArrayMode = ToArrayMode.IGNORE_NULL;

        char[] cs = new char[chars.size()];
        int index = 0;
        switch (toArrayMode) {
            case THROW_WHEN_NULL:
                for (Character c : chars) {
                    cs[index] = c;
                    index++;
                }
                break;
            case DEFAULT_VALUE:
                for (Character c : chars) {
                    cs[index] = (c == null ? Ascii.NUL : c);
                    index++;
                }
                break;
            default:
                // chars.removeIf(Objects::isNull); // 有些集合并不支持删除操作
                // Character[] characterArray = chars.toArray(new Character[0]);
                Character[] characterArray = chars.stream().filter(Objects::nonNull).toArray(Character[]::new);
                cs = new char[characterArray.length];
                for (int i = 0; i < characterArray.length; i++) {
                    cs[i] = characterArray[i];
                }
        }
        return cs;
    }

    /**
     * Character Collection to char[]
     *
     * @param valueForNull 使用此值来替代 {@code null}
     * @param chars        Character Collection
     * @return char[], return {@code null} when {@code chars} is {@code null}
     * @since 0.2.0
     */
    public static char[] toChars(char valueForNull, Collection<Character> chars) {
        if (chars == null) return null;
        if (chars.size() == 0) return new char[0];
        char[] cs = new char[chars.size()];
        int index = 0;
        for (Character c : chars) {
            cs[index] = (c == null ? valueForNull : c);
            index++;
        }
        return cs;
    }

    /**
     * Character Array to char[] with {@link ToArrayMode}
     *
     * @param toArrayMode toArrayMode. 该值如果为null，则按 {@link ToArrayMode#IGNORE_NULL} 处理。
     * @param chars       Character Array
     * @return char[], return {@code null} when {@code chars} is {@code null}
     * @since 0.2.0
     */
    public static char[] toChars(ToArrayMode toArrayMode, Character... chars) {
        if (chars == null) return null;
        if (chars.length == 0) return new char[0];
        if (toArrayMode == null) toArrayMode = ToArrayMode.IGNORE_NULL;

        char[] cs = new char[chars.length];
        switch (toArrayMode) {
            case THROW_WHEN_NULL:
                for (int i = 0; i < chars.length; i++) {
                    cs[i] = chars[i];
                }
                break;
            case DEFAULT_VALUE:
                for (int i = 0; i < chars.length; i++) {
                    Character c = chars[i];
                    cs[i] = (c == null ? Ascii.NUL : c);
                }
                break;
            default:
                Character[] characterArray = Arrays.stream(chars).filter(Objects::nonNull).toArray(Character[]::new);
                cs = new char[characterArray.length];
                for (int i = 0; i < characterArray.length; i++) {
                    cs[i] = characterArray[i];
                }
        }
        return cs;
    }

    /**
     * Character Array to char[]
     *
     * @param valueForNull 使用此值来替代 {@code null}
     * @param chars        Character Array
     * @return char[], return {@code null} when {@code chars} is {@code null}
     * @since 0.2.0
     */
    public static char[] toChars(char valueForNull, Character... chars) {
        if (chars == null) return null;
        if (chars.length == 0) return new char[0];
        char[] cs = new char[chars.length];
        int index = 0;
        for (Character c : chars) {
            cs[index] = (c == null ? valueForNull : c);
            index++;
        }
        return cs;
    }

    /**
     * Boolean Collection to boolean[] with {@link ToArrayMode}
     *
     * @param toArrayMode toArrayMode. 该值如果为null，则按 {@link ToArrayMode#IGNORE_NULL} 处理。
     * @param bools       Boolean Collection
     * @return boolean[], return {@code null} when {@code bools} is {@code null}
     * @since 0.2.0
     */
    public static boolean[] toBooleans(ToArrayMode toArrayMode, Collection<Boolean> bools) {
        if (bools == null) return null;
        if (bools.size() == 0) return new boolean[0];
        if (toArrayMode == null) toArrayMode = ToArrayMode.IGNORE_NULL;

        boolean[] bs = new boolean[bools.size()];
        int index = 0;
        switch (toArrayMode) {
            case THROW_WHEN_NULL:
                for (Boolean b : bools) {
                    bs[index] = b;
                    index++;
                }
                break;
            case DEFAULT_VALUE:
                for (Boolean b : bools) {
                    bs[index] = (b != null && b);
                    index++;
                }
                break;
            default:
                Boolean[] booleanArray = bools.stream().filter(Objects::nonNull).toArray(Boolean[]::new);
                bs = new boolean[booleanArray.length];
                for (int i = 0; i < booleanArray.length; i++) {
                    bs[i] = booleanArray[i];
                }
        }
        return bs;
    }

    /**
     * Boolean Collection to boolean[]
     *
     * @param valueForNull 使用此值来替代 {@code null}
     * @param bools        Boolean Collection
     * @return boolean[], return {@code null} when {@code bools} is {@code null}
     * @since 0.2.0
     */
    public static boolean[] toBooleans(boolean valueForNull, Collection<Boolean> bools) {
        if (bools == null) return null;
        if (bools.size() == 0) return new boolean[0];
        boolean[] bs = new boolean[bools.size()];
        int index = 0;
        for (Boolean b : bools) {
            bs[index] = (b == null ? valueForNull : b);
            index++;
        }
        return bs;
    }

    /**
     * Boolean Array to boolean[] with {@link ToArrayMode}
     *
     * @param toArrayMode toArrayMode. 该值如果为null，则按 {@link ToArrayMode#IGNORE_NULL} 处理。
     * @param bools       Boolean Array
     * @return boolean[], return {@code null} when {@code bools} is {@code null}
     * @since 0.2.0
     */
    public static boolean[] toBooleans(ToArrayMode toArrayMode, Boolean... bools) {
        if (bools == null) return null;
        if (bools.length == 0) return new boolean[0];
        if (toArrayMode == null) toArrayMode = ToArrayMode.IGNORE_NULL;

        boolean[] bs = new boolean[bools.length];
        switch (toArrayMode) {
            case THROW_WHEN_NULL:
                for (int i = 0; i < bools.length; i++) {
                    bs[i] = bools[i];
                }
                break;
            case DEFAULT_VALUE:
                for (int i = 0; i < bools.length; i++) {
                    Boolean b = bools[i];
                    bs[i] = (b != null && b);
                }
                break;
            default:
                Boolean[] booleanArray = Arrays.stream(bools).filter(Objects::nonNull).toArray(Boolean[]::new);
                bs = new boolean[booleanArray.length];
                for (int i = 0; i < booleanArray.length; i++) {
                    bs[i] = booleanArray[i];
                }
        }
        return bs;
    }

    /**
     * Boolean Array to boolean[]
     *
     * @param valueForNull 使用此值来替代 {@code null}
     * @param bools        Boolean Array
     * @return boolean[], return {@code null} when {@code booleans} is {@code null}
     * @since 0.2.0
     */
    public static boolean[] toBooleans(boolean valueForNull, Boolean... bools) {
        if (bools == null) return null;
        if (bools.length == 0) return new boolean[0];
        boolean[] bs = new boolean[bools.length];
        int index = 0;
        for (Boolean b : bools) {
            bs[index] = (b == null ? valueForNull : b);
            index++;
        }
        return bs;
    }

    /**
     * Byte Collection to byte[] with {@link ToArrayMode}
     *
     * @param toArrayMode toArrayMode. 该值如果为null，则按 {@link ToArrayMode#IGNORE_NULL} 处理。
     * @param bytes       Byte Collection
     * @return byte[], return {@code null} when {@code bytes} is {@code null}
     * @since 0.2.0
     */
    public static byte[] toBytes(ToArrayMode toArrayMode, Collection<Byte> bytes) {
        if (bytes == null) return null;
        if (bytes.size() == 0) return new byte[0];
        if (toArrayMode == null) toArrayMode = ToArrayMode.IGNORE_NULL;

        byte[] bs = new byte[bytes.size()];
        int index = 0;
        switch (toArrayMode) {
            case THROW_WHEN_NULL:
                for (Byte b : bytes) {
                    bs[index] = b;
                    index++;
                }
                break;
            case DEFAULT_VALUE:
                for (Byte b : bytes) {
                    bs[index] = (b == null ? 0 : b);
                    index++;
                }
                break;
            default:
                Byte[] byteArray = bytes.stream().filter(Objects::nonNull).toArray(Byte[]::new);
                bs = new byte[byteArray.length];
                for (int i = 0; i < byteArray.length; i++) {
                    bs[i] = byteArray[i];
                }
        }
        return bs;
    }

    /**
     * Byte Collection to byte[]
     *
     * @param valueForNull 使用此值来替代 {@code null}
     * @param bytes        Byte Collection
     * @return byte[], return {@code null} when {@code bytes} is {@code null}
     * @since 0.2.0
     */
    public static byte[] toBytes(byte valueForNull, Collection<Byte> bytes) {
        if (bytes == null) return null;
        if (bytes.size() == 0) return new byte[0];
        byte[] bs = new byte[bytes.size()];
        int index = 0;
        for (Byte b : bytes) {
            bs[index] = (b == null ? valueForNull : b);
            index++;
        }
        return bs;
    }

    /**
     * Byte Array to byte[] with {@link ToArrayMode}
     *
     * @param toArrayMode toArrayMode. 该值如果为null，则按 {@link ToArrayMode#IGNORE_NULL} 处理。
     * @param bytes       Byte Array
     * @return byte[], return {@code null} when {@code bytes} is {@code null}
     * @since 0.2.0
     */
    public static byte[] toBytes(ToArrayMode toArrayMode, Byte... bytes) {
        if (bytes == null) return null;
        if (bytes.length == 0) return new byte[0];
        if (toArrayMode == null) toArrayMode = ToArrayMode.IGNORE_NULL;

        byte[] bs = new byte[bytes.length];
        switch (toArrayMode) {
            case THROW_WHEN_NULL:
                for (int i = 0; i < bytes.length; i++) {
                    bs[i] = bytes[i];
                }
                break;
            case DEFAULT_VALUE:
                for (int i = 0; i < bytes.length; i++) {
                    Byte b = bytes[i];
                    bs[i] = (b == null ? 0 : b);
                }
                break;
            default:
                Byte[] byteArray = Arrays.stream(bytes).filter(Objects::nonNull).toArray(Byte[]::new);
                bs = new byte[byteArray.length];
                for (int i = 0; i < byteArray.length; i++) {
                    bs[i] = byteArray[i];
                }
        }
        return bs;
    }

    /**
     * Byte Array to byte[]
     *
     * @param valueForNull 使用此值来替代 {@code null}
     * @param bytes        Byte Array
     * @return byte[], return {@code null} when {@code bytes} is {@code null}
     * @since 0.2.0
     */
    public static byte[] toBytes(byte valueForNull, Byte... bytes) {
        if (bytes == null) return null;
        if (bytes.length == 0) return new byte[0];
        byte[] bs = new byte[bytes.length];
        int index = 0;
        for (Byte b : bytes) {
            bs[index] = (b == null ? valueForNull : b);
            index++;
        }
        return bs;
    }

    /**
     * Short Collection to short[] with {@link ToArrayMode}
     *
     * @param toArrayMode toArrayMode. 该值如果为null，则按 {@link ToArrayMode#IGNORE_NULL} 处理。
     * @param shorts      Short Collection
     * @return short[], return {@code null} when {@code shorts} is {@code null}
     * @since 0.2.0
     */
    public static short[] toShorts(ToArrayMode toArrayMode, Collection<Short> shorts) {
        if (shorts == null) return null;
        if (shorts.size() == 0) return new short[0];
        if (toArrayMode == null) toArrayMode = ToArrayMode.IGNORE_NULL;

        short[] ss = new short[shorts.size()];
        int index = 0;
        switch (toArrayMode) {
            case THROW_WHEN_NULL:
                for (Short s : shorts) {
                    ss[index] = s;
                    index++;
                }
                break;
            case DEFAULT_VALUE:
                for (Short s : shorts) {
                    ss[index] = (s == null ? 0 : s);
                    index++;
                }
                break;
            default:
                Short[] shortArray = shorts.stream().filter(Objects::nonNull).toArray(Short[]::new);
                ss = new short[shortArray.length];
                for (int i = 0; i < shortArray.length; i++) {
                    ss[i] = shortArray[i];
                }
        }
        return ss;
    }

    /**
     * Short Collection to short[]
     *
     * @param valueForNull 使用此值来替代 {@code null}
     * @param shorts       Short Collection
     * @return short[], return {@code null} when {@code shorts} is {@code null}
     * @since 0.2.0
     */
    public static short[] toShorts(short valueForNull, Collection<Short> shorts) {
        if (shorts == null) return null;
        if (shorts.size() == 0) return new short[0];
        short[] ss = new short[shorts.size()];
        int index = 0;
        for (Short s : shorts) {
            ss[index] = (s == null ? valueForNull : s);
            index++;
        }
        return ss;
    }

    /**
     * Short Array to short[] with {@link ToArrayMode}
     *
     * @param toArrayMode toArrayMode. 该值如果为null，则按 {@link ToArrayMode#IGNORE_NULL} 处理。
     * @param shorts      Short Array
     * @return short[], return {@code null} when {@code shorts} is {@code null}
     * @since 0.2.0
     */
    public static short[] toShorts(ToArrayMode toArrayMode, Short... shorts) {
        if (shorts == null) return null;
        if (shorts.length == 0) return new short[0];
        if (toArrayMode == null) toArrayMode = ToArrayMode.IGNORE_NULL;

        short[] ss = new short[shorts.length];
        switch (toArrayMode) {
            case THROW_WHEN_NULL:
                for (int i = 0; i < shorts.length; i++) {
                    ss[i] = shorts[i];
                }
                break;
            case DEFAULT_VALUE:
                for (int i = 0; i < shorts.length; i++) {
                    Short s = shorts[i];
                    ss[i] = (s == null ? 0 : s);
                }
                break;
            default:
                Short[] shortArray = Arrays.stream(shorts).filter(Objects::nonNull).toArray(Short[]::new);
                ss = new short[shortArray.length];
                for (int i = 0; i < shortArray.length; i++) {
                    ss[i] = shortArray[i];
                }
        }
        return ss;
    }

    /**
     * Short Array to short[]
     *
     * @param valueForNull 使用此值来替代 {@code null}
     * @param shorts       Short Array
     * @return short[], return {@code null} when {@code shorts} is {@code null}
     * @since 0.2.0
     */
    public static short[] toShorts(short valueForNull, Short... shorts) {
        if (shorts == null) return null;
        if (shorts.length == 0) return new short[0];
        short[] ss = new short[shorts.length];
        int index = 0;
        for (Short s : shorts) {
            ss[index] = (s == null ? valueForNull : s);
            index++;
        }
        return ss;
    }

    /**
     * Integer Collection to int[] with {@link ToArrayMode}
     *
     * @param toArrayMode toArrayMode. 该值如果为null，则按 {@link ToArrayMode#IGNORE_NULL} 处理。
     * @param ints        Integer Collection
     * @return int[], return {@code null} when {@code ints} is {@code null}
     * @since 0.2.0
     */
    public static int[] toInts(ToArrayMode toArrayMode, Collection<Integer> ints) {
        if (ints == null) return null;
        if (ints.size() == 0) return new int[0];
        if (toArrayMode == null) toArrayMode = ToArrayMode.IGNORE_NULL;

        int[] is = new int[ints.size()];
        int index = 0;
        switch (toArrayMode) {
            case THROW_WHEN_NULL:
                for (Integer i : ints) {
                    is[index] = i;
                    index++;
                }
                break;
            case DEFAULT_VALUE:
                for (Integer i : ints) {
                    is[index] = (i == null ? 0 : i);
                    index++;
                }
                break;
            default:
                Integer[] integerArray = ints.stream().filter(Objects::nonNull).toArray(Integer[]::new);
                is = new int[integerArray.length];
                for (int i = 0; i < integerArray.length; i++) {
                    is[i] = integerArray[i];
                }
        }
        return is;
    }

    /**
     * Integer Collection to int[]
     *
     * @param valueForNull 使用此值来替代 {@code null}
     * @param ints         Integer Collection
     * @return int[], return {@code null} when {@code ints} is {@code null}
     * @since 0.2.0
     */
    public static int[] toInts(int valueForNull, Collection<Integer> ints) {
        if (ints == null) return null;
        if (ints.size() == 0) return new int[0];
        int[] is = new int[ints.size()];
        int index = 0;
        for (Integer i : ints) {
            is[index] = (i == null ? valueForNull : i);
            index++;
        }
        return is;
    }

    /**
     * Integer Array to int[] with {@link ToArrayMode}
     *
     * @param toArrayMode toArrayMode. 该值如果为null，则按 {@link ToArrayMode#IGNORE_NULL} 处理。
     * @param ints        Integer Array
     * @return int[], return {@code null} when {@code ints} is {@code null}
     * @since 0.2.0
     */
    public static int[] toInts(ToArrayMode toArrayMode, Integer... ints) {
        if (ints == null) return null;
        if (ints.length == 0) return new int[0];
        if (toArrayMode == null) toArrayMode = ToArrayMode.IGNORE_NULL;

        int[] is = new int[ints.length];
        switch (toArrayMode) {
            case THROW_WHEN_NULL:
                for (int i = 0; i < ints.length; i++) {
                    is[i] = ints[i];
                }
                break;
            case DEFAULT_VALUE:
                for (int i = 0; i < ints.length; i++) {
                    Integer integer = ints[i];
                    is[i] = (integer == null ? 0 : integer);
                }
                break;
            default:
                Integer[] integerArray = Arrays.stream(ints).filter(Objects::nonNull).toArray(Integer[]::new);
                is = new int[integerArray.length];
                for (int i = 0; i < integerArray.length; i++) {
                    is[i] = integerArray[i];
                }
        }
        return is;
    }

    /**
     * Integer Array to int[]
     *
     * @param valueForNull 使用此值来替代 {@code null}
     * @param ints         Integer Array
     * @return int[], return {@code null} when {@code ints} is {@code null}
     * @since 0.2.0
     */
    public static int[] toInts(int valueForNull, Integer... ints) {
        if (ints == null) return null;
        if (ints.length == 0) return new int[0];
        int[] is = new int[ints.length];
        int index = 0;
        for (Integer i : ints) {
            is[index] = (i == null ? valueForNull : i);
            index++;
        }
        return is;
    }

    /**
     * Float Collection to float[] with {@link ToArrayMode}
     *
     * @param toArrayMode toArrayMode. 该值如果为null，则按 {@link ToArrayMode#IGNORE_NULL} 处理。
     * @param floats      Float Collection
     * @return float[], return {@code null} when {@code floats} is {@code null}
     * @since 0.2.0
     */
    public static float[] toFloats(ToArrayMode toArrayMode, Collection<Float> floats) {
        if (floats == null) return null;
        if (floats.size() == 0) return new float[0];
        if (toArrayMode == null) toArrayMode = ToArrayMode.IGNORE_NULL;

        float[] fs = new float[floats.size()];
        int index = 0;
        switch (toArrayMode) {
            case THROW_WHEN_NULL:
                for (Float f : floats) {
                    fs[index] = f;
                    index++;
                }
                break;
            case DEFAULT_VALUE:
                for (Float f : floats) {
                    fs[index] = (f == null ? 0.0f : f);
                    index++;
                }
                break;
            default:
                Float[] floatArray = floats.stream().filter(Objects::nonNull).toArray(Float[]::new);
                fs = new float[floatArray.length];
                for (int i = 0; i < floatArray.length; i++) {
                    fs[i] = floatArray[i];
                }
        }
        return fs;
    }

    /**
     * Float Collection to float[]
     *
     * @param valueForNull 使用此值来替代 {@code null}
     * @param floats       Float Collection
     * @return float[], return {@code null} when {@code floats} is {@code null}
     * @since 0.2.0
     */
    public static float[] toFloats(float valueForNull, Collection<Float> floats) {
        if (floats == null) return null;
        if (floats.size() == 0) return new float[0];
        float[] fs = new float[floats.size()];
        int index = 0;
        for (Float f : floats) {
            fs[index] = (f == null ? valueForNull : f);
            index++;
        }
        return fs;
    }

    /**
     * Float Array to float[] with {@link ToArrayMode}
     *
     * @param toArrayMode toArrayMode. 该值如果为null，则按 {@link ToArrayMode#IGNORE_NULL} 处理。
     * @param floats      Float Array
     * @return float[], return {@code null} when {@code floats} is {@code null}
     * @since 0.2.0
     */
    public static float[] toFloats(ToArrayMode toArrayMode, Float... floats) {
        if (floats == null) return null;
        if (floats.length == 0) return new float[0];
        if (toArrayMode == null) toArrayMode = ToArrayMode.IGNORE_NULL;

        float[] fs = new float[floats.length];
        switch (toArrayMode) {
            case THROW_WHEN_NULL:
                for (int i = 0; i < floats.length; i++) {
                    fs[i] = floats[i];
                }
                break;
            case DEFAULT_VALUE:
                for (int i = 0; i < floats.length; i++) {
                    Float f = floats[i];
                    fs[i] = (f == null ? 0.0f : f);
                }
                break;
            default:
                Float[] floatArray = Arrays.stream(floats).filter(Objects::nonNull).toArray(Float[]::new);
                fs = new float[floatArray.length];
                for (int i = 0; i < floatArray.length; i++) {
                    fs[i] = floatArray[i];
                }
        }
        return fs;
    }

    /**
     * Float Array to float[]
     *
     * @param valueForNull 使用此值来替代 {@code null}
     * @param floats       Float Array
     * @return float[], return {@code null} when {@code floats} is {@code null}
     * @since 0.2.0
     */
    public static float[] toFloats(float valueForNull, Float... floats) {
        if (floats == null) return null;
        if (floats.length == 0) return new float[0];
        float[] fs = new float[floats.length];
        int index = 0;
        for (Float f : floats) {
            fs[index] = (f == null ? valueForNull : f);
            index++;
        }
        return fs;
    }

    /**
     * Long Collection to long[] with {@link ToArrayMode}
     *
     * @param toArrayMode toArrayMode. 该值如果为null，则按 {@link ToArrayMode#IGNORE_NULL} 处理。
     * @param longs       Long Collection
     * @return long[], return {@code null} when {@code longs} is {@code null}
     * @since 0.2.0
     */
    public static long[] toLongs(ToArrayMode toArrayMode, Collection<Long> longs) {
        if (longs == null) return null;
        if (longs.size() == 0) return new long[0];
        if (toArrayMode == null) toArrayMode = ToArrayMode.IGNORE_NULL;

        long[] ls = new long[longs.size()];
        int index = 0;
        switch (toArrayMode) {
            case THROW_WHEN_NULL:
                for (Long l : longs) {
                    ls[index] = l;
                    index++;
                }
                break;
            case DEFAULT_VALUE:
                for (Long l : longs) {
                    ls[index] = (l == null ? 0L : l);
                    index++;
                }
                break;
            default:
                Long[] longArray = longs.stream().filter(Objects::nonNull).toArray(Long[]::new);
                ls = new long[longArray.length];
                for (int i = 0; i < longArray.length; i++) {
                    ls[i] = longArray[i];
                }
        }
        return ls;
    }

    /**
     * Long Collection to long[]
     *
     * @param valueForNull 使用此值来替代 {@code null}
     * @param longs        Long Collection
     * @return long[], return {@code null} when {@code longs} is {@code null}
     * @since 0.2.0
     */
    public static long[] toLongs(long valueForNull, Collection<Long> longs) {
        if (longs == null) return null;
        if (longs.size() == 0) return new long[0];
        long[] ls = new long[longs.size()];
        int index = 0;
        for (Long l : longs) {
            ls[index] = (l == null ? valueForNull : l);
            index++;
        }
        return ls;
    }

    /**
     * Long Array to long[] with {@link ToArrayMode}
     *
     * @param toArrayMode toArrayMode. 该值如果为null，则按 {@link ToArrayMode#IGNORE_NULL} 处理。
     * @param longs       Long Array
     * @return long[], return {@code null} when {@code longs} is {@code null}
     * @since 0.2.0
     */
    public static long[] toLongs(ToArrayMode toArrayMode, Long... longs) {
        if (longs == null) return null;
        if (longs.length == 0) return new long[0];
        if (toArrayMode == null) toArrayMode = ToArrayMode.IGNORE_NULL;

        long[] ls = new long[longs.length];
        switch (toArrayMode) {
            case THROW_WHEN_NULL:
                for (int i = 0; i < longs.length; i++) {
                    ls[i] = longs[i];
                }
                break;
            case DEFAULT_VALUE:
                for (int i = 0; i < longs.length; i++) {
                    Long l = longs[i];
                    ls[i] = (l == null ? 0L : l);
                }
                break;
            default:
                Long[] longArray = Arrays.stream(longs).filter(Objects::nonNull).toArray(Long[]::new);
                ls = new long[longArray.length];
                for (int i = 0; i < longArray.length; i++) {
                    ls[i] = longArray[i];
                }
        }
        return ls;
    }

    /**
     * Long Array to long[]
     *
     * @param valueForNull 使用此值来替代 {@code null}
     * @param longs        Long Array
     * @return long[], return {@code null} when {@code longs} is {@code null}
     * @since 0.2.0
     */
    public static long[] toLongs(long valueForNull, Long... longs) {
        if (longs == null) return null;
        if (longs.length == 0) return new long[0];
        long[] ls = new long[longs.length];
        int index = 0;
        for (Long l : longs) {
            ls[index] = (l == null ? valueForNull : l);
            index++;
        }
        return ls;
    }

    /**
     * Double Collection to double[] with {@link ToArrayMode}
     *
     * @param toArrayMode toArrayMode. 该值如果为null，则按 {@link ToArrayMode#IGNORE_NULL} 处理。
     * @param doubles     Double Collection
     * @return double[], return {@code null} when {@code doubles} is {@code null}
     * @since 0.2.0
     */
    public static double[] toDoubles(ToArrayMode toArrayMode, Collection<Double> doubles) {
        if (doubles == null) return null;
        if (doubles.size() == 0) return new double[0];
        if (toArrayMode == null) toArrayMode = ToArrayMode.IGNORE_NULL;

        double[] ds = new double[doubles.size()];
        int index = 0;
        switch (toArrayMode) {
            case THROW_WHEN_NULL:
                for (Double d : doubles) {
                    ds[index] = d;
                    index++;
                }
                break;
            case DEFAULT_VALUE:
                for (Double d : doubles) {
                    ds[index] = (d == null ? 0.0d : d);
                    index++;
                }
                break;
            default:
                Double[] doubleArray = doubles.stream().filter(Objects::nonNull).toArray(Double[]::new);
                ds = new double[doubleArray.length];
                for (int i = 0; i < doubleArray.length; i++) {
                    ds[i] = doubleArray[i];
                }
        }
        return ds;
    }

    /**
     * Double Collection to double[]
     *
     * @param valueForNull 使用此值来替代 {@code null}
     * @param doubles      Double Collection
     * @return double[], return {@code null} when {@code doubles} is {@code null}
     * @since 0.2.0
     */
    public static double[] toDoubles(double valueForNull, Collection<Double> doubles) {
        if (doubles == null) return null;
        if (doubles.size() == 0) return new double[0];
        double[] ds = new double[doubles.size()];
        int index = 0;
        for (Double d : doubles) {
            ds[index] = (d == null ? valueForNull : d);
            index++;
        }
        return ds;
    }

    /**
     * Double Array to double[] with {@link ToArrayMode}
     *
     * @param toArrayMode toArrayMode. 该值如果为null，则按 {@link ToArrayMode#IGNORE_NULL} 处理。
     * @param doubles     Double Array
     * @return double[], return {@code null} when {@code doubles} is {@code null}
     * @since 0.2.0
     */
    public static double[] toDoubles(ToArrayMode toArrayMode, Double... doubles) {
        if (doubles == null) return null;
        if (doubles.length == 0) return new double[0];
        if (toArrayMode == null) toArrayMode = ToArrayMode.IGNORE_NULL;

        double[] ds = new double[doubles.length];
        switch (toArrayMode) {
            case THROW_WHEN_NULL:
                for (int i = 0; i < doubles.length; i++) {
                    ds[i] = doubles[i];
                }
                break;
            case DEFAULT_VALUE:
                for (int i = 0; i < doubles.length; i++) {
                    Double d = doubles[i];
                    ds[i] = (d == null ? 0.0d : d);
                }
                break;
            default:
                Double[] doubleArray = Arrays.stream(doubles).filter(Objects::nonNull).toArray(Double[]::new);
                ds = new double[doubleArray.length];
                for (int i = 0; i < doubleArray.length; i++) {
                    ds[i] = doubleArray[i];
                }
        }
        return ds;
    }

    /**
     * Double Array to double[]
     *
     * @param valueForNull 使用此值来替代 {@code null}
     * @param doubles      Double Array
     * @return double[], return {@code null} when {@code doubles} is {@code null}
     * @since 0.2.0
     */
    public static double[] toDoubles(double valueForNull, Double... doubles) {
        if (doubles == null) return null;
        if (doubles.length == 0) return new double[0];
        double[] ds = new double[doubles.length];
        int index = 0;
        for (Double d : doubles) {
            ds[index] = (d == null ? valueForNull : d);
            index++;
        }
        return ds;
    }

    /**
     * CharSequence to char[]
     *
     * @param charSequence charSequence
     * @return char[]
     */
    public static char[] toChars(CharSequence charSequence) {
        return charSequence == null ? null : charSequence.toString().toCharArray();
    }
}
