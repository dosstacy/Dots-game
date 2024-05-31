package sk.tuke.gamestudio.server.contoller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/dots")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class MenuController {
    @GetMapping()
    public String startMenu(HttpSession session) {
        session.invalidate();
        return "startMenu";
    }

    @GetMapping("/mainMenu")
    public String mainMenu(HttpSession session) {
        String username  = (String)session.getAttribute("username");
        if(username == null){
            return "redirect:/dots/unregisteredPage";
        }
        return "mainMenu";
    }

    @GetMapping("/modeMenu")
    public String modeMenu(HttpSession session) {
        String username  = (String)session.getAttribute("username");
        if(username == null){
            return "redirect:/dots/unregisteredPage";
        }
        return "modeMenu";
    }

    @GetMapping("/afterGameWindow")
    public String afterGameWindow() {
        return "afterGameWindow";
    }

    @GetMapping("/unregisteredPage")
    public String unregisteredPage() {
        return "unregisteredPage";
    }

    @GetMapping("/instruction")
    public String instructionPage() {
        return "instructionPage";
    }
}
