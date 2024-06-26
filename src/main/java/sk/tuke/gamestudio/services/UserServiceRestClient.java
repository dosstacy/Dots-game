package sk.tuke.gamestudio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.User;

@Service
public class UserServiceRestClient implements UserService{
    private final String url = "http://localhost:8080/api/user";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void addUser(User user) {
        restTemplate.postForEntity(url + "/createAccount", user, User.class);
    }

    @Override
    public void loginUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        restTemplate.postForEntity(url + "/logIn", user, User.class);
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
