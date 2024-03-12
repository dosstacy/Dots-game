package main.java.sk.tuke.gamestudio.services;

import main.java.sk.tuke.gamestudio.entity.Rating;

import java.util.List;

public interface RatingService {
    void addRating(Rating rating, String username);
    void deleteRating(Rating rating);
    List<Integer> getAllRatings(String username);
}
