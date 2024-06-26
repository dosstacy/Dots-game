package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.game.dots.consoleUI.ConsoleUI;
import sk.tuke.gamestudio.game.dots.consoleUI.EndMenuConsoleUI;
import sk.tuke.gamestudio.game.dots.consoleUI.JDBCUI;
import sk.tuke.gamestudio.game.dots.consoleUI.StartMenuConsoleUI;
import sk.tuke.gamestudio.services.*;

@SpringBootApplication
@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = "sk.tuke.gamestudio.server.*"))
public class SpringClient {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);

    }
    @Bean
    public CommandLineRunner runner(ConsoleUI consoleUI) {
        return s -> {
            consoleUI.registration();
        };
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
    public JDBCUI jdbcConsoleUI() {
        return new JDBCUI();
    }
    @Bean
    public StartMenuConsoleUI startMenuConsoleUI() {
        return new StartMenuConsoleUI();
    }

    @Bean
    public ScoreService scoreService() {
        return new ScoreServiceRestClient();
    }

    @Bean
    public RatingService ratingService() {
        return new RatingServiceRestClient();
    }

    @Bean
    public CommentService commentService() {
        return new CommentServiceRestClient();
    }

    @Bean
    public UserService userService(){
        return new UserServiceRestClient();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}
