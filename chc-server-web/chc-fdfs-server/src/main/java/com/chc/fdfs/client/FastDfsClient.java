package com.chc.fdfs.client;

import com.chc.base.Result;
import com.chc.fdfs.pojo.entity.FdfsStorageInfoDO;
import com.chc.fdfs.pojo.vo.StorageVo;
import com.chc.fdfs.pojo.vo.ThumbImageVo;
import com.chc.fdfs.service.FdfsStorageInfoService;
import com.github.tobato.fastdfs.domain.conn.FdfsWebServer;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.domain.upload.FastImageFile;
import com.github.tobato.fastdfs.domain.upload.ThumbImage;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

/**
 * Description: FastDfs客户端
 *
 * @author CuiHaochong
 * @date 2019/8/8
 */
@Component
public class FastDfsClient {

    private final Logger logger = LoggerFactory.getLogger(FastDfsClient.class);

    @Autowired
    private FastFileStorageClient storageClient;
    @Autowired
    private ThumbImageConfig thumbImageConfig;
    @Autowired
    private FdfsWebServer fdfsWebServer;

    @Autowired
    FdfsStorageInfoService fdfsStorageInfoService;

    /**
     * 上传文件
     *
     * @param file 文件对象
     * @return 访问路径StorageVo, key如下:
     * <p> absolutePath 绝对路径
     * <p> relativePath 相对路径
     */
    public Result<StorageVo> uploadFile(MultipartFile file) {
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            logger.error("传入文件转化流错误");
            return Result.error("传入文件转化流错误");
        }
        String extName = FilenameUtils.getExtension(file.getOriginalFilename());
        StorageVo storageVo;
        try {
            StorePath storePath = storageClient.uploadFile(inputStream, file.getSize(), extName, null);
            storageVo = getResAccessUrl(storePath);
        } catch (Exception e) {
            logger.error("上传文件失败,异常为:" + e.getMessage());
            return Result.error("上传文件失败,异常为:" + e.getMessage());
        }
        try {
            addStorageInfo(extName, file.getSize(), storageVo.getRelativePath(), null);
        } catch (Exception e) {
            logger.error("保存上传文件信息失败,异常为:" + e.getMessage());
            return Result.error("保存上传文件信息失败,异常为:" + e.getMessage());
        }
        return Result.success(storageVo);
    }

    /**
     * 上传文件
     *
     * @param bytes   文件数据
     * @param extName 文件后缀名
     * @return 访问路径StorageVo, key如下:
     * <p> absolutePath 绝对路径
     * <p> relativePath 相对路径
     */
    public Result<StorageVo> uploadFile(byte[] bytes, String extName) {
        StorageVo storageVo;
        try {
            StorePath storePath = storageClient.uploadFile(
                new ByteArrayInputStream(bytes), bytes.length, extName, null);
            storageVo = getResAccessUrl(storePath);
        } catch (Exception e) {
            logger.error("上传文件失败,异常为:" + e.getMessage());
            return Result.error("上传文件失败,异常为:" + e.getMessage());
        }
        try {
            addStorageInfo(extName, bytes.length, storageVo.getRelativePath(), null);
        } catch (Exception e) {
            logger.error("保存上传文件信息失败,异常为:" + e.getMessage());
            return Result.error("保存上传文件信息失败,异常为:" + e.getMessage());
        }
        return Result.success(storageVo);
    }

    /**
     * 上传文件
     *
     * @param file 文件对象
     * @return 访问路径StorageVo, key如下:
     * <p> absolutePath 绝对路径
     * <p> relativePath 相对路径
     */
    public Result<StorageVo> uploadFile(File file) {
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            logger.error("传入文件转化流错误");
            return Result.error("传入文件转化流错误,异常为:" + e.getMessage());
        }
        String extName = FilenameUtils.getExtension(file.getName());
        StorageVo storageVo;
        try {
            StorePath storePath = storageClient.uploadFile(inputStream, file.length(), extName, null);
            storageVo = getResAccessUrl(storePath);
        } catch (Exception e) {
            logger.error("上传文件失败,异常为:" + e.getMessage());
            return Result.error("上传文件失败,异常为:" + e.getMessage());
        }
        try {
            addStorageInfo(extName, file.length(), storageVo.getRelativePath(), null);
        } catch (Exception e) {
            logger.error("保存上传文件信息失败,异常为:" + e.getMessage());
            return Result.error("保存上传文件信息失败,异常为:" + e.getMessage());
        }
        return Result.success(storageVo);
    }

    /**
     * 把字符串作为指定格式的文件上传
     *
     * @param content       文件内容
     * @param fileExtension 文件扩展名
     * @return 访问路径StorageVo, key如下:
     * <p> absolutePath 绝对路径
     * <p> relativePath 相对路径
     */
    public StorageVo uploadFile(String content, String fileExtension) {
        byte[] buff = content.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream stream = new ByteArrayInputStream(buff);
        StorePath storePath = storageClient.uploadFile(stream, buff.length, fileExtension, null);
        return getResAccessUrl(storePath);
    }

    /**
     * 上传图片,并按照默认配置生成缩略图
     *
     * @param file 上传文件
     * @return 原图片访问路径StorageVo, key如下:
     * <p> absolutePath 绝对路径
     * <p> relativePath 相对路径
     */
    public Result<StorageVo> uploadImageDef(MultipartFile file) {
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            logger.error("传入文件转化流错误");
            return Result.error("传入文件转化流错误,异常为:" + e.getMessage());
        }
        String extName = FilenameUtils.getExtension(file.getOriginalFilename());
        StorageVo storageVo;
        try {
            StorePath storePath = storageClient.uploadImageAndCrtThumbImage(inputStream, file.getSize(), extName, null);
            storageVo = getResAccessUrl(storePath);
        } catch (Exception e) {
            logger.error("上传文件失败,异常为:" + e.getMessage());
            return Result.error("上传文件失败,异常为:" + e.getMessage());
        }
        try {
            addStorageInfo(extName, file.getSize(), storageVo.getRelativePath(), defaultThumbImage());
        } catch (Exception e) {
            logger.error("保存上传文件信息失败,异常为:" + e.getMessage());
            return Result.error("保存上传文件信息失败,异常为:" + e.getMessage());
        }
        return Result.success(storageVo);
    }

    /**
     * 上传图片,并按照默认配置生成缩略图,自定义规则如下:
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
    public Result<StorageVo> uploadImageSelf(MultipartFile file, ThumbImageVo vo) {
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            logger.error("传入文件转化流错误");
            return Result.error("传入文件转化流错误,异常为:" + e.getMessage());
        }
        String extName = FilenameUtils.getExtension(file.getOriginalFilename());
        StorageVo storageVo;
        ThumbImage thumbImage = vo.convert(vo);
        //图片转化操作
        if (StringUtils.isNotBlank(vo.getFormat())) {
            Result<InputStream> rs = doImageConverter(vo.getFormat(), inputStream);
            if (rs.getCode() == Result.success().getCode()) {
                inputStream = rs.getData();
            } else {
                return Result.error(rs.getCode(), rs.getMsg());
            }
        }
        try {
            StorePath storePath = storageClient.uploadImage(new FastImageFile(inputStream, file.getSize(), extName, new HashSet<>(), thumbImage));
            storageVo = getResAccessUrl(storePath);
        } catch (Exception e) {
            logger.error("上传文件失败,异常为:" + e.getMessage());
            return Result.error("上传文件失败,异常为:" + e.getMessage());
        }
        try {
            addStorageInfo(extName, file.getSize(), storageVo.getRelativePath(), thumbImage);
        } catch (Exception e) {
            logger.error("保存上传文件信息失败,异常为:" + e.getMessage());
            return Result.error("保存上传文件信息失败,异常为:" + e.getMessage());
        }
        return Result.success(storageVo);
    }

    /**
     * 图片转化格式
     *
     * @param format 图片待转化格式
     * @param in     输入流
     * @return 转化结果
     */
    private Result<InputStream> doImageConverter(String format, InputStream in) {
        if (!ImageConverterUtil.isSupportImage(format)) {
            logger.error("期望转化的类型[{}]不支持", format);
            return Result.error("不支持的图片转化类型");
        }
        try {
            File temp = ImageConverterUtil.converter(in, format);
            FileInputStream data = new FileInputStream(temp);
            return Result.success(data);
        } catch (FileNotFoundException e) {
            logger.error("图片类型转化异常,异常为{}", e.getMessage());
            return Result.error("图片类型转化异常,异常为:" + e.getMessage());
        }
    }

    /**
     * 根据原图片相对路径获取缩略图路径
     *
     * @param relativePath 原图片相对路径
     * @return 访问路径StorageVo, key如下:
     * <p> absolutePath 绝对路径
     * <p> relativePath 相对路径
     */
    public Result<StorageVo> getThumbImagePath(String relativePath) {
        FdfsStorageInfoDO storageInfo = fdfsStorageInfoService.findByRelativePath(relativePath);
        if (storageInfo == null) {
            logger.error("图片相对路径有误");
            return Result.error("图片相对路径有误");
        }
        StringBuilder buff = new StringBuilder(relativePath);
        int index = buff.lastIndexOf(".");
        buff.insert(index, getPrefixName(storageInfo));
        StorageVo storageVo = getResAccessUrl(StorePath.parseFromUrl(buff.toString()));
        return Result.success(storageVo);
    }

    /**
     * 拼接缩略图后缀
     *
     * @param storageInfo 存储实体
     * @return 缩略图后缀
     */
    private String getPrefixName(FdfsStorageInfoDO storageInfo) {
        if (storageInfo.getThumbImageWidth() > 0 && storageInfo.getThumbImageHeight() > 0) {
            StringBuilder buffer = new StringBuilder();
            buffer.append("_").append(storageInfo.getThumbImageWidth()).append("x")
                .append(storageInfo.getThumbImageHeight());
            return new String(buffer);
        } else if (storageInfo.getThumbImagePercent() > 0.0) {
            StringBuilder buffer = new StringBuilder();
            buffer.append("_").append(new Double(100 * storageInfo.getThumbImagePercent()).intValue()).append("p_");
            return new String(buffer);
        } else {
            return "";
        }
    }

    /**
     * 根据文件路径下载文件
     *
     * @param fileUrl 文件url
     * @return 二进制流
     */
    public byte[] download(String fileUrl) {
        StorePath storePath;
        try {
            storePath = Optional.of(StorePath.parseFromUrl(fileUrl)).orElse(new StorePath());
        } catch (Exception e) {
            logger.error("传入文件路径错误,异常为:" + e.getMessage());
            return null;
        }
        return storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), new DownloadByteArray());
    }

    /**
     * 删除文件
     *
     * @param relativePath 文件访问地址
     */
    public Result deleteFile(String relativePath) {
        if (StringUtils.isEmpty(relativePath)) {
            logger.warn("删除文件,访问地址为空");
            return Result.error("访问地址为空");
        }
        try {
            StorePath storePath = StorePath.parseFromUrl(relativePath);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (FdfsUnsupportStorePathException e) {
            logger.error("删除文件失败,异常为:" + e.getMessage());
            return Result.error("删除文件失败,异常为:" + e.getMessage());
        }
        return Result.success();
    }


    /**
     * 封装文件访问地址
     *
     * @param storePath 存储对象
     * @return 访问路径StorageVo, key如下:
     * <p> absolutePath 绝对路径
     * <p> relativePath 相对路径
     */
    public StorageVo getResAccessUrl(StorePath storePath) {
        if (storePath == null) {
            return null;
        }
        return getResAccessUrl(storePath.getFullPath());
    }

    private StorageVo getResAccessUrl(String relativePath) {
        String absolutePath = StringUtils.isBlank(relativePath) ? null
            : "http://" + fdfsWebServer.getWebServerUrl() + "/" + relativePath;

        return new StorageVo(absolutePath, relativePath);
    }

    /**
     * 保留文件存储信息到数据库
     *
     * @param extName      文件后缀名
     * @param fileSize     文件大小(byte)
     * @param relativePath 存储相对路径
     * @param thumbImage   缩略图配置
     */
    private void addStorageInfo(String extName, long fileSize, String relativePath, ThumbImage thumbImage) {
        FdfsStorageInfoDO fdfsStorageInfoDO = new FdfsStorageInfoDO();
        fdfsStorageInfoDO.setFileSize(fileSize);
        fdfsStorageInfoDO.setFileExtName(extName);
        fdfsStorageInfoDO.setRelativePath(relativePath);
        fdfsStorageInfoDO.setStorageTime(LocalDateTime.now());
        if (thumbImage != null) {
            fdfsStorageInfoDO.setThumbImageWidth(thumbImage.getWidth());
            fdfsStorageInfoDO.setThumbImageHeight(thumbImage.getHeight());
            fdfsStorageInfoDO.setThumbImagePercent(thumbImage.getPercent());
        }
        fdfsStorageInfoService.addOne(fdfsStorageInfoDO);
    }

    /**
     * 截取文件相对存储路径
     */
    private String subResAccessUrl(String fullPath) {
        return StringUtils.substringAfter(fullPath, "http://" + fdfsWebServer.getWebServerUrl() + "/");
    }

    private ThumbImage defaultThumbImage() {
        return new ThumbImage(thumbImageConfig.getWidth(), thumbImageConfig.getWidth());
    }

}
