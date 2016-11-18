package com.millhouse.chessrating.model;

/**
 * Created by Millhouse on 11/17/2016.
 */
public class Player {
    private long id;
    private String name;
    private String surname;
    private int rating;

    public Player(long id, String name, String surname, int rating) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", rating=" + rating +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
