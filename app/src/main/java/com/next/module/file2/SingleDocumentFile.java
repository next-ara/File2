package com.next.module.file2;

import android.content.Context;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * ClassName:SingleDocumentFile
 *
 * @author Afton
 * @time 2024/6/7
 * @auditor
 */
public class SingleDocumentFile extends File2 {

    //文件uri
    private Uri uri;

    //上下文
    private Context context;

    public SingleDocumentFile(File2 parent, Uri uri) {
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
        return DocumentsContractApi19.getName(this.context, this.uri);
    }

    @Override
    public String getPath() {
        return DocumentsContractApi19.getPath(this.context, this.uri);
    }

    @Override
    public long lastModified() {
        return DocumentsContractApi19.lastModified(this.context, this.uri);
    }

    @Override
    public long length() {
        return DocumentsContractApi19.length(this.context, this.uri);
    }

    @Override
    public boolean isDirectory() {
        return DocumentsContractApi19.isDirectory(this.context, this.uri);
    }

    @Override
    public boolean isFile() {
        return DocumentsContractApi19.isFile(this.context, this.uri);
    }

    @Override
    public boolean exists() {
        return DocumentsContractApi19.exists(this.context, this.uri);
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
        try {
            return DocumentsContract.deleteDocument(this.context.getContentResolver(), this.uri);
        } catch (Exception e) {
            return false;
        }
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
        return DocumentsContractApi19.canWrite(this.context, this.uri);
    }

    @Override
    public boolean canRead() {
        return DocumentsContractApi19.canRead(this.context, this.uri);
    }

    @Override
    public String getType() {
        String type = DocumentsContractApi19.getType(this.context, this.uri);
        if (!TextUtils.isEmpty(type)) {
            return type;
        } else {
            return FileTool.getTypeForName(getName());
        }
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        OutputStream os;
        try {
            os = this.context.getContentResolver().openOutputStream(this.uri);
        } catch (Exception e) {
            throw new IOException("Can't open OutputStream");
        }
        if (os == null) {
            throw new IOException("Can't open OutputStream");
        }

        return os;
    }

    @Override
    public OutputStream openOutputStream(boolean append) throws IOException {
        OutputStream os;
        try {
            os = this.context.getContentResolver().openOutputStream(this.uri, append ? "wa" : "w");
        } catch (Exception e) {
            throw new IOException("Can't open OutputStream");
        }
        if (os == null) {
            throw new IOException("Can't open OutputStream");
        }

        return os;
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