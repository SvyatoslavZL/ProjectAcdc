package com.javarush.kovalinsky.cmd;

import com.javarush.kovalinsky.dto.Role;
import com.javarush.kovalinsky.dto.UserTo;
import com.javarush.kovalinsky.service.ImageService;
import com.javarush.kovalinsky.service.UserService;
import com.javarush.kovalinsky.util.Key;
import com.javarush.kovalinsky.util.RequestHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Optional;

public class EditUser implements Command {

    private final UserService userService;
    private final ImageService imageService;

    public EditUser(UserService userService, ImageService imageService) {
        this.userService = userService;
        this.imageService = imageService;
    }

    @Override
    public String doGet(HttpServletRequest req) {
        String stringId = req.getParameter(Key.ID);
        if (stringId != null) {
            long id = Long.parseLong(stringId);
            Optional<UserTo> optionalUser = userService.get(id);
            if (optionalUser.isPresent()) {
                UserTo user = optionalUser.get();
                req.setAttribute(Key.USER, user);
            }
        }
        return getJspPage();
    }

    @Override
    public String doPost(HttpServletRequest req) throws ServletException, IOException {
        UserTo user = UserTo.builder()
                .login(req.getParameter(Key.LOGIN))
                .password(req.getParameter(Key.PASSWORD))
                .role(Role.valueOf(req.getParameter(Key.ROLE)))
                .build();
        user.setId(RequestHelper.getId(req));
        userService.update(user);
        imageService.uploadImage(req, user.getImage());
        return getPage() + "?id=" + user.getId();
    }
}
