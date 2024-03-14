package main.java.sk.tuke.gamestudio.services;

import main.java.sk.tuke.gamestudio.entity.Comment;

import java.sql.Timestamp;
import java.util.List;

public interface CommentService {
    void addComment(Comment comment, String username);
    List<Comment> getUserComments(String username);
    List<Comment> getCommentsForCommunity();
    void reset();
}
