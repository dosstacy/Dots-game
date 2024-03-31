package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class CommentServiceJDBC implements CommentService {
    private static final String URL = "jdbc:postgresql://localhost:5432/gamestudio";
    private static final String USER = "postgres";
    private static final String PASSWORD = "dosstpostgre";

    @Override
    public void addComment(Comment comment, String username) {
        String ADD_COMMENT = "INSERT INTO comment (comment, username, commented_on) VALUES (?, ?, ?);";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_COMMENT)) {
            preparedStatement.setString(1, comment.getComment());
            preparedStatement.setString(2, username);
            preparedStatement.setTimestamp(3, comment.getCommented_on());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new GameStudioException(e);
        }
    }

    @Override
    public List<Comment> getUserComments(String username) {
        String GET_COMMENT = "SELECT comment, commented_on FROM comment WHERE username = ?;";
        List<Comment> comments = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(GET_COMMENT)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                comments.add(new Comment(resultSet.getString("comment"),
                        resultSet.getTimestamp("commented_on")));
            }
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
        return comments;
    }

    @Override
    public List<Comment> getCommentsForCommunity() {
        String GET_COMMENT = "SELECT username, comment, date FROM comment;";
        List<Comment> comments = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(GET_COMMENT)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                comments.add(new Comment(resultSet.getString("username"),
                        resultSet.getString("comment"),
                        resultSet.getTimestamp("commented_on")));
            }
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
        return comments;
    }

    @Override
    public void reset() {
        String DELETE_COMMENT = "TRUNCATE TABLE comment";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(DELETE_COMMENT))
        {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }
}

