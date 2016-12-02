package com.millhouse.chessrating.dao;

import com.millhouse.chessrating.model.Player;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * Created by Vadim Ovcharuk uazori@gmail.com on 11/18/2016.
 * interface for Chess project PlayerDao
 */

public interface PlayerDao {
    Player getById(Long id);
    Player getByName(String name);
    void saveOrUpdate(Player player)throws ConstraintViolationException;
    void deletePlayerById(Long id);
    List<Player>  getAllPlayers();


}
