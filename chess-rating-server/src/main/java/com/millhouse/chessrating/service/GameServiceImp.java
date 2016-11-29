package com.millhouse.chessrating.service;

import com.millhouse.chessrating.dao.GameDao;
import com.millhouse.chessrating.dao.PlayerDao;
import com.millhouse.chessrating.model.Game;
import com.millhouse.chessrating.model.Player;
import com.millhouse.chessrating.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Millhouse on 11/24/2016.
 * Game service implementation
 */
@Service
@Transactional
public class GameServiceImp implements GameService{

    @Autowired
    GameDao gameDao;

    @Autowired
    PlayerDao playerDao;

    @Override
    public Game findById(Long id) {
        return null;
    }

    @Override
    public Game findByPlayerName(String name) {
        return null;
    }

    @Override
    public Game findByResult(Enum result) {
        return null;
    }

    @Override
    public void saveGame(Game game1111) {

        System.out.println("Game service");

        Player white = new Player("one","oneSurname",3);
        Player black = new Player("two","twoSurname",2);
        Player white2 = new Player("three","3Surname",1);

        Set<Player> playerSet = new HashSet<>();
        playerSet.add(white);
        playerSet.add(black);



        Game game = new Game(white,black, Result.MATE, LocalDateTime.now(),LocalDateTime.now());
        Game game1 = new Game(white2,black,Result.STALEMATE,LocalDateTime.now(),LocalDateTime.now());

        Set<Game> gamesWhite = new HashSet<>();

        gamesWhite.add(game);
       /* white.setGames(gamesWhite);*/

        Set<Game> gamesBlack = new HashSet<>();

        gamesBlack.add(game);
        gamesBlack.add(game1);

       /* black.setGames(gamesBlack);*/

        Set<Game> gamesWhite2 = new HashSet<>();

        gamesWhite2.add(game1);

        /*white2.setGames(gamesWhite2);*/



        playerDao.saveOrUpdate(white);
     // playerDao.saveOrUpdate(black);
      //
        // playerDao.saveOrUpdate(white2);

       // gameDao.saveOrUpdate(game1);
        gameDao.saveOrUpdate(game);

        /*List<Game> games = gameDao.getAllGames();
        System.out.println("games = "+games);

        List<Player> playerList = playerDao.getAllPlayers();
        System.out.println("players = "+ playerList);*/
    }

    @Override
    public void updateGame(Game game) {

    }

    @Override
    public void deleteGameById(Long id) {

    }

    @Override
    public List<Game> findAllGamess() {
        return gameDao.getAllGames();
    }

    @Override
    public boolean isGameExist(Game game) {
        return false;
    }
}
