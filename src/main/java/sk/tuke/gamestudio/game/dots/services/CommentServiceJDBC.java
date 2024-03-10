package main.java.sk.tuke.gamestudio.game.dots.services;

import main.java.sk.tuke.gamestudio.game.dots.entity.Comment;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CommentServiceJDBC implements CommentService{
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final String ADD_COMMENT = "INSERT INTO comment (text, username, date) VALUES (?, ?, ?);";
    private static final String URL = "jdbc:postgresql://localhost:5432/gamestudio";

    @Override
    public void addComment(Comment comment, String username) {
        try(Connection connection = DriverManager.getConnection(URL, "postgres", "dosstpostgre");
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_COMMENT)){
            preparedStatement.setString(1, comment.getComment());
            preparedStatement.setString(2, username);
            LocalDate currentDate = LocalDate.now();
            String formattedDate = currentDate.format(formatter);
            preparedStatement.setString(3, formattedDate);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Please try again.\n");
        }
    }

    @Override
    public void deleteComment(Comment comment) {

    }
}
