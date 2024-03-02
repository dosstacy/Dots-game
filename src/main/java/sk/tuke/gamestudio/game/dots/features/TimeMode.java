package main.java.sk.tuke.gamestudio.game.dots.features;

import main.java.sk.tuke.gamestudio.game.dots.core.Mode;

public class TimeMode implements Mode {
    @Override
    public void startGame() {

    }

    @Override
    public void isWinOrLose() {

    }

    public void Timer() throws InterruptedException {
        int charsWritten = 0;
        int start = 5000;

        while (start != 0) {
            Thread.sleep(1000);
            int elapsedTime = start;
            elapsedTime = elapsedTime / 1000;

            String seconds = Integer.toString(elapsedTime % 60);
            String minutes = Integer.toString((elapsedTime % 3600) / 60);

            if (seconds.length() < 2) {
                seconds = "0" + seconds;
            }

            if (minutes.length() < 2) {
                minutes = "0" + minutes;
            }

            String writeThis = minutes + ":" + seconds;

            for (int i = 0; i < charsWritten; i++) {
                System.out.print("\b");
            }
            System.out.print(writeThis);
            charsWritten = writeThis.length();
            start -= 1000;
        }
        Thread.sleep(1000);
        System.out.print("\b");
        System.out.println("0");
        System.out.println("Time is up!");
        System.exit(0);
    }
}
