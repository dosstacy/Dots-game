package sk.tuke.gamestudio.game.dots;

import sk.tuke.gamestudio.game.dots.consoleUI.ConsoleUI;
import sk.tuke.gamestudio.game.dots.consoleUI.StartMenuConsoleUI;

public class Main { //TODO implement methods in JPA + come up with something with insert, remove sout in exceptions
    public static void main(String[] args) {
        StartMenuConsoleUI startMenuConsoleUI = new StartMenuConsoleUI();
        startMenuConsoleUI.displayRegistrationMenu();
        ConsoleUI consoleUI = new ConsoleUI(startMenuConsoleUI.getUser());
        consoleUI.startGame();
        }
}
