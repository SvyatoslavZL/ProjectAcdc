package com.javarush.kovalinsky.cmd;

import com.javarush.kovalinsky.entity.Role;
import com.javarush.kovalinsky.entity.User;
import com.javarush.kovalinsky.service.ImageService;
import com.javarush.kovalinsky.service.UserService;
import com.javarush.kovalinsky.util.Go;
import com.javarush.kovalinsky.util.Key;
import jakarta.servlet.http.HttpServletRequest;

@SuppressWarnings("unused")
public class Signup implements Command {

    private final UserService userService;
    private final ImageService imageService;

    public Signup(UserService userService, ImageService imageService) {
        this.userService = userService;
        this.imageService = imageService;
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
        imageService.uploadImage(req, user.getImage());
        return Go.LIST_USER;
    }
}
