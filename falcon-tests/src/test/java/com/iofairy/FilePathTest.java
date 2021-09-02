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

        String path1 = FilePath.path("./a", "b", "./c/", "/d");
        String path2 = FilePath.path("/", "./");
        String path3 = FilePath.path("./", "./", "//", "/.");
        String path4 = FilePath.path("./.", "./");
        String path5 = FilePath.path(".///.", "./");
        String path6 = FilePath.path(".\\./", "./");
        String path7 = FilePath.path("\\/", "./.");
        String path8 = FilePath.path("\\/", "./\\.//.///", ".././a///b//.\\c//.");
        String path9 = FilePath.path("./a", "b", "./c", "/d///");
        String path10 = FilePath.path("./a", "\\  /\\ ././b", "./c", "/d///");
        String path11 = FilePath.path("\\/", "./a.");
        String path12 = FilePath.path("/abc/", "./a.", "b", "/c/", "d/");

        assertEquals("./a/b/c/d", path1);
        assertEquals("/", path2);
        assertEquals(".", path3);
        assertEquals(".", path4);
        assertEquals(".", path5);
        assertEquals(".\\.", path6);                //  .\.
        assertEquals("\\", path7);                  //  \
        assertEquals("\\/\\./../a/b/.\\c", path8);   //   \/\./../a/b/.\c
        assertEquals("./a/b/c/d", path9);
        assertEquals("./a/\\  /\\ ./b/c/d", path10); //   ./a/\  /\ ./b/c/d
        assertEquals("\\/a.", path11);               //   \/a.
        assertEquals("/abc/a./b/c/d", path12);
    }

    @Test
    public void testPathWin() {
        System.out.println("\n>>> testPathWin");

        String path1 = FilePath.pathWin("./a", "\\\\b", "\\\\./c", "/d");
        String path2 = FilePath.pathWin("/", "./");
        String path3 = FilePath.pathWin("./", "./");
        String path4 = FilePath.pathWin("./.", "./");
        String path5 = FilePath.pathWin(".///.", "\\\\\\\\./");
        String path6 = FilePath.pathWin(".\\./", "./");
        String path7 = FilePath.pathWin("\\/", "./.");
        String path8 = FilePath.pathWin("\\/", "./\\\\\\.//.///", "\\\\.././a///b//.\\c//.");
        String path9 = FilePath.pathWin("./a", "b", "./c", "/d///");
        String path10 = FilePath.pathWin("./a", "\\  /\\ ././b", "./c", "/d///");
        String path11 = FilePath.pathWin("\\/", "./a.");
        String path12 = FilePath.pathWin("/abc/", "./a.", "b", "/c/", "d/", "\\e");

        assertEquals(".\\a\\b\\c\\d", path1);
        assertEquals("\\", path2);
        assertEquals(".", path3);
        assertEquals(".", path4);
        assertEquals(".", path5);
        assertEquals(".", path6);
        assertEquals("\\", path7);
        assertEquals("\\..\\a\\b\\c", path8);
        assertEquals(".\\a\\b\\c\\d", path9);
        assertEquals(".\\a\\  \\ .\\b\\c\\d", path10);
        assertEquals("\\a.", path11);
        assertEquals("\\abc\\a.\\b\\c\\d\\e", path12);

    }

    @Test
    public void testPathSlash() {
        System.out.println("\n>>> testPathSlash");

        String path1 = FilePath.pathSlash("./a", "\\\\b", "\\\\./c", "/d");
        String path2 = FilePath.pathSlash("/", "./");
        String path3 = FilePath.pathSlash("./", "./");
        String path4 = FilePath.pathSlash("./.", "./");
        String path5 = FilePath.pathSlash(".///.", "\\\\\\\\./");
        String path6 = FilePath.pathSlash(".\\./", "./");
        String path7 = FilePath.pathSlash("\\/", "./.");
        String path8 = FilePath.pathSlash("\\/", "./\\\\\\.//.///", "\\\\.././a///b//.\\c//.");
        String path9 = FilePath.pathSlash("./a", "b", "./c", "/d///");
        String path10 = FilePath.pathSlash("./a", "\\  /\\ ././b", "./c", "/d///");
        String path11 = FilePath.pathSlash("\\/", "./a.");
        String path12 = FilePath.pathSlash("/abc/", "./a.", "b", "/c/", "d/", "\\e");

        assertEquals("./a/b/c/d", path1);
        assertEquals("/", path2);
        assertEquals(".", path3);
        assertEquals(".", path4);
        assertEquals(".", path5);
        assertEquals(".", path6);
        assertEquals("/", path7);
        assertEquals("/../a/b/c", path8);
        assertEquals("./a/b/c/d", path9);
        assertEquals("./a/  / ./b/c/d", path10);
        assertEquals("/a.", path11);
        assertEquals("/abc/a./b/c/d/e", path12);

    }

    @Test
    public void testPathNull() {
        System.out.println("\n>>> testPathNull");

        String paths1 = FilePath.path("");
        String paths2 = FilePath.path("a");
        String paths3 = FilePath.path("a", "");
        String paths4 = FilePath.path("a", "b", "");
        assertNull(paths1);
        assertNull(paths2);
        assertNull(paths3);
        assertNull(paths4);
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
