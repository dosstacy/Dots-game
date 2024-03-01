package main.java.sk.tuke.gamestudio.game.dots.core;

import main.java.sk.tuke.gamestudio.game.dots.features.Color;

public class GameBoard {
    public Dot[][] gameBoard;
    private final int boardSize = 6;
    private final Selection selection;
    public Dot[][] selectedDots;

    public GameBoard() {
        gameBoard = new Dot[boardSize][boardSize];
        selectedDots = new Dot[boardSize][boardSize];
        for (int i = 0; i < selectedDots.length; i++) {
            for (int j = 0; j < selectedDots.length; j++) {
                selectedDots[i][j] = new Dot("0");
            }
        }
        selection = new Selection();
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void createGameBoard(){
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                gameBoard[i][j] = new Dot(Color.randomColor() + "◯" + Color.ANSI_RESET);
            }
        }
    }

    public void printGameBoard() {
        System.out.println("╔════ஓ๑♡๑ஓ═══╗");
        for (Dot[] dots : gameBoard) {
            System.out.print("| ");
            for (Dot dot : dots) {
                System.out.print(dot.dot + " ");
            }
            System.out.println("|");
        }
        System.out.println("╚════ஓ๑♡๑ஓ═══╝");
    }

    public void missingAnimation() {
        for (int i = 0; i < selectedDots.length; i++) {
            for (int j = 0; j < selectedDots.length; j++) {
                if (selectedDots[i][j].dot.contains("◯")) {
                    selection.resetSelection(gameBoard[i][j].dot);
                    gameBoard[i][j].dot = "*";
                }
            }
        }
        printGameBoard();
        cleanArray();
    }
    public void shiftDotsDown() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = boardSize - 1; j >= 0; j--) {
                if (gameBoard[j][i].dot.equals("*")) {
                    int k = j - 1;
                    while (k >= 0 && gameBoard[k][i].dot.equals("*")) {
                        k--;
                    }
                    if (k >= 0) {
                        gameBoard[j][i].dot = gameBoard[k][i].dot;
                        gameBoard[k][i].dot = "*";
                    }else {
                        gameBoard[j][i].dot = Color.randomColor() + "◯" + Color.ANSI_RESET;
                    }
                }
            }
        }
    }

    public void cleanArray(){
        for(int i = 0; i < selectedDots.length; i++){
            for(int j = 0; j < selectedDots.length; j++){
                selectedDots[i][j].dot = " ";
            }
        }
    }
}
