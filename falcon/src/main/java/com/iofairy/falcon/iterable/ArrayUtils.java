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
package com.iofairy.falcon.iterable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Array Utils <br>
 * 数组工具类
 *
 * @since 0.2.0
 * @deprecated Since falcon version 0.3.5, replaced by {@link com.iofairy.falcon.iterable.ArrayKit}
 */
@Deprecated
public class ArrayUtils {

    /**
     * char[] to Character List.
     *
     * @param chars char[]
     * @return Character List, return {@code null} when {@code chars} is {@code null}
     * @since 0.2.0
     */
    public static List<Character> toCharList(char[] chars) {
        if (chars == null) return null;
        List<Character> cs = new ArrayList<>();
        for (char c : chars) {
            cs.add(c);
        }
        return cs;
    }

    /**
     * String to Character List.
     *
     * @param str String
     * @return Character List, return {@code null} when {@code str} is {@code null}
     * @since 0.2.0
     */
    public static List<Character> toCharList(String str) {
        if (str == null) return null;
        List<Character> cs = new ArrayList<>();
        for (char c : str.toCharArray()) {
            cs.add(c);
        }
        return cs;
    }

    /**
     * Character Collection to String with {@link ToArrayMode}
     *
     * @param toArrayMode toArrayMode. 该值如果为null，则按 {@link ToArrayMode#IGNORE_NULL} 处理。
     * @param chars       Character Collection
     * @return String, return {@code null} when {@code chars} is {@code null}
     */
    public static String charsToString(ToArrayMode toArrayMode, Collection<Character> chars) {
        char[] cs = PrimitiveArrays.toChars(toArrayMode, chars);
        if (cs == null) return null;
        return new String(cs);
    }

    /**
     * Character Collection to String
     *
     * @param valueForNull 使用此值来替代 {@code null}
     * @param chars        Character Collection
     * @return String, return {@code null} when {@code chars} is {@code null}
     */
    public static String charsToString(char valueForNull, Collection<Character> chars) {
        char[] cs = PrimitiveArrays.toChars(valueForNull, chars);
        if (cs == null) return null;
        return new String(cs);
    }

    /**
     * Character Array to String with {@link ToArrayMode}
     *
     * @param toArrayMode toArrayMode. 该值如果为null，则按 {@link ToArrayMode#IGNORE_NULL} 处理。
     * @param chars       Character Array
     * @return String, return {@code null} when {@code chars} is {@code null}
     */
    public static String charsToString(ToArrayMode toArrayMode, Character... chars) {
        char[] cs = PrimitiveArrays.toChars(toArrayMode, chars);
        if (cs == null) return null;
        return new String(cs);
    }

    /**
     * Character Array to String
     *
     * @param valueForNull 使用此值来替代 {@code null}
     * @param chars        Character Array
     * @return String, return {@code null} when {@code chars} is {@code null}
     */
    public static String charsToString(char valueForNull, Character... chars) {
        char[] cs = PrimitiveArrays.toChars(valueForNull, chars);
        if (cs == null) return null;
        return new String(cs);
    }

}
