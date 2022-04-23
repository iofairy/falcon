package com.iofairy.test;

import com.iofairy.falcon.string.Strings;
import com.iofairy.top.G;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author GG
 * @version 1.0
 */
public class StringsTest {
    @Test
    public void testToCharArr() {
        Character c = null;
        List<Character> chars1 = Arrays.asList(c, null, null);
        List<Character> chars2 = Arrays.asList(c, 'a', null, 'b');

        char[] cs1 = Strings.toCharArray(chars1);
        char[] cs2 = Strings.toCharArray(chars2);
        char[] cs3 = Strings.toCharArray(c);
        char[] cs4 = Strings.toCharArray((Character[]) null);
        char[] cs5 = Strings.toCharArray(null, null, null);
        char[] cs6 = Strings.toCharArray(c, 'a', null, 'b');
        char[] cs7 = Strings.toCharArray();
        char[] cs8 = Strings.toCharArray((List<Character>) null);

        assertEquals(G.toString(cs1), "[]");
        assertEquals(G.toString(cs2), "['a', 'b']");
        assertEquals(G.toString(cs3), "[]");
        assertNull(cs4);
        assertEquals(G.toString(cs5), "[]");
        assertEquals(G.toString(cs6), "['a', 'b']");
        assertEquals(G.toString(cs7), "[]");
        assertNull(cs8);

    }

    @Test
    public void testToString() {
        Character c = null;
        List<Character> chars1 = Arrays.asList(c, null, null);
        List<Character> chars2 = Arrays.asList(c, 'a', null, 'b');

        String cs1 = Strings.charsToString(chars1);
        String cs2 = Strings.charsToString(chars2);
        String cs3 = Strings.charsToString(c);
        String cs4 = Strings.charsToString((Character[]) null);
        String cs5 = Strings.charsToString(null, null, null);
        String cs6 = Strings.charsToString(c, 'a', null, 'b');
        String cs7 = Strings.charsToString();
        String cs8 = Strings.charsToString((List<Character>) null);

        assertEquals(cs1, "");
        assertEquals(cs2, "ab");
        assertEquals(cs3, "");
        assertNull(cs4);
        assertEquals(cs5, "");
        assertEquals(cs6, "ab");
        assertEquals(cs7, "");
        assertNull(cs8);

    }

    @Test
    public void testToCharacterList() {
        List<Character> chars1 = Strings.toCharList((char[]) null);
        List<Character> chars2 = Strings.toCharList(new char[0]);
        List<Character> chars3 = Strings.toCharList(new char[]{'a', 'b', 'c'});
        List<Character> chars4 = Strings.toCharList((String) null);
        List<Character> chars5 = Strings.toCharList("");
        List<Character> chars6 = Strings.toCharList(" a b c");
        List<Character> chars7 = Strings.toCharList(" a 呀 文字 ");

        assertNull(chars1);
        assertEquals(G.toString(chars2), "[]");
        assertEquals(G.toString(chars3), "['a', 'b', 'c']");
        assertNull(chars4);
        assertEquals(G.toString(chars5), "[]");
        assertEquals(G.toString(chars6), "[' ', 'a', ' ', 'b', ' ', 'c']");
        assertEquals(G.toString(chars7), "[' ', 'a', ' ', '呀', ' ', '文', '字', ' ']");
    }

}
