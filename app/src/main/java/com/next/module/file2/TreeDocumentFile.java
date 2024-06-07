package com.next.module.file2;

import android.content.Context;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import com.next.module.file2.tool.FileTool;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * ClassName:TreeDocumentFile
 *
 * @author Afton
 * @time 2024/6/7
 * @auditor
 */
public class TreeDocumentFile extends File2 {

    //文件uri
    private Uri uri;

    //上下文
    private Context context;

    public TreeDocumentFile(File2 parent, Uri uri) {
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
        int index = displayName.lastIndexOf('.');
        String mimeType = "application/octet-stream";
        if (index > 0) {
            displayName = displayName.substring(0, index);
            String extension = displayName.substring(index + 1);
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }

        Uri result = this.createFile(this.uri, mimeType, displayName);
        return result != null ? new TreeDocumentFile(this, result) : null;
    }

    @Override
    public File2 createDirectory(String displayName) {
        Uri result = createFile(this.uri, DocumentsContract.Document.MIME_TYPE_DIR, displayName);
        return result != null ? new TreeDocumentFile(this, result) : null;
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
        Uri[] results = DocumentsContractApi19.listFiles(this.context, this.uri);
        File2[] resultFiles = new File2[results.length];

        for (int i = 0; i < results.length; ++i) {
            resultFiles[i] = new TreeDocumentFile(this, results[i]);
        }

        return resultFiles;
    }

    @Override
    public File2[] listFiles(FileNameFilter filter) {
        Uri[] results = DocumentsContractApi19.listFiles(this.context, this.uri);

        ArrayList<File2> resultFiles = new ArrayList<>();
        for (Uri result : results) {
            String fileName = DocumentsContractApi19.getName(this.context, result);
            if (!TextUtils.isEmpty(fileName) && filter.accept(this, fileName)) {
                resultFiles.add(new TreeDocumentFile(this, result));
            }
        }

        return resultFiles.toArray(new File2[resultFiles.size()]);
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
        try {
            Uri result = DocumentsContract.renameDocument(this.context.getContentResolver(), this.uri, displayName);
            if (result != null) {
                this.uri = result;
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public File2 findFile(String displayName) {
        if (TextUtils.isEmpty(displayName)) {
            return null;
        }

        if (!isDirectory()) {
            return null;
        }

        Uri[] results = DocumentsContractApi19.listFiles(this.context, this.uri);

        for (Uri uri : results) {
            final String name = DocumentsContractApi19.getName(this.context, uri);
            if (displayName.equals(name)) {
                return new TreeDocumentFile(this, uri);
            }
        }
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
        if (isDirectory()) {
            throw new IOException("Can't open OutputStream from a directory");
        }

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
        if (isDirectory()) {
            throw new IOException("Can't open OutputStream from a directory");
        }

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
        if (isDirectory()) {
            throw new IOException("Can't open InputStream from a directory");
        }

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

    /**
     * 创建文件
     *
     * @param self        当前文件
     * @param mimeType    文件类型
     * @param displayName 文件名
     * @return 文件uri
     */
    private Uri createFile(Uri self, String mimeType, String displayName) {
        try {
            return DocumentsContract.createDocument(this.context.getContentResolver(), self, mimeType, displayName);
        } catch (Exception e) {
            return null;
        }
    }
}