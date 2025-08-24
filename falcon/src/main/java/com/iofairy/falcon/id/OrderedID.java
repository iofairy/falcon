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
package com.iofairy.falcon.id;

import com.iofairy.except.IDGenerateException;
import com.iofairy.os.OS;
import com.iofairy.lambda.RT1;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static com.iofairy.validator.Preconditions.*;

/**
 * 根据不同的ID分类（不同的Key）有序生成ID（自增ID）
 *
 * @since 0.5.11
 * @deprecated since <b>Falcon v0.6.0</b>，use {@link com.iofairy.id.OrderedID} instead
 */
@Deprecated
public final class OrderedID {

    private final static ConcurrentHashMap<String, AtomicLong> ID_GENERATORS = new ConcurrentHashMap<>();

    private final static String ERROR_MSG = OS.IS_ZH_LANG ? "OrderedID生成分类【${…}】自增ID异常。" : "OrderedID failed to generate the Self-increment ID of classify [${?}]. ";

    /**
     * 初始化ID生成器
     *
     * @param idMap id map
     */
    public static void init(Map<String, AtomicLong> idMap) {
        ID_GENERATORS.putAll(idMap);
    }

    /**
     * 生成下一个ID
     *
     * @return next id
     */
    public static long nextId() {
        return nextId("", null);
    }

    /**
     * 根据ID分类，生成下一个ID
     *
     * @param classify ID分类
     * @return next id
     */
    public static long nextId(String classify) {
        return nextId(classify, null);
    }

    /**
     * 根据ID分类，生成下一个ID
     *
     * @param standbyIdGenerator 当{@link #ID_GENERATORS}不存在此classify时，会使用备用ID生成器
     * @return next id
     * @throws IDGenerateException 当standbyIdGenerator生成器发生异常时，抛出此异常
     */
    public static long nextId(RT1<String, Long, Throwable> standbyIdGenerator) {
        return nextId("", standbyIdGenerator);
    }

    /**
     * 根据ID分类，生成下一个ID
     *
     * @param classify           ID分类
     * @param standbyIdGenerator 当{@link #ID_GENERATORS}不存在此classify时，会使用备用ID生成器
     * @return next id
     * @throws IDGenerateException 当standbyIdGenerator生成器发生异常时，抛出此异常
     */
    public static long nextId(final String classify, final RT1<String, Long, Throwable> standbyIdGenerator) {
        checkNullNPE(classify, args("classify"));
        return ID_GENERATORS.computeIfAbsent(
                classify,
                key -> {
                    try {
                        return new AtomicLong(standbyIdGenerator == null ? -1L : standbyIdGenerator.$(key));
                    } catch (Throwable e) {
                        throw new IDGenerateException(e, ERROR_MSG, key);
                    }
                }).incrementAndGet();
    }

    /**
     * 获取所有分类的ID生成器
     *
     * @return 获取所有分类的ID生成器
     */
    public static Map<String, Long> allMaxIds() {
        Map<String, Long> map = new HashMap<>();
        ID_GENERATORS.forEach((k, v) -> map.put(k, v.get()));
        return map;
    }

}
