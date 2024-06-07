package com.next.module.file2.tool;

import com.next.module.file2.File2;

import java.util.ArrayList;

/**
 * ClassName:文件排序工具类
 *
 * @author Afton
 * @time 2024/6/7
 * @auditor
 */
public class FileSortTool {

    //文件排序
    public static final class SortType {
        //按名称正序
        public static final int NAME_FORWARD = 0;
        //按名称倒序
        public static final int NAME_REVERSE = 1;
        //按时间正序
        public static final int TIME_FORWARD = 2;
        //按时间倒序
        public static final int TIME_REVERSE = 3;
    }

    /**
     * 文件排序
     *
     * @param file2s   文件列表
     * @param sortType 排序类型
     */
    public static void sort(ArrayList<File2> file2s, int sortType) {
        switch (sortType) {
            case SortType.TIME_FORWARD:
                timeForwardSort(file2s);
                break;
            case SortType.TIME_REVERSE:
                timeReverseSort(file2s);
                break;
            case SortType.NAME_FORWARD:
                nameForwardSort(file2s);
                break;
            case SortType.NAME_REVERSE:
                nameReverseSort(file2s);
                break;
        }
    }

    /**
     * 时间正序排序
     *
     * @param file2s 文件列表
     */
    private static void timeForwardSort(ArrayList<File2> file2s) {
        file2s.sort((o1, o2) -> {
            long diff = o1.lastModified() - o2.lastModified();
            if (diff > 0) return 1;
            else if (diff == 0) return 0;
            else return -1;
        });
    }

    /**
     * 时间倒序排序
     *
     * @param file2s 文件列表
     */
    private static void timeReverseSort(ArrayList<File2> file2s) {
        file2s.sort((o1, o2) -> {
            long diff = o2.lastModified() - o1.lastModified();
            if (diff > 0) return 1;
            else if (diff == 0) return 0;
            else return -1;
        });
    }

    /**
     * 名称正序排序
     *
     * @param file2s 文件列表
     */
    private static void nameForwardSort(ArrayList<File2> file2s) {
        file2s.sort((o1, o2) -> {
            if (o1.isDirectory() && !o2.isDirectory()) {
                return -1;
            } else if (!o1.isDirectory() && o2.isDirectory()) {
                return 1;
            } else {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    /**
     * 名称倒序排序
     *
     * @param file2s 文件列表
     */
    private static void nameReverseSort(ArrayList<File2> file2s) {
        file2s.sort((o1, o2) -> {
            if (o1.isDirectory() && !o2.isDirectory()) {
                return 1;
            } else if (!o1.isDirectory() && o2.isDirectory()) {
                return -1;
            } else {
                return o2.getName().compareTo(o1.getName());
            }
        });
    }
}