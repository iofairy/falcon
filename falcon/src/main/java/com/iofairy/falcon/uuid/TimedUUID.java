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
package com.iofairy.falcon.uuid;

import com.iofairy.top.S;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.UUID;

/**
 * 带时间的UUID
 *
 * @since 0.4.19
 */
public class TimedUUID {
    /**
     * 默认的 TimedUUID 实例（仅能设置一次，应在程序初始化时设置）<br>
     * <b>注：</b><br>
     * 如果在设置之前就调用 {@link #getId()}，则会被设置 {@link #TIMED_ID}
     */
    private static TimedUUID DEFAULT_ID;
    /**
     * 均采用默认参数（25位（含15位时间））的 TimedUUID 实例
     */
    public static final TimedUUID TIMED_ID = of();
    /**
     * 26位（含16位时间）的 TimedUUID 实例
     */
    public static final TimedUUID TIMED_ID26 = of(26, true, 3);
    /**
     * 30位（含17位时间）的 TimedUUID 实例
     */
    public static final TimedUUID TIMED_ID30 = of(30, true, 4);
    /**
     * 32位（含17位时间）的 TimedUUID 实例
     */
    public static final TimedUUID TIMED_ID32 = of(32, true, 4);
    /**
     * 28位（含17位时间）的带 _(下划线) 的 TimedUUID 实例
     */
    public static final TimedUUID TIMED_ID_UNDERLINE = of(0, 1, 28, true, true, 4);
    /**
     * 工作节点ID（数字）
     */
    public final int workerNum;
    /**
     * 工作节点ID位数，范围 <b>[1, 6]</b>
     */
    public final int workerIdLength;
    /**
     * 工作节点ID（字母序列）
     */
    public final String workerId;
    /**
     * 最后生成的ID位数，范围 <b>[25, 49]</b><br>
     * {@code 时间串(15~17位) + "_"(0~1位) + 工作节点ID(1~6位) + 36进制UUID(25位) = 最大49位 }
     */
    public final int idLength;
    /**
     * 是否大写
     */
    public final boolean upperCase;
    /**
     * 生成的ID是否带下划线（用于区分时间串与UUID）
     */
    public final boolean withUnderline;
    /**
     * 最后生成的“年”的位数，范围 <b>[2, 4]</b>
     */
    public final int yearLength;
    /**
     * 时间格式串
     */
    public final DateTimeFormatter formatter;
    /*
     * 默认值及最大最小值设置
     */
    private static final int MIN_ID_LENGTH = 25;
    private static final int MAX_ID_LENGTH = 49;
    private static final int DEFAULT_ID_LENGTH = 25;
    private static final int DEFAULT_WORKER_NUM = 0;
    private static final int MIN_WORKERID_LENGTH = 1;
    private static final int MAX_WORKERID_LENGTH = 6;
    private static final int DEFAULT_WORKERID_LENGTH = 2;
    private static final boolean DEFAULT_UPPER_CASE = true;
    private static final boolean DEFAULT_WITH_UNDERLINE = false;
    private static final int MIN_YEAR_LENGTH = 2;
    private static final int MAX_YEAR_LENGTH = 4;
    private static final int DEFAULT_YEAR_LENGTH = 2;


    public TimedUUID() {
        this(DEFAULT_WORKER_NUM, DEFAULT_WORKERID_LENGTH, DEFAULT_ID_LENGTH, DEFAULT_UPPER_CASE, DEFAULT_WITH_UNDERLINE, DEFAULT_YEAR_LENGTH);
    }

    public TimedUUID(int workerNum, int workerIdLength) {
        this(workerNum, workerIdLength, DEFAULT_ID_LENGTH, DEFAULT_UPPER_CASE, DEFAULT_WITH_UNDERLINE, DEFAULT_YEAR_LENGTH);
    }

    public TimedUUID(boolean upperCase, boolean withUnderline) {
        this(DEFAULT_WORKER_NUM, DEFAULT_WORKERID_LENGTH, DEFAULT_ID_LENGTH, upperCase, withUnderline, DEFAULT_YEAR_LENGTH);
    }

    public TimedUUID(int workerNum, int workerIdLength, int idLength) {
        this(workerNum, workerIdLength, idLength, DEFAULT_UPPER_CASE, DEFAULT_WITH_UNDERLINE, DEFAULT_YEAR_LENGTH);
    }

    public TimedUUID(int idLength, boolean upperCase, boolean withUnderline) {
        this(DEFAULT_WORKER_NUM, DEFAULT_WORKERID_LENGTH, idLength, upperCase, withUnderline, DEFAULT_YEAR_LENGTH);
    }

    public TimedUUID(int idLength, boolean upperCase, boolean withUnderline, int yearLength) {
        this(DEFAULT_WORKER_NUM, DEFAULT_WORKERID_LENGTH, idLength, upperCase, withUnderline, yearLength);
    }

    public TimedUUID(int workerNum, int workerIdLength, int idLength, int yearLength) {
        this(workerNum, workerIdLength, idLength, DEFAULT_UPPER_CASE, DEFAULT_WITH_UNDERLINE, yearLength);
    }

    public TimedUUID(int idLength, boolean upperCase, int yearLength) {
        this(DEFAULT_WORKER_NUM, DEFAULT_WORKERID_LENGTH, idLength, upperCase, DEFAULT_WITH_UNDERLINE, yearLength);
    }

    public TimedUUID(int workerNum, int workerIdLength, int idLength, boolean upperCase, boolean withUnderline, int yearLength) {
        this.workerNum = workerNum;
        this.workerIdLength = workerIdLength < MIN_WORKERID_LENGTH ? MIN_WORKERID_LENGTH : (Math.min(workerIdLength, MAX_WORKERID_LENGTH));
        this.idLength = idLength < MIN_ID_LENGTH ? MIN_ID_LENGTH : (Math.min(idLength, MAX_ID_LENGTH));
        this.upperCase = upperCase;
        this.withUnderline = withUnderline;
        this.workerId = numberToLetters(this.workerNum, this.workerIdLength);
        this.yearLength = yearLength < MIN_YEAR_LENGTH ? MIN_YEAR_LENGTH : (Math.min(yearLength, MAX_YEAR_LENGTH));

        switch (this.yearLength) {
            case 2:
                this.formatter = DateTimeFormatter.ofPattern("yyMMddHHmmssSSS");
                break;
            case 4:
                this.formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
                break;
            default:
                this.formatter = new DateTimeFormatterBuilder()
                        .appendValueReduced(ChronoField.YEAR, 3, 3, 0)
                        .appendPattern("MMddHHmmssSSS")
                        .toFormatter();
        }

    }

    public static TimedUUID of() {
        return new TimedUUID();
    }

    public static TimedUUID of(int workerNum, int workerIdLength) {
        return new TimedUUID(workerNum, workerIdLength, DEFAULT_ID_LENGTH, DEFAULT_UPPER_CASE, DEFAULT_WITH_UNDERLINE, DEFAULT_YEAR_LENGTH);
    }

    public static TimedUUID of(boolean upperCase, boolean withUnderline) {
        return new TimedUUID(DEFAULT_WORKER_NUM, DEFAULT_WORKERID_LENGTH, DEFAULT_ID_LENGTH, upperCase, withUnderline, DEFAULT_YEAR_LENGTH);
    }

    public static TimedUUID of(int idLength, boolean upperCase, boolean withUnderline) {
        return new TimedUUID(DEFAULT_WORKER_NUM, DEFAULT_WORKERID_LENGTH, idLength, upperCase, withUnderline, DEFAULT_YEAR_LENGTH);
    }

    public static TimedUUID of(int idLength, boolean upperCase, boolean withUnderline, int yearLength) {
        return new TimedUUID(DEFAULT_WORKER_NUM, DEFAULT_WORKERID_LENGTH, idLength, upperCase, withUnderline, yearLength);
    }

    public static TimedUUID of(int workerNum, int workerIdLength, int idLength) {
        return new TimedUUID(workerNum, workerIdLength, idLength, DEFAULT_UPPER_CASE, DEFAULT_WITH_UNDERLINE, DEFAULT_YEAR_LENGTH);
    }

    public static TimedUUID of(int workerNum, int workerIdLength, int idLength, int yearLength) {
        return new TimedUUID(workerNum, workerIdLength, idLength, DEFAULT_UPPER_CASE, DEFAULT_WITH_UNDERLINE, yearLength);
    }

    public static TimedUUID of(int idLength, boolean upperCase, int yearLength) {
        return new TimedUUID(DEFAULT_WORKER_NUM, DEFAULT_WORKERID_LENGTH, idLength, upperCase, DEFAULT_WITH_UNDERLINE, yearLength);
    }

    public static TimedUUID of(int workerNum, int workerIdLength, int idLength, boolean upperCase, boolean withUnderline, int yearLength) {
        return new TimedUUID(workerNum, workerIdLength, idLength, upperCase, withUnderline, yearLength);
    }


    /**
     * 从 {@link #DEFAULT_ID} 获取随机ID
     *
     * @return 随机ID
     */
    public static String getId() {
        setDefaultId(TIMED_ID);

        return DEFAULT_ID.randomId();
    }

    /**
     * 从 {@link #TIMED_ID} 获取 <b>25位（含15位时间）</b> 随机ID
     *
     * @return 随机ID
     */
    public static String newId() {
        return TIMED_ID.randomId();
    }

    /**
     * 从 {@link #TIMED_ID26} 获取 <b>26位（含16位时间）</b> 随机ID
     *
     * @return 随机ID
     */
    public static String shortId() {
        return TIMED_ID26.randomId();
    }

    /**
     * 从 {@link #TIMED_ID30} 获取 <b>30位（含17位时间）</b> 随机ID
     *
     * @return 随机ID
     */
    public static String mediumId() {
        return TIMED_ID30.randomId();
    }

    /**
     * 从 {@link #TIMED_ID32} 获取 <b>32位（含17位时间）</b> 随机ID
     *
     * @return 随机ID
     */
    public static String longId() {
        return TIMED_ID32.randomId();
    }

    /**
     * 从 {@link #TIMED_ID_UNDERLINE} 获取 <b>28位（含17位时间）的带 _(下划线)</b> 随机ID
     *
     * @return 随机ID
     */
    public static String linedId() {
        return TIMED_ID_UNDERLINE.randomId();
    }

    /**
     * 获取随机ID
     *
     * @return 随机ID
     */
    public String randomId() {
        String dateTime = formatter.format(LocalDateTime.now());
        String base36 = uuidToBase36();

        String id = dateTime + (withUnderline ? "_" : "") + workerId + base36;
        id = id.substring(0, Math.min(idLength, id.length()));
        return upperCase ? id.toUpperCase() : id.toLowerCase();
    }

    public static TimedUUID getDefaultId() {
        return DEFAULT_ID;
    }

    public static void setDefaultId(TimedUUID defaultId) {
        if (DEFAULT_ID == null) {
            DEFAULT_ID = defaultId;
        }
    }

    private static String uuidToBase36() {
        String hexUuid = UUID.randomUUID().toString();
        String[] split = hexUuid.split("-");
        if (split.length == 5) {
            hexUuid = split[0] + split[4] + split[1] + split[2] + split[3];
        } else {
            hexUuid = hexUuid.replaceAll("-", "");
        }

        int radix = 36;
        BigInteger bigInteger = new BigInteger(hexUuid, 16);
        String base36 = bigInteger.toString(radix);
        /*
         * 16进制UUID 转 36进制，最大25位
         */
        if (base36.length() < 25) {
            base36 = S.padRightChars(base36, '0', 25);  // 这里采用末位补0的方式，因为末位可能被裁剪。
        }
        return base36;
    }


    /**
     * 将数字映射成指定位数的字母<b>（大写）</b> <br>
     * 注：<br>
     * <ul>
     *     <li> 映射成1位字母，number取值 {@code [0, 25]}
     *     <li> 映射成2位字母，number取值 {@code [0, 675]}
     *     <li> 映射成3位字母，number取值 {@code [0, 17575]}
     *     <li> 映射成4位字母，number取值 {@code [0, 456975]}
     *     <li> 映射成5位字母，number取值 {@code [0, 11881375]}
     *     <li> 映射成6位字母，number取值 {@code [0, 308915775]}
     * </ul>
     *
     * @param number       要转成字母的数字
     * @param letterLength 字母位数，取值范围 {@code [1, 6]}
     * @return 指定位数的字母<b>（大写）</b>
     */
    public static String numberToLetters(int number, int letterLength) {
        if (number < 0 || letterLength < 1 || letterLength > 6) throw new IllegalArgumentException("参数`number`应为非负数，`letterLength`取值范围为[1, 6]！");

        int maxNumber = (int) Math.pow(26, letterLength) - 1;
        if (number > maxNumber)
            throw new IllegalArgumentException("参数`number`超出范围，当前位数[" + letterLength + "]下，最大允许值[" + maxNumber + "]，无法映射到指定位数的字母！");

        char[] letters = new char[letterLength];

        for (int i = letterLength - 1; i >= 0; i--) {
            letters[i] = (char) ('A' + number % 26);
            number /= 26;
        }

        return String.valueOf(letters);
    }

    @Override
    public String toString() {
        return "TimedUUID{" +
                "workerNum=" + workerNum +
                ", workerIdLength=" + workerIdLength +
                ", workerId='" + workerId + '\'' +
                ", idLength=" + idLength +
                ", upperCase=" + upperCase +
                ", withUnderline=" + withUnderline +
                ", yearLength=" + yearLength +
                '}';
    }
}
