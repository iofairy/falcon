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
package com.iofairy.falcon.misc;

import com.iofairy.except.ConditionsNotMetException;
import com.iofairy.except.OutOfBoundsException;
import com.iofairy.si.SI;
import com.iofairy.top.G;
import com.iofairy.top.O;
import com.iofairy.top.S;

import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Arrays;
import java.util.Collection;

import static com.iofairy.falcon.misc.ErrorMsgType.*;

/**
 * Pre-check whether the parameters meet the conditions. <br><br>
 *
 * <b>NOTE: </b>You'd better import this class first using <b>static import</b>:
 * <blockquote><pre>{@code
 * import static com.iofairy.falcon.misc.Preconditions.*;
 * }</pre></blockquote>
 *
 * @since 0.5.0
 */
public final class Preconditions {

    private Preconditions() {
    }

    public static Object[] args(Object... objs) {
        return O.args(objs);
    }

    public static CharSequence[] args(CharSequence... cs) {
        return O.args(cs);
    }

    public static String[] args(String... ss) {
        return O.args(ss);
    }

    /*==============================================================================
     *************    throw ConditionsNotMetException with 400 code    *************
     ==============================================================================*/
    public static void checkBlank(CharSequence cs) {
        if (S.isBlank(cs)) {
            throwConditionsNotMetException400(getErrorMsg(BLANK, (String[]) null));
        }
    }

    public static void checkBlank(CharSequence cs, String msgTemplate, Object... objs) {
        if (S.isBlank(cs)) {
            throwConditionsNotMetException400(msgTemplate, objs);
        }
    }

    public static void checkBlank(CharSequence cs, String[] paramNames) {
        if (S.isBlank(cs)) {
            throwConditionsNotMetException400(getErrorMsg(BLANK, paramNames));
        }
    }

    public static void checkEmpty(Object o) {
        if (G.isEmpty(o)) {
            throwConditionsNotMetException400(getErrorMsg(EMPTY, (String[]) null));
        }
    }

    public static void checkEmpty(Object o, String msgTemplate, Object... objs) {
        if (G.isEmpty(o)) {
            throwConditionsNotMetException400(msgTemplate, objs);
        }
    }

    public static void checkEmpty(Object o, String[] paramNames) {
        if (G.isEmpty(o)) {
            throwConditionsNotMetException400(getErrorMsg(EMPTY, paramNames));
        }
    }

    public static void checkNull(Object o) {
        if (o == null) {
            throwConditionsNotMetException400(getErrorMsg(NULL, (String[]) null));
        }
    }

    public static void checkNull(Object o, String msgTemplate, Object... objs) {
        if (o == null) {
            throwConditionsNotMetException400(msgTemplate, objs);
        }
    }

    public static void checkNull(Object o, String[] paramNames) {
        if (o == null) {
            throwConditionsNotMetException400(getErrorMsg(NULL, paramNames));
        }
    }

    public static void checkNotBlank(CharSequence cs) {
        if (!S.isBlank(cs)) {
            throwConditionsNotMetException400(getErrorMsg(NOT_BLANK, (String[]) null));
        }
    }

    public static void checkNotBlank(CharSequence cs, String msgTemplate, Object... objs) {
        if (!S.isBlank(cs)) {
            throwConditionsNotMetException400(msgTemplate, objs);
        }
    }

    public static void checkNotBlank(CharSequence cs, String[] paramNames) {
        if (!S.isBlank(cs)) {
            throwConditionsNotMetException400(getErrorMsg(NOT_BLANK, paramNames));
        }
    }

    public static void checkNotEmpty(Object o) {
        if (!G.isEmpty(o)) {
            throwConditionsNotMetException400(getErrorMsg(NOT_EMPTY, (String[]) null));
        }
    }

    public static void checkNotEmpty(Object o, String msgTemplate, Object... objs) {
        if (!G.isEmpty(o)) {
            throwConditionsNotMetException400(msgTemplate, objs);
        }
    }

    public static void checkNotEmpty(Object o, String[] paramNames) {
        if (!G.isEmpty(o)) {
            throwConditionsNotMetException400(getErrorMsg(NOT_EMPTY, paramNames));
        }
    }

    public static void checkNotNull(Object o) {
        if (o != null) {
            throwConditionsNotMetException400(getErrorMsg(NOT_NULL, (String[]) null));
        }
    }

    public static void checkNotNull(Object o, String msgTemplate, Object... objs) {
        if (o != null) {
            throwConditionsNotMetException400(msgTemplate, objs);
        }
    }

    public static void checkNotNull(Object o, String[] paramNames) {
        if (o != null) {
            throwConditionsNotMetException400(getErrorMsg(NOT_NULL, paramNames));
        }
    }

    public static void checkHasBlank(CharSequence[] cs) {
        if (S.hasBlank(cs)) {
            throwConditionsNotMetException400(getErrorMsg(HAS_BLANK, (String[]) null));
        }
    }

    public static void checkHasBlank(CharSequence[] cs, String msgTemplate, Object... objs) {
        if (S.hasBlank(cs)) {
            throwConditionsNotMetException400(msgTemplate, objs);
        }
    }

    public static void checkHasBlank(CharSequence[] cs, String[] paramNames) {
        if (S.hasBlank(cs)) {
            throwConditionsNotMetException400(getErrorMsg(HAS_BLANK, paramNames));
        }
    }

    public static void checkHasEmpty(CharSequence[] cs) {
        if (S.hasEmpty(cs)) {
            throwConditionsNotMetException400(getErrorMsg(HAS_EMPTY, (String[]) null));
        }
    }

    public static void checkHasEmpty(CharSequence[] cs, String msgTemplate, Object... objs) {
        if (S.hasEmpty(cs)) {
            throwConditionsNotMetException400(msgTemplate, objs);
        }
    }

    public static void checkHasEmpty(CharSequence[] cs, String[] paramNames) {
        if (S.hasEmpty(cs)) {
            throwConditionsNotMetException400(getErrorMsg(HAS_EMPTY, paramNames));
        }
    }

    public static void checkHasNull(Object[] os) {
        if (G.hasNull(os)) {
            throwConditionsNotMetException400(getErrorMsg(HAS_NULL, (String[]) null));
        }
    }

    public static void checkHasNull(Object[] os, String msgTemplate, Object... objs) {
        if (G.hasNull(os)) {
            throwConditionsNotMetException400(msgTemplate, objs);
        }
    }

    public static void checkHasNull(Object[] os, String[] paramNames) {
        if (G.hasNull(os)) {
            throwConditionsNotMetException400(getErrorMsg(HAS_NULL, paramNames));
        }
    }

    public static void checkHasBlank(Collection<? extends CharSequence> cs) {
        if (S.hasBlank(cs)) {
            throwConditionsNotMetException400(getErrorMsg(HAS_BLANK, (String[]) null));
        }
    }

    public static void checkHasBlank(Collection<? extends CharSequence> cs, String msgTemplate, Object... objs) {
        if (S.hasBlank(cs)) {
            throwConditionsNotMetException400(msgTemplate, objs);
        }
    }

    public static void checkHasBlank(Collection<? extends CharSequence> cs, String[] paramNames) {
        if (S.hasBlank(cs)) {
            throwConditionsNotMetException400(getErrorMsg(HAS_BLANK, paramNames));
        }
    }

    public static void checkHasEmpty(Collection<? extends CharSequence> cs) {
        if (S.hasEmpty(cs)) {
            throwConditionsNotMetException400(getErrorMsg(HAS_EMPTY, (String[]) null));
        }
    }

    public static void checkHasEmpty(Collection<? extends CharSequence> cs, String msgTemplate, Object... objs) {
        if (S.hasEmpty(cs)) {
            throwConditionsNotMetException400(msgTemplate, objs);
        }
    }

    public static void checkHasEmpty(Collection<? extends CharSequence> cs, String[] paramNames) {
        if (S.hasEmpty(cs)) {
            throwConditionsNotMetException400(getErrorMsg(HAS_EMPTY, paramNames));
        }
    }

    public static void checkHasNull(Collection<?> os) {
        if (G.hasNull(os)) {
            throwConditionsNotMetException400(getErrorMsg(HAS_NULL, (String[]) null));
        }
    }

    public static void checkHasNull(Collection<?> os, String msgTemplate, Object... objs) {
        if (G.hasNull(os)) {
            throwConditionsNotMetException400(msgTemplate, objs);
        }
    }

    public static void checkHasNull(Collection<?> os, String[] paramNames) {
        if (G.hasNull(os)) {
            throwConditionsNotMetException400(getErrorMsg(HAS_NULL, paramNames));
        }
    }


    public static void checkAllBlank(CharSequence[] cs) {
        if (S.allBlank(cs)) {
            throwConditionsNotMetException400(getErrorMsg(ALL_BLANK, (String[]) null));
        }
    }

    public static void checkAllBlank(CharSequence[] cs, String msgTemplate, Object... objs) {
        if (S.allBlank(cs)) {
            throwConditionsNotMetException400(msgTemplate, objs);
        }
    }

    public static void checkAllBlank(CharSequence[] cs, String[] paramNames) {
        if (S.allBlank(cs)) {
            throwConditionsNotMetException400(getErrorMsg(ALL_BLANK, paramNames));
        }
    }

    public static void checkAllEmpty(CharSequence[] cs) {
        if (S.allEmpty(cs)) {
            throwConditionsNotMetException400(getErrorMsg(ALL_EMPTY, (String[]) null));
        }
    }

    public static void checkAllEmpty(CharSequence[] cs, String msgTemplate, Object... objs) {
        if (S.allEmpty(cs)) {
            throwConditionsNotMetException400(msgTemplate, objs);
        }
    }

    public static void checkAllEmpty(CharSequence[] cs, String[] paramNames) {
        if (S.allEmpty(cs)) {
            throwConditionsNotMetException400(getErrorMsg(ALL_EMPTY, paramNames));
        }
    }

    public static void checkAllNull(Object[] os) {
        if (G.allNull(os)) {
            throwConditionsNotMetException400(getErrorMsg(ALL_NULL, (String[]) null));
        }
    }

    public static void checkAllNull(Object[] os, String msgTemplate, Object... objs) {
        if (G.allNull(os)) {
            throwConditionsNotMetException400(msgTemplate, objs);
        }
    }

    public static void checkAllNull(Object[] os, String[] paramNames) {
        if (G.allNull(os)) {
            throwConditionsNotMetException400(getErrorMsg(ALL_NULL, paramNames));
        }
    }


    public static void checkAllBlank(Collection<? extends CharSequence> cs) {
        if (S.allBlank(cs)) {
            throwConditionsNotMetException400(getErrorMsg(ALL_BLANK, (String[]) null));
        }
    }

    public static void checkAllBlank(Collection<? extends CharSequence> cs, String msgTemplate, Object... objs) {
        if (S.allBlank(cs)) {
            throwConditionsNotMetException400(msgTemplate, objs);
        }
    }

    public static void checkAllBlank(Collection<? extends CharSequence> cs, String[] paramNames) {
        if (S.allBlank(cs)) {
            throwConditionsNotMetException400(getErrorMsg(ALL_BLANK, paramNames));
        }
    }

    public static void checkAllEmpty(Collection<? extends CharSequence> cs) {
        if (S.allEmpty(cs)) {
            throwConditionsNotMetException400(getErrorMsg(ALL_EMPTY, (String[]) null));
        }
    }

    public static void checkAllEmpty(Collection<? extends CharSequence> cs, String msgTemplate, Object... objs) {
        if (S.allEmpty(cs)) {
            throwConditionsNotMetException400(msgTemplate, objs);
        }
    }

    public static void checkAllEmpty(Collection<? extends CharSequence> cs, String[] paramNames) {
        if (S.allEmpty(cs)) {
            throwConditionsNotMetException400(getErrorMsg(ALL_EMPTY, paramNames));
        }
    }

    public static void checkAllNull(Collection<?> os) {
        if (G.allNull(os)) {
            throwConditionsNotMetException400(getErrorMsg(ALL_NULL, (String[]) null));
        }
    }

    public static void checkAllNull(Collection<?> os, String msgTemplate, Object... objs) {
        if (G.allNull(os)) {
            throwConditionsNotMetException400(msgTemplate, objs);
        }
    }

    public static void checkAllNull(Collection<?> os, String[] paramNames) {
        if (G.allNull(os)) {
            throwConditionsNotMetException400(getErrorMsg(ALL_NULL, paramNames));
        }
    }


    /**
     * expression 为 {@code true} 则抛出异常
     *
     * @param expression 条件
     */
    public static void checkCondition(boolean expression) {
        if (expression) {
            throwConditionsNotMetException400("The condition is not met! ");
        }
    }

    /**
     * expression 为 {@code true} 则抛出异常
     *
     * @param expression  条件
     * @param msgTemplate 信息模板
     * @param objs        信息参数
     */
    public static void checkCondition(boolean expression, String msgTemplate, Object... objs) {
        if (expression) {
            throwConditionsNotMetException400(msgTemplate, objs);
        }
    }


    private static void throwConditionsNotMetException400(String msgTemplate, Object... objs) {
        throw new ConditionsNotMetException(msgTemplate, objs).setCode("400");
    }

    /*==============================================================================
     ****************    throw UnsupportedTemporalTypeException     ****************
     ==============================================================================*/

    /**
     * 专门用于校验时间相关的表达式，expression 为 {@code true} 则抛出 {@link UnsupportedTemporalTypeException} 异常
     *
     * @param expression  时间相关的条件表达式
     * @param msgTemplate 信息模板
     * @param objs        信息参数
     */
    public static void checkTemporal(boolean expression, String msgTemplate, Object... objs) {
        if (expression) {
            throw new UnsupportedTemporalTypeException(SI.$(msgTemplate, objs));
        }
    }


    /*==============================================================================
     ********************    throw IllegalArgumentException     ********************
     ==============================================================================*/

    /**
     * 专门用于校验非法参数的表达式，expression 为 {@code true} 则抛出 {@link IllegalArgumentException} 异常
     *
     * @param expression  参数条件表达式
     * @param msgTemplate 信息模板
     * @param objs        信息参数
     */
    public static void checkArgument(boolean expression, String msgTemplate, Object... objs) {
        if (expression) {
            throw new IllegalArgumentException(SI.$(msgTemplate, objs));
        }
    }

    /*==============================================================================
     **********************    throw OutOfBoundsException     **********************
     ==============================================================================*/

    /**
     * 专门用于校验参数是否超出范围的表达式，expression 为 {@code true} 则抛出 {@link OutOfBoundsException} 异常
     *
     * @param expression  条件表达式
     * @param number      当前值
     * @param msgTemplate 信息模板
     * @param objs        信息参数
     */
    public static void checkOutOfBounds(boolean expression, Number number, String msgTemplate, Object... objs) {
        if (expression) {
            throw new OutOfBoundsException(number, SI.$(msgTemplate, objs));
        }
    }

    /*==============================================================================
     ****************          throw IllegalStateException          ****************
     ==============================================================================*/

    /**
     * expression 为 {@code true} 则抛出异常
     *
     * @param expression 条件
     */
    public static void checkState(boolean expression) {
        if (expression) {
            throw new IllegalStateException();
        }
    }

    /**
     * expression 为 {@code true} 则抛出异常
     *
     * @param expression  条件
     * @param msgTemplate 信息模板
     * @param objs        信息参数
     */
    public static void checkState(boolean expression, String msgTemplate, Object... objs) {
        if (expression) {
            throw new IllegalStateException(SI.$(msgTemplate, objs));
        }
    }

    /*==============================================================================
     ****************          throw NullPointerException          ****************
     ==============================================================================*/

    /**
     * expression 为 {@code true} ，则抛出 {@link NullPointerException} 异常
     *
     * @param expression  条件
     * @param msgTemplate 信息模板
     * @param objs        信息参数
     */
    public static void checkConditionNPE(boolean expression, String msgTemplate, Object... objs) {
        if (expression) {
            throw new NullPointerException(SI.$(msgTemplate, objs));
        }
    }

    /**
     * 待校验的对象为 {@code null}，则抛出 {@link NullPointerException} 异常
     *
     * @param o 待校验的对象
     */
    public static void checkNullNPE(Object o) {
        if (o == null) {
            throw new NullPointerException(getErrorMsg(NULL, (String[]) null));
        }
    }

    /**
     * 待校验的对象为 {@code null}，则抛出 {@link NullPointerException} 异常
     *
     * @param o           待校验的对象
     * @param msgTemplate 信息模板
     * @param objs        信息参数
     */
    public static void checkNullNPE(Object o, String msgTemplate, Object... objs) {
        if (o == null) {
            throw new NullPointerException(SI.$(msgTemplate, objs));
        }
    }

    /**
     * 待校验的对象为 {@code null}，则抛出 {@link NullPointerException} 异常
     *
     * @param o          待校验的对象
     * @param paramNames 校验的参数名称数组
     */
    public static void checkNullNPE(Object o, String[] paramNames) {
        if (o == null) {
            throw new NullPointerException(getErrorMsg(NULL, paramNames));
        }
    }

    /**
     * 待校验的数组或集合中存在为 {@code null} 的元素，则抛出 {@link NullPointerException} 异常
     *
     * @param os 待校验的数组或集合
     */
    public static void checkHasNullNPE(Object[] os) {
        if (G.hasNull(os)) {
            throw new NullPointerException(getErrorMsg(HAS_NULL, (String[]) null));
        }
    }

    /**
     * 待校验的数组或集合中存在为 {@code null} 的元素，则抛出 {@link NullPointerException} 异常
     *
     * @param os          待校验的数组或集合
     * @param msgTemplate 信息模板
     * @param objs        信息参数
     */
    public static void checkHasNullNPE(Object[] os, String msgTemplate, Object... objs) {
        if (G.hasNull(os)) {
            throw new NullPointerException(SI.$(msgTemplate, objs));
        }
    }

    /**
     * 待校验的数组或集合中存在为 {@code null} 的元素，则抛出 {@link NullPointerException} 异常
     *
     * @param os         待校验的数组或集合
     * @param paramNames 校验的参数名称数组
     */
    public static void checkHasNullNPE(Object[] os, String[] paramNames) {
        if (G.hasNull(os)) {
            throw new NullPointerException(getErrorMsg(HAS_NULL, paramNames));
        }
    }

    /**
     * 待校验的数组或集合中存在为 {@code null} 的元素，则抛出 {@link NullPointerException} 异常
     *
     * @param os 待校验的数组或集合
     */
    public static void checkHasNullNPE(Collection<?> os) {
        if (G.hasNull(os)) {
            throw new NullPointerException(getErrorMsg(HAS_NULL, (String[]) null));
        }
    }

    /**
     * 待校验的数组或集合中存在为 {@code null} 的元素，则抛出 {@link NullPointerException} 异常
     *
     * @param os          待校验的数组或集合
     * @param msgTemplate 信息模板
     * @param objs        信息参数
     */
    public static void checkHasNullNPE(Collection<?> os, String msgTemplate, Object... objs) {
        if (G.hasNull(os)) {
            throw new NullPointerException(SI.$(msgTemplate, objs));
        }
    }

    /**
     * 待校验的数组或集合中存在为 {@code null} 的元素，则抛出 {@link NullPointerException} 异常
     *
     * @param os         待校验的数组或集合
     * @param paramNames 校验的参数名称数组
     */
    public static void checkHasNullNPE(Collection<?> os, String[] paramNames) {
        if (G.hasNull(os)) {
            throw new NullPointerException(getErrorMsg(HAS_NULL, paramNames));
        }
    }

    /**
     * 待校验的数组或集合中的元素全部为 {@code null}，则抛出 {@link NullPointerException} 异常
     *
     * @param os 待校验的数组或集合
     */
    public static void checkAllNullNPE(Object[] os) {
        if (G.allNull(os)) {
            throw new NullPointerException(getErrorMsg(ALL_NULL, (String[]) null));
        }
    }

    /**
     * 待校验的数组或集合中的元素全部为 {@code null}，则抛出 {@link NullPointerException} 异常
     *
     * @param os          待校验的数组或集合
     * @param msgTemplate 信息模板
     * @param objs        信息参数
     */
    public static void checkAllNullNPE(Object[] os, String msgTemplate, Object... objs) {
        if (G.allNull(os)) {
            throw new NullPointerException(SI.$(msgTemplate, objs));
        }
    }

    /**
     * 待校验的数组或集合中的元素全部为 {@code null}，则抛出 {@link NullPointerException} 异常
     *
     * @param os         待校验的数组或集合
     * @param paramNames 校验的参数名称数组
     */
    public static void checkAllNullNPE(Object[] os, String[] paramNames) {
        if (G.allNull(os)) {
            throw new NullPointerException(getErrorMsg(ALL_NULL, paramNames));
        }
    }

    /**
     * 待校验的数组或集合中的元素全部为 {@code null}，则抛出 {@link NullPointerException} 异常
     *
     * @param os 待校验的数组或集合
     */
    public static void checkAllNullNPE(Collection<?> os) {
        if (G.allNull(os)) {
            throw new NullPointerException(getErrorMsg(ALL_NULL, (String[]) null));
        }
    }

    /**
     * 待校验的数组或集合中的元素全部为 {@code null}，则抛出 {@link NullPointerException} 异常
     *
     * @param os          待校验的数组或集合
     * @param msgTemplate 信息模板
     * @param objs        信息参数
     */
    public static void checkAllNullNPE(Collection<?> os, String msgTemplate, Object... objs) {
        if (G.allNull(os)) {
            throw new NullPointerException(SI.$(msgTemplate, objs));
        }
    }

    /**
     * 待校验的数组或集合中的元素全部为 {@code null}，则抛出 {@link NullPointerException} 异常
     *
     * @param os         待校验的数组或集合
     * @param paramNames 校验的参数名称数组
     */
    public static void checkAllNullNPE(Collection<?> os, String[] paramNames) {
        if (G.allNull(os)) {
            throw new NullPointerException(getErrorMsg(ALL_NULL, paramNames));
        }
    }


    /**
     * 根据 错误信息类型 和 参数名，动态生成错误信息。
     *
     * @param type       错误信息类型。如果 type 为 null，则默认为 {@link ErrorMsgType#NULL}
     * @param paramNames 参数名
     * @return 错误信息
     */
    public static String getErrorMsg(ErrorMsgType type, String... paramNames) {
        if (type == null) type = ErrorMsgType.NULL;

        int length = G.isEmpty(paramNames) ? 0 : paramNames.length;

        // 没传参数名，或只有一个参数，但是参数名为空
        boolean paramNameEmpty = length == 0 || (length == 1 && S.isBlank(paramNames[0]));
        final char SPACE = ' ';
        String paramNamesStr = paramNameEmpty ? "" : (length == 1 ? "`" + paramNames[0] + "`" : Arrays.asList(paramNames).toString()) + SPACE;

        String mustBe = type.mustBe;
        String errorReason = type.errorReason;

        String errorMsg = (paramNameEmpty ? "This parameter " : (length == 1 ? "The parameter " : "All parameters ")) + paramNamesStr + "must be " + mustBe + "! ";

        String length1Msg = "The parameter " + paramNamesStr + "must be " + mustBe + "! ";

        switch (type) {
            case NULL:      // OR has not null
            case NOT_NULL:  // OR has not null
            case EMPTY:     // OR has empty
            case NOT_EMPTY: // OR has not empty
            case BLANK:     // OR has blank
            case NOT_BLANK: // OR has not blank
                // default errorMsg
                break;
            case HAS_BLANK:
            case HAS_EMPTY:
            case HAS_NULL:
                errorMsg = paramNameEmpty ? "None of the parameters " + paramNamesStr + "can be " + errorReason + "! " :
                        (length == 1 ? length1Msg : "None of these parameters " + paramNamesStr + "can be " + errorReason + "! ");
                break;
            case ALL_BLANK:
            case ALL_EMPTY:
            case ALL_NULL:
                errorMsg = paramNameEmpty ? "At least one parameter " + paramNamesStr + "can't be " + errorReason + "! " :
                        (length == 1 ? length1Msg : "At least one of these parameters " + paramNamesStr + "can't be " + errorReason + "! ");
                break;
        }
        return errorMsg;
    }

}
