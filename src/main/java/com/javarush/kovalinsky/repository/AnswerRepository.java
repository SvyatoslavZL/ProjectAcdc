package com.javarush.kovalinsky.repository;

import com.javarush.kovalinsky.entity.Answer;
import com.javarush.kovalinsky.config.SessionCreator;

public class AnswerRepository extends BaseRepository<Answer> {


    public AnswerRepository(SessionCreator sessionCreator) {
        super(Answer.class, sessionCreator);
    }
}
