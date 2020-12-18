package nl.fhict.websocket.messages;


import nl.fhict.websocket.enums.MessageType;
import nl.fhict.websocket.models.Disc;
import nl.fhict.websocket.models.Player;
import nl.fhict.websocket.models.Point;

import java.util.ArrayList;

public class Message {
    public MessageType messageType;
    public String senderDestination;
    public String username;
    public Point point;
    public ArrayList<Player> players;
    public int matchId;
    public int turn;
    public Disc disc;
    public boolean discIsPlaced;
    public Player winner;

//    Constructors with parameters for every situation
//    Maybe singleton style like: Message messageOut = Message.get...Message(...,  ..., ...);
}
