package com.chc.util.encrypt;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;
import java.security.SecureRandom;

/**
 * Description: DES算法工具类
 *
 * @author cuihaochong
 * @date 2019/9/4
 */
public class DesUtil {

    /**
     * 密钥算法
     */
    private static final String KEY_ALGORITHM = "DES";
    /**
     * 加密/解密算法 / 工作模式 / 填充方式
     */
    private static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5PADDING";
    /**
     * 安全密钥
     */
    private String keyData = "ABCDEFGHIJKLMNOPQRSTWXYZabcdefghijklmnopqrstwxyz0123456789-_.";

    /**
     * 功能：构造
     */
    public DesUtil() {
    }

    /**
     * 功能：构造
     *
     * @param key key
     */
    public DesUtil(String key) {
        this.keyData = key;
    }

    /**
     * 转换密钥
     *
     * @param key 二进制密钥
     * @return Key 密钥
     * @throws Exception 异常
     */
    private static Key toKey(byte[] key) throws Exception {
        // 实例化DES密钥材料
        DESKeySpec dks = new DESKeySpec(key);
        // 实例化秘密密钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        // 生成秘密密钥
        return keyFactory.generateSecret(dks);
    }

    /**
     * 加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return byte[] 加密数据
     * @throws Exception 异常
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 还原密钥
        Key k = toKey(key);
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 实例化
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        // 初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, k, sr);
        // 执行操作
        return cipher.doFinal(data);
    }

    /**
     * 解密
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return byte[] 解密数据
     * @throws Exception 异常
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 还原密钥
        Key k = toKey(key);
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 实例化
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        // 初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k, sr);
        // 执行操作
        return cipher.doFinal(data);
    }

    /**
     * 生成密钥
     *
     * @return byte[] 二进制密钥
     * @throws Exception 异常
     */
    public static byte[] initKey() throws Exception {
        // 实例化密钥生成器
        KeyGenerator kg = KeyGenerator.getInstance(CIPHER_ALGORITHM, "BC");
        // 初始化密钥生成器
        kg.init(64, new SecureRandom());
        // 生成秘密密钥
        SecretKey secretKey = kg.generateKey();
        // 获得密钥的二进制编码形式
        return secretKey.getEncoded();
    }

}
