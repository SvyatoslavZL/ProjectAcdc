package com.javarush.lesson07;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Cnn {

    public static final String DATABASE_URL_KEY = "database.url";
    public static final String DATABASE_USER_KEY = "database.user";
    public static final String DATABASE_PASSWORD_KEY = "database.password";

    static {
        try {
            Class.forName("org.postgresql.Driver");
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
