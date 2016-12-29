package com.millhouse.chessrating.service;

import com.millhouse.chessrating.dao.PlayerDao;
import com.millhouse.chessrating.dao.TournamentDao;
import com.millhouse.chessrating.dto.TournamentDto;
import com.millhouse.chessrating.dto.transformer.GameTransformer;
import com.millhouse.chessrating.dto.transformer.TournamentTransformer;
import com.millhouse.chessrating.model.Game;
import com.millhouse.chessrating.model.InitialRating;
import com.millhouse.chessrating.model.Player;
import com.millhouse.chessrating.model.Tournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;



@Service
@Transactional
public class TournamentServiceImp implements TournamentService {


    @Autowired
    private TournamentDao tournamentDao;

    @Autowired

    private PlayerDao playerDao;

    private TournamentTransformer tournamentTransformer = new TournamentTransformer();

    @Override
    public TournamentDto findById(Long id) {

        Tournament tournament = tournamentDao.getById(id);

        if (tournament == null) {
            return new TournamentDto();
        }

        return tournamentTransformer.transform(tournament, true);


    }


    @Override
    public void saveOrUpdate(TournamentDto tournamentDto) {

        TournamentTransformer tournamentTransformer = new TournamentTransformer();


        List<Player> players = playerDao.getAllPlayers();

        Tournament tournament = tournamentTransformer.transform(tournamentDto,players);


        Set<InitialRating> initialRatings = tournament.getInitialRatings();

        initialRatings.forEach(rating -> rating.setTournament(tournament));

        tournament.setInitialRatings(initialRatings);

        System.out.println("initial ratings");

        System.out.println(initialRatings);

        tournamentDao.saveOrUpdate(tournament);
    }

    @Override
    public List<TournamentDto> findAllTournaments() {


        List<Tournament> tournaments = tournamentDao.getAllTournaments();

        List<TournamentDto> tournamentDtos = tournaments.stream().map(t -> tournamentTransformer.transform(t, true)).collect(Collectors.toList());

        return tournamentDtos;

    }
}
