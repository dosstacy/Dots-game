package sk.tuke.gamestudio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Rating;

@Service
public class RatingServiceRestClient implements RatingService {
    private final String url = "http://localhost:8080/api/rating";
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void setRating(Rating rating) {
        restTemplate.postForEntity(url + "/addRating", rating, Rating.class);
    }

    @Override
    public int getAverageRating() {
        return restTemplate.getForObject(url + "/avgRating", Integer.class);
    }

    @Override
    public int getRating(String username) {
        return restTemplate.getForObject(url + "/" + username, Integer.class);
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
