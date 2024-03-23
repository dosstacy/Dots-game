package sk.tuke.gamestudio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import sk.tuke.gamestudio.game.dots.consoleUI.ConsoleUI;
import sk.tuke.gamestudio.game.dots.consoleUI.StartMenuConsoleUI;

@SpringBootApplication

public class GamestudioApplication {

    public static void main(String[] args) {
        SpringApplication.run(GamestudioApplication.class, args);
        StartMenuConsoleUI startMenuConsoleUI = new StartMenuConsoleUI();
        startMenuConsoleUI.displayRegistrationMenu();
        ConsoleUI consoleUI = new ConsoleUI(startMenuConsoleUI.getUser());
        consoleUI.startGame();
    }

}
