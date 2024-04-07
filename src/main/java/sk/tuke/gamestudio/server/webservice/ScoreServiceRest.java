package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.GetTop10;
import sk.tuke.gamestudio.entity.MaxScoreResult;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.services.ScoreService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/score")
public class ScoreServiceRest {

    @Autowired
    private ScoreService scoreService;

    @PostMapping("/addScore")
    public void addScore(@RequestBody Score score) {
        scoreService.addScore(score);
    }

    @GetMapping("/{username}")
    public List<MaxScoreResult> getDataForAccount(@PathVariable String username) {
        return scoreService.getDataForAccount(username);
    }

    @GetMapping("/getTop10")
    public List<GetTop10> getTop10() {
        boolean success = true;
        try{
            scoreService.getTop10();
        }catch(Exception e){
            success = false;
        }
        if(success){
            return scoreService.getTop10();
        }else {
            return Collections.emptyList();
        }
    }
}
