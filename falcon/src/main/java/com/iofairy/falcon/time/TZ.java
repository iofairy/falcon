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

import com.iofairy.tcf.Try;

import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Time Zone<br>
 * 时区
 *
 * @since 0.1.0
 */
public class TZ {
    /**
     * All Available Zone Ids.
     */
    public static final List<ZoneId> ZONE_IDS = ZoneId.getAvailableZoneIds().stream().map(ZoneId::of).collect(Collectors.toList());
    /**
     * Default Time Zone
     */
    public static final ZoneId DEFAULT_ZONE = Try.tcf(() -> ZoneId.systemDefault(), false);
    /**
     * <b>上海(+08:00)</b> Time Zone
     */
    public static final ZoneId SHANGHAI = getZoneId("Asia/Shanghai");
    /**
     * <b>重庆(+08:00)</b> Time Zone
     */
    public static final ZoneId CHONGQING = getZoneId("Asia/Chongqing");
    /**
     * <b>重庆(+08:00)</b> Time Zone
     */
    public static final ZoneId CHUNGKING = getZoneId("Asia/Chungking");
    /**
     * <b>哈尔滨(+08:00)</b> Time Zone
     */
    public static final ZoneId HARBIN = getZoneId("Asia/Harbin");
    /**
     * <b>香港(+08:00)</b> Time Zone
     */
    public static final ZoneId HONG_KONG = getZoneId("Asia/Hong_Kong");
    /**
     * <b>香港(+08:00)</b> Time Zone
     */
    public static final ZoneId HONGKONG = getZoneId("Hongkong");
    /**
     * <b>澳门(+08:00)</b> Time Zone
     */
    public static final ZoneId MACAU = getZoneId("Asia/Macau");
    /**
     * <b>台北(+08:00)</b> Time Zone
     */
    public static final ZoneId TAIPEI = getZoneId("Asia/Taipei");
    /**
     * <b>乌鲁木齐(+06:00)</b> Time Zone
     */
    public static final ZoneId URUMQI = getZoneId("Asia/Urumqi");
    /**
     * <b>喀什(+06:00)</b> Time Zone
     */
    public static final ZoneId KASHGAR = getZoneId("Asia/Kashgar");
    /**
     * <b>PRC(+08:00)</b> Time Zone
     */
    public static final ZoneId PRC = getZoneId("PRC");
    /**
     * <b>英国伦敦(+00:00)</b> Time Zone
     */
    public static final ZoneId LONDON = getZoneId("Europe/London");
    /**
     * <b>爱尔兰都柏林(+00:00)</b> Time Zone
     */
    public static final ZoneId DUBLIN = getZoneId("Europe/Dublin");
    /**
     * <b>德国柏林(+01:00)</b> Time Zone
     */
    public static final ZoneId BERLIN = getZoneId("Europe/Berlin");
    /**
     * <b>俄罗斯莫斯科(+03:00)</b> Time Zone
     */
    public static final ZoneId MOSCOW = getZoneId("Europe/Moscow");
    /**
     * <b>法国巴黎(+01:00)</b> Time Zone
     */
    public static final ZoneId PARIS = getZoneId("Europe/Paris");
    /**
     * <b>美国纽约(-05:00)</b> Time Zone
     */
    public static final ZoneId NEW_YORK = getZoneId("America/New_York");
    /**
     * <b>美国洛杉矶(-08:00)</b> Time Zone
     */
    public static final ZoneId LOS_ANGELES = getZoneId("America/Los_Angeles");
    /**
     * <b>GMT(+00:00)</b> Time Zone
     */
    public static final ZoneId GMT = getZoneId("GMT");
    /**
     * <b>UTC(+00:00)</b> Time Zone
     */
    public static final ZoneId UTC = getZoneId("UTC");
    /**
     * <b>UCT(+00:00)</b> Time Zone
     */
    public static final ZoneId UCT = getZoneId("UCT");
    /**
     * <b>GMT0(+00:00)</b> Time Zone
     */
    public static final ZoneId GMT0 = getZoneId("GMT0");
    /**
     * <b>WET(+00:00)</b> Time Zone
     */
    public static final ZoneId WET = getZoneId("WET");
    /**
     * <b>EET(+02:00)</b> Time Zone
     */
    public static final ZoneId EET = getZoneId("EET");
    /**
     * <b>GB(+00:00)</b> Time Zone
     */
    public static final ZoneId GB = getZoneId("GB");
    /**
     * <b>CET(+01:00)</b> Time Zone
     */
    public static final ZoneId CET = getZoneId("CET");
    /**
     * <b>NZ(+13:00)</b> Time Zone
     */
    public static final ZoneId NZ = getZoneId("NZ");
    /**
     * <b>MET(+01:00)</b> Time Zone
     */
    public static final ZoneId MET = getZoneId("MET");
    /**
     * <b>ROK(+09:00)</b> Time Zone
     */
    public static final ZoneId ROK = getZoneId("ROK");
    /**
     * <b>Universal(+00:00)</b> Time Zone
     */
    public static final ZoneId UNIVERSAL = getZoneId("Universal");


    private static ZoneId getZoneId(final String zoneIdName) {
        return Try.tcf(() -> ZoneId.of(zoneIdName), false);
    }
}
