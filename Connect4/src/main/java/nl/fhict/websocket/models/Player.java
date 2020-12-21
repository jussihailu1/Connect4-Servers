package nl.fhict.websocket.models;

import lombok.Getter;
import lombok.Setter;

public class Player {
    @Getter @Setter private int id;
    @Getter private String username;
    @Getter private int discCount;

    public Player(String username){
        this.username = username;
        this.discCount = 21;
    }

    public void decreaseDiscCount() {
        discCount--;
    }
}
