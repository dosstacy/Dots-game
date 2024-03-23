package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Comment;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment, String username);
    List<Comment> getUserComments(String username);
    List<Comment> getCommentsForCommunity();
    void reset();
}
