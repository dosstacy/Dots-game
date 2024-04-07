package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.services.RatingService;

@RestController
@RequestMapping("/api/rating")
public class RatingServiceRest {

    @Autowired
    private RatingService ratingService;

    @PostMapping("/addRating")
    public void setRating(@RequestBody Rating rating){
        ratingService.setRating(rating);
    }

    @GetMapping("/avgRating")
    public int getAverageRating(){
        return ratingService.getAverageRating();
    }

    @GetMapping("/{username}")
    public int getRating(@PathVariable String username){
        return ratingService.getRating(username);
    }
}
