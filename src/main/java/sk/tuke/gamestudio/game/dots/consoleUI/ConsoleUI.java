package main.java.sk.tuke.gamestudio.game.dots.consoleUI;

import main.java.sk.tuke.gamestudio.game.dots.core.*;
import main.java.sk.tuke.gamestudio.game.dots.entity.Comment;
import main.java.sk.tuke.gamestudio.game.dots.entity.Rating;
import main.java.sk.tuke.gamestudio.game.dots.entity.Score;
import main.java.sk.tuke.gamestudio.game.dots.entity.User;
import main.java.sk.tuke.gamestudio.game.dots.features.*;
import main.java.sk.tuke.gamestudio.game.dots.services.CommentServiceJDBC;
import main.java.sk.tuke.gamestudio.game.dots.services.RatingServiceJDBC;
import main.java.sk.tuke.gamestudio.game.dots.services.ScoreServiceJDBC;
import main.java.sk.tuke.gamestudio.game.dots.services.UserServiceJDBC;

import java.util.Scanner;

public class ConsoleUI {
    private final GameBoard field;
    private final Cursor cursor;
    private GameMode gameMode;
    private final Selection selection;
    private final String[] addition;
    private int scores = 0;
    private int moves = 6;
    private PlayingMode playingMode;
    private User user;
    private int option;
    public ConsoleUI() {
        field = new GameBoard();
        field.createGameBoard();
        cursor = new Cursor(field);
        gameMode = GameMode.CURSOR;
        selection = new Selection(field);
        addition = new String[]{
                Color.ANSI_YELLOW + "                -\"d\" for moving down;" + Color.ANSI_RESET,
                Color.ANSI_YELLOW + "                -\"u\" for moving up;" + Color.ANSI_RESET,
                "SCORES: " + scores + Color.ANSI_YELLOW + "       -\"r\" for moving right;" + Color.ANSI_RESET,
                Color.ANSI_YELLOW + "                -\"l\" for moving left;" + Color.ANSI_RESET,
                Color.ANSI_YELLOW + "                -\"m\" for change mode(selection or cursor);" + Color.ANSI_RESET,
                Color.ANSI_YELLOW + "                -\"ENTER\" to connect dots;" + Color.ANSI_RESET,
        };
    }

    public void gameStart() {
        /*System.out.println(Color.ANSI_PURPLE +  "========= Please choose the option (1 or 2) =========\n" + Color.ANSI_RESET +
                           "                    1. Log In\n" + "                    2. Sign Up\n" +
                Color.ANSI_PURPLE + "=====================================================" + Color.ANSI_RESET);
        System.out.print("Enter the option: ");
        option = new Scanner(System.in).nextInt();*/

        //userRegistration();
        //System.out.println(Color.ANSI_PURPLE + "                                   Hello, " + user.getUsername() + Color.ANSI_RESET);

        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            createButtonsWindow();
            System.out.println("Please write the mode you want to play or \"e\" to exit: ");
            input = scanner.nextLine().trim().toLowerCase();

            cursor.prevColor = field.gameBoard[cursor.getPosX()][cursor.getPosY()].dot;
            field.gameBoard[cursor.getPosX()][cursor.getPosY()].dot = cursor.selectDot(field.gameBoard[cursor.getPosX()][cursor.getPosY()]);

            switch (input) {
                case "timed":
                    timeMode();
                    writeScoreToDatabase("timed");
                    endMenu();
                    break;
                case "moves":
                    moveMode();
                    break;
                case "endless":
                    playingMode = PlayingMode.ENDLESS;
                    while (true) {
                        play();
                    }
                case "account":
                    System.out.println(); //тільки коменти, зірочки та останні 5 результатів користувача
                    break;
                case "e":
                    endMenu();
                    break;
                default:
                    System.out.println("Invalid input");
            }
        } while (!isValidInput(input));
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        printGameBoard();
        //доробити колір для помилок і рестарт меню
        System.out.print("Enter a letter (u, d, r, l, m, e) or 'ENTER': ");
        String input = scanner.nextLine().trim().toLowerCase();
        switch (input) {
            case "m":
                switchMode();
                break;
            case "u":
                moveUp();
                break;
            case "d":
                moveDown();
                break;
            case "r":
                moveRight();
                break;
            case "l":
                moveLeft();
                break;
            case "":
                connectionButton();
                break;
            case "e":
                if(playingMode == PlayingMode.TIMED){
                    writeScoreToDatabase("timed");
                } else if (playingMode == PlayingMode.MOVES) {
                    writeScoreToDatabase("moves");
                }else {
                    writeScoreToDatabase("endless");
                }
                endMenu();
                break;
            default:
                System.out.println("Invalid input");
        }
    }

    private void switchMode() {
        if (gameMode == GameMode.CURSOR) {
            gameMode = GameMode.SELECTION;
            selection.setPosX(cursor.getPosX());
            selection.setPosY(cursor.getPosY());
            if (field.gameBoard[selection.getPosX()][selection.getPosY()].dot.contains(Color.WHITE_BACKGROUND)) {
                field.gameBoard[selection.getPosX()][selection.getPosY()].dot = field.gameBoard[selection.getPosX()][selection.getPosY()].dot.replace(Color.WHITE_BACKGROUND, "");
            }
            field.gameBoard[selection.getPosX()][selection.getPosY()].dot = selection.selectDot(field.gameBoard[selection.getPosX()][selection.getPosY()]);
            field.gameBoard[selection.getPosX()][selection.getPosY()].setState(DotState.SELECTED);
        } else {
            gameMode = GameMode.CURSOR;
            selection.resetAllSelection(field);
            field.cleanArray();
            field.gameBoard[cursor.getPosX()][cursor.getPosY()].dot = cursor.selectDot(field.gameBoard[cursor.getPosX()][cursor.getPosY()]);
            field.gameBoard[cursor.getPosX()][cursor.getPosY()].setState(DotState.NOT_SELECTED);
        }
    }

    private void moveUp() {
        if (gameMode == GameMode.CURSOR) {
            cursor.moveUp();
        } else if (gameMode == GameMode.SELECTION) {
            selection.moveUp();
        }
    }

    private void moveDown() {
        if (gameMode == GameMode.CURSOR) {
            cursor.moveDown();
        } else if (gameMode == GameMode.SELECTION) {
            selection.moveDown();
        }
    }

    private void moveRight() {
        if (gameMode == GameMode.CURSOR) {
            cursor.moveRight();
        } else if (gameMode == GameMode.SELECTION) {
            selection.moveRight();
        }
    }

    private void moveLeft() {
        if (gameMode == GameMode.CURSOR) {
            cursor.moveLeft();
        } else if (gameMode == GameMode.SELECTION) {
            selection.moveLeft();
        }
    }

    private void connectionButton() {
        if (gameMode == GameMode.SELECTION) {
            int countDots = 0;
            for (int i = 0; i < field.selectedDots.length; i++) {
                for (int j = 0; j < field.selectedDots.length; j++) {
                    if (!field.selectedDots[i][j].dot.equals("0")) {
                        countDots++;
                    }
                }
            }
            if (countDots > 1) {
                scores += countDots;
                updateScores();
                if (playingMode == PlayingMode.MOVES) {
                    moves -= 1;
                    updateMoves();
                    if (moves == 0) {
                        field.missingAnimation();
                        field.shiftDotsDown();
                        gameMode = GameMode.CURSOR;
                        selection.resetAllSelection(field);
                        printGameBoard();
                        writeScoreToDatabase("moves");
                        endMenu();
                    }
                }
                field.missingAnimation();
                field.shiftDotsDown();
                gameMode = GameMode.CURSOR;
                selection.resetAllSelection(field);
                cursor.prevColor = field.gameBoard[cursor.getPosX()][cursor.getPosY()].dot;
                field.gameBoard[cursor.getPosX()][cursor.getPosY()].dot = cursor.selectDot(field.gameBoard[cursor.getPosX()][cursor.getPosY()]);
            } else {
                gameMode = GameMode.CURSOR;
                selection.resetAllSelection(field);
                cursor.prevColor = field.gameBoard[cursor.getPosX()][cursor.getPosY()].dot;
                field.gameBoard[cursor.getPosX()][cursor.getPosY()].dot = cursor.selectDot(field.gameBoard[cursor.getPosX()][cursor.getPosY()]);
            }
        }
    }

    private void createButtonsWindow() {
        System.out.println("\n               ⠂⠁⠈⠂⠄⠄⠂⠁⠁⠂⠄⠄⠂⠁⠁⠂ Welcome to the game ⠂⠁⠈⠂⠄⠄⠂⠁⠁⠂⠄⠄⠂⠁⠁⠂");
        System.out.println(Color.ANSI_PURPLE +  "                       ::::::         :::::     :::::::::::  ::::::  \n" + Color.ANSI_RESET +
                Color.ANSI_PURPLE +"                       :::   :::    :::    :::       :::    :::    :::\n"+ Color.ANSI_RESET +
                Color.ANSI_PURPLE +"                       :::    ::: :::        :::     :::     :::      \n"+ Color.ANSI_RESET +
                Color.ANSI_PURPLE +"                       :::    ::: :::        :::     :::       :::    \n"+ Color.ANSI_RESET +
                Color.ANSI_PURPLE +"                       :::    ::: :::        :::     :::          ::: \n"+ Color.ANSI_RESET +
                Color.ANSI_PURPLE +"                       :::   :::    :::     :::      :::    :::    :::\n"+ Color.ANSI_RESET +
                Color.ANSI_PURPLE +"                       ::::::         :::::          :::      ::::::\n" + Color.ANSI_RESET);
        System.out.println("              Please choose the mode you want to play or account to see your info:");
        System.out.println(Color.ANSI_YELLOW + "                                ⏱⭑⟡༄⏱⭑⟡༄. Timed .⏱⭑⟡༄⏱⭑⟡༄\n" + Color.ANSI_RESET);
        System.out.println(Color.ANSI_RED + "                                ˖°༄˖°༄˖°༄˖° Moves ˖°༄˖°༄˖˖°༄\n" + Color.ANSI_RESET);
        System.out.println(Color.ANSI_BLUE + "                                ⁺˚⋆｡°✩₊⋆ထ Endless ထ⁺˚⋆｡°✩₊⋆\n" + Color.ANSI_RESET);
        System.out.println(Color.ANSI_GREEN + "                               ✧ ˚. ⿻⋆｡˚ Account ୭ ⿻˚.  ༘ ˚\n" + Color.ANSI_RESET);
    }

    private void printGameBoard() {
        System.out.println(Color.ANSI_GREEN + "ะ.⋆⸙͎۪⋆༶⋆⸙͎۪˙კ¸.⋆⸙͎۪⋆༶⋆⸙͎۪˙კ¸.⋆⸙͎۪⋆༶⋆⸙͎۪˙კ¸.⋆⸙͎۪⋆༶⋆⸙͎۪˙კ¸.⋆⸙͎۪⋆༶⋆⸙͎۪˙კ¸.⋆⸙͎۪⋆༶⋆⸙͎۪˙კ¸.⋆⸙͎۪⋆༶⋆⸙͎۪˙კ¸.⋆⸙͎۪⋆༶⋆⸙͎۪˙კ¸.⋆⸙͎۪⋆༶⋆⸙͎۪˙კ¸.⋆⸙͎۪⋆༶⋆⸙͎۪˙კ¸.⋆⸙͎۪⋆༶⋆⸙͎۪˙კ¸⊹\n" + Color.ANSI_RESET);
        System.out.println("╔════ஓ๑♡๑ஓ═══╗                         " + Color.ANSI_YELLOW + "⟡ INSTRUCTIONS ⟡" + Color.ANSI_RESET);
        for (int i = 0; i < field.getBoardSize(); i++) {
            System.out.print("| ");
            for (int j = 0; j < field.getBoardSize(); j++) {
                System.out.print(field.gameBoard[i][j].dot + " ");
            }
            System.out.print("|      ");
            System.out.println(addition[i]);
        }
        System.out.println("╚════ஓ๑♡๑ஓ═══╝                      " + Color.ANSI_YELLOW + "-\"e\" to exit;" + Color.ANSI_RESET);
    }

    private void updateScores() {
        addition[2] = "SCORES: " + scores + Color.ANSI_YELLOW + "       -\"r\" for moving right;" + Color.ANSI_RESET;
    }

    private void updateMoves() {
        addition[3] = "MOVES: " + moves + Color.ANSI_YELLOW + "        -\"l\" for moving left;" + Color.ANSI_RESET;
        if (moves == 5 || moves == 4) {
            addition[3] = "MOVES: " + Color.ANSI_YELLOW + moves + Color.ANSI_YELLOW + "        -\"l\" for moving left;" + Color.ANSI_RESET;
        } else if (moves <= 3) {
            addition[3] = "MOVES: " + Color.ANSI_RED + moves + Color.ANSI_YELLOW + "        -\"l\" for moving left;" + Color.ANSI_RESET;
        }
    }

    private boolean isValidInput(String input) {
        return input.equals("m") || input.equals("u") || input.equals("d") || input.equals("r") ||
                input.equals("l") || input.equals("") || input.equals("e");
    }

    private void timeMode() {
        playingMode = PlayingMode.TIMED;
        long startTime = System.currentTimeMillis();
        long duration = 10000;
        long endTime = startTime + duration;
        while (((endTime - System.currentTimeMillis()) / 1000) > 0) {
            System.out.println("Seconds left: " + (endTime - System.currentTimeMillis()) / 1000);
            play();
        }
        boolean timeIsEnd = true;
        System.out.println("Time id up!");
    }

    private void moveMode() {
        playingMode = PlayingMode.MOVES;
        while (moves >= 0) {
            if (moves == 5 || moves == 4) {
                addition[3] = "MOVES: " + Color.ANSI_YELLOW + moves + Color.ANSI_YELLOW + "        -\"l\" for moving left;" + Color.ANSI_RESET;
            } else if (moves <= 3) {
                addition[3] = "MOVES: " + Color.ANSI_RED + moves + Color.ANSI_YELLOW + "        -\"l\" for moving left;" + Color.ANSI_RESET;
            } else {
                addition[3] = "MOVES: " + moves + Color.ANSI_YELLOW + "        -\"l\" for moving left;" + Color.ANSI_RESET;
            }
            play();
        }
    }

    private void endMenu() {
        Scanner scanner = new Scanner(System.in);
        String answer;
        do {
            System.out.println("Already leave us? We are happy that you play our game. " +
                    "Would you please rate us and leave the comment? [Y/N]");
            answer = scanner.nextLine();
            if (answer.equals("y")) {
                leaveComment();
                rateUs();
                System.exit(0);
            } else if (answer.equals("n")) {
                System.out.println("We are sorry that you do not leave feedback, " +
                        "but we hope that you enjoyed our game :)");
                System.exit(0);
            } else {
                System.out.println("Incorrect answer. Please write down Y or N.");
            }
        } while (true);
    }
    private void leaveComment(){
        CommentServiceJDBC commentService;
        Comment comment;

        do {
            System.out.print("Comment (only 100 symbols!):  ");
            String text = new Scanner(System.in).nextLine().toLowerCase();

            if(text.length() > 100){
                System.out.println("The comment should not exceed 100 symbols. Please try again.");
            } else {
                commentService = new CommentServiceJDBC();
                comment = new Comment(text);
                commentService.addComment(comment, user.getUsername());
                System.out.println("Your comment successfully added. Thanks!");
                break;
            }

        } while (true);
    }
    private void rateUs(){
        int answer;

        RatingServiceJDBC ratingService;
        Rating rating;

        do {
            System.out.println("Please rate us from 1 to 5: ");
            answer = new Scanner(System.in).nextInt();

            if(answer <= 0 || answer > 5){
                System.out.println("The rating must be only from 1 to 5. Please try again.");
            } else {
                ratingService = new RatingServiceJDBC();
                rating = new Rating(answer);
                ratingService.addRating(rating, user.getUsername());
                System.out.println("Your rate successfully added. Thanks!");
                break;
            }

        } while (true);
    }
    private void writeScoreToDatabase(String mode){
        ScoreServiceJDBC scoreService = new ScoreServiceJDBC();
        Score score = new Score(scores);
        scoreService.addResult(score, user.getUsername(), mode);
    }
    private void userRegistration(){
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
                        if (username.length() < 4) {
                            System.out.println(Color.ANSI_RED + "Username length must be longer than 4!" + Color.ANSI_RESET);
                        }
                    }while(username.length() < 4);
                    do {
                        System.out.print("Enter your password: ");
                        password = new Scanner(System.in).nextLine().trim();
                        if (password.length() < 8) {
                            System.out.println(Color.ANSI_RED + "Username length must be longer than 4!" + Color.ANSI_RESET);
                        }
                    }while(password.length() < 8);
                    userService = new UserServiceJDBC();
                    user = new User(username, password);
                    userService.addUser(user);
                    isValidInput = true;
                }while(!userService.signUpCheck);
            }else{
                System.out.println(Color.ANSI_RED + "Invalid input. Please try again." +Color.ANSI_RESET); //безкінечно виводить
            }
        }while(!isValidInput);
    }
    private void accountButton(){

    }
}
