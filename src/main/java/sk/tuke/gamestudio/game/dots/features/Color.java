package sk.tuke.gamestudio.game.dots.features;

import sk.tuke.gamestudio.game.dots.core.GameBoard;

import java.util.Random;

public class Color {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String PURPLE_BACKGROUND = "\u001B[45m";
    public static final String WHITE_BACKGROUND	 = "\u001B[47m";
    public static final String[] COLORS = {ANSI_RED, ANSI_GREEN, ANSI_YELLOW, ANSI_BLUE, ANSI_PURPLE};

    public static String randomColor() {
        Random rand = new Random();
        int index = rand.nextInt(COLORS.length);
        return COLORS[index];
    }

    public static String returnColor(String color) {
        if (color.contains(RED_BACKGROUND)) {
            return "red-selection red";
        } else if (color.contains(BLUE_BACKGROUND)) {
            return "blue-selection blue";
        } else if (color.contains(GREEN_BACKGROUND)) {
            return "green-selection green";
        } else if (color.contains(YELLOW_BACKGROUND)) {
            return "yellow-selection yellow";
        } else if (color.contains(PURPLE_BACKGROUND)) {
            return "purple-selection purple";
        }else if(color.contains(ANSI_RED)){
            return "red";
        }else if(color.contains(ANSI_GREEN)){
            return "green";
        }else if(color.contains(ANSI_YELLOW)){
            return "yellow";
        } else if (color.contains(ANSI_BLUE)) {
            return "blue";
        }else if(color.contains(ANSI_PURPLE)){
            return "purple";
        }else{
            return "bomb";
        }
    }
}
