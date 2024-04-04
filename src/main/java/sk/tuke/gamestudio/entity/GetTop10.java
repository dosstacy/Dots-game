package sk.tuke.gamestudio.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "top10")

@NamedQuery( name = "Score.getTop10",
        query = "SELECT s FROM GetTop10 s ORDER BY s.maxResult DESC")


public class GetTop10 implements Serializable {
    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "max_result")
    private int maxResult;
    @Column(name = "gamemode")
    private String gameMode;
    @Column(name = "date")
    private Timestamp date;

    public GetTop10() {
    }
    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
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
