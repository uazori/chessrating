package com.millhouse.chessrating.model;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "tournament")
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

    @Column(name = "system")
    private String system;

    @Column(name = "tournament_start")
    private LocalDateTime start;
    @Column(name = "tournament_end")
    private LocalDateTime end;
    @Column(name = "tournament_finished")
    private boolean tournamentFinished;


    @OneToMany(cascade=CascadeType.ALL, mappedBy = "tournament")
    private Set<InitialRating> initialRatings;


    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Player> players;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Game> games;

    @Override
    public String toString() {
        return "Tournament{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", system='" + system + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", tournamentFinished=" + tournamentFinished +
                ", players=" + players +
                ", games=" + games +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tournament that = (Tournament) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (system != null ? !system.equals(that.system) : that.system != null) return false;
        if (start != null ? !start.equals(that.start) : that.start != null) return false;
        if (end != null ? !end.equals(that.end) : that.end != null) return false;
        if (players != null ? !players.equals(that.players) : that.players != null) return false;
        return games != null ? games.equals(that.games) : that.games == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (system != null ? system.hashCode() : 0);
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (end != null ? end.hashCode() : 0);
        result = 31 * result + (players != null ? players.hashCode() : 0);
        result = 31 * result + (games != null ? games.hashCode() : 0);
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public boolean getTournamentFinished() {
        return tournamentFinished;
    }

    public void setTournamentFinished(boolean tournamentFinished) {
        this.tournamentFinished = tournamentFinished;
    }

    public Set<InitialRating> getInitialRatings() {
        return initialRatings;
    }

    public void setInitialRatings(Set<InitialRating> initialRatings) {
        this.initialRatings = initialRatings;
    }
}
