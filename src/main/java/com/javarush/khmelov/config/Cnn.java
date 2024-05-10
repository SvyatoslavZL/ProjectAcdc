package com.javarush.khmelov.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Cnn {

    public static final String DATABASE_URL_KEY = "database.url";
    public static final String DATABASE_USER_KEY = "database.user";
    public static final String DATABASE_PASSWORD_KEY = "database.password";
    public static final String DATABASE_DRIVER_KEY = "database.driver";

    static {
        try {
            Class.forName(ConfigUtil.getValue(DATABASE_DRIVER_KEY));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection get() {
        try {
            return DriverManager.getConnection(
                    ConfigUtil.getValue(DATABASE_URL_KEY),
                    ConfigUtil.getValue(DATABASE_USER_KEY),
                    ConfigUtil.getValue(DATABASE_PASSWORD_KEY)
            );
        } catch (SQLException e) {
            throw new RuntimeException("failed cooention", e);
        }
    }
}
