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

    private ArrayList<Disc> winningDiscs;

    public ArrayList<Disc> getWinningDiscs(){
        return this.winningDiscs;
    }

    public boolean checkWin(ArrayList<ArrayList<Disc>> grid, Disc disc) {
        this.grid = grid;
        this.disc = disc;
        this.gridWidth = grid.size();
        this.gridHeight = grid.get(0).size();

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
            this.winningDiscs = new ArrayList<>();

            Disc d1 = getDiscBelow(1);
            if (disc.getDiscState() == d1.getDiscState()) {
                winningDiscs.add(d1);

                Disc d2 = getDiscBelow(2);
                if (disc.getDiscState() == getDiscBelow(2).getDiscState()) {
                    winningDiscs.add(d2);

                    Disc d3 = getDiscBelow(3);
                    if (disc.getDiscState() == getDiscBelow(3).getDiscState()) {
                        winningDiscs.add(d3);

                        winningDiscs.add(disc);
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
        this.checkingLeft = true;
        this.checkingRight = false;
        this.count = 0;
        this.winningDiscs = new ArrayList<>();
        checkSide(0);
        if (count == 3) {
            winningDiscs.add(disc);
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
            Disc d = grid.get(disc.getPoint().getX() - incr).get(disc.getPoint().getY());
            if (d.getDiscState() == disc.getDiscState()) {
                count++;
                winningDiscs.add(d);
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

    private boolean checkDiagonalLeft() {
        this.checkingLeft = true;
        this.checkingRight = false;
        this.count = 0;
        this.leftCount = 0;
        this.rightCount = 0;
        this.winningDiscs = new ArrayList<>();

        checkLeftDown(1);

        if (leftCount + rightCount == 3) {
            winningDiscs.add(disc);
            return true;
        }
        return false;
    }

    private boolean checkDiagonalRight() {
        this.checkingLeft = true;
        this.checkingRight = false;
        this.count = 0;
        this.leftCount = 0;
        this.rightCount = 0;
        this.winningDiscs = new ArrayList<>();

        checkLeftUp(1);

        if (leftCount + rightCount == 3) {
            winningDiscs.add(disc);
            return true;
        }
        return false;
    }

    private void checkLeftDown(int incr) {
        if (disc.getPoint().getX() - incr > -1 && disc.getPoint().getY() + incr < gridHeight) {
            Disc d = grid.get(disc.getPoint().getX() - incr).get(disc.getPoint().getY() + incr);
            if (d.getDiscState() == disc.getDiscState()) {
                leftCount++;
                winningDiscs.add(d);
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
            Disc d = grid.get(disc.getPoint().getX() + incr).get(disc.getPoint().getY() - incr);
            if (d.getDiscState() == disc.getDiscState()) {
                rightCount++;
                winningDiscs.add(d);
                checkRightUp(++incr);
            }
        }
    }

    private void checkLeftUp(int incr) {
        if (disc.getPoint().getX() - incr > -1 && disc.getPoint().getY() - incr > -1) {
            Disc d = grid.get(disc.getPoint().getX() - incr).get(disc.getPoint().getY() - incr);
            if (d.getDiscState() == disc.getDiscState()) {
                leftCount++;
                winningDiscs.add(d);
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
            Disc d = grid.get(disc.getPoint().getX() + incr).get(disc.getPoint().getY() + incr);
            if (d.getDiscState() == disc.getDiscState()) {
                winningDiscs.add(d);
                rightCount++;
                checkRightDown(++incr);
            }
        }
    }
}
