package main.java.sk.tuke.gamestudio.game.dots;

import main.java.sk.tuke.gamestudio.game.dots.consoleUI.ConsoleUI;
import main.java.sk.tuke.gamestudio.game.dots.consoleUI.StartMenuConsoleUI;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        StartMenuConsoleUI startMenuConsoleUI = new StartMenuConsoleUI();
        startMenuConsoleUI.displayRegistationMenu();
        startMenuConsoleUI.getUser();
        ConsoleUI consoleUI = new ConsoleUI();
        consoleUI.startGame();
        }
}
