package com.next.module.file2.tool;

import com.next.module.file2.File2;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * ClassName:文件操作工具类
 *
 * @author Afton
 * @time 2024/6/7
 * @auditor
 */
public class FileOperationTool {

    /**
     * 复制文件
     *
     * @param sourceFile       源文件
     * @param targetParentFile 目标父文件
     * @param isAddLastSuffix  是否添加后缀
     * @return 是否复制成功
     */
    public static boolean copyFile(File2 sourceFile, File2 targetParentFile, boolean isAddLastSuffix) {
        if (sourceFile == null || targetParentFile == null) {
            return false;
        }

        if (!sourceFile.exists() || !targetParentFile.exists()) {
            return false;
        }

        if (sourceFile.isDirectory() || !targetParentFile.isDirectory()) {
            return false;
        }

        File2 file2 = targetParentFile.findFile(sourceFile.getName());
        if (!isAddLastSuffix && file2 != null && file2.exists()) {
            return false;
        }

        //源文件名称
        String sourceFileName = sourceFile.getName();
        //目标文件名称
        String targetFileName = sourceFileName;
        int i = 1;
        int index = sourceFileName.lastIndexOf(".");
        while (file2 != null && file2.exists()) {
            i++;
            if (index < 0) {
                targetFileName = sourceFileName + "(" + i + ")";
                file2 = targetParentFile.findFile(targetFileName);
            } else {
                targetFileName = sourceFileName.substring(0, index) + "(" + i + ")" + sourceFileName.substring(index);
                file2 = targetParentFile.findFile(targetFileName);
            }
        }

        File2 targetFile = targetParentFile.createFile(targetFileName);
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = sourceFile.openInputStream();
            outputStream = targetFile.openOutputStream();

            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, len);
            }

            return true;
        } catch (Exception e) {
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
            }
        }

        return false;
    }

    /**
     * 复制文件
     *
     * @param sourceFile       源文件
     * @param targetParentFile 目标父文件
     * @return 是否复制成功
     */
    public static boolean copyFile(File2 sourceFile, File2 targetParentFile) {
        return copyFile(sourceFile, targetParentFile, true);
    }

    /**
     * 移动文件
     *
     * @param sourceFile       源文件
     * @param targetParentFile 目标父文件
     * @param isAddLastSuffix  是否添加后缀
     * @return 是否移动成功
     */
    public static boolean moveFile(File2 sourceFile, File2 targetParentFile, boolean isAddLastSuffix) {
        boolean isSuccess = copyFile(sourceFile, targetParentFile, isAddLastSuffix);
        if (isSuccess) {
            return sourceFile.delete();
        }

        return false;
    }

    /**
     * 移动文件
     *
     * @param sourceFile       源文件
     * @param targetParentFile 目标父文件
     * @return 是否移动成功
     */
    public static boolean moveFile(File2 sourceFile, File2 targetParentFile) {
        return moveFile(sourceFile, targetParentFile, true);
    }
}