package main.java.sk.tuke.gamestudio.game.dots.core;

import main.java.sk.tuke.gamestudio.game.dots.features.Color;

public class Cursor implements GameMode{
    protected int posX;
    protected int posY;
    public String prevColor;
    private final GameBoard field;
    public Cursor(GameBoard field) {
        this.field = field;
    }
    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void moveUp() {
        if (posX == 0) {
            return;
        }
        field.gameBoard[posX][posY].dot = prevColor;
        posX--;
        prevColor = field.gameBoard[posX][posY].dot;
        field.gameBoard[posX][posY].dot = selectDot(field.gameBoard[posX][posY]);
    }

    public void moveDown() {
        if (posX == field.getBoardSize()-1) {
            return;
        }
        field.gameBoard[posX][posY].dot = prevColor;
        posX++;
        prevColor = field.gameBoard[posX][posY].dot;
        field.gameBoard[posX][posY].dot = selectDot(field.gameBoard[posX][posY]);
    }

    public void moveRight() {
        if (posY == field.getBoardSize()-1) {
            return;
        }
        field.gameBoard[posX][posY].dot = prevColor;
        posY++;
        prevColor = field.gameBoard[posX][posY].dot;
        field.gameBoard[posX][posY].dot = selectDot(field.gameBoard[posX][posY]);
    }

    public void moveLeft() {
        if (posY == 0) {
            return;
        }
        field.gameBoard[posX][posY].dot = prevColor;
        posY--;
        prevColor = field.gameBoard[posX][posY].dot;
        field.gameBoard[posX][posY].dot = selectDot(field.gameBoard[posX][posY]);
    }

    public String selectDot(Dot dot) {
        String string = dot.dot;
        string = Color.WHITE_BACKGROUND + string;
        return string;
    }
}
