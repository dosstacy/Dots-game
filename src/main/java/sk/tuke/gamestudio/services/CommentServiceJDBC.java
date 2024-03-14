package main.java.sk.tuke.gamestudio.services;

import main.java.sk.tuke.gamestudio.entity.Comment;
import main.java.sk.tuke.gamestudio.game.dots.features.Color;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//TODO community button
public class CommentServiceJDBC implements CommentService{
    private static final String URL = "jdbc:postgresql://localhost:5432/gamestudio";
    private static final String USER = "postgres";
    private static final String PASSWORD = "dosstpostgre";

    @Override
    public void addComment(Comment comment, String username) {
        String ADD_COMMENT = "INSERT INTO comment (text, username, date) VALUES (?, ?, ?);";
        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_COMMENT)){
            preparedStatement.setString(1, comment.getComment());
            preparedStatement.setString(2, username);
            preparedStatement.setTimestamp(3, comment.getCommentedOn());
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(Color.ANSI_RED + "Please try again.\n" + Color.ANSI_RESET);
        }
    }

    @Override
    public List<Comment> getUserComments(String username){
        String GET_COMMENT = "SELECT text, date FROM comment WHERE username = ?;";
        List<Comment> comments = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(GET_COMMENT)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                comments.add(new Comment(resultSet.getString("text"),
                                         resultSet.getTimestamp("date")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return comments;
    }
    @Override
    public List<Comment> getAllComments() {
        //SELECT username, text, date FROM comment  WHERE username = ? AND date = ? ORDER BY date DESC;
        String GET_ALL_COMMENTS = "SELECT username, text, date FROM comment ORDER BY date DESC;";
        List<Comment> comments = new ArrayList<>();
        Comment comment;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(GET_ALL_COMMENTS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                comment = new Comment(resultSet.getString("username"),
                                      resultSet.getString("text"),
                                      resultSet.getTimestamp("date"));
                comments.add(comment);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return comments;
    }
    @Override
    public void reset() {

    }
}
