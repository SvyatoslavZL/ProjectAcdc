package com.javarush.khmelov.repository;

import com.javarush.khmelov.entity.User;
import com.javarush.khmelov.config.SessionCreator;
import jakarta.transaction.Transactional;

@Transactional
public class UserRepository extends BaseRepository<User> {


    public UserRepository(SessionCreator sessionCreator) {
        super(User.class, sessionCreator);
    }
}
