package com.javarush.kovalinsky.cmd;

import com.javarush.kovalinsky.BaseIT;
import com.javarush.kovalinsky.config.NanoSpring;
import com.javarush.kovalinsky.dto.Role;
import com.javarush.kovalinsky.dto.UserTo;
import com.javarush.kovalinsky.service.UserService;
import com.javarush.kovalinsky.util.Go;
import com.javarush.kovalinsky.util.Key;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

class LoginIT extends BaseIT {

    private final Login login = NanoSpring.find(Login.class);
    private final UserService userService = NanoSpring.find(UserService.class);

    @Test
    void whenAdminLogin_thenReturnProfile() {
        createAdminWithTestDataIfThereIsNoSuchAdmin();
        doReturn("ZipL").when(request).getParameter(Key.LOGIN);
        doReturn("admin").when(request).getParameter(Key.PASSWORD);

        Assertions.assertEquals(Go.PROFILE, login.doPost(request));
        verify(session).setAttribute(eq(Key.USER), any(UserTo.class));
    }

    @Test
    void whenIncorrectLogin_thenReturnLogin() {
        doReturn("none").when(request).getParameter(Key.LOGIN);
        doReturn("qwerty").when(request).getParameter(Key.PASSWORD);
        Assertions.assertEquals(Go.LOGIN, login.doPost(request));
    }

    private void createAdminWithTestDataIfThereIsNoSuchAdmin() {
        Optional<UserTo> optionalAdmin = userService.get("ZipL", "admin");
        if (optionalAdmin.isEmpty()) {
            UserTo admin = UserTo.builder()
                    .login("ZipL")
                    .password("admin")
                    .role(Role.ADMIN)
                    .build();
            userService.create(admin);
        }
    }
}