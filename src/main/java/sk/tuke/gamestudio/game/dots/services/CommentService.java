package main.java.sk.tuke.gamestudio.game.dots.services;

import main.java.sk.tuke.gamestudio.game.dots.entity.Comment;
import main.java.sk.tuke.gamestudio.game.dots.entity.User;

public interface CommentService {
    void addComment(Comment comment, String username);
    void deleteComment(Comment comment);
}
