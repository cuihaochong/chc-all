package com.chc.util.encrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Description: MD5算法工具类
 *
 * @author cuihaochong
 * @date 2019/9/4
 */
public class Md5Util {

    private static Logger logger = LoggerFactory.getLogger(Md5Util.class);

    /**
     * 计算32位md5值
     *
     * @param str 需要加密的字符串
     * @return md5值
     */
    public static String md5(String str) {
        return md5(str, false, 32);
    }

    /**
     * 计算md5值
     *
     * @param str     需要加密的字符串
     * @param isUpper 字母大小写(false为默认小写，true为大写)
     * @param bit     加密的位数（16,32,64）
     * @return md5值
     */
    public static String md5(String str, boolean isUpper, Integer bit) {
        String md5 = "";
        bit = bit == null ? 32 : bit;
        try {
            // 创建加密对象
            MessageDigest md = MessageDigest.getInstance("md5");
            if (bit == 64) {
                md5 = Base64Util.encodeToString(md.digest(str.getBytes(StandardCharsets.UTF_8)));
            } else {
                // 计算MD5函数
                md.update(str.getBytes());
                byte[] b = md.digest();
                int i;
                StringBuilder sb = new StringBuilder();
                for (byte value : b) {
                    i = value;
                    if (i < 0) {
                        i += 256;
                    }
                    if (i < 16) {
                        sb.append("0");
                    }
                    sb.append(Integer.toHexString(i));
                }
                //得到32为md5值
                md5 = sb.toString();
                if (bit == 16) {
                    //截取32位md5为16位
                    md5 = md5.substring(8, 24);
                }
            }
            //转换成大写
            if (isUpper) {
                md5 = md5.toUpperCase();
            }
        } catch (Exception e) {
            logger.error("md5加密抛出异常:{}", e.getMessage());
        }
        return md5;
    }


    /**
     * 加密解密算法 执行一次加密，两次解密
     */
    public static String convertMd5(String str) {
        char[] a = str.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        return new String(a);
    }
}
