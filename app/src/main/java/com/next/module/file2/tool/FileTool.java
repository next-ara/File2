package com.next.module.file2.tool;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import androidx.core.content.FileProvider;

import com.next.module.file2.File2;
import com.next.module.file2.FileConfig;
import com.next.module.file2.RawFile;

/**
 * ClassName:文件工具类
 *
 * @author Afton
 * @time 2024/6/7
 * @auditor
 */
public class FileTool {

    /**
     * 根据文件名获取文件类型
     *
     * @param name 文件名
     * @return 文件类型
     */
    public static String getTypeForName(String name) {
        if (TextUtils.isEmpty(name)) {
            return null;
        }

        final int lastDot = name.lastIndexOf('.');
        if (lastDot >= 0) {
            final String extension = name.substring(lastDot + 1).toLowerCase();
            final String mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
            if (!TextUtils.isEmpty(mime)) {
                return mime;
            }
        }

        return "application/octet-stream";
    }

    /**
     * 打开文件
     *
     * @param file 文件对象
     * @return 是否打开成功
     */
    public static boolean openFile(File2 file) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            String mimeType = file.getType();
            intent.setDataAndType(getOpenUri(file), mimeType);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            FileConfig.getApplication().startActivity(intent);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取打开文件的uri
     *
     * @param file2 文件对象
     * @return uri
     */
    private static Uri getOpenUri(File2 file2) {
        if (file2 instanceof RawFile rawFile) {
            return FileProvider.getUriForFile(FileConfig.getApplication(), FileConfig.getAuthority(), rawFile.getFile());
        } else {
            return file2.getUri();
        }
    }
}