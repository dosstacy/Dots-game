package main.java.sk.tuke.gamestudio.services;

import main.java.sk.tuke.gamestudio.entity.Comment;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment, String username);
    void deleteComment(Comment comment);
    List<String> getAllComments(String username);
}
