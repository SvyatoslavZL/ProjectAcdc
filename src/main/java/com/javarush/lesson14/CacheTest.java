package com.javarush.lesson14;

import com.javarush.khmelov.config.SessionCreator;
import com.javarush.khmelov.entity.User;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;

public class CacheTest {


    @Test
    public void read() throws Exception {
        @Cleanup SessionCreator sessionCreator = new SessionCreator();
        for (int i = 0; i < 1000; i++) {
            Session session = sessionCreator.getSession();
            try (session) {
                User user = session.get(User.class, 1L);
                System.out.println(user);
            }
        }
    }

}
