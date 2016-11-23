package com.millhouse.chessrating.model;

import javax.persistence.*;

/**
 * Created by Millhouse on 11/17/2016.
 */
@Entity
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "player_name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "rating")
    private int rating;

    public Player() {
    }

    public Player(long id, String name, String surname, int rating) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.rating = rating;
    }

    public Player(String name, String surname, int rating) {
        this.name = name;
        this.surname = surname;
        this.rating = rating;
    }

    /*@Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", rating=" + rating +
                '}';
    }*/

   /* @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (rating != player.rating) return false;
        if (!name.equals(player.name)) return false;
        return surname.equals(player.surname);

    }*/

  /*  @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + surname.hashCode();
        result = 31 * result + rating;
        return result;
    }
*/
    public Long getId() {
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
