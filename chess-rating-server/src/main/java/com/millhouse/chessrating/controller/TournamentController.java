package com.millhouse.chessrating.controller;

import com.millhouse.chessrating.dto.TournamentDto;
import com.millhouse.chessrating.model.Tournament;
import com.millhouse.chessrating.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;



@RestController
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    //-------------------Retrieve Tournament by Id--------------------------------------------------------

    @RequestMapping(value = "/tournament/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TournamentDto> getTournament(@PathVariable("id") Long id) {



        TournamentDto tournamentDto = tournamentService.findById(id);
/*
        if (tournamentDto.getId() == null) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }*/


        return new ResponseEntity<>(tournamentDto, HttpStatus.OK);
    }

    //-------------------Create a Tournament--------------------------------------------------------

    @RequestMapping(value = "/tournament", method = RequestMethod.POST)
    public ResponseEntity<Object> createTournament(@RequestBody TournamentDto tournament, UriComponentsBuilder ucBuilder) {


        System.out.println("create = " + tournament);
        if (tournament.getId()!=null) {

            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        tournamentService.saveOrUpdate(tournament);


       /* HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/tournament/{id}").buildAndExpand(tournament.getId()).toUri());*/
        return new ResponseEntity<>(tournament, HttpStatus.CREATED);
    }

    //------------------- Update a Tournament --------------------------------------------------------

    @RequestMapping(value = "/tournament/{id}", method = RequestMethod.PUT)
    public ResponseEntity<TournamentDto> updateTournament(@PathVariable("id") Long id, @RequestBody TournamentDto tournamentDto) {

        System.out.println(tournamentDto);

        if (tournamentDto.getId() == null) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        tournamentService.saveOrUpdate(tournamentDto);

        return new ResponseEntity<>(tournamentDto, HttpStatus.OK);
    }

    //------------------------ Retrieve All Tournament --------------------
    @RequestMapping(value = "/tournament", method = RequestMethod.GET)
    public ResponseEntity<List<TournamentDto>> listAllTournaments() {

        System.out.println("all tournament");

        List<TournamentDto> tournamentDtos = tournamentService.findAllTournaments();

        System.out.println("tournamentsDtos = " + tournamentDtos);

      /*  if (tournaments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }*/

        return new ResponseEntity<>(tournamentDtos, HttpStatus.OK);
    }
}
