package sk.tuke.gamestudio.server.contoller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.game.dots.core.Dot;
import sk.tuke.gamestudio.game.dots.core.GameBoard;

import java.util.Random;

@Controller
@RequestMapping("/dots")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class DotsController {
    private GameBoard gameBoard = new GameBoard();

    @GetMapping()
    public String startMenu() {
        return "startMenu";
    }

    @GetMapping("/mainMenu")
    public String mainMenu() {
        return "mainMenu";
    }

    @GetMapping("/modeMenu")
    public String modeMenu() {
        return "modeMenu";
    }

    @GetMapping("/new")
    public String newGame(Model model) {
        gameBoard = new GameBoard();
        prepareModel(model);
        return "dots";
    }

    private String getHtmlGameBoard(){
        StringBuilder sb = new StringBuilder();
        String[] dots = {"/images/violet.png", "/images/blue.png", "/images/green.png", "/images/yellow.png", "/images/pink.png"};

        sb.append("<table class='dots-board'>\n");
        for (int row = 0; row < gameBoard.getBoardSize(); row++) {
            sb.append("<tr>\n");
            for (int col = 0; col < gameBoard.getBoardSize(); col++) {
                gameBoard.gameBoard[row][col] = new Dot(dots[new Random().nextInt(dots.length)]);
                sb.append("<td>\n");
                sb.append(String.format("<img src='%s' alt='dot' />\n", gameBoard.gameBoard[row][col].getDot()));
                sb.append("</td>\n");
            }
            sb.append("</tr>\n");
        }
        sb.append("</table>\n");

        return sb.toString();
    }

    private void prepareModel(Model model) {
        model.addAttribute("htmlboard", getHtmlGameBoard());
        model.addAttribute("gameboard", gameBoard);
    }

}
