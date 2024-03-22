package com.javarush.kovalinsky.lesson14.controller;

import com.javarush.kovalinsky.lesson14.entity.Quest;
import com.javarush.kovalinsky.lesson14.service.QuestService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(urlPatterns = {"", "/start"})
public class StartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String spaceQuestStart = QuestService.SPACE_QUEST_START;
        Quest spaceQuest = QuestService.initAndGetSpaceQuest();

        HttpSession session = req.getSession();
        session.setAttribute("spaceQuest", spaceQuest);
        session.setAttribute("spaceQuestStart", spaceQuestStart);

        req.getRequestDispatcher("WEB-INF/start.jsp").forward(req, resp);
    }
}
