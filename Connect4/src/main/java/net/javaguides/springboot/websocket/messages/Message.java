package net.javaguides.springboot.websocket.messages;


import net.javaguides.springboot.websocket.enums.MessageType;
import net.javaguides.springboot.websocket.models.Player;
import net.javaguides.springboot.websocket.models.Point;

public class Message {
    public MessageType messageType;
    public String senderDestination;
    public Player player;
    public Point point;
    public int lobbyId;
    public int turn;

//    Constructors with parameters for every situation
//    Maybe singleton style like: Message messageOut = Message.get...Message(...,  ..., ...);
}
