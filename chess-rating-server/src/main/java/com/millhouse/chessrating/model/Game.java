package com.millhouse.chessrating.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.DoubleSummaryStatistics;

/**
 * Created by Vadim Ovcharuk uazori@gmail.com on 11/24/2016.
 * Game entity for Chess Project
 */
@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "white_player_id")
    private Player white;
    @ManyToOne()
    @JoinColumn(name = "black_player_id")
    private Player black;

    @Column(name = "winner")
    private String winner;
    @Enumerated(EnumType.STRING)
    @Column(name = "game_result")//* enum type string
    private Result result;
    @Column(name = "white_player_score")
    private Double whiteScore;
    @Column(name = "black_player_score")
    private Double blackScore;
    @Column(name = "game_start")
    private LocalDateTime start;
    @Column(name = "game_end")
    private LocalDateTime end;


    public Double getPlayerScore(Player player, Player opponent) {

        //calculating Elo Chess Rating

        //E_A expected result for Player

        Double ratingDelta = opponent.getRating() - player.getRating();

        if (ratingDelta > 400D) ratingDelta = 400D;
        if (ratingDelta< -400D) ratingDelta = -400D;
        System.out.println();
        Double expectedResult = 1 / (1 + Math.pow(10, ratingDelta / 400));


/*
        FIDE uses the following ranges:[20]
        K = 40, for a player new to the rating list until the completion of events with a total of 30 games and for all players until their 18th birthday, as long as their rating remains under 2300.
        K = 20, for players with a rating always under 2400.
        K = 10, for players with any published rating of at least 2400 and at least 30 games played in previous events. Thereafter it remains permanently at 10.*/


        Double playerKoefficient = 0D;
        if (player.getRating() < 2400) {
            playerKoefficient = 40D;
        } else if (player.getRating() >= 2400) {
            playerKoefficient = 20D;
        }


        Double gameResult;
        if (result == Result.DRAW || result == Result.STALEMATE) {
            gameResult = 0.5D;
        } else {
            if ((winner.equals("White")) && (player.equals(white))) {
                gameResult = 1D;
            } else if (winner.equals("Black") && (player.equals(black))) {
                gameResult = 1D;
            } else {
                gameResult = 0D;
            }


        }

        Double score = playerKoefficient * (gameResult - expectedResult);

        return score;

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        if (!white.equals(game.white)) return false;
        if (!black.equals(game.black)) return false;
        if (!winner.equals(game.winner)) return false;
        if (result != game.result) return false;
        if (!whiteScore.equals(game.whiteScore)) return false;
        if (!blackScore.equals(game.blackScore)) return false;
        if (!start.equals(game.start)) return false;
        return end.equals(game.end);

    }

    @Override
    public int hashCode() {
        int result1 = white.hashCode();
        result1 = 31 * result1 + black.hashCode();
        result1 = 31 * result1 + winner.hashCode();
        result1 = 31 * result1 + result.hashCode();
        result1 = 31 * result1 + whiteScore.hashCode();
        result1 = 31 * result1 + blackScore.hashCode();
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
                ", winner='" + winner + '\'' +
                ", result=" + result +
                ", whiteScore=" + whiteScore +
                ", blackScore=" + blackScore +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    public Game() {
    }

    public Game(Player white, Player black, String winner, Result result, LocalDateTime start, LocalDateTime end) {

        this.white = white;
        this.black = black;
        this.winner = winner;
        this.result = result;
        this.whiteScore = getPlayerScore(white, black);
        this.blackScore = getPlayerScore(black, white);
        this.start = start;
        this.end = end;
    }

    public Game(Long id, Player white, Player black, String winner, Result result, LocalDateTime start, LocalDateTime end) {

        this.id = id;
        this.white = white;
        this.black = black;
        this.winner = winner;
        this.result = result;
        this.whiteScore = getPlayerScore(white, black);
        this.blackScore = getPlayerScore(black, white);
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

    public String getWinner() {

        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
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

    public Double getWhiteScore() {
        return whiteScore;
    }

    public void setWhiteScore(Double whiteScore) {
        this.whiteScore = whiteScore;
    }

    public Double getBlackScore() {
        return blackScore;
    }

    public void setBlackScore(Double blackScore) {
        this.blackScore = blackScore;
    }
}
