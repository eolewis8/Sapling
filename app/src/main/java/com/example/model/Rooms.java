package com.example.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rooms {

    private int numPlayers;
    private int numConnected;
    private String roomName;
    private Map<String, String> players;
    private int highScore;
    private String highScorePlayer;
    private Map<String, Integer> stats;

    public Rooms() {};

    public Rooms(int numPlayers, String roomName, Map<String, String> players, Map<String, Integer> stats) {
        this.numPlayers = numPlayers;
        this.roomName = roomName;
        this.players = players;
        this.stats = stats;
        this.numConnected = players.size();
        this.highScore = 0;
        this.highScorePlayer = "";
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Map<String, String> getPlayers() {
        return players;
    }

    public void setPlayers(Map<String, String> players) {
        this.players = players;
    }

    public int getNumConnected() {
        return players.size();
    }

    public void setNumConnected(int numConnected) {
        this.numConnected = numConnected;
    }
}
