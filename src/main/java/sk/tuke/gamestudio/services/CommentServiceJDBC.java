package main.java.sk.tuke.gamestudio.services;

import main.java.sk.tuke.gamestudio.entity.Comment;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CommentServiceJDBC implements CommentService{
    //public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final String ADD_COMMENT = "INSERT INTO comment (text, username) VALUES (?, ?);";
    private static final String GET_COMMENT = "SELECT text FROM comment WHERE username = ?;";
    private static final String URL = "jdbc:postgresql://localhost:5432/gamestudio";
    private static final String USER = "postgres";
    private static final String PASSWORD = "dosstpostgre";

    //з базиданих теж тоді видалити колонку
    @Override
    public void addComment(Comment comment, String username) {
        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_COMMENT)){
            preparedStatement.setString(1, comment.getComment());
            preparedStatement.setString(2, username);
            //LocalDate currentDate = LocalDate.now();
            //String formattedDate = currentDate.format(formatter);
            //preparedStatement.setString(3, formattedDate);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Please try again.\n");
        }
    }

    @Override
    public void deleteComment(Comment comment) {

    }
    @Override
    public List<String> getAllComments(String username){
        List<String> comments = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(GET_COMMENT)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                String comment = resultSet.getString("text");
                comments.add(comment);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return comments;
    }
}
