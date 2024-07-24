package com.javarush.kovalinsky.service;

import com.javarush.kovalinsky.dto.QuestionTo;
import com.javarush.kovalinsky.entity.Question;
import com.javarush.kovalinsky.mapping.Dto;
import com.javarush.kovalinsky.repository.Repository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
@Transactional
public class QuestionService {

    private final Repository<Question> questionRepository;

    public Optional<QuestionTo> get(long id) {
        return Optional.of(questionRepository.get(id)).map(Dto.MAPPER::from);
    }

    public Optional<QuestionTo> update(Long questionId, String text) {
        Question question = questionRepository.get(questionId);
        question.setText(text);
        questionRepository.update(question);
        return Optional.of(question).map(Dto.MAPPER::from);
    }
}
