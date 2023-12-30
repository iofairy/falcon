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

import com.iofairy.top.G;
import com.iofairy.top.S;

/**
 * Exception Utils
 *
 * @since 0.4.15
 */
public class Exceptions {
    /**
     * 带异常跟踪ID的异常信息
     *
     * @param e 异常对象
     * @return 异常堆栈字符串
     */
    public static String stackTrace(Throwable e) {
        return stackTrace(G.stackTrace(e), null);
    }

    /**
     * 带异常跟踪ID的异常信息（截取指定长度）
     *
     * @param e      异常对象
     * @param length 截取长度
     * @return 异常堆栈字符串
     */
    public static String stackTrace(Throwable e, int length) {
        return stackTrace(e, null, length);
    }

    /**
     * 带异常跟踪ID的异常信息
     *
     * @param errorMsg 异常信息
     * @return 异常堆栈字符串
     */
    public static String stackTrace(String errorMsg) {
        return stackTrace(errorMsg, null);
    }

    /**
     * 带异常跟踪ID的异常信息
     *
     * @param e   异常对象
     * @param tag 异常标签
     * @return 异常堆栈字符串
     */
    public static String stackTrace(Throwable e, String tag) {
        return stackTrace(G.stackTrace(e), tag);
    }

    /**
     * 带异常跟踪ID的异常信息（截取指定长度）
     *
     * @param e      异常对象
     * @param tag    异常标签
     * @param length 截取长度
     * @return 异常堆栈字符串
     */
    public static String stackTrace(Throwable e, String tag, int length) {
        String stackTrace = stackTrace(G.stackTrace(e), tag);
        return stackTrace.length() <= length ? stackTrace : stackTrace.substring(0, length);
    }

    /**
     * 带异常跟踪ID的异常信息
     *
     * @param errorMsg 异常信息
     * @param tag      异常标签
     * @return 异常堆栈字符串
     */
    public static String stackTrace(String errorMsg, String tag) {
        return "异常跟踪ID：【" + Numbers.randomInt(6) + "】---" + (S.isBlank(tag) ? "" : (tag + " >>> ")) + errorMsg;
    }

}
