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
package com.iofairy.falcon.regex;

/**
 * Match Index <br>
 * 正则表达式匹配到的字符串索引信息
 *
 * @since 0.0.7
 */
public class MatchIndex {
    /**
     * 匹配的字符串开始索引
     */
    public final int start;
    /**
     * 匹配的字符串结束索引
     */
    public final int end;
    /**
     * 匹配的字符串长度
     */
    public final int length;

    public MatchIndex(int start, int end) {
        this.start = start;
        this.end = end;
        this.length = end - start;
    }

    @Override
    public String toString() {
        return "MatchIndex{" +
                "start=" + start +
                ", end=" + end +
                ", length=" + length +
                '}';
    }
}
