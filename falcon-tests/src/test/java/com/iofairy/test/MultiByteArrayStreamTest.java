package com.iofairy.test;

import com.iofairy.falcon.io.MultiByteArrayInputStream;
import com.iofairy.falcon.io.MultiByteArrayOutputStream;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author GG
 * @version 1.0
 * @date 2022/8/18 21:13
 */
public class MultiByteArrayStreamTest {
    private static final int EOF = -1;
    private static final char END_CHAR = '✐';

    @Test
    public void testReadAndMarkAndReset() {
        // String s = "Hello, this is MultiByteArrayInputStream Unit Test! ";
        byte[] bytes1 = "Hello".getBytes();
        byte[] bytes2 = "".getBytes();
        byte[] bytes3 = ", th".getBytes();
        byte[] bytes4 = "".getBytes();
        byte[] bytes5 = "i".getBytes();
        byte[] bytes6 = "s is Mu".getBytes();
        byte[] bytes7 = "ltiByteArrayInputStream".getBytes();
        byte[] bytes8 = " Uni".getBytes();
        byte[] bytes9 = "t Test! ".getBytes();

        MultiByteArrayInputStream multiBais = new MultiByteArrayInputStream(bytes1, bytes2, bytes3, bytes4, bytes5, bytes6, bytes7, bytes8, bytes9);
        int read1 = multiBais.read();
        byteToString(read1, multiBais);  // H    pos: 1, avail: 51
        byteToStringAndAssert(read1, multiBais, "H", 51);

        byte[] readBytes = new byte[30];
        multiBais.read(readBytes, 5, 6);
        byteToString(readBytes, multiBais);         // \0\0\0\0\0ello, \0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0  pos: 7, avail: 45
        byteToStringAndAssert(readBytes, multiBais, "\0\0\0\0\0ello, \0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0", 45);

        multiBais.mark(0);

        multiBais.read(readBytes, 4, 3);
        byteToString(readBytes, multiBais);         // \0\0\0\0thilo, \0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0  pos: 10, avail: 42
        byteToStringAndAssert(readBytes, multiBais, "\0\0\0\0thilo, \0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0", 42);

        int read2 = multiBais.read();
        byteToString(read2, multiBais);  // s  pos: 11, avail: 41
        byteToStringAndAssert(read2, multiBais, "s", 41);

        multiBais.reset();

        multiBais.read(readBytes, 4, 3);
        byteToString(readBytes, multiBais);         // \0\0\0\0thilo, \0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0  pos: 10, avail: 42
        byteToStringAndAssert(readBytes, multiBais, "\0\0\0\0thilo, \0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0", 42);

        multiBais.read(readBytes, 7, 10);
        byteToString(readBytes, multiBais);         // \0\0\0\0this is Multi\0\0\0\0\0\0\0\0\0\0\0\0\0  pos: 20, avail: 32
        byteToStringAndAssert(readBytes, multiBais, "\0\0\0\0this is Multi\0\0\0\0\0\0\0\0\0\0\0\0\0", 32);

        // multiBais.mark(0);

        multiBais.markStream(0).read(readBytes, 0, 11);
        byteToString(readBytes, multiBais);         // ByteArrayIn Multi\0\0\0\0\0\0\0\0\0\0\0\0\0  pos: 31, avail: 21
        byteToStringAndAssert(readBytes, multiBais, "ByteArrayIn Multi\0\0\0\0\0\0\0\0\0\0\0\0\0", 21);

        // multiBais.reset();

        multiBais.resetStream().read(readBytes, 5, 11);
        byteToString(readBytes, multiBais);         // ByteAByteArrayIni\0\0\0\0\0\0\0\0\0\0\0\0\0  pos: 31, avail: 21
        byteToStringAndAssert(readBytes, multiBais, "ByteAByteArrayIni\0\0\0\0\0\0\0\0\0\0\0\0\0", 21);

        multiBais.read(readBytes, 9, 15);
        byteToString(readBytes, multiBais);         // ByteAByteputStream Unit \0\0\0\0\0\0  pos: 46, avail: 6
        byteToStringAndAssert(readBytes, multiBais, "ByteAByteputStream Unit \0\0\0\0\0\0", 6);

        assertThrows(IndexOutOfBoundsException.class, () -> multiBais.read(readBytes, 20, 12));

        multiBais.read(readBytes, 20, 9);
        byteToString(readBytes, multiBais);         // ByteAByteputStream UTest! \0\0\0\0  pos: 52, avail: 0
        byteToStringAndAssert(readBytes, multiBais, "ByteAByteputStream UTest! \0\0\0\0", 0);

        int read3 = multiBais.read();
        byteToStringAndAssert(read3, multiBais, String.valueOf(END_CHAR), 0);
    }

    @Test
    public void testReadAndMarkAndReset1() {
        // String s = "Hello, this is MultiByteArrayInputStream Unit Test! ";
        byte[] bytes1 = "Hello".getBytes();
        byte[] bytes2 = "".getBytes();
        byte[] bytes3 = ", th".getBytes();
        byte[] bytes4 = "".getBytes();
        byte[] bytes5 = "i".getBytes();
        byte[] bytes6 = "s is Mu".getBytes();
        byte[] bytes7 = "ltiByteArrayInputStream".getBytes();
        byte[] bytes8 = " Uni".getBytes();
        byte[] bytes9 = "t Test! ".getBytes();

        MultiByteArrayInputStream multiBais = new MultiByteArrayInputStream(bytes1, bytes2, bytes3, bytes4, bytes5, bytes6, bytes7, bytes8, bytes9);
        byte[] readBytes = new byte[69];
        assertThrows(IndexOutOfBoundsException.class, () -> multiBais.read(readBytes, 10, 60));
        multiBais.read(readBytes, 10, 56);
        byteToString(readBytes, multiBais);
        byteToStringAndAssert(readBytes, multiBais, "\0\0\0\0\0\0\0\0\0\0Hello, this is MultiByteArrayInputStream Unit Test! \0\0\0\0\0\0\0", 0);
        assertEquals(-1, multiBais.read());

    }

    @Test
    public void testSkip() {
        // String s = "Hello, this is MultiByteArrayInputStream Unit Test! ";
        byte[] bytes1 = "Hello".getBytes();
        byte[] bytes2 = "".getBytes();
        byte[] bytes3 = ", th".getBytes();
        byte[] bytes4 = "".getBytes();
        byte[] bytes5 = "i".getBytes();
        byte[] bytes6 = "s is Mu".getBytes();
        byte[] bytes7 = "ltiByteArrayInputStream".getBytes();
        byte[] bytes8 = " Uni".getBytes();
        byte[] bytes9 = "t Test! ".getBytes();

        MultiByteArrayInputStream multiBais = new MultiByteArrayInputStream(bytes1, bytes2, bytes3, bytes4, bytes5, bytes6, bytes7, bytes8, bytes9);
        byte[] bytes = new byte[60];
        for (int i = 0; i < 60; i++) {
            // multiBais.mark(0);
            multiBais.markStream(0).skip(i);    // 这里的 mark 操作其实也没什么作用，因为后面的 reset 已经重置位置了
            int read = multiBais.read();
            bytes[i] = read == -1 ? 0 : (byte) read;
            skipByteToString(read, multiBais, i);
            multiBais.resetStream();
        }
        // printBytes(bytes);
        assertEquals("Hello, this is MultiByteArrayInputStream Unit Test! \0\0\0\0\0\0\0\0", new String(bytes));
    }

    private void byteToString(byte[] bytes, MultiByteArrayInputStream multiBais) {
        printBytes(bytes);
        String byteString = new String(bytes);
        System.out.println();
        System.out.println(multiBais.available());
    }

    private void byteToStringAndAssert(byte[] bytes, MultiByteArrayInputStream multiBais, String expected, int expectedAvailable) {
        String byteString = new String(bytes);
        int available = multiBais.available();
        assertEquals(expected, byteString);
        assertEquals(expectedAvailable, available);
    }

    private static void printBytes(byte[] bytes) {
        for (byte aByte : bytes) {
            if (aByte == 0) {
                System.out.print('\\');
                System.out.print(0);
            } else if (aByte == EOF) {
                System.out.print(END_CHAR);
            } else {
                System.out.print((char) aByte);
            }
        }
    }

    private void byteToString(int i, MultiByteArrayInputStream multiBais) {
        if (i == EOF) {
            System.out.println(i + "---" + multiBais.available());
        } else {
            System.out.println((char) i + "---" + multiBais.available());
        }
    }

    private void byteToStringAndAssert(int i, MultiByteArrayInputStream multiBais, String expected, int expectedAvailable) {
        String byteString = String.valueOf(i == -1 ? END_CHAR : (char) i);
        int available = multiBais.available();
        assertEquals(expected, byteString);
        assertEquals(expectedAvailable, available);
    }

    private void skipByteToString(int i, MultiByteArrayInputStream multiBais, long skip) {
        if (i == -1) {
            System.out.println(END_CHAR + "---" + multiBais.available());
        } else {
            System.out.println((char) i + "---" + multiBais.available() + "---" + skip);
        }
    }

    @Test
    public void testException() {
        assertThrows(NullPointerException.class, () -> new MultiByteArrayInputStream(null));
        assertThrows(NullPointerException.class, () -> new MultiByteArrayInputStream((byte[]) null));
        assertThrows(NullPointerException.class, () -> new MultiByteArrayInputStream((byte[][]) null));
        assertThrows(NullPointerException.class, () -> new MultiByteArrayInputStream(new byte[0], null));
        MultiByteArrayInputStream multiBais1 = new MultiByteArrayInputStream(new byte[0]);
        byte[][] bufs1 = {new byte[0], new byte[0]};
        MultiByteArrayInputStream multiBais2 = new MultiByteArrayInputStream(bufs1);
        byte[][] bufs2 = {new byte[0], null};
        assertThrows(NullPointerException.class, () -> new MultiByteArrayInputStream(bufs2));
    }

    @Test
    public void testWrite() {
        // String s = "Hello, this is MultiByteArrayInputStream Unit Test! ";
        byte[] bytes1 = "Hello".getBytes();
        byte[] bytes2 = "".getBytes();
        byte[] bytes3 = ", th".getBytes();
        byte[] bytes4 = "".getBytes();
        byte[] bytes5 = "i".getBytes();
        byte[] bytes6 = "s is Mu".getBytes();
        byte[] bytes7 = "ltiByteArrayInputStream".getBytes();
        byte[] bytes8 = " Uni".getBytes();
        byte[] bytes9 = "t Test! ".getBytes();

        MultiByteArrayInputStream multiBais1 = new MultiByteArrayInputStream(bytes1, bytes2, bytes3, bytes4, bytes5, bytes6, bytes7, bytes8, bytes9);
        MultiByteArrayInputStream multiBais2 = new MultiByteArrayInputStream("This is unit test: Reset stream and put into new bytes! ".getBytes());

        MultiByteArrayOutputStream multiBaos = new MultiByteArrayOutputStream();
        int read;
        while ((read = multiBais1.read()) != -1) {
            multiBaos.write(read);
        }
        System.out.println("multiBaos-1: " + new String(multiBaos.toByteArrays()[0]));
        assertEquals("Hello, this is MultiByteArrayInputStream Unit Test! ", new String(multiBaos.toByteArrays()[0]));
        multiBaos.reset();
        System.out.println("multiBaos-2: " + new String(multiBaos.toByteArrays()[0]));
        assertEquals("", new String(multiBaos.toByteArrays()[0]));
        try {
            multiBaos.write(bytes1);
            multiBaos.write(bytes2);
            multiBaos.write(bytes3);
            multiBaos.write(bytes4);
            multiBaos.write(bytes5);
            multiBaos.write(bytes6);
            multiBaos.write(bytes7);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("multiBaos-3: " + new String(multiBaos.toByteArrays()[0]));
        assertEquals("Hello, this is MultiByteArrayInputStream", new String(multiBaos.toByteArrays()[0]));
        // multiBaos.reset();
        System.out.println("multiBaos-4: " + new String(multiBaos.resetStream().toByteArrays()[0]));
        assertEquals("", new String(multiBaos.toByteArrays()[0]));

        while ((read = multiBais2.read()) != -1) {
            multiBaos.write(read);
        }
        System.out.println("multiBaos-5: " + new String(multiBaos.toByteArrays()[0]));
        assertEquals("This is unit test: Reset stream and put into new bytes! ", new String(multiBaos.toByteArrays()[0]));
    }
}
