package com.millhouse.chessrating.model;

import javax.persistence.*;

/**
 * Created by Millhouse on 12/27/2016.
 */

@Entity
@Table(name = "initial_rating")
public class InitialRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name= "tournament_id" )
    private Tournament tournament;
    @Column(name = "player_id")
    private Long playerId;
    @Column(name = "rating")
    private Double rating;

    public InitialRating() {
    }

    public InitialRating(Long playerId, Double rating) {
        this.playerId = playerId;
        this.rating = rating;
    }

    public InitialRating(Tournament tournament, Long playerId, Double rating) {
        this.tournament = tournament;
        this.playerId = playerId;
        this.rating = rating;
    }


    @Override
    public String toString() {
        return "InitialRating{" +
                "id=" + id +
                ", playerId=" + playerId +
                ", rating=" + rating +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InitialRating that = (InitialRating) o;

        if (tournament != null ? !tournament.equals(that.tournament) : that.tournament != null) return false;
        if (playerId != null ? !playerId.equals(that.playerId) : that.playerId != null) return false;
        return rating != null ? rating.equals(that.rating) : that.rating == null;

    }

    @Override
    public int hashCode() {
        int result = tournament != null ? tournament.hashCode() : 0;
        result = 31 * result + (playerId != null ? playerId.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }


    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }
}
