package com.next.module.file2;

import android.content.Context;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import androidx.annotation.NonNull;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * ClassName:TrickOutputStream
 *
 * @author Afton
 * @time 2024/6/7
 * @auditor
 */
public class TrickOutputStream extends FileOutputStream {

    private ParcelFileDescriptor pfd;

    public TrickOutputStream(FileDescriptor fdObj, ParcelFileDescriptor pfd) {
        super(fdObj);
        this.pfd = pfd;
    }

    @Override
    public void close() throws IOException {
        this.pfd.close();
        super.close();
    }

    @NonNull
    public static OutputStream create(Context context, Uri uri, String mode) throws IOException {
        ParcelFileDescriptor pfd;
        try {
            pfd = context.getContentResolver().openFileDescriptor(uri, mode);
        } catch (Exception e) {
            throw new IOException("Can't get ParcelFileDescriptor");
        }
        if (pfd == null) {
            throw new IOException("Can't get ParcelFileDescriptor");
        }
        FileDescriptor fd = pfd.getFileDescriptor();
        if (fd == null) {
            throw new IOException("Can't get FileDescriptor");
        }

        return new TrickOutputStream(fd, pfd);
    }
}