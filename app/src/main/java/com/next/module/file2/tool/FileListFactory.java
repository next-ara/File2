package com.next.module.file2.tool;

import com.next.module.file2.File2;

import java.util.ArrayList;

/**
 * ClassName:文件列表工厂类
 *
 * @author Afton
 * @time 2024/6/7
 * @auditor
 */
public class FileListFactory {

    /**
     * 加载信息类
     */
    public static class LoadInfo {

        //文件夹路径
        public String path;

        //排序类型
        public int sortType;

        //是否显示隐藏文件
        public boolean showHidden;

        /**
         * 设置文件夹路径
         *
         * @param path 文件夹路径
         * @return 加载信息对象
         */
        public LoadInfo setPath(String path) {
            this.path = path;
            return this;
        }

        /**
         * 设置排序类型
         *
         * @param sortType 排序类型
         * @return 加载信息对象
         */
        public LoadInfo setSortType(int sortType) {
            this.sortType = sortType;
            return this;
        }

        /**
         * 设置是否显示隐藏文件
         *
         * @param showHidden 是否显示隐藏文件
         * @return 加载信息对象
         */
        public LoadInfo setShowHidden(boolean showHidden) {
            this.showHidden = showHidden;
            return this;
        }
    }

    //文件列表加载器列表
    private ArrayList<FileListLoader> fileListLoaders;

    public FileListFactory() {
        //初始化文件列表加载器列表
        this.initFileListLoaders();
    }

    /**
     * 获取文件列表
     *
     * @param loadInfo 加载信息对象
     * @return 文件列表
     * @throws FileLoadException 文件加载异常
     */
    public ArrayList<File2> getFileList(LoadInfo loadInfo) throws FileLoadException {
        if (loadInfo == null) {
            throw new FileLoadException(FileLoadException.ErrorCode.ERROR_CODE_INCOMPLETE_INFO);
        }

        for (FileListLoader fileListLoader : this.fileListLoaders) {
            if (fileListLoader.isExecute(loadInfo)) {
                ArrayList<File2> fileList = fileListLoader.getFileList(loadInfo);
                FileSortTool.sort(fileList, loadInfo.sortType);
                return fileList;
            }
        }

        return new ArrayList<>();
    }

    /**
     * 初始化文件列表加载器列表
     */
    private void initFileListLoaders() {
        this.fileListLoaders = new ArrayList<>();
        //DocumentFile加载器注册
        this.fileListLoaders.add(new DocumentFileListLoader());
        //RawFile加载器注册
        this.fileListLoaders.add(new RawFileListLoader());
    }
}