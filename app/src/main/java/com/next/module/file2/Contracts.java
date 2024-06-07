package com.next.module.file2;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.Nullable;

/**
 * ClassName:Contracts
 *
 * @author Afton
 * @time 2024/6/7
 * @auditor
 */
public class Contracts {

    /**
     * 查询
     *
     * @param context      上下文
     * @param self         当前目录
     * @param column       列
     * @param defaultValue 默认值
     * @return 查询结果
     */
    public static String queryForString(Context context, Uri self, String column, String defaultValue) {
        final ContentResolver resolver = context.getContentResolver();

        Cursor c = null;
        try {
            c = resolver.query(self, new String[]{column}, null, null, null);
            if (c != null && c.moveToFirst() && !c.isNull(0)) {
                return c.getString(0);
            } else {
                return defaultValue;
            }
        } catch (Exception e) {
            return defaultValue;
        } finally {
            closeQuietly(c);
        }
    }

    /**
     * 查询
     *
     * @param context      上下文
     * @param self         当前目录
     * @param column       列
     * @param defaultValue 默认值
     * @return 查询结果
     */
    public static int queryForInt(Context context, Uri self, String column, int defaultValue) {
        return (int) queryForLong(context, self, column, defaultValue);
    }

    /**
     * 查询
     *
     * @param context      上下文
     * @param self         当前目录
     * @param column       列
     * @param defaultValue 默认值
     * @return 查询结果
     */
    public static long queryForLong(Context context, Uri self, String column, long defaultValue) {
        final ContentResolver resolver = context.getContentResolver();

        Cursor c = null;
        try {
            c = resolver.query(self, new String[]{column}, null, null, null);
            if (c != null && c.moveToFirst() && !c.isNull(0)) {
                return c.getLong(0);
            } else {
                return defaultValue;
            }
        } catch (Exception e) {
            return defaultValue;
        } finally {
            closeQuietly(c);
        }
    }

    /**
     * 关闭
     *
     * @param closeable 自动关闭资源
     */
    public static void closeQuietly(@Nullable AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException var2) {
                throw var2;
            } catch (Exception var3) {
            }
        }
    }
}