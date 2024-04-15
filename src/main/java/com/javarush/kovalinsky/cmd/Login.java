package com.javarush.kovalinsky.cmd;

import com.javarush.kovalinsky.entity.User;
import com.javarush.kovalinsky.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

@SuppressWarnings("unused")
public class Login implements Command {

    private final UserService userService;

    public Login(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String doPost(HttpServletRequest req) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        Optional<User> user = userService.get(login, password);
        if (user.isPresent()) {
            req.getSession().setAttribute("user", user.get());
            return "/play-game"; //TODO return profile
        } else {
            return "/login"; //TODO error message
        }
    }
}
