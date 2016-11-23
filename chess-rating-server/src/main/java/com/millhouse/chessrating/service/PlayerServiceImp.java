package com.millhouse.chessrating.service;

import com.millhouse.chessrating.dao.PlayerDao;
import com.millhouse.chessrating.dao.PlayerDaoImp;
import com.millhouse.chessrating.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static sun.audio.AudioPlayer.player;

/**
 * Created by Millhouse on 11/17/2016.
 */

@Service("playerService")
@Transactional
public class PlayerServiceImp implements PlayerService {

    private static final AtomicLong counter = new AtomicLong();

    private static List<Player> players = populateDummyUsers();

    @Autowired
    private PlayerDao dao;

    @Override
    public Player findById(Long id) {

        Player player = dao.getById(id);

        if (player == null) {
            throw new RuntimeException("Player with id " + id + " not found");
        }

        return player;
    }

    @Override
    public Player findByName(String name) {

        return dao.getByName(name);
    }

    @Override
    public void savePlayer(Player player) {
        System.out.println("player  " + player);

       dao.saveOrUpdate(player);
    }

    @Override
    public void updatePlayer(Player player) {
        dao.saveOrUpdate(player);
    }

    @Override
    public void deletePlayerById(Long id) {

        dao.deletePlayerById(id);

    }

    @Override
    public List<Player> findAllPlayers() {
        return dao.getAllPlayers();
    }

    @Override
    public boolean isPlayerExist(Player player) {
        return findByName(player.getName())!=null ;
    }

    private static List<Player> populateDummyUsers(){
        List<Player> players = new ArrayList<>();
        players.add(new Player(counter.incrementAndGet(),"Bill","Bobson",5));
        players.add(new Player(counter.incrementAndGet(),"Sam","Dilinger", 1));
        players.add(new Player(counter.incrementAndGet(),"Tom","Good",2));
        players.add(new Player(counter.incrementAndGet(),"Jerome","Peasant",3));
        players.add(new Player(counter.incrementAndGet(),"Denis","Big",4));
        return players;
    }
}
