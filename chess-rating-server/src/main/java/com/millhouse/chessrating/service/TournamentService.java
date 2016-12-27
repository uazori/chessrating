package com.millhouse.chessrating.service;

import com.millhouse.chessrating.dto.TournamentDto;
import com.millhouse.chessrating.model.Tournament;

import java.util.List;


public interface TournamentService {

    TournamentDto findById(Long id);
    void saveOrUpdate(TournamentDto tournamentDto);
    List<TournamentDto> findAllTournaments();

}
