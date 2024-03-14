package main.java.sk.tuke.gamestudio.entity;

import java.sql.Timestamp;

public class Score {
    private int score;
    private String username;
    private String gameMode;
    private Timestamp date;
    public Score(int score, String username, String gameMode) {
        this.score = score;
        this.username = username;
        this.gameMode = gameMode;
    }
    public Score(String username, int score, String gameMode, Timestamp date) {
        setScore(score);
        setUsername(username);
        setGameMode(gameMode);
        setDate(date);
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
