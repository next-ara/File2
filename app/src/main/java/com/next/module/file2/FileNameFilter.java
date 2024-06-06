package com.next.module.file2;

/**
 * ClassName:文件名称过滤器
 *
 * @author Afton
 * @time 2024/6/7
 * @auditor
 */
public interface FileNameFilter {

    /**
     * 承认
     *
     * @param dir      目录
     * @param filename 文件名
     * @return 是否承认
     */
    boolean accept(File2 dir, String fileName);
}