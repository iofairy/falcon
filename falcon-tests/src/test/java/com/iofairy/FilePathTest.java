package com.iofairy;

import com.iofairy.falcon.fs.FilePath;
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

}
