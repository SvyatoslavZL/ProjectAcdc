package com.javarush.kovalinsky.config;

import liquibase.Scope;
import liquibase.command.CommandScope;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static org.hibernate.cfg.JdbcSettings.*;

@AllArgsConstructor
@Slf4j
public class DataBaseValidator {

    private final ApplicationProperties properties;

    public static final String DB_CHANGELOG_XML = "db/changelog.xml";

    public void start() {
        log.info("Liquibase running...");
        try {
            Scope.child(Scope.Attr.resourceAccessor, new ClassLoaderResourceAccessor(), () -> {
                CommandScope update = new CommandScope("update");
                update.addArgumentValue("changelogFile", DB_CHANGELOG_XML);
                String url = properties.getProperty(JAKARTA_JDBC_URL);
                update.addArgumentValue("url", url);
                String username = properties.getProperty(JAKARTA_JDBC_USER);
                update.addArgumentValue("username", username);
                String password = properties.getProperty(JAKARTA_JDBC_PASSWORD);
                update.addArgumentValue("password", password);
                update.execute();
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.info("Liquibase running...DONE");
    }
}






