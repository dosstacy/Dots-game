package main.java.sk.tuke.gamestudio.services;
import main.java.sk.tuke.gamestudio.entity.User;
import main.java.sk.tuke.gamestudio.game.dots.features.Color;

import java.sql.*;

public class UserServiceJDBC implements UserService{
    private static final String ADD_USER = "INSERT INTO user_data(username, user_password) VALUES (?, ?);";
    private static final String URL = "jdbc:postgresql://localhost:5432/gamestudio";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "dosstpostgre";
    public boolean loginCheck = false;
    public boolean signUpCheck = false;

    @Override
    public void addUser(User user) {
        try(Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER))
        {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
            signUpCheck = true;
            System.out.println();
            System.out.println(Color.ANSI_GREEN + "Successful sign up!" + Color.ANSI_RESET);
        }catch (Exception e){
            //e.printStackTrace();
            signUpCheck = false;
            System.out.println(Color.ANSI_RED + "This login is already in use! Please enter another login." + Color.ANSI_RESET);
        }
    }

    public void loginUser(String username, String password) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT COUNT(*) FROM user_data WHERE username = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                int count = resultSet.getInt(1);

                if (count == 0) {
                    System.out.println(Color.ANSI_RED + "User doesn't exist. Please try again." + Color.ANSI_RESET);
                    return;
                }
            }

            query = "SELECT user_password FROM user_data WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();
                String dbPassword = null;
                while (resultSet.next()) {
                    dbPassword = resultSet.getString("user_password");
                }

                if (!password.equals(dbPassword)) {
                    System.out.println(Color.ANSI_RED + "Incorrect password." + Color.ANSI_RESET);
                    return;
                }
            }
            System.out.println();
            System.out.println(Color.ANSI_GREEN + "Successful log in!" + Color.ANSI_RESET);
            loginCheck = true;
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println(Color.ANSI_RED + "Something went wrong... Please wait an administrator :/ " + Color.ANSI_RESET);
        }
    }
}
