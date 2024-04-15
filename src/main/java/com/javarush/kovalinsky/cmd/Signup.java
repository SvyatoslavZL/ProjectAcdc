package com.javarush.kovalinsky.cmd;

import com.javarush.kovalinsky.entity.Role;
import com.javarush.kovalinsky.entity.User;
import com.javarush.kovalinsky.service.UserService;
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
                .login(req.getParameter("login"))
                .password(req.getParameter("password"))
                .role(Role.valueOf(req.getParameter("role")))
                .build();
        userService.create(user);
        // TODO addImage
        return "/list-user"; //TODO redirectConstants
    }
}
