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

import java.io.Serializable;
import java.time.temporal.TemporalAmount;
import java.util.Calendar;
import java.util.Date;

/**
 * ChronoInterval
 *
 * @since 0.1.0
 */
public interface ChronoInterval extends TemporalAmount, Serializable {

    /**
     * Adds to the specified date object.
     *
     * @param date date
     * @return Date
     */
    Date addTo(Date date);

    /**
     * Adds to the specified calendar object.
     *
     * @param calendar calendar
     * @return Calendar
     */
    Calendar addTo(Calendar calendar);

    /**
     * Subtracts this object from the specified date object.
     *
     * @param date date
     * @return Date
     */
    Date subtractFrom(Date date);

    /**
     * Subtracts this object from the specified calendar object.
     *
     * @param calendar calendar
     * @return Calendar
     */
    Calendar subtractFrom(Calendar calendar);
}
