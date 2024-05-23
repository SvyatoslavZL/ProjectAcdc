package com.javarush.kovalinsky.repository;

import com.javarush.kovalinsky.entity.Game;
import com.javarush.kovalinsky.config.SessionCreator;

public class GameRepository extends BaseRepository<Game> {


    public GameRepository(SessionCreator sessionCreator) {
        super(Game.class, sessionCreator);
    }
}
