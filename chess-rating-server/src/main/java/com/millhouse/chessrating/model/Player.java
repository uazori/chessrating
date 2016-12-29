package com.millhouse.chessrating.model;

import javax.persistence.*;

/**
 * Created by Vadim Ovcharuk uazori@gmail.com on 11/17/2016.
 * Player entity for ChessProject
 */
@Entity
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private Long id;
    @Column(name = "player_name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "username")
    private String username;
    @Column(name = "rating")
    private double rating;
    @Column(name = "activity")
    private boolean activity;


    public Player() {
    }

    public Player(long id, String name, String surname,String username, int rating, boolean activity) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.rating = rating;
        this.activity = activity;
    }

    public Player(String name, String surname,String username, int rating, boolean activity ) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.rating = rating;
        this.activity = activity;
    }


    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                ", rating=" + rating +
                ", activity=" + activity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (Double.compare(player.rating, rating) != 0) return false;
        if (activity != player.activity) return false;
        if (name != null ? !name.equals(player.name) : player.name != null) return false;
        if (surname != null ? !surname.equals(player.surname) : player.surname != null) return false;
        return username != null ? username.equals(player.username) : player.username == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        temp = Double.doubleToLongBits(rating);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (activity ? 1 : 0);
        return result;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public boolean isActivity() {
        return activity;
    }

    public void setActivity(boolean activity) {
        this.activity = activity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
