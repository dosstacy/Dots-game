package sk.tuke.gamestudio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Comment;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class CommentServiceRestClient implements CommentService {
    private final String url = "http://localhost:8080/api/comment";
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void addComment(Comment comment, String username) {
        comment.setUsername(username);
        restTemplate.postForEntity(url + "/addComment?username=" + username, comment, Comment.class);
    }

    @Override
    public List<Comment> getUserComments(String username) {
        ResponseEntity<Comment[]> response = restTemplate.getForEntity(url + "/" + username, Comment[].class);
        Comment[] results = response.getBody();
        if (results != null) {
            return Arrays.asList(results);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Comment> getCommentsForCommunity() {
        ResponseEntity<Comment[]> response = restTemplate.getForEntity(url + "/communityComments", Comment[].class);
        Comment[] results = response.getBody();
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
