package com.millhouse.chessrating.controller;

import com.millhouse.chessrating.dto.GameDto;
import com.millhouse.chessrating.model.Player;
import com.millhouse.chessrating.service.GameService;
import com.millhouse.chessrating.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Created by Vadim Ovcharuk uazori@gmail.com on 11/24/2016.
 * GameController for Chess project
 */

@RestController
public class GameController {

    @Autowired
    PlayerService playerService;
    @Autowired
    GameService gameService;

    //-------------------Retrieve Game by Id--------------------------------------------------------

    @RequestMapping(value = "/game/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameDto> getGame(@PathVariable("id") Long id) {



        GameDto gameDto = gameService.findById(id);

        if (gameDto.getId() == null) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<>(gameDto, HttpStatus.OK);
    }

    //-------------------Create a Game--------------------------------------------------------

    @RequestMapping(value = "/game/", method = RequestMethod.POST)
    public ResponseEntity<GameDto> createGame(@RequestBody GameDto gameDto, UriComponentsBuilder ucBuilder) {




        if (gameDto.getId()==null) {

            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        gameService.saveOrUpdateGame(gameDto);


        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/game/{id}").buildAndExpand(gameDto.getId()).toUri());
        return new ResponseEntity<>(gameDto, HttpStatus.CREATED);
    }

    //------------------- Update a Game --------------------------------------------------------

    @RequestMapping(value = "/game/{id}", method = RequestMethod.PUT)
    public ResponseEntity<GameDto> updateGame(@PathVariable("id") Long id, @RequestBody GameDto gameDto) {


        GameDto currentGame = gameService.findById(id);

        if (currentGame.getId() == null) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        currentGame.setWhiteId(gameDto.getWhiteId());
        currentGame.setBlackId(gameDto.getBlackId());
        currentGame.setWinnerId(gameDto.getWinnerId());
        currentGame.setResult(gameDto.getResult());
        currentGame.setStart(gameDto.getStart());
        currentGame.setEnd(gameDto.getEnd());

        gameService.saveOrUpdateGame(currentGame);

        return new ResponseEntity<>(currentGame, HttpStatus.OK);
    }

    //-------------------Retrieve Game by PlayerId--------------------------------------------------------

    @RequestMapping(value = "/gamesByPlayerId/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GameDto>> getGamesByPlayerId(@PathVariable("id") Long id) {



        List<GameDto> gamesDto = gameService.findByPlayerId(id);

        if (gamesDto.isEmpty()) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<>(gamesDto, HttpStatus.OK);
    }
//------------------- Retrieve Game by Player name --------------------------------------------------------

    @RequestMapping(value = "/gamesByPlayerName/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GameDto>> getGameByName(@PathVariable("name") String name) {



        List<GameDto> gamesDto = gameService.findByPlayerName(name);

        if (gamesDto.isEmpty()) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<>(gamesDto, HttpStatus.OK);
    }

//------------------- Retrieve Game by Result --------------------------------------------------------

    @RequestMapping(value = "/gamesByResult/{result}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GameDto>> getGameByResult(@PathVariable("result") String result) {



        List<GameDto> gamesDto = gameService.findByResult(result);

        if (gamesDto.isEmpty()) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<>(gamesDto, HttpStatus.OK);
    }

    //------------------------ Retrieve All Games --------------------
    @RequestMapping(value = "/games/", method = RequestMethod.GET)
    public ResponseEntity<List<GameDto>> listAllGames() {


        List<GameDto> gamesDto = gameService.findAllGames();

        if (gamesDto.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<>(gamesDto, HttpStatus.OK);
    }

    //------------------- Delete a Game --------------------------------------------------------
    @RequestMapping(value = "/game/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Player> deleteGame(@PathVariable("id") Long id) {


        GameDto gameDto = gameService.findById(id);
        if (gameDto.getId() == null) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        gameService.deleteGameById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
