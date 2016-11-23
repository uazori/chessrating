package com.millhouse.chessrating.dao;

import com.millhouse.chessrating.model.Player;

import java.util.List;

/**
 * Created by Millhouse on 11/18/2016.
 */

public interface PlayerDao {
    Player getById(Long id);
    Player getByName(String name);
    void saveOrUpdate(Player player);
    void deletePlayerById(Long id);
    List<Player>  getAllPlayers();


}
