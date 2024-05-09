package sk.tuke.gamestudio.server.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.GetTop10;
import sk.tuke.gamestudio.entity.MaxScoreResult;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.services.CommentService;
import sk.tuke.gamestudio.services.GameStudioException;
import sk.tuke.gamestudio.services.RatingService;
import sk.tuke.gamestudio.services.ScoreService;

import javax.servlet.http.HttpSession;
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
    public String dataForAccount(Model model ,HttpSession session) {
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
}
