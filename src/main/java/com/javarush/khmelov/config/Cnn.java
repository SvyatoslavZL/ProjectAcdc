package com.javarush.khmelov.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Cnn {

    final static ApplicationProperties applicationProperties = NanoSpring.find(ApplicationProperties.class);

    static {
        try {
            Class.forName(applicationProperties.getProperty(ApplicationProperties.HIBERNATE_CONNECTION_DRIVER_CLASS));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection get() {
        try {
            return DriverManager.getConnection(
                    applicationProperties.getProperty(ApplicationProperties.HIBERNATE_CONNECTION_URL),
                    applicationProperties.getProperty(ApplicationProperties.HIBERNATE_CONNECTION_USERNAME),
                    applicationProperties.getProperty(ApplicationProperties.HIBERNATE_CONNECTION_PASSWORD)
            );
        } catch (SQLException e) {
            throw new RuntimeException("failed Connection", e);
        }
    }
}
