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

    private int gridWidth;
    private int gridHeight;

    private boolean checkingLeft;
    private boolean checkingRight;
    private int count;
    private int leftCount;
    private int rightCount;

    public boolean checkWin(ArrayList<ArrayList<Disc>> grid, Disc disc) {
        this.grid = grid;
        this.disc = disc;
        this.gridWidth = grid.size();
        this.gridHeight = grid.get(0).size();
        this.checkingLeft = true;
        this.checkingRight = false;
        this.count = 0;

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

    private Disc getDiscBelow(int offsetY) {
        return grid.get(disc.getPoint().getX()).get(disc.getPoint().getY() + offsetY);
    }

    private boolean checkHorizontal() {
        checkSide(0);
        if (count == 3) {
            return true;
        }
        return false;
    }

    private void checkSide(int incr) {
        if (!checkingLeft && !checkingRight) {
            return;
        }

        if (checkingLeft) {
            incr--;
        } else {
            incr++;
        }

        if (disc.getPoint().getX() - incr > -1) {
            if (grid.get(disc.getPoint().getX() - incr).get(disc.getPoint().getY()).getDiscState() == disc.getDiscState()) {
                count++;
                checkSide(incr);
            } else {
                if (!checkingRight) {
                    checkingLeft = false;
                    checkingRight = true;
                } else {
                    checkingRight = false;
                }
                checkSide(0);
            }
        } else {
            if (!checkingRight) {
                checkingLeft = false;
                checkingRight = true;
                checkSide(0);
            } else {
                checkingRight = false;
            }
        }
    }

    private Disc getDiscOnSide(int offsetX) {
        return grid.get(disc.getPoint().getX() - offsetX).get(disc.getPoint().getY());
    }

    private boolean checkDiagonalLeft() {
        leftCount = 0;
        rightCount = 0;

        checkLeftDown(1);

        if (leftCount + rightCount == 3) {
            return true;
        }
        return false;
    }

    private boolean checkDiagonalRight() {
        leftCount = 0;
        rightCount = 0;

        checkLeftUp(1);

        if (leftCount + rightCount == 3) { return true; }
        return false;
    }

    private void checkLeftDown(int incr) {
        if (disc.getPoint().getX() - incr > -1 && disc.getPoint().getY() + incr < gridHeight) {
            if (grid.get(disc.getPoint().getX() - incr).get(disc.getPoint().getY() + incr).getDiscState() == disc.getDiscState()) {
                leftCount++;
                checkLeftDown(++incr);
            } else {
                checkRightUp(1);
            }
        } else {
            checkRightUp(1);
        }
    }

    private void checkRightUp(int incr) {
        if (disc.getPoint().getX() + incr < gridWidth && disc.getPoint().getY() - incr > -1) {
            if (grid.get(disc.getPoint().getX() + incr).get(disc.getPoint().getY() - incr).getDiscState() == disc.getDiscState()) {
                rightCount++;
                checkRightUp(++incr);
            }
        }
    }

    private void checkLeftUp(int incr) {
        if (disc.getPoint().getX() - incr > -1 && disc.getPoint().getY() - incr > -1) {
            if (grid.get(disc.getPoint().getX() - incr).get(disc.getPoint().getY() - incr).getDiscState() == disc.getDiscState()) {
                leftCount++;
                checkLeftUp(++incr);
            } else {
                checkRightDown(1);
            }
        } else {
            checkRightDown(1);
        }
    }

    private void checkRightDown(int incr) {
        if (disc.getPoint().getX() + incr < gridWidth && disc.getPoint().getY() + incr < gridHeight) {
            if (grid.get(disc.getPoint().getX() + incr).get(disc.getPoint().getY() + incr).getDiscState() == disc.getDiscState()) {
                rightCount++;
                checkRightDown(++incr);
            }
        }
    }
}
