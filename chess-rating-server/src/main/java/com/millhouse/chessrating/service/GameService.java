package com.millhouse.chessrating.service;

import com.millhouse.chessrating.model.Game;

import java.util.List;

/**
 * Created by Millhouse on 11/24/2016.
 * Interface for Game Service
 */
public interface GameService {
    Game findById(Long id);
    Game findByPlayerName(String name);
    Game findByResult(Enum result);
    void saveGame(Game game);
    void updateGame(Game game);
    void deleteGameById(Long id);
    List<Game> findAllGamess();
    boolean isGameExist(Game game);
}
