package main.java.sk.tuke.gamestudio.game.dots.consoleUI;

import main.java.sk.tuke.gamestudio.entity.Comment;
import main.java.sk.tuke.gamestudio.entity.Rating;
import main.java.sk.tuke.gamestudio.entity.User;
import main.java.sk.tuke.gamestudio.game.dots.core.*;
import main.java.sk.tuke.gamestudio.entity.Score;
import main.java.sk.tuke.gamestudio.game.dots.features.*;
import main.java.sk.tuke.gamestudio.services.CommentServiceJDBC;
import main.java.sk.tuke.gamestudio.services.RatingServiceJDBC;
import main.java.sk.tuke.gamestudio.services.ScoreServiceJDBC;

//TODO після того, як я в головному меню вибираю щось інше, ніж мод, то курсор не зникає; після кожної гри зробити ретурн кнопку

import java.util.List;
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
    private final StartMenuConsoleUI startMenu;
    private final EndMenuConsoleUI endMenu;
    private final User user;
    public ConsoleUI(User user) {
        field = new GameBoard();
        field.createGameBoard();
        cursor = new Cursor(field);
        gameMode = GameMode.CURSOR;
        selection = new Selection(field);
        startMenu = new StartMenuConsoleUI();
        this.user = user;
        endMenu = new EndMenuConsoleUI(user);
        addition = new String[]{
                Color.ANSI_YELLOW + "                -\"d\" for moving down;" + Color.ANSI_RESET,
                Color.ANSI_YELLOW + "                -\"u\" for moving up;" + Color.ANSI_RESET,
                "SCORES: " + scores + Color.ANSI_YELLOW + "       -\"r\" for moving right;" + Color.ANSI_RESET,
                Color.ANSI_YELLOW + "                -\"l\" for moving left;" + Color.ANSI_RESET,
                Color.ANSI_YELLOW + "                -\"m\" for change mode(selection or cursor);" + Color.ANSI_RESET,
                Color.ANSI_YELLOW + "                -\"ENTER\" to connect dots;" + Color.ANSI_RESET,
        };
        cursor.prevColor = field.gameBoard[cursor.getPosX()][cursor.getPosY()].dot;
        field.gameBoard[cursor.getPosX()][cursor.getPosY()].dot = cursor.selectDot(field.gameBoard[cursor.getPosX()][cursor.getPosY()]);

    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            startMenu.displayStartMenu();
            System.out.println("Please write the mode you want to play or \"e\" to exit: ");
            input = scanner.nextLine().trim().toLowerCase();

            switch (input) {
                case "timed":
                    timeMode();
                    writeScoreToDatabase("timed");
                    endMenu.displayEndMenu();
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
                    dataInAccountButton();
                    break;
                case "community":
                    communityButton();
                    break;
                case "e":
                    endMenu.displayEndMenu();
                    break;
                default:
                    System.out.println(Color.ANSI_RED + "Bad input!" + Color.ANSI_RESET);
            }
        } while (!isValidInput(input));
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);

        printGameBoard();
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
                endMenu.displayEndMenu();
                break;
            default:
                System.out.println(Color.ANSI_RED + "Bad input!" + Color.ANSI_RESET);
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
                        System.out.println(Color.ANSI_RED + "The moves are over!" + Color.ANSI_RESET);
                        endMenu.displayEndMenu();
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
        return input.equals("timed") || input.equals("moves") || input.equals("endless") ||
                input.equals("account") || input.equals("e");
    }

    private void timeMode() {
        playingMode = PlayingMode.TIMED;
        long startTime = System.currentTimeMillis();
        long duration = 10000;
        long endTime = startTime + duration;
        while (((endTime - System.currentTimeMillis()) / 1000) > 0) {
            System.out.println(Color.ANSI_RED + "Seconds left: " +Color.ANSI_RESET + (endTime - System.currentTimeMillis()) / 1000);
            play();
        }
        System.out.println(Color.ANSI_RED + "Time is up!" + Color.ANSI_RESET);
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
    private void writeScoreToDatabase(String mode){
        ScoreServiceJDBC scoreService = new ScoreServiceJDBC();
        Score score = new Score(scores, user.getUsername(), mode);
        scoreService.addScore(score);
    }
    private void dataInAccountButton(){
        ScoreServiceJDBC scoreServiceJDBC = new ScoreServiceJDBC();
        List<String> data;
        data = scoreServiceJDBC.getDataForAccount(user.getUsername());

        System.out.println(Color.ANSI_PURPLE + "Your recent activities displayed here...\n" + Color.ANSI_RESET);
        System.out.format("%40s%n", Color.ANSI_PURPLE + "YOUR BEST SCORES" + Color.ANSI_RESET);
        System.out.println("+------------+------------+---------------------------+");
        System.out.format("| %-10s | %-10s | %-25s |%n", "SCORE", "MODE", "DATE");
        System.out.println("+------------+------------+---------------------------+");
        for (int i = 0; i < data.size()-1; i += 3) {
            System.out.format("| %-10s | %-10s | %-25s |%n", data.get(i), data.get(i + 1), data.get(i + 2));
        }
        System.out.println("+------------+------------+---------------------------+");

        System.out.print(Color.ANSI_PURPLE + "YOUR RECENT RATING: " + Color.ANSI_RESET);
        RatingServiceJDBC ratingServiceJDBC = new RatingServiceJDBC();
        System.out.println(new Rating().getRatingInStars(ratingServiceJDBC.getRating(user.getUsername())));

        System.out.println(Color.ANSI_PURPLE + "YOUR RECENT COMMENTS: " + Color.ANSI_RESET);
        CommentServiceJDBC commentServiceJDBC = new CommentServiceJDBC();
        List<Comment> commentsList = commentServiceJDBC.getUserComments(user.getUsername());
        for (Comment comment : commentsList) {
            System.out.println(Color.ANSI_GREEN + comment.getCommentedOn() + Color.ANSI_RESET);
            System.out.println(comment.getComment());
            System.out.println();
        }
        returnQuestion();
    }

    private void returnQuestion(){
        String answer;
        do {
            System.out.print(Color.ANSI_PURPLE +
                    "Please enter 'r' if you want to return to main menu or 'e' to exit game: " + Color.ANSI_RESET);
            answer = new Scanner(System.in).nextLine();
            if (answer.equalsIgnoreCase("r")) {
                startGame();
                break;
            } else if (answer.equalsIgnoreCase("e")) {
                endMenu.displayEndMenu();
            } else {
                System.out.println(Color.ANSI_RED + "Bad input" + Color.ANSI_RESET);
            }
        }while(!(answer.equalsIgnoreCase("r") || answer.equalsIgnoreCase("e")));
    }
    private void communityButton(){
        List<Rating> ratingsList = new RatingServiceJDBC().getAllRatings();
        for(Rating rating : ratingsList){
            System.out.println(rating.getUsername());
            System.out.println(rating.getRatedOn());
            System.out.println(rating.getRatingInStars(rating.getRating()));
        }
        /*List<Comment> commentsList = new CommentServiceJDBC().getAllComments();
        for (Comment comment : commentsList) {
            System.out.println(Color.ANSI_GREEN + comment.getUsername() + " on "
                    + comment.getCommentedOn() + Color.ANSI_RESET);
        }*/
        returnQuestion();
    }
}