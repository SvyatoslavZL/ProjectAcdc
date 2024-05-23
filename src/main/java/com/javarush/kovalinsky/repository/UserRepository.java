package com.javarush.kovalinsky.repository;

import com.javarush.kovalinsky.entity.User;
import com.javarush.kovalinsky.config.SessionCreator;

public class UserRepository extends BaseRepository<User> {

    public UserRepository(SessionCreator sessionCreator) {
        super(User.class, sessionCreator);
    }
}
