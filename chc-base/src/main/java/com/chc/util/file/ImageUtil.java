package com.chc.util.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;

/**
 * Description: 图片工具类
 *
 * @author cuihaochong
 * @date 2019/9/5
 */
public class ImageUtil {
    private static Logger log = LoggerFactory.getLogger(ImageUtil.class);

    private static String DEFAULT_PREVFIX = "thumb_";
    private static Boolean DEFAULT_FORCE = false;//建议该值为false

    /**
     * 生成缩略图
     *
     * @param imagePath 图片路径
     * @param w         缩略图宽度
     * @param h         缩略图高度
     * @param prevfix   缩略图前缀
     * @param force     是否强制按照宽高生成缩略图(如果为false，则生成最佳比例缩略图)
     * @return 缩略图路径
     */
    public static String thumbnailImage(String imagePath, int w, int h, String prevfix, boolean force) {
        File imgFile = new File(imagePath);
        String newPath = "";
        if (imgFile.exists()) {
            try {
                // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
                String types = Arrays.toString(ImageIO.getReaderFormatNames());
                String suffix = null;
                // 获取图片后缀
                if (imgFile.getName().contains(".")) {// 飒飒大
                    suffix = imgFile.getName().substring(imgFile.getName().lastIndexOf(".") + 1);
                }// 类型和图片后缀全部小写，然后判断后缀是否合法
                if (suffix == null || !types.toLowerCase().contains(suffix.toLowerCase())) {
                    log.error("Sorry, the image suffix is illegal. the standard image suffix is {}." + types);
                    return "";
                }
                log.debug("target image's size, width:{}, height:{}.", w, h);
                Image img = ImageIO.read(imgFile);
                if (!force) {
                    // 根据原图与要求的缩略图比例，找到最合适的缩略图比例
                    int width = img.getWidth(null);
                    int height = img.getHeight(null);
                    if ((width * 1.0) / w < (height * 1.0) / h) {
                        if (width > w) {
                            h = Integer.parseInt(new java.text.DecimalFormat("0").format(height * w / (width * 1.0)));
                            log.debug("change image's height, width:{}, height:{}.", w, h);
                        }
                    } else {
                        if (height > h) {
                            w = Integer.parseInt(new java.text.DecimalFormat("0").format(width * h / (height * 1.0)));
                            log.debug("change image's width, width:{}, height:{}.", w, h);
                        }
                    }
                }
                BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics g = bi.getGraphics();
                g.drawImage(img, 0, 0, w, h, Color.LIGHT_GRAY, null);
                g.dispose();
                String p = imgFile.getPath();
                newPath = p.substring(0, p.lastIndexOf(File.separator)) + File.separator + prevfix + imgFile.getName();
                // 将图片保存在原目录并加上前缀
                ImageIO.write(bi, suffix, new File(newPath));
                log.debug("缩略图在原路径下生成成功");
            } catch (IOException e) {
                log.error("generate thumbnail image failed.", e);
            }
        } else {
            log.warn("the image is not exist.");
        }

        return newPath;
    }


    /**
     * 指定图片宽度和高度和压缩比例对图片进行压缩
     *
     * @param imgsrc     源图片地址
     * @param imgdist    目标图片地址
     * @param widthdist  压缩后图片的宽度
     * @param heightdist 压缩后图片的高度
     * @param rate       压缩的比例
     * @return 压缩后图片路径
     */
    public static String reduceImg(String imgsrc, String imgdist, int widthdist, int heightdist, Float rate) throws Exception {

        String outFile = "";

        File srcfile = new File(imgsrc);

        String fileName = srcfile.getName();
        // 检查图片文件是否存在
        if (!srcfile.exists()) {
            throw new Exception("文件不存在");
        }
        String types = Arrays.toString(ImageIO.getReaderFormatNames());
        String suffix = null;
        // 获取图片后缀
        if (fileName.contains(".")) {
            suffix = srcfile.getName().substring(fileName.lastIndexOf(".") + 1);
        }// 类型和图片后缀全部小写，然后判断后缀是否合法
        if (suffix == null || !types.toLowerCase().contains(suffix.toLowerCase())) {
            log.error("Sorry, the image suffix is illegal. the standard image suffix is {}." + types);
        }

        // 如果比例不为空则说明是按比例压缩
        if (rate != null && rate > 0) {
            //获得源图片的宽高存入数组中
//                int[] results = getImgWidthHeight(srcfile);
            int height = getImgHeight(srcfile);
            int width = getImgWidth(srcfile);
            if (height == 0 || width == 0) {
                throw new Exception("输入参数错误");
            } else {
                //按比例缩放或扩大图片大小，将浮点型转为整型
                widthdist = (int) (width * rate);
                heightdist = (int) (height * rate);
            }
        }
        // 开始读取文件并进行压缩
        Image src = ImageIO.read(srcfile);

        // 构造一个类型为预定义图像类型之一的 BufferedImage
        BufferedImage tag = new BufferedImage((int) widthdist, (int) heightdist, BufferedImage.TYPE_INT_RGB);

        //绘制图像  getScaledInstance表示创建此图像的缩放版本，返回一个新的缩放版本Image,按指定的width,height呈现图像
        //Image.SCALE_SMOOTH,选择图像平滑度比缩放速度具有更高优先级的图像缩放算法。
        Graphics g = tag.getGraphics();
        g.drawImage(src.getScaledInstance(widthdist, heightdist, Image.SCALE_SMOOTH), 0, 0, null);
        g.dispose();

        if (!imgdist.endsWith(File.separator)) {
            imgdist += File.separator;
        }
        outFile = imgdist + "compress_" + fileName;
        ImageIO.write(tag, suffix, new File(outFile));

        return imgdist + "compress_" + fileName;
    }

    /**
     * 指定压缩比例对图片进行压缩
     *
     * @param imgsrc  源图片地址
     * @param imgdist 目标图片地址
     * @param rate    压缩的比例
     * @return 压缩后的图片
     */
    public static String reduceImg(String imgsrc, String imgdist, Float rate) throws Exception {
        return reduceImg(imgsrc, imgdist, 0, 0, rate);
    }

    /**
     * 指定图片宽度和高度图片进行压缩
     *
     * @param imgsrc     源图片地址
     * @param imgdist    目标图片地址
     * @param widthdist  压缩后图片的宽度
     * @param heightdist 压缩后图片的高度
     * @return 压缩后的图片地址
     */
    public static String reduceImg(String imgsrc, String imgdist, int widthdist, int heightdist) throws Exception {
        return reduceImg(imgsrc, imgdist, widthdist, heightdist, 0f);
    }

    /**
     * 获取图片宽度
     *
     * @param file 图片文件
     * @return 图片宽度
     * @author WTW 2018年6月22日
     */
    public static int getImgWidth(MultipartFile file) {
        BufferedImage src = null;
        int ret = -1;
        try {
            src = ImageIO.read(file.getInputStream());
            ret = src.getWidth(); // 得到源图宽
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }


    /**
     * 获取图片宽度
     *
     * @param file 图片文件
     * @return 图片宽度
     * @author WTW 2018年6月22日
     */
    public static int getImgWidth(File file) {
        BufferedImage src = null;
        int ret = -1;
        try {
            src = ImageIO.read(new FileInputStream(file));
            ret = src.getWidth(); // 得到源图宽
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 获取图片高度
     *
     * @param file 图片文件
     * @return 图片高度
     * @author WTW 2018年6月22日
     */
    public static int getImgHeight(MultipartFile file) {
        BufferedImage src = null;
        int ret = -1;
        try {
            src = ImageIO.read(file.getInputStream());
            ret = src.getHeight(); // 得到源图高
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 获取图片高度
     *
     * @param file 图片文件
     * @return 图片高度
     * @author WTW 2018年6月22日
     */
    public static int getImgHeight(File file) {
        BufferedImage src = null;
        int ret = -1;
        try {
            src = ImageIO.read(new FileInputStream(file));
            ret = src.getHeight(); // 得到源图高
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 生成缩略图
     *
     * @param imagePath 图片路径
     * @param w         缩略图宽度
     * @param h         缩略图高度
     */
    public static String thumbnailImage(String imagePath, int w, int h) {
        return thumbnailImage(imagePath, w, h, DEFAULT_PREVFIX, DEFAULT_FORCE);
    }

}
