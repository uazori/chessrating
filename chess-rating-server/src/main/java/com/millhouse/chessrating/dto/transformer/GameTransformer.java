package com.millhouse.chessrating.dto.transformer;

import com.millhouse.chessrating.dto.GameDto;
import com.millhouse.chessrating.model.Game;
import com.millhouse.chessrating.model.Player;
import com.millhouse.chessrating.model.Result;

/**
 * Created by Vadim Ovcharuk uazori@gmail.com on 11/29/2016.
 * transformer for Game to Game DTO
 */
public class GameTransformer {
    public GameDto transform(Game game) {
        GameDto gameDto = new GameDto();

        gameDto.setId(game.getId());
        gameDto.setWhiteId(game.getWhite().getId());
        gameDto.setBlackId(game.getBlack().getId());
        gameDto.setWinner(game.getWinner());
        gameDto.setResult(game.getResult().toString());
        gameDto.setWhiteScore(game.getWhiteScore());
        gameDto.setBlackScore(game.getBlackScore());
        gameDto.setStart(game.getStart());
        gameDto.setEnd(game.getEnd());

        return gameDto;
    }

    public Game transform(Player white, Player black, GameDto gameDto) {


        Result result = Result.valueOf(gameDto.getResult());


        return new Game(gameDto.getId(), white, black, gameDto.getWinner(), result, gameDto.getStart(), gameDto.getEnd());



    }

}
