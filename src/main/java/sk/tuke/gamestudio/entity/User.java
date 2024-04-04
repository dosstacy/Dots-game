package sk.tuke.gamestudio.entity;
import javax.persistence.*;

import java.io.Serializable;

@Entity

@Table(name = "users")
@NamedQuery( name = "User.loginUser",
        query = "SELECT COUNT(*) FROM User u WHERE u.username = :username")

@NamedQuery( name = "User.loginUserPassword",
        query = "SELECT u.password FROM User u WHERE u.username = :username")

@NamedQuery( name = "User.reset",
        query = "DELETE FROM User")

public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int ident;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public User() {

    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

