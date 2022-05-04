package com.iofairy.test;

import com.iofairy.falcon.iterable.ArrayUtils;
import com.iofairy.falcon.iterable.ToArrayMode;
import com.iofairy.top.G;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author GG
 * @version 1.0
 * @date 2022/5/2 23:00
 */
public class ArrayUtilsTest {

    @Test
    public void testToCharacterList() {
        List<Character> chars1 = ArrayUtils.toCharList((char[]) null);
        List<Character> chars2 = ArrayUtils.toCharList(new char[0]);
        List<Character> chars3 = ArrayUtils.toCharList(new char[]{'a', 'b', 'c'});
        List<Character> chars4 = ArrayUtils.toCharList((String) null);
        List<Character> chars5 = ArrayUtils.toCharList("");
        List<Character> chars6 = ArrayUtils.toCharList(" a b c");
        List<Character> chars7 = ArrayUtils.toCharList(" a 呀 文字 ");

        assertNull(chars1);
        assertEquals(G.toString(chars2), "[]");
        assertEquals(G.toString(chars3), "['a', 'b', 'c']");
        assertNull(chars4);
        assertEquals(G.toString(chars5), "[]");
        assertEquals(G.toString(chars6), "[' ', 'a', ' ', 'b', ' ', 'c']");
        assertEquals(G.toString(chars7), "[' ', 'a', ' ', '呀', ' ', '文', '字', ' ']");
    }


    @Test
    public void testToString() {
        Character c = null;
        Collection<Character> chars1 = Arrays.asList(c, null, null);
        Collection<Character> chars2 = Arrays.asList(c, 'a', null, 'b');

        String cs01 = ArrayUtils.charsToString(ToArrayMode.IGNORE_NULL, chars1);
        String cs02 = ArrayUtils.charsToString(ToArrayMode.IGNORE_NULL, chars2);
        String cs03 = ArrayUtils.charsToString(ToArrayMode.IGNORE_NULL, c);
        String cs04 = ArrayUtils.charsToString(ToArrayMode.IGNORE_NULL, (Character[]) null);
        String cs05 = ArrayUtils.charsToString(ToArrayMode.IGNORE_NULL, null, null);
        String cs06 = ArrayUtils.charsToString(ToArrayMode.IGNORE_NULL, c, 'a', null, 'b');
        String cs07 = ArrayUtils.charsToString(ToArrayMode.IGNORE_NULL);
        String cs08 = ArrayUtils.charsToString(ToArrayMode.IGNORE_NULL, (Collection<Character>) null);

        String cs14 = ArrayUtils.charsToString(ToArrayMode.THROW_WHEN_NULL, (Character[]) null);
        String cs17 = ArrayUtils.charsToString(ToArrayMode.THROW_WHEN_NULL);
        String cs18 = ArrayUtils.charsToString(ToArrayMode.THROW_WHEN_NULL, (Collection<Character>) null);
        assertNull(cs14);
        assertEquals(cs17, "");
        assertNull(cs18);
        assertThrows(NullPointerException.class, () -> ArrayUtils.charsToString(ToArrayMode.THROW_WHEN_NULL, chars1));
        assertThrows(NullPointerException.class, () -> ArrayUtils.charsToString(ToArrayMode.THROW_WHEN_NULL, chars2));
        assertThrows(NullPointerException.class, () -> ArrayUtils.charsToString(ToArrayMode.THROW_WHEN_NULL, c));
        assertThrows(NullPointerException.class, () -> ArrayUtils.charsToString(ToArrayMode.THROW_WHEN_NULL, null, null));
        assertThrows(NullPointerException.class, () -> ArrayUtils.charsToString(ToArrayMode.THROW_WHEN_NULL, c, 'a', null, 'b'));

        String cs21 = ArrayUtils.charsToString(ToArrayMode.DEFAULT_VALUE, chars1);
        String cs22 = ArrayUtils.charsToString(ToArrayMode.DEFAULT_VALUE, chars2);
        String cs23 = ArrayUtils.charsToString(ToArrayMode.DEFAULT_VALUE, c);
        String cs24 = ArrayUtils.charsToString(ToArrayMode.DEFAULT_VALUE, (Character[]) null);
        String cs25 = ArrayUtils.charsToString(ToArrayMode.DEFAULT_VALUE, null, null);
        String cs26 = ArrayUtils.charsToString(ToArrayMode.DEFAULT_VALUE, c, 'a', null, 'b');
        String cs27 = ArrayUtils.charsToString(ToArrayMode.DEFAULT_VALUE);
        String cs28 = ArrayUtils.charsToString(ToArrayMode.DEFAULT_VALUE, (Collection<Character>) null);

        String cs31 = ArrayUtils.charsToString(null, chars1);
        String cs32 = ArrayUtils.charsToString(null, chars2);
        String cs33 = ArrayUtils.charsToString(null, c);
        String cs34 = ArrayUtils.charsToString(null, (Character[]) null);
        String cs35 = ArrayUtils.charsToString(null, null, null);
        String cs36 = ArrayUtils.charsToString(null, c, 'a', null, 'b');
        String cs37 = ArrayUtils.charsToString(null);
        String cs38 = ArrayUtils.charsToString(null, (Collection<Character>) null);

        String cs41 = ArrayUtils.charsToString('?', chars1);
        String cs42 = ArrayUtils.charsToString('?', chars2);
        String cs43 = ArrayUtils.charsToString('?', c);
        String cs44 = ArrayUtils.charsToString('?', (Character[]) null);
        String cs45 = ArrayUtils.charsToString('?', null, null);
        String cs46 = ArrayUtils.charsToString('?', c, 'a', null, 'b');
        String cs47 = ArrayUtils.charsToString('?');
        String cs48 = ArrayUtils.charsToString('?', (Collection<Character>) null);

        System.out.println("cs01: " + cs01);
        System.out.println("cs02: " + cs02);
        System.out.println("cs03: " + cs03);
        System.out.println("cs04: " + cs04);
        System.out.println("cs05: " + cs05);
        System.out.println("cs06: " + cs06);
        System.out.println("cs07: " + cs07);
        System.out.println("cs08: " + cs08);
        System.out.println("cs21: " + cs21);
        System.out.println("cs22: " + cs22);
        System.out.println("cs23: " + cs23);
        System.out.println("cs24: " + cs24);
        System.out.println("cs25: " + cs25);
        System.out.println("cs26: " + cs26);
        System.out.println("cs27: " + cs27);
        System.out.println("cs28: " + cs28);
        System.out.println("cs31: " + cs31);
        System.out.println("cs32: " + cs32);
        System.out.println("cs33: " + cs33);
        System.out.println("cs34: " + cs34);
        System.out.println("cs35: " + cs35);
        System.out.println("cs36: " + cs36);
        System.out.println("cs37: " + cs37);
        System.out.println("cs38: " + cs38);
        System.out.println("cs41: " + cs41);
        System.out.println("cs42: " + cs42);
        System.out.println("cs43: " + cs43);
        System.out.println("cs44: " + cs44);
        System.out.println("cs45: " + cs45);
        System.out.println("cs46: " + cs46);
        System.out.println("cs47: " + cs47);
        System.out.println("cs48: " + cs48);

        assertEquals(cs01, "");
        assertEquals(cs02, "ab");
        assertEquals(cs03, "");
        assertNull(cs04);
        assertEquals(cs05, "");
        assertEquals(cs06, "ab");
        assertEquals(cs07, "");
        assertNull(cs08);
        assertEquals(cs21, "\u0000\u0000\u0000");
        assertEquals(cs22, "\0a\0b");
        assertEquals(cs23, "\0");
        assertNull(cs24);
        assertEquals(cs25, "\0\0");
        assertEquals(cs26, "\0a\0b");
        assertEquals(cs27, "");
        assertNull(cs28);
        assertEquals(cs31, "");
        assertEquals(cs32, "ab");
        assertEquals(cs33, "");
        assertNull(cs34);
        assertEquals(cs35, "");
        assertEquals(cs36, "ab");
        assertEquals(cs37, "");
        assertNull(cs38);
        assertEquals(cs41, "???");
        assertEquals(cs42, "?a?b");
        assertEquals(cs43, "?");
        assertNull(cs44);
        assertEquals(cs45, "??");
        assertEquals(cs46, "?a?b");
        assertEquals(cs47, "");
        assertNull(cs48);
    }
}
