package nl.fhict.websocket.models;

import nl.fhict.websocket.enums.DiscState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiscTest {

    @Test
    void Point_Is_Set_Correctly_When_Creating_New_Disc() {
        Point point = new Point(0, 0);
        Disc disc = new Disc(point);

        assertEquals(point, disc.getPoint());
    }

    @Test
    void DiscState_Is_Set_Correctly_When_Creating_New_Disc() {
        Point point = new Point(0, 0);
        Disc disc = new Disc(point);

        assertEquals(DiscState.NOT_CLICKED, disc.getDiscState());
    }

    @Test
    void DiscState_Is_Set_Correctly_When_Placing_Disc() {
        DiscState newDiscState = DiscState.P1;
        Point point = new Point(0, 0);
        Disc disc = new Disc(point);

        disc.place(newDiscState);

        assertEquals(newDiscState, disc.getDiscState());
    }
}
