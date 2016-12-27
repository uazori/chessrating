package com.millhouse.chessrating.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.millhouse.chessrating.dto.utils.LocalDateTimeDeserializer;
import com.millhouse.chessrating.dto.utils.LocalDateTimeSerializer;

import java.time.LocalDateTime;

/**
 * Created by Vadim Ovcharuk uazori@gmail.com on 11/29/2016.
 * Dto for Game
 */
public class GameDto  {



    private Long id;
    private Long whiteId;
    private Long blackId;
    private String winner;

    private String result;
    private Double whiteScore;
    private Double blackScore;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime start;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime end;


    @Override
    public String toString() {
        return "GameDto{" +
                "id=" + id +
                ", whiteId=" + whiteId +
                ", blackId=" + blackId +
                ", winner='" + winner + '\'' +
                ", result='" + result + '\'' +
                ", whiteScore=" + whiteScore +
                ", blackScore=" + blackScore +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    public GameDto() {
    }

    public GameDto(Long id, Long whiteId, Long blackId, String winner, String result,Double whiteScore,Double blackScore, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.whiteId = whiteId;
        this.blackId = blackId;
        this.winner = winner;
        this.result = result;
        this.whiteScore = whiteScore;
        this.blackScore = blackScore;
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameDto gameDto = (GameDto) o;

        if (id != null ? !id.equals(gameDto.id) : gameDto.id != null) return false;
        if (!whiteId.equals(gameDto.whiteId)) return false;
        if (!blackId.equals(gameDto.blackId)) return false;
        if (!winner.equals(gameDto.winner)) return false;
        if (!result.equals(gameDto.result)) return false;
        if (!whiteScore.equals(gameDto.whiteScore)) return false;
        if (!blackScore.equals(gameDto.blackScore)) return false;
        if (!start.equals(gameDto.start)) return false;
        return end.equals(gameDto.end);

    }

    @Override
    public int hashCode() {
        int result1 = id.hashCode();
        result1 = 31 * result1 + whiteId.hashCode();
        result1 = 31 * result1 + blackId.hashCode();
        result1 = 31 * result1 + winner.hashCode();
        result1 = 31 * result1 + result.hashCode();
        result1 = 31 * result1 + whiteScore.hashCode();
        result1 = 31 * result1 + blackScore.hashCode();
        result1 = 31 * result1 + start.hashCode();
        result1 = 31 * result1 + end.hashCode();
        return result1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWhiteId() {
        return whiteId;
    }

    public void setWhiteId(Long whiteId) {
        this.whiteId = whiteId;
    }

    public Long getBlackId() {
        return blackId;
    }

    public void setBlackId(Long blackId) {
        this.blackId = blackId;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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
