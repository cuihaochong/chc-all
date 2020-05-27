SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_fdfs_storage_info
-- ----------------------------
DROP TABLE IF EXISTS `t_fdfs_storage_info`;
CREATE TABLE `t_fdfs_storage_info` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `file_size` bigint(20) NOT NULL COMMENT '文件大小',
  `file_ext_name` varchar(255) DEFAULT NULL COMMENT '文件扩展名',
  `relative_path` varchar(255) NOT NULL COMMENT '存储相对路径',
  `storage_time` datetime DEFAULT NULL COMMENT '存储时间',
  `thumb_image_width` int(11) DEFAULT '0' COMMENT '缩略图宽度',
  `thumb_image_height` int(11) DEFAULT '0' COMMENT '缩略图高度',
  `thumb_image_percent` double(3,2) DEFAULT '0.00' COMMENT '缩略图百分比',
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
