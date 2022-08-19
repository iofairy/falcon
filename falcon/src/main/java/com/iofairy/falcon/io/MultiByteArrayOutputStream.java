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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements an output stream in which the data is written into a byte array.
 * The buffer automatically grows as data is written to it.
 * The data can be retrieved using <code>toByteArrays()</code> and <code>toString()</code>.
 * <p>
 * Closing a <tt>MultiByteArrayOutputStream</tt> has no effect.
 * The methods in this class can be called after the stream has been closed without generating an <tt>IOException</tt>.
 *
 * @implSpec This class is <b>thread-safe</b>.
 * @since 0.3.4
 */
public class MultiByteArrayOutputStream extends OutputStream {

    /**
     * The buffer where data is stored.
     */
    protected List<ByteArrayOutputStream> baosList = new ArrayList<>();

    /**
     * The number of valid bytes in the buffer.
     */
    protected long count;

    private final static int BUFFER_INITIALIZED_SIZE = 1024;

    /**
     * Creates a new byte array output stream. The buffer capacity is
     * initially 10240 bytes, though its size increases if necessary.
     */
    public MultiByteArrayOutputStream() {
        this(BUFFER_INITIALIZED_SIZE);
    }

    /**
     * Creates a new byte array output stream, with a buffer capacity of
     * the specified size, in bytes.
     *
     * @param size the initial size.
     * @throws IllegalArgumentException if size is negative.
     */
    public MultiByteArrayOutputStream(int size) {
        baosList.add(new ByteArrayOutputStream(size));
    }

    /**
     * Writes the specified byte to this byte array output stream.
     *
     * @param b the byte to be written.
     */
    public synchronized void write(int b) {
        try {
            baosList.get(baosList.size() - 1).write(b);
        } catch (OutOfMemoryError error) {
            baosList.add(new ByteArrayOutputStream(BUFFER_INITIALIZED_SIZE));
            baosList.get(baosList.size() - 1).write(b);
        }

        count += 1;
    }

    /**
     * Writes <code>len</code> bytes from the specified byte array
     * starting at offset <code>off</code> to this byte array output stream.
     *
     * @param b   the data.
     * @param off the start offset in the data.
     * @param len the number of bytes to write.
     */
    public synchronized void write(byte[] b, int off, int len) {
        if ((off < 0) || (off > b.length) || (len < 0) || ((off + len) - b.length > 0)) throw new IndexOutOfBoundsException();

        try {
            baosList.get(baosList.size() - 1).write(b, off, len);
        } catch (OutOfMemoryError error) {
            baosList.add(new ByteArrayOutputStream(BUFFER_INITIALIZED_SIZE));
            baosList.get(baosList.size() - 1).write(b, off, len);
        }

        count += len;
    }

    /**
     * Resets the <code>count</code> field of this byte array output
     * stream to zero, so that all currently accumulated output in the
     * output stream is discarded. The output stream can be used again,
     * reusing the already allocated buffer space.
     *
     * @see MultiByteArrayOutputStream#count
     */
    public synchronized void reset() {
        count = 0;
        baosList.forEach(ByteArrayOutputStream::reset);
        baosList.clear();
        baosList.add(new ByteArrayOutputStream(BUFFER_INITIALIZED_SIZE));
    }

    /**
     * Creates a newly allocated byte array. Its size is the current
     * size of this output stream and the valid contents of the buffer
     * have been copied into it.
     *
     * @return the current contents of this output stream, as a byte arrays.
     * @see MultiByteArrayOutputStream#size()
     */
    public synchronized byte[][] toByteArrays() {
        byte[][] byteArrays = new byte[baosList.size()][];
        for (int i = 0; i < baosList.size(); i++) {
            byteArrays[i] = baosList.get(i).toByteArray();
        }
        return byteArrays;
    }

    /**
     * Returns the current size of the buffer.
     *
     * @return the value of the <code>count</code> field, which is the number
     * of valid bytes in this output stream.
     * @see MultiByteArrayOutputStream#count
     */
    public synchronized long size() {
        return count;
    }

    /**
     * Closing a <tt>MultiByteArrayOutputStream</tt> has no effect. The methods in
     * this class can be called after the stream has been closed without
     * generating an <tt>IOException</tt>.
     */
    public void close() throws IOException {
        Exception e = null;
        for (ByteArrayOutputStream baos : baosList) {
            try {
                baos.close();
            } catch (Exception ex) {
                e = ex;
            }
        }
        if (e != null) throw new IOException(e);
    }

}
