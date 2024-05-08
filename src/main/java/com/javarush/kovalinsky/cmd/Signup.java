package com.javarush.kovalinsky.cmd;

import com.javarush.kovalinsky.entity.Role;
import com.javarush.kovalinsky.entity.User;
import com.javarush.kovalinsky.service.UserService;
import com.javarush.kovalinsky.util.Go;
import com.javarush.kovalinsky.util.Key;
import jakarta.servlet.http.HttpServletRequest;

@SuppressWarnings("unused")
public class Signup implements Command {

    private final UserService userService;

    public Signup(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String doPost(HttpServletRequest req) {
        User user = User.builder()
                .id(0L)
                .login(req.getParameter(Key.LOGIN))
                .password(req.getParameter(Key.PASSWORD))
                .role(Role.valueOf(req.getParameter(Key.ROLE)))
                .build();
        userService.create(user);
        // TODO addImage
        return Go.LIST_USER;
    }
}
