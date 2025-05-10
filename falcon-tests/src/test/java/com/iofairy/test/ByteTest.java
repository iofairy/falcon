package com.iofairy.test;

import com.iofairy.falcon.unit.ByteBinaryUnit;
import com.iofairy.falcon.unit.ByteDecimalUnit;
import com.iofairy.falcon.unit.Bytes;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author GG
 * @version 1.0
 * @date 2023/7/21 11:22
 */
public class ByteTest {
    @Test
    public void testByte1() {
        Bytes bytes01 = Bytes.ofBs(1025.25d, true);
        Bytes bytes02 = Bytes.ofBs(1025.25d, false);
        Bytes bytes03 = Bytes.ofKBs(1025.25d, true);
        Bytes bytes04 = Bytes.ofKBs(1025.25d, false);
        Bytes bytes05 = Bytes.ofYBs(1025.252525d, true);
        Bytes bytes06 = Bytes.ofYBs(1025.252525d, false);

        System.out.println(bytes01.toString());
        System.out.println(bytes02.toString());
        System.out.println(bytes03.toString());
        System.out.println(bytes04.toString());
        System.out.println(bytes05.toString());
        System.out.println(bytes06.toString());
        System.out.println(bytes01.toSimpleString());
        System.out.println(bytes02.toSimpleString());
        System.out.println(bytes03.toSimpleString());
        System.out.println(bytes04.toSimpleString());
        System.out.println(bytes05.toSimpleString());
        System.out.println(bytes06.toSimpleString());
        System.out.println(bytes01.toFullString());
        System.out.println(bytes02.toFullString());
        System.out.println(bytes03.toFullString());
        System.out.println(bytes04.toFullString());
        System.out.println(bytes05.toFullString());
        System.out.println(bytes06.toFullString());

        assertEquals(bytes01.toString(), "当前采用[二进制]字节单位计算，字节大小为：[1KiB`1B`2bit]");
        assertEquals(bytes02.toString(), "当前采用[十进制]字节单位计算，字节大小为：[1KB`25B`2bit]");
        assertEquals(bytes03.toString(), "当前采用[二进制]字节单位计算，字节大小为：[1MiB`1KiB`256B]");
        assertEquals(bytes04.toString(), "当前采用[十进制]字节单位计算，字节大小为：[1MB`25KB`250B]");
        assertEquals(bytes05.toString(), "当前采用[二进制]字节单位计算，字节大小为：[1025YiB`258ZiB`599EiB`670PiB`108TiB`137GiB`640MiB`629KiB`149B]");
        assertEquals(bytes06.toString(), "当前采用[十进制]字节单位计算，字节大小为：[1025YB`252ZB`525EB`0PB`0TB`0GB`0MB`0KB`0B]");
        assertEquals(bytes01.toSimpleString(), "当前采用[二进制]字节单位计算，字节大小为：[1KiB`1B`2bit]");
        assertEquals(bytes02.toSimpleString(), "当前采用[十进制]字节单位计算，字节大小为：[1KB`25B`2bit]");
        assertEquals(bytes03.toSimpleString(), "当前采用[二进制]字节单位计算，字节大小为：[1MiB`1KiB`256B]");
        assertEquals(bytes04.toSimpleString(), "当前采用[十进制]字节单位计算，字节大小为：[1MB`25KB`250B]");
        assertEquals(bytes05.toSimpleString(), "当前采用[二进制]字节单位计算，字节大小为：[1025YiB`258ZiB`599EiB`670PiB`108TiB`137GiB`640MiB`629KiB`149B]");
        assertEquals(bytes06.toSimpleString(), "当前采用[十进制]字节单位计算，字节大小为：[1025YB`252ZB`525EB]");


        Bytes bytes = bytes01.toAnotherUnit();
        System.out.println(bytes01 + "---总bit数：" + bytes01.getBits() + "---转换后：" + bytes);
        assertEquals(bytes.toString(), "当前采用[十进制]字节单位计算，字节大小为：[1KB`25B`2bit]");
        bytes = bytes02.toAnotherUnit();
        System.out.println(bytes02 + "---总bit数：" + bytes02.getBits() + "---转换后：" + bytes);
        assertEquals(bytes.toString(), "当前采用[二进制]字节单位计算，字节大小为：[1KiB`1B`2bit]");
        bytes = bytes03.toAnotherUnit();
        System.out.println(bytes03 + "---总bit数：" + bytes03.getBits() + "---转换后：" + bytes);
        assertEquals(bytes.toString(), "当前采用[十进制]字节单位计算，字节大小为：[1MB`49KB`856B]");
        bytes = bytes04.toAnotherUnit();
        System.out.println(bytes04 + "---总bit数：" + bytes04.getBits() + "---转换后：" + bytes);
        assertEquals(bytes.toString(), "当前采用[二进制]字节单位计算，字节大小为：[1001KiB`226B]");

    }

    @Test
    public void testByte2() {
        Bytes bytes01 = Bytes.ofBs(-1025.25d, true);
        Bytes bytes02 = Bytes.ofBs(-1025.25d, false);
        Bytes bytes03 = Bytes.ofKBs(-1025.25d, true);
        Bytes bytes04 = Bytes.ofKBs(-1025.25d, false);
        Bytes bytes05 = Bytes.ofYBs(-1025.252525d, true);
        Bytes bytes06 = Bytes.ofYBs(-1025.252525d, false);

        System.out.println(bytes01.toString());
        System.out.println(bytes02.toString());
        System.out.println(bytes03.toString());
        System.out.println(bytes04.toString());
        System.out.println(bytes05.toString());
        System.out.println(bytes06.toString());
        System.out.println(bytes01.toSimpleString());
        System.out.println(bytes02.toSimpleString());
        System.out.println(bytes03.toSimpleString());
        System.out.println(bytes04.toSimpleString());
        System.out.println(bytes05.toSimpleString());
        System.out.println(bytes06.toSimpleString());
        System.out.println(bytes01.toFullString());
        System.out.println(bytes02.toFullString());
        System.out.println(bytes03.toFullString());
        System.out.println(bytes04.toFullString());
        System.out.println(bytes05.toFullString());
        System.out.println(bytes06.toFullString());

        assertEquals(bytes01.toString(), "当前采用[二进制]字节单位计算，字节大小为：[-1KiB`-1B`-2bit]");
        assertEquals(bytes02.toString(), "当前采用[十进制]字节单位计算，字节大小为：[-1KB`-25B`-2bit]");
        assertEquals(bytes03.toString(), "当前采用[二进制]字节单位计算，字节大小为：[-1MiB`-1KiB`-256B]");
        assertEquals(bytes04.toString(), "当前采用[十进制]字节单位计算，字节大小为：[-1MB`-25KB`-250B]");
        assertEquals(bytes05.toString(), "当前采用[二进制]字节单位计算，字节大小为：[-1025YiB`-258ZiB`-599EiB`-670PiB`-108TiB`-137GiB`-640MiB`-629KiB`-149B]");
        assertEquals(bytes06.toString(), "当前采用[十进制]字节单位计算，字节大小为：[-1025YB`-252ZB`-525EB`0PB`0TB`0GB`0MB`0KB`0B]");
        assertEquals(bytes01.toSimpleString(), "当前采用[二进制]字节单位计算，字节大小为：[-1KiB`-1B`-2bit]");
        assertEquals(bytes02.toSimpleString(), "当前采用[十进制]字节单位计算，字节大小为：[-1KB`-25B`-2bit]");
        assertEquals(bytes03.toSimpleString(), "当前采用[二进制]字节单位计算，字节大小为：[-1MiB`-1KiB`-256B]");
        assertEquals(bytes04.toSimpleString(), "当前采用[十进制]字节单位计算，字节大小为：[-1MB`-25KB`-250B]");
        assertEquals(bytes05.toSimpleString(), "当前采用[二进制]字节单位计算，字节大小为：[-1025YiB`-258ZiB`-599EiB`-670PiB`-108TiB`-137GiB`-640MiB`-629KiB`-149B]");
        assertEquals(bytes06.toSimpleString(), "当前采用[十进制]字节单位计算，字节大小为：[-1025YB`-252ZB`-525EB]");


        Bytes bytes = bytes01.toAnotherUnit();
        System.out.println(bytes01 + "---总bit数：" + bytes01.getBits() + "---转换后：" + bytes);
        assertEquals(bytes.toString(), "当前采用[十进制]字节单位计算，字节大小为：[-1KB`-25B`-2bit]");
        bytes = bytes02.toAnotherUnit();
        System.out.println(bytes02 + "---总bit数：" + bytes02.getBits() + "---转换后：" + bytes);
        assertEquals(bytes.toString(), "当前采用[二进制]字节单位计算，字节大小为：[-1KiB`-1B`-2bit]");
        bytes = bytes03.toAnotherUnit();
        System.out.println(bytes03 + "---总bit数：" + bytes03.getBits() + "---转换后：" + bytes);
        assertEquals(bytes.toString(), "当前采用[十进制]字节单位计算，字节大小为：[-1MB`-49KB`-856B]");
        bytes = bytes04.toAnotherUnit();
        System.out.println(bytes04 + "---总bit数：" + bytes04.getBits() + "---转换后：" + bytes);
        assertEquals(bytes.toString(), "当前采用[二进制]字节单位计算，字节大小为：[-1001KiB`-226B]");

    }


    @Test
    public void testFormat() {
        Bytes bytes01 = Bytes.of(0, 0, 0, 1, 1, 2, true);
        Bytes bytes02 = Bytes.of(0, 0, 0, 1, 25, 2, false);
        Bytes bytes03 = Bytes.of(0, 0, 1, 1, 256, true);
        Bytes bytes04 = Bytes.of(0, 0, 1, 25, 250, false);
        Bytes bytes05 = Bytes.ofYBs(new BigDecimal("0.0000000000009094947017729282379150390625"), true);
        Bytes bytes06 = Bytes.of(0, 0, 1, -1, 256, true);
        Bytes bytes07 = Bytes.of(0, 0, 1, -25, 250, false);

        System.out.println(bytes01.toFullString());
        System.out.println(bytes02.toFullString());
        System.out.println(bytes03.toFullString());
        System.out.println(bytes04.toFullString());
        System.out.println(bytes05.toFullString());
        System.out.println(bytes06.toFullString());
        System.out.println(bytes07.toFullString());
        System.out.println("============================================================");
        System.out.println(bytes01.format());           // 1.001KiB
        System.out.println(bytes02.format());           // 1.025KB
        System.out.println(bytes03.format());           // 1.001MiB
        System.out.println(bytes04.format());           // 1.025MB
        System.out.println(bytes05.format());           // 1.0TiB
        System.out.println(bytes06.format());           // 1023.25KiB
        System.out.println(bytes07.format());           // 975.25KB

        System.out.println(bytes01.compareTo(bytes02));
        System.out.println(bytes03.compareTo(bytes04));
        System.out.println(bytes01.equals(bytes02));
        System.out.println(bytes03.equals(bytes04));

        assertEquals(bytes01.format(), "1.001KiB");
        assertEquals(bytes02.format(), "1.025KB");
        assertEquals(bytes03.format(), "1.001MiB");
        assertEquals(bytes04.format(), "1.025MB");
        assertEquals(bytes05.format(), "1.0TiB");
        assertEquals(bytes06.format(), "1023.25KiB");
        assertEquals(bytes07.format(), "975.25KB");
        assertEquals(bytes01.compareTo(bytes02), 0);
        assertEquals(bytes03.compareTo(bytes04), 1);
        assertTrue(bytes01.equals(bytes02));
        assertFalse(bytes03.equals(bytes04));

    }

    @Test
    public void testConvertUnit() {
        Bytes bytes01 = Bytes.of(0, 0, 0, 1, 1, 2, true);
        Bytes bytes02 = Bytes.of(0, 0, 0, 1, 25, 2, false);
        Bytes bytes03 = Bytes.of(0, 0, 1, 1, 256, true);
        Bytes bytes04 = Bytes.of(0, 0, 1, 25, 250, false);
        Bytes bytes05 = Bytes.ofYBs(new BigDecimal("0.0000000000009094947017729282379150390625"), true);

        System.out.println(bytes01.toFullString());
        System.out.println(bytes02.toFullString());
        System.out.println(bytes03.toFullString());
        System.out.println(bytes04.toFullString());
        System.out.println(bytes05.toFullString());
        System.out.println(bytes05.toAnotherUnit().toFullString());
        System.out.println("============================================================");
        BigDecimal bigDecimal1 = bytes01.convertUnit(ByteBinaryUnit.MiB);
        BigDecimal bigDecimal2 = bytes02.convertUnit(ByteDecimalUnit.MB.getIndex());
        BigDecimal bigDecimal3 = bytes05.convertUnit(ByteBinaryUnit.MiB);
        BigDecimal bigDecimal4 = bytes05.convertUnit(ByteDecimalUnit.MB);
        BigDecimal bigDecimal5 = bytes05.convertUnit(ByteDecimalUnit.PB);
        BigDecimal bigDecimal6 = bytes05.convertUnit(ByteBinaryUnit.PiB);
        System.out.println("" + bigDecimal1 + ByteBinaryUnit.MiB);      // 0.0009777545928955078125MiB
        System.out.println("" + bigDecimal2 + ByteDecimalUnit.MB);      // 0.00102525MB
        System.out.println("" + bigDecimal3 + ByteBinaryUnit.MiB);      // 1048576MiB
        System.out.println("" + bigDecimal4 + ByteDecimalUnit.MB);      // 1048576MB
        System.out.println("" + bigDecimal5 + ByteDecimalUnit.PB);      // 0.001099511627776PB
        System.out.println("" + bigDecimal6 + ByteBinaryUnit.PiB);      // 0.0009765625PiB

        assertEquals("0.0009777545928955078125MiB", "" + bigDecimal1 + ByteBinaryUnit.MiB);
        assertEquals("0.00102525MB", "" + bigDecimal2 + ByteDecimalUnit.MB);
        assertEquals("1048576MiB", "" + bigDecimal3 + ByteBinaryUnit.MiB);
        assertEquals("1099511.627776MB", "" + bigDecimal4 + ByteDecimalUnit.MB);
        assertEquals("0.001099511627776PB", "" + bigDecimal5 + ByteDecimalUnit.PB);
        assertEquals("0.0009765625PiB", "" + bigDecimal6 + ByteBinaryUnit.PiB);
    }

}
