package com.millhouse.chessrating.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.millhouse.chessrating.configuration.ChessRatingConfiguration;
import com.millhouse.chessrating.model.Player;
import com.millhouse.chessrating.service.PlayerServiceImp;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vadim Ovcharuk uazori@gmail.com on 11/18/2016.
 * Tests for PlayerController
 */

@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
@EnableWebMvc
@ContextConfiguration(classes = {ChessRatingConfiguration.class})
public class PlayerControllerTest extends TestCase {


    private MediaType APPLICATION_JSON_MEDIA_TYPE;
    private MockMvc mockMvc;

    @Mock
    private PlayerServiceImp playerServiceMock;

    @InjectMocks
    private PlayerController playerController = new PlayerController();

    @Before
    public void setUp() {
        mockMvc = standaloneSetup(playerController).build();
        APPLICATION_JSON_MEDIA_TYPE = new MediaType(MediaType.APPLICATION_JSON.getType(),
                MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    }


    @Test
    public void testListAllPlayer_PlayersNotFound_ShouldReturnStatusNoConnect() throws Exception {
        List<Player> players = new ArrayList<>();

        when(playerServiceMock.findAllPlayers()).thenReturn(players);
        mockMvc.perform(get("/player/"))
                .andExpect(status().isNoContent());

        verify(playerServiceMock, times(1)).findAllPlayers();
        verifyNoMoreInteractions(playerServiceMock);


    }


    @Test
    public void testListAllPlayer_PlayersFound_ShouldReturnFoundPlayers() throws Exception {

        List<Player> players = new ArrayList<>();

        players.add(new Player(1L, "Test1", "Test1Surname", 1,true));
        players.add(new Player(2L, "Test2", "Test2Surname", 2,true));
        players.add(new Player(3L, "Test3", "Test3Surname", 3,true));

        when(playerServiceMock.findAllPlayers()).thenReturn(players);

        mockMvc.perform(get("/player/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_MEDIA_TYPE))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Test1")))
                .andExpect(jsonPath("$[0].surname", is("Test1Surname")))
                .andExpect(jsonPath("$[0].rating", is(1.0)))

                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Test2")))
                .andExpect(jsonPath("$[1].surname", is("Test2Surname")))
                .andExpect(jsonPath("$[1].rating", is(2.0)))

                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("Test3")))
                .andExpect(jsonPath("$[2].surname", is("Test3Surname")))
                .andExpect(jsonPath("$[2].rating", is(3.0)));

        verify(playerServiceMock, times(1)).findAllPlayers();
        verifyNoMoreInteractions(playerServiceMock);


    }

    @Test
    public void testGetPlayer_PlayerFound_ShouldReturnFoundPlayer() throws Exception {

        Player testPlayer = new Player(23, "TestPlayer", "TestPlayerSurname", 5,true);

        when(playerServiceMock.findById(anyLong())).thenReturn(testPlayer);

        mockMvc.perform(get("/player/1")).andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_MEDIA_TYPE))
                .andExpect(jsonPath("$.id", is(23)))
                .andExpect(jsonPath("$.name", is("TestPlayer")))
                .andExpect(jsonPath("$.surname", is("TestPlayerSurname")))
                .andExpect(jsonPath("$.rating", is(5.0)));

        verify(playerServiceMock, times(1)).findById(anyLong());
        verifyNoMoreInteractions(playerServiceMock);

    }

    @Test
    public void testGetPlayer_PlayerNotFound_ShouldReturnStatusNotFound() throws Exception {



        when(playerServiceMock.findById(11L)).thenReturn(null);

        mockMvc.perform(get("/player/11")).andExpect(status().isNotFound());

        verify(playerServiceMock, times(1)).findById(11L);
        verifyNoMoreInteractions(playerServiceMock);

    }
    @Test
    public void testCreatePlayer_PlayerAdded_ShouldReturnCreatedPlayer() throws Exception {
        Player testPlayer = new Player("TestName", "TestSurname", 4,true);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        byte[] jsonTestPlayer = mapper.writeValueAsBytes(testPlayer);
        doNothing().when(playerServiceMock).saveOrUpdatePlayer(testPlayer);

        mockMvc.perform(post("/player/")
                .contentType(APPLICATION_JSON_MEDIA_TYPE)
                .content(jsonTestPlayer))
                .andExpect(content().contentType(APPLICATION_JSON_MEDIA_TYPE))

                .andExpect(jsonPath("$.name", is("TestName")))
                .andExpect(jsonPath("$.surname", is("TestSurname")))
                .andExpect(jsonPath("$.rating", is(4.0)));

        verify(playerServiceMock, times(1)).saveOrUpdatePlayer(testPlayer);

    }

    @Test
    public void testCreatePlayer_PlayerAlreadyExist_ShouldReturnStatusConflict() throws Exception {

        Player testPlayer = new Player(1,"TestName", "TestSurname", 3,true);

       /* when(playerServiceMock.isPlayerExist(testPlayer)).thenReturn(true);*/

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        byte[] jsonTestPlayer = mapper.writeValueAsBytes(testPlayer);


        mockMvc.perform(post("/player/")
                .contentType(APPLICATION_JSON_MEDIA_TYPE)
                .content(jsonTestPlayer))
                .andExpect(status().isConflict());


       /* verify(playerServiceMock, times(1)).isPlayerExist(testPlayer);*/
        verifyNoMoreInteractions(playerServiceMock);

    }

    @Test
    public void testUpdatePlayer_PlayerUpdated_ShouldReturnUpdatedPlayer() throws Exception {
        Player testPlayer = new Player(1,"TestName", "TestSurname", 3,true);

/*        when(playerServiceMock.findById(anyLong())).thenReturn(testPlayer);*/

        doNothing().when(playerServiceMock).saveOrUpdatePlayer(any());

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        byte[] jsonTestPlayer = mapper.writeValueAsBytes(testPlayer);


        mockMvc.perform(put("/player/1")
                .contentType(APPLICATION_JSON_MEDIA_TYPE)
                .content(jsonTestPlayer))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_MEDIA_TYPE))

                .andExpect(jsonPath("$.name", is("TestName")))
                .andExpect(jsonPath("$.surname", is("TestSurname")))
                .andExpect(jsonPath("$.rating", is(3.0)));

/*        verify(playerServiceMock, times(1)).findById(anyLong());*/
        verify(playerServiceMock, times(1)).saveOrUpdatePlayer(any());

    }


    @Test
    public void testUpdatePlayer_PlayerNotFound_ShouldReturnStatusNotFound() throws Exception {
        Player testPlayer = new Player("TestName", "TestSurname", 3,true);

        doNothing().when(playerServiceMock).saveOrUpdatePlayer(testPlayer);



        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        byte[] jsonTestPlayer = mapper.writeValueAsBytes(testPlayer);


        mockMvc.perform(put("/player/1").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTestPlayer))
                .andExpect(status().isNotFound());



        verifyNoMoreInteractions(playerServiceMock);

    }


    @Test
    public void testDeletePlayer_PlayerDeleted_ShouldReturnStatusNoConnect() throws Exception {


        when(playerServiceMock.findById(anyLong())).thenReturn(new Player());

        doNothing().when(playerServiceMock).deletePlayerById(anyLong());



        mockMvc.perform(delete("/player/1")).andExpect(status().isNoContent());


        verify(playerServiceMock, times(1)).findById(anyLong());
        verify(playerServiceMock, times(1)).deletePlayerById(anyLong());

    }


    @Test
    public void testDeletePlayer_PlayerNotFound_ShouldReturnStatusNotFound() throws Exception {

        when(playerServiceMock.findById(anyLong())).thenReturn(null);

        mockMvc.perform(delete("/player/1")).andExpect(status().isNotFound());


        verify(playerServiceMock, times(1)).findById(anyLong());
        verifyNoMoreInteractions(playerServiceMock);

    }


}