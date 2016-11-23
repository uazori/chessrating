package com.millhouse.chessrating.dao;

import com.millhouse.chessrating.configuration.HibernateConfiguration;
import com.millhouse.chessrating.model.Player;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by Millhouse on 11/21/2016.
 * test project ChessRating
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {HibernateConfiguration.class})

@Transactional
@Rollback
public class PlayerDaoImpTest {


    @Autowired
    private PlayerDao playerDao;


    @Test
    public void testGetById() throws Exception {

        List<Player> playerList = getTestPlayers();
        Player player1 = playerList.get(0);
        Player player2 = playerList.get(1);

        playerDao.saveOrUpdate(player1);
        playerDao.saveOrUpdate(player2);

        playerDao.deletePlayerById(player2.getId());


        Player fetchedPlayer = playerDao.getById(player1.getId());

        assertEquals(player1.getId(), fetchedPlayer.getId());
        assertNull(playerDao.getById(player2.getId()));
    }

    @Test
    public void testGetByName() throws Exception {
        List<Player> playerList = getTestPlayers();

        Player player = playerList.get(3);
        playerDao.saveOrUpdate(player);

        Player fetchedPlayer = playerDao.getByName(player.getName());

        assertEquals(player, fetchedPlayer);

    }

    @Test
    public void testSaveOrUpdatePlayer() throws Exception {
        List<Player> playerList = getTestPlayers();

        Player player = playerList.get(4);

        playerDao.saveOrUpdate(player);

        assertEquals(player, playerDao.getById(player.getId()));

        player.setRating(10);

        playerDao.saveOrUpdate(player);
        Player fetchedPlayer = playerDao.getById(player.getId());

        assertEquals(10, fetchedPlayer.getRating());
    }

    @Test
    public void testDeletePlayerById() throws Exception {
        List<Player> playerList = getTestPlayers();

        Player player = playerList.get(2);
        playerDao.saveOrUpdate(player);

        playerDao.deletePlayerById(player.getId());

        assertNull(playerDao.getById(player.getId()));
    }

    @Test
    public void testGetAllPlayers() throws Exception {
        List<Player> playerList = getTestPlayers();

        List<Player> existingPlayers = playerDao.getAllPlayers();

        for (Player player : playerList) {
            playerDao.saveOrUpdate(player);
        }

        playerList.addAll(existingPlayers);

        List<Player> fetchedPlayers = playerDao.getAllPlayers();

        assertThat(new HashSet<>(playerList), is(new HashSet<>(fetchedPlayers)));
    }

    public void testDeleteAllPlayers() throws Exception {

    }

    public void testIsPlayerExist() throws Exception {

    }

    public List<Player> getTestPlayers() {

        List<Player> playerList = new ArrayList<>();

        playerList.add(new Player("FirstTestName", "FirstTestSurname", 1));
        playerList.add(new Player("SecondTestName", "SecondTestSurname", 2));
        playerList.add(new Player("ThirdTestName", "ThirdTestSurname", 3));
        playerList.add(new Player("FourthTestName", "FourthTestSurname", 4));
        playerList.add(new Player("FifthName", "FifthSurname", 5));

        return playerList;
    }

}