package sk.tuke.gamestudio.services;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import sk.tuke.gamestudio.entity.Score;

import java.util.List;

@Transactional
public class ScoreServiceJPA implements ScoreService{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void addScore(Score score) {
        try {
            Query query = entityManager.createNativeQuery("INSERT INTO score (score, username, gamemode, date) " +
                    "VALUES (:score, :username, :gamemode, :date) ON CONFLICT (username, gamemode) " +
                    "DO UPDATE SET score = EXCLUDED.score, date = EXCLUDED.date WHERE EXCLUDED.score > :score;");
            query.setParameter("score", score.getScore());
            query.setParameter("username", score.getUsername());
            query.setParameter("gamemode", score.getGamemode());
            query.setParameter("date", score.getDate());
            query.executeUpdate();
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getDataForAccount(String username) {
        TypedQuery<String> query = entityManager.createNamedQuery("Score.getDataForAccount", String.class);
        return query.setParameter("username", username).getResultList();
    }

    @Override
    public List<Score> getTop10() {
        TypedQuery<Score> query = entityManager.createNamedQuery("Score.getTop10", Score.class);
        query.setMaxResults(10);
        return query.getResultList();
    }

    @Override
    public void reset() {
        entityManager.createNamedQuery("Score.reset").executeUpdate();
    }
}
