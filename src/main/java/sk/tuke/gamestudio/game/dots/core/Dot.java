package main.java.sk.tuke.gamestudio.game.dots.core;

import main.java.sk.tuke.gamestudio.game.dots.features.Color;
import main.java.sk.tuke.gamestudio.game.dots.features.DotState;

public class Dot {
    private DotState state;
    public String dot;
    private GameBoard gameBoard;

    public Dot(GameBoard gameBoard) {
        state = DotState.NOT_SELECTED;
        dot = Color.randomColor() + "◯" + Color.ANSI_RESET;
        this.gameBoard = new GameBoard();
    }
    public Dot() {
        state = DotState.NOT_SELECTED;
        dot = Color.randomColor() + "◯" + Color.ANSI_RESET;
    }

    void setState(DotState state) {
        this.state = state;
    }

    public DotState getState() {
        return state;
    }

}
