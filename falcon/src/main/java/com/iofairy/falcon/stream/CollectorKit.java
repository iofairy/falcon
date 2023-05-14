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
package com.iofairy.falcon.stream;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Collector Kit
 *
 * @since 0.4.1
 */
public class CollectorKit {
    public static final Collector.Characteristics[] CH_ALL = {
            Collector.Characteristics.CONCURRENT,
            Collector.Characteristics.UNORDERED,
            Collector.Characteristics.IDENTITY_FINISH
    };
    public static final Collector.Characteristics[] CH_CONCURRENT_UNORDERED = {Collector.Characteristics.CONCURRENT, Collector.Characteristics.UNORDERED};
    public static final Collector.Characteristics[] CH_CONCURRENT_ID = {Collector.Characteristics.CONCURRENT, Collector.Characteristics.IDENTITY_FINISH};
    public static final Collector.Characteristics[] CH_UNORDERED_ID = {Collector.Characteristics.UNORDERED, Collector.Characteristics.IDENTITY_FINISH};
    public static final Collector.Characteristics[] CH_CONCURRENT = {Collector.Characteristics.CONCURRENT};
    public static final Collector.Characteristics[] CH_UNORDERED = {Collector.Characteristics.UNORDERED};
    public static final Collector.Characteristics[] CH_ID = {Collector.Characteristics.IDENTITY_FINISH};
    public static final Collector.Characteristics[] CH_EMPTY = {};

    private CollectorKit() {
    }

    /**
     * 创建一个Map分组收集器，允许key为null
     *
     * @param classifier 分类器
     * @param <T>        Stream流中的元素类型
     * @param <K>        Map的Key的类型
     * @return Map收集器
     */
    public static <T, K> Collector<T, ?, Map<K, List<T>>> groupingByAllowNullKey(Function<? super T, ? extends K> classifier) {
        return Collectors.toMap(classifier, Collections::singletonList,
                (List<T> oldList, List<T> newList) -> {
                    List<T> resultList = new ArrayList<>(oldList.size() + 1);
                    resultList.addAll(oldList);
                    resultList.addAll(newList);
                    return resultList;
                });
    }

    /**
     * 创建一个Map收集器
     *
     * @param mapSupplier          map对象提供
     * @param keyMapper            map key的映射器
     * @param valueMapper          map value的映射器
     * @param isAllowDuplicateKeys 是否允许相同的 key，如果 false，遇到相同的 key，抛出异常；如果 true，遇到相同的 key，则会覆盖
     * @param <T>                  Stream流中的元素类型
     * @param <K>                  Map的Key的类型
     * @param <U>                  Map的Value的类型
     * @param <M>                  Map
     * @return Map收集器
     * @throws IllegalStateException 如果参数{@code isAllowDuplicateKeys}为 true 且转换过程存在相同的key，则抛出此异常
     */
    public static <T, K, U, M extends Map<K, U>> Collector<T, ?, M> toMap(Supplier<M> mapSupplier,
                                                                          Function<? super T, ? extends K> keyMapper,
                                                                          Function<? super T, ? extends U> valueMapper,
                                                                          final boolean isAllowDuplicateKeys) {
        Collector.Characteristics[] characteristics = mapSupplier.get() instanceof ConcurrentMap ? CH_ALL : CH_ID;

        BiConsumer<M, T> accumulator = (map, element) -> {
            K key = keyMapper.apply(element);
            if (!isAllowDuplicateKeys && map.containsKey(key)) throwDuplicateKeyException(key);
            map.put(key, valueMapper.apply(element));
        };

        return Collector.of(mapSupplier, accumulator, (map1, map2) -> {
            map2.forEach((k, v) -> {
                if (!isAllowDuplicateKeys && map1.containsKey(k)) throwDuplicateKeyException(k);
                map1.put(k, v);
            });
            return map1;
        }, characteristics);
    }

    @SuppressWarnings("unchecked")
    public static <T, K, U, M extends Map<K, U>> Collector<T, ?, M> toMap(Function<? super T, ? extends K> keyMapper,
                                                                          Function<? super T, ? extends U> valueMapper) {
        return toMap(() -> (M) new HashMap<K, U>(), keyMapper, valueMapper, false);
    }

    @SuppressWarnings("unchecked")
    public static <T, K, U, M extends Map<K, U>> Collector<T, ?, M> toMap(Function<? super T, ? extends K> keyMapper,
                                                                          Function<? super T, ? extends U> valueMapper,
                                                                          final boolean isAllowDuplicateKeys) {
        return toMap(() -> (M) new HashMap<K, U>(), keyMapper, valueMapper, isAllowDuplicateKeys);
    }

    @SuppressWarnings("unchecked")
    public static <T, K, U, M extends Map<K, U>> Collector<T, ?, M> toLinkedMap(Function<? super T, ? extends K> keyMapper,
                                                                                Function<? super T, ? extends U> valueMapper) {
        return toMap(() -> (M) new LinkedHashMap<K, U>(), keyMapper, valueMapper, false);
    }

    @SuppressWarnings("unchecked")
    public static <T, K, U, M extends Map<K, U>> Collector<T, ?, M> toLinkedMap(Function<? super T, ? extends K> keyMapper,
                                                                                Function<? super T, ? extends U> valueMapper,
                                                                                final boolean isAllowDuplicateKeys) {
        return toMap(() -> (M) new LinkedHashMap<K, U>(), keyMapper, valueMapper, isAllowDuplicateKeys);
    }

    @SuppressWarnings("unchecked")
    public static <T, K, U, M extends Map<K, U>> Collector<T, ?, M> toConcurrentMap(Function<? super T, ? extends K> keyMapper,
                                                                                    Function<? super T, ? extends U> valueMapper) {
        return toMap(() -> (M) new ConcurrentHashMap<K, U>(), keyMapper, valueMapper, false);
    }

    @SuppressWarnings("unchecked")
    public static <T, K, U, M extends Map<K, U>> Collector<T, ?, M> toConcurrentMap(Function<? super T, ? extends K> keyMapper,
                                                                                    Function<? super T, ? extends U> valueMapper,
                                                                                    final boolean isAllowDuplicateKeys) {
        return toMap(() -> (M) new ConcurrentHashMap<K, U>(), keyMapper, valueMapper, isAllowDuplicateKeys);
    }

    private static void throwDuplicateKeyException(Object key) {
        throw new IllegalStateException(String.format("Duplicate key: `%s`", key));
    }

}
