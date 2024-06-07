package com.next.module.file2.tool;

/**
 * ClassName:文件加载异常
 *
 * @author Afton
 * @time 2024/6/7
 * @auditor
 */
public class FileLoadException extends RuntimeException {

    //错误码
    public static class ErrorCode {
        //文件不存在
        public static final int ERROR_CODE_FILE_NOT_EXIST = 1;
        //没有访问权限
        public static final int ERROR_CODE_NO_PERMISSION = 2;
        //加载信息不完整
        public static final int ERROR_CODE_INCOMPLETE_INFO = 3;
    }

    //错误码
    private int errorCode;

    public FileLoadException(int errorCode) {
        this.errorCode = errorCode;
    }

    public FileLoadException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public FileLoadException(String message, Throwable cause, int errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public FileLoadException(Throwable cause, int errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public FileLoadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int errorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }
}