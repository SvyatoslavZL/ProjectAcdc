package com.javarush.lesson09;

import com.javarush.khmelov.BaseIT;
import com.javarush.khmelov.entity.Role;
import com.javarush.khmelov.entity.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserDbDaoTest extends BaseIT {

    private final SessionCreator sessionCreator = new SessionCreator();
    UserDbDao userDao = new UserDbDao(sessionCreator);

    @Test
    @DisplayName("When find all users result contains ADMIN")
    void getAll() {
        userDao.getAll().forEach(System.out::println);

    }

    @Test
    void find() {
        User findPattern = User.builder().role(Role.ADMIN).build();
        Stream<User> userStream = userDao.find(findPattern);
        userStream.forEach(System.out::println);

    }

    @Test
    @DisplayName("When find by id then get user id=1 role=ADMIN")
    void get() {
        User user = userDao.get(1L);
        assertEquals(1L, user.getId());
        assertEquals(Role.ADMIN, user.getRole());
    }

    @Test
    @DisplayName("When create+update+delete tempUser then no Exception")
    void createUpdateDelete() {
        User tempUser = User.builder()
                .login("login_test")
                .password("password_test")
                .role(Role.GUEST)
                .build();
        userDao.create(tempUser);
        System.out.println("CREATE " + tempUser);

        tempUser.setPassword("password_test_update");
        userDao.update(tempUser);
        System.out.println("UPDATE " + tempUser);

        userDao.delete(tempUser);
        System.out.println("DELETE " + tempUser);
        assertTrue(tempUser.getId() > 0);
    }

    @AfterEach
    void close() throws IOException {
        sessionCreator.close();
    }
}