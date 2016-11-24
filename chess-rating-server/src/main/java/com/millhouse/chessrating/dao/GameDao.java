package com.millhouse.chessrating.dao;

import com.millhouse.chessrating.model.Game;

import java.util.List;

/**
 * Created by Millhouse on 11/24/2016.
 * Interface for Game Data access object
 */
public interface GameDao {
    Game getById(Long id);
    Game getByPlayerName(String name);
    Game getByResult(Enum result);
    void saveOrUpdate(Game game);
    void deleteGameById(Long id);
    List<Game> getAllGames();
}
