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

import java.util.*;

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
    SZ(EnumSet.of(COMPRESSION_ONLY), ".sz", "The Snappy uses either 'framing' or 'framing2'"),
    SNAPPY(EnumSet.of(COMPRESSION_ONLY), ".snappy", "The snappy in Java"),
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
    TGZ(EnumSet.of(SOFTWARE_PACKAGING, MULTI_FUNCTION), ".tgz", "tar with gzip or Slackware Package(based on tar with gzip)"),
    TAZ(EnumSet.of(MULTI_FUNCTION), ".taz", "tar with gzip"),
    TAR_Z(EnumSet.of(MULTI_FUNCTION), ".tar.z", "tar with compress (.tar.Z)"),
    TZ(EnumSet.of(MULTI_FUNCTION), ".tz", "tar with compress (.tZ)"),
    TAR_BZ2(EnumSet.of(MULTI_FUNCTION), ".tar.bz2", "tar with bzip2"),
    TBZ2(EnumSet.of(MULTI_FUNCTION), ".tbz2", "tar with bzip2"),
    TAR_LZ(EnumSet.of(MULTI_FUNCTION), ".tar.lz", "tar with lzip"),
    TLZ(EnumSet.of(MULTI_FUNCTION), ".tlz", "tar with lzip"),
    TAR_XZ(EnumSet.of(MULTI_FUNCTION), ".tar.xz", "tar with xz"),
    TXZ(EnumSet.of(MULTI_FUNCTION), ".txz", "tar with xz"),
    TAR_ZST(EnumSet.of(MULTI_FUNCTION), ".tar.zst", "tar with Zstandard"),
    TZST(EnumSet.of(MULTI_FUNCTION), ".tzst", "tar with Zstandard"),
    TAR_SZ(EnumSet.of(MULTI_FUNCTION), ".tar.sz", "tar with Snappy"),
    TAR_SNAPPY(EnumSet.of(MULTI_FUNCTION), ".tar.snappy", "tar with Snappy"),
    TAR_LZMA(EnumSet.of(MULTI_FUNCTION), ".tar.lzma", "tar with lzma"),
    ZIP(EnumSet.of(MULTI_FUNCTION), ".zip", "ZIP"),
    ZZIP(EnumSet.of(MULTI_FUNCTION), ".zz", "Zzip"),
    /*
     * SOFTWARE_PACKAGING and MULTI_FUNCTION
     */
    DEB(EnumSet.of(SOFTWARE_PACKAGING, MULTI_FUNCTION), ".deb", "Debian package (deb)"),
    PKG(EnumSet.of(SOFTWARE_PACKAGING, MULTI_FUNCTION), ".pkg", "Macintosh Installer"),
    MPKG(EnumSet.of(SOFTWARE_PACKAGING, MULTI_FUNCTION), ".mpkg", "Macintosh Installer"),
    RPM(EnumSet.of(SOFTWARE_PACKAGING, MULTI_FUNCTION), ".rpm", "RPM Package Manager (RPM)"),
    MSI(EnumSet.of(SOFTWARE_PACKAGING, MULTI_FUNCTION), ".msi", "Windows Installer (also MSI)"),
    JAR(EnumSet.of(SOFTWARE_PACKAGING, MULTI_FUNCTION), ".jar", "Java Archive (JAR)"),
    WAR(EnumSet.of(SOFTWARE_PACKAGING, MULTI_FUNCTION), ".war", "Web Application archive (Java-based web app)"),
    IPA(EnumSet.of(SOFTWARE_PACKAGING, MULTI_FUNCTION), ".ipa", "iOS and iPadOS application package"),
    APK(EnumSet.of(SOFTWARE_PACKAGING, MULTI_FUNCTION), ".apk", "Android application package"),
    XAPK(EnumSet.of(SOFTWARE_PACKAGING, MULTI_FUNCTION), ".xapk", "Android application package"),
    APKS(EnumSet.of(SOFTWARE_PACKAGING, MULTI_FUNCTION), ".apks", "Android application package"),
    APKM(EnumSet.of(SOFTWARE_PACKAGING, MULTI_FUNCTION), ".apkm", "Android application package"),
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
     * 归档器或压缩器的官方名称或描述
     */
    public final String description;

    static final Map<String, ArchiveFormat> SA_MAP = new HashMap<>();
    /**
     * 混合压缩格式（一般是tar与其他压缩算法一起压缩）
     */
    public static final ArchiveFormat[] MIXED_FORMATS = {TAR_GZ, TGZ, TAZ, TAR_Z, TZ, TAR_BZ2, TBZ2, TAR_LZ, TLZ, TAR_XZ, TXZ, TAR_ZST, TZST, TAR_SZ, TAR_SNAPPY, TAR_LZMA};
    /**
     * 有多个扩展名的压缩格式（如：.tar.gz, .tar.Z 等）
     */
    public static final ArchiveFormat[] MULTI_EXTS_FORMATS = {TAR_GZ, TAR_Z, TAR_BZ2, TAR_LZ, TAR_XZ, TAR_ZST, TAR_SZ, TAR_SNAPPY, TAR_LZMA};
    /**
     * 相同的格式
     */
    static final Map<ArchiveFormat, ArchiveFormat> CONSISTENT_FORMAT1 = new HashMap<>();
    static final Map<ArchiveFormat, ArchiveFormat> CONSISTENT_FORMAT2 = new HashMap<>();

    static {
        for (ArchiveFormat value : values()) {
            String ext = value.extName;
            if (ext.equals(".z") || ext.equals(".Z")) {
                SA_MAP.put(value.extName, value);
            } else {
                SA_MAP.put(ext.toLowerCase(), value);
            }
        }

        CONSISTENT_FORMAT1.put(TAR_GZ, TGZ);
        CONSISTENT_FORMAT1.put(TAR_BZ2, TBZ2);
        CONSISTENT_FORMAT1.put(TAR_LZ, TLZ);
        CONSISTENT_FORMAT1.put(TAR_XZ, TXZ);
        CONSISTENT_FORMAT1.put(TAR_ZST, TZST);
        CONSISTENT_FORMAT1.put(TAR_Z, TZ);

        CONSISTENT_FORMAT2.put(TGZ, TAR_GZ);
        CONSISTENT_FORMAT2.put(TAZ, TAR_GZ);
        CONSISTENT_FORMAT2.put(TBZ2, TAR_BZ2);
        CONSISTENT_FORMAT2.put(TLZ, TAR_LZ);
        CONSISTENT_FORMAT2.put(TXZ, TAR_XZ);
        CONSISTENT_FORMAT2.put(TZST, TAR_ZST);
        CONSISTENT_FORMAT2.put(TZ, TAR_Z);
    }

    ArchiveFormat(EnumSet<ArchiveType> archiveTypes, String extName, String description) {
        this.archiveTypes = archiveTypes;
        this.extName = extName;
        this.description = description;
    }

    public static ArchiveFormat of(String extName) {
        return of(extName, false);
    }

    /**
     * 获取 ArchiveFormat 实例
     *
     * @param extName     文件扩展名
     * @param isUnionType 如果为true，则 .tgz 返回 {@link #TAR_GZ}，.tbz2 返回 {@link #TAR_BZ2} 依此类推……
     * @return ArchiveFormat
     */
    public static ArchiveFormat of(String extName, boolean isUnionType) {
        if (extName == null) return null;

        if (!extName.startsWith(".")) extName = "." + extName;

        String lowerExtName = extName.toLowerCase();
        if (isUnionType) {
            ArchiveFormat consistentFormat = CONSISTENT_FORMAT2.get(SA_MAP.get(lowerExtName));
            if (consistentFormat != null) return consistentFormat;
        }

        return SA_MAP.get(extName.equals(".z") || extName.equals(".Z") ? extName : lowerExtName);
    }

    public boolean isMixedFormat() {
        return isMixedFormat(this);
    }

    public boolean isMultiExtsFormat() {
        return isMultiExtsFormat(this);
    }

    /**
     * 是否是混合归档格式
     *
     * @param archiveFormat 归档格式
     * @return true，是混合类型，反之，不是混合类型
     */
    public static boolean isMixedFormat(ArchiveFormat archiveFormat) {
        return Arrays.asList(MIXED_FORMATS).contains(archiveFormat);
    }

    /**
     * 是否是具有多个扩展名的归档格式，如：{@code .tar.gz, .tar.bz2} 等等
     *
     * @param archiveFormat 归档格式
     * @return true，具有多个扩展名的归档格式
     */
    public static boolean isMultiExtsFormat(ArchiveFormat archiveFormat) {
        return Arrays.asList(MULTI_EXTS_FORMATS).contains(archiveFormat);
    }

}
