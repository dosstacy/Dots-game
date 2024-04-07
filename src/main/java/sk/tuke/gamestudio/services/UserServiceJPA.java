package sk.tuke.gamestudio.services;

import javax.persistence.*;
import javax.transaction.Transactional;
import sk.tuke.gamestudio.entity.User;

@Transactional
public class UserServiceJPA implements UserService{
    @PersistenceContext
    private EntityManager entityManager;
    private boolean loginCheck = false;
    private boolean signUpCheck = false;

    @Override
    public void addUser(User user) {
        String encodedPassword = hashPassword(user.getPassword());
        user.setPassword(encodedPassword);
        try {
            entityManager.persist(user);
            signUpCheck = true;
            System.out.println();
        }catch (Exception e){
            signUpCheck = false;
            throw new GameStudioException(e);
        }
    }

    @Override
    public void loginUser(String username, String password) {
        try {
            entityManager.createNamedQuery("User.loginUser", Long.class)
                    .setParameter("username", username)
                    .getSingleResult();

            entityManager.createNamedQuery("User.loginUserPassword", String.class)
                    .setParameter("username", username)
                    .getSingleResult();

            loginCheck = true;
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

    @Override
    public boolean getLoginCheck() {
        return loginCheck;
    }

    @Override
    public boolean getSignUpCheck() {
        return signUpCheck;
    }

}
