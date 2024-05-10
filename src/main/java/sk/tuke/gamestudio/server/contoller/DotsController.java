package sk.tuke.gamestudio.server.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.dots.consoleUI.JDBCUI;
import sk.tuke.gamestudio.game.dots.core.Dot;
import sk.tuke.gamestudio.game.dots.core.GameBoard;
import sk.tuke.gamestudio.game.dots.core.Selection;
import sk.tuke.gamestudio.game.dots.features.Color;
import sk.tuke.gamestudio.game.dots.features.DotState;
import sk.tuke.gamestudio.game.dots.features.PlayingMode;
import sk.tuke.gamestudio.services.ScoreService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.sql.Timestamp;
import java.util.Stack;

@Controller
@RequestMapping("/dots")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class DotsController {
    private GameBoard gameBoard;
    private Selection selection;
    private int lastRowIndex = -1;
    private int lastColIndex = -1;
    private int rowIndex = -1;
    private int colIndex = -1;
    private String mode;
    @Autowired
    private ScoreService scoreService;
    private final Stack<Point> selectedDotsStack = new Stack<>();
    private PlayingMode playingMode;
    private final JDBCUI JDBCUI = new JDBCUI();

    @GetMapping("/saveProgress")
    public String saveProgress(HttpSession session) {
        String username  = (String)session.getAttribute("username");
        if(username == null){
            return "redirect:/dots/unregisteredPage";
        }
        return "saveProgress";
    }

    @PostMapping("/saveProgressPost")
    public String saveProgressPost(HttpSession session) {
        String username  = (String)session.getAttribute("username");
        if(username == null){
            return "redirect:/dots/unregisteredPage";
        }

        Score score = new Score((String) session.getAttribute("username"), JDBCUI.getScores(), mode, new Timestamp(System.currentTimeMillis()));
        scoreService.addScore(score);
        return "redirect:/dots/mainMenu";
    }

    @PostMapping("/game/new/{mode}")
    public String newGame(HttpSession session, @PathVariable String mode) {
        String username  = (String)session.getAttribute("username");
        if(username == null){
            return "redirect:/dots/unregisteredPage";
        }

        session.removeAttribute("gameBoard");
        gameBoard = new GameBoard();
        session.setAttribute("gameBoard", gameBoard);
        JDBCUI.setScores(0);
        if (mode.equals("moves")) {
            playingMode = PlayingMode.MOVES;
            gameBoard.setMoves(20);
        }else{
            playingMode = PlayingMode.ENDLESS;
        }
        this.mode = mode;
        return "redirect:/dots/game";
    }

    @GetMapping("/game")
    public String newGame(Model model, HttpSession session) {
        String username  = (String)session.getAttribute("username");
        if(username == null){
            return "redirect:/dots/unregisteredPage";
        }

        prepareModel(model);
        return "dots";
    }

    @PostMapping("/game")
    public String newGamePost(Model model, HttpServletRequest request) {
        var session = request.getSession();
        String username  = (String)session.getAttribute("username");
        if(username == null){
            return "redirect:/dots/unregisteredPage";
        }
        selection = new Selection(gameBoard);

        if (session.getAttribute("gameBoard") == null) {
            return "redirect:/dots/modeMenu";
        }

        if (rowIndex == -1 || colIndex == -1) {
            lastRowIndex = rowIndex;
            lastColIndex = colIndex;
        }

        rowIndex = Integer.parseInt(request.getParameter("row"));
        colIndex = Integer.parseInt(request.getParameter("col"));
        Dot currentDot = gameBoard.gameBoard[rowIndex][colIndex];

        if(isSameColor(lastRowIndex, lastColIndex, rowIndex, colIndex) && currentDot != null) {
            if (currentDot.getState() == DotState.NOT_SELECTED) {
                if (((lastRowIndex == -1 && lastColIndex == -1) || isValidMove(lastRowIndex, lastColIndex, rowIndex, colIndex))) {
                    selectDot(currentDot);
                }
            } else if (currentDot.getState() == DotState.SELECTED) {
                Point point = new Point(rowIndex, colIndex);
                if (!selectedDotsStack.isEmpty() && selectedDotsStack.peek().equals(point)) {
                    resetSelectionAt(rowIndex, colIndex);
                    selectedDotsStack.pop();
                } else {
                    while (!selectedDotsStack.isEmpty() && !selectedDotsStack.peek().equals(point)) {
                        Point p = selectedDotsStack.pop();
                        resetSelectionAt(p.x, p.y);
                    }
                }
                lastRowIndex = -1;
                lastColIndex = -1;
            }
        }
        prepareModel(model);
        return "dots";
    }

    private void resetSelectionAt(int row, int col) {
        Dot currentDot = gameBoard.gameBoard[row][col];
        if (currentDot != null && currentDot.getState() == DotState.SELECTED) {
            currentDot.setDot(selection.resetSelection(currentDot));
            currentDot.setState(DotState.NOT_SELECTED);
            gameBoard.selectedDots[row][col].dot = "0";
        }
    }

    private void selectDot(Dot currentDot){
        gameBoard.selectedDots[rowIndex][colIndex].dot = gameBoard.gameBoard[rowIndex][colIndex].dot;
        selectedDotsStack.push(new Point(rowIndex, colIndex));
        currentDot.setDot(selection.selectDot(currentDot));
        currentDot.setState(DotState.SELECTED);
        lastRowIndex = rowIndex;
        lastColIndex = colIndex;
    }

    private boolean isValidMove(int lastRow, int lastCol, int nextRow, int nextCol) {
        return (nextRow == lastRow && Math.abs(nextCol - lastCol) == 1) || (nextCol == lastCol && Math.abs(nextRow - lastRow) == 1);
    }

    private boolean isSameColor(int lastRow, int lastCol, int nextRow, int nextCol){
        if(lastRow == -1 || lastCol == -1 || nextRow == -1 || nextCol == -1){
            return true;
        }
        return gameBoard.gameBoard[lastRow][lastCol].dot.contains(gameBoard.gameBoard[nextRow][nextCol].dot);
    }

    @GetMapping("/newSpace")
    public String newSpace(HttpSession session) {
        String username  = (String)session.getAttribute("username");
        if(username == null){
            return "redirect:/dots/unregisteredPage";
        }

        gameBoard.setCountDots(0);
        for (int row = 0; row < gameBoard.selectedDots.length; row++) {
            for (int col = 0; col < gameBoard.selectedDots.length; col++) {
                if (!gameBoard.selectedDots[row][col].dot.equals("0")) {
                    gameBoard.setCountDots(gameBoard.getCountDots() + 1);
                }
            }
        }
        if (gameBoard.getCountDots() > 1) {
            JDBCUI.setScores(JDBCUI.getScores() + gameBoard.getCountDots());
            if (playingMode == PlayingMode.MOVES) {
                gameBoard.setMoves(gameBoard.getMoves() - 1);
            }
            gameBoard.missingAnimation();
            gameBoard.shiftDotsDown();
            selection.resetAllSelection(gameBoard);
            lastRowIndex = -1;
            lastColIndex = -1;
        }

        if(gameBoard.getMoves() <= 0){
            Score score = new Score((String) session.getAttribute("username"), JDBCUI.getScores(), mode, new Timestamp(System.currentTimeMillis()));
            scoreService.addScore(score);
            return "redirect:/dots/afterGameWindow";
        }
        return "redirect:/dots/game";
    }

    private String getHtmlGameBoard() {
        StringBuilder sb = new StringBuilder();

        sb.append("<table class='dots-board'>");
        for (int row = 0; row < gameBoard.getBoardSize(); row++) {
            sb.append("<tr>");
            for (int col = 0; col < gameBoard.getBoardSize(); col++) {

                sb.append("<td>");
                sb.append(String.format("<form action='/dots/game' method='post'>" +
                        "<input type='hidden' name='row' value='%d'>" +
                        "<input type='hidden' name='col' value='%d'>" +
                        "<button type='submit' class='dot-button %s'" +
                        "</button>" +
                        "</form>", row, col, Color.returnColor(gameBoard.gameBoard[row][col].getDot())));
                sb.append("</td>");
            }
            sb.append("</tr>");
        }
        sb.append("</table>");

        if(playingMode == PlayingMode.MOVES){
            sb.append(String.format("<span class='moves-count'>Moves: <br>%d</span>" + "<span class='scores-1'>Scores: <br>%d</span>",
                    gameBoard.getMoves(), JDBCUI.getScores()));
        }else{
            sb.append(String.format("<span class='scores'>Scores: <br>%d</span>", JDBCUI.getScores()));
        }

        return sb.toString();
    }

    private void prepareModel(Model model) {
        model.addAttribute("htmlboard", getHtmlGameBoard());
    }
}
