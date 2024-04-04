package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.GetTop10;
import sk.tuke.gamestudio.entity.MaxScoreResult;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.game.dots.features.PlayingMode;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreServiceJDBC implements ScoreService {
    private static final String GET_BEST_USERS_SCORES = """
            SELECT username, MAX(score) as max_result
            FROM score
            WHERE gamemode = ?
            GROUP BY username
            ORDER BY max_result DESC
            LIMIT 5;""";
    private static final String GET_TOP_10 = """
            SELECT MAX(score) as max_result, gamemode, date, username
            FROM score
            GROUP BY date, gamemode, username
            ORDER BY max_result DESC
            LIMIT 10;""";
    private static final String GET_BEST_SCORES = """
            SELECT username, MAX(score) as max_result
            FROM score
            WHERE gamemode = ? AND username = ?
            GROUP BY username
            ORDER BY max_result DESC
            LIMIT 5;""";

    private static final String GET_ALL_DATA = """
            SELECT MAX(score) as max_result, gamemode, date
                                 FROM score
                                 WHERE username = ?
                                 GROUP BY date, gamemode
                                 ORDER BY max_result DESC
                                 LIMIT 10;""";
    private static final String URL = "jdbc:postgresql://localhost:5432/gamestudio";
    private static final String USER = "postgres";
    private static final String PASSWORD = "dosstpostgre";

    @Override
    public void addScore(Score score) {
        if(score.getScore() == 0){
            return;
        }
        String ADD_SCORE = "INSERT INTO score (score, username, gamemode, date) VALUES (?, ?, ?, ?) ON CONFLICT (username, gamemode)\n" +
                "DO UPDATE SET score = EXCLUDED.score, date = EXCLUDED.date WHERE EXCLUDED.score > score.score;";

        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_SCORE))
        {
            preparedStatement.setInt(1, score.getScore());
            preparedStatement.setString(2, score.getUsername());
            preparedStatement.setString(3, score.getGamemode());
            preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new GameStudioException(e);
        }
    }

    public Map<String, Integer> getTopScores(PlayingMode playingMode) {
        Map<String, Integer> usersScores = new HashMap<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(GET_BEST_USERS_SCORES))
        {
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
            throw new GameStudioException(e);
        }
        return usersScores;
    } //for all user after every game
    public List<Integer> getTopUserScores(PlayingMode playingMode, User username){ //найкращі після кожної гри
        List<Integer> userScores = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(GET_BEST_SCORES))
        {
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
                int scores = resultSet.getInt("score");
                userScores.add(scores);
            }
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
        return userScores;
    } //top 10 during every game

    @Override
    public List<MaxScoreResult> getDataForAccount(String username){
        MaxScoreResult maxScoreResult = new MaxScoreResult();
        List<MaxScoreResult> scores = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(GET_ALL_DATA))
        {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                maxScoreResult.setMaxResult(resultSet.getInt("max_result"));
                maxScoreResult.setGameMode(resultSet.getString("gamemode"));
                maxScoreResult.setDate(resultSet.getTimestamp("date"));
                scores.add(maxScoreResult);
            }
        }catch (SQLException e){
            throw new GameStudioException(e);
        }
        return scores;
    }
    @Override
    public List<GetTop10> getTop10(){
        List<GetTop10> scores = new ArrayList<>();
        GetTop10 users = new GetTop10();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(GET_TOP_10)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                users.setUsername(resultSet.getString("username"));
                users.setGameMode(resultSet.getString("gamemode"));
                users.setDate(resultSet.getTimestamp("date"));
                users.setMaxResult(resultSet.getInt("max_result"));
                scores.add(users);
            }
        }catch (SQLException e){
            throw new GameStudioException(e);
        }
        return scores;
    }

    @Override
    public void reset() {
        String DELETE_SCORES = "TRUNCATE TABLE score";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(DELETE_SCORES))
        {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }
}
