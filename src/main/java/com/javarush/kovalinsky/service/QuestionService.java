package com.javarush.kovalinsky.service;

import com.javarush.kovalinsky.entity.Question;
import com.javarush.kovalinsky.repository.QuestionRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;

@SuppressWarnings("unused")
@AllArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Optional<Question> get(long id) {
        return Optional.of(questionRepository.get(id));
    }

    public Optional<Question> update(Long questionId, String text) {
        Question question = questionRepository.get(questionId);
        question.setText(text);
        questionRepository.update(question);
        return Optional.of(question);
    }
}
