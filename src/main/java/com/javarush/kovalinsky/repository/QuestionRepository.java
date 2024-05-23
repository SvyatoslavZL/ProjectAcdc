package com.javarush.kovalinsky.repository;

import com.javarush.kovalinsky.entity.Question;
import com.javarush.kovalinsky.config.SessionCreator;

public class QuestionRepository extends BaseRepository<Question> {


    public QuestionRepository(SessionCreator sessionCreator) {
        super(Question.class, sessionCreator);
    }
}
