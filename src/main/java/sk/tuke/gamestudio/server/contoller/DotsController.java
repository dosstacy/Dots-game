package sk.tuke.gamestudio.server.contoller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.game.dots.core.GameBoard;
import sk.tuke.gamestudio.game.dots.features.Color;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/dots")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class DotsController {
    private GameBoard gameField;

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
        prepareModel(model);
        return "dots";
    }

    @PostMapping("/new")
    public String newGamePost(Model model, HttpSession session) {
        gameField = (GameBoard) session.getAttribute("gameBoard");

        if (gameField == null) {
            gameField = new GameBoard();
            session.setAttribute("gameBoard", gameField);
        }
        prepareModel(model);
        return "dots";
    }

    private String getHtmlGameBoard() {
        StringBuilder sb = new StringBuilder();

        sb.append("<table class='dots-board'>");
        for (int row = 0; row < gameField.getBoardSize(); row++) {
            sb.append("<tr>");
            for (int col = 0; col < gameField.getBoardSize(); col++) {

                sb.append("<td>");
                sb.append(String.format("<form action='/dots/new' method='post'>" +
                        "<input type='hidden' name='row' value='%d'>" +
                        "<input type='hidden' name='col' value='%d'>" +
                        "<button type='submit' class='dot-button %s'" +
                        "</button>" +
                        "</form>", row, col, Color.returnColor(gameField.gameBoard[row][col].getDot())));
                sb.append("</td>");
            }
            sb.append("</tr>");
        }
        sb.append("</table>");

        return sb.toString();
    }

    private void prepareModel(Model model) {
        model.addAttribute("htmlboard", getHtmlGameBoard());
    }
}
