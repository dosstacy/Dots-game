package sk.tuke.gamestudio.services;

import org.springframework.http.ResponseEntity;
import sk.tuke.gamestudio.entity.GetTop10;
import sk.tuke.gamestudio.entity.MaxScoreResult;
import sk.tuke.gamestudio.entity.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ScoreServiceRestClient implements ScoreService {
    private final String url = "http://localhost:8080/api/score";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void addScore(Score score) {
        restTemplate.postForEntity(url + "/addScore", score, Score.class);
    }

    @Override
    public List<MaxScoreResult> getDataForAccount(String username) {
        ResponseEntity<MaxScoreResult[]> response = restTemplate.getForEntity(url + "/" + username, MaxScoreResult[].class);
        MaxScoreResult[] results = response.getBody();
        if (results != null) {
            return Arrays.asList(results);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<GetTop10> getTop10() {
        ResponseEntity<GetTop10[]> response = restTemplate.getForEntity(url + "/getTop10", GetTop10[].class);
        GetTop10[] results = response.getBody();
        if (results != null) {
            return Arrays.asList(results);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
