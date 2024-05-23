package com.javarush.kovalinsky.repository;

import com.javarush.kovalinsky.entity.Quest;
import com.javarush.kovalinsky.config.SessionCreator;

public class QuestRepository extends BaseRepository<Quest> {


    public QuestRepository(SessionCreator sessionCreator) {
        super(Quest.class, sessionCreator);
    }
}
