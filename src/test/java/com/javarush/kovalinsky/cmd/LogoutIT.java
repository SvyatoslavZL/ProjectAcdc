package com.javarush.kovalinsky.cmd;

import com.javarush.kovalinsky.BaseIT;
import com.javarush.kovalinsky.config.NanoSpring;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class LogoutIT extends BaseIT {

    private final Logout logout = NanoSpring.find(Logout.class);

    @Test
    void whenOpenPage_thenInvalidateSession() {
        logout.doGet(request);
        Mockito.verify(session).invalidate();
    }
}
