package com.chc.fdfs.service;

import com.chc.fdfs.pojo.entity.FdfsStorageInfoDO;

/**
 * Description:
 *
 * @author CuiHaochong
 * @date 2019/8/28
 */
public interface FdfsStorageInfoService {

    /**
     * 插入一条
     *
     * @param info FdfsInfo实体
     */
    void addOne(FdfsStorageInfoDO info);

    /**
     * 根据存储相对路径查询数据
     *
     * @param relativePath 存储相对路径
     * @return FdfsInfo实体
     */
    FdfsStorageInfoDO findByRelativePath(String relativePath);
}
