package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.services.GameStudioException;
import sk.tuke.gamestudio.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserServiceRest {

    @Autowired
    private UserService userService;

    @PostMapping("/createAccount")
    public void addUser(@RequestBody User user) {
        try {
            userService.addUser(user);
        }catch (Exception e) {
            throw new GameStudioException(e);
        }
    }

    @PostMapping("/logIn")
    public void loginUser(@RequestBody User user) {
        userService.loginUser(user.getUsername(), user.getPassword());
    }
}