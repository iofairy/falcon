package com.iofairy.test;

import com.iofairy.falcon.fs.FilePath;
import com.iofairy.falcon.fs.PathInfo;
import com.iofairy.tcf.Try;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author GG
 * @version 1.0
 * @date 2021/8/31 19:47
 */
public class FilePathTest {
    @Test
    public void testPath() {
        System.out.println("\n>>> testPath");

        String path01 = FilePath.path("./a", "b", "./c/", "/d");
        String path02 = FilePath.path("/", "./");
        String path03 = FilePath.path("./", "./", "//", "/.");
        String path04 = FilePath.path("./.", "./");
        String path05 = FilePath.path(".///.", "./");
        String path06 = FilePath.path(".\\./", "./");
        String path07 = FilePath.path("\\/", "./.");
        String path08 = FilePath.path("\\/", "./\\.//.///", ".././a///b//.\\c//.");
        String path09 = FilePath.path("./a", "b", "./c", "/d///");
        String path10 = FilePath.path("./a", "\\  /\\ ././b", "./c", "/d///");
        String path11 = FilePath.path("\\/", "./a.");
        String path12 = FilePath.path("/abc/", "./a.", "b", "/c/", "d/");
        String path13 = FilePath.path("");
        String path14 = FilePath.path("", "", "///a");
        String path15 = FilePath.path("", "", "//\\a");
        String path16 = FilePath.path("", "", "", "");
        String path17 = FilePath.path("", "", "\\a", "");
        String path18 = FilePath.path("", "", "\\a", "", "/b/c///d", ".", "e");
        String path19 = FilePath.path(".", "", "\\a", "", "/b/c///d", ".", "e/.");
        String path20 = FilePath.path("/", "", "\\a", "", "/b/c///d", ".", "e/.");
        String path21 = FilePath.path(".", "", "/a", "", "/b/c///d", ".", "e/.");
        String path22 = FilePath.path("/", null);
        String path23 = FilePath.path("./", (String[])null);
        String path24 = FilePath.path("/", new String[]{});
        String path25 = FilePath.path("../..", "/.\\", "../", "/././a./.a/..");
        String path26 = FilePath.path("../..", "/.\\", "../", "/././a./.a/.");
        String path27 = FilePath.path("../..", "/.\\\\\\", "../", "/././a./.a/.\\\\");

        assertEquals("./a/b/c/d", path01);
        assertEquals("/", path02);
        assertEquals(".", path03);
        assertEquals(".", path04);
        assertEquals(".", path05);
        assertEquals(".\\.", path06);                //  .\.
        assertEquals("\\", path07);                  //  \
        assertEquals("\\/\\./../a/b/.\\c", path08);   //   \/\./../a/b/.\c
        assertEquals("./a/b/c/d", path09);
        assertEquals("./a/\\  /\\ ./b/c/d", path10); //   ./a/\  /\ ./b/c/d
        assertEquals("\\/a.", path11);               //   \/a.
        assertEquals("/abc/a./b/c/d", path12);
        assertEquals("", path13);
        assertEquals("/a", path14);
        assertEquals("/\\a", path15);
        assertEquals("", path16);
        assertEquals("\\a", path17);
        assertEquals("\\a/b/c/d/e", path18);
        assertEquals("./\\a/b/c/d/e", path19);
        assertEquals("/\\a/b/c/d/e", path20);
        assertEquals("./a/b/c/d/e", path21);
        assertEquals("/", path22);
        assertEquals(".", path23);
        assertEquals("/", path24);
        assertEquals("../../.\\/../a./.a/..", path25);
        assertEquals("../../.\\/../a./.a", path26);
        assertEquals("../../.\\\\\\/../a./.a/.\\\\", path27);

    }

    @Test
    public void testPathWin() {
        System.out.println("\n>>> testPathWin");

        String path01 = FilePath.pathWin("./a", "\\\\b", "\\\\./c", "/d");
        String path02 = FilePath.pathWin("/", "./");
        String path03 = FilePath.pathWin("./", "./");
        String path04 = FilePath.pathWin("./.", "./");
        String path05 = FilePath.pathWin(".///.", "\\\\\\\\./");
        String path06 = FilePath.pathWin(".\\./", "./");
        String path07 = FilePath.pathWin("\\/", "./.");
        String path08 = FilePath.pathWin("\\/", "./\\\\\\.//.///", "\\\\.././a///b//.\\c//.");
        String path09 = FilePath.pathWin("./a", "b", "./c", "/d///");
        String path10 = FilePath.pathWin("./a", "\\  /\\ ././b", "./c", "/d///");
        String path11 = FilePath.pathWin("\\/", "./a.");
        String path12 = FilePath.pathWin("/abc/", "./a.", "b", "/c/", "d/", "\\e");
        String path13 = FilePath.pathWin("");
        String path14 = FilePath.pathWin("", "", "///a");
        String path15 = FilePath.pathWin("", "", "//\\a");
        String path16 = FilePath.pathWin("", "", "", "");
        String path17 = FilePath.pathWin("", "", "\\a", "");
        String path18 = FilePath.pathWin("", "", "\\a", "", "/b/c///d", ".", "e");
        String path19 = FilePath.pathWin(".", "", "\\a", "", "/b/c///d", ".", "e/.");
        String path20 = FilePath.pathWin("/", "", "\\a", "", "/b/c///d", ".", "e/.");
        String path21 = FilePath.pathWin(".", "", "/a", "", "/b/c///d", ".", "e/.");
        String path22 = FilePath.pathWin("/", null);
        String path23 = FilePath.pathWin("./", (String[])null);
        String path24 = FilePath.pathWin("/", new String[]{});
        String path25 = FilePath.pathWin("../..", "/.\\", "../", "/././a./.a/..");
        String path26 = FilePath.pathWin("../..", "/.\\", "../", "/././a./.a/.");
        String path27 = FilePath.pathWin("../..", "/.\\\\\\", "../", "/././a./.a/.\\\\");

        assertEquals(".\\a\\b\\c\\d", path01);
        assertEquals("\\", path02);
        assertEquals(".", path03);
        assertEquals(".", path04);
        assertEquals(".", path05);
        assertEquals(".", path06);
        assertEquals("\\", path07);
        assertEquals("\\..\\a\\b\\c", path08);
        assertEquals(".\\a\\b\\c\\d", path09);
        assertEquals(".\\a\\  \\ .\\b\\c\\d", path10);
        assertEquals("\\a.", path11);
        assertEquals("\\abc\\a.\\b\\c\\d\\e", path12);
        assertEquals("", path13);
        assertEquals("\\a", path14);
        assertEquals("\\a", path15);
        assertEquals("", path16);
        assertEquals("\\a", path17);
        assertEquals("\\a\\b\\c\\d\\e", path18);
        assertEquals(".\\a\\b\\c\\d\\e", path19);
        assertEquals("\\a\\b\\c\\d\\e", path20);
        assertEquals(".\\a\\b\\c\\d\\e", path21);
        assertEquals("\\", path22);
        assertEquals(".", path23);
        assertEquals("\\", path24);
        assertEquals("..\\..\\..\\a.\\.a\\..", path25);
        assertEquals("..\\..\\..\\a.\\.a", path26);
        assertEquals("..\\..\\..\\a.\\.a", path27);

    }

    @Test
    public void testPathSlash() {
        System.out.println("\n>>> testPathSlash");

        String path01 = FilePath.pathSlash("./a", "\\\\b", "\\\\./c", "/d");
        String path02 = FilePath.pathSlash("/", "./");
        String path03 = FilePath.pathSlash("./", "./");
        String path04 = FilePath.pathSlash("./.", "./");
        String path05 = FilePath.pathSlash(".///.", "\\\\\\\\./");
        String path06 = FilePath.pathSlash(".\\./", "./");
        String path07 = FilePath.pathSlash("\\/", "./.");
        String path08 = FilePath.pathSlash("\\/", "./\\\\\\.//.///", "\\\\.././a///b//.\\c//.");
        String path09 = FilePath.pathSlash("./a", "b", "./c", "/d///");
        String path10 = FilePath.pathSlash("./a", "\\  /\\ ././b", "./c", "/d///");
        String path11 = FilePath.pathSlash("\\/", "./a.");
        String path12 = FilePath.pathSlash("/abc/", "./a.", "b", "/c/", "d/", "\\e");
        String path13 = FilePath.pathSlash("");
        String path14 = FilePath.pathSlash("", "", "///a");
        String path15 = FilePath.pathSlash("", "", "//\\a");
        String path16 = FilePath.pathSlash("", "", "", "");
        String path17 = FilePath.pathSlash("", "", "\\a", "");
        String path18 = FilePath.pathSlash("", "", "\\a", "", "/b/c///d", ".", "e");
        String path19 = FilePath.pathSlash(".", "", "\\a", "", "/b/c///d", ".", "e/.");
        String path20 = FilePath.pathSlash("/", "", "\\a", "", "/b/c///d", ".", "e/.");
        String path21 = FilePath.pathSlash(".", "", "/a", "", "/b/c///d", ".", "e/.");
        String path22 = FilePath.pathSlash("/", null);
        String path23 = FilePath.pathSlash("./", (String[])null);
        String path24 = FilePath.pathSlash("/", new String[]{});
        String path25 = FilePath.pathSlash("../..", "/.\\", "../", "/././a./.a/..");
        String path26 = FilePath.pathSlash("../..", "/.\\", "../", "/././a./.a/.");
        String path27 = FilePath.pathSlash("../..", "/.\\\\\\", "../", "/././a./.a/.\\\\");

        assertEquals("./a/b/c/d", path01);
        assertEquals("/", path02);
        assertEquals(".", path03);
        assertEquals(".", path04);
        assertEquals(".", path05);
        assertEquals(".", path06);
        assertEquals("/", path07);
        assertEquals("/../a/b/c", path08);
        assertEquals("./a/b/c/d", path09);
        assertEquals("./a/  / ./b/c/d", path10);
        assertEquals("/a.", path11);
        assertEquals("/abc/a./b/c/d/e", path12);
        assertEquals("", path13);
        assertEquals("/a", path14);
        assertEquals("/a", path15);
        assertEquals("", path16);
        assertEquals("/a", path17);
        assertEquals("/a/b/c/d/e", path18);
        assertEquals("./a/b/c/d/e", path19);
        assertEquals("/a/b/c/d/e", path20);
        assertEquals("./a/b/c/d/e", path21);
        assertEquals("/", path22);
        assertEquals(".", path23);
        assertEquals("/", path24);
        assertEquals("../../../a./.a/..", path25);
        assertEquals("../../../a./.a", path26);
        assertEquals("../../../a./.a", path27);

    }

    @Test
    public void testPathInfo() {
        PathInfo info01 = FilePath.info("");
        PathInfo info02 = FilePath.info("/");
        PathInfo info03 = FilePath.info("\\");
        PathInfo info04 = FilePath.info(".");
        PathInfo info05 = FilePath.info("/\\");
        PathInfo info06 = FilePath.info("///a");
        PathInfo info07 = FilePath.info("///a/b/");
        PathInfo info08 = FilePath.info("./a/b\\c\\");
        PathInfo info09 = FilePath.info(".///a\\b//\\c");
        PathInfo info10 = FilePath.info(".///a\\b//\\c/");
        PathInfo info11 = FilePath.info(".///a/111.abc.txt");
        PathInfo info12 = FilePath.info(".///a/111.abc.tx\\t");
        PathInfo info13 = FilePath.info(".///a/111.abc.tx/t");
        PathInfo info14 = FilePath.info(".///a/111.abc.txt/");
        PathInfo info15 = FilePath.info(".///a\\b//\\c/.");
        PathInfo info16 = FilePath.info(".///a\\b//\\c/..");
        PathInfo info17 = FilePath.info(".///a\\b//\\c/..\\");
        PathInfo info18 = FilePath.info(".///a\\b//\\c/../");
        PathInfo info19 = FilePath.info("..");
        PathInfo info20 = FilePath.info("..\\");
        PathInfo info21 = FilePath.info("../");
        assertNull(info01);
        assertEquals("PathInfo{hasRoot=true, parentPath='/', fileName=null, fullPath='/', paths=[\"/\"], ext='', extNoDot='', separator='/', separatorType=SLASH}", info02.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath=null, fileName='\\', fullPath='\\', paths=[\"\\\"], ext='', extNoDot='', separator='/', separatorType=SLASH}", info03.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath=null, fileName='.', fullPath='.', paths=[\".\"], ext='', extNoDot='', separator='/', separatorType=SLASH}", info04.toString());
        assertEquals("PathInfo{hasRoot=true, parentPath='/', fileName='\\', fullPath='/\\', paths=[\"/\", \"\\\"], ext='', extNoDot='', separator='/', separatorType=SLASH}", info05.toString());
        assertEquals("PathInfo{hasRoot=true, parentPath='/', fileName='a', fullPath='/a', paths=[\"/\", \"a\"], ext='', extNoDot='', separator='/', separatorType=SLASH}", info06.toString());
        assertEquals("PathInfo{hasRoot=true, parentPath='/a', fileName='b', fullPath='/a/b', paths=[\"/\", \"a\", \"b\"], ext='', extNoDot='', separator='/', separatorType=SLASH}", info07.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath='./a', fileName='b\\c\\', fullPath='./a/b\\c\\', paths=[\".\", \"a\", \"b\\c\\\"], ext='', extNoDot='', separator='/', separatorType=SLASH}", info08.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath='./a\\b', fileName='\\c', fullPath='./a\\b/\\c', paths=[\".\", \"a\\b\", \"\\c\"], ext='', extNoDot='', separator='/', separatorType=SLASH}", info09.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath='./a\\b', fileName='\\c', fullPath='./a\\b/\\c', paths=[\".\", \"a\\b\", \"\\c\"], ext='', extNoDot='', separator='/', separatorType=SLASH}", info10.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath='./a', fileName='111.abc.txt', fullPath='./a/111.abc.txt', paths=[\".\", \"a\", \"111.abc.txt\"], ext='.txt', extNoDot='txt', separator='/', separatorType=SLASH}", info11.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath='./a', fileName='111.abc.tx\\t', fullPath='./a/111.abc.tx\\t', paths=[\".\", \"a\", \"111.abc.tx\\t\"], ext='', extNoDot='', separator='/', separatorType=SLASH}", info12.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath='./a/111.abc.tx', fileName='t', fullPath='./a/111.abc.tx/t', paths=[\".\", \"a\", \"111.abc.tx\", \"t\"], ext='', extNoDot='', separator='/', separatorType=SLASH}", info13.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath='./a', fileName='111.abc.txt', fullPath='./a/111.abc.txt', paths=[\".\", \"a\", \"111.abc.txt\"], ext='.txt', extNoDot='txt', separator='/', separatorType=SLASH}", info14.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath='./a\\b', fileName='\\c', fullPath='./a\\b/\\c', paths=[\".\", \"a\\b\", \"\\c\"], ext='', extNoDot='', separator='/', separatorType=SLASH}", info15.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath='./a\\b/\\c', fileName='..', fullPath='./a\\b/\\c/..', paths=[\".\", \"a\\b\", \"\\c\", \"..\"], ext='', extNoDot='', separator='/', separatorType=SLASH}", info16.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath='./a\\b/\\c', fileName='..\\', fullPath='./a\\b/\\c/..\\', paths=[\".\", \"a\\b\", \"\\c\", \"..\\\"], ext='', extNoDot='', separator='/', separatorType=SLASH}", info17.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath='./a\\b/\\c', fileName='..', fullPath='./a\\b/\\c/..', paths=[\".\", \"a\\b\", \"\\c\", \"..\"], ext='', extNoDot='', separator='/', separatorType=SLASH}", info18.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath=null, fileName='..', fullPath='..', paths=[\"..\"], ext='', extNoDot='', separator='/', separatorType=SLASH}", info19.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath=null, fileName='..\\', fullPath='..\\', paths=[\"..\\\"], ext='', extNoDot='', separator='/', separatorType=SLASH}", info20.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath=null, fileName='..', fullPath='..', paths=[\"..\"], ext='', extNoDot='', separator='/', separatorType=SLASH}", info21.toString());
        // System.out.println(info01);
        // System.out.println(info02);
        // System.out.println(info03);
        // System.out.println(info04);
        // System.out.println(info05);
        // System.out.println(info06);
        // System.out.println(info07);
        // System.out.println(info08);
        // System.out.println(info09);
        // System.out.println(info10);
        // System.out.println(info11);
        // System.out.println(info12);
        // System.out.println(info13);
        // System.out.println(info14);
        // System.out.println(info15);
        // System.out.println(info16);
        // System.out.println(info17);
        // System.out.println(info18);
        // System.out.println(info19);
        // System.out.println(info20);
        // System.out.println(info21);
    }

    @Test
    public void testPathInfoWin() {
        PathInfo info01 = FilePath.infoWin("");
        PathInfo info02 = FilePath.infoWin("/");
        PathInfo info03 = FilePath.infoWin("\\");
        PathInfo info04 = FilePath.infoWin(".");
        PathInfo info05 = FilePath.infoWin("/\\");
        PathInfo info06 = FilePath.infoWin("///a");
        PathInfo info07 = FilePath.infoWin("///a/b/");
        PathInfo info08 = FilePath.infoWin("./a/b\\c\\");
        PathInfo info09 = FilePath.infoWin(".///a\\b//\\c");
        PathInfo info10 = FilePath.infoWin(".///a\\b//\\c/");
        PathInfo info11 = FilePath.infoWin(".///a/111.abc.txt");
        PathInfo info12 = FilePath.infoWin(".///a/111.abc.tx\\t");
        PathInfo info13 = FilePath.infoWin(".///a/111.abc.tx/t");
        PathInfo info14 = FilePath.infoWin(".///a/111.abc.txt/");
        PathInfo info15 = FilePath.infoWin(".///a\\b//\\c/.");
        PathInfo info16 = FilePath.infoWin(".///a\\b//\\c/..");
        PathInfo info17 = FilePath.infoWin(".///a\\b//\\c/..\\");
        PathInfo info18 = FilePath.infoWin(".///a\\b//\\c/../");
        PathInfo info19 = FilePath.infoWin("..");
        PathInfo info20 = FilePath.infoWin("..\\");
        PathInfo info21 = FilePath.infoWin("../");
        assertNull(info01);
        assertEquals("PathInfo{hasRoot=true, parentPath='\\', fileName=null, fullPath='\\', paths=[\"\\\"], ext='', extNoDot='', separator='\\', separatorType=BACKSLASH}", info02.toString());
        assertEquals("PathInfo{hasRoot=true, parentPath='\\', fileName=null, fullPath='\\', paths=[\"\\\"], ext='', extNoDot='', separator='\\', separatorType=BACKSLASH}", info03.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath=null, fileName='.', fullPath='.', paths=[\".\"], ext='', extNoDot='', separator='\\', separatorType=BACKSLASH}", info04.toString());
        assertEquals("PathInfo{hasRoot=true, parentPath='\\', fileName=null, fullPath='\\', paths=[\"\\\"], ext='', extNoDot='', separator='\\', separatorType=BACKSLASH}", info05.toString());
        assertEquals("PathInfo{hasRoot=true, parentPath='\\', fileName='a', fullPath='\\a', paths=[\"\\\", \"a\"], ext='', extNoDot='', separator='\\', separatorType=BACKSLASH}", info06.toString());
        assertEquals("PathInfo{hasRoot=true, parentPath='\\a', fileName='b', fullPath='\\a\\b', paths=[\"\\\", \"a\", \"b\"], ext='', extNoDot='', separator='\\', separatorType=BACKSLASH}", info07.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath='.\\a\\b', fileName='c', fullPath='.\\a\\b\\c', paths=[\".\", \"a\", \"b\", \"c\"], ext='', extNoDot='', separator='\\', separatorType=BACKSLASH}", info08.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath='.\\a\\b', fileName='c', fullPath='.\\a\\b\\c', paths=[\".\", \"a\", \"b\", \"c\"], ext='', extNoDot='', separator='\\', separatorType=BACKSLASH}", info09.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath='.\\a\\b', fileName='c', fullPath='.\\a\\b\\c', paths=[\".\", \"a\", \"b\", \"c\"], ext='', extNoDot='', separator='\\', separatorType=BACKSLASH}", info10.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath='.\\a', fileName='111.abc.txt', fullPath='.\\a\\111.abc.txt', paths=[\".\", \"a\", \"111.abc.txt\"], ext='.txt', extNoDot='txt', separator='\\', separatorType=BACKSLASH}", info11.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath='.\\a\\111.abc.tx', fileName='t', fullPath='.\\a\\111.abc.tx\\t', paths=[\".\", \"a\", \"111.abc.tx\", \"t\"], ext='', extNoDot='', separator='\\', separatorType=BACKSLASH}", info12.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath='.\\a\\111.abc.tx', fileName='t', fullPath='.\\a\\111.abc.tx\\t', paths=[\".\", \"a\", \"111.abc.tx\", \"t\"], ext='', extNoDot='', separator='\\', separatorType=BACKSLASH}", info13.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath='.\\a', fileName='111.abc.txt', fullPath='.\\a\\111.abc.txt', paths=[\".\", \"a\", \"111.abc.txt\"], ext='.txt', extNoDot='txt', separator='\\', separatorType=BACKSLASH}", info14.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath='.\\a\\b', fileName='c', fullPath='.\\a\\b\\c', paths=[\".\", \"a\", \"b\", \"c\"], ext='', extNoDot='', separator='\\', separatorType=BACKSLASH}", info15.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath='.\\a\\b\\c', fileName='..', fullPath='.\\a\\b\\c\\..', paths=[\".\", \"a\", \"b\", \"c\", \"..\"], ext='', extNoDot='', separator='\\', separatorType=BACKSLASH}", info16.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath='.\\a\\b\\c', fileName='..', fullPath='.\\a\\b\\c\\..', paths=[\".\", \"a\", \"b\", \"c\", \"..\"], ext='', extNoDot='', separator='\\', separatorType=BACKSLASH}", info17.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath='.\\a\\b\\c', fileName='..', fullPath='.\\a\\b\\c\\..', paths=[\".\", \"a\", \"b\", \"c\", \"..\"], ext='', extNoDot='', separator='\\', separatorType=BACKSLASH}", info18.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath=null, fileName='..', fullPath='..', paths=[\"..\"], ext='', extNoDot='', separator='\\', separatorType=BACKSLASH}", info19.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath=null, fileName='..', fullPath='..', paths=[\"..\"], ext='', extNoDot='', separator='\\', separatorType=BACKSLASH}", info20.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath=null, fileName='..', fullPath='..', paths=[\"..\"], ext='', extNoDot='', separator='\\', separatorType=BACKSLASH}", info21.toString());
        // System.out.println(info01);
        // System.out.println(info02);
        // System.out.println(info03);
        // System.out.println(info04);
        // System.out.println(info05);
        // System.out.println(info06);
        // System.out.println(info07);
        // System.out.println(info08);
        // System.out.println(info09);
        // System.out.println(info10);
        // System.out.println(info11);
        // System.out.println(info12);
        // System.out.println(info13);
        // System.out.println(info14);
        // System.out.println(info15);
        // System.out.println(info16);
        // System.out.println(info17);
        // System.out.println(info18);
        // System.out.println(info19);
        // System.out.println(info20);
        // System.out.println(info21);
    }

    @Test
    public void testPathInfoSlash() {
        PathInfo info01 = FilePath.infoSlash("");
        PathInfo info02 = FilePath.infoSlash("/");
        PathInfo info03 = FilePath.infoSlash("\\");
        PathInfo info04 = FilePath.infoSlash(".");
        PathInfo info05 = FilePath.infoSlash("/\\");
        PathInfo info06 = FilePath.infoSlash("///a");
        PathInfo info07 = FilePath.infoSlash("///a/b/");
        PathInfo info08 = FilePath.infoSlash("./a/b\\c\\");
        PathInfo info09 = FilePath.infoSlash(".///a\\b//\\c");
        PathInfo info10 = FilePath.infoSlash(".///a\\b//\\c/");
        PathInfo info11 = FilePath.infoSlash(".///a/111.abc.txt");
        PathInfo info12 = FilePath.infoSlash(".///a/111.abc.tx\\t");
        PathInfo info13 = FilePath.infoSlash(".///a/111.abc.tx/t");
        PathInfo info14 = FilePath.infoSlash(".///a/111.abc.txt/");
        PathInfo info15 = FilePath.infoSlash(".///a\\b//\\c/.");
        PathInfo info16 = FilePath.infoSlash(".///a\\b//\\c/..");
        PathInfo info17 = FilePath.infoSlash(".///a\\b//\\c/..\\");
        PathInfo info18 = FilePath.infoSlash(".///a\\b//\\c/../");
        PathInfo info19 = FilePath.infoSlash("..");
        PathInfo info20 = FilePath.infoSlash("..\\");
        PathInfo info21 = FilePath.infoSlash("../");
        assertNull(info01);
        assertEquals("PathInfo{hasRoot=true, parentPath='/', fileName=null, fullPath='/', paths=[\"/\"], ext='', extNoDot='', separator='/', separatorType=WIN_SLASH}", info02.toString());
        assertEquals("PathInfo{hasRoot=true, parentPath='/', fileName=null, fullPath='/', paths=[\"/\"], ext='', extNoDot='', separator='/', separatorType=WIN_SLASH}", info03.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath=null, fileName='.', fullPath='.', paths=[\".\"], ext='', extNoDot='', separator='/', separatorType=WIN_SLASH}", info04.toString());
        assertEquals("PathInfo{hasRoot=true, parentPath='/', fileName=null, fullPath='/', paths=[\"/\"], ext='', extNoDot='', separator='/', separatorType=WIN_SLASH}", info05.toString());
        assertEquals("PathInfo{hasRoot=true, parentPath='/', fileName='a', fullPath='/a', paths=[\"/\", \"a\"], ext='', extNoDot='', separator='/', separatorType=WIN_SLASH}", info06.toString());
        assertEquals("PathInfo{hasRoot=true, parentPath='/a', fileName='b', fullPath='/a/b', paths=[\"/\", \"a\", \"b\"], ext='', extNoDot='', separator='/', separatorType=WIN_SLASH}", info07.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath='./a/b', fileName='c', fullPath='./a/b/c', paths=[\".\", \"a\", \"b\", \"c\"], ext='', extNoDot='', separator='/', separatorType=WIN_SLASH}", info08.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath='./a/b', fileName='c', fullPath='./a/b/c', paths=[\".\", \"a\", \"b\", \"c\"], ext='', extNoDot='', separator='/', separatorType=WIN_SLASH}", info09.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath='./a/b', fileName='c', fullPath='./a/b/c', paths=[\".\", \"a\", \"b\", \"c\"], ext='', extNoDot='', separator='/', separatorType=WIN_SLASH}", info10.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath='./a', fileName='111.abc.txt', fullPath='./a/111.abc.txt', paths=[\".\", \"a\", \"111.abc.txt\"], ext='.txt', extNoDot='txt', separator='/', separatorType=WIN_SLASH}", info11.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath='./a/111.abc.tx', fileName='t', fullPath='./a/111.abc.tx/t', paths=[\".\", \"a\", \"111.abc.tx\", \"t\"], ext='', extNoDot='', separator='/', separatorType=WIN_SLASH}", info12.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath='./a/111.abc.tx', fileName='t', fullPath='./a/111.abc.tx/t', paths=[\".\", \"a\", \"111.abc.tx\", \"t\"], ext='', extNoDot='', separator='/', separatorType=WIN_SLASH}", info13.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath='./a', fileName='111.abc.txt', fullPath='./a/111.abc.txt', paths=[\".\", \"a\", \"111.abc.txt\"], ext='.txt', extNoDot='txt', separator='/', separatorType=WIN_SLASH}", info14.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath='./a/b', fileName='c', fullPath='./a/b/c', paths=[\".\", \"a\", \"b\", \"c\"], ext='', extNoDot='', separator='/', separatorType=WIN_SLASH}", info15.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath='./a/b/c', fileName='..', fullPath='./a/b/c/..', paths=[\".\", \"a\", \"b\", \"c\", \"..\"], ext='', extNoDot='', separator='/', separatorType=WIN_SLASH}", info16.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath='./a/b/c', fileName='..', fullPath='./a/b/c/..', paths=[\".\", \"a\", \"b\", \"c\", \"..\"], ext='', extNoDot='', separator='/', separatorType=WIN_SLASH}", info17.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath='./a/b/c', fileName='..', fullPath='./a/b/c/..', paths=[\".\", \"a\", \"b\", \"c\", \"..\"], ext='', extNoDot='', separator='/', separatorType=WIN_SLASH}", info18.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath=null, fileName='..', fullPath='..', paths=[\"..\"], ext='', extNoDot='', separator='/', separatorType=WIN_SLASH}", info19.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath=null, fileName='..', fullPath='..', paths=[\"..\"], ext='', extNoDot='', separator='/', separatorType=WIN_SLASH}", info20.toString());
        assertEquals("PathInfo{hasRoot=false, parentPath=null, fileName='..', fullPath='..', paths=[\"..\"], ext='', extNoDot='', separator='/', separatorType=WIN_SLASH}", info21.toString());

        // System.out.println(info01);
        // System.out.println(info02);
        // System.out.println(info03);
        // System.out.println(info04);
        // System.out.println(info05);
        // System.out.println(info06);
        // System.out.println(info07);
        // System.out.println(info08);
        // System.out.println(info09);
        // System.out.println(info10);
        // System.out.println(info11);
        // System.out.println(info12);
        // System.out.println(info13);
        // System.out.println(info14);
        // System.out.println(info15);
        // System.out.println(info16);
        // System.out.println(info17);
        // System.out.println(info18);
        // System.out.println(info19);
        // System.out.println(info20);
        // System.out.println(info21);
    }

    @Test
    public void testExt() {
        String ext1 = FilePath.ext(null);
        String ext2 = FilePath.ext("");
        String ext3 = FilePath.ext("    ");
        String ext4 = FilePath.ext(".");
        String ext5 = FilePath.ext("abc.");
        String ext6 = FilePath.ext(".\\abc.\\");
        String ext7 = FilePath.ext(".\\");
        String ext8 = FilePath.ext(".tx/t");
        String ext9 = FilePath.ext("..txt");
        String ext10 = FilePath.ext("abc. txt");
        String ext11 = FilePath.ext("..");

        assertNull(ext1);
        assertEquals("", ext2);
        assertEquals("", ext3);
        assertEquals("", ext4);
        assertEquals(".", ext5);
        assertEquals("", ext6);
        assertEquals("", ext7);
        assertEquals("", ext8);
        assertEquals(".txt", ext9);
        assertEquals(". txt", ext10);
        assertEquals("", ext11);

        // System.out.println(ext1);
        // System.out.println(ext2);
        // System.out.println(ext3);
        // System.out.println(ext4);
        // System.out.println(ext5);
        // System.out.println(ext6);
        // System.out.println(ext7);
        // System.out.println(ext8);
        // System.out.println(ext9);
        // System.out.println(ext10);
        // System.out.println(ext11);
    }

    @Test
    public void testExtNoDot() {
        String ext1 = FilePath.extNoDot(null);
        String ext2 = FilePath.extNoDot("");
        String ext3 = FilePath.extNoDot("    ");
        String ext4 = FilePath.extNoDot(".");
        String ext5 = FilePath.extNoDot("abc.");
        String ext6 = FilePath.extNoDot(".\\abc.\\");
        String ext7 = FilePath.extNoDot(".\\");
        String ext8 = FilePath.extNoDot(".tx/t");
        String ext9 = FilePath.extNoDot("..txt");
        String ext10 = FilePath.extNoDot("abc. txt");
        String ext11 = FilePath.extNoDot("..");

        assertNull(ext1);
        assertEquals("", ext2);
        assertEquals("", ext3);
        assertEquals("", ext4);
        assertEquals("", ext5);
        assertEquals("", ext6);
        assertEquals("", ext7);
        assertEquals("", ext8);
        assertEquals("txt", ext9);
        assertEquals(" txt", ext10);
        assertEquals("", ext11);

        // System.out.println(ext1);
        // System.out.println(ext2);
        // System.out.println(ext3);
        // System.out.println(ext4);
        // System.out.println(ext5);
        // System.out.println(ext6);
        // System.out.println(ext7);
        // System.out.println(ext8);
        // System.out.println(ext9);
        // System.out.println(ext10);
        // System.out.println(ext11);
    }

    @Test
    public void testPathNull() {
        System.out.println("\n>>> testPathNull");

        String paths1 = FilePath.path("a", (String) null);
        String paths2 = FilePath.path("a", new String[]{null});
        String paths3 = FilePath.path("a", "", null);
        String paths4 = FilePath.path("a", "/b", null);
        String paths5 = FilePath.path("a", new String[]{"b", null, "c"});
        assertNull(paths1);
        assertNull(paths2);
        assertNull(paths3);
        assertNull(paths4);
        assertNull(paths5);
    }

    @Test
    public void testMergeDotSlashAndBackslash() {
        System.out.println("\n>>> testMergeDotSlashAndBackslash");

        String path1 = "/./aa/.a/./././bb/a/b/./././.ccc././././.";
        String mergedPath1 = FilePath.mergeDotSlash(path1);
        System.out.println(mergedPath1);
        assertEquals("/aa/.a/bb/a/b/.ccc./.", mergedPath1);
        String path2 = "\\.\\aa\\.a\\.\\.\\\\.\\.\\.\\bb\\a\\b\\.\\.\\.\\.ccc.\\.\\.\\.\\.";
        String mergedPath2 = FilePath.mergeDotBackslash(path2);
        System.out.println(mergedPath2);
        assertEquals("\\aa\\.a\\\\bb\\a\\b\\.ccc.\\.", mergedPath2);   // \aa\.a\\bb\a\b\.ccc.\.
    }

    @Test
    public void testMergeMultiSlash() {
        System.out.println("\n>>> testMergeMultiSlash");

        String path1 = "aaa///aa///...//";
        String path2 = "aaa\\\\\\aa\\\\\\...\\\\";
        String mergedPath1 = FilePath.mergeSlash(path1, true);
        System.out.println(mergedPath1);
        assertEquals("aaa/aa/.../", mergedPath1);
        String mergedPath2 = FilePath.mergeSlash(path2, false);
        System.out.println(mergedPath2);
        assertEquals("aaa\\aa\\...\\", mergedPath2);

    }

    @Test
    public void testPathCostTime() {
        Try.sleep(2000);
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 10000; i++) {
            String path0 = FilePath.path("\\/", "./\\.//.///", ".././a///b//.\\c//.///abcdefg");
            String path1 = FilePath.path("", "", "\\a", "", "/b/c///d", ".", "e");
            String path2 = FilePath.path(".", "", "\\a", "", "/b/c///d", ".", "e/.");
            String path3 = FilePath.path("/", "", "\\a", "", "/b/c///d", ".", "e/.");
            String path4 = FilePath.pathWin("", "", "\\a", "", "/b/c///d", ".", "e");
            String path5 = FilePath.pathWin(".", "", "\\a", "", "/b/c///d", ".", "e/.");
            String path6 = FilePath.pathWin("/", "", "\\a", "", "/b/c///d", ".", "e/.");
            String path7 = FilePath.pathSlash("\\/", "./\\\\\\.//.///", "\\\\.././a///b//.\\c//.");
            String path8 = FilePath.pathSlash("./a", "b", "./c", "/d///");
            String path9 = FilePath.pathSlash("./a", "\\  /\\ ././b", "./c", "/d///");
        }
        // 10000 * 10??????1084   1150   1182
        System.out.println("PathCostTime1: " + (System.currentTimeMillis() - startTime));

        startTime = System.currentTimeMillis();
        for (int i = 0; i < 50000; i++) {
            String path0 = FilePath.path("/???????????????", "/??????1/??????2/??????3/", "????????????????????????????????????????????????????????????????????????abcdefghijklmn");
            String path1 = FilePath.pathSlash("E:\\???????????????", "\\??????1\\??????2\\??????3\\", "????????????????????????????????????????????????????????????????????????abcdefghijklmn");
        }
        // 50000 * 2??????747   1351   804
        System.out.println("PathCostTime2: " + (System.currentTimeMillis() - startTime));

    }
}
