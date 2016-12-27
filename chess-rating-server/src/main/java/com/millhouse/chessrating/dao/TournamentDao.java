package com.millhouse.chessrating.dao;

import com.millhouse.chessrating.model.Tournament;

import java.util.List;

public interface TournamentDao {
    Tournament getById(Long id);
    void saveOrUpdate(Tournament tournament);
    List<Tournament> getAllTournaments();
}
