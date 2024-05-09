package sk.tuke.gamestudio.game.dots.consoleUI;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.entity.*;
import sk.tuke.gamestudio.game.dots.features.Color;
import sk.tuke.gamestudio.services.*;

import java.sql.Timestamp;
import java.util.List;
public class JDBCUI {
    private int scores = 0;
    private User user;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private ScoreService scoreService;
    public JDBCUI(){}
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    protected void dataInAccountButton(){
        List<MaxScoreResult> data;
        data = scoreService.getDataForAccount(user.getUsername());

        System.out.println(Color.ANSI_PURPLE + "Your recent activities displayed here...\n" + Color.ANSI_RESET);
        System.out.format("%40s%n", Color.ANSI_PURPLE + "YOUR BEST SCORES" + Color.ANSI_RESET);
        System.out.println("+------------+------------+---------------------------+");
        System.out.format("| %-10s | %-10s | %-25s |%n", "SCORE", "MODE", "DATE");
        System.out.println("+------------+------------+---------------------------+");
        for (MaxScoreResult datum : data) {
            System.out.format("| %-10s | %-10s | %-25s |%n",
                    datum.getMaxResult(),
                    datum.getGameMode(),
                    datum.getDate());
        }
        System.out.println("+------------+------------+---------------------------+");

        System.out.print(Color.ANSI_PURPLE + "YOUR RECENT RATING: " + Color.ANSI_RESET);

        int rating = 0;
        try {
            rating = ratingService.getRating(user.getUsername());
        }catch (Exception e){
            System.out.println(Color.ANSI_RED + "You haven't rated the game yet" + Color.ANSI_RESET);
        }

        System.out.println(new Rating().getRatingInStars(rating));

        System.out.println(Color.ANSI_PURPLE + "YOUR RECENT COMMENTS: " + Color.ANSI_RESET);
        List<Comment> commentsList = commentService.getUserComments(user.getUsername());
        for (Comment comment : commentsList) {
            System.out.println(Color.ANSI_GREEN + comment.getCommented_on() + Color.ANSI_RESET);
            System.out.println(comment.getComment());
            System.out.println();
        }
    }

    protected void communityButton(){
        int rating = 0;
        try {
            rating = ratingService.getAverageRating();
        }catch (Exception e){
            System.out.println(Color.ANSI_RED + "No one has rated this game yet" + Color.ANSI_RESET);
        }
        System.out.println(Color.ANSI_PURPLE + "AVERAGE RATING OF THIS GAME: " + Color.ANSI_RESET + rating);
        System.out.println();

        List<GetTop10> top10;
        top10 = scoreService.getTop10();

        System.out.format("%45s%n", Color.ANSI_PURPLE + "TOP 10" + Color.ANSI_RESET);
        System.out.println("+-----------------+------------+------------+---------------------------+");
        System.out.format("| %-15s | %-10s | %-10s | %-25s |%n", "USER", "SCORE", "MODE", "DATE");
        System.out.println("+-----------------+------------+------------+---------------------------+");
        for (GetTop10 score : top10) {
            System.out.format("| %-15s | %-10s | %-10s | %-25s |%n",
                    score.getUsername(),
                    score.getMaxResult(),
                    score.getGameMode(),
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
    public void writeScoreToDatabase(String mode){
        Score score = new Score(user.getUsername(), scores, mode, new Timestamp(System.currentTimeMillis()));
        scoreService.addScore(score);
    }


}
