package main.java.sk.tuke.gamestudio.game.dots.core;

import main.java.sk.tuke.gamestudio.game.dots.features.Color;
import main.java.sk.tuke.gamestudio.game.dots.features.DotState;

public class Dot {
    private DotState state;
    public String dot;
    public Dot(String dot) {
        state = DotState.NOT_SELECTED;
        this.dot = dot;
    }

    public String generateColors(){
        String string = Color.randomColor() + "◯" + Color.ANSI_RESET;
        return string;
    }

    public void setState(DotState state) {
        this.state = state;
    }

    public DotState getState() {
        return state;
    }

}
