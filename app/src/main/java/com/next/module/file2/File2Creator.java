package com.next.module.file2;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.provider.DocumentsContract;

import androidx.annotation.Nullable;
import androidx.documentfile.provider.DocumentFile;

import java.io.File;

/**
 * ClassName:File2创建工具类
 *
 * @author Afton
 * @time 2024/6/8
 * @auditor
 */
public class File2Creator {

    /**
     * 根据File对象创建File2对象
     *
     * @param file File对象
     * @return File2对象
     */
    public static <T extends File2> T fromFile(@Nullable File file) {
        return file != null ? (T) new RawFile(null, file) : null;
    }

    /**
     * 根据Uri对象创建File2对象
     *
     * @param uri Uri对象
     * @return File2对象
     */
    public static <T extends File2> T fromUri(Uri uri) {
        if (uri == null) {
            return null;
        }

        Context context = FileConfig.getApplication();

        if (isFileUri(uri)) {
            return (T) fromFile(new File(uri.getPath()));
        } else if (DocumentFile.isDocumentUri(context, uri)) {
            if (DocumentsContract.isTreeUri(uri)) {
                return (T) fromTreeDocumentUri(uri);
            } else {
                return (T) fromSingleDocumentUri(uri);
            }
        } else if (isMediaUri(uri)) {
            return (T) new MediaFile(uri);
        }

        return null;
    }

    /**
     * 判断Uri是否为File的Uri
     *
     * @param uri Uri对象
     * @return 是否为File的Uri
     */
    private static boolean isFileUri(Uri uri) {
        return uri != null && ContentResolver.SCHEME_FILE.equals(uri.getScheme());
    }

    /**
     * 判断Uri是否为Media的Uri
     *
     * @param uri Uri对象
     * @return 是否为Media的Uri
     */
    private static boolean isMediaUri(Uri uri) {
        return uri != null && ContentResolver.SCHEME_CONTENT.equals(uri.getScheme());
    }

    /**
     * 根据SingleDocumentUri对象创建File2对象
     *
     * @param singleUri SingleDocumentUri对象
     * @return File2对象
     */
    private static File2 fromSingleDocumentUri(Uri singleUri) {
        return new SingleDocumentFile(null, singleUri);
    }

    /**
     * 根据TreeDocumentUri对象创建File2对象
     *
     * @param treeUri TreeDocumentUri对象
     * @return File2对象
     */
    private static File2 fromTreeDocumentUri(Uri treeUri) {
        String documentId = DocumentsContract.getDocumentId(treeUri);
        return new TreeDocumentFile(null,
                DocumentsContract.buildDocumentUriUsingTree(treeUri, documentId));
    }
}