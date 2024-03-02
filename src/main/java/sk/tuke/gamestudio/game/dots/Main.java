package main.java.sk.tuke.gamestudio.game.dots;

import main.java.sk.tuke.gamestudio.game.dots.consoleUI.ConsoleUI;
import main.java.sk.tuke.gamestudio.game.dots.core.GameBoard;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        ConsoleUI consoleUI = new ConsoleUI();
        //consoleUI.play();;
        //new TimeMode().Timer();
        //GameBoard gameBoard = new GameBoard();
        consoleUI.createButtonWindow();
    }
}
