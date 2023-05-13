package com.iofairy.test;

import com.iofairy.falcon.iterable.CollectionKit;
import com.iofairy.tuple.Tuple;
import com.iofairy.tuple.Tuple2;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author GG
 * @version 1.0
 */
public class CollectionKitTest {
    @Test
    public void testDivideArr() {
        System.out.println("\n>>> testDivideArr");

        Integer[] intArr = {0, 1, 2, 3, 4, 5, 6, 7};
        List<Integer[]> divide = CollectionKit.divide(intArr, 1);
        for (Integer[] integers : divide) {
            System.out.println(Arrays.asList(integers));
        }
        System.out.println("=========================");
        assertEquals(8, divide.size());
        assertEquals(5, divide.get(5)[0]);

        List<Integer[]> divide1 = CollectionKit.divide(intArr, 2);
        for (Integer[] integers : divide1) {
            System.out.println(Arrays.asList(integers));
        }
        System.out.println("=========================");
        assertEquals(4, divide1.size());
        assertEquals(2, divide1.get(1)[0]);

        List<Integer[]> divide2 = CollectionKit.divide(intArr, 3);
        for (Integer[] integers : divide2) {
            System.out.println(Arrays.asList(integers));
        }
        System.out.println("=========================");
        assertEquals(3, divide2.size());
        assertEquals(5, divide2.get(1)[2]);
    }

    @Test
    public void testDivideList() {
        System.out.println("\n>>> testDivideList");

        Integer[] intArr = {0, 1, 2, 3, 4, 5, 6};
        List<Integer> intList = Arrays.asList(intArr);
        List<List<Integer>> divideList = CollectionKit.divide(intList, 2);
        assertEquals("[[0, 1], [2, 3], [4, 5], [6]]", divideList.toString());

        List<String> strList = new ArrayList<>();
        strList.add("a");
        strList.add("b");
        strList.add("c");

        List<List<String>> divide0 = CollectionKit.divide(strList, 0);
        assertEquals("[[a, b, c]]", divide0.toString());
        List<List<String>> divide1 = CollectionKit.divide(strList, 1);
        assertEquals("[[a], [b], [c]]", divide1.toString());
        List<List<String>> divide2 = CollectionKit.divide(strList, 2);
        assertEquals("[[a, b], [c]]", divide2.toString());
        List<List<String>> divide3 = CollectionKit.divide(strList, 3);
        assertEquals("[[a, b, c]]", divide3.toString());
        List<List<String>> divide4 = CollectionKit.divide(strList, 4);
        assertEquals("[[a, b, c]]", divide4.toString());

        List<String> nullList = null;
        List<List<String>> nullDivide = CollectionKit.divide(nullList, 3);
        assertEquals("[]", nullDivide.toString());

        List<String> strList1 = Arrays.asList("a", "b", "c", "d", "e");
        List<List<String>> divide5 = CollectionKit.divide(strList1, 2);
        assertEquals("[[a, b], [c, d], [e]]", divide5.toString());

    }

    @Test
    public void testDivideSet() {
        System.out.println("\n>>> testDivideSet");

        Set<Integer> intSet = new HashSet<>();
        intSet.add(1);
        intSet.add(2);
        intSet.add(3);

        List<Set<Integer>> divide0 = CollectionKit.divide(intSet, 0);
        assertEquals("[[1, 2, 3]]", divide0.toString());
        List<Set<Integer>> divide1 = CollectionKit.divide(intSet, 1);
        assertEquals("[[1], [2], [3]]", divide1.toString());
        List<Set<Integer>> divide2 = CollectionKit.divide(intSet, 2);
        assertEquals("[[1, 2], [3]]", divide2.toString());
        List<Set<Integer>> divide3 = CollectionKit.divide(intSet, 3);
        assertEquals("[[1, 2, 3]]", divide3.toString());
        List<Set<Integer>> divide4 = CollectionKit.divide(intSet, 4);
        assertEquals("[[1, 2, 3]]", divide4.toString());

    }

    @Test
    public void testDivideMap() {
        System.out.println("\n>>> testDivideMap");

        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);

        List<Map<String, Integer>> divide0 = CollectionKit.divide(map, 0);
        assertEquals("[{a=1, b=2, c=3}]", divide0.toString());
        List<Map<String, Integer>> divide1 = CollectionKit.divide(map, 1);
        assertEquals("[{a=1}, {b=2}, {c=3}]", divide1.toString());
        List<Map<String, Integer>> divide2 = CollectionKit.divide(map, 2);
        assertEquals("[{a=1, b=2}, {c=3}]", divide2.toString());
        List<Map<String, Integer>> divide3 = CollectionKit.divide(map, 3);
        assertEquals("[{a=1, b=2, c=3}]", divide3.toString());
        List<Map<String, Integer>> divide4 = CollectionKit.divide(map, 4);
        assertEquals("[{a=1, b=2, c=3}]", divide4.toString());
    }

    @Test
    public void testBalance0() {
        System.out.println("\n>>> testBalance0");

        List<Integer> list0 = Arrays.asList(11, 12, 13, 14, 15);
        List<Integer> list1 = Arrays.asList(26);
        Set<Integer> list2 = new HashSet<>(Arrays.asList(57, 58, 59, 58, 59));
        List<Integer> list3 = Arrays.asList(30, 31);
        List<Integer> list4 = Arrays.asList(83, 84, 85);
        List<Integer> list5 = Arrays.asList(71, 72);

        List<Collection<Integer>> collections = Arrays.asList(list0, list1, list2, list3, list4, list5);

        List<List<Integer>> balance1 = CollectionKit.balance(2, collections);
        System.out.println(balance1);
        List<List<Integer>> balance2 = CollectionKit.balance(3, collections);
        System.out.println(balance2);
        List<List<Integer>> balance3 = CollectionKit.balance(4, collections);
        System.out.println(balance3);
        assertEquals(balance1.toString(), "[[11, 12, 13, 14, 15, 30, 31, 26], [57, 58, 59, 83, 84, 85, 71, 72]]");
        assertEquals(balance2.toString(), "[[57, 58, 59, 30, 31], [11, 12, 13, 14, 15], [83, 84, 85, 71, 72, 26]]");
        assertEquals(balance3.toString(), "[[83, 84, 85], [57, 58, 59, 26], [30, 31, 71, 72], [11, 12, 13, 14, 15]]");

    }

    @Test
    public void testBalance1() {
        System.out.println("\n>>> testBalance1");

        List<Integer> list0 = Arrays.asList(11, 12, 13, 14, 15);
        List<Integer> list1 = Arrays.asList(26);
        List<Integer> list2 = Arrays.asList(57, 58, 59);
        List<Integer> list3 = Arrays.asList(30, 31);
        List<Integer> list4 = Arrays.asList(62);
        List<Integer> list5 = Arrays.asList(83, 84, 85);
        List<Integer> list6 = Arrays.asList(71, 72);
        List<Integer> list7 = Arrays.asList(91, 92, 93, 94);
        List<Integer> list8 = new ArrayList<>();
        List<Integer> list9 = null;


        List<List<Integer>> balance = CollectionKit.balance(0, list0, list1, list2, list3, list4, list5, list6, list7, list8, list9);
        assertNull(balance);
        List<List<Integer>> balance1 = CollectionKit.balance(3, list0, list1, list2, list3, list4, list5, list6, list7, list8, list9);
        assertEquals("[[57, 58, 59, 83, 84, 85, 62], [91, 92, 93, 94, 30, 31, 26], [11, 12, 13, 14, 15, 71, 72]]", balance1.toString());
        List<List<Integer>> balance2 = CollectionKit.balance(4, list0, list1, list2, list3, list4, list5, list6, list7, list8, list9);
        assertEquals("[[83, 84, 85, 71, 72], [57, 58, 59, 30, 31], [11, 12, 13, 14, 15], [91, 92, 93, 94, 26, 62]]", balance2.toString());
        List<List<Integer>> balance3 = CollectionKit.balance(10, list0, list1, list2, list3, list4, list5, list6, list7, list8, list9);
        assertEquals("[[26], [62], [30, 31], [71, 72], [57, 58, 59], [83, 84, 85], [91, 92, 93, 94], [11, 12, 13, 14, 15]]", balance3.toString());
        List<List<Integer>> balance4 = CollectionKit.balance(1, list0, list1, list2, list3, list4, list5, list6, list7, list8, list9);
        assertEquals("[[11, 12, 13, 14, 15, 91, 92, 93, 94, 57, 58, 59, 83, 84, 85, 30, 31, 71, 72, 26, 62]]", balance4.toString());

    }

    @Test
    @SuppressWarnings("unchecked")
    public void testBalance2() {
        System.out.println("\n>>> testBalance2");


        Tuple2<String, Integer>[] list0 = new Tuple2[]{Tuple.of("a", 11), Tuple.of("a", 12), Tuple.of("a", 13), Tuple.of("a", 14), Tuple.of("a", 15)};
        Tuple2<String, Integer>[] list1 = new Tuple2[]{Tuple.of("b", 26)};
        Tuple2<String, Integer>[] list2 = new Tuple2[]{Tuple.of("c", 57), Tuple.of("c", 58), Tuple.of("c", 59)};
        Tuple2<String, Integer>[] list3 = new Tuple2[]{Tuple.of("d", 30), Tuple.of("d", 31)};
        Tuple2<String, Integer>[] list4 = new Tuple2[]{Tuple.of("e", 62)};
        Tuple2<String, Integer>[] list5 = new Tuple2[]{Tuple.of("f", 83), Tuple.of("f", 84), Tuple.of("f", 85)};
        Tuple2<String, Integer>[] list6 = new Tuple2[]{Tuple.of("g", 71), Tuple.of("g", 72)};
        Tuple2<String, Integer>[] list7 = new Tuple2[]{};
        Tuple2<String, Integer>[] list8 = null;


        List<List<Tuple2<String, Integer>>> balance = CollectionKit.balance(0, list0, list1, list2, list3, list4, list5, list6, list7, list8);
        assertNull(balance);

        List<List<Tuple2<String, Integer>>> balance1 = CollectionKit.balance(3, list0, list1, list2, list3, list4, list5, list6, list7, list8);
        assertEquals("[[(\"a\", 11), (\"a\", 12), (\"a\", 13), (\"a\", 14), (\"a\", 15)], " +
                "[(\"c\", 57), (\"c\", 58), (\"c\", 59), (\"d\", 30), (\"d\", 31), (\"e\", 62)], " +
                "[(\"f\", 83), (\"f\", 84), (\"f\", 85), (\"g\", 71), (\"g\", 72), (\"b\", 26)]]", balance1.toString());

        List<List<Tuple2<String, Integer>>> balance2 = CollectionKit.balance(4, list0, list1, list2, list3, list4, list5, list6, list7, list8);
        assertEquals("[[(\"f\", 83), (\"f\", 84), (\"f\", 85), (\"e\", 62)], [(\"c\", 57), (\"c\", 58), (\"c\", 59), (\"b\", 26)], " +
                "[(\"d\", 30), (\"d\", 31), (\"g\", 71), (\"g\", 72)], [(\"a\", 11), (\"a\", 12), (\"a\", 13), (\"a\", 14), (\"a\", 15)]]", balance2.toString());

        List<List<Tuple2<String, Integer>>> balance3 = CollectionKit.balance(10, list0, list1, list2, list3, list4, list5, list6, list7, list8);
        assertEquals("[[(\"b\", 26)], [(\"e\", 62)], [(\"d\", 30), (\"d\", 31)], [(\"g\", 71), (\"g\", 72)], " +
                "[(\"c\", 57), (\"c\", 58), (\"c\", 59)], [(\"f\", 83), (\"f\", 84), (\"f\", 85)], " +
                "[(\"a\", 11), (\"a\", 12), (\"a\", 13), (\"a\", 14), (\"a\", 15)]]", balance3.toString());

        List<List<Tuple2<String, Integer>>> balance4 = CollectionKit.balance(1, list0, list1, list2, list3, list4, list5, list6, list7, list8);
        assertEquals("[[(\"a\", 11), (\"a\", 12), (\"a\", 13), (\"a\", 14), (\"a\", 15), (\"c\", 57), (\"c\", 58), (\"c\", 59), (\"f\", 83), " +
                "(\"f\", 84), (\"f\", 85), (\"d\", 30), (\"d\", 31), (\"g\", 71), (\"g\", 72), (\"b\", 26), (\"e\", 62)]]", balance4.toString());

    }

    @Test
    public void testBalanceCostTime() {
        System.out.println("\n>>> testBalanceCostTime");

        List<Integer> list0 = Arrays.asList(11, 12, 13, 14, 15);
        List<Integer> list1 = Arrays.asList(26);
        List<Integer> list2 = Arrays.asList(57, 58, 59);
        List<Integer> list3 = Arrays.asList(30, 31);
        List<Integer> list4 = Arrays.asList(62);
        List<Integer> list5 = Arrays.asList(83, 84, 85);
        List<Integer> list6 = Arrays.asList(71, 72);
        List<Integer> list7 = Arrays.asList(91, 92, 93, 94);
        List<Integer> list8 = new ArrayList<>();
        List<Integer> list9 = null;

        List<List<Integer>> allLists = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            allLists.addAll(Arrays.asList(list0, list1, list2, list3, list4, list5, list6, list7, list8, list9));
        }

        long start = System.currentTimeMillis();
        List<List<Integer>> balance = CollectionKit.balance(10000, allLists);
        System.out.println("balance数量：" + balance.size() + " >>> 耗时：" + (System.currentTimeMillis() - start));

    }


    /*
    @Test
    public void testSortForBalance() {
        List<Integer> list0 = Arrays.asList(11, 12, 13, 14, 15);
        List<Integer> list1 = Arrays.asList(26);
        List<Integer> list2 = Arrays.asList(57, 58, 59);
        List<Integer> list3 = Arrays.asList(30, 31);
        List<Integer> list4 = Arrays.asList(62);
        List<Integer> list5 = Arrays.asList(83, 84, 85);
        List<Integer> list6 = Arrays.asList(71, 72);
        List<Integer> list7 = Arrays.asList(91, 92, 93, 94);
        List<Integer> list8 = new ArrayList<>();

        SimpleEntry<Integer, List<Integer>> entry1 = new SimpleEntry<>(1, list8);   // size: 0
        SimpleEntry<Integer, List<Integer>> entry2 = new SimpleEntry<>(2, list1);   // size: 1
        SimpleEntry<Integer, List<Integer>> entry3 = new SimpleEntry<>(3, list2);   // size: 3
        SimpleEntry<Integer, List<Integer>> entry4 = new SimpleEntry<>(4, list7);   // size: 4
        SimpleEntry<Integer, List<Integer>> entry5 = new SimpleEntry<>(5, list0);   // size: 5
        SimpleEntry<Integer, List<Integer>> entry6 = new SimpleEntry<>(6, list6);   // size: 2

        System.out.println(">>>>>> entriesNull <<<<<<");
        List<Entry<Integer, List<Integer>>> entriesNull = null;
        CollectorUtils.fastSortForBalance(entriesNull);
        System.out.println(entriesNull);

        System.out.println(">>>>>> entriesEmpty <<<<<<");
        List<Entry<Integer, List<Integer>>> entriesEmpty = new ArrayList<>();
        CollectorUtils.fastSortForBalance(entriesEmpty);
        System.out.println(entriesEmpty);

        System.out.println(">>>>>> entries1 <<<<<<");
        Map<Integer, List<Integer>> map1 = new HashMap<>();
        map1.put(0, list3);
        List<Entry<Integer, List<Integer>>> entries1 = MapUtils.sortBy(map1, e -> e.getValue().size());
        CollectorUtils.fastSortForBalance(entries1);
        System.out.println("第一次排序：" + entries1);
        entries1.add(0, entry2);
        CollectorUtils.fastSortForBalance(entries1);
        System.out.println("添加：" + entry2 + "   后排序：" + entries1);
        entries1.add(0, entry1);
        CollectorUtils.fastSortForBalance(entries1);
        System.out.println("添加：" + entry1 + "   后排序：" + entries1);
        entries1.add(0, entry4);
        CollectorUtils.fastSortForBalance(entries1);
        System.out.println("添加：" + entry4 + "   后排序：" + entries1);


        System.out.println(">>>>>> entries2 <<<<<<");
        Map<Integer, List<Integer>> map2 = new HashMap<>();
        map2.put(0, list3);
        List<Entry<Integer, List<Integer>>> entries2 = MapUtils.sortBy(map2, e -> e.getValue().size());
        CollectorUtils.fastSortForBalance(entries2);
        System.out.println("第一次排序：" + entries2);
        entries2.add(0, entry3);
        CollectorUtils.fastSortForBalance(entries2);
        System.out.println("添加：" + entry3 + "   后排序：" + entries2);
        entries2.add(0, entry5);
        CollectorUtils.fastSortForBalance(entries2);
        System.out.println("添加：" + entry5 + "   后排序：" + entries2);
        entries2.add(0, entry4);
        CollectorUtils.fastSortForBalance(entries2);
        System.out.println("添加：" + entry4 + "   后排序：" + entries2);
        entries2.add(0, entry2);
        CollectorUtils.fastSortForBalance(entries2);
        System.out.println("添加：" + entry2 + "   后排序：" + entries2);
        entries2.add(0, entry6);
        CollectorUtils.fastSortForBalance(entries2);
        System.out.println("添加：" + entry6 + "   后排序：" + entries2);

    }*/

    @Test
    public void testFindNth() {
        String[] ss = {"a", "ae", "c", "ab", "d", "ae", "af", "af", "d", "g"};  // ["a", "ae", "c", "ab", "d", "ae", "af", "af", "d", "g"]
        List<String> sList = Arrays.asList(ss);         // ["a", "ae", "c", "ab", "d", "ae", "af", "af", "d", "g"]
        Set<String> sSet = new HashSet<>(sList);        // [a, ab, c, ae, d, af, g]
        // ss  --->  e.startsWith("a")  --->  [a, ae, ab, ae, af, af]
        // sSet  --->  e.startsWith("a")  --->  [a, ab, ae, af]

        Tuple2<String, Integer> nth0 = CollectionKit.findNth(ss, null, 2);         // ("c", 2)
        Tuple2<String, Integer> nth1 = CollectionKit.findNth(sList, null, 2);         // ("c", 2)
        String nth2 = CollectionKit.findNth(sSet, null, 2);         // c

        Tuple2<String, Integer> nth3 = CollectionKit.findNth(ss, e -> e.startsWith("a"), 2);         // ("ab", 3)
        Tuple2<String, Integer> nth4 = CollectionKit.findNth(sList, e -> e.startsWith("a"), 2);         // ("ab", 3)
        String nth5 = CollectionKit.findNth(sSet, e -> e.startsWith("a"), 2);         // ae

        Tuple2<String, Integer> nth6 = CollectionKit.findNth(ss, e -> e.startsWith("a"), 20);         // null
        Tuple2<String, Integer> nth7 = CollectionKit.findNth(sList, e -> e.startsWith("a"), 20);         // null
        String nth8 = CollectionKit.findNth(sSet, e -> e.startsWith("a"), 20);         // null

        Tuple2<String, Integer> nth9 = CollectionKit.findNth(ss, null, 20);         // null
        Tuple2<String, Integer> nth10 = CollectionKit.findNth(sList, null, 20);         // null
        String nth11 = CollectionKit.findNth(sSet, null, 20);         // null

        Tuple2<String, Integer> nth12 = CollectionKit.findNth(ss, null, 0);         // ("a", 0)
        Tuple2<String, Integer> nth13 = CollectionKit.findNth(sList, null, 0);         // ("a", 0)
        String nth14 = CollectionKit.findNth(sSet, null, 0);         // a

        Tuple2<String, Integer> nth15 = CollectionKit.findNth(ss, e -> e.startsWith("a"), 0);         // ("a", 0)
        Tuple2<String, Integer> nth16 = CollectionKit.findNth(sList, e -> e.startsWith("a"), 0);         // ("a", 0)
        String nth17 = CollectionKit.findNth(sSet, e -> e.startsWith("a"), 0);         // a

        Tuple2<String, Integer> nth18 = CollectionKit.findNth(ss, null, -1);         // ("g", 9)
        Tuple2<String, Integer> nth19 = CollectionKit.findNth(sList, null, -1);         // ("g", 9)
        String nth20 = CollectionKit.findNth(sSet, null, -1);         // g

        Tuple2<String, Integer> nth21 = CollectionKit.findNth(ss, e -> e.startsWith("a"), -1);         // ("af", 7)
        Tuple2<String, Integer> nth22 = CollectionKit.findNth(sList, e -> e.startsWith("a"), -1);         // ("af", 7)
        String nth23 = CollectionKit.findNth(sSet, e -> e.startsWith("a"), -1);         // af

        Tuple2<String, Integer> nth24 = CollectionKit.findNth(ss, null, -11);         // null
        Tuple2<String, Integer> nth25 = CollectionKit.findNth(sList, null, -10);         // ("a", 0)
        String nth26 = CollectionKit.findNth(sSet, null, -7);         // a

        Tuple2<String, Integer> nth27 = CollectionKit.findNth(ss, e -> e.startsWith("a"), -2);         // ("af", 6)
        Tuple2<String, Integer> nth28 = CollectionKit.findNth(sList, e -> e.startsWith("a"), -2);         // ("af", 6)
        String nth29 = CollectionKit.findNth(sSet, e -> e.startsWith("a"), -2);         // ae

        Tuple2<String, Integer> nth30 = CollectionKit.findNth(ss, e -> e.startsWith("a"), -6);         // ("a", 0)
        Tuple2<String, Integer> nth31 = CollectionKit.findNth(sList, e -> e.startsWith("a"), -7);         // null
        String nth32 = CollectionKit.findNth(sSet, e -> e.startsWith("a"), -4);         // a
        String nth33 = CollectionKit.findNth(sSet, e -> e.startsWith("a"), -5);         // null

        assertEquals(nth0.toString(), "(\"c\", 2)");
        assertEquals(nth1.toString(), "(\"c\", 2)");
        assertEquals(nth2.toString(), "c");
        assertEquals(nth3.toString(), "(\"ab\", 3)");
        assertEquals(nth4.toString(), "(\"ab\", 3)");
        assertEquals(nth5.toString(), "ae");
        assertNull(nth6);
        assertNull(nth7);
        assertNull(nth8);
        assertNull(nth9);
        assertNull(nth10);
        assertNull(nth11);
        assertEquals(nth12.toString(), "(\"a\", 0)");
        assertEquals(nth13.toString(), "(\"a\", 0)");
        assertEquals(nth14.toString(), "a");
        assertEquals(nth15.toString(), "(\"a\", 0)");
        assertEquals(nth16.toString(), "(\"a\", 0)");
        assertEquals(nth17.toString(), "a");
        assertEquals(nth18.toString(), "(\"g\", 9)");
        assertEquals(nth19.toString(), "(\"g\", 9)");
        assertEquals(nth20.toString(), "g");
        assertEquals(nth21.toString(), "(\"af\", 7)");
        assertEquals(nth22.toString(), "(\"af\", 7)");
        assertEquals(nth23.toString(), "af");
        assertNull(nth24);
        assertEquals(nth25.toString(), "(\"a\", 0)");
        assertEquals(nth26.toString(), "a");
        assertEquals(nth27.toString(), "(\"af\", 6)");
        assertEquals(nth28.toString(), "(\"af\", 6)");
        assertEquals(nth29.toString(), "ae");
        assertEquals(nth30.toString(), "(\"a\", 0)");
        assertNull(nth31);
        assertEquals(nth32.toString(), "a");
        assertNull(nth33);

        System.out.println("nth0: " + nth0);
        System.out.println("nth1: " + nth1);
        System.out.println("nth2: " + nth2);
        System.out.println("nth3: " + nth3);
        System.out.println("nth4: " + nth4);
        System.out.println("nth5: " + nth5);
        System.out.println("nth6: " + nth6);
        System.out.println("nth7: " + nth7);
        System.out.println("nth8: " + nth8);
        System.out.println("nth9: " + nth9);
        System.out.println("nth10: " + nth10);
        System.out.println("nth11: " + nth11);
        System.out.println("nth12: " + nth12);
        System.out.println("nth13: " + nth13);
        System.out.println("nth14: " + nth14);
        System.out.println("nth15: " + nth15);
        System.out.println("nth16: " + nth16);
        System.out.println("nth17: " + nth17);
        System.out.println("nth18: " + nth18);
        System.out.println("nth19: " + nth19);
        System.out.println("nth20: " + nth20);
        System.out.println("nth21: " + nth21);
        System.out.println("nth22: " + nth22);
        System.out.println("nth23: " + nth23);
        System.out.println("nth24: " + nth24);
        System.out.println("nth25: " + nth25);
        System.out.println("nth26: " + nth26);
        System.out.println("nth27: " + nth27);
        System.out.println("nth28: " + nth28);
        System.out.println("nth29: " + nth29);
        System.out.println("nth30: " + nth30);
        System.out.println("nth31: " + nth31);
        System.out.println("nth32: " + nth32);
        System.out.println("nth33: " + nth33);
    }

    @Test
    public void testFindRandom() {
        String[] ss = {"a", "ae", "c", "ab", "d", "ae", "af", "af", "d", "g"};  // ["a", "ae", "c", "ab", "d", "ae", "af", "af", "d", "g"]
        List<String> sList = Arrays.asList(ss);         // ["a", "ae", "c", "ab", "d", "ae", "af", "af", "d", "g"]
        Set<String> sSet = new HashSet<>(sList);        // [a, ab, c, ae, d, af, g]
        // ss  --->  e.startsWith("a")  --->  [a, ae, ab, ae, af, af]
        // sSet  --->  e.startsWith("a")  --->  [a, ab, ae, af]

        Tuple2<String, Integer> random0 = CollectionKit.findRandom(ss, null);
        Tuple2<String, Integer> random1 = CollectionKit.findRandom(sList, null);
        String random2 = CollectionKit.findRandom(sSet, null);

        Tuple2<String, Integer> random3 = CollectionKit.findRandom(ss, e -> e.startsWith("a"));
        Tuple2<String, Integer> random4 = CollectionKit.findRandom(sList, e -> e.startsWith("a"));
        String random5 = CollectionKit.findRandom(sSet, e -> e.startsWith("a"));

        System.out.println("random0: " + random0);
        System.out.println("random1: " + random1);
        System.out.println("random2: " + random2);
        System.out.println("random3: " + random3);
        System.out.println("random4: " + random4);
        System.out.println("random5: " + random5);

    }
}
