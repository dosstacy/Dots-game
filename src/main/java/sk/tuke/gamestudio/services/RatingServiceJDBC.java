package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.game.dots.features.Color;

import java.sql.*;

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
        } catch (SQLException e) {
            System.out.println(Color.ANSI_RED +  "You haven't rated the game yet" + Color.ANSI_RESET); //nie je chyba
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
        }catch (SQLException e){
            throw new GameStudioException(e);
        }
    }
    @Override
    public int getAverageRating() {
        String GET_AVERAGE_RATING = "SELECT AVG(rating) AS average_rating FROM rating;";
        double avgRating;
        int result = 0;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(GET_AVERAGE_RATING)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                avgRating = resultSet.getDouble("average_rating");
                result = (int) Math.round(avgRating);
            } else {
                System.out.println(Color.ANSI_RED + "No one has rated the game yet" + Color.ANSI_RESET);
            }
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
        return result;
    }
    @Override
    public void reset() {
        String DELETE_RATING = "TRUNCATE TABLE rating";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(DELETE_RATING))
        {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }
}
