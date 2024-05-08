package com.javarush.kovalinsky.cmd;

import com.javarush.kovalinsky.BaseIT;
import com.javarush.kovalinsky.config.Winter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LoginIT extends BaseIT {

    private final Login login = Winter.find(Login.class);

    @Test
    void whenAdminLogin_thenReturnPlaygame() {
//        when(request.get)
    }

}