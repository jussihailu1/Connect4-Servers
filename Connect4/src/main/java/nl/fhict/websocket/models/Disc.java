package nl.fhict.websocket.models;

import lombok.Getter;
import nl.fhict.websocket.enums.DiscState;

public class Disc {
    @Getter
    private Point point;
    @Getter
    private DiscState discState;

    public Disc(Point point){
        this.point = point;
        this.discState = DiscState.NOT_CLICKED;
    }

    public void place(DiscState discState){
        this.discState = discState;
    }
}
