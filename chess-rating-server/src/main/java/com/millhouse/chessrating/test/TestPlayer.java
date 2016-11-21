package com.millhouse.chessrating.test;

import com.millhouse.chessrating.model.Player;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Millhouse on 11/18/2016.
 */
@RunWith(MockitoJUnitRunner.class)

public class TestPlayer  {
    public static final String REST_SERVICE_URI = "http://localhost:8080/";

    /* GET */
    @Test
    @SuppressWarnings("unchecked")
    private static void listAllPlayer(){
        System.out.println("Testing listAllUsers API-----------");

        RestTemplate restTemplate = new RestTemplate();
        List<LinkedHashMap<String, Object>> usersMap = restTemplate.getForObject(REST_SERVICE_URI+"/players/", List.class);

        if(usersMap!= null){
            for(LinkedHashMap<String, Object> map : usersMap){
                System.out.println("Player : id="+map.get("id")+", Name="+map.get("name")+", Surname="+map.get("surname")+", rating="+map.get("rating"));
            }
        }else{
            System.out.println("No player exist----------");
        }
    }
    /* GET */
    private static void getPlayer(){
        System.out.println("Testing getUser API----------");
        RestTemplate restTemplate = new RestTemplate();
        Player player = restTemplate.getForObject(REST_SERVICE_URI+"/player/1", Player.class);
        System.out.println(player);
    }

    private static void createUser(){

        System.out.println("Testing create User API----------");
        RestTemplate restTemplate = new RestTemplate();
       // Playerp playerp
    }

    public static void main(String[] args) {
        listAllPlayer();
        getPlayer();
    }
}
