package sk.tuke.gamestudio.services;

import javax.persistence.*;
import javax.transaction.Transactional;

import sk.tuke.gamestudio.entity.GetTop10;
import sk.tuke.gamestudio.entity.MaxScoreResult;
import sk.tuke.gamestudio.entity.Score;
import java.util.List;

@Transactional
public class ScoreServiceJPA implements ScoreService{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void addScore(Score score) {
        if(score.getScore() == 0){
            return;
        }
        try {
            Query query = entityManager.createNativeQuery("INSERT INTO score (score, username, gamemode, date) " +
                    "VALUES (:score, :username, :gamemode, :date) ON CONFLICT (username, gamemode) " +
                    "DO UPDATE SET score = EXCLUDED.score, date = EXCLUDED.date WHERE EXCLUDED.score > score.score");
            query.setParameter("score", score.getScore());
            query.setParameter("username", score.getUsername());
            query.setParameter("gamemode", score.getGamemode());
            query.setParameter("date", score.getDate());
            query.executeUpdate();
        } catch (PersistenceException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public List<MaxScoreResult> getDataForAccount(String username) {
        try {
            entityManager.createNativeQuery("TRUNCATE TABLE max_score_result;").executeUpdate();
            entityManager.createNativeQuery("""
                            INSERT INTO max_score_result (max_result, gamemode, date)
                            SELECT MAX(s.score) as max_result, s.gamemode, s.date
                            FROM Score s
                            WHERE s.username = :username
                            GROUP BY s.date, s.gamemode
                            ORDER BY max_result DESC
                            ON CONFLICT (gamemode) DO UPDATE SET max_result = EXCLUDED.max_result, gamemode = EXCLUDED.gamemode, date = EXCLUDED.date;""")
                    .setParameter("username", username)
                    .executeUpdate();
        }catch (Exception e){
            throw new GameStudioException(e);
        }

        return entityManager.createNamedQuery("MaxScoreResult.getDataForAccount", MaxScoreResult.class)
                .getResultList();
    }

    @Override
    public List<GetTop10> getTop10() {
        try{
                entityManager.createNativeQuery("INSERT INTO top10 (max_result, username, gamemode, date) " +
                                                    "SELECT MAX(s.score) as max_result, s.username, s.gamemode, s.date " +
                                                    "FROM Score s " +
                                                    "GROUP BY s.username, s.gamemode, s.date " +
                                                    "ORDER BY max_result DESC " +
                                                    "ON CONFLICT (gamemode, username) DO UPDATE SET max_result = EXCLUDED.max_result, gamemode = EXCLUDED.gamemode, date = EXCLUDED.date;")
                        .executeUpdate();
        } catch (Exception e) {
            throw new GameStudioException(e);
        }
        return entityManager.createNamedQuery("Score.getTop10", GetTop10.class).getResultList();
    }

    @Override
    public void reset() {
        try {
            entityManager.createNamedQuery("Score.reset").executeUpdate();
        }catch (Exception e){
            throw new GameStudioException(e);
        }
    }
}
