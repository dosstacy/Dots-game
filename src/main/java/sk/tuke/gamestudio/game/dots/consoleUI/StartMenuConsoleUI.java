package main.java.sk.tuke.gamestudio.game.dots.consoleUI;

import main.java.sk.tuke.gamestudio.entity.User;
import main.java.sk.tuke.gamestudio.game.dots.features.Color;
import main.java.sk.tuke.gamestudio.services.UserServiceJDBC;

import java.util.Scanner;//
//TODO fix user null pointer exception
public class StartMenuConsoleUI {
    private User user;
    private int option = 0;
    public StartMenuConsoleUI() {
    }

    public void displayRegistationMenu(){
        System.out.println(Color.ANSI_PURPLE +  "========= Please choose the option (1 or 2) =========\n" + Color.ANSI_RESET +
                "                    1. Log In\n" + "                    2. Sign Up\n" +
                Color.ANSI_PURPLE + "=====================================================" + Color.ANSI_RESET);
        System.out.print("Enter the option: ");
        option = new Scanner(System.in).nextInt();

        loginOrSignUp();
        System.out.println(Color.ANSI_PURPLE + "                                   Hello, " + user.getUsername() + Color.ANSI_RESET);
    }
    private void loginOrSignUp(){
        String username;
        String password;

        boolean isValidInput = false;
        do {
            UserServiceJDBC userService;
            if(option == 1) {
                System.out.println();
                System.out.println(Color.ANSI_PURPLE + "Welcome back!" + Color.ANSI_RESET);
                do {
                    System.out.print("Please enter your username: ");
                    username = new Scanner(System.in).nextLine().trim();
                    System.out.print("Enter your password: ");
                    password = new Scanner(System.in).nextLine().trim();
                    userService = new UserServiceJDBC();
                    user = new User(username, password);
                    userService.loginUser(user.getUsername(), user.getPassword());
                    isValidInput = true;
                }while(!userService.loginCheck);
            } else if (option == 2) {
                System.out.println();
                System.out.println(Color.ANSI_PURPLE + "Hello, nice to meet you!" + Color.ANSI_RESET);
                do {
                    do {
                        System.out.print("Please enter your username: ");
                        username = new Scanner(System.in).nextLine().trim();
                        if (username.length() < 4 || username.length() > 12) {
                            System.out.println(Color.ANSI_RED + "Username length must be longer than 4 but shorter than 12!" + Color.ANSI_RESET);
                        }
                    }while(username.length() < 4 || username.length() > 12);
                    do {
                        System.out.print("Enter your password: ");
                        password = new Scanner(System.in).nextLine().trim();
                        if (password.length() < 8) {
                            System.out.println(Color.ANSI_RED + "Password length must be longer than 8!" + Color.ANSI_RESET);
                        }
                    }while(password.length() < 8);
                    userService = new UserServiceJDBC();
                    user = new User(username, password);
                    userService.addUser(user);
                    isValidInput = true;
                }while(!userService.signUpCheck);
            }else{
                System.out.println(Color.ANSI_RED + "Invalid input. Please try again." + Color.ANSI_RESET);
                System.out.print("Enter the option: ");
                option = new Scanner(System.in).nextInt();
            }
        }while(!isValidInput);
    }
    public void displayStartMenu() {
        System.out.println("\n               ⠂⠁⠈⠂⠄⠄⠂⠁⠁⠂⠄⠄⠂⠁⠁⠂ Welcome to the game ⠂⠁⠈⠂⠄⠄⠂⠁⠁⠂⠄⠄⠂⠁⠁⠂");
        System.out.println(Color.ANSI_PURPLE +  "                       ::::::         :::::     :::::::::::: ::::::  \n" + Color.ANSI_RESET +
                Color.ANSI_PURPLE +"                       :::   :::    :::    :::       :::    :::    :::\n"+ Color.ANSI_RESET +
                Color.ANSI_PURPLE +"                       :::    ::: :::        :::     :::     :::      \n"+ Color.ANSI_RESET +
                Color.ANSI_PURPLE +"                       :::    ::: :::        :::     :::       :::    \n"+ Color.ANSI_RESET +
                Color.ANSI_PURPLE +"                       :::    ::: :::        :::     :::          ::: \n"+ Color.ANSI_RESET +
                Color.ANSI_PURPLE +"                       :::   :::    :::     :::      :::    :::    :::\n"+ Color.ANSI_RESET +
                Color.ANSI_PURPLE +"                       ::::::          :::::         :::      ::::::\n" + Color.ANSI_RESET);
        System.out.println("              Please choose the mode you want to play or account to see your info:");
        System.out.println(Color.ANSI_YELLOW + "                                ⏱⭑⟡༄⏱⭑⟡༄. Timed .⏱⭑⟡༄⏱⭑⟡༄\n" + Color.ANSI_RESET);
        System.out.println(Color.ANSI_RED + "                                ˖°༄˖°༄˖°༄˖° Moves ˖°༄˖°༄˖˖°༄\n" + Color.ANSI_RESET);
        System.out.println(Color.ANSI_BLUE + "                                ⁺˚⋆｡°✩₊⋆ထ Endless ထ⁺˚⋆｡°✩₊⋆\n" + Color.ANSI_RESET);
        System.out.println(Color.ANSI_GREEN + "                               ✧ ˚. ⿻⋆｡˚ Account ୭ ⿻˚.  ༘ ˚\n" + Color.ANSI_RESET);
    }

    public User getUser() {
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        return user;
    }
}
