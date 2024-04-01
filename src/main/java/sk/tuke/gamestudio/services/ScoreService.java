package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Score;

import java.util.List;

public interface ScoreService {
    void addScore(Score score);
    List<String> getDataForAccount(String username);
    List<Score> getTop10();
    void reset();
}
