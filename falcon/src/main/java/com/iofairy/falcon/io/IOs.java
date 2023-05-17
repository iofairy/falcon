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
package com.iofairy.falcon.io;

import com.iofairy.top.G;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 * IO流工具类
 *
 * @since 0.3.6
 */
public class IOs {
    public static final int EOF = -1;
    public static final int DEFAULT_BUFFER_SIZE = 8192;


    /**
     * 将 {@code InputStream} 转为 {@code MultiByteArrayInputStream}
     *
     * @param inputStream 输入流
     * @return MultiByteArrayInputStream
     * @throws IOException 如果发生 I/O 异常，则抛出 {@code IOException}
     */
    public static MultiByteArrayInputStream toMultiBAIS(InputStream inputStream) throws IOException {
        Objects.requireNonNull(inputStream, "Parameter `inputStream` must be non-null!");

        MultiByteArrayOutputStream multiBaos = new MultiByteArrayOutputStream();
        copy(inputStream, multiBaos);
        return new MultiByteArrayInputStream(multiBaos.toByteArrays());
    }

    /**
     * 将 {@code InputStream} 转为 {@code MultiByteArrayOutputStream}
     *
     * @param inputStream 输入流
     * @return MultiByteArrayOutputStream
     * @throws IOException 如果发生 I/O 异常，则抛出 {@code IOException}
     * @since 0.4.2
     */
    public static MultiByteArrayOutputStream toMultiBAOS(InputStream inputStream) throws IOException {
        Objects.requireNonNull(inputStream, "Parameter `inputStream` must be non-null!");

        MultiByteArrayOutputStream multiBaos = new MultiByteArrayOutputStream();
        copy(inputStream, multiBaos);
        return multiBaos;
    }

    /**
     * 复制一个 {@code InputStream} 到 {@code OutputStream} 中
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     * @return 复制的字节数量
     * @throws IOException 如果发生 I/O 异常，则抛出 {@code IOException}
     */
    public static long copy(final InputStream inputStream, final OutputStream outputStream) throws IOException {
        return copy(inputStream, outputStream, new byte[DEFAULT_BUFFER_SIZE]);
    }

    /**
     * 复制一个 {@code InputStream} 到 {@code OutputStream} 中
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     * @param bufferSize   用于复制的缓冲区大小
     * @return 复制的字节数量
     * @throws IOException 如果发生 I/O 异常，则抛出 {@code IOException}
     */
    public static long copy(final InputStream inputStream, final OutputStream outputStream, final int bufferSize) throws IOException {
        return copy(inputStream, outputStream, new byte[bufferSize]);
    }

    /**
     * 复制一个 {@code InputStream} 到 {@code OutputStream} 中
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     * @param buffer       用于复制的缓冲区
     * @return 复制的字节数量
     * @throws IOException 如果发生 I/O 异常，则抛出 {@code IOException}
     */
    public static long copy(final InputStream inputStream, final OutputStream outputStream, final byte[] buffer) throws IOException {
        if (G.hasNull(inputStream, outputStream)) throw new RuntimeException("Parameters `inputStream`, `outputStream` must be non-null!");

        int n;
        long count = 0;
        for (; (n = inputStream.read(buffer)) != EOF; count += n) {
            outputStream.write(buffer, 0, n);
        }

        return count;
    }

}
