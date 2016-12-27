package com.millhouse.chessrating.service;

import com.millhouse.chessrating.dto.GameDto;

import java.util.List;

/**
 * Created by Vadim Ovcharuk uazori@gmail.com on 11/24/2016.
 * Interface for Game Service
 */
public interface GameService {
    GameDto findById(Long id);

    List<GameDto> findByPlayerName(String name);

    List<GameDto> findByResult(String result);

    List<GameDto> findByPlayerId(Long id);

    boolean isGameExist(GameDto gameDto);

    void saveOrUpdateGame(GameDto gameDto);

    void deleteGameById(Long id);

    List<GameDto> findAllGames();

}
