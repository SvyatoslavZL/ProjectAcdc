package com.javarush.kovalinsky.lesson14.controller;

import com.javarush.kovalinsky.lesson14.entity.Answer;
import com.javarush.kovalinsky.lesson14.entity.Quest;
import com.javarush.kovalinsky.lesson14.entity.Question;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/game")
public class GameServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int questionId = (int) session.getAttribute("nextQuestionId");
        if (questionId == 3 || questionId < 0) {
            session.setAttribute("showRestartButton", true);
        }

        Quest spaceQuest = (Quest) session.getAttribute("spaceQuest");
        for (Question question : spaceQuest.getQuestions()) {
            if (question.getId() == questionId) {
                session.setAttribute("questionText", question.getText());
                session.setAttribute("answers", question.getAnswers());
            }
        }

        req.getRequestDispatcher("WEB-INF/game.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (req.getParameter("option") == null) {
            session.invalidate();
            resp.sendRedirect("start");
            return;
        }

        int option = Integer.parseInt(req.getParameter("option"));
        Answer userAnswer = null;
        for (Answer answer : (List<Answer>) session.getAttribute("answers")) {
            if (answer.getId() == option) {
                userAnswer = answer;
            }
        }

        if (userAnswer != null) {
            session.setAttribute("nextQuestionId", userAnswer.getNextQuestionId());
        } else {
            throw new ServletException("User hasn't chosen the answer");
        }

        resp.sendRedirect("game");
    }
}
