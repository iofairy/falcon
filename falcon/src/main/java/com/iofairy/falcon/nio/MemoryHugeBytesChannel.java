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
package com.iofairy.falcon.nio;

import com.iofairy.top.G;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SeekableByteChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.iofairy.validator.Preconditions.*;

/**
 * 支持超大内存操作的Channel
 *
 * @implSpec This class is <b>thread-safe</b>.
 * @since 0.4.2
 */
public class MemoryHugeBytesChannel implements SeekableByteChannel {
    /**
     * 字节数组初始化默认长度
     */
    private final static int BUFFER_INITIALIZED_SIZE = 8192;
    /**
     * 每个数据缓冲区存储最大长度
     */
    private final static int PER_BUFFER_MAX_SIZE = Integer.MAX_VALUE - 1024;
    private final List<byte[]> datas = new ArrayList<>();
    private boolean isOpen = true;
    /**
     * 数据指针
     */
    private long position = 0L;
    /**
     * 数据真实长度，不包含扩充时，数组末尾填充的 0。
     */
    private long size = 0L;
    /**
     * 整个 {@link #datas} 所有字节数，包含0，从而考虑是否需要扩容
     */
    private long capacity = 0L;

    public MemoryHugeBytesChannel(final byte[]... datas) {
        if (!G.isEmpty(datas)) {
            for (byte[] buf : datas) {
                if (!G.isEmpty(buf)) {
                    this.datas.add(buf);
                    this.size += buf.length;
                }
            }
        }
        this.capacity = this.size;
    }

    public MemoryHugeBytesChannel() {
        addData(initializeBytes());
    }

    @Override
    public synchronized int read(ByteBuffer dst) throws IOException {
        ensureOpen();

        int bytesNeedRead = dst.remaining();
        final long availableLength = size - position;
        if (availableLength <= 0) return -1;

        if (bytesNeedRead > availableLength) {
            bytesNeedRead = (int) availableLength;
        }

        Indexes indexes = getIndexesByPosition(datas, position, size);
        if (indexes == null) return -1;

        int indexOfList = indexes.indexOfList;
        int offset = indexes.indexOfArrayInList;
        byte[] currentData = datas.get(indexOfList);
        int currentRemainLength = currentData.length - offset;

        dst.put(currentData, offset, Math.min(bytesNeedRead, currentRemainLength));

        int newBytesNeedRead = bytesNeedRead - currentRemainLength;
        while (newBytesNeedRead > 0) {
            currentData = datas.get(++indexOfList);
            dst.put(currentData, 0, Math.min(newBytesNeedRead, currentData.length));
            newBytesNeedRead -= currentData.length;
        }

        position += bytesNeedRead;
        return bytesNeedRead;
    }

    @Override
    public synchronized int write(ByteBuffer src) throws IOException {
        ensureOpen();
        if (G.isEmpty(datas)) addData(initializeBytes());

        final int bytesNeedWrite = src.remaining();
        if (bytesNeedWrite == 0) return 0;

        final long availableLength = this.capacity - position;

        if (bytesNeedWrite > availableLength) {        // 需要扩容
            reCapacity(bytesNeedWrite);
        }

        Indexes indexes = getIndexesByPosition(datas, position, capacity);
        int indexOfList = indexes.indexOfList;
        int indexOfArray = indexes.indexOfArrayInList;
        byte[] bytes = datas.get(indexOfList);
        int currentLength = bytes.length - indexOfArray;

        src.get(bytes, indexOfArray, Math.min(currentLength, bytesNeedWrite));

        int newBytesNeedWrite = bytesNeedWrite - currentLength;
        while (newBytesNeedWrite > 0) {
            bytes = datas.get(++indexOfList);
            if (G.isEmpty(bytes)) continue;

            src.get(bytes, 0, Math.min(bytes.length, newBytesNeedWrite));
            newBytesNeedWrite -= bytes.length;
        }

        position += bytesNeedWrite;
        if (size < position) {
            size = position;
        }
        return bytesNeedWrite;
    }

    private void reCapacity(int bytesNeedWrite) {
        byte[] lastBytes = removeData(datas.size() - 1);

        while ((this.capacity + lastBytes.length - position) < bytesNeedWrite) {
            if (lastBytes.length == 0) {
                lastBytes = initializeBytes();
            } else {
                int length = lastBytes.length << 1;
                if (length > PER_BUFFER_MAX_SIZE || length < 0) {
                    addData(lastBytes);
                    lastBytes = initializeBytes();
                } else {
                    lastBytes = Arrays.copyOf(lastBytes, length);
                }
            }
        }

        addData(lastBytes);
    }

    @Override
    public long position() {
        return position;
    }

    @Override
    public synchronized SeekableByteChannel position(long newPosition) throws IOException {
        ensureOpen();
        checkArgument(newPosition < 0, "`newPosition` cannot be negative.");

        position = newPosition;
        return this;
    }

    @Override
    public long size() {
        return size;
    }

    @Override
    public synchronized SeekableByteChannel truncate(long newSize) throws IOException {
        ensureOpen();
        checkArgument(newSize < 0, "`newSize` cannot be negative.");

        if (newSize >= this.size) {
            if (this.position > newSize) {
                this.position = (int) newSize;
            }
            return this;
        }

        if (newSize == 0) {
            clearDatas();
            addData(initializeBytes());
            this.size = 0;
            this.position = 0;
            return this;
        }

        datas.removeIf(b -> b == null || b.length == 0);

        long preSumSize = 0;
        int index = 0;
        int remainLength = 0;
        for (int i = 0; i < datas.size(); i++) {
            long currentLength = preSumSize + datas.get(i).length;
            index = i;

            if (currentLength == newSize) break;

            if (currentLength > newSize) {
                remainLength = (int) (newSize - preSumSize);
                break;
            }

            preSumSize = currentLength;
        }

        final int finalIndex = index;
        datas.removeIf(b -> datas.indexOf(b) > finalIndex);
        if (remainLength > 0) {
            byte[] data = datas.remove(finalIndex);
            datas.add(Arrays.copyOf(data, remainLength));
        }

        this.capacity = newSize;
        this.size = newSize;
        this.position = newSize;
        return this;
    }

    private void clearDatas() {
        this.datas.clear();
        this.capacity = 0;
    }

    private byte[] removeData(int index) {
        byte[] bytes = datas.remove(index);
        this.capacity -= bytes.length;
        return bytes;
    }

    private void addData(byte[] bytes) {
        this.datas.add(bytes);
        this.capacity += bytes.length;
    }

    public byte[][] toByteArrays() {
        return toByteArrays(datas, size);
    }

    private static byte[][] toByteArrays(final List<byte[]> datas, final long size) {
        if (G.isEmpty(datas) || size <= 0) {
            return new byte[0][];
        }

        List<byte[]> bytes = new ArrayList<>();

        long preSumSize = 0;
        for (byte[] data : datas) {
            if (data == null) {
                continue;
            }
            long currentLength = data.length + preSumSize;
            if (currentLength < size) {
                bytes.add(data);
                preSumSize += data.length;
            } else {
                if (currentLength == size) {
                    bytes.add(data);
                } else {
                    bytes.add(Arrays.copyOf(data, (int) (size - preSumSize)));
                }
                break;
            }
        }
        byte[][] bs = new byte[bytes.size()][];
        for (int i = 0; i < bytes.size(); i++) {
            bs[i] = bytes.get(i);
        }
        return bs;
    }

    @Override
    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public void close() throws IOException {
        isOpen = false;
    }

    /**
     * 确保 Channel 是开启的状态
     *
     * @throws ClosedChannelException 当Channel是关闭时，抛出此异常
     */
    private void ensureOpen() throws ClosedChannelException {
        if (!isOpen()) {
            throw new ClosedChannelException();
        }
    }

    /**
     * 初始化一个字节数组
     *
     * @return 字节数组
     */
    private static byte[] initializeBytes() {
        return new byte[BUFFER_INITIALIZED_SIZE];
    }

    /**
     * 通过指定的position信息获取该position在List中的序号及在List中的Array中的序号
     *
     * @param datas          数据
     * @param globalPosition 全局位置信息
     * @param size           数据大小
     * @return Indexes对象
     */
    private Indexes getIndexesByPosition(final List<byte[]> datas, final long globalPosition, final long size) {
        if (G.isEmpty(datas) || globalPosition >= size) {
            return null;
        }

        int indexOfList = 0;
        long preSumSize = 0;
        for (byte[] data : datas) {
            if (preSumSize + data.length > globalPosition) {
                break;
            }

            indexOfList++;
            preSumSize += data.length;
        }

        int indexOfArrayInList = (int) (globalPosition - preSumSize);
        return Indexes.of(indexOfList, indexOfArrayInList);
    }

    static class Indexes {
        /**
         * List中的序号
         */
        int indexOfList;
        /**
         * 在Array（Array在List中）中的序号
         */
        int indexOfArrayInList;

        public Indexes(int indexOfList, int indexOfArrayInList) {
            this.indexOfList = indexOfList;
            this.indexOfArrayInList = indexOfArrayInList;
        }

        public static Indexes of(int indexOfList, int indexOfArrayInList) {
            return new Indexes(indexOfList, indexOfArrayInList);
        }

        @Override
        public String toString() {
            return "{" +
                    "indexOfList: " + indexOfList +
                    ", indexOfArrayInList: " + indexOfArrayInList +
                    '}';
        }
    }

}
