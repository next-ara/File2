package com.next.module.file2.tool;

import android.os.Build;

import com.next.module.file2.File2;
import com.next.module.file2.File2Creator;
import com.next.module.file2.RawFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * ClassName:RawFile加载器类
 *
 * @author Afton
 * @time 2024/6/8
 * @auditor
 */
public class RawFileListLoader extends FileListLoader {

    @Override
    public ArrayList<File2> getFileList(FileListFactory.LoadInfo loadInfo) throws FileLoadException {
        String path = loadInfo.path;
        //检查访问权限
        if (!this.checkAccessPermission(path)) {
            throw new FileLoadException(FileLoadException.ErrorCode.ERROR_CODE_NO_PERMISSION);
        }

        RawFile rawFile = (RawFile) File2Creator.fromFile(new File(path));
        //检查文件是否存在且是文件夹
        if (!rawFile.exists() || !rawFile.isDirectory()) {
            throw new FileLoadException(FileLoadException.ErrorCode.ERROR_CODE_FILE_NOT_EXIST);
        }

        File2[] file2s = rawFile.listFiles((dir, fileName) -> loadInfo.showHidden || !fileName.startsWith("."));
        return new ArrayList<>(Arrays.asList(file2s));
    }

    @Override
    public boolean isExecute(FileListFactory.LoadInfo loadInfo) {
        return true;
    }

    /**
     * 检查访问权限
     *
     * @param path 路径
     * @return 是否有访问权限
     */
    private boolean checkAccessPermission(String path) {
        if (FilePathTool.isAppDataPath(path)) {
            return true;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return !FilePathTool.isDataPath(path) && !FilePathTool.isObbPath(path) && !FilePathTool.isUnderDataPath(path) && !FilePathTool.isUnderObbPath(path);
        }

        return true;
    }
}