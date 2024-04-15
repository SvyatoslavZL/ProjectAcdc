package com.javarush.kovalinsky.service;

import com.javarush.kovalinsky.entity.User;
import com.javarush.kovalinsky.repository.Repository;
import com.javarush.kovalinsky.repository.UserRepository;

import java.util.Collection;
import java.util.Optional;

public class UserService {

    private final Repository<User> userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void create(User user) {
        userRepository.create(user);
    }

    public void update(User user) {
        userRepository.update(user);
    }

    public Collection<User> getAll() {
        return userRepository.getAll();
    }

    public Optional<User> get(long id) {
        User userPattern = User.builder().id(id).build();
        return userRepository.find(userPattern).findAny();
    }

    public Optional<User> get(String login, String password) {
        User userPattern = User
                .builder()
                .login(login)
                .password(password)
                .build();
        return userRepository.find(userPattern).findAny();
    }
}
