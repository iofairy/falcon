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

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import static com.iofairy.falcon.zip.ArchiveType.*;

/**
 * 归档文件的格式<br>
 * <a href="https://en.wikipedia.org/wiki/List_of_archive_formats">Archive formats</a>
 *
 * @since 0.3.7
 */
public enum ArchiveFormat {
    /*
     * ARCHIVING_ONLY
     */
    A(EnumSet.of(ARCHIVING_ONLY), ".a", "Unix Archiver"),
    AR(EnumSet.of(ARCHIVING_ONLY), ".ar", "Unix Archiver"),
    CPIO(EnumSet.of(ARCHIVING_ONLY), ".cpio", "cpio"),
    SHAR(EnumSet.of(ARCHIVING_ONLY), ".shar", "Shell archive"),
    LBR(EnumSet.of(ARCHIVING_ONLY), ".LBR", ".LBR"),
    MAR(EnumSet.of(ARCHIVING_ONLY), ".mar", "Mozilla ARchive"),
    SBX(EnumSet.of(ARCHIVING_ONLY), ".sbx", "SeqBox"),
    TAR(EnumSet.of(ARCHIVING_ONLY), ".tar", "Tape archive"),
    /*
     * COMPRESSION_ONLY
     */
    BROTLI(EnumSet.of(COMPRESSION_ONLY), ".br", "Brotli"),
    BZIP2(EnumSet.of(COMPRESSION_ONLY), ".bz2", "bzip2"),
    GZIP(EnumSet.of(COMPRESSION_ONLY), ".gz", "gzip"),
    LZIP(EnumSet.of(COMPRESSION_ONLY), ".lz", "lzip"),
    LZ4(EnumSet.of(COMPRESSION_ONLY), ".lz4", "LZ4"),
    LZMA(EnumSet.of(COMPRESSION_ONLY), ".lzma", "lzma"),
    LZOP(EnumSet.of(COMPRESSION_ONLY), ".lzo", "lzop"),
    RZIP(EnumSet.of(COMPRESSION_ONLY), ".rz", "rzip"),
    SNAPPY(EnumSet.of(COMPRESSION_ONLY), ".sz", "Snappy"),
    XZ(EnumSet.of(COMPRESSION_ONLY), ".xz", "xz"),
    Z_PACK(EnumSet.of(COMPRESSION_ONLY), ".z", "pack"),
    Z_COMPRESS(EnumSet.of(COMPRESSION_ONLY), ".Z", "compress"),
    ZSTD(EnumSet.of(COMPRESSION_ONLY), ".zst", "Zstandard"),
    /*
     * MULTI_FUNCTION
     */
    SEVEN_ZIP(EnumSet.of(MULTI_FUNCTION), ".7z", "7z"),
    S7Z(EnumSet.of(MULTI_FUNCTION), ".s7z", "7zX"),
    ACE(EnumSet.of(MULTI_FUNCTION), ".ace", "ACE"),
    ARJ(EnumSet.of(MULTI_FUNCTION), ".arj", "ARJ"),
    RAR(EnumSet.of(MULTI_FUNCTION), ".rar", "RAR"),
    TAR_GZ(EnumSet.of(MULTI_FUNCTION), ".tar.gz", "tar with gzip"),
    TAR_Z(EnumSet.of(MULTI_FUNCTION), ".tar.Z", "tar with compress"),
    TAR_BZ2(EnumSet.of(MULTI_FUNCTION), ".tar.bz2", "tar with bzip2"),
    TBZ2(EnumSet.of(MULTI_FUNCTION), ".tbz2", "tar with bzip2"),
    TAR_LZ(EnumSet.of(MULTI_FUNCTION), ".tar.lz", "tar with lzip"),
    TLZ(EnumSet.of(MULTI_FUNCTION), ".tlz", "tar with lzip"),
    TAR_XZ(EnumSet.of(MULTI_FUNCTION), ".tar.xz", "tar with xz"),
    TXZ(EnumSet.of(MULTI_FUNCTION), ".txz", "tar with xz"),
    TAR_ZST(EnumSet.of(MULTI_FUNCTION), ".tar.zst", "tar with zstd"),
    ZIP(EnumSet.of(MULTI_FUNCTION), ".zip", "ZIP"),
    ZZIP(EnumSet.of(MULTI_FUNCTION), ".zz", "Zzip"),
    /*
     * SOFTWARE_PACKAGING and MULTI_FUNCTION
     */
    DEB(EnumSet.of(SOFTWARE_PACKAGING, MULTI_FUNCTION), ".deb", "Debian package (deb)"),
    PKG(EnumSet.of(SOFTWARE_PACKAGING, MULTI_FUNCTION), ".pkg", "Macintosh Installer"),
    MPKG(EnumSet.of(SOFTWARE_PACKAGING, MULTI_FUNCTION), ".mpkg", "Macintosh Installer"),
    RPM(EnumSet.of(SOFTWARE_PACKAGING, MULTI_FUNCTION), ".rpm", "RPM Package Manager (RPM)"),
    TGZ(EnumSet.of(SOFTWARE_PACKAGING, MULTI_FUNCTION), ".tgz", "Slackware Package"),
    MSI(EnumSet.of(SOFTWARE_PACKAGING, MULTI_FUNCTION), ".msi", "Windows Installer (also MSI)"),
    JAR(EnumSet.of(SOFTWARE_PACKAGING, MULTI_FUNCTION), ".jar", "Java Archive (JAR)"),
    WAR(EnumSet.of(SOFTWARE_PACKAGING, MULTI_FUNCTION), ".war", "Web Application archive (Java-based web app)"),
    APK(EnumSet.of(SOFTWARE_PACKAGING, MULTI_FUNCTION), ".apk", "Android application package"),
    CRX(EnumSet.of(SOFTWARE_PACKAGING, MULTI_FUNCTION), ".crx", "Google Chrome extension package"),
    /*
     * DISK_IMAGE
     */
    DMG(EnumSet.of(DISK_IMAGE, MULTI_FUNCTION), ".dmg", "Apple Disk Image"),
    ISO(EnumSet.of(DISK_IMAGE, ARCHIVING_ONLY), ".iso", "ISO-9660 image");


    /**
     * 归档类型
     */
    public final EnumSet<ArchiveType> archiveTypes;
    /**
     * 归档或压缩的扩展名
     */
    public final String extName;
    /**
     * 归档器或压缩器的官方名称
     */
    public final String officialName;

    static final Map<String, ArchiveFormat> SA_MAP = new HashMap<>();

    static {
        for (ArchiveFormat value : values()) {
            String ext = value.extName;
            if (ext.equals(".z") || ext.equals(".Z")) {
                SA_MAP.put(value.extName, value);
            } else {
                SA_MAP.put(ext.toLowerCase(), value);
            }
        }
    }

    ArchiveFormat(EnumSet<ArchiveType> archiveTypes, String extName, String officialName) {
        this.archiveTypes = archiveTypes;
        this.extName = extName;
        this.officialName = officialName;
    }

    public static ArchiveFormat of(String extName) {
        if (extName == null) return null;

        if (!extName.startsWith(".")) extName = "." + extName;
        return SA_MAP.get(extName.equals(".z") || extName.equals(".Z") ? extName : extName.toLowerCase());
    }

}
