package com.next.module.file2;

import android.net.Uri;

import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * ClassName:File2
 *
 * @author Afton
 * @time 2024/6/6
 * @auditor
 */
abstract public class File2 {

    //父文件对象
    private File2 parent;

    public File2(File2 parent) {
        this.parent = parent;
    }

    /**
     * 获取文件Uri
     *
     * @return 文件Uri
     */
    abstract public Uri getUri();

    /**
     * 创建文件
     *
     * @param displayName 文件名
     * @return 文件对象
     */
    abstract public File2 createFile(String displayName);

    /**
     * 创建文件夹
     *
     * @param displayName 文件夹名
     * @return 文件夹对象
     */
    abstract public File2 createDirectory(String displayName);

    /**
     * 获取文件名
     *
     * @return 文件名
     */
    abstract public String getName();

    /**
     * 获取文件路径
     *
     * @return 文件路径
     */
    abstract public String getPath();

    /**
     * 获取文件最后修改时间
     *
     * @return 文件最后修改时间
     */
    abstract public long lastModified();

    /**
     * 获取文件大小
     *
     * @return 文件大小
     */
    abstract public long length();

    /**
     * 判断是否是文件夹
     *
     * @return 是否是文件夹
     */
    abstract public boolean isDirectory();

    /**
     * 判断是否是文件
     *
     * @return 是否是文件
     */
    abstract public boolean isFile();

    /**
     * 获取父文件对象
     *
     * @return 父文件对象
     */
    public File2 getParentFile() {
        return this.parent;
    }

    /**
     * 判断文件是否存在
     *
     * @return 文件是否存在
     */
    abstract public boolean exists();

    /**
     * 获取文件列表
     *
     * @return 文件列表
     */
    abstract public File2[] listFiles();

    /**
     * 获取文件列表
     *
     * @param filter 文件过滤器
     * @return 文件列表
     */
    abstract public File2[] listFiles(FileNameFilter filter);

    /**
     * 删除文件
     *
     * @return 是否删除成功
     */
    abstract public boolean delete();

    /**
     * 重命名文件
     *
     * @param displayName 新的文件名
     * @return 是否重命名成功
     */
    abstract public boolean renameTo(String displayName);

    /**
     * 查找文件
     *
     * @param displayName 文件名
     * @return 文件对象
     */
    abstract public File2 findFile(String displayName);

    /**
     * 判断文件是否可写
     *
     * @return 文件是否可写
     */
    abstract public boolean canWrite();

    /**
     * 判断文件是否可读
     *
     * @return 文件是否可读
     */
    abstract public boolean canRead();

    /**
     * 获取文件类型
     *
     * @return 文件类型
     */
    abstract public String getType();

    /**
     * 打开文件输出流
     *
     * @return 文件输出流
     * @throws IOException IO异常
     */
    abstract public OutputStream openOutputStream() throws IOException;

    /**
     * 打开文件输出流
     *
     * @param append 是否追加
     * @return 文件输出流
     * @throws IOException IO异常
     */
    abstract public OutputStream openOutputStream(boolean append) throws IOException;

    /**
     * 打开文件输入流
     *
     * @return 文件输入流
     * @throws IOException IO异常
     */
    abstract public InputStream openInputStream() throws IOException;
}