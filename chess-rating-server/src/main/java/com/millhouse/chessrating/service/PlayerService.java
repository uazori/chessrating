package com.millhouse.chessrating.service;

import com.millhouse.chessrating.model.Player;

import java.util.List;

/**
 * Created by Millhouse on 11/17/2016.
 */
public interface PlayerService {
    Player findById(Long id);
    Player findByName(String name);
    void savePlayer(Player player);
    void updatePlayer(Player player);
    void deletePlayerById(Long id);
    List<Player> findAllPlayers();
    void deleteAllPlayers();
    boolean isPlayerExist(Player player);
}
