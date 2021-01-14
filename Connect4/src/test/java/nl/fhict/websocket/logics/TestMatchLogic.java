package nl.fhict.websocket.logics;

import nl.fhict.websocket.controller.MessageController;
import nl.fhict.websocket.models.Disc;
import nl.fhict.websocket.models.Match;
import nl.fhict.websocket.models.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestMatchLogic {

    private MatchLogic matchLogic;

    @BeforeEach
    void setUp() {
        matchLogic = MatchLogic.getInstance(new MessageController());
    }

    @Test
    public void searchMatch_Test_Expect_Waiting_In_Lobby(){
        String player1 = "player1";

        Match match = matchLogic.searchMatch(player1);

        assertNull(match);
    }

    @Test
    public void searchMatch_Test_Expect_Lobby_Found(){
        String player1 = "player1";
        String player2 = "player2";

        matchLogic.searchMatch(player1);
        Match match = matchLogic.searchMatch(player2);

        assertNotNull(match);
    }

    @Test
    public void searchMatch_Test_Both_Players_In_Lobby(){
        String player1 = "player1";
        String player2 = "player2";

        matchLogic.searchMatch(player1);
        Match match = matchLogic.searchMatch(player2);

        assertEquals(player1, match.getPlayers().get(0).getUsername());
        assertEquals(player2, match.getPlayers().get(1).getUsername());
    }

    @Test
    public void placeDisc_Test(){
        String player1 = "player1";
        String player2 = "player2";
        matchLogic.searchMatch(player1);
        Match match = matchLogic.searchMatch(player2);
        int matchId = match.getId();
        int pointX = 6;
        int pointY = 2;
        Point point = new Point(pointX, pointY);

        matchLogic.placeDisc(matchId, point);
        Disc placedDisc = match.getGrid().get(pointX).get(pointY);

        assertNotNull(placedDisc);
    }
}
