package main.java.sk.tuke.gamestudio.game.dots.consoleUI;

import main.java.sk.tuke.gamestudio.entity.Comment;
import main.java.sk.tuke.gamestudio.entity.Rating;
import main.java.sk.tuke.gamestudio.game.dots.features.Color;
import main.java.sk.tuke.gamestudio.services.CommentServiceJDBC;
import main.java.sk.tuke.gamestudio.services.RatingServiceJDBC;

import java.util.Scanner;

public class EndMenuConsoleUI {
    private final StartMenuConsoleUI startMenu;
    public EndMenuConsoleUI() {
        startMenu = new StartMenuConsoleUI();
    }

    public void displayEndMenu() {
        Scanner scanner = new Scanner(System.in);
        String answer;
        System.out.println(Color.ANSI_PURPLE + "\nAre you already leaving us? We are glad that you played our game. " +
                "Would you like to rate us? [Y/N]" + Color.ANSI_RESET);
        answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("y")) {
            rateUs();
            System.out.println(Color.ANSI_PURPLE + "Would you like to leave a comment? [Y/N]" + Color.ANSI_RESET);
            answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("y")) {
                leaveComment();
            }
        } else if (answer.equalsIgnoreCase("n")) {
            System.out.println(Color.ANSI_PURPLE + "We hope you enjoyed our game :)" + Color.ANSI_RESET);
            System.exit(0);
        } else {
            System.out.println(Color.ANSI_RED + "Incorrect answer. Please enter Y or N." + Color.ANSI_RESET);
        }
    }
    private void leaveComment(){
        CommentServiceJDBC commentService;
        Comment comment;

        do {
            System.out.print("Comment (only 100 symbols!):  ");
            String text = new Scanner(System.in).nextLine().toLowerCase();

            if(text.length() > 100){
                System.out.println("The comment should not exceed 100 symbols. Please try again.");
            } else {
                commentService = new CommentServiceJDBC();
                comment = new Comment(text);
                commentService.addComment(comment, startMenu.getUser().getUsername());
                System.out.println("Your comment successfully added. Thanks!");
                break;
            }

        } while (true);
    }
    private void rateUs(){
        int answer;

        RatingServiceJDBC ratingService;
        Rating rating;

        do {
            System.out.println("Please rate us from 1 to 5: ");
            answer = new Scanner(System.in).nextInt();

            if(answer <= 0 || answer > 5){
                System.out.println("The rating must be only from 1 to 5. Please try again.");
            } else {
                ratingService = new RatingServiceJDBC();
                rating = new Rating(answer);
                ratingService.addRating(rating, startMenu.getUser().getUsername());
                System.out.println("Your rate successfully added. Thanks!");
                break;
            }

        } while (true);
    }
}
