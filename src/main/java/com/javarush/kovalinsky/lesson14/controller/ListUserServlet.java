package com.javarush.kovalinsky.lesson14.controller;

import com.javarush.kovalinsky.lesson14.entity.User;
import com.javarush.kovalinsky.lesson14.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;

@WebServlet("/list-user")
public class ListUserServlet extends HttpServlet {

    private final UserService userService = UserService.USER_SERVICE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection<User> users = userService.getAll();
        req.setAttribute("users", users);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/list-user.jsp");
        requestDispatcher.forward(req,resp);
    }
}
