package com.iofairy.test;

import com.iofairy.falcon.io.IOs;
import com.iofairy.falcon.io.MultiByteArrayInputStream;
import com.iofairy.falcon.io.MultiByteArrayOutputStream;
import com.iofairy.falcon.string.Strings;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author GG
 * @version 1.0
 */
public class IOsTest {
    @Test
    public void testToMultiBAIS() {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(new byte[0]);
            MultiByteArrayInputStream multiBAIS = IOs.toMultiBAIS(bais);
            ByteArrayInputStream bais1 = new ByteArrayInputStream(new byte[1]);
            MultiByteArrayInputStream multiBAIS1 = IOs.toMultiBAIS(bais1);

            assertEquals(-1, multiBAIS.read());
            assertEquals(0, multiBAIS1.read());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCopy() {
        try {
            MultiByteArrayInputStream bais1 = new MultiByteArrayInputStream(new byte[0]);
            MultiByteArrayOutputStream baos1 = new MultiByteArrayOutputStream();

            long copy01 = IOs.copy(bais1, baos1, -1);
            long size01 = baos1.size();
            bais1.reset();
            baos1 = new MultiByteArrayOutputStream();

            long copy02 = IOs.copy(bais1, baos1, 0);
            long size02 = baos1.size();
            bais1.reset();
            baos1 = new MultiByteArrayOutputStream();

            long copy03 = IOs.copy(bais1, baos1, 10);
            long size03 = baos1.size();

            System.out.println(copy01 + "---" + size01);
            System.out.println(copy02 + "---" + size02);
            System.out.println(copy03 + "---" + size03);
            assertEquals("0---0", copy01 + "---" + size01);
            assertEquals("0---0", copy02 + "---" + size02);
            assertEquals("0---0", copy03 + "---" + size03);
            System.out.println("=============================================");
            MultiByteArrayInputStream bais2 = new MultiByteArrayInputStream(new byte[10]);
            MultiByteArrayOutputStream baos2 = new MultiByteArrayOutputStream();

            long copy11 = IOs.copy(bais2, baos2, -1);
            long size11 = baos2.size();
            bais2.reset();
            baos2 = new MultiByteArrayOutputStream();

            long copy12 = IOs.copy(bais2, baos2, 0);
            long size12 = baos2.size();
            bais2.reset();
            baos2 = new MultiByteArrayOutputStream();

            long copy13 = IOs.copy(bais2, baos2, 9);
            long size13 = baos2.size();
            bais2.reset();
            baos2 = new MultiByteArrayOutputStream();

            long copy14 = IOs.copy(bais2, baos2, 10);
            long size14 = baos2.size();
            bais2.reset();
            baos2 = new MultiByteArrayOutputStream();

            long copy15 = IOs.copy(bais2, baos2, 20);
            long size15 = baos2.size();

            System.out.println(copy11 + "---" + size11);
            System.out.println(copy12 + "---" + size12);
            System.out.println(copy13 + "---" + size13);
            System.out.println(copy14 + "---" + size14);
            System.out.println(copy15 + "---" + size15);

            assertEquals("0---0", copy11 + "---" + size11);
            assertEquals("0---0", copy12 + "---" + size12);
            assertEquals("9---9", copy13 + "---" + size13);
            assertEquals("10---10", copy14 + "---" + size14);
            assertEquals("10---10", copy15 + "---" + size15);

            System.out.println("=============================================");
            MultiByteArrayInputStream bais3 = new MultiByteArrayInputStream(new byte[100], new byte[100], new byte[8192]);
            MultiByteArrayOutputStream baos3 = new MultiByteArrayOutputStream();

            long copy21 = IOs.copy(bais3, baos3, -10);
            long size21 = baos3.size();
            bais3.reset();
            baos3 = new MultiByteArrayOutputStream();

            long copy22 = IOs.copy(bais3, baos3, 0);
            long size22 = baos3.size();
            bais3.reset();
            baos3 = new MultiByteArrayOutputStream();

            long copy23 = IOs.copy(bais3, baos3, 1);
            long size23 = baos3.size();
            bais3.reset();
            baos3 = new MultiByteArrayOutputStream();

            long copy24 = IOs.copy(bais3, baos3, 1000);
            long size24 = baos3.size();
            bais3.reset();
            baos3 = new MultiByteArrayOutputStream();

            long copy25 = IOs.copy(bais3, baos3, 8192);
            long size25 = baos3.size();
            bais3.reset();
            baos3 = new MultiByteArrayOutputStream();

            long copy26 = IOs.copy(bais3, baos3, 8392);
            long size26 = baos3.size();
            bais3.reset();
            baos3 = new MultiByteArrayOutputStream();

            long copy27 = IOs.copy(bais3, baos3, 8393);
            long size27 = baos3.size();
            bais3.reset();
            baos3 = new MultiByteArrayOutputStream();

            long copy28 = IOs.copy(bais3, baos3, 10000);
            long size28 = baos3.size();

            System.out.println(copy21 + "---" + size21);
            System.out.println(copy22 + "---" + size22);
            System.out.println(copy23 + "---" + size23);
            System.out.println(copy24 + "---" + size24);
            System.out.println(copy25 + "---" + size25);
            System.out.println(copy26 + "---" + size26);
            System.out.println(copy27 + "---" + size27);
            System.out.println(copy28 + "---" + size28);

            assertEquals(copy21 + "---" + size21, "0---0");
            assertEquals(copy22 + "---" + size22, "0---0");
            assertEquals(copy23 + "---" + size23, "1---1");
            assertEquals(copy24 + "---" + size24, "1000---1000");
            assertEquals(copy25 + "---" + size25, "8192---8192");
            assertEquals(copy26 + "---" + size26, "8392---8392");
            assertEquals(copy27 + "---" + size27, "8392---8392");
            assertEquals(copy28 + "---" + size28, "8392---8392");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReadBytes() {
        MultiByteArrayInputStream bais = new MultiByteArrayInputStream("这是InputStream".getBytes(), "读取字节数组".getBytes(),
                "的测试，".getBytes(), "会用到`IOs.readBytes`和`IOs.copy`等函数！".getBytes());

        try {
            byte[][] bytes = IOs.readBytes(bais, -10L);
            String s1 = Strings.toString(bytes);
            bais.reset();

            bytes = IOs.readBytes(bais, -1L);
            String s2 = Strings.toString(bytes);
            bais.reset();

            bytes = IOs.readBytes(bais, 0L);
            String s3 = Strings.toString(bytes);
            bais.reset();

            bytes = IOs.readBytes(bais, 10L);
            String s4 = Strings.toString(bytes);
            bais.reset();

            bytes = IOs.readBytes(bais, 20L);
            String s5 = Strings.toString(bytes);
            bais.reset();

            bytes = IOs.readBytes(bais, 100L);
            String s6 = Strings.toString(bytes);
            bais.reset();

            bytes = IOs.readBytes(bais, null);
            String s7 = Strings.toString(bytes);
            bais.reset();

            System.out.println(s1);
            System.out.println(s2);
            System.out.println(s3);
            System.out.println(s4);
            System.out.println(s5);
            System.out.println(s6);
            System.out.println(s7);

            assertEquals(s1, "");
            assertEquals(s2, "");
            assertEquals(s3, "");
            assertEquals(s4, "这是Inpu");
            assertEquals(s5, "这是InputStream读");
            assertEquals(s6, "这是InputStream读取字节数组的测试，会用到`IOs.readBytes`和`IOs.copy`等函数！");
            assertEquals(s7, "这是InputStream读取字节数组的测试，会用到`IOs.readBytes`和`IOs.copy`等函数！");

            System.out.println("============================================");

            bytes = IOs.readBytes(bais, 10L);
            String s01 = Strings.toString(bytes);
            bytes = IOs.readBytes(bais, 19L);
            String s02 = Strings.toString(bytes);
            bytes = IOs.readBytes(bais, 31L);
            String s03 = Strings.toString(bytes);
            bytes = IOs.readBytes(bais, 36L);
            String s04 = Strings.toString(bytes);
            bytes = IOs.readBytes(bais, 1L);
            String s05 = Strings.toString(bytes);
            bytes = IOs.readBytes(bais, 0L);
            String s06 = Strings.toString(bytes);

            System.out.println(s01);
            System.out.println(s02);
            System.out.println(s03);
            System.out.println(s04);
            System.out.println(s05);
            System.out.println(s06);

            assertEquals(s01, "这是Inpu");
            assertEquals(s02, "tStream读取字节");
            assertEquals(s03, "数组的测试，会用到`IOs");
            assertEquals(s04, ".readBytes`和`IOs.copy`等函数！");
            assertEquals(s05, "");
            assertEquals(s06, "");
            assertEquals(s01 + s02 + s03 + s04 + s05 + s06, "这是InputStream读取字节数组的测试，会用到`IOs.readBytes`和`IOs.copy`等函数！");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
