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
package com.iofairy.falcon.util;

import com.iofairy.top.G;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Collection Utils
 * @since 0.0.1
 */
public class CollectorUtils {

    /**
     * Divide array to multi arrays by per batch size. <br>
     * 限定每个数组存放的最大元素个数，将一个数组分成多个数组，并存放在List中。<br>
     * <b>Examples:</b>
     * <pre>
     * Integer[] intArr = {0, 1, 2, 3, 4, 5, 6, 7};
     * List&lt;Integer[]&gt; divided = divide(intArr, 3);
     * </pre>
     * <b>divided result:</b>
     * <pre>
     * [[0, 1, 2], [3, 4, 5], [6, 7]]
     * </pre>
     *
     * @param arr array
     * @param perBatchSize per batch size
     * @param <T> type of array
     * @return divided multi arrays
     */
    public static <T> List<T[]> divide(T[] arr, int perBatchSize) {
        List<T[]> las = new ArrayList<>();
        if (!G.isEmpty(arr)) {
            if (perBatchSize <= 0) {
                las.add(arr);
                return las;
            }

            int size = arr.length;
            for (int fromIndex = 0; fromIndex < size; fromIndex += perBatchSize) {
                int toIndex = Math.min(fromIndex + perBatchSize, size);
                las.add(Arrays.copyOfRange(arr, fromIndex, toIndex));
            }
        }
        return las;
    }

    /**
     * Divide list to multi lists by per batch size. <br>
     * 限定每个List存放的最大元素个数，将一个List分成多个List，并存放在List中。<br>
     * <b>Examples:</b>
     * <pre>
     * List&lt;String&gt; strList = Arrays.asList("a", "b", "c", "d", "e");
     * List&lt;List&lt;String&gt;&gt; divided = divide(strList, 2);
     * </pre>
     * <b>divided result:</b>
     * <pre>
     * [[a, b], [c, d], [e]]
     * </pre>
     *
     * @param list list
     * @param perBatchSize per batch size
     * @param <T> type of list
     * @return divided multi lists
     */
    public static <T> List<List<T>> divide(List<T> list, int perBatchSize) {
        List<List<T>> lls = new ArrayList<>();
        if (!G.isEmpty(list)) {
            if (perBatchSize <= 0) {
                lls.add(list);
                return lls;
            }

            int size = list.size();
            for (int fromIndex = 0; fromIndex < size; fromIndex += perBatchSize) {
                int toIndex = Math.min(fromIndex + perBatchSize, size);
                lls.add(list.subList(fromIndex, toIndex));
            }
        }
        return lls;
    }

    /**
     * Divide set to multi sets by per batch size. <br>
     * 限定每个Set存放的最大元素个数，将一个Set分成多个Set，并存放在List中。<br>
     * <b>Examples:</b>
     * <pre>
     * Set&lt;Integer&gt; intSet = new HashSet&lt;&gt;();
     * intSet.add(1);
     * intSet.add(2);
     * intSet.add(3);
     * List&lt;Set&lt;Integer&gt;&gt; divided = divide(intSet, 2);
     * </pre>
     * <b>divided result:</b>
     * <pre>
     * [[1, 2], [3]]
     * </pre>
     *
     * @param set set
     * @param perBatchSize per batch size
     * @param <T> type of set
     * @return divided multi sets
     */
    public static <T> List<Set<T>> divide(Set<T> set, int perBatchSize) {
        List<Set<T>> lss = new ArrayList<>();
        if (!G.isEmpty(set)) {
            if (perBatchSize <= 0) {
                lss.add(set);
                return lss;
            }

            ArrayList<T> ts = new ArrayList<>(set);
            List<List<T>> ll = divide(ts, perBatchSize);
            ll.forEach(e -> lss.add(new HashSet<>(e)));
        }
        return lss;
    }

    /**
     * Divide map to multi maps by per batch size. <br>
     * 限定每个Map存放的最大Key的个数，将一个Map分成多个Map，并存放在List中。<br>
     * <b>Examples:</b>
     * <pre>
     * Map&lt;String, Integer&gt; map = new HashMap&lt;&gt;();
     * map.put("a", 1);
     * map.put("b", 2);
     * map.put("c", 3);
     *
     * List&lt;Map&lt;String, Integer&gt;&gt; divided = divide(map, 2);
     * </pre>
     * <b>divided result:</b>
     * <pre>
     * [{a=1, b=2}, {c=3}]
     * </pre>
     *
     * @param map map
     * @param perBatchSize per batch size
     * @param <K> type of key
     * @param <V> type of value
     * @return divided multi maps
     */
    public static <K, V> List<Map<K, V>> divide(Map<K, V> map, int perBatchSize) {
        List<Map<K, V>> lms = new ArrayList<>();
        if (!G.isEmpty(map)) {
            if (perBatchSize <= 0) {
                lms.add(map);
                return lms;
            }

            Set<K> ks = map.keySet();
            List<Set<K>> lss = divide(ks, perBatchSize);
            lss.forEach(s -> lms.add(s.stream().collect(Collectors.toMap(e -> e, map::get))));
        }
        return lms;
    }

    /**
     * 将多个 {@code List} 中的元素尽可能均匀分配在指定数量（partitions）的 {@code List} 中。
     * <b>但原来在同一个 {@code List} 中的元素，分配以后，依然在同一个 {@code List} 中。</b>
     * <b>Examples:</b>
     * <pre>
     * List&lt;Integer&gt; list0 = Arrays.asList(11, 12, 13, 14, 15);
     * List&lt;Integer&gt; list1 = Arrays.asList(26);
     * List&lt;Integer&gt; list2 = Arrays.asList(57, 58, 59);
     * List&lt;Integer&gt; list3 = Arrays.asList(30, 31);
     * List&lt;Integer&gt; list4 = Arrays.asList(83, 84, 85);
     * List&lt;Integer&gt; list5 = Arrays.asList(71, 72);
     *
     * List&lt;List&lt;Integer&gt;&gt; lists = Arrays.asList(list0, list1, list2, list3, list4, list5);
     *
     * List&lt;List&lt;Integer&gt;&gt; balance1 = CollectorUtils.balance(3, lists);
     * //-- balance1: [[57, 58, 59, 30, 31], [11, 12, 13, 14, 15], [83, 84, 85, 71, 72, 26]]
     *
     * List&lt;List&lt;Integer&gt;&gt; balance2 = CollectorUtils.balance(4, lists);
     * //-- balance2: [[83, 84, 85], [57, 58, 59, 26], [30, 31, 71, 72], [11, 12, 13, 14, 15]]
     * </pre>
     * @param partitions 分配后 {@code List} 的数量
     * @param listList 待分配的 {@code List} 列表
     * @param <E> 元素类型
     * @return 分配后 {@code List} 列表
     * @since 0.0.2
     */
    public static <E> List<List<E>> balance(int partitions, List<List<E>> listList) {
        if (partitions <= 0 || G.isEmpty(listList)) return null;
        // transform to `ArrayList` that is mutable list.
        List<List<E>> sortedLists = listList.stream().filter(l -> !G.isEmpty(l)).map(ArrayList::new)
                .sorted(Comparator.comparingInt(list -> -list.size())).collect(Collectors.toList());

        if (G.isEmpty(sortedLists)) return null;

        int min = Math.min(partitions, sortedLists.size());
        Map<Integer, List<E>> map = new HashMap<>();
        for (int i = 0; i < min; i++) {
            map.put(i, sortedLists.remove(0));
        }
        // sort map by value.size. 按 map 中的value的size 从小到大排序
        List<Map.Entry<Integer, List<E>>> entries = MapUtils.sortBy(map, kv -> kv.getValue().size());
        for (List<E> list : sortedLists) {
            entries.get(0).getValue().addAll(list);
//            entries = MapUtils.sortBy(entries, kv -> kv.getValue().size());   // 排序效率太低
            fastSortForBalance(entries);    // 针对 balance 的排序算法，效率非常高
        }

        return entries.stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

    /**
     * 将多个 {@code List} 中的元素尽可能均匀分配在指定数量（partitions）的 {@code List} 中。
     * <b>但原来在同一个 {@code List} 中的元素，分配以后，依然在同一个 {@code List} 中。</b>
     * @param partitions 分配后 {@code List} 的数量
     * @param listArray 待分配的 {@code List} 数组
     * @param <E> 元素类型
     * @return 分配后 {@code List} 列表
     * @see #balance(int, List)
     * @since 0.0.2
     */
    @SafeVarargs
    public static <E> List<List<E>> balance(int partitions, List<E>... listArray) {
        if (partitions <= 0 || G.isEmpty(listArray)) return null;
        return balance(partitions, Arrays.asList(listArray));
    }

    /**
     * 将多个数组中的元素尽可能均匀分配在指定数量（partitions）的 {@code List} 中。
     * <b>但原来在同一个数组中的元素，分配以后，会在同一个 {@code List} 中。</b>
     * @param partitions 分配后 {@code List} 的数量
     * @param arrays 待分配的数组
     * @param <E> 元素类型
     * @return 分配后 {@code List} 列表
     * @see #balance(int, List)
     * @since 0.0.2
     */
    @SafeVarargs
    public static <E> List<List<E>> balance(int partitions, E[]... arrays) {
        if (partitions <= 0 || G.isEmpty(arrays)) return null;
        List<List<E>> lists = Arrays.stream(arrays).filter(Objects::nonNull).map(Arrays::asList).collect(Collectors.toList());
        return balance(partitions, lists);
    }

    /**
     * Fast Sort For {@link #balance(int, List)}<br>
     * 针对 {@link #balance(int, List)} 方法的快速排序
     * @param sortedEntries sortedEntries
     * @param <E> elements type in List
     * @see #balance(int, List)
     * @since 0.0.2
     */
    private static <E> void fastSortForBalance(List<Map.Entry<Integer, List<E>>> sortedEntries) {
        if (G.isEmpty(sortedEntries) || sortedEntries.size() <= 1) return;

        int entriesSize = sortedEntries.size();

        Map.Entry<Integer, List<E>> firstEntry = sortedEntries.get(0);
        List<E> firstValue = firstEntry.getValue();
        int firstValueSize = firstValue.size();
        int preGtIndex = 0;
        for (int i = 1; i < entriesSize; i++) {
            if (firstValueSize > sortedEntries.get(i).getValue().size()) {
                preGtIndex = i;
            }else{
                break;
            }
        }

        if (preGtIndex != 0) {
            sortedEntries.add(preGtIndex + 1, firstEntry);
            sortedEntries.remove(0);
        }
    }

}
