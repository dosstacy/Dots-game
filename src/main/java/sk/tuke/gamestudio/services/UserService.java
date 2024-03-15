package main.java.sk.tuke.gamestudio.services;

import main.java.sk.tuke.gamestudio.entity.User;

public interface UserService {
    void addUser(User user);
    void loginUser(String username, String password);
    void reset();
}
