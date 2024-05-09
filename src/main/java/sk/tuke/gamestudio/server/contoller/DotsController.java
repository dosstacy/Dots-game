package sk.tuke.gamestudio.server.contoller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.game.dots.consoleUI.JDBCConsoleUI;
import sk.tuke.gamestudio.game.dots.core.Dot;
import sk.tuke.gamestudio.game.dots.core.GameBoard;
import sk.tuke.gamestudio.game.dots.core.Selection;
import sk.tuke.gamestudio.game.dots.features.Color;
import sk.tuke.gamestudio.game.dots.features.DotState;
import sk.tuke.gamestudio.game.dots.features.PlayingMode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.util.Stack;

@Controller
@RequestMapping("/dots")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class DotsController {
    private static GameBoard gameField = new GameBoard();
    private Selection selection;
    private int lastRowIndex = -1;
    private int lastColIndex = -1;
    private int rowIndex = -1;
    private int colIndex = -1;
    private final Stack<Point> selectedDotsStack = new Stack<>();
    private PlayingMode playingMode;
    private final JDBCConsoleUI jdbcConsoleUI = new JDBCConsoleUI();

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

    @GetMapping("/new/{mode}")
    public String newGame(Model model, @PathVariable String mode) {
        if(mode.equals("timed")){
            playingMode = PlayingMode.TIMED;
        } else if (mode.equals("moves")) {
            playingMode = PlayingMode.MOVES;
        }else{
            playingMode = PlayingMode.ENDLESS;
        }
        System.out.println(mode);
        prepareModel(model);
        return "dots";
    }

    @PostMapping("/new")
    public String newGamePost(Model model, HttpSession session, HttpServletRequest request) {
        selection = new Selection(gameField);

        if (session.getAttribute("gameBoard") == null) {
            session.setAttribute("gameBoard", gameField);
        }

        if (rowIndex == -1 || colIndex == -1) {
            lastRowIndex = rowIndex;
            lastColIndex = colIndex;
        }

        String row = request.getParameter("row");
        String col = request.getParameter("col");
        rowIndex = Integer.parseInt(row);
        colIndex = Integer.parseInt(col);
        Dot currentDot = gameField.gameBoard[rowIndex][colIndex];

        if(isSameColor(gameField, lastRowIndex, lastColIndex, rowIndex, colIndex)) {
            System.out.println("lastRowIndex: " + lastRowIndex + " lastColIndex: " + lastColIndex);
            System.out.println("rowIndex: " + rowIndex + " colIndex: " + colIndex);
            if (currentDot != null && currentDot.getState() == DotState.NOT_SELECTED) {
                if (((lastRowIndex == -1 && lastColIndex == -1) || isValidMove(lastRowIndex, lastColIndex, rowIndex, colIndex))) {
                    gameField.selectedDots[rowIndex][colIndex].dot = gameField.gameBoard[rowIndex][colIndex].dot;
                    selectedDotsStack.push(new Point(rowIndex, colIndex));
                    currentDot.setDot(selection.selectDot(currentDot));
                    currentDot.setState(DotState.SELECTED);
                    lastRowIndex = rowIndex;
                    lastColIndex = colIndex;
                } else {
                    System.out.println("виберіть сусідню кульку.");
                }
            } else if (currentDot != null && currentDot.getState() == DotState.SELECTED) {
                if (!selectedDotsStack.isEmpty() && selectedDotsStack.peek().equals(new Point(rowIndex, colIndex))) {
                    resetSelectionAt(rowIndex, colIndex);
                    selectedDotsStack.pop();
                } else {
                    while (!selectedDotsStack.isEmpty() && !selectedDotsStack.peek().equals(new Point(rowIndex, colIndex))) {
                        Point p = selectedDotsStack.pop();
                        resetSelectionAt(p.x, p.y);
                    }
                }
                lastRowIndex = -1;
                lastColIndex = -1;
            }
        }else {
            System.out.println("another color");
        }
        prepareModel(model);
        return "dots";
    }

    private void resetSelectionAt(int row, int col) {
        Dot currentDot = gameField.gameBoard[row][col];
        if (currentDot != null && currentDot.getState() == DotState.SELECTED) {
            currentDot.setDot(selection.resetSelection(currentDot));
            currentDot.setState(DotState.NOT_SELECTED);
            gameField.selectedDots[row][col].dot = "0";
        }
    }

    private boolean isValidMove(int lastRow, int lastCol, int nextRow, int nextCol) {
        return (nextRow == lastRow && Math.abs(nextCol - lastCol) == 1) || (nextCol == lastCol && Math.abs(nextRow - lastRow) == 1);
    }

    private boolean isSameColor(GameBoard field, int lastRow, int lastCol, int nextRow, int nextCol){
        if(lastRow == -1 || lastCol == -1 || nextRow == -1 || nextCol == -1){
            return true;
        }
        return field.gameBoard[lastRow][lastCol].dot.contains(field.gameBoard[nextRow][nextCol].dot);
    }

    @GetMapping("/newSpace")
    public String newSpace() {
        gameField.setCountDots(0);
        for (int row = 0; row < gameField.selectedDots.length; row++) {
            for (int col = 0; col < gameField.selectedDots.length; col++) {
                if (!gameField.selectedDots[row][col].dot.equals("0")) {
                    gameField.setCountDots(gameField.getCountDots() + 1);
                }
            }
        }
        if (gameField.getCountDots() > 1) {
            jdbcConsoleUI.setScores(jdbcConsoleUI.getScores() + gameField.getCountDots());
            if (playingMode == PlayingMode.MOVES) {
                gameField.setMoves(gameField.getMoves() - 1);
            }
        }
        gameField.missingAnimation();
        gameField.shiftDotsDown();
        selection.resetAllSelection(gameField);
        lastRowIndex = -1;
        lastColIndex = -1;

        if(gameField.getMoves() == 0){
            System.out.println("ходи закінчилися");
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

        if(playingMode == PlayingMode.MOVES){
            sb.append(String.format("<span class='moves-count'>Moves: <br>%d</span>" + "<span class='scores-1'>Scores: <br>%d</span>",
                    gameField.getMoves(), gameField.getCountDots()));
            System.out.println(gameField.getCountDots());
        } else if (playingMode == PlayingMode.TIMED) {
            sb.append(String.format("<span class='time'>Time: </span>" + "<span class='scores-1'>Scores: <br>%d</span>", gameField.getCountDots()));
        }else{
            sb.append(String.format("<span class='scores'>Scores: <br>%d</span>", gameField.getCountDots()));
        }

        return sb.toString();
    }

    private void prepareModel(Model model) {
        model.addAttribute("htmlboard", getHtmlGameBoard());
    }
}
