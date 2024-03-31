package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Rating;

public interface RatingService {
    void setRating(Rating rating, String username);
    int getAverageRating();
    int getRating(String username);
    void reset();
}
