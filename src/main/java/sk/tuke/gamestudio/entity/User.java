package sk.tuke.gamestudio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;

import java.io.Serializable;

@Entity

@NamedQuery( name = "User.loginUser",
        query = "SELECT COUNT(*) FROM User u WHERE u.username = :username")

@NamedQuery( name = "User.loginUserPassword",
        query = "SELECT u.password FROM User u WHERE u.username = :username")

@NamedQuery( name = "User.reset",
        query = "DELETE FROM User")

public class User implements Serializable {
    @Id
    @GeneratedValue
    private int ident;
    private String username;
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

    public int getIdent() {
        return ident;
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }
}

