package com.next.module.file2.tool;

import android.content.UriPermission;

import com.next.module.file2.File2;
import com.next.module.file2.File2Creator;
import com.next.module.file2.FileConfig;
import com.next.module.file2.TreeDocumentFile;

import java.util.List;

/**
 * ClassName:DocumentFile加载器类
 *
 * @author Afton
 * @time 2024/6/8
 * @auditor
 */
public class DocumentFileListLoader extends FileListLoader {

    @Override
    public FileListFactory.FileListInfo getFileList(String path) throws FileLoadException {
        //检查访问权限
        if (!this.checkAccessPermission(path)) {
            throw new FileLoadException(FileLoadException.ErrorCode.ERROR_CODE_NO_PERMISSION);
        }

        TreeDocumentFile treeDocumentFile = File2Creator.fromUri(FilePathTool.dataPathToUri(path));
        //检查文件是否存在且是文件夹
        if (!treeDocumentFile.exists() || !treeDocumentFile.isDirectory()) {
            throw new FileLoadException(FileLoadException.ErrorCode.ERROR_CODE_FILE_NOT_EXIST);
        }

        File2[] file2s = treeDocumentFile.listFiles();
        return new FileListFactory.FileListInfo(treeDocumentFile, file2s);
    }

    @Override
    public boolean isExecute(String path) {
        if (FilePathTool.isAppDataPath(path)) {
            return false;
        }

        return FilePathTool.isDataPath(path) || FilePathTool.isObbPath(path) || FilePathTool.isUnderDataPath(path) || FilePathTool.isUnderObbPath(path);
    }

    /**
     * 检查访问权限
     *
     * @param path 路径
     * @return 是否有访问权限
     */
    private boolean checkAccessPermission(String path) {
        List<UriPermission> uriPermissions = FileConfig.getApplication().getContentResolver().getPersistedUriPermissions();
        String uriPath = FilePathTool.dataPathToUri(path).getPath();
        for (UriPermission uriPermission : uriPermissions) {
            String itemPath = uriPermission.getUri().getPath();
            if (uriPath != null && itemPath != null && (uriPath + "/").contains(itemPath + "/")) {
                return true;
            }
        }

        return false;
    }
}