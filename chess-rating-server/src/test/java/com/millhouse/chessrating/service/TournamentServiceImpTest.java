package com.millhouse.chessrating.service;

import com.millhouse.chessrating.dao.GameDao;
import com.millhouse.chessrating.dao.PlayerDao;
import com.millhouse.chessrating.dao.PlayerDaoImp;
import com.millhouse.chessrating.dao.TournamentDaoImp;
import com.millhouse.chessrating.model.Game;
import com.millhouse.chessrating.model.Player;
import com.millhouse.chessrating.model.Result;
import com.millhouse.chessrating.model.Tournament;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class TournamentServiceImpTest extends TestCase {

    @Mock
    private TournamentDaoImp tournamentDao;

    @InjectMocks
    private TournamentServiceImp tournamentService;



    @Before
    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);

    }



    @Test
    public void saveOrUpdate() throws Exception {


        Set<Player> players = new HashSet<>();
        Set<Game> games = new HashSet<>();

        Player player1 = new Player(1, "FirstTestName", "FirstTestSurname", 1,true);
        Player player2 = new Player(2, "SecondTestName", "SecondTestSurname", 2,true);
        Player player3 = new Player(3, "ThirdTestName", "ThirdTestSurname", 3,true);
        Player player4 = new Player(4, "FourthTestName", "FourthTestSurname", 4,true);
        Player player5 = new Player(5, "FifthName", "FifthSurname", 5,true);

        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        games.add(new Game(1L, player1, player2, "white", Result.MATE, LocalDateTime.parse("02:02 30-11-2016", formatter), LocalDateTime.parse("02:32 30-11-2016", formatter)));
        games.add(new Game(2L, player1, player3, "white", Result.DRAW, LocalDateTime.parse("12:25 29-11-2016", formatter), LocalDateTime.parse("13:25 29-11-2016", formatter)));
        games.add(new Game(3L, player2, player3, "black", Result.STALEMATE, LocalDateTime.parse("09:43 15-11-2016", formatter), LocalDateTime.parse("11:00 15-11-2016", formatter)));
        games.add(new Game(4L, player4, player5, "white", Result.DRAW, LocalDateTime.parse("17:07 25-10-2016", formatter), LocalDateTime.parse("17:37 25-10-2016", formatter)));


        Tournament tournament = new Tournament();

        tournament.setName("2016");
        tournament.setDescription(" first tour");
        tournament.setPlayers(players);
        tournament.setGames(games);

        System.out.println("test " + tournament);
/*        tournamentService.saveOrUpdate(tournament);*/

       /* System.out.println(tournaments);*/
    }

    @Test
    public void getAllTournaments() throws Exception {

        List<Tournament> tournament = new ArrayList<>();

        when(tournamentDao.getAllTournaments()).thenReturn(tournament);

        List<Tournament> fetchedGameDto = tournamentDao.getAllTournaments();

        assertThat(fetchedGameDto).isNotNull().isNotEmpty().hasSize(4);

    }

}