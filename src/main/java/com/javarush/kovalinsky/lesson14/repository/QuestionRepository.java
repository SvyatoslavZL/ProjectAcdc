package com.javarush.kovalinsky.lesson14.repository;

import com.javarush.kovalinsky.lesson14.entity.Answer;
import com.javarush.kovalinsky.lesson14.entity.Question;

import java.util.*;

public class QuestionRepository implements Repository<Question> {

    private final Map<Long, Question> map = new HashMap<>();

    public QuestionRepository(AnswerRepository answerRepository) {
        map.put(1L, new Question(1L, "Ты потерял память. Принять вызов НЛО?", Collections.emptyList()));
        map.put(-1L, new Question(-1L, "Ты отклонил вызов. Поражение", Collections.emptyList()));
        map.put(2L, new Question(2L, "Ты принял вызов. Поднимешься на мостик к капитану?", Collections.emptyList()));
        map.put(-2L, new Question(-2L, "Ты не пошёл на переговоры. Поражение", Collections.emptyList()));
        map.put(3L, new Question(3L, "Ты поднялся на мостик. Кто ты?", Collections.emptyList()));
        map.put(-3L, new Question(-3L, "Твою ложь разоблачили. Поражение", Collections.emptyList()));
        map.put(4L, new Question(4L, "Тебя вернули домой. Победа!", Collections.emptyList()));

        for (Question question : map.values()) {
            question.answers = (List<Answer>) answerRepository.getAnswersByQuestionId(question.getId());
        }
    }

    @Override
    public Collection<Question> getAll() {
        return map.values();
    }
}
