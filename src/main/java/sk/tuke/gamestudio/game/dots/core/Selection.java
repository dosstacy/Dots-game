package main.java.sk.tuke.gamestudio.game.dots.core;

import main.java.sk.tuke.gamestudio.game.dots.features.Color;
import main.java.sk.tuke.gamestudio.game.dots.features.DotState;

public class Selection implements DotsAction {
    private int posX;
    private int posY;
    private final GameBoard field;
    int dotsCount = 0;
    public Selection(GameBoard field) {
        this.field = field;
        posX = 0;
        posY = 0;
    }

    public Selection() {
        this.field = null;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    @Override
    public void moveUp() {
        assert field != null;
        if (posX == 0) {
            return;
        }

        field.selectedDots[posX][posY].dot = field.gameBoard[posX][posY].dot;
        System.out.println("перед if " + field.gameBoard[posX][posY].dot + " " + posX + " " + posY);
        if(field.gameBoard[posX][posY].dot.contains(field.gameBoard[posX-1][posY].dot)){
            System.out.println("після першого if " + field.gameBoard[posX][posY].dot + " " + posX + " " + posY);
            System.out.println("перевіряю умову " + field.gameBoard[posX][posY].dot.contains(field.gameBoard[posX-1][posY].dot) + " " + field.gameBoard[posX][posY].dot + " " + field.gameBoard[posX-1][posY].dot);
            if(field.gameBoard[posX-1][posY].getState() == DotState.NOT_SELECTED){
                System.out.println("після if getsState " + field.gameBoard[posX][posY].dot + " " + posX + " " + posY);
                posX--;
                field.gameBoard[posX][posY].dot = selectDot(field.gameBoard[posX][posY]);
                field.gameBoard[posX][posY].setState(DotState.SELECTED);
                field.selectedDots[posX][posY].dot = field.gameBoard[posX][posY].dot;
                System.out.println(field.gameBoard[posX][posY].dot + " " + posX + " " + posY);
            }else{
                System.out.println("you have already chose this color");
                System.out.println(field.gameBoard[posX][posY].dot + " " + posX + " " + posY);
                System.out.println(field.gameBoard[posX-1][posY].dot + " " + (posX-1) + " " + posY + " " + field.gameBoard[posX-1][posY].getState());
                field.gameBoard[posX][posY].dot = resetSelection(field.gameBoard[posX][posY].dot);
                field.gameBoard[posX][posY].setState(DotState.NOT_SELECTED);
                field.selectedDots[posX][posY].dot = "0";
                posX--;
            }
        }else{
            System.out.println("Not the same color!");
        }
    }

    @Override
    public void moveDown() {
        assert field != null;
        if (posX == field.getBoardSize()-1) {
            return;
        }

        field.selectedDots[posX][posY].dot = field.gameBoard[posX][posY].dot;
        System.out.println("перед if " + field.gameBoard[posX][posY].dot + " " + posX + " " + posY);
        if(field.gameBoard[posX][posY].dot.contains(field.gameBoard[posX+1][posY].dot)){
            System.out.println("після першого if " + field.gameBoard[posX][posY].dot + " " + posX + " " + posY);
            System.out.println("перевіряю умову " + field.gameBoard[posX][posY].dot.contains(field.gameBoard[posX+1][posY].dot) + " " + field.gameBoard[posX][posY].dot + " " + field.gameBoard[posX+1][posY].dot);
            if(field.gameBoard[posX+1][posY].getState() == DotState.NOT_SELECTED) {
                System.out.println("після if getsState " + field.gameBoard[posX][posY].dot + " " + posX + " " + posY);
                posX++;
                field.gameBoard[posX][posY].dot = selectDot(field.gameBoard[posX][posY]);
                field.gameBoard[posX][posY].setState(DotState.SELECTED);
                field.selectedDots[posX][posY].dot = field.gameBoard[posX][posY].dot;
                System.out.println(field.gameBoard[posX][posY].dot + " " + posX + " " + posY);
            }else{
                System.out.println("you have already chose this color");
                System.out.println(field.gameBoard[posX][posY].dot + " " + posX + " " + posY);
                System.out.println(field.gameBoard[posX+1][posY].dot + " " + (posX+1) + " " + posY + " " + field.gameBoard[posX+1][posY].getState());
                field.gameBoard[posX][posY].dot = resetSelection(field.gameBoard[posX][posY].dot);
                field.gameBoard[posX][posY].setState(DotState.NOT_SELECTED);
                field.selectedDots[posX][posY].dot = "0";
                posX++;
            }
        }else{
            System.out.println("Not the same color!");
        }
    }

    @Override
    public void moveRight() {
        assert field != null;
        if (posY == field.getBoardSize()-1) {
            return;
        }

        field.selectedDots[posX][posY].dot = field.gameBoard[posX][posY].dot;
        System.out.println("перед if " + field.gameBoard[posX][posY].dot + " " + posX + " " + posY);
        if(field.gameBoard[posX][posY].dot.contains(field.gameBoard[posX][posY+1].dot)){
            System.out.println("після першого if " + field.gameBoard[posX][posY].dot + " " + posX + " " + posY);
            System.out.println("перевіряю умову " + field.gameBoard[posX][posY].dot.contains(field.gameBoard[posX][posY+1].dot) + " " + field.gameBoard[posX][posY].dot + " " + field.gameBoard[posX][posY+1].dot);
            if(field.gameBoard[posX][posY+1].getState() == DotState.NOT_SELECTED) {
                System.out.println("після if getsState " + field.gameBoard[posX][posY].dot + " " + posX + " " + posY);
                posY++;
                field.gameBoard[posX][posY].dot = selectDot(field.gameBoard[posX][posY]);
                field.gameBoard[posX][posY].setState(DotState.SELECTED);
                field.selectedDots[posX][posY].dot = field.gameBoard[posX][posY].dot;
                System.out.println(field.gameBoard[posX][posY].dot + " " + posX + " " + posY);
            }else{
                System.out.println("you have already chose this color");
                System.out.println(field.gameBoard[posX][posY].dot + " " + posX + " " + posY);
                System.out.println(field.gameBoard[posX][posY+1].dot + " " + posX + " " + (posY+1) + " " + field.gameBoard[posX][posY+1].getState());
                field.gameBoard[posX][posY].dot = resetSelection(field.gameBoard[posX][posY].dot);
                field.selectedDots[posX][posY].dot = "0";
                posY++;
            }
        }else{
            System.out.println("Not the same color!");
        }
    }

    @Override
    public void moveLeft() {
        assert field != null;
        if (posY == 0) {
            return;
        }

        field.selectedDots[posX][posY].dot = field.gameBoard[posX][posY].dot;
        System.out.println("перед if " + field.gameBoard[posX][posY].dot + " " + posX + " " + posY);
        if(field.gameBoard[posX][posY].dot.contains(field.gameBoard[posX][posY-1].dot)){
            System.out.println("після першого if " + field.gameBoard[posX][posY].dot + " " + posX + " " + posY);
            System.out.println("перевіряю умову " + field.gameBoard[posX][posY].dot.contains(field.gameBoard[posX][posY-1].dot) + " " + field.gameBoard[posX][posY].dot + " " + field.gameBoard[posX][posY-1].dot);
            if(field.gameBoard[posX][posY-1].getState() == DotState.NOT_SELECTED) {
                System.out.println("після if getsState " + field.gameBoard[posX][posY].dot + " " + posX + " " + posY);
                posY--;
                field.gameBoard[posX][posY].dot = selectDot(field.gameBoard[posX][posY]);
                field.gameBoard[posX][posY].setState(DotState.SELECTED);
                field.selectedDots[posX][posY].dot = field.gameBoard[posX][posY].dot;
                System.out.println(field.gameBoard[posX][posY].dot + " " + posX + " " + posY);
            }else{
                System.out.println("you have already chose this color");
                System.out.println(field.gameBoard[posX][posY].dot + " " + posX + " " + posY);
                System.out.println(field.gameBoard[posX][posY-1].dot + " " + posX + " " + (posY-1) + " " + field.gameBoard[posX][posY-1].getState());
                field.gameBoard[posX][posY].dot = resetSelection(field.gameBoard[posX][posY].dot);
                field.gameBoard[posX][posY].setState(DotState.NOT_SELECTED);
                field.selectedDots[posX][posY].dot = "0";
                posY--;
            }
        }else{
            System.out.println("Not the same color!");
        }
    }

    @Override
    public String selectDot(Dot dot) {
        String string = dot.dot;
        if(string.contains(Color.ANSI_RED)){
            return Color.RED_BACKGROUND + string;
        } else if (string.contains(Color.ANSI_BLUE)) {
            return Color.BLUE_BACKGROUND + string;
        } else if (string.contains(Color.ANSI_GREEN)) {
            return Color.GREEN_BACKGROUND + string;
        } else if (string.contains(Color.ANSI_PURPLE)) {
            return Color.PURPLE_BACKGROUND + string;
        } else if (string.contains(Color.ANSI_YELLOW)) {
            return Color.YELLOW_BACKGROUND + string;
        }else{
            return string;
        }
    }

    public void resetAllSelection(GameBoard board){
        for(int i = 0; i < board.getBoardSize(); i++){
            for(int j = 0; j < board.getBoardSize(); j++){
                if(board.gameBoard[i][j].dot.contains(Color.RED_BACKGROUND)){
                    board.gameBoard[i][j].dot = board.gameBoard[i][j].dot.replace(Color.RED_BACKGROUND, "");
                    board.gameBoard[i][j].setState(DotState.NOT_SELECTED);
                } else if (board.gameBoard[i][j].dot.contains(Color.BLUE_BACKGROUND)) {
                    board.gameBoard[i][j].dot = board.gameBoard[i][j].dot.replace(Color.BLUE_BACKGROUND, "");
                    board.gameBoard[i][j].setState(DotState.NOT_SELECTED);
                } else if (board.gameBoard[i][j].dot.contains(Color.GREEN_BACKGROUND)) {
                    board.gameBoard[i][j].dot = board.gameBoard[i][j].dot.replace(Color.GREEN_BACKGROUND, "");
                    board.gameBoard[i][j].setState(DotState.NOT_SELECTED);
                } else if (board.gameBoard[i][j].dot.contains(Color.PURPLE_BACKGROUND)) {
                    board.gameBoard[i][j].dot = board.gameBoard[i][j].dot.replace(Color.PURPLE_BACKGROUND, "");
                    board.gameBoard[i][j].setState(DotState.NOT_SELECTED);
                } else if (board.gameBoard[i][j].dot.contains(Color.YELLOW_BACKGROUND)) {
                    board.gameBoard[i][j].dot = board.gameBoard[i][j].dot.replace(Color.YELLOW_BACKGROUND, "");
                    board.gameBoard[i][j].setState(DotState.NOT_SELECTED);
                }
            }
        }
    }
    public String resetSelection(String string){
        if(string.contains(Color.RED_BACKGROUND)){
            string = string.replace(Color.RED_BACKGROUND, "");
        } else if (string.contains(Color.BLUE_BACKGROUND)) {
            string = string.replace(Color.BLUE_BACKGROUND, "");
        } else if (string.contains(Color.GREEN_BACKGROUND)) {
            string = string.replace(Color.GREEN_BACKGROUND, "");
        } else if (string.contains(Color.PURPLE_BACKGROUND)) {
            string = string.replace(Color.PURPLE_BACKGROUND, "");
        } else if (string.contains(Color.YELLOW_BACKGROUND)) {
            string = string.replace(Color.YELLOW_BACKGROUND, "");
        }
        return string;
    }
}
