package sk.tuke.gamestudio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity

@NamedQuery( name = "Rating.getRating",
        query = "SELECT r.rating FROM Rating r WHERE r.username = :username")

//окремий метод в RatingServiceJPA
//@NamedQuery( name = "Rating.setRating",
// query = "INSERT INTO rating (rating, username, rated_on) VALUES (?, ?, ?) ON CONFLICT (username) DO UPDATE SET rating = EXCLUDED.rating, rated_on = EXCLUDED.rated_on")

@NamedQuery( name = "Rating.getAverageRating",
        query = "SELECT AVG(r.rating) AS average_rating FROM Rating r")

@NamedQuery( name = "Rating.reset",
        query = "DELETE FROM Rating")

public class Rating implements Serializable {
    @Id
    @GeneratedValue
    private int ident;
    private int rating;
    private Timestamp ratedOn;
    private String username;
    public Rating(int rating) {
        setRating(rating);
    }
    public Rating(String username, int rating, Timestamp ratedOn) {
        setUsername(username);
        setRating(rating);
        setRatedOn(ratedOn);
    }
    public Rating(){

    }
    public int getRating() {
        return rating;
    }

    public Timestamp getRatedOn() {
        return ratedOn;
    }

    public void setRatedOn(Timestamp ratedOn) {
        this.ratedOn = ratedOn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getRatingInStars(int rating){
        if(rating == 1){
            return "★✩✩✩✩";
        } else if (rating == 2) {
            return "★★✩✩✩";
        } else if (rating == 3) {
            return "★★★✩✩";
        } else if (rating == 4) {
            return "★★★★✩";
        } else if (rating == 5) {
            return "★★★★★";
        }
        return "✩✩✩✩✩";
    }

    public int getIdent() {
        return ident;
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }
}
