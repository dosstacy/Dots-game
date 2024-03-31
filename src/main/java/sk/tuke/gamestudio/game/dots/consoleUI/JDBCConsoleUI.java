package sk.tuke.gamestudio.game.dots.consoleUI;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.game.dots.features.Color;
import sk.tuke.gamestudio.services.CommentServiceJDBC;
import sk.tuke.gamestudio.services.RatingServiceJDBC;
import sk.tuke.gamestudio.services.ScoreServiceJDBC;

import java.util.List;

public class JDBCConsoleUI {
    private int scores = 0;
    private User user;
    public JDBCConsoleUI(User user) {
        setUser(user);
    }

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
        ScoreServiceJDBC scoreServiceJDBC = new ScoreServiceJDBC();
        List<String> data;
        data = scoreServiceJDBC.getDataForAccount(user.getUsername());

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
        RatingServiceJDBC ratingServiceJDBC = new RatingServiceJDBC();
        System.out.println(new Rating().getRatingInStars(ratingServiceJDBC.getRating(user.getUsername())));

        System.out.println(Color.ANSI_PURPLE + "YOUR RECENT COMMENTS: " + Color.ANSI_RESET);
        CommentServiceJDBC commentServiceJDBC = new CommentServiceJDBC();
        List<Comment> commentsList = commentServiceJDBC.getUserComments(user.getUsername());
        for (Comment comment : commentsList) {
            System.out.println(Color.ANSI_GREEN + comment.getCommented_on() + Color.ANSI_RESET);
            System.out.println(comment.getComment());
            System.out.println();
        }
    }

    protected void communityButton(){
        RatingServiceJDBC ratingServiceJDBC = new RatingServiceJDBC();
        System.out.println(Color.ANSI_PURPLE + "AVERAGE RATING OF THIS GAME: " + Color.ANSI_RESET + ratingServiceJDBC.getAverageRating());
        System.out.println();

        ScoreServiceJDBC scoreServiceJDBC = new ScoreServiceJDBC();
        List<Score> top10;
        top10 = scoreServiceJDBC.getTop10();

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

        CommentServiceJDBC commentServiceJDBC = new CommentServiceJDBC();
        List<Comment> commentList = commentServiceJDBC.getCommentsForCommunity();
        System.out.println(Color.ANSI_PURPLE + "COMMENTS: " + Color.ANSI_RESET);
        for(Comment comment : commentList){
            System.out.println(Color.ANSI_GREEN + comment.getUsername() + " on " + comment.getCommented_on() + Color.ANSI_RESET);
            System.out.println(comment.getComment());
            System.out.println();
        }
    }
    protected void writeScoreToDatabase(String mode){
        ScoreServiceJDBC scoreService = new ScoreServiceJDBC();
        Score score = new Score(scores, user.getUsername(), mode);
        scoreService.addScore(score);
    }


}
