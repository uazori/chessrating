package com.millhouse.chessrating.controller;

import com.millhouse.chessrating.model.Game;
import com.millhouse.chessrating.model.Player;
import com.millhouse.chessrating.service.GameService;
import com.millhouse.chessrating.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/games/", method = RequestMethod.GET)
    public ResponseEntity<List<Game>> listAllUsers() {


        gameService.saveGame(new Game());

        System.out.println("Retrieve All Player");
       //List<Player> players =  // playerService.findAllPlayers();
        List<Game> games = new ArrayList<>();//gameService.findAllGamess();

        if (games.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<>(games, HttpStatus.OK);
    }
}
