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

import java.io.*;
import java.util.*;

import static com.iofairy.falcon.misc.Preconditions.*;

/**
 * A <code>MultiByteArrayInputStream</code> contains an internal buffer that contains bytes that
 * may be read from the stream.
 * An internal counter keeps track of the next byte to be supplied by the <code>read</code> method.
 * <p>
 * Closing a <tt>MultiByteArrayInputStream</tt> has no effect.
 * The methods in this class can be called after the stream has been closed without generating an <tt>IOException</tt>.
 *
 * @implSpec This class is <b>thread-safe</b>.
 * @since 0.3.4
 */
public class MultiByteArrayInputStream extends InputStream {

    /**
     * ByteArrayInputStream List
     */
    protected List<ByteArrayInputStream> baisList = new ArrayList<>();

    /**
     * The index of the next character to read from the input stream buffer.
     * This value should always be nonnegative and not larger than the value of <code>count</code>.
     * The next byte to be read from the input stream buffer will be <code>buf[pos]</code>.
     */
    protected long pos;

    /**
     * The currently marked position in the stream.
     * MultiByteArrayInputStream objects are marked at position zero by default when constructed.
     * They may be marked at another position within the buffer by the <code>mark()</code> method.
     * The current buffer position is set to this point by the <code>reset()</code> method.
     * <p>
     * If no mark has been set, then the value of mark is the offset
     * passed to the constructor (or 0 if the offset was not supplied).
     */
    protected long mark = 0;

    /**
     * The index one greater than the last valid character in the input stream buffer.
     * This value should always be nonnegative and not larger than the length of <code>buf</code>.
     * It  is one greater than the position of the last byte within <code>buf</code> that
     * can ever be read  from the input stream buffer.
     */
    protected long count;

    /**
     * Creates a <code>MultiByteArrayInputStream</code> so that
     * it uses <code>buf</code> as its buffer array. The buffer array is not copied.
     * The initial value of <code>pos</code> is <code>0</code> and the initial value
     * of <code>count</code> is the length of <code>buf</code>.
     *
     * @param bufs the input buffers.
     */
    public MultiByteArrayInputStream(byte[]... bufs) {
        checkConditionNPE(bufs == null || Arrays.stream(bufs).anyMatch(Objects::isNull),
                "Parameter `bufs` must be non-null, and any elements in the `bufs` must be non-null too! ");

        long countSum = 0;
        if (bufs.length == 0) {
            baisList.add(new ByteArrayInputStream(new byte[0]));
        } else {
            for (byte[] buf : bufs) {
                baisList.add(new ByteArrayInputStream(buf));
                countSum += buf.length;
            }
        }

        this.pos = 0;
        this.count = countSum;
    }

    /**
     * Reads the next byte of data from this input stream.
     * The value byte is returned as an <code>int</code> in the range <code>0</code> to <code>255</code>.
     * If no byte is available because the end of the stream has been reached, the value <code>-1</code> is returned.
     * <p>
     * This <code>read</code> method cannot block.
     *
     * @return the next byte of data, or <code>-1</code> if the end of the stream has been reached.
     */
    public synchronized int read() {
        if (pos < count) {
            pos++;
            return baisList.get(findFirstAvailableGreaterThan0()).read();
        } else {
            return -1;
        }
    }

    /**
     * Reads up to <code>len</code> bytes of data into an array of bytes from this input stream.
     * If <code>pos</code> equals <code>count</code>, then <code>-1</code> is returned to indicate end of file.
     * Otherwise, the  number <code>k</code> of bytes read is equal to the smaller of <code>len</code> and <code>count-pos</code>.
     * If <code>k</code> is positive, then bytes <code>buf[pos]</code> through <code>buf[pos+k-1]</code> are copied
     * into <code>b[off]</code>  through <code>b[off+k-1]</code> in the manner performed by <code>System.arraycopy</code>.
     * The value <code>k</code> is added into <code>pos</code> and <code>k</code> is returned.
     * <p>
     * This <code>read</code> method cannot block.
     *
     * @param b   the buffer into which the data is read.
     * @param off the start offset in the destination array <code>b</code>
     * @param len the maximum number of bytes read.
     * @return the total number of bytes read into the buffer, or <code>-1</code> if there is no more data because the end of
     * the stream has been reached.
     * @throws NullPointerException      If <code>b</code> is <code>null</code>.
     * @throws IndexOutOfBoundsException If <code>off</code> is negative, <code>len</code> is negative,
     *                                   or <code>len</code> is greater than <code>b.length - off</code>
     */
    public synchronized int read(byte[] b, int off, int len) {
        checkNullNPE(b, args("b"));

        if (off < 0 || len < 0 || len > b.length - off) throw new IndexOutOfBoundsException();

        if (pos >= count) return -1;

        long totalAvailable = count - pos;
        if (len > totalAvailable) len = (int) totalAvailable;

        if (len <= 0) return 0;

        int tmpLen = len;
        while (true) {
            int firstAvailableGreaterThan0 = findFirstAvailableGreaterThan0();
            if (firstAvailableGreaterThan0 == -1) break;

            ByteArrayInputStream bais = baisList.get(firstAvailableGreaterThan0);
            int available = bais.available();   // 这个 available 一定会 大于0

            if (tmpLen > available) {
                int readLen = bais.read(b, off, available);
                off += readLen;
                tmpLen -= readLen;
            } else {    // tmpLen <= available
                bais.read(b, off, tmpLen);
                break;
            }
        }

        pos += len;
        return len;
    }

    /**
     * 找 {@link #baisList} 列表中，第一个 {@link ByteArrayInputStream#available()} 大于0的 {@link ByteArrayInputStream} 在列表中的序号
     *
     * @return 第一个 {@link ByteArrayInputStream#available()} 大于0的 {@link ByteArrayInputStream} 在列表中的序号
     */
    private int findFirstAvailableGreaterThan0() {
        for (int i = 0; i < baisList.size(); i++) {
            if (baisList.get(i).available() > 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Skips <code>n</code> bytes of input from this input stream.
     * Fewer bytes might be skipped if the end of the input stream is reached.
     * The actual number <code>k</code> of bytes to be skipped is equal to
     * the smaller of <code>n</code> and  <code>count-pos</code>.
     * The value <code>k</code> is added into <code>pos</code> and <code>k</code> is returned.
     *
     * @param n the number of bytes to be skipped.
     * @return the actual number of bytes skipped.
     */
    public synchronized long skip(long n) {
        long k = count - pos;
        if (n < k) k = n < 0 ? 0 : n;

        long tmpK = k;
        for (ByteArrayInputStream bais : baisList) {
            if (tmpK < 0) break;
            int available = bais.available();
            if (available <= 0) continue;

            if (tmpK <= available) {
                bais.skip(tmpK);
                break;
            } else {
                tmpK = tmpK - bais.skip(available);
            }
        }

        pos += k;
        return k;
    }

    /**
     * 获取剩余可读取的字节数。当剩余可读取的字节数超过 {@link Integer#MAX_VALUE} - 8 时，返回 {@link Integer#MAX_VALUE} - 8，否则返回实际的数量。
     *
     * @return 返回剩余可读取的字节数。当剩余可读取的字节数超过 {@link Integer#MAX_VALUE} - 8 时，返回 {@link Integer#MAX_VALUE} - 8，否则返回实际的数量。
     */
    public synchronized int available() {
        long availableBytes = count - pos;
        return availableBytes >= Integer.MAX_VALUE - 8 ? Integer.MAX_VALUE - 8 : (int) availableBytes;
    }

    /**
     * Returns the number of remaining bytes that can be read (or skipped over)
     * from this input stream.
     * <p>
     * The value returned is <code>count&nbsp;- pos</code>,
     * which is the number of bytes remaining to be read from the input buffer.
     *
     * @return the number of remaining bytes that can be read (or skipped
     * over) from this input stream without blocking.
     */
    public synchronized long availableBytes() {
        return count - pos;
    }

    /**
     * Tests if this <code>InputStream</code> supports mark/reset. The
     * <code>markSupported</code> method of <code>MultiByteArrayInputStream</code>
     * always returns <code>true</code>.
     *
     * @return {@code true} if supported, otherwise, {@code false}
     */
    public boolean markSupported() {
        return true;
    }

    /**
     * Set the current marked position in the stream.
     * MultiByteArrayInputStream objects are marked at position zero by default when constructed.
     * They may be marked at another position within the buffer by this method.
     * <p>
     * If no mark has been set, then the value of the mark is the offset passed to
     * the constructor (or 0 if the offset was not supplied).
     *
     * <p> Note: The <code>readAheadLimit</code> for this class
     * has no meaning.
     */
    public synchronized void mark(int readAheadLimit) {
        mark = pos;
        baisList.forEach(bais -> bais.mark(readAheadLimit));
    }

    /**
     * Set the current marked position in the stream.
     * MultiByteArrayInputStream objects are marked at position zero by default when constructed.
     * They may be marked at another position within the buffer by this method.
     * <p>
     * If no mark has been set, then the value of the mark is the offset passed to
     * the constructor (or 0 if the offset was not supplied).
     *
     * <p> Note: The <code>readAheadLimit</code> for this class
     * has no meaning.
     *
     * @return this
     * @since 0.5.11
     */
    public MultiByteArrayInputStream markStream(int readAheadLimit) {
        mark(readAheadLimit);
        return this;
    }

    /**
     * Resets the buffer to the marked position.  The marked position
     * is 0 unless another position was marked or an offset was specified
     * in the constructor.
     */
    public synchronized void reset() {
        pos = mark;
        baisList.forEach(ByteArrayInputStream::reset);
    }

    /**
     * Resets the buffer to the marked position.  The marked position
     * is 0 unless another position was marked or an offset was specified
     * in the constructor.
     *
     * @return this
     * @since 0.5.11
     */
    public MultiByteArrayInputStream resetStream() {
        reset();
        return this;
    }

    /**
     * Closing a <tt>MultiByteArrayInputStream</tt> has no effect. The methods in
     * this class can be called after the stream has been closed without
     * generating an <tt>IOException</tt>.
     */
    public void close() throws IOException {
        Exception e = null;
        for (ByteArrayInputStream bais : baisList) {
            try {
                bais.close();
            } catch (Exception ex) {
                e = ex;
            }
        }
        if (e != null) throw new IOException(e);
    }

}
