package com.millhouse.chessrating.dao;

import com.millhouse.chessrating.model.InitialRating;

import java.util.List;

/**
 * Created by Millhouse on 12/27/2016.
 */
public interface InitialRatingDao {

    void saveOrUpdate(InitialRating rating);

}
