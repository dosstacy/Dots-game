package main.java.sk.tuke.gamestudio.game.dots.core;

public interface GameMode {
    void moveUp();
    void moveDown();
    void moveRight();
    void moveLeft();
    String selectDot(String string);
}
