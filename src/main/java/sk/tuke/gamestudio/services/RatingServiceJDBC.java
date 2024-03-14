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
        String GET_RATING = "SELECT rating FROM rating WHERE username = ?;";
        int rating = 0;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(GET_RATING)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            rating = resultSet.getInt("rating");
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println(Color.ANSI_RED +  "You haven't rated the game yet" + Color.ANSI_RESET);
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
    public int getAverageRating() {
        String GET_AVERAGE_RATING = "SELECT ROUND(AVG(rating)) AS average_rating FROM rating;";
        int avgRating = 0;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(GET_AVERAGE_RATING)) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            avgRating = resultSet.getInt("average_rating");
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println(Color.ANSI_RED +  "You haven't rated the game yet" + Color.ANSI_RESET);
        }
        return avgRating;
    }
    public List<Rating> getAllRatings(){
        String GET_ALL_COMMENTS = "SELECT username, rating, rated_on FROM rating ORDER BY rated_on DESC;";
        List<Rating> ratings = new ArrayList<>();
        Rating rating;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(GET_ALL_COMMENTS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                rating = new Rating(resultSet.getString("username"),
                        resultSet.getInt("rating"),
                        resultSet.getTimestamp("rated_on"));
                ratings.add(rating);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ratings;
    }
    @Override
    public void reset() {

    }
}
