package nl.fhict.websocket.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    public void Username_Is_Set_Correctly_When_Creating_New_Player(){
        String username = "player";
        Player player = new Player(username);

        assertEquals(username, player.getUsername());
    }

    @Test
    public void DiscCount_Is_Set_Correctly_When_Creating_New_Player(){
        String username = "player";
        Player player = new Player(username);

        int expectedDiscCount = 21;

        assertEquals(expectedDiscCount, player.getDiscCount());
    }

    @Test
    void decreaseDiscCount() {
        String username = "player";
        Player player = new Player(username);
        int discCountBefore = player.getDiscCount();

        player.decreaseDiscCount();

        int discCountAfter = player.getDiscCount();

        assertEquals(discCountBefore, discCountAfter + 1);
    }
}
