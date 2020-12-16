package net.javaguides.springboot.websocket.controller;

import net.javaguides.springboot.websocket.enums.MessageType;
import net.javaguides.springboot.websocket.logics.MatchLogic;
import net.javaguides.springboot.websocket.messages.Message;
import net.javaguides.springboot.websocket.models.Match;
import net.javaguides.springboot.websocket.models.Player;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;


@Controller
public class GreetingController {
    //    @Autowired
//    private final SimpMessagingTemplate simpMessagingTemplate;
    private MatchLogic matchLogic;

//    public MessageController(SimpMessagingTemplate simpMessagingTemplate) {
//        this.simpMessagingTemplate = simpMessagingTemplate;
//        matchLogic = MatchLogic.getInstance();
//    }

    @MessageMapping("Test")
    @SendTo("/topic/Jussi")
    public String test(Message message) {
        return "Received message: " + message.senderDestination;
    }

    @MessageMapping("searchMatch")
    public void searchMatch(Message messageIn) {
        Message messageOut = new Message();
        Match match = matchLogic.searchMatch(messageIn.player);
        if (match != null) {
            messageOut.messageType = MessageType.MATCH_FOUND;
            messageOut.turn = match.getTurn();
            for (Player player : match.getPlayers()) {
                String destination = "/topic/" + player.getUsername();
//                simpMessagingTemplate.convertAndSend(destination, messageOut);
            }
        } else {
            messageOut.messageType = MessageType.SEARCHING_MATCH;
//            simpMessagingTemplate.convertAndSend(messageIn.senderDestination, messageOut);
        }
    }

    @MessageMapping("placeDisc")
    public void placeDisc(Message messageIn) {
        Message messageOut = new Message();
        messageOut.messageType = MessageType.PLACE_DISC;
        matchLogic.placeDisc(messageIn.lobbyId, messageIn.point);
    }
}
