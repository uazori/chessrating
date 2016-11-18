package com.millhouse.chessrating.controller;

import com.millhouse.chessrating.model.Player;
import com.millhouse.chessrating.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Millhouse on 11/17/2016.
 */
@RestController
public class HelloWorldController {

    @Autowired
    PlayerService playerService;

    @RequestMapping(value = "/players/", method = RequestMethod.GET)
    public ResponseEntity<List<Player>> listAllUsers() {

        System.out.println("Inside List all users");
        List<Player> players = playerService.findAllPlayers();

        if (players.isEmpty()) {
            return new ResponseEntity<List<Player>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Player>>(players, HttpStatus.OK);
    }

    @RequestMapping(value = "/plauers/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> getUser(@PathVariable("id") long id) {



    Player player = playerService.findById(id);

        if (player== null){
            System.out.println("User with id " + id + "not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @RequestMapping(value = "/player/", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody Player player, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating User " + player.getName());

        if (playerService.isPlayerExist(player)) {
            System.out.println("A User with name " + player.getName() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        playerService.savePlayer(player);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/player/{id}").buildAndExpand(player.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

}
