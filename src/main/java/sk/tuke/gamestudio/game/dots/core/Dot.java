package sk.tuke.gamestudio.game.dots.core;
import sk.tuke.gamestudio.game.dots.features.DotState;

public class Dot {
    private DotState state;
    public String dot;
    public Dot(String dot) {
        state = DotState.NOT_SELECTED;
        this.dot = dot;
    }

    public String getDot() {
        return dot;
    }

    public void setState(DotState state) {
        this.state = state;
    }

    public DotState getState() {
        return state;
    }

}
