package com.next.module.file2.tool;

import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import com.next.module.file2.FileConfig;

/**
 * ClassName:文件路径工具类
 *
 * @author Afton
 * @time 2024/6/8
 * @auditor
 */
public class FilePathTool {

    //根目录
    public static final String ROOT_PATH = Environment.getExternalStorageDirectory().getPath();

    /**
     * 是否是应用Data目录
     *
     * @param path 路径
     * @return 是否是应用Data目录
     */
    public static boolean isAppDataPath(String path) {
        return (path + "/").contains(ROOT_PATH + "/Android/data/" + FileConfig.getApplication().getPackageName() + "/");
    }

    /**
     * 是否是Data目录
     *
     * @param path 路径
     * @return 是否是Data目录
     */
    public static boolean isDataPath(String path) {
        return (ROOT_PATH + "/Android/data").equals(path);
    }

    /**
     * 是否是Obb目录
     *
     * @param path 路径
     * @return 是否是Obb目录
     */
    public static boolean isObbPath(String path) {
        return (ROOT_PATH + "/Android/obb").equals(path);
    }

    /**
     * 是否是Data目录下的子目录
     *
     * @param path 路径
     * @return 是否是Data目录下的子目录
     */
    public static boolean isUnderDataPath(String path) {
        return path.contains(ROOT_PATH + "/Android/data/");
    }

    /**
     * 是否是Obb目录下的子目录
     *
     * @param path 路径
     * @return 是否是Obb目录下的子目录
     */
    public static boolean isUnderObbPath(String path) {
        return path.contains(ROOT_PATH + "/Android/obb/");
    }

    /**
     * 是否是根目录
     *
     * @param path 路径
     * @return 是否是根目录
     */
    public static boolean isRootPath(String path) {
        return ROOT_PATH.equals(path);
    }

    /**
     * 获取父路径
     *
     * @param path 路径
     * @return 父路径
     */
    public static String getParentPath(String path) {
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }

        return path.substring(0, path.lastIndexOf("/"));
    }

    /**
     * data路径转Uri
     *
     * @param path 路径
     * @return Uri
     */
    public static Uri dataPathToUri(String path) {
        String halfPath = path.replace(ROOT_PATH + "/", "");
        String[] segments = halfPath.split("/");
        Uri.Builder uriBuilder = new Uri.Builder()
                .scheme("content")
                .authority("com.android.externalstorage.documents")
                .appendPath("tree");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            uriBuilder.appendPath("primary:A\u200Bndroid/" + segments[1]);
        } else {
            uriBuilder.appendPath("primary:Android/" + segments[1]);
        }
        uriBuilder.appendPath("document");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            uriBuilder.appendPath("primary:A\u200Bndroid/" + halfPath.replace("Android/", ""));
        } else {
            uriBuilder.appendPath("primary:" + halfPath);
        }

        return uriBuilder.build();
    }
}