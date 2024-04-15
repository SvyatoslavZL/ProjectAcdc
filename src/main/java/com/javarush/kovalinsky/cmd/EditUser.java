package com.javarush.kovalinsky.cmd;

import com.javarush.kovalinsky.entity.Role;
import com.javarush.kovalinsky.entity.User;
import com.javarush.kovalinsky.service.UserService;
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
        String stringId = req.getParameter("id");
        if (stringId != null) {
            long id = Long.parseLong(stringId);
            Optional<User> optionalUser = userService.get(id);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                req.setAttribute("user", user);
            }
        }
        return getJspPage();
    }

    @Override
    public String doPost(HttpServletRequest req) {
        User user = User.builder()
                .id(Long.parseLong(req.getParameter("id")))
                .login(req.getParameter("login"))
                .password(req.getParameter("password"))
                .role(Role.valueOf(req.getParameter("role")))
                .build();

        // TODO setId using RequestHelper instead
        userService.update(user);
        // TODO uploadImage

        return getPage() + "?id=" + user.getId();
    }
}
