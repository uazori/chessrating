package com.millhouse.chessrating.test;

import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Millhouse on 11/18/2016.
 */
public class TestPlayer {
    public static final String REST_SERVICE_URI = "http://localhost:8080/";

    /* GET */
    @SuppressWarnings("unchecked")
    private static void listAllPlayer(){
        System.out.println("Testing listAllUsers API-----------");

        RestTemplate restTemplate = new RestTemplate();
        List<LinkedHashMap<String, Object>> usersMap = restTemplate.getForObject(REST_SERVICE_URI+"/players/", List.class);

        if(usersMap!=null){
            for(LinkedHashMap<String, Object> map : usersMap){
                System.out.println("Player : id="+map.get("id")+", Name="+map.get("name")+", Surname="+map.get("surname")+", rating="+map.get("rating"));
            }
        }else{
            System.out.println("No user exist----------");
        }
    }

    public static void main(String[] args) {
        listAllPlayer();
    }
}
