package sk.tuke.gamestudio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity

@NamedQuery( name = "Score.getDataForAccount",
        query = "SELECT MAX(s.score) as max_result, s.gamemode, s.date FROM Score s WHERE s.username = :username GROUP BY s.date, s.gamemode ORDER BY max_result DESC LIMIT 10")

@NamedQuery( name = "Score.getTop10",
        query = "SELECT MAX(s.score) as max_result, s.gamemode, s.date, s.username FROM Score s GROUP BY s.date, s.gamemode, s.username ORDER BY max_result DESC LIMIT 10")

@NamedQuery( name = "Score.reset",
        query = "DELETE FROM Score")

public class Score implements Serializable {
    @Id
    @GeneratedValue
    private int ident;
    private int score;
    private String username;
    private String gamemode;
    private Timestamp date;
    public Score(int score, String username, String gamemode) {
        this.score = score;
        this.username = username;
        this.gamemode = gamemode;
    }
    public Score(String username, int score, String gamemode, Timestamp date) {
        setScore(score);
        setUsername(username);
        setGamemode(gamemode);
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

    public String getGamemode() {
        return gamemode;
    }

    public void setGamemode(String gameMode) {
        this.gamemode = gameMode;
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
