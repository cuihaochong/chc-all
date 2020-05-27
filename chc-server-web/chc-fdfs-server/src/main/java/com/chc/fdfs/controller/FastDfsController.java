package com.chc.fdfs.controller;

import com.chc.base.Result;
import com.chc.fdfs.client.FastDfsClient;
import com.chc.fdfs.pojo.vo.StorageVo;
import com.chc.fdfs.pojo.vo.ThumbImageVo;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description:
 *
 * @author cuihaochong
 * @date 2019/9/10
 */
@CrossOrigin
@RestController
@RequestMapping("/fdfs")
public class FastDfsController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    FastDfsClient fdfsClient;

    /**
     * 多媒体文件上传
     *
     * @param file 上传文件
     * @return 源文件访问路径StorageVo, key如下:
     * <p> absolutePath 绝对路径
     * <p> relativePath 相对路径
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<StorageVo> upload(@RequestParam("file") MultipartFile file) {
        return fdfsClient.uploadFile(file);
    }

    /**
     * 上传图片,并按照默认配置生成缩略图
     *
     * @param file 上传文件
     * @return 原图片访问路径StorageVo, key如下:
     * <p> absolutePath 绝对路径
     * <p> relativePath 相对路径
     */
    @PostMapping("/uploadImageDef")
    public Result<StorageVo> uploadImageDef(@RequestParam("file") MultipartFile file) {
        return fdfsClient.uploadImageDef(file);
    }

    /**
     * 上传图片,并按照自定义配置生成缩略图,自定义规则如下:
     * <p>图片转化格式->format
     * <p>不生成缩略图->只传file
     * <p>使用默认配置->defaultConfig:true
     * <p>按照长,宽配置->width(int),height(int)
     * <p>按照缩放比例配置->percent(double),精度最高为小数点后两位
     *
     * @param file 上传文件
     * @return 访问路径StorageVo, key如下:
     * <p> absolutePath 绝对路径
     * <p> relativePath 相对路径
     */
    @PostMapping("/uploadImageSelf")
    public Result<StorageVo> uploadImageSelf(@RequestParam("file") MultipartFile file, ThumbImageVo vo) {
        return fdfsClient.uploadImageSelf(file, vo);
    }

    /**
     * 根据原图片相对路径获取缩略图路径
     *
     * @param relativePath 原图片相对路径
     * @return 缩略图访问路径StorageVo, key如下:
     * <p> absolutePath 绝对路径
     * <p> relativePath 相对路径
     */
    @GetMapping("/getThumbImagePath")
    public Result<StorageVo> getThumbImagePath(@RequestParam("relativePath") String relativePath) {
        return fdfsClient.getThumbImagePath(relativePath);
    }

    /**
     * 根据源文件相对路径获取绝对路径
     *
     * @param relativePath 源文件相对路径
     * @return 源文件访问路径StorageVo, key如下:
     * <p> absolutePath 绝对路径
     * <p> relativePath 相对路径
     */
    @GetMapping("/getAbsolutePath")
    public Result<StorageVo> getAbsolutePath(@RequestParam("relativePath") String relativePath) {
        try {
            StorageVo data = fdfsClient.getResAccessUrl(StorePath.parseFromUrl(relativePath));
            if (data == null) {
                return Result.error("文件相对路径有误,请重新输入");
            }
            return Result.success(data);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 文件下载
     *
     * @param relativePath 文件相对路径
     */
    @GetMapping("/download")
    public void download(@RequestParam("relativePath") String relativePath, HttpServletResponse response) {

        byte[] data = fdfsClient.download(relativePath);
        response.setCharacterEncoding("UTF-8");
//		response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("test.jpg", "UTF-8"));

        try {
            ServletOutputStream outputStream = response.getOutputStream();
            IOUtils.write(data, outputStream);
        } catch (IOException e) {
            logger.error("下载文件出现异常,异常为:" + e.getMessage());
        }
    }

    /**
     * 删除文件
     *
     * @param relativePath 源文件相对路径
     */
    @GetMapping("/deleteFile")
    public Result deleteFile(@RequestParam("relativePath") String relativePath) {
        return fdfsClient.deleteFile(relativePath);
    }
}
