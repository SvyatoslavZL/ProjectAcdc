package com.javarush.lesson09;

import com.javarush.khmelov.BaseIT;
import com.javarush.khmelov.config.ApplicationProperties;
import com.javarush.khmelov.config.SessionCreator;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SessionCreatorTest extends BaseIT {

    @Test
    void getSession() {
        ApplicationProperties applicationProperties = new ApplicationProperties();
        SessionCreator sessionCreator = new SessionCreator(applicationProperties);
        Session session = sessionCreator.getSession();
        System.out.println(session);
        Assertions.assertNotNull(session);
    }


}