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

import com.iofairy.tcf.Close;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.iofairy.falcon.misc.Preconditions.*;

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
        checkNullNPE(inputStream, args("inputStream"));

        try (MultiByteArrayOutputStream multiBaos = new MultiByteArrayOutputStream()) {
            copy(inputStream, multiBaos);
            return new MultiByteArrayInputStream(multiBaos.toByteArrays());
        }
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
        checkNullNPE(inputStream, args("inputStream"));

        MultiByteArrayOutputStream multiBaos = null;
        try {
            multiBaos = new MultiByteArrayOutputStream();
            copy(inputStream, multiBaos);
            return multiBaos;
        } catch (Exception e) {
            Close.close(multiBaos);
            throw new IOException(e);
        }
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
     * 从 {@code InputStream} 中读取指定长度的字节数到 {@code OutputStream} 中
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     * @param copyLength   复制的字节长度
     * @return 复制的字节数量
     * @throws IOException 如果发生 I/O 异常，则抛出 {@code IOException}
     * @since 0.4.6
     */
    public static long copy(final InputStream inputStream, final OutputStream outputStream, long copyLength) throws IOException {
        checkHasNullNPE(args(inputStream, outputStream), args("inputStream", "outputStream"));

        if (copyLength <= 0) return 0;

        byte[] buffer = new byte[(int) Math.min(DEFAULT_BUFFER_SIZE, copyLength)];

        long count = 0;
        int n;
        while ((n = inputStream.read(buffer)) != EOF && copyLength > 0) {
            outputStream.write(buffer, 0, n);

            count += n;
            copyLength -= n;
            if (copyLength < 0) copyLength = 0;
            buffer = new byte[(int) Math.min(DEFAULT_BUFFER_SIZE, copyLength)];
        }
        return count;
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
        checkHasNullNPE(args(inputStream, outputStream, buffer), args("inputStream", "outputStream", "buffer"));

        int n;
        long count = 0;
        for (; (n = inputStream.read(buffer)) != EOF; count += n) {
            outputStream.write(buffer, 0, n);
        }

        return count;
    }

    /**
     * 从输入流中读取字节数组<br>
     * <b>注：内部会自动关闭 InputStream 输入流</b>
     *
     * @param is 输入流
     * @return 二维字节数组
     * @throws IOException 如果发生 I/O 异常，则抛出 {@code IOException}
     * @see #readBytes(InputStream, Long, boolean)
     * @since 0.4.6
     */
    public static byte[][] readBytes(InputStream is) throws IOException {
        return readBytes(is, null, true);
    }

    /**
     * 从输入流中读取字节数组<br>
     * <b>注：内部会自动关闭 InputStream 输入流</b>
     *
     * @param is         输入流
     * @param readLength 读取字节长度
     * @return 二维字节数组
     * @throws IOException 如果发生 I/O 异常，则抛出 {@code IOException}
     * @see #readBytes(InputStream, Long, boolean)
     * @since 0.4.6
     */
    public static byte[][] readBytes(InputStream is, Long readLength) throws IOException {
        return readBytes(is, readLength, true);
    }

    /**
     * 从输入流中读取字节数组
     *
     * @param is      输入流
     * @param isClose 是否关闭输入流
     * @return 二维字节数组
     * @throws IOException 如果发生 I/O 异常，则抛出 {@code IOException}
     * @see #readBytes(InputStream, Long, boolean)
     * @since 0.4.6
     */
    public static byte[][] readBytes(InputStream is, boolean isClose) throws IOException {
        return readBytes(is, null, isClose);
    }

    /**
     * 从输入流中读取字节数组
     *
     * @param is         输入流
     * @param readLength 读取字节长度
     * @param isClose    是否关闭输入流
     * @return 二维字节数组
     * @throws IOException 如果发生 I/O 异常，则抛出 {@code IOException}
     * @since 0.4.6
     */
    public static byte[][] readBytes(InputStream is, Long readLength, boolean isClose) throws IOException {
        checkNullNPE(is, args("is"));

        MultiByteArrayOutputStream baos = null;
        try {
            baos = new MultiByteArrayOutputStream();

            if (readLength == null) {
                copy(is, baos, new byte[DEFAULT_BUFFER_SIZE]);
            } else {
                copy(is, baos, readLength);
            }

            return baos.toByteArrays();
        } finally {
            if (isClose) {
                Close.close(is);
            }
            Close.close(baos);
        }
    }

}
