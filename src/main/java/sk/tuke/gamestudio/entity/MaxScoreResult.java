package sk.tuke.gamestudio.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "max_score_result")

@NamedQuery(name = "MaxScoreResult.getDataForAccount",
        query = "SELECT s FROM MaxScoreResult s ORDER BY s.maxResult DESC"
)

public class MaxScoreResult implements Serializable {
    @Id
    @Column(name = "gamemode")
    private String gameMode;
    @Column(name = "max_result")
    private int maxResult;
    @Column(name = "date")
    private Timestamp date;

    public MaxScoreResult() {
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
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
