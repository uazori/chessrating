package com.millhouse.chessrating.dto.transformer;

import com.millhouse.chessrating.dto.GameDto;
import com.millhouse.chessrating.dto.TournamentDto;
import com.millhouse.chessrating.model.Game;
import com.millhouse.chessrating.model.Player;
import com.millhouse.chessrating.model.Result;
import com.millhouse.chessrating.model.Tournament;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class TournamentTransformer {


    public TournamentDto transform(Tournament tournament, boolean withPlayersAndGames) {

        TournamentDto tournamentDto = new TournamentDto();

        tournamentDto.setId(tournament.getId());
        tournamentDto.setName(tournament.getName());
        tournamentDto.setDescription(tournament.getDescription());
        tournamentDto.setSystem(tournament.getSystem());

        tournamentDto.setStart(tournament.getStart());
        tournamentDto.setEnd(tournament.getEnd());

        Set<Game> games = tournament.getGames();
        Set<Player> players = tournament.getPlayers();

        if (withPlayersAndGames) {

            GameTransformer transformer = new GameTransformer();

            tournamentDto.setGameDtos(games.stream().map(transformer::transform).collect(Collectors.toList()));
            tournamentDto.setPlayers(new ArrayList<>(players));
        } else {

            tournamentDto.setGameDtos(new ArrayList<>(games.size()));
            tournamentDto.setPlayers(new ArrayList<>(players.size()));

            return tournamentDto;

        }
        return tournamentDto;
    }

    public Tournament transform(TournamentDto tournamentDto, List<Player> players) {

        Tournament tournament = new Tournament();

        tournament.setId(tournamentDto.getId());
        tournament.setName(tournamentDto.getName());
        tournament.setDescription(tournamentDto.getDescription());
        tournament.setSystem(tournamentDto.getSystem());
        tournament.setPlayers(new HashSet<>(tournamentDto.getPlayers()));

        tournament.setStart(tournamentDto.getStart());
        tournament.setEnd(tournamentDto.getEnd());

        List<GameDto> gameDtos = tournamentDto.getGameDtos();

        GameTransformer transformer = new GameTransformer();

        Set<Game> games = gameDtos.stream()
                .map(g -> {
                    Player white = players.stream().filter(player -> player.getId().equals(g.getWhiteId())).findAny().get();
                    Player black = players.stream().filter(player -> player.getId().equals(g.getBlackId())).findAny().get();
                    return transformer.transform(white, black, g);

                })
                .collect(Collectors.toSet());

        tournament.setGames(games);


       /* Result result = Result.valueOf(gameDto.getResult());*/


        /*Game newGame = new Game(gameDto.getId(), white, black, gameDto.getWinner(), result, gameDto.getStart(), gameDto.getEnd());*/


        return tournament;
    }
}
