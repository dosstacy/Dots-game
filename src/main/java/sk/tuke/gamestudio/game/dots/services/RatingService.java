package main.java.sk.tuke.gamestudio.game.dots.services;

import main.java.sk.tuke.gamestudio.game.dots.entity.Comment;
import main.java.sk.tuke.gamestudio.game.dots.entity.Rating;

public interface RatingService {
    void addRating(Rating rating, String username);
    void deleteRating(Rating rating);
}
