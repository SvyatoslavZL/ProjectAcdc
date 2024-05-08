package com.javarush.kovalinsky.cmd;

import com.javarush.kovalinsky.entity.User;
import com.javarush.kovalinsky.util.Go;
import com.javarush.kovalinsky.util.Key;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("unused")
public class Profile implements Command {

    @Override
    public String doPost(HttpServletRequest req) {
        if (req.getParameter(Key.LOGOUT) == null) {
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute(Key.USER);
            return Go.EDIT_USER + "?id=" + user.getId();
        } else {
            return Go.LOGOUT;
        }
    }
}
