package main.java.sk.tuke.gamestudio.entity;

import java.sql.Timestamp;

public class Comment{
    private String comment;
    private String username;
    private Timestamp commentedOn;
    public Comment(String comment, Timestamp commentedOn) {
        setComment(comment);
        this.commentedOn = commentedOn;
    }
    public Comment(String username, String comment, Timestamp commentedOn) {
        setComment(comment);
        setCommentedOn(commentedOn);
        setUser(username);
    }
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getCommentedOn() {
        return commentedOn;
    }
    public void setCommentedOn(Timestamp commentedOn){
        this.commentedOn = commentedOn;
    }
    public String getUsername() {
        return username;
    }

    public void setUser(String username) {
        this.username = username;
    }
}
