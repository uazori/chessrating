package com.millhouse.chessrating.dao;

import com.millhouse.chessrating.model.Game;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Millhouse on 11/24/2016.
 * implementation GameDao interface
 */

@Service("gameService")
public class GameDaoImp implements GameDao {



    @Autowired
    SessionFactory sessionFactory;


    @Override
    public Game getById(Long id) {
        return null;
    }

    @Override
    public Game getByPlayerName(String name) {
        return null;
    }

    @Override
    public Game getByResult(Enum result) {
        return null;
    }

    @Override
    public void saveOrUpdate(Game game) {

    }

    @Override
    public void deleteGameById(Long id) {

    }

    @Override
    public List<Game> getAllGames() {
        return null;
    }
}
