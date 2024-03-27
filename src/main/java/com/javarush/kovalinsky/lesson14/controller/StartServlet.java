package com.javarush.kovalinsky.lesson14.controller;

import com.javarush.kovalinsky.lesson14.entity.SpaceQuest;
import com.javarush.kovalinsky.lesson14.repository.AnswerRepository;
import com.javarush.kovalinsky.lesson14.repository.QuestionRepository;
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
        QuestionRepository questionRepository = new QuestionRepository(new AnswerRepository());
        SpaceQuest spaceQuest =  new SpaceQuest(questionRepository);

        req.setAttribute("startMessage", spaceQuest.getSTART_MESSAGE());
        HttpSession session = req.getSession();
        session.setAttribute("questQuestions", spaceQuest.questions);
        session.setAttribute("nextQuestionId", spaceQuest.getStartQuestionId());

        req.getRequestDispatcher("WEB-INF/start.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        String userName = req.getParameter("userName");
        session.setAttribute("userName", userName);
        session.setAttribute("showRestartButton", false);

        resp.sendRedirect("game");
    }
}
