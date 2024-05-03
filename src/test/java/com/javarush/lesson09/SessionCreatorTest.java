package com.javarush.lesson09;

import com.javarush.khmelov.BaseIT;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessionCreatorTest extends BaseIT {

    @Test
    void getSession() {
        SessionCreator sessionCreator = new SessionCreator();
        Session session = sessionCreator.getSession();
        System.out.println(session);
        Assertions.assertNotNull(session);
    }


}