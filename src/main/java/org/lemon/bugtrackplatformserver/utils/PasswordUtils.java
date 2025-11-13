package org.lemon.bugtrackplatformserver.utils;

import org.springframework.util.DigestUtils;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * 密码工具类
 */
public class PasswordUtils {
    
    private static final String SALT_PREFIX = "bugtrackplatformserver";
    private static final int SALT_LENGTH = 16;
    
    /**
     * 禁止实例化
     */
    private PasswordUtils() {
        throw new UnsupportedOperationException("Utility class");
    }
    
    /**
     * 生成盐值
     * 
     * @return 盐值
     */
    private static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
    
    /**
     * 加密密码
     * 
     * @param rawPassword 原始密码
     * @return 加密后的密码（包含盐值）
     */
    public static String encode(String rawPassword) {
        String salt = generateSalt();
        String saltedPassword = SALT_PREFIX + salt + rawPassword;
        String hashedPassword = DigestUtils.md5DigestAsHex(saltedPassword.getBytes());
        return salt + "$" + hashedPassword;
    }
    
    /**
     * 验证密码
     * 
     * @param rawPassword 原始密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        if (encodedPassword == null || !encodedPassword.contains("$")) {
            return false;
        }
        
        String[] parts = encodedPassword.split("\\$", 2);
        String salt = parts[0];
        String hashedPassword = parts[1];
        
        String saltedPassword = SALT_PREFIX + salt + rawPassword;
        String newHashedPassword = DigestUtils.md5DigestAsHex(saltedPassword.getBytes());
        
        return hashedPassword.equals(newHashedPassword);
    }
}