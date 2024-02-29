package main.java.sk.tuke.gamestudio.game.dots.core;

public class GameBoard {
    public String[][] gameBoard;
    private final int boardSize = 6;
    private final Selection selection;
    public String[][] selectedDots;

    public GameBoard() {
        gameBoard = new String[boardSize][boardSize];
        selectedDots = new String[boardSize][boardSize];
        for (int i = 0; i < selectedDots.length; i++) {
            for (int j = 0; j < selectedDots.length; j++) {
                selectedDots[i][j] = "0";
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
                gameBoard[i][j] = new Dot().dot;
            }
        }
    }

    public void printGameBoard() {
        System.out.println("╔════ஓ๑♡๑ஓ═══╗");
        for (String[] dots : gameBoard) {
            System.out.print("| ");
            for (String dot : dots) {
                System.out.print(dot + " ");
            }
            System.out.println("|");
        }
        System.out.println("╚════ஓ๑♡๑ஓ═══╝");
    }

    public void missingAnimation() {
        for (int i = 0; i < selectedDots.length; i++) {
            for (int j = 0; j < selectedDots.length; j++) {
                if (selectedDots[i][j].contains("◯")) {
                    selection.resetSelection(gameBoard[i][j]);
                    gameBoard[i][j] = "*";
                }
            }
        }
        printGameBoard();
        cleanArray();
    }
    public void shiftBallsDown() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = boardSize - 1; j >= 0; j--) {
                if (gameBoard[j][i].equals("*")) {
                    int k = j - 1;
                    while (k >= 0 && gameBoard[k][i].equals("*")) {
                        k--;
                    }
                    if (k >= 0) {
                        gameBoard[j][i] = gameBoard[k][i];
                        gameBoard[k][i] = "*";
                    }else {
                        gameBoard[j][i] = new Dot().dot;
                    }
                }
            }
        }
    }

    public void cleanArray(){
        for(int i = 0; i < selectedDots.length; i++){
            for(int j = 0; j < selectedDots.length; j++){
                selectedDots[i][j] = " ";
            }
        }
    }
}
