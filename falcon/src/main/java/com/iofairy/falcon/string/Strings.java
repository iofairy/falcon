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
package com.iofairy.falcon.string;

import java.util.ArrayList;
import java.util.List;

/**
 * String utils
 *
 * @since 0.1.2
 */
public class Strings {

    /**
     * Character List to char[], {@code null} Character will be ignored.
     *
     * @param chars Character List
     * @return char[], return {@code null} when {@code chars} is {@code null}
     */
    public static char[] toCharArray(List<Character> chars) {
        if (chars == null) return null;
        if (chars.size() == 0) return new char[0];
        StringBuilder builder = new StringBuilder();
        for (Character c : chars) {
            if (c != null) {
                builder.append(c);
            }
        }
        return builder.toString().toCharArray();
    }

    /**
     * Character Array to char[], {@code null} Character will be ignored.
     *
     * @param chars Character Array
     * @return char[], return {@code null} when {@code chars} is {@code null}
     */
    public static char[] toCharArray(Character... chars) {
        if (chars == null) return null;
        if (chars.length == 0) return new char[0];
        StringBuilder builder = new StringBuilder();
        for (Character c : chars) {
            if (c != null) {
                builder.append(c);
            }
        }
        return builder.toString().toCharArray();
    }

    /**
     * Character List to String, {@code null} Character will be ignored.
     *
     * @param chars Character List
     * @return String, return {@code null} when {@code chars} is {@code null}
     */
    public static String charsToString(List<Character> chars) {
        if (chars == null) return null;
        if (chars.size() == 0) return "";
        StringBuilder builder = new StringBuilder();
        for (Character c : chars) {
            if (c != null) {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    /**
     * Character Array to String, {@code null} Character will be ignored.
     *
     * @param chars Character Array
     * @return String, return {@code null} when {@code chars} is {@code null}
     */
    public static String charsToString(Character... chars) {
        if (chars == null) return null;
        if (chars.length == 0) return "";
        StringBuilder builder = new StringBuilder();
        for (Character c : chars) {
            if (c != null) {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    /**
     * char[] to Character List.
     *
     * @param chars char[]
     * @return Character List, return {@code null} when {@code chars} is {@code null}
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
     */
    public static List<Character> toCharList(String str) {
        if (str == null) return null;
        List<Character> cs = new ArrayList<>();
        for (char c : str.toCharArray()) {
            cs.add(c);
        }
        return cs;
    }
}
