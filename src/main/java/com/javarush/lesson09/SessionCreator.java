package com.javarush.lesson09;

import com.javarush.khmelov.entity.User;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.Closeable;
import java.io.IOException;
import java.util.Properties;

public class SessionCreator implements Closeable {

    private final SessionFactory sessionFactory;

    @SneakyThrows
    public SessionCreator() {
        Configuration configuration = new Configuration();
//        configuration.configure();
        configuration.addAnnotatedClass(User.class);
//        Properties properties = configuration.getProperties();
//        properties.load(SessionFactory.class.getResourceAsStream("/application.properties"));
//        configuration.addProperties(properties);
        sessionFactory = configuration.buildSessionFactory();
    }

    public Session getSession(){
        return sessionFactory.openSession();
    }


    @Override
    public void close() throws IOException {
        sessionFactory.close();
    }

    public static void main(String[] args) throws IOException {
        SessionCreator sessionCreator = new SessionCreator();
        UserDbDao userDbDao=new UserDbDao(sessionCreator);

        User user = userDbDao.get(1L);
        System.out.println(user);

        sessionCreator.close();
    }
}
