package com.oneself.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author liuhuan
 * date 2024/12/30
 * packageName com.oneself.utils
 * className EncryptionUtils
 * description 加解密工具类
 * version 1.0
 */
@Slf4j
public class EncryptionUtils {

    /**
     * 私有构造器，防止被实例化
     */
    private EncryptionUtils() {
        throw new AssertionError("此工具类不允许实例化");
    }

    /**
     * 算法
     */
    public static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    /**
     * 固定密钥
     */
    private static final String SECURITY_KEY = "X1anrenfuw0d1ng_";

    /**
     * 动态生成随机IV
     *
     * @return 随机生成的16字节IV
     */
    private static byte[] generateRandomIV() {
        byte[] iv = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        return iv;
    }

    /**
     * 通用加密逻辑，支持随机IV
     *
     * @param content 待加密内容
     * @param sKey    密钥
     * @return 包含IV的加密结果（Base64编码）
     */
    @SuppressWarnings("java:S3329")
    public static String encrypt(String content, String sKey) {
        try {
            // 生成随机IV
            byte[] ivBytes = generateRandomIV();
            // 通过 generateRandomIV 方法生成随机IV
            IvParameterSpec iv = new IvParameterSpec(ivBytes);

            // 加密
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec skeySpec = new SecretKeySpec(sKey.getBytes(StandardCharsets.US_ASCII), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encryptedBytes = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));

            // 将IV和密文拼接并Base64编码
            byte[] result = new byte[ivBytes.length + encryptedBytes.length];
            System.arraycopy(ivBytes, 0, result, 0, ivBytes.length);
            System.arraycopy(encryptedBytes, 0, result, ivBytes.length, encryptedBytes.length);
            return Base64.getEncoder().encodeToString(result);
        } catch (Exception e) {
            log.error("加密时出错", e);
            return content;
        }
    }

    /**
     * 默认使用固定密钥加密
     *
     * @param content 待加密内容
     * @return 包含IV的加密结果（Base64编码）
     */
    public static String encrypt(String content) {
        return encrypt(content, SECURITY_KEY);
    }

    /**
     * 通用解密逻辑，支持动态IV
     *
     * @param encryptedContent 包含IV的加密内容（Base64编码）
     * @param sKey             密钥
     * @return 解密后的内容
     */
    @SuppressWarnings("java:S3329")
    public static String decrypt(String encryptedContent, String sKey) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedContent);

            // 解密逻辑 ，提取加密时随机生成的 IV（前 16 字节）
            byte[] ivBytes = new byte[16];
            System.arraycopy(decodedBytes, 0, ivBytes, 0, 16);
            IvParameterSpec iv = new IvParameterSpec(ivBytes);

            // 提取密文
            byte[] encryptedBytes = new byte[decodedBytes.length - 16];
            System.arraycopy(decodedBytes, 16, encryptedBytes, 0, encryptedBytes.length);

            // 解密
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec skeySpec = new SecretKeySpec(sKey.getBytes(StandardCharsets.US_ASCII), "AES");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] originalBytes = cipher.doFinal(encryptedBytes);
            return new String(originalBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("解密时出错", e);
            return encryptedContent;
        }
    }

    /**
     * 默认使用固定密钥解密
     *
     * @param encryptedContent 包含IV的加密内容（Base64编码）
     * @return 解密后的内容
     */
    public static String decrypt(String encryptedContent) {
        return decrypt(encryptedContent, SECURITY_KEY);
    }

}