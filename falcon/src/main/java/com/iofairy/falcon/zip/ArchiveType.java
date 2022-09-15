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
package com.iofairy.falcon.zip;

/**
 * 归档文件的类型<br>
 * <a href="https://en.wikipedia.org/wiki/Archive_file#Types">Archive file Types</a>
 *
 * @since 0.3.7
 */
public enum ArchiveType {
    /**
     * Archiving only formats store metadata and concatenate files
     */
    ARCHIVING_ONLY,
    /**
     * Compression only formats only compress files
     */
    COMPRESSION_ONLY,
    /**
     * Multi-function(Archiving and compression) formats can store metadata, concatenate, compress, encrypt, create error detection
     * and recovery information, and package the archive into self-extracting and self-expanding files
     */
    MULTI_FUNCTION,
    /**
     * Software packaging formats are used to create software packages that may be self-installing files
     */
    SOFTWARE_PACKAGING,
    /**
     * Disk image formats are used to create disk images of mass storage volumes
     */
    DISK_IMAGE

}
