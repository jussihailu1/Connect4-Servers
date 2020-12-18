package nl.fhict.websocket.logics;

import nl.fhict.websocket.models.Disc;

import java.util.ArrayList;

public class CheckWinLogic {
    private static CheckWinLogic INSTANCE;

    private CheckWinLogic() {
    }

    public static CheckWinLogic getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CheckWinLogic();
        }

        return INSTANCE;
    }

    private ArrayList<ArrayList<Disc>> grid;
    private Disc disc;
    private int leftCount;
    private int rightCount;

    public boolean checkWin(ArrayList<ArrayList<Disc>> grid, Disc disc) {
        this.grid = grid;
        this.disc = disc;
        this.leftCount = 0;
        this.rightCount = 0;

        if (checkVertical()) {
            return true;
        } else if (checkHorizontal()) {
            return true;
        } else if (checkDiagonalLeft()) {
            return true;
        } else if (checkDiagonalRight()) {
            return true;
        }

        return false;
    }

    private boolean checkVertical() {
        if (disc.getPoint().getY() < 3) {
            if (disc.getDiscState() == getDiscBelow(1).getDiscState()) {
                if (disc.getDiscState() == getDiscBelow(2).getDiscState()) {
                    if (disc.getDiscState() == getDiscBelow(3).getDiscState()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Disc getDiscBelow(int offsetY){
        return grid.get(disc.getPoint().getX()).get(disc.getPoint().getY() + offsetY);
    }

    private boolean checkHorizontal() {
        return false;
    }

    private void checkLeft(int incrementX){
        if (grid.get(disc.getPoint().getX() - incrementX) != null) {
            if (getDiscOnSide(incrementX).getDiscState() == disc.getDiscState()) {
                leftCount++;
                checkLeft(++incrementX);
            } else {
                checkRight(1);
            }
        } else {
            checkRight(1);
        }
    }

    private Disc getDiscOnSide(int offsetX){
        return grid.get(disc.getPoint().getX() - offsetX).get(disc.getPoint().getY());
    }

    private void checkRight(int incrementX){

    }

    private boolean checkDiagonalLeft() {
        return false;
    }

    private boolean checkDiagonalRight() {
        return false;
    }
}
