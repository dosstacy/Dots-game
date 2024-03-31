package sk.tuke.gamestudio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity

@NamedQuery( name = "Comment.getUserComments",
        query = "SELECT c.comment, c.commented_on FROM Comment c WHERE c.username = :username")

@NamedQuery( name = "Comment.getCommentsForCommunity",
        query = "SELECT c.username, c.comment, c.commented_on FROM Comment c")

@NamedQuery( name = "Comment.reset",
        query = "DELETE FROM Comment")

public class Comment implements Serializable {
    @Id
    @GeneratedValue
    private int ident;
    private String comment;
    private String username;
    private Timestamp commented_on;
    public Comment(String comment, Timestamp commented_on) {
        setComment(comment);
        setCommented_on(commented_on);
    }
    public Comment(String username, String comment, Timestamp commented_on){
        setUsername(username);
        setComment(comment);
        setCommented_on(commented_on);
    }
    public Comment(){

    }
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getCommented_on() {
        return commented_on;
    }
    public void setCommented_on(Timestamp commentedOn){
        this.commented_on = commentedOn;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public int getIdent() {
        return ident;
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }
}
