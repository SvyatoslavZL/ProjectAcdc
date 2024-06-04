package com.javarush.lesson16;

import com.javarush.khmelov.config.NanoSpring;
import com.javarush.khmelov.entity.User;

import java.util.Optional;

public class Runner {
    public static void main(String[] args) {
        DemoTransactional transactional = NanoSpring.find(DemoTransactional.class);
        Optional<User> user = transactional.get(1L);
        System.out.println(user.orElseThrow());
    }
}
