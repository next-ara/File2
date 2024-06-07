package com.next.module.file2;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.File;

/**
 * ClassName:DocumentsContractApi19
 *
 * @author Afton
 * @time 2024/6/7
 * @auditor
 */
@RequiresApi(Build.VERSION_CODES.KITKAT)
public class DocumentsContractApi19 {

    //虚拟目录标记
    private static final int FLAG_VIRTUAL_DOCUMENT = 512;

    private static final String AUTHORITY_DOCUMENT_EXTERNAL_STORAGE = "com.android.externalstorage.documents";
    private static final String AUTHORITY_DOCUMENT_DOWNLOAD = "com.android.providers.downloads.documents";
    private static final String AUTHORITY_DOCUMENT_MEDIA = "com.android.providers.media.documents";

    /**
     * 判断是否是虚拟目录
     *
     * @param context 上下文
     * @param self    当前目录
     * @return 是否是虚拟目录
     */
    public static boolean isVirtual(Context context, Uri self) {
        if (!DocumentsContract.isDocumentUri(context, self)) {
            return false;
        } else {
            return (getFlags(context, self) & FLAG_VIRTUAL_DOCUMENT) != 0L;
        }
    }

    /**
     * 获取文件名
     *
     * @param context 上下文
     * @param self    当前目录
     * @return 文件名
     */
    @Nullable
    public static String getName(Context context, Uri self) {
        return queryForString(context, self, "_display_name", (String) null);
    }

    /**
     * 获取文件原始类型
     *
     * @param context 上下文
     * @param self    当前目录
     * @return 文件原始类型
     */
    @Nullable
    private static String getRawType(Context context, Uri self) {
        return queryForString(context, self, "mime_type", (String) null);
    }

    /**
     * 获取文件类型
     *
     * @param context 上下文
     * @param self    当前目录
     * @return 文件类型
     */
    @Nullable
    public static String getType(Context context, Uri self) {
        String rawType = getRawType(context, self);
        return DocumentsContract.Document.MIME_TYPE_DIR.equals(rawType) ? null : rawType;
    }

    /**
     * 获取文件标记
     *
     * @param context 上下文
     * @param self    当前目录
     * @return 文件标记
     */
    public static long getFlags(Context context, Uri self) {
        return queryForLong(context, self, "flags", 0L);
    }

    /**
     * 是否是目录
     *
     * @param context 上下文
     * @param self    当前目录
     * @return 是否是目录
     */
    public static boolean isDirectory(Context context, Uri self) {
        return DocumentsContract.Document.MIME_TYPE_DIR.equals(getRawType(context, self));
    }

    /**
     * 是否是文件
     *
     * @param context 上下文
     * @param self    当前目录
     * @return 是否是文件
     */
    public static boolean isFile(Context context, Uri self) {
        String type = getRawType(context, self);
        return !DocumentsContract.Document.MIME_TYPE_DIR.equals(type) && !TextUtils.isEmpty(type);
    }

    /**
     * 获取文件最后修改时间
     *
     * @param context 上下文
     * @param self    当前目录
     * @return 文件最后修改时间
     */
    public static long lastModified(Context context, Uri self) {
        return queryForLong(context, self, "last_modified", 0L);
    }

    /**
     * 获取文件大小
     *
     * @param context 上下文
     * @param self    当前目录
     * @return 文件大小
     */
    public static long length(Context context, Uri self) {
        return queryForLong(context, self, "_size", 0L);
    }

    /**
     * 是否可读
     *
     * @param context 上下文
     * @param self    当前目录
     * @return 是否可读
     */
    public static boolean canRead(Context context, Uri self) {
        if (context.checkCallingOrSelfUriPermission(self, Intent.FLAG_GRANT_READ_URI_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return !TextUtils.isEmpty(getRawType(context, self));
        }
    }

    /**
     * 是否可写
     *
     * @param context 上下文
     * @param self    当前目录
     * @return 是否可写
     */
    public static boolean canWrite(Context context, Uri self) {
        if (context.checkCallingOrSelfUriPermission(self, Intent.FLAG_GRANT_WRITE_URI_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            String type = getRawType(context, self);
            int flags = queryForInt(context, self, "flags", 0);
            if (TextUtils.isEmpty(type)) {
                return false;
            } else if ((flags & 4) != 0) {
                return true;
            } else if (DocumentsContract.Document.MIME_TYPE_DIR.equals(type) && (flags & 8) != 0) {
                return true;
            } else {
                return !TextUtils.isEmpty(type) && (flags & 2) != 0;
            }
        }
    }

    /**
     * 是否存在
     *
     * @param context 上下文
     * @param self    当前目录
     * @return 是否存在
     */
    public static boolean exists(Context context, Uri self) {
        ContentResolver resolver = context.getContentResolver();
        Cursor c = null;

        boolean var5;
        try {
            c = resolver.query(self, new String[]{"document_id"}, (String) null, (String[]) null, (String) null);
            boolean var4 = c.getCount() > 0;
            return var4;
        } catch (Exception var9) {
            Log.w("DocumentFile", "Failed query: " + var9);
            var5 = false;
        } finally {
            closeQuietly(c);
        }

        return var5;
    }

    /**
     * 获取文件路径
     *
     * @param context 上下文
     * @param self    当前目录
     * @return 文件路径
     */
    public static String getPath(Context context, Uri self) {
        if (self == null) {
            return null;
        }

        try {
            final String authority = self.getAuthority();
            if (AUTHORITY_DOCUMENT_EXTERNAL_STORAGE.equals(authority)) {
                final String docId = DocumentsContract.getDocumentId(self);
                final String[] split = docId.split(":");
                final String type = split[0];
                final String path = split[1];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + path;
                } else {
                    File[] cacheDirs = context.getExternalCacheDirs();
                    String storageDir = null;
                    for (File cacheDir : cacheDirs) {
                        final String cachePath = cacheDir.getPath();
                        int index = cachePath.indexOf(type);
                        if (index >= 0) {
                            storageDir = cachePath.substring(0, index + type.length());
                        }
                    }

                    if (storageDir != null) {
                        return storageDir + "/" + path;
                    } else {
                        return null;
                    }
                }
            } else if (AUTHORITY_DOCUMENT_DOWNLOAD.equals(authority)) {
                final String id = DocumentsContract.getDocumentId(self);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return queryForString(context, contentUri, MediaStore.MediaColumns.DATA, null);
            } else if (AUTHORITY_DOCUMENT_MEDIA.equals(authority)) {
                final String docId = DocumentsContract.getDocumentId(self);
                final String[] split = docId.split(":");
                final String type = split[0];
                final String id = split[1];

                final Uri baseUri;
                if ("image".equals(type)) {
                    baseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    baseUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    baseUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                } else {
                    return null;
                }

                final Uri contentUri = ContentUris.withAppendedId(baseUri, Long.valueOf(id));
                return queryForString(context, contentUri, MediaStore.MediaColumns.DATA, null);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
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
    @Nullable
    private static String queryForString(Context context, Uri self, String column, @Nullable String defaultValue) {
        ContentResolver resolver = context.getContentResolver();
        Cursor c = null;

        String var7;
        try {
            c = resolver.query(self, new String[]{column}, (String) null, (String[]) null, (String) null);
            String var6;
            if (!c.moveToFirst() || c.isNull(0)) {
                var6 = defaultValue;
                return var6;
            }

            var6 = c.getString(0);
            return var6;
        } catch (Exception var11) {
            Log.w("DocumentFile", "Failed query: " + var11);
            var7 = defaultValue;
        } finally {
            closeQuietly(c);
        }

        return var7;
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
    private static int queryForInt(Context context, Uri self, String column, int defaultValue) {
        return (int) queryForLong(context, self, column, (long) defaultValue);
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
    private static long queryForLong(Context context, Uri self, String column, long defaultValue) {
        ContentResolver resolver = context.getContentResolver();
        Cursor c = null;

        long var7;
        try {
            c = resolver.query(self, new String[]{column}, (String) null, (String[]) null, (String) null);
            if (!c.moveToFirst() || c.isNull(0)) {
                var7 = defaultValue;
                return var7;
            }

            var7 = c.getLong(0);
        } catch (Exception var13) {
            Log.w("DocumentFile", "Failed query: " + var13);
            long var8 = defaultValue;
            return var8;
        } finally {
            closeQuietly(c);
        }

        return var7;
    }

    /**
     * 关闭
     *
     * @param closeable 自动关闭资源
     */
    private static void closeQuietly(@Nullable AutoCloseable closeable) {
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