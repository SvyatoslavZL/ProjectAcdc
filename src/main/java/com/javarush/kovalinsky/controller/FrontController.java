package com.javarush.kovalinsky.controller;

import com.javarush.kovalinsky.cmd.Command;
import com.javarush.kovalinsky.config.Config;
import com.javarush.kovalinsky.config.HttpResolver;
import com.javarush.kovalinsky.config.Winter;
import com.javarush.kovalinsky.entity.Role;
import com.javarush.kovalinsky.util.Go;
import com.javarush.kovalinsky.util.Key;
import com.javarush.kovalinsky.util.RequestHelper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet({
        Go.INDEX, Go.HOME,
        Go.SIGNUP, Go.LOGIN, Go.LIST_USER, Go.EDIT_USER,
        Go.PLAY_GAME
})
public class FrontController extends HttpServlet {

    private HttpResolver httpResolver;

    @Override
    public void init(ServletConfig config) {
        httpResolver = Winter.find(HttpResolver.class);
        Winter.find(Config.class).fillStartData();
        config.getServletContext().setAttribute(Key.ROLES, Role.values());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uriCommand = RequestHelper.getCommand(req);
        String cmdName = uriCommand.equals("/") ? "home" : uriCommand.substring(1);
        Command command = httpResolver.resolve(cmdName);
        if (req.getMethod().equalsIgnoreCase(Key.GET)) {
            String view = command.doGet(req);
            req.getRequestDispatcher(view).forward(req, resp);
        } else if (req.getMethod().equalsIgnoreCase(Key.POST)) {
            String redirect = command.doPost(req);
            redirect = redirect.startsWith("/") ? redirect.substring(1) : redirect;
            resp.sendRedirect(redirect);
        } else {
            throw new UnsupportedOperationException(req.getMethod());
        }
    }
}
