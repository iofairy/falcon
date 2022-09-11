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
     * 根目录（如果是路径是相对路径，则为{@code null}）
     */
    private String root;
    /**
     * 路径最后一部分的父目录名称（如果路径只有根目录，则为 {@code null}）
     */
    private String parentPath;
    /**
     * 完整路径
     */
    private String fullPath;
    /**
     * 路径的每个部分
     */
    private String[] paths;
    /**
     * 文件名对象
     */
    private FileName fileName;
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

    public PathInfo(boolean hasRoot, String root, String parentPath, String fullPath, String[] paths, String fileName, String separator, SeparatorType separatorType) {
        this.hasRoot = hasRoot;
        this.root = root;
        this.parentPath = parentPath;
        this.fullPath = fullPath;
        this.paths = paths;
        this.fileName = FileName.of(fileName);
        this.separator = separator;
        this.separatorType = separatorType;
    }

    public boolean isHasRoot() {
        return hasRoot;
    }

    public void setHasRoot(boolean hasRoot) {
        this.hasRoot = hasRoot;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
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

    public FileName getFileName() {
        return fileName;
    }

    public void setFileName(FileName fileName) {
        this.fileName = fileName;
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
        String tmpRoot = root == null ? null : '\'' + root + '\'';
        String tmpParentPath = parentPath == null ? null : '\'' + parentPath + '\'';
        String tmpFullPath = fullPath == null ? null : '\'' + fullPath + '\'';
        return "PathInfo{" +
                "hasRoot=" + hasRoot +
                ", root=" + tmpRoot +
                ", fullPath=" + tmpFullPath +
                ", parentPath=" + tmpParentPath +
                ", fileName=" + fileName +
                ", paths=" + G.toString(paths) +
                ", separator='" + separator + '\'' +
                ", separatorType=" + separatorType +
                '}';
    }
}
