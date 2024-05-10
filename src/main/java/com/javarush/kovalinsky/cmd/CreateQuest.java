package com.javarush.kovalinsky.cmd;

import com.javarush.kovalinsky.entity.User;
import com.javarush.kovalinsky.service.QuestService;
import com.javarush.kovalinsky.util.Go;
import com.javarush.kovalinsky.util.Key;
import com.javarush.kovalinsky.util.RequestHelper;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

@SuppressWarnings("unused")
public class CreateQuest implements Command {

    private final QuestService questService;

    public CreateQuest(QuestService questService) {
        this.questService = questService;
    }

    @Override
    public String doPost(HttpServletRequest req) {
        String name = req.getParameter(Key.NAME);
        String text = req.getParameter(Key.TEXT);
        Optional<User> optionalUser = RequestHelper.getUser(req.getSession());
        optionalUser.ifPresent(user -> questService.create(name, text, user.getId()));
        return Go.HOME;
    }
}
