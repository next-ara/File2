package com.next.module.file2.tool;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.next.module.file2.File2Creator;
import com.next.module.file2.FileConfig;
import com.next.module.file2.MediaFile;

import java.util.ArrayList;

/**
 * ClassName:媒体文件工具类
 *
 * @author Afton
 * @time 2024/6/8
 * @auditor
 */
public class MediaFileTool {

    /**
     * 获取图片媒体文件
     *
     * @return 媒体文件列表
     */
    public static ArrayList<MediaFile> getImageMediaFile() {
        ArrayList<MediaFile> mediaFiles = new ArrayList<>();
        ContentResolver contentResolver = FileConfig.getApplication().getContentResolver();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.Images.Media._ID};
        Cursor cursor = contentResolver.query(uri, projection, null, null, MediaStore.Images.Media.DATE_TAKEN + " DESC");
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    long imageId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                    Uri imageUri = ContentUris.withAppendedId(uri, imageId);
                    MediaFile mediaFile = File2Creator.fromUri(imageUri);
                    mediaFiles.add(mediaFile);
                }
            } finally {
                cursor.close();
            }
        }

        return mediaFiles;
    }

    /**
     * 获取音频媒体文件
     *
     * @return 媒体文件列表
     */
    public static ArrayList<MediaFile> getAudioMediaFile() {
        ArrayList<MediaFile> mediaFiles = new ArrayList<>();
        ContentResolver contentResolver = FileConfig.getApplication().getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.Audio.Media._ID};
        Cursor cursor = contentResolver.query(uri, projection, null, null, MediaStore.Audio.Media.DATE_TAKEN + " DESC");
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    long imageId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                    Uri imageUri = ContentUris.withAppendedId(uri, imageId);
                    MediaFile mediaFile = File2Creator.fromUri(imageUri);
                    mediaFiles.add(mediaFile);
                }
            } finally {
                cursor.close();
            }
        }

        return mediaFiles;
    }

    /**
     * 获取视频媒体文件
     *
     * @return 媒体文件列表
     */
    public static ArrayList<MediaFile> getVideoMediaFile() {
        ArrayList<MediaFile> mediaFiles = new ArrayList<>();
        ContentResolver contentResolver = FileConfig.getApplication().getContentResolver();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.Video.Media._ID};
        Cursor cursor = contentResolver.query(uri, projection, null, null, MediaStore.Video.Media.DATE_TAKEN + " DESC");
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    long imageId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                    Uri imageUri = ContentUris.withAppendedId(uri, imageId);
                    MediaFile mediaFile = File2Creator.fromUri(imageUri);
                    mediaFiles.add(mediaFile);
                }
            } finally {
                cursor.close();
            }
        }

        return mediaFiles;
    }

    /**
     * 获取媒体文件
     *
     * @param selectionArgs 媒体类型
     * @return 媒体文件列表
     */
    public static ArrayList<MediaFile> getMediaFile(String[] selectionArgs) {
        ArrayList<MediaFile> mediaFiles = new ArrayList<>();
        ContentResolver contentResolver = FileConfig.getApplication().getContentResolver();
        Uri uri = MediaStore.Files.getContentUri("external");

        String[] projection = {MediaStore.Files.FileColumns._ID};
        String selection = MediaStore.Files.FileColumns.MIME_TYPE + "=?";

        Cursor cursor = contentResolver.query(uri, projection, selection, selectionArgs, MediaStore.Files.FileColumns.DATE_TAKEN + " DESC");
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    long imageId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID));
                    Uri imageUri = ContentUris.withAppendedId(uri, imageId);
                    MediaFile mediaFile = File2Creator.fromUri(imageUri);
                    mediaFiles.add(mediaFile);
                }
            } finally {
                cursor.close();
            }
        }

        return mediaFiles;
    }
}