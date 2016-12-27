package com.millhouse.chessrating.service;

import com.millhouse.chessrating.dao.GameDaoImp;
import com.millhouse.chessrating.dao.PlayerDaoImp;
import com.millhouse.chessrating.dto.GameDto;
import com.millhouse.chessrating.model.Game;
import com.millhouse.chessrating.model.Player;
import com.millhouse.chessrating.model.Result;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by Vadim Ovcharuk uazori@gmail.com on 11/30/2016.
 * tests for GameService
 */

@RunWith(MockitoJUnitRunner.class)
public class GameServiceImpTest extends TestCase {


    @Mock
    private GameDaoImp gameDao;

    @Mock
    private PlayerDaoImp playerDao;

    @InjectMocks
    private GameServiceImp gameService;

    private List<Game> gamesList;
    private List<GameDto> gameDtos;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
        gamesList = getTestGamesList();
        gameDtos = getTestGameDtoList();

    }

    @Test
    public void findById_GameExist_returnGameDto() throws Exception {

        Game testGame = gamesList.get(3);

        when(gameDao.getById(4L)).thenReturn(testGame);

        GameDto fetchedGameDto = gameService.findById(4L);

        assertThat(fetchedGameDto).isNotNull().extracting("id").contains(4L);


    }

    @Test
    public void findById_GameNotExist_returnEmptyGameDto() throws Exception {



        when(gameDao.getById(4L)).thenReturn(null);

        GameDto fetchedGameDto = gameService.findById(4L);

        assertEquals(fetchedGameDto.getId(),null);


    }

    @Test
    public void findByPlayerName_GameExist_ReturnGameDtoList() throws Exception {

        List<Game> testGameList = gamesList.subList(1, 3);

        when(gameDao.getByPlayerName("FirstPlayer")).thenReturn(testGameList);

        List<GameDto> fetchedDto = gameService.findByPlayerName("FirstPlayer");

        assertThat(fetchedDto).isNotNull().isNotEmpty().hasSize(2).extracting("whiteId").contains(1L, 2L);

    }

    @Test
    public void findByPlayerId_GamesExist_returnGameDtoList() {
        List<Game> testGameList = gamesList.subList(1, 3);

        when(gameDao.getByPlayerId(1L)).thenReturn(testGameList);

        List<GameDto> fetchedDto = gameService.findByPlayerId(1L);

        assertThat(fetchedDto).isNotNull().isNotEmpty().hasSize(2).extracting("whiteId").contains(1L, 2L);

    }

    @Test
    public void findByPlayerId_GamesNotExist_returnEmptyGameDtoList() {

        List<Game> testGameList = new ArrayList<>();

        when(gameDao.getByPlayerId(1L)).thenReturn(testGameList);

        List<GameDto> fetchedDto = gameService.findByPlayerId(1L);

        assertThat(fetchedDto).isEmpty();

    }


    @Test
    public void findByResult_GamesExist_returnGameDtoList() throws Exception {

        List<Game> testGameList = new ArrayList<>();

        testGameList.add(gamesList.get(1));
        testGameList.add(gamesList.get(3));


        when(gameDao.getByResult(Result.DRAW)).thenReturn(testGameList);

        List<GameDto> fetchedDto = gameService.findByResult("DRAW");

        assertThat(fetchedDto).isNotNull().isNotEmpty().hasSize(2).extracting("result").contains("DRAW", "DRAW");
    }

    @Test
    public void saveOrUpdateGame_updateGame() throws Exception {

        Game game = gamesList.get(2);

        doNothing().when(gameDao).saveOrUpdate(game);
        when(playerDao.getById(game.getWhite().getId())).thenReturn(game.getWhite());
        when(playerDao.getById(game.getBlack().getId())).thenReturn(game.getBlack());


        GameDto gameDto = gameDtos.get(2);

        gameService.saveOrUpdateGame(gameDto);

        verify(gameDao, atLeastOnce()).saveOrUpdate(game);

    }


    @Test
    public void findAllGames_GameExist_returnGameDtoList() throws Exception {

        when(gameDao.getAllGames()).thenReturn(gamesList);

        List<GameDto> fetchedGameDto = gameService.findAllGames();

        assertThat(fetchedGameDto).isNotNull().isNotEmpty().hasSize(4);//.extracting("whiteId").contains(1L,2L);
    }

    @Test
    public void findAllGames_GamesNotExist_returnEmptyGameDtoList() throws Exception {

        List<Game> gamesEmpty = new ArrayList<>();

        when(gameDao.getAllGames()).thenReturn(gamesEmpty);

        List<GameDto> fetchedGameDto = gameService.findAllGames();

        assertThat(fetchedGameDto).isEmpty();//.extracting("whiteId").contains(1L,2L);
    }


    private List<Game> getTestGamesList() {


        List<Player> players = new ArrayList<>();
        List<Game> games = new ArrayList<>();

        Player player1 = new Player(1, "FirstTestName", "FirstTestSurname", 1,true);
        Player player2 = new Player(2, "SecondTestName", "SecondTestSurname", 2,true);
        Player player3 = new Player(3, "ThirdTestName", "ThirdTestSurname", 3,true);
        Player player4 = new Player(4, "FourthTestName", "FourthTestSurname", 4,true);
        Player player5 = new Player(5, "FifthName", "FifthSurname", 5,true);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        games.add(new Game(1L, player1, player2, "white", Result.MATE, LocalDateTime.parse("02:02 30-11-2016", formatter), LocalDateTime.parse("02:32 30-11-2016", formatter)));
        games.add(new Game(2L, player1, player3, "white", Result.DRAW, LocalDateTime.parse("12:25 29-11-2016", formatter), LocalDateTime.parse("13:25 29-11-2016", formatter)));
        games.add(new Game(3L, player2, player3, "black", Result.STALEMATE, LocalDateTime.parse("09:43 15-11-2016", formatter), LocalDateTime.parse("11:00 15-11-2016", formatter)));
        games.add(new Game(4L, player4, player5, "white", Result.DRAW, LocalDateTime.parse("17:07 25-10-2016", formatter), LocalDateTime.parse("17:37 25-10-2016", formatter)));

        return games;
    }

    private List<GameDto> getTestGameDtoList() {

        List<GameDto> gameDtoList = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        gameDtoList.add(new GameDto(1L, 1L, 2L, "white", "MATE",20D,-20D, LocalDateTime.parse("02:02 30-11-2016", formatter), LocalDateTime.parse("02:32 30-11-2016", formatter)));
        gameDtoList.add(new GameDto(2L, 1L, 3L, "white", "DRAW",20D,-20D, LocalDateTime.parse("12:25 29-11-2016", formatter), LocalDateTime.parse("13:25 29-11-2016", formatter)));
        gameDtoList.add(new GameDto(3L, 2L, 3L, "black", "STALEMATE",20D,-20D, LocalDateTime.parse("09:43 15-11-2016", formatter), LocalDateTime.parse("11:00 15-11-2016", formatter)));
        gameDtoList.add(new GameDto(4L, 5L, 5L, "white", "DRAW",20D,-20D, LocalDateTime.parse("17:07 25-10-2016", formatter), LocalDateTime.parse("17:37 25-10-2016", formatter)));

        return gameDtoList;


    }
}