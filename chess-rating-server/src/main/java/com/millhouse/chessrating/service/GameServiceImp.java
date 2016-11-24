package com.millhouse.chessrating.service;

import com.millhouse.chessrating.dao.GameDao;
import com.millhouse.chessrating.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Millhouse on 11/24/2016.
 * Game service implementation
 */
@Service
@Transactional
public class GameServiceImp implements GameService{

    @Autowired
    GameDao gameDao;

    @Override
    public Game findById(Long id) {
        return null;
    }

    @Override
    public Game findByPlayerName(String name) {
        return null;
    }

    @Override
    public Game findByResult(Enum result) {
        return null;
    }

    @Override
    public void saveGame(Game game) {

    }

    @Override
    public void updateGame(Game game) {

    }

    @Override
    public void deleteGameById(Long id) {

    }

    @Override
    public List<Game> findAllGamess() {
        return null;
    }

    @Override
    public boolean isGameExist(Game game) {
        return false;
    }
}
