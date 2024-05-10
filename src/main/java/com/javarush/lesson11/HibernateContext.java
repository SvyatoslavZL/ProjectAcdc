package com.javarush.lesson11;

import com.javarush.khmelov.entity.User;
import com.javarush.khmelov.exception.AppException;
import com.javarush.khmelov.config.SessionCreator;
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
                User bob1 = session.get(User.class, 3L);
                User bob2 = session.get(User.class, 3L);
                print(session);

                bob1.setPassword("321");
                print(session);
                System.out.println(bob2);
                print(session);
                System.out.println(bob1);
                tx.commit();
                print(session);
                bob1.setPassword("123");

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
        boolean dirty = session.isDirty();

        SessionStatistics statistics = session.getStatistics();
        String name = session.toString();
        String line = "-".repeat(60);
        System.out.printf("%s%nName: %s%nDirty: %s%nStat %s%n%s%n",
                line,name, dirty, statistics, line);
    }
}
