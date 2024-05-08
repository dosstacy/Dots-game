package sk.tuke.gamestudio.server.contoller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.game.dots.core.Dot;
import sk.tuke.gamestudio.game.dots.core.GameBoard;
import sk.tuke.gamestudio.game.dots.core.Selection;
import sk.tuke.gamestudio.game.dots.features.Color;
import sk.tuke.gamestudio.game.dots.features.DotState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/dots")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class DotsController {
    private static GameBoard gameField = new GameBoard();
    private Selection selection;

    @GetMapping()
    public String startMenu(HttpSession session) {
        session.invalidate();
        return "startMenu";
    }

    @GetMapping("/mainMenu")
    public String mainMenu() {
        return "mainMenu";
    }

    @GetMapping("/modeMenu")
    public String modeMenu(HttpSession session) {
        if (session.getAttribute("gameBoard") != null) {
            session.removeAttribute("gameBoard");
            System.out.println(session.getAttribute("gameBoard"));
            System.out.println("атрибут видалено");
        }
        gameField = new GameBoard();
        return "modeMenu";
    }

    @GetMapping("/new")
    public String newGame(Model model) {
        prepareModel(model);
        return "dots";
    }

    @PostMapping("/new")
    public String newGamePost(Model model, HttpSession session, HttpServletRequest request) {
        selection = new Selection(gameField);
        if (session.getAttribute("gameBoard") == null) {
            session.setAttribute("gameBoard", gameField);
        }

        String row = request.getParameter("row");
        String col = request.getParameter("col");
        int rowIndex = Integer.parseInt(row);
        int colIndex = Integer.parseInt(col);
        Dot currentDot = gameField.gameBoard[rowIndex][colIndex];
        if (currentDot != null && currentDot.getState() == DotState.NOT_SELECTED) {
            gameField.selectedDots[rowIndex][colIndex].dot = gameField.gameBoard[rowIndex][colIndex].dot;
            currentDot.setDot(selection.selectDot(currentDot));
            currentDot.setState(DotState.SELECTED);
        }else if(currentDot != null && currentDot.getState() == DotState.SELECTED){
            currentDot.setDot(selection.resetSelection(currentDot));
            currentDot.setState(DotState.NOT_SELECTED);
            gameField.selectedDots[rowIndex][colIndex].dot = "0";
        }

        prepareModel(model);
        return "dots";
    }

    @GetMapping("/newSpace")
    public String newSpace() {
        int countDots = 0;
        for (int row = 0; row < gameField.selectedDots.length; row++) {
            for (int col = 0; col < gameField.selectedDots.length; col++) {
                if (!gameField.selectedDots[row][col].dot.equals("0")) {
                    countDots++;
                }
            }
        }
        if (countDots > 1) {
            gameField.missingAnimation();
            gameField.shiftDotsDown();
            selection.resetAllSelection(gameField);
        }
        return "redirect:/dots/new";
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
