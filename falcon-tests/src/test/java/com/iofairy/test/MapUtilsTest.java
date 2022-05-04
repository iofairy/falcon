package com.iofairy.test;

import com.iofairy.falcon.map.MapUtils;
import com.iofairy.top.G;
import com.iofairy.tuple.Tuple2;
import org.junit.jupiter.api.Test;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author GG
 * @version 1.0
 * @date 2021/9/11 14:18
 */
public class MapUtilsTest {
    @Test
    public void testSortedMap() {
        Map<String, Double> doubleMap = new HashMap<>();
        doubleMap.put("aa", 90.1);
        doubleMap.put("b", 85.2);
        doubleMap.put("ccc", 70.5);
        doubleMap.put("dd", 89.8);
        doubleMap.put("eeeee", 96.3);

        List<Map.Entry<String, Double>> entries = MapUtils.sortBy(doubleMap, Map.Entry::getValue);
        assertEquals("[ccc=70.5, b=85.2, dd=89.8, aa=90.1, eeeee=96.3]", entries.toString());

        List<Map.Entry<String, Double>> entriesDouble = MapUtils.sortByDouble(doubleMap, Map.Entry::getValue);
        assertEquals("[ccc=70.5, b=85.2, dd=89.8, aa=90.1, eeeee=96.3]", entriesDouble.toString());

        List<Map.Entry<String, Double>> entriesStrLength = MapUtils.sortBy(entriesDouble, kv -> kv.getKey().length());
        assertEquals("[b=85.2, dd=89.8, aa=90.1, ccc=70.5, eeeee=96.3]", entriesStrLength.toString());

    }

    @Test
    public void testSortMap1() {
        Map<String, List<Integer>> listMap = new HashMap<>();
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> list2 = Arrays.asList(1, 2);
        List<Integer> list3 = Arrays.asList(1, 2, 3, 4);
        List<Integer> list4 = Arrays.asList(1, 2, 3);
        List<Integer> list5 = Arrays.asList();

        listMap.put("a", list1);
        listMap.put("b", list2);
        listMap.put("c", list3);
        listMap.put("d", list4);
        listMap.put("e", list5);

        List<Map.Entry<String, List<Integer>>> entries = MapUtils.sortBy(listMap, kv -> kv.getValue().size());
        assertEquals("[e=[], b=[1, 2], d=[1, 2, 3], c=[1, 2, 3, 4], a=[1, 2, 3, 4, 5]]", entries.toString());

        List<Map.Entry<String, List<Integer>>> entriesInt = MapUtils.sortByInt(listMap, kv -> -kv.getValue().size());
        assertEquals("[a=[1, 2, 3, 4, 5], c=[1, 2, 3, 4], d=[1, 2, 3], b=[1, 2], e=[]]", entriesInt.toString());
    }

    @Test
    public void testMapToArray() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", 123456);
        map.put("name", "zs");
        map.put("age", 20);
        map.put("nickname", "nickname");
        map.put("hobby", "swim");
        map.put(null, "null_value");
        map.put("other", 'a');

        Tuple2<String[], Object[]> tuple = MapUtils.mapToArray(map, String.class, Object.class);
        assertEquals("([\"id\", \"name\", \"age\", \"nickname\", \"hobby\", null, \"other\"], " +
                "[123456, \"zs\", 20, \"nickname\", \"swim\", \"null_value\", 'a'])", tuple.toString());
        assertEquals("java.lang.Character", tuple._2[6].getClass().getName());
        assertEquals(8, ((String) tuple._2[3]).length());
        System.out.println(tuple);
        System.out.println("tuple._2[6] class name: " + tuple._2[6].getClass().getName());
        System.out.println("((String) tuple._2[3]).length(): " + ((String) tuple._2[3]).length());

        Tuple2<String[], Object[]> tuple1 = MapUtils.mapToArray(map, String.class, Object.class, null);
        assertEquals("([\"id\", \"name\", \"age\", \"nickname\", \"hobby\", null, \"other\"], " +
                "[123456, \"zs\", 20, \"nickname\", \"swim\", \"null_value\", 'a'])", G.toString(tuple1));
        System.out.println(tuple1);

        Tuple2<String[], Object[]> tuple2 = MapUtils.mapToArray(map, String.class, Object.class, "other", null, "age");
        assertEquals("([\"id\", \"name\", \"nickname\", \"hobby\"], [123456, \"zs\", \"nickname\", \"swim\"])", tuple2.toString());
        System.out.println(tuple2);

    }

    @Test
    public void testMapToArray1() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", 123456);
        map.put("name", "zs");
        map.put("age", 20);
        map.put("nickname", "nickname");
        map.put("hobby", "swim");
        map.put(null, "null_value");
        map.put("other", 'a');

        String[] keys = MapUtils.keyToArray(map, String.class, "other", null);
        Object[] values = MapUtils.valueToArray(map, Object.class, "other", null);
        assertEquals("[\"id\", \"name\", \"age\", \"nickname\", \"hobby\"]", G.toString(keys));
        assertEquals("[123456, \"zs\", 20, \"nickname\", \"swim\"]", G.toString(values));
        System.out.println(G.toString(keys));
        System.out.println(G.toString(values));

    }
}
