package com.javarush.khmelov;

import com.javarush.khmelov.config.ApplicationProperties;
import com.javarush.khmelov.config.Configurator;
import com.javarush.khmelov.config.NanoSpring;
import com.javarush.khmelov.config.SessionCreator;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import static com.javarush.khmelov.config.ApplicationProperties.*;
import static org.hibernate.cfg.JdbcSettings.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ContainerIT {

    private final static JdbcDatabaseContainer<?> CONTAINER;

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
        //fill db
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
    void testSessionCreator() {
        SessionCreator sessionCreator = NanoSpring.find(SessionCreator.class);
        assertNotNull(sessionCreator);
    }

}
