package com.millhouse.chessrating.dao;

import com.millhouse.chessrating.configuration.HibernateConfiguration;
import com.millhouse.chessrating.model.Game;
import com.millhouse.chessrating.model.Player;
import com.millhouse.chessrating.model.Result;
import org.junit.Assert;
import org.junit.Assert.*;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Table;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Millhouse on 11/24/2016.
 * Tests for Game Dao implementation
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {HibernateConfiguration.class})

@Transactional
//@Rollback
public class GameDaoImpTest extends TestCase {

    @Autowired
    GameDao gameDao;
    @Autowired
    PlayerDao playerDao;

    @Test
    public void testGetById_Game_Exist_Retutn_Game() throws Exception {
        Player white = new Player("one", "oneSurname", 3);
        Player black = new Player("two", "twoSurname", 2);

        Game game = new Game(white, black, Result.MATE, LocalDateTime.now(), LocalDateTime.now());
        Game game1 = new Game(black, white, Result.STALEMATE, LocalDateTime.of(2016, 11, 28, 12, 30), LocalDateTime.now());

        gameDao.saveOrUpdate(game);
        gameDao.saveOrUpdate(game1);


        Game fetchedGame = gameDao.getById(game1.getId());

        System.out.println("fetched game " + fetchedGame);

        assertEquals(game1, fetchedGame);
    }

    @Test
    public void testGetById_Game_NO_Exist_Return_Null() throws Exception {
        Player white = new Player("Not_one", "oneSurname", 3);
        Player black = new Player("Not_two", "twoSurname", 2);

        Game game = new Game(white, black, Result.MATE, LocalDateTime.now(), LocalDateTime.now());
        Game game1 = new Game(black, white, Result.STALEMATE, LocalDateTime.of(2016, 11, 28, 12, 30), LocalDateTime.now());

        gameDao.saveOrUpdate(game);


        Game fetchedGame = gameDao.getById(game1.getId());

        System.out.println("fetched game " + fetchedGame);

        assertNull(fetchedGame);
    }

    @Test
    public void testGetByPlayerName_Game_exist_return_Games() throws Exception {
        Player white = new Player("one", "oneSurname", 3);
        Player black = new Player("two", "twoSurname", 2);

        Game game = new Game(white, black, Result.MATE, LocalDateTime.now(), LocalDateTime.now());
        Game game1 = new Game(black, white, Result.STALEMATE, LocalDateTime.of(2016, 11, 28, 12, 30), LocalDateTime.now());

        gameDao.saveOrUpdate(game);
        gameDao.saveOrUpdate(game1);

        List<Game> fetchedGame = gameDao.getByPlayerName(white.getName());

        System.out.println("fetched game " + fetchedGame);

        assertEquals(white.getName(), fetchedGame.get(0).getWhite().getName());

        assertEquals(black.getName(), fetchedGame.get(1).getWhite().getName());
    }

    @Test
    public void testGetByPlayerName_Games_not_exist_return_Null() throws Exception {
        Player white = new Player("one", "oneSurname", 3);
        Player black = new Player("two", "twoSurname", 2);

        Game game = new Game(white, black, Result.MATE, LocalDateTime.now(), LocalDateTime.now());
        Game game1 = new Game(black, white, Result.STALEMATE, LocalDateTime.of(2016, 11, 28, 12, 30), LocalDateTime.now());

        gameDao.saveOrUpdate(game);
        gameDao.saveOrUpdate(game1);

        List<Game> fetchedGame = gameDao.getByPlayerName("Three");

        System.out.println("fetched game " + fetchedGame);

        assertNull(fetchedGame);

    }

    @Test
    public void testGetByResult_Games_Exist_Return_Games() throws Exception {
        Player white = new Player("one", "oneSurname", 3);
        Player black = new Player("two", "twoSurname", 2);

        Game game = new Game(white, black, Result.MATE, LocalDateTime.now(), LocalDateTime.now());
        Game game1 = new Game(black, white, Result.STALEMATE, LocalDateTime.of(2016, 11, 28, 12, 30), LocalDateTime.now());

        gameDao.saveOrUpdate(game);
        gameDao.saveOrUpdate(game1);


        List<Game> fetchedGame = gameDao.getByResult(Result.STALEMATE);

        System.out.println("fetched game " + fetchedGame);

        assertEquals(white.getName(), white.getName());
    }


    @Test
    public void testGetByResult_Games_Not_Exist_Return_Null() throws Exception {
        Player white = new Player("NoOne", "NoOneSurname", 3);
        Player black = new Player("MoTwo", "NoTwoSurname", 2);

        Game game = new Game(white, black, Result.MATE, LocalDateTime.now(), LocalDateTime.now());
        Game game1 = new Game(black, white, Result.STALEMATE, LocalDateTime.of(2016, 11, 28, 12, 30), LocalDateTime.now());

        gameDao.saveOrUpdate(game);
        gameDao.saveOrUpdate(game1);


        List<Game> fetchedGame = gameDao.getByResult(Result.DRAW);

        System.out.println("fetched game " + fetchedGame);

        assertNull(fetchedGame);
    }

    @Test
    public void testSaveOrUpdate() throws Exception {

        Player white = new Player("one", "oneSurname", 3);
        Player black = new Player("two", "twoSurname", 2);
        Player white2 = new Player("three", "3Surname", 1);

        Set<Player> playerSet = new HashSet<>();
        playerSet.add(white);
        playerSet.add(black);


        Game game = new Game(white, black, Result.MATE, LocalDateTime.now(), LocalDateTime.now());
        Game game1 = new Game(white2, black, Result.STALEMATE, LocalDateTime.now(), LocalDateTime.now());

    /*    Set<Game> gamesWhite = new HashSet<>();

        gamesWhite.add(game);
        white.setGames(gamesWhite);

        Set<Game> gamesBlack = new HashSet<>();

        gamesBlack.add(game);
        gamesBlack.add(game1);

        black.setGames(gamesBlack);

        Set<Game> gamesWhite2 = new HashSet<>();

        gamesWhite2.add(game1);
        white2.setGames(gamesWhite2);
*/

        playerDao.saveOrUpdate(white);
        playerDao.saveOrUpdate(black);
        playerDao.saveOrUpdate(white2);

        gameDao.saveOrUpdate(game1);
        gameDao.saveOrUpdate(game);

        List<Game> games = gameDao.getAllGames();
        System.out.println("games = " + games);

        List<Player> playerList = playerDao.getAllPlayers();
        System.out.println("players = " + playerList);
        assertEquals(playerList.size(), 3);
        assertEquals(games.size(), 2);
    }

    @Test
    public void testDeleteGameById_Game_exist_return_1() throws Exception {

        Player white = new Player("oneID", "oneSurnameID", 3);
        Player black = new Player("twoID", "twoSurnameID", 2);

        Game game = new Game(white, black, Result.MATE, LocalDateTime.now(), LocalDateTime.now());
        Game game1 = new Game(black, white, Result.STALEMATE, LocalDateTime.now(), LocalDateTime.now());


        gameDao.saveOrUpdate(game);
        gameDao.saveOrUpdate(game1);

        assertEquals(1, gameDao.deleteGameById(game.getId()));

    }

    @Test
    public void testDeleteGameById_Game_not_exist_return_0() throws Exception {

        Player white = new Player("NoOneID", "oneSurnameID", 3);
        Player black = new Player("NoTwoID", "twoSurnameID", 2);

        Game game = new Game(white, black, Result.MATE, LocalDateTime.now(), LocalDateTime.now());
        Game game1 = new Game(black, white, Result.STALEMATE, LocalDateTime.now(), LocalDateTime.now());


        gameDao.saveOrUpdate(game);
        gameDao.saveOrUpdate(game1);

        assertEquals(1, gameDao.deleteGameById(game.getId()));
        assertEquals(0, gameDao.deleteGameById(game.getId()));

    }

    @Test
    public void testGetAllGames_Games_Exist_return_Games() throws Exception {

        Player white = new Player("one", "oneSurname", 3);
        Player black = new Player("two", "twoSurname", 2);

        Game game = new Game(white, black, Result.MATE, LocalDateTime.now(), LocalDateTime.now());
        Game game1 = new Game(black, white, Result.STALEMATE, LocalDateTime.now(), LocalDateTime.now());


        gameDao.saveOrUpdate(game);
        gameDao.saveOrUpdate(game1);

        List<Game> fetchedGames = gameDao.getAllGames();

        assertEquals(game1.getResult(), fetchedGames.get(1).getResult());

    }

    @Test
    public void testGetAllGames_Games_not_Exist_return_Null() throws Exception {

        List<Game> fetchedGames = gameDao.getAllGames();

        assertNull(fetchedGames);

    }

}