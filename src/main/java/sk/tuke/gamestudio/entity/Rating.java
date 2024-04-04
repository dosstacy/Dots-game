package sk.tuke.gamestudio.entity;

import javax.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})


@NamedQuery( name = "Rating.getRating",
        query = "SELECT r.rating FROM Rating r WHERE r.username = :username")

@NamedQuery( name = "Rating.getAverageRating",
        query = "SELECT AVG(r.rating) AS average_rating FROM Rating r")

@NamedQuery( name = "Rating.reset",
        query = "DELETE FROM Rating")

public class Rating implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ident;
    @Column(name = "rating")
    private int rating;
    @Column(name = "rated_on")
    private Timestamp ratedOn;
    @Column(name = "username")
    private String username;
    public Rating(int rating) {
        setRating(rating);
    }
    public Rating(){

    }
    public int getRating() {
        return rating;
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

    public Timestamp getRatedOn() {
        return ratedOn;
    }

    public void setRatedOn(Timestamp ratedOn) {
        this.ratedOn = ratedOn;
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
