package com.javarush.kovalinsky.lesson14.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/game")
public class GameServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("win", "false");

        req.getRequestDispatcher("WEB-INF/game.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        if (userName != null) {
            HttpSession session = req.getSession();
            session.setAttribute("userName", userName);
            resp.sendRedirect("game");
        }




//        HttpSession session = req.getSession();
//        String action = req.getParameter("action");
//
//        if (action != null) {
//            //последствия в зависимости от того что было выбрано
//        }
    }
}
