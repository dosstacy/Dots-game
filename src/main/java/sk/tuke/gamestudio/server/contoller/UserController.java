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
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/dots/logIn")
    public String getLoginUser() {
        return "logIn";
    }

    @PostMapping("/dots/logIn")
    public String loginUser(String username, String password, Model model) {
        try {
            userService.loginUser(username, password);
            return "redirect:/dots/mainMenu";
        } catch (GameStudioException e) {
            model.addAttribute("loginError", "Неправильний логін або пароль");
            return "logIn";
        }
    }

    @GetMapping("/dots/signUp")
    public String getSignUpUser() {
        return "signUp";
    }

    @PostMapping("/dots/signUp")
    public String signUpUser(User user, Model model) {
        try {
            userService.addUser(user);
            return "redirect:/dots/mainMenu";
        } catch (GameStudioException e) {
            model.addAttribute("loginError", "User already exists");
            return "signUp";
        }
    }

}
