package com.millhouse.chessrating.model;

import org.hibernate.annotations.NotFound;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Millhouse on 11/24/2016.
 * Game entity for Chess Project
 */
@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "white_player_id")
    private Player white;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "black_player_id")
    private Player black;
    @Enumerated(EnumType.STRING)
    @Column(name = "game_result")//* enum type string
    private Result result;
    @Column(name = "game_start")
    private LocalDateTime start;
    @Column(name = "game_end")
    private LocalDateTime end;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        if (!white.equals(game.white)) return false;
        if (!black.equals(game.black)) return false;
        if (!result.equals(game.result)) return false;
        if (!start.equals(game.start)) return false;
        return end.equals(game.end);

    }

    @Override
    public int hashCode() {
        int result1 = white.hashCode();
        result1 = 31 * result1 + black.hashCode();
        result1 = 31 * result1 + result.hashCode();
        result1 = 31 * result1 + start.hashCode();
        result1 = 31 * result1 + end.hashCode();
        return result1;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", white=" + white +
                ", black=" + black +
                ", result='" + result + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    public Game() {
    }

    public Game(Player white, Player black, Result
            result, LocalDateTime start, LocalDateTime end) {

        this.white = white;
        this.black = black;
        this.result = result;
        this.start = start;
        this.end = end;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getWhite() {

        return white;
    }

    public void setWhite(Player white) {
        this.white = white;
    }

    public Player getBlack() {
        return black;
    }

    public void setBlack(Player black) {
        this.black = black;
    }
    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
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

}
