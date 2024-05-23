package com.javarush.kovalinsky.repository;

import com.javarush.kovalinsky.entity.Role;
import com.javarush.kovalinsky.entity.User;
import com.javarush.kovalinsky.config.SessionCreator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(admin, user);
    }

    @Test
    void find() {
        User pattern = User.builder().login("testAdmin").build();
        Stream<User> userStream = userRepository.find(pattern);
        assertEquals(admin, userStream.findFirst().orElseThrow());
    }

    @Test
    void update() {
        admin.setLogin("newLogin");
        userRepository.update(admin);
        User user = userRepository.get(admin.getId());
        assertEquals(admin, user);
    }

    @AfterEach
    void tearDown() {
        userRepository.delete(admin);
    }
}