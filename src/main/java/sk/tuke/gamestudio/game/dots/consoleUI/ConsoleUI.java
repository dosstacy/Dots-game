package sk.tuke.gamestudio.game.dots.consoleUI;

import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.game.dots.core.*;
import sk.tuke.gamestudio.game.dots.features.*;

import java.util.Scanner;
public class ConsoleUI {
    private final GameBoard field;
    private final Cursor cursor;
    private GameMode gameMode;
    private final Selection selection;
    private String[] addition;
    private int moves = 6;
    private boolean isEndlessEnd = false;
    private PlayingMode playingMode;
    private final StartMenuConsoleUI startMenu;
    private final EndMenuConsoleUI endMenu;
    private final JDBCConsoleUI jdbcConsoleUI;

    public ConsoleUI(User user) {
        field = new GameBoard();
        field.createGameBoard();
        cursor = new Cursor(field);
        gameMode = GameMode.CURSOR;
        selection = new Selection(field);
        startMenu = new StartMenuConsoleUI();
        endMenu = new EndMenuConsoleUI(user);
        jdbcConsoleUI = new JDBCConsoleUI(user);

        cursor.prevColor = field.gameBoard[cursor.getPosX()][cursor.getPosY()].dot;
        field.gameBoard[cursor.getPosX()][cursor.getPosY()].dot = cursor.selectDot(field.gameBoard[cursor.getPosX()][cursor.getPosY()]);
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        String input;
        initializeAddition();

        do {
            startMenu.displayStartMenu();
            System.out.println("Please write the mode you want to play or \"e\" to exit: ");
            input = scanner.nextLine().trim().toLowerCase();

            switch (input) {
                case "timed":
                    timeMode();
                    jdbcConsoleUI.writeScoreToDatabase("timed");
                    endMenu.displayEndMenu();
                    break;
                case "moves":
                    moveMode();
                    break;
                case "endless":
                    playingMode = PlayingMode.ENDLESS;
                    while (!isEndlessEnd) {
                        play();
                    }
                case "account":
                    jdbcConsoleUI.dataInAccountButton();
                    returnQuestion();
                    break;
                case "community":
                    jdbcConsoleUI.communityButton();
                    returnQuestion();
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
                   jdbcConsoleUI.writeScoreToDatabase("timed");
                } else if (playingMode == PlayingMode.MOVES) {
                   jdbcConsoleUI.writeScoreToDatabase("moves");
                }else {
                   jdbcConsoleUI.writeScoreToDatabase("endless");
                }
                isEndlessEnd = true;
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
            for (int row = 0; row < field.selectedDots.length; row++) {
                for (int col = 0; col < field.selectedDots.length; col++) {
                    if (!field.selectedDots[row][col].dot.equals("0")) {
                        countDots++;
                    }
                }
            }
            if (countDots > 1) {
                jdbcConsoleUI.setScores(jdbcConsoleUI.getScores() + countDots);
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
                        jdbcConsoleUI.writeScoreToDatabase("moves");
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
    private void initializeAddition(){
        addition = new String[]{
                Color.ANSI_YELLOW + "                -\"d\" for moving down;" + Color.ANSI_RESET,
                Color.ANSI_YELLOW + "                -\"u\" for moving up;" + Color.ANSI_RESET,
                "SCORES: " + jdbcConsoleUI.getScores() + Color.ANSI_YELLOW + "       -\"r\" for moving right;" + Color.ANSI_RESET,
                Color.ANSI_YELLOW + "                -\"l\" for moving left;" + Color.ANSI_RESET,
                Color.ANSI_YELLOW + "                -\"m\" for change mode(selection or cursor);" + Color.ANSI_RESET,
                Color.ANSI_YELLOW + "                -\"ENTER\" to connect dots;" + Color.ANSI_RESET,
        };
    }

    private void printGameBoard() {
        System.out.println(Color.ANSI_GREEN + "ะ.⋆⸙͎۪⋆༶⋆⸙͎۪˙კ¸.⋆⸙͎۪⋆༶⋆⸙͎۪˙კ¸.⋆⸙͎۪⋆༶⋆⸙͎۪˙კ¸.⋆⸙͎۪⋆༶⋆⸙͎۪˙კ¸.⋆⸙͎۪⋆༶⋆⸙͎۪˙კ¸.⋆⸙͎۪⋆༶⋆⸙͎۪˙კ¸.⋆⸙͎۪⋆༶⋆⸙͎۪˙კ¸.⋆⸙͎۪⋆༶⋆⸙͎۪˙კ¸.⋆⸙͎۪⋆༶⋆⸙͎۪˙კ¸.⋆⸙͎۪⋆༶⋆⸙͎۪˙კ¸.⋆⸙͎۪⋆༶⋆⸙͎۪˙კ¸⊹\n" + Color.ANSI_RESET);
        System.out.println("╔════ஓ๑♡๑ஓ═══╗                         " + Color.ANSI_YELLOW + "⟡ INSTRUCTIONS ⟡" + Color.ANSI_RESET);
        for (int row = 0; row < field.getBoardSize(); row++) {
            System.out.print("| ");
            for (int col = 0; col < field.getBoardSize(); col++) {
                System.out.print(field.gameBoard[row][col].dot + " ");
            }
            System.out.print("|      ");
            System.out.println(addition[row]);
        }
        System.out.println("╚════ஓ๑♡๑ஓ═══╝                      " + Color.ANSI_YELLOW + "-\"e\" to exit;" + Color.ANSI_RESET);
    }

    private void updateScores() {
        addition[2] = "SCORES: " + jdbcConsoleUI.getScores() + Color.ANSI_YELLOW + "       -\"r\" for moving right;" + Color.ANSI_RESET;
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
        long duration = 15000;
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
}