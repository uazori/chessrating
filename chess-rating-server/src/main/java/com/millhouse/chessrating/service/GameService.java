package com.millhouse.chessrating.service;

import com.millhouse.chessrating.dto.GameDto;
import com.millhouse.chessrating.model.Game;

import java.util.List;

/**
 * Created by Millhouse on 11/24/2016.
 * Interface for Game Service
 */
public interface GameService {
    GameDto
    findById(Long id);

    List<GameDto> findByPlayerName(String name);

    List<GameDto> findByResult(String result);

    void saveOrUpdateGame(GameDto gameDto);

    void deleteGameById(Long id);

    List<GameDto> findAllGames();

    boolean isGameExist(Game game);
}
