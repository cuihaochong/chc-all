package com.chc.fdfs.pojo.vo;

import com.github.tobato.fastdfs.domain.upload.ThumbImage;
import lombok.Data;

/**
 * Description: 缩略图入参Vo
 *
 * @author CuiHaochong
 * @date 2019/8/28
 */
@Data
public class ThumbImageVo {
    /**
     * 按默认配置生成缩略图
     */
    private boolean defaultConfig = false;
    /**
     * 缩放长度
     */
    private int width;
    /**
     * 缩放高度
     */
    private int height;
    /**
     * 缩放比例
     */
    private double percent;

    /**
     * 图片转化格式
     */
    private String format;


    /**
     * 构造ThumbImage
     *
     * @param vo 图片缩略图vo
     * @return 构造ThumbImage
     */
    public ThumbImage convert(ThumbImageVo vo) {
        if (vo.isDefaultConfig()) {
            return new ThumbImage();
        } else if (vo.getWidth() > 0 && vo.getHeight() > 0) {
            return new ThumbImage(vo.getWidth(), vo.getHeight());
        } else if (vo.getPercent() > 0.0) {
            return new ThumbImage(vo.getPercent());
        } else {
            return null;
        }
    }
}
