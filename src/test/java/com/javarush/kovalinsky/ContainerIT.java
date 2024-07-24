package com.javarush.kovalinsky;

import com.javarush.kovalinsky.config.ApplicationProperties;
import com.javarush.kovalinsky.config.Configurator;
import com.javarush.kovalinsky.config.NanoSpring;
import com.javarush.kovalinsky.config.SessionCreator;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.hibernate.cfg.JdbcSettings.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ContainerIT {

    public static final JdbcDatabaseContainer<?> CONTAINER;

    public static final String DOCKER_IMAGE_NAME = "postgres:16.3";

    static {
        //create
        CONTAINER = new PostgreSQLContainer<>(DOCKER_IMAGE_NAME);
        CONTAINER.start();
        //set new properties from TestContainers
        ApplicationProperties properties = NanoSpring.find(ApplicationProperties.class);
        properties.setProperty(JAKARTA_JDBC_URL, CONTAINER.getJdbcUrl());
        properties.setProperty(JAKARTA_JDBC_USER, CONTAINER.getUsername());
        properties.setProperty(JAKARTA_JDBC_PASSWORD, CONTAINER.getPassword());
        //fill db /todo delete and use liquibase instead of it
        Configurator configurator = NanoSpring.find(Configurator.class);
        configurator.fillStartData();
    }

    public ContainerIT() {
        init();
    }

    public static void init() {
        System.out.println("init started");
    }

    @Test
    void sessionCreatorTest() {
        SessionCreator sessionCreator = NanoSpring.find(SessionCreator.class);
        assertNotNull(sessionCreator);
    }
}
