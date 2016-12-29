package com.millhouse.chessrating.dao;

import com.millhouse.chessrating.configuration.HibernateConfiguration;
import com.millhouse.chessrating.model.Game;
import com.millhouse.chessrating.model.InitialRating;
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
    @Autowired
    private InitialRatingDao initialRatingDao;

    @Test
    @Rollback(false)
    public void saveOrUpdate() throws Exception {

        List<Game> games = gameDao.getAllGames();
        List<Player> players = playerDao.getAllPlayers();

        Set<Player> playerSet = new HashSet<>(players);
        Set<Game> gameSet = new HashSet<>(games);

        Tournament tournament = tournamentDao.getById(3L);
       /*
        tournament.setPlayers(playerSet);
        tournament.setGames(gameSet);*/

        System.out.println("before save Tournament = " + tournament);

        /*tournamentDao.saveOrUpdate(tournament);*/

        System.out.println("Tournament = " + tournament);

        InitialRating rate = new InitialRating(tournament,123L,1800d);
        InitialRating rate1 = new InitialRating(tournament,124L,1500d);
        InitialRating rate2 = new InitialRating(tournament,125L,1200d);
        Set<InitialRating> initialRatings = new HashSet<>();
        initialRatings.add(rate);
        initialRatings.add(rate1);
        initialRatings.add(rate2);


        initialRatingDao.saveOrUpdate(rate);

        tournament.setInitialRatings(initialRatings);
        tournamentDao.saveOrUpdate(tournament);

       /*

        players.forEach(player ->initialRatings.add(new InitialRating(tournament,player.getId(),player.getRating()) ));

        for (InitialRating rate: initialRatings) {


            initialRatingDao.saveOrUpdate(rate);

        }

        tournament.setInitialRatings(initialRatings);
*/

       Tournament fetchedTournament = tournamentDao.getById(3L);


        System.out.println(fetchedTournament);


    }

    @Test
    public void getAll() throws Exception {

    }

}