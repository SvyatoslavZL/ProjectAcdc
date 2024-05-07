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
        Configuration configuration = new Configuration();     //1. hibernate.properties
     // configuration.configure();                             //2. hibernate.cfg.xml
     // Properties properties = configuration.getProperties(); //3.1 prepare for read
     // properties.load(SessionFactory.class.getResourceAsStream("/application.properties")); //3.2 your
     // configuration.addProperties(properties);               //3.3 application.properties
     // configuration.add????Resource()                        //and 100500 other ways
        configuration.addAnnotatedClass(User.class);
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
