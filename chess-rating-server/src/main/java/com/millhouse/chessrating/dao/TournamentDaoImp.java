package com.millhouse.chessrating.dao;

import com.millhouse.chessrating.model.InitialRating;
import com.millhouse.chessrating.model.Tournament;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Set;


@Service("tournamentDao")
public class TournamentDaoImp implements TournamentDao {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public Tournament getById(Long id) {

        Query query = sessionFactory.getCurrentSession().createQuery("from Tournament tournament where tournament.id =:tournamentId");
        query.setParameter("tournamentId", id);

        Tournament result;

        try {
            result = (Tournament) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return result;

    }

    @Override
    public void saveOrUpdate(Tournament tournament) {

        System.out.println("Dao save = " + tournament);


        if (tournament.getId()==null){sessionFactory.getCurrentSession().save(tournament);}
        else {sessionFactory.getCurrentSession().saveOrUpdate(tournament);}

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Tournament> getAllTournaments() {
        return sessionFactory.getCurrentSession().createQuery("from Tournament ").getResultList();
    }
}
