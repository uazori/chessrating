package com.millhouse.chessrating.dao;

import com.millhouse.chessrating.model.Game;
import com.millhouse.chessrating.model.Result;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
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

        Query query = sessionFactory.getCurrentSession().createQuery("from Game game where game.id =:gameId");
        query.setParameter("gameId", id);

        Game result;

        try {
            result = (Game) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return result;
    }

    @Override
    public List<Game> getByPlayerName(String name) {

        Query whitePlayerQuery = sessionFactory.getCurrentSession().createQuery("from Game game where game.white.name =:name");
        whitePlayerQuery.setParameter("name", name);
        List<Game> whitePlayerResult = whitePlayerQuery.getResultList();

        Query blackPlayerQuery = sessionFactory.getCurrentSession().createQuery("from Game game where game.black.name =:name");
        blackPlayerQuery.setParameter("name", name);
        List<Game> blackPlayerResult = blackPlayerQuery.getResultList();

        whitePlayerResult.addAll(blackPlayerResult);

        if (whitePlayerResult.isEmpty()) {
            return null;
        }
        return whitePlayerResult;
    }

    @Override
    public List<Game> getByResult(Result result) {

        Query whitePlayerQuery = sessionFactory.getCurrentSession().createQuery("from Game game where game.result =:result");
        whitePlayerQuery.setParameter("result", result);
        List<Game> whitePlayerResult = whitePlayerQuery.getResultList();

        if (whitePlayerResult.isEmpty()) {
            return null;
        }

        return whitePlayerResult;
    }

    @Override
    public void saveOrUpdate(Game game) {
        sessionFactory.getCurrentSession().saveOrUpdate(game);
    }

    @Override
    public int deleteGameById(Long id) {

        Query Query = sessionFactory.getCurrentSession().createQuery("delete from Game game where game.id =:gameId");
        Query.setParameter("gameId", id);

        return Query.executeUpdate();

    }

    @Override
    public List<Game> getAllGames() {

        Query query = sessionFactory.getCurrentSession().createQuery("from Game ");

        List<Game> result = query.getResultList();

        if (result.isEmpty()) return null;

        return result;

    }
}
