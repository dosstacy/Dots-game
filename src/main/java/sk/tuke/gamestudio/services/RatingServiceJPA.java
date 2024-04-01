package sk.tuke.gamestudio.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import sk.tuke.gamestudio.entity.Rating;

@Transactional
public class RatingServiceJPA implements RatingService{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void setRating(Rating rating, String username) {
        rating.setUsername(username);
        Query query = entityManager.createNativeQuery("INSERT INTO rating (rating, username, rated_on) VALUES (:rating, :username, :rated_on) " +
                "ON CONFLICT (username) DO UPDATE SET rating = EXCLUDED.rating, rated_on = EXCLUDED.rated_on;");
        query.setParameter("rating", rating.getRating());
        query.setParameter("username", username);
        query.setParameter("rated_on", rating.getRating());
        query.executeUpdate();
    }

    @Override
    public int getAverageRating() {
        return (int) entityManager.createNamedQuery("Rating.getAverageRating").getSingleResult();
    }

    @Override
    public int getRating(String username) {
        return (int) entityManager.createNamedQuery("Rating.getRating").getSingleResult();
    }

    @Override
    public void reset() {
        entityManager.createNamedQuery("Rating.reset").executeUpdate();
    }
}
