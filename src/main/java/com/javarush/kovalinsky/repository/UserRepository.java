package com.javarush.kovalinsky.repository;

import com.javarush.kovalinsky.entity.User;

import java.util.stream.Stream;

public class UserRepository extends BaseRepository<User> {

    @Override
    public Stream<User> find(User pattern) {
        return map.values()
                .stream()
                .filter(repoUser -> nullOrEquals(pattern.getId(), repoUser.getId()))
                .filter(repoUser -> nullOrEquals(pattern.getLogin(), repoUser.getLogin()))
                .filter(repoUser -> nullOrEquals(pattern.getPassword(), repoUser.getPassword()))
                .filter(repoUser -> nullOrEquals(pattern.getRole(), repoUser.getRole()));
    }
}
