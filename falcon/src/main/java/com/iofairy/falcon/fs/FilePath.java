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

import com.iofairy.falcon.os.OS;
import com.iofairy.top.G;
import com.iofairy.tuple.Tuple;
import com.iofairy.tuple.Tuple3;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * File Path
 * @since 0.0.1
 */
public class FilePath {
    public final static String ROOT          =  "/";            // 根路径
    public final static String SLASH         =  "/";            // 斜杠
    public final static String BACKSLASH     =  "\\";           // 反斜杠
    public final static String EMPTY         =  "";             // 空字符串
    public final static String DOT           =  ".";            // 当前目录
    public final static String PARENT_DIR    =  "..";           // 上级目录
    public final static String DOTSLASH      =  "./";           // UNIX当前目录
    public final static String DOTBACKSLASH  =  ".\\";          // Win当前目录
    public final static String TWOBACKSLASH  =  "\\\\";         // 双反斜杠（用于正则匹配）
    public final static String SLASHDOT      =  "/.";
    public final static String BACKSLASHDOT  =  "\\.";

    public final static Pattern SLASH_PATTERN = Pattern.compile("[/\\\\]");


    /**
     * Path for UNIX systems. it will use {@code '/'} as files separator. <br>
     * 用于UNIX下拼接路径。会使用 {@code '/'} 作为文件之间的分隔符。
     * 且 {@code '\'} 在UNIX系统路径中作为转义字符使用，所以不能转成 {@code '/'}。 <br>
     * <b>Examples:</b>
     * <pre>
     * FilePath.path("./a", "b", "./c/", "/d")  returns "./a/b/c/d"
     * FilePath.path("./", "./", "//", "/.")    returns "."
     * </pre>
     * @param firstPath firstPath
     * @param subPaths subPaths
     * @return a full path
     */
    public static String path(String firstPath, String... subPaths) {
        return concatPath(false, firstPath, subPaths);
    }

    /**
     * Path for Windows systems. it will use {@code '\'} as files separator,
     * and will transform all {@code '/'} to {@code '\'}. <br>
     * 用于Windows下拼接路径。且会使用 {@code '\'} 作为文件之间的分隔符，会将所有 {@code '/'} 转换成 {@code '\'}。 <br>
     * <b>Examples:</b>
     * <pre>
     * FilePath.pathWin("./a", "\\\\b", "\\\\./c", "/d")    returns ".\a\b\c\d"
     * FilePath.pathWin("./.", "./")                        returns "."
     * </pre>
     * @param firstPath firstPath
     * @param subPaths subPaths
     * @return a full path
     */
    public static String pathWin(String firstPath, String... subPaths) {
        String path = concatPath(true, firstPath, subPaths);
        return slashToBackslash(path);
    }

    /**
     * Path for Windows systems. it will use {@code '/'} as files separator,
     * and will transform all {@code '\'} to {@code '/'}. <br>
     * 用于Windows下拼接路径。且会使用 {@code '/'} 作为文件之间的分隔符，会将所有 {@code '\'} 转换成 {@code '/'}。 <br>
     * <b>Examples:</b>
     * <pre>
     * FilePath.pathSlash("./a", "\\\\b", "\\\\./c", "/d")  returns "./a/b/c/d"
     * FilePath.pathSlash("/", "./")                        returns "/"
     * </pre>
     * @param firstPath firstPath
     * @param subPaths subPaths
     * @return a full path
     */
    public static String pathSlash(String firstPath, String... subPaths) {
        return concatPath(true, firstPath, subPaths);
    }

    /**
     * Concat paths. <br>
     * 拼接路径。<br>
     *
     * @param isWin     is Windows path or not
     * @param firstPath firstPath
     * @param subPaths  subPaths
     * @return path. <b>Note:</b> it will return {@code null} when <b>{@code firstPath}</b> is {@code null} or there is {@code null} value in <b>{@code subPaths}</b>.
     */
    private static String concatPath(boolean isWin, String firstPath, String... subPaths) {
        if (null == firstPath) return null;
        boolean isFirstPathEmpty = EMPTY.equals(firstPath);
        StringBuffer pathBuffer = new StringBuffer(firstPath);
        if (!G.isEmpty(subPaths)) {
            // subPaths 中的元素不能有 null 值
            if (G.hasNull(subPaths)) return null;

            boolean isFirstNonEmptySubPath = true;  // 是否是第一个不为空的子目录
            for (String path : subPaths) {
                if (!G.isEmpty(path)) {
                    /*
                     * 如果 firstPath 为 ""，且 subPaths 中有不为 "" 的路径，那么第一个
                     * 不为 "" 的 subPath 前面不加 "/"，当作 firstPath 来使用
                     */
                    if (isFirstNonEmptySubPath && isFirstPathEmpty) {
                        isFirstNonEmptySubPath = false;
                        pathBuffer.append(path);
                        continue;
                    }
                    pathBuffer.append(SLASH).append(path);
                }
            }
        }

        String tempPath = pathBuffer.toString();
        tempPath = isWin ? backslashToSlash(tempPath) : tempPath;
        // 对'/'和'./' 去重
        tempPath = mergeRepeated(tempPath);
        // 去除尾部 / 或 /. （可能的特殊情况：./., /, /., ., ./）
        tempPath = rmTailSlash(tempPath, false);
        return rmTailSlashDot(tempPath, false);
    }

    /**
     * The system-dependent for concat paths. <br>
     * On UNIX systems, it will call {@link #path(String, String...)}; <br>
     * On Windows systems, it will call {@link #pathSlash(String, String...)}. <br><br>
     * 根据系统自动选择拼接paths的方法。<br>
     * 在Unix系统，会调用 {@link #path(String, String...)}；<br>
     * 在Windows系统，则会调用 {@link #pathSlash(String, String...)}。
     * @param firstPath firstPath
     * @param subPaths subPaths
     * @return a full path
     */
    public static String pathAuto(String firstPath, String... subPaths) {
        return OS.IS_WINDOWS ? pathSlash(firstPath, subPaths) : path(firstPath, subPaths);
    }

    /**
     * The system-dependent for concat paths. <br>
     * On UNIX systems, it will call {@link #path(String, String...)}; <br>
     * On Windows systems, it will call {@link #pathWin(String, String...)}. <br><br>
     * 根据系统自动选择拼接paths的方法。<br>
     * 在Unix系统，会调用 {@link #path(String, String...)}；<br>
     * 在Windows系统，则会调用 {@link #pathWin(String, String...)}。
     * @param firstPath firstPath
     * @param subPaths subPaths
     * @return a full path
     */
    public static String pathAutoWin(String firstPath, String... subPaths) {
        return OS.IS_WINDOWS ? pathWin(firstPath, subPaths) : path(firstPath, subPaths);
    }

    /**
     * PathInfo for UNIX systems. Using {@link SeparatorType#SLASH}.<br>
     *
     * @param filePath 文件路径
     * @return PathInfo
     * @since 0.0.6
     */
    public static PathInfo info(String filePath) {
        return getPathInfo(filePath, SeparatorType.SLASH);
    }

    /**
     * PathInfo for Windows systems. Using {@link SeparatorType#BACKSLASH}.<br>
     *
     * @param filePath 文件路径
     * @return PathInfo
     * @since 0.0.6
     */
    public static PathInfo infoWin(String filePath) {
        return getPathInfo(filePath, SeparatorType.BACKSLASH);
    }

    /**
     * PathInfo for Windows systems. Using {@link SeparatorType#WIN_SLASH}.<br>
     *
     * @param filePath 文件路径
     * @return PathInfo
     * @since 0.0.6
     */
    public static PathInfo infoSlash(String filePath) {
        return getPathInfo(filePath, SeparatorType.WIN_SLASH);
    }

    /**
     * The system-dependent for getting PathInfo.<br>
     *
     * @param filePath 文件路径
     * @return PathInfo
     * @since 0.0.6
     */
    public static PathInfo infoAuto(String filePath) {
        return OS.IS_WINDOWS ? infoSlash(filePath) : info(filePath);
    }

    /**
     * The system-dependent for getting PathInfo.<br>
     *
     * @param filePath 文件路径
     * @return PathInfo
     * @since 0.0.6
     */
    public static PathInfo infoAutoWin(String filePath) {
        return OS.IS_WINDOWS ? infoWin(filePath) : info(filePath);
    }

    /**
     * Get PathInfo
     *
     * @param filePath      filePath
     * @param separatorType separatorType
     * @return PathInfo
     * @since 0.0.6
     */
    private static PathInfo getPathInfo(String filePath, SeparatorType separatorType) {
        if (G.isEmpty(filePath)) return null;

        String path;
        String rootStr = SLASH;
        String separator = SLASH;
        switch (separatorType) {
            case SLASH:
                path = path(filePath);
                break;
            case WIN_SLASH:
                path = pathSlash(filePath);
                break;
            default:
                path = pathWin(filePath);
                rootStr = BACKSLASH;
                separator = BACKSLASH;
        }

        if (path.equals(rootStr))
            return new PathInfo(true, rootStr, null, rootStr, new String[]{rootStr}, EMPTY, EMPTY, separator, separatorType);

        String[] paths = path.split(separator.equals(SLASH) ? SLASH : TWOBACKSLASH);

        if (path.startsWith(rootStr)) {
            paths[0] = rootStr;     // paths[0] 原始值为 空字符串("")
            String parentPath = rootStr + String.join(separator, Arrays.copyOfRange(paths, 1, paths.length - 1));
            Tuple3<String, String, String> infoTuple = getPathInfo(paths);
            return new PathInfo(true, parentPath, infoTuple._1, path, paths, infoTuple._2, infoTuple._3, separator, separatorType);
        } else {
            if (paths.length == 1) {
                Tuple3<String, String, String> infoTuple = getPathInfo(paths);
                return new PathInfo(false, null, infoTuple._1, paths[0], paths, infoTuple._2, infoTuple._3, separator, separatorType);
            } else {
                String parentPath = String.join(separator, Arrays.copyOfRange(paths, 0, paths.length - 1));
                Tuple3<String, String, String> infoTuple = getPathInfo(paths);
                return new PathInfo(false, parentPath, infoTuple._1, path, paths, infoTuple._2, infoTuple._3, separator, separatorType);
            }
        }
    }

    /**
     * Get fileName, ext, extNoDot
     *
     * @param paths path array
     * @return fileName, ext, extNoDot
     */
    private static Tuple3<String, String, String> getPathInfo(String[] paths) {
        String fileName = paths[paths.length - 1];
        String ext = ext(fileName);
        String extNoDot = ext == null ? null : ext.replace(DOT, EMPTY);
        return Tuple.of(fileName, ext, extNoDot);
    }

    /**
     * Merge multi {@code "/"} and {@code "./"} to one first, and then add {@code "./"} to head. <br>
     * 先去除重复的{@code "/"}和{@code "./"}，再添加头部的 {@code "./"}
     * @param originPath originPath
     * @return path
     */
    public static String addHeadDotSlash(String originPath) {
        return addHeadDotSlash(originPath, true);
    }

    /**
     * Add {@code "./"} to head. <br>
     * 添加头部的 {@code "./"}
     * @param originPath originPath
     * @param isNeedMerge is need merge {@code "/"} and {@code "./"} first
     * @return path
     */
    public static String addHeadDotSlash(String originPath, boolean isNeedMerge) {
        if (G.isEmpty(originPath)) return originPath;
        String path = isNeedMerge ? mergeRepeated(originPath) : originPath;
        if (path.startsWith(DOTSLASH)) return path;
        return path.startsWith(SLASH) ? DOT + path : DOTSLASH + path;
    }

    /**
     * Merge multi {@code "/"} and {@code "./"} to one first, and then remove {@code "./"} at the head. <br>
     * 先去除重复的{@code "/"}和{@code "./"}，再删除头部的 {@code "./"}
     * @param originPath originPath
     * @return path
     */
    public static String rmHeadDotSlash(String originPath) {
        return rmHeadDotSlash(originPath, true);
    }

    /**
     * Remove {@code "./"} at the head. <br>
     * 删除头部的 {@code "./"}
     * @param originPath originPath
     * @param isNeedMerge is need merge {@code "/"} and {@code "./"} first
     * @return path
     */
    public static String rmHeadDotSlash(String originPath, boolean isNeedMerge) {
        if (G.isEmpty(originPath)) return originPath;
        String path = isNeedMerge ? mergeRepeated(originPath) : originPath;
        if (path.equals(DOTSLASH)) return path;
        return path.startsWith(DOTSLASH) ? path.substring(2) : path;
    }

    /**
     * Merge multi {@code "/"} and {@code "./"} to one first, and then remove tail {@code "/."}. <br>
     * 先去除重复的{@code "/"}和{@code "./"}，再删除尾部的 {@code "/."}
     * @param originPath originPath
     * @return path
     */
    public static String rmTailSlashDot(String originPath) {
        return rmTailSlashDot(originPath, true);
    }

    /**
     * Remove tail {@code "/."}. <br>
     * 删除尾部的 {@code "/."}
     * @param originPath originPath
     * @param isNeedMerge is need merge {@code "/"} and {@code "./"} first
     * @return path
     */
    public static String rmTailSlashDot(String originPath, boolean isNeedMerge) {
        if (G.isEmpty(originPath)) return originPath;
        String path = isNeedMerge ? mergeRepeated(originPath) : originPath;
        if (path.equals(SLASHDOT)) return SLASH;
        return path.endsWith(SLASHDOT) ? path.substring(0, path.length() - 2) : path;
    }

    /**
     * Merge multi {@code "/"} and {@code "./"} to one first, and then remove tail {@code "/"}. <br>
     * 先去除重复的{@code "/"}和{@code "./"}，再去除路径尾部 {@code '/'}
     * @param originPath originPath
     * @return path
     */
    public static String rmTailSlash(String originPath) {
        return rmTailSlash(originPath, true);
    }

    /**
     * Remove tail {@code "/"}. <br>
     * 去除路径尾部 {@code '/'}
     * @param originPath originPath
     * @param isNeedMerge is need merge {@code "/"} and {@code "./"} first
     * @return path
     */
    public static String rmTailSlash(String originPath, boolean isNeedMerge) {
        if (G.isEmpty(originPath)) return originPath;
        String path = isNeedMerge ? mergeRepeated(originPath) : originPath;
        // 路径只有一个 '/'，不做任何处理
        if (path.equals(SLASH)) return path;
        return path.endsWith(SLASH) ? path.substring(0, path.length() - 1) : path;
    }

    /**
     * Merge multi {@code "/"} and {@code "./"} to one first, and then remove {@code "/"} at the head. <br>
     * 先去除重复的{@code "/"}和{@code "./"}，再去除路径头部 {@code '/'}
     * @param originPath originPath
     * @return path
     */
    public static String rmHeadSlash(String originPath) {
        return rmHeadSlash(originPath, true);
    }

    /**
     * Remove {@code "/"} at the head. <br>
     * 去除路径头部 {@code '/'}
     * @param originPath originPath
     * @param isNeedMerge is need merge {@code "/"} and {@code "./"} first
     * @return path
     */
    public static String rmHeadSlash(String originPath, boolean isNeedMerge) {
        if (G.isEmpty(originPath)) return originPath;
        String path = isNeedMerge ? mergeRepeated(originPath) : originPath;
        // 路径只有一个 '/'，不做任何处理
        if (path.equals(SLASH)) return path;
        return path.startsWith(SLASH) ? path.substring(1) : path;
    }

    /**
     * Merge multi {@code "/"} and {@code "./"} to one first, and then add {@code "/"} to head. <br>
     * 先去除重复的{@code "/"}和{@code "./"}，再添加头部 {@code '/'}
     * @param originPath originPath
     * @return path
     */
    public static String addHeadSlash(String originPath) {
        return addHeadSlash(originPath, true);
    }

    /**
     * Add {@code "/"} to head. <br>
     * 添加头部 {@code '/'}
     * @param originPath originPath
     * @param isNeedMerge is need merge {@code "/"} and {@code "./"} first
     * @return path
     */
    public static String addHeadSlash(String originPath, boolean isNeedMerge) {
        if (G.isEmpty(originPath)) return originPath;
        String path = isNeedMerge ? mergeRepeated(originPath) : originPath;
        // 路径只有一个 '/'，不做任何处理
        if (path.equals(SLASH)) return path;
        return path.startsWith(SLASH) ? path : SLASH + path;
    }

    /**
     * Merge multi {@code "/"} and {@code "./"} to one first, and then add tail {@code "/"}. <br>
     * 先去除重复的{@code "/"}和{@code "./"}，再添加尾部 {@code '/'}
     * @param originPath originPath
     * @return path
     */
    public static String addTailSlash(String originPath) {
        return addTailSlash(originPath, true);
    }

    /**
     * Add tail {@code "/"}. <br>
     * 添加尾部 {@code '/'}
     * @param originPath originPath
     * @param isNeedMerge is need merge {@code "/"} and {@code "./"} first
     * @return path
     */
    public static String addTailSlash(String originPath, boolean isNeedMerge) {
        if (G.isEmpty(originPath)) return originPath;
        String path = isNeedMerge ? mergeRepeated(originPath) : originPath;
        // 路径只有一个 '/'，不做任何处理
        if (path.equals(SLASH)) return path;
        return path.endsWith(SLASH) ? path : path + SLASH;
    }

    /**
     * For Windows systems, transform {@code '\'} to {@code '/'}, and <b>merge multi {@code '/'} to one</b> in file path. <br>
     * 针对Windows系统，将路径中的 {@code '\'} 转成 {@code '/'}，并<b>合并多个重复的{@code '/'}</b>。
     * @param originPath originPath
     * @return parsed path
     */
    public static String backslashToSlash(String originPath) {
        if (G.isEmpty(originPath)) return originPath;
        return mergeSlash(originPath.replaceAll("\\\\+", "/"), true);
    }

    /**
     * For Windows systems, transform {@code '/'} to {@code '\'}, and <b>merge multi {@code '\'} to one</b> in file path. <br>
     * 针对Windows系统，将路径中的 {@code '/'} 转成 {@code '\'}，并<b>合并多个重复的{@code '\'}</b>。
     * @param originPath originPath
     * @return parsed path
     */
    public static String slashToBackslash(String originPath) {
        if (G.isEmpty(originPath)) return originPath;
        return mergeSlash(originPath.replaceAll("/+", "\\\\"), false);
    }

    /**
     * Merge multi {@code '/'} to one first, and then merge multi {@code "./"} to one. <br>
     * 先去除重复的 {@code '/'}，再去除重复的 {@code "./"}。
     * @param originPath originPath
     * @return merged path
     */
    public static String mergeRepeated(String originPath) {
        return mergeDotSlash(mergeSlash(originPath, true));
    }

    /**
     * Merge multi {@code '/'} or {@code '\'} to one. <br>
     * 合并多个 {@code '/'} 或 {@code '\'} 成一个。
     * @param originPath originPath
     * @param whetherMergeSlash if {@code true}, merge {@code '/'}; otherwise, merge {@code '\'}
     * @return merged path
     */
    public static String mergeSlash(String originPath, boolean whetherMergeSlash) {
        if (G.isEmpty(originPath)) return originPath;
        String mergeChar = whetherMergeSlash ? "/" : "\\\\";
        return originPath.replaceAll(mergeChar + "+", mergeChar);
    }

    /**
     * Merge multi {@code './'} to one. <br>
     * 合并多个 {@code './'} 成一个。
     * @param originPath originPath
     * @return merged path
     */
    public static String mergeDotSlash(String originPath) {
        if (G.isEmpty(originPath)) return originPath;
        return originPath.replaceAll("/(\\./)+", "/");
    }

    /**
     * Merge multi {@code '.\'} to one. <br>
     * 合并多个 {@code '.\'} 成一个。
     * @param originPath originPath
     * @return merged path
     */
    public static String mergeDotBackslash(String originPath) {
        if (G.isEmpty(originPath)) return originPath;
        return originPath.replaceAll("\\\\(\\.\\\\)+", "\\\\");
    }

    /**
     * Get file extension from path or filename. <br>
     * 从路径或文件名中获取文件扩展名。 <br>
     * 注：Windows不能创建以 . 为后缀的文件或文件夹（会自动去除），Linux则可以。
     *
     * @param path path
     * @return file extension, <b>eg</b>: {@code .txt}, {@code .csv}
     */
    public static String ext(String path) {
        if (path == null) return null;
        if (G.isBlank(path) || DOT.equals(path) || PARENT_DIR.equals(path)) return EMPTY;
        int dotIndex = path.lastIndexOf(DOT);
        if (dotIndex == -1) {
            return EMPTY;
        } else {
            String subStr = path.substring(dotIndex);
            return SLASH_PATTERN.matcher(subStr).find() ? EMPTY : subStr;
        }
    }

    /**
     * Get file extension without dot from path or filename. <br>
     * 从路径或文件名中获取文件扩展名，不带点。 <br>
     * 注：Windows不能创建以 . 为后缀的文件或文件夹（会自动去除），Linux则可以。
     *
     * @param path path
     * @return file extension without dot, <b>eg</b>: {@code txt}, {@code csv}
     */
    public static String extNoDot(String path) {
        String ext = ext(path);
        if (ext == null) return null;
        return ext.replace(DOT, EMPTY);
    }

    /**
     * Verify params. <br>
     * 校验参数的有效性
     * @param firstPath firstPath
     * @param subPaths subPaths
     */
    private static void verifyParams(String firstPath, String... subPaths) {
        if (G.isEmpty(firstPath) || G.hasEmpty(subPaths))
            throw new NullPointerException("`firstPath` or `subPaths` must be not empty! 参数`firstPath` or `subPaths`必须是非空！");
    }

}
