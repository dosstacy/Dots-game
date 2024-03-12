package main.java.sk.tuke.gamestudio.services;

import main.java.sk.tuke.gamestudio.entity.Rating;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RatingServiceJDBC implements RatingService{
    //public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final String ADD_RATING = "INSERT INTO rating (rating_value, username) VALUES (?, ?);";
    private static final String GET_RATING = "SELECT rating_value FROM rating WHERE username = ?;";
    private static final String URL = "jdbc:postgresql://localhost:5432/gamestudio";
    private static final String USER = "postgres";
    private static final String PASSWORD = "dosstpostgre";

    @Override
    public void addRating(Rating rating, String username){
        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_RATING)){
            preparedStatement.setInt(1, rating.getRating());
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
    //видалити з дб колонку часу і дати

    @Override
    public void deleteRating(Rating rating) {

    }
    @Override
    public List<Integer> getAllRatings(String username){
        List<Integer> ratings = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(GET_RATING)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int rating = resultSet.getInt("rating_value");
                ratings.add(rating);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ratings;
    }
}
