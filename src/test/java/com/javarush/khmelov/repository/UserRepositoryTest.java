package com.javarush.khmelov.repository;

import com.javarush.khmelov.entity.Role;
import com.javarush.khmelov.entity.User;
import com.javarush.khmelov.config.SessionCreator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserRepositoryTest {


    private final UserRepository userRepository = new UserRepository(new SessionCreator());
    private User admin;

    @BeforeEach
    void createAdmin() {
        admin = User.builder()
                .login("testAdmin")
                .password("testPassword")
                .role(Role.ADMIN)
                .build();
        userRepository.create(admin);
    }

    @Test
    void get() {
        User user = userRepository.get(admin.getId());
        Assertions.assertEquals(admin, user);
    }


    @Test
    void find() {
        User pattern = User.builder().login("testAdmin").build();
        var userStream = userRepository.find(pattern);
        Assertions.assertEquals(admin, userStream.findFirst().orElseThrow());
    }

    @Test
    void update() {
        admin.setLogin("newLogin");
        userRepository.update(admin);
        User user = userRepository.get(admin.getId());
        Assertions.assertEquals(admin, user);
    }

    @AfterEach
    void tearDown() {
        userRepository.delete(admin);
    }
}