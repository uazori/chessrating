package com.millhouse.chessrating.dao;

import com.millhouse.chessrating.configuration.HibernateConfiguration;
import org.junit.Assert.*;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Millhouse on 11/24/2016.
 * Tests for Game Dao implementation
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {HibernateConfiguration.class})

@Transactional
@Rollback
public class GameDaoImpTest extends TestCase {

    @Autowired
    GameDao gameDao;

    @Test
    public void testGetById() throws Exception {
    assertNull( gameDao.getById(1L));
    }

    public void testGetByPlayerName() throws Exception {

    }

    public void testGetByResult() throws Exception {

    }

    public void testSaveOrUpdate() throws Exception {

    }

    public void testDeleteGameById() throws Exception {

    }

    public void testGetAllGames() throws Exception {

    }

}