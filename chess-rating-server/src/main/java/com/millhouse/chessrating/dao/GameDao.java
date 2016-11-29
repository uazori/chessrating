package com.millhouse.chessrating.dao;

import com.millhouse.chessrating.model.Game;
import com.millhouse.chessrating.model.Result;

import java.util.List;

/**
 * Created by Millhouse on 11/24/2016.
 * Interface for Game Data access object
 */
public interface GameDao {
    Game getById(Long id);
    List<Game> getByPlayerName(String name);
    List<Game> getByResult(Result result);
    void saveOrUpdate(Game game);
    int deleteGameById(Long id);
    List<Game> getAllGames();
}
