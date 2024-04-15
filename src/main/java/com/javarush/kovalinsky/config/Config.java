package com.javarush.kovalinsky.config;

import com.javarush.kovalinsky.entity.Role;
import com.javarush.kovalinsky.entity.User;
import com.javarush.kovalinsky.service.QuestService;
import com.javarush.kovalinsky.service.UserService;

public class Config {

    private final UserService userService;
    private final QuestService questService;

    public Config(UserService userService, QuestService questService) {
        this.userService = userService;
        this.questService = questService;
    }

    public void fillStartData() {
        if (userService.get(1L).isEmpty()) {
            User admin = new User(-1L, "ZipL", "admin", Role.ADMIN);
            userService.create(admin);
            addDemoQuests(admin);

            userService.create(new User(-1L, "Alisa", "qwerty", Role.USER));
            userService.create(new User(-1L, "Bob", "1234", Role.GUEST));
        }
    }

    private void addDemoQuests(User author) {
        Long authorId = author.getId();
        questService.create("НЛО",
                """
                        1: Ты потерял память. Принять вызов НЛО?
                        2<  Принять вызов
                        91< Отклонить вызов
                                                
                        2: Ты принял вызов. Подняться на мостик к капитану?
                        92< Отказаться подниматься на мостик
                        3< Подняться на мостик
                                                
                        3: Ты поднялся на мостик. Ты кто?
                        93< Солгать о себе
                        99< Рассказать правду
                                                
                        91- Ты отклонил вызов. Поражение.
                        92- Ты не пошел на переговоры. Поражение.
                        93- Твою ложь разоблачили. Поражение.

                        99+ Вы выиграли
                        """, authorId);
    }
}
