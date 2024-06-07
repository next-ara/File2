package com.next.module.file2;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * ClassName:MediaContract
 *
 * @author Afton
 * @time 2024/6/7
 * @auditor
 */
public class MediaContract {

    /**
     * 获取文件名
     *
     * @param context 上下文
     * @param self    文件
     * @return 文件名
     */
    public static String getName(Context context, Uri self) {
        return Contracts.queryForString(context, self, MediaStore.MediaColumns.DISPLAY_NAME, null);
    }

    /**
     * 获取文件类型
     *
     * @param context 上下文
     * @param self    文件
     * @return 文件类型
     */
    public static String getType(Context context, Uri self) {
        return Contracts.queryForString(context, self, MediaStore.MediaColumns.MIME_TYPE, null);
    }

    /**
     * 获取文件路径
     *
     * @param context 上下文
     * @param self    文件
     * @return 文件路径
     */
    public static String getPath(Context context, Uri self) {
        return Contracts.queryForString(context, self, MediaStore.MediaColumns.DATA, null);
    }

    /**
     * 获取文件最后修改时间
     *
     * @param context 上下文
     * @param self    文件
     * @return 文件最后修改时间
     */
    public static long lastModified(Context context, Uri self) {
        return Contracts.queryForLong(context, self, MediaStore.MediaColumns.DATE_MODIFIED, -1L);
    }

    /**
     * 获取文件大小
     *
     * @param context 上下文
     * @param self    文件
     * @return 文件大小
     */
    public static long length(Context context, Uri self) {
        return Contracts.queryForLong(context, self, MediaStore.MediaColumns.SIZE, -1L);
    }
}