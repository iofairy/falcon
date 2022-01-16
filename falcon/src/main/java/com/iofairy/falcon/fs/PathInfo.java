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

/**
 * Path Info <br>
 * 路径信息
 *
 * @since 0.0.6
 */
public class PathInfo {
    /**
     * 首字符是否是 '/' 或 '\'
     */
    private boolean hasRoot;
    /**
     * 路径最后一部分的父目录名称
     */
    private String parentPath;
    /**
     * 路径最后的部分：文件/文件夹名称
     */
    private String fileName;
    /**
     * 完整路径
     */
    private String fullPath;
    /**
     * 路径的每个部分
     */
    private String[] paths;
    /**
     * 获取文件后缀名（如：.txt, .csv）
     */
    private String ext;
    /**
     * 获取文件后缀名，不带点（如：txt, csv）
     */
    private String extNoDot;
    /**
     * 路径分隔符
     */
    private String separator;
    /**
     * 分隔符类型
     */
    private SeparatorType separatorType;

    public PathInfo() {
    }

    public PathInfo(boolean hasRoot, String parentPath, String fileName, String fullPath, String[] paths, String ext, String extNoDot, String separator, SeparatorType separatorType) {
        this.hasRoot = hasRoot;
        this.parentPath = parentPath;
        this.fileName = fileName;
        this.fullPath = fullPath;
        this.paths = paths;
        this.ext = ext;
        this.extNoDot = extNoDot;
        this.separator = separator;
        this.separatorType = separatorType;
    }

    public boolean isHasRoot() {
        return hasRoot;
    }

    public void setHasRoot(boolean hasRoot) {
        this.hasRoot = hasRoot;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String[] getPaths() {
        return paths;
    }

    public void setPaths(String[] paths) {
        this.paths = paths;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getExtNoDot() {
        return extNoDot;
    }

    public void setExtNoDot(String extNoDot) {
        this.extNoDot = extNoDot;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public SeparatorType getSeparatorType() {
        return separatorType;
    }

    public void setSeparatorType(SeparatorType separatorType) {
        this.separatorType = separatorType;
    }

    @Override
    public String toString() {
        String tmpParentPath = parentPath == null ? null : '\'' + parentPath + '\'';
        String tmpFileName = fileName == null ? null : '\'' + fileName + '\'';
        String tmpFullPath = fullPath == null ? null : '\'' + fullPath + '\'';
        String tmpExt = ext == null ? null : '\'' + ext + '\'';
        String tmpExtNoDot = extNoDot == null ? null : '\'' + extNoDot + '\'';
        return "PathInfo{" +
                "hasRoot=" + hasRoot +
                ", parentPath=" + tmpParentPath +
                ", fileName=" + tmpFileName +
                ", fullPath=" + tmpFullPath +
                ", paths=" + G.toString(paths) +
                ", ext=" + tmpExt +
                ", extNoDot=" + tmpExtNoDot +
                ", separator='" + separator + '\'' +
                ", separatorType=" + separatorType +
                '}';
    }
}
