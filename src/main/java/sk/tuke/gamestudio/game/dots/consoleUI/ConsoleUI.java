package main.java.sk.tuke.gamestudio.game.dots.consoleUI;

import main.java.sk.tuke.gamestudio.game.dots.core.Cursor;
import main.java.sk.tuke.gamestudio.game.dots.core.GameBoard;
import main.java.sk.tuke.gamestudio.game.dots.core.Selection;
import main.java.sk.tuke.gamestudio.game.dots.features.Color;
import main.java.sk.tuke.gamestudio.game.dots.features.DotState;
import main.java.sk.tuke.gamestudio.game.dots.features.GameMode;
import main.java.sk.tuke.gamestudio.game.dots.features.TimeMode;

import java.util.Scanner;

public class ConsoleUI {
    private final GameBoard field;
    private final Cursor cursor;
    private GameMode gameMode;
    private final Selection selection;

    public ConsoleUI() {
        field = new GameBoard();
        field.createGameBoard();
        cursor = new Cursor(field);
        gameMode = GameMode.CURSOR;
        selection = new Selection(field);
    }
    public void startWindowAndChooseMode() throws InterruptedException {
        createButtonWindow();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please write the mode you want to play or \"e\" to exit: ");
        String input = scanner.nextLine().trim().toLowerCase();

        switch (input) {
            case "timed":
                new TimeMode().Timer();
                play();
            case "moves":
            case "endless":
                while(true){
                    play();
                }
            case "e":
                System.exit(0);
            default:
                System.out.println("Invalid input");
        }
    }

    public void play() throws InterruptedException {
        cursor.prevColor = field.gameBoard[cursor.getPosX()][cursor.getPosY()].dot;
        field.gameBoard[cursor.getPosX()][cursor.getPosY()].dot = cursor.selectDot(field.gameBoard[cursor.getPosX()][cursor.getPosY()]);

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
                case "": connectionButton(); break;
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
            if(field.gameBoard[selection.getPosX()][selection.getPosY()].dot.contains(Color.WHITE_BACKGROUND)) {
                field.gameBoard[selection.getPosX()][selection.getPosY()].dot = field.gameBoard[selection.getPosX()][selection.getPosY()].dot.replace(Color.WHITE_BACKGROUND, "");
            }
            field.gameBoard[selection.getPosX()][selection.getPosY()].dot = selection.selectDot(field.gameBoard[selection.getPosX()][selection.getPosY()]);
            field.gameBoard[selection.getPosX()][selection.getPosY()].setState(DotState.SELECTED);
        } else {
            gameMode = GameMode.CURSOR;
            selection.resetAllSelection(field);
            field.cleanArray();
            field.gameBoard[cursor.getPosX()][cursor.getPosY()].dot = cursor.selectDot(field.gameBoard[cursor.getPosX()][cursor.getPosY()]);
            field.gameBoard[cursor.getPosX()][cursor.getPosY()].setState(DotState.NOT_SELECTED);
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

    private void connectionButton() throws InterruptedException {
        if(gameMode == GameMode.SELECTION){
            int countDots = 0;
            for(int i = 0; i < field.selectedDots.length; i++){
                for(int j = 0; j < field.selectedDots.length; j++){
                    if(field.selectedDots[i][j].dot.equals("0")){
                        countDots++;
                    }
                }
            }
            if(countDots > 1) {
                field.missingAnimation();
                Thread.sleep(1000);
                field.shiftDotsDown();
                gameMode = GameMode.CURSOR;
                selection.resetAllSelection(field);
                cursor.prevColor = field.gameBoard[cursor.getPosX()][cursor.getPosY()].dot;
                field.gameBoard[cursor.getPosX()][cursor.getPosY()].dot = cursor.selectDot(field.gameBoard[cursor.getPosX()][cursor.getPosY()]);
            }else{
                gameMode = GameMode.CURSOR;
                selection.resetAllSelection(field);
                cursor.prevColor = field.gameBoard[cursor.getPosX()][cursor.getPosY()].dot;
                field.gameBoard[cursor.getPosX()][cursor.getPosY()].dot = cursor.selectDot(field.gameBoard[cursor.getPosX()][cursor.getPosY()]);
            }
        }
    }

    public void createButtonWindow() {
        System.out.println("\n             ⠂⠁⠈⠂⠄⠄⠂⠁⠁⠂⠄⠄⠂⠁⠁⠂Welcome to the game \"Dots\"!⠂⠁⠈⠂⠄⠄⠂⠁⠁⠂⠄⠄⠂⠁⠁⠂");
        System.out.println("                            Please choose the mode you want to play:");
        System.out.println("                                ⏱⭑⟡༄⏱⭑⟡༄. Timed .⏱⭑⟡༄⏱⭑⟡༄\n");
        System.out.println("                                ˖°༄˖°༄˖°༄˖° Moves ˖°༄˖°༄˖˖°༄\n");
        System.out.println("                                ⁺˚⋆｡°✩₊⋆ထ Endless ထ⁺˚⋆｡°✩₊⋆\n");
    }
}
