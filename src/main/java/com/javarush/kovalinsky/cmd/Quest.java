package com.javarush.kovalinsky.cmd;

import com.javarush.kovalinsky.dto.QuestTo;
import com.javarush.kovalinsky.dto.QuestionTo;
import com.javarush.kovalinsky.dto.Role;
import com.javarush.kovalinsky.dto.UserTo;
import com.javarush.kovalinsky.service.ImageService;
import com.javarush.kovalinsky.service.QuestService;
import com.javarush.kovalinsky.service.QuestionService;
import com.javarush.kovalinsky.util.Err;
import com.javarush.kovalinsky.util.Go;
import com.javarush.kovalinsky.util.Key;
import com.javarush.kovalinsky.util.RequestHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Optional;

@SuppressWarnings("unused")
public class Quest implements Command {

    private final QuestService questService;
    private final QuestionService questionService;
    private final ImageService imageService;

    public Quest(QuestService questService, QuestionService questionService, ImageService imageService) {
        this.questService = questService;
        this.questionService = questionService;
        this.imageService = imageService;
    }

    @Override
    public String doGet(HttpServletRequest req) {
        long id = RequestHelper.getId(req);
        Optional<QuestTo> quest = questService.get(id);
        req.setAttribute(Key.QUEST, quest.orElseThrow());
        return getJspPage();
    }

    @Override
    public String doPost(HttpServletRequest req) throws ServletException, IOException {
        Optional<UserTo> editor = RequestHelper.getUser(req.getSession());
        if (editor.isPresent() && editor.get().getRole() == Role.ADMIN) {
            Long id = RequestHelper.getId(req);
            Long questionId = RequestHelper.getId(req, Key.QUESTION_ID);
            String text = req.getParameter(Key.TEXT);
            Optional<QuestionTo> question = questionService.update(questionId, text);
            if (question.isPresent()) {
                imageService.uploadImage(req, question.get().getImage());
            }
            return "%s?id=%d#bookmark%d".formatted(Go.QUEST, id, questionId);
        } else {
            RequestHelper.setError(req, Err.NO_PERMISSIONS_FOR_OPERATION);
            return Go.QUEST;
        }
    }
}
