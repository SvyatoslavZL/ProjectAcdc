package com.javarush.kovalinsky.config;

import lombok.SneakyThrows;
import org.hibernate.cfg.JdbcSettings;

import java.io.FileReader;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

public class ApplicationProperties extends Properties {

    public static final Path CLASSES_ROOT = Paths.get(URI.create(
            Objects.requireNonNull(
                    ApplicationProperties.class.getResource("/")
            ).toString()
    ));

    //only in Tomcat (not use in tests)
    public static final Path WEB_INF = CLASSES_ROOT.getParent();

    @SneakyThrows
    public ApplicationProperties() {
        this.load(new FileReader(CLASSES_ROOT + "/application.properties"));
        try {
            String driver = this.getProperty(JdbcSettings.JAKARTA_JDBC_DRIVER);
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
