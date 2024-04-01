package sk.tuke.gamestudio.services;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import sk.tuke.gamestudio.entity.Users;
import sk.tuke.gamestudio.game.dots.features.Color;

@Transactional
public class UserServiceJPA implements UserService{
    @PersistenceContext
    private EntityManager entityManager;
    public boolean loginCheck = false;
    @Override
    public void addUser(Users users) {
        entityManager.persist(users);
    }

    @Override
    public void loginUser(String username, String password) {
        Query query = entityManager.createNamedQuery("Users.loginUser");
        query.setParameter("username", username);
        Long count = (Long) query.getSingleResult();

        if (count == 0) {
            System.out.println(Color.ANSI_RED + "Users doesn't exist. Please try again." + Color.ANSI_RESET);
            return;
        }

        query = entityManager.createNamedQuery("Users.loginUserPassword");
        query.setParameter("username", username);
        String dbPassword = (String) query.getSingleResult();

        if (!checkPassword(password, dbPassword)) {
            System.out.println(Color.ANSI_RED + "Incorrect password." + Color.ANSI_RESET);
            return;
        }

        System.out.println();
        System.out.println(Color.ANSI_GREEN + "Successful log in!" + Color.ANSI_RESET);
        loginCheck = true;
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
        entityManager.createNamedQuery("Users.reset").executeUpdate();
    }
}
