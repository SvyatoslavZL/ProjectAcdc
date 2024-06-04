package com.javarush.khmelov.config;

import com.javarush.khmelov.entity.*;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.Closeable;
import java.io.IOException;

public class SessionCreator implements Closeable {

    private final SessionFactory sessionFactory;

    @SneakyThrows
    public SessionCreator(ApplicationProperties applicationProperties) {
        Configuration configuration = new Configuration();        //1. hibernate.properties
        // configuration.configure();                             //2. hibernate.cfg.xml
        // Properties properties = configuration.getProperties(); //3.1 prepare for read
        // properties.load(SessionFactory.class.getResourceAsStream("/application.properties")); //3.2 your
        // configuration.addProperties(properties);               //3.3 application.properties
        // configuration.add????Resource()                        //and 100500 other ways
        configuration.addProperties(applicationProperties); //use 3.3 - my ApplicationProperties
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(UserInfo.class);
        configuration.addAnnotatedClass(Quest.class);
        configuration.addAnnotatedClass(Question.class);
        configuration.addAnnotatedClass(Answer.class);
        configuration.addAnnotatedClass(Game.class);
        sessionFactory = configuration.buildSessionFactory();
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }


    @Override
    public void close() throws IOException {
        sessionFactory.close();
    }

}
