package com.millhouse.chessrating.dao;

import com.millhouse.chessrating.model.Player;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * Created by Millhouse on 11/18/2016.
 * Player Dao implementation
 */
@Service("playerDao")
public class PlayerDaoImp implements PlayerDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Player getById(Long id) {


        Query query = sessionFactory.getCurrentSession().createQuery("from Player where id =:playerId");
        query.setParameter("playerId", id);
        List<Player> playerList = query.getResultList();
        if (playerList == null || playerList.isEmpty()) {
            return null;
        }
        return playerList.get(0);

    }

    @Override
    public Player getByName(String name) {

        Query query = sessionFactory.getCurrentSession().createQuery("from Player where name = :playerName");
        query.setParameter("playerName", name);
        Player player;
        try {
           player =  (Player) query.getSingleResult();
        }
        catch (NoResultException ex){
            return new Player();
        }
        return player;

    }

    @Override
    public void saveOrUpdate(Player player) throws ConstraintViolationException {
        if (player.getId() == null) {

            sessionFactory.getCurrentSession().save(player);


        } else {
            sessionFactory.getCurrentSession().saveOrUpdate(player);
        }
    }

    @Override
    public void deletePlayerById(Long id) {
        Query query = sessionFactory.getCurrentSession().createQuery("delete Player where id =:playerId");
        query.setParameter("playerId", id);
        query.executeUpdate();
       /* sessionFactory.getCurrentSession().clear();*/
    }

    @Override
    public List<Player> getAllPlayers() {

        Query query = sessionFactory.getCurrentSession().createQuery("from Player ");

        return query.list();
    }


}
