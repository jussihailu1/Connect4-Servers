package net.javaguides.springboot.websocket.models;

import lombok.Getter;
import net.javaguides.springboot.websocket.enums.DiscState;

import java.util.ArrayList;

public class Match {
    @Getter
    private int id;
    @Getter
    private ArrayList<Player> players;
    @Getter
    private ArrayList<ArrayList<Disc>> grid;
    @Getter
    private int turn;

    public Match(Player player) {
        players.add(player);
    }

    public void addPlayer(Player player) {
        players.add(player);
        initiateGame();
    }

    private void initiateGame() {
        createGrid();
        setRandomTurn();
    }

    private void setRandomTurn() {
        turn = (int) Math.round(Math.random());
    }

    private void createGrid() {
        grid = new ArrayList<>();
        for (int x = 0; x < 7; x++) {
            ArrayList<Disc> column = new ArrayList<>();
            for (int y = 0; y < 6; y++) {
                column.add(new Disc(new Point(x, y)));
            }
            grid.add(column);
        }
    }

    public Disc placeDisc(Point point) {
        DiscState discState = DiscState.values()[turn];
        Disc disc = findDisc(point);
        disc.place(discState);
        switchTurn();
        return disc;
    }

    private void switchTurn() {
        turn = 1 - turn;
    }

    private Disc findDisc(Point point) {
        ArrayList<Disc> column = grid.get(point.getX());
        for (int y = column.size() - 1; y >= 0; y--) {
            Disc disc = column.get(y);
            if (disc.getDiscState() == DiscState.NOT_CLICKED) {
                return disc;
            }
        }
        return null;
    }
}
