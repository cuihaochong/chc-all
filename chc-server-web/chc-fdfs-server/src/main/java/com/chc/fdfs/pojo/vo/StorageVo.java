package com.chc.fdfs.pojo.vo;

import lombok.Data;

/**
 * Description: 文件存储返回数据Vo
 *
 * @author CuiHaochong
 * @date 2019/8/30
 */
@Data
public class StorageVo {

    /**
     * 绝对路径
     */
    private String absolutePath;
    /**
     * 相对路径
     */
    private String relativePath;

    public StorageVo(String absolutePath, String relativePath) {
        this.absolutePath = absolutePath;
        this.relativePath = relativePath;
    }
}
