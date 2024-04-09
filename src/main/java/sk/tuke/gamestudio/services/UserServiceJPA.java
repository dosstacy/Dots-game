package sk.tuke.gamestudio.services;

import javax.persistence.*;
import javax.transaction.Transactional;
import sk.tuke.gamestudio.entity.User;

@Transactional
public class UserServiceJPA implements UserService{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(User user) {
        if(user == null) {
            throw new GameStudioException("User cannot be null");
        }
        String encodedPassword = hashPassword(user.getPassword());
        user.setPassword(encodedPassword);
        try {
            entityManager.persist(user);
        }catch (Exception e){
            throw new GameStudioException(e);
        }
    }

    @Override
    public void loginUser(String username, String password) {
        if(username == null || password == null) {
            throw new GameStudioException("Username or password cannot be null");
        }
        try {
            entityManager.createNamedQuery("User.loginUser", Long.class)
                    .setParameter("username", username)
                    .getSingleResult();

            String firstPassword = entityManager.createNamedQuery("User.loginUserPassword", String.class)
                    .setParameter("username", username)
                    .getSingleResult();

            if(!firstPassword.equals(hashPassword(password))){
                throw new GameStudioException();
            }
        }catch (Exception e){
            throw new GameStudioException(e);
        }
    }

    private String hashPassword(String password) {
        int hash = password.hashCode();
        return Integer.toBinaryString(hash);
    }

    @Override
    public void reset() {
        try {
            entityManager.createNamedQuery("User.reset").executeUpdate();
        }catch (Exception e){
            throw new GameStudioException(e);
        }
    }
}
