package com.javarush.kovalinsky.cmd;

import com.javarush.kovalinsky.entity.User;
import com.javarush.kovalinsky.service.UserService;
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
        Collection<User> users = userService.getAll();
        req.setAttribute("users", users); //TODO attrConstants (util.Key class)
        return getJspPage();
    }
}
