package main.java.sk.tuke.gamestudio.services;

import main.java.sk.tuke.gamestudio.entity.Rating;
import main.java.sk.tuke.gamestudio.game.dots.features.Color;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RatingServiceJDBC implements RatingService{
    private static final String URL = "jdbc:postgresql://localhost:5432/gamestudio";
    private static final String USER = "postgres";
    private static final String PASSWORD = "dosstpostgre";

    @Override
    public int getRating(String username) {
        String GET_RATING = "SELECT rating_value FROM rating WHERE username = ?;";
        int rating = 0;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(GET_RATING)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            rating = resultSet.getInt("rating_value");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rating;
    }
    @Override
    public void setRating(Rating rating, String username){
        String ADD_RATING = "INSERT INTO rating (rating, username, rated_on) VALUES (?, ?, ?)\n" +
                "ON CONFLICT (username) DO UPDATE SET rating = EXCLUDED.rating, rated_on = EXCLUDED.rated_on;";
        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_RATING)){
            preparedStatement.setInt(1, rating.getRating());
            preparedStatement.setString(2, username);
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(Color.ANSI_RED + "Please try again.\n" + Color.ANSI_RESET);
        }
    }
    @Override
    public int getAverageRating(String game) {
        return 0;
    }
    @Override
    public void reset() {

    }
}
