package com.next.module.file2.tool;

import android.content.UriPermission;
import android.net.Uri;
import android.os.Build;

import com.next.module.file2.File2;
import com.next.module.file2.File2Creator;
import com.next.module.file2.FileConfig;
import com.next.module.file2.TreeDocumentFile;

import java.util.ArrayList;
import java.util.Arrays;
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
    public ArrayList<File2> getFileList(FileListFactory.LoadInfo loadInfo) throws FileLoadException {
        String path = loadInfo.path;
        //检查访问权限
        if (!this.checkAccessPermission(path)) {
            throw new FileLoadException(FileLoadException.ErrorCode.ERROR_CODE_NO_PERMISSION);
        }

        TreeDocumentFile treeDocumentFile = (TreeDocumentFile) File2Creator.fromUri(pathToUri(path));
        File2[] file2s = treeDocumentFile.listFiles((dir, fileName) -> loadInfo.showHidden || !fileName.startsWith("."));
        return (ArrayList<File2>) Arrays.asList(file2s);
    }

    @Override
    public boolean isExecute(FileListFactory.LoadInfo loadInfo) {
        String path = loadInfo.path;
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
        String uriPath = this.pathToUri(path).getPath();
        for (UriPermission uriPermission : uriPermissions) {
            String itemPath = uriPermission.getUri().getPath();
            if (uriPath != null && itemPath != null && (uriPath + "/").contains(itemPath + "/")) {
                return true;
            }
        }

        return false;
    }

    /**
     * 路径转Uri
     *
     * @param path 路径
     * @return Uri
     */
    private Uri pathToUri(String path) {
        String halfPath = path.replace(FilePathTool.ROOT_PATH + "/", "");
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