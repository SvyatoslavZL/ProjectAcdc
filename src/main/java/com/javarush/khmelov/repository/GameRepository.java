package com.javarush.khmelov.repository;

import com.javarush.khmelov.entity.Game;
import com.javarush.khmelov.config.SessionCreator;

public class GameRepository extends BaseRepository<Game> {


    public GameRepository(SessionCreator sessionCreator) {
        super(Game.class, sessionCreator);
    }
}
