package sk.tuke.gamestudio.game.dots.core;

public interface DotsAction {
    void moveUp();
    void moveDown();
    void moveRight();
    void moveLeft();
    String selectDot(Dot dot);
}
