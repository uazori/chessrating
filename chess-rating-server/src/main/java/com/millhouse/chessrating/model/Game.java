package com.millhouse.chessrating.model;

import java.time.LocalDateTime;

/**
 * Created by Millhouse on 11/24/2016.
 * Game entity for Chess Project
 */
public class Game {

    private Long id;
    private Player white;
    private Player black;
    private Enum result;
    private LocalDateTime start;
    private LocalDateTime end;


    public Game() {
    }

    public Game(Player white, Player black, Enum result, LocalDateTime start, LocalDateTime end) {

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

    public Enum getResult() {
        return result;
    }

    public void setResult(Enum result) {
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
