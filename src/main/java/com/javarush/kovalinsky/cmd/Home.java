package com.javarush.kovalinsky.cmd;

import com.javarush.kovalinsky.service.QuestService;
import com.javarush.kovalinsky.util.Key;
import jakarta.servlet.http.HttpServletRequest;

@SuppressWarnings("unused")
public class Home implements Command {

    private final QuestService questService;

    public Home(QuestService questService) {
        this.questService = questService;
    }

    @Override
    public String doGet(HttpServletRequest req) {
        req.setAttribute(Key.QUESTS, questService.getAll());
        return getJspPage();
    }
}
