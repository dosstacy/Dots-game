package sk.tuke.gamestudio.services;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.game.dots.features.Color;

@Transactional
public class RatingServiceJPA implements RatingService{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void setRating(Rating rating) {
        try {
            Query query = entityManager.createNativeQuery("INSERT INTO rating (rating, username, rated_on) VALUES (:rating, :username, :rated_on) " +
                    "ON CONFLICT (username) DO UPDATE SET rating = EXCLUDED.rating, rated_on = EXCLUDED.rated_on;");
            query.setParameter("rating", rating.getRating());
            query.setParameter("username", rating.getUsername());
            query.setParameter("rated_on", rating.getRatedOn());
            query.executeUpdate();
        }catch (Exception e){
            throw new GameStudioException(e);
        }
    }

    @Override
    public int getAverageRating() {
        try {
            Double result = entityManager.createNamedQuery("Rating.getAverageRating", Double.class)
                    .getSingleResult();

            if (result != null) {
                return (int) Math.round(result);
            } else {
                System.out.println(Color.ANSI_RED + "No one has rated the game yet" + Color.ANSI_RESET);
                return 0;
            }
        } catch (Exception e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public int getRating(String username) {
        int rating = 0;
        try {
            rating =  entityManager.createNamedQuery("Rating.getRating", Integer.class)
                    .setParameter("username", username)
                    .getSingleResult();
        }catch (NoResultException e){
            System.out.println(Color.ANSI_RED + "You haven't rated the game yet" + Color.ANSI_RESET);
        }catch (Exception e){
            throw new GameStudioException(e);
        }
        return rating;
    }

    @Override
    public void reset() {
        try {
            entityManager.createNamedQuery("Rating.reset").executeUpdate();
        }catch (Exception e){
            throw new GameStudioException(e);
        }
    }
}
