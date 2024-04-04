package sk.tuke.gamestudio.entity;

import javax.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"username", "gamemode"})})

@NamedQuery( name = "Score.reset",
        query = "DELETE FROM Score")

public class Score implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "score_id")
    private int ident;
    @Column(name = "score")
    private int score;
    @Column(name = "username")
    private String username;
    @Column(name = "gamemode")
    private String gamemode;
    @Column(name = "date")
    private Timestamp date;
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
}
