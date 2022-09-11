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

import com.iofairy.top.G;

import java.util.Arrays;

/**
 * 文件名信息
 *
 * @since 0.3.5
 */
public class FileName {
    /**
     * 文件、文件夹名称
     */
    public final String name;
    /**
     * 后缀名数组（如果为{@code null} 或者 {@code exts.length <= 1}，则说明没有后缀名）
     */
    public final String[] exts;
    /**
     * 没有后缀名{@link #ext}的文件名称
     */
    public final String nameNoExt;
    /**
     * 没有后缀名{@link #ext1}的文件名称
     */
    public final String nameNoExt1;
    /**
     * 没有后缀名{@link #ext2}的文件名称
     */
    public final String nameNoExt2;
    /**
     * 获取文件后缀名（如：.txt, .csv）
     */
    public final String ext;
    /**
     * 获取文件后缀名（如：.tar.gz）
     */
    public final String ext1;
    /**
     * 获取文件后缀名（如：.a.b.c）
     */
    public final String ext2;
    /**
     * 获取文件后缀名，不带点（如：txt, csv）
     */
    public final String extNoDot;
    /**
     * 获取文件后缀名，不带点（如：tar.gz）
     */
    public final String extNoDot1;
    /**
     * 获取文件后缀名，不带点（如：a.b.c）
     */
    public final String extNoDot2;

    public FileName(String fileName) {
        this.name = fileName;
        this.exts = FilePath.exts(fileName);

        String nameNoExt = null;
        String nameNoExt1 = null;
        String nameNoExt2 = null;
        String ext = null;
        String ext1 = null;
        String ext2 = null;
        String extNoDot = null;
        String extNoDot1 = null;
        String extNoDot2 = null;

        if (!G.isEmpty(exts) && exts.length >= 2) {
            /*
             * 这里的 exts 是从文件名中获得的，所以不会存在斜杠（/），不需要再做是否存在斜杠（/）的校验
             */
            int length = exts.length;
            nameNoExt = String.join(".", Arrays.copyOfRange(exts, 0, length - 1));
            extNoDot = exts[length - 1];
            ext = "." + extNoDot;
            if (length >= 3) {
                nameNoExt1 = String.join(".", Arrays.copyOfRange(exts, 0, length - 2));
                extNoDot1 = exts[length - 2] + ext;
                ext1 = "." + extNoDot1;
            }
            if (length >= 4) {
                nameNoExt2 = String.join(".", Arrays.copyOfRange(exts, 0, length - 3));
                extNoDot2 = exts[length - 3] + ext1;
                ext2 = "." + extNoDot2;
            }
        }

        this.nameNoExt = nameNoExt;
        this.nameNoExt1 = nameNoExt1;
        this.nameNoExt2 = nameNoExt2;
        this.ext = ext;
        this.ext1 = ext1;
        this.ext2 = ext2;
        this.extNoDot = extNoDot;
        this.extNoDot1 = extNoDot1;
        this.extNoDot2 = extNoDot2;
    }

    public static FileName of(String fileName) {
        return new FileName(fileName);
    }

    @Override
    public String toString() {
        String tmpName = name == null ? null : '\'' + name + '\'';
        String tmpNameNoExt = nameNoExt == null ? null : '\'' + nameNoExt + '\'';
        String tmpNameNoExt1 = nameNoExt1 == null ? null : '\'' + nameNoExt1 + '\'';
        String tmpNameNoExt2 = nameNoExt2 == null ? null : '\'' + nameNoExt2 + '\'';
        String tmpExt = ext == null ? null : '\'' + ext + '\'';
        String tmpExt1 = ext1 == null ? null : '\'' + ext1 + '\'';
        String tmpExt2 = ext2 == null ? null : '\'' + ext2 + '\'';
        String tmpExtNoDot = extNoDot == null ? null : '\'' + extNoDot + '\'';
        String tmpExtNoDot1 = extNoDot1 == null ? null : '\'' + extNoDot1 + '\'';
        String tmpExtNoDot2 = extNoDot2 == null ? null : '\'' + extNoDot2 + '\'';
        return "FileName{" +
                "name=" + tmpName +
                ", exts=" + G.toString(exts) +
                ", nameNoExt=" + tmpNameNoExt +
                ", nameNoExt1=" + tmpNameNoExt1 +
                ", nameNoExt2=" + tmpNameNoExt2 +
                ", ext=" + tmpExt +
                ", ext1=" + tmpExt1 +
                ", ext2=" + tmpExt2 +
                ", extNoDot=" + tmpExtNoDot +
                ", extNoDot1=" + tmpExtNoDot1 +
                ", extNoDot2=" + tmpExtNoDot2 +
                '}';
    }
}
