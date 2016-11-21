package com.millhouse.chessrating.dao;

import com.millhouse.chessrating.model.Player;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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
    public Player getById(Long id) {


        Query query = sessionFactory.getCurrentSession().createQuery("from Player where id =:playerId");
        query.setParameter("playerId", id);
        List<?> playerList = query.list();
        if (playerList.size() == 0){throw new RuntimeException("Player with id "+id+" not found");}
        return (Player) playerList.get(0);

    }

    @Override
    public Player getByName(String name) {

        Query query = sessionFactory.getCurrentSession().createQuery("from Player where name =:playerName");
        query.setParameter("playerName", name);
        List<?> playerList = query.list();
        if (playerList.size() == 0) {
            return null;
        }
        return (Player) playerList.get(0);
    }

    @Override
    public void savePlayer(Player player) {
        sessionFactory.getCurrentSession().persist(player);
    }

    @Override
    public void updatePlayer(Player player) {
        sessionFactory.getCurrentSession().saveOrUpdate(player);
    }

    @Override
    public void deletePlayerById(Long id) {
    Query query = sessionFactory.getCurrentSession().createQuery("delete Player where id =:playerId");
        query.setParameter("playerId", id);
        int result = query.executeUpdate();
    }

    @Override
    public List<Player> getAllPlayers() {

        Query query = sessionFactory.getCurrentSession().createQuery("from Player ");

        return query.list();
    }

    @Override
    public void deleteAllPlayers() {

    }

    @Override
    public boolean isPlayerExist(Player player) {
        return false;
    }
}
