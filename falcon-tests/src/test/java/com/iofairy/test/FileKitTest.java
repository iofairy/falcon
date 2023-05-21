package com.iofairy.test;

import com.iofairy.falcon.fs.FileInFolder;
import com.iofairy.falcon.fs.FileKit;
import com.iofairy.falcon.fs.FilePath;
import com.iofairy.falcon.os.OS;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author GG
 * @version 1.0
 * @date 2022/9/11 8:05
 */
public class FileKitTest {

    static String resourcePath = FilePath.pathAuto(OS.USER_DIR, "src/test/resources");

    @Test
    public void testListFiles() {
        String pathForFileListTest = FilePath.pathAuto(resourcePath, "fileListTest");
        File thisFile = new File(pathForFileListTest);
        File[] files = FileKit.listFiles(thisFile);
        File[] files1 = FileKit.listFiles(thisFile, 1);
        File[] files2 = FileKit.listFiles(thisFile, 1, f -> f.isDirectory() || f.getName().endsWith(".gz"));
        File[] files3 = FileKit.listFiles(thisFile, -1);
        File[] files4 = FileKit.listFiles(thisFile, -1, f -> f.isDirectory() || f.getName().endsWith(".gz"));

        // System.out.println(Arrays.stream(files).map(File::getName).collect(Collectors.toList()));
        // System.out.println(Arrays.stream(files1).map(File::getName).collect(Collectors.toList()));
        // System.out.println(Arrays.stream(files2).map(File::getName).collect(Collectors.toList()));
        // System.out.println(Arrays.stream(files3).map(File::getName).collect(Collectors.toList()));
        // System.out.println(Arrays.stream(files4).map(File::getName).collect(Collectors.toList()));

        assertEquals(Arrays.stream(files).map(File::getName).collect(Collectors.toList()).toString(),  "[文本文档 - 副本.txt, 文本文档 - 副本.txt.gz, 文本文档.7z, 文本文档.txt, 新建文件夹 (2), 空文件夹]");
        assertEquals(Arrays.stream(files1).map(File::getName).collect(Collectors.toList()).toString(), "[文本文档 - 副本.txt, 文本文档 - 副本.txt.gz, 文本文档.7z, 文本文档.txt, 新建文件夹 (2), 文本文档-第3层.7z, 新建文件夹-第3层, 空文件夹]");
        assertEquals(Arrays.stream(files2).map(File::getName).collect(Collectors.toList()).toString(), "[文本文档 - 副本.txt.gz, 新建文件夹 (2), 新建文件夹-第3层, 空文件夹]");
        assertEquals(Arrays.stream(files3).map(File::getName).collect(Collectors.toList()).toString(), "[文本文档 - 副本.txt, 文本文档 - 副本.txt.gz, 文本文档.7z, 文本文档.txt, 新建文件夹 (2), 文本文档-第3层.7z, 新建文件夹-第3层, 文本文档 - 副本-第4层.txt, 文本文档 - 副本-第4层.txt.gz, 文本文档-第4层.txt, 空文件夹]");
        assertEquals(Arrays.stream(files4).map(File::getName).collect(Collectors.toList()).toString(), "[文本文档 - 副本.txt.gz, 新建文件夹 (2), 新建文件夹-第3层, 文本文档 - 副本-第4层.txt.gz, 空文件夹]");

        // System.out.println("============================================================");
        // Arrays.asList(files).forEach(System.out::println);
        // System.out.println("============================================================");
        // Arrays.asList(files1).forEach(System.out::println);
        // System.out.println("============================================================");
        // Arrays.asList(files2).forEach(System.out::println);
        // System.out.println("============================================================");
        // Arrays.asList(files3).forEach(System.out::println);
        // System.out.println("============================================================");
        // Arrays.asList(files4).forEach(System.out::println);
    }

    @Test
    public void testListFileInFolders() {
        String path = FilePath.pathAuto(resourcePath, "fileListTest");
        List<FileInFolder> fileInFolders = FileKit.listFileInFolders(path);
        fileInFolders.forEach(System.out::println);
        assertEquals(fileInFolders.size(), 12);

        FileInFolder fileInFolder = fileInFolders.get(9);
        assertEquals(fileInFolder.getRelativePath(), "新建文件夹 (2)/新建文件夹-第3层/文本文档 - 副本-第4层.txt.gz");
        assertEquals(fileInFolder.getRelativePathWithBaseName(), "fileListTest/新建文件夹 (2)/新建文件夹-第3层/文本文档 - 副本-第4层.txt.gz");

        fileInFolders = FileKit.listFileInFolders((String) null);
        assertEquals(fileInFolders.size(), 0);

        fileInFolders = FileKit.listFileInFolders(FilePath.pathAuto(resourcePath, "fileListTest/文本文档 - 副本.txt"));
        assertEquals(fileInFolders.size(), 1);
        System.out.println(fileInFolders);
        assertEquals(fileInFolders.get(0).getRelativePathWithBaseName(), "文本文档 - 副本.txt");
    }
}
