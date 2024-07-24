package com.javarush.kovalinsky.cmd;

import com.javarush.kovalinsky.dto.UserTo;
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
            UserTo userTo = (UserTo) session.getAttribute(Key.USER);
            return Go.EDIT_USER + "?id=" + userTo.getId();
        } else {
            return Go.LOGOUT;
        }
    }
}
