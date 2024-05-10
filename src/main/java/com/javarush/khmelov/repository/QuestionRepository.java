package com.javarush.khmelov.repository;

import com.javarush.khmelov.entity.Question;
import com.javarush.khmelov.config.SessionCreator;

public class QuestionRepository extends BaseRepository<Question> {


    public QuestionRepository(SessionCreator sessionCreator) {
        super(Question.class, sessionCreator);
    }
}
