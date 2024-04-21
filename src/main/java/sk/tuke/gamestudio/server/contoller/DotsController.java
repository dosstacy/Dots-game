package sk.tuke.gamestudio.server.contoller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.game.dots.core.GameBoard;

@Controller
@RequestMapping("/dots")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class DotsController {
    private GameBoard gameBoard = new GameBoard();

    @GetMapping()
    public String startMenu(Model model) {
        prepareModel(model);
        return "startMenu";
    }

    @GetMapping("/mainMenu")
    public String mainMenu(Model model) {
        prepareModel(model);
        return "mainMenu";
    }

    private String getHtmlGameBoard(){
        StringBuilder html = new StringBuilder();

        return html.toString();
    }

    private void prepareModel(Model model) {
        model.addAttribute("htmlboard", getHtmlGameBoard());
    }

}
