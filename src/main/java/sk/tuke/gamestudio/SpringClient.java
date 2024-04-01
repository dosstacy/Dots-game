package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.tuke.gamestudio.entity.Users;
import sk.tuke.gamestudio.game.dots.consoleUI.ConsoleUI;
import sk.tuke.gamestudio.game.dots.consoleUI.StartMenuConsoleUI;
import sk.tuke.gamestudio.services.*;

@SpringBootApplication
@Configuration
public class SpringClient {
    public static void main(String[] args) {
        SpringApplication.run(SpringClient.class, args);
    }

    @Bean
    public CommandLineRunner runner(StartMenuConsoleUI startMenuConsoleUI) {
        return args -> {
            startMenuConsoleUI.displayRegistrationMenu();
            ConsoleUI consoleUI = new ConsoleUI(startMenuConsoleUI.getUser());
            consoleUI.startGame();
        };
    }

    @Bean
    public ConsoleUI consoleUI(Users users) {
        return new ConsoleUI(users);
    }

    @Bean
    public Users user() {
        return new Users();
    }

    @Bean
    public StartMenuConsoleUI startMenuConsoleUI() {
        return new StartMenuConsoleUI();
    }

    @Bean
    public ScoreService scoreService() {
        return new ScoreServiceJPA();
    }

    @Bean
    public RatingService ratingService() {
        return new RatingServiceJPA();
    }

    @Bean
    public CommentService commentService() {
        return new CommentServiceJPA();
    }
}
