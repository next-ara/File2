package com.next.module.file2;

import android.net.Uri;
import android.text.TextUtils;

import com.next.module.file2.tool.FileTool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * ClassName:RawFile
 *
 * @author Afton
 * @time 2024/6/7
 * @auditor
 */
public class RawFile extends File2 {

    private File file;

    public RawFile(File2 parent, File file) {
        super(parent);
        this.file = file;
    }

    @Override
    public Uri getUri() {
        return Uri.fromFile(this.file);
    }

    @Override
    public File2 createFile(String displayName) {
        if (TextUtils.isEmpty(displayName)) {
            return null;
        }

        final File target = new File(this.file, displayName);
        if (target.exists()) {
            if (target.isFile()) {
                return new RawFile(this, target);
            } else {
                return null;
            }
        } else {
            OutputStream os = null;
            try {
                os = new FileOutputStream(target);
                return new RawFile(this, target);
            } catch (IOException e) {
                return null;
            } finally {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
        }
    }

    @Override
    public File2 createDirectory(String displayName) {
        if (TextUtils.isEmpty(displayName)) {
            return null;
        }

        final File target = new File(this.file, displayName);
        if (target.isDirectory() || target.mkdirs()) {
            return new RawFile(this, target);
        } else {
            return null;
        }
    }

    @Override
    public String getName() {
        return this.file.getName();
    }

    @Override
    public String getPath() {
        return this.file.getPath();
    }

    @Override
    public long lastModified() {
        return this.file.lastModified();
    }

    @Override
    public long length() {
        return this.file.length();
    }

    @Override
    public boolean isDirectory() {
        return this.file.isDirectory();
    }

    @Override
    public boolean isFile() {
        return this.file.isFile();
    }

    @Override
    public boolean exists() {
        return this.file.exists();
    }

    @Override
    public File2[] listFiles() {
        final File[] files = this.file.listFiles();
        if (files == null) {
            return null;
        }

        int length = files.length;
        File2[] file2s = new File2[length];
        for (int i = 0; i < length; i++) {
            file2s[i] = new RawFile(this, files[i]);
        }

        return file2s;
    }

    @Override
    public File2[] listFiles(FileNameFilter filter) {
        if (filter == null) {
            return listFiles();
        }

        final File[] files = this.file.listFiles();
        if (files == null) {
            return null;
        }

        final ArrayList<File2> file2s = new ArrayList<>();
        for (File file : files) {
            if (filter.accept(this, file.getName())) {
                file2s.add(new RawFile(this, file));
            }
        }

        return file2s.toArray(new File2[file2s.size()]);
    }

    @Override
    public boolean delete() {
        this.deleteContents(this.file);
        return this.file.delete();
    }

    @Override
    public boolean renameTo(String displayName) {
        if (TextUtils.isEmpty(displayName)) {
            return false;
        }

        final File target = new File(this.file.getParentFile(), displayName);
        if (this.file.renameTo(target)) {
            this.file = target;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public File2 findFile(String displayName) {
        if (TextUtils.isEmpty(displayName)) {
            return null;
        }

        final File child = new File(this.file, displayName);
        return child.exists() ? new RawFile(this, child) : null;
    }

    @Override
    public boolean canWrite() {
        return this.file.canWrite();
    }

    @Override
    public boolean canRead() {
        return this.file.canRead();
    }

    @Override
    public String getType() {
        if (this.file.isDirectory()) {
            return null;
        } else {
            return FileTool.getTypeForName(this.getName());
        }
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        return new FileOutputStream(this.file);
    }

    @Override
    public OutputStream openOutputStream(boolean append) throws IOException {
        return new FileOutputStream(this.file, append);
    }

    @Override
    public InputStream openInputStream() throws IOException {
        return new FileInputStream(this.file);
    }

    /**
     * 删除目录下的所有文件
     *
     * @param dir 目录
     * @return 是否删除成功
     */
    private boolean deleteContents(File dir) {
        File[] files = dir.listFiles();
        boolean success = true;
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    success &= this.deleteContents(file);
                }

                if (!file.delete()) {
                    success = false;
                }
            }
        }

        return success;
    }

    public File getFile() {
        return file;
    }
}