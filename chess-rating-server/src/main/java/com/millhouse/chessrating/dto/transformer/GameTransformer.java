package com.millhouse.chessrating.dto.transformer;

import com.millhouse.chessrating.dto.GameDto;
import com.millhouse.chessrating.model.Game;
import com.millhouse.chessrating.model.Player;
import com.millhouse.chessrating.model.Result;

/**
 * Created by Millhouse on 11/29/2016.
 * transformer for Game to Game DTO
 */
public class GameTransformer {
    public GameDto transform(Game game) {
        GameDto gameDto = new GameDto();

        gameDto.setId(game.getId());
        gameDto.setWhiteId(game.getWhite().getId());
        gameDto.setBlackId(game.getBlack().getId());
        gameDto.setWinnerId(game.getWinner().getId());
        gameDto.setResult(game.getResult().toString());
        gameDto.setStart(game.getStart());
        gameDto.setEnd(game.getEnd());

        return gameDto;
    }

    public Game transform(Player white, Player black, GameDto gameDto) {

        Result result = Result.valueOf(gameDto.getResult());

        Player winner ;
        if (white.getId().equals(gameDto.getWhiteId())) {
            winner = white;
        } else {
            winner = black;
        }



        return new Game(gameDto.getId(), white, black, winner, result, gameDto.getStart(), gameDto.getEnd());
    }

}
