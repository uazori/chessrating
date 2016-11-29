package com.millhouse.chessrating.controller;

import com.millhouse.chessrating.dto.GameDto;
import com.millhouse.chessrating.dto.transformer.SampleDto;
import com.millhouse.chessrating.model.Game;
import com.millhouse.chessrating.model.Player;
import com.millhouse.chessrating.model.Result;
import com.millhouse.chessrating.service.GameService;
import com.millhouse.chessrating.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Millhouse on 11/24/2016.
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

        System.out.println("Find game by id");

        GameDto gameDto = gameService.findById(id);

        if (gameDto== null){
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<>(gameDto, HttpStatus.OK);
    }

    //-------------------Create a Game--------------------------------------------------------

    @RequestMapping(value = "/game/", method = RequestMethod.POST)
    public ResponseEntity<SampleDto> createGame(@RequestBody SampleDto sampleDto, UriComponentsBuilder ucBuilder) {

        System.out.println("sampleDto = " + sampleDto.getId());

        GameDto gameDto = new GameDto();

        System.out.println("Creating Game with white player id = " + gameDto.getBlackId() + " black player id = " + gameDto.getWhiteId());


        System.out.println("white id =" + gameDto.getWhiteId() + "black id " + gameDto.getBlackId());



        /*if (playerService.isPlayerExist(player)) {
            System.out.println("A Game with name " + player.getName() + " already exist");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }*/

     /*   gameService.saveOrUpdateGame(gameDto);*/


        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/game/{id}").buildAndExpand(gameDto.getId()).toUri());
        return new ResponseEntity<>(sampleDto, HttpStatus.CREATED);
    }

//------------------- Retrieve Game by Player name --------------------------------------------------------

    @RequestMapping(value = "/gamesName/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GameDto>> getGameByName(@PathVariable("name") String name) {

        System.out.println("Find Game by Player name");

        List<GameDto> gamesDto = gameService.findByPlayerName(name);

        if (gamesDto.isEmpty()) {
            System.out.println("Game with player " + name + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<>(gamesDto, HttpStatus.OK);
    }

//------------------- Retrieve Game by Result --------------------------------------------------------

    @RequestMapping(value = "/gamesResult/{result}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GameDto>> getGameByResult(@PathVariable("result") String result) {

        System.out.println("Find Game by Player name");


        List<GameDto> gamesDto = gameService.findByResult(result);

        if (gamesDto.isEmpty()) {
            System.out.println("Game with player " + result + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<>(gamesDto, HttpStatus.OK);
    }

    //------------------------ Retrieve All Games --------------------
    @RequestMapping(value = "/games/", method = RequestMethod.GET)
    public ResponseEntity<List<GameDto>> listAllUsers() {


        System.out.println("Retrieve All Games");

        List<GameDto> gamesDto = gameService.findAllGames();

        if (gamesDto.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<>(gamesDto, HttpStatus.OK);
    }

    //------------------- Delete a Player --------------------------------------------------------
    @RequestMapping(value = "/game/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Player> deleteGame(@PathVariable("id") Long id) {
        System.out.println("Fetching & Deleting Game with id " + id);

        GameDto gameDto = gameService.findById(id);
        if (gameDto == null) {
            System.out.println("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        gameService.deleteGameById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
