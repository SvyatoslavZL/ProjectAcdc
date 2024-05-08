package com.javarush.kovalinsky.cmd;

import com.javarush.kovalinsky.entity.Role;
import com.javarush.kovalinsky.entity.User;
import com.javarush.kovalinsky.service.UserService;
import com.javarush.kovalinsky.util.Key;
import com.javarush.kovalinsky.util.RequestHelper;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

@SuppressWarnings("unused")
public class EditUser implements Command {

    private final UserService userService;

    public EditUser(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String doGet(HttpServletRequest req) {
        String stringId = req.getParameter(Key.ID);
        if (stringId != null) {
            long id = Long.parseLong(stringId);
            Optional<User> optionalUser = userService.get(id);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                req.setAttribute(Key.USER, user);
            }
        }
        return getJspPage();
    }

    @Override
    public String doPost(HttpServletRequest req) {
        User user = User.builder()
                .login(req.getParameter(Key.LOGIN))
                .password(req.getParameter(Key.PASSWORD))
                .role(Role.valueOf(req.getParameter(Key.ROLE)))
                .build();

        user.setId(RequestHelper.getId(req));
        userService.update(user);
        // TODO uploadImage

        return getPage() + "?id=" + user.getId();
    }
}
