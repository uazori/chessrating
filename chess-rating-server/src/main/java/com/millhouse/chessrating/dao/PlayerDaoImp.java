package com.millhouse.chessrating.dao;

import com.millhouse.chessrating.model.Player;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Millhouse on 11/18/2016.
 */
@Service("playerDao")
public class PlayerDaoImp implements PlayerDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Player getById(long id) {
        return null;
    }

    @Override
    public Player getByName(String name) {
        return null;
    }

    @Override
    public void savePlayer(Player player) {
    sessionFactory.getCurrentSession().persist(player);
    }

    @Override
    public void updatePlayer(Player player) {

    }

    @Override
    public void deletePlayerById(long id) {

    }

    @Override
    public List<Player> getAllPlayers() {
        return null;
    }

    @Override
    public void deleteAllPlayers() {

    }

    @Override
    public boolean isPlayerExist(Player player) {
        return false;
    }
}
