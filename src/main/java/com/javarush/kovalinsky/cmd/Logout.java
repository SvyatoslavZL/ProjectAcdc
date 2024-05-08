package com.javarush.kovalinsky.cmd;

import com.javarush.kovalinsky.util.Go;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("unused")
public class Logout implements Command {

    @Override
    public String doGet(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.invalidate();
        return Go.LOGIN;
    }
}
