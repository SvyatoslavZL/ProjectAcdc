package com.javarush.lesson11;

import com.javarush.kovalinsky.entity.User;
import com.javarush.kovalinsky.exception.AppException;
import com.javarush.kovalinsky.config.SessionCreator;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.stat.SessionStatistics;

public class HibernateContext {
    public static void main(String[] args) {
        SessionCreator sessionCreator = new SessionCreator();
        Session session = sessionCreator.getSession();
        try (session) {
            Transaction tx = session.beginTransaction();
            try {
                print(session);
                User bob1 = session.find(User.class, 3L);
                User bob2 = session.find(User.class, 3L);
                print(session);

                bob1.setPassword("1234");
                print(session);
                System.out.println(bob2);
                print(session);
                System.out.println(bob1);
                tx.commit();
                bob1.setPassword("1234");

                print(session);
                Transaction tx2 = session.beginTransaction();
                tx2.commit();
                print(session);
            } catch (Exception e) {
                tx.rollback();
                throw new AppException(e);
            }
        }

    }

    private static void print(Session session) {
        String name = session.toString();
        boolean dirty = session.isDirty();
        SessionStatistics statistics = session.getStatistics();
        String line = "-".repeat(60);
        System.out.printf("%s%nName: %s%n Dirty: %s%n Stat: %s%n%s%n",
                line, name, dirty, statistics, line);
    }
}
