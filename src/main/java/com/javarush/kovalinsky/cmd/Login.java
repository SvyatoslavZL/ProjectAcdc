package com.javarush.kovalinsky.cmd;

import com.javarush.kovalinsky.entity.User;
import com.javarush.kovalinsky.service.UserService;
import com.javarush.kovalinsky.util.Err;
import com.javarush.kovalinsky.util.Go;
import com.javarush.kovalinsky.util.Key;
import com.javarush.kovalinsky.util.RequestHelper;
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
        String login = req.getParameter(Key.LOGIN);
        String password = req.getParameter(Key.PASSWORD);
        Optional<User> user = userService.get(login, password);
        if (user.isPresent()) {
            req.getSession().setAttribute(Key.USER, user.get());
            return Go.PROFILE;
        } else {
            RequestHelper.setError(req, Err.INVALID_USER_DATA);
            return Go.LOGIN;
        }
    }
}
