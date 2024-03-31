package sk.tuke.gamestudio.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.game.dots.features.PlayingMode;

import java.util.List;
import java.util.Map;

@Transactional
public class ScoreServiceJPA implements ScoreService{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void addScore(Score score) {

    }

    @Override
    public Map<String, Integer> getTopScores(PlayingMode playingMode) {
        return null;
    }

    @Override
    public List<Integer> getTopUserScores(PlayingMode playingMode, User username) {
        return null;
    }

    @Override
    public List<String> getDataForAccount(String username) {
        return null;
    }

    @Override
    public void reset() {

    }
}
