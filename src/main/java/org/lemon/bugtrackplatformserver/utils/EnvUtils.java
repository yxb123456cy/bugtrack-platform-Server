package org.lemon.bugtrackplatformserver.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 环境变量工具类 - 支持读取 .env、系统环境变量、Spring 环境变量
 * 用于从 resources/env/.env 文件中安全加载环境变量
 */
public class EnvUtils {
    /** 环境变量缓存 */
    private static final Map<String, String> ENV_MAP;

    /** 环境文件目录 */
    private static final String ENV_DIR = "env/";

    /** 默认环境变量键名 */
    private static final String ENV_KEY = "APP_ENV";

    /** 禁止实例化 */
    private EnvUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    // ==============================
    // 🔹 初始化加载
    // ==============================
    static {
        Map<String, String> map = new HashMap<>();

        // 1️⃣ 加载基础 .env
        loadEnvFile(map, ENV_DIR + ".env");

        // 2️⃣ 确定当前环境
        String activeEnv = System.getenv(ENV_KEY);
        if (activeEnv == null || activeEnv.isBlank()) {
            activeEnv = map.getOrDefault(ENV_KEY, "dev");
        }

        // 3️⃣ 尝试加载环境专属文件
        String envFileName = String.format("%s.env.%s", ENV_DIR, activeEnv);
        loadEnvFile(map, envFileName);

        ENV_MAP = Collections.unmodifiableMap(map);
    }

    /**
     * 从资源文件中加载环境变量
     *
     * @param targetMap 存储目标 map
     * @param path 资源路径
     */
    private static void loadEnvFile(Map<String, String> targetMap, String path) {
        try (InputStream input = EnvUtils.class.getClassLoader().getResourceAsStream(path)) {
            if (input == null) {
                return;
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#")) continue;
                    int idx = line.indexOf('=');
                    if (idx > 0) {
                        String key = line.substring(0, idx).trim();
                        String value = line.substring(idx + 1).trim();
                        targetMap.put(key, value);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("[EnvUtils] ⚠️ 加载 " + path + " 失败：" + e.getMessage());
        }
    }

    // ==============================
    // 🔹 公共访问方法
    // ==============================

    /** 获取字符串值 */
    public static String get(String key, String defaultValue) {
        return Optional.ofNullable(ENV_MAP.get(key)).orElse(defaultValue);
    }

    public static String get(String key) {
        return get(key, null);
    }

    /** 获取整数值 */
    public static int getInt(String key, int defaultValue) {
        try {
            String val = get(key);
            return val != null ? Integer.parseInt(val) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /** 获取布尔值 */
    public static boolean getBool(String key, boolean defaultValue) {
        String val = get(key);
        if (val == null) return defaultValue;
        return "true".equalsIgnoreCase(val)
                || "1".equals(val)
                || "yes".equalsIgnoreCase(val)
                || "on".equalsIgnoreCase(val);
    }

    /** 获取当前环境名 */
    public static String getActiveEnv() {
        return get(ENV_KEY, "dev");
    }

    /** 获取所有配置 */
    public static Map<String, String> getAll() {
        return ENV_MAP;
    }

    /** 打印当前环境配置（仅调试使用） */
    public static void printAll() {
        System.out.println("========== Loaded Environment ==========");
        ENV_MAP.forEach((k, v) -> System.out.printf("%s = %s%n", k, v));
        System.out.println("========================================");
    }

    public static void main(String[] args) {
        printAll();
    }
}
