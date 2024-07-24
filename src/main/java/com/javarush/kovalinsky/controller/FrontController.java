package com.javarush.kovalinsky.controller;

import com.javarush.kovalinsky.cmd.Command;
import com.javarush.kovalinsky.config.Configurator;
import com.javarush.kovalinsky.config.HttpResolver;
import com.javarush.kovalinsky.config.NanoSpring;
import com.javarush.kovalinsky.dto.Role;
import com.javarush.kovalinsky.util.Go;
import com.javarush.kovalinsky.util.Key;
import com.javarush.kovalinsky.util.RequestHelper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@MultipartConfig(fileSizeThreshold = 1 << 20)
@WebServlet(value = {
        Go.INDEX, Go.HOME,
        Go.SIGNUP, Go.LOGIN, Go.LOGOUT,
        Go.PROFILE, Go.LIST_USER, Go.EDIT_USER,
        Go.CREATE_QUEST, Go.QUEST,
        Go.PLAY_GAME

}, loadOnStartup = 1)
public class FrontController extends HttpServlet {

    private HttpResolver httpResolver;

    @Override
    public void init(ServletConfig config) {
        httpResolver = NanoSpring.find(HttpResolver.class);
        NanoSpring.find(Configurator.class).fillStartData();
        config.getServletContext().setAttribute(Key.ROLES, Role.values());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uriCommand = RequestHelper.getCommand(req);
        String cmdName = fixRootCase(uriCommand);
        Command command = httpResolver.resolve(cmdName);
        if (req.getMethod().equalsIgnoreCase(Key.GET)) {
            String view = command.doGet(req);
            req.getRequestDispatcher(view).forward(req, resp);
        } else if (req.getMethod().equalsIgnoreCase(Key.POST)) {
            String redirect = command.doPost(req);
            redirect = fixAbsoluteAddressing(redirect);
            resp.sendRedirect(redirect);
        } else {
            throw new UnsupportedOperationException(req.getMethod());
        }
    }

    private static String fixRootCase(String uriCommand) {
        return uriCommand.equals("/")
                ? Key.HOME
                : uriCommand.substring(1);
    }

    private static String fixAbsoluteAddressing(String redirect) {
        return redirect.startsWith("/")
                ? redirect.substring(1)
                : redirect;
    }
}
