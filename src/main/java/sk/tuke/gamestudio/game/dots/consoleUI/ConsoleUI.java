package main.java.sk.tuke.gamestudio.game.dots.consoleUI;

import main.java.sk.tuke.gamestudio.game.dots.core.Cursor;
import main.java.sk.tuke.gamestudio.game.dots.core.GameBoard;
import main.java.sk.tuke.gamestudio.game.dots.core.Selection;
import main.java.sk.tuke.gamestudio.game.dots.features.Color;
import main.java.sk.tuke.gamestudio.game.dots.features.GameMode;

import java.util.Scanner;

public class ConsoleUI {
    private final GameBoard field;
    private final Cursor cursor;
    private GameMode gameMode;
    private String color;
    private final Selection selection;

    public ConsoleUI() {
        field = new GameBoard();
        field.createGameBoard();
        cursor = new Cursor(field);
        gameMode = GameMode.CURSOR;
        selection = new Selection(field);
    }

    public void play() throws InterruptedException {
        cursor.prevColor = field.gameBoard[cursor.getPosX()][cursor.getPosY()];
        field.gameBoard[cursor.getPosX()][cursor.getPosY()] = cursor.selectDot(field.gameBoard[cursor.getPosX()][cursor.getPosY()]);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            field.printGameBoard();
            System.out.println("Enter a letter (u, d, r, l), 'ok' or 'exit':");
            String input = scanner.nextLine().trim().toLowerCase();

            switch (input) {
                case "m": switchMode(); break;
                case "u": moveUp(); break;
                case "d": moveDown(); break;
                case "r": moveRight(); break;
                case "l": moveLeft(); break;
                case "":
                    if(gameMode == GameMode.SELECTION){
                        field.missingAnimation();
                        Thread.sleep(1000);
                        field.shiftDotsDown();
                        gameMode = GameMode.CURSOR;
                        field.gameBoard[cursor.getPosX()][cursor.getPosY()] = cursor.selectDot(field.gameBoard[cursor.getPosX()][cursor.getPosY()]);
                    }
                    break;
                case "e":
                    System.exit(0);
                default:
                    System.out.println("Invalid input");
            }

        }
    }

    private void switchMode(){
        if (gameMode == GameMode.CURSOR) {
            gameMode = GameMode.SELECTION;
            selection.setPosX(cursor.getPosX());
            selection.setPosY(cursor.getPosY());
            if(field.gameBoard[selection.getPosX()][selection.getPosY()].contains(Color.WHITE_BACKGROUND)){
                field.gameBoard[selection.getPosX()][selection.getPosY()] = field.gameBoard[selection.getPosX()][selection.getPosY()].replace(Color.WHITE_BACKGROUND, "");
            }
            color = field.gameBoard[selection.getPosX()][selection.getPosY()];
            field.gameBoard[selection.getPosX()][selection.getPosY()] = selection.selectDot(field.gameBoard[selection.getPosX()][selection.getPosY()]);
        } else {
            gameMode = GameMode.CURSOR;
            selection.resetAllSelection(field);
            field.cleanArray();
            field.gameBoard[cursor.getPosX()][cursor.getPosY()] = color;
            field.gameBoard[cursor.getPosX()][cursor.getPosY()] = cursor.selectDot(field.gameBoard[cursor.getPosX()][cursor.getPosY()]);
        }
    }

    private void moveUp(){
        if (gameMode == GameMode.CURSOR) {
            cursor.moveUp();
        } else if (gameMode == GameMode.SELECTION) {
            selection.moveUp();
        }
    }

    private void moveDown(){
        if (gameMode == GameMode.CURSOR) {
            cursor.moveDown();
        } else if (gameMode == GameMode.SELECTION) {
            selection.moveDown();
        }
    }

    private void moveRight(){
        if (gameMode == GameMode.CURSOR) {
            cursor.moveRight();
        } else if (gameMode == GameMode.SELECTION) {
            selection.moveRight();
        }
    }

    private void moveLeft(){
        if (gameMode == GameMode.CURSOR) {
            cursor.moveLeft();
        } else if (gameMode == GameMode.SELECTION) {
            selection.moveLeft();
        }
    }

    /*public void connectionAnimation(String[][] board, int x, int y) throws InterruptedException {
        board[x][y] = "*";
        printGameBoard(board);
        Thread.sleep(500);
    }*/
}
