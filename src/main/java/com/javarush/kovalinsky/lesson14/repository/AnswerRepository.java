package com.javarush.kovalinsky.lesson14.repository;

import com.javarush.kovalinsky.lesson14.entity.Answer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AnswerRepository implements Repository<Answer> {

    private final Map<Long, Answer> map = new HashMap<>();

    public AnswerRepository() {
        map.put(1L, new Answer(1L, 1L, "Принять вызов", 2L));
        map.put(2L, new Answer(2L, 1L, "Отклонить вызов", -1L));
        map.put(3L, new Answer(3L, 2L, "Подняться на мостик", 3L));
        map.put(4L, new Answer(4L, 2L, "Отказаться подниматься на мостик", -2L));
        map.put(5L, new Answer(5L, 3L, "Рассказать о себе правду", 4L));
        map.put(6L, new Answer(6L, 3L, "Солгать о себе", -3L));
    }

    @Override
    public Collection<Answer> getAll() {
        return map.values();
    }

    public Collection<Answer> getAnswersByQuestionId(long questionId) {
        ArrayList<Answer> resultAnswers = new ArrayList<>();
        for (Answer answer : map.values()) {
            if (answer.getQuestionId() == questionId) {
                resultAnswers.add(answer);
            }
        }
        return resultAnswers;
    }
}
