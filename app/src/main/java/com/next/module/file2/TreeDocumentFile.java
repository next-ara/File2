package com.next.module.file2;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.webkit.MimeTypeMap;

import androidx.annotation.Nullable;

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
        ContentResolver resolver = this.context.getContentResolver();
        Uri childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(this.uri, DocumentsContract.getDocumentId(this.uri));
        ArrayList<Uri> results = new ArrayList();
        Cursor c = null;

        try {
            c = resolver.query(childrenUri, new String[]{"document_id"}, null, null, null);

            while (c.moveToNext()) {
                String documentId = c.getString(0);
                Uri documentUri = DocumentsContract.buildDocumentUriUsingTree(this.uri, documentId);
                results.add(documentUri);
            }
        } catch (Exception e) {
        } finally {
            this.closeQuietly(c);
        }

        Uri[] result = results.toArray(new Uri[results.size()]);
        File2[] resultFiles = new File2[result.length];

        for (int i = 0; i < result.length; ++i) {
            resultFiles[i] = new TreeDocumentFile(this, result[i]);
        }

        return resultFiles;
    }

    @Override
    public File2[] listFiles(FileNameFilter filter) {
        return new File2[0];
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
        return DocumentsContractApi19.getType(this.context, this.uri);
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        return null;
    }

    @Override
    public OutputStream openOutputStream(boolean append) throws IOException {
        return null;
    }

    @Override
    public InputStream openInputStream() throws IOException {
        return null;
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

    /**
     * 关闭
     *
     * @param closeable 自动关闭资源
     */
    private void closeQuietly(@Nullable AutoCloseable closeable) {
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