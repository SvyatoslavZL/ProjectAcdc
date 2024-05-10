package com.javarush.khmelov.repository;

import com.javarush.khmelov.entity.Quest;
import com.javarush.khmelov.config.SessionCreator;

public class QuestRepository extends BaseRepository<Quest> {


    public QuestRepository(SessionCreator sessionCreator) {
        super(Quest.class, sessionCreator);
    }
}
