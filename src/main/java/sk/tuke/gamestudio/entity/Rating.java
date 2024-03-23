package sk.tuke.gamestudio.entity;

import java.sql.Timestamp;

public class Rating {
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
}
