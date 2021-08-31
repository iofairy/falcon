package com.iofairy;

import com.iofairy.falcon.util.CollectorUtils;
import org.junit.jupiter.api.Test;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author GG
 * @version 1.0
 */
public class CollectorUtilsTest {
    @Test
    public void testDivideArr() {
        Integer[] intArr = {0, 1, 2, 3, 4, 5, 6, 7};
        List<Integer[]> divide = CollectorUtils.divide(intArr, 1);
        for (Integer[] integers : divide) {
            System.out.println(Arrays.asList(integers));
        }
        System.out.println("=========================");
        assertEquals(8, divide.size());
        assertEquals(5, divide.get(5)[0]);

        List<Integer[]> divide1 = CollectorUtils.divide(intArr, 2);
        for (Integer[] integers : divide1) {
            System.out.println(Arrays.asList(integers));
        }
        System.out.println("=========================");
        assertEquals(4, divide1.size());
        assertEquals(2, divide1.get(1)[0]);

        List<Integer[]> divide2 = CollectorUtils.divide(intArr, 3);
        for (Integer[] integers : divide2) {
            System.out.println(Arrays.asList(integers));
        }
        System.out.println("=========================");
        assertEquals(3, divide2.size());
        assertEquals(5, divide2.get(1)[2]);
    }

    @Test
    public void testDivideList() {
        Integer[] intArr = {0, 1, 2, 3, 4, 5, 6};
        List<Integer> intList = Arrays.asList(intArr);
        List<List<Integer>> divideList = CollectorUtils.divide(intList, 2);
        assertEquals("[[0, 1], [2, 3], [4, 5], [6]]", divideList.toString());

        List<String> strList = new ArrayList<>();
        strList.add("a");
        strList.add("b");
        strList.add("c");

        List<List<String>> divide0 = CollectorUtils.divide(strList, 0);
        assertEquals("[[a, b, c]]", divide0.toString());
        List<List<String>> divide1 = CollectorUtils.divide(strList, 1);
        assertEquals("[[a], [b], [c]]", divide1.toString());
        List<List<String>> divide2 = CollectorUtils.divide(strList, 2);
        assertEquals("[[a, b], [c]]", divide2.toString());
        List<List<String>> divide3 = CollectorUtils.divide(strList, 3);
        assertEquals("[[a, b, c]]", divide3.toString());
        List<List<String>> divide4 = CollectorUtils.divide(strList, 4);
        assertEquals("[[a, b, c]]", divide4.toString());

        List<String> nullList = null;
        List<List<String>> nullDivide = CollectorUtils.divide(nullList, 3);
        assertEquals("[]", nullDivide.toString());

        List<String> strList1 = Arrays.asList("a", "b", "c", "d", "e");
        List<List<String>> divide5 = CollectorUtils.divide(strList1, 2);
        assertEquals("[[a, b], [c, d], [e]]", divide5.toString());

    }

    @Test
    public void testDivideSet() {
        Set<Integer> intSet = new HashSet<>();
        intSet.add(1);
        intSet.add(2);
        intSet.add(3);

        List<Set<Integer>> divide0 = CollectorUtils.divide(intSet, 0);
        assertEquals("[[1, 2, 3]]", divide0.toString());
        List<Set<Integer>> divide1 = CollectorUtils.divide(intSet, 1);
        assertEquals("[[1], [2], [3]]", divide1.toString());
        List<Set<Integer>> divide2 = CollectorUtils.divide(intSet, 2);
        assertEquals("[[1, 2], [3]]", divide2.toString());
        List<Set<Integer>> divide3 = CollectorUtils.divide(intSet, 3);
        assertEquals("[[1, 2, 3]]", divide3.toString());
        List<Set<Integer>> divide4 = CollectorUtils.divide(intSet, 4);
        assertEquals("[[1, 2, 3]]", divide4.toString());

    }

    @Test
    public void testDivideMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);

        List<Map<String, Integer>> divide0 = CollectorUtils.divide(map, 0);
        assertEquals("[{a=1, b=2, c=3}]", divide0.toString());
        List<Map<String, Integer>> divide1 = CollectorUtils.divide(map, 1);
        assertEquals("[{a=1}, {b=2}, {c=3}]", divide1.toString());
        List<Map<String, Integer>> divide2 = CollectorUtils.divide(map, 2);
        assertEquals("[{a=1, b=2}, {c=3}]", divide2.toString());
        List<Map<String, Integer>> divide3 = CollectorUtils.divide(map, 3);
        assertEquals("[{a=1, b=2, c=3}]", divide3.toString());
        List<Map<String, Integer>> divide4 = CollectorUtils.divide(map, 4);
        assertEquals("[{a=1, b=2, c=3}]", divide4.toString());
    }
}
