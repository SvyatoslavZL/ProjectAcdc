package com.javarush.kovalinsky.cmd;

import com.javarush.kovalinsky.BaseIT;
import com.javarush.kovalinsky.config.Winter;
import com.javarush.kovalinsky.entity.User;
import com.javarush.kovalinsky.util.Go;
import com.javarush.kovalinsky.util.Key;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LoginIT extends BaseIT {

    private final Login login = Winter.find(Login.class);

    @Test
    void whenAdminLogin_thenReturnProfile() {
        doReturn("ZipL").when(request).getParameter(Key.LOGIN);
        doReturn("admin").when(request).getParameter(Key.PASSWORD);

        assertEquals(Go.PROFILE, login.doPost(request));
        verify(session).setAttribute(eq(Key.USER), any(User.class));
    }

    @Test
    void whenIncorrectLogin_thenReturnLogin() {
        doReturn("none").when(request).getParameter(Key.LOGIN);
        doReturn("qwerty").when(request).getParameter(Key.PASSWORD);
        assertEquals(Go.LOGIN, login.doPost(request));
    }
}