package main.java.sk.tuke.gamestudio.entity;

public class Score {
    private int score;
    private String username;
    private String gameMode;
    public Score(int score, String username, String gameMode) {
        this.score = score;
        this.username = username;
        this.gameMode = gameMode;
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
}
