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

/**
 * File Path
 * @since 0.0.1
 */
public class FilePath {
    public final static String ROOT = "/";
    public final static String SLASH = "/";             // 斜杠
    public final static String BACKSLASH = "\\";        // 反斜杠
    public final static String DOT = ".";               // 当前目录
    public final static String DOTSLASH = "./";         // UNIX当前目录
    public final static String DOTBACKSLASH = ".\\";    // Win当前目录
    public final static String SLASHDOT = "/.";


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
        if (G.isEmpty(firstPath) || G.hasEmpty(subPaths)) return null;
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
        if (G.isEmpty(firstPath) || G.hasEmpty(subPaths)) return null;
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
        if (G.isEmpty(firstPath) || G.hasEmpty(subPaths)) return null;
        return concatPath(true, firstPath, subPaths);
    }

    /**
     * Concat subPaths.
     * @param isWin is Windows path
     * @param firstPath firstPath
     * @param subPaths subPaths
     * @return path
     */
    private static String concatPath(boolean isWin, String firstPath, String... subPaths) {
        StringBuffer pathBuffer = new StringBuffer(firstPath);
        for (String path : subPaths) {
            pathBuffer.append(SLASH).append(path);
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
     * The system-dependent subPaths. <br>
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
     * The system-dependent subPaths. <br>
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
