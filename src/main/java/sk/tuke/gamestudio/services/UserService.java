package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.User;

public interface UserService {
    void addUser(User user);
    void loginUser(String username, String password);
    void reset();
}
