package com.chc.fdfs.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * t_fdfs_storage_info表
 *
 * @author CuiHaochong
 * @version 1.0.0 2019-08-28
 */
@Data
@TableName("t_fdfs_storage_info")
public class FdfsStorageInfoDO implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -1541438869097914552L;

    /**
     * pid
     */
    @TableId(value = "pid", type = IdType.AUTO)
    private Integer pid;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件扩展名
     */
    private String fileExtName;

    /**
     * 存储相对路径
     */
    private String relativePath;

    /**
     * 存储时间
     */
    private LocalDateTime storageTime;

    /**
     * 缩略图宽度
     */
    private Integer thumbImageWidth;

    /**
     * 缩略图高度
     */
    private Integer thumbImageHeight;

    /**
     * 缩略图百分比
     */
    private Double thumbImagePercent;

}