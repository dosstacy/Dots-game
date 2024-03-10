package main.java.sk.tuke.gamestudio.game.dots.services;
import main.java.sk.tuke.gamestudio.game.dots.entity.User;

import java.sql.*;

public class UserServiceJDBC implements UserService{
    private static final String ADD_USER = "INSERT INTO user_data(username, user_password) VALUES (?, ?);";
    private static final String URL = "jdbc:postgresql://localhost:5432/gamestudio";
    public boolean loginCheck;

    @Override
    public void addUser(User user) {
        try(Connection connection = DriverManager.getConnection(URL, "postgres", "dosstpostgre");
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER))
        {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
            loginCheck = true;
        }catch (Exception e){
            e.printStackTrace();
            loginCheck = false;
            System.out.println("This login is already in use! Please enter another login.\n");
        }
    }

    public void loginUser(String username, String password) {
        try (Connection connection = DriverManager.getConnection(URL, "postgres", "dosstpostgre")) {
            String query = "SELECT COUNT(*) FROM user_data WHERE username = ?"; //рахує к-сть username

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                int count = resultSet.getInt(1);

                if (count == 0) {
                    System.out.println("user doesnt exist.");
                    return;
                }
            }

            query = "SELECT user_password FROM user_data WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                String dbPassword = resultSet.getString("user_password");

                if (!password.equals(dbPassword)) {
                    System.out.println("incorrect password.");
                    return;
                }
            }

            System.out.println("successful sign up!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
