package sk.tuke.gamestudio.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sk.tuke.gamestudio.entity.User;

@Transactional
public class UserServiceJPA implements UserService{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void addUser(User user) {

    }

    @Override
    public void loginUser(String username, String password) {

    }

    @Override
    public void reset() {

    }
}
