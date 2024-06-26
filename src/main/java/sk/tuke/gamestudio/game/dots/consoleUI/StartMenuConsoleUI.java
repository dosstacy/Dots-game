package sk.tuke.gamestudio.game.dots.consoleUI;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.game.dots.features.Color;
import sk.tuke.gamestudio.services.UserService;

import java.util.Scanner;
public class StartMenuConsoleUI {
    private User user;
    private String option;
    @Autowired
    private UserService userService;
    private final Scanner scanner = new Scanner(System.in);
    public StartMenuConsoleUI() {
    }
    public void displayRegistrationMenu(){
        System.out.println(Color.ANSI_PURPLE +  "========= Please choose the option (1 or 2) =========\n" + Color.ANSI_RESET +
                "                    1. Log In\n" + "                    2. Sign Up\n" +
                Color.ANSI_PURPLE + "=====================================================" + Color.ANSI_RESET);
        System.out.print("Enter the option: ");
        option = scanner.nextLine();

        loginOrSignUp();
        System.out.format("%60s%n", Color.ANSI_PURPLE + "Hello, " + user.getUsername() + Color.ANSI_RESET);
    }
    private void loginOrSignUp() {
        String username;
        String password;

        boolean success = false;
        while (!option.equals("1") && !option.equals("2")) {
            System.out.println(Color.ANSI_RED + "Invalid input. Please try again." + Color.ANSI_RESET);
            System.out.print("Enter the option: ");
            option = scanner.nextLine();
        }

        if (option.equals("1")) {
            System.out.println();
            System.out.println(Color.ANSI_PURPLE + "Welcome back!" + Color.ANSI_RESET);
            do {
                System.out.print("Please enter your username: ");
                username = scanner.nextLine().trim();
                System.out.print("Enter your password: ");
                password = scanner.nextLine().trim();
                user = new User(username, password);

                try {
                    userService.loginUser(user.getUsername(), user.getPassword());
                    success = true;
                } catch (Exception e) {
                    System.out.println(Color.ANSI_RED + "Incorrect login or password" + Color.ANSI_RESET);
                }
            } while (!success);
            System.out.println(Color.ANSI_GREEN + "Successful log in!" + Color.ANSI_RESET);
        } else {
            System.out.println();
            System.out.println(Color.ANSI_PURPLE + "Hello, nice to meet you!" + Color.ANSI_RESET);
            do {
                do {
                    System.out.print("Please enter your username: ");
                    username = scanner.nextLine().trim();
                    if (username.length() < 4 || username.length() > 12) {
                        System.out.println(Color.ANSI_RED + "Username length must be longer than 4 but shorter than 12!" + Color.ANSI_RESET);
                    }
                } while (username.length() < 4 || username.length() > 12);
                do {
                    System.out.print("Enter your password: ");
                    password = scanner.nextLine().trim();
                    if (password.length() < 6) {
                        System.out.println(Color.ANSI_RED + "Password length must be longer than 6!" + Color.ANSI_RESET);
                    }
                } while (password.length() < 6);
                user = new User(username, password);

                try {
                    userService.addUser(user);
                    success = true;
                } catch (Exception e) {
                    System.out.println(Color.ANSI_RED + "This login is already in use! Please enter another login." + Color.ANSI_RESET);
                }

            } while (!success);
            System.out.println(Color.ANSI_GREEN + "Successful sign up!" + Color.ANSI_RESET);
        }
    }
    public void displayStartMenu() {
        System.out.println("               ⠂⠁⠈⠂⠄⠄⠂⠁⠁⠂⠄⠄⠂⠁⠁⠂ Welcome to the game ⠂⠁⠈⠂⠄⠄⠂⠁⠁⠂⠄⠄⠂⠁⠁⠂");
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
        System.out.println(Color.ANSI_GREEN + "                               ° ༘⋆\uD83D\uDD87₊˚ෆ \uD83D\uDD87 Community .° ༘⋆\uD83D\uDD87₊˚ෆ\n" + Color.ANSI_RESET);
        System.out.println(Color.ANSI_PURPLE + "                               ✧ ˚. ⿻⋆｡˚ Account ୭ ⿻˚.  ༘ ˚✧\n" + Color.ANSI_RESET);
    }
    public User getUser() {
        return user;
    }
}
