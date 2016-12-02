package com.millhouse.chessrating.controller;

import com.millhouse.chessrating.model.Player;
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
 * Created by Millhouse on 11/17/2016.
 * Rest PlayerController  for Chess rating project
 */

@RestController

public class PlayerController {

    @Autowired
    private PlayerService playerService;

    //-------------------Retrieve All Player--------------------------------------------------------

    @RequestMapping(value = "/players/", method = RequestMethod.GET)
    public ResponseEntity<List<Player>> listAllUsers() {


        List<Player> players = playerService.findAllPlayers();


        if (players.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    //-------------------Retrieve Single Player--------------------------------------------------------

    @RequestMapping(value = "/player/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> getUser(@PathVariable("id") Long id) {



    Player player = playerService.findById(id);

        if (player== null){

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    //-------------------Create a Player--------------------------------------------------------
    @RequestMapping(value = "/player/", method = RequestMethod.POST)
    public ResponseEntity<Player> createUser(@RequestBody Player player, UriComponentsBuilder ucBuilder) {



        if ((player.getId())!=null) {

            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        playerService.savePlayer(player);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/player/{id}").buildAndExpand(player.getId()).toUri());
        return new ResponseEntity<>(player, HttpStatus.CREATED);
    }

    //------------------- Update a Player --------------------------------------------------------

    @RequestMapping(value = "/player/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Player> updateUser(@PathVariable("id") Long id, @RequestBody Player player) {


        Player currentPlayer = playerService.findById(id);

        if (currentPlayer == null) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        currentPlayer.setName(player.getName());
        currentPlayer.setSurname(player.getSurname());
        currentPlayer.setRating(player.getRating());

        playerService.updatePlayer(currentPlayer);
        return new ResponseEntity<>(currentPlayer, HttpStatus.OK);
    }

    //------------------- Delete a Player --------------------------------------------------------
    @RequestMapping(value = "/player/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Player> deleteUser(@PathVariable("id") Long id) {


        Player user = playerService.findById(id);
        if (user == null) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        playerService.deletePlayerById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
