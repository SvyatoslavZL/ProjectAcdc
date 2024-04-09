package com.javarush.khmelov.controller;

import com.javarush.khmelov.cmd.Command;
import com.javarush.khmelov.config.Config;
import com.javarush.khmelov.config.HttpResolver;
import com.javarush.khmelov.config.NanoSpring;
import com.javarush.khmelov.entity.Role;
import com.javarush.khmelov.util.Go;
import com.javarush.khmelov.util.RequestHelper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@MultipartConfig(fileSizeThreshold = 1 << 20)
@WebServlet({
        Go.INDEX, Go.HOME,
        Go.SIGNUP, Go.LOGIN, Go.LOGOUT,
        Go.LIST_USER, Go.PROFILE, Go.EDIT_USER,
        Go.CREATE_QUEST, Go.QUEST,
        Go.PLAY_GAME,
        Go.STATISTICS
})
public class FrontController extends HttpServlet {

    private HttpResolver httpResolver;

    @Override
    public void init(ServletConfig config) {
        httpResolver = NanoSpring.find(HttpResolver.class);
        NanoSpring.find(Config.class).fillStartData();
        config.getServletContext().setAttribute("roles", Role.values());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uriCommand = RequestHelper.getCommand(req);
        String cmdName = fixRootCase(uriCommand);
        Command command = httpResolver.resolve(cmdName);
        if (req.getMethod().equalsIgnoreCase("get")) {
            String view = command.doGet(req);
            req.getRequestDispatcher(view).forward(req, resp);
        } else if (req.getMethod().equalsIgnoreCase("post")) {
            String redirect = command.doPost(req);
            redirect = fixAbsoluteAddressing(redirect);
            resp.sendRedirect(redirect);
        } else {
            throw new UnsupportedOperationException(req.getMethod());
        }
    }

    private static String fixRootCase(String uriCommand) {
        return uriCommand.equals("/")
                ? "home"
                : uriCommand.substring(1);
    }

    private static String fixAbsoluteAddressing(String redirect) {
        return redirect.startsWith("/")
                ? redirect.substring(1)
                : redirect;
    }
}
