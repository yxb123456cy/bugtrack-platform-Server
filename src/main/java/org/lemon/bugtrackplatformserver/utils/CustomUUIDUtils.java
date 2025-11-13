package org.lemon.bugtrackplatformserver.utils;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

/**
 * 自定义 UUID 生成工具类
 */
public class CustomUUIDUtils {
    /** 安全随机数生成器，线程安全 */
    private static final SecureRandom RANDOM = new SecureRandom();

    /** 禁止实例化 */
    private CustomUUIDUtils() {
        throw new UnsupportedOperationException("Utility class");
    }
    /**
     * 生成标准 UUID（包含横线）
     *
     * @return 形如：550e8400-e29b-41d4-a716-446655440000
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 生成无横线 UUID（32 位）
     *
     * @return 形如：550e8400e29b41d4a716446655440000
     */
    public static String simpleUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    // ==============================
    // 🔹 时间戳 + 随机数 混合短 UUID
    // ==============================

    /**
     * 生成基于时间戳的短 UUID（22~26 位）
     *
     * @return 形如：kxYgtsn-6uEeNjg6e0mN5A
     */
    public static String shortUUID() {
        long timestamp = System.currentTimeMillis();
        long randomLong = RANDOM.nextLong();
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.putLong(timestamp);
        buffer.putLong(randomLong);
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(buffer.array());
    }

    // ==============================
    // 🔹 Base64 编码 UUID
    // ==============================

    /**
     * 生成 Base64 编码的 UUID（长度 22）
     *
     * @return 形如：N94EJtnRQxO6Oa2Z1o42Zw
     */
    public static String base64UUID() {
        UUID uuid = UUID.randomUUID();
        ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
        buffer.putLong(uuid.getMostSignificantBits());
        buffer.putLong(uuid.getLeastSignificantBits());
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(buffer.array());
    }

    // ==============================
    // 🔹 自定义长度随机字符串
    // ==============================

    private static final char[] ALPHANUM = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

    /**
     * 生成指定长度的随机字符串（仅字母数字）
     *
     * @param length 长度，建议 ≥ 8
     * @return 随机字符串，如：A7c9XzP2
     */
    public static String randomString(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("length must be greater than 0");
        }
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALPHANUM[RANDOM.nextInt(ALPHANUM.length)]);
        }
        return sb.toString();
    }

    // ==============================
    // 🔹 雪花算法 UUID（Snowflake）
    // ==============================

    /**
     * 使用简化版雪花算法生成 64 位唯一 ID
     *
     * @param workerId 机器 ID（0~31）
     * @param datacenterId 数据中心 ID（0~31）
     * @return 唯一 ID
     */
    public static synchronized long snowflakeId(long workerId, long datacenterId) {
        long timestamp = System.currentTimeMillis() & 0x1FFFFFFFFFFL; // 保留 41 位
        return (timestamp << 22) | ((datacenterId & 31) << 17) | ((workerId & 31) << 12) | (RANDOM.nextInt(4096));
    }
}
