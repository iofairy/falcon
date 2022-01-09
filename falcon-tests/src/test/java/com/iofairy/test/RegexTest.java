package com.iofairy.test;

import com.iofairy.falcon.regex.MatchInfo;
import com.iofairy.falcon.regex.Regex;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author GG
 * @version 1.0
 */
public class RegexTest {
    @Test
    public void testMatch() {
        System.out.println("\n>>> testMatch");

        MatchInfo match1 = Regex.match(null, "a");
        MatchInfo match2 = Regex.match(Pattern.compile(""), null);
        MatchInfo match3 = Regex.match(Pattern.compile(""), "");
        MatchInfo match4 = Regex.match(Pattern.compile("\\."), "");
        MatchInfo match5 = Regex.match(Pattern.compile("\\."), "0.0.1");
        MatchInfo match6 = Regex.match(Pattern.compile("\\d+"), "001a002b");
        assertNull(match1);
        assertNull(match2);
        assertEquals("[MatchIndex{start=0, end=0, length=0}]", match3.indices.toString());
        assertEquals("", match3.group(0));
        assertEquals("[]", match4.indices.toString());
        assertNull(match4.group(0));
        assertEquals("[MatchIndex{start=1, end=2, length=1}, MatchIndex{start=3, end=4, length=1}]", match5.indices.toString());
        assertEquals(".", match5.group(0));
        assertEquals("[MatchIndex{start=0, end=3, length=3}, MatchIndex{start=4, end=7, length=3}]", match6.indices.toString());
        assertEquals("002", match6.group(1));

        System.out.println(match1);
        System.out.println(match2);
        System.out.println(match3);
        System.out.println(match4);
        System.out.println(match5);
        System.out.println(match6);
    }

    @Test
    public void testMatchOverlap() {
        System.out.println("\n>>> testMatchOverlap");

        MatchInfo match1 = Regex.matchOverlap(null, "a");
        MatchInfo match2 = Regex.matchOverlap(Pattern.compile(""), null);
        MatchInfo match3 = Regex.matchOverlap(Pattern.compile(""), "");
        MatchInfo match4 = Regex.matchOverlap(Pattern.compile("\\."), "");
        MatchInfo match5 = Regex.matchOverlap(Pattern.compile("\\."), "0.0.1");
        MatchInfo match6 = Regex.matchOverlap(Pattern.compile("\\d+"), "001a002b");
        assertNull(match1);
        assertNull(match2);
        assertEquals("[MatchIndex{start=0, end=0, length=0}]", match3.indices.toString());
        assertEquals("", match3.group(0));
        assertEquals("[]", match4.indices.toString());
        assertNull(match4.group(0));
        assertEquals("[MatchIndex{start=1, end=2, length=1}, MatchIndex{start=3, end=4, length=1}]", match5.indices.toString());
        assertEquals(".", match5.group(0));
        assertEquals(6, match6.count);
        assertEquals("1", match6.group(2));

        System.out.println(match1);
        System.out.println(match2);
        System.out.println(match3);
        System.out.println(match4);
        System.out.println(match5);
        System.out.println(match6);
    }
}
