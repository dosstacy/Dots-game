package main.java.sk.tuke.gamestudio.game.dots.entity;

public class Rating {
    private int rating;
    public Rating(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
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
        }
        return "★★★★★";
    }
}
