package main.java.sk.tuke.gamestudio.game.dots;

import main.java.sk.tuke.gamestudio.game.dots.consoleUI.ConsoleUI;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        ConsoleUI consoleUI = new ConsoleUI();
        consoleUI.gameStart();
        //consoleUI.play();
        //new TimeMode().Timer();
        //GameBoard gameBoard = new GameBoard();
        //consoleUI.createButtonWindow();
        /*TimeModeThread timeModeThread = new TimeModeThread();
        InputThread inputThread = new InputThread();
        timeModeThread.start();
        Thread.sleep(2000);
        inputThread.start();*/
        //System.out.println();

        /*int charsWritten = 0;
        int start = 1;
        while (start <= 10) {
            Thread.sleep(1000);
            String writeToConsole = start + "";
            for (int i = 0; i < charsWritten; i++) {
                System.out.print("\b");
            }
            System.out.print(writeToConsole);
            charsWritten = writeToConsole.length();
            start += 1;
        }*/
        }
}
