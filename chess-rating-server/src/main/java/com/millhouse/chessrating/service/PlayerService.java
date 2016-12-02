package com.millhouse.chessrating.service;

import com.millhouse.chessrating.model.Player;

import java.util.List;

/**
 * Created by Vadim Ovcharuk uazori@gmail.com on 11/17/2016.
 */
public interface PlayerService {
    Player findById(Long id);
    Player findByName(String name);
    void saveOrUpdatePlayer(Player player);
    void deletePlayerById(Long id);
    List<Player> findAllPlayers();
    boolean isPlayerExist(Player player);
}
