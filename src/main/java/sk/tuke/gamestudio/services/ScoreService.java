package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.GetTop10;
import sk.tuke.gamestudio.entity.MaxScoreResult;
import sk.tuke.gamestudio.entity.Score;

import java.util.List;

public interface ScoreService {
    void addScore(Score score);
    List<MaxScoreResult> getDataForAccount(String username);
    List<GetTop10> getTop10();
    void reset();
}
