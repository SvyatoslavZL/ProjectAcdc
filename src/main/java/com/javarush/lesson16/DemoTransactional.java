package com.javarush.lesson16;

import com.javarush.khmelov.entity.User;
import com.javarush.khmelov.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
@Transactional
public class DemoTransactional {

    UserService userService;

    public Optional<User> get(long id){
        return userService.get(id);
    }

}
