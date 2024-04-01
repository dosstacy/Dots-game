package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Users;

public interface UserService {
    void addUser(Users users);
    void loginUser(String username, String password);
    void reset();
}
