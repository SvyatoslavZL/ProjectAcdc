package com.javarush.kovalinsky.service;

import com.javarush.kovalinsky.dto.UserTo;
import com.javarush.kovalinsky.entity.User;
import com.javarush.kovalinsky.mapping.Dto;
import com.javarush.kovalinsky.repository.Repository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
@Transactional
public class UserService {

    private final Repository<User> userRepository;

    public void create(UserTo userTo) {
        userRepository.create(Dto.MAPPER.from(userTo));
    }

    public void update(UserTo userTo) {
        userRepository.update(Dto.MAPPER.from(userTo));
    }

    public Collection<UserTo> getAll() {
        return userRepository.getAll().stream().map(Dto.MAPPER::from).toList();
    }

    public Optional<UserTo> get(long id) {
        User userPattern = User.builder().id(id).build();
        return userRepository.find(userPattern).map(Dto.MAPPER::from).findAny();
    }

    public Optional<UserTo> get(String login, String password) {
        User userPattern = User.builder()
                .login(login)
                .password(password)
                .build();
        return userRepository.find(userPattern).map(Dto.MAPPER::from).findAny();
    }
}
