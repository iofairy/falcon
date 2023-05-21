/*
 * Copyright (C) 2021 iofairy, <https://github.com/iofairy/falcon>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iofairy.falcon.fs;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * File工具套件
 *
 * @since 0.3.5
 */
public class FileKit {
    /**
     * 递归遍历文件夹，获取文件夹下指定的文件及文件夹<br>
     * 注：如果没有权限，{@code file}参数不存在或不是文件夹，都将返回{@code null}
     *
     * @param file 文件夹
     * @return 指定的文件及文件夹的数组（不包含当前目录）
     */
    public static File[] listFiles(File file) {
        return listFiles(file, 0, null);
    }

    /**
     * 递归遍历文件夹，获取文件夹下指定的文件及文件夹<br>
     * 注：如果没有权限，{@code file}参数不存在或不是文件夹，都将返回{@code null}
     *
     * @param file   文件夹
     * @param filter 文件过滤
     * @return 指定的文件及文件夹的数组（不包含当前目录）
     */
    public static File[] listFiles(File file, FileFilter filter) {
        return listFiles(file, 0, filter);
    }

    /**
     * 递归遍历文件夹，获取文件夹下指定的文件及文件夹<br>
     * 注：如果没有权限，{@code file}参数不存在或不是文件夹，都将返回{@code null}
     *
     * @param file           文件夹
     * @param recursiveLevel 文件夹递归层级。-1：无限递归；0：不递归；&gt;0：递归次数
     * @return 指定的文件及文件夹的数组（不包含当前目录）
     */
    public static File[] listFiles(File file, int recursiveLevel) {
        return listFiles(file, recursiveLevel, null);
    }

    /**
     * 递归遍历文件夹，获取文件夹下指定的文件及文件夹<br>
     * 注：如果没有权限，{@code file}参数不存在或不是文件夹，都将返回{@code null}
     *
     * @param file           文件夹
     * @param recursiveLevel 文件夹递归层级。-1：无限递归；0：不递归；&gt;0：递归次数
     * @param filter         文件过滤
     * @return 指定的文件及文件夹的数组（不包含当前目录）
     */
    public static File[] listFiles(File file, int recursiveLevel, FileFilter filter) {
        if (file == null || !file.exists() || !file.isDirectory()) return null;
        File[] fileArray = file.listFiles(filter);
        if (fileArray == null) return null;
        List<File> files = new ArrayList<>();
        for (File f : fileArray) {
            listFiles(f, files, recursiveLevel, filter);
        }
        return files.toArray(new File[0]);
    }

    private static void listFiles(File file, List<File> files, int recursiveLevel, FileFilter filter) {
        files.add(file);
        if (file.isDirectory() && recursiveLevel != 0) {
            File[] fileArray = file.listFiles(filter);
            if (fileArray == null) return;
            for (File f : fileArray) {
                listFiles(f, files, recursiveLevel - 1, filter);
            }
        }
    }

    /**
     * 获取FileInFolder列表。如果文件不存在，则返回空列表
     *
     * @param filePath 文件路径
     * @return FileInFolder列表
     * @throws SecurityException the file does not have permission to access
     * @since 0.4.3
     */
    public static List<FileInFolder> listFileInFolders(String filePath) {
        if (filePath == null) return new ArrayList<>();
        return listFileInFolders(new File(filePath), false);
    }

    /**
     * 获取FileInFolder列表。如果文件不存在，则返回空列表
     *
     * @param file 文件
     * @return FileInFolder列表
     * @throws SecurityException the file does not have permission to access
     * @since 0.4.3
     */
    public static List<FileInFolder> listFileInFolders(File file) {
        return listFileInFolders(file, false);
    }

    /**
     * 获取FileInFolder列表。如果文件不存在，则返回空列表
     *
     * @param filePath                    文件路径
     * @param isExcludeFilesNoPermissions 是否排除掉没有权限访问的文件或目录（如果不排除，当没有权限访问时，直接抛出异常）
     * @return FileInFolder列表
     * @throws SecurityException the file does not have permission to access
     * @since 0.4.3
     */
    public static List<FileInFolder> listFileInFolders(String filePath, boolean isExcludeFilesNoPermissions) {
        if (filePath == null) return new ArrayList<>();
        return listFileInFolders(new File(filePath), isExcludeFilesNoPermissions);
    }

    /**
     * 获取FileInFolder列表。如果文件不存在，则返回空列表
     *
     * @param file                        文件
     * @param isExcludeFilesNoPermissions 是否排除掉没有权限访问的文件或目录（如果不排除，当没有权限访问时，直接抛出异常）
     * @return FileInFolder列表
     * @throws SecurityException the file does not have permission to access
     * @since 0.4.3
     */
    public static List<FileInFolder> listFileInFolders(File file, boolean isExcludeFilesNoPermissions) {
        List<FileInFolder> fileInFolders = new ArrayList<>();
        if (file != null && file.exists()) {
            String absolutePath = file.getAbsolutePath();
            String fileName = file.getName();
            if (file.isFile()) {
                FileInFolder fileInFolder = new FileInFolder(file, absolutePath, "", fileName, true, false);
                fileInFolders.add(fileInFolder);
            } else {
                boolean isBasePathRoot = file.getParent() == null;
                FileInFolder fileInFolder = new FileInFolder(file, absolutePath, "", fileName, true, isBasePathRoot);
                fileInFolders.add(fileInFolder);
                File[] files = file.listFiles();
                if (files == null) {
                    if (!isExcludeFilesNoPermissions)
                        throw new SecurityException("\"" + absolutePath + "\" the file does not exist, or has no permission to access!");
                } else {
                    for (File listFile : files) {
                        listFileInFolders(fileInFolders, listFile, absolutePath, isBasePathRoot, "", isBasePathRoot ? "" : fileName + "/", isExcludeFilesNoPermissions);
                    }
                }
            }
        }
        return fileInFolders;
    }

    private static void listFileInFolders(List<FileInFolder> fileInFolders, File file, String basePath, boolean isBasePathRoot, String prefix, String prefixWithBaseName, boolean isExcludeFilesNoPermissions) {
        String fileName = file.getName();
        FileInFolder fileInFolder = new FileInFolder(file, basePath, prefix + fileName, prefixWithBaseName + fileName, false, isBasePathRoot);
        fileInFolders.add(fileInFolder);

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            String absolutePath = file.getAbsolutePath();

            if (files == null) {
                if (!isExcludeFilesNoPermissions) {
                    throw new SecurityException("\"" + absolutePath + "\" the file does not exist, or has no permission to access!");
                }
            } else {
                for (File listFile : files) {
                    listFileInFolders(fileInFolders, listFile, basePath, isBasePathRoot, prefix + fileName + "/", prefixWithBaseName + fileName + "/", isExcludeFilesNoPermissions);
                }
            }
        }
    }

}
