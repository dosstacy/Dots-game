package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.tuke.gamestudio.game.dots.consoleUI.ConsoleUI;
import sk.tuke.gamestudio.game.dots.consoleUI.EndMenuConsoleUI;
import sk.tuke.gamestudio.game.dots.consoleUI.JDBCConsoleUI;
import sk.tuke.gamestudio.game.dots.consoleUI.StartMenuConsoleUI;
import sk.tuke.gamestudio.services.*;

@SpringBootApplication
@Configuration
public class SpringClient {
    public static void main(String[] args) {
        SpringApplication.run(SpringClient.class, args);
    }
    @Bean
    public CommandLineRunner runner(ConsoleUI consoleUI) {
        return s -> consoleUI.registration();
    }

    @Bean
    public ConsoleUI consoleUI() {
        return new ConsoleUI();
    }

    @Bean
    public EndMenuConsoleUI endMenuConsoleUI() {
        return new EndMenuConsoleUI();
    }

    @Bean
    public JDBCConsoleUI jdbcConsoleUI() {
        return new JDBCConsoleUI();
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

    @Bean
    public UserService userService(){
        return new UserServiceJPA();
    }

}
