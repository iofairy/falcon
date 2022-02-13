package com.iofairy.test;

import com.iofairy.falcon.os.OS;
import org.junit.jupiter.api.Test;

/**
 * @author GG
 * @version 1.0
 */
public class OSTest {

    @Test
    public void testOS() {
        System.out.println("os name: " + OS.OS_NAME);
        System.out.println("os version: " + OS.OS_VERSION);
        System.out.println("os arch: " + OS.OS_ARCH);
        System.out.println("user home: " + OS.USER_HOME);
        System.out.println("module root dir: " + OS.USER_DIR);
        System.out.println("os language: " + OS.USER_LANGUAGE);
        System.out.println("user timezone: " + OS.USER_TIMEZONE);
        System.out.println("user timezone: " + System.getProperty("user.timezone"));
        System.out.println("awt toolkit classname: " + OS.AWT_TOOLKIT_CLASSNAME);
        System.out.println("line separator: " + OS.LINE_SEPARATOR);
        System.out.println("file separator: " + OS.FILE_SEPARATOR);
        System.out.println("path separator: " + OS.PATH_SEPARATOR);
        System.out.println("is mac os: " + OS.IS_MAC);
        System.out.println("is windows system: " + OS.IS_WINDOWS);
        System.out.println("is windows 10: " + OS.IS_WINDOWS_10);
        System.out.println("DEFAULT_LOCALE: " + OS.DEFAULT_LOCALE);
        System.out.println("DEFAULT_LANG: " + OS.DEFAULT_LANG);
        System.out.println("DEFAULT_COUNTRY: " + OS.DEFAULT_COUNTRY);
        System.out.println("IS_ZH_LANG: " + OS.IS_ZH_LANG);
        System.out.println("DEFAULT_ZONE_ID: " + OS.ZONE_ID);
    }
}
