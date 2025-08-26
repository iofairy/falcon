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

import com.iofairy.range.Range;

import java.util.HashMap;

/**
 * The <b>comparable Map</b> class is used when the actual type parameters of
 * the {@link Range} class cannot be determined during <b>deserialization</b>. <br>
 * 这是一个<b>可比较的Map类</b>，此类在反序列化 {@link Range} 对象且无法确定{@link Range}类的类型参数的实际类型时使用
 *
 * @param <K> key
 * @param <V> value
 * @since 0.6.0
 */
public class ComparableMap<K, V> extends HashMap<K, V> implements Comparable<ComparableMap<K, V>> {

    @Override
    public int compareTo(ComparableMap<K, V> o) {
        return Integer.compare(size(), o.size());
    }

}