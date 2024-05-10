package com.javarush.lesson09;

import com.javarush.khmelov.BaseIT;
import com.javarush.khmelov.config.SessionCreator;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SessionCreatorTest extends BaseIT {

    @Test
    void getSession() {
        SessionCreator sessionCreator = new SessionCreator();
        Session session = sessionCreator.getSession();
        System.out.println(session);
        Assertions.assertNotNull(session);
    }


}