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

import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

/**
 * String utils
 */
public class Strings {
    /**
     * 将二维字节数组转成字符串
     *
     * @param byteArrays 二维字节数组
     * @param charset    字符编码
     * @return 字符串
     * @since 0.4.2
     */
    public static String toString(byte[][] byteArrays, Charset charset) {
        StringBuilder sb = new StringBuilder();
        for (byte[] bytes : byteArrays) {
            sb.append(new String(bytes, charset));
        }
        return sb.toString();
    }

    /**
     * 将二维字节数组转成字符串
     *
     * @param byteArrays 二维字节数组
     * @return 字符串
     * @since 0.4.2
     */
    public static String toString(byte[][] byteArrays) {
        return toString(byteArrays, StandardCharsets.UTF_8);
    }

    /**
     * 将毫秒转成其他时间单位
     *
     * @param millis 毫秒数
     * @return 带单位的时间
     * @since 0.4.19
     */
    public static String convertTime(long millis) {
        if (millis < 0) return "-" + convertTime(Math.abs(millis));
        if (millis < 1000) return millis + "毫秒";

        DecimalFormat df = new DecimalFormat("0.0");
        df.setRoundingMode(RoundingMode.HALF_UP);

        if (millis < 60000) return df.format(millis / 1000.0) + "秒";
        if (millis < 3600000) return df.format(millis / 60000.0) + "分";
        if (millis < 86400000) return df.format(millis / 3600000.0) + "时";

        return df.format(millis / 86400000.0) + "天";
    }


    /**
     * 重复字符串指定的次数
     *
     * @param str         字符串
     * @param repeatTimes 次数
     * @return 字符串
     * @deprecated Since version 0.4.11, replaced by {@link com.iofairy.top.S#repeat(String, int)}
     */
    @Deprecated
    public static String repeat(String str, int repeatTimes) {
        if (str == null) return null;
        if (str.length() == 0 || repeatTimes <= 0) return "";
        if (repeatTimes == 1) return str;
        if (repeatTimes > Integer.MAX_VALUE - 8)
            throw new IllegalArgumentException("Parameter `repeatTimes` must be <= (Integer.MAX_VALUE - 8), otherwise, the memory will overflow! ");

        return new String(new char[repeatTimes]).replace("\0", str);
    }

}
