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

    public boolean checkWin(ArrayList<ArrayList<Disc>> grid, Disc disc) {
        this.grid = grid;
        this.disc = disc;

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
        checkSide(0);
        if (count == 3) { return true; }
        return false;
    }

    private boolean checkingLeft = true;
    private boolean checkingRight = false;
    private int count = 0;

    private void checkSide(int incr){
        if(!checkingLeft && !checkingRight){
            return;
        }

        if (checkingLeft) {
            incr--;
        } else {
            incr++;
        }

        if (grid.get(disc.getPoint().getX() - incr) != null) {
            if (grid.get(disc.getPoint().getX() - incr).get(disc.getPoint().getY()).getDiscState() == disc.getDiscState()) {
                count++;
                checkSide(incr);
            } else {
                if (!checkingRight) {
                    checkingLeft = false;
                    checkingRight = true;
                    checkSide(0);
                } else {
                    checkingRight = false;
                    checkSide(0);
                }
            }
        } else {
            if (!checkingRight) {
                checkingLeft = false;
                checkingRight = true;
                checkSide(0);
            } else{
                checkingRight = false;
            }
        }
    }

    private Disc getDiscOnSide(int offsetX){
        return grid.get(disc.getPoint().getX() - offsetX).get(disc.getPoint().getY());
    }

    private boolean checkDiagonalLeft() {
        return false;
    }

    private boolean checkDiagonalRight() {
        return false;
    }
}
