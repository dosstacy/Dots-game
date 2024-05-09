package sk.tuke.gamestudio.server.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.GetTop10;
import sk.tuke.gamestudio.entity.MaxScoreResult;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.services.CommentService;
import sk.tuke.gamestudio.services.RatingService;
import sk.tuke.gamestudio.services.ScoreService;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;

@Controller()
@Scope(WebApplicationContext.SCOPE_SESSION)
public class AccountController {
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RatingService ratingService;

    @GetMapping("/dots/account")
    public String dataForAccount(Model model, HttpSession session) {
        try {
            String username = session.getAttribute("username").toString();
            List<MaxScoreResult> scores = scoreService.getDataForAccount(username);
            List<Comment> comments = commentService.getUserComments(username);

            int rating = ratingService.getRating(username);
            String ratingInStars = new Rating().getRatingInStars(rating);

            model.addAttribute("scores", scores);
            model.addAttribute("comments", comments);
            model.addAttribute("rating", ratingInStars);
            return "account";
        } catch(Exception e){
            model.addAttribute("rating_error", "Uh...Ups!\nThe “account” feature is not available.\nYou must first play the game at least once.");
            return "errorPage";
        }
    }

    @GetMapping("/dots/community")
    public String dataForCommunity(Model model) {
        List<GetTop10> scores = scoreService.getTop10();
        List<Comment> comments = commentService.getCommentsForCommunity();
        int avgRating = ratingService.getAverageRating();
        model.addAttribute("scores", scores);
        model.addAttribute("comments", comments);
        model.addAttribute("avgRating", avgRating);
        return "community";
    }

    @GetMapping("/dots/afterGameWindow")
    public String afterGameWindow() {
        return "afterGameWindow";
    }

    @GetMapping("/dots/other")
    public String otherButton() {
        return "other";
    }

    @PostMapping("/dots/other")
    public String other(Model model, @RequestParam String rate, @RequestParam String text, HttpSession session) {
        int rating = 0;
        try{
            session.getAttribute("username");
        }catch (Exception e){
            model.addAttribute("nullError", "Sorry, but unregistered users cannot leave comments and rate the game...");
            return "errorPage";
        }

        String username = (String) session.getAttribute("username");
        Comment comment = new Comment(text, new Timestamp(System.currentTimeMillis()));
        commentService.addComment(comment, username);
        session.setAttribute("comment", text);
        model.addAttribute("comment", text);

        try{
            rating = Integer.parseInt(rate);
        }catch (Exception e) {
            model.addAttribute("rate_error", "The rating must be only from 1 to 5. Please try again.");
            return "errorPage";
        }

        Rating ratings = new Rating(rating);
        ratings.setRatedOn(new Timestamp(System.currentTimeMillis()));
        ratings.setUsername(username);
        ratingService.setRating(ratings);

        return "other";
    }
}
