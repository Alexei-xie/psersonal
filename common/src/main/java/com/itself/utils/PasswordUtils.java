package com.itself.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtils {
    // 定义盐的长度
    private static final int SALT_LENGTH = 16;

    /**
     * 对密码进行盐加密
     *
     * @param password 明文密码
     * @return 盐加密后的密码
     */
    public static String encryptPassword(String password) {
        String salt = generateSalt(); // 生成盐
        String saltedPassword = salt + password; // 将盐和密码组合
        String hashedPassword = hashPassword(saltedPassword); // 对盐和密码组合后进行哈希
        return salt + hashedPassword; // 返回盐和哈希后的密码组合
    }
    public static String encryptPasswordWithSalt(String password,String salt) {
        String saltedPassword = salt + password; // 将盐和密码组合
        String hashedPassword = hashPassword(saltedPassword); // 对盐和密码组合后进行哈希
        return salt + hashedPassword; // 返回盐和哈希后的密码组合
    }

    /**
     * 生成随机盐
     *
     * @return 随机生成的盐
     */
    private static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[SALT_LENGTH];
        random.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }

    /**
     * 对盐和密码组合后进行哈希
     *
     * @param saltedPassword 盐和密码组合后的字符串
     * @return 哈希后的字符串
     */
    private static String hashPassword(String saltedPassword) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = messageDigest.digest(saltedPassword.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing password failed", e);
        }
    }

    /**
     * 验证密码是否正确
     *
     * @param password      明文密码
     * @param hashedPwdWithSalt 盐加密后的密码
     * @return true 如果密码正确，否则返回 false
     */
    public static boolean verifyPassword(String password, String hashedPwdWithSalt) {
        String salt = hashedPwdWithSalt.substring(0, SALT_LENGTH); // 获取盐
        String saltedPassword = salt + password; // 将盐和密码组合
        String hashedPassword = hashPassword(saltedPassword); // 对盐和密码组合后进行哈希
        String hashedPwdWithSaltNew = salt + hashedPassword; // 将新生成的哈希后的密码和盐组合
        return hashedPwdWithSaltNew.equals(hashedPwdWithSalt); // 比较两个字符串是否相等
    }
}