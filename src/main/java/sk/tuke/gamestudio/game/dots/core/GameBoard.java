package sk.tuke.gamestudio.game.dots.core;

import sk.tuke.gamestudio.game.dots.features.Color;
import sk.tuke.gamestudio.game.dots.features.DotState;

public class GameBoard {
    public Dot[][] gameBoard;
    private final int boardSize = 6;
    private final Selection selection;
    public Dot[][] selectedDots;

    public GameBoard() {
        gameBoard = new Dot[boardSize][boardSize];
        selectedDots = new Dot[boardSize][boardSize];
        for (int row = 0; row < selectedDots.length; row++) {
            for (int col = 0; col < selectedDots.length; col++) {
                selectedDots[row][col] = new Dot("0");
            }
        }
        selection = new Selection();
        createGameBoard();
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void createGameBoard(){
        for(int row = 0; row < boardSize; row++){
            for(int col = 0; col < boardSize; col++){
                gameBoard[row][col] = new Dot(Color.randomColor() + "◯" + Color.ANSI_RESET);
            }
        }
    }

    public void missingAnimation() {
        for (int row = 0; row < selectedDots.length; row++) {
            for (int col = 0; col < selectedDots.length; col++) {
                if (selectedDots[row][col].dot.contains("◯")) {
                    selection.resetSelection(gameBoard[row][col].dot);
                    gameBoard[row][col].setState(DotState.NOT_SELECTED);
                    gameBoard[row][col].dot = "*";
                }
            }
        }
        cleanArray();
    }
    public void shiftDotsDown() {
        for (int row = 0; row < boardSize; row++) {
            for (int col = boardSize - 1; col >= 0; col--) {
                if (gameBoard[col][row].dot.equals("*")) {
                    int newRowIndex = col - 1;
                    while (newRowIndex >= 0 && gameBoard[newRowIndex][row].dot.equals("*")) {
                        newRowIndex--;
                    }
                    if (newRowIndex >= 0) {
                        gameBoard[col][row].dot = gameBoard[newRowIndex][row].dot;
                        gameBoard[newRowIndex][row].dot = "*";
                    }else {
                        gameBoard[col][row].dot = Color.randomColor() + "◯" + Color.ANSI_RESET;
                    }
                }
            }
        }
    }

    public void cleanArray(){
        for (Dot[] selectedDot : selectedDots) {
            for (int j = 0; j < selectedDots.length; j++) {
                selectedDot[j].dot = "0";
            }
        }
    }
}
