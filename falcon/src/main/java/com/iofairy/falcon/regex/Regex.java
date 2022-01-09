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

import com.iofairy.top.G;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Regex <br>
 * 正则表达式匹配
 *
 * @since 0.0.7
 */
public class Regex {
    /**
     * 获取正则表达式所有匹配的结果信息
     *
     * @param pattern 正则匹配模板引擎
     * @param input   待匹配的字符串
     * @return 匹配结果信息
     */
    public static MatchInfo match(Pattern pattern, String input) {
        if (G.hasNull(pattern, input)) return null;

        Matcher matcher = pattern.matcher(input);
        List<MatchIndex> matchIndices = new ArrayList<>();
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            matchIndices.add(new MatchIndex(start, end));
        }

        return new MatchInfo(pattern, input, matchIndices);
    }

    /**
     * 采用重复匹配的方式获取正则表达式所有匹配的结果信息
     *
     * @param pattern 正则匹配模板引擎
     * @param input   待匹配的字符串
     * @return 匹配结果信息
     */
    public static MatchInfo matchOverlap(Pattern pattern, String input) {
        if (G.hasNull(pattern, input)) return null;
        if (input.equals("")) return match(pattern, input);

        Matcher matcher = pattern.matcher(input);
        List<MatchIndex> matchIndices = new ArrayList<>();
        int startIndex = 0;
        while (matcher.find(startIndex)) {
            int start = matcher.start();
            int end = matcher.end();
            startIndex = start + 1;
            matchIndices.add(new MatchIndex(start, end));
        }

        return new MatchInfo(pattern, input, matchIndices);
    }
}
