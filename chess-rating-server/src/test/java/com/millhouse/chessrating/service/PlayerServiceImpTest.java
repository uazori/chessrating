package com.millhouse.chessrating.service;

import com.millhouse.chessrating.dao.PlayerDaoImp;
import com.millhouse.chessrating.model.Player;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by Millhouse on 11/18/2016.
 *
 * test  PlayerService  for ChessRating  project
 */
@RunWith(MockitoJUnitRunner.class)
public class PlayerServiceImpTest extends TestCase {

    @Mock
    private PlayerDaoImp playerDao;

    @InjectMocks
    private PlayerServiceImp playerService;

    @Spy
    List<Player> players = new ArrayList<>();


    @Before
    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
        players = getTestPlayersList();

    }

    private List<Player> getTestPlayersList() {

        List<Player> players = new ArrayList<>();
        players.add(new Player("FirstTestName", "FirstTestSurname", 1));
        players.add(new Player("SecondTestName", "SecondTestSurname", 2));
        players.add(new Player("ThirdTestName", "ThirdTestSurname", 3));
        players.add(new Player("FourthTestName", "FourthTestSurname", 4));
        players.add(new Player("FifthName", "FifthSurname", 5));

        return players;
    }

    public void tearDown() throws Exception {

    }

    @Test
    public void testFindById() throws Exception {

        Player testPlayer = players.get(3);
        when(playerDao.getById(anyLong())).thenReturn(testPlayer);

        assertEquals(testPlayer,playerService.findById(anyLong()));
    }


    @Test
    public void testFindByName() throws Exception {
        //Expected Player

        Player expectedPlayer = new Player(1, "Bill", "Bobson", 5);
        when(playerDao.getByName(anyString())).thenReturn(expectedPlayer);
        assertEquals(expectedPlayer, playerService.findByName(anyString()));
    }

    @Test
    public void testSaveOrUpdatePlayer() throws Exception {
        players = getTestPlayersList();
        Player testPlayer = players.get(2);
        doNothing().when(playerDao).saveOrUpdate(any(Player.class));
        playerService.savePlayer(testPlayer);
        verify(playerDao, atLeastOnce()).saveOrUpdate(any(Player.class));

    }


    @Test
    public void testDeleteUserById() throws Exception {

        doNothing().when(playerDao).deletePlayerById(anyLong());
        playerService.deletePlayerById(anyLong());
        verify(playerDao, atLeastOnce()).deletePlayerById(anyLong());


    }

    @Test
    public void testFindAllPlayers() throws Exception {
        List<Player> players = getTestPlayersList();
        when(playerDao.getAllPlayers()).thenReturn(players);
        assertEquals(playerService.findAllPlayers(), players);
    }

    @Test
    public void testIsPlayerExist(){
        Player player = new Player();
        when(playerDao.getByName(anyString())).thenReturn(player);
        assertFalse(playerService.isPlayerExist(players.get(2)));

        when(playerDao.getByName(anyString())).thenReturn(players.get(3));
        assertTrue(playerService.isPlayerExist(players.get(3)));

    }
}