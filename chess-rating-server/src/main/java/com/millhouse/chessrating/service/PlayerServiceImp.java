package com.millhouse.chessrating.service;

import com.millhouse.chessrating.dao.PlayerDao;
import com.millhouse.chessrating.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Vadim Ovcharuk uazori@gmail.com on 11/17/2016.
 * Service for chess player
 */

@Service("playerService")
@Transactional
public class PlayerServiceImp implements PlayerService {

    @Autowired
    private PlayerDao dao;

    @Override
    public Player findById(Long id) {

        Player player = dao.getById(id);

        if (player == null) {
            throw new RuntimeException("Player with id " + id + " not found");
        }

        return player;
    }

    @Override
    public Player findByName(String name) {

        return dao.getByName(name);
    }

    @Override
    public void savePlayer(Player player) {
        System.out.println("player  " + player);

       dao.saveOrUpdate(player);
    }

    @Override
    public void updatePlayer(Player player) {
        dao.saveOrUpdate(player);
    }

    @Override
    public void deletePlayerById(Long id) {

        dao.deletePlayerById(id);

    }

    @Override
    public List<Player> findAllPlayers() {
        return dao.getAllPlayers();
    }

    @Override
    public boolean isPlayerExist(Player player) {
        return findByName(player.getName()).getName()!=null ;
    }


}
