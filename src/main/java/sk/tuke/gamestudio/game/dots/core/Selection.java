package main.java.sk.tuke.gamestudio.game.dots.core;

import main.java.sk.tuke.gamestudio.game.dots.features.Color;

public class Selection implements GameMode{
    private int posX;
    private int posY;
    private final GameBoard field;
    private int x;
    private int y;
    private String temp;
    int dotsCount = 0;
    public Selection(GameBoard field) {
        this.field = field;
        posX = 0;
        posY = 0;
        x = 0;
        y = 0;
        temp = null;
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
        x = posX;

        //temp = resetSelection(field.selectedDots[posX][posY]);
        field.selectedDots[posX][posY] = field.gameBoard[posX][posY];
        if(field.gameBoard[posX][posY].contains(field.gameBoard[x-1][posY])){
            //System.out.println(selectionState(field.gameBoard[x-1][posY]) + field.gameBoard[x-1][posY] + x + posY);
            if(!selectionState(field.gameBoard[x-1][posY])){
                //System.out.println("X2: " + x);
                posX--;
                field.gameBoard[posX][posY] = selectDot(field.gameBoard[posX][posY]);
                field.selectedDots[posX][posY] = field.gameBoard[posX][posY];
            }else{
                //System.out.println("you have already chose this color");
                //System.out.println(posX + " " + posY + " " + field.gameBoard[posX][posY]);
                field.gameBoard[posX][posY] = resetSelection(field.gameBoard[posX][posY]);
                //System.out.println(posX + " " + posY + " " + field.gameBoard[posX][posY]);
                field.selectedDots[posX][posY] = "0";
                posX--;
                //System.out.println(posX + " " + posY + " " + field.gameBoard[posX][posY]);
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
        x = posX;

        field.selectedDots[posX][posY] = field.gameBoard[posX][posY];
        if(field.gameBoard[posX][posY].contains(field.gameBoard[x+1][posY])){
            if(!selectionState(field.gameBoard[x+1][posY])) {
                posX++;
                field.gameBoard[posX][posY] = selectDot(field.gameBoard[posX][posY]);
                field.selectedDots[posX][posY] = field.gameBoard[posX][posY];
            }else{
                System.out.println("you have already chose this color");
                System.out.println(posX + " " + posY + " " + field.gameBoard[posX][posY]);
                field.gameBoard[posX][posY] = resetSelection(field.gameBoard[posX][posY]);
                System.out.println(posX + " " + posY + " " + field.gameBoard[posX][posY]);
                field.selectedDots[posX][posY] = "0";
                posX++;
                System.out.println(posX + " " + posY + " " + field.gameBoard[posX][posY]);
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
        y = posY;

        field.selectedDots[posX][posY] = field.gameBoard[posX][posY];
        if(field.gameBoard[posX][posY].contains(field.gameBoard[posX][y+1])){
            if(!selectionState(field.gameBoard[posX][y+1])) {
                posY++;
                field.gameBoard[posX][posY] = selectDot(field.gameBoard[posX][posY]);
                field.selectedDots[posX][posY] = field.gameBoard[posX][posY];
            }else{
                System.out.println("you have already chose this color");
                System.out.println(posX + " " + posY + " " + field.gameBoard[posX][posY]);
                field.gameBoard[posX][posY] = resetSelection(field.gameBoard[posX][posY]);
                System.out.println(posX + " " + posY + " " + field.gameBoard[posX][posY]);
                field.selectedDots[posX][posY] = "0";
                posY++;
                System.out.println(posX + " " + posY + " " + field.gameBoard[posX][posY]);
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
        y = posY;

        field.selectedDots[posX][posY] = field.gameBoard[posX][posY];
        if(field.gameBoard[posX][posY].contains(field.gameBoard[posX][y-1])){
            if(!selectionState(field.gameBoard[posX][y-1])) {
                posY--;
                field.gameBoard[posX][posY] = selectDot(field.gameBoard[posX][posY]);
                field.selectedDots[posX][posY] = field.gameBoard[posX][posY];
            }else{
                System.out.println("you have already chose this color");
                System.out.println(posX + " " + posY + " " + field.gameBoard[posX][posY]);
                field.gameBoard[posX][posY] = resetSelection(field.gameBoard[posX][posY]);
                System.out.println(posX + " " + posY + " " + field.gameBoard[posX][posY]);
                field.selectedDots[posX][posY] = "0";
                posY--;
                System.out.println(posX + " " + posY + " " + field.gameBoard[posX][posY]);
            }
        }else{
            System.out.println("Not the same color!");
        }
    }

    @Override
    public String selectDot(String string) {
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
                if(board.gameBoard[i][j].contains(Color.RED_BACKGROUND)){
                    board.gameBoard[i][j] = board.gameBoard[i][j].replace(Color.RED_BACKGROUND, "");
                } else if (board.gameBoard[i][j].contains(Color.BLUE_BACKGROUND)) {
                    board.gameBoard[i][j] = board.gameBoard[i][j].replace(Color.BLUE_BACKGROUND, "");
                } else if (board.gameBoard[i][j].contains(Color.GREEN_BACKGROUND)) {
                    board.gameBoard[i][j] = board.gameBoard[i][j].replace(Color.GREEN_BACKGROUND, "");
                } else if (board.gameBoard[i][j].contains(Color.PURPLE_BACKGROUND)) {
                    board.gameBoard[i][j] = board.gameBoard[i][j].replace(Color.PURPLE_BACKGROUND, "");
                } else if (board.gameBoard[i][j].contains(Color.YELLOW_BACKGROUND)) {
                    board.gameBoard[i][j] = board.gameBoard[i][j].replace(Color.YELLOW_BACKGROUND, "");
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

    public boolean selectionState(String string){
        if(string.contains(Color.RED_BACKGROUND)){
            return true;
        } else if (string.contains(Color.BLUE_BACKGROUND)) {
            return true;
        } else if (string.contains(Color.GREEN_BACKGROUND)) {
            return true;
        } else if (string.contains(Color.PURPLE_BACKGROUND)) {
            return true;
        } else if (string.contains(Color.YELLOW_BACKGROUND)) {
            return true;
        }
        return false;
    }
}
