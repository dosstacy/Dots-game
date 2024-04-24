package sk.tuke.gamestudio.server.contoller;

import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.services.GameStudioException;
import sk.tuke.gamestudio.services.UserService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/dots")
public class UserController {
    @Autowired
    private UserService userService;
    private String username;

    @GetMapping("/logIn")
    public String getLoginUser() {
        return "logIn";
    }

    @PostMapping("/logIn")
    public String loginUser(String username, String password, Model model) {
        try {
            setUsername(username);
            userService.loginUser(username, password);
            model.addAttribute("username", username);
            return "redirect:/dots/mainMenu";
        } catch (GameStudioException e) {
            model.addAttribute("loginError", "Incorrect username or password!");
            return "logIn";
        }
    }

    @GetMapping("/signUp")
    public String getSignUpUser() {
        return "signUp";
    }

    @PostMapping("/signUp")
    public String signUpUser(User user, Model model) {
        try {
            userService.addUser(user);
            return "redirect:/dots/mainMenu";
        } catch (GameStudioException e) {
            model.addAttribute("loginError", "This username is already taken!");
            return "signUp";
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
