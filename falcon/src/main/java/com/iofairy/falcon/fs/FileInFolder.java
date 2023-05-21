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

/**
 * 文件相对于某个基础路径的信息
 *
 * @since 0.4.3
 */
public class FileInFolder {
    /**
     * 当前文件
     */
    private File file;
    /**
     * 基础路径
     */
    private String basePath;
    /**
     * 相对于基础路径的名称（或路径）
     */
    private String relativePath;
    /**
     * 前面带上基础路径的相对路径的名称（或路径）
     */
    private String relativePathWithBaseName;
    /**
     * 当前文件是否是基础文件
     */
    private boolean isBaseFile;
    /**
     * 父目录是否是根目录
     */
    private boolean isBasePathRoot;

    public FileInFolder() {
    }

    public FileInFolder(File file, String basePath, String relativePath, String relativePathWithBaseName, boolean isBaseFile, boolean isBasePathRoot) {
        this.file = file;
        this.basePath = basePath;
        this.relativePath = relativePath;
        this.relativePathWithBaseName = relativePathWithBaseName;
        this.isBaseFile = isBaseFile;
        this.isBasePathRoot = isBasePathRoot;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getRelativePathWithBaseName() {
        return relativePathWithBaseName;
    }

    public void setRelativePathWithBaseName(String relativePathWithBaseName) {
        this.relativePathWithBaseName = relativePathWithBaseName;
    }

    public boolean isBaseFile() {
        return isBaseFile;
    }

    public void setBaseFile(boolean baseFile) {
        isBaseFile = baseFile;
    }

    public boolean isBasePathRoot() {
        return isBasePathRoot;
    }

    public void setBasePathRoot(boolean basePathRoot) {
        isBasePathRoot = basePathRoot;
    }

    @Override
    public String toString() {
        return "FileInFolder{" +
                "file=" + file +
                ", basePath='" + basePath + '\'' +
                ", relativePath='" + relativePath + '\'' +
                ", relativePathWithBaseName='" + relativePathWithBaseName + '\'' +
                ", isBaseFile=" + isBaseFile +
                ", isBasePathRoot=" + isBasePathRoot +
                '}';
    }
}
