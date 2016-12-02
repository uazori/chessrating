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
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Vadim Ovcharuk uazori@gmail.com on 11/24/2016.
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
        if (game == null) return new GameDto();
        return transformer.transform(game);
    }

    @Override
    public List<GameDto> findByPlayerName(String name) {

        GameTransformer transformer = new GameTransformer();

        List<Game> games = gameDao.getByPlayerName(name);


        return games.stream().map(transformer::transform).collect(Collectors.toList());
    }

    @Override
    public List<GameDto> findByPlayerId(Long id) {

        GameTransformer transformer = new GameTransformer();

        List<Game> games = gameDao.getByPlayerId(id);

        return games.stream().map(transformer::transform).collect(Collectors.toList());

    }

    @Override
    public List<GameDto> findByResult(String result) {

        GameTransformer transformer = new GameTransformer();

        Result resultEnum = Result.valueOf(result);

        List<Game> games = gameDao.getByResult(resultEnum);


        return games.stream().map(transformer::transform).collect(Collectors.toList());

    }

    @Override
    public boolean isGameExist(GameDto gameDto) {

        Game game = gameDao.getById(gameDto.getId());
        return game != null;
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


        GameTransformer transformer = new GameTransformer();

        List<Game> games = gameDao.getAllGames();
        return games.stream().map(transformer::transform).collect(Collectors.toList());
    }


}
