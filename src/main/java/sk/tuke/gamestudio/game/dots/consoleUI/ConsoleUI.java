package main.java.sk.tuke.gamestudio.game.dots.consoleUI;

import main.java.sk.tuke.gamestudio.game.dots.core.*;
import main.java.sk.tuke.gamestudio.game.dots.features.*;

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
                Color.ANSI_YELLOW + "                -\"e\" to exit;" + Color.ANSI_RESET
        };
    }
    public void gameStart() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            createButtonsWindow();
            System.out.println("Please write the mode you want to play or \"e\" to exit: ");
            input = scanner.nextLine().trim().toLowerCase();
            switch (input) {
                case "timed":
                    playingMode = PlayingMode.TIMED;
                    play();
                    break;
                case "moves":
                    playingMode = PlayingMode.MOVES;
                    if (moves == 5 || moves == 4) {
                        addition[3] = "MOVES: " + Color.ANSI_YELLOW + moves + Color.ANSI_YELLOW + "        -\"l\" for moving left;" + Color.ANSI_RESET;
                    } else if (moves <= 3) {
                        addition[3] = "MOVES: " + Color.ANSI_RED + moves + Color.ANSI_YELLOW + "        -\"l\" for moving left;" + Color.ANSI_RESET;
                    } else {
                        addition[3] = "MOVES: " + moves + Color.ANSI_YELLOW + "        -\"l\" for moving left;" + Color.ANSI_RESET;
                    }
                    play();
                    break;
                case "endless":
                    playingMode = PlayingMode.ENDLESS;
                    play();
                    break;
                case "e":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid input");
                    //break;
            }
        }while(!isValidInput(input));
    }

    public void play() throws InterruptedException {
        cursor.prevColor = field.gameBoard[cursor.getPosX()][cursor.getPosY()].dot;
        field.gameBoard[cursor.getPosX()][cursor.getPosY()].dot = cursor.selectDot(field.gameBoard[cursor.getPosX()][cursor.getPosY()]);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            printGameBoard();
            if(playingMode == PlayingMode.TIMED){
                TimeModeThread timeModeThread = new TimeModeThread();
                timeModeThread.start();
            }
            /*InputThread inputThread = new InputThread();
            inputThread.start();
            String input = inputThread.getInput();*/
            //Thread.sleep(3000);

            System.out.print("Enter a letter (u, d, r, l, m, e) or 'ENTER': ");
            String input = scanner.nextLine().trim().toLowerCase();
            switch (input) {
                case "m": switchMode(); break;
                case "u": moveUp(); break;
                case "d": moveDown(); break;
                case "r": moveRight(); break;
                case "l": moveLeft(); break;
                case "": connectionButton(); break;
                case "e":
                    System.exit(0);
                default:
                    System.out.println("Invalid input");
            }

        }
    }

    private void switchMode(){
        if (gameMode == GameMode.CURSOR) {
            gameMode = GameMode.SELECTION;
            selection.setPosX(cursor.getPosX());
            selection.setPosY(cursor.getPosY());
            if(field.gameBoard[selection.getPosX()][selection.getPosY()].dot.contains(Color.WHITE_BACKGROUND)) {
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

    private void moveUp(){
        if (gameMode == GameMode.CURSOR) {
            cursor.moveUp();
        } else if (gameMode == GameMode.SELECTION) {
            selection.moveUp();
        }
    }

    private void moveDown(){
        if (gameMode == GameMode.CURSOR) {
            cursor.moveDown();
        } else if (gameMode == GameMode.SELECTION) {
            selection.moveDown();
        }
    }

    private void moveRight(){
        if (gameMode == GameMode.CURSOR) {
            cursor.moveRight();
        } else if (gameMode == GameMode.SELECTION) {
            selection.moveRight();
        }
    }

    private void moveLeft(){
        if (gameMode == GameMode.CURSOR) {
            cursor.moveLeft();
        } else if (gameMode == GameMode.SELECTION) {
            selection.moveLeft();
        }
    }

    private void connectionButton() throws InterruptedException {
        if(gameMode == GameMode.SELECTION){
            int countDots = 0;
            for(int i = 0; i < field.selectedDots.length; i++){
                for(int j = 0; j < field.selectedDots.length; j++){
                    if(!field.selectedDots[i][j].dot.equals("0")){
                        countDots++;
                    }
                }
            }
            if(countDots > 1) {
                scores += countDots;
                updateScores();
                if(playingMode == PlayingMode.MOVES) {
                    moves -= 1;
                    updateMoves();
                    if(moves == 0){
                        field.missingAnimation();
                        field.shiftDotsDown();
                        gameMode = GameMode.CURSOR;
                        selection.resetAllSelection(field);
                        printGameBoard();
                        System.exit(0);
                    }
                }
                field.missingAnimation();
                field.shiftDotsDown();
                gameMode = GameMode.CURSOR;
                selection.resetAllSelection(field);
                cursor.prevColor = field.gameBoard[cursor.getPosX()][cursor.getPosY()].dot;
                field.gameBoard[cursor.getPosX()][cursor.getPosY()].dot = cursor.selectDot(field.gameBoard[cursor.getPosX()][cursor.getPosY()]);
            }else{
                gameMode = GameMode.CURSOR;
                selection.resetAllSelection(field);
                cursor.prevColor = field.gameBoard[cursor.getPosX()][cursor.getPosY()].dot;
                field.gameBoard[cursor.getPosX()][cursor.getPosY()].dot = cursor.selectDot(field.gameBoard[cursor.getPosX()][cursor.getPosY()]);
            }
        }
    }

    private void createButtonsWindow() {
        System.out.println("\n             ⠂⠁⠈⠂⠄⠄⠂⠁⠁⠂⠄⠄⠂⠁⠁⠂Welcome to the game \"Dots\"!⠂⠁⠈⠂⠄⠄⠂⠁⠁⠂⠄⠄⠂⠁⠁⠂");
        System.out.println("                            Please choose the mode you want to play:");
        System.out.println(Color.ANSI_YELLOW + "                                ⏱⭑⟡༄⏱⭑⟡༄. Timed .⏱⭑⟡༄⏱⭑⟡༄\n" + Color.ANSI_RESET);
        System.out.println(Color.ANSI_RED + "                                ˖°༄˖°༄˖°༄˖° Moves ˖°༄˖°༄˖˖°༄\n" + Color.ANSI_RESET);
        System.out.println(Color.ANSI_BLUE + "                                ⁺˚⋆｡°✩₊⋆ထ Endless ထ⁺˚⋆｡°✩₊⋆\n" + Color.ANSI_RESET);
    }

    private void printGameBoard() {
        System.out.println(Color.ANSI_GREEN + "ะ.⋆⸙͎۪⋆༶⋆⸙͎۪˙კ¸.⋆⸙͎۪⋆༶⋆⸙͎۪˙კ¸.⋆⸙͎۪⋆༶⋆⸙͎۪˙კ¸.⋆⸙͎۪⋆༶⋆⸙͎۪˙კ¸.⋆⸙͎۪⋆༶⋆⸙͎۪˙კ¸.⋆⸙͎۪⋆༶⋆⸙͎۪˙კ¸.⋆⸙͎۪⋆༶⋆⸙͎۪˙კ¸.⋆⸙͎۪⋆༶⋆⸙͎۪˙კ¸.⋆⸙͎۪⋆༶⋆⸙͎۪˙კ¸.⋆⸙͎۪⋆༶⋆⸙͎۪˙კ¸.⋆⸙͎۪⋆༶⋆⸙͎۪˙კ¸⊹\n" + Color.ANSI_RESET);
        System.out.println("╔════ஓ๑♡๑ஓ═══╗                         " + Color.ANSI_YELLOW +  "⟡ INSTRUCTIONS ⟡" + Color.ANSI_RESET);
        for (int i = 0; i < field.getBoardSize(); i++) {
            System.out.print("| ");
            for (int j = 0; j < field.getBoardSize(); j++) {
                System.out.print(field.gameBoard[i][j].dot + " ");
            }
            System.out.print("|      ");
            System.out.println(addition[i]);
        }
        System.out.println("╚════ஓ๑♡๑ஓ═══╝");
    }
    private void updateScores() {
        addition[2] = "SCORES: " + scores + Color.ANSI_YELLOW + "       -\"r\" for moving right;" + Color.ANSI_RESET;
    }
    private void updateMoves() {
        addition[3] = "MOVES: " + moves + Color.ANSI_YELLOW + "        -\"l\" for moving left;" + Color.ANSI_RESET;
        if(moves == 5 || moves == 4){
            addition[3] = "MOVES: " + Color.ANSI_YELLOW + moves + Color.ANSI_YELLOW + "        -\"l\" for moving left;" + Color.ANSI_RESET;
        } else if (moves <= 3) {
            addition[3] = "MOVES: " + Color.ANSI_RED + moves + Color.ANSI_YELLOW + "        -\"l\" for moving left;" + Color.ANSI_RESET;
        }
    }
    private boolean isValidInput(String input) {
        return input.equals("m") || input.equals("u") || input.equals("d") || input.equals("r") ||
                input.equals("l") || input.equals("") || input.equals("e");
    }
}
