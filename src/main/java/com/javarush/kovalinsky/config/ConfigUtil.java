package com.javarush.kovalinsky.config;

import java.io.IOException;
import java.util.Properties;

public class ConfigUtil {

    private static Properties properties = new Properties();

    private ConfigUtil() {
    }

    static {
        try {
            properties.load(ConfigUtil.class.getResourceAsStream("/application.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getValue(String key) {
        return properties.getProperty(key);
    }
}
