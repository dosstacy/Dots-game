package sk.tuke.gamestudio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
public class Score implements Serializable {
    @Id
    @GeneratedValue
    private int ident;
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
    public Score(){

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

    public int getIdent() {
        return ident;
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }
}
