package main.java.sk.tuke.gamestudio.services;

import main.java.sk.tuke.gamestudio.entity.Score;
import main.java.sk.tuke.gamestudio.entity.User;
import main.java.sk.tuke.gamestudio.game.dots.features.PlayingMode;

import java.util.List;
import java.util.Map;

public interface ScoreService {
    void addScore(Score score);
    Map<String, Integer> getTopScores(PlayingMode playingMode);
    List<Integer> getTopUserScores(PlayingMode playingMode, User username);
    List<String> getDataForAccount(String username);
    void reset();
}
