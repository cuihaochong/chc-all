package com.chc.util.encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Description: AES算法工具类
 *
 * @author cuihaochong
 * @date 2019/9/4
 */
public class AesUtil {
    private static final String KEY_AES = "AES";

    /**
     * AES加密
     *
     * @param src 待加密字符串
     * @param key 16位密钥
     * @return 加密字符串
     * @throws Exception 异常
     */
    public static String encrypt(String src, String key) throws Exception {
        if (key == null || key.length() != 16) {
            throw new Exception("key不满足条件");
        }
        byte[] raw = key.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_AES);
        Cipher cipher = Cipher.getInstance(KEY_AES);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(src.getBytes());
        return byte2hex(encrypted);
    }

    /**
     * AES解密
     *
     * @param src 待解密字符串
     * @param key 16位密钥
     * @return 解密字符串
     * @throws Exception 异常
     */
    public static String decrypt(String src, String key) throws Exception {
        if (key == null || key.length() != 16) {
            throw new Exception("key不满足条件");
        }
        byte[] raw = key.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_AES);
        Cipher cipher = Cipher.getInstance(KEY_AES);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] encrypted1 = hex2byte(src);
        byte[] original = cipher.doFinal(encrypted1);
        return new String(original);
    }

    /**
     * 十六进制转byte数组
     *
     * @param hexStr 十六进制字符串
     * @return byte数组
     */
    public static byte[] hex2byte(String hexStr) {
        if (hexStr == null) {
            return null;
        }
        int l = hexStr.length();
        if (l % 2 == 1) {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt(hexStr.substring(i * 2, i * 2 + 2),
                16);
        }
        return b;
    }

    /**
     * byte数组转十六进制
     *
     * @param b byte数组
     * @return 十六进制字符串
     */
    public static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String tempStr;
        for (byte value : b) {
            tempStr = (Integer.toHexString(value & 0XFF));
            if (tempStr.length() == 1) {
                hs.append("0").append(tempStr);
            } else {
                hs.append(tempStr);
            }
        }
        return hs.toString().toUpperCase();
    }
}
