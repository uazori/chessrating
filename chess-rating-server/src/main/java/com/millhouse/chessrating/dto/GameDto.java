package com.millhouse.chessrating.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.millhouse.chessrating.dto.utils.LocalDateTimeDeserializer;
import com.millhouse.chessrating.dto.utils.LocalDateTimeSerializer;

import java.time.LocalDateTime;

/**
 * Created by Millhouse on 11/29/2016.
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
