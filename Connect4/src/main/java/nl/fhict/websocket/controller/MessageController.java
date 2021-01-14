package nl.fhict.websocket.controller;

import nl.fhict.websocket.enums.MessageType;
import nl.fhict.websocket.logics.MatchLogic;
import nl.fhict.websocket.messages.Message;
import nl.fhict.websocket.models.Disc;
import nl.fhict.websocket.models.Match;
import nl.fhict.websocket.models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;


@Controller
public class MessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    private MatchLogic matchLogic;

    public MessageController() {
        matchLogic = MatchLogic.getInstance(this);
    }

    @MessageMapping("searchMatch")
    public void searchMatch(Message messageIn) {
        Message messageOut = new Message();
        Match match = matchLogic.searchMatch(messageIn.username);
        if (match != null) {
            messageOut.messageType = MessageType.MATCH_FOUND;
            messageOut.turn = match.getTurn();
            messageOut.players = match.getPlayers();
            messageOut.matchId = match.getId();
            sendMessageToPlayers(match.getPlayers(), messageOut);
        } else {
            messageOut.messageType = MessageType.SEARCHING_MATCH;
            simpMessagingTemplate.convertAndSend(messageIn.senderDestination, messageOut);
        }
    }

    @MessageMapping("placeDisc")
    public void placeDisc(Message messageIn) {
        matchLogic.placeDisc(messageIn.matchId, messageIn.point);
    }

    public void sendPlaceDiscMessage(Match match, Disc disc) {
        Message message = new Message();
        message.messageType = MessageType.PLACE_DISC;
        message.disc = disc;
        message.turn = match.getTurn();
        message.players = match.getPlayers();
        sendMessageToPlayers(match.getPlayers(), message);
    }

    public void sendWinMessage(ArrayList<Player> players, Disc disc, ArrayList<Disc> winningDiscs, Player winner) {
        Message message = new Message();
        message.messageType = MessageType.GAME_WON;
        message.disc = disc;
        message.winningDiscs = winningDiscs;
        message.players = players;
        message.player = winner;
        sendMessageToPlayers(players, message);
    }

    public void sendDiscNotPlacedMessage(ArrayList<Player> players) {
        Message message = new Message();
        message.messageType = MessageType.DISC_NOT_PLACED;
        sendMessageToPlayers(players, message);
    }

    private void sendMessageToPlayers(ArrayList<Player> players, Message message) {
        for (Player player : players) {
            String destination = "/topic/" + player.getUsername();
            simpMessagingTemplate.convertAndSend(destination, message);
        }
    }
}
