package sk.tuke.gamestudio.game.dots.consoleUI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.entity.Users;
import sk.tuke.gamestudio.game.dots.features.Color;
import sk.tuke.gamestudio.services.*;

import java.util.List;

@Component
public class JDBCConsoleUI {
    private int scores = 0;
    private Users users;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private ScoreService scoreService;
    public JDBCConsoleUI(Users users) {
        setUser(users);
    }

    public Users getUser() {
        return users;
    }

    public void setUser(Users users) {
        this.users = users;
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    protected void dataInAccountButton(){
        List<String> data;
        data = scoreService.getDataForAccount(users.getUsername());

        System.out.println(Color.ANSI_PURPLE + "Your recent activities displayed here...\n" + Color.ANSI_RESET);
        System.out.format("%40s%n", Color.ANSI_PURPLE + "YOUR BEST SCORES" + Color.ANSI_RESET);
        System.out.println("+------------+------------+---------------------------+");
        System.out.format("| %-10s | %-10s | %-25s |%n", "SCORE", "MODE", "DATE");
        System.out.println("+------------+------------+---------------------------+");
        for (int element = 0; element < data.size()-1; element += 3) {
            System.out.format("| %-10s | %-10s | %-25s |%n", data.get(element), data.get(element + 1), data.get(element + 2));
        }
        System.out.println("+------------+------------+---------------------------+");

        System.out.print(Color.ANSI_PURPLE + "YOUR RECENT RATING: " + Color.ANSI_RESET);
        System.out.println(new Rating().getRatingInStars(ratingService.getRating(users.getUsername())));

        System.out.println(Color.ANSI_PURPLE + "YOUR RECENT COMMENTS: " + Color.ANSI_RESET);
        List<Comment> commentsList = commentService.getUserComments(users.getUsername());
        for (Comment comment : commentsList) {
            System.out.println(Color.ANSI_GREEN + comment.getCommented_on() + Color.ANSI_RESET);
            System.out.println(comment.getComment());
            System.out.println();
        }
    }

    protected void communityButton(){
        System.out.println(Color.ANSI_PURPLE + "AVERAGE RATING OF THIS GAME: " + Color.ANSI_RESET + ratingService.getAverageRating());
        System.out.println();

        List<Score> top10;
        top10 = scoreService.getTop10();

        System.out.format("%45s%n", Color.ANSI_PURPLE + "TOP 10" + Color.ANSI_RESET);
        System.out.println("+-----------------+------------+------------+---------------------------+");
        System.out.format("| %-15s | %-10s | %-10s | %-25s |%n", "USER", "SCORE", "MODE", "DATE");
        System.out.println("+-----------------+------------+------------+---------------------------+");
        for (Score score : top10) {
            System.out.format("| %-15s | %-10s | %-10s | %-25s |%n",
                    score.getUsername(),
                    score.getScore(),
                    score.getGamemode(),
                    score.getDate());
        }
        System.out.println("+-----------------+------------+------------+---------------------------+\n");

        List<Comment> commentList = commentService.getCommentsForCommunity();
        System.out.println(Color.ANSI_PURPLE + "COMMENTS: " + Color.ANSI_RESET);
        for(Comment comment : commentList){
            System.out.println(Color.ANSI_GREEN + comment.getUsername() + " on " + comment.getCommented_on() + Color.ANSI_RESET);
            System.out.println(comment.getComment());
            System.out.println();
        }
    }
    protected void writeScoreToDatabase(String mode){
        Score score = new Score(scores, users.getUsername(), mode);
        scoreService.addScore(score); //чому тут scoreService null
    }


}
