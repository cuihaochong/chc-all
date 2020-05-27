package com.chc.fdfs.client;

import com.github.tobato.fastdfs.FdfsClientConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * Description: 图片格式转化
 *
 * @author CuiHaochong
 * @date 2019/9/3
 */
@Slf4j
@Component
@ConfigurationProperties(prefix = "fdfs.temp")
public class ImageConverterUtil {
    /**
     * 支持的图片类型
     */
    private static final List<String> SUPPORT_IMAGE_LIST = Arrays.asList(FdfsClientConstants.SUPPORT_IMAGE_TYPE);

    private static String dic;

    /**
     * 是否是支持的图片文件
     *
     * @param fileExtName 文件扩展名
     * @return 是/否
     */
    public static boolean isSupportImage(String fileExtName) {
        return SUPPORT_IMAGE_LIST.contains(fileExtName.toUpperCase());
    }

    /**
     * 转化操作
     *
     * @param in 文件流
     * @return 转化后文件
     */
    public static File converter(InputStream in, String format) {
        BufferedImage im = null;
        try {
            im = ImageIO.read(in);
        } catch (IOException e) {
            log.error("读取文件错误,异常为:" + e.getMessage());
        }
        return converter(im, format);
    }

    public static File converter(File file, String format) {
        BufferedImage im = null;
        try {
            im = ImageIO.read(file);
        } catch (IOException e) {
            log.error("读取文件错误,异常为:" + e.getMessage());
        }
        return converter(im, format);
    }

    public static File converter(BufferedImage im, String format) {
        if (isSupportImage(format)) {
            try {
                File tempFile = createTempFile(format);
                if (tempFile == null) {
                    return null;
                }
                boolean write = ImageIO.write(im, format, tempFile);
                if (write) {
                    return tempFile;
                }
            } catch (IOException e) {
                log.error("图片格式转化错误,异常为:" + e.getMessage());
            }
        }
        return null;
    }

    /**
     * 创建临时文件
     *
     * @param format 临时文件后缀名,不包含.
     * @return 临时文件
     * @throws IOException 异常
     */
    private static File createTempFile(String format) throws IOException {
        if (StringUtils.isBlank(dic)) {
            log.error("未配置fdfs.temp.dic");
        }
        File tempDic = new File(dic);
        if (!tempDic.exists()) {
            log.info("目录" + dic + "的不存在，正在创建");
            if (tempDic.mkdirs()) {
                log.info("目录" + dic + "创建成功！");
            } else {
                log.info("目录" + dic + "创建失败！");
                return null;
            }
        }
        return File.createTempFile(System.currentTimeMillis() + "", "." + format, tempDic);
    }

    public String getDic() {
        return dic;
    }

    public void setDic(String dic) {
        ImageConverterUtil.dic = dic;
    }
}
