package com.javarush.kovalinsky.repository;

import com.javarush.kovalinsky.entity.Question;

import java.util.stream.Stream;

public class QuestionRepository extends BaseRepository<Question> {

    @Override
    public Stream<Question> find(Question pattern) {
        return map.values()
                .stream()
                .filter(repoQue -> nullOrEquals(pattern.getId(), repoQue.getId()))
                .filter(repoQue -> nullOrEquals(pattern.getQuestId(), repoQue.getQuestId()))
                .filter(repoQue -> nullOrEquals(pattern.getText(), repoQue.getText()))
                .filter(repoQue -> nullOrEquals(pattern.getGameState(), repoQue.getGameState()));
    }
}
