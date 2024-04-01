package sk.tuke.gamestudio.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity

@NamedQuery( name = "Users.loginUser",
        query = "SELECT COUNT(*) FROM Users u WHERE u.username = :username")

@NamedQuery( name = "Users.loginUserPassword",
        query = "SELECT u.password FROM Users u WHERE u.username = :username")

@NamedQuery( name = "Users.reset",
        query = "DELETE FROM Users")

public class Users implements Serializable {
    @Id
    @GeneratedValue
    private int ident;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public Users() {

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

