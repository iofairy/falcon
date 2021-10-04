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

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * Map Utils
 * @since 0.0.2
 */
public class MapUtils {

    /**
     * To sort map according to the provided {@code Comparator}. <br>
     * 对 map 的键或值进行排序
     * @param map map
     * @param comparator comparator
     * @param <K> key type
     * @param <V> value type
     * @return list of {@link Entry}
     */
    public static <K, V> List<Entry<K, V>> sortBy(Map<K, V> map, Comparator<? super Entry<K, V>> comparator) {
        return map.entrySet().stream().sorted(comparator).collect(Collectors.toList());
    }

    /**
     * To sort map according to the provided {@code Function}. <br>
     * 对 map 的键或值进行排序
     * @param map map
     * @param keyExtractor keyExtractor
     * @param <K> key type
     * @param <V> value type
     * @param <U> return type of {@code Function}
     * @return list of {@link Entry}
     */
    public static <K, V, U extends Comparable<? super U>> List<Entry<K, V>> sortBy(Map<K, V> map, Function<? super Entry<K, V>, ? extends U> keyExtractor) {
        return sortBy(map, Comparator.comparing(keyExtractor));
    }

    /**
     * To sort map according to the provided {@code ToIntFunction}. <br>
     * 对 map 的键或值进行排序
     * @param map map
     * @param keyExtractor keyExtractor
     * @param <K> key type
     * @param <V> value type
     * @return list of {@link Entry}
     */
    public static <K, V> List<Entry<K, V>> sortByInt(Map<K, V> map, ToIntFunction<? super Entry<K, V>> keyExtractor) {
        return sortBy(map, Comparator.comparingInt(keyExtractor));
    }

    /**
     * To sort map according to the provided {@code ToLongFunction}. <br>
     * 对 map 的键或值进行排序
     * @param map map
     * @param keyExtractor keyExtractor
     * @param <K> key type
     * @param <V> value type
     * @return list of {@link Entry}
     */
    public static <K, V> List<Entry<K, V>> sortByLong(Map<K, V> map, ToLongFunction<? super Entry<K, V>> keyExtractor) {
        return sortBy(map, Comparator.comparingLong(keyExtractor));
    }

    /**
     * To sort map according to the provided {@code ToDoubleFunction}. <br>
     * 对 map 的键或值进行排序
     * @param map map
     * @param keyExtractor keyExtractor
     * @param <K> key type
     * @param <V> value type
     * @return list of {@link Entry}
     */
    public static <K, V> List<Entry<K, V>> sortByDouble(Map<K, V> map, ToDoubleFunction<? super Entry<K, V>> keyExtractor) {
        return sortBy(map, Comparator.comparingDouble(keyExtractor));
    }

    /**
     * To sort map according to the provided {@code Comparator}. <br>
     * 对 map 的键或值进行排序
     * @param entries {@link Entry} list
     * @param comparator comparator
     * @param <K> key type
     * @param <V> value type
     * @return list of {@link Entry}
     */
    public static <K, V> List<Entry<K, V>> sortBy(List<Entry<K, V>> entries, Comparator<? super Entry<K, V>> comparator) {
        return entries.stream().sorted(comparator).collect(Collectors.toList());
    }

    /**
     * To sort map according to the provided {@code Function}. <br>
     * 对 map 的键或值进行排序
     * @param entries {@link Entry} list
     * @param keyExtractor keyExtractor
     * @param <K> key type
     * @param <V> value type
     * @param <U> return type of {@code Function}
     * @return list of {@link Entry}
     */
    public static <K, V, U extends Comparable<? super U>> List<Entry<K, V>> sortBy(List<Entry<K, V>> entries, Function<? super Entry<K, V>, ? extends U> keyExtractor) {
        return sortBy(entries, Comparator.comparing(keyExtractor));
    }

    /**
     * To sort map according to the provided {@code ToIntFunction}. <br>
     * 对 map 的键或值进行排序
     * @param entries {@link Entry} list
     * @param keyExtractor keyExtractor
     * @param <K> key type
     * @param <V> value type
     * @return list of {@link Entry}
     */
    public static <K, V> List<Entry<K, V>> sortByInt(List<Entry<K, V>> entries, ToIntFunction<? super Entry<K, V>> keyExtractor) {
        return sortBy(entries, Comparator.comparingInt(keyExtractor));
    }

    /**
     * To sort map according to the provided {@code ToLongFunction}. <br>
     * 对 map 的键或值进行排序
     * @param entries {@link Entry} list
     * @param keyExtractor keyExtractor
     * @param <K> key type
     * @param <V> value type
     * @return list of {@link Entry}
     */
    public static <K, V> List<Entry<K, V>> sortByLong(List<Entry<K, V>> entries, ToLongFunction<? super Entry<K, V>> keyExtractor) {
        return sortBy(entries, Comparator.comparingLong(keyExtractor));
    }

    /**
     * To sort map according to the provided {@code ToDoubleFunction}. <br>
     * 对 map 的键或值进行排序
     * @param entries {@link Entry} list
     * @param keyExtractor keyExtractor
     * @param <K> key type
     * @param <V> value type
     * @return list of {@link Entry}
     */
    public static <K, V> List<Entry<K, V>> sortByDouble(List<Entry<K, V>> entries, ToDoubleFunction<? super Entry<K, V>> keyExtractor) {
        return sortBy(entries, Comparator.comparingDouble(keyExtractor));
    }

}
