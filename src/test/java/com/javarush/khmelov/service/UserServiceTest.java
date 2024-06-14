package com.javarush.khmelov.service;

import com.javarush.khmelov.dto.Role;
import com.javarush.khmelov.dto.UserTo;
import com.javarush.khmelov.entity.User;
import com.javarush.khmelov.mapping.Dto;
import com.javarush.khmelov.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private User user;
    private UserTo userTo;
    private UserRepository userRepositoryMock;
    private UserService userService;

    @BeforeEach
    void init() {
        user = User.builder()
                .id(1L)
                .login("testLogin")
                .password("testPassword")
                .role(Role.USER)
                .build();
        userTo=Dto.MAPPER.from(user);
        userRepositoryMock = mock(UserRepository.class);
        when(userRepositoryMock.find(any(User.class))).thenReturn(Stream.of(user));
        userService = new UserService(userRepositoryMock);
    }

    @Test
    void getById() {
        Optional<UserTo> optional = userService.get(1L);
        assertEquals(userTo, optional.orElseThrow());
    }

    @Test
    void getByLoginAndPassword() {
        Optional<UserTo>  optional = userService.get("login", "password");
        assertEquals(userTo, optional.orElseThrow());
        verify(userRepositoryMock).find(any(User.class));
    }

    @Test
    void getAll() {
        when(userRepositoryMock.getAll()).thenReturn(List.of(user, user));
        Collection<UserTo> all = userService.getAll();
        assertTrue(all.contains(userTo));
        assertEquals(2, all.size());
        verify(userRepositoryMock).getAll();
    }

    @Test
    void update() {
        userService.update(userTo);
        verify(userRepositoryMock).update(any(User.class)); //TODO fix after dto
    }

    @Test
    void create() {
        userService.create(userTo);
        verify(userRepositoryMock).create(any(User.class)); //TODO fix after dto
    }

}