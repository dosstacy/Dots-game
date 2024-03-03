package main.java.sk.tuke.gamestudio.game.dots.core;

import main.java.sk.tuke.gamestudio.game.dots.consoleUI.ConsoleUI;

public class GameThread extends Thread{
    private ConsoleUI game;

    public GameThread() {
        this.game = new ConsoleUI();
    }
    public void run(){
        try {
            game.play();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
