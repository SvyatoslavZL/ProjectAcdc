package com.javarush.lesson07;

import java.io.IOException;
import java.util.Properties;

public class ConfigUtil {

    private static final Properties properties = new Properties();

    private ConfigUtil() {
    }

    static {
        try {
            var config = ConfigUtil.class.getResourceAsStream("/application.properties");
            properties.load(config);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getValue(String key) {
        return properties.getProperty(key);
    }
}
