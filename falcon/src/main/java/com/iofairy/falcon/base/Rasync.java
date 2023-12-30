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
package com.iofairy.falcon.base;

/**
 * 异步调用结果（Asynchronous return Result）
 *
 * @since 0.4.15
 */
public class Rasync<RES> {
    /**
     * 异步调用返回结果
     */
    public RES res;
    /**
     * 异步调用时的当前线程信息
     */
    public Thread currentThread = Thread.currentThread();
    /**
     * 异步调用异常信息
     */
    public Throwable throwable;

    public Rasync() {
    }

    public Rasync(RES res) {
        this.res = res;
    }

    public Rasync(RES res, Throwable throwable) {
        this.res = res;
        this.throwable = throwable;
    }

    public static <RES> Rasync<RES> of() {
        return new Rasync<>();
    }

    public static <RES> Rasync<RES> of(RES res) {
        return new Rasync<>(res);
    }

    public static <RES> Rasync<RES> of(RES res, Throwable throwable) {
        return new Rasync<>(res, throwable);
    }

    public RES getRes() {
        return res;
    }

    public void setRes(RES res) {
        this.res = res;
    }

    public Thread getCurrentThread() {
        return currentThread;
    }

    public void setCurrentThread(Thread currentThread) {
        this.currentThread = currentThread;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public String toString() {
        return "Rasync{" +
                "res=" + res +
                ", currentThread=" + currentThread +
                ", throwable=" + throwable +
                '}';
    }
}
