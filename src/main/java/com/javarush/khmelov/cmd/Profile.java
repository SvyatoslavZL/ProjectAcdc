package com.javarush.khmelov.cmd;

import com.javarush.khmelov.dto.UserTo;
import com.javarush.khmelov.util.Go;
import com.javarush.khmelov.util.Key;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("unused")
public class Profile implements Command {

    @Override
    public String doPost(HttpServletRequest request) {
        if (request.getParameter(Key.LOGOUT) == null) {
            HttpSession session = request.getSession();
            UserTo userTo = (UserTo) session.getAttribute("user");
            return Go.EDIT_USER + "?id=" + userTo.getId();
        } else {
            return Go.LOGOUT;
        }
    }
}
