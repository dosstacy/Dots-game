package main.java.sk.tuke.gamestudio.game.dots.core;

import main.java.sk.tuke.gamestudio.game.dots.consoleUI.ConsoleUI;
import main.java.sk.tuke.gamestudio.game.dots.features.TimeMode;

public class TimeModeThread extends Thread{
    private TimeMode timeMode;
    public TimeModeThread() {
        this.timeMode = new TimeMode();
    }

    public void run() {
        try {
            timeMode.Timer();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
