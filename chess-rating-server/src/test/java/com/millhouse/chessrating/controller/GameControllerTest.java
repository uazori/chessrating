package com.millhouse.chessrating.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.millhouse.chessrating.configuration.ChessRatingConfiguration;
import com.millhouse.chessrating.dto.GameDto;
import com.millhouse.chessrating.model.Game;
import com.millhouse.chessrating.model.Player;
import com.millhouse.chessrating.service.GameServiceImp;
import com.millhouse.chessrating.service.PlayerServiceImp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by Vadim Ovcharuk uazori@gmail.com on 12/1/2016.
 *
 * tests for Game Controller
 */


@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
@EnableWebMvc
@ContextConfiguration(classes = {ChessRatingConfiguration.class})
public class GameControllerTest {

    private MediaType APPLICATION_JSON_MEDIA_TYPE;
    private MockMvc mockMvc;

    @Mock
    private GameServiceImp gameServiceMock;

    @InjectMocks
    private GameController gameController = new GameController();

    @Before
    public void setUp() {
        mockMvc = standaloneSetup(gameController).build();
        APPLICATION_JSON_MEDIA_TYPE = new MediaType(MediaType.APPLICATION_JSON.getType(),
                MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    }

    @Test
    public void getGame_gameExist_() throws Exception {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        GameDto gameDto = new GameDto(1L, 1L, 2L, 1L, "MATE", LocalDateTime.parse("02:02 30-11-2016", formatter), LocalDateTime.parse("02:32 30-11-2016", formatter));

        when(gameServiceMock.findById(1L)).thenReturn(gameDto);

        mockMvc.perform(get("/game/1")).andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_MEDIA_TYPE))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.whiteId", is(1)))
                .andExpect(jsonPath("$.blackId", is(2)))
                .andExpect(jsonPath("$.winnerId", is(1)))
                .andExpect(jsonPath("$.result", is("MATE")))
                .andExpect(jsonPath("$.start", is("2016-11-30T02:02")))
                .andExpect(jsonPath("$.end", is("2016-11-30T02:32")))
        ;

        verify(gameServiceMock, times(1)).findById(anyLong());
        verifyNoMoreInteractions(gameServiceMock);
    }


    @Test
    public void getGame_gameNotExist_returnStatusNotFound() throws Exception {


        GameDto gameDto = new GameDto();

        when(gameServiceMock.findById(1L)).thenReturn(gameDto);

        mockMvc.perform(get("/game/1")).andExpect(status().isNotFound());

        verify(gameServiceMock, times(1)).findById(anyLong());
        verifyNoMoreInteractions(gameServiceMock);
    }

    @Test
    public void createGame_gameAdded_returnGame() throws Exception {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        GameDto gameDto = new GameDto(1L, 1L, 2L, 1L, "MATE", LocalDateTime.parse("02:02 30-11-2016", formatter), LocalDateTime.parse("02:32 30-11-2016", formatter));


        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        byte[] jsonTestGame = mapper.writeValueAsBytes(gameDto);


        doNothing().when(gameServiceMock).saveOrUpdateGame(gameDto);


        mockMvc.perform(post("/game/")
                .contentType(APPLICATION_JSON_MEDIA_TYPE)
                .content(jsonTestGame))
                .andExpect(content().contentType(APPLICATION_JSON_MEDIA_TYPE))

                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.whiteId", is(1)))
                .andExpect(jsonPath("$.blackId", is(2)))
                .andExpect(jsonPath("$.winnerId", is(1)))
                .andExpect(jsonPath("$.result", is("MATE")))
                .andExpect(jsonPath("$.start", is("2016-11-30T02:02")))
                .andExpect(jsonPath("$.end", is("2016-11-30T02:32")));

        verify(gameServiceMock, times(1)).saveOrUpdateGame(gameDto);

        verifyNoMoreInteractions(gameServiceMock);
    }

    @Test
    public void createGame_gameExist_returnStatusConflict() throws Exception {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        GameDto gameDto = new GameDto(null, 1L, 2L, 1L, "MATE", LocalDateTime.parse("02:02 30-11-2016", formatter), LocalDateTime.parse("02:32 30-11-2016", formatter));

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        byte[] jsonTestGame = mapper.writeValueAsBytes(gameDto);



        mockMvc.perform(post("/game/")
                .contentType(APPLICATION_JSON_MEDIA_TYPE)
                .content(jsonTestGame))
                .andExpect(status().isConflict());


        verifyNoMoreInteractions(gameServiceMock);
    }

    @Test
    public void updateGame_GameUpdated_returnGame() throws Exception {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        GameDto gameDto = new GameDto(1L, 1L, 2L, 1L, "MATE", LocalDateTime.parse("02:02 30-11-2016", formatter), LocalDateTime.parse("02:32 30-11-2016", formatter));


        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        byte[] jsonTestGame = mapper.writeValueAsBytes(gameDto);


        doNothing().when(gameServiceMock).saveOrUpdateGame(gameDto);
       /* when(gameServiceMock.findById(1L)).thenReturn(gameDto);*/

        mockMvc.perform(post("/game/1")
                .contentType(APPLICATION_JSON_MEDIA_TYPE)
                .content(jsonTestGame))
                .andExpect(content().contentType(APPLICATION_JSON_MEDIA_TYPE))

                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.whiteId", is(1)))
                .andExpect(jsonPath("$.blackId", is(2)))
                .andExpect(jsonPath("$.winnerId", is(1)))
                .andExpect(jsonPath("$.result", is("MATE")))
                .andExpect(jsonPath("$.start", is("2016-11-30T02:02")))
                .andExpect(jsonPath("$.end", is("2016-11-30T02:32")));

        verify(gameServiceMock, times(1)).saveOrUpdateGame(gameDto);
       /* verify(gameServiceMock, times(1)).findById(1L);*/
        verifyNoMoreInteractions(gameServiceMock);

    }

    @Test
    public void updateGame_GameNotExist_returnStatusNotFound() throws Exception {

        GameDto gameDto = new GameDto();

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        byte[] jsonTestGame = mapper.writeValueAsBytes(gameDto);


        /*doNothing().when(gameServiceMock).saveOrUpdateGame(gameDto);*/
      /*  when(gameServiceMock.findById(1L)).thenReturn(gameDto);*/

        mockMvc.perform(post("/game/1")
                .contentType(APPLICATION_JSON_MEDIA_TYPE)
                .content(jsonTestGame))

                .andExpect(status().isNotFound());


      /*  verify(gameServiceMock, times(1)).findById(1L);*/
        verifyNoMoreInteractions(gameServiceMock);

    }

    @Test
    public void getGamesByPlayerId_GamesExist_returnGameDtoList() throws Exception {

        List<GameDto> gameDtoList = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        gameDtoList.add(new GameDto(1L, 1L, 2L, 1L, "MATE", LocalDateTime.parse("02:02 30-11-2016", formatter), LocalDateTime.parse("02:32 30-11-2016", formatter)));
        gameDtoList.add(new GameDto(2L, 1L, 3L, 1L, "DRAW", LocalDateTime.parse("12:25 29-11-2016", formatter), LocalDateTime.parse("13:25 29-11-2016", formatter)));
        gameDtoList.add(new GameDto(3L, 2L, 3L, 3L, "STALEMATE", LocalDateTime.parse("09:43 15-11-2016", formatter), LocalDateTime.parse("11:00 15-11-2016", formatter)));


        when(gameServiceMock.findByPlayerId(1L)).thenReturn(gameDtoList);

        mockMvc.perform(get("/gamesByPlayerId/1")).andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_MEDIA_TYPE))

                .andExpect(jsonPath("$", hasSize(3)))

                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].whiteId", is(1)))
                .andExpect(jsonPath("$[0].blackId", is(2)))
                .andExpect(jsonPath("$[0].winnerId", is(1)))
                .andExpect(jsonPath("$[0].result", is("MATE")))
                .andExpect(jsonPath("$[0].start", is("2016-11-30T02:02")))
                .andExpect(jsonPath("$[0].end", is("2016-11-30T02:32")))

                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].whiteId", is(1)))
                .andExpect(jsonPath("$[1].blackId", is(3)))
                .andExpect(jsonPath("$[1].winnerId", is(1)))
                .andExpect(jsonPath("$[1].result", is("DRAW")))
                .andExpect(jsonPath("$[1].start", is("2016-11-29T12:25")))
                .andExpect(jsonPath("$[1].end", is("2016-11-29T13:25")))

                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].whiteId", is(2)))
                .andExpect(jsonPath("$[2].blackId", is(3)))
                .andExpect(jsonPath("$[2].winnerId", is(3)))
                .andExpect(jsonPath("$[2].result", is("STALEMATE")))
                .andExpect(jsonPath("$[2].start", is("2016-11-15T09:43")))
                .andExpect(jsonPath("$[2].end", is("2016-11-15T11:00")));

        verify(gameServiceMock, times(1)).findByPlayerId(anyLong());

        verifyNoMoreInteractions(gameServiceMock);

    }

    @Test
    public void getGamesByPlayerId_GamesNotExist_returnStatusNotFound() throws Exception {

        List<GameDto> gameDtoList = new ArrayList<>();

        when(gameServiceMock.findByPlayerId(1L)).thenReturn(gameDtoList);

        mockMvc.perform(get("/gamesByPlayerId/1")).andExpect(status().isNotFound());

        verify(gameServiceMock, times(1)).findByPlayerId(anyLong());

        verifyNoMoreInteractions(gameServiceMock);

    }

    @Test
    public void getGameByName_GamesExist_returnGameDtoLis() throws Exception {

        List<GameDto> gameDtoList = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        gameDtoList.add(new GameDto(1L, 1L, 2L, 1L, "MATE", LocalDateTime.parse("02:02 30-11-2016", formatter), LocalDateTime.parse("02:32 30-11-2016", formatter)));
        gameDtoList.add(new GameDto(2L, 1L, 3L, 1L, "DRAW", LocalDateTime.parse("12:25 29-11-2016", formatter), LocalDateTime.parse("13:25 29-11-2016", formatter)));
        gameDtoList.add(new GameDto(3L, 2L, 3L, 3L, "STALEMATE", LocalDateTime.parse("09:43 15-11-2016", formatter), LocalDateTime.parse("11:00 15-11-2016", formatter)));


        when(gameServiceMock.findByPlayerName("GoodBoy")).thenReturn(gameDtoList);

        mockMvc.perform(get("/gamesByPlayerName/GoodBoy")).andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_MEDIA_TYPE))

                .andExpect(jsonPath("$", hasSize(3)))

                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].whiteId", is(1)))
                .andExpect(jsonPath("$[0].blackId", is(2)))
                .andExpect(jsonPath("$[0].winnerId", is(1)))
                .andExpect(jsonPath("$[0].result", is("MATE")))
                .andExpect(jsonPath("$[0].start", is("2016-11-30T02:02")))
                .andExpect(jsonPath("$[0].end", is("2016-11-30T02:32")))

                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].whiteId", is(1)))
                .andExpect(jsonPath("$[1].blackId", is(3)))
                .andExpect(jsonPath("$[1].winnerId", is(1)))
                .andExpect(jsonPath("$[1].result", is("DRAW")))
                .andExpect(jsonPath("$[1].start", is("2016-11-29T12:25")))
                .andExpect(jsonPath("$[1].end", is("2016-11-29T13:25")))

                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].whiteId", is(2)))
                .andExpect(jsonPath("$[2].blackId", is(3)))
                .andExpect(jsonPath("$[2].winnerId", is(3)))
                .andExpect(jsonPath("$[2].result", is("STALEMATE")))
                .andExpect(jsonPath("$[2].start", is("2016-11-15T09:43")))
                .andExpect(jsonPath("$[2].end", is("2016-11-15T11:00")));

        verify(gameServiceMock, times(1)).findByPlayerName("GoodBoy");

        verifyNoMoreInteractions(gameServiceMock);


    }

    @Test
    public void getGameByName_GamesNotExist_returnStatusNotFound() throws Exception {

        List<GameDto> gameDtoList = new ArrayList<>();

        when(gameServiceMock.findByPlayerName("GoodBoy")).thenReturn(gameDtoList);

        mockMvc.perform(get("/gamesByPlayerName/GoodBoy")).andExpect(status().isNotFound());

        verify(gameServiceMock, times(1)).findByPlayerName("GoodBoy");

        verifyNoMoreInteractions(gameServiceMock);


    }


    @Test
    public void getGameByResult_gamesExist_returnGameDtoList() throws Exception {
        List<GameDto> gameDtoList = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        gameDtoList.add(new GameDto(1L, 1L, 2L, 1L, "MATE", LocalDateTime.parse("02:02 30-11-2016", formatter), LocalDateTime.parse("02:32 30-11-2016", formatter)));

        when(gameServiceMock.findByResult("MATE")).thenReturn(gameDtoList);

        mockMvc.perform(get("/gamesByResult/MATE")).andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_MEDIA_TYPE))

                .andExpect(jsonPath("$", hasSize(1)))

                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].whiteId", is(1)))
                .andExpect(jsonPath("$[0].blackId", is(2)))
                .andExpect(jsonPath("$[0].winnerId", is(1)))
                .andExpect(jsonPath("$[0].result", is("MATE")))
                .andExpect(jsonPath("$[0].start", is("2016-11-30T02:02")))
                .andExpect(jsonPath("$[0].end", is("2016-11-30T02:32")));

        verify(gameServiceMock, times(1)).findByResult("MATE");

        verifyNoMoreInteractions(gameServiceMock);

    }

    @Test
    public void getGameByResult_gamesExist_returnStatusNotFound() throws Exception {
        List<GameDto> gameDtoList = new ArrayList<>();


        when(gameServiceMock.findByResult("MATE")).thenReturn(gameDtoList);

        mockMvc.perform(get("/gamesByResult/MATE")).andExpect(status().isNotFound());

        verify(gameServiceMock, times(1)).findByResult("MATE");

        verifyNoMoreInteractions(gameServiceMock);

    }


    @Test
    public void listAllGames_gamesExist_returnGameDtoList() throws Exception {
        List<GameDto> gameDtoList = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        gameDtoList.add(new GameDto(1L, 1L, 2L, 1L, "MATE", LocalDateTime.parse("02:02 30-11-2016", formatter), LocalDateTime.parse("02:32 30-11-2016", formatter)));
        gameDtoList.add(new GameDto(2L, 1L, 3L, 1L, "DRAW", LocalDateTime.parse("12:25 29-11-2016", formatter), LocalDateTime.parse("13:25 29-11-2016", formatter)));
        gameDtoList.add(new GameDto(3L, 2L, 3L, 3L, "STALEMATE", LocalDateTime.parse("09:43 15-11-2016", formatter), LocalDateTime.parse("11:00 15-11-2016", formatter)));


        when(gameServiceMock.findAllGames()).thenReturn(gameDtoList);

        mockMvc.perform(get("/games/")).andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_MEDIA_TYPE))

                .andExpect(jsonPath("$", hasSize(3)))

                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].whiteId", is(1)))
                .andExpect(jsonPath("$[0].blackId", is(2)))
                .andExpect(jsonPath("$[0].winnerId", is(1)))
                .andExpect(jsonPath("$[0].result", is("MATE")))
                .andExpect(jsonPath("$[0].start", is("2016-11-30T02:02")))
                .andExpect(jsonPath("$[0].end", is("2016-11-30T02:32")))

                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].whiteId", is(1)))
                .andExpect(jsonPath("$[1].blackId", is(3)))
                .andExpect(jsonPath("$[1].winnerId", is(1)))
                .andExpect(jsonPath("$[1].result", is("DRAW")))
                .andExpect(jsonPath("$[1].start", is("2016-11-29T12:25")))
                .andExpect(jsonPath("$[1].end", is("2016-11-29T13:25")))

                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].whiteId", is(2)))
                .andExpect(jsonPath("$[2].blackId", is(3)))
                .andExpect(jsonPath("$[2].winnerId", is(3)))
                .andExpect(jsonPath("$[2].result", is("STALEMATE")))
                .andExpect(jsonPath("$[2].start", is("2016-11-15T09:43")))
                .andExpect(jsonPath("$[2].end", is("2016-11-15T11:00")));

        verify(gameServiceMock, times(1)).findAllGames();

        verifyNoMoreInteractions(gameServiceMock);
    }

    @Test
    public void listAllGames_gamesNotExist_returnStatusNtFound() throws Exception {
        List<GameDto> gameDtoList = new ArrayList<>();

        when(gameServiceMock.findAllGames()).thenReturn(gameDtoList);

        mockMvc.perform(get("/games/")).andExpect(status().isNotFound());

        verify(gameServiceMock, times(1)).findAllGames();

        verifyNoMoreInteractions(gameServiceMock);
    }

    @Test
    public void deleteGame_gamesNotExist_returnStatusNotFound() throws Exception {

        GameDto gameDto = new GameDto();


        when(gameServiceMock.findById(1L)).thenReturn(gameDto);

        doNothing().when(gameServiceMock).deleteGameById(1L);


        mockMvc.perform(delete("/game/1")).andExpect(status().isNotFound());

        verify(gameServiceMock, times(1)).findById(1L);

      /*  verify(gameServiceMock, times(1)).deleteGameById(1L);*/


        verifyNoMoreInteractions(gameServiceMock);
    }

    @Test
    public void deleteGame_gamesExist_returnStatusNoContent() throws Exception {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        GameDto gameDto = new GameDto(1L, 1L, 2L, 1L, "MATE", LocalDateTime.parse("02:02 30-11-2016", formatter), LocalDateTime.parse("02:32 30-11-2016", formatter));


        when(gameServiceMock.findById(1L)).thenReturn(gameDto);

        doNothing().when(gameServiceMock).deleteGameById(1L);


        mockMvc.perform(delete("/game/1")).andExpect(status().isNoContent());

        verify(gameServiceMock, times(1)).findById(1L);

        verify(gameServiceMock, times(1)).deleteGameById(1L);


        verifyNoMoreInteractions(gameServiceMock);
    }
}