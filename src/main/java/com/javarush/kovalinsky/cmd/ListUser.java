package com.javarush.kovalinsky.cmd;

import com.javarush.kovalinsky.dto.UserTo;
import com.javarush.kovalinsky.service.UserService;
import com.javarush.kovalinsky.util.Key;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Collection;

@SuppressWarnings("unused")
public class ListUser implements Command {

    private final UserService userService;

    public ListUser(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String doGet(HttpServletRequest req) {
        Collection<UserTo> users = userService.getAll();
        req.setAttribute(Key.USERS, users);
        return getJspPage();
    }
}
