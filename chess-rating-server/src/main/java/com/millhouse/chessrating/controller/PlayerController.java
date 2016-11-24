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

        System.out.println("Retrieve All Player");
        List<Player> players = playerService.findAllPlayers();


        if (players.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    //-------------------Retrieve Single Player--------------------------------------------------------

    @RequestMapping(value = "/player/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> getUser(@PathVariable("id") Long id) {

        System.out.println("Find user by id");

    Player player = playerService.findById(id);

        if (player== null){
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    //-------------------Create a Player--------------------------------------------------------
    @RequestMapping(value = "/player/", method = RequestMethod.POST)
    public ResponseEntity<Player> createUser(@RequestBody Player player, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating User " + player.getName());


        if (playerService.isPlayerExist(player)) {
            System.out.println("A User with name " + player.getName() + " already exist");
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
        System.out.println("Updating User " + id);

        Player currentPlayer = playerService.findById(id);

        if (currentPlayer == null) {
            System.out.println("User with id " + id + " not found");
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
        System.out.println("Fetching & Deleting User with id " + id);

        Player user = playerService.findById(id);
        if (user == null) {
            System.out.println("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        playerService.deletePlayerById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
