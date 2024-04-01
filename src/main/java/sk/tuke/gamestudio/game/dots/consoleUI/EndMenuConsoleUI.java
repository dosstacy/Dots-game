package sk.tuke.gamestudio.game.dots.consoleUI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Users;
import sk.tuke.gamestudio.game.dots.features.Color;
import sk.tuke.gamestudio.services.CommentService;
import sk.tuke.gamestudio.services.RatingService;

import java.sql.Timestamp;
import java.util.Scanner;

@Component
public class EndMenuConsoleUI {
    public Users users;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RatingService ratingService;

    public EndMenuConsoleUI(Users users) {
        this.users = users;
    }

    public void displayEndMenu() {
        Scanner scanner = new Scanner(System.in);
        String answer;
        do {
            System.out.println(Color.ANSI_PURPLE + "\nAre you already leaving us? We are glad that you played our game. " +
                    "Would you like to rate us? [Y/N]" + Color.ANSI_RESET);
            answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("y")) {
                rateUs();
                do {
                    System.out.println(Color.ANSI_PURPLE + "Would you like to leave a comment? [Y/N]" + Color.ANSI_RESET);
                    answer = scanner.nextLine();
                    if (answer.equalsIgnoreCase("y")) {
                        leaveComment();
                        System.exit(0);
                    } else if (answer.equalsIgnoreCase("n")) {
                        System.out.println(Color.ANSI_PURPLE + "We hope you enjoyed our game :)" + Color.ANSI_RESET);
                        System.exit(0);
                    } else {
                        System.out.println(Color.ANSI_RED + "Incorrect answer. Please enter Y or N." + Color.ANSI_RESET);
                    }
                }while(true);
            } else if (answer.equalsIgnoreCase("n")) {
                System.out.println(Color.ANSI_PURPLE + "We hope you enjoyed our game :)" + Color.ANSI_RESET);
                System.exit(0);
            } else {
                System.out.println(Color.ANSI_RED + "Incorrect answer. Please enter Y or N." + Color.ANSI_RESET);
            }
        }while(true);
    }
    private void leaveComment(){
       do {
            System.out.print("Comment (only 100 symbols!):  ");
            String text = new Scanner(System.in).nextLine().toLowerCase();

            if(text.length() > 100){
                System.out.println("The comment should not exceed 100 symbols. Please try again.");
            } else {
                Comment comment = new Comment(text, new Timestamp(System.currentTimeMillis()));
                commentService.addComment(comment, users.getUsername());
                System.out.println(Color.ANSI_GREEN + "Your comment successfully added. Thanks!" + Color.ANSI_RESET);
                break;
            }

        } while (true);
    }
    private void rateUs(){
        int answer = 0;
        Rating rating;

        do {
            System.out.println("Please rate us from 1 to 5: ");
            try {
                answer = new Scanner(System.in).nextInt();
            }catch (Exception e){
                System.out.println(Color.ANSI_RED + "Bad input" + Color.ANSI_RESET);
            }

            if(answer <= 0 || answer > 5){
                System.out.println("The rating must be only from 1 to 5. Please try again.");
            } else {
                rating = new Rating(answer);
                ratingService.setRating(rating, users.getUsername());
                System.out.println(Color.ANSI_GREEN + "Your rate successfully added. Thanks!" + Color.ANSI_RESET);
                break;
            }

        } while (true);
    }
}
