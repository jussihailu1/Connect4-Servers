package nl.fhict.websocket.models;

import lombok.Getter;

public class Player {
    @Getter private String username;
    @Getter int discCount;

    public Player(String username){
        this.username = username;
        this.discCount = 21;
    }
}
