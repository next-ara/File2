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

    //文件列表信息
    public static class FileListInfo {

        //父文件对象
        private File2 parentFile;

        //子文件列表
        private File2[] childFileList;

        public FileListInfo(File2 parentFile, File2[] childFileList) {
            this.parentFile = parentFile;
            this.childFileList = childFileList;
        }

        public File2 getParentFile() {
            return parentFile;
        }

        public File2[] getChildFileList() {
            return childFileList;
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
     * @param path 路径
     * @return 文件列表
     * @throws FileLoadException 文件加载异常
     */
    public FileListInfo getFileList(String path) throws FileLoadException {
        for (FileListLoader fileListLoader : this.fileListLoaders) {
            if (fileListLoader.isExecute(path)) {
                return fileListLoader.getFileList(path);
            }
        }

        return null;
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