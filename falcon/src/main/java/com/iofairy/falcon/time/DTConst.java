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
package com.iofairy.falcon.time;

import java.time.*;
import java.time.temporal.Temporal;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * DateTime Constant
 *
 * @since 0.3.0
 */
public class DTConst {
    /*###################################################################################
     ************************************************************************************
     ------------------------------------------------------------------------------------
     ***********************               星期字段设置              **********************
     ------------------------------------------------------------------------------------
     ************************************************************************************
     ###################################################################################*/
    /**
     * A week starts on <b>Monday</b> and the first week has a minimum of <b>4 days</b>.
     */
    public static final WeekFields WEEK_ISO = WeekFields.ISO;
    /**
     * A week starts on <b>Monday</b> and the first week has a minimum of <b>1 day</b>.
     */
    public static final WeekFields MONDAY_MIN1 = WeekFields.of(DayOfWeek.MONDAY, 1);
    /**
     * A week starts on <b>Monday</b> and the first week has a minimum of <b>4 days</b>.
     */
    public static final WeekFields MONDAY_MIN4 = WeekFields.of(DayOfWeek.MONDAY, 4);
    /**
     * A week starts on <b>SUNDAY</b> and the first week has a minimum of <b>1 day</b>.
     */
    public static final WeekFields SUNDAY_MIN1 = WeekFields.of(DayOfWeek.SUNDAY, 1);
    /**
     * A week starts on <b>SUNDAY</b> and the first week has a minimum of <b>4 days</b>.
     */
    public static final WeekFields SUNDAY_MIN4 = WeekFields.of(DayOfWeek.SUNDAY, 4);
    /**
     * A week starts on <b>SATURDAY</b> and the first week has a minimum of <b>1 day</b>.
     */
    public static final WeekFields SATURDAY_MIN1 = WeekFields.of(DayOfWeek.SATURDAY, 1);
    /**
     * A week starts on <b>SATURDAY</b> and the first week has a minimum of <b>4 days</b>.
     */
    public static final WeekFields SATURDAY_MIN4 = WeekFields.of(DayOfWeek.SATURDAY, 4);

    /*###################################################################################
     ************************************************************************************
     ------------------------------------------------------------------------------------
     ***********************                  其他                  **********************
     ------------------------------------------------------------------------------------
     ************************************************************************************
     ###################################################################################*/
    /**
     * Universal supported temporal
     */
    public static final List<Class<? extends Temporal>> SUPPORTED_TEMPORAL_COMMON =
            Collections.unmodifiableList(Arrays.asList(LocalDateTime.class, ZonedDateTime.class, OffsetDateTime.class, Instant.class));
    /**
     * Universal supported temporal string
     */
    public static final String SUPPORTED_TEMPORAL_COMMON_STRING = SUPPORTED_TEMPORAL_COMMON.stream().map(Class::getSimpleName).collect(Collectors.joining(", "));

    /**
     * hours of a day. <br>
     * {@code
     * ["00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
     * "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"]}
     */
    public static final List<String> HHs = Collections.unmodifiableList(
            IntStream.range(0, 24).boxed().map(i -> String.format("%02d", i)).collect(Collectors.toList()));

    /**
     * minutes of a hour or seconds of a minute. <br>
     * {@code
     * [
     * "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
     * "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
     * "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" ]}
     */
    public static final List<String> MSs = Collections.unmodifiableList(
            IntStream.range(0, 60).boxed().map(i -> String.format("%02d", i)).collect(Collectors.toList()));


}
