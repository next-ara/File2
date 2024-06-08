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
    public ArrayList<File2> getFileList(String path) throws FileLoadException {
        for (FileListLoader fileListLoader : this.fileListLoaders) {
            if (fileListLoader.isExecute(path)) {
                return fileListLoader.getFileList(path);
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