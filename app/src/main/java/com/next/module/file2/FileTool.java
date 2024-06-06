package com.next.module.file2;

import android.text.TextUtils;
import android.webkit.MimeTypeMap;

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
}