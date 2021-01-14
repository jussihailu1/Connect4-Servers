package nl.fhict.websocket.logics;

import nl.fhict.websocket.models.Disc;
import nl.fhict.websocket.models.Match;
import nl.fhict.websocket.models.Player;
import nl.fhict.websocket.models.Point;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CheckWinLogicTest {

    private CheckWinLogic checkWinLogic;

    @BeforeEach
    void setUp() {
        checkWinLogic = CheckWinLogic.getInstance();
    }

    @Test
    void checkWin_No_Win() {
        int matchId = 0;
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        Match match = new Match(player1, matchId);
        match.addPlayer(player2);
        Point point1 = new Point(0, 0);
        Point point2 = new Point(1, 0);

        match.placeDisc(point1);
        match.placeDisc(point1);
        match.placeDisc(point2);
        match.placeDisc(point2);
        match.placeDisc(point1);
        match.placeDisc(point1);

        Disc disc = match.placeDisc(point2);

        boolean anyWin = checkWinLogic.checkWin(match.getGrid(), match.getGridWidth(), match.getGridHeight(), disc);

        assertFalse(anyWin);
    }

    @Test
    void checkWin_Vertical() {
        int matchId = 0;
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        Match match = new Match(player1, matchId);
        match.addPlayer(player2);
        Point point1 = new Point(0, 0);
        Point point2 = new Point(1, 0);

        match.placeDisc(point1);
        match.placeDisc(point2);
        match.placeDisc(point1);
        match.placeDisc(point2);
        match.placeDisc(point1);
        match.placeDisc(point2);

        Disc disc = match.placeDisc(point1);

        boolean verticalWin = checkWinLogic.checkWin(match.getGrid(), match.getGridWidth(), match.getGridHeight(), disc);

        assertTrue(verticalWin);
    }

    @Test
    void checkWin_Horizontal() {
        int matchId = 0;
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        Match match = new Match(player1, matchId);
        match.addPlayer(player2);

        match.placeDisc(new Point(0, 0));
        match.placeDisc(new Point(0, 0));
        match.placeDisc(new Point(1, 0));
        match.placeDisc(new Point(1, 0));
        match.placeDisc(new Point(2, 0));
        match.placeDisc(new Point(2, 0));

        Disc disc = match.placeDisc(new Point(3, 0));

        boolean horizontalWin = checkWinLogic.checkWin(match.getGrid(), match.getGridWidth(), match.getGridHeight(), disc);

        assertTrue(horizontalWin);
    }

    @Test
    void checkWin_DiagonalLeft() {
        int matchId = 0;
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        Match match = new Match(player1, matchId);
        match.addPlayer(player2);

        match.placeDisc(new Point(0, 0));
        match.placeDisc(new Point(1, 0));
        match.placeDisc(new Point(1, 0));
        match.placeDisc(new Point(2, 0));
        match.placeDisc(new Point(3, 0));
        match.placeDisc(new Point(2, 0));
        match.placeDisc(new Point(2, 0));
        match.placeDisc(new Point(3, 0));
        match.placeDisc(new Point(4, 0));
        match.placeDisc(new Point(3, 0));

        Disc disc = match.placeDisc(new Point(3, 0));

        boolean diagonalLeftWin = checkWinLogic.checkWin(match.getGrid(), match.getGridWidth(), match.getGridHeight(), disc);

        assertTrue(diagonalLeftWin);
    }

    @Test
    void checkWin_DiagonalRight() {
        int matchId = 0;
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        Match match = new Match(player1, matchId);
        match.addPlayer(player2);

        match.placeDisc(new Point(4, 0));
        match.placeDisc(new Point(3, 0));
        match.placeDisc(new Point(3, 0));
        match.placeDisc(new Point(2, 0));
        match.placeDisc(new Point(1, 0));
        match.placeDisc(new Point(2, 0));
        match.placeDisc(new Point(2, 0));
        match.placeDisc(new Point(1, 0));
        match.placeDisc(new Point(0, 0));
        match.placeDisc(new Point(1, 0));

        Disc disc = match.placeDisc(new Point(1, 0));

        boolean diagonalLeftWin = checkWinLogic.checkWin(match.getGrid(), match.getGridWidth(), match.getGridHeight(), disc);

        assertTrue(diagonalLeftWin);
    }

    @Test
    void getWinningDiscs() {
        int matchId = 0;
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        Match match = new Match(player1, matchId);
        match.addPlayer(player2);
        Point pointWithWinningX = new Point(0, 0);
        Point point2 = new Point(1, 0);

        Disc winningDisc1 = match.placeDisc(pointWithWinningX);
        match.placeDisc(point2);
        Disc winningDisc2 = match.placeDisc(pointWithWinningX);
        match.placeDisc(point2);
        Disc winningDisc3 = match.placeDisc(pointWithWinningX);
        match.placeDisc(point2);
        Disc winningDisc4 = match.placeDisc(pointWithWinningX);

        checkWinLogic.checkWin(match.getGrid(), match.getGridWidth(), match.getGridHeight(), winningDisc4);

        ArrayList<Disc> expectedWinningDiscs = new ArrayList<>();
        expectedWinningDiscs.add(winningDisc1);
        expectedWinningDiscs.add(winningDisc2);
        expectedWinningDiscs.add(winningDisc3);
        expectedWinningDiscs.add(winningDisc4);

        assertTrue(expectedWinningDiscs.containsAll(checkWinLogic.getWinningDiscs()));
    }
}
