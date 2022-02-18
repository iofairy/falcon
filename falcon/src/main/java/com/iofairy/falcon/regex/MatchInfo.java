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

import java.util.List;
import java.util.regex.Pattern;

/**
 * Match info <br>
 * 字符串正则匹配的结果
 *
 * @since 0.1.0
 */
public class MatchInfo {
    /**
     * pattern
     */
    public final Pattern pattern;
    /**
     * 被用于匹配的字符串
     */
    public final String input;
    /**
     * 匹配到的所有字符串的序号信息
     */
    public final List<MatchIndex> indices;
    /**
     * 匹配的总数，与 {@code indices}` 数组长度一致
     */
    public final int count;

    public MatchInfo(Pattern pattern, String input, List<MatchIndex> indices) {
        this.pattern = pattern;
        this.input = input;
        this.indices = indices;
        this.count = indices == null ? 0 : indices.size();
    }

    /**
     * 获取指定序号满足匹配的子字符串
     *
     * @param groupIndex 指定的序号
     * @return 匹配的子字符串
     */
    public String group(int groupIndex) {
        if (groupIndex < count) {
            MatchIndex matchIndex = indices.get(groupIndex);
            return input.substring(matchIndex.start, matchIndex.end);
        }
        return null;
    }

    @Override
    public String toString() {
        return "MatchInfo{" +
                "pattern=" + pattern +
                ", input='" + input + '\'' +
                ", indices=" + indices +
                ", count=" + count +
                '}';
    }
}
