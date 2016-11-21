package com.millhouse.chessrating.service;

import com.millhouse.chessrating.model.Player;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

/**
 * Created by Millhouse on 11/18/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class PlayerServiceImpTest extends TestCase {
    @Mock
    private PlayerServiceImp playerService;

    public void setUp() throws Exception {
        super.setUp();

    }

    public void tearDown() throws Exception {

    }

    public void testFindById() throws Exception {

    }


    @Test
    public void testFindByName() throws Exception {
        //Expected Player

    Player expecedPlayer = new Player(1,"Bill","Bobson",5);
        when(playerService.findByName("Steve")).thenReturn(expecedPlayer);
        assertEquals(new Player(1,"Bill","Bobson",5),playerService.findByName("Steve"));
    }

    public void testSavePlayer() throws Exception {

    }

    public void testUpdatePlayer() throws Exception {

    }

    public void testDeleteUserById() throws Exception {

    }

    public void testFindAllPlayers() throws Exception {

    }

    public void testDeleteAllPlayers() throws Exception {

    }

    public void testIsPlayerExist() throws Exception {

    }

}