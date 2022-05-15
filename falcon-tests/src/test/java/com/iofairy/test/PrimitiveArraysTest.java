package com.iofairy.test;

import com.iofairy.falcon.iterable.ArrayUtils;
import com.iofairy.falcon.iterable.PrimitiveArrays;
import com.iofairy.falcon.iterable.ToArrayMode;
import com.iofairy.top.G;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author GG
 * @version 1.0
 * @date 2022/5/2 11:31
 */
public class PrimitiveArraysTest {
    @Test
    public void testToChars() {
        CopyOnWriteArraySet<Character> set = new CopyOnWriteArraySet<>();
        set.add(' ');
        set.add(null);
        set.add(null);
        set.add('a');
        set.add('b');
        List<Character> list = Arrays.asList(' ', null, null, 'a', 'b');
        System.out.println("set: " + set);
        System.out.println("list: " + list);

        char[] array01 = PrimitiveArrays.toChars(ToArrayMode.IGNORE_NULL, set);
        // char[] array02 = PrimitiveArrays.toChars(ToArrayMode.THROW_WHEN_NULL, set);
        char[] array03 = PrimitiveArrays.toChars(ToArrayMode.DEFAULT_VALUE, set);
        char[] array04 = PrimitiveArrays.toChars(null, set);
        char[] array05 = PrimitiveArrays.toChars(ToArrayMode.IGNORE_NULL, list);
        // char[] array06 = PrimitiveArrays.toChars(ToArrayMode.THROW_WHEN_NULL, list);
        char[] array07 = PrimitiveArrays.toChars(ToArrayMode.DEFAULT_VALUE, list);
        char[] array08 = PrimitiveArrays.toChars(null, list);
        char[] array09 = PrimitiveArrays.toChars((char) 0, set);
        char[] array10 = PrimitiveArrays.toChars((char) 0, list);
        char[] array11 = PrimitiveArrays.toChars('?', set);
        char[] array12 = PrimitiveArrays.toChars('?', list);
        System.out.println(G.toString(array01));
        System.out.println(G.toString(array03));
        System.out.println(G.toString(array04));
        System.out.println(G.toString(array05));
        System.out.println(G.toString(array07));
        System.out.println(G.toString(array08));
        System.out.println(G.toString(array09));
        System.out.println(G.toString(array10));
        System.out.println(G.toString(array11));
        System.out.println(G.toString(array12));

        assertThrows(NullPointerException.class, () -> PrimitiveArrays.toChars(ToArrayMode.THROW_WHEN_NULL, set));
        assertThrows(NullPointerException.class, () -> PrimitiveArrays.toChars(ToArrayMode.THROW_WHEN_NULL, list));
        assertEquals(G.toString(array01), "[' ', 'a', 'b']");
        assertEquals(G.toString(array03), "[' ', '\0', 'a', 'b']");
        assertEquals(G.toString(array04), "[' ', 'a', 'b']");
        assertEquals(G.toString(array05), "[' ', 'a', 'b']");
        assertEquals(G.toString(array07), "[' ', '\0', '\0', 'a', 'b']");
        assertEquals(G.toString(array08), "[' ', 'a', 'b']");
        assertEquals(G.toString(array09), "[' ', '\0', 'a', 'b']");
        assertEquals(G.toString(array10), "[' ', '\0', '\0', 'a', 'b']");
        assertEquals(G.toString(array11), "[' ', '?', 'a', 'b']");
        assertEquals(G.toString(array12), "[' ', '?', '?', 'a', 'b']");
    }

    @Test
    public void testToChars1() {
        char[] chars1 = PrimitiveArrays.toChars(null);
        char[] chars2 = PrimitiveArrays.toChars(new StringBuffer());
        char[] chars3 = PrimitiveArrays.toChars(new StringBuffer(""));
        char[] chars4 = PrimitiveArrays.toChars("");
        char[] chars5 = PrimitiveArrays.toChars("abc");
        // System.out.println(chars1);     // System.out.println 方法不能接收 类型为 char[] 的 null值，会报空指针异常
        System.out.println(chars2);
        System.out.println(chars3);
        System.out.println(chars4);
        System.out.println(chars5);
        assertNull(chars1);
        assertEquals(G.toString(chars2), "[]");
        assertEquals(G.toString(chars3), "[]");
        assertEquals(G.toString(chars4), "[]");
        assertEquals(G.toString(chars5), "['a', 'b', 'c']");
    }

    @Test
    public void testToBooleans() {
        CopyOnWriteArraySet<Boolean> set = new CopyOnWriteArraySet<>();
        set.add(false);
        set.add(null);
        set.add(null);
        set.add(true);
        set.add(false);
        List<Boolean> list = Arrays.asList(false, null, null, true, false);
        System.out.println("set: " + set);
        System.out.println("list: " + list);

        boolean[] array01 = PrimitiveArrays.toBooleans(ToArrayMode.IGNORE_NULL, set);
        // boolean[] array02 = PrimitiveArrays.toBooleans(ToArrayMode.THROW_WHEN_NULL, set);
        boolean[] array03 = PrimitiveArrays.toBooleans(ToArrayMode.DEFAULT_VALUE, set);
        boolean[] array04 = PrimitiveArrays.toBooleans(null, set);
        boolean[] array05 = PrimitiveArrays.toBooleans(ToArrayMode.IGNORE_NULL, list);
        // boolean[] array06 = PrimitiveArrays.toBooleans(ToArrayMode.THROW_WHEN_NULL, list);
        boolean[] array07 = PrimitiveArrays.toBooleans(ToArrayMode.DEFAULT_VALUE, list);
        boolean[] array08 = PrimitiveArrays.toBooleans(null, list);
        boolean[] array09 = PrimitiveArrays.toBooleans(false, set);
        boolean[] array10 = PrimitiveArrays.toBooleans(false, list);
        boolean[] array11 = PrimitiveArrays.toBooleans(true, set);
        boolean[] array12 = PrimitiveArrays.toBooleans(true, list);

        boolean[] array13 = PrimitiveArrays.toBooleans(ToArrayMode.IGNORE_NULL);
        boolean[] array14 = PrimitiveArrays.toBooleans(ToArrayMode.IGNORE_NULL, (Boolean[]) null);
        boolean[] array15 = PrimitiveArrays.toBooleans(ToArrayMode.IGNORE_NULL, null, null);
        boolean[] array16 = PrimitiveArrays.toBooleans(ToArrayMode.DEFAULT_VALUE);
        boolean[] array17 = PrimitiveArrays.toBooleans(ToArrayMode.DEFAULT_VALUE, (Boolean[]) null);
        boolean[] array18 = PrimitiveArrays.toBooleans(ToArrayMode.DEFAULT_VALUE, null, null);
        boolean[] array19 = PrimitiveArrays.toBooleans(true);
        boolean[] array20 = PrimitiveArrays.toBooleans(false, (Boolean[]) null);
        boolean[] array21 = PrimitiveArrays.toBooleans(true, null, null);
        System.out.println(G.toString(array01));
        System.out.println(G.toString(array03));
        System.out.println(G.toString(array04));
        System.out.println(G.toString(array05));
        System.out.println(G.toString(array07));
        System.out.println(G.toString(array08));
        System.out.println(G.toString(array09));
        System.out.println(G.toString(array10));
        System.out.println(G.toString(array11));
        System.out.println(G.toString(array12));
        System.out.println(G.toString(array13));
        System.out.println(G.toString(array14));
        System.out.println(G.toString(array15));
        System.out.println(G.toString(array16));
        System.out.println(G.toString(array17));
        System.out.println(G.toString(array18));
        System.out.println(G.toString(array19));
        System.out.println(G.toString(array20));
        System.out.println(G.toString(array21));

        assertThrows(NullPointerException.class, () -> PrimitiveArrays.toBooleans(ToArrayMode.THROW_WHEN_NULL, set));
        assertThrows(NullPointerException.class, () -> PrimitiveArrays.toBooleans(ToArrayMode.THROW_WHEN_NULL, list));
        assertThrows(NullPointerException.class, () -> PrimitiveArrays.toBooleans(ToArrayMode.THROW_WHEN_NULL, null, null));
        assertEquals(G.toString(array01), "[false, true]");
        assertEquals(G.toString(array03), "[false, false, true]");
        assertEquals(G.toString(array04), "[false, true]");
        assertEquals(G.toString(array05), "[false, true, false]");
        assertEquals(G.toString(array07), "[false, false, false, true, false]");
        assertEquals(G.toString(array08), "[false, true, false]");
        assertEquals(G.toString(array09), "[false, false, true]");
        assertEquals(G.toString(array10), "[false, false, false, true, false]");
        assertEquals(G.toString(array11), "[false, true, true]");
        assertEquals(G.toString(array12), "[false, true, true, true, false]");

        assertEquals(G.toString(array13), "[]");
        assertNull(array14);
        assertEquals(G.toString(array15), "[]");
        assertEquals(G.toString(array16), "[]");
        assertNull(array17);
        assertEquals(G.toString(array18), "[false, false]");
        assertEquals(G.toString(array19), "[]");
        assertNull(array20);
        assertEquals(G.toString(array21), "[true, true]");
    }

    @Test
    public void testToBytes() {
        CopyOnWriteArraySet<Byte> set = new CopyOnWriteArraySet<>();
        set.add((byte) 1);
        set.add(null);
        set.add(null);
        set.add((byte) 20);
        set.add((byte) 15);
        List<Byte> list = Arrays.asList((byte) 1, null, null, (byte) 20, (byte) 15);
        System.out.println("set: " + set);
        System.out.println("list: " + list);

        byte[] array01 = PrimitiveArrays.toBytes(ToArrayMode.IGNORE_NULL, set);
        // byte[] array02 = PrimitiveArrays.toBytes(ToArrayMode.THROW_WHEN_NULL, set);
        byte[] array03 = PrimitiveArrays.toBytes(ToArrayMode.DEFAULT_VALUE, set);
        byte[] array04 = PrimitiveArrays.toBytes(null, set);
        byte[] array05 = PrimitiveArrays.toBytes(ToArrayMode.IGNORE_NULL, list);
        // byte[] array06 = PrimitiveArrays.toBytes(ToArrayMode.THROW_WHEN_NULL, list);
        byte[] array07 = PrimitiveArrays.toBytes(ToArrayMode.DEFAULT_VALUE, list);
        byte[] array08 = PrimitiveArrays.toBytes(null, list);
        byte[] array09 = PrimitiveArrays.toBytes((byte) 6, set);
        byte[] array10 = PrimitiveArrays.toBytes((byte) 6, list);
        System.out.println(G.toString(array01));
        System.out.println(G.toString(array03));
        System.out.println(G.toString(array04));
        System.out.println(G.toString(array05));
        System.out.println(G.toString(array07));
        System.out.println(G.toString(array08));
        System.out.println(G.toString(array09));
        System.out.println(G.toString(array10));

        assertThrows(NullPointerException.class, () -> PrimitiveArrays.toBytes(ToArrayMode.THROW_WHEN_NULL, set));
        assertThrows(NullPointerException.class, () -> PrimitiveArrays.toBytes(ToArrayMode.THROW_WHEN_NULL, list));
        assertEquals(G.toString(array01), "[1, 20, 15]");
        assertEquals(G.toString(array03), "[1, 0, 20, 15]");
        assertEquals(G.toString(array04), "[1, 20, 15]");
        assertEquals(G.toString(array05), "[1, 20, 15]");
        assertEquals(G.toString(array07), "[1, 0, 0, 20, 15]");
        assertEquals(G.toString(array08), "[1, 20, 15]");
        assertEquals(G.toString(array09), "[1, 6, 20, 15]");
        assertEquals(G.toString(array10), "[1, 6, 6, 20, 15]");
    }

    @Test
    public void testToShorts() {
        CopyOnWriteArraySet<Short> set = new CopyOnWriteArraySet<>();
        set.add((short) 1);
        set.add(null);
        set.add(null);
        set.add((short) -20);
        set.add((short) 15);
        List<Short> list = Arrays.asList((short) 1, null, null, (short) -20, (short) 15);
        System.out.println("set: " + set);
        System.out.println("list: " + list);

        short[] array01 = PrimitiveArrays.toShorts(ToArrayMode.IGNORE_NULL, set);
        // short[] array02 = PrimitiveArrays.toShorts(ToArrayMode.THROW_WHEN_NULL, set);
        short[] array03 = PrimitiveArrays.toShorts(ToArrayMode.DEFAULT_VALUE, set);
        short[] array04 = PrimitiveArrays.toShorts(null, set);
        short[] array05 = PrimitiveArrays.toShorts(ToArrayMode.IGNORE_NULL, list);
        // short[] array06 = PrimitiveArrays.toShorts(ToArrayMode.THROW_WHEN_NULL, list);
        short[] array07 = PrimitiveArrays.toShorts(ToArrayMode.DEFAULT_VALUE, list);
        short[] array08 = PrimitiveArrays.toShorts(null, list);
        short[] array09 = PrimitiveArrays.toShorts((short) -6, set);
        short[] array10 = PrimitiveArrays.toShorts((short) 6, list);
        System.out.println(G.toString(array01));
        System.out.println(G.toString(array03));
        System.out.println(G.toString(array04));
        System.out.println(G.toString(array05));
        System.out.println(G.toString(array07));
        System.out.println(G.toString(array08));
        System.out.println(G.toString(array09));
        System.out.println(G.toString(array10));

        assertThrows(NullPointerException.class, () -> PrimitiveArrays.toShorts(ToArrayMode.THROW_WHEN_NULL, set));
        assertThrows(NullPointerException.class, () -> PrimitiveArrays.toShorts(ToArrayMode.THROW_WHEN_NULL, list));
        assertEquals(G.toString(array01), "[1, -20, 15]");
        assertEquals(G.toString(array03), "[1, 0, -20, 15]");
        assertEquals(G.toString(array04), "[1, -20, 15]");
        assertEquals(G.toString(array05), "[1, -20, 15]");
        assertEquals(G.toString(array07), "[1, 0, 0, -20, 15]");
        assertEquals(G.toString(array08), "[1, -20, 15]");
        assertEquals(G.toString(array09), "[1, -6, -20, 15]");
        assertEquals(G.toString(array10), "[1, 6, 6, -20, 15]");
    }

    @Test
    public void testToInts() {
        CopyOnWriteArraySet<Integer> set = new CopyOnWriteArraySet<>();
        set.add(1);
        set.add(null);
        set.add(null);
        set.add(-20);
        set.add(15);
        List<Integer> list = Arrays.asList(1, null, null, -20, 15);
        System.out.println("set: " + set);
        System.out.println("list: " + list);

        int[] array01 = PrimitiveArrays.toInts(ToArrayMode.IGNORE_NULL, set);
        // int[] array02 = PrimitiveArrays.toInts(ToArrayMode.THROW_WHEN_NULL, set);
        int[] array03 = PrimitiveArrays.toInts(ToArrayMode.DEFAULT_VALUE, set);
        int[] array04 = PrimitiveArrays.toInts(null, set);
        int[] array05 = PrimitiveArrays.toInts(ToArrayMode.IGNORE_NULL, list);
        // int[] array06 = PrimitiveArrays.toInts(ToArrayMode.THROW_WHEN_NULL, list);
        int[] array07 = PrimitiveArrays.toInts(ToArrayMode.DEFAULT_VALUE, list);
        int[] array08 = PrimitiveArrays.toInts(null, list);
        int[] array09 = PrimitiveArrays.toInts(-6, set);
        int[] array10 = PrimitiveArrays.toInts(6, list);

        int[] array11 = PrimitiveArrays.toInts(ToArrayMode.IGNORE_NULL);
        int[] array12 = PrimitiveArrays.toInts(ToArrayMode.IGNORE_NULL, (Integer[]) null);
        int[] array13 = PrimitiveArrays.toInts(ToArrayMode.IGNORE_NULL, null, null);
        int[] array14 = PrimitiveArrays.toInts(ToArrayMode.DEFAULT_VALUE);
        int[] array15 = PrimitiveArrays.toInts(ToArrayMode.DEFAULT_VALUE, (Integer[]) null);
        int[] array16 = PrimitiveArrays.toInts(ToArrayMode.DEFAULT_VALUE, null, null);
        int[] array17 = PrimitiveArrays.toInts(-20);
        int[] array18 = PrimitiveArrays.toInts(-20, (Integer[]) null);
        int[] array19 = PrimitiveArrays.toInts(-20, null, null);
        System.out.println(G.toString(array01));
        System.out.println(G.toString(array03));
        System.out.println(G.toString(array04));
        System.out.println(G.toString(array05));
        System.out.println(G.toString(array07));
        System.out.println(G.toString(array08));
        System.out.println(G.toString(array09));
        System.out.println(G.toString(array10));
        System.out.println(G.toString(array11));
        System.out.println(G.toString(array12));
        System.out.println(G.toString(array13));
        System.out.println(G.toString(array14));
        System.out.println(G.toString(array15));
        System.out.println(G.toString(array16));
        System.out.println(G.toString(array17));
        System.out.println(G.toString(array18));
        System.out.println(G.toString(array19));

        assertThrows(NullPointerException.class, () -> PrimitiveArrays.toInts(ToArrayMode.THROW_WHEN_NULL, set));
        assertThrows(NullPointerException.class, () -> PrimitiveArrays.toInts(ToArrayMode.THROW_WHEN_NULL, list));
        assertThrows(NullPointerException.class, () -> PrimitiveArrays.toInts(ToArrayMode.THROW_WHEN_NULL, null, null));
        assertEquals(G.toString(array01), "[1, -20, 15]");
        assertEquals(G.toString(array03), "[1, 0, -20, 15]");
        assertEquals(G.toString(array04), "[1, -20, 15]");
        assertEquals(G.toString(array05), "[1, -20, 15]");
        assertEquals(G.toString(array07), "[1, 0, 0, -20, 15]");
        assertEquals(G.toString(array08), "[1, -20, 15]");
        assertEquals(G.toString(array09), "[1, -6, -20, 15]");
        assertEquals(G.toString(array10), "[1, 6, 6, -20, 15]");
        assertEquals(G.toString(array11), "[]");
        assertNull(array12);
        assertEquals(G.toString(array13), "[]");
        assertEquals(G.toString(array14), "[]");
        assertNull(array15);
        assertEquals(G.toString(array16), "[0, 0]");
        assertEquals(G.toString(array17), "[]");
        assertNull(array18);
        assertEquals(G.toString(array19), "[-20, -20]");
    }

    @Test
    public void testToLongs() {
        CopyOnWriteArraySet<Long> set = new CopyOnWriteArraySet<>();
        set.add(1L);
        set.add(null);
        set.add(null);
        set.add(-20L);
        set.add(15L);
        List<Long> list = Arrays.asList(1L, null, null, -20L, 15L);
        System.out.println("set: " + set);
        System.out.println("list: " + list);

        long[] array01 = PrimitiveArrays.toLongs(ToArrayMode.IGNORE_NULL, set);
        // long[] array02 = PrimitiveArrays.toLongs(ToArrayMode.THROW_WHEN_NULL, set);
        long[] array03 = PrimitiveArrays.toLongs(ToArrayMode.DEFAULT_VALUE, set);
        long[] array04 = PrimitiveArrays.toLongs(null, set);
        long[] array05 = PrimitiveArrays.toLongs(ToArrayMode.IGNORE_NULL, list);
        // long[] array06 = PrimitiveArrays.toLongs(ToArrayMode.THROW_WHEN_NULL, list);
        long[] array07 = PrimitiveArrays.toLongs(ToArrayMode.DEFAULT_VALUE, list);
        long[] array08 = PrimitiveArrays.toLongs(null, list);
        long[] array09 = PrimitiveArrays.toLongs(-6, set);
        long[] array10 = PrimitiveArrays.toLongs(6, list);
        System.out.println(G.toString(array01));
        System.out.println(G.toString(array03));
        System.out.println(G.toString(array04));
        System.out.println(G.toString(array05));
        System.out.println(G.toString(array07));
        System.out.println(G.toString(array08));
        System.out.println(G.toString(array09));
        System.out.println(G.toString(array10));

        assertThrows(NullPointerException.class, () -> PrimitiveArrays.toLongs(ToArrayMode.THROW_WHEN_NULL, set));
        assertThrows(NullPointerException.class, () -> PrimitiveArrays.toLongs(ToArrayMode.THROW_WHEN_NULL, list));
        assertEquals(G.toString(array01), "[1, -20, 15]");
        assertEquals(G.toString(array03), "[1, 0, -20, 15]");
        assertEquals(G.toString(array04), "[1, -20, 15]");
        assertEquals(G.toString(array05), "[1, -20, 15]");
        assertEquals(G.toString(array07), "[1, 0, 0, -20, 15]");
        assertEquals(G.toString(array08), "[1, -20, 15]");
        assertEquals(G.toString(array09), "[1, -6, -20, 15]");
        assertEquals(G.toString(array10), "[1, 6, 6, -20, 15]");
    }

    @Test
    public void testToFloats() {
        CopyOnWriteArraySet<Float> set = new CopyOnWriteArraySet<>();
        set.add(1.01f);
        set.add(null);
        set.add(null);
        set.add(-20.29f);
        set.add(15.5f);
        List<Float> list = Arrays.asList(1.01f, null, null, -20.29f, 15.5f);
        System.out.println("set: " + set);
        System.out.println("list: " + list);

        float[] array01 = PrimitiveArrays.toFloats(ToArrayMode.IGNORE_NULL, set);
        // float[] array02 = PrimitiveArrays.toFloats(ToArrayMode.THROW_WHEN_NULL, set);
        float[] array03 = PrimitiveArrays.toFloats(ToArrayMode.DEFAULT_VALUE, set);
        float[] array04 = PrimitiveArrays.toFloats(null, set);
        float[] array05 = PrimitiveArrays.toFloats(ToArrayMode.IGNORE_NULL, list);
        // float[] array06 = PrimitiveArrays.toFloats(ToArrayMode.THROW_WHEN_NULL, list);
        float[] array07 = PrimitiveArrays.toFloats(ToArrayMode.DEFAULT_VALUE, list);
        float[] array08 = PrimitiveArrays.toFloats(null, list);
        float[] array09 = PrimitiveArrays.toFloats(-6, set);
        float[] array10 = PrimitiveArrays.toFloats(6, list);
        System.out.println(G.toString(array01));
        System.out.println(G.toString(array03));
        System.out.println(G.toString(array04));
        System.out.println(G.toString(array05));
        System.out.println(G.toString(array07));
        System.out.println(G.toString(array08));
        System.out.println(G.toString(array09));
        System.out.println(G.toString(array10));

        assertThrows(NullPointerException.class, () -> PrimitiveArrays.toFloats(ToArrayMode.THROW_WHEN_NULL, set));
        assertThrows(NullPointerException.class, () -> PrimitiveArrays.toFloats(ToArrayMode.THROW_WHEN_NULL, list));
        assertEquals(G.toString(array01), "[1.01, -20.29, 15.5]");
        assertEquals(G.toString(array03), "[1.01, 0, -20.29, 15.5]");
        assertEquals(G.toString(array04), "[1.01, -20.29, 15.5]");
        assertEquals(G.toString(array05), "[1.01, -20.29, 15.5]");
        assertEquals(G.toString(array07), "[1.01, 0, 0, -20.29, 15.5]");
        assertEquals(G.toString(array08), "[1.01, -20.29, 15.5]");
        assertEquals(G.toString(array09), "[1.01, -6, -20.29, 15.5]");
        assertEquals(G.toString(array10), "[1.01, 6, 6, -20.29, 15.5]");
    }

    @Test
    public void testToDoubles() {
        CopyOnWriteArraySet<Double> set = new CopyOnWriteArraySet<>();
        set.add(1.01d);
        set.add(null);
        set.add(null);
        set.add(-20.29d);
        set.add(15.5d);
        List<Double> list = Arrays.asList(1.01d, null, null, -20.29d, 15.5d);
        System.out.println("set: " + set);
        System.out.println("list: " + list);

        double[] array01 = PrimitiveArrays.toDoubles(ToArrayMode.IGNORE_NULL, set);
        // double[] array02 = PrimitiveArrays.toDoubles(ToArrayMode.THROW_WHEN_NULL, set);
        double[] array03 = PrimitiveArrays.toDoubles(ToArrayMode.DEFAULT_VALUE, set);
        double[] array04 = PrimitiveArrays.toDoubles(null, set);
        double[] array05 = PrimitiveArrays.toDoubles(ToArrayMode.IGNORE_NULL, list);
        // double[] array06 = PrimitiveArrays.toDoubles(ToArrayMode.THROW_WHEN_NULL, list);
        double[] array07 = PrimitiveArrays.toDoubles(ToArrayMode.DEFAULT_VALUE, list);
        double[] array08 = PrimitiveArrays.toDoubles(null, list);
        double[] array09 = PrimitiveArrays.toDoubles(-6, set);
        double[] array10 = PrimitiveArrays.toDoubles(6, list);
        System.out.println(G.toString(array01));
        System.out.println(G.toString(array03));
        System.out.println(G.toString(array04));
        System.out.println(G.toString(array05));
        System.out.println(G.toString(array07));
        System.out.println(G.toString(array08));
        System.out.println(G.toString(array09));
        System.out.println(G.toString(array10));

        assertThrows(NullPointerException.class, () -> PrimitiveArrays.toDoubles(ToArrayMode.THROW_WHEN_NULL, set));
        assertThrows(NullPointerException.class, () -> PrimitiveArrays.toDoubles(ToArrayMode.THROW_WHEN_NULL, list));
        assertEquals(G.toString(array01), "[1.01, -20.29, 15.5]");
        assertEquals(G.toString(array03), "[1.01, 0, -20.29, 15.5]");
        assertEquals(G.toString(array04), "[1.01, -20.29, 15.5]");
        assertEquals(G.toString(array05), "[1.01, -20.29, 15.5]");
        assertEquals(G.toString(array07), "[1.01, 0, 0, -20.29, 15.5]");
        assertEquals(G.toString(array08), "[1.01, -20.29, 15.5]");
        assertEquals(G.toString(array09), "[1.01, -6, -20.29, 15.5]");
        assertEquals(G.toString(array10), "[1.01, 6, 6, -20.29, 15.5]");
    }

    @Test
    public void testPrimitiveArrays() {
        Character c = null;
        List<Character> chars1 = Arrays.asList(c, null, null);
        List<Character> chars2 = Arrays.asList(c, 'a', null, 'b');

        char[] cs01 = PrimitiveArrays.toChars(ToArrayMode.IGNORE_NULL, chars1);
        char[] cs02 = PrimitiveArrays.toChars(ToArrayMode.IGNORE_NULL, chars2);
        char[] cs03 = PrimitiveArrays.toChars(ToArrayMode.IGNORE_NULL, c);
        char[] cs04 = PrimitiveArrays.toChars(ToArrayMode.IGNORE_NULL, (Character[]) null);
        char[] cs05 = PrimitiveArrays.toChars(ToArrayMode.IGNORE_NULL, null, null, null);
        char[] cs06 = PrimitiveArrays.toChars(ToArrayMode.IGNORE_NULL, c, 'a', null, 'b');
        char[] cs07 = PrimitiveArrays.toChars(ToArrayMode.IGNORE_NULL);
        char[] cs08 = PrimitiveArrays.toChars(ToArrayMode.IGNORE_NULL, (List<Character>) null);

        char[] cs14 = PrimitiveArrays.toChars(ToArrayMode.THROW_WHEN_NULL, (Character[]) null);
        char[] cs17 = PrimitiveArrays.toChars(ToArrayMode.THROW_WHEN_NULL);
        char[] cs18 = PrimitiveArrays.toChars(ToArrayMode.THROW_WHEN_NULL, (List<Character>) null);
        assertNull(cs14);
        assertEquals(G.toString(cs17), "[]");
        assertNull(cs18);
        assertThrows(NullPointerException.class, () -> ArrayUtils.charsToString(ToArrayMode.THROW_WHEN_NULL, chars1));
        assertThrows(NullPointerException.class, () -> ArrayUtils.charsToString(ToArrayMode.THROW_WHEN_NULL, chars2));
        assertThrows(NullPointerException.class, () -> ArrayUtils.charsToString(ToArrayMode.THROW_WHEN_NULL, c));
        assertThrows(NullPointerException.class, () -> ArrayUtils.charsToString(ToArrayMode.THROW_WHEN_NULL, null, null, null));
        assertThrows(NullPointerException.class, () -> ArrayUtils.charsToString(ToArrayMode.THROW_WHEN_NULL, c, 'a', null, 'b'));


        char[] cs21 = PrimitiveArrays.toChars(ToArrayMode.DEFAULT_VALUE, chars1);
        char[] cs22 = PrimitiveArrays.toChars(ToArrayMode.DEFAULT_VALUE, chars2);
        char[] cs23 = PrimitiveArrays.toChars(ToArrayMode.DEFAULT_VALUE, c);
        char[] cs24 = PrimitiveArrays.toChars(ToArrayMode.DEFAULT_VALUE, (Character[]) null);
        char[] cs25 = PrimitiveArrays.toChars(ToArrayMode.DEFAULT_VALUE, null, null, null);
        char[] cs26 = PrimitiveArrays.toChars(ToArrayMode.DEFAULT_VALUE, c, 'a', null, 'b');
        char[] cs27 = PrimitiveArrays.toChars(ToArrayMode.DEFAULT_VALUE);
        char[] cs28 = PrimitiveArrays.toChars(ToArrayMode.DEFAULT_VALUE, (List<Character>) null);

        char[] cs31 = PrimitiveArrays.toChars(null, chars1);
        char[] cs32 = PrimitiveArrays.toChars(null, chars2);
        char[] cs33 = PrimitiveArrays.toChars(null, c);
        char[] cs34 = PrimitiveArrays.toChars(null, (Character[]) null);
        char[] cs35 = PrimitiveArrays.toChars(null, null, null, null);
        char[] cs36 = PrimitiveArrays.toChars(null, c, 'a', null, 'b');
        char[] cs37 = PrimitiveArrays.toChars((ToArrayMode) null);
        char[] cs38 = PrimitiveArrays.toChars(null, (List<Character>) null);

        char[] cs41 = PrimitiveArrays.toChars('?', chars1);
        char[] cs42 = PrimitiveArrays.toChars('?', chars2);
        char[] cs43 = PrimitiveArrays.toChars('?', c);
        char[] cs44 = PrimitiveArrays.toChars('?', (Character[]) null);
        char[] cs45 = PrimitiveArrays.toChars('?', null, null, null);
        char[] cs46 = PrimitiveArrays.toChars('?', c, 'a', null, 'b');
        char[] cs47 = PrimitiveArrays.toChars('?');
        char[] cs48 = PrimitiveArrays.toChars('?', (List<Character>) null);

        System.out.println("cs01: " + G.toString(cs01));
        System.out.println("cs02: " + G.toString(cs02));
        System.out.println("cs03: " + G.toString(cs03));
        System.out.println("cs04: " + G.toString(cs04));
        System.out.println("cs05: " + G.toString(cs05));
        System.out.println("cs06: " + G.toString(cs06));
        System.out.println("cs07: " + G.toString(cs07));
        System.out.println("cs08: " + G.toString(cs08));
        System.out.println("cs21: " + G.toString(cs21));
        System.out.println("cs22: " + G.toString(cs22));
        System.out.println("cs23: " + G.toString(cs23));
        System.out.println("cs24: " + G.toString(cs24));
        System.out.println("cs25: " + G.toString(cs25));
        System.out.println("cs26: " + G.toString(cs26));
        System.out.println("cs27: " + G.toString(cs27));
        System.out.println("cs28: " + G.toString(cs28));
        System.out.println("cs31: " + G.toString(cs31));
        System.out.println("cs32: " + G.toString(cs32));
        System.out.println("cs33: " + G.toString(cs33));
        System.out.println("cs34: " + G.toString(cs34));
        System.out.println("cs35: " + G.toString(cs35));
        System.out.println("cs36: " + G.toString(cs36));
        System.out.println("cs37: " + G.toString(cs37));
        System.out.println("cs38: " + G.toString(cs38));
        System.out.println("cs41: " + G.toString(cs41));
        System.out.println("cs42: " + G.toString(cs42));
        System.out.println("cs43: " + G.toString(cs43));
        System.out.println("cs44: " + G.toString(cs44));
        System.out.println("cs45: " + G.toString(cs45));
        System.out.println("cs46: " + G.toString(cs46));
        System.out.println("cs47: " + G.toString(cs47));
        System.out.println("cs48: " + G.toString(cs48));

        assertEquals(G.toString(cs01), "[]");
        assertEquals(G.toString(cs02), "['a', 'b']");
        assertEquals(G.toString(cs03), "[]");
        assertNull(cs04);
        assertEquals(G.toString(cs05), "[]");
        assertEquals(G.toString(cs06), "['a', 'b']");
        assertEquals(G.toString(cs07), "[]");
        assertNull(cs08);
        assertEquals(G.toString(cs21), "['\0', '\0', '\0']");
        assertEquals(G.toString(cs22), "['\0', 'a', '\0', 'b']");
        assertEquals(G.toString(cs23), "['\0']");
        assertNull(cs24);
        assertEquals(G.toString(cs25), "['\0', '\0', '\0']");
        assertEquals(G.toString(cs26), "['\0', 'a', '\0', 'b']");
        assertEquals(G.toString(cs27), "[]");
        assertNull(cs28);
        assertEquals(G.toString(cs31), "[]");
        assertEquals(G.toString(cs32), "['a', 'b']");
        assertEquals(G.toString(cs33), "[]");
        assertNull(cs34);
        assertEquals(G.toString(cs35), "[]");
        assertEquals(G.toString(cs36), "['a', 'b']");
        assertEquals(G.toString(cs37), "[]");
        assertNull(cs38);
        assertEquals(G.toString(cs41), "['?', '?', '?']");
        assertEquals(G.toString(cs42), "['?', 'a', '?', 'b']");
        assertEquals(G.toString(cs43), "['?']");
        assertNull(cs44);
        assertEquals(G.toString(cs45), "['?', '?', '?']");
        assertEquals(G.toString(cs46), "['?', 'a', '?', 'b']");
        assertEquals(G.toString(cs47), "[]");
        assertNull(cs48);
    }
}
