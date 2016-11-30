package com.millhouse.chessrating.service;

import com.millhouse.chessrating.dao.GameDao;
import com.millhouse.chessrating.dao.PlayerDao;
import com.millhouse.chessrating.dto.GameDto;
import com.millhouse.chessrating.dto.transformer.GameTransformer;
import com.millhouse.chessrating.model.Game;
import com.millhouse.chessrating.model.Player;
import com.millhouse.chessrating.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Millhouse on 11/24/2016.
 * Game service implementation
 */
@Service
@Transactional
public class GameServiceImp implements GameService {

    @Autowired
    GameDao gameDao;

    @Autowired
    PlayerDao playerDao;

    @Override
    public GameDto findById(Long id) {

        GameTransformer transformer = new GameTransformer();
        Game game = gameDao.getById(id);
        if (game == null)return null;
        return transformer.transform(game);
    }

    @Override
    public List<GameDto> findByPlayerName(String name) {

        GameTransformer transformer = new GameTransformer();

        List<Game> games = gameDao.getByPlayerName(name);
        List<GameDto> gameDtoList = new ArrayList<>();


        for (Game game : games) {

            gameDtoList.add(transformer.transform(game));

        }


        return gameDtoList;
    }

    @Override
    public List<GameDto> findByResult(String result) {

        GameTransformer transformer = new GameTransformer();

        Result resultEnum = Result.valueOf(result);

        List<Game> games = gameDao.getByResult(resultEnum);

        List<GameDto> gameDtoList = new ArrayList<>();


        for (Game game : games) {

            gameDtoList.add(transformer.transform(game));

        }


        return gameDtoList;

    }

    @Override
    public void saveOrUpdateGame(GameDto gameDto) {

        Player white = playerDao.getById(gameDto.getWhiteId());
        Player black = playerDao.getById(gameDto.getBlackId());

        GameTransformer transformer = new GameTransformer();

        Game game = transformer.transform(white, black, gameDto);

        System.out.println("Game service save Game");

        gameDao.saveOrUpdate(game);

    }


    @Override
    public void deleteGameById(Long id) {
    gameDao.deleteGameById(id);
    }


    @Override
    public List<GameDto> findAllGames() {

       /* Player white = new Player("one", "oneSurname", 3);
        Player black = new Player("two", "twoSurname", 2);

        Game game11 = new Game(white, black,white, Result.MATE, LocalDateTime.now(), LocalDateTime.now());
        Game game22 = new Game(black, white,white, Result.STALEMATE, LocalDateTime.of(2016, 11, 28, 12, 30), LocalDateTime.now());

        playerDao.saveOrUpdate(white);
        playerDao.saveOrUpdate(black);

        gameDao.saveOrUpdate(game11);
        gameDao.saveOrUpdate(game22);

*/

        GameTransformer transformer = new GameTransformer();

        List<Game> games = gameDao.getAllGames();

        List<GameDto> gameDtoList = new ArrayList<>();


        for (Game game : games) {

            gameDtoList.add(transformer.transform(game));

        }

        return gameDtoList;
    }

    @Override
    public boolean isGameExist(Game game) {
        return false;
    }
}
