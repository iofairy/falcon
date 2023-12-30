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
package com.iofairy.falcon.map;

import com.iofairy.top.G;
import com.iofairy.top.O;
import com.iofairy.tuple.Tuple;
import com.iofairy.tuple.Tuple2;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;

/**
 * Map Utils
 *
 * @since 0.4.1
 */
public class MapKit {

    /**
     * A map to two List for Key and Value<br>
     * 将 map 转换成 key和value 的List
     *
     * @param map        map
     * @param removeKeys remove keys
     * @param <K>        key type
     * @param <V>        value type
     * @return Tuple2 of key and value List
     */
    @SafeVarargs
    public static <K, V> Tuple2<List<K>, List<V>> mapToList(Map<K, V> map, K... removeKeys) {
        if (map == null) return Tuple.of(null, null);
        if (!G.isEmpty(removeKeys)) {
            for (K removeKey : removeKeys) {
                map.remove(removeKey);
            }
        }
        List<K> keys = new ArrayList<>();
        List<V> values = new ArrayList<>();
        map.forEach((k, v) -> {
            keys.add(k);
            values.add(v);
        });
        return Tuple.of(keys, values);
    }

    /**
     * A map to List for Key<br>
     * 将 map 转换成 key 的List
     *
     * @param map        map
     * @param removeKeys remove keys
     * @param <K>        key type
     * @param <V>        value type
     * @return key List
     */
    @SafeVarargs
    public static <K, V> List<K> keyToList(Map<K, V> map, K... removeKeys) {
        return mapToList(map, removeKeys)._1;
    }

    /**
     * A map to List for Value<br>
     * 将 map 转换成 value 的List
     *
     * @param map        map
     * @param removeKeys remove keys
     * @param <K>        key type
     * @param <V>        value type
     * @return value List
     */
    @SafeVarargs
    public static <K, V> List<V> valueToList(Map<K, V> map, K... removeKeys) {
        return mapToList(map, removeKeys)._2;
    }

    /**
     * A map to two array for Key and Value<br>
     * 将 map 转换成 key和value 的数组
     *
     * @param map        map
     * @param kClass     key class
     * @param vClass     value class
     * @param removeKeys remove keys
     * @param <K>        key type
     * @param <V>        value type
     * @return Tuple2 of key and value array
     */
    @SafeVarargs
    public static <K, V> Tuple2<K[], V[]> mapToArray(Map<K, V> map, Class<K> kClass, Class<V> vClass, K... removeKeys) {
        Tuple2<List<K>, List<V>> tuple2 = mapToList(map, removeKeys);
        K[] ks = tuple2._1 == null ? null : tuple2._1.toArray(O.arrayO(kClass));
        V[] vs = tuple2._2 == null ? null : tuple2._2.toArray(O.arrayO(vClass));
        return Tuple.of(ks, vs);
    }

    /**
     * A map to array for Key<br>
     * 将 map 转换成 key 的数组
     *
     * @param map        map
     * @param kClass     key class
     * @param removeKeys remove keys
     * @param <K>        key type
     * @param <V>        value type
     * @return key array
     */
    @SafeVarargs
    public static <K, V> K[] keyToArray(Map<K, V> map, Class<K> kClass, K... removeKeys) {
        List<K> ks = keyToList(map, removeKeys);
        return ks == null ? null : ks.toArray(O.arrayO(kClass));
    }

    /**
     * A map to array for Value<br>
     * 将 map 转换成 value 的数组
     *
     * @param map        map
     * @param vClass     value class
     * @param removeKeys remove keys
     * @param <K>        key type
     * @param <V>        value type
     * @return value array
     */
    @SafeVarargs
    public static <K, V> V[] valueToArray(Map<K, V> map, Class<V> vClass, K... removeKeys) {
        List<V> vs = valueToList(map, removeKeys);
        return vs == null ? null : vs.toArray(O.arrayO(vClass));
    }

    /**
     * To sort map according to the provided {@code Comparator}. <br>
     * 对 map 的键或值进行排序
     *
     * @param map        map
     * @param comparator comparator
     * @param <K>        key type
     * @param <V>        value type
     * @return list of {@link Entry}
     */
    public static <K, V> List<Entry<K, V>> sortBy(Map<K, V> map, Comparator<? super Entry<K, V>> comparator) {
        return map.entrySet().stream().sorted(comparator).collect(Collectors.toList());
    }

    /**
     * To sort map according to the provided {@code Function}. <br>
     * 对 map 的键或值进行排序
     *
     * @param map          map
     * @param keyExtractor keyExtractor
     * @param <K>          key type
     * @param <V>          value type
     * @param <U>          return type of {@code Function}
     * @return list of {@link Entry}
     */
    public static <K, V, U extends Comparable<? super U>> List<Entry<K, V>> sortBy(Map<K, V> map, Function<? super Entry<K, V>, ? extends U> keyExtractor) {
        return sortBy(map, Comparator.comparing(keyExtractor));
    }

    /**
     * To sort map according to the provided {@code ToIntFunction}. <br>
     * 对 map 的键或值进行排序
     *
     * @param map          map
     * @param keyExtractor keyExtractor
     * @param <K>          key type
     * @param <V>          value type
     * @return list of {@link Entry}
     */
    public static <K, V> List<Entry<K, V>> sortByInt(Map<K, V> map, ToIntFunction<? super Entry<K, V>> keyExtractor) {
        return sortBy(map, Comparator.comparingInt(keyExtractor));
    }

    /**
     * To sort map according to the provided {@code ToLongFunction}. <br>
     * 对 map 的键或值进行排序
     *
     * @param map          map
     * @param keyExtractor keyExtractor
     * @param <K>          key type
     * @param <V>          value type
     * @return list of {@link Entry}
     */
    public static <K, V> List<Entry<K, V>> sortByLong(Map<K, V> map, ToLongFunction<? super Entry<K, V>> keyExtractor) {
        return sortBy(map, Comparator.comparingLong(keyExtractor));
    }

    /**
     * To sort map according to the provided {@code ToDoubleFunction}. <br>
     * 对 map 的键或值进行排序
     *
     * @param map          map
     * @param keyExtractor keyExtractor
     * @param <K>          key type
     * @param <V>          value type
     * @return list of {@link Entry}
     */
    public static <K, V> List<Entry<K, V>> sortByDouble(Map<K, V> map, ToDoubleFunction<? super Entry<K, V>> keyExtractor) {
        return sortBy(map, Comparator.comparingDouble(keyExtractor));
    }

    /**
     * To sort map according to the provided {@code Comparator}. <br>
     * 对 map 的键或值进行排序
     *
     * @param entries    {@link Entry} list
     * @param comparator comparator
     * @param <K>        key type
     * @param <V>        value type
     * @return list of {@link Entry}
     */
    public static <K, V> List<Entry<K, V>> sortBy(List<Entry<K, V>> entries, Comparator<? super Entry<K, V>> comparator) {
        return entries.stream().sorted(comparator).collect(Collectors.toList());
    }

    /**
     * To sort map according to the provided {@code Function}. <br>
     * 对 map 的键或值进行排序
     *
     * @param entries      {@link Entry} list
     * @param keyExtractor keyExtractor
     * @param <K>          key type
     * @param <V>          value type
     * @param <U>          return type of {@code Function}
     * @return list of {@link Entry}
     */
    public static <K, V, U extends Comparable<? super U>> List<Entry<K, V>> sortBy(List<Entry<K, V>> entries, Function<? super Entry<K, V>, ? extends U> keyExtractor) {
        return sortBy(entries, Comparator.comparing(keyExtractor));
    }

    /**
     * To sort map according to the provided {@code ToIntFunction}. <br>
     * 对 map 的键或值进行排序
     *
     * @param entries      {@link Entry} list
     * @param keyExtractor keyExtractor
     * @param <K>          key type
     * @param <V>          value type
     * @return list of {@link Entry}
     */
    public static <K, V> List<Entry<K, V>> sortByInt(List<Entry<K, V>> entries, ToIntFunction<? super Entry<K, V>> keyExtractor) {
        return sortBy(entries, Comparator.comparingInt(keyExtractor));
    }

    /**
     * To sort map according to the provided {@code ToLongFunction}. <br>
     * 对 map 的键或值进行排序
     *
     * @param entries      {@link Entry} list
     * @param keyExtractor keyExtractor
     * @param <K>          key type
     * @param <V>          value type
     * @return list of {@link Entry}
     */
    public static <K, V> List<Entry<K, V>> sortByLong(List<Entry<K, V>> entries, ToLongFunction<? super Entry<K, V>> keyExtractor) {
        return sortBy(entries, Comparator.comparingLong(keyExtractor));
    }

    /**
     * To sort map according to the provided {@code ToDoubleFunction}. <br>
     * 对 map 的键或值进行排序
     *
     * @param entries      {@link Entry} list
     * @param keyExtractor keyExtractor
     * @param <K>          key type
     * @param <V>          value type
     * @return list of {@link Entry}
     */
    public static <K, V> List<Entry<K, V>> sortByDouble(List<Entry<K, V>> entries, ToDoubleFunction<? super Entry<K, V>> keyExtractor) {
        return sortBy(entries, Comparator.comparingDouble(keyExtractor));
    }

    /**
     * 从数组中获取 {@code Map<数组中元素, 元素位置>} 的map，以及获取 {@code Map<元素位置, 数组中元素>} 的map
     *
     * @param strings 数组
     * @return tuple中存储：{@code (Map<数组中元素, 元素位置>, Map<元素位置, 数组中元素>)}
     * @since 0.4.15
     */
    public static Tuple2<Map<String, Integer>, Map<Integer, String>> mapFromArray(CharSequence... strings) {
        return mapFromList(true, G.isEmpty(strings) ? null : Arrays.asList(strings));
    }

    /**
     * 从数组中获取 {@code Map<数组中元素, 元素位置>} 的map，以及获取 {@code Map<元素位置, 数组中元素>} 的map
     *
     * @param isCaseSensitive map是否大小写敏感
     * @param strings         数组
     * @return tuple中存储：{@code (Map<数组中元素, 元素位置>, Map<元素位置, 数组中元素>)}
     * @since 0.4.15
     */
    public static Tuple2<Map<String, Integer>, Map<Integer, String>> mapFromArray(boolean isCaseSensitive, CharSequence... strings) {
        return mapFromList(isCaseSensitive, G.isEmpty(strings) ? null : Arrays.asList(strings));
    }

    /**
     * 从list中获取 {@code Map<list中元素, 元素位置>} 的map，以及获取 {@code Map<元素位置, list中元素>} 的map
     *
     * @param strings list
     * @return tuple中存储：{@code (Map<list中元素, 元素位置>, Map<元素位置, list中元素>)}
     * @since 0.4.15
     */
    public static Tuple2<Map<String, Integer>, Map<Integer, String>> mapFromList(List<? extends CharSequence> strings) {
        return mapFromList(true, strings);
    }

    /**
     * 从list中获取 {@code Map<list中元素, 元素位置>} 的map，以及获取 {@code Map<元素位置, list中元素>} 的map
     *
     * @param isCaseSensitive map是否大小写敏感
     * @param strings         list
     * @return tuple中存储：{@code (Map<list中元素, 元素位置>, Map<元素位置, list中元素>)}
     * @since 0.4.15
     */
    public static Tuple2<Map<String, Integer>, Map<Integer, String>> mapFromList(boolean isCaseSensitive, List<? extends CharSequence> strings) {
        Map<String, Integer> stringIndexMap = new HashMap<>();
        Map<Integer, String> indexStringMap = new HashMap<>();
        if (G.isEmpty(strings)) return Tuple.of(stringIndexMap, indexStringMap);

        for (int i = 0; i < strings.size(); i++) {
            CharSequence charSequence = strings.get(i);
            String str = charSequence == null ? null : charSequence.toString();
            str = isCaseSensitive ? str : str.toLowerCase();
            stringIndexMap.put(str, i);
            indexStringMap.put(i, str);
        }
        return Tuple.of(stringIndexMap, indexStringMap);
    }

}
