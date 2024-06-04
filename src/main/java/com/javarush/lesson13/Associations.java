package com.javarush.lesson13;

import com.javarush.khmelov.config.ApplicationProperties;
import com.javarush.khmelov.config.SessionCreator;
import com.javarush.khmelov.entity.User;
import com.javarush.khmelov.exception.AppException;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;

public class Associations {


    @Test
    public void read() throws Exception{
        @Cleanup SessionCreator sessionCreator = new SessionCreator(new ApplicationProperties());
        Session session = sessionCreator.getSession();
        User admin=null;
        try (session) {
            Transaction tx = session.beginTransaction();
            try {
                admin = session.get(User.class, 1L);
                System.out.println(admin);
                System.out.println(admin.getUserInfo());
                System.out.println(admin.getQuests());
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw new AppException(e);
            }
        }

    }

}
