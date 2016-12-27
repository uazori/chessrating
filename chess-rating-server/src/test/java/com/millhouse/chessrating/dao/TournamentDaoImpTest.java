package com.millhouse.chessrating.dao;

import com.millhouse.chessrating.configuration.HibernateConfiguration;
import com.millhouse.chessrating.model.Game;
import com.millhouse.chessrating.model.Player;
import com.millhouse.chessrating.model.Tournament;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {HibernateConfiguration.class})

@Transactional
/*@Rollback*/
public class TournamentDaoImpTest {



    @Autowired
    private GameDao gameDao;
    @Autowired
    private PlayerDao playerDao;
    @Autowired
    private TournamentDao tournamentDao;

    @Test
    /*@Rollback(false)*/
    public void saveOrUpdate() throws Exception {

        List<Game> games = gameDao.getAllGames();
        List<Player> players = playerDao.getAllPlayers();

        Set<Player> playerSet = new HashSet<>(players);
        Set<Game> gameSet = new HashSet<>(games);


        Tournament tournament = new Tournament();
        tournament.setName("last in 2016");
        tournament.setDescription("Christmas tournament ");
        tournament.setPlayers(playerSet);
        tournament.setGames(gameSet);

        System.out.println("Tournament = " + tournament);

        tournamentDao.saveOrUpdate(tournament);


       List<Tournament> fetchedTournament = tournamentDao.getAllTournaments();


        System.out.println(fetchedTournament);


    }

    @Test
    public void getAll() throws Exception {

    }

}