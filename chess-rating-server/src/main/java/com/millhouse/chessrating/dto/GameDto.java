package com.millhouse.chessrating.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.millhouse.chessrating.dto.utils.LocalDateTimeDeserializer;
import com.millhouse.chessrating.dto.utils.LocalDateTimeSerializer;

import java.time.LocalDateTime;

/**
 * Created by Vadim Ovcharuk uazori@gmail.com on 11/29/2016.
 * Dto for Game ghhghdghfg
 */
public class GameDto  {



    private Long id;
    private Long whiteId;
    private Long blackId;
    private Long winnerId;

    private String result;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime start;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime end;


    public GameDto() {
    }

    public GameDto(Long id, Long whiteId, Long blackId, Long winnerId, String result, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.whiteId = whiteId;
        this.blackId = blackId;
        this.winnerId = winnerId;
        this.result = result;
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameDto gameDto = (GameDto) o;

        if (id != null ? !id.equals(gameDto.id) : gameDto.id != null) return false;
        if (whiteId != null ? !whiteId.equals(gameDto.whiteId) : gameDto.whiteId != null) return false;
        if (blackId != null ? !blackId.equals(gameDto.blackId) : gameDto.blackId != null) return false;
        if (winnerId != null ? !winnerId.equals(gameDto.winnerId) : gameDto.winnerId != null) return false;
        if (result != null ? !result.equals(gameDto.result) : gameDto.result != null) return false;
        return start != null ? start.equals(gameDto.start) : gameDto.start == null && (end != null ? end.equals(gameDto.end) : gameDto.end == null);

    }

    @Override
    public int hashCode() {
        int result1 = id != null ? id.hashCode() : 0;
        result1 = 31 * result1 + (whiteId != null ? whiteId.hashCode() : 0);
        result1 = 31 * result1 + (blackId != null ? blackId.hashCode() : 0);
        result1 = 31 * result1 + (winnerId != null ? winnerId.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + (start != null ? start.hashCode() : 0);
        result1 = 31 * result1 + (end != null ? end.hashCode() : 0);
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

    public Long getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(Long winnerId) {
        this.winnerId = winnerId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
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
