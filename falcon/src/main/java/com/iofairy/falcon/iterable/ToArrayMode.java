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

/**
 * Mode for handling {@code null} value when convert Collection or Array to primitive array
 *
 * @since 0.2.0
 */
public enum ToArrayMode {
    /**
     * Ignore {@code null} value<br>
     * 如果值为 {@code null}，则忽略不作处理
     */
    IGNORE_NULL,
    /**
     * Throws {@link  NullPointerException} when contains {@code null}<br>
     * 如果集合或数组中包含 {@code null} 值，将会抛出 {@link  NullPointerException}
     */
    THROW_WHEN_NULL,
    /**
     * Use <b>default value</b> when value is {@code null}<br>
     * 使用默认值来替代 {@code null} 值<br>
     * ------------------------------<br>
     * <p>Default value for null value:
     * <table border="1" summary="Default value for null value">
     * <tr>
     *   <th>Wrapper Class</th>
     *   <th>Primitive Type</th>
     *   <th>Default value for null value</th>
     * </tr>
     * <tr>
     *   <td>Boolean</td>
     *   <td>boolean</td>
     *   <td>false</td>
     * </tr>
     * <tr>
     *   <td>Byte</td>
     *   <td>byte</td>
     *   <td>0</td>
     * </tr>
     * <tr>
     *   <td>Short</td>
     *   <td>short</td>
     *   <td>0</td>
     * </tr>
     * <tr>
     *   <td>Character</td>
     *   <td>char</td>
     *   <td>'\u0000' (NUL)</td>
     * </tr>
     * <tr>
     *   <td>Integer</td>
     *   <td>int</td>
     *   <td>0</td>
     * </tr>
     * <tr>
     *   <td>Float</td>
     *   <td>float</td>
     *   <td>0.0f</td>
     * </tr>
     * <tr>
     *   <td>Double</td>
     *   <td>double</td>
     *   <td>0.0d</td>
     * </tr>
     * <tr>
     *   <td>Long</td>
     *   <td>long</td>
     *   <td>0L</td>
     * </tr>
     * </table>
     */
    DEFAULT_VALUE
}
