package com.millhouse.chessrating.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.millhouse.chessrating.dto.utils.LocalDateTimeDeserializer;
import com.millhouse.chessrating.dto.utils.LocalDateTimeSerializer;
import com.millhouse.chessrating.model.Player;

import java.time.LocalDateTime;
import java.util.List;


public class TournamentDto {

    private Long id;
    private String name;
    private String description;
    private String system;
    private List<Player> players;
    private List<GameDto> gameDtos;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime start;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime end;

    @Override
    public String toString() {
        return "TournamentDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", system='" + system + '\'' +
                ", players=" + players +
                ", gameDtos=" + gameDtos +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Player> getPlayers() {
        return players;
    }


    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<GameDto> getGameDtos() {
        return gameDtos;
    }

    public void setGameDtos(List<GameDto> gameDtos) {
        this.gameDtos = gameDtos;
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
