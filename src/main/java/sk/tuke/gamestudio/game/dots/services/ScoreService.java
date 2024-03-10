package main.java.sk.tuke.gamestudio.game.dots.services;

import main.java.sk.tuke.gamestudio.game.dots.entity.Score;
import main.java.sk.tuke.gamestudio.game.dots.entity.User;
import main.java.sk.tuke.gamestudio.game.dots.features.PlayingMode;

import java.util.List;
import java.util.Map;

public interface ScoreService {
    void addResult(Score score, String username, String mode);
    Map<String, Integer> getTopScores(PlayingMode playingMode);
    Map<String, Integer> getTopUserScores(PlayingMode playingMode, User username);
}
