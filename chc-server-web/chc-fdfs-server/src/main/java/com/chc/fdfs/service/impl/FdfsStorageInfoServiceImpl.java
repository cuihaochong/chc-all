package com.chc.fdfs.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chc.fdfs.mapper.FdfsInfoMapper;
import com.chc.fdfs.pojo.entity.FdfsStorageInfoDO;
import com.chc.fdfs.service.FdfsStorageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description: FdfsStorageInfo服务具体实现
 *
 * @author CuiHaochong
 * @date 2019/8/28
 */
@Service
public class FdfsStorageInfoServiceImpl implements FdfsStorageInfoService {
    @Autowired
    FdfsInfoMapper mapper;

    @Override
    public void addOne(FdfsStorageInfoDO info) {
        mapper.insert(info);
    }

    @Override
    public FdfsStorageInfoDO findByRelativePath(String relativePath) {
        return mapper.selectOne(Wrappers.lambdaQuery(FdfsStorageInfoDO.class)
            .eq(FdfsStorageInfoDO::getRelativePath, relativePath));
    }
}
