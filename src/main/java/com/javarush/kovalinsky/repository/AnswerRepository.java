package com.javarush.kovalinsky.repository;

import com.javarush.kovalinsky.entity.Answer;

import java.util.stream.Stream;

public class AnswerRepository extends BaseRepository<Answer> {

    @Override
    public Stream<Answer> find(Answer pattern) {
        return map.values()
                .stream()
                .filter(repoAns -> nullOrEquals(pattern.getId(), repoAns.getId()))
                .filter(repoAns -> nullOrEquals(pattern.getQuestionId(), repoAns.getQuestionId()))
                .filter(repoAns -> nullOrEquals(pattern.getText(), repoAns.getText()))
                .filter(repoAns -> nullOrEquals(pattern.getNextQuestionId(), repoAns.getNextQuestionId()));
    }
}
