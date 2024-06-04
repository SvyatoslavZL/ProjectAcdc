package com.javarush.khmelov.config;

import com.javarush.khmelov.entity.*;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class SessionCreator {

    private final SessionFactory sessionFactory;
    private final ThreadLocal<AtomicInteger> levelBox = new ThreadLocal<>();
    private final ThreadLocal<Session> sessionBox = new ThreadLocal<>();

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
        return sessionBox.get() == null || !sessionBox.get().isOpen()
                ? sessionFactory.openSession()
                : sessionBox.get();
    }

    public void beginTransactional() {
        if (levelBox.get() == null) {
            levelBox.set(new AtomicInteger(0));
        }
        AtomicInteger level = levelBox.get();
        if (level.getAndIncrement() == 0) {
            Session session = getSession();
            sessionBox.set(session);
            session.beginTransaction();
        }
        log(level.get(), "begin level: ");
    }


    public void endTransactional() {
        AtomicInteger level = levelBox.get();
        Session session = sessionBox.get();
        log(level.get(), "end level: ");
        if (level.decrementAndGet() == 0) {
            try {
                session.getTransaction().commit();
            } catch (RuntimeException e) {
                session.getTransaction().rollback();
                throw e;
            }
        }
    }

    private void log(int level, String message) {
        String simpleName = Thread.currentThread().getStackTrace()[4].toString();
        System.out.println("\t".repeat(level) + message + level+" from "+simpleName);
        System.out.flush();
    }

    public void close() {
        sessionFactory.close();
    }

}
