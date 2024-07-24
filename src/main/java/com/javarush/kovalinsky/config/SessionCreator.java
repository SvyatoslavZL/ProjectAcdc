package com.javarush.kovalinsky.config;

import com.javarush.kovalinsky.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class SessionCreator implements AutoCloseable {

    private final SessionFactory sessionFactory;
    private final ThreadLocal<AtomicInteger> levelBox = new ThreadLocal<>();
    private final ThreadLocal<Session> sessionBox = new ThreadLocal<>();

    public SessionCreator(ApplicationProperties applicationProperties) {
        Configuration configuration = new Configuration();
        configuration.addProperties(applicationProperties);
        configuration.addAnnotatedClass(User.class);
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
        log.info(">>> start level: {} session={}", level.get(), sessionBox.get());
    }

    public void endTransactional() {
        AtomicInteger level = levelBox.get();
        Session session = sessionBox.get();
        log.info("\t\tcheck tx: {} session={}", level.get(), session);

        if (level.decrementAndGet() == 0) {
            sessionBox.remove();
            try (session) {
                session.getTransaction().commit();
            } catch (RuntimeException e) {
                session.getTransaction().rollback();
                throw e;
            }
        }
        log.info("<<< end level: {} session={}", level.get(), session);
    }

    public void close() {
        sessionFactory.close();
    }
}
