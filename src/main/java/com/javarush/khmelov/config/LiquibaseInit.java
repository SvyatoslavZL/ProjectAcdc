package com.javarush.khmelov.config;

import com.javarush.khmelov.exception.AppException;
import liquibase.Scope;
import liquibase.command.CommandScope;
import liquibase.resource.ClassLoaderResourceAccessor;

public class LiquibaseInit {
    public static void create() {
        System.out.println("Running Liquibase...");

        try {
            Scope.child(Scope.Attr.resourceAccessor, new ClassLoaderResourceAccessor(), () -> {
                CommandScope update = new CommandScope("update");

                update.addArgumentValue("changelogFile", "db/changelog.xml");
                update.addArgumentValue("url", "jdbc:postgresql://localhost:5432/game");
                update.addArgumentValue("username", "postgres");
                update.addArgumentValue("password", "postgres");

                update.execute();
            });
        } catch (Exception e) {
            throw new AppException(e);
        }

        System.out.println("Running Liquibase...DONE");
    }
}
