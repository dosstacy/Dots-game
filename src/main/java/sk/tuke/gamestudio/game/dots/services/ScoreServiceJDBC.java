package main.java.sk.tuke.gamestudio.game.dots.services;

import main.java.sk.tuke.gamestudio.game.dots.entity.Score;
import main.java.sk.tuke.gamestudio.game.dots.entity.User;
import main.java.sk.tuke.gamestudio.game.dots.features.PlayingMode;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ScoreServiceJDBC implements ScoreService {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final String ADD_SCORE = "INSERT INTO score (result, username, date, gamemode) VALUES (?, ?, ?, ?);";
    private static final String GET_BEST_USERS_SCORES = "SELECT username, MAX(result) as max_result\n" +
            "FROM score\n" +
            "WHERE gamemode = ?\n" +
            "GROUP BY username\n" +
            "ORDER BY max_result DESC\n" +
            "LIMIT 5;";
    private static final String GET_BEST_SCORE = "SELECT username, MAX(result) as max_result\n" +
            "FROM score\n" +
            "WHERE gamemode = ? AND usename = ?\n" +
            "GROUP BY username\n" +
            "ORDER BY max_result DESC\n" +
            "LIMIT 5;";
    private static final String URL = "jdbc:postgresql://localhost:5432/gamestudio";
    private static final String user = "postgres";
    private static final String password = "dosstpostgre";

    @Override
    public void addResult(Score score, String username, String mode) {
        try(Connection connection = DriverManager.getConnection(URL, user, password);
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_SCORE))
        {
            preparedStatement.setInt(1, score.getScore());
            preparedStatement.setString(2, username);
            LocalDate currentDate = LocalDate.now();
            String formattedDate = currentDate.format(formatter);
            preparedStatement.setString(3, formattedDate);
            preparedStatement.setString(4, mode);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            //e.printStackTrace();
            System.out.println("Please try again.\n");
        }
    }

    @Override
    public Map<String, Integer> getTopScores(PlayingMode playingMode) {
        Map<String, Integer> usersScores = new HashMap<>();
        try (Connection connection = DriverManager.getConnection(URL, user, password);
             PreparedStatement statement = connection.prepareStatement(GET_BEST_USERS_SCORES)) {
            if(playingMode == PlayingMode.TIMED){
                statement.setString(1, "timed");
            }else if(playingMode == PlayingMode.MOVES){
                statement.setString(1, "moves");
            }else{
                statement.setString(1, "endless");
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String usernames = resultSet.getString("username");
                int scores = resultSet.getInt("result");
                usersScores.put(usernames, scores);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersScores;
    }
    @Override
    public Map<String, Integer> getTopUserScores(PlayingMode playingMode, User username){
        Map<String, Integer> userScores = new HashMap<>();
        try (Connection connection = DriverManager.getConnection(URL, user, password);
             PreparedStatement statement = connection.prepareStatement(GET_BEST_USERS_SCORES)) {
            statement.setString(2, username.getUsername());
            if(playingMode == PlayingMode.TIMED){
                statement.setString(1, "timed");
            }else if(playingMode == PlayingMode.MOVES){
                statement.setString(1, "moves");
            }else{
                statement.setString(1, "endless");
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int scores = resultSet.getInt("result");
                userScores.put("You", scores);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userScores;
    }

}
