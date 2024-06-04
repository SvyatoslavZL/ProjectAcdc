package com.javarush.khmelov.service;

import com.javarush.khmelov.entity.Question;
import com.javarush.khmelov.repository.Repository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.Optional;

@AllArgsConstructor
@Transactional
public class QuestionService {

    private final Repository<Question> questionRepository;

    public Optional<Question> get(long id) {
        return Optional.of(questionRepository.get(id));
    }

    @SneakyThrows
    public Optional<Question> update(Long questionId, String text) {
        Question question = questionRepository.get(questionId);
        question.setText(text);
        questionRepository.update(question);
        return Optional.of(question);
    }
}
