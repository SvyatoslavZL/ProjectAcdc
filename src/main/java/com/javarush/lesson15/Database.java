package com.javarush.lesson15;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

enum Database {

    SESSION;

    private Session session;

    private final SessionFactory sessionFactory;

    Session open() {
        session = sessionFactory.openSession();
        return session;
    }

    void close() {
        session.close();
    }

    void closeFactory() {
        sessionFactory.close();
    }

    Database() {
        LogManager.getLogManager()
                .getLogger("")
                .setLevel(Level.WARNING);

        Configuration configure = new Configuration()
                .setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/game")
                .setProperty("hibernate.connection.username", "postgres")
                .setProperty("hibernate.connection.password", "postgres")
                .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.format_sql", "true")
                .setProperty("hibernate.hbm2ddl.auto", "create");
        //.configure(); //skip hibernate.cfg.xml
        //configure.addAnnotatedClass(????.class);
        configure.addAnnotatedClass(BaseParent.class);
        configure.addAnnotatedClass(Customer.class);
        configure.addAnnotatedClass(Seller.class);
        sessionFactory = configure.buildSessionFactory();
    }
}
