package sk.tuke.gamestudio.game.dots;

import sk.tuke.gamestudio.game.dots.consoleUI.ConsoleUI;
import sk.tuke.gamestudio.game.dots.consoleUI.StartMenuConsoleUI;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        StartMenuConsoleUI startMenuConsoleUI = new StartMenuConsoleUI();
        startMenuConsoleUI.displayRegistrationMenu();
        ConsoleUI consoleUI = new ConsoleUI(startMenuConsoleUI.getUser());
        consoleUI.startGame();
        }
}
