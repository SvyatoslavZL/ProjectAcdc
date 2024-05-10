package com.javarush.khmelov.repository;

import com.javarush.khmelov.entity.Answer;
import com.javarush.khmelov.config.SessionCreator;

public class AnswerRepository extends BaseRepository<Answer> {


    public AnswerRepository(SessionCreator sessionCreator) {
        super(Answer.class, sessionCreator);
    }
}
