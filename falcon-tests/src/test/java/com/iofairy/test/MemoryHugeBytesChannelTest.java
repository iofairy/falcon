package com.iofairy.test;

import com.iofairy.falcon.nio.MemoryHugeBytesChannel;
import com.iofairy.falcon.string.Strings;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author GG
 * @version 1.0
 * @date 2023/5/17 21:00
 */
public class MemoryHugeBytesChannelTest {
    @Test
    public void testChannel1() throws IOException {
        // String s = "Hello, this is MemoryHugeBytesChannel Unit Test! ";
        byte[] bytes1 = "Hello".getBytes();
        byte[] bytes2 = "".getBytes();
        byte[] bytes3 = ", th".getBytes();
        byte[] bytes4 = "".getBytes();
        byte[] bytes5 = "i".getBytes();
        byte[] bytes6 = "s is Mem".getBytes();
        byte[] bytes7 = "oryHugeBytesChannel".getBytes();
        byte[] bytes8 = " Uni".getBytes();
        byte[] bytes9 = new byte[0];
        byte[] bytes10 = "t Test! ".getBytes();

        MemoryHugeBytesChannel hugeBytesChannel = new MemoryHugeBytesChannel(bytes1, bytes2, bytes3, bytes4, bytes5, bytes6);

        MemoryHugeBytesChannel outChannel = new MemoryHugeBytesChannel();
        ByteBuffer buf = ByteBuffer.allocate(1024);
        while (hugeBytesChannel.read(buf) != -1) {
            buf.flip();
            outChannel.write(buf);
            buf.clear();
        }

        String s1 = Strings.toString(outChannel.toByteArrays());
        System.out.println(s1);
        assertEquals(s1, "Hello, this is Mem");

        hugeBytesChannel.write(ByteBuffer.wrap(bytes7));
        hugeBytesChannel.write(ByteBuffer.wrap(bytes8));
        hugeBytesChannel.write(ByteBuffer.wrap(bytes9));
        hugeBytesChannel.write(ByteBuffer.wrap(bytes10));
        String s2 = Strings.toString(hugeBytesChannel.toByteArrays());
        System.out.println(s2);
        assertEquals(s2, "Hello, this is MemoryHugeBytesChannel Unit Test! ");

        hugeBytesChannel.truncate(5);
        String s3 = Strings.toString(hugeBytesChannel.toByteArrays());
        System.out.println(s3);
        assertEquals(s3, "Hello");

        try {
            hugeBytesChannel.position(-1);
            throwException();
        } catch (Exception e) {
            assertSame(e.getClass(), IllegalArgumentException.class);
            System.out.println(e.getMessage());
            assertEquals(e.getMessage(), "`newPosition` cannot be negative.");
        }

    }

    private void throwException() {
        throw new RuntimeException();
    }
}
