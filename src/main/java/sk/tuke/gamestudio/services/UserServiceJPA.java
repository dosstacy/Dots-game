package sk.tuke.gamestudio.services;

import javax.persistence.*;
import javax.transaction.Transactional;
import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.game.dots.features.Color;

@Transactional
public class UserServiceJPA implements UserService{
    @PersistenceContext
    private EntityManager entityManager;
    public boolean loginCheck = false;
    public boolean signUpCheck = false;
    @Override
    public void addUser(User user) {
        String encodedPassword = hashPassword(user.getPassword());
        user.setPassword(encodedPassword);
        try {
            entityManager.persist(user);
            signUpCheck = true;
            System.out.println();
            System.out.println(Color.ANSI_GREEN + "Successful sign up!" + Color.ANSI_RESET);
        }catch (Exception e){
            signUpCheck = false;
            System.out.println(Color.ANSI_RED + "This login is already in use! Please enter another login." + Color.ANSI_RESET);
        }
    }

    @Override
    public void loginUser(String username, String password) {
        try {
            long count = entityManager.createNamedQuery("User.loginUser", Long.class)
                    .setParameter("username", username)
                    .getSingleResult();

            if (count == 0) {
                System.out.println(Color.ANSI_RED + "User doesn't exist. Please try again." + Color.ANSI_RESET);
                return;
            }

            String dbPassword = entityManager.createNamedQuery("User.loginUserPassword", String.class)
                    .setParameter("username", username)
                    .getSingleResult();

            if (!checkPassword(password, dbPassword)) {
                System.out.println(Color.ANSI_RED + "Incorrect password." + Color.ANSI_RESET);
                return;
            }

            System.out.println();
            System.out.println(Color.ANSI_GREEN + "Successful log in!" + Color.ANSI_RESET);
            loginCheck = true;
        }catch (Exception e){
            throw new GameStudioException(e);
        }
    }

    private String hashPassword(String password) {
        int hash = password.hashCode();
        return Integer.toBinaryString(hash);
    }
    private boolean checkPassword(String password, String hashedPassword) {
        return hashPassword(password).equals(hashedPassword);
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

    @Override
    public void setLoginCheck(boolean loginCheck) {
        this.loginCheck = loginCheck;
    }

    @Override
    public void setSignUpCheck(boolean signUpCheck) {
        this.signUpCheck = signUpCheck;
    }
}
