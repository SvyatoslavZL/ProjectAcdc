package com.javarush.kovalinsky.cmd;

import com.javarush.kovalinsky.BaseIT;
import com.javarush.kovalinsky.config.NanoSpring;
import com.javarush.kovalinsky.repository.UserRepository;
import com.javarush.kovalinsky.util.Go;
import com.javarush.kovalinsky.util.Key;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.doReturn;

class SignupIT extends BaseIT {

    private final Signup signup = NanoSpring.find(Signup.class);
    private final UserRepository repository = NanoSpring.find(UserRepository.class);

    @Test
    void whenSignup_thenGoToListUserAndUserRepositoryContainsNewUser() throws ServletException, IOException {
        doReturn("newTestLogin").when(request).getParameter(Key.LOGIN);
        doReturn("newTestPassword").when(request).getParameter(Key.PASSWORD);
        doReturn("GUEST").when(request).getParameter(Key.ROLE);

        String uri = signup.doPost(request);
        Assertions.assertEquals(Go.LIST_USER, uri);
        Assertions.assertTrue(repository.getAll().toString().contains("newTestLogin"));
    }
}
