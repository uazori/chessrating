package com.millhouse.chessrating.dao;

import com.millhouse.chessrating.model.InitialRating;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Millhouse on 12/27/2016.
 */
@Service("InitialRatingDao")
public class InitialRatingDaoImp implements InitialRatingDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveOrUpdate(InitialRating rating) {

        sessionFactory.getCurrentSession().save(rating);
    }
}
