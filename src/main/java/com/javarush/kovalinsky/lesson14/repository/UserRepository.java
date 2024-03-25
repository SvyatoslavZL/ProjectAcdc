package com.javarush.kovalinsky.lesson14.repository;

import com.javarush.kovalinsky.lesson14.entity.Role;
import com.javarush.kovalinsky.lesson14.entity.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class UserRepository implements Repository<User> {

    public static final AtomicLong id = new AtomicLong(System.currentTimeMillis());
    private final Map<Long, User> map = new HashMap<>();

    public UserRepository() {
        map.put(1L, new User(1L, "Alisa", "qwerty", Role.USER));
        map.put(2L, new User(2L, "Bob", "", Role.GUEST));
        map.put(3L, new User(3L, "Carl", "admin", Role.ADMIN));
        map.put(4L, new User(3L, "Khmelov", "admin", Role.ADMIN));
    }

    @Override
    public Collection<User> getAll() {
        return map.values();
    }

    @Override
    public Optional<User> get(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public void create(User entity) {
        entity.setId(id.incrementAndGet());
        update(entity);
    }

    @Override
    public void update(User entity) {
        map.put(entity.getId(), entity);
    }

    @Override
    public void delete(User entity) {
        map.remove(entity.getId());
    }
}
