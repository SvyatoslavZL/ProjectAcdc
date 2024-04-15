package com.javarush.kovalinsky.repository;

import com.javarush.kovalinsky.entity.Game;

import java.util.stream.Stream;

public class GameRepository extends BaseRepository<Game> {

    @Override
    public Stream<Game> find(Game pattern) {
        return map.values()
                .stream()
                .filter(repoGame -> nullOrEquals(pattern.getId(), repoGame.getId()))
                .filter(repoGame -> nullOrEquals(pattern.getQuestId(), repoGame.getQuestId()))
                .filter(repoGame -> nullOrEquals(pattern.getUserId(), repoGame.getUserId()))
                .filter(repoGame -> nullOrEquals(pattern.getCurrentQuestionId(), repoGame.getCurrentQuestionId()))
                .filter(repoGame -> nullOrEquals(pattern.getGameState(), repoGame.getGameState()));
    }
}
