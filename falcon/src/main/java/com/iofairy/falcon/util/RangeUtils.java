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

import com.iofairy.except.UnexpectedParameterException;
import com.iofairy.tuple.Tuple;
import com.iofairy.tuple.Tuple2;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * RangeUtils
 *
 * @since 0.1.0
 * @deprecated Since falcon version 0.3.0, replaced by {@link Ranges}
 */
@Deprecated
public class RangeUtils {
    /**
     * 将一个Range（范围）分成多个小的Range（范围）
     *
     * @param beginIndex  range开始序号（包含）
     * @param endIndex    range结束序号（不包含）
     * @param divideCount 分成几份
     * @param skewRatio   数据倾斜率，取值范围：(-1, 1)。
     *                    如：skewRatio为0.1，则 每一个Range都比前一个Range多10%的大小；skewRatio为-0.1，则 每一个Range都比前一个Range少10%的大小。
     * @return range list
     */
    public static List<Tuple2<Long, Long>> divideRange(long beginIndex, long endIndex, int divideCount, float skewRatio) {
        if (divideCount < 2) throw new UnexpectedParameterException("parameter `divideCount` must >= 2!");
        if (endIndex - beginIndex < divideCount)
            throw new UnexpectedParameterException("(`endIndex` - `beginIndex`) must be >= `divideCount`!");
        if (skewRatio <= -1 || skewRatio >= 1)
            throw new UnexpectedParameterException("parameter `skewRatio` must be in (-1, 1)!");
        long dataCount = endIndex - beginIndex;
        /*
         * x 为首个range大小
         *   x + x*(1+skewRatio)+ x*(1+skewRatio)² + x*(1+skewRatio)³... = dataCount
         *   x * (1 + (1+skewRatio) + (1+skewRatio)² + (1+skewRatio)³... ) = dataCount
         *   ratioSum = (1 + (1+skewRatio) + (1+skewRatio)² + (1+skewRatio)³... )
         *   x = dataCount / ratioSum
         */
        double ratioSum = IntStream.range(0, divideCount).boxed().mapToDouble(i -> Math.pow(1 + skewRatio, i)).sum();
        ratioSum = new BigDecimal(ratioSum).setScale(4, RoundingMode.DOWN).doubleValue();

        List<Tuple2<Long, Long>> rangeList = new ArrayList<>();
        float floatFirstSize = new BigDecimal(dataCount / ratioSum).setScale(4, RoundingMode.DOWN).floatValue();
        long firstSize = new BigDecimal(floatFirstSize).setScale(0, RoundingMode.HALF_UP).longValue();
        rangeList.add(Tuple.of(beginIndex, firstSize + beginIndex).alias("beginInclusive", "endExclusive"));
        for (int i = 1; i < divideCount; i++) {
            Tuple2<Long, Long> preRange = rangeList.get(i - 1);
            long lineSize = new BigDecimal(floatFirstSize * Math.pow(1 + skewRatio, i)).setScale(0, RoundingMode.HALF_UP).longValue();
            rangeList.add(Tuple.of(preRange._2, i < divideCount - 1 ? preRange._2 + lineSize : endIndex).alias("beginInclusive", "endExclusive"));
        }
        return rangeList;
    }
}
