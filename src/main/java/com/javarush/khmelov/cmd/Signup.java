package com.javarush.khmelov.cmd;

import com.javarush.khmelov.dto.Role;
import com.javarush.khmelov.dto.UserTo;
import com.javarush.khmelov.service.ImageService;
import com.javarush.khmelov.service.UserService;
import com.javarush.khmelov.util.Go;
import com.javarush.khmelov.util.Key;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@SuppressWarnings("unused")
public class Signup implements Command {

    private final UserService userService;
    private final ImageService imageService;

    public Signup(UserService userService, ImageService imageService) {
        this.userService = userService;
        this.imageService = imageService;
    }

    @Override
    public String doPost(HttpServletRequest request) throws ServletException, IOException {
        UserTo user = UserTo.builder()
                .login(request.getParameter(Key.LOGIN))
                .password(request.getParameter(Key.PASSWORD))
                .role(Role.valueOf(request.getParameter(Key.ROLE)))
                .build();
        userService.create(user);
        imageService.uploadImage(request, user.getImage());
        return Go.LIST_USER;
    }
}
