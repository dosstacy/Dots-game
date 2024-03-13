package main.java.sk.tuke.gamestudio.services;

import main.java.sk.tuke.gamestudio.entity.Rating;

import java.util.List;

public interface RatingService {
    void setRating(Rating rating, String username);
    int getAverageRating(String game);
    int getRating(String username);
    void reset();
}
