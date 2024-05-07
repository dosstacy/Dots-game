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

import javax.servlet.http.HttpSession;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/dots")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/logIn")
    public String getLoginUser() {
        return "logIn";
    }

    @PostMapping("/logIn")
    public String loginUser(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        try {
            userService.loginUser(username, password);
            session.setAttribute("username", username);
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
    public String signUpUser(User user, Model model, HttpSession session) {
        try {
            userService.addUser(user);
            session.setAttribute("username", user.getUsername());
            return "redirect:/dots/mainMenu";
        } catch (GameStudioException e) {
            model.addAttribute("loginError", "This username is already taken!");
            return "signUp";
        }
    }
}
