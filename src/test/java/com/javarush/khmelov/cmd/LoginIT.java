package com.javarush.khmelov.cmd;

import com.javarush.khmelov.BaseIT;
import com.javarush.khmelov.config.NanoSpring;
import com.javarush.khmelov.dto.UserTo;
import com.javarush.khmelov.entity.User;
import com.javarush.khmelov.util.Go;
import com.javarush.khmelov.util.Key;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LoginIT extends BaseIT {

    private final Login login = NanoSpring.find(Login.class);

    @Test
    void whenAdminLogin_thenReturnProfile() {
        when(request.getParameter(Key.LOGIN)).thenReturn("Carl");
        when(request.getParameter(Key.PASSWORD)).thenReturn("admin");

        assertEquals(Go.PROFILE, login.doPost(request));
        verify(session).setAttribute(eq(Key.USER), any(UserTo.class));
    }

    @Test
    void whenIncorrectLogin_thenReturnLogin() {
        when(request.getParameter(Key.LOGIN)).thenReturn("none");
        when(request.getParameter(Key.PASSWORD)).thenReturn("qwerty");

        assertEquals(Go.LOGIN, login.doPost(request));
    }
}