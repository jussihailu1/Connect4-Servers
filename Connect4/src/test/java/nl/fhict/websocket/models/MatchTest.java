package nl.fhict.websocket.models;

import nl.fhict.websocket.enums.DiscState;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MatchTest {

    @Test
    public void Match_Is_Created() {
        int matchId = 0;
        Player player1 = new Player("player1");

        Match match = new Match(player1, matchId);

        assertNotNull(match);
        assertEquals(player1, match.getPlayers().get(0));
        assertEquals(matchId, match.getId());
    }

    @Test
    public void Match_Has_Player() {
        int matchId = 0;
        Player player1 = new Player("player1");

        Match match = new Match(player1, matchId);

        assertEquals(1, match.getPlayers().size());
        assertEquals(player1, match.getPlayers().get(0));
        assertEquals(matchId, match.getId());
    }

    @Test
    public void Match_Has_Correct_Id() {
        int matchId = 0;
        Player player1 = new Player("player1");

        Match match = new Match(player1, matchId);

        assertEquals(matchId, match.getId());
    }

    @Test
    public void Grid_Is_Right_Size() {
        int matchId = 0;
        Player player1 = new Player("player1");

        Match match = new Match(player1, matchId);

        assertEquals(7, match.getGridWidth());
        assertEquals(6, match.getGridHeight());
    }

    @Test
    public void Actual_Grid_Is_Right_Size() {
        int matchId = 0;
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        Match match = new Match(player1, matchId);

        match.addPlayer(player2);

        int x = match.getGridWidth() - 1;
        int y = match.getGridHeight() - 1;

        assertNotNull(match.getGrid().get(x).get(y));
    }

    @Test
    public void DiscStates_At_Start_Are_Correct() {
        int matchId = 0;
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        Match match = new Match(player1, matchId);

        match.addPlayer(player2);

        boolean allDiscStatesAreNOT_CLICKED = true;

        outerloop:
        for (int x = 0; x < match.getGridWidth(); x++) {
            for (int y = 0; y < match.getGridHeight(); y++) {
                Disc disc = match.getGrid().get(x).get(y);
                if (disc.getDiscState() != DiscState.NOT_CLICKED) {
                    allDiscStatesAreNOT_CLICKED = false;
                    break outerloop;
                }
            }
        }

        assertTrue(allDiscStatesAreNOT_CLICKED);
    }

    @Test
    public void Add_Player_To_Match() {
        int matchId = 0;
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        Match match = new Match(player1, matchId);

        match.addPlayer(player2);

        assertEquals(2, match.getPlayers().size());
        assertEquals(player2, match.getPlayers().get(1));
    }

    @Test
    public void Player_Ids_Are_Set_Correctly() {
        int matchId = 0;
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        Match match = new Match(player1, matchId);

        match.addPlayer(player2);

        assertEquals(0, player1.getId());
        assertEquals(1, player2.getId());
    }

    @Test
    public void Turn_Is_Set_Randomly() {
        double sum = 0;
        double totalTests = 1000;
        for (int i = 0; i < totalTests; i++) {
            Match match = new Match(new Player("player1"), 0);
            match.addPlayer(new Player("player2"));
            sum += match.getTurn();
        }

        double average = sum / totalTests;

        boolean turnIsSetRandomly = average > 0.49 && average < 0.51;

        assertTrue(turnIsSetRandomly);
    }

    @Test
    public void Place_Disc_On_Lowest_Row() {
        int matchId = 0;
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        Match match = new Match(player1, matchId);
        match.addPlayer(player2);
        int lowestY = match.getGridHeight() - 1;
        Point point = new Point(0, lowestY);

        Disc disc = match.placeDisc(point);

        assertEquals(point.getX(), disc.getPoint().getX());
        assertEquals(point.getY(), disc.getPoint().getY());
    }

    @Test
    public void Place_Disc_Not_On_Lowest_Row() {
        int matchId = 0;
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        Match match = new Match(player1, matchId);
        match.addPlayer(player2);
        Point point = new Point(2, 4);

        Disc disc = match.placeDisc(point);

        int lowestY = match.getGridHeight() - 1;

        assertEquals(point.getX(), disc.getPoint().getX());
        assertEquals(lowestY, disc.getPoint().getY());
    }

    @Test
    public void placeDisc_Returns_Right_Disc() {
        int matchId = 0;
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        Match match = new Match(player1, matchId);
        match.addPlayer(player2);
        int x = 2;
        int lowestY = match.getGridHeight() - 1;
        Point point = new Point(x, lowestY);

        Disc disc = match.placeDisc(point);
        Disc expectedDisc = match.getGrid().get(x).get(lowestY);

        assertEquals(expectedDisc, disc);
    }

    @Test
    public void getLastPLacedPlayer_Returns_Right_Player() {
        int matchId = 0;
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        Match match = new Match(player1, matchId);
        match.addPlayer(player2);
        Point point = new Point(0, 0);
        Disc disc = match.placeDisc(point);

        if (match.getTurn() == 0) {
            assertEquals(player2, match.getLastPlacedPlayer());
        } else {
            assertEquals(player1, match.getLastPlacedPlayer());
        }
    }

    @Test
    public void discCanBePlaced_Expect_False() {
        int matchId = 0;
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        Match match = new Match(player1, matchId);
        match.addPlayer(player2);
        Point point = new Point(0, 0);
        for (int i = 0; i < match.getGridHeight(); i++) {
            match.placeDisc(point);
        }

        boolean discCanBePlaced = match.discCanBePlaced(point);

        assertFalse(discCanBePlaced);
    }

    @Test
    public void discCanBePlaced_Expect_True() {
        int matchId = 0;
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        Match match = new Match(player1, matchId);
        match.addPlayer(player2);
        Point point = new Point(0, 0);
        for (int i = 0; i < match.getGridHeight() - 1; i++) {
            match.placeDisc(point);
        }

        boolean discCanBePlaced = match.discCanBePlaced(point);

        assertTrue(discCanBePlaced);
    }
}
