package com.iofairy;

import com.iofairy.falcon.time.DateTimePattern;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * @author GG
 * @version 1.0
 * @date 2021/10/1 20:54
 */
public class DateTimePatternTest {
    @Test
    public void testForDate() {
        assertEquals("yyyy年MM月dd日HH时mm分ss秒SSS毫秒", DateTimePattern.forDate("2020年1月2日6时6分6秒6毫秒"));
        assertEquals("MM月dd日HH时mm分ss秒SSS毫秒", DateTimePattern.forDate("1月2日6时6分6秒6毫秒"));
        assertEquals("yyyy年MM月dd日HH时mm分ss秒", DateTimePattern.forDate("2020年1月2日6时6分6秒"));
        assertEquals("yyyy.MM.dd HH:mm:ss.SSS", DateTimePattern.forDate("999.1.2 6:6:6.6"));
        assertEquals("yyyy-MM-dd HH:mm:ss.SSS", DateTimePattern.forDate("2020-1-2 6:6:6.6"));
        assertEquals("yyyy/MM/dd HH:mm:ss.SSS", DateTimePattern.forDate("2020/1/2 6:6:6.6"));
        assertEquals("yyyy-MM-dd HH:mm:ss", DateTimePattern.forDate("2020-01-02 06:06:06"));
        assertEquals("yyyy-MM-dd HH:mm:ss", DateTimePattern.forDate("999-1-2 6:6:6"));
        assertEquals("yyyy-MM-dd HH:mm:ss", DateTimePattern.forDate("999-1-2 6:6:6"));
        assertEquals("MM.dd HH:mm:ss.SSS", DateTimePattern.forDate("01.2 6:6:6.6"));
        assertEquals("MM-dd HH:mm:ss.SSS", DateTimePattern.forDate("01-2 6:6:6.6"));
        assertEquals("mm:ss.SSS", DateTimePattern.forDate("6:6.6"));
        assertEquals("mm:ss.SSS", DateTimePattern.forDate("6:6.655"));
        assertEquals("yyyyMMddHHmmssSSS", DateTimePattern.forDate("20200102060606006"));
        assertEquals("yyyyMMddHHmmss", DateTimePattern.forDate("20200102060606"));
        assertEquals("yyyy", DateTimePattern.forDate("999"));
        assertEquals("yyyy", DateTimePattern.forDate("2020"));
        assertEquals("yyyy年MM月dd日", DateTimePattern.forDate("2020年1月2日"));
        assertEquals("yyyy年MM月dd日", DateTimePattern.forDate("2020年01月02日"));
        assertEquals("MM月dd日HH时mm分ss秒SSS毫秒", DateTimePattern.forDate("1月2日6时6分6秒6毫秒"));
        assertEquals("yyyy-MM-dd", DateTimePattern.forDate("2020-01-02"));
        assertEquals("yyyy-MM-dd", DateTimePattern.forDate("999-1-2"));
        assertEquals("yyyy-MM-dd", DateTimePattern.forDate("999-1-2"));
        assertEquals("yyyy.MM.dd", DateTimePattern.forDate("2020.01.02"));
        assertEquals("yyyy.MM.dd", DateTimePattern.forDate("999.1.2"));
        assertEquals("yyyy.MM.dd", DateTimePattern.forDate("999.1.2"));
        assertEquals("HH:mm:ss", DateTimePattern.forDate("06:06:06"));
        assertEquals("HH:mm:ss", DateTimePattern.forDate("6:6:6"));
        assertEquals("HH:mm:ss", DateTimePattern.forDate("6:6:6"));
        assertEquals("MM.dd", DateTimePattern.forDate("1.2"));
        assertEquals("MM.dd", DateTimePattern.forDate("01.02"));


        assertNull(DateTimePattern.forDate("2020/1/2 6:6.:6.6"));
        assertNull(DateTimePattern.forDate("20201266665"));
        assertNull(DateTimePattern.forDate("99"));
        assertNull(DateTimePattern.forDate("yyyy年MM月dd日HH时mm分ss秒SSS毫秒"));
        assertNull(DateTimePattern.forDate("yyyy年MM月dd日HH时mm分ss秒SSSS毫秒"));
    }

    @Test
    public void testforLocalDT() {
        assertEquals("yyyy年MM月dd日HH时mm分ss秒SSS毫秒", DateTimePattern.forLocalDT("2020年01月02日06时06分06秒066毫秒"));
        assertEquals("yyyy年MM月dd日HH时mm分ss秒", DateTimePattern.forLocalDT("2020年01月02日06时06分06秒"));
        assertEquals("yyyy.MM.dd HH:mm:ss.SSS", DateTimePattern.forLocalDT("2020.01.02 06:06:06.066"));
        assertEquals("yyyy-MM-dd HH:mm:ss.SSS", DateTimePattern.forLocalDT("2020-01-02 06:06:06.066"));
        assertEquals("yyyy/MM/dd HH:mm:ss.SSS", DateTimePattern.forLocalDT("2020/01/02 06:06:06.066"));
        assertEquals("yyyy-MM-dd HH:mm:ss", DateTimePattern.forLocalDT("2020-01-02 06:06:06"));
        assertEquals("yyyyMMddHHmmssSSS", DateTimePattern.forLocalDT("20200102060606006"));
        assertEquals("yyyyMMddHHmmss", DateTimePattern.forLocalDT("20200102060606"));
        assertEquals("yyyy", DateTimePattern.forLocalDT("2020"));
        assertEquals("yyyy年MM月dd日", DateTimePattern.forLocalDT("2020年01月02日"));
        assertEquals("yyyy-MM-dd", DateTimePattern.forLocalDT("2020-01-02"));
        assertEquals("yyyy.MM.dd", DateTimePattern.forLocalDT("2020.01.02"));
        assertEquals("HH:mm:ss", DateTimePattern.forLocalDT("06:06:06"));

        assertNull(DateTimePattern.forLocalDT("2020年1月2日"));
        assertNull(DateTimePattern.forLocalDT("999"));
        assertNull(DateTimePattern.forLocalDT("2020-1-2 6:6:6.6"));
        assertNull(DateTimePattern.forLocalDT("2020年1月2日6时6分6秒6毫秒"));
        assertNull(DateTimePattern.forLocalDT("6:6:6"));
        assertNull(DateTimePattern.forLocalDT("6:6:6"));
        assertNull(DateTimePattern.forLocalDT("1.2"));
        assertNull(DateTimePattern.forLocalDT("01.02"));
        assertNull(DateTimePattern.forLocalDT("2020/1/2 6:6.:6.6"));
        assertNull(DateTimePattern.forLocalDT("20201266665"));
        assertNull(DateTimePattern.forLocalDT("99"));
        assertNull(DateTimePattern.forLocalDT("yyyy年MM月dd日HH时mm分ss秒SSS毫秒"));
        assertNull(DateTimePattern.forLocalDT("yyyy年MM月dd日HH时mm分ss秒SSSS毫秒"));
    }
}
