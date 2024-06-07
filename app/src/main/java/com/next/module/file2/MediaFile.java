package com.next.module.file2;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.next.module.file2.tool.FileTool;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * ClassName:MediaFile
 *
 * @author Afton
 * @time 2024/6/7
 * @auditor
 */
public class MediaFile extends File2 {

    //文件uri
    private Uri uri;

    //上下文
    private Context context;

    public MediaFile(File2 parent, Uri uri) {
        super(parent);
        this.uri = uri;
        this.context = FileConfig.getApplication();
    }

    @Override
    public Uri getUri() {
        return this.uri;
    }

    @Override
    public File2 createFile(String displayName) {
        return null;
    }

    @Override
    public File2 createDirectory(String displayName) {
        return null;
    }

    @Override
    public String getName() {
        return MediaContract.getName(this.context, this.uri);
    }

    @Override
    public String getPath() {
        return MediaContract.getPath(this.context, this.uri);
    }

    @Override
    public long lastModified() {
        return MediaContract.lastModified(this.context, this.uri);
    }

    @Override
    public long length() {
        return MediaContract.length(this.context, this.uri);
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public boolean isFile() {
        InputStream is;
        try {
            is = this.openInputStream();
        } catch (IOException e) {
            return false;
        }

        Contracts.closeQuietly(is);
        return true;
    }

    @Override
    public boolean exists() {
        return this.isFile();
    }

    @Override
    public File2[] listFiles() {
        return null;
    }

    @Override
    public File2[] listFiles(FileNameFilter filter) {
        return null;
    }

    @Override
    public boolean delete() {
        return false;
    }

    @Override
    public boolean renameTo(String displayName) {
        return false;
    }

    @Override
    public File2 findFile(String displayName) {
        return null;
    }

    @Override
    public boolean canWrite() {
        OutputStream os;
        try {
            os = openOutputStream(true);
        } catch (IOException e) {
            return false;
        }

        Contracts.closeQuietly(os);
        return true;
    }

    @Override
    public boolean canRead() {
        return this.isFile();
    }

    @Override
    public String getType() {
        String type = MediaContract.getType(this.context, this.uri);
        if (!TextUtils.isEmpty(type)) {
            return type;
        } else {
            return FileTool.getTypeForName(getName());
        }
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        return TrickOutputStream.create(this.context, this.uri, "w");
    }

    @Override
    public OutputStream openOutputStream(boolean append) throws IOException {
        return TrickOutputStream.create(this.context, this.uri, append ? "wa" : "w");
    }

    @Override
    public InputStream openInputStream() throws IOException {
        InputStream is;
        try {
            is = this.context.getContentResolver().openInputStream(this.uri);
        } catch (Exception e) {
            throw new IOException("Can't open InputStream");
        }
        if (is == null) {
            throw new IOException("Can't open InputStream");
        }
        return is;
    }
}