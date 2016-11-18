package com.millhouse.chessrating.service;

import com.millhouse.chessrating.model.Player;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static sun.audio.AudioPlayer.player;

/**
 * Created by Millhouse on 11/17/2016.
 */

@Service("userService")

public class PlayerServiceImp implements PlayerService {

    private static final AtomicLong counter = new AtomicLong();

    private static List<Player> players = populateDummyUsers();

    @Override
    public Player findById(long id) {
        for (Player player : players) {
            if (player.getId() == id) return player;

        }
        return null;
    }

    @Override
    public Player findByName(String name) {
        for (Player player : players) {
            if (player.getName().equalsIgnoreCase(name)) {
                return player;
            }

        }
        return null;
    }

    @Override
    public void savePlayer(Player player) {
        player.setId(counter.incrementAndGet());
        players.add(player);
    }

    @Override
    public void updatePlayer(Player player) {
        int index = players.indexOf(player);
        players.set(index, player);
    }

    @Override
    public void deleteUserById(long id) {

        for (Iterator<Player> iterator = players.iterator(); iterator.hasNext(); ) {
            Player player = iterator.next();
            if (player.getId() == id) {
                iterator.remove();
            }

        }
    }

    @Override
    public List<Player> findAllPlayers() {
        return players;
    }

    @Override
    public void deleteAllPlayers() {
    players.clear();
    }

    @Override
    public boolean isPlayerExist(Player player) {
        return findByName(player.getName())!=null ;
    }

    private static List<Player> populateDummyUsers(){
        List<Player> players = new ArrayList<Player>();
        players.add(new Player(counter.incrementAndGet(),"Bill","Bobson",5));
        players.add(new Player(counter.incrementAndGet(),"Sam","Dilinger", 1));
        players.add(new Player(counter.incrementAndGet(),"Tom","Good",2));
        players.add(new Player(counter.incrementAndGet(),"Jerome","Peasant",3));
        players.add(new Player(counter.incrementAndGet(),"Silvia","Big",4));
        return players;
    }
}
