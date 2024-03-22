package com.javarush.kovalinsky.lesson14.service;

import com.javarush.kovalinsky.lesson14.entity.Answer;
import com.javarush.kovalinsky.lesson14.entity.Quest;
import com.javarush.kovalinsky.lesson14.entity.Question;

import java.util.ArrayList;
import java.util.Collection;

public class QuestService {

    public static final String SPACE_QUEST_START = """
            <legend>
                Ты стоишь в космическом порту и готов подняться на борт своего корабля.
                Разве ты не об этом мечтал? Стать капитаном галактического судна с экипажем,
                который будет совершать подвиги под твоим командованием.
                Так что вперёд!
            </legend>
                        
            <h2>Знакомство с экипажем</h2>
            <legend>
                Когда ты поднялся на борт корабля, тебя поприветствовала девушка с чёрной палкой в руках:
                - Здравствуйте, командир! Я Зинаида - ваша помощница. Видите? Там в углу пьёт кофе
                наш штурман - сержант Перегарный Шлейф, под штурвалом спит наш бортмеханик - Чёрный Богдан,
                а фотографирует его Сергей Стальная Пятка - наш навигатор.
                А как обращаться к вам?
            </legend>
            <br/>
            """;

//    public static final String SPACE_QUEST_START = """
//            Ты стоишь в космическом порту и готов подняться на борт своего корабля.
//            Разве ты не об этом мечтал? Стать капитаном галактического судна с экипажем,
//            который будет совершать подвиги под твоим командованием. Так что вперёд!
//            \n
//            Знакомство с экипажем.
//            \n
//            Когда ты поднялся на борт корабля, тебя поприветствовала девушка с чёрной палкой в руках:
//            - Здравствуйте, командир! Я Зинаида - ваша помощница. Видите? Там в углу пьёт кофе
//            наш штурман - сержант Перегарный Шлейф, под штурвалом спит наш бортмеханик - Чёрный Богдан,
//            а фотографирует его Сергей Стальная Пятка - наш навигатор. А как обращаться к вам?
//            \n
//            """;

    public static Quest initAndGetSpaceQuest() {
        Quest spaceQuest = new Quest(0L, "SpaceQuest", 0L);
        spaceQuest.setName("SpaceQuest");
        spaceQuest.questions.addAll(initQuestions(spaceQuest.getId()));
        return spaceQuest;
    }

    public static Collection<Question> initQuestions(Long QuestId) {
        if (QuestId == 0L) {
            Question question0 = new Question(0L, QuestId, "Ты потерял память. Принять вызов НЛО?");
            question0.answers.add(new Answer(0L, question0.getId(), "Принять вызов", question0.getId() + 1));
            question0.answers.add(new Answer(1L, question0.getId(), "Отклонить вызов", -1L));

            Question losing1 = new Question(-1L, QuestId, "Ты отклонил вызов. Поражение");
            Question question1 = new Question(1L, QuestId, "Ты принял вызов. Поднимешься на мостик к капитану?");
            question1.answers.add(new Answer(2L, question1.getId(), "Подняться на мостик", question1.getId() + 1));
            question1.answers.add(new Answer(3L, question1.getId(), "Отказаться подниматься на мостик", -2L));

            Question losing2 = new Question(-2L, QuestId, "Ты не пошёл на переговоры. Поражение");
            Question question2 = new Question(2L, QuestId, "Ты поднялся на мостик. Кто ты?");
            question2.answers.add(new Answer(4L, question2.getId(), "Рассказать о себе правду", question2.getId() + 1));
            question2.answers.add(new Answer(5L, question2.getId(), "Солгать о себе", -3L));

            Question win = new Question(3L, QuestId, "Тебя вернули домой. Победа!");
            Question losing3 = new Question(-3L, QuestId, "Твою ложь разоблачили. Поражение");

            Collection<Question> spaceQuestQuestions = new ArrayList<>();
            spaceQuestQuestions.add(question0);
            spaceQuestQuestions.add(question1);
            spaceQuestQuestions.add(question2);
            spaceQuestQuestions.add(losing1);
            spaceQuestQuestions.add(losing2);
            spaceQuestQuestions.add(losing3);
            spaceQuestQuestions.add(win);
            return spaceQuestQuestions;
        } else {
            throw new IllegalStateException("No such quest!");
        }
    }
}
