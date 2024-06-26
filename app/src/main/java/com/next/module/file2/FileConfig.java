package com.next.module.file2;

import android.app.Application;

/**
 * ClassName:文件配置类
 *
 * @author Afton
 * @time 2024/6/7
 * @auditor
 */
public class FileConfig {

    private static FileConfig instance;

    //应用对象
    private Application application;

    //文件访问权限
    private String authority;

    public static FileConfig getInstance() {
        if (instance == null) {
            instance = new FileConfig();
        }

        return instance;
    }

    /**
     * 获取应用对象
     *
     * @return 应用对象
     */
    public static Application getApplication() {
        return getInstance().application;
    }

    /**
     * 获取文件访问权限
     *
     * @return 文件访问权限
     */
    public static String getAuthority() {
        return getInstance().authority;
    }

    /**
     * 初始化
     *
     * @param application 应用对象
     * @return 文件配置对象
     */
    public FileConfig init(Application application) {
        this.application = application;
        return this;
    }

    /**
     * 设置文件访问权限
     *
     * @param authority 文件访问权限
     * @return 文件配置对象
     */
    public FileConfig setAuthority(String authority) {
        this.authority = authority;
        return this;
    }
}