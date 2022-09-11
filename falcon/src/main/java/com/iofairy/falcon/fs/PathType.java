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
package com.iofairy.falcon.fs;

/**
 * 文件路径的类型
 *
 * @since 0.3.5
 */
public enum PathType {
    /**
     * Path for Unix-like systems. it will use {@code '/'} as files separator. <br>
     * Unix-like下的路径。会使用 {@code '/'} 作为文件之间的分隔符。
     */
    UNIX_LIKE,
    /**
     * Path for Windows systems. it will use {@code '\'} as files separator, and will transform all {@code '/'} to {@code '\'}. <br>
     * Windows下的路径。且会使用 {@code '\'} 作为文件之间的分隔符，会将所有 {@code '/'} 转换成 {@code '\'}。
     */
    WIN,
    /**
     * Path for Windows systems. it will use {@code '/'} as files separator, and will transform all {@code '\'} to {@code '/'}. <br>
     * Windows下的路径。且会使用 {@code '/'} 作为文件之间的分隔符，会将所有 {@code '\'} 转换成 {@code '/'}。
     */
    WIN_SLASH,
    /**
     * The system-dependent for concat paths. <br>
     * On Unix-like systems, it will choose {@link #UNIX_LIKE}; <br>
     * On Windows systems, it will choose {@link #WIN_SLASH}. <br>
     * 根据系统自动选择路径类型：在Unix-like系统，会选择 {@link #UNIX_LIKE}；在Windows系统，则会选择 {@link #WIN_SLASH}。
     */
    AUTO,
    /**
     * The system-dependent for concat paths. <br>
     * On Unix-like systems, it will choose {@link #UNIX_LIKE}; <br>
     * On Windows systems, it will choose {@link #WIN}. <br>
     * 根据系统自动选择路径类型：在Unix-like系统，会选择 {@link #UNIX_LIKE}；在Windows系统，则会选择 {@link #WIN}。
     */
    AUTO_WIN
}

