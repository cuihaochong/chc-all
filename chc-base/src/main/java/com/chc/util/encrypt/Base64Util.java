package com.chc.util.encrypt;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Description: 基于Jdk8实现的Base64工具类
 *
 * @author cuihaochong
 * @date 2019/7/30
 */
public class Base64Util {

    /**
     * Base64加密
     *
     * @param encoderStr 待加密字符串
     * @return 加密字符串
     * @author cuihaochong
     * @version 1.0 初始版本
     */
    public static String encodeToString(String encoderStr) {
        return encodeToString(encoderStr.getBytes(StandardCharsets.UTF_8));
    }

    public static String encodeToString(String encoderStr, int num) {
        String s = encodeToString(encoderStr.getBytes(StandardCharsets.UTF_8));
        if (num > 1) {
            s = encodeToString(s, num - 1);
        }
        return s;
    }

    /**
     * Base64加密
     *
     * @param encoderByte 待加密byte数组
     * @return 加密字符串
     */
    public static String encodeToString(byte[] encoderByte) {
        return Base64.getEncoder().encodeToString(encoderByte);
    }

    /**
     * Base64解密
     *
     * @param decoderStr 待解密字符串
     * @return 解密字符串
     */
    public static String decodeToString(String decoderStr) {
        return new String(decodeToByte(decoderStr), StandardCharsets.UTF_8);
    }

    public static String decodeToString(String decoderStr, int num) {
        String s = new String(decodeToByte(decoderStr), StandardCharsets.UTF_8);
        if (num > 1) {
            s = decodeToString(s, num - 1);
        }
        return s;
    }

    /**
     * Base64解密
     *
     * @param decoderStr 待解密字符串
     * @return 解密byte数组
     */
    public static byte[] decodeToByte(String decoderStr) {
        return Base64.getDecoder().decode(decoderStr);
    }

}
