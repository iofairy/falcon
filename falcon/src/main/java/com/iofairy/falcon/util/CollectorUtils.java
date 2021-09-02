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
}
