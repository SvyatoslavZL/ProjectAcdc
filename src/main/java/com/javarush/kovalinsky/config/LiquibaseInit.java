package com.javarush.kovalinsky.config;

import com.javarush.kovalinsky.exception.AppException;
import liquibase.Scope;
import liquibase.command.CommandScope;
import liquibase.resource.ClassLoaderResourceAccessor;

public class LiquibaseInit {
    public static void create(){
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
    }
}






