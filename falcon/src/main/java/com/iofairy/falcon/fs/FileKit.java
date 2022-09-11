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

}
